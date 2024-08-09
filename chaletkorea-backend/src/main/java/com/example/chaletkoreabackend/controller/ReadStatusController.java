package com.example.chaletkoreabackend.controller;

import com.example.chaletkoreabackend.dto.cooperation.ReadStatusListDTO;
import com.example.chaletkoreabackend.service.ReadStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/read-status")
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @Autowired
    public ReadStatusController(ReadStatusService readStatusService) {
        this.readStatusService = readStatusService;
    }

    @GetMapping("/cooperation/{cooperationId}")
    public List<ReadStatusListDTO> getReadStatuses(
            @PathVariable Long cooperationId,
            @RequestParam(required = false) Boolean hasFirstRead) {
        return readStatusService.getReadStatuses(cooperationId, hasFirstRead);
    }
}