package com.revup.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //auth
    LOGIN_ERROR(BAD_REQUEST, "로그인에러"),
    PERMISSION_DENIED(FORBIDDEN, "해당 API 권한이 없습니다"),
    TOKEN_TIMEOUT(UNAUTHORIZED, "만료된 토큰"),
    TOKEN_NOT_EXIST(FORBIDDEN, "존재하지 않는 토큰입니다."),
    TOKEN_NOT_SUPPORTED(FORBIDDEN, "지원하지 않는 토큰입니다."),
    TOKEN_INVALID(FORBIDDEN, "유효하지 않은 토큰입니다"),
    REQUEST_INVALID(BAD_REQUEST, "잘못된 요청방식입니다."),
    TOKEN_BAD_SIGNATURE(FORBIDDEN, "서명 불일치"),
    ACCESS_TOKEN_NOT_FOUNT(NOT_FOUND, "ACCESS TOKEN이 없음"),
    UNKNOWN_EXCEPTION(INTERNAL_SERVER_ERROR, "알 수 없는 오류 발생"),

    //user
    USER_NOT_FOUND(NOT_FOUND, "일치하는 회원이 없습니다."),

    //Infra Exception
    OTHER_SERVER_BAD_REQUEST(BAD_REQUEST, "외부 api 400에러"),
    OTHER_SERVER_UNAUTHORIZED(UNAUTHORIZED, "외부 api 401에러"),
    OTHER_SERVER_EXPIRED_TOKEN(BAD_REQUEST, "Other server expired token"),
    OTHER_SERVER_FORBIDDEN(FORBIDDEN, "외부 api 403에러"),

    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "질문 id : %s 가 존재하지 않습니다"),
    ;
    private final HttpStatus httpStatus;
    private final String messageTemplate;
}
