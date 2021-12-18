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

	function sendComp() {

		var frm = document.getElementById("frm");
		var comp_name = document.getElementById("comp_name");
		var comp_price = document.getElementById("comp_price");
		var isCheck = false;

		var returnValue;
		var comp_qty = "";

		// 한국어 20글자 이내
		var reg = /^[가-힣]{1,20}$/;
		if (!reg.test(comp_name.value)) {
			alert("부품명은 한글로 20자이내로 작성해주세요");
			comp_name.value = ""; // 아무것도 없는 값을 넣어주겠다
			comp_name.focus(); // 커서 띄우기
			return;
		} else {
			isCheck = true;
		}
		
		// 가격 유효성검사
		reg = /^[0-9]+$/;
		if(!reg.test(comp_price.value)){
			alert("가격은 숫자만 작성해주세요");
			comp_price.value = "";
			comp_price.focus();
			return;
		} else {
			isCheck = true;
		}

		if (isCheck == true) {
			returnValue = confirm("재고에 새로운 부품을 입고하시겠습니까 ?");
		} else {
			alert("양식에 맞춰 모두 작성해주세요");
			return;
		}

		if (returnValue == true) {
			var prom = prompt("수량을 입력해주세요", "");
			comp_qty = prom;
		} else {
			alert("신규 부품이 등록되었습니다.");
			comp_qty = "";
		}

		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			//함수 포인터 넣는 것
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

				if (comp_name.value == "") {
					alert("부품명을 입력해주세요");
					return;
				} else if (comp_price.value == "") {
					alert("부품가격을 입력해주세요");
					return;
				} else {
					isCheck = true;
					if (returnValue) {
						prom;
					}
				}
				
				location.href = "${pageContext.request.contextPath }/production/component_enroll_page.do";
			}

		};

		url = "${pageContext.request.contextPath }/production/component_enroll_process.do?"
				+ "comp_name="
				+ comp_name.value
				+ "&comp_price="
				+ comp_price.value
				+ "&comp_qty=" + comp_qty;

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
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/component_page.do">부품조회</a></h5></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/component_enroll_page.do">부품등록</a></h5></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/none_in_item_page.do">입고등록</a></h5></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/in_item_page.do">입고조회</a></h5></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/out_item_enroll_page.do">출고등록</a></h5></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/out_item_page.do">출고조회</a></h5></li>
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
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">신규
									부품 등록</span>
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
												href="${pageContext.request.contextPath }/production/component_enroll_page.do">부품등록</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<!--  부품 등록하는 입력 폼 나오는 곳 -->
						<div class="form-group row mt-3">
							<label class="col-sm-2 col-form-label">부품이름</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="comp_name"
									id="comp_name" placeholder="부품이름을 입력해주세요">
							</div>
						</div>

						<div class="form-group row mt-3">
							<label class="col-sm-2 col-form-label">부품가격</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" name="comp_price"
									id="comp_price" placeholder="부품가격을 입력해주세요">
							</div>
						</div>

						<div class="row">
							<div class="col"></div>
							<div class="col"></div>
							<div class="col">
								<input type="button" class="btn btn-primary btn-sm btn-block"
									onclick="sendComp()" value="등록하기" />
							</div>
						</div>

						<!--  부품 등록하는 입력 폼 나오는 곳 -->

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