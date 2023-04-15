package ru.bendricks.shopmap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.exception.NotFoundException;
import ru.bendricks.shopmap.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById(int id){
        var userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new NotFoundException("user"));
    }

}
