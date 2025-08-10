package com.example.project_backend.configuration.oauth;

import com.example.project_backend.configuration.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 인증 성공 후 처리
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();

        // 사용자 정보에서 이메일 추출
        String email = oAuth2User.getAttribute("email");

        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(email);

        String baseUrl = "http://localhost:3000"; // 기본값
        String referer = request.getHeader("Referer");

        // Referer 헤더를 확인하여 요청한 서버에 따라 URL을 변경
        if (referer != null && referer.contains("5173")) {
            baseUrl = "http://localhost:5173"; // 개발 서버에서 요청한 경우
        }

        // JWT 토큰을 클라이언트에게 전달 (예: 리다이렉트 URL에 포함)
        response.sendRedirect(baseUrl + "/oauth2/success?token=" + token);
    }
}
