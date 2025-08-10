/// <reference types="vite/client" />
import { defineConfig, loadEnv } from 'vite'
import react from '@vitejs/plugin-react'

/**
 * Vite config
 *
 * mode 에 따라 .env, .env.development, .env.production, docker-compose.override.yml 의
 * VITE_DOCKER 값을 로드.
 */
export default defineConfig(({ mode }) => {
    // env 파일과 process.env 를 병합해서 가져옵니다.
    const env = loadEnv(mode, process.cwd(), '')

    // override.yml 또는 .env 에서 VITE_DOCKER=true 로 설정한 경우
    const isDocker = env.VITE_DOCKER === 'true'

    return {
        plugins: [react()],
        server: {
            port: isDocker ? 3001 : 3000,
            proxy: {
                '/api': {
                    target: isDocker
                        ? 'http://host.docker.internal:8080' // 도커 컨테이너→로컬 백엔드
                        : 'http://localhost:8080',           // 로컬 npm run dev
                    changeOrigin: true,
                },
            },
        },
        define: {
            // 코드 안에서 import.meta.env.VITE_API_BASE_URL 을 사용할 수 있게
            'process.env': {
                VITE_API_BASE_URL: JSON.stringify(env.VITE_API_BASE_URL),
            },
        },
    }
})
