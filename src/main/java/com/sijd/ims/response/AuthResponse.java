package com.sijd.ims.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sijd.ims.entity.user.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponse {
    private int statusCode;
    private String error;
    private String message;
    private String data;
    private String token;
    private String refreshToken;
    private String expirationTime;
    private String name;
    private String email;
    private String role;
    private String password;
    private User user;
}
