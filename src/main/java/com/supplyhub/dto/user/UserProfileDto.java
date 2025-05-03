package com.supplyhub.dto.user;

import com.supplyhub.entities.User;

public record UserProfileDto(String email, String username) {
    public static UserProfileDto fromUser(User user) {
        return new UserProfileDto(
                user.getEmail(),
                user.getUsername()
        );
    }
}
