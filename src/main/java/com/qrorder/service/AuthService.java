package com.qrorder.service;

import com.qrorder.dto.auth.request.LoginRequest;
import com.qrorder.dto.auth.request.RegisterRequest;
import com.qrorder.dto.auth.response.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    LoginResponse register( RegisterRequest request );

}