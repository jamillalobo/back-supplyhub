package com.supplyhub.domain.user.auth.dto;

public record AuthenticationRequestDto(
        String email,
        String password
) {
}