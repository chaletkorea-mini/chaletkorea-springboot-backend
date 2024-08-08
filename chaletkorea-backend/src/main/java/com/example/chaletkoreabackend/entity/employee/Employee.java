package com.example.chaletkoreabackend.entity.employee;

import com.example.chaletkoreabackend.entity.Notification;
import com.example.chaletkoreabackend.entity.cooperation.Assignee;
import com.example.chaletkoreabackend.entity.cooperation.Cc;
import com.example.chaletkoreabackend.entity.cooperation.ReadStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "employee_id")
    private Long employeeId;

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

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadStatus> readStatuses = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignee> assignees = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cc> ccList = new ArrayList<>();

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();
}
