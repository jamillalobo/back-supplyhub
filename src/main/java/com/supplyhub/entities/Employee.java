package com.supplyhub.entities;


import com.supplyhub.dto.employee.CreateDataEmployeeDto;
import com.supplyhub.dto.employee.UpdateDataEmployeeData;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.util.List;

@Entity
@DiscriminatorValue("EMPLOYEE")
public class Employee extends User {
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;


    public Employee(@Valid CreateDataEmployeeDto data) {
        this.setUsername(data.username());
        this.setEmail(data.email());
        this.setCpf(data.cpf());
    }

    public Employee() {
    }


    public void updateEmployee(UpdateDataEmployeeData data) {
        if (data.username() != null) {
            setUsername(data.username());
        }

        if (data.email() != null) {
            setEmail(data.email());
        }

        if (data.password() != null) {
            setPassword(data.password());
        }
    }
}

