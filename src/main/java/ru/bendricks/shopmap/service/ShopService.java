package ru.bendricks.shopmap.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.shopmap.dto.entity.ShopDTO;
import ru.bendricks.shopmap.entity.Shop;
import ru.bendricks.shopmap.entity.ShopStatus;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.entity.UserRole;
import ru.bendricks.shopmap.exception.NotEnoughAuthoritiesException;
import ru.bendricks.shopmap.exception.NotFoundException;
import ru.bendricks.shopmap.exception.TooMuchShopsException;
import ru.bendricks.shopmap.mapper.shop.ShopListMapper;
import ru.bendricks.shopmap.mapper.shop.ShopMapper;
import ru.bendricks.shopmap.repository.ShopRepository;
import ru.bendricks.shopmap.security.UserDetailsInfo;

import java.util.List;

@Service
@Slf4j
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserService userService;
    private final ShopMapper mapper;
    private final ShopListMapper listMapper;

    @Autowired
    public ShopService(ShopRepository shopRepository, UserService userService, ShopMapper mapper, ShopListMapper listMapper) {
        this.shopRepository = shopRepository;
        this.userService = userService;
        this.mapper = mapper;
        this.listMapper = listMapper;
    }

    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ShopDTO createShop(ShopDTO shopDTO) {
        Shop shop = mapper.createDTOToModel(shopDTO);
        User user = ((UserDetailsInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).user();
        if (shopRepository.countByCreator(user) >= 3 && user.getRole() != UserRole.ROLE_ADMIN) {
            throw new TooMuchShopsException("Too much shops");
        }
        shop.setCreator(user);
        shop.getAddresses().forEach((el) -> el.setShop(shop));
        shop.getAdditionalImages().forEach((el) -> el.setShop(shop));
        return mapper.toDTO(shopRepository.save(shop));
    }

    public ShopDTO findShopById(int id) {
        return mapper.toDTO(findById(id));
    }

    private Shop findById(int id) {
        return shopRepository.findById(id).orElseThrow(() -> new NotFoundException("Contract"));
    }

    public List<ShopDTO> getShopsByNameAndPage(String nameLike, Integer page, Integer size, Sort.Direction direction, String param) {
        return listMapper.toDTO(shopRepository.findAllByString(nameLike, ShopStatus.OPERATING, PageRequest.of(page, size, direction, param)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<ShopDTO> getShopsInModeration(Integer page) {
        return listMapper.toDTO(shopRepository.findAllByString("", ShopStatus.IN_MODERATION, PageRequest.of(page, 8, Sort.Direction.ASC, "creationTime")));
    }

    public Integer getShopsByNameAndStatusAmount(String nameLike, ShopStatus status) {
        return shopRepository.getMatchesAmount(nameLike, status);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public Integer getShopsInModerationAmount() {
        return shopRepository.getMatchesAmount("", ShopStatus.IN_MODERATION);
    }

    @Transactional
    @PreAuthorize("isAuthenticated()")
    public ShopDTO updateShop(ShopDTO shopDTO) {
        final Shop shop = findById(shopDTO.getId());
        User user = ((UserDetailsInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).user();
        if (!shop.getCreator().getId().equals(user.getId())
                && !user.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_ADMIN.name()))
                && !user.getAuthorities().contains(new SimpleGrantedAuthority(UserRole.ROLE_MODERATOR.name()))) {
            throw new NotEnoughAuthoritiesException("Not enough authorities to do this");
        }
        shop.setShopStatus(ShopStatus.IN_MODERATION);
        merge(
                shop,
                mapper.toModel((shopDTO))
        );
        shop.getAddresses().forEach((el) -> el.setShop(shop));
        shop.getAdditionalImages().forEach((el) -> el.setShop(shop));
        log.info(shop.toString());
        return mapper.toDTO(
                shopRepository.save(
                        shop
                )
        );
    }

    private Shop merge(Shop dest, Shop src) {
        if (src.getName() != null) {
            dest.setName(src.getName());
        }
        if (src.getDescription() != null) {
            dest.setDescription(src.getDescription());
        }
        if (src.getShopStatus() != null) {
            dest.setShopStatus(src.getShopStatus());
        }
        if (src.getMainImageURL() != null) {
            dest.setMainImageURL(src.getMainImageURL());
        }
        if (src.getCreator() != null) {
            dest.setCreator(src.getCreator());
        }
        if (src.getAddresses() != null) {
            dest.getAddresses().clear();
            dest.getAddresses().addAll(src.getAddresses());
        }
        if (src.getAdditionalImages() != null) {
            dest.getAdditionalImages().clear();
            dest.getAdditionalImages().addAll(src.getAdditionalImages());
        }
        return dest;
    }
}
