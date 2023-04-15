package ru.bendricks.shopmap.mapper.user;

import org.springframework.stereotype.Component;
import ru.bendricks.shopmap.dto.entity.UserDTO;
import ru.bendricks.shopmap.entity.User;

@Component
public class UserMapper {

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
                .shops(user.getShops())
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
                .shops(userDTO.getShops())
                .build();
    };

}
