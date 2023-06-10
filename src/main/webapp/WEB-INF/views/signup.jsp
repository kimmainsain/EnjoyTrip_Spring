<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="include/head.jsp"%>
</head>

<body>
	<div class="row text-center" style="width: 100vw; height: 100vh">
		<div class="m-auto text-center">
			<h3>signup</h3>
			<div class="mt-3">
				<label>아이디</label><input type="text" name="userid" id="userid" />
			</div>
			<div class="mt-3">
				<label>패스워드</label><input type="password" name="userpw"  id="userpw" />
			</div>
			<div class="mt-3">
				<label>닉네임</label><input type="text" name="nickname"  id="nickname" />
			</div>
			<%--
			<select class="mt-3" id="region">
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
			</select> <select class="mt-3" id="keyword">
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
		--%>
			<div class="mt-3">
				<label>이메일</label><input type="email"  name="email"  id="email" />
			</div>
			<button type="button" class="mt-3" id="signup-button">회원가입</button>
		</div>
	</div>
</body>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script src="./assets/js/signup.js"></script>
<script src="./assets/js/login.js"></script>
</html>
