package com.example.chaletkoreabackend.repository.cooperation;

import com.example.chaletkoreabackend.dto.cooperation.CooperationListDTO;
import com.example.chaletkoreabackend.entity.QAttachment;
import com.example.chaletkoreabackend.entity.QHoldDeadline;
import com.example.chaletkoreabackend.entity.cooperation.*;
import com.example.chaletkoreabackend.entity.employee.QEmployee;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
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
    public Page<CooperationListDTO> findAllWithDetails(Long userId, String searchTerm, Status statusFilter, Pageable pageable) {
        QCooperation cooperation = QCooperation.cooperation;
        QAssignee assignee = QAssignee.assignee;
        QCc cc = QCc.cc;
        QReadStatus readStatus = QReadStatus.readStatus;
        QEmployee employee = QEmployee.employee;
        QAttachment attachment = QAttachment.attachment;
        QAssigneeDept assigneeDept = QAssigneeDept.assigneeDept;
        QCcDept ccDept = QCcDept.ccDept;
        QHoldDeadline holdDeadline = QHoldDeadline.holdDeadline1;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(
                assignee.employee.employeeId.eq(userId)
                        .or(cc.employee.employeeId.eq(userId))
        );

        if (searchTerm != null && !searchTerm.isEmpty()) {
            builder.and(cooperation.title.containsIgnoreCase(searchTerm));
        }

        if (statusFilter != null) {
            builder.and(cooperation.status.eq(statusFilter));
        }

        // 서브쿼리 결과를 Expression으로 만듭니다.
        Expression<Long> readCountExpression = JPAExpressions
                .select(readStatus.count())
                .from(readStatus)
                .where(readStatus.cooperation.eq(cooperation)
                        .and(readStatus.firstRead.isNotNull()));

        Expression<String> firstAttachmentNameExpression = JPAExpressions
                .select(attachment.filename)
                .from(attachment)
                .where(attachment.cooperation.eq(cooperation))
                .orderBy(attachment.uploadDate.asc())
                .limit(1);

        Expression<String> firstAssigneeNameAndPositionExpression = JPAExpressions
                .select(Expressions.stringTemplate("CONCAT({0}, ' ', {1})", assignee.employee.name, assignee.employee.position))
                .from(assignee)
                .where(assignee.cooperation.eq(cooperation))
                .orderBy(assignee.createdAt.asc())
                .limit(1);

        Expression<String> firstAssigneeDeptNameExpression = JPAExpressions
                .select(assigneeDept.department.departmentName)
                .from(assigneeDept)
                .where(assigneeDept.cooperation.eq(cooperation))
                .orderBy(assigneeDept.createdAt.asc())
                .limit(1);

        Expression<String> firstCcNameAndPositionExpression = JPAExpressions
                .select(Expressions.stringTemplate("CONCAT({0}, ' ', {1})", cc.employee.name, cc.employee.position))
                .from(cc)
                .where(cc.cooperation.eq(cooperation))
                .orderBy(cc.createdAt.asc())
                .limit(1);

        Expression<String> firstCcDeptNameExpression = JPAExpressions
                .select(ccDept.department.departmentName)
                .from(ccDept)
                .where(ccDept.cooperation.eq(cooperation))
                .orderBy(ccDept.createdAt.asc())
                .limit(1);

        // 읽음 여부 확인
        Expression<Boolean> isReadExpression = JPAExpressions
                .select(readStatus.firstRead.isNotNull())
                .from(readStatus)
                .where(readStatus.cooperation.eq(cooperation)
                        .and(readStatus.employee.employeeId.eq(userId)))
                .exists();

        List<CooperationListDTO> results = queryFactory
                .select(Projections.constructor(
                        CooperationListDTO.class,
                        cooperation.cooperationId,
                        cooperation.title,
                        employee.name,
                        employee.position,
                        firstAttachmentNameExpression,
                        Expressions.asNumber(cooperation.attachments.size()).longValue(),
                        firstAssigneeNameAndPositionExpression,
                        Expressions.asNumber(cooperation.assignees.size()).longValue(),
                        firstCcNameAndPositionExpression,
                        Expressions.asNumber(cooperation.ccList.size()).longValue(),
                        firstAssigneeDeptNameExpression,
                        Expressions.asNumber(cooperation.assigneeDepts.size()).longValue(),
                        firstCcDeptNameExpression,
                        Expressions.asNumber(cooperation.ccDepts.size()).longValue(),
                        cooperation.status,
                        cooperation.desiredCompletionDate,
                        holdDeadline.holdDeadline,
                        cooperation.createdAt,
                        Expressions.asNumber(readCountExpression),
                        isReadExpression // 추가된 읽음 여부
                ))
                .from(cooperation)
                .leftJoin(cooperation.employee, employee)
                .leftJoin(cooperation.assignees, assignee)
                .leftJoin(cooperation.ccList, cc)
                .leftJoin(cooperation.assigneeDepts, assigneeDept)
                .leftJoin(cooperation.ccDepts, ccDept)
                .leftJoin(cooperation.holdDeadlines, holdDeadline)
                .leftJoin(readStatus).on(readStatus.cooperation.eq(cooperation)
                        .and(readStatus.employee.employeeId.eq(userId)))
                .where(builder)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(cooperation.count())
                .from(cooperation)
                .leftJoin(cooperation.assignees, assignee)
                .leftJoin(cooperation.ccList, cc)
                //total 계산시 필요없는 join을 주석처리
//                .leftJoin(cooperation.assigneeDepts, assigneeDept)
//                .leftJoin(cooperation.ccDepts, ccDept)
//                .leftJoin(cooperation.holdDeadlines, holdDeadline)
//                .leftJoin(readStatus).on(readStatus.cooperation.eq(cooperation)
//                        .and(readStatus.employee.employeeId.eq(userId)))
                .where(builder)
                .fetchOne();

        total = (total != null) ? total : 0L;

        return new PageImpl<>(results, pageable, total);
    }
}
