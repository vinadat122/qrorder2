package com.qrorder.service.impl;

import com.qrorder.dto.user.CreateUserRequest;
import com.qrorder.dto.user.UpdateUserRequest;
import com.qrorder.dto.user.UserResponse;

import com.qrorder.entity.User;

import com.qrorder.repository.UserRepository;

import com.qrorder.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository
            userRepository;

    private final PasswordEncoder
            passwordEncoder;

    @Override
    public void createUser(
            CreateUserRequest request
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
                                request.getUsername().trim()
                        )

                        .password(
                                passwordEncoder.encode(
                                        request.getPassword()
                                )
                        )

                        .role(
                                request.getRole()
                        )

                        .enabled(
                                true
                        )

                        .build();

        userRepository.save(
                user
        );
    }

    @Override
    public void updateUser(

            Long id,

            UpdateUserRequest request
    ) {

        User user =

                userRepository
                        .findById(id)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "User not found"
                                )
                        );

        user.setRole(
                request.getRole()
        );

        user.setEnabled(
                request.getEnabled()
        );

        userRepository.save(
                user
        );
    }

    @Override
    public void deleteUser(
            Long id
    ) {

        User user =

                userRepository
                        .findById(id)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "User not found"
                                )
                        );

        user.setEnabled(
                false
        );

        userRepository.save(
                user
        );
    }

    @Override
    public UserResponse getUserById(
            Long id
    ) {

        User user =

                userRepository
                        .findById(id)
                        .orElseThrow(() ->

                                new RuntimeException(
                                        "User not found"
                                )
                        );

        return mapToResponse(
                user
        );
    }

    @Override
    public List<UserResponse>
    getUsers() {

        return userRepository
                .findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private UserResponse mapToResponse(
            User user
    ) {

        return UserResponse.builder()

                .id(
                        user.getId()
                )

                .username(
                        user.getUsername()
                )

                .role(
                        user.getRole()
                )

                .enabled(
                        user.getEnabled()
                )

                .build();
    }
}
