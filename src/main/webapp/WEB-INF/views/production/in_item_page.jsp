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
	function getFormatDate(date){
		var year = date.getFullYear().toString(); 
			year=year.substring(2,4);              //yyyy
		var month = (1 + date.getMonth());          //M
		month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
		var day = date.getDate();                   //d
		day = day >= 10 ? day : '0' + day;          //day 두자리로 저장
	
		var hours = date.getHours()+9; // 시
		hours = hours >= 10 ? hours : '0' + hours;
		var minutes = date.getMinutes();  // 분
		minutes = minutes >= 10 ? minutes : '0' + minutes;
		var seconds = date.getSeconds();  // 초
		seconds = seconds >= 10 ? seconds : '0' + seconds;
	return  year + '/' + month + '/' + day + '&nbsp&nbsp&nbsp&nbsp&nbsp' + hours + ':' + minutes + ':' + seconds;       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
	}
	// 정렬하기
	function sendSort() {

		const urlParams = new URLSearchParams(window.location.search);
		
		var factory_in_item_code = urlParams.get("factory_in_item_code");
		var comp_no = urlParams.get("comp_no");
		var start_date = urlParams.get("start_date");
		var end_date = urlParams.get("end_date");
		var emp_code = urlParams.get("emp_code");
		var supplier_no = urlParams.get("supplier_no");
		var sort = document.getElementById("sort").value;
		var currPage = urlParams.get("currPage");
		if (currPage == null)
			currPage = 1;

		var xmlhttp = new XMLHttpRequest();

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//alert(xmlhttp.responseText);
	
				var List = JSON.parse(xmlhttp.responseText);
				
				for(var i=0; i<$('#table tr').length-1; i++){
					table();
				}
				
				var innerHtml = "";
				for(item of List){
					// 데이터 값을 노출할 테이블 만들기
					innerHtml += "<tr>";
					
					var code = item.factoryInItemVo.factory_in_item_code;
					innerHtml += "<td class=text-center>"+code+"</td>";
					
					var compName = item.productComponentVo.comp_name;
					innerHtml += "<td class=text-center>"+compName+"</td>";
					
					var qty = item.factoryInItemVo.factory_in_item_qty;
					innerHtml += "<td class=text-center>"+qty+"</td>";
					
					var price = item.productComponentVo.comp_price;
					innerHtml += "<td class=text-center>"+price+"</td>";
					
					var supplierName = item.supplierVo.supplier_name;
					innerHtml += "<td class=text-center>"+supplierName+"</td>";
					
					var date = new Date(item.factoryInItemVo.factory_in_item_date);
					date = getFormatDate(date);
					innerHtml += "<td class=text-center>"+date+"</td>";
					
					innerHtml += "</tr>";
				}
				document.getElementById("tbody").innerHTML = innerHtml;
			}
		};
		
		// 페이징 링크 수정하기
		var paging = document.getElementsByClassName('paging');
		for(var i=0; i<paging.length; i++){
			var x = paging[i].href.substring(0,62);
			if(factory_in_item_code == null)
				factory_in_item_code = "";
			x += "&factory_in_item_code="+factory_in_item_code;
			if(comp_no == null)
				comp_no = "";
			x += "&comp_no="+comp_no;
			if(start_date == null)
				start_date = "";
			x += "&start_date="+start_date;
			if(end_date == null)
				end_date = "";
			x += "&end_date="+end_date;
			if(emp_code == null)
				emp_code = "";
			x += "&emp_code="+emp_code;
			if(supplier_no == null)
				supplier_no = "";
			x += "&supplier_no="+supplier_no;
			x += "&sort="+sort;
			paging[i].href = x;
		}
		
		
		var requestUrl = "${pageContext.request.contextPath }/production/in_item_process.do?";
		//url에 달기
		if(factory_in_item_code == null)
			factory_in_item_code = "";
		requestUrl += "factory_in_item_code="+factory_in_item_code;
		if(comp_no == null)
			comp_no = "";
		requestUrl += "&comp_no="+comp_no;
		if(start_date == null)
			start_date = "";
		requestUrl += "&start_date="+start_date;
		if(end_date == null)
			end_date = "";
		requestUrl += "&end_date="+end_date;
		if(emp_code == null)
			emp_code = "";
		requestUrl += "&emp_code="+emp_code;
		if(supplier_no == null)
			supplier_no = "";
		requestUrl += "&supplier_no="+supplier_no;
		requestUrl += "&sort="+sort;
		requestUrl += "&currPage="+currPage;
	
		xmlhttp.open("get", requestUrl , true);
		xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xmlhttp.send();
		
		function table() {
			var lo_table = document.getElementById("table"); // 테이블지정
			var li_rows = lo_table.rows.length; // 테이블 row 갯수
			var li_row_index = li_rows - 1; // 테이블 row의 인덱스
			
			if(li_row_index >= 0){
				lo_table.deleteRow(li_row_index);
			}
		}
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
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">입고조회</span>
							</div>
							<div class="col"></div>
							<!---네비게이터-->
							<div class="col-6 mb-5">
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
												href="${pageContext.request.contextPath }/production/in_item_page.do">입고조회</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<div class="row">

							<div class="col">
								<form action="./in_item_page.do" method="get">
									<table class="table bg-light">
										<tbody>
											<tr>
												<th>부품명</th>
												<td>
												<c:if test="${!empty componentList }">
													<select name="comp_no" id="comp_no" class="form-control form-control-sm" onchange="sendCompNo()">
															<option value="">부품명을 선택해주세요</option>
															<c:forEach var="data" items="${componentList }">
																<option value="${data.productComponentVo.comp_no }">
																	${data.productComponentVo.comp_name }</option>
															</c:forEach>
													</select>
												</c:if>
												</td>
												<th>공급업체</th>
												<td>
												<c:if test="${!empty supplierList }">
													<select name="supplier_no" id="supplier_no" class="form-control form-control-sm">
															<option value="">공급업체를 선택해주세요</option>
															<c:forEach var="data" items="${supplierList }">
																<option value="${data.supplierVo.supplier_no }">
																	${data.supplierVo.supplier_name }</option>
															</c:forEach>
													</select>
												</c:if>
												</td>	
											</tr>
											<tr>
												<th>입고일자</th>
												<td scope="col"><input placeholder="입고날짜를 입력해주세요."
													name="start_date" id="start_date" type="date"
													class="form-control form-control-sm"></td>
												<td colspan="2"><input placeholder="입고날짜를 입력해주세요."
													name="end_date" id="end_date" type="date"
													class="form-control form-control-sm" style="width:300px;"></td>
												
											</tr>
											
											<tr>
												<td></td>
												<td></td>
												<td></td>
												<td><input type="submit" value="검색"
													class="btn btn-dark btn-block btn-sm" style="width:100px; float:right;"></td>
											</tr>
										</tbody>
									</table>
								</form>
							</div>
						</div>

						<div class="mt-1 row">
							<div class="col">
								<div class="row">
									<div class="col"><select name="sort" id="sort" onchange="sendSort()">
											<option value=0>정렬선택</option>
											<option value=1>이름순</option>
											<option value=2>수량순</option>
											<option value=3>가격낮은순</option>
											<option value=4>가격높은순</option>
											<option value=5>입고날짜순</option>
										</select></div>
									<div class="col"></div>
									
								</div>
							</div>
						</div>

						<div class="row mt-1">
							<!-- 테이블  -->
							<div class="col">
								<table
									class="table table-hover shadow-sm p-3 mb-5 bg-white rounded"
									id="table">
									<thead class="shadow-none p-3 mb-5 bg-light rounded">
										<tr>
											<td class="text-center">입고번호</td>
											<td class="text-center">부품이름</td>
											<td class="text-center">수량</td>
											<td class="text-center">가격</td>
											<td class="text-center">공급업체</td>
											<td class="text-center">입고날짜</td>
										</tr>
									</thead>
									<tbody id="tbody">
										<c:forEach items="${inItemList }" var="data">
											<tr>
												<td class="text-center">${data.factoryInItemVo.factory_in_item_code }</td>
												<td class="text-center">${data.productComponentVo.comp_name }</td>
												<td class="text-center">${data.factoryInItemVo.factory_in_item_qty }</td>
												<td class="text-center">${data.productComponentVo.comp_price }</td>
												<td class="text-center">${data.supplierVo.supplier_name }</td>
												<td class="text-center"><fmt:formatDate
														value="${data.factoryInItemVo.factory_in_item_date }"
														pattern="yyyy-MM-dd" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													<fmt:formatDate
														value="${data.factoryInItemVo.factory_in_item_date }"
														pattern="HH:mm:ss" />	
												</td>
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
											class="page-link paging"
											href="./in_item_page.do?currPage=${beginPage-1 }&factory_in_item_code=${param.factory_in_item_code}&comp_no=${param.comp_no}&start_date=${param.start_date}&end_date=${param.end_date}&emp_code=${param.emp_code}&supplier_no=${param.supplier_no}&sort=${param.sort}">
												이전</a></li>
										<c:forEach begin="${beginPage }" end="${endPage }" var="i">
											<!-- 띄어쓰기 주의 -->
											<li
												class="page-item<c:if test="${currPage==i }"> active</c:if>">
												<a class="page-link paging"
												href="./in_item_page.do?currPage=${i }&factory_in_item_code=${param.factory_in_item_code}&comp_no=${param.comp_no}&start_date=${param.start_date}&end_date=${param.end_date}&emp_code=${param.emp_code}&supplier_no=${param.supplier_no}&sort=${param.sort}">${i }</a>
											</li>
										</c:forEach>
										<li
											class="page-item<c:if test="${endPage+1 > (totalCount-1)/5+1 }"> disabled</c:if>"><a
											class="page-link paging"
											href="./in_item_page.do?currPage=${endPage+1 }&factory_in_item_code=${param.factory_in_item_code}&comp_no=${param.comp_no}&start_date=${param.start_date}&end_date=${param.end_date}&emp_code=${param.emp_code}&supplier_no=${param.supplier_no}&sort=${param.sort}">다음</a></li>
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