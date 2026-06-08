package com.qrorder.config;

import com.qrorder.entity.User;
import com.qrorder.entity.enums.Role;

import com.qrorder.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class DataSeeder
        implements CommandLineRunner {

    private final UserRepository
            userRepository;

    private final PasswordEncoder
            passwordEncoder;

    @Override
    public void run(
            String... args
    ) {

        createUser(
                "admin",
                "admin123",
                Role.ADMIN
        );

        createUser(
                "waiter",
                "waiter123",
                Role.WAITER
        );

        createUser(
                "kitchen",
                "kitchen123",
                Role.KITCHEN
        );

        createUser(
                "cashier",
                "cashier123",
                Role.CASHIER
        );
    }

    private void createUser(

            String username,

            String password,

            Role role
    ) {

        if(userRepository
                .existsByUsernameIgnoreCase(
                        username
                )) {

            return;
        }

        User user =

                User.builder()

                        .username(
                                username
                        )

                        .password(
                                passwordEncoder.encode(
                                        password
                                )
                        )

                        .role(
                                role
                        )

                        .enabled(
                                true
                        )

                        .build();

        userRepository.save(
                user
        );

        System.out.println(
                "Created user: "
                        + username
        );
    }
}