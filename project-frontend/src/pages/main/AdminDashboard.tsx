// src/pages/main/AdminDashboard.tsx
import '../../styles/main/AdminDashboard.css';

export default function AdminDashboard() {
    return (
        <>
            {/* 1) 상단 카드 표시 */}
            <div className="row-top">
                <div className="card green">
                    진행 중 프로젝트<br/><strong>7개</strong>
                </div>
                <div className="card yellow">
                    최근 완료 프로젝트<br/><strong>2개</strong>
                </div>
                <div className="card red">
                    SM 프로젝트<br/><strong>3개</strong>
                </div>
            </div>

            {/* 2) 하단 그리드 표시 */}
            <div className="row-bottom">
                {/* 좌측: 테이블 두 개 */}
                <div className="bottom-left">
                    <section className="employee-status">
                        <h3>직원 현황</h3>
                        <table>
                            <thead>
                            <tr>
                                <th>이름</th>
                                <th>직급</th>
                                <th>나이</th>
                                <th>상태</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>김철수</td>
                                <td>과장</td>
                                <td>42</td>
                                <td><span className="dot green" />투입중</td>
                            </tr>
                            <tr>
                                <td>이영희</td>
                                <td>대리</td>
                                <td>34</td>
                                <td><span className="dot orange" />대기중</td>
                            </tr>
                            </tbody>
                        </table>
                    </section>

                    <section className="part-status">
                        <h3>파트 별 투입 현황</h3>
                        <table>
                            <thead>
                            <tr>
                                <th>파트명</th>
                                <th>총 인원</th>
                                <th>투입 중</th>
                                <th>대기 중</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>SI1</td>
                                <td>3</td>
                                <td>2</td>
                                <td>1</td>
                            </tr>
                            <tr>
                                <td>SM1</td>
                                <td>3</td>
                                <td>1</td>
                                <td>2</td>
                            </tr>
                            </tbody>
                        </table>
                    </section>
                </div>

                {/* 우측: 리스트와 텍스트 */}
                <div className="bottom-right">
                    <section className="tech-stack">
                        <h3>기술 스택 분포</h3>
                        <ul>
                            <li>Python — 40%</li>
                            <li>JavaScript — 35%</li>
                            <li>Java — 25%</li>
                        </ul>
                    </section>

                    <section className="project-info">
                        <h3>프로젝트 현황</h3>
                        <ul>
                            <li>SI 프로젝트 A — 진행 중</li>
                            <li>SM 프로젝트 B — 완료</li>
                        </ul>
                    </section>

                    <section className="team-ratio">
                        <h3>팀/파트 구성 비율</h3>
                        <p>SI기술팀: 60%, SM기술팀: 40%</p>
                    </section>

                    <section className="top-participants">
                        <h3>TOP 참여자</h3>
                        <ol>
                            <li>김철수 — 7회</li>
                            <li>이영희 — 5회</li>
                            <li>박민수 — 3회</li>
                        </ol>
                    </section>
                </div>
            </div>
        </>
    );
}
