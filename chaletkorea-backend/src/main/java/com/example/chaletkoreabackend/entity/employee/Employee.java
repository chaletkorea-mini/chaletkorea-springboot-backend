package com.example.chaletkoreabackend.entity.employee;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="employee_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "department_code")
    private Department department;

    @Column(name="employee_name")
    private String name;

    private String email;
    private String position;
    private String phone;
    private String mobile;
    private String role;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
