<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<meta charset="UTF-8">
<title>쪽지쓰기</title>

<script type="text/javascript">

	function sendDeptNo(){
		
		var inputValue = document.getElementById("dept_no").value; 
		
		if(inputValue == ""){
			
			var isSelectBox = document.getElementById("branch_select")
			if(isSelectBox != null){
				
				 var length = isSelectBox.childNodes.length;
				 for(var i=0; i<length; i++){
					 isSelectBox.removeChild(isSelectBox.childNodes[0]);
				 }

			}
			
			
			var isSelectBox = document.getElementById("emp_name_select")
			if(isSelectBox != null){
				
				 var length = isSelectBox.childNodes.length;
				 for(var i=0; i<length; i++){
					 isSelectBox.removeChild(isSelectBox.childNodes[0]);
				 }

			}

		}
		
		if(inputValue == "30"){
			inputValue = 2
		}
		
		if(inputValue == "40"){
			inputValue = 3
		}
		
		if(inputValue == "10" || inputValue == "21" || inputValue == "22" || inputValue == "23"){
			
			var isSelectBox = document.getElementById("branch_select")
			if(isSelectBox != null){
				
				 var length = isSelectBox.childNodes.length;
				 for(var i=0; i<length; i++){
					 isSelectBox.removeChild(isSelectBox.childNodes[0]);
				 }

			}
			
			
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange =  function(){
										//함수 포인터 넣는 것
				if(xmlhttp.readyState==4 && xmlhttp.status == 200){
					
					//얘도 리스트
					var empList = JSON.parse(xmlhttp.responseText);
		
					
					//리스트(배열 만드는 것)
					var emp_name_select = document.getElementById("emp_name_select");
			
					
					//팀이름 바꿀 때마다 셀렉트 박스 안에 값들 리무브 차일드 해주기
					 var length = emp_name_select.childNodes.length;
					 for(var i=0; i<length; i++){
						 emp_name_select.removeChild(emp_name_select.childNodes[0]);
					 }
					
					 
					
					 //JSON으로 받아온 리스트(empList) for문 돌려서 옵션태그 추가하기
					for(emp of empList){
						var option = document.createElement("option");
						
						option.innerText = emp.emp_name;
						option.value = emp.emp_code;
						
						emp_name_select.appendChild(option);
					
					}	
					
				}											
										
			};
			
			xmlhttp.open("post","${pageContext.request.contextPath }/message/sendDeptNo.do", true); 											
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlhttp.send("dept_no="+inputValue);
			
			
			
		}
		
		
		if(inputValue == 2 || inputValue == 3){
			
			var isSelectBox = document.getElementById("emp_name_select")
			if(isSelectBox != null){
				
				 var length = isSelectBox.childNodes.length;
				 for(var i=0; i<length; i++){
					 isSelectBox.removeChild(isSelectBox.childNodes[0]);
				 }

			}
			
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange =  function(){
										//함수 포인터 넣는 것
				if(xmlhttp.readyState==4 && xmlhttp.status == 200){
					
					//얘도 리스트
					var branchList = JSON.parse(xmlhttp.responseText);
			
					
			
					//리스트(배열 만드는 것)
					var selectBox = document.createElement("SELECT");
					selectBox.setAttribute("id", "branch_name_select");
					selectBox.setAttribute("class", "form-control");
					selectBox.setAttribute("onchange", "sendBranchNO()");
					var branch_select = document.getElementById("branch_select");
					
					
					var length1 = branch_select.childNodes.length;
					for(var i=0; i<length1; i++){
						branch_select.removeChild(branch_select.childNodes[0]);
					 }
						
					
					branch_select.appendChild(selectBox);
					  
					
				
					
					//팀이름 바꿀 때마다 셀렉트 박스 안에 값들 리무브 차일드 해주기
					 var length = branch_name_select.childNodes.length;
					 for(var i=0; i<length; i++){
						 branch_name_select.removeChild(branch_name_select.childNodes[0]);
					 }
						
					
					var option = document.createElement("option");
					option.innerText = "지점명";
					option.value = "";
					branch_name_select.appendChild(option);
						
					 //JSON으로 받아온 리스트(empList) for문 돌려서 옵션태그 추가하기
					for(branch of branchList){
						
						var option = document.createElement("option");
						option.innerText = branch.branch_name;
						option.value = branch.branch_no;
						
						branch_name_select.appendChild(option);
					
					}	
					
				}											
										
			};
			
			xmlhttp.open("post","${pageContext.request.contextPath }/message/sendBranchTypeNo.do", true); 											
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlhttp.send("branch_type_no="+inputValue);
			
	
		}
			

	}

	
	function sendBranchNO(){
		
		var inputValue = document.getElementById("branch_name_select").value; 
	
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange =  function(){
									//함수 포인터 넣는 것
			if(xmlhttp.readyState==4 && xmlhttp.status == 200){
				
				//얘도 리스트
				var empList = JSON.parse(xmlhttp.responseText);
	
				
				//리스트(배열 만드는 것)
				var emp_name_select = document.getElementById("emp_name_select");
		
				
				//팀이름 바꿀 때마다 셀렉트 박스 안에 값들 리무브 차일드 해주기
				 var length = emp_name_select.childNodes.length;
				 for(var i=0; i<length; i++){
					 emp_name_select.removeChild(emp_name_select.childNodes[0]);
				 }
					
				
				 //JSON으로 받아온 리스트(empList) for문 돌려서 옵션태그 추가하기
				for(emp of empList){
					var option = document.createElement("option");
					
					option.innerText = emp.emp_name;
					option.value = emp.emp_code;
					
					emp_name_select.appendChild(option);
				
				}	
				
			}											
									
		};
		
		xmlhttp.open("post","${pageContext.request.contextPath }/message/sendBranchNo.do", true); 											
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("branch_no="+inputValue);
		
	}
	
	
	
	
	function sub_form(){
		
		
		var frm = document.getElementById("frm");
		
		var emp_name_select = document.getElementById("emp_name_select").value;
		var msg_title = document.getElementById("msg_title").value;
		var msg_content = document.getElementById("msg_content").value;
		
		if(emp_name_select == ""){
			alert("받는 사람을 선택해주세요.");
			return;
		}else if(msg_title == ""){
			alert("제목 입력해주세요.");
			return;
		}else if(msg_content == ""){
			alert("내용을 입력해주세요.");
			return;	
		}else{
			frm.submit();
		}


	}


