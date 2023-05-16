package ru.bendricks.shopmap.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.shopmap.dto.AuthenticationRequestDTO;
import ru.bendricks.shopmap.dto.AuthenticationResponse;
import ru.bendricks.shopmap.dto.AuthoritiesResponse;
import ru.bendricks.shopmap.dto.SignUpDTO;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.exception.NotCreatedException;
import ru.bendricks.shopmap.security.UserDetailsInfo;
import ru.bendricks.shopmap.security.service.AuthService;
import ru.bendricks.shopmap.security.util.JWTUtil;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthService authService, JWTUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse performLogin(@RequestBody AuthenticationRequestDTO authenticationRequestDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword());

        authenticationManager.authenticate(authInputToken);

        User user = ((UserDetailsInfo) authService.loadUserByUsername(authenticationRequestDTO.getUsername())).user();
        String token = jwtUtil.generateToken(user);
        log.info(authenticationRequestDTO.getUsername() + " logged in");
        return new AuthenticationResponse(
                        token,
                        System.currentTimeMillis(),
                        new AuthoritiesResponse(
                                user.getId(),
                                user.getRole()
                        )
                );
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticationResponse performRegistration(@Valid @RequestBody SignUpDTO signUpDTO, BindingResult bindingResult) {
        checkForAddErrors(bindingResult);
        User user = authService.createUser(signUpDTO);
        String token = jwtUtil.generateToken(user);
        return new AuthenticationResponse(
                token,
                System.currentTimeMillis(),
                new AuthoritiesResponse(
                        user.getId(),
                        user.getRole()
                )
        );
    }

    private void checkForAddErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            errorMessage.append("User not created").append(" - ");
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append("; ");
            }

            throw new NotCreatedException(errorMessage.toString());
        }
    }

}
