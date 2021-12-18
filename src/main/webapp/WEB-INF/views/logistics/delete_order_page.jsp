<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>발주관리</title>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script type="text/javascript">

	function frm_submit(){
	
	    var check_details = document.getElementsByClassName("check_detail");   
	    
	    isAnyChecked = true;
	    
	    for(var checkBox of check_details){
	    	if(checkBox.checked == true){
	    		isAnyChecked = false;
	    	}
	    }
	    
		 if(isAnyChecked == true){
			 alert("발주반려할 발주번호를 선택해주세요");
			 return;
			 
		 }else{
			 frm.submit();
		 }
	
	}

	function getFormatDate(date){
	    var year = date.getFullYear().toString(); 
	   year=year.substring(2,4);              //yyyy
	    var month = (1 + date.getMonth());          //M
	    month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
	    var day = date.getDate();                   //d
	    day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
	    
	    var hours = date.getHours(); // 시
	    hours = hours >= 10 ? hours : '0' + hours;
	    var minutes = date.getMinutes();  // 분
	    minutes = minutes >= 10 ? minutes : '0' + minutes;
	    var seconds = date.getSeconds();  // 초
	    seconds = seconds >= 10 ? seconds : '0' + seconds;
	    return  year + '-' + month + '-' + day + '&nbsp&nbsp&nbsp&nbsp&nbsp' + hours + ':' + minutes + ':' + seconds;       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
	}
	


</script>

