package com.example.chaletkoreabackend.repository;

import com.example.chaletkoreabackend.entity.employee.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Employee findByEmail(String email);
}

