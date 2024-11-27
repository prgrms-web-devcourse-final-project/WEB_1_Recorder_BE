package com.revup.answer.mapper;

import com.revup.answer.dto.request.AnswerCodeCreateRequest;
import com.revup.answer.entity.Answer;
import com.revup.answer.entity.AnswerCode;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionCode;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AnswerCodeMapper {
    public List<AnswerCode> toEntities(List<AnswerCodeCreateRequest> codes, Answer answer) {
        if (codes == null || codes.isEmpty()) {
            return Collections.emptyList();
        }
        return codes.stream()
                .map(code -> AnswerCode.builder()
                        .answer(answer)
                        .name(code.name())
                        .content(code.content())
                        .build())
                .toList();
    }
}
