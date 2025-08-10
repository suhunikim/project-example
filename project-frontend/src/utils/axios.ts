// src/utils/axios.ts
import axios from "axios";

const api = axios.create({
    baseURL: "/api",          // ← 반드시 상대경로 "/api" 로 설정
    timeout: 5000,
    headers: { "Content-Type": "application/json" },
});

// 요청마다 자동으로 토큰 붙이기
api.interceptors.request.use(config => {
    const token = sessionStorage.getItem("accessToken");
    if (token) config.headers.Authorization = `Bearer ${token}`;
    return config;
});

export default api;
