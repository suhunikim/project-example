package com.example.project_backend.domain.user.repository;

import com.example.project_backend.domain.user.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
}
