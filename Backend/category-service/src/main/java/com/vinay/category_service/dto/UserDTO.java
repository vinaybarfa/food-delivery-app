package com.vinay.category_service.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    private String fullName;
    private String email;
    private String password;
    private boolean isAvailable = true;
}
