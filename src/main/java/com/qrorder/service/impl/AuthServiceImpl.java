
package com.qrorder.service.impl;

import com.qrorder.dto.auth.request.LoginRequest;
import com.qrorder.dto.auth.request.RegisterRequest;
import com.qrorder.dto.auth.response.LoginResponse;
import com.qrorder.dto.auth.response.UserResponse;
import com.qrorder.entity.User;
import com.qrorder.entity.enums.Role;
import com.qrorder.repository.UserRepository;
import com.qrorder.service.AuthService;
import com.qrorder.util.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository
            userRepository;

    private final PasswordEncoder
            passwordEncoder;

    private final JwtUtil
            jwtUtil;

    @Override
    public LoginResponse login(
            LoginRequest request
    ) {

        User user =

                userRepository
                        .findByUsername(
                                request.getUsername()
                        )
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "User not found"
                                )
                        );

        if(!user.getEnabled()) {

            throw new RuntimeException(
                    "User is disabled"
            );
        }

        boolean checkPassword =

                passwordEncoder.matches(

                        request.getPassword(),

                        user.getPassword()
                );

        if(!checkPassword) {

            throw new RuntimeException(
                    "Wrong password"
            );
        }

        String token =

                jwtUtil.generateToken(

                        user.getUsername(),

                        user.getRole()
                                .name()
                );

        return LoginResponse.builder()

                .accessToken(
                        token
                )

                .user(

                        UserResponse.builder()

                                .id(
                                        user.getId()
                                )

                                .username(
                                        user.getUsername()
                                )

                                .role(
                                        user.getRole()
                                )

                                .build()
                )

                .build();
    }

    @Override
    public LoginResponse register(
            RegisterRequest request
    ) {

        if(userRepository
                .existsByUsernameIgnoreCase(
                        request.getUsername()
                )) {

            throw new RuntimeException(
                    "Username already exists"
            );
        }

        User user =

                User.builder()

                        .username(
                                request.getUsername()
                        )

                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()
                                )
                        )

                        .role(
                                Role.USER
                        )

                        .enabled(
                                true
                        )

                        .build();

        userRepository.save(
                user
        );

        String token =

                jwtUtil.generateToken(

                        user.getUsername(),

                        user.getRole()
                                .name()
                );

        return LoginResponse.builder()

                .accessToken(
                        token
                )

                .user(

                        UserResponse.builder()

                                .id(
                                        user.getId()
                                )

                                .username(
                                        user.getUsername()
                                )

                                .role(
                                        user.getRole()
                                )

                                .build()
                )

                .build();
    }
}
