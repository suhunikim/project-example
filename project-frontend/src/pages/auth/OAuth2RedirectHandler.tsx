import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const OAuth2RedirectHandler = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const token = new URL(window.location.href).searchParams.get("token");

        if (token) {
            sessionStorage.setItem("accessToken", token);
            navigate("/main");
        } else {
            navigate("/login?error=missing_token");
        }
    }, [navigate]);

    return <p>로그인 중입니다...</p>;
};

export default OAuth2RedirectHandler;
