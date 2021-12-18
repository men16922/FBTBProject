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

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
	integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>

<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>

<script>
	function pass_form() {

		var frm = document.getElementById("frm");

		var hp_notice_title = document.getElementById("hp_notice_title").value;
		var hp_notice_content = document.getElementById("hp_notice_content").value;

		if (hp_notice_title == "") {
			alert("제목을 입력해주세요.");
			return;
		} else if (hp_notice_content == "") {
			alert("내용을 입력해주세요.");
			return;
		}

		frm.submit(); //값을 DB로 전달
	};

	function pass_form2() {

		var frm2 = document.getElementById("frm2");

		var hp_notice_title = document.getElementById("hp_notice_title2").value;
		var hp_notice_content = document.getElementById("hp_notice_content2").value;

		if (hp_notice_title == "") {
			alert("제목을 입력해주세요.");
			return;
		} else if (hp_notice_content == "") {
			alert("내용을 입력해주세요.");
			return;
		}

		frm2.submit(); //값을 DB로 전달
	};

	setInterval(function() {
		var count = ${customerreservationcount}
		;

		if (count > 0) {
			if ($(".alert").is(":visible")) {
				$(".alert").css("display", "none");
			} else {
				$(".alert").css("display", "block");
			}
		}

		//console.log("1초");
	}, 3000);

	$(function() {
		$("#xxxx").datepicker();
	});

	function weather() {

		var apiURI = "http://api.openweathermap.org/data/2.5/weather?q=Seoul&appid=15bf461db74ce6c952e25d8281345c03";
		$
				.ajax({
					url : apiURI,
					dataType : "json",
					type : "GET",
					async : "false",
					success : function(resp) {
						console.log(resp);
						console.log("현재온도 : " + (resp.main.temp - 273.15));
						console.log("현재습도 : " + resp.main.humidity);
						console.log("날씨 : " + resp.weather[0].main);
						console.log("상세날씨설명 : " + resp.weather[0].description);
						console.log("날씨 이미지 : " + resp.weather[0].icon);
						console.log("바람   : " + resp.wind.speed);
						console.log("나라   : " + resp.sys.country);
						console.log("도시이름  : " + resp.name);
						console.log("구름  : " + (resp.clouds.all) + "%");

						var imgURL = "http://openweathermap.org/img/w/"
								+ resp.weather[0].icon + ".png";
						var Temp = Math.floor(resp.main.temp - 273.15) + "℃";
						var city = resp.name;

						//    $('.CurrIcon').append('http://api.openweathermap.org/img/wn' + $Icon +'@2x.png');
						//   $('.CurrTemp').prepend($Temp);
						//    $('.City').append($city);
						document.getElementById("CurrIcon").src = imgURL;
						document.getElementById("temp").innerText = Temp;
						document.getElementById("weather").innerText = (resp.weather[0].main);
						document.getElementById("wind").innerText = (resp.wind.speed + "m/s");
						document.getElementById("humidity").innerText = (resp.main.humidity + "%");
						document.getElementById("cloud").innerText = ((resp.clouds.all) + "%");

					}
				})

	}
