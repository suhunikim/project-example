package com.example.project_backend.common.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // Common (일반적인 에러)
    // 입력 값 오류
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "Invalid Input Value"),

    //잘못된 HTTP 메서드
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "Invalid Method"),

    //데이터를 찾을 수 없음
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "Entity Not Found"),

    //서버 내부 오류
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),

    //잘못된 타입
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "Invalid Type Value"),

    //접근 거부
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Access is Denied"),


    // User (사용자 관련 에러)
    // 사용자를 찾을 수 없음
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User Not Found"),

    // 이메일 중복
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "Email Already Exists"),

    // 비밀번호 불일치
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "Password Mismatch"),
    // ... 추가적인 User 관련 에러 코드
    ;

    private final HttpStatus status; // HTTP 상태 코드
    private final String message;    // 에러 메시지

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
