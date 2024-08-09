package com.example.chaletkoreabackend.service;

import com.example.chaletkoreabackend.dto.cooperation.CooperationListDTO;
import com.example.chaletkoreabackend.repository.CooperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CooperationService {

    private final CooperationRepository cooperationRepository;

    public CooperationService(CooperationRepository cooperationRepository) {
        this.cooperationRepository = cooperationRepository;
    }

    public Page<CooperationListDTO> getPagedCooperationList(Long userId, Pageable pageable) {
        return cooperationRepository.findAllWithDetails(userId, pageable);
    }
}
