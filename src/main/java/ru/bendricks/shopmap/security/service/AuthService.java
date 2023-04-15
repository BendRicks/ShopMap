package ru.bendricks.shopmap.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.shopmap.dto.PasswordChangeDTO;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.entity.UserRole;
import ru.bendricks.shopmap.repository.UserRepository;
import ru.bendricks.shopmap.security.UserDetailsInfo;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()){
            throw new UsernameNotFoundException("No user with such email");
        }
        return new UserDetailsInfo(user.get());
    }

    @Transactional
    public void changePassword(User userToChange, PasswordChangeDTO passwordChangeDTO) throws UsernameNotFoundException {
        if (passwordEncoder.matches(passwordChangeDTO.getOldPassword(), userToChange.getPassword())) {
            userToChange.setPassword(
                    passwordEncoder.encode(
                            passwordChangeDTO.getNewPassword()
                    )
            );
            userRepository.save(userToChange);
        }
    }

    public boolean isEmailAvailable(String email){
        return userRepository.findByEmail(email).isEmpty();
    }

    public boolean isUsernameAvailable(String username){
        return userRepository.findByUsername(username).isEmpty();
    }

    @Transactional
    public User create(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(UserRole.ROLE_USER);
        user.setCreationTime(LocalDateTime.now());
        userRepository.save(user);
        return user;
    }

}
