package com.qrorder.controller;

import com.qrorder.dto.auth.LoginRequest;
import com.qrorder.dto.auth.LoginResponse;
import com.qrorder.dto.auth.RegisterRequest;
import com.qrorder.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        return authService.login(request);
    }
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {

        authService.register(request);

        return "Register success";
    }
}