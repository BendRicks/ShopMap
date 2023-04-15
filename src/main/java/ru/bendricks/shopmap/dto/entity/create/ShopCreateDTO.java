package ru.bendricks.shopmap.dto.entity.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bendricks.shopmap.dto.entity.UserDTO;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopCreateDTO {

    private UserDTO creator;
    private String name;
    private List<AddressCreateDTO> addresses;

}
