package com.revup.answer.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.answer.dto.AnswerDto;
import com.revup.answer.entity.Answer;
import com.revup.common.BooleanStatus;
import com.revup.question.entity.Question;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.revup.answer.entity.QAnswer.answer;
import static com.revup.user.entity.QUser.user;

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

    @Override
    public List<Answer> findByQuestion(Question question) {
        return queryFactory.select(answer)
                .from(answer)
                .leftJoin(answer.user, user).fetchJoin()
                .where(answer.question.eq(question))
                .orderBy(answer.createdAt.desc())
                .fetch();
    }

    @Override
    public List<Answer> findByUserAndLimit(User currentUser, Long lastId, int size) {
        BooleanBuilder builder = new BooleanBuilder();

        // 현재 사용자 조건 추가
        builder.and(answer.user.eq(currentUser));

        // lastId 조건 동적 생성
        if (lastId != null) {
            builder.and(answer.id.lt(lastId));
        }

        List<Long> answerIds = queryFactory.select(answer.id)
                .from(answer)
                .where(builder)
                .orderBy(answer.createdAt.desc())
                .limit(size)
                .fetch();

        return queryFactory.selectFrom(answer)
                .leftJoin(answer.user, user).fetchJoin()
                .where(
                        answer.id.in(answerIds)
                )
                .orderBy(answer.createdAt.desc())
                .fetch();
    }
}
