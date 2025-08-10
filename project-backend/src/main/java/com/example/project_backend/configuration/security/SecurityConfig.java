package com.example.project_backend.configuration.security;

import com.example.project_backend.configuration.jwt.JwtAuthenticationFilter;
import com.example.project_backend.configuration.oauth.CustomOAuth2UserService;
import com.example.project_backend.configuration.oauth.OAuth2AuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final ClientRegistrationRepository clientRepo;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    // JWT 인증 필터를 SecurityFilterChain에 등록
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // OAuth2 인증 요청을 처리할 Resolver 설정
        var resolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRepo, "/oauth2/authorization"
        );

        // OAuth2 인증 요청 시 추가 파라미터 설정
        resolver.setAuthorizationRequestCustomizer(customizer ->
                customizer.additionalParameters(params ->
                        params.put("prompt", "select_account")
                )
        );

        // HTTP 보안 설정
        http
            // CORS 설정
            .cors(cors -> cors.configurationSource(request -> {
                var corsConfig = new CorsConfiguration();
                corsConfig.setAllowedOrigins(List.of("http://localhost:3000", "http://127.0.0.1:3000"));
                corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                corsConfig.setAllowedHeaders(List.of("*"));
                corsConfig.setAllowCredentials(true);
                return corsConfig;
            }))
            // CSRF 설정
            .csrf(AbstractHttpConfigurer::disable)
            // 세션 관리 설정
            .authorizeHttpRequests(auth -> auth
                    // "/api/auth/**" 경로는 인증 없이 접근 허용
                    .requestMatchers("/api/**", "/oauth2/**").permitAll()
                    // 그 외의 모든 요청은 인증 필요
                    .anyRequest().authenticated()
            )
            // OAuth2 로그인 설정
            .oauth2Login(
                    // OAuth2 로그인 설정
                    oauth -> oauth
                            // OAuth2 로그인 요청을 처리할 URL 설정
                            .authorizationEndpoint(authz -> authz
                                    .authorizationRequestResolver(resolver)
                            )
                            // 로그인 성공 후 처리할 핸들러 설정
                            .userInfoEndpoint(userInfo -> userInfo
                                    .userService(customOAuth2UserService) // OAuth2 사용자 정보 서비스 설정
                            )
                            // 인증 성공 핸들러 설정
                            .successHandler(oAuth2AuthenticationSuccessHandler) // 인증 성공 핸들러 설정
            )
            // JWT 인증 필터 등록
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 비밀번호 암호화를 위한 PasswordEncoder 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
