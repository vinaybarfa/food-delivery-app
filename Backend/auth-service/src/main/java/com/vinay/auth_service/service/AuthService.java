package com.vinay.auth_service.service;

import com.vinay.auth_service.dto.request.LoginRequestDto;
import com.vinay.auth_service.dto.request.SignUpRequestDto;
import com.vinay.auth_service.dto.response.LoginResponseDto;
import com.vinay.auth_service.dto.response.SignUpResponseDto;
import com.vinay.auth_service.entity.RefreshToken;
import com.vinay.auth_service.entity.Role;
import com.vinay.auth_service.entity.User;
import com.vinay.auth_service.exception.AuthenticationFailedException;
import com.vinay.auth_service.exception.ResourceAlreadyExistsException;
import com.vinay.auth_service.jwt.JwtService;
import com.vinay.auth_service.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RefreshTokenService refreshTokenService;

    /*
     * LOGIN
     */
    public LoginResponseDto login(
            LoginRequestDto request
    ) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(

                            new UsernamePasswordAuthenticationToken(
                                    request.getEmail(),
                                    request.getPassword()
                            )
                    );

            User user =
                    userRepository
                            .findByEmail(request.getEmail())
                            .orElseThrow(() ->
                                    new AuthenticationFailedException(
                                            "Invalid credentials"
                                    )
                            );

            if (!user.isEnabled()) {

                throw new AuthenticationFailedException(
                        "Account is disabled"
                );
            }

            if (!user.isAccountNonLocked()) {

                throw new AuthenticationFailedException(
                        "Account is locked"
                );
            }

            /*
             * GENERATE ACCESS TOKEN
             */
            String accessToken =
                    jwtService.generateAccessToken(user);

            /*
             * GENERATE REFRESH TOKEN
             */
            RefreshToken refreshToken =
                    refreshTokenService
                            .createRefreshToken(
                                    user.getEmail()
                            );

            log.info(
                    "User logged in successfully: {}",
                    user.getEmail()
            );

            return LoginResponseDto.builder()

                    .id(user.getId())

                    .email(user.getEmail())

                    .accessToken(accessToken)

                    .refreshToken(
                            refreshToken.getRefreshToken()
                    )

                    .build();

        } catch (BadCredentialsException ex) {

            log.error(
                    "Authentication failed for user: {}",
                    request.getEmail()
            );

            throw new AuthenticationFailedException(
                    "Invalid email or password"
            );
        }
    }

    /*
     * SIGNUP
     */
    public SignUpResponseDto signup(
            SignUpRequestDto request
    ) {

        /*
         * CHECK EXISTING USER
         */
        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new ResourceAlreadyExistsException(
                    "User already exists with email: "
                            + request.getEmail()
            );
        }

        /*
         * CREATE USER
         */
        User newUser = User.builder()

                .fullName(request.getFullName())

                .email(request.getEmail())

                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )

                .role(
                        request.getRole() != null
                                ? request.getRole()
                                : Role.ROLE_CUSTOMER
                )

                .enabled(true)

                .accountNonLocked(true)

                .build();

        User savedUser =
                userRepository.save(newUser);

        log.info(
                "New user registered: {}",
                savedUser.getEmail()
        );

        /*
         * TODO:
         * Publish UserRegisteredEvent to Kafka
         */

        return SignUpResponseDto.builder()

                .id(savedUser.getId())

                .email(savedUser.getEmail())

                .build();
    }
}