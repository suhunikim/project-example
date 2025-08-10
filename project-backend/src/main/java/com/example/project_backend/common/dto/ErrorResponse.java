package com.example.project_backend.common.dto;

import com.example.project_backend.common.code.ErrorCode;

// 불변 데이터 객체 표현에는 record가 더 적합하고, 코드가 간결해지며, Lombok 의존성을 줄일 수 있음.
public record ErrorResponse(int status, String message) {

    // ErrorCode로부터 ErrorResponse를 생성하는 정적 팩토리 메서드
    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getStatus().value(), errorCode.getMessage());
    }
}
