package com.supplyhub.dto.employee;

import com.supplyhub.entities.Employee;

public record ListDataEmployees(
        Long id,
        String username,
        String email,
        String cpf
) {
    public ListDataEmployees(Employee employee) {
        this(employee.getId(), employee.getUsername(), employee.getEmail(), employee.getCpf());
    }
}
