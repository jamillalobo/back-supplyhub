package com.supplyhub.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.supplyhub.domain.user.auth.AuthenticationService;
import com.supplyhub.domain.user.auth.dto.AuthenticationRequestDto;
import com.supplyhub.domain.user.auth.dto.AuthenticationResponseDto;
import com.supplyhub.infra.security.JwtService;

public class AuthenticationServiceTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_ShouldReturnJwtToken_WhenCredentialsAreValid() {
        // Arrange
        String email = "lucas@gmail.com";
        String password = "12345678";
        String username = "lucas";
        String token = "jwt_token";

        User user = new User();
        user.setEmail(email);
        user.setUsername(username);

        AuthenticationRequestDto requestDto = new AuthenticationRequestDto(email, password);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(username, password);

        when(userRepository.findByEmail(email)).thenReturn(user);
        when(jwtService.generateToken(username)).thenReturn(token);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));

        // Act
        AuthenticationResponseDto response = authenticationService.authenticate(requestDto);

        // Assert
        assertNotNull(response);
        assertEquals(token, response.token());
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService).generateToken(username);
    }

    @Test
    void authenticate_ShouldThrow_WhenUserNotFound() {
        // Arrange
        AuthenticationRequestDto requestDto = new AuthenticationRequestDto("notfound@gmail.com", "12345678");
        when(userRepository.findByEmail(requestDto.email())).thenReturn(null);

        // Act & Assert
        assertThrows(NullPointerException.class, () -> authenticationService.authenticate(requestDto));
        verify(userRepository).findByEmail(requestDto.email());
        verifyNoMoreInteractions(authenticationManager, jwtService);
    }
}
