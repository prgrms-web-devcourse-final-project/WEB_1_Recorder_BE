package com.revup.question.command;

import com.revup.image.entity.QuestionImage;
import com.revup.question.dto.request.QuestionCreateRequest;
import com.revup.question.dto.response.QuestionIdResponse;
import com.revup.question.entity.Question;
import com.revup.question.mapper.QuestionMapper;
import com.revup.question.service.QuestionService;
import com.revup.tag.adaptor.TagAdaptor;
import com.revup.tag.entity.Tag;
import com.revup.tag.mapper.TagMapper;
import com.revup.user.adaptor.UserAdaptor;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QuestionCommand {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final TagMapper tagMapper;
    private final UserAdaptor userAdaptor;
    private final TagAdaptor tagAdaptor;

    public QuestionIdResponse create(QuestionCreateRequest request) {
//        User user = userAdaptor.getCurrentUser();
        User user = null;

        Question question = questionMapper.toEntity(request, user);

        List<Tag> tags = tagAdaptor.getTags(tagMapper.toString(request.tags()));

        List<QuestionImage> questionImages = questionMapper.toQuestionImages(request.images(), question);

        Long id = questionService.createQuestion(question, tags, questionImages);

        return new QuestionIdResponse(id);
    }



}
