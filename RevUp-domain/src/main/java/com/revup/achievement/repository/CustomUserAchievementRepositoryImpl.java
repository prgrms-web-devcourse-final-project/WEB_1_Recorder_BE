package com.revup.achievement.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.achievement.dto.UserAchievedInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.revup.achievement.entity.QUserAchievement.userAchievement;
import static com.revup.achievement.entity.QAchievement.achievement;

@Slf4j
@RequiredArgsConstructor
public class CustomUserAchievementRepositoryImpl implements CustomUserAchievementRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findAllIdsByUserId(Long userId) {
        return queryFactory.select(userAchievement.id)
                .from(userAchievement)
                .where(userAchievement.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<UserAchievedInfo> findAllInfoByUserId(Long userId) {
        return queryFactory.select(
                        Projections.constructor(UserAchievedInfo.class,
                                userAchievement.id,
                                achievement,
                                Expressions.numberTemplate(
                                        Integer.class, "ROW_NUMBER() OVER (PARTITION BY {0} ORDER BY {1})",
                                        achievement.type, achievement.standard), // ROW_NUMBER
                                userAchievement.isVisible
                        )
                )
                .from(achievement)
                .innerJoin(userAchievement)
                .on(userAchievement.achievement.eq(achievement))
                .where(userAchievement.user.id.eq(userId))
                .orderBy(achievement.standard.asc())
                .fetch();
    }

}
