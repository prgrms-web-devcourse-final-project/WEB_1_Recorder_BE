package com.revup.answer.usecase;

import com.revup.answer.dto.request.AnswerCreateRequest;
import com.revup.answer.dto.response.AnswerIdResponse;
import com.revup.answer.entity.Answer;
import com.revup.answer.mapper.AnswerMapper;
import com.revup.answer.service.AnswerService;
import com.revup.image.entity.AnswerImage;
import com.revup.image.entity.QuestionImage;
import com.revup.question.adaptor.QuestionAdaptor;
import com.revup.question.entity.Question;
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
    private final QuestionAdaptor questionAdaptor;
    private final UserUtil userUtil;

    public AnswerIdResponse execute(AnswerCreateRequest request){
        User user = userUtil.getCurrentUser();

        Question question = questionAdaptor.findById(request.questionId());

        Answer answer = answerMapper.toEntity(request, user, question);

        List<AnswerImage> answerImages = answerMapper.toAnswerImages(request.images(), answer);

        return new AnswerIdResponse(answerService.createAnswer(answer, answerImages));
        
    }
}
