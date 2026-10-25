package com.vinay.auth_service.controller;

import com.vinay.auth_service.dto.request.LoginRequestDto;
import com.vinay.auth_service.dto.request.RefreshTokenRequest;
import com.vinay.auth_service.dto.request.SignUpRequestDto;
import com.vinay.auth_service.dto.response.LoginResponseDto;
import com.vinay.auth_service.dto.response.SignUpResponseDto;
import com.vinay.auth_service.entity.RefreshToken;
import com.vinay.auth_service.entity.User;
import com.vinay.auth_service.jwt.JwtService;
import com.vinay.auth_service.service.AuthService;
import com.vinay.auth_service.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    // private final CartRepository cartRepository;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final AuthService authService;

    // LOGIN API
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) {

        LoginResponseDto response =
                authService.login(loginRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    // SIGNUP API
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(
            @RequestBody SignUpRequestDto signUpRequestDto
    ) {

        SignUpResponseDto response =
                authService.signup(signUpRequestDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // REFRESH TOKEN API
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshJwtToken(
            @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {

        // Verify refresh token
        RefreshToken refreshToken =
                refreshTokenService.verifyRefreshToken(
                        refreshTokenRequest.getRefreshToken()
                );

        // Get user from refresh token
        User user = refreshToken.getUser();

        // Generate new access token
        String accessToken =
                jwtService.generateAccessToken(user);

        // Build response
        LoginResponseDto response =
                LoginResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getRefreshToken())
                        .tokenType("Bearer")
                        .build();

        return ResponseEntity.ok(response);
    }

    // VALIDATE JWT TOKEN API
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(
            @RequestHeader("Authorization") String token
    ) {

        // Remove "Bearer " from token
        String jwt = token.substring(7);

        boolean isValid =
                jwtService.isTokenValid(jwt);

        return ResponseEntity.ok(isValid);
    }
}