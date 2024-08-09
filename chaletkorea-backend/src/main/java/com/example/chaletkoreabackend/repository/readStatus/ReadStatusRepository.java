package com.example.chaletkoreabackend.repository.readStatus;

import com.example.chaletkoreabackend.entity.cooperation.ReadStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadStatusRepository extends JpaRepository<ReadStatus, Long>, ReadStatusCustomRepository {

}