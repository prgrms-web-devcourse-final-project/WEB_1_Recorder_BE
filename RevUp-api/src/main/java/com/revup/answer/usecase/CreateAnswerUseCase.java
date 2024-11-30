package com.revup.answer.usecase;

import com.revup.answer.dto.request.AnswerCreateRequest;
import com.revup.answer.dto.response.AnswerIdResponse;
import com.revup.answer.entity.Answer;
import com.revup.answer.entity.AnswerCode;
import com.revup.answer.mapper.AnswerCodeMapper;
import com.revup.answer.mapper.AnswerImageMapper;
import com.revup.answer.mapper.AnswerMapper;
import com.revup.answer.service.AnswerService;
import com.revup.answer.entity.AnswerImage;
import com.revup.question.entity.QuestionCode;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
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


    public AnswerIdResponse execute(AnswerCreateRequest request,User currentUser){

        Answer answer = answerMapper.toEntity(request, currentUser);

        List<AnswerCode> answerCodes = codeMapper.toEntities(request.codes(), answer);

        List<AnswerImage> answerImages = imageMapper.toEntities(request.images(), answer);


        Long answerId = answerService.createAnswer(request.questionId(), answer, answerImages, answerCodes);

        return new AnswerIdResponse(answerId);
        
    }
}
