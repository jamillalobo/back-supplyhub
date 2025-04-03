package com.supplyhub.dto;

public record AuthenticationRequestDto(
        String username,
        String password
) {
}