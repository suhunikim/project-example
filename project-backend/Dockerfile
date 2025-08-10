# project-backend/Dockerfile

# --- Build Stage ---
# OpenJDK 17 이미지를 기반으로 빌드 스테이지 시작 (필요에 따라 버전 변경)
# AS build: 이 스테이지에 "build"라는 별칭(alias)을 지정
FROM openjdk:17-jdk-slim AS build

# 컨테이너 내 작업 디렉토리 설정
WORKDIR /app

# 호스트의 현재 디렉토리(project-backend)의 모든 파일/폴더를 컨테이너의 /app 디렉토리로 복사
COPY . .

# Gradle을 사용하여 애플리케이션 빌드
# --mount=type=cache,target=/root/.gradle: Gradle 캐시를 사용하여 빌드 속도 향상 (Docker BuildKit 기능)
# ./gradlew clean build: Gradle Wrapper를 사용하여 프로젝트 정리(clean) 후 빌드(build)
# -x test: 테스트는 실행하지 않고 빌드
RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build -x test

# --- Runtime Stage ---
# 빌드 스테이지와 동일한 OpenJDK 17 슬림 버전 이미지를 기반으로 런타임 스테이지 시작
FROM openjdk:17-jdk-slim

# 컨테이너 내 작업 디렉토리 설정
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일(/app/build/libs/*.jar)을
# 런타임 이미지의 /app 디렉토리에 app.jar라는 이름으로 복사
COPY --from=build /app/build/libs/*.jar app.jar

# 컨테이너 실행 시 실행할 명령어 정의
# java -jar app.jar: app.jar 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

# (선택 사항) 컨테이너가 노출할 포트 지정 (docker-compose.yml에서 설정하는 것이 일반적)
# EXPOSE 8080