package com.example.chaletkoreabackend.entity.employee;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long divisionCode;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "division")
    private List<Department> departments;

}