<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
</head>

<body>
	<div class="container">

		<div class="row mt-5">
			<div class="col mb-3">
				<h1>해당 부서페이지에 접근 권한이 없습니다.</h1>
			</div>
		</div>

		<div class="row mt-5">
			<div class="col mb-3">
				<c:if test="${sessionUser.deptTypeVo.dept_type_no==1}">
					<a
						href="${pageContext.request.contextPath }/management/main_page.do">본인
						부서 페이지로 이동</a>
				</c:if>
				<c:if test="${sessionUser.deptTypeVo.dept_type_no==2}">
					<a
						href="${pageContext.request.contextPath }/logistics/main_page.do">본인
						부서 페이지로 이동</a>
				</c:if>
				<c:if test="${sessionUser.deptTypeVo.dept_type_no==3}">
					<a href="${pageContext.request.contextPath }/sales/main_page.do">본인
						부서 페이지로 이동</a>
				</c:if>
				<c:if test="${sessionUser.deptTypeVo.dept_type_no==4}">
					<a
						href="${pageContext.request.contextPath }/production/main_page.do">본인
						부서 페이지로 이동</a>
				</c:if>
				
				<c:if test="${empty sessionUser}">
					<a
						href="${pageContext.request.contextPath }/employee/login_page.do">
						로그인페이지로 이동</a>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>