package com.qrorder.service;

import com.qrorder.dto.user.CreateUserRequest;
import com.qrorder.dto.user.UpdateUserRequest;
import com.qrorder.dto.user.UserResponse;

import java.util.List;

public interface UserService {

    void createUser(
            CreateUserRequest request
    );

    void updateUser(
            Long id,
            UpdateUserRequest request
    );

    void deleteUser(
            Long id
    );

    UserResponse getUserById(
            Long id
    );

    List<UserResponse> getUsers();
}
