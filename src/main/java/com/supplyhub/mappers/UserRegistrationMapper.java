package com.supplyhub.mappers;


import com.supplyhub.dto.RegistrationRequestDto;
import com.supplyhub.dto.RegistrationResponseDto;
import com.supplyhub.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationMapper {

    public User toEntity(RegistrationRequestDto registrationRequestDto) {
        final var user = new User();

        user.setEmail(registrationRequestDto.email());
        user.setUsername(registrationRequestDto.username());
        user.setPassword(registrationRequestDto.password());
        user.setCpf(registrationRequestDto.cpf());

        return user;
    }

    public RegistrationResponseDto toRegistrationResponseDto(
            final User user) {

        return new RegistrationResponseDto(
                user.getEmail(), user.getUsername());
    }

}
