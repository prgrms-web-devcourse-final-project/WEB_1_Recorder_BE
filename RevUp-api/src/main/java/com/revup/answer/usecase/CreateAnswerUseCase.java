package com.revup.answer.usecase;

import com.revup.answer.dto.request.AnswerCreateRequest;
import com.revup.answer.dto.response.AnswerIdResponse;
import com.revup.answer.entity.Answer;
import com.revup.answer.entity.AnswerCode;
import com.revup.answer.entity.AnswerImage;
import com.revup.answer.mapper.AnswerCodeMapper;
import com.revup.answer.mapper.AnswerImageMapper;
import com.revup.answer.mapper.AnswerMapper;
import com.revup.answer.service.AnswerService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CreateAnswerUseCase {
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;
    private final AnswerCodeMapper codeMapper;
    private final AnswerImageMapper imageMapper;


    public AnswerIdResponse execute(AnswerCreateRequest request, User currentUser) {

        Answer answer = answerMapper.toEntity(request, currentUser);

        AnswerCode answerCode = codeMapper.toEntity(request.code(), answer);

        List<AnswerImage> answerImages = imageMapper.toEntities(request.images(), answer);


        Long answerId = answerService.createAnswer(request.questionId(), answer, answerImages, answerCode);

        return new AnswerIdResponse(answerId);

    }
}
