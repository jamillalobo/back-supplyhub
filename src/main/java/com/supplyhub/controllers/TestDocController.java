package com.supplyhub.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestDocController {

    @GetMapping("/docs-test")
    public String docs() {
        return "Swagger está vivo!";
    }
}
