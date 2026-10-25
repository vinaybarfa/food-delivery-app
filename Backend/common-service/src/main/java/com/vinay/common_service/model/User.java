package com.vinay.common_service.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private CardDetails cardDetails;

}
