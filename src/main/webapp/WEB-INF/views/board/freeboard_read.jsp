<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<% pageContext.setAttribute("replaceChar","\n"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>

<!-- navbar -->
<jsp:include page="../commons/global_nav.jsp"></jsp:include>

<!-- 글 읽기 페이지 -->

<div class="container">
	<div class="row">
		<table class="table table-striped" style="text-align:center; border: 1px solid #dddddd">
			<thead>
				<tr bgcolor="orange" >
					<td colspan="3" >글 제목 : 안녕하세요</td>
					
				</tr>
			</thead>
			<tbody>
				<tr>
					<td col-1>작성자 : 최병민</td>
					<td col-1>작성일 : 2020-06-01</td>
					<td col-1>조회 : 40</td>
				</tr>
				<tr>
					<td> 내용 </td>
					<td>안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요<br>
						안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요<br>
						안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요<br>
						안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요<br>
						안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요안녕하세요<br>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	
	

</div>

		<div class="container mt-5">
			<div class="row mt-1"><!-- 내용 -->
					<div class="col-2">
						<a href="${pageContext.request.contextPath}/board/main_page.do">목록으로</a>
					</div>
					<c:if test="${!empty sessionUser && sessionUser.member_no == aaaa.memberVo.member_no}">
					<div class="col-2">
						<a href="${pageContext.request.contextPath}/board/delete_content_process.do?board_no=${aaaa.boardVo.board_no}">삭제</a>
					</div>
					<div class="col-2">
						<a href="${pageContext.request.contextPath}/board/update_content_page.do?board_no=${aaaa.boardVo.board_no}">수정</a>
					</div>
					</c:if>
			</div>
		</div>	


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</body>
</html>