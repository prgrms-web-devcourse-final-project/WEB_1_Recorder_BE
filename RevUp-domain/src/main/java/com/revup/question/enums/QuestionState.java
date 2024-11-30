package com.revup.question.enums;

import com.revup.question.exception.InvalidQuestionStateException;

public enum QuestionState {
    PENDING,      // 채택 대기 상태 (답변을 기다리는 상태)
    NO_ANSWER,    // 답변이 하나도 없는 상태
    ADOPTED;      // 질문이 채택된 상태

    public static QuestionState of(String state) {
        try {
            return QuestionState.valueOf(state.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidQuestionStateException();
        }
    }
}
