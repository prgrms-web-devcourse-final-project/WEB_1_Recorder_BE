package com.revup.achievement.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.achievement.entity.Achievement;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.revup.achievement.entity.QAchievement.achievement;
@RequiredArgsConstructor
public class CustomAchievementRepositoryImpl implements CustomAchievementRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findAllIds() {
        return queryFactory.select(achievement.id)
                .from(achievement)
                .fetch();
    }

    @Override
    public List<Achievement> findAllAchievements() {
        return queryFactory.selectFrom(achievement)
                .orderBy(achievement.type.asc())
                .orderBy(achievement.standard.asc())
                .fetch();
    }
}
