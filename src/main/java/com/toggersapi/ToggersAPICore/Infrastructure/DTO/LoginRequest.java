package com.toggersapi.ToggersAPICore.Infrastructure.DTO;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Email can't be null")
        String email,
        @NotBlank(message = "Password can't be null")
        String password
) {
}
