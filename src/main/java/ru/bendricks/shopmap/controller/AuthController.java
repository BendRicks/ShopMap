package ru.bendricks.shopmap.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.shopmap.dto.AuthenticationRequestDTO;
import ru.bendricks.shopmap.dto.AuthenticationResponse;
import ru.bendricks.shopmap.dto.MessageResponse;
import ru.bendricks.shopmap.dto.PasswordChangeDTO;
import ru.bendricks.shopmap.security.UserDetailsInfo;
import ru.bendricks.shopmap.security.service.AuthService;
import ru.bendricks.shopmap.security.util.JWTUtil;
import ru.bendricks.shopmap.util.constraints.GeneralConstants;

@RestController
@RequestMapping("/api/auth")
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
    public AuthenticationResponse performLogin(@RequestBody AuthenticationRequestDTO authenticationRequestDTO, HttpServletResponse response) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationRequestDTO.getUsername(), authenticationRequestDTO.getPassword());

        authenticationManager.authenticate(authInputToken);

        String token = jwtUtil.generateToken(
                (
                        (UserDetailsInfo) authService.loadUserByUsername(
                                authenticationRequestDTO.getUsername()
                        )
                ).user()
        );
        int cookieAgeInSeconds = 86400;

        Cookie cookie = new Cookie("jwt-token", GeneralConstants.BEARER_HEADER_TOKEN.concat(token));
        cookie.setMaxAge(cookieAgeInSeconds); // expire in 1 day
        response.addCookie(cookie);
        return new AuthenticationResponse(
                token,
                System.currentTimeMillis()
        );
    }

    @PostMapping("/password/change")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse changePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){
        UserDetailsInfo userDetailsInfo = (UserDetailsInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        authService.changePassword(userDetailsInfo.user(), passwordChangeDTO);
        return new MessageResponse(
                "Updated successful",
                System.currentTimeMillis()
        );
    }

}
