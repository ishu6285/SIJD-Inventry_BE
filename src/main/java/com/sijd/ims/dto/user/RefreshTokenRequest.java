package com.sijd.ims.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message = "token required")
    private String token;
}
