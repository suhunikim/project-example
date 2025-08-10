package com.example.project_backend.configuration.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Spring이 해당 클래스를 빈으로 등록하도록 지정
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // JWT 토큰 관련 기능 (토큰 생성, 검증, 정보 추출)
    private final JwtTokenProvider jwtTokenProvider;

    // 사용자 정보를 로드하기 위한 서비스 (UserDetailService 구현체)
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 1. http 요청 헤더에게 JWT 토큰을 추출
        String token = getJwtFromRequest(request);

        // 2. 토큰이 존재하고 유효한 경우
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 토큰에서 사용자 이메일을 추출
            String userEmail = jwtTokenProvider.getUserEmail(token);

            // 3. UserDetailsService를 사용해 사용자 정보를 로드
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

            // 4. 사용자 정보를 기반으로 인증 객체 생성 (비밀번호는 null, 권한 정보 포함)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            // 요청에 대한 추가 정보를 인증 객체에 설정 (예: IP, 세션 정보)
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 5. SecurityContext에 인증 객체를 저장하여 인증된 사용자 정보를 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 6. 필터 체인을 계속 진행
        filterChain.doFilter(request, response);
    }

    // "Authorization" 헤더에서 JWT 토큰만 추출하는 메서드
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        // 값이 존재하고 "Bearer"으로 시작하면, 토큰 부분을 리턴
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
