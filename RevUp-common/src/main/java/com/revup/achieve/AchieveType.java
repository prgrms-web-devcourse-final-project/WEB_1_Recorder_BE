package com.revup.achieve;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AchieveType {

    REQUESTED_FEEDBACK("받은 리뷰 요청 수", "받은 리뷰 요청 수 %d개 이상"),   //리뷰 요청
    CUMULATIVE_LIKES("누적 추천 수", "누적 추천 수 %d개 이상"),   //좋아요
    ADOPTION_RATE("채택률", "채택률 %d%% 이상"),      //채택률
    ADOPTION_COUNT("채택 답변 개수", "채택 답변 수 %d개 이상"),     //채택 답변 개수
    CONTINUOUS_ANSWER("연속 답변 일수","%d일 연속 답변"); //연속 답변

    @Getter
    private final String type;
    private final String content;

    public String getContent(int condition) {
        return String.format(this.content, condition);
    }
}
