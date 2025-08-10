// src/layout/Layout.tsx

import { useState, useEffect } from 'react';
import { NavLink, Outlet, useNavigate } from 'react-router-dom';
import axios from '../utils/axios';                       // API 호출용 axios 인스턴스
import '../styles/layout/Layout.css';                   // 레이아웃 전용 CSS

// Layout 컴포넌트에 넘겨줄 props 타입 정의
interface LayoutProps {
    isAdmin?: boolean;  // 관리자용 레이아웃인지 구분
}

// Layout 컴포넌트: 사이드바 + 메인 콘텐츠 영역을 감싸는 공통 레이아웃
// isAdmin이 LayoutProps로 전달되면 관리자 레이아웃으로 사용
const Layout = ({ isAdmin }: LayoutProps) => {
    const navigate = useNavigate();                      // 페이지 이동 함수
    const [menuOpen, setMenuOpen] = useState(false);     // 모바일 메뉴 열림/닫힘 상태
    const [userName, setUserName] = useState('');        // 로그인된 사용자 이름

    // 1) 컴포넌트가 처음 렌더링될 때 한 번 실행
    useEffect(() => {
        axios.get('/auth/me')                                // 내 정보 조회 API 호출
            .then(res => {
                setUserName(res.data.data.userName);           // 사용자 이름 상태에 저장
            })
            .catch(() => {
                // 토큰 없거나 만료되면 로그인 페이지로 이동
                navigate('/login', { replace: true });
            });
    }, [navigate]);

    // 2) 메뉴 항목 정의
    const commonMenu = [
        { label: '메인', path: '/' },
        { label: '내 정보', path: '/profile' },
    ];
    const adminMenu = [
        { label: '대시보드', path: '/admin' },
        { label: '구성원 관리', path: '/admin/users' },
        {
            label: '설정',
            children: [                                // 하위 메뉴(서브메뉴)
                { label: '메뉴 관리', path: '/admin/settings/menu' },
                { label: '권한 관리', path: '/admin/settings/roles' },
            ],
        },
    ];
    // isAdmin이 true면 관리자 메뉴까지, 아니면 공통 메뉴만 사용
    const menuItems = isAdmin ? [...commonMenu, ...adminMenu] : commonMenu;

    return (
        <div className="dashboard-container">
            {/* ===== 모바일 햄버거 버튼 (1023px 이하 화면) ===== */}
            <div className="mobile-header">
                <button
                    className="hamburger"
                    onClick={() => setMenuOpen(open => !open)}
                >
                    ☰
                </button>
            </div>

            {/* ===== 사이드바 시작 ===== */}
            {/* menuOpen이 true면 'open' 클래스 추가해서 보이도록 */}
            <aside className={`sidebar ${menuOpen ? 'open' : ''}`}>
                {/* 로그인된 사용자 이름 표시 */}
                <div className="user-info">
                    {userName && `${userName}님 로그인 중`}
                </div>

                {/* 메뉴 제목 */}
                <h2 className="menu-title">
                    {isAdmin ? '관리자 메뉴' : '메뉴'}
                </h2>

                {/* 메뉴 리스트 */}
                <nav>
                    <ul className="menu">
                        {menuItems.map(item => (
                            <li key={item.label}>
                                {/* 링크가 있으면 NavLink, 없으면 단순 텍스트 */}
                                {item.path
                                    ? <NavLink to={item.path}>{item.label}</NavLink>
                                    : <span>{item.label}</span>
                                }

                                {/* 하위 메뉴가 있으면 서브메뉴 렌더링 */}
                                {'children' in item && item.children && (
                                    <ul className="submenu">
                                        {item.children.map(sub => (
                                            <li key={sub.label}>
                                                <NavLink to={sub.path!}>{sub.label}</NavLink>
                                            </li>
                                        ))}
                                    </ul>
                                )}
                            </li>
                        ))}
                    </ul>
                </nav>
            </aside>
            {/* ===== 사이드바 끝 ===== */}

            {/* ===== 메인 콘텐츠 영역 ===== */}
            {/* Outlet: App.tsx에서 설정된 자식 Route 컴포넌트가 이 위치에 렌더링 */}
            <main className="dashboard-main">
                <Outlet />
            </main>
        </div>
    );
};

export default Layout;
