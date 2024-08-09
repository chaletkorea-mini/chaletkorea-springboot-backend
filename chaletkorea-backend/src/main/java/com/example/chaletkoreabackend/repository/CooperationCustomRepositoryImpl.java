package com.example.chaletkoreabackend.repository;

import com.example.chaletkoreabackend.dto.cooperation.CooperationListDTO;
import com.example.chaletkoreabackend.entity.cooperation.QAssignee;
import com.example.chaletkoreabackend.entity.cooperation.QCc;
import com.example.chaletkoreabackend.entity.cooperation.QCooperation;
import com.example.chaletkoreabackend.entity.cooperation.QReadStatus;
import com.example.chaletkoreabackend.entity.employee.QEmployee;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CooperationCustomRepositoryImpl implements CooperationCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CooperationCustomRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    @Override
    public Page<CooperationListDTO> findAllWithDetails(Long userId, Pageable pageable) {
        QCooperation cooperation = QCooperation.cooperation;
        QAssignee assignee = QAssignee.assignee;
        QCc cc = QCc.cc;
        QReadStatus readStatus = QReadStatus.readStatus;
        QEmployee employee = QEmployee.employee;

        // 서브쿼리 결과를 Expression으로 만듭니다.
        Expression<Long> readCountExpression = JPAExpressions
                .select(readStatus.count())
                .from(readStatus)
                .where(readStatus.cooperation.eq(cooperation)
                        .and(readStatus.firstRead.isNotNull()));

        // 쿼리 생성 및 페이징 적용
        List<CooperationListDTO> results = queryFactory
                .select(Projections.constructor(
                        CooperationListDTO.class,
                        cooperation.cooperationId,
                        cooperation.title,
                        employee.name,
                        employee.position,
                        cooperation.attachment,
                        // 동적으로 계산된 attachCnt
                        Expressions.asNumber(cooperation.attachments.size()).longValue(),
                        assignee.employee.name,
                        // 동적으로 계산된 assigneeCnt
                        Expressions.asNumber(cooperation.assignees.size()).longValue(),
                        cc.employee.name,
                        // 동적으로 계산된 ccCnt
                        Expressions.asNumber(cooperation.ccList.size()).longValue(),
                        cooperation.assigneeDept,
                        cooperation.assigneeDeptCnt,
                        cooperation.ccDept,
                        cooperation.ccDeptCnt,
                        cooperation.status,
                        cooperation.desiredCompletionDate,
                        cooperation.createdAt,
                        Expressions.asNumber(readCountExpression)
                ))
                .from(cooperation)
                .leftJoin(cooperation.employee, employee)
                .leftJoin(cooperation.assignees, assignee)
                .leftJoin(cooperation.ccList, cc)
                .where(employee.employeeId.eq(userId)
                        .and(assignee.cooperation.cooperationId.eq(cooperation.cooperationId)) // Assignee의 cooperationId 조건
                        .and(cc.cooperation.cooperationId.eq(cooperation.cooperationId))     // CC의 cooperationId 조건
                )
                .offset(pageable.getOffset())  // 페이지 오프셋 설정
                .limit(pageable.getPageSize()) // 페이지 크기 설정
                .fetch();

        // 총 레코드 수 계산
        long total = queryFactory
                .select(cooperation.count())
                .from(cooperation)
                .leftJoin(cooperation.assignees, assignee)
                .leftJoin(cooperation.ccList, cc)
                .where(employee.employeeId.eq(userId)
                        .and(assignee.cooperation.cooperationId.eq(cooperation.cooperationId)) // Assignee의 cooperationId 조건
                        .and(cc.cooperation.cooperationId.eq(cooperation.cooperationId))     // CC의 cooperationId 조건
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}
