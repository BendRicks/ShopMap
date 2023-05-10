package ru.bendricks.shopmap.mapper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.ShopDTO;
import ru.bendricks.shopmap.dto.entity.UserDTO;
import ru.bendricks.shopmap.entity.Shop;
import ru.bendricks.shopmap.entity.User;

import java.util.List;

@Component
public class UserListMapper {

    private final UserMapper userMapper;

    @Autowired
    public UserListMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public List<UserDTO> toDTORestricted(List<User> shopList){
        if (shopList == null){
            return null;
        }
        return shopList.stream().map(userMapper::toDTORestricted).toList();
    };

}
