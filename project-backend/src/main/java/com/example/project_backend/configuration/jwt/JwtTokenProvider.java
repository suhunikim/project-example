package com.example.project_backend.configuration.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    private Key key;

    // 빈 초기화 시 key 생성
    @PostConstruct
    public void init() {
        byte[] keyBytes = jwtConfig.getSecretKey().getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    // Subject를 email로 설정
    public String createToken(String userEmail) {
        Date now = new Date();
        //만료 시간 (초 -> 밀리초)
        Date expiration = new Date(now.getTime() + jwtConfig.getExpiryTime() * 1000);

        return Jwts.builder()
                .subject(userEmail)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    // JWT 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) key) // ✅ `verifyWith()`에 key 넣기
                    .build()
                    .parseSignedClaims(token); // ✅ 검증

            return true; // 검증 성공
        } catch (Exception e) {
            return false; // 검증 실패
        }
    }

    // JWT 토큰에서 subject(email) 추출
    public String getUserEmail(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
