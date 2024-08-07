package com.example.chaletkoreabackend.controller;

import com.example.chaletkoreabackend.dto.login.LoginReqDto;
import com.example.chaletkoreabackend.dto.login.LoginResDto;
import com.example.chaletkoreabackend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto loginReqDto) {
        LoginResDto loginResDto = employeeService.login(loginReqDto);
        return ResponseEntity.ok(loginResDto);
    }


    //패스워드 찾기


}
