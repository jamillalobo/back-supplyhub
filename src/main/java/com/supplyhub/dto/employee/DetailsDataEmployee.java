package com.supplyhub.dto.employee;

import com.supplyhub.entities.Employee;

public record DetailsDataEmployee(
        String username,
        String email,
        String password
) {
    public static DetailsDataEmployee fromEmployee(Employee employee) {
        return new DetailsDataEmployee(
                employee.getUsername(),
                employee.getEmail(),
                employee.getPassword()
        );
    }
}
