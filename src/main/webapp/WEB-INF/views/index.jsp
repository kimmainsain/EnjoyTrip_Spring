<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="include/head.jsp" %>
</head>

    <body>
		<%@ include file="include/nav.jsp" %>
        <div class="container">
            <div style="height: 70px"></div>
            <!-- 여기서부터 진짜 contents 시작 -->
            <div id="page-content">
                <div class="container-fluid p-3 bg-info" style="height: 300px">
                    <h4 class="card-title">서비스 소개</h4>
                    <p>당신을 위한 여행 정보 커뮤니티</p>
                </div>
                <%--
                <div class="container-fluid p-3 bg-secondary" style="height: 300px">
                    <h4 class="card-title">당신을 위한 추천 여행지</h4>
                    <p>회원가입 시 입력한 지역과 관심사 기반 여행지 추천</p>
                    <a href="/main?action=favorite">바로가기</a>
                </div>--%>
                <div class="container-fluid p-3 bg-success" style="height: 300px">
                    <h4 class="card-title">여행 정보 공유 게시판</h4>
                    <p>자유로운 소통 게시판</p>
                    <a href="/board/free">바로가기</a>
                </div>
            </div>
            <!-- content 끝 -->
        </div>

        
    <%@ include file="include/footer.jsp" %>
    </body>
    <script src="./assets/js/login.js"></script>
    <script src="./assets/js/main.js"></script>
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"
    ></script>
</html>
