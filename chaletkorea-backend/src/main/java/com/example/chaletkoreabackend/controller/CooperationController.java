package com.example.chaletkoreabackend.controller;

import com.example.chaletkoreabackend.dto.cooperation.CooperationListDTO;
import com.example.chaletkoreabackend.service.CooperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cooperation")
public class CooperationController {

    private final CooperationService cooperationService;

    public CooperationController(CooperationService cooperationService) {
        this.cooperationService = cooperationService;
    }

    @GetMapping
    public Page<CooperationListDTO> getPagedCooperations(@RequestParam Long userId, Pageable pageable) {
        return cooperationService.getPagedCooperationList(userId, pageable);
    }
}
