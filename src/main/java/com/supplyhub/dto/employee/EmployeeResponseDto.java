package com.supplyhub.dto.employee;

import com.supplyhub.entities.Employee;

public class EmployeeResponseDto {
    private Long id;
    private String username;

    public EmployeeResponseDto(Employee employee) {
        this.id = employee.getId();
        this.username = employee.getUsername();
    }
}
