package com.revup.question.usecase;

import com.revup.page.Page;
import com.revup.question.dto.request.QuestionPageRequest;
import com.revup.question.dto.response.QuestionBriefResponse;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionType;
import com.revup.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.revup.question.constatnt.PageConstant.GROUP_SIZE;
import static com.revup.question.constatnt.PageConstant.SIZE;

@Component
@RequiredArgsConstructor
public class QuestionQuery {
    private final QuestionService questionService;

    public Page<QuestionBriefResponse> getQuestionList(QuestionPageRequest request) {
        long totalItems = questionService.getTotalQuestionCount(QuestionType.of(request.type()));

        int currentGroup = request.page() / GROUP_SIZE;
        int startPage = currentGroup * GROUP_SIZE + 1;

        // 현재 그룹의 최대 페이지 번호와 전체 페이지 수 중 작은 값을 반환
        int endPage = Math.min(
                startPage + GROUP_SIZE - 1,
                (int) Math.ceil((double) totalItems / SIZE)
        );

        // 시작 페이지가 1보다 크면 prev 존재
        boolean prev = startPage > 1;

        // 전체 게시물 수보다 endPage*SIZE 이면, 다음 존재
        boolean next = (long) endPage * SIZE < totalItems;

        List<Question> questions = questionService.getQuestionsByPage(
                QuestionType.of(request.type()),
                (long)request.page()*SIZE,
                SIZE
        );

        List<QuestionBriefResponse> content = questions.stream()
                .map(QuestionBriefResponse::of)
                .toList();

        return new Page<>(
                content,
                request.page(),
                content.size(),
                startPage,
                endPage,
                prev,
                next
        );
    }
}
