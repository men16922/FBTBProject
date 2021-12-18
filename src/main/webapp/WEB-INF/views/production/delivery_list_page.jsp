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
	<jsp:include page="../commons/global_nav.jsp"></jsp:include>
	<jsp:include page="../commons/production_nav.jsp"></jsp:include>

	<div class="container-fluid">
		<!-- 메인 기능들이 나오는 row -->
		<div class="row">
			<div class="col-1" style="background-color: #f9f9fa"></div>

			<!--  사이드바 시작  -->
			<div class="col-2"
				style="padding: 0; border-right: 2px solid #e8ecf1; border-left: 2px solid #e8ecf1;">
				<ul class="list-group list-group-flush">
				<!--  수정할 부분 시작 -->
					<li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">발주관리</h2></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/product_order_list_page.do">발주조회</a></h5></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/delivery_request_page.do">생산예정리스트</a></h5></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/delivery_ready_page.do">배송등록</a></h5></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/delivery_list_page.do">배송조회</a></h5></li>
					<!--  수정할 부분 끝 -->
				</ul>
			</div>
			<!--  사이드바 끝   -->


			<!--  메인 기능 나오는 곳 -->
			<div class="col pl-5 pr-5">
				<!--메인기능 내용시작-->
				<div class="row">
					<!--메인기능 메인col-->
					<div class="col" style="background-color: white">
						<!--1. 타이틀, 네비게이터 시작-->
						<div class="row mt-3">
							<!--타이틀-->
							<div class="col-4 mt-3">
								<span
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">배송조회</span>
							</div>
							<div class="col"></div>
							<!---네비게이터-->
							<div class="col-6">
								<div class="row">
									<div class="col"></div>
								</div>
								<div class="row">
									<div class="col-2"></div>
									<div class="col mt-5" style="display: inline;">
										<ul
											style="list-style: none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
											<li><a style="color: gray; font-size: 14px" href="#">발주관리</a>
												> <a style="color: gray; font-size: 14px"
												href="${pageContext.request.contextPath }/production/delivery_list_page.do">배송조회</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<div class="row">

							<div class="col">
								<form action="./delivery_list_page.do" method="get">
									<table class="table bg-light">
										<tbody>
											<tr>
												<th>상세번호</th>
												<td><input placeholder="발주상세번호를 입력해주세요."
													name="store_order_detail_no" type="text"
													class="form-control form-control-sm"></td>
												<th>운송장번호</th>
												<td><input placeholder="운송장번호를 입력해주세요."
													name="invoice_no" type="text"
													class="form-control form-control-sm"></td>
											</tr>
											<tr>
												<th>배송등록일자</th>
												<td scope="col"><input name="start_date" type="date"
													class="form-control form-control-sm"></td>
												<td colspan="2"><input name="end_date" type="date"
													class="form-control form-control-sm" style="width:300px;"></td>
											</tr>

											<tr>
												<th>지점명</th>
												<td><c:if test="${!empty branchList }">
														<select name="branch_no" id="branch_no"
															class="form-control form-control-sm">
															<option value="">매장을 선택해주세요</option>
															<c:forEach var="data" items="${branchList }">
																<option value="${data.branchVo.branch_no }">
																	${data.branchVo.branch_name }</option>
															</c:forEach>
														</select>
													</c:if></td>
												<td></td>
												<td><input type="submit" value="검색"
													class="btn btn-dark btn-block btn-sm"
													style="width: 100px; float: right;"></td>
											</tr>
										</tbody>
									</table>

								</form>
							</div>
						</div>

						<div class="row mt-3">
							<!-- 테이블  -->
							<div class="col">
								<table
									class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
									<thead class="shadow-none p-3 mb-5 bg-light rounded">
										<tr>
											<td class="text-center">발주상세번호</td>
											<td class="text-center">운송장번호</td>
											<td class="text-center">지점명</td>
											<td class="text-center">담당자</td>
											<td class="text-center">배송날짜</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${list }" var="data">
											<tr>
												<td class="text-center">${data.deliveryVo.store_order_detail_no }</td>
												<td class="text-center"><a
													href="${pageContext.request.contextPath }/production/delivery_list_read_page.do?store_order_detail_no=${data.deliveryVo.store_order_detail_no }">
														${data.deliveryVo.invoice_no }</a></td>
												<td class="text-center">${data.branchVo.branch_name }</td>
												<td class="text-center">${data.employeeVo.emp_name }</td>
												<td class="text-center"><fmt:formatDate
														value="${data.deliveryVo.delivery_date }"
														pattern="yyyy-MM-dd" /></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="row mt-3">
							<!-- 버튼위치 -->
							<div class="col"></div>
							<div class="col">
								<!-- 페이지 버튼 -->
								<nav aria-label="Page navigation example">
									<ul class="pagination">
										<li
											class="page-item<c:if test="${beginPage-1<=0 }"> disabled</c:if>"><a
											class="page-link"
											href="./delivery_list_page.do?currPage=${beginPage-1 }&store_order_detail_no=${param.store_order_detail_no}&invoice_no=${param.invoice_no}&branch_no=${param.branch_no}&delivery_date=${param.delivery_date}">이전</a></li>
										<c:forEach begin="${beginPage }" end="${endPage }" var="i">
											<!-- 띄어쓰기 주의 -->
											<li
												class="page-item<c:if test="${currPage==i }"> active</c:if>">
												<a class="page-link"
												href="./delivery_list_page.do?currPage=${i }&store_order_detail_no=${param.store_order_detail_no}&invoice_no=${param.invoice_no}&branch_no=${param.branch_no}&delivery_date=${param.delivery_date}">${i }</a>
											</li>
										</c:forEach>
										<li
											class="page-item<c:if test="${endPage+1 > (totalCount-1)/5+1 }"> disabled</c:if>"><a
											class="page-link"
											href="./delivery_list_page.do?currPage=${endPage+1 }&store_order_detail_no=${param.store_order_detail_no}&invoice_no=${param.invoice_no}&branch_no=${param.branch_no}&delivery_date=${param.delivery_date}">다음</a></li>
									</ul>
								</nav>
							</div>
							<div class="col"></div>
						</div>

					</div>
				</div>
				<!--메인기능 내용끝-->

			</div>
			<!--  메인기능 나오는 곳 끝 -->

			<div class="col-1" style="background-color: #f9f9fa"></div>
		</div>

		<!--  footer  -->
		<jsp:include page="../commons/footer.jsp"></jsp:include>
	</div>

	<jsp:include page="../commons/alert_unread_message.jsp"></jsp:include>
	<jsp:include page="../production/production_message_alert.jsp"></jsp:include>
      <jsp:include page="../production/production_order_message_alert.jsp"></jsp:include>
      
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
		integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
		crossorigin="anonymous"></script>

</body>
</html>