package ru.bendricks.shopmap.dto.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalImageDTO {

    private Integer id;

    private ShopDTO shop;
    
    @NotBlank(message = "Must not be empty", groups = {CreateInfo.class})
    @Size(min = 2, max = 255, message = "Length must be between 2 and 255", groups = {UpdateInfo.class, CreateInfo.class})
    private String imageUrl;

    @NotBlank(message = "Must not be empty", groups = {CreateInfo.class})
    @Size(min = 2, max = 1001, message = "Length must be between 2 and 1000", groups = {UpdateInfo.class, CreateInfo.class})
    private String imageDescription;

}
