<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <!-- 상단 navbar start -->
        <nav class="navbar navbar-expand-lg navbar-light bg-light shadow fixed-top">
            <div class="container">
                <a class="navbar-brand text-primary fw-bold" href="/"> TRIP </a>
                <button
                    class="navbar-toggler"
                    type="button"
                    data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent"
                    aria-expanded="false"
                    aria-label="Toggle navigation"
                >
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/search">관광지 찾기</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/board/free">자유게시판</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/board/notice">공지사항</a>
                        </li>
                    </ul>
                    <!-- 로그인 전 -->
				<c:if test="${empty user}">
                    <ul class="navbar-nav mb-2 me-2 mb-lg-0" id="before-login">
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/signup">회원가입</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/login">로그인</a>
                        </li>
                    </ul>
				</c:if>
				<c:if test="${not empty user }">
                    <!-- 로그인 후 -->
                    <ul class="navbar-nav mb-2 me-2 mb-lg-0 " id="after-login">
                        <li class="nav-item">
                            <button class="nav-link border-0 bg-transparent" aria-current="page" id="logout-button">로그아웃</button>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="/mypage">내정보수정</a>
                        </li>
				<c:if test="${not empty admin }">
                        <li class="nav-item" id="isadmin">
                            <a class="nav-link" aria-current="page" href="/admin">관리자</a>
                        </li>
				</c:if>
                    </ul>
				</c:if>
                </div>
            </div>
        </nav>
        <!-- 상단 navbar end -->