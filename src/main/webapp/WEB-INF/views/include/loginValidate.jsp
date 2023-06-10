<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- Login Session 확인용. login 여부 확인 필요한 페이지에 삽입 --%>
<c:if test="${empty loggedUser }">
	<script>
		alert("비정상적인 접근입니다. 로그인을 먼저 해주세요.");
		location.href = "${root}/main";
	</script>
</c:if>