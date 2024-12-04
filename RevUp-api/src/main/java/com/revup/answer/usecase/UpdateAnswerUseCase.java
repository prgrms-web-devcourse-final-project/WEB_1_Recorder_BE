package com.revup.answer.usecase;

import com.revup.answer.dto.AnswerUpdateInfo;
import com.revup.answer.dto.request.AnswerUpdateRequest;
import com.revup.answer.dto.response.AnswerIdResponse;
import com.revup.answer.entity.AnswerImage;
import com.revup.answer.mapper.AnswerImageMapper;
import com.revup.answer.mapper.AnswerMapper;
import com.revup.answer.service.AnswerService;
import com.revup.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UpdateAnswerUseCase {
    private final AnswerService answerService;
    private final AnswerMapper answerMapper;
    private final AnswerImageMapper imageMapper;

    public AnswerIdResponse execute(@Valid AnswerUpdateRequest request, User currentUser) {
        AnswerUpdateInfo updateInfo = answerMapper.toUpdateInfo(request);

        List<AnswerImage> images = imageMapper.toUpdateEntity(request.images());


        Long id = answerService.updateAnswer(request.answerId(), updateInfo, images, currentUser);

        return new AnswerIdResponse(id);
    }
}
