package ru.bendricks.shopmap.dto.entity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;
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

    @Null(groups = {CreateInfo.class})
    @NotNull(groups = {UpdateInfo.class})
    private Integer id;

    private UserDTO creator;

    @NotBlank(message = "Must not be empty", groups = {CreateInfo.class})
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45", groups = {UpdateInfo.class, CreateInfo.class})
    private String name;

    @NotNull(groups = {CreateInfo.class})
    @Size(min = 10, max = 1001, message = "Length must be between 2 and 1000", groups = {UpdateInfo.class, CreateInfo.class})
    private String description;

    @NotNull(groups = {CreateInfo.class})
    @Size(min = 2, max = 1001, message = "Length must be between 2 and 1000", groups = {UpdateInfo.class, CreateInfo.class})
    private String mainImageURL;

    private LocalDateTime creationTime;
    private ShopStatus shopStatus;

    @Valid
    @NotNull(groups = {CreateInfo.class})
    private List<AddressDTO> addresses;

    @Valid
    private List<AdditionalImageDTO> additionalImages;

}
