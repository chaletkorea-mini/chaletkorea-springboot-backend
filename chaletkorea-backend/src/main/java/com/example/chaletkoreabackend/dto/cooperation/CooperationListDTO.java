package com.example.chaletkoreabackend.dto.cooperation;

import com.example.chaletkoreabackend.entity.cooperation.Status;
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

    //클라이언트에서 enum 값을 잘 변환하여 사용해야함.
    private Status status;

    private LocalDateTime desiredCompletionDate;
    private LocalDateTime holdDate;
    private LocalDateTime createdAt;
    private Long readCount;
    private Boolean isRead;
}
