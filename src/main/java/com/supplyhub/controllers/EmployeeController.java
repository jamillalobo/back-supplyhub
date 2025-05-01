package com.supplyhub.controllers;

import com.supplyhub.dto.employee.*;
import com.supplyhub.entities.Employee;
import com.supplyhub.services.EmployeeService;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping
    @Transactional
    public ResponseEntity create(@RequestBody @Valid CreateDataEmployeeDto data, UriComponentsBuilder uriBuilder) throws MessagingException, UnsupportedEncodingException {
        Employee created = employeeService.createEmployee(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailsDataEmployeeDto> findById(@PathVariable Long id) {
        DetailsDataEmployeeDto employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<ListDataEmployees>> findAll() {
        List<ListDataEmployees> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateDataEmployee data) {
        EmployeeResponseDto updated = employeeService.update(id, data);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
