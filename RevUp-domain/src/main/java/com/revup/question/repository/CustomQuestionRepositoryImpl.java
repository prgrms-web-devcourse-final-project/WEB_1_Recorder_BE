package com.revup.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionType;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.revup.question.entity.QQuestion.question;
import static com.revup.user.entity.QUser.user;

@RequiredArgsConstructor
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Question> findQuestionList(Long page, Long size, QuestionType type) {
        return queryFactory.select(question)
                .leftJoin(question.user, user).fetchJoin()
                .where(question.type.eq(type))
                .orderBy(question.createdAt.desc())
                .offset(page * size)
                .limit(size)
                .fetch();
    }

}
