package com.example.project_backend.configuration.security;

import com.example.project_backend.common.code.UserStatus;
import com.example.project_backend.domain.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 단일 역할을 GrantedAuthority로 변환 (UserRole은 Enum이므로 name() 메서드로 변환)
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())); // 권한 정보는 필요에 따라 구현
    }

    @Override
    public String getPassword() {
        return user.getPassword(); // 비밀번호
    }

    @Override
    public String getUsername() {
        return user.getUserEmail(); // 사용자 이름 (이메일)
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정 만료 여부 (true: 만료되지 않음)
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정 잠금 여부 (true: 잠금되지 않음)
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 자격 증명 만료 여부 (true: 만료되지 않음)
    }

    @Override
    public boolean isEnabled() {
        // UserStatus가 ACTIVE인 경우 활성화된 계정으로 판단
        return user.getStatus().equals(UserStatus.ACTIVE);
    }
}
