package com.supplyhub.domain.employee.dto;

import com.supplyhub.domain.employee.Employee;

public record ListDataEmployeesDto(
        Long id,
        String username,
        String email,
        String cpf
) {
    public ListDataEmployeesDto(Employee employee) {
        this(employee.getId(), employee.getUsername(), employee.getEmail(), employee.getCpf());
    }
}
