import {useState} from "react";
import "../../styles/auth/signup.css";
import {useNavigate} from "react-router-dom";
import axios from "../../utils/axios";

export default function Signup() {
    // navigete 초기화
    const navigate = useNavigate();
    
    // form 초기화
    const [form, setForm] = useState({
        userEmail: "",
        password: "",
        userName: "",
        phoneNumber: "",
    });

    // 서버 에러 메시지 상태
    const [error, setError] = useState("");

    // onChange 함수는 input의 name 속성을 사용하여 form 상태를 업데이트.
    const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setForm({...form, [e.target.name]: e.target.value})
    };

    // onSubmit 함수는 폼 제출 시 호출되며, axios를 사용하여 회원가입 API를 호출.
    const onSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        console.log("폼 데이터 :", form);

        // 이전 error 상태 초기화
        setError("");

        try{
            // 회원가입 API 호출
            await axios.post('/auth/signup', {
                user_email: form.userEmail,
                password: form.password,
                user_name: form.userName,
                phone_number: form.phoneNumber,
            });

            alert('회원가입이 완료되었습니다!\n로그인 페이지로 이동합니다.');

            // 로그인 페이지로 이동
            navigate("/login");
        } catch (err: any) {
            const msg = err.response?.data?.message || "사용자 등록에 실패했습니다.";
            setError(msg);
        }
    }

    return (
        <div className="signup-container">
            <div className="signup-card">
                <h2 className="signup-title">사용자 등록</h2>
                <form id="signupForm" className="signup-form" onSubmit={onSubmit}>
                    <div className="signup-sections">
                        <div className="signup-section">
                            <label>
                                이메일 {error && <div className="signup-error">{error}</div>}
                                <input className="signup-input" name="userEmail" type="email"
                                       value={form.userEmail} onChange={onChange} required/>
                            </label>
                            <label>
                                비밀번호
                                <input className="signup-input" name="password" type="password"
                                       value={form.password} onChange={onChange} minLength={6} required/>
                            </label>
                        </div>
                        <div className="signup-section">
                            <label>
                                이름
                                <input className="signup-input" name="userName" type="text"
                                       value={form.userName} onChange={onChange} required/>
                            </label>
                            <label>
                                전화번호
                                <input className="signup-input" name="phoneNumber" type="tel" pattern="\d{2,3}-\d{3,4}-\d{4}"
                                       value={form.phoneNumber} onChange={onChange} placeholder="010-1234-5678" required/>
                            </label>
                        </div>
                    </div>
                    <button type="submit" className="signup-button">
                        등록
                    </button>
                </form>
            </div>
        </div>
    );
}
