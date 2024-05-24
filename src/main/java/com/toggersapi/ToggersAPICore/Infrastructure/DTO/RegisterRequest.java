package com.toggersapi.ToggersAPICore.Infrastructure.DTO;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @NotBlank(message = "Username can't be null")
        String username,
        @NotBlank(message = "Email can't be null")
        String email,
        @NotBlank(message = "Password can't be null")
        String password
) {
}
