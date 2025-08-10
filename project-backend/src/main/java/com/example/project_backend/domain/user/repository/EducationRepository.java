package com.example.project_backend.domain.user.repository;

import com.example.project_backend.domain.user.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {
}
