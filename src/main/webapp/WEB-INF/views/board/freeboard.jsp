<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- navbar -->
<jsp:include page="../commons/global_nav.jsp"></jsp:include>

<!-- 게시글 목록.... -->

<div class="container mt-5">
	<div class="row">
		<div class="col-2"></div>
		<div class="col">
			<h1>자유게시판</h1>
		
			<div class="row"> <!-- 테이블 -->
				<div class="col">
					<table class="table table-hover">
						<thead>
							<tr><td>글번호</td><td>제목</td><td>작성자</td><td>조회수</td><td>작성일</td></tr>
						</thead>
						<tbody>
							
						</tbody>
					
					</table>
				</div>
			</div>
			<div class="row mt-3"> <!-- 버튼들... -->
				<div class="col-8"> <!-- 페이지 버튼 -->
					<nav aria-label="Page navigation example">
					   <ul class="pagination">
					  
					    <li class="page-item<c:if test="${beginPage-1 <= 0}"> disabled</c:if>"><a class="page-link" href="./main_page.do?currPage=${beginPage-1 }&search_word=${param.search_word}">이전</a></li>
					  	<c:forEach begin="${beginPage }" end="${endPage }" var="i">
					  		<li class="page-item<c:if test="${currPage==i}"> active</c:if>"><a class="page-link" href="./main_page.do?currPage=${i}&search_word=${param.search_word}">${i}</a></li>
					  	</c:forEach>
					    <li class="page-item<c:if test="${endPage+1 >= (totalCount-1)/10+1 }"> disabled</c:if>"><a class="page-link" href="./main_page.do?currPage=${endPage+1 }&search_word=${param.search_word}">다음</a></li>
					  <!-- 
					    <li class="page-item disabled"><a class="page-link" href="#">이전</a></li>
					    <li class="page-item active"><a class="page-link" href="#">4</a></li>
				     -->
					  </ul>

					</nav>
				</div>
				<div class="col">
				<c:if test="${!empty sessionUser }"> 
					<a class="btn btn-primary btn-block" href="./write_content_page.do">글 쓰기</a>
					</c:if>
				</div>
			</div>		
		</div>
		<div class="col-2"></div>
	</div>
</div>
<div>
<form action="./freeboard.do" method="get">
			<div class="row my-3">
				<div class="col"></div>
				<div class="col-4">
					<input name="search_word" type="text" class="form-control">
				</div>
				<div class="col-2">
					<input type="submit" class="btn btn-primary btn-block" value="검색" style="width:200px;">
				</div>
			</div>
		</form>
</div>
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</body>
</html>