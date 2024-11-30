package com.revup.question.usecase;

import com.revup.question.adaptor.QuestionAdaptor;
import com.revup.question.dto.request.QuestionUpdateRequest;
import com.revup.question.dto.request.QuestionAcceptAnswerRequest;
import com.revup.question.dto.response.QuestionIdResponse;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionCode;
import com.revup.question.entity.QuestionImage;
import com.revup.question.mapper.QuestionCodeMapper;
import com.revup.question.mapper.QuestionImageMapper;
import com.revup.question.mapper.QuestionMapper;
import com.revup.question.service.QuestionService;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateQuestionUseCase {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final QuestionImageMapper imageMapper;
    private final QuestionCodeMapper codeMapper;

    public QuestionIdResponse updateQuestion(QuestionUpdateRequest request, User currentUser) {

        Question question = questionMapper.toUpdateEntity(request);

        List<QuestionImage> images = imageMapper.toUpdateEntity(request.images());

        List<QuestionCode> codes = codeMapper.toUpdateEntity(request.codes());

        Long id = questionService.updateQuestion(request.id(), currentUser, question, images, codes);

        return new QuestionIdResponse(id);

    }

    public QuestionIdResponse acceptAnswer(QuestionAcceptAnswerRequest request, User currentUser) {
        Long id = questionService.adoptAnswer(request.questionId(), request.answerId(), request.review(), currentUser);
        return new QuestionIdResponse(id);

    }
}
