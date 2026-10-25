package com.vinay.auth_service.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private Long id;

    private String email;
    
    private String accessToken;

    private String refreshToken;

    private String tokenType = "Bearer";
}
