package com.example.chaletkoreabackend.repository;

import com.example.chaletkoreabackend.entity.cooperation.Cooperation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CooperationRepository extends JpaRepository<Cooperation, Long>, CooperationCustomRepository  {
}
