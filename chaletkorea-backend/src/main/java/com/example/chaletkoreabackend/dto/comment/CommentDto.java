package com.example.chaletkoreabackend.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private Long cooperationId;
    private Long employeeId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
