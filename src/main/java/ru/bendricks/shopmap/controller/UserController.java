package ru.bendricks.shopmap.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.bendricks.shopmap.dto.AuthoritiesResponse;
import ru.bendricks.shopmap.dto.entity.UserDTO;
import ru.bendricks.shopmap.entity.AccountStatus;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.entity.UserRole;
import ru.bendricks.shopmap.exception.NotAuthorizedException;
import ru.bendricks.shopmap.mapper.user.UserMapper;
import ru.bendricks.shopmap.security.UserDetailsInfo;
import ru.bendricks.shopmap.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600, allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/current/authorities")
    @ResponseStatus(HttpStatus.OK)
    public AuthoritiesResponse getAuthorities() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getClass().equals(UserDetailsInfo.class))
            throw new NotAuthorizedException("Not authorized");
        User user = ((UserDetailsInfo) principal).user();
        return new AuthoritiesResponse(
                user.getId(),
                user.getRole()
        );
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(principal.toString());
        if (!principal.getClass().equals(UserDetailsInfo.class))
            throw new NotAuthorizedException("Not authorized");
        return userService.findById(((UserDetailsInfo) principal).getId());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO changeStatusById(@PathVariable Integer id, @PathVariable AccountStatus status) {
        return userService.changeAccountStatus(id, status);
    }

    @PutMapping("/{id}/role/{role}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO changeRoleById(@PathVariable Integer id, @PathVariable UserRole role) {
        return userService.changeAccountRole(id, role);
    }

}
