package com.supplyhub.dto.employee;

import jakarta.validation.constraints.NotNull;

public record UpdateDataEmployee(
        @NotNull
        Long id,
        String username,
        String email,
        String password
) {
}
