package com.qrorder.service.impl;

import com.qrorder.dto.auth.LoginRequest;
import com.qrorder.dto.auth.LoginResponse;
import com.qrorder.entity.User;
import com.qrorder.repository.UserRepository;
import com.qrorder.service.AuthService;
import com.qrorder.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.qrorder.dto.auth.RegisterRequest;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername());

        if(user == null) {
            throw new RuntimeException("User not found");
        }

        boolean checkPassword = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if(!checkPassword) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return LoginResponse.builder()
                .token(token)
                .build();
    }
    @Override
    public void register(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
    }
}