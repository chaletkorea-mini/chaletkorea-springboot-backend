package com.example.chaletkoreabackend.repository.readStatus;

import com.example.chaletkoreabackend.dto.cooperation.ReadStatusListDTO;
import com.example.chaletkoreabackend.entity.cooperation.QReadStatus;
import com.example.chaletkoreabackend.entity.employee.QEmployee;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReadStatusCustomRepositoryImpl implements ReadStatusCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public ReadStatusCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ReadStatusListDTO> findByCooperationId(Long cooperationId, Boolean hasFirstRead) {
        QReadStatus readStatus = QReadStatus.readStatus;
        QEmployee employee = QEmployee.employee;

        // 기본 쿼리
        var query = queryFactory
                .select(Projections.constructor(
                        ReadStatusListDTO.class,
                        employee.name,
                        employee.position,
                        readStatus.firstRead,
                        readStatus.finalRead
                ))
                .from(readStatus)
                .join(readStatus.employee, employee)
                .where(readStatus.cooperation.cooperationId.eq(cooperationId));

        // 조건에 따른 필터 추가
        if (hasFirstRead != null) {
            if (hasFirstRead) {
                query.where(readStatus.firstRead.isNotNull());
            } else {
                query.where(readStatus.firstRead.isNull());
            }
        }

        return query.fetch();
    }
}