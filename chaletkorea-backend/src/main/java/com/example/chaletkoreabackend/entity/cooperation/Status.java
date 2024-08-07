package com.example.chaletkoreabackend.entity.cooperation;

import lombok.Getter;

@Getter
public enum Status {
    WRITING("작성중"),
    REQUESTED("요청"),
    IN_PROGRESS("진행중"),
    COMPLETED("완료"),
    REJECTED("반려"),
    PENDING("보류");

    private final String description;

    Status(String description) {
        this.description = description;
    }

}