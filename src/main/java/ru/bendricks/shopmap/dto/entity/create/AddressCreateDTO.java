package ru.bendricks.shopmap.dto.entity.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bendricks.shopmap.dto.entity.ShopDTO;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressCreateDTO {

    private ShopDTO shop;
    private String city;
    private String district;
    private String street;
    private String building;
    private String room;

}
