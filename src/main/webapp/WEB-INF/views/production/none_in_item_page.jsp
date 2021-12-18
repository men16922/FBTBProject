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
	 function frm_submit() {
			// form id 가져오기
			var frm = document.getElementById("frm");

			var check = document.getElementsByClassName("check");
			isChecked = true;
			
			//alert(check);
			
			for(var checkBox of check){
				if(checkBox.checked == true){
					isChecked = false;
				}
			}
			
			if(isChecked == true) {
				alert("입고등록할 발주를 선택해주세요");
				return;
			} else {
				frm.submit();  
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
            <div class="col" style="background-color:white">
                <!--1. 타이틀, 네비게이터 시작-->
                <div class="row mt-3">
                    <!--타이틀-->
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">입고등록</span></div>
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
                                        <a style="color: gray; font-size: 14px" href="#">재고관리</a>
                                        >
                                        <a style="color: gray; font-size: 14px" href="${pageContext.request.contextPath }/production/none_in_item_page.do">발주부품등록</a>
                                      
                                    </li>
                                </ul>             
                            </div>      
                        </div>
                    </div>
                </div>
                <!--1. 타이틀, 네비게이터 끝-->

                <form action="./in_item_enroll_process.do" method="get" id="frm">

					<div class="row mt-3">
						<!-- 테이블  -->
						<div class="col">
							<table
								class="table table-hover shadow-sm p-3 mb-5 bg-white rounded">
								<thead class="shadow-none p-3 mb-5 bg-light rounded">
									<tr>
										<td class="text-center"></td>
										<td class="text-center">발주상세번호</td>
										<td class="text-center">부품명</td>
										<td class="text-center">수량</td>
										<td class="text-center">승인여부</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${list }" var="data">
										<tr>
											<td class="text-center"><input type="checkbox"
												class="check" name="factory_order_detail_no"
												value="${data.factoryOrderDetailVo.factory_order_detail_no }">
												<input type="hidden" name="check_approval" value=""
												id="check_approval"></td>
											<td class="text-center">${data.factoryOrderDetailVo.factory_order_detail_no }
											</td>
											<td class="text-center">${data.productComponentVo.comp_name }
												<input type="hidden" name="comp_no"
												value="${data.productComponentVo.comp_no }">
											</td>
											<td class="text-center">${data.factoryOrderDetailVo.factory_order_qty }
												<input type="hidden" name="factory_in_item_qty"
												value="${data.factoryOrderDetailVo.factory_order_qty }">
											</td>
											<td class="text-center">${data.factoryOrderDetailVo.check_approval }</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
					<div class="row mt-1">

						<div class="col"></div>
						<div class="col"></div>
						<div class="col">
							<input type="button" value="등록하기" class="btn btn-outline-primary btn-sm btn-block mb-5"
								onclick="frm_submit()">
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