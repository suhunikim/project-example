# project-frontend/Dockerfile

# 1. 베이스 이미지 정의
ARG NODE_ENV=production
FROM node:18-slim AS base
WORKDIR /app
COPY package*.json ./
RUN npm install

# 2. 개발 모드 스테이지
FROM base AS dev
# dev 모드 환경 변수
ENV NODE_ENV=development
# 전체 소스 복사 (호스트 볼륨 바인드 시 덮어쓰기 가능)
COPY . .
# Vite(또는 CRA) 개발 서버 실행
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]

# 3. 프로덕션 빌드 스테이지
FROM base AS build
COPY . .
RUN npm run build

# 4. 런타임(Nginx) 스테이지
FROM nginx:alpine AS prod
COPY nginx/default.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
