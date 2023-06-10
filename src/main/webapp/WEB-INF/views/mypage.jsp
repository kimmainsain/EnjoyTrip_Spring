<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ include file="include/head.jsp" %>
</head>
<script>
    var id = '${user.id}';
</script>
<body>
<%@ include file="include/nav.jsp" %>

<div class="container">
    <div style="height: 70px"></div>
    <!-- 여기서부터 진짜 contents 시작 -->
    <div class="page-content">
        <h4 class="card-title">내 정보 확인</h4>
        <div class="m-3">
            <label>닉네임 : </label> <span id="mynickname">${user.nickname}</span>
        </div>
        <%--
        <div class="m-3">
            <label>지역 : </label> <span id="myregion"></span>
        </div>
        <div class="m-3">
            <label>관심사 : </label> <span id="mykeyword"></span>
        </div>
        --%>
    </div>
    <div class="page-content">
        <h4 class="card-title">내 정보 수정</h4>
        <input type="hidden" id="id" value="${loggedUser.id}"/>
        <input type="hidden" name="action" value="userEdit"/>
        <div class="m-3">
            <label>PW</label><input type="password" id="userpw" name="userpw"/>
        </div>
        <div class="m-3">
            <label>닉네임</label><input type="text" id="nickname" name="nickname" value="${user.nickname}"/>
        </div>
        <%--
        <div class="m-3">
            <label>지역&nbsp;&nbsp;&nbsp;</label> <select class="mt-3"
                id="region">
                <option value="0" selected>관심 지역 선택</option>
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
        </div>
        <div class="m-3">
            <label>관심사</label> <select class="mt-3" id="keyword">
                <option value="0" selected>선호 관광지 유형</option>
                <option value="12">관광지</option>
                <option value="14">문화시설</option>
                <option value="15">축제공연행사</option>
                <option value="25">여행코스</option>
                <option value="28">레포츠</option>
                <option value="32">숙박</option>
                <option value="38">쇼핑</option>
                <option value="39">음식점</option>
            </select>
        </div>
        --%>
        <div class="m-3">
            <label>이메일</label> <input type="email" id="email" name="email"
                                      value="${user.email}"/>
        </div>
        <button type="button" id="modify-button">수정</button>
        <button type="button" id="quit-button">회원 탈퇴</button>

    </div>
    <!-- content 끝 -->
</div>


<%@ include file="include/footer.jsp" %>
</body>
<script src="./assets/js/login.js"></script>
<script src="./assets/js/mypage.js"></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
</html>
