package com.revup.question.mapper;

import com.revup.question.dto.request.QuestionCodeCreateRequest;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionCode;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class QuestionCodeMapper {

    public List<QuestionCode> toEntity(List<QuestionCodeCreateRequest> codes, Question question) {
        if (codes == null || codes.isEmpty()) {
            return Collections.emptyList();
        }
        return codes.stream()
                .map(code -> QuestionCode.builder()
                        .question(question)
                        .name(code.name())
                        .content(code.content())
                        .build())
                .toList();
    }
}
