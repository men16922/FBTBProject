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
	function refreshRequestList() {
		location.reload();
	}

	function sendEnroll() {

		var factory_out_item_no = document
				.getElementById("factory_out_item_no");
		var product_no = document.getElementById("product_no");
		var factory_out_item_qty = document
				.getElementById("factory_out_item_qty");
		var branch_no = document.getElementById("branch_no");
		var factory_out_item_date = document
				.getElementById("factory_out_item_date");

		// 출고번호, 수량 유효성검사
		var reg = /^[0-9]+$/;

		if (factory_out_item_no.value == "") {
			alert("출고번호를 입력해주세요");
			return;
		} else if (!reg.test(factory_out_item_no.value)) {
			alert("출고번호는 숫자만 작성해주세요");
			factory_out_item_no.value = "";
			factory_out_item_no.focus();
			return;
		}

		if (product_no.value == "") {
			alert("상품명을 선택해주세요");
			return;
		}

		if (factory_out_item_qty.value == "") {
			alert("수량을 입력해주세요");
			return;
		} else if (!reg.test(factory_out_item_qty.value)) {
			alert("수량은 숫자만 작성해주세요");
			factory_out_item_qty.value = "";
			factory_out_item_qty.focus();
			return;
		}

		if (branch_no.value == "") {
			alert("매장명을 선택해주세요");
			return;
		}

		if (factory_out_item_date.value == "") {
			alert("날짜를 입력해주세요");
			return;
		}

		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			//함수 포인터 넣는 것
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				location.href = "${pageContext.request.contextPath }/production/out_item_enroll_page.do";
			}

		};

		url = "${pageContext.request.contextPath }/production/out_item_enroll_process.do?"
				+ "factory_out_item_no="
				+ factory_out_item_no.value
				+ "&product_no="
				+ product_no.value
				+ "&factory_out_item_qty="
				+ factory_out_item_qty.value
				+ "&branch_no="
				+ branch_no.value
				+ "&factory_out_item_date=" + factory_out_item_date.value;

		xmlhttp.open("get", url, true);
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
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
				style="padding: 0; border-right: 2px solid #e8ecf1;">
				<ul class="list-group list-group-flush">
					<!--  수정할 부분 시작 -->
					<li class="list-group-item"
						style="height: 105px; background-color: #7393a7; color: white; font-weight: bold;"><h2
							class="mt-3" align="center">재고관리</h2></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/component_page.do">부품조회</a>
						</h5></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/component_enroll_page.do">부품등록</a>
						</h5></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/none_in_item_page.do">입고등록</a>
						</h5></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/in_item_page.do">입고조회</a>
						</h5></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/out_item_enroll_page.do">출고등록</a>
						</h5></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/out_item_page.do">출고조회</a>
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
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">출고등록</span>
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
											<li><a style="color: gray; font-size: 14px" href="#">재고관리</a>
												> <a style="color: gray; font-size: 14px"
												href="${pageContext.request.contextPath }/production/out_item_enroll_page.do">출고등록</a>

											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<div class="row mt-4">
							<div class="col-2 mx-3">출고번호</div>
							<div class="col mx-3">
								<input placeholder="출고번호를 입력해주세요." name="factory_out_item_no"
									id="factory_out_item_no" type="text" class="form-control">
							</div>
						</div>

						<div class="row mt-1">
							<div class="col-2 mx-3">상품명</div>
							<div class="col mx-3">
								<c:if test="${!empty productList }">
									<select name="product_no" id="product_no" class="form-control">
										<option value="">상품명을 선택해주세요</option>
										<c:forEach var="data" items="${productList }">
											<option value="${data.productVo.product_no }">
												${data.productVo.product_name }</option>
										</c:forEach>
									</select>
								</c:if>
							</div>
						</div>

						<div class="row mt-1">
							<div class="col-2 mx-3">출고수량</div>
							<div class="col mx-3">
								<input placeholder="출고수량을 입력해주세요." name="factory_out_item_qty"
									id="factory_out_item_qty" type="text" class="form-control">
							</div>
						</div>

						<div class="row mt-1">
							<div class="col-2 mx-3">매장명</div>
							<div class="col mx-3">
								<c:if test="${!empty branchList }">
									<select name="branch_no" id="branch_no" class="form-control">
										<option value="">매장명을 선택해주세요</option>
										<c:forEach var="data" items="${branchList }">
											<option value="${data.branchVo.branch_no }">
												${data.branchVo.branch_name }</option>
										</c:forEach>
									</select>
								</c:if>
							</div>
						</div>

						<div class="row mt-1">
							<div class="col-2 mx-3">출고날짜</div>
							<div class="col mx-3">
								<input placeholder="출고날짜를 입력해주세요." name="factory_out_item_date"
									id="factory_out_item_date" type="date" class="form-control">
							</div>
						</div>

						<div class="row">
							<div class="col"></div>
							<div class="col"></div>
							<div class="col">
								<input type="button" value="등록"
									class="btn btn-primary btn-block" onclick="sendEnroll()"
									style="margin-top: 30px;">
							</div>
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