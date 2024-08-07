package com.example.chaletkoreabackend.entity.cooperation;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cooperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cooperation_id")
    private Long id;

    private String title;
    private LocalDateTime desiredCompletionDate;

    @Column(name = "requesting_department")
    private String requesting_department;

    @Enumerated(EnumType.STRING)
    private Status status;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



}