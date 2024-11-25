package com.revup.question.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionType;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.revup.answer.entity.QAnswer.answer;
import static com.revup.question.entity.QQuestion.question;
import static com.revup.question.entity.QQuestionTag.questionTag;
import static com.revup.tag.entity.QTag.tag;
import static com.revup.user.entity.QUser.user;

@RequiredArgsConstructor
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Question> findQuestionsByType(QuestionType type, long offset, int size) {
        List<Long> questionIds = queryFactory.select(question.id)
                .from(question)
                .where(
                        question.type.eq(type)
                )
                .orderBy(question.id.desc())
                .offset(offset)
                .limit(size)
                .fetch();

        return queryFactory.selectFrom(question)
                .leftJoin(question.user, user).fetchJoin()
                .leftJoin(question.questionTags, questionTag).fetchJoin()
                .leftJoin(questionTag.tag, tag).fetchJoin()
                .where(
                        question.id.in(questionIds) // 페이징된 ID에 대해서만 조인
                )
                .orderBy(question.id.desc())
                .fetch();
    }

    @Override
    public long countQuestionsByType(QuestionType type) {
        Long count = queryFactory.select(question.count())
                .from(question)
                .where(type == null ? null : question.type.eq(type))
                .fetchOne();
        return count != null ? count : 0;
    }

    @Override
    public Optional<Question> findByIdWithTagsAndAnswers(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(question)
                .leftJoin(question.user, user).fetchJoin()
                .leftJoin(question.questionTags, questionTag).fetchJoin()
                .leftJoin(questionTag.tag, tag).fetchJoin()
                .leftJoin(question.answers, answer).fetchJoin()
                .where(question.id.eq(id))
                .fetchOne());
    }


}
