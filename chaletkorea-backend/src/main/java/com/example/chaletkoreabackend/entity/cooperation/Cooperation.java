package com.example.chaletkoreabackend.entity.cooperation;

import com.example.chaletkoreabackend.entity.Attachment;
import com.example.chaletkoreabackend.entity.HoldDeadline;
import com.example.chaletkoreabackend.entity.Notification;
import com.example.chaletkoreabackend.entity.employee.Employee;
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
public class Cooperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JoinColumn(name = "cooperation_id")
    private Long cooperationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private String title;
    private LocalDateTime desiredCompletionDate;

    @Enumerated(EnumType.STRING)
    private Status status;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "cooperation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadStatus> readStatuses = new ArrayList<>();

    @OneToMany(mappedBy = "cooperation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Assignee> assignees = new ArrayList<>();

    @OneToMany(mappedBy = "cooperation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cc> ccList = new ArrayList<>();

    @OneToMany(mappedBy = "cooperation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();

    @OneToMany(mappedBy = "cooperation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attachment> attachments = new ArrayList<>();

    @OneToMany(mappedBy = "cooperation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssigneeDept> assigneeDepts = new ArrayList<>();

    @OneToMany(mappedBy = "cooperation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CcDept> ccDepts = new ArrayList<>();

    @OneToMany(mappedBy = "cooperation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HoldDeadline> holdDeadlines = new ArrayList<>();
}