package com.qrorder.dto.auth.response;

import com.qrorder.entity.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;

    private String username;

    private Role role;
}