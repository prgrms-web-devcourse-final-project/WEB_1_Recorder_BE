package com.revup.heart.usecase;

import com.revup.heart.dto.request.HeartRequest;
import com.revup.heart.dto.response.HeartCountResponse;
import com.revup.heart.dto.response.HeartStateResponse;
import com.revup.heart.enums.HeartType;
import com.revup.heart.service.HeartService;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeartUseCase {

    private final HeartService heartService;

    public void process(Long answerId, HeartRequest request, Long userId) {
        // 현재 좋아요가 눌렸는지 싫어요가 눌렸는지 아무것도 아닌지
        HeartType currentState = heartService.getState(answerId, userId);

        HeartType newState = HeartType.from(request.isGood());

        // 현재 상태와 새로운 요청이 동일한 타입이면 삭제
        if (newState.equals(currentState)) {
            heartService.removeHeart(answerId, userId, request.isGood());
            return;
        }

        // 추가전에 만약 반대 상태가 존재하면 삭제
        if (currentState != null) {
            boolean isOpposite = !request.isGood();
            heartService.removeHeart(answerId, userId, isOpposite);
        }

        // 새로운 상태 추가
        heartService.addHeart(answerId, userId, request.isGood());

    }

    public HeartStateResponse getState(Long answerId, User currentUser) {
        HeartType state = heartService.getState(answerId, currentUser.getId());
        return new HeartStateResponse(state.getValue());
    }

    public HeartCountResponse getCount(Long answerId) {
        int goodCount = heartService.getGoodCount(answerId);
        int badCount = heartService.getBadCount(answerId);

        return new HeartCountResponse(goodCount, badCount);
    }
}
