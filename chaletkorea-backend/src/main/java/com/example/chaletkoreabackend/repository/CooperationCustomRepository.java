package com.example.chaletkoreabackend.repository;

import com.example.chaletkoreabackend.dto.cooperation.CooperationListDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CooperationCustomRepository {
    Page<CooperationListDTO> findAllWithDetails(Long userId, Pageable pageable);
}
