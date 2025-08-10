package com.example.project_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"jwt.secret=ThisIsMySuperSecretTestKeyForHS256PleaseMakeItLonger32Bytes",
		"jwt.expiration=3600"
})
class ProjectBackendApplicationTests {

	@Test
	void contextLoads() {
		// 이제 jwt.secret 과 jwt.expiration 값을 올바르게 가지고 실행됩니다.
	}

}