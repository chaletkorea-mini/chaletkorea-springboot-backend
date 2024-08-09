package com.example.chaletkoreabackend.repository.cooperation;

import com.example.chaletkoreabackend.dto.cooperation.CooperationListDTO;
import com.example.chaletkoreabackend.entity.cooperation.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CooperationCustomRepository {
    Page<CooperationListDTO> findAllWithDetails(Long userId, String searchTerm, Status statusFilter, Pageable pageable);
}
