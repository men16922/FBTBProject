<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="row">
	<div class="col">
		<nav class="navbar navbar-expand-lg navbar-light"
			style="background-color: #6c737e;">
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ml-auto mr-auto">

					<li class="nav-item dropdown ml-5 mr-5 pl-3 pr-3">
						<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false" style="color: white"> 발주관리 </a>
						<div class="dropdown-menu" aria-labelledby="navbarDropdown" style="background-color: #e8ecf1">
							<a class="dropdown-item" href="${pageContext.request.contextPath }/logistics/read_list_order_page.do">발주조회</a>
							<a class="dropdown-item" href="${pageContext.request.contextPath }/logistics/first_check_page.do">1차검토</a>
							
							<c:if test="${sessionUser.employeeRankVo.rank_no <= 5 }">
							<a class="dropdown-item" href="${pageContext.request.contextPath }/logistics/last_check_page.do">출고요청</a>
							<a class="dropdown-item" href="${pageContext.request.contextPath }/logistics/reselect_factory_page.do">공장재선택</a>
							</c:if>
							
							<a class="dropdown-item" href="${pageContext.request.contextPath }/logistics/delete_order_page.do">발주반려</a>

						</div>
					</li>
					
<!-- 					
					<li class="nav-item dropdown ml-5 mr-5 pl-3 pr-3">
					<a
						class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
						role="button" data-toggle="dropdown" aria-haspopup="true"
						aria-expanded="false" style="color: white"> 공장조회 </a>
						
						<div class="dropdown-menu" aria-labelledby="navbarDropdown"
							style="background-color: #e8ecf1">
							<a class="dropdown-item"
								href="#">공장재고</a>
						</div>
						
					</li> -->

					<li class="nav-item dropdown ml-5 mr-5 pl-3 pr-3">
						<a class="nav-link" href="${pageContext.request.contextPath }/logistics/main_page.do" style="color: white">부서게시판 </a>
					</li>
					
					
					


				</ul>
			</div>

		</nav>
	</div>
</div>

