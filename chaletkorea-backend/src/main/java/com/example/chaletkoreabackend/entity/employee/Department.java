package com.example.chaletkoreabackend.entity.employee;

import com.example.chaletkoreabackend.entity.cooperation.AssigneeDept;
import com.example.chaletkoreabackend.entity.cooperation.CcDept;
import jakarta.persistence.*;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Employee> employees = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssigneeDept> assigneeDepts = new ArrayList<>();

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CcDept> ccDepts = new ArrayList<>();
}