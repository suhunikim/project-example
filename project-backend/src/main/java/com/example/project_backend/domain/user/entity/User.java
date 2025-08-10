package com.example.project_backend.domain.user.entity;

import com.example.project_backend.common.code.UserRole;
import com.example.project_backend.common.code.UserStatus;
import com.example.project_backend.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user_info")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "user_email", nullable = false, unique = true)
    private String userEmail;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "department")
    private String department;

    @Column(name = "position")
    private String position;

    @Column(name = "job_Title")
    private String jobTitle;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "manager_id")
    private Long managerId;
}
