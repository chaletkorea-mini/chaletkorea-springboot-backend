package com.example.chaletkoreabackend.service;

import com.example.chaletkoreabackend.dto.login.LoginReqDto;
import com.example.chaletkoreabackend.dto.login.LoginResDto;
import com.example.chaletkoreabackend.entity.employee.Employee;
import com.example.chaletkoreabackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;


    // 로그인
    public LoginResDto login(LoginReqDto loginReqDto) {
        Employee employee = employeeRepository.findByEmail(loginReqDto.getEmail());
        if (employee != null && loginReqDto.getPassword().equals(employee.getPassword())) {
            return new LoginResDto("로그인 성공");
        } else {
            return new LoginResDto("로그인 실패: 잘못된 사용자명 또는 비밀번호");
        }

    }


    //패스워드 찾기

    


}