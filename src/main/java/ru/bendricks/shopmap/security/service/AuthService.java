package ru.bendricks.shopmap.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bendricks.shopmap.dto.SignUpDTO;
import ru.bendricks.shopmap.entity.AccountStatus;
import ru.bendricks.shopmap.entity.User;
import ru.bendricks.shopmap.entity.UserRole;
import ru.bendricks.shopmap.exception.NotCreatedException;
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
    public User createUser(SignUpDTO signUpDTO){
        if (!signUpDTO.getPassword().equals(signUpDTO.getPasswordRepeat())){
            throw new NotCreatedException("Password repeat not matches with password");
        }
        boolean isAdminSignUp = signUpDTO.getUsername().equals("admin");
        User user = User.builder()
                .username(signUpDTO.getUsername())
                .email(signUpDTO.getEmail())
                .role(isAdminSignUp? UserRole.ROLE_ADMIN: UserRole.ROLE_USER)
                .accountStatus(isAdminSignUp? AccountStatus.VERIFIED: AccountStatus.NOT_VERIFIED)
                .password(passwordEncoder.encode(signUpDTO.getPassword()))
                .creationTime(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return user;
    }

}
