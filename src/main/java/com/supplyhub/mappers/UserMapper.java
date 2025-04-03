package com.supplyhub.mappers;

import com.supplyhub.dto.UserProfileDto;
import com.supplyhub.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserProfileDto toUserProfileDto(final User user) {
        return new UserProfileDto(user.getEmail(), user.getUsername());
    }
}
