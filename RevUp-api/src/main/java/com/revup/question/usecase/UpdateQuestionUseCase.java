package com.revup.question.usecase;

import com.revup.question.adaptor.QuestionAdaptor;
import com.revup.question.dto.QuestionUpdateRequest;
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
    private final QuestionAdaptor questionAdaptor;
    private final QuestionMapper questionMapper;
    private final QuestionImageMapper imageMapper;
    private final QuestionCodeMapper codeMapper;
    private final UserUtil userUtil;

    public QuestionIdResponse updateQuestion(QuestionUpdateRequest request) {
        // Question 조회
        Question question = questionAdaptor.findById(request.id());

        // 권한 검증
        userUtil.checkPermission(question.getUser());

        questionMapper.updateEntity(request, question);

        List<QuestionImage> images = imageMapper.toEntities(request.images(), question);
        questionService.updateImages(question.getId(), images);

        List<QuestionCode> codes = codeMapper.toEntities(request.codes(), question);
        questionService.updateCodes(question.getId(), codes);

        return new QuestionIdResponse(question.getId());

    }

    public QuestionIdResponse acceptAnswer(QuestionAcceptAnswerRequest request, User currentUser) {
        Long id = questionService.adoptAnswer(request.questionId(), request.answerId(), request.review(), currentUser);
        return new QuestionIdResponse(id);

    }
}
