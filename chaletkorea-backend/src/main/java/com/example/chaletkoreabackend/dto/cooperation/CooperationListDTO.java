package com.example.chaletkoreabackend.dto.cooperation;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CooperationListDTO {
    private Long cooperationId;
    private String title;
    private String name;
    private String position;
    private String attachment;
    private Long attachCnt;
    private String assignee;
    private Long assigneeCnt;
    private String cc;
    private Long ccCnt;
    private String assigneeDept;
    private Long assigneeDeptCnt;
    private String ccDept;
    private Long ccDeptCnt;
    private String status;
    private LocalDateTime desiredCompletionDate;
    private LocalDateTime holdDate;
    private LocalDateTime createdAt;
    private Long readCount;
}
