package ru.bendricks.shopmap.dto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bendricks.shopmap.entity.Shop;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {

    private Integer id;
    private ShopDTO shop;
    private String city;
    private String district;
    private String street;
    private String building;
    private String room;

}
