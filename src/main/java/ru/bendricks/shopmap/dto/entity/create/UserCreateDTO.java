package ru.bendricks.shopmap.dto.entity.create;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class UserCreateDTO {

    @NotBlank(message = "Must not be empty")
    @Size(min = 2, max = 45, message = "Length must be between 2 and 45")
    public String username;

    @NotBlank(message = "Must not be empty")
    @Size(min = 8, max = 64, message = "Length must be between 8 and 64")
    public String password;

}