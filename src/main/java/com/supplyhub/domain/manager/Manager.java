package com.supplyhub.domain.manager;

import com.supplyhub.domain.employee.Employee;

import com.supplyhub.domain.user.User;
import com.supplyhub.domain.user.dto.CreateDataUserDto;
import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.util.List;

@Entity
@DiscriminatorValue("MANAGER")
public class Manager extends User {

    public Manager(@Valid CreateDataUserDto data) {
        this.setUsername(data.username());
        this.setEmail(data.email());
        this.setCpf(data.cpf());
    }

    public Manager() {}
}

