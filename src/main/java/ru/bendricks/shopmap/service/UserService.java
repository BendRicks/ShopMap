package ru.bendricks.shopmap.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.bendricks.shopmap.dto.entity.UserDTO;
import ru.bendricks.shopmap.entity.AccountStatus;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.entity.UserRole;
import ru.bendricks.shopmap.exception.NotFoundException;
import ru.bendricks.shopmap.mapper.user.UserListMapper;
import ru.bendricks.shopmap.mapper.user.UserMapper;
import ru.bendricks.shopmap.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserListMapper userListMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, UserListMapper userListMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userListMapper = userListMapper;
    }

    public List<UserDTO> getUsers() {
        return userListMapper.toDTORestricted(userRepository.findAll());
    }

    public UserDTO findById(int id){
        return userMapper.toDTO(findUserById(id));
    }

    public User findUserById(int id) {
        var userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new NotFoundException("user"));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @Transactional
    public UserDTO changeAccountStatus(Integer userId, AccountStatus status) {
        User user = findUserById(userId);
        user.setAccountStatus(status);
        return userMapper.toDTO(userRepository.save(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    @Transactional
    public UserDTO changeAccountRole(Integer userId, UserRole role) {
        User user = findUserById(userId);
        user.setRole(role);
        return userMapper.toDTO(userRepository.save(user));
    }

}
