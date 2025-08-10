import React, {useState} from "react";
import {Link, Navigate, useNavigate} from "react-router-dom";
import axios from "../../utils/axios"; // axios import

import "../../styles/common/default.css"; // css import
import "../../styles/auth/login.css"; // css import

export default function Login() {
    const token = sessionStorage.getItem("accessToken");
    if (token) return <Navigate to="/main" />;

    // navigate 초기화
    const navigate = useNavigate();

    // form 초기화
    const [form, setForm] = useState({
        userEmail: "",
        password: "",
    });

    // 입력 변경 핸들러
    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setForm({...form, [e.target.name]: e.target.value})
    }

    // 서버 에러 메시지 상태
    const [error, setError] = useState("");

    // onSubmit 함수는 폼 제출 시 호출되며, axios를 사용하여 로그인 API를 호출.
    const onSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        setError("");

        try {
            const response = await axios.post("/auth/login", {
                user_email: form.userEmail,
                password: form.password,
            });

            const {accessToken} = response.data.data;
            console.log("로그인 응답 :", response.data);
            if (accessToken) {
                console.log("로그인 응답 :", response.data);
                sessionStorage.setItem("accessToken", accessToken);
                navigate("/");
            } else {
                console.log("로그인 응답 :", response.data);
                navigate("/login?error=missing_token");
            }

        } catch (err: any) {
            const msg = err.response?.data?.message || "로그인에 실패했습니다.";
            setError(msg);
        }
    };

    return (
        <div className="login-container">
            <div className="login-card">
                <div className="login-sections">
                    <section className="login-section email-login">
                        <h2 className="login-title">LOGIN</h2>
                        {error && <div className="login-error">{error}</div>}
                        <form className="login-form" onSubmit={onSubmit}>
                            <input type="email" name="userEmail" placeholder="이메일" className="login-input"
                                   value={form.userEmail} onChange={onChange}/>
                            <input type="password" name="password" placeholder="비밀번호" className="login-input"
                                   value={form.password} onChange={onChange}/>
                            <button type="submit" className="login-button">로그인</button>
                        </form>
                    </section>

                    <section className="login-section social-signup">
                        <div className="divider">또는</div>
                        <a href="http://localhost:8080/oauth2/authorization/google" className="google-button">
                            <img src="https://developers.google.com/identity/images/g-logo.png" alt="Google"/>
                            Google 로그인
                        </a>
                        <p className="signup-text">계정이 없으신가요? <Link to="/signup">회원가입</Link></p>
                    </section>
                </div>
            </div>
        </div>
    );
}
