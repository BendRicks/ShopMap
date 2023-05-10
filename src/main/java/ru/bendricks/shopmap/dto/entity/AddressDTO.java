package ru.bendricks.shopmap.dto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "Must not be empty", groups = {CreateInfo.class})
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45", groups = {UpdateInfo.class, CreateInfo.class})
    private String city;

    @NotBlank(message = "Must not be empty", groups = {CreateInfo.class})
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45", groups = {UpdateInfo.class, CreateInfo.class})
    private String district;

    @NotBlank(message = "Must not be empty", groups = {CreateInfo.class})
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45", groups = {UpdateInfo.class, CreateInfo.class})
    private String street;

    @NotBlank(message = "Must not be empty", groups = {CreateInfo.class})
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45", groups = {UpdateInfo.class, CreateInfo.class})
    private String building;

    @NotBlank(message = "Must not be empty", groups = {CreateInfo.class})
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45", groups = {UpdateInfo.class, CreateInfo.class})
    private String room;

}
