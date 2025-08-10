package com.example.project_backend.common.code;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserStatus {

    ACTIVE("활성"),
    INACTIVE("비활성"),
    BLOCKED("차단");
    
    private final String description;

    UserStatus(String description) {
        this.description = description;
    }
}
