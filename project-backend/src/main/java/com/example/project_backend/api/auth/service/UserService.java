package com.example.project_backend.api.auth.service;

import com.example.project_backend.api.auth.dto.*;

import java.util.List;

public interface UserService {

    // 회원가입
    UserResponseDto registerUser(UserSignUpRequestDto userSignUpRequestDto);

    // 로그인
    LoginResponseDto login (UserLoginRequestDto userLoginRequestDto);
    
    // 사용자 메일로 사용자 정보 조회
    UserResponseDto getUserById(Long userId);

    // 모든 사용자 정보 조회
    List<UserResponseDto> getAllUsers();

    // 사용자 정보 수정
    UserResponseDto updateUser(Long userId, UserModifyDto userModifyDto);

    // 사용자 삭제
    void deleteUser(Long userId);
}
