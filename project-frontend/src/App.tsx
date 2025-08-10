// src/App.tsx
import { BrowserRouter, Routes, Route } from "react-router-dom";
import PublicRoute    from "./routes/PublicRoute";
import ProtectedRoute from "./routes/ProtectedRoute";
import Layout         from "./layout/Layout";

import Signup           from "./pages/auth/Signup";
import Login            from "./pages/auth/Login";
import Main             from "./pages/main/Main";
import AdminDashboard   from "./pages/main/AdminDashboard";
import UserDashboard    from "./pages/main/UserDashboard";
import NotFound         from "./pages/error/NotFound";

function App() {
    return (
        <BrowserRouter>
            <Routes>
                {/* 비로그인 페이지 (PublicRoute로 감싸서, 로그인 상태면 리다이렉트) */}
                <Route
                    path="/signup"
                    element={<PublicRoute><Signup/></PublicRoute>}
                />
                <Route
                    path="/login"
                    element={<PublicRoute><Login/></PublicRoute>}
                />

                {/* 최초 진입점 (토큰만 검사, Main이 role 분기) */}
                <Route element={<ProtectedRoute />}>
                    <Route path="/" element={<Main />} />
                </Route>

                {/* 관리자 전용: 인증+ADMIN 권한 검사 → Layout(isAdmin) → AdminDashboard */}
                <Route element={<ProtectedRoute requiredRole="ADMIN" />}>
                    <Route element={<Layout isAdmin />}>
                        <Route path="/admin" element={<AdminDashboard />} />
                    </Route>
                </Route>

                {/* 일반 사용자 전용: 인증만 검사 → Layout → UserDashboard */}
                <Route element={<ProtectedRoute />}>
                    <Route element={<Layout />}>
                        <Route path="/user" element={<UserDashboard />} />
                    </Route>
                </Route>

                {/* 그 외: 404 */}
                <Route path="*" element={<NotFound />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;
