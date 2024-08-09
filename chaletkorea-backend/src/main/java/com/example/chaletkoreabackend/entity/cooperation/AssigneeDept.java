package com.example.chaletkoreabackend.entity.cooperation;

import com.example.chaletkoreabackend.entity.employee.Department;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AssigneeDept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperation_id")
    private Cooperation cooperation;

    @ManyToOne
    @JoinColumn(name = "department_code")
    private Department department;

    private LocalDateTime createdAt;
}
