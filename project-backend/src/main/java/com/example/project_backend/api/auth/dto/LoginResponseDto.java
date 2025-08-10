package com.example.project_backend.api.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponseDto {

    private UserResponseDto user;
    private String accessToken;

    public LoginResponseDto(UserResponseDto user, String accessToken) {
        this.user = user;
        this.accessToken = accessToken;
    }
}
