package com.revup.answer.usecase;

import com.revup.answer.dto.request.AnswerUpdateRequest;
import com.revup.answer.dto.response.AnswerIdResponse;
import com.revup.answer.entity.Answer;
import com.revup.answer.mapper.AnswerCodeMapper;
import com.revup.answer.mapper.AnswerImageMapper;
import com.revup.answer.mapper.AnswerMapper;
import com.revup.answer.service.AnswerService;
import com.revup.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateAnswerUseCase {
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;
    private final AnswerImageMapper imageMapper;
    private final AnswerCodeMapper codeMapper;

    public AnswerIdResponse execute(@Valid AnswerUpdateRequest request, User currentUser) {
        Answer answer = answerMapper.toUpdateEntity(request);
        imageMapper.toUpdateEn
    }
}
