<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<%@ include file="include/head.jsp" %>
</head>
<script>
    var board_name = '${name}';
</script>
<body>
    <%@ include file="include/nav.jsp" %>

    <div class="container">
        <div style="height: 70px"></div>
        <div id="page-content">
            <h4 class="card-title">글 작성</h4>
            <div class="container">
                <label>제목</label>
                <input type="text" style="width:100%; height:100%;" name="title" id="title"/>
            </div>
            <div class="container">
                <label>내용</label>
                <textarea style="width:100%; height:100%;" name="content" id="content"></textarea>
            </div>
            <button type="button" class="mb-3 me-3" id="write-btn">작성</button>
        </div>
        </form>
    </div>

    <%@ include file="include/footer.jsp" %>
</body>
<script src="/assets/js/login.js"></script>
<script src="/assets/js/write.js"></script>
<script
src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
crossorigin="anonymous"
></script>
</html>