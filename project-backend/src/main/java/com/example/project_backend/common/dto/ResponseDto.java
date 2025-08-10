package com.example.project_backend.common.dto;

import com.example.project_backend.common.code.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ResponseDto<T> {

    private final boolean success;
    private final String message;
    private final T data;
    private final ErrorResponse error;

    public static <T> ResponseDto<T> success(String message, T data) {
        return new ResponseDto<>(true, message, data, null);
    }

    public static <T> ResponseDto<T> fail(ErrorCode errorCode) {
        return new ResponseDto<>(false, errorCode.getMessage(), null, ErrorResponse.of(errorCode));
    }

    public static <T> ResponseDto<T> fail(ErrorResponse errorResponse) {
        return new ResponseDto<>(false, null, null, errorResponse);
    }
}
