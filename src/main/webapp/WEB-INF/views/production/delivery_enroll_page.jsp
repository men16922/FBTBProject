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

function enrollDelivery() {
	var store_order_detail_no = document.getElementById("store_order_detail_no");
	var invoice_no = document.getElementById("invoice_no");
	
	// 유효성 검사 후 팝업 띄우기
	var isNext = false;
	
	// 유효성 검사
	var reg = /[\{\}\[\]\/?.,;:|\)*~`!^\+<>@\#$%&\\\=\(\'\"]/gi;
	if(reg.test(invoice_no.value)) {
		alert("운송장번호에 - 를 제외한 특수문자는 사용할 수 없습니다.");
		invoice_no.focus();
		return;
	} else if(invoice_no.value.length > 10){
		alert("운송장번호를 10글자를 넘을 수 없습니다.");
		invoice_no.value="";
		invoice_no.focus();
		return;
	}
		
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function() {
		//함수 포인터 넣는 것
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			// 호출 되었을 때

			var popUrl = "${pageContext.request.contextPath }/order/order_popup.do?store_order_detail_no="+store_order_detail_no.value;
			window.open(popUrl, "popup", "width=500, height=500, left=800, top=350");
			
			location.href = "${pageContext.request.contextPath }/production/delivery_ready_page.do";
		}
	};
	
	url = "${pageContext.request.contextPath }/production/delivery_enroll_process.do?" + "store_order_detail_no="+store_order_detail_no.value +"&invoice_no="+invoice_no.value;
	
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
					<li class="list-group-item"
						style="height: 105px; background-color: #7393a7; color: white; font-weight: bold;"><h2
							class="mt-3" align="center">발주관리</h2></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/product_order_list_page.do">발주조회</a>
						</h5></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/delivery_request_page.do">출고요청리스트</a>
						</h5></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/delivery_ready_page.do">배송등록</a>
						</h5></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/delivery_list_page.do">배송조회</a>
						</h5></li>
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
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">배송등록</span>
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
												href="${pageContext.request.contextPath }/production/delivery_ready_page.do">배송등록준비</a>
												> <a style="color: gray; font-size: 14px"
												href="${pageContext.request.contextPath }/production/delivery_enroll_page.do">배송등록</a>

											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<!-- 테이블  -->
						<div class="row">
						<div class="col"></div>
						<div class="col">
						<table class="table table-bordered mt-5 table-sm"
							style="width: 500px;">
							<tbody>
								<tr>
									<th scope="row" class="text-center bg-light rounded">상세번호</th>
									<td class="text-center">${map.processManagementVo.store_order_detail_no }
									<input type="hidden" id="store_order_detail_no" value="${map.processManagementVo.store_order_detail_no }">
									</td>
								</tr>
								<tr>
									<th scope="row" class="text-center bg-light rounded">운송장번호</th>
									<td class="text-center"><input type="text" id="invoice_no" name="invoice_no" style="width:100%; border: 0;"></td>
								</tr>
								<tr>
									<th scope="row" class="text-center bg-light rounded">담당자</th>
									<td class="text-center">${sessionUser.employeeVo.emp_name }</td>
								</tr>
								<tr>
									<th scope="row" class="text-center bg-light rounded">매장명</th>
									<td class="text-center">${map.branchVo.branch_name }</td>
								</tr>
								<tr>
									<th scope="row" class="text-center bg-light rounded">제품명</th>
									<td class="text-center">${map.productVo.product_name }</td>
								</tr>
								<tr>
									<th scope="row" class="text-center bg-light rounded">수량</th>
									<td class="text-center">${map.processManagementVo.confirm_qty }</td>
								</tr>
							</tbody>
						</table>
						</div>
						<div class="col"></div>
						</div>
						<div class="row mb-5">
							<div class="col"></div>
							<div class="col"></div>
							<div class="col"><input type="button" value="등록" onclick="enrollDelivery()" class="btn btn-outline-primary"></div>
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