package com.supplyhub.services;


import com.supplyhub.dto.employee.*;
import com.supplyhub.entities.Employee;
import com.supplyhub.repositories.EmployeeRepository;
import com.supplyhub.repositories.UserRepository;
import com.supplyhub.utils.EmailService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EmployeeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private EmailService emailService;

    public Employee createEmployee(CreateDataEmployeeDto data) {
        Employee employee = new Employee(data);
        employee.setPassword(passwordEncoder.encode(data.password()));
        return userRepository.save(employee);
//        emailService.sendMail(employee);
    }

    public DetailsDataEmployeeDto findById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        return DetailsDataEmployeeDto.fromEmployee(employee);
    }


    public List<ListDataEmployees> findAll() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(ListDataEmployees::new)
                .toList();
    }

    public EmployeeResponseDto update(Long id, UpdateDataEmployee data) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        employee.setUsername(data.username());
        employee.setPassword(data.password());

        Employee updated = employeeRepository.save(employee);
        return new EmployeeResponseDto(updated);
    }

    public void delete(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EntityNotFoundException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }
}
