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
    TOKEN_NOT_EXIST(FORBIDDEN, "토큰 정보가 없습니다."),
    TOKEN_NOT_SUPPORTED(FORBIDDEN, "지원하지 않는 토큰입니다."),
    TOKEN_INVALID(FORBIDDEN, "유효하지 않은 토큰입니다"),
    REQUEST_INVALID(BAD_REQUEST, "잘못된 요청방식입니다."),
    TOKEN_BAD_SIGNATURE(FORBIDDEN, "서명 불일치"),
    ACCESS_TOKEN_NOT_FOUND(NOT_FOUND, "accessToken이 없음"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "refreshToken이 없음"),
    UNKNOWN_EXCEPTION(INTERNAL_SERVER_ERROR, "알 수 없는 오류 발생"),

    //user
    USER_NOT_FOUND(NOT_FOUND, "일치하는 회원이 없습니다."),
    INVALID_CERTIFICATION_NUMBER(BAD_REQUEST, "인증 번호가 유효하지 않습니다."),
    USER_PERMISSION(FORBIDDEN, "권한이 없는 사용자입니다"),
    USER_ID_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 id : %s 가 존재하지 않습니다"),
    EMAIL_DOMAIN_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 도메인이 존재하지 않습니다"),

    //user skill stack
    NOT_MY_SKILL_STACK(BAD_REQUEST, "자신의 기술스택만 삭제 가능합니다"),
    DUPLICATED_USER_SKILL_STACK(BAD_REQUEST,"이미 등록된 기술 스택입니다"),
    USER_SKILL_STACK_NOT_FOUND(NOT_FOUND,"이미 삭제되었거나 없는 기술스택입니다"),

    //Infra Exception
    OTHER_SERVER_BAD_REQUEST(BAD_REQUEST, "외부 api 400에러"),
    OTHER_SERVER_UNAUTHORIZED(UNAUTHORIZED, "외부 api 401에러"),
    OTHER_SERVER_EXPIRED_TOKEN(BAD_REQUEST, "Other server expired token"),
    OTHER_SERVER_FORBIDDEN(FORBIDDEN, "외부 api 403에러"),

    // 질문
    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "질문 id : %s 가 존재하지 않습니다"),
    QUESTION_INVALID_TYPE(HttpStatus.BAD_REQUEST, "잘못된 질문 타입 입니다"),
    QUESTION_INVALID_STATE(BAD_REQUEST,"잘못된 질문 상태입니다" ),
    QUESTION_ALREADY_ACCEPT(CONFLICT, "이미 답변을 채택한 질문입니다"),

    // 기술스택
    SKILL_INVALID(BAD_REQUEST, "잘못된 기술스택입니다"),


    FEEDBACK_NOT_FOUND(HttpStatus.NOT_FOUND, "피드백 id : %s 가 존재하지 않습니다"),
    FEEDBACK_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "피드백코드 id : %s 가 존재하지 않습니다"),
    INTERRUPTED(HttpStatus.SERVICE_UNAVAILABLE, "처리가 강제로 중단되었습니다. 잠시 후 다시 시도해주세요."),



    FILE_INVALID_NAME(HttpStatus.BAD_REQUEST, "파일 이름이 유효하지 않습니다"),
    FILE_EXCEED_SIZE(HttpStatus.PAYLOAD_TOO_LARGE, "파일 크기가 허용된 최대 크기를 초과했습니다"),
    FILE_UNSUPPORTED_EXTENSION(HttpStatus.BAD_REQUEST, "허용되지 않은 파일 확장자 입니다"),
    S3_SERVICE(HttpStatus.BAD_REQUEST,"Amazon S3 서비스 오류: %s" ),
    S3_CLIENT(HttpStatus.BAD_REQUEST, "Amazon S3 클라이언트 오류: %s"),

    ANSWER_CONCURRENCY(CONFLICT, "답변을 생성하는 동안 충돌이 발생했습니다 다시 시도해주세요"),
    ANSWER_NOT_FOUND(NOT_FOUND, "답변 id : %s 가 존재하지 않습니다"),
    ADOPTED_REVIEW_INVALID(BAD_REQUEST,"올바르지 않은 채택 리뷰입니다" ),
    ANSWER_ALREADY_ADOPTED(CONFLICT, "이미 채택된 답변입니다"),
    ANSWER_NOT_LINKED(BAD_REQUEST,"해당 답변은 이 질문에 속하지 않습니다" ),

    HEART_TYPE_INVALID(BAD_REQUEST, "잘못된 반응 타입입니다"),
    HEART_ALREADY_EXIST(CONFLICT, "이미 해당 답변에 좋아요/싫어요가 존재합니다"),
    HEART_NOT_FOUND(NOT_FOUND, "해당 좋아요/싫어요가 존재하지 않습니다"),;

    private final HttpStatus httpStatus;
    private final String messageTemplate;
}
