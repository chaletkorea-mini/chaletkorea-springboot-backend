package com.example.chaletkoreabackend.entity;

import com.example.chaletkoreabackend.entity.cooperation.Cooperation;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cooperation_id")
    private Cooperation cooperation;

    private String filename;
    private String filePath;
    private LocalDateTime uploadDate;
    private Long fileSize;
    private String fileType;
}
