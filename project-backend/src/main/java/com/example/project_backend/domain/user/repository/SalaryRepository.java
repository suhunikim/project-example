package com.example.project_backend.domain.user.repository;

import com.example.project_backend.domain.user.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
}
