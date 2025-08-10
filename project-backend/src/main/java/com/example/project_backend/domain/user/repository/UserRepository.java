package com.example.project_backend.domain.user.repository;

import com.example.project_backend.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /// 아래와 같은 간단한 쿼리 메서드는 명시적으로 선언하지 않아도 됨.
    /// 복잡한 쿼리, JQPL, 네이티브 SQL 쿼리를 작성하는 경우에 많이 선언 함.

    // 이메일로 사용자 찾기 (Optional: 사용자가 없을 수도 있으므로)
    Optional<User> findByUserEmail(String userEmail);

    // ID로 사용자 찾기
    Optional<User> findByUserId(Long userId);

    // 이름으로 사용자 찾기
    List<User> findByUserName(String userName);

    // 이메일 중복 확인 (존재 여부 확인)
    boolean existsByUserEmail(String userEmail);
}
