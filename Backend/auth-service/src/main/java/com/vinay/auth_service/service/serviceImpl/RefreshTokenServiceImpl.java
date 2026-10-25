package com.vinay.auth_service.service.serviceImpl;

import com.vinay.auth_service.entity.RefreshToken;
import com.vinay.auth_service.entity.User;
import com.vinay.auth_service.repository.RefreshTokenRepository;
import com.vinay.auth_service.repository.UserRepository;
import com.vinay.auth_service.service.RefreshTokenService;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    // 5 hours in milliseconds
    public long refreshTokenValidity =
            5 * 60 * 60 * 1000;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    @Override
    public RefreshToken createRefreshToken(
            String username
    ) {

        User user = userRepository
                .findByEmail(username)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "User not found with username : "
                                        + username
                        )
                );

        RefreshToken token = user.getRefreshToken();

        // If token already exists then update expiry
        if (token != null) {

            token.setExpiryDate(
                    Instant.now()
                            .plusMillis(refreshTokenValidity)
            );

        } else {

            token = RefreshToken.builder()
                    .refreshToken(
                            UUID.randomUUID().toString()
                    )
                    .expiryDate(
                            Instant.now()
                                    .plusMillis(refreshTokenValidity)
                    )
                    .user(user)
                    .build();
        }

        user.setRefreshToken(token);

        refreshTokenRepository.save(token);

        return token;
    }

    @Override
    public RefreshToken verifyRefreshToken(
            String refreshToken
    ) {

        RefreshToken refreshTokenObj =
                refreshTokenRepository
                        .findByRefreshToken(refreshToken)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Given Token does not exist in database"
                                )
                        );

        // Check token expiry
        if (
                refreshTokenObj
                        .getExpiryDate()
                        .compareTo(Instant.now()) < 0
        ) {

            refreshTokenRepository.delete(refreshTokenObj);

            throw new RuntimeException(
                    "Refresh Token is Expired!!"
            );
        }

        return refreshTokenObj;
    }
}