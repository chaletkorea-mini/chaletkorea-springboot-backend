package com.example.chaletkoreabackend.entity;

import com.example.chaletkoreabackend.entity.cooperation.Cooperation;
import com.example.chaletkoreabackend.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class HoldDeadline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="hold_deadline_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperation_id")
    private Cooperation cooperation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime holdDeadline;

}

