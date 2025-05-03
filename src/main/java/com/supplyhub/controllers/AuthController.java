package com.supplyhub.controllers;

import com.supplyhub.dto.auth.AuthenticationRequestDto;
import com.supplyhub.dto.auth.AuthenticationResponseDto;
import com.supplyhub.dto.user.CreateDataUserDto;
import com.supplyhub.dto.user.UserResponseDto;
import com.supplyhub.services.Auth.AuthenticationService;
import com.supplyhub.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody final AuthenticationRequestDto authenticationRequestDto
    ) {
        return ResponseEntity.ok(
                authenticationService.authenticate(authenticationRequestDto));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(
            @Valid @RequestBody final CreateDataUserDto registrationDTO) {

        final var registeredUser = userService
                .registerUser(registrationDTO);

        return ResponseEntity.ok(registeredUser);
    }
}
