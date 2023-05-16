package ru.bendricks.shopmap.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SignUpDTO {

    @NotBlank(message = "Username must not be empty")
    @Size(min = 4, max = 45, message = "Username length must be between 4 and 45")
    private String username;
    @NotBlank(message = "Email must not be empty")
    @Size(min = 5, max = 100, message = "Email length must be between 5 and 100")
    private String email;
    @NotBlank(message = "Password must not be empty")
    @Size(min = 8, message = "Password length must be minimum 8")
    private String password;
    @NotBlank(message = "Password repeat must not be empty")
    @Size(min = 8, message = "Password repeat length must be minimum 8")
    private String passwordRepeat;

}
