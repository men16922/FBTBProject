<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<div class="col-2" style="padding: 0; border-right: 2px solid #e8ecf1;">
		<ul class="list-group list-group-flush">
			<!--  수정할 부분 시작 -->
			<li class="list-group-item"
				style="height: 105px; background-color: #7393a7; color: white; font-weight: bold;"><h2
					class="mt-3" align="center">발주관리</h2></li>
			<li class="list-group-item"><h5>
					<a style="color: #6c737e;"
						href="${pageContext.request.contextPath }/logistics/read_list_order_page.do">발주조회</a>
				</h5></li>
			<li class="list-group-item"><h5>
					<a style="color: #6c737e;"
						href="${pageContext.request.contextPath }/logistics/first_check_page.do">1차검토</a>
				</h5></li>
			<c:if test="${sessionUser.employeeRankVo.rank_no <= 5 }">
				<li class="list-group-item"><h5>
						<a style="color: #6c737e;"
							href="${pageContext.request.contextPath }/logistics/last_check_page.do">출고요청</a>
					</h5></li>
				<li class="list-group-item"><h5>
						<a style="color: #6c737e;"
							href="${pageContext.request.contextPath }/logistics/reselect_factory_page.do">공장재선택</a>
					</h5></li>
			</c:if>
			<li class="list-group-item"><h5>
					<a style="color: #6c737e;"
						href="${pageContext.request.contextPath }/logistics/delete_order_page.do">발주반려</a>
				</h5></li>

			<!--  수정할 부분 끝 -->
		</ul>
</div>