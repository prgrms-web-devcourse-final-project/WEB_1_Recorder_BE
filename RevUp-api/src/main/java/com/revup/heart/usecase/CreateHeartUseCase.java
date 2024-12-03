package com.revup.heart.usecase;

import com.revup.heart.dto.request.HeartCreateRequest;
import com.revup.heart.dto.response.HeartIdResponse;
import com.revup.heart.entity.Heart;
import com.revup.heart.mapper.HeartMapper;
import com.revup.heart.service.HeartService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateHeartUseCase {
    private final HeartService heartService;
    private final HeartMapper heartMapper;
    public HeartIdResponse execute(HeartCreateRequest request, User currentUser) {
        Heart heart = heartMapper.toEntity(request, currentUser);

        heartService.createHeart(request.answerId(), heart, currentUser);
    }
}
