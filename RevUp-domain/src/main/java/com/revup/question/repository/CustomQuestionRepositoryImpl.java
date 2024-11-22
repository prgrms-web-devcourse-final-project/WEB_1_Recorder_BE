package com.revup.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionType;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.revup.question.entity.QQuestion.question;
import static com.revup.question.entity.QQuestionTag.questionTag;
import static com.revup.tag.entity.QTag.tag;
import static com.revup.user.entity.QUser.user;

@RequiredArgsConstructor
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Question> findQuestionsByType(QuestionType type, long offset, int size) {
        return queryFactory.selectFrom(question)
                .join(question.user, user).fetchJoin()
                .leftJoin(question.questionTags, questionTag).fetchJoin()
                .leftJoin(questionTag.tag, tag).fetchJoin()
                .where(type == null ? null : question.type.eq(type)) // type이 null이면 전체 조회
                .orderBy(question.createdAt.desc())
                .offset(offset)
                .limit(size)
                .fetch();
    }

    @Override
    public long countQuestionsByType(QuestionType type) {
        return queryFactory.selectFrom(question)
                .where(type == null ? null : question.type.eq(type))
                .fetch().size();
    }


}
