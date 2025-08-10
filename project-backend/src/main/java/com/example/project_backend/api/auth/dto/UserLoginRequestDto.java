package com.example.project_backend.api.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserLoginRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String userEmail;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
