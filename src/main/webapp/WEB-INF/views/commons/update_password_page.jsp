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
<title>정보수정</title>

<script type="text/javascript">
	var isConfirmPW = false;

	function confirmPw() {
		var inputValue = document.getElementById("emp_password").value; //.value는 id박스의 스트링값을 가져오는 것, 그냥 하면 태그를 가져오는 것

		var xmlhttp = new XMLHttpRequest();
		xmlhttp.onreadystatechange = function() {
			//함수 포인터 넣는 것
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				//서버로부터 응답을 받고(4) 404낫파운드가 아닌 200번일 때 성공한 경우이다
				if (xmlhttp.responseText == 'true') {
					//여기에 채워서 날아옴!
					isConfirmPW = true;
					alert("비밀번호 확인이 되었습니다.");

				} else {
					isConfirmPW = false;
					alert("비밀번호를 다시 확인해주세요. ");
				}
			}

		};

		xmlhttp.open("post",
				"${pageContext.request.contextPath }/employee/confirmPw.do",
				true); //true는 비동기식으로 하겠다는 뜻(default:true)
		//아래서 send하고 바로 다음 함수로 넘어감!(응답 끝나는 걸 기다리지 않음)
		xmlhttp.setRequestHeader("Content-type",
				"application/x-www-form-urlencoded");
		xmlhttp.send("emp_password=" + inputValue);

	}

	function frm_submit() {

		var frm = document.getElementById("frm");

		var new_emp_password = document.getElementById("new_emp_password").value;
		var new_emp_password_check = document
				.getElementById("new_emp_password_check").value;

		//비밀번호 확인 했는지
		if (isConfirmPW != true) {
			alert("기존비밀번호 확인을 해주셔야합니다.");
			return;
		}

		if (new_emp_password == "" || new_emp_password_check == "") {
			alert("새 비밀번호를 입력해주세요");

		}
		//새비밀번호 같은 걸로 했는지
		else if (new_emp_password != new_emp_password_check) {
			alert("같은 값의 새 비밀번호를 입력해주세요");

		} else {

			alert("비밀번호 변경이 완료되었습니다");

			frm.submit();
		}

	}
</script>




</head>
<body>
	<!-- global navbar -->
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
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">마이페이지</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/employee/update_password_page.do">비밀번호 수정</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/employee/management_employee.do">직원관리</a></h5></li>
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
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">비밀번호 수정</span></div>
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
                                        <a style="color: gray; font-size: 14px" href="#">마이페이지</a>
                                        >
                                        <a style="color: gray; font-size: 14px">비밀번호 수정</a>
                                    </li>
                                </ul>             
                            </div>      
                        </div>
                    </div>
                </div>
                <!--1. 타이틀, 네비게이터 끝-->
				
				<form action="${pageContext.request.contextPath }/employee/update_password_process.do" method="post" id="frm">
				<!-- 테이블 내용 -->
					<div class="row mt-5">
						<div class="col-3"></div>
						<div class="col">
						<table class="table table-hover shadow-sm p-3 mb-2 bg-white rounded">
							<tbody>
							<tr>
								<th class="shadow-none p-3 mb-5 bg-light rounded">사번</th>
								<td>${sessionUser.employeeVo.emp_no }</td>
								<td></td>
							</tr>
							
							<tr>
								<th class="shadow-none p-3 mb-5 bg-light rounded">이름</th>
								<td>${sessionUser.employeeVo.emp_name }</td>
								<td></td>
							</tr>
							
							<tr>
								<th class="shadow-none p-3 mb-5 bg-light rounded">담당지점</th>
								<td>${sessionUser.branchVo.branch_name }</td>
								<td></td>
							</tr>
							
							<tr>
								<th class="shadow-none p-3 mb-5 bg-light rounded">기존 비밀번호</th>
								<td><input type="password" class="form-control" name="emp_password" id="emp_password"></td>
								<td><input type="button" class="btn btn-secondary btn-block" value="비밀번호 확인" onclick="confirmPw()"></td>
							</tr>
							
							<tr>
								<th class="shadow-none p-3 mb-5 bg-light rounded">새 비밀번호</th>
								<td><input type="password" class="form-control" name="new_emp_password"
										id="new_emp_password"></td>
								<td></td>
								
							</tr>
							
							<tr>
								<th class="shadow-none p-3 mb-5 bg-light rounded">새 비밀번호 확인</th>
								<td><input type="password" class="form-control" id="new_emp_password_check"></td>
								<td></td>
								
							</tr>
							
							</tbody>
						</table>
						
							<!-- 버튼들 -->
							<div class="row mt-3 mb-5">
								<div class="col"></div>
								<div class="col"></div>
								<div class="col-3">
									<input type="button" class="btn btn-primary btn-block" value="수정완료" onclick="frm_submit()">
								</div>
							</div>
							
						</div>
						<div class="col-3"></div>
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