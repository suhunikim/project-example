package com.example.project_backend.domain.user.repository;

import com.example.project_backend.domain.user.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
