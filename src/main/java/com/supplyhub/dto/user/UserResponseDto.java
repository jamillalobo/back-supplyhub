package com.supplyhub.dto.user;

import com.supplyhub.entities.User;

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
