package com.revup.heart.usecase;

import com.revup.heart.dto.request.HeartRequest;
import com.revup.heart.dto.response.HeartCountResponse;
import com.revup.heart.dto.response.HeartStateResponse;
import com.revup.heart.enums.HeartType;
import com.revup.heart.port.HeartPort;
import com.revup.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HeartUseCase {

    private final HeartPort heartPort;

    public void process(Long answerId, HeartRequest request, Long userId) {
        // 현재 좋아요가 눌렸는지 싫어요가 눌렸는지 아무것도 아닌지
        HeartType currentType = heartPort.getHeartType(answerId, userId);

        HeartType newType = HeartType.from(request.isGood());

        if (request.click()) {
            // 만약 요청과 다른 타입이 저장되어있으면 삭제
            if (!newType.equals(currentType)&&currentType!=null) {
                heartPort.removeHeart(answerId, userId,!request.isGood());
            }
            heartPort.addHeart(answerId, userId, request.isGood());
        }

        else {
            heartPort.removeHeart(answerId, userId, request.isGood());
        }

    }

    public HeartStateResponse getState(Long answerId, User currentUser) {
        HeartType state = heartPort.getHeartType(answerId, currentUser.getId());
        return new HeartStateResponse(state.getValue());
    }

    public HeartCountResponse getCount(Long answerId) {
        int goodCount = heartPort.getGoods(answerId).size();
        int badCount = heartPort.getBads(answerId).size();

        return new HeartCountResponse(goodCount, badCount);
    }
}
