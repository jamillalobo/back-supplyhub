package com.supplyhub.domain.employee.dto;

import com.supplyhub.domain.employee.Employee;

import lombok.Data;

@Data
public class EmployeeResponseDto {
    private Long id;
    private String username;

    public EmployeeResponseDto(Employee employee) {
        this.id = employee.getId();
        this.username = employee.getUsername();
    }
}
