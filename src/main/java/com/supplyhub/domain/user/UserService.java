package com.supplyhub.domain.user;

import com.supplyhub.domain.manager.Manager;
import com.supplyhub.domain.user.dto.CreateDataUserDto;
import com.supplyhub.domain.user.dto.UserProfileDto;
import com.supplyhub.domain.user.dto.UserResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.GONE;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserProfileDto getUserByUsername(final String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(GONE,
                        "The user account has been deleted or inactivated"));
        return UserProfileDto.fromUser(user);
    }

    @Transactional
    public UserResponseDto registerUser(CreateDataUserDto request) {
        if (userRepository.existsByUsername(request.username()) ||
                userRepository.existsByEmail(request.email())) {

            throw new ValidationException(
                    "Username or Email already exists");
        }

        Manager manager = new Manager(request);
        manager.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(manager);

        return UserResponseDto.fromUser(manager);
    }
}
