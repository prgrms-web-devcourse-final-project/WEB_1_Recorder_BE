package com.revup.answer.mapper;

import com.revup.answer.dto.AnswerUpdateInfo;
import com.revup.answer.dto.request.AnswerCreateRequest;
import com.revup.answer.dto.request.AnswerUpdateRequest;
import com.revup.answer.entity.Answer;
import com.revup.common.BooleanStatus;
import com.revup.image.dto.request.ImageRequest;
import com.revup.answer.entity.AnswerImage;
import com.revup.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class AnswerMapper {
    public Answer toEntity(AnswerCreateRequest request, User user) {
        return Answer.builder()
                .title(request.title())
                .content(request.content())
                .code(request.code())
                .isAccept(BooleanStatus.FALSE)
                .user(user)
                .build();
    }

    public AnswerUpdateInfo toUpdateInfo(AnswerUpdateRequest request){
        return new AnswerUpdateInfo(
                request.title(),
                request.content(),
                request.code()
        );
    }

}
