package com.revup.feedback.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.feedback.entity.Mentor;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.revup.feedback.entity.QMentor.mentor;
import static com.revup.feedback.entity.QMentorSkillStack.mentorSkillStack;
import static com.revup.user.entity.QUser.user;

@RequiredArgsConstructor
public class CustomMentorRepositoryImpl implements CustomMentorRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Mentor> findAllWithMentorSkillStacksAndUser() {
        return queryFactory.selectFrom(mentor)
                .join(mentor.mentorSkillStacks, mentorSkillStack).fetchJoin()
                .join(mentor.user, user).fetchJoin()
                .fetch()
                ;
    }

}
