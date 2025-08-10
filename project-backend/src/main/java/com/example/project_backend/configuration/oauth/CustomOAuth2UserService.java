package com.example.project_backend.configuration.oauth;

import com.example.project_backend.common.code.UserStatus;
import com.example.project_backend.domain.user.entity.User;
import com.example.project_backend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        //구글에서 사용자 정보 가져오기
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(request);

        // 사용자 정보 추출
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // 사용자 정보로 User 엔티티 생성, DB에서 사용자 조회 or 자동 등록
        User user = userRepository.findByUserEmail(email)
                .orElseGet(() -> userRepository.save(User.builder()
                        .userEmail(email)
                        .userName(name)
                        .password("") // 소셜 로그인은 비밀번호 없음
                        .status(UserStatus.ACTIVE)
                        .phoneNumber("010-0000-0000") // 기본값 설정
                        .build()
                ));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().name())),
                oAuth2User.getAttributes(),
                "email" // 구글에서 사용자 식별을 위한 키
        );
    }
}
