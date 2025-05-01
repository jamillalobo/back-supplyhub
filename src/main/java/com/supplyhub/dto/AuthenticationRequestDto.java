package com.supplyhub.dto;

public record AuthenticationRequestDto(
        String email,
        String password
) {
}