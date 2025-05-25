package com.supplyhub.domain.user.auth;

import com.supplyhub.domain.user.auth.dto.AuthenticationRequestDto;
import com.supplyhub.domain.user.auth.dto.AuthenticationResponseDto;
import com.supplyhub.domain.user.User;
import com.supplyhub.domain.user.UserRepository;
import com.supplyhub.infra.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public AuthenticationResponseDto authenticate(
            final AuthenticationRequestDto request) {

        User user = userRepository.findByEmail(request.email());

        final var authToken = UsernamePasswordAuthenticationToken
                .unauthenticated(user.getUsername(), request.password());

        final var authentication = authenticationManager
                .authenticate(authToken);

        final var token = jwtService.generateToken(user.getUsername());
        return new AuthenticationResponseDto(token);
    }
}
