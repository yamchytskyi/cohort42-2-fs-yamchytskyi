package de.ait.javalessons.utils;

import de.ait.javalessons.model.User;
import de.ait.javalessons.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserDataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if(userRepository.findByUsername("admin") == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("adminnpass"));
            admin.getRoles().add("ROLE_ADMIN");
            userRepository.save(admin);
        }

        if(userRepository.findByUsername("user") == null) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("adminnpass"));
            user.getRoles().add("ROLE_ADMIN");
            userRepository.save(user);
        }
    }
}
