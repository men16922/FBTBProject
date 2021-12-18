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
	
function sendResCode(store_order_res_code, i){
	
	var qqq="qqq"+i;
	var store_order_res_code = store_order_res_code;

    var xmlhttp = new XMLHttpRequest();
    xmlhttp.onreadystatechange =  function(){
                         //함수 포인터 넣는 것
       if(xmlhttp.readyState==4 && xmlhttp.status == 200){
    	   
    	   var List = JSON.parse(xmlhttp.responseText);
			
    	   var innerHtml = "<tr class=\"bg-light qq\" style=\"font-weight:bold;\"><td class=text-center>상세번호</td><td class=text-center>제품명</td><td class=text-center>확정수량</td><td class=text-center>현재상태</td><td class=text-center>담당자</td></tr>";
    	   
			for(item of List){
				
				// 데이터 값을 노출할 테이블 만들기
				innerHtml += "<tr class=\"bg-light qq\">";
				
				var store_order_detail_no = item.storeOrderDetailVo.store_order_detail_no;
				innerHtml += "<td class=\"text-center\"><a style=\"color:orange\" href='${pageContext.request.contextPath }/order/read_detail_order_page.do?store_order_detail_no="+store_order_detail_no+"'>"+store_order_detail_no+"</a></td>";
				
				var product_name = item.productVo.product_name;
				innerHtml += "<td class=\"text-center\">"+product_name+"</td>";
				
				var confirm_qty = item.processManagementVo.confirm_qty;
				innerHtml += "<td class=\"text-center\">"+confirm_qty+"</td>";
				
				var status = item.processListVo.process_name;
				innerHtml += "<td class=\"text-center\">"+status+"</td>";
				
				var end_emp_code = item.employeeVo2.emp_name;
				innerHtml += "<td class=\"text-center\">"+end_emp_code+"</td>";
				
				innerHtml += "</tr>";
			}
			document.getElementsByClassName("tbody2")[i].innerHTML = innerHtml;
    	   	
			if($('.www').eq(i).next().attr("class") != 'bg-light qq')	                
            	$('.www').eq(i).after(innerHtml);
			
       }                                 
                         
    };
    
    url = "${pageContext.request.contextPath }/production/sendResCode.do?store_order_res_code="+store_order_res_code;
    
    xmlhttp.open("get", url , true);                                  
    xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
    xmlhttp.send();
	
 }
 
function fn_spread(i)
{

   if($(".detailList").eq(i).is(":visible")){
        $(".detailList").eq(i).css("display","none");
    }
    else{
        $(".detailList").eq(i).css("display","block");
    }

}

function remove_tr(i){
	   var qqq="qqq"+(i);
	  //$("#tbody").find(".qq").remove();
	   //$("#tbody").find("."+qqq).remove();
}

</script>

</head>

<body>
	<jsp:include page="../commons/global_nav.jsp"></jsp:include>
	<jsp:include page="../commons/production_nav.jsp"></jsp:include>

	<div class="container-fluid">
		<!-- 메인 기능들이 나오는 row -->
		<div class="row">
			<div class="col-1"></div>

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
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">상품
									발주조회</span>
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
											<li><a style="color: gray; font-size: 14px" href="#">발주관리</a>
												> <a style="color: gray; font-size: 14px"
												href="${pageContext.request.contextPath }/production/product_order_list_page.do">발주조회</a>
											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<div class="row">

							<div class="col">
								<form action="./product_order_list_page.do" method="get">
									<table class="table bg-light">
										<tbody>
											<tr>
												<th>발주번호</th>
												<td><input placeholder="발주번호를 입력해주세요."
													name="store_order_res_no" type="text" class="form-control form-control-sm"></td>
												<th>지점명</th>
												<td><c:if test="${!empty branchList }">
														<select name="branch_name" id="branch_name" class="form-control form-control-sm">
															<option value="">지점을 선택해주세요</option>
															<c:forEach var="data" items="${branchList }">
																<option value="${data.branchVo.branch_name }">${data.branchVo.branch_name }</option>
															</c:forEach>
														</select>
													</c:if></td>
											</tr>
											<tr>
												<th>발주일자</th>
												<td scope="col"><input placeholder="발주일자를 입력해주세요."
													name="start_date" type="date" class="form-control form-control-sm"></td>
												<td colspan="2"><input placeholder="발주일자를 입력해주세요."
													name="end_date" type="date" class="form-control form-control-sm" style="width:300px;"></td>
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
						</div>
						<div class="mt-1 row">
							<div class="col">
								<div class="row">
									<div class="col"><select name="sort" id="sort" onchange="sendSort()">
											<option value=0>정렬선택</option>
											<option value=1>지점명순</option>
											<option value=2>발주일자순</option>
										</select></div>
									<div class="col"></div>
									
								</div>
							</div>
						</div>

						<div class="row mt-1">
							<!-- 테이블  -->

							<div class="col">
								<table
									class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
									<thead class="shadow-none p-3 mb-5 bg-light rounded">
										<tr>
											<td class="text-center">발주번호</td>
											<td class="text-center">발주지점</td>
											<td class="text-center">발주일자</td>
										</tr>
									</thead>
									<tbody id="tbody">
										<c:forEach items="${list }" var="data" varStatus="i">
											<tr class="www">
												<!-- 발주번호 누르면 밑에 테이블로 ajax로  발주 상세번호 생성. 페이지 넘기지 않고 -->
												<td class="text-center">
													<input type="hidden" id="store_order_res_code" value="${data.storeOrderReservationVo.store_order_res_code }">
														<a href="javascript:remove_tr(${i.index}); javascript:sendResCode('${data.storeOrderReservationVo.store_order_res_code }',${i.index });">${data.storeOrderReservationVo.store_order_res_no }</a>
														<!-- <a href="javascript:remove_tr(${i.index}); javascript:add_tr('${xxx.storeOrderReservationVo.store_order_res_no}',${i.index});">${xxx.storeOrderReservationVo.store_order_res_no}</a> -->
													<div class="detailList"  style="display:none">
													<table class="table table-hover shadow-sm p-3 mt-3 bg-white rounded">
														<thead class="thead2"></thead>
														<tbody class="tbody2"></tbody>		
													</table>
													</div>
												</td>
												<td class="text-center">${data.branchVo.branch_name }</td>
												<td class="text-center"><fmt:formatDate
														value="${data.storeOrderReservationVo.store_order_res_date }"
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
											class="page-link paging"
											href="./product_order_list_page.do?currPage=${beginPage-1 }&store_order_res_no=${param.store_order_res_no}&branch_name=${param.branch_name}&start_date=${param.start_date}&end_date=${param.end_date }&sort=${param.sort}">이전</a></li>
										<c:forEach begin="${beginPage }" end="${endPage }" var="i">
											<!-- 띄어쓰기 주의 -->
											<li
												class="page-item<c:if test="${currPage==i }"> active</c:if>">
												<a class="page-link paging"
												href="./product_order_list_page.do?currPage=${i }&store_order_res_no=${param.store_order_res_no}&branch_name=${param.branch_name}&start_date=${param.start_date}&end_date=${param.end_date }&sort=${param.sort}">${i }</a>
											</li>
										</c:forEach>
										<li
											class="page-item<c:if test="${endPage+1 > (totalCount-1)/5+1 }"> disabled</c:if>"><a
											class="page-link paging"
											href="./product_order_list_page.do?currPage=${endPage+1 }&store_order_res_no=${param.store_order_res_no}&branch_name=${param.branch_name}&start_date=${param.start_date}&end_date=${param.end_date }&sort=${param.sort}">다음</a></li>
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