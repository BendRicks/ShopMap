package ru.bendricks.shopmap.mapper.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.ShopDTO;
import ru.bendricks.shopmap.dto.entity.UserDTO;
import ru.bendricks.shopmap.entity.Shop;
import ru.bendricks.shopmap.entity.ShopStatus;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.mapper.address.AddressListMapper;
import ru.bendricks.shopmap.mapper.image.ImageListMapper;
import ru.bendricks.shopmap.mapper.user.UserMapper;

import java.time.LocalDateTime;

@Component
public class ShopMapper {

    private final AddressListMapper addressListMapper;
    private final ImageListMapper imageListMapper;

    @Autowired
    public ShopMapper(AddressListMapper addressListMapper, ImageListMapper imageListMapper) {
        this.addressListMapper = addressListMapper;
        this.imageListMapper = imageListMapper;
    }

    public Shop toModel(ShopDTO shopDTO) {
        if (shopDTO == null) {
            return null;
        }
        return Shop.builder()
                .id(shopDTO.getId())
                .name(shopDTO.getName())
                .description(shopDTO.getDescription())
                .creationTime(shopDTO.getCreationTime())
                .addresses(addressListMapper.dtoToModel(shopDTO.getAddresses()))
                .additionalImages(imageListMapper.createDTOToModel(shopDTO.getAdditionalImages()))
                .mainImageURL(shopDTO.getMainImageURL())
                .shopStatus(shopDTO.getShopStatus())
                .build();
    }

    ;

    public Shop createDTOToModel(ShopDTO shopCreateDTO) {
        if (shopCreateDTO == null) {
            return null;
        }
        return Shop.builder()
                .name(shopCreateDTO.getName())
                .description(shopCreateDTO.getDescription())
                .creationTime(LocalDateTime.now())
                .addresses(addressListMapper.createDTOToModel(shopCreateDTO.getAddresses()))
                .additionalImages(imageListMapper.createDTOToModel(shopCreateDTO.getAdditionalImages()))
                .mainImageURL(shopCreateDTO.getMainImageURL())
                .shopStatus(ShopStatus.IN_MODERATION)
                .build();
    }

    ;

    public ShopDTO toDTO(Shop shop) {
        if (shop == null || shop.getCreator() == null) {
            return null;
        }
        User user = shop.getCreator();
        return ShopDTO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .creator(
                        UserDTO.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .creationTime(user.getCreationTime())
                                .role(user.getRole())
                                .build()
                )
                .description(shop.getDescription())
                .mainImageURL(shop.getMainImageURL())
                .creationTime(shop.getCreationTime())
                .addresses(addressListMapper.toDTO(shop.getAddresses()))
                .additionalImages(imageListMapper.toDTO(shop.getAdditionalImages()))
                .shopStatus(shop.getShopStatus())
                .build();
    }

    ;

    public ShopDTO toDTORestricted(Shop shop) {
        if (shop == null) {
            return null;
        }
        return ShopDTO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .description(shop.getDescription())
                .mainImageURL(shop.getMainImageURL())
                .creationTime(shop.getCreationTime())
                .addresses(addressListMapper.toDTO(shop.getAddresses()))
                .additionalImages(imageListMapper.toDTO(shop.getAdditionalImages()))
                .shopStatus(shop.getShopStatus())
                .build();
    }

    ;

}
