package com.example.chaletkoreabackend.service;

import com.example.chaletkoreabackend.dto.cooperation.CooperationListDTO;
import com.example.chaletkoreabackend.entity.cooperation.Status;
import com.example.chaletkoreabackend.repository.cooperation.CooperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CooperationService {

    private final CooperationRepository cooperationRepository;

    public CooperationService(CooperationRepository cooperationRepository) {
        this.cooperationRepository = cooperationRepository;
    }

    public Page<CooperationListDTO> getPagedCooperationList(Long userId, String searchTerm, String status, Pageable pageable) {
        Status statusFilter = null;
        if (status != null && !status.isEmpty()) {
            try {
                statusFilter = Status.fromDescription(status);
            } catch (IllegalArgumentException e) {
                // handle invalid description
            }
        }

        return cooperationRepository.findAllWithDetails(userId, searchTerm, statusFilter, pageable);
    }
}
