package ru.bendricks.shopmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.bendricks.shopmap.entity.Shop;
import ru.bendricks.shopmap.entity.ShopStatus;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.entity.UserRole;
import ru.bendricks.shopmap.exception.TooMuchShopsException;
import ru.bendricks.shopmap.repository.ShopRepository;
import ru.bendricks.shopmap.security.UserDetailsInfo;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public List<Shop> getShops(){
        return shopRepository.findAll();
    }

    public Shop createShop(Shop shop){
        User user = ((UserDetailsInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).user();
        if (shopRepository.countByCreator(user) >= 3 && user.getRole() != UserRole.ROLE_ADMIN) {
            throw new TooMuchShopsException("Too much shops");
        }
        shop.setCreator(user);
        shop.getAddresses().forEach((el) -> el.setShop(shop));
        return shopRepository.save(shop);
    }

    public List<Shop> getShopsByNameAndPage(String nameLike, Integer page, Integer size, Sort.Direction direction, String param) {
        return shopRepository.findAllByString(nameLike, PageRequest.of(page, size, direction, param));
    }

    public Integer getShopsByNameAndPageMathcesAmount(String nameLike) {
        return shopRepository.getMatchesAmount(nameLike);
    }
}
