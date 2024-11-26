package com.revup.question.entity;

public enum QuestionState {
    TEMPORARY,    // 임시 저장 상태
    PENDING,      // 채택 대기 상태 (답변을 기다리는 상태)
    NO_ANSWER,    // 답변이 하나도 없는 상태
    ADOPTED;      // 질문이 채택된 상태

    public static QuestionState from(boolean isTemporary){
        return isTemporary ? TEMPORARY : PENDING;
    }
}
