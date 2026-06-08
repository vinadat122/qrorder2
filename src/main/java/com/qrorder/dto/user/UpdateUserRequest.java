package com.qrorder.dto.user;

import com.qrorder.entity.enums.Role;

import jakarta.validation.constraints.NotNull;

import lombok.Data;

@Data
public class UpdateUserRequest {

    @NotNull
    private Role role;

    @NotNull
    private Boolean enabled;
}