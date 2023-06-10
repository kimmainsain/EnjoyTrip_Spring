<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
	<%@ include file="include/head.jsp" %>
</head>

<script>
	var board_name = `${name}`;
	var article_no = ${article.articleNo}
</script>
<body>
    <%@ include file="include/nav.jsp" %>

    <div class="container " >
        <div style="height: 70px"></div>
        <!-- 여기서부터 진짜 contents 시작 -->
        <div id="page-content">
            <c:if test="${not empty article }">
            
	           <div class="row">
	           		 <h4>글 내용 보기</h4>
	           </div>
           
	     		<div>
					<h2 class="title" style="display: inline-block">제목 : ${article.title }</h2><br/>
					<h3 style="display: inline-block">작성자 : ${article.nickName}</h3><br/>
	        		${article.content }
	      		</div>
        	</c:if>
       	 
        	<c:if test="${empty article }">
	        	<h4>글이 존재하지 않습니다</h4>
	        	<a href="/">메인으로 이동</a>
        	</c:if>
			<c:if test="${not empty editable}">
            	<div class="row align-items-end"> 
	            <div class="col-4 ">
	           <button type="button" class="mt-3" id="modify-btn">수정</button>
	           <button type="button" class="mt-3" id="del-btn">삭제</button>
			</c:if>
	            </div>
            </div>
        </div>
        
    </div>
    <%@ include file="include/footer.jsp" %>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
crossorigin="anonymous"
></script>
<script src="/assets/js/login.js"></script>
<script src="/assets/js/detail.js"></script>

</html>