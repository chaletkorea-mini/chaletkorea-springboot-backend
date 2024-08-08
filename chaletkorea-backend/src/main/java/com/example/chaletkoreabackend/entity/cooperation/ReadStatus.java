package com.example.chaletkoreabackend.entity.cooperation;

import com.example.chaletkoreabackend.entity.employee.Employee;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ReadStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperation_id")
    private Cooperation cooperation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "is_read")
    private boolean isRead; // 조회 여부

}
