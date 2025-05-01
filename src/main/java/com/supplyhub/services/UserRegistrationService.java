package com.supplyhub.services;

import com.supplyhub.dto.RegistrationRequestDto;
import com.supplyhub.entities.Employee;
import com.supplyhub.entities.User;
import com.supplyhub.repositories.UserRepository;
import jakarta.validation.ValidationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(User request) {
        if (userRepository.existsByUsername(request.getUsername()) ||
                userRepository.existsByEmail(request.getEmail())) {

            throw new ValidationException(
                    "Username or Email already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCpf(request.getCpf());
        System.out.println("request cpf" + request.getCpf());

        return userRepository.save(user);
    }
}
