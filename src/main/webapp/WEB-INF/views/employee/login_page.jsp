<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
	
<script type="text/javascript">

	var isConfimIdPw = false;
	var pass = false;
	function sub_form() {
			   
			var frm = document.getElementById("frm");

			var emp_no = document.getElementById("emp_no").value;
			var emp_password = document.getElementById("emp_password").value;

			var url = "emp_no=" + emp_no + "&emp_password=" + emp_password;

			if (emp_no == "") {
				alert("아이디를 입력해주세요.");
				return;
			} else if (emp_password == "") {
				alert("비밀번호를 입력해주세요.");
				return;
			} else if (isConfimIdPw == false) {

				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {

					if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

						if (xmlhttp.responseText == 'true') {
							//여기에 채워서 날아옴!
							isConfimIdPw = true;
							frm.submit();			
							

						} else {
							isConfimIdPw = false;
							alert("아이디와 비밀번호를 확인해주세요. ");
						
							return;
						}

					}

				};

				xmlhttp.open(
								"post",
								"${pageContext.request.contextPath }/employee/confirmIdPw.do",
								true);
				xmlhttp.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				xmlhttp.send(url);

			}
			

	}
	
	function onEnterSubmit(){

		var keyCode = window.event.keyCode;
		if(keyCode==13) sub_form();
		
	}

	
	 function pass_form() {
	

			var emp_no = document.getElementById("emp_no2").value;
			var emp_name = document.getElementById("emp_name2").value;
			var emp_email = document.getElementById("emp_email2").value;

			var url = "emp_name=" + emp_name + "&emp_no=" + emp_no + "&emp_email=" + emp_email;

			if (emp_no == "") {
				alert("사번을 입력해주세요.");
				return;
			} else if (emp_password == "") {
				alert("이름을 입력해주세요.");
				return;
			} 
			else if (emp_email == "") {
				alert("이메일을 입력해주세요.");
				return;
			}
			
			else if (pass == false) {

				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {

					if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

						if (xmlhttp.responseText == 'true') {
							//여기에 채워서 날아옴!
							
							pass = true;
							sendemail();

						} else {
							pass = false;
							alert("입력값을 확인해주세요. ");
						
							return;
						}

					}

				};
				xmlhttp.open(
								"post",
								"${pageContext.request.contextPath }/employee/find_password.do",
								true);
				xmlhttp.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				xmlhttp.send(url);

			}
			

	}
	 
	 function sendemail() {
		   
	

			var emp_no = document.getElementById("emp_no2").value;
			var emp_email = document.getElementById("emp_email2").value;

			var url = "emp_no=" + emp_no + "&emp_email=" + emp_email;


			
		 	
				var xmlhttp = new XMLHttpRequest();
				xmlhttp.onreadystatechange = function() {

					if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

						if (xmlhttp.responseText == 'true') {
							//여기에 채워서 날아옴!
							
							alert("이메일을 보냈습니다.");

					}

				};
				}
				xmlhttp.open(
								"post",
								"${pageContext.request.contextPath }/employee/employee_find_mailing_process.do",
								true);
				xmlhttp.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				xmlhttp.send(url);

			}
			

	 
</script>
</head>
<body style="background-color: #3d81d6;">

	<div class="row" style="height: 100px;">
		<div class="col"></div>
	</div>

	<div class="row" style="height: 100px;">
		<div class="col-4"></div>
		<div class="col">
			<img src="${pageContext.request.contextPath}/resources/img/logo.png" class="img-fluid">
		</div>
		<div class="col-3"></div>
	</div>

	<!-- 중앙 로그인 row -->
	<div class="row">
		<div class="col-3"></div>
		<div class="col">
		
			<form
				action="${pageContext.request.contextPath }/employee/login_process.do"
				method="post" id="frm">
				<div class="row mr-5 mt-5 rounded shadow"
					style="background-color: white">
					<div class="col-6" style="border-right: 1px solid #dddddd;">
						<div class="row mt-5 mb-1">
							<div class="col-3"></div>
							<div class="col">
								<img src="${pageContext.request.contextPath}/resources/img/comp.png" class="img-fluid">
							</div>
							<div class="col-2"></div>
						</div>
						<div class="row mb-5">
							<div class="col-1"></div>
							<div class="col" style="text-align: center;">
								<span>BT전자 ERP시스템입니다.</span><br> <span>로그인 후 이용해주세요.</span><br>
								<span style="color: #3d81d6">ADMINISTRATION</span>
							</div>
							<div class="col-1"></div>
						</div>
					</div>


					<div class="col-6">

						<div class="row mt-5 mb-1">
							<div class="col" style="text-align: center;">
								<span
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: #cacaca; font-weight: bold; font-size: 30px;">Login
									Account</span>
							</div>
						</div>

						<div class="row mt-3 mb-2">
							<!-- id -->
							<div class="col-3" style="text-align: center">사번</div>
							<div class="col">
								<input placeholder="사번을 입력해주세요." name="emp_no" id="emp_no" type="text" class="form-control">
							</div>
						</div>

						<div class="row mt-1 mb-3">
							<!-- pw -->
							<div class="col-3" style="text-align: center">비밀번호</div>
							<div class="col">
								<input onkeydown="onEnterSubmit()" placeholder="비밀번호를 입력해주세요." name="emp_password" id="emp_password" type="password" class="form-control">
							</div>
						</div>

						<div class="row mt-3">
							<div class="col">
								<input type="button" value="로그인" class="btn btn-primary btn-block" onclick="sub_form()">
							</div>
						</div>

						<div class="row mt-2 mb-5">
							<div class="col">
							<button type="button" class="btn btn-secondary btn-block" data-toggle="modal" data-target="#myModal">비밀번호 찾기</button>

							</div>
						</div>
						
					</div>
		
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
						
						
						<div class="modal-dialog" role="document">
						<div class="modal-content">
						<div class="modal-header">
						
						<h4 class="modal-title" id="myModalLabel">비밀번호 찾기</h4>
						</div>
						<div class="modal-body">
						
						<div class="row">
						<div class="col-2" >
							이름
						</div>
						<div class="col">
						<input placeholder="이름을 입력해주세요." name="emp_name2" id="emp_name2" type="text" class="form-control">
						</div>
						</div>
						
						<div class="row">
						<div class="col-2" >
							사번
						</div>
						<div class="col">
						<input placeholder="사번을 입력해주세요." name="emp_no2" id="emp_no2" type="text" class="form-control">
						</div>
						</div>
						
						<div class="row">
						<div class="col-2" >
							이메일
						</div>
						<div class="col">
						<input placeholder="이메일을 입력해주세요." name="emp_email2" id="emp_email2" type="text" class="form-control">
						</div>
						</div>
						
						</div>
						<div class="modal-footer">
						<button type="button" class="btn btn-primary" onclick="pass_form()">확인</button>
						<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						</div>
						</div>
						</div>
						</div>
			

				</div>
			</form>
			
		</div>

		<div class="col-3"></div>
		
	</div>

	<!-- login_footer -->
	<div class="row mt-2" style="height: auto;">
		<div class="col-4 ml-4"></div>
		<div class="col ml-5">
			<span style="color: white; font-size: 20px; font-weight: lighter;">
			Copyright © 2020 bt.co.Ltd. All rights reserved.</span>
		</div>
		<div class="col-4"></div>
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