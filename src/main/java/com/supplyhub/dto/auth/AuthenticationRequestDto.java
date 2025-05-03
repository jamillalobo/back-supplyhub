package com.supplyhub.dto.auth;

public record AuthenticationRequestDto(
        String email,
        String password
) {
}