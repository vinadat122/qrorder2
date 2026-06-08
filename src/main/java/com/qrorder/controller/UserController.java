package com.qrorder.controller;

import com.qrorder.dto.user.CreateUserRequest;
import com.qrorder.dto.user.UpdateUserRequest;
import com.qrorder.dto.user.UserResponse;

import com.qrorder.service.UserService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService
            userService;

    @PostMapping
    public Map<String, String>
    createUser(

            @Valid
            @RequestBody
            CreateUserRequest request
    ) {

        userService.createUser(
                request
        );

        return Map.of(
                "message",
                "Create user success"
        );
    }

    @GetMapping
    public List<UserResponse>
    getUsers() {

        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(

            @PathVariable
            Long id
    ) {

        return userService
                .getUserById(id);
    }

    @PutMapping("/{id}")
    public Map<String, String>
    updateUser(

            @PathVariable
            Long id,

            @Valid
            @RequestBody
            UpdateUserRequest request
    ) {

        userService.updateUser(
                id,
                request
        );

        return Map.of(
                "message",
                "Update user success"
        );
    }

    @DeleteMapping("/{id}")
    public Map<String, String>
    deleteUser(

            @PathVariable
            Long id
    ) {

        userService.deleteUser(
                id
        );

        return Map.of(
                "message",
                "Delete user success"
        );
    }
}