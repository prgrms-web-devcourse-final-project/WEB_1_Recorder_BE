package com.revup.question.usecase;

import com.revup.common.SkillStack;
import com.revup.image.entity.QuestionImage;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.dto.response.QuestionIdResponse;
import com.revup.question.entity.Question;
import com.revup.question.entity.QuestionCode;
import com.revup.question.mapper.QuestionMapper;
import com.revup.question.service.QuestionService;
import com.revup.user.entity.User;
import com.revup.user.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CreateQuestionUseCase {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final UserUtil userUtil;


    public QuestionIdResponse execute(QuestionCreateRequest request) {
        User user = userUtil.getCurrentUser();

        Set<SkillStack> questionStacks = questionMapper.toQuestionStacks(request.stacks());

        Question question = questionMapper.toEntity(request, user, questionStacks);

        List<QuestionCode> questionCodes = questionMapper.toQuestionCodes(request.codes(), question);

        List<QuestionImage> questionImages = questionMapper.toQuestionImages(request.images(), question);

        Long id = questionService.createQuestion(question, questionImages, questionCodes);

        return new QuestionIdResponse(id);}

}
