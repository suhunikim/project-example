package com.example.project_backend.common.exception;

import com.example.project_backend.common.code.ErrorCode;
import com.example.project_backend.common.dto.ErrorResponse;
import com.example.project_backend.common.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();

        // ErrorResponse 사용
        ErrorResponse response = ErrorResponse.of(errorCode);
        return new ResponseEntity<>(response, errorCode.getStatus());
    }

    // MethodArgumentNotValidException 처리 (추가)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseDto<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = new ErrorResponse(400, e.getBindingResult().toString()); // 400 Bad Request
        return new ResponseEntity<>(ResponseDto.fail(errorResponse), HttpStatus.BAD_REQUEST);
    }

    // 기타 예외 처리 (필요에 따라 추가)
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ResponseDto<?>> handleException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(ResponseDto.fail(errorResponse), ErrorCode.INTERNAL_SERVER_ERROR.getStatus());
    }
}
