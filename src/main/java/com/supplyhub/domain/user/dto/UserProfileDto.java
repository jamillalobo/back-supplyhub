package com.supplyhub.domain.user.dto;

import com.supplyhub.domain.user.User;

public record UserProfileDto(String email, String username, String type) {
    public static UserProfileDto fromUser(User user) {
        String userType = null;
        if (user instanceof com.supplyhub.domain.employee.Employee) {
            userType = "EMPLOYEE";
        } else if (user instanceof com.supplyhub.domain.manager.Manager) {
            userType = "MANAGER";
        } else if (user != null) {
            userType = user.getClass().getSimpleName().toUpperCase();
        }
        return new UserProfileDto(
                user.getEmail(),
                user.getUsername(),
                userType
        );
    }
}
