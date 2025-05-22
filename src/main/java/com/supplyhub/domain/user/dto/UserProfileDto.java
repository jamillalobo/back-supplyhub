package com.supplyhub.domain.user.dto;

import com.supplyhub.domain.user.User;

public record UserProfileDto(String email, String username) {
    public static UserProfileDto fromUser(User user) {
        return new UserProfileDto(
                user.getEmail(),
                user.getUsername()
        );
    }
}
