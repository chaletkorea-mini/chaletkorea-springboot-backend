package com.example.chaletkoreabackend.entity.comment;

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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "cooperation_id")
    private Cooperation cooperation;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
