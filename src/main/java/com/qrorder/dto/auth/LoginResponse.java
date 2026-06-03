package com.qrorder.dto.auth;

import com.qrorder.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String accessToken;
    private User user;
}