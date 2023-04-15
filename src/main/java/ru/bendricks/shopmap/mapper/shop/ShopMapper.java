package ru.bendricks.shopmap.mapper.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.ShopDTO;
import ru.bendricks.shopmap.dto.entity.create.ShopCreateDTO;
import ru.bendricks.shopmap.entity.Shop;
import ru.bendricks.shopmap.entity.ShopStatus;
import ru.bendricks.shopmap.mapper.address.AddressListMapper;
import ru.bendricks.shopmap.mapper.user.UserMapper;

import java.time.LocalDateTime;

@Component
public class ShopMapper {

    private final AddressListMapper addressListMapper;
    private final UserMapper userMapper;

    @Autowired
    public ShopMapper(AddressListMapper addressListMapper, UserMapper userMapper) {
        this.addressListMapper = addressListMapper;
        this.userMapper = userMapper;
    }

    public Shop toModel(ShopDTO shopDTO){
        if (shopDTO == null){
            return null;
        }
        return Shop.builder()
                .id(shopDTO.getId())
                .name(shopDTO.getName())
                .creator(userMapper.toModel(shopDTO.getCreator()))
                .creationTime(shopDTO.getCreationTime())
                .addresses(addressListMapper.dtoToModel(shopDTO.getAddresses()))
                .shopStatus(shopDTO.getShopStatus())
                .build();
    };

    public Shop toModel(ShopCreateDTO shopCreateDTO){
        if (shopCreateDTO == null){
            return null;
        }
        return Shop.builder()
                .name(shopCreateDTO.getName())
                .creationTime(LocalDateTime.now())
                .addresses(addressListMapper.createDTOToModel(shopCreateDTO.getAddresses()))
                .shopStatus(ShopStatus.OPERATING)
                .build();
    };

    public ShopDTO toDTO(Shop shop){
        if (shop == null){
            return null;
        }
        return ShopDTO.builder()
                .id(shop.getId())
                .name(shop.getName())
                .creator(userMapper.toDTORestricted(shop.getCreator()))
                .creationTime(shop.getCreationTime())
                .addresses(addressListMapper.toDTO(shop.getAddresses()))
                .shopStatus(shop.getShopStatus())
                .build();
    };

}
