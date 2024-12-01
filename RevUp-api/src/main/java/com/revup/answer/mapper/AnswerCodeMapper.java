package com.revup.answer.mapper;

import com.revup.answer.dto.request.AnswerCodeCreateRequest;
import com.revup.answer.entity.Answer;
import com.revup.answer.entity.AnswerCode;
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

    public List<AnswerCode> toUpdateEntity(List<AnswerCodeCreateRequest> codes) {
        if (codes == null || codes.isEmpty()) {
            return Collections.emptyList();
        }
        return codes.stream()
                .map(code -> AnswerCode.builder()
                        .name(code.name())
                        .content(code.content())
                        .build())
                .toList();
    }
}
