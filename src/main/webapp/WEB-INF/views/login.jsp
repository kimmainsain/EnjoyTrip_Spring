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
		<h3 style="color: #3396f4">WELCOME!!</h3>
		<div class="mt-3">
			<input type="text" name="userid" id="userid" autocapitalize="none"
				   placeholder="아이디" />
		</div>
		<div class="mt-3">
			<input type="password" name="userpw" id="userpw" placeholder="비밀번호" />
		</div>
		<button type="button" class="mt-3" id="login-button">로그인</button>
		<button type="button"  class="mt-3" id="find-button">비밀번호 찾기</button>
	</div>
</div>
</body>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>
<script src="./assets/js/login.js"></script>
</html>
