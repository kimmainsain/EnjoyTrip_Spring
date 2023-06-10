<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="include/head.jsp"%>
</head>
<script>
	var board_name = `${name}`;
</script>
<body>
	<%@ include file="include/nav.jsp"%>

	<div class="container">
		<div style="height: 70px"></div>
		<!-- 여기서부터 진짜 contents 시작 -->
		<div id="page-content">
			<h4 class="card-title">게시판</h4>
			<div class="row">
				<div class="mt-3">
					<input name="keyword" id="keyword" placeholder="검색어" />
					<button type="button" class="mt-3" id="keyword-button" onclick="keywordSearch()">검색</button>
				</div>
			</div>
			<div class="row">
				<table class="w-100 m-auto table table-striped">
					<thead>
						<tr>
							<th class="w-25">글 번호</th>
							<th class="w-25">작성자</th>
							<th class="w-50">제목</th>
						</tr>
					</thead>
					<tbody id="board-list">
						
					</tbody>
				</table>
			</div>
			
			
		</div>
		<c:if test="${not empty user }">
			<%
				// Todo : 수정
            	Object adminReq = request.getAttribute("adminReq");
            	boolean admin = (adminReq == null ? false : (boolean)adminReq);
            	Object isAdmin = request.getSession().getAttribute("admin");
            	boolean admin2 = (isAdmin == null ? false : (boolean)isAdmin);	
            	if(!admin || admin2){
            		%>
			<a class="mb-r me-3" href="/board/${name}/write">글쓰기</a>
			<%
            	}
            	
            %>

		</c:if>

		<div id="page-nav">

		</div>

		<!-- content 끝 -->
	</div>
	<%@ include file="include/footer.jsp"%>
</body>
<script	src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
	crossorigin="anonymous"></script>
<script src="/assets/js/login.js"></script>
<script src="/assets/js/board.js"></script>
</html>
