package com.revup.feedback.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.feedback.entity.Mentor;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.revup.feedback.entity.QMentor.mentor;
import static com.revup.user.entity.QUser.user;

@RequiredArgsConstructor
public class CustomMentorRepositoryImpl implements CustomMentorRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Mentor> findMentorsByPageAndSize(long offset, int size) {
        return queryFactory.selectFrom(mentor)
                .join(mentor.user, user).fetchJoin()
                .orderBy(mentor.createdAt.desc())
                .offset(offset)
                .limit(size)
                .fetch();
    }

}
