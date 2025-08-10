// src/routes/ProtectedRoute.tsx
import React, { useEffect, useState } from "react";
import { Navigate, Outlet } from "react-router-dom";
import api from "../utils/axios";

interface Props {
    /** ADMIN 등 특정 역할이 필요하면 지정 */
    requiredRole?: string;
}

const ProtectedRoute: React.FC<Props> = ({ requiredRole }) => {
    const [allow, setAllow] = useState<boolean | null>(null);

    useEffect(() => {
        const token = sessionStorage.getItem("accessToken");
        if (!token) {
            setAllow(false);
            return;
        }
        api.get("/auth/me")
            .then(res => {
                const role = res.data.data.role;
                setAllow(!requiredRole || role === requiredRole);
            })
            .catch(() => setAllow(false));
    }, [requiredRole]);

    // 검사 중
    if (allow === null) return <div>로딩 중…</div>;
    // 실패 시 로그인으로
    if (!allow) return <Navigate to="/login" replace />;
    // 성공 시 자식 라우트(Outlet) 렌더
    return <Outlet />;
};

export default ProtectedRoute;
