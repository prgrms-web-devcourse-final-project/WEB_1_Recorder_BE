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
    QUESTION_INVALID_TYPE(HttpStatus.BAD_REQUEST, "잘못된 질문 타입 입니다"),

    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 id : %s 가 존재하지 않습니다"),

    FILE_INVALID_NAME(HttpStatus.BAD_REQUEST, "파일 이름이 유효하지 않습니다"),
    FILE_EXCEED_SIZE(HttpStatus.PAYLOAD_TOO_LARGE, "파일 크기가 허용된 최대 크기를 초과했습니다"),
    FILE_UNSUPPORTED_EXTENSION(HttpStatus.BAD_REQUEST, "허용되지 않은 파일 확장자 입니다"),
    S3_SERVICE(HttpStatus.BAD_REQUEST,"Amazon S3 서비스 오류: %s" ),
    S3_CLIENT(HttpStatus.BAD_REQUEST, "Amazon S3 클라이언트 오류: %s"),;

    private final HttpStatus httpStatus;
    private final String messageTemplate;
}
