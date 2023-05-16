package ru.bendricks.shopmap.dto.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bendricks.shopmap.entity.AccountStatus;
import ru.bendricks.shopmap.entity.UserRole;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "Must not be empty")
    private Integer id;

    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    private String username;

    @Size(min = 7, max = 100, message = "Length must be between 2 and 45")
    private String email;

    private LocalDateTime creationTime;
    private UserRole role;
    private AccountStatus status;
    private List<ShopDTO> shops;

}
