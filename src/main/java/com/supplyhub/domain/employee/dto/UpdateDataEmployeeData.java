package com.supplyhub.domain.employee.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateDataEmployeeData(
        @NotNull
        Long id,
        String username,
        String email,
        String password
) {
}
