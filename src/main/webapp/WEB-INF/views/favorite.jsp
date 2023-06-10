<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="/include/head.jsp" %>
</head>

    <body>
       <%@ include file="/include/nav.jsp" %>

        <div class="container">
            <div style="height: 70px"></div>
            <!-- 여기서부터 진짜 contents 시작 -->
            <div id="page-content">
                <h4 class="card-title">우리 지역 선호 관광지</h4>
                <label>검색어</label><input type="text" id="keyword" />
                <button id="search-button">찾기</button>
                <div id="map" style="width: 100%; height: 500px"></div>
                <div class="continer" id="result">
                    <div class="row">
                        <table class="w-100 m-auto table table-striped" style="display: none">
                            <thead>
                                <tr>
                                    <th class="w-25">사진</th>
                                    <th class="w-25">이름</th>
                                    <th class="w-50">주소</th>
                                </tr>
                            </thead>
                            <tbody id="board-list"></tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- content 끝 -->
        </div>

        
    <%@ include file="/include/footer.jsp" %>
    </body>
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"
    ></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=93d4a8aea9d2bf96bbadf6fcf40304a0"></script>
    <script src="./assets/js/kakaoSearch.js"></script>
    <script src="./assets/js/login.js"></script>
    <script src="./assets/js/map.js"></script>
    <script src="./assets/js/favorite.js"></script>
</html>
