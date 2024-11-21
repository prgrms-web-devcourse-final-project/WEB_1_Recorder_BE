package com.revup.question.entity;

public enum QuestionState {
    TEMPORARY, PENDING, RECEIVED, ADOPTED;

    public static QuestionState from(boolean isTemporary){
        return isTemporary ? TEMPORARY : PENDING;
    }
}
