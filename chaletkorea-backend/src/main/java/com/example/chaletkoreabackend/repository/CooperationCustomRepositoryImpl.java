package com.example.chaletkoreabackend.repository;

import com.example.chaletkoreabackend.dto.cooperation.CooperationListDTO;
import com.example.chaletkoreabackend.entity.QAttachment;
import com.example.chaletkoreabackend.entity.cooperation.*;
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
        QAttachment attachment = QAttachment.attachment;
        QAssigneeDept assigneeDept = QAssigneeDept.assigneeDept; // AssigneeDept 엔티티 추가
        QCcDept ccDept = QCcDept.ccDept; // CcDept 엔티티 추가


        // 서브쿼리 결과를 Expression으로 만듭니다.
        Expression<Long> readCountExpression = JPAExpressions
                .select(readStatus.count())
                .from(readStatus)
                .where(readStatus.cooperation.eq(cooperation)
                        .and(readStatus.firstRead.isNotNull()));

        // 가장 먼저 생성된 첨부파일의 이름을 가져오기 위한 서브쿼리
        Expression<String> firstAttachmentNameExpression = JPAExpressions
                .select(attachment.filename)
                .from(attachment)
                .where(attachment.cooperation.eq(cooperation))
                .orderBy(attachment.uploadDate.asc())
                .limit(1);

        // 가장 먼저 생성된 담당자의 이름을 가져오기 위한 서브쿼리
        Expression<String> firstAssigneeNameExpression = JPAExpressions
                .select(assignee.employee.name)
                .from(assignee)
                .where(assignee.cooperation.eq(cooperation))
                .orderBy(assignee.createdAt.asc()) // Assignee의 생성일자로 정렬
                .limit(1);

        // 가장 먼저 생성된 담당 부서의 이름을 가져오기 위한 서브쿼리
        Expression<String> firstAssigneeDeptNameExpression = JPAExpressions
                .select(assigneeDept.department.departmentName)
                .from(assigneeDept)
                .where(assigneeDept.cooperation.eq(cooperation))
                .orderBy(assigneeDept.createdAt.asc()) // AssigneeDept의 생성일자로 정렬
                .limit(1);

        // 가장 먼저 생성된 참조자의 이름을 가져오기 위한 서브쿼리
        Expression<String> firstCcNameExpression = JPAExpressions
                .select(cc.employee.name)
                .from(cc)
                .where(cc.cooperation.eq(cooperation))
                .orderBy(cc.createdAt.asc()) // Cc의 생성일자로 정렬
                .limit(1);

        // 가장 먼저 생성된 참조 부서의 이름을 가져오기 위한 서브쿼리
        Expression<String> firstCcDeptNameExpression = JPAExpressions
                .select(ccDept.department.departmentName)
                .from(ccDept)
                .where(ccDept.cooperation.eq(cooperation))
                .orderBy(ccDept.createdAt.asc()) // CcDept의 생성일자로 정렬
                .limit(1);

        // 쿼리 생성 및 페이징 적용
        List<CooperationListDTO> results = queryFactory
                .select(Projections.constructor(
                        CooperationListDTO.class,
                        cooperation.cooperationId,
                        cooperation.title,
                        employee.name,
                        employee.position,
                        firstAttachmentNameExpression,
                        // 동적으로 계산된 attachCnt
                        Expressions.asNumber(cooperation.attachments.size()).longValue(),
                        firstAssigneeNameExpression,
                        // 동적으로 계산된 assigneeCnt
                        Expressions.asNumber(cooperation.assignees.size()).longValue(),
                        firstCcNameExpression,
                        // 동적으로 계산된 ccCnt
                        Expressions.asNumber(cooperation.ccList.size()).longValue(),
                        firstAssigneeDeptNameExpression,
                        Expressions.asNumber(cooperation.assigneeDepts.size()).longValue(),
                        firstCcDeptNameExpression,
                        Expressions.asNumber(cooperation.ccDepts.size()).longValue(),
                        cooperation.status,
                        cooperation.desiredCompletionDate,
                        cooperation.createdAt,
                        Expressions.asNumber(readCountExpression)
                ))
                .from(cooperation)
                .leftJoin(cooperation.employee, employee)
                .leftJoin(cooperation.assignees, assignee)
                .leftJoin(cooperation.ccList, cc)
                .leftJoin(cooperation.assigneeDepts, assigneeDept)
                .leftJoin(cooperation.ccDepts, ccDept)
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
                .leftJoin(cooperation.assigneeDepts, assigneeDept)
                .leftJoin(cooperation.ccDepts, ccDept)
                .where(employee.employeeId.eq(userId)
                        .and(assignee.cooperation.cooperationId.eq(cooperation.cooperationId)) // Assignee의 cooperationId 조건
                        .and(cc.cooperation.cooperationId.eq(cooperation.cooperationId))     // CC의 cooperationId 조건
                )
                .fetchOne();

        return new PageImpl<>(results, pageable, total);
    }
}
