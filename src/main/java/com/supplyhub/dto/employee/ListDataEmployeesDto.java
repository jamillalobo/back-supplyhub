package com.supplyhub.dto.employee;

import com.supplyhub.entities.Employee;

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
