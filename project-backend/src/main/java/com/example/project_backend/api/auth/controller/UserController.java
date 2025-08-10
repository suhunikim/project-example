package com.example.project_backend.api.auth.controller;

import com.example.project_backend.api.auth.dto.LoginResponseDto;
import com.example.project_backend.api.auth.dto.UserLoginRequestDto;
import com.example.project_backend.api.auth.dto.UserResponseDto;
import com.example.project_backend.api.auth.dto.UserSignUpRequestDto;
import com.example.project_backend.api.auth.service.UserService;
import com.example.project_backend.common.code.ErrorCode;
import com.example.project_backend.common.dto.ResponseDto;
import com.example.project_backend.common.exception.CustomException;
import com.example.project_backend.configuration.security.CustomUserDetails;
import com.example.project_backend.domain.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<UserResponseDto>> signUp (@Valid @RequestBody UserSignUpRequestDto userSignUpRequestDto) {
        UserResponseDto registerUser = userService.registerUser(userSignUpRequestDto);
        return ResponseEntity.ok(ResponseDto.success("사용자 등록 성공", registerUser));
    }

    // 아이디 + 비밀번회 등 민감한 정보는 HTTP Body에 담아야 안전하게 전달하므로 PostMapping 사용
    @PostMapping("/login")
    public ResponseEntity<ResponseDto<LoginResponseDto>> login (@Valid @RequestBody UserLoginRequestDto userLoginRequestDto) {
        LoginResponseDto loggedInUser = userService.login(userLoginRequestDto);
        return ResponseEntity.ok(ResponseDto.success("로그인 성공", loggedInUser));
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseDto<UserResponseDto>> getCurrentUser(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        log.info(">> userDetails: {}", userDetails);

        if (userDetails == null || userDetails.getUser() == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND, "인증된 사용자 정보가 없습니다.");
        }

        User user = userDetails.getUser();
        log.info(">> user.getUserName(): {}", user.getUserName());

        // DTO 변환 시 예외 처리
        UserResponseDto dto;
        try {
            dto = UserResponseDto.fromEntity(user);
            log.info("UserResponseDto.fromEntity() 변환 성공: {}", dto);
        } catch (Exception e) {
            log.error("fromEntity 변환 중 예외 발생", e);
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR, "UserResponseDto 변환 실패");
        }

        // ResponseDto 생성 및 JSON 직렬화 로그
        ResponseDto<UserResponseDto> responseDto = ResponseDto.success("사용자 정보 조회 성공", dto);
        try {
            String responseJson = new ObjectMapper().writeValueAsString(responseDto);
            log.info("ResponseDto JSON 직렬화: {}", responseJson);
        } catch (Exception e) {
            log.error("ResponseDto JSON 직렬화 실패", e);
        }

        return ResponseEntity.ok(responseDto);
    }

}
