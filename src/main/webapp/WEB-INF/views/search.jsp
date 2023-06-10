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
            <div id="page-content">
                <h4 class="card-title">관광지 검색</h4>
                <form class="d-flex my-3" onsubmit="return false;" role="search">
                    <select id="search-area" class="form-select me-2">
                        <option value="0" selected>검색 할 지역 선택</option>
                        <option value="1">서울</option>
                        <option value="2">인천</option>
                        <option value="3">대전</option>
                        <option value="4">대구</option>
                        <option value="5">광주</option>
                        <option value="6">부산</option>
                        <option value="7">울산</option>
                        <option value="8">세종특별자치시</option>
                        <option value="31">경기도</option>
                        <option value="32">강원도</option>
                        <option value="33">충청북도</option>
                        <option value="34">충청남도</option>
                        <option value="35">경상북도</option>
                        <option value="36">경상남도</option>
                        <option value="37">전라북도</option>
                        <option value="38">전라남도</option>
                        <option value="39">제주도</option>
                    </select>
                    <select id="search-content-id" class="form-select me-2">
                        <option value="0" selected>관광지 유형</option>
                        <option value="12">관광지</option>
                        <option value="14">문화시설</option>
                        <option value="15">축제공연행사</option>
                        <option value="25">여행코스</option>
                        <option value="28">레포츠</option>
                        <option value="32">숙박</option>
                        <option value="38">쇼핑</option>
                        <option value="39">음식점</option>
                    </select>
                    <input id="search-keyword" class="form-control me-2" type="search" placeholder="검색어" aria-label="검색어" />
                    <button id="btn-search" class="btn btn-outline-success" type="button">검색</button>
                </form>
                <div id="map" style="width: 100%; height: 500px"></div>
                <div class="continer" id="result">
                    <div class="row">
                        <table class="w-100 m-auto table table-striped" style="display: none">
                            <tr>
                                <th class="w-25">사진</th>
                                <th class="w-25">이름</th>
                                <th class="w-50">주소</th>
                            </tr>
                            <tbody id="board-list"></tbody>
                        </table>
                    </div>
                </div>
            </div>
            <!-- content 끝 -->
        </div>

    <%@ include file="include/footer.jsp" %>
    </body>
    <script src="./assets/js/login.js"></script>
    <script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
    <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=93d4a8aea9d2bf96bbadf6fcf40304a0"></script>
    <script src="./assets/js/kakaoSearch.js"></script>
    <script src="./assets/js/map.js"></script>
    <script src="./assets/js/search.js"></script>
</html>
