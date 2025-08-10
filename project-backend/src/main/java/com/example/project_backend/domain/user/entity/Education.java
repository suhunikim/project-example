package com.example.project_backend.domain.user.entity;

import com.example.project_backend.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_education")
public class Education extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User 엔티티와의 연관 관계

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "degree")
    private String degree;

    @Column(name = "major")
    private String major;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "gpa", precision = 3, scale = 2)
    private BigDecimal gpa; // 학점 (3.25와 같이 소수점 둘째 자리까지)
}