package com.supplyhub.dto.employee;

import com.supplyhub.entities.Employee;

public class EmployeeResponseDTO {
    private Long id;
    private String username;

    public EmployeeResponseDTO(Employee employee) {
        this.id = employee.getId();
        this.username = employee.getUsername();
    }
}
