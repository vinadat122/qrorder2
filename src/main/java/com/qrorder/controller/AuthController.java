package com.qrorder.controller;

import com.qrorder.dto.auth.request.LoginRequest;
import com.qrorder.dto.auth.response.LoginResponse;
import com.qrorder.dto.auth.request.RegisterRequest;
import com.qrorder.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(

            @Valid
            @RequestBody
            LoginRequest request
    ) {

        return authService.login(
                request
        );
    }

    @PostMapping("/register")
    public LoginResponse register(

            @Valid
            @RequestBody
            RegisterRequest request
    ) {

        return authService.register(
                request
        );
    }
}