package com.supplyhub.domain.employee.dto;

import com.supplyhub.domain.employee.Employee;

public record DetailsDataEmployeeDto(
        String username,
        String email,
        String password
) {
    public static DetailsDataEmployeeDto fromEmployee(Employee employee) {
        return new DetailsDataEmployeeDto(
                employee.getUsername(),
                employee.getEmail(),
                employee.getPassword()
        );
    }
}
