package ru.bendricks.shopmap.mapper.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.ShopDTO;
import ru.bendricks.shopmap.entity.Shop;
import java.util.List;

@Component
public class ShopListMapper {

    private final ShopMapper shopMapper;

    @Autowired
    public ShopListMapper(ShopMapper shopMapper) {
        this.shopMapper = shopMapper;
    }

    public List<Shop> toModel(List<ShopDTO> shopDTOList){
        if (shopDTOList == null){
            return null;
        }
        return shopDTOList.stream().map(shopMapper::toModel).toList();
    };
    public List<ShopDTO> toDTO(List<Shop> shopList){
        if (shopList == null){
            return null;
        }
        return shopList.stream().map(shopMapper::toDTO).toList();
    };

}
