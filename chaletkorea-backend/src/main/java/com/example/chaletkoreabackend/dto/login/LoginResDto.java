package com.example.chaletkoreabackend.dto.login;


import lombok.Data;

@Data
public class LoginResDto {
    private String message;

    public LoginResDto(String message) {
        this.message = message;
    }
}
