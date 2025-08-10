package com.example.project_backend.common.exception;

import com.example.project_backend.common.code.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    // RuntimeException 생성자에 에러 베시지 전달
    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // 추가 생성자 : 개발자가 메시지를 넣을수 있음
    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
