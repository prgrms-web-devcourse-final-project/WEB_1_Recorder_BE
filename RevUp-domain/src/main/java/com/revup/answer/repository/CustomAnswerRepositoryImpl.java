package com.revup.answer.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.answer.dto.AnswerDto;
import com.revup.common.BooleanStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.revup.answer.entity.QAnswer.answer;

@RequiredArgsConstructor
public class CustomAnswerRepositoryImpl implements CustomAnswerRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Integer> findGoodCountsByUserId(Long userId) {
        return queryFactory.select(answer.goodCount)
                .from(answer)
                .where(answer.user.id.eq(userId))
                .where(answer.isDeleted.eq(BooleanStatus.FALSE))
                .fetch();
    }

    @Override
    public List<AnswerDto> findAnswerDtosByUserId(Long userId) {
        return queryFactory.select(
                Projections.constructor(AnswerDto.class,
                answer.id,
                answer.isAccept
                )).from(answer)
                .where(answer.user.id.eq(userId))
                .where(answer.isDeleted.eq(BooleanStatus.FALSE))
                .fetch();
    }

    @Override
    public List<LocalDate> findCreatedAtsByUserId(Long userId) {
        List<LocalDateTime> createdAts = queryFactory.select(answer.createdAt)
                .from(answer)
                .where(answer.user.id.eq(userId))
                .where(answer.isDeleted.eq(BooleanStatus.FALSE))
                .orderBy(answer.createdAt.asc())
                .fetch();

        return createdAts.stream().
                map(LocalDateTime::toLocalDate)
                .distinct()
                .toList();

    }
}
