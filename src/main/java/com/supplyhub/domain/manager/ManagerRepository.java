package com.supplyhub.domain.manager;

import com.supplyhub.domain.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
