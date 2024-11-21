package com.revup.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "질문 id : %s 가 존재하지 않습니다"),
    QUESTION_INVALID_TYPE(HttpStatus.BAD_REQUEST, "잘못된 질문 타입 입니다"),
    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 id : %s 가 존재하지 않습니다"),;
    private final HttpStatus httpStatus;
    private final String messageTemplate;
}
