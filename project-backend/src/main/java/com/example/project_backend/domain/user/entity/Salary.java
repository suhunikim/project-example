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
@Table(name = "user_salary")
public class Salary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // 일단, 단방향 관계로 설정
    @JoinColumn(name = "user_id", nullable = false) // 외래 키 컬럼 지정
    private User user;

    @Column(name = "salary", nullable = false, precision = 10, scale = 2)
    private BigDecimal salary;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "position_year", nullable = false)
    private int positionYear;
}