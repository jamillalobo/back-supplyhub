package com.supplyhub.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.supplyhub.domain.manager.Manager;
import com.supplyhub.domain.user.dto.CreateDataUserDto;
import com.supplyhub.domain.user.dto.UserProfileDto;
import com.supplyhub.domain.user.dto.UserResponseDto;

import jakarta.validation.ValidationException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserByUsername_ShouldReturnUserProfile() {
        // Arrange
        String username = "lucas";
        Manager mockManager = new Manager();
        mockManager.setUsername(username);
        mockManager.setPassword("encoded_pass");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockManager));

        // Act
        UserProfileDto result = userService.getUserByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals(username, result.username());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void getUserByUsername_ShouldThrow_WhenUserNotFound() {
        // Arrange
        String username = "notfound";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> userService.getUserByUsername(username));
    }

    @Test
    void registerUser_ShouldCreateUser() {
        // Arrange
        CreateDataUserDto dto = new CreateDataUserDto(
                "lucas",
                "lucas@gmail.com",
                "12345678",
                "70322463467"
        );

        when(userRepository.existsByUsername(dto.username())).thenReturn(false);
        when(userRepository.existsByEmail(dto.email())).thenReturn(false);
        when(passwordEncoder.encode(dto.password())).thenReturn("encoded_pass");
        when(userRepository.save(any(Manager.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserResponseDto result = userService.registerUser(dto);

        // Assert
        assertNotNull(result);
        assertEquals(dto.username(), result.username());
        verify(userRepository).save(any(Manager.class));
    }

    @Test
    void registerUser_ShouldThrow_WhenUsernameOrEmailExists() {
        // Arrange
        CreateDataUserDto dto = new CreateDataUserDto(
                "lucas",
                "lucas@gmail.com",
                "12345678",
                "70322463467"
        );

        when(userRepository.existsByUsername(dto.username())).thenReturn(true);

        // Act & Assert
        assertThrows(ValidationException.class, () -> userService.registerUser(dto));
    }
}
