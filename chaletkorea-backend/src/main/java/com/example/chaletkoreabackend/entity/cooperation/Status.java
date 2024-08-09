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

    // description으로부터 Status를 찾는 메서드
    public static Status fromDescription(String description) {
        for (Status status : values()) {
            if (status.getDescription().equalsIgnoreCase(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + description + "]");
    }
}