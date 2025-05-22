package com.supplyhub.domain.user.dto;

import com.supplyhub.domain.user.User;

public record UserResponseDto(
        String username,
        String email
) {
    public static UserResponseDto fromUser(User user) {
        return new UserResponseDto(
                user.getUsername(),
                user.getEmail()
        );
    }
}
