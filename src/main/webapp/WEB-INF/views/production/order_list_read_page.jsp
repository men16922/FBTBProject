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
			<div class="col-1"></div>

			<!--  사이드바 시작  -->
			<div class="col-2"
				style="padding: 0; border-right: 2px solid #e8ecf1; border-left: 2px solid #e8ecf1;">
				<ul class="list-group list-group-flush">
					<!--  수정할 부분 시작 -->
					<li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;">
					<h2 class="mt-3" align="center">부품공급</h2></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/order_enroll_page.do">부품 발주등록</a></h5></li>
					<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/order_list_page.do">발주조회</a></h5></li>
					<!--  수정할 부분 끝 -->
				</ul>
			</div>
			<!--  사이드바 끝   -->


			<!--  메인 기능 나오는 곳 -->
        <div class="col pl-5 pr-5">
        <!--메인기능 내용시작-->
        <div class="row">
            <!--메인기능 메인col-->
            <div class="col" style="background-color:white">
                <!--1. 타이틀, 네비게이터 시작-->
                <div class="row mt-3">
                    <!--타이틀-->
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">부품 발주상세내역</span></div>
                    <div class="col"></div>
                    <!---네비게이터-->
                    <div class="col-6">
                        <div class="row">                                   
                            <div class="col"></div>      
                        </div>
                        <div class="row"> 
                            <div class="col-2"></div> 
                            <div class="col mt-5" style="display:inline;">     
                                <ul style="list-style:none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
                                    <li>
                                        <a style="color: gray; font-size: 14px" href="#">부품공급</a>
                                        >
                                        <a style="color: gray; font-size: 14px" href="${pageContext.request.contextPath }/production/order_list_page.do">발주조회</a>
                                          >
                                        <a style="color: gray; font-size: 14px" href="${pageContext.request.contextPath }/production/order_list_read_page.do">발주상세내역</a>
                                    </li>
                                </ul>             
                            </div>      
                        </div>
                    </div>
                </div>
                <!--1. 타이틀, 네비게이터 끝-->

                <div class="row mt-3">
			<!-- 테이블  -->
			<div class="col">
				<table class="table table-hover shadow-sm p-3 mb-5 bg-white rounded" >
					<thead class="shadow-none p-3 mb-5 bg-light rounded">
						<tr>
							<td class="text-center">발주상세번호</td>
							<td class="text-center">사원명</td>
							<td class="text-center">부품명</td>
							<td class="text-center">발주수량</td>
							<td class="text-center">공급업체</td>
							<td class="text-center">발주일자</td>
							<td class="text-center">승인여부</td>
							<td class="text-center">비고</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${order }" var="data">
							<tr>
								<td class="text-center">${data.factoryOrderDetailVo.factory_order_detail_no }</td>
								<td class="text-center">${data.employeeVo.emp_name }</td>
								<td class="text-center">${data.productComponentVo.comp_name }</td>
								<td class="text-center">${data.factoryOrderDetailVo.factory_order_qty }</td>
								<td class="text-center">${data.supplierVo.supplier_name }</td>
								<td class="text-center">
									<fmt:formatDate value="${data.factoryOrderVo.factory_order_res_date }" pattern="yyyy-MM-dd"/></td>
								<td class="text-center">${data.factoryOrderDetailVo.check_approval }</td>
								<td class="text-center">${data.factoryOrderDetailVo.factory_order_note }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="row mt-5">
			<div class="col"></div>
			<div class="col"></div>
			<div class="col">
				<a class="btn btn-outline-secondary btn-sm btn-block mb-5"
					href="${pageContext.request.contextPath }/production/order_list_page.do">목록으로</a>
			</div>
		</div>
                    
            </div>        
        </div>
        <!--메인기능 내용끝-->

       </div>
         <!--  메인기능 나오는 곳 끝 -->

			<div class="col-1"></div>

		</div>

		<!--  footer  -->
		<jsp:include page="../commons/footer.jsp"></jsp:include>
	</div>
	
	<jsp:include page="../commons/alert_unread_message.jsp"></jsp:include>
	<jsp:include page="../production/production_message_alert.jsp"></jsp:include>
      <jsp:include page="../production/production_order_message_alert.jsp"></jsp:include>
      
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>
</body>
</html>