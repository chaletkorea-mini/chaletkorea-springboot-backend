package com.example.chaletkoreabackend.repository.readStatus;

import com.example.chaletkoreabackend.dto.cooperation.ReadStatusListDTO;

import java.util.List;

public interface ReadStatusCustomRepository {
    List<ReadStatusListDTO> findByCooperationId(Long cooperationId, Boolean hasFirstRead);
}
