package com.example.chaletkoreabackend.entity.employee;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long departmentCode;


    @ManyToOne
    @JoinColumn(name = "division_code")
    private Division division;

    @Column(nullable = true)
    private String departmentName;

    @OneToMany(mappedBy = "department")
    private List<Employee> employees;

}