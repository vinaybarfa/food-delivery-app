package com.vinay.auth_service.service;

import com.vinay.auth_service.entity.RefreshToken;
import org.springframework.stereotype.Service;

@Service
public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);
    RefreshToken verifyRefreshToken(String refreshToken);



}
