<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>발주조회</title>
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet"
	type="text/css">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
</head>
<body>

	<!-- global bavbar -->
	<jsp:include page="../commons/global_nav.jsp"></jsp:include>


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
										style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">발주상세보기</span>
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
													> <a style="color: gray; font-size: 14px" href="#">발주조회</a>
													> <a style="color: gray; font-size: 14px">발주상세보기</a>

												</li>
											</ul>
										</div>
									</div>
								</div>
							</div>
							<!--1. 타이틀, 네비게이터 끝-->



							<!--2. 주문 기본정보-->
							<div class="row mx-auto">
								<div class="col">

									<!--기본정보(제목-->
									<div class="row mt-5">
										<div class="col-2 pt-2 mb-2"
											style="text-align: center; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 20px;">[기본정보]</div>
										<div class="col"></div>
									</div>

									<!--기본정보(내용)-->
									<div class="row">
										<div class="col">
											<table
												class="table table-hover shadow-sm p-3 mb-3 bg-white rounded"
												style="text-align: center;">
												<thead class="shadow-none p-3 mb-5 bg-light rounded">
													<tr>
														<th>발주번호</th>
														<th>상세번호</th>
														<th>제품명</th>
														<th>초기수량</th>
														<th>확정수량</th>
														<th>발주매장</th>
														<th>현재상태</th>
													</tr>
												</thead>
												<tbody>
													<tr>
														<td>${detailMap.storeOrderReservationVo.store_order_res_no }</td>
														<td>${detailMap.storeOrderDetailVo.store_order_detail_no }</td>
														<td>${detailMap.productVo.product_name }</td>
														<td>${detailMap.storeOrderDetailVo.first_order_qty }</td>
														<td>${detailMap.processManagementVo.confirm_qty }</td>
														<td>${detailMap.branchVo.branch_name }</td>
														<td>
														
														<c:choose>    
														 	<c:when test="${detailMap.processStatusVo.process_no==3 && detailMap.processStatusVo.flag eq 'R'.charAt(0)}">						 		 
														 		공장수정중
														 	</c:when>
														 	<c:when test="${detailMap.processStatusVo.flag eq 'N'.charAt(0)}">						 		 
														 		발주반려
														 	</c:when>							 	
														 	<c:otherwise>
														 		${detailMap.processListVo.process_name }
														 	</c:otherwise>
														 </c:choose> 
													
														</td>
													</tr>
												</tbody>
											</table>
										</div>
									</div>
									
									<!-- 1차검토, 출고요청 버튼 -->
									<div class="row">
										<div class="col"></div>
										
										<!-- 공장재선택 -->
										<c:if test="${detailMap.processStatusVo.process_no==3 && detailMap.processStatusVo.flag eq 'R'.charAt(0) && sessionUser.employeeRankVo.rank_no <= 5}">
										<div class="col-2">
											<a href="${pageContext.request.contextPath}/logistics/update_factory_page.do?store_order_detail_no=${detailMap.storeOrderDetailVo.store_order_detail_no}&return_page=1"><input type="button" class="btn btn-primary btn-block" value="공장 재선택"></a>
	
										</div>
										</c:if>
										
										<!-- 출고요청 -->
										<c:if test="${detailMap.processStatusVo.process_no==2 && detailMap.processStatusVo.flag eq 'Y'.charAt(0) && sessionUser.employeeRankVo.rank_no <= 5}">
										<div class="col-2">
											<a href="${pageContext.request.contextPath}/logistics/update_confirm_qty_page.do?store_order_detail_no=${detailMap.storeOrderDetailVo.store_order_detail_no}&return_page=1"><input type="button" class="btn btn-primary btn-block" value="출고요청"></a>
	
										</div>
										</c:if>
										
										<!-- 1차검토 -->
										<c:if test="${detailMap.processStatusVo.process_no==1 && detailMap.processStatusVo.flag eq 'Y'.charAt(0)}">
										<div class="col-2">
											<a href="${pageContext.request.contextPath}/logistics/update_first_qty_page.do?store_order_detail_no=${detailMap.storeOrderDetailVo.store_order_detail_no}&return_page=1"><input type="button" class="btn btn-primary btn-block" value="1차검토"></a>			
										</div>
										</c:if>
										
										<!-- 발주반려 -->
										<c:if test="${detailMap.processStatusVo.process_no <= 3 && detailMap.processStatusVo.flag eq 'Y'.charAt(0)}">
										<div class="col-2">
											<a href="${pageContext.request.contextPath}/logistics/update_delete_page.do?store_order_detail_no=${detailMap.storeOrderDetailVo.store_order_detail_no}&return_page=1"><input type="button" class="btn btn-primary btn-block" value="발주반려"></a>			
										</div>
										</c:if>
										
									</div>

								</div>
							</div>

							<!--3. 주문배송상태,  프로세스 이미지-->
							<div class="row mb-5">
								<div class="col">

									<!--프로세스(제목)-->
									<div class="row mt-5">
										<div class="col-2 pt-2 mb-2"
											style="text-align: center; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 18px;">[발주/배송상태]</div>
										<div class="col"></div>
									</div>

									<!--프로세스(첫째줄)-->
									<div class="row">
										<div class="col-10 mx-auto">
											<div class="row">
												<!--주문요청-->
												<div class="col-3">
													<!--아이콘-->
													<div class="row">
														<div class="col ml-5">
														<c:choose>
															<c:when test="${detailMap.processStatusVo.process_no == 1 && detailMap.processStatusVo.flag eq 'Y'.charAt(0)}">
															<img src="${pageContext.request.contextPath}/resources/img/order_request.png" class="img-fluid"
																width="100px" height="100px">
															</c:when>
															<c:when test="${detailMap.processStatusVo.process_no == 1 && detailMap.processStatusVo.flag eq 'N'.charAt(0)}">
															<img src="${pageContext.request.contextPath}/resources/img/order_request_not.png" class="img-fluid"
																width="100px" height="100px">
															</c:when>
															<c:otherwise>
																<img src="${pageContext.request.contextPath}/resources/img/order_request_not.png" class="img-fluid"
																	width="100px" height="100px">
															</c:otherwise>
														</c:choose>
															
														</div>
													</div>

													<!--아이콘 정보-->
													<div class="row">
														<div class="col ml-2">
															<img src="${pageContext.request.contextPath}/resources/img/step1.png">
														</div>
													</div>
												</div>


												<!--화살표-->
												<div class="col">
													<img src="${pageContext.request.contextPath}/resources/img/direc_icon.png" class="img-fluid"
														style="padding: 0px 3px 0px 3px; margin-top: 16px;">
												</div>



												<!--1차검토-->
												<div class="col-3">
													<!--아이콘-->
													<div class="row">
														<div class="col ml-4">
														<c:choose>
															<c:when test="${detailMap.processStatusVo.process_no == 2 && detailMap.processStatusVo.flag eq 'Y'.charAt(0) || detailMap.processStatusVo.flag eq 'U'.charAt(0)}">
															<img src="${pageContext.request.contextPath}/resources/img/first_check.png" class="img-fluid"
																width="100px" height="100px">
															</c:when>
															<c:when test="${detailMap.processStatusVo.process_no == 2 && detailMap.processStatusVo.flag eq 'N'.charAt(0)}">
															<img src="${pageContext.request.contextPath}/resources/img/first_check_not.png" class="img-fluid"
																width="100px" height="100px">
															</c:when>
															<c:otherwise>
																<img src="${pageContext.request.contextPath}/resources/img/first_check_not.png" class="img-fluid"
																	width="100px" height="100px">
															</c:otherwise>
														</c:choose>
													
														</div>
													</div>

													<!--아이콘 정보-->
													<div class="row">
														<div class="col ml-2">
															<img src="${pageContext.request.contextPath}/resources/img/step2.png">
														</div>
													</div>
												</div>


												<!--화살표-->
												<div class="col">
													<img src="${pageContext.request.contextPath}/resources/img/direc_icon.png" class="img-fluid"
														style="padding: 0px 3px 0px 3px; margin-top: 16px;">
												</div>



												<!--출고요청-->
												<div class="col-3">
													<!--아이콘-->
													<div class="row">
														<div class="col ml-4">
														<c:choose>
															<c:when test="${detailMap.processStatusVo.process_no == 3 && detailMap.processStatusVo.flag eq 'Y'.charAt(0) || detailMap.processStatusVo.flag eq 'U'.charAt(0) || detailMap.processStatusVo.flag eq 'R'.charAt(0)}">
															<img src="${pageContext.request.contextPath}/resources/img/last_check.png" class="img-fluid"
																width="100px" height="100px">
															</c:when>
															<c:when test="${detailMap.processStatusVo.process_no == 3 && detailMap.processStatusVo.flag eq 'N'.charAt(0)}">
															<img src="${pageContext.request.contextPath}/resources/img/last_check_not.png" class="img-fluid"
																width="100px" height="100px">
															</c:when>
															<c:otherwise>
																<img src="${pageContext.request.contextPath}/resources/img/last_check_not.png" class="img-fluid"
																	width="100px" height="100px">
															</c:otherwise>
														</c:choose>
														</div>
													</div>

													<!--아이콘 정보-->
													<div class="row">
														<div class="col ml-2">
															<img src="${pageContext.request.contextPath}/resources/img/step3.png">
														</div>
													</div>

												</div>



												<!--화살표-->
												<div class="col">
													<img src="${pageContext.request.contextPath}/resources/img/direc_icon.png" class="img-fluid"
														style="padding: 0px 3px 0px 3px; margin-top: 16px;">
												</div>

											</div>
										</div>
									</div>

									<!--프로세스(둘째줄)-->
									<div class="row mt-4">
										<div class="col-8 mx-auto">
											<div class="row">
												<!--배송준비중-->
												<div class="col-3">
													<!--아이콘-->
													<div class="row ml-2">
														<div class="col">
														<c:choose>
															<c:when test="${detailMap.processStatusVo.process_no == 4}">
															<img src="${pageContext.request.contextPath}/resources/img/delivery_ready.png" class="img-fluid"
																width="100px" height="100px">
															</c:when>
															<c:otherwise>
																<img src="${pageContext.request.contextPath}/resources/img/delivery_ready_not.png" class="img-fluid"
																	width="100px" height="100px">
															</c:otherwise>
														</c:choose>
														</div>
													</div>

													<!--아이콘 정보-->
													<div class="row">
														<div class="col">
															<img src="${pageContext.request.contextPath}/resources/img/step4.png">
														</div>
													</div>
												</div>



												<!--화살표-->
												<div class="col">
													<img class="img-fluid"
														style="padding: 0px 10px 0px 10px; margin-top: 16px;"
														src="${pageContext.request.contextPath}/resources/img/direc_icon.png">
												</div>



												<!--배송중-->
												<div class="col-3">
													<!--아이콘-->
													<div class="row ml-2">
														<div class="col">														
														<c:choose>
															<c:when test="${detailMap.processStatusVo.process_no == 5}">
															<img src="${pageContext.request.contextPath}/resources/img/delivery_ing.png" class="img-fluid"
																width="100px" height="100px">
															</c:when>
															<c:otherwise>
																<img src="${pageContext.request.contextPath}/resources/img/delivery_not.png" class="img-fluid"
																	width="100px" height="100px">
															</c:otherwise>
														</c:choose>
														</div>
													</div>

													<!--아이콘 정보-->
													<div class="row">
														<div class="col">
															<img src="${pageContext.request.contextPath}/resources/img/step5.png">
														</div>
													</div>
												</div>


												<!--화살표-->
												<div class="col">
													<img class="img-fluid"
														style="padding: 0px 10px 0px 10px; margin-top: 16px;"
														src="${pageContext.request.contextPath}/resources/img/direc_icon.png">
												</div>


												<!--배송완료-->
												<div class="col-3">
													<!--아이콘-->
													<div class="row ml-2">
														<div class="col">
														<c:choose>
															<c:when test="${detailMap.processStatusVo.process_no == 6}">
															<img src="${pageContext.request.contextPath}/resources/img/delivery_success.png" class="img-fluid"
																width="100px" height="100px">
															</c:when>
															<c:otherwise>
																<img src="${pageContext.request.contextPath}/resources/img/delivery_success_not.png" class="img-fluid"
																	width="100px" height="100px">
															</c:otherwise>
														</c:choose>
															
														</div>
													</div>

													<!--아이콘 정보-->
													<div class="row">
														<div class="col">
															<img src="${pageContext.request.contextPath}/resources/img/step6.png">
														</div>
													</div>
												</div>
											</div>

										</div>

									</div>

								</div>
							</div>

							<!--프로세스 배송중부터 보이게-->
							<!--4. 배송정보-->
							<c:if test="${detailMap.processStatusVo.process_no >= 3 }">
							<div class="row mb-5">
								<div class="col">

									<!--배송정보(제목)-->
									<div class="row">
										<div class="col-2 pt-2 mb-2"
											style="text-align: center; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 20px;">[배송정보]</div>
										<div class="col"></div>
									</div>

									<!--배송정보(내용)-->
									<div class="row">
										<div class="col">
											<table
												class="table table-hover shadow-sm p-3 mb-5 bg-white rounded"
												style="text-align: center;">
												<thead class="shadow-none p-3 mb-5 bg-light rounded">
													<tr>
														<th>출고공장</th>
														<th>운송장번호</th>
														<th>배송지</th>
														<th>배송출발일</th>
														<th>도착예정일</th>

													</tr>
												</thead>
												<tbody>
													<tr>
														<td>${detailMap.factoryBranchVo.branch_name }</td>
														<td>
															<c:choose>
																<c:when test="${detailMap.processStatusVo.process_no >= 5}">
																	${detailMap.deliveryVo.invoice_no }
																</c:when>
																<c:otherwise>
																	-
																</c:otherwise>
															</c:choose>
														</td>
														<td>
															<c:choose>
																<c:when test="${detailMap.processStatusVo.process_no >= 5}">
																	${detailMap.factoryBranchVo.branch_address }
																</c:when>
																<c:otherwise>
																	-
																</c:otherwise>
															</c:choose>
														</td>
														<td>
															<c:choose>
																<c:when test="${detailMap.processStatusVo.process_no >= 5}">
																	<fmt:formatDate value="${detailMap.deliveryVo.delivery_date }" pattern="yy.MM.dd.hh.mm.ss" />
																</c:when>
																<c:otherwise>
																	-
																</c:otherwise>
															</c:choose>
														</td>
														<td>
															<c:choose>
																<c:when test="${detailMap.processStatusVo.process_no >= 5}">
																	<fmt:formatDate value="${detailMap.deliveryVo.arrive_delivery_date }" pattern="yy.MM.dd.hh.mm.ss" />
																</c:when>
																<c:otherwise>
																	-
																</c:otherwise>
															</c:choose>
														</td>
											
													</tr>
												</tbody>
											</table>
										</div>
									</div>



								</div>
							</div>
							</c:if>

							<!--5. 상세정보-->
							<div class="row mx-auto">
								<div class="col">

									<!--상세정보(제목)-->
									<div class="row mx-auto">
										<div class="col-2 pt-2 mb-2"
											style="text-align: center; font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 20px;">[상세정보]</div>
										<div class="col"></div>
									</div>

									<!--상세정보(내용)-->
									<div class="row">
										<div class="col">
											
											<table
												class="table table-hover shadow-sm p-3 mb-5 bg-white rounded"
												style="text-align: center;">
												<thead class="shadow-none p-3 mb-5 bg-light rounded">
													<tr>
														<th>발주상태</th>
														<th>승인여부</th>
														<th>처리날짜</th>
														<th>처리지점</th>
														<th>담당자</th>
													</tr>
												</thead>
												
												<c:forEach var="data" items="${statusList }">
												<tbody>
													<tr>
														<td>
														<c:choose>
															<c:when test="${data.processStatusVo.process_no==2 && data.processStatusVo.flag eq 'U'.charAt(0)}">
		                     								1차수정 
		                     								</c:when>
															<c:when test="${data.processStatusVo.process_no==3 && data.processStatusVo.flag eq 'U'.charAt(0)}">
									                     	2차수정 
									                     	</c:when>
															<c:when test="${data.processStatusVo.process_no==3 && data.processStatusVo.flag eq 'R'.charAt(0)}">
									                     	공장수정중
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.flag eq 'N'.charAt(0)}">
		                     								발주반려
		                     								</c:when>
															<c:otherwise>
		                     								${data.processListVo.process_name }
		                     								</c:otherwise>
														</c:choose>
														</td>
														
														<td>
															${data.processStatusVo.flag}
														</td>
														<td>
														<fmt:formatDate
																value="${data.processStatusVo.confirm_date}"
																pattern="yy-MM-dd" /> &nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate
																value="${data.processStatusVo.confirm_date}"
																pattern="HH:mm:ss" />
														</td>
														
														<td>
													
														<c:choose>
															<c:when test="${data.processStatusVo.process_no==1 && data.processStatusVo.flag == 'Y'.charAt(0)}">
		                     								${detailMap.branchVo.branch_name}
		                     								</c:when>
															<c:when test="${data.processStatusVo.process_no==2 && data.processStatusVo.flag == 'U'.charAt(0)}">
									                     	${detailMap.firstCheckBranchVo.branch_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==2 && data.processStatusVo.flag == 'Y'.charAt(0)}">
									                     	${detailMap.firstCheckBranchVo.branch_name}
									                     	</c:when>
															<c:when test="${data.processStatusVo.process_no==3 && data.processStatusVo.flag == 'U'.charAt(0)}">
									                     	${detailMap.lastCheckBranchVo.branch_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==3 && data.processStatusVo.flag == 'Y'.charAt(0)}">
									                     	${detailMap.lastCheckBranchVo.branch_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==3 && data.processStatusVo.flag == 'R'.charAt(0)}">
									                     	${detailMap.lastCheckBranchVo.branch_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==4 && data.processStatusVo.flag == 'Y'.charAt(0)}">
									                     	${detailMap.factoryBranchVo.branch_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==5 && data.processStatusVo.flag == 'Y'.charAt(0)}">
									                     	${detailMap.factoryBranchVo.branch_name}
									                     	</c:when>
									   						<c:when test="${data.processStatusVo.process_no==6 && data.processStatusVo.flag == 'Y'.charAt(0)}">
									                     	${detailMap.factoryBranchVo.branch_name}
									                     	</c:when>
															<c:otherwise>
		                     								-
		                     								</c:otherwise>
														</c:choose>
														</td>
														
														<td>
														<c:choose>
															<c:when test="${data.processStatusVo.process_no==1 && data.processStatusVo.flag == 'Y'.charAt(0)}">
		                     								${detailMap.orderResEemployeeVo.emp_name}
		                     								</c:when>
		                     								<c:when test="${data.processStatusVo.process_no==1 && data.processStatusVo.flag == 'N'.charAt(0)}">
		                     								-
		                     								</c:when>
															<c:when test="${data.processStatusVo.process_no==2 && data.processStatusVo.flag == 'U'.charAt(0)}">
									                     	${detailMap.firstCheckEmployeeVo.emp_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==2 && data.processStatusVo.flag == 'Y'.charAt(0)}">
									                     	${detailMap.firstCheckEmployeeVo.emp_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==2 && data.processStatusVo.flag == 'N'.charAt(0)}">
									                     	-
									                     	</c:when>
															<c:when test="${data.processStatusVo.process_no==3 && data.processStatusVo.flag == 'U'.charAt(0)}">
									                     	${detailMap.lastCheckEmployeeVo.emp_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==3 && data.processStatusVo.flag == 'Y'.charAt(0)}">
									                     	${detailMap.lastCheckEmployeeVo.emp_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==3 && data.processStatusVo.flag == 'N'.charAt(0)}">
									                     	-
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==4}">
									                     	${detailMap.factoryEmployeeVo.emp_name}
									                     	</c:when>
									                     	<c:when test="${data.processStatusVo.process_no==5}">
									                     	${detailMap.deliveryEmployeeVo.emp_name}
									                     	</c:when>
									   						<c:when test="${data.processStatusVo.process_no==6}">
									                     	${detailMap.deliveryEmployeeVo.emp_name}
									                     	</c:when>
									                     	
															<c:otherwise>
		                     								-
		                     								</c:otherwise>
														</c:choose>

														</td>
													</tr>
												</tbody>
												</c:forEach>
											</table>
										</div>
									</div>
								</div>
							</div>
						<div class="row mb-5">
							<div class="col"></div>
							<div class="col"></div>
							<div class="col"></div>
							<div class="col"></div>
			
							<div class="col">
							<a href="${pageContext.request.contextPath }/logistics/read_list_order_page.do"><input type="button" class="btn btn-primary btn-block" value="목록으로"></a>
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