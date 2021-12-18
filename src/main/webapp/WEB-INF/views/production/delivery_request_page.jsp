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

<script type="text/javascript">

function refreshRequestList(){
	location.reload();
}

function acceptOrRejectOrder(check) {
	var store_order_detail_no = document.getElementById("store_order_detail_no");
	var loc = "${pageContext.request.contextPath }/production/delivery_request_page.do";
	
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		//함수 포인터 넣는 것
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			// 호출 되었을 때
			var popUrl = "${pageContext.request.contextPath }/order/order_popup.do?store_order_detail_no="+store_order_detail_no.value;
			window.open(popUrl, "popup", "width=500, height=500, left=800, top=350");
			
			location.href = loc;
			refreshRequestList();
		}
	};
	
	// 버튼이 승인인지 거절인지 확인
	if(check == 0) {
		// 승인
		url = "${pageContext.request.contextPath }/production/delivery_request_process.do?" + "store_order_detail_no="+store_order_detail_no.value;
	} else if(check == 1) {
		url = "${pageContext.request.contextPath }/production/delivery_reject_process.do?" + "store_order_detail_no="+store_order_detail_no.value;
	}
	
	xmlhttp.open("get", url, true);
	xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xmlhttp.send();
	
	
}

</script>
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
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">생산예정 리스트</span>
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
												href="${pageContext.request.contextPath }/production/delivery_request_page.do">생산예정 리스트</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<!-- 테이블  -->
						<div class="row mt-3">
							<div class="col">
									<table
										class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
										<thead class="shadow-none p-3 mb-5 bg-light rounded">
											<tr>
												<td class="text-center">발주번호</td>
												<td class="text-center">제품명</td>
												<td class="text-center">수량</td>
												<td class="text-center">매장명</td>
												<td class="text-center">발주일자</td>
												<td class="text-center">현재상태</td>
												<td class="text-center">승인</td>
												<td class="text-center">거절</td>
											</tr>
										</thead>
										<tbody>

											<c:forEach items="${list }" var="data">
												<tr>
													<td class="text-center">${data.storeOrderDetailVo.store_order_detail_no }
														<input type="hidden" name="store_order_detail_no" id="store_order_detail_no" value="${data.storeOrderDetailVo.store_order_detail_no }">
													</td>
													<td class="text-center">${data.productVo.product_name }</td>
													<td class="text-center">${data.processManagementVo.confirm_qty }</td>
													<td class="text-center">${data.branchVo.branch_name }</td>
													<td class="text-center"><fmt:formatDate
															value="${data.storeOrderDetailVo.store_order_generating_time }"
															pattern="yyyy-MM-dd" /></td>
													<td class="text-center">${data.processListVo.process_name }</td>
													<td class="text-center"><input type="button" onclick="acceptOrRejectOrder(0)" value="승인" class="btn btn-outline-secondary btn-sm btn-block"></td>
													<td class="text-center"><input type="button" onclick="acceptOrRejectOrder(1)" value="거절" class="btn btn-outline-secondary btn-sm btn-block"></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>

							</div>
						</div>
						<!-- 테이블 끝 -->

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