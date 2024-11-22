package com.revup.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "질문 id : %s 가 존재하지 않습니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원 id : %s 가 존재하지 않습니다"),
    FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "피드백 id : %s 가 존재하지 않습니다"),
    FEEDBACK_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "피드백코드 id : %s 가 존재하지 않습니다"),
    INTERRUPTED(HttpStatus.SERVICE_UNAVAILABLE, "처리가 강제로 중단되었습니다. 잠시 후 다시 시도해주세요."),
    ;
    private final HttpStatus httpStatus;
    private final String messageTemplate;
}
