package com.supplyhub.domain.manager.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateDataManagerData(
    @NotNull
    Long id,
    String username,
    String email,
    String password
) {}