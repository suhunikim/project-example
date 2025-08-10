package com.example.project_backend.domain.user.entity;

import com.example.project_backend.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user_skill")
public class Skill extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // User 엔티티와의 연관 관계

    @Column(name = "skill_name", nullable = false)
    private String skillName;

    @Column(name = "proficiency")
    private String proficiency; // 숙련도 (상, 중, 하)
}