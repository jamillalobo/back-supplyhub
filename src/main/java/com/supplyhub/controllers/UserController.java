package com.supplyhub.controllers;


import com.supplyhub.entities.User;
import com.supplyhub.services.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserService userService;
//    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<?> getUserProfile(@Parameter(hidden = true) Authentication authentication) {
        var user = userService.getUserByUsername(authentication.getName());
        return ResponseEntity.ok(user);
    }

}
