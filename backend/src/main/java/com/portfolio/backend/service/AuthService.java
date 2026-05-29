package com.portfolio.backend.service;

import com.portfolio.backend.dto.auth.LoginRequest;
import com.portfolio.backend.dto.auth.LoginResponse;
import com.portfolio.backend.security.JwtService;
import com.portfolio.backend.security.UserContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        // Email ve şifreyi doğrula
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Authentication başarılıysa UserContext'i al
        UserContext userContext = (UserContext) authentication.getPrincipal();

        // Token üret
        String token = jwtService.generateToken(userContext);

        return LoginResponse.builder()
                .token(token)
                .email(userContext.getUser().getEmail())
                .role(userContext.getUser().getRole().name())
                .build();
    }
}