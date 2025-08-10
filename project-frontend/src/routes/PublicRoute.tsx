import { Navigate } from "react-router-dom";
import {JSX} from "react";

interface Props {
    children: JSX.Element;
}

// 로그인 된 사용자는 로그인 페이지에 접근할 수 없고 메인 페이지로 리다이렉트 됩니다.
const PublicRoute = ({ children }: Props) => {
    const token = sessionStorage.getItem("accessToken");

    // ✅ accessToken이 있으면 메인 페이지로 리다이렉트합니다.
    if (token) {
        return <Navigate to="/main" />;
    }
    return children;
}

export default PublicRoute;