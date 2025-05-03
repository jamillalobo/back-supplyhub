package com.supplyhub.dto.employee;

import jakarta.validation.constraints.NotNull;

public record UpdateDataEmployeeData(
        @NotNull
        Long id,
        String username,
        String email,
        String password
) {
}