</head>
<body>
	<!-- global bavbar -->
	<jsp:include page="../commons/global_nav.jsp"></jsp:include>

	<c:if
		test="${!empty sessionUser && sessionUser.deptVo.dept_type_no eq 2}">
		<!-- logistics navbar -->
		<jsp:include page="../logistics/logistics_nav.jsp"></jsp:include>


		<div class="container-fluid">
			<!-- 메인 기능들이 나오는 row -->
			<div class="row">
				<div class="col-1" style="background-color: #f9f9fa"></div>

				<!--  사이드바 시작  -->
				<jsp:include page="../logistics/logistics_side_nav.jsp"></jsp:include>
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
										style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">발주반려</span>
								</div>
								<div class="col"></div>
								<!---네비게이터-->
								<div class="col-6">
									<div class="row">
										<div class="col"></div>
									</div>
									<div class="row">
										<div class="col-3"></div>
										<div class="col mt-5" style="display: inline;">
											<ul
												style="list-style: none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
												<li><a style="color: gray; font-size: 14px" href="#">발주관리</a>
													> <a style="color: gray; font-size: 14px">발주반려</a>

												</li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<!--1. 타이틀, 네비게이터 끝-->
							<!-- 검색테이블 시작 -->
							<div class="row mt-3">
								<div class="col-1"></div>
								<div class="col">

									<form
										action="${pageContext.request.contextPath }/logistics/delete_order_page.do"
										method="get">
										<table class="table bg-light">
											<tbody>
												<tr>
													<th>발주번호</th>
													<td><input placeholder="발주번호를 입력해주세요."
														name="store_order_res_no" type="text" class="form-control">
													</td>

													<th>담당자</th>
													<td><input placeholder="담당자명을 입력해주세요." id="emp_name"
														name="emp_name" type="text" class="form-control">
													</td>
												</tr>
												<tr>
													<th>발주일자</th>
													<td scope="col"><input placeholder="발주날짜를 입력해주세요."
														name="start_date" id="start_date" type="date"
														class="form-control form-control-sm"></td>
													<td colspan="2"><input placeholder="발주날짜를 입력해주세요."
														name="end_date" id="end_date" type="date"
														class="form-control form-control-sm" style="width: 300px;"></td>

												</tr>

												<tr>
													<td></td>
													<td></td>
													<td></td>
													<td><input type="submit" value="검색"
														class="btn btn-dark btn-block btn-sm"
														style="width: 100px; float: right;"></td>
												</tr>
											</tbody>
										</table>
									</form>
								</div>
								<div class="col-1"></div>
							</div>
							<!-- 검색테이블 끝 -->




							<form
								action="${pageContext.request.contextPath }/logistics/delete_order_process.do"
								method="get" id="frm">
							<input type="hidden" value="2" name="return_page">
								<div class="row mt-5">
									<!-- 테이블 -->
									<div class="col">
										<table
											class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
											<thead class="shadow-none p-3 mb-5 bg-light rounded">
												<tr>
													<td>확인</td>
													<td>발주번호</td>
													<td>발주상세번호</td>
													<td>제품명</td>
													<td>발주수량</td>
													<td>발주일자</td>
													<td>요청상태</td>
													<td>반려</td>

												</tr>
											</thead>
											<tbody>
												<c:forEach var="data" items="${dataList }">
													<tr>
														<td><input type="checkbox" class="check_detail"
															value="${data.storeOrderDetailVo.store_order_detail_no }"
															name="check_detail"></td>
														<td>${data.storeOrderReservationVo.store_order_res_no }</td>
														<td><a
															href="${pageContext.request.contextPath }/logistics/update_delete_page.do?store_order_detail_no=${data.storeOrderDetailVo.store_order_detail_no }&return_page=2">${data.storeOrderDetailVo.store_order_detail_no }</a></td>
														<td>${data.productVo.product_name }</td>
														<td><c:choose>
																<c:when test="${data.processListVo.process_no == 1 }">
								                                 ${data.storeOrderDetailVo.first_order_qty }
								                                </c:when>
																<c:otherwise>
								                                  ${data.processManagementVo.confirm_qty }
								                                </c:otherwise>
															</c:choose></td>

														<td><fmt:formatDate
																value="${data.storeOrderReservationVo.store_order_res_date}"
																pattern="yy-MM-dd" /> &nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate
																value="${data.storeOrderReservationVo.store_order_res_date}"
																pattern="HH:mm:ss" /></td>
														<td>${data.processListVo.process_name }</td>

														<td><input type="button"
															class="btn btn-outline-secondary btn-block" value="반려"
															onclick="frm_submit()"></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
								</div>

								<div class="row mb-5">
									<!-- 버튼들... -->

									<div class="col">
										<!-- 페이지 버튼 -->
										<nav aria-label="Page navigation example">
											<ul class="pagination">

												<li
													class="page-item<c:if test="${beginPage-1 <= 0}"> disabled</c:if>">
													<a class="page-link"
													href="${pageContext.request.contextPath }/logistics/delete_order_page.do?currPage=${beginPage-1 }&store_order_res_no=${param.store_order_res_no}&store_order_detail_no=${param.store_order_detail_no}&emp_name=${param.emp_name}&product_name=${param.product_name}&start_date=${param.start_date}&end_date=${param.end_date}">이전</a>
												</li>
												<c:forEach begin="${beginPage }" end="${endPage }" var="i">
													<li
														class="page-item<c:if test="${currPage==i}"> active</c:if>"><a
														class="page-link"
														href="${pageContext.request.contextPath }/logistics/delete_order_page.do?currPage=${i}&store_order_res_no=${param.store_order_res_no}&store_order_detail_no=${param.store_order_detail_no}&emp_name=${param.emp_name}&product_name=${param.product_name}&start_date=${param.start_date}&end_date=${param.end_date}">${i}</a></li>
												</c:forEach>
												<li
													class="page-item<c:if test="${endPage+1 >= (totalCount-1)/10+1 }"> disabled</c:if>"><a
													class="page-link"
													href="${pageContext.request.contextPath }/logistics/delete_order_page.do?currPage=${endPage+1 }&store_order_res_no=${param.store_order_res_no}&store_order_detail_no=${param.store_order_detail_no}&emp_name=${param.emp_name}&product_name=${param.product_name}&start_date=${param.start_date}&end_date=${param.end_date}">다음</a></li>

											</ul>

										</nav>
									</div>


									<div class="col-6">
										<div class="row">
											<div class="col"></div>
											<div class="col-3">
												<input type="reset" class="btn btn-secondary btn-block"
													value="초기화">
											</div>
											<div class="col-6">
												<input type="button" class="btn btn-primary btn-block"
													value="반려" onclick="frm_submit()">
											</div>
										</div>
									</div>

								</div>
							</form>

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

	</c:if>


	<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
		integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
		integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
		integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
		crossorigin="anonymous"></script>
</body>
</html>