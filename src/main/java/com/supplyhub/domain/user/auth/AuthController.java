package com.supplyhub.domain.user.auth;

import com.supplyhub.domain.user.auth.dto.AuthenticationRequestDto;
import com.supplyhub.domain.user.auth.dto.AuthenticationResponseDto;
import com.supplyhub.domain.user.dto.CreateDataUserDto;
import com.supplyhub.domain.user.dto.UserResponseDto;
import com.supplyhub.domain.user.UserService;
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
