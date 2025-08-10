package com.example.project_backend.api.auth.dto;

import com.example.project_backend.common.code.UserRole;
import com.example.project_backend.common.code.UserStatus;
import com.example.project_backend.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Long userId;
    private String userName;
    private String userEmail;
    private String phoneNumber;
    private UserStatus status;
    private String statusDescription;
    private UserRole role;
    private String roleTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastLogin;

    private String department;
    private String position;
    private String jobTitle;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate hireDate;

    private Long managerId;

    public static UserResponseDto fromEntity(User user){
        UserResponseDto dto = new UserResponseDto();

        dto.userId = user.getUserId();
        dto.userName = user.getUserName() != null ? user.getUserName() : "";
        dto.userEmail = user.getUserEmail() != null ? user.getUserEmail() : "";
        dto.phoneNumber = user.getPhoneNumber() != null ? user.getPhoneNumber() : "";

        UserStatus status = user.getStatus();
        dto.status = status != null ? status : UserStatus.INACTIVE;
        dto.statusDescription = status != null ? status.getDescription() : "대기중";

        UserRole role = user.getRole();
        dto.role = role != null ? role : UserRole.USER;
        dto.roleTitle = role != null ? role.getTitle() : "일반 사용자";

        dto.lastLogin = user.getLastLogin();

        dto.department = user.getDepartment() != null ? user.getDepartment() : "";
        dto.position = user.getPosition() != null ? user.getPosition() : "";
        dto.jobTitle = user.getJobTitle() != null ? user.getJobTitle() : "";

        dto.hireDate = user.getHireDate();
        dto.managerId = user.getManagerId();

        return dto;
    }
}
