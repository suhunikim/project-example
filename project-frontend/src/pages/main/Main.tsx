import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "../../utils/axios";

const Main = () => {
    const navigate = useNavigate();

    useEffect(() => {
        const token = sessionStorage.getItem("accessToken");
        if (!token) {
            // 토큰 없으면 로그인 페이지로 이동
            navigate("/login");
            return;
        }

        axios
            .get("/auth/me", {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            })
            .then((res) => {
                const { role } = res.data.data;
                // 관리자면 /admin, 아니면 /user 로 이동
                if (role === "ADMIN") {
                    navigate("/admin");
                } else {
                    navigate("/user");
                }
            })
            .catch((err) => {
                console.error(err);
                sessionStorage.removeItem("accessToken");
                navigate("/login");
            });
    }, [navigate]);

    // 권한 체크 후 리다이렉션만 하기 때문에 로딩 표시만 해도 무방합니다.
    return <div>잠시만 기다려주세요...</div>;
};

export default Main;
