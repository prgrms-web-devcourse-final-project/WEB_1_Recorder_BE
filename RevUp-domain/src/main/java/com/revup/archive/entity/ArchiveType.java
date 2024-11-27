package com.revup.archive.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ArchiveType {

    REQUESTED_REVIEW("받은 리뷰 요청 수 %d개 이상"),   //리뷰 요청
    CUMULATIVE_LIKES("누적 추천 수 %d개 이상"),   //좋아요
    ADOPTION_RATE("채택률 %d%% 이상"),      //채택률
    ADOPTION_COUNT("채택 답변 수 %d개 이상"),     //채택 답변 개수
    CONTINUOUS_ANSWER("%d일 연속 답변"); //연속 답변

    private final String title;


    public String getTitle(int condition) {
        return String.format(this.title, condition);
    }
}
