package com.vinay.auth_service.dto.request;

import com.vinay.auth_service.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    private String email;
    private String password;
    private String fullName;
    private Role role;
}