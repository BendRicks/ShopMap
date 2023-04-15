package ru.bendricks.shopmap.dto.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bendricks.shopmap.entity.Address;
import ru.bendricks.shopmap.entity.ShopStatus;
import ru.bendricks.shopmap.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopDTO {

    private Integer id;
    private UserDTO creator;
    private String name;
    private LocalDateTime creationTime;
    private ShopStatus shopStatus;
    private List<AddressDTO> addresses;


}
