package com.example.project_backend.domain.user.entity;

import com.example.project_backend.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_project")
public class Project extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User 엔티티와의 연관 관계

    @Column(name = "project_name", nullable = false)
    private String projectName;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "role")
    private String role; // 프로젝트 내 역할

    @Column(name = "skills")
    private String skills; // 사용 기술 (쉼표로 구분된 문자열, JSON 등)

    @Column(name = "url")
    private String url; // 프로젝트 URL
}