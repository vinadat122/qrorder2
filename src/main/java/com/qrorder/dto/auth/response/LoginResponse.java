package com.qrorder.dto.auth.response;

import com.qrorder.dto.auth.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor

public class LoginResponse {

    private String accessToken;

    private UserResponse user;
}