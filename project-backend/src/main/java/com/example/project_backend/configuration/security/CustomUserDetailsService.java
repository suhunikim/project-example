package com.example.project_backend.configuration.security;

import com.example.project_backend.common.code.ErrorCode;
import com.example.project_backend.common.exception.CustomException;
import com.example.project_backend.domain.user.entity.User;
import com.example.project_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        // 사용자 이메일로 사용자 정보 조회
        User user = userRepository.findByUserEmail(username)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND, "User not found with email: " + username)); // 사용자 정보를 찾지 못한 경우 예외 처리

        return new CustomUserDetails(user);
    }
}
