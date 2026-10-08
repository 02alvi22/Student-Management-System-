package com.Alvi.StudentManagemant.config;

import com.Alvi.StudentManagemant.model.Users;
import com.Alvi.StudentManagemant.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner loadSamoleData(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {

        return args -> {
            Users users = usersRepository.findByUsername("Admin").orElse(new Users());

            users.setUsername("Admin");
            users.setPassword(passwordEncoder.encode("admin123")); // CHANGED: no special characters
            users.setActive(true);

            Users saved = usersRepository.save(users);


            System.out.println(">>> DataInitializer ran. Matches admin123? "
                    + passwordEncoder.matches("admin123", saved.getPassword()));
        };
    }
}