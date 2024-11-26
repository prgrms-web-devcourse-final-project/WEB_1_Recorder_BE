package com.revup.question.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.revup.common.SkillStack;
import com.revup.question.criteria.QuestionSearchCriteria;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionState;
import com.revup.question.entity.QuestionType;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.revup.answer.entity.QAnswer.answer;
import static com.revup.question.entity.QQuestion.question;
import static com.revup.user.entity.QUser.user;

@RequiredArgsConstructor
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public List<Question> findQuestionsByCriteria(QuestionSearchCriteria criteria, long offset, int size) {
        BooleanBuilder builder = buildSearchConditions(criteria);

        List<Long> questionIds = queryFactory.select(question.id)
                .from(question)
                .where(builder)
                .orderBy(question.id.desc())
                .offset(offset)
                .limit(size)
                .fetch();

        return queryFactory.selectFrom(question)
                .leftJoin(question.user, user).fetchJoin()
                .innerJoin(question.stacks).fetchJoin()
                .where(
                        question.id.in(questionIds) // 페이징된 ID에 대해서만 조인
                )
                .orderBy(question.id.desc())
                .fetch();
    }

    @Override
    public long countQuestionsByCriteria(QuestionSearchCriteria criteria) {
        BooleanBuilder builder = buildSearchConditions(criteria);

        Long count = queryFactory.select(question.count())
                .from(question)
                .where(builder)
                .fetchOne();

        return count != null ? count : 0;
    }

    @Override
    public Optional<Question> findByIdWithTagsAndAnswers(Long id) {
        return Optional.ofNullable(queryFactory.selectFrom(question)
                .leftJoin(question.user, user).fetchJoin()
                .innerJoin(question.stacks).fetchJoin()
                .leftJoin(question.answers, answer).fetchJoin()
                .where(question.id.eq(id))
                .fetchOne());
    }

    private BooleanBuilder buildSearchConditions(QuestionSearchCriteria criteria) {
        BooleanBuilder builder = new BooleanBuilder();

        // 기본 조건: 임시저장 제외
        builder.and(question.state.ne(QuestionState.TEMPORARY));

        // 질문 유형
        if (criteria.type() != null) {
            builder.and(question.type.eq(QuestionType.of(criteria.type())));
        }

        // 질문 상태
        if (criteria.state() != null) {
            builder.and(question.state.eq(QuestionState.of(criteria.state())));
        }

        // 기술 스택
        if (criteria.stack() != null) {
            builder.and(question.stacks.any().eq(SkillStack.of(criteria.stack())));
        }

        // 키워드 검색 (제목 또는 내용에 포함)
        if (criteria.keyword() != null) {
            builder.and(
                    question.title.containsIgnoreCase(criteria.keyword())
                            .or(question.content.containsIgnoreCase(criteria.keyword()))
            );
        }

        return builder;
    }


}
