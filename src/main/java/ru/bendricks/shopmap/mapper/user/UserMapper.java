package ru.bendricks.shopmap.mapper.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.UserDTO;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.mapper.shop.ShopListMapper;

@Component
public class UserMapper {

    private final ShopListMapper listMapper;

    @Autowired
    public UserMapper(ShopListMapper listMapper) {
        this.listMapper = listMapper;
    }

    public UserDTO toDTO(User user){
        if (user == null){
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .creationTime(user.getCreationTime())
                .role(user.getRole())
                .status(user.getAccountStatus())
                .shops(listMapper.toDTORestricted(user.getShops()))
                .build();
    }

    public UserDTO toDTORestricted(User user){
        if (user == null){
            return null;
        }
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .creationTime(user.getCreationTime())
                .role(user.getRole())
                .status(user.getAccountStatus())
                .build();
    }

    public User toModel(UserDTO userDTO){
        if (userDTO == null){
            return null;
        }
        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .creationTime(userDTO.getCreationTime())
                .role(userDTO.getRole())
                .shops(listMapper.toModel(userDTO.getShops()))
                .build();
    };

}