</script>


</head>
<body>
	<!-- navbar -->
	<jsp:include page="../commons/global_nav.jsp"></jsp:include>



	<!-- logistics navbar -->
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

			<!--  사이드바 시작  -->
			<div class="col-2"
				style="padding: 0; border-right: 2px solid #e8ecf1;">
				<ul class="list-group list-group-flush">
					<!--  수정할 부분 시작 -->
					<li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">쪽지함</h2></li>
               		<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/message/list_message_page.do">받은쪽지함</a></h5></li>
               		<li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/message/list_sended_message_page.do">보낸쪽지함</a></h5></li>
					</li>
					<!--  수정할 부분 끝 -->
				</ul>
			</div>
			<!--  사이드바 끝   -->


			<!--  메인 기능 나오는 곳 -->
			<div class="col pl-5 pr-5">
				<!--메인기능 내용시작-->
				<div class="row">
					<div class="col-2"></div>
					<!--메인기능 메인col-->
					<div class="col" style="background-color: white">
						<!--1. 타이틀, 네비게이터 시작-->
						<div class="row mt-3">
							<!--타이틀-->
							<div class="col-4 mt-3">
								<span style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">쪽지쓰기</span>
							</div>
							<div class="col"></div>
							<!---네비게이터-->
							<div class="col-6">
								<div class="row">
									<div class="col"></div>
								</div>
								<div class="row">
									<div class="col mt-5" style="display: inline;">
									<ul style="list-style: none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
									<li>
                                        <a style="color: gray; font-size: 14px" href="#">쪽지함</a>
                                        >
                                        <a style="color: gray; font-size: 14px" href="#">받은쪽지함</a>
                                        >
                                        <a style="color: gray; font-size: 14px">쪽지쓰기</a>
                                    </li>
									</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<form id="frm" action="${pageContext.request.contextPath }/message/write_message_process.do" method="get">
							<!-- 테이블 내용 -->
							<div class="row mt-5">
								<div class="col">
									<div class="row mb-3">
										<div class="col">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">보내는 사람</span>
												</div>
												<input class="form-control" type="text" id="text-input"
													placeholder="${sessionUser.employeeVo.emp_name }" readonly>
											</div>
										</div>
									</div>

									<div class="row mb-3">
										<div class="col">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">받는 사람</span>
												</div>
												<c:if test="${!empty deptList }">
													<select name="dept_no" id="dept_no" class="form-control" onchange="sendDeptNo()">
														<option value="">부서명</option>
														<c:forEach var="data" items="${deptList }">
															<option value="${data.dept_no }">${data.dept_name }</option>
														</c:forEach>
													</select>
												</c:if>
												
												<div id="branch_select"></div>
								
												<select id="emp_name_select" name="receiver" class="form-control">
													<option value="">사원명</option>
												</select>
											</div>
										</div>
									</div>

									<div class="row mb-3">
										<div class="col">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">쪽지 제목</span>
												</div>
												<input type="text" class="form-control" name="msg_title"
													id="msg_title">
											</div>
										</div>
									</div>



									<div class="row mb-3">

										<div class="col">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">쪽지 내용</span>
												</div>
												<textarea class="form-control" rows="10" cols="40"
													aria-label="With textarea" id="msg_content"
													name="msg_content"></textarea>
											</div>
										</div>

									</div>


									<!-- 버튼들 -->
									<div class="row mb-5">
										<div class="col"></div>
										<div class="col-3">
											<a
												href="${pageContext.request.contextPath }/message/list_message_page.do">
												<input type="button" class="btn btn-secondary btn-block"
												value="목록으로">
											</a>
										</div>
										<div class="col-3">
											<input type="button" class="btn btn-primary btn-block"
												value="작성완료" onclick="sub_form()">
										</div>

									</div>
								</div>
							</div>
						</form>

					</div>
					<div class="col-2"></div>
				</div>
				<!--메인기능 내용끝-->

			</div>
			<!--  메인기능 나오는 곳 끝 -->

			<div class="col-1" style="background-color: #f9f9fa"></div>

		</div>

		<!--  footer  -->
		<jsp:include page="../commons/footer.jsp"></jsp:include>
		
	</div>

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