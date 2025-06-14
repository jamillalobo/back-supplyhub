package com.supplyhub.domain.employee;

import com.supplyhub.domain.employee.dto.*;
import com.supplyhub.domain.employee.dto.UpdateDataEmployeeData;
import com.supplyhub.domain.user.dto.CreateDataUserDto;
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
    public ResponseEntity create(@RequestBody @Valid CreateDataUserDto data, UriComponentsBuilder uriBuilder) throws MessagingException, UnsupportedEncodingException {
        Employee created = employeeService.createEmployee(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailsDataEmployeeDto> findById(@PathVariable Long id) {
        DetailsDataEmployeeDto employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<ListDataEmployeesDto>> findAll() {
        List<ListDataEmployeesDto> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDto> update(@PathVariable Long id, @RequestBody @Valid UpdateDataEmployeeData data) {
        EmployeeResponseDto updated = employeeService.update(id, data);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
