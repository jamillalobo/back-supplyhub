package com.supplyhub.domain.employee;

import com.supplyhub.domain.employee.dto.UpdateDataEmployeeData;
import com.supplyhub.domain.product.Product;
import com.supplyhub.domain.user.User;
import com.supplyhub.domain.user.dto.CreateDataUserDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.util.List;

@Entity
@DiscriminatorValue("EMPLOYEE")
public class Employee extends User {
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;

    public Employee(@Valid CreateDataUserDto data) {
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

