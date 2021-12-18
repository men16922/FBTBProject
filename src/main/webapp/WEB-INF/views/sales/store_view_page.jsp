<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../commons/global_nav.jsp"></jsp:include>

<div class="container ml-20">
	<div class="row">
	<div class="col">
	<jsp:include page="../commons/sales_nav.jsp"></jsp:include>
	</div>
	</div>

<div style="margin-top:50px"></div>
<div style="margin-left:100px; margin-right:200px; width:200px; font-size:30px;">입고조회</div>
<div class="search mt-5">

	<div class="row">
	<div class="col">
	<ul class="list" >입고번호</ul>
	</div>
	<div class="col">
	<input placeholder="입고번호를 입력해주세요." name="store_in_item_no" type="text" class="list2" size=40>
	</div>
	<div class="col-3">
	</div>
	</div>
	
	<div class="row">
	<div class="col">
	<ul class="list" >제품명</ul>
	</div>
	<div class="col">
	<input placeholder="제품명을 입력해주세요." name="product_name" type="text" class="list2" size=40>
	</div>
	<div class="col-3">
	</div>
	</div>
	
	
	<div class="row">
	<div class="col">
	<ul class="list" >입고날짜</ul>
	</div>
	<div class="col">
	<input placeholder="시작일" name="in_start_date" type="text" class="list2" size=14">
	</div>
	<div class="col">
	<input placeholder="마지막일" name="in_final_date" type="text" class="list2" size=14>
	</div>
	<div class="col-3">
	</div>
	</div>
	
	
	<div class="row">
	<div class="col">
	<ul class="list" >처리사원</ul>
	</div>
	<div class="col">
	<input placeholder="사원이름을 입력해주세요." name="employee_name" type="text" class="list2" size=40>
	</div>
	<div class="col-3">
	<input type="submit" value="검색" class="btn btn-primary btn-block" style="margin-top:30px;">
	</div>
	</div>

</div>

<div class="sort mt-1">
<div class="row">
	<div class="col">
		<ul class="sortlist">정렬</ul>
	</div>
	<div class="col">
		<select class="sortlist" name=sort>
			<option value=1 selected>입고번호순
			<option value=2>제품번호순
			<option value=3>입고수량순
		</select>
	</div>
</div>
</div>
	<div class="row"> <!-- 테이블 -->
				<div class="col">
					<table class="table table-hover">
						<thead>
							<tr>
							<td>입고번호</td><td>제품번호</td><td>제품이름</td><td>입고수량</td>
							<td>제품가격</td><td>입고일자</td><td>입고처리사원</td>
							</tr>
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

				</div>
			</div>	
</div>




<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>