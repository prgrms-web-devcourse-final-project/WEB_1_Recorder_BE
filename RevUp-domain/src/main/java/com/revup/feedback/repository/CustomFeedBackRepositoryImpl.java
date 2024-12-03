package com.revup.feedback.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.revup.feedback.entity.QFeedback.feedback;

@RequiredArgsConstructor
public class CustomFeedBackRepositoryImpl implements CustomFeedBackRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findRequestedFeedBackIdsByUserId(Long userId) {
        return queryFactory.select(feedback.id)
                .from(feedback)
                .where(feedback.teacher.id.eq(userId))
                .fetch();
    }
}
