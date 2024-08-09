package com.example.chaletkoreabackend.service;

import com.example.chaletkoreabackend.dto.cooperation.ReadStatusListDTO;
import com.example.chaletkoreabackend.repository.readStatus.ReadStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReadStatusService {

    private final ReadStatusRepository readStatusRepository;

    @Autowired
    public ReadStatusService(ReadStatusRepository readStatusRepository) {
        this.readStatusRepository = readStatusRepository;
    }

    public List<ReadStatusListDTO> getReadStatuses(Long cooperationId, Boolean hasFirstRead) {
        return readStatusRepository.findByCooperationId(cooperationId, hasFirstRead);
    }
}