package com.example.project_backend.api.auth.service;

import com.example.project_backend.api.auth.dto.*;
import com.example.project_backend.common.code.ErrorCode;
import com.example.project_backend.common.exception.CustomException;
import com.example.project_backend.configuration.jwt.JwtTokenProvider;
import com.example.project_backend.domain.user.entity.User;
import com.example.project_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) //읽기 전용 트랜잭션
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public UserResponseDto registerUser(UserSignUpRequestDto userSignUpRequestDto) {

        // 이메일 중복 체크
        if(userRepository.existsByUserEmail(userSignUpRequestDto.getUserEmail())) {
            throw new CustomException(ErrorCode.DUPLICATE_EMAIL);
        }

        // UserSignUpRequestDto -> User 엔티티 변환
        User user = User.builder()
                .userEmail(userSignUpRequestDto.getUserEmail())
                .password(passwordEncoder.encode(userSignUpRequestDto.getPassword()))
                .userName(userSignUpRequestDto.getUserName())
                .phoneNumber(userSignUpRequestDto.getPhoneNumber())
                .build();

        User savedUser = userRepository.save(user);

        return UserResponseDto.fromEntity(savedUser);
    }

    @Override
    public LoginResponseDto login(UserLoginRequestDto userLoginRequestDto) {
        // 1. 이메일로 사용자 조회
        // 2. 사용자 없으면 exception (Optional을 사용하므로 orElseThrow를 사용하여 exception 밸상)
        User user = userRepository.findByUserEmail(userLoginRequestDto.getUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 3. 조회 된 사용자 비밀번호 확인
        // 4. 비밀번호 불일치하면 exception
        if(!passwordEncoder.matches(userLoginRequestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }

        // 5. JWT 토큰 생성
        String token = jwtTokenProvider.createToken(user.getUserEmail());

        return new LoginResponseDto(UserResponseDto.fromEntity(user), token);
    }

    @Override
    public UserResponseDto getUserById(Long userId) {
        return null;
    }

    @Override
    public List<UserResponseDto> getAllUsers() {
        return List.of();
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserModifyDto userModifyDto) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }
}
