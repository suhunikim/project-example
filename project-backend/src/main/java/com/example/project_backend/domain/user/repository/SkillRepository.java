package com.example.project_backend.domain.user.repository;

import com.example.project_backend.domain.user.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
}
