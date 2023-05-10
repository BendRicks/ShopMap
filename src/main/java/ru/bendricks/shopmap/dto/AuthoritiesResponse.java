package ru.bendricks.shopmap.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bendricks.shopmap.entity.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthoritiesResponse {

    private Integer userId;
    private UserRole userRole;

}