</script>
</head>
<body onload="weather();">
<body>

	<jsp:include page="../commons/global_nav.jsp"></jsp:include>
	<c:if test="${sessionUser.deptTypeVo.dept_type_no==1}">
		<jsp:include page="../commons/management_nav.jsp"></jsp:include>
	</c:if>
	<c:if test="${sessionUser.deptTypeVo.dept_type_no==2}">
		<jsp:include page="../logistics/logistics_nav.jsp"></jsp:include>
	</c:if>
	<c:if test="${sessionUser.deptTypeVo.dept_type_no==3}">
		<jsp:include page="../commons/sales_nav.jsp"></jsp:include>
	</c:if>
	<c:if test="${sessionUser.deptTypeVo.dept_type_no==4}">
		<jsp:include page="../commons/production_nav.jsp"></jsp:include>
	</c:if>

	<div class="container-fluid">
		<!-- 메인 기능들이 나오는 row -->
		<div class="row">
			<div class="col-1" style="background-color: #f9f9fa"></div>

			<!-- 첫번째 col -->
			<div class="col pl-5 pr-5" style="padding: 30px;">
				<!-- 로그인 정보 -->
				<div class="row">
					<div class="col">
						<div class="card bg-light mb-3">
							<div class="card-header"
								style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 20px;">사원
								정보</div>
							<div class="card-body">
								<p class="card-text">${sessionUser.branchVo.branch_name }
									${sessionUser.employeeRankVo.rank_name }</p>
								<p class="card-text"
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 20px;">${sessionUser.employeeVo.emp_name }님
								</p>
								<p class="card-text">환영합니다</p>
							</div>
						</div>
					</div>
				</div>

				<div class="row mt-3">
					<!-- 달력 -->
					<div class="col ml-3">
						<div id="xxxx"></div>
					</div>
				</div>

				<div class="row mt-3">
					<!--  날씨 나오는 곳 11 -->
					<div class="col"
						style="background-color: rgb(51, 122, 183); color: white;">
						<ul style="list-style: none;">
							<li><img id="CurrIcon" src="" style="width: 60px;"></li>
						</ul>
					</div>

					<div class="col mr-3"
						style="background-color: rgb(51, 122, 183); color: white;">
						<ul style="list-style: none">
							<li class="mt-2" id="temp" style="font-size: 20px;"></li>
							<li id="weather" style="font-weight: bold"></li>
							<li class="mt-1 mb-1" style="font-size: small"><i>Seoul</i></li>
						</ul>
					</div>
				</div>

				<div class="row">
					<!--  날씨 나오는 곳 22 -->
					<div class="col"
						style="background-color: rgb(245, 245, 245); color: rgb(51, 122, 183);">
						<ul style="list-style: none; padding: 0px;">
							<li><img id=""
								src="${pageContext.request.contextPath}/resources/img/wind.png"
								class="img-fluid" style="text-align: center; padding-top: 10px"></li>
							<li class="mt-1 mb-1" id="wind"></li>

						</ul>
					</div>

					<div class="col"
						style="background-color: rgb(245, 245, 245); color: rgb(51, 122, 183);">
						<ul style="list-style: none; padding: 0px;">
							<li><img id=""
								src="${pageContext.request.contextPath}/resources/img/water.png"
								class="img-fluid" style="text-align: center; padding-top: 10px"></li>
							<li class="mt-1 mb-1" id="humidity"></li>

						</ul>
					</div>

					<div class="col mr-3"
						style="background-color: rgb(245, 245, 245); color: rgb(51, 122, 183);">
						<ul style="list-style: none; padding: 0px;">
							<li><img id=""
								src="${pageContext.request.contextPath}/resources/img/cloud.png"
								class="img-fluid" style="text-align: center; padding-top: 10px"></li>
							<li class="mt-1 mb-1" id="cloud"></li>
						</ul>
					</div>


				</div>

			</div>
			<!-- 첫번째 col 끝   -->


			<!--  메인 기능 나오는 곳 -->
			<div class="col-5"
				style="border-right: 2px solid #e8ecf1; border-left: 2px solid #e8ecf1; padding: 30px;">
				<!--  부서별 할일 보여주기 / 수정해야 할 부분  -->
				<div class="row">
					<div class="col">
						<!--  부서별 들어가야할 곳 -->
						<c:if test="${sessionUser.deptTypeVo.dept_type_no==1}">
							<jsp:include page="../management/sample_page.jsp"></jsp:include>
						</c:if>
						<c:if test="${sessionUser.deptTypeVo.dept_type_no==2}">
							<jsp:include page="../logistics/logistics_do_list.jsp"></jsp:include>
						</c:if>
						<c:if test="${sessionUser.deptTypeVo.dept_type_no==3}">
							<jsp:include page="../sales/sales_do_list.jsp"></jsp:include>
						</c:if>
						<c:if test="${sessionUser.deptTypeVo.dept_type_no==4}">
							<jsp:include page="../production/production_do_list.jsp">
								<jsp:param name="factoryOrderCount" value="${factoryOrderCount }" />
								<jsp:param name="productReqCount" value="${productReqCount }" />
								<jsp:param name="productReadyCount" value="${productReadyCount }" />
							</jsp:include>
						</c:if>
					</div>
				</div>
				<!--  부서별 할일 보여주기 / 수정해야 할 부분  -->
				<div class="row mt-3 mb-5">
					<div class="col">
						<div class="card">
							<div class="card-header"
								style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 20px;">사내
								자유게시판</div>
							<div class="card-body">
								<c:forEach begin="0" end="4" var="data" items="${dataList }">
									<ul class="list-group list-group-flush">
										<li class="list-group-item"><a
											style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; font-weight: bold; font-size: 16px;"
											href="${pageContext.request.contextPath }/freeboard/read_content_freeboard_page.do?freeboard_no=${data.freeBoardVo.freeboard_no}">${data.freeBoardVo.freeboard_title}</a>
											<pre></pre>
											${data.branchVo.branch_name}&nbsp;${data.freeBoardVo.freeboard_writer }
										</li>
									</ul>

								</c:forEach>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--  메인기능 나오는 곳 끝 -->

			<!-- 세번째 col -->
			<div class="col pl-5 pr-5" style="padding: 30px;">
				<div class="row">
					<div class="col mr-5">
						<div class="row">
							<h3
								style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 25px;">
								회사 소식
								<c:if
									test="${!empty sessionUser && sessionUser.deptTypeVo.dept_type_no==1}">
									<button type="button" class="btn btn-outline-dark btn-sm"
										data-toggle="modal" data-target="#myModal">수정</button>
								</c:if>
							</h3>

						</div>


						<div class="card text-center">
							<div class="card-header">
								<c:forEach var="aaaa" items="${homepage_list_Company }">
                  ${aaaa.homepageNoticeVo.hp_notice_title}
                  </c:forEach>
							</div>
							<div class="card-body">

								<c:forEach var="aaaa" items="${homepage_list_Company }">
									<h5 class="card-title">${aaaa.homepageNoticeVo.hp_notice_content}</h5>
								</c:forEach>
							</div>
							<div class="card-footer text-muted">
								<c:forEach var="aaaa" items="${homepage_list_Company }">
									<fmt:formatDate
										value="${aaaa.homepageNoticeVo.hp_notice_writedate}"
										pattern="yyyy-MM-dd" />
								</c:forEach>
							</div>
						</div>

						<br>


						<div class="card text-center">
							<div class="card-header">
								<c:forEach var="aaaa" items="${homepage_list_Chairman }">
                  ${aaaa.homepageNoticeVo.hp_notice_title}
                  </c:forEach>
							</div>
							<div class="card-body">
								<c:forEach var="aaaa" items="${homepage_list_Chairman }">
									<h5 class="card-title">${aaaa.homepageNoticeVo.hp_notice_content}</h5>
								</c:forEach>
							</div>
							<div class="card-footer text-muted">
								<c:forEach var="aaaa" items="${homepage_list_Chairman }">
									<fmt:formatDate
										value="${aaaa.homepageNoticeVo.hp_notice_writedate}"
										pattern="yyyy-MM-dd" />
								</c:forEach>
							</div>
						</div>

					</div>
				</div>



				<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel">


					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">

								<h4 class="modal-title" id="myModalLabel">수정하기</h4>
							</div>
							<div class="modal-body">


								<form action="./homepageNotice_modify_process.do" method="get"
									id="frm">
									<input type="hidden" name="hp_notice_type_no" value="1">

									<div class="row">
										<div class="col-2">제목</div>
										<c:forEach var="aaaa" items="${homepage_list_Company }">
											<div class="col">
												<input class="form-control" type="text" id="hp_notice_title"
													name="hp_notice_title"
													value="${aaaa.homepageNoticeVo.hp_notice_title}">
											</div>
										</c:forEach>
									</div>

									<div class="row">
										<div class="col-2">내용</div>
										<div class="col">
											<c:forEach var="aaaa" items="${homepage_list_Company }">
												<input class="form-control" type="text"
													id="hp_notice_content" name="hp_notice_content"
													value="${aaaa.homepageNoticeVo.hp_notice_content}">
											</c:forEach>
										</div>
									</div>
								</form>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										onclick="pass_form()">확인</button>
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">취소</button>
								</div>


								<form action="./homepageNotice_modify_process.do" method="get"
									id="frm2">
									<input type="hidden" name="hp_notice_type_no" value="2">
									<div class="row">
										<div class="col-2">제목</div>
										<c:forEach var="bbbb" items="${homepage_list_Chairman}">
											<div class="col">
												<input class="form-control" type="text"
													id="hp_notice_title2" name="hp_notice_title"
													value="${bbbb.homepageNoticeVo.hp_notice_title}">
											</div>
										</c:forEach>
									</div>

									<div class="row">
										<div class="col-2">내용</div>
										<div class="col">
											<c:forEach var="bbbb" items="${homepage_list_Chairman }">
												<input class="form-control" type="text"
													id="hp_notice_content2" name="hp_notice_content"
													value="${bbbb.homepageNoticeVo.hp_notice_content}">
											</c:forEach>
										</div>
									</div>
								</form>
								<div class="modal-footer">
									<button type="button" class="btn btn-primary"
										onclick="pass_form2()">확인</button>
									<button type="button" class="btn btn-secondary"
										data-dismiss="modal">취소</button>
								</div>


							</div>

						</div>
					</div>
				</div>

			</div>
		<!-- 세번재 col 끝   -->


			<div class="col-1" style="background-color: #f9f9fa"></div>

		</div>
		<!--  footer  -->
      <jsp:include page="../commons/footer.jsp"></jsp:include>
   </div>

   <jsp:include page="../commons/alert_unread_message.jsp"></jsp:include>
   <c:if test="${sessionUser.deptTypeVo.dept_type_no==1}">
      
   </c:if>
   <c:if test="${sessionUser.deptTypeVo.dept_type_no==2}">

   </c:if>
   <c:if test="${sessionUser.deptTypeVo.dept_type_no==3}">
      <jsp:include page="../sales/sales_alert.jsp"></jsp:include>
      <jsp:include page="../sales/sales_alert2.jsp"></jsp:include>
   </c:if>
   <c:if test="${sessionUser.deptTypeVo.dept_type_no==4}">
      <jsp:include page="../production/production_message_alert.jsp"></jsp:include>
      <jsp:include page="../production/production_order_message_alert.jsp"></jsp:include>
   </c:if>

</body>


</html>