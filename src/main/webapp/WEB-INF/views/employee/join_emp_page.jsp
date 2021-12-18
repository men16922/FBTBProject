<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous"></script>

<script type="text/javascript">

	var isConfirmId = false;
	
	function confirmID(){
		var inputValue = document.getElementById("id").value;
		
		var xmlhttp = new XMLHttpRequest();
		
		xmlhttp.onreadystatechange= function(){
			if(xmlhttp.readyState==4 && xmlhttp.status==200){
				if(xmlhttp.responseText == 'true'){ // true 혹은 false라는 문자열
					isConfirmId=true;
					alert("사용 가능한 아이디 입니다.");
				}else{
					alert("사용 불가능한 아이디 입니다.");
					isConfirmID = false;
				}
			}
		};
		
		xmlhttp.open("post", "./confirmId.do", true);
		xmlhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		
		xmlhttp.send("id=" +inputValue);
	}
	
	function confirmID_jQuery(){
		
		var inputValue = $("#id").val();
		
		$.ajax({
			type : 'post',
			url : './confirmId.do',
			data : {'id' : inputValue},
			success : function(result){
				if(result == 'true'){
					alert("사용 가능한 아이디 입니다.");
					isConfirmID = true;
				}else{
					alert("사용 불가능한 아이디 입니다.");
					isConfirmID = false;
				}
			}
		});	
		}

	function frm_submit(){
		var frm = document.getElementById("frm");
	
		//유효성 검사
		var idBox = document.getElementById("id");
		
		
		//ID 유효성....이메일 검사
		var reg = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		
		if(!reg.test(idBox.value)){
			alert("아이디는 이메일 형식에 맞춰야 됩니다.");
			
			idBox.value ="";
			idBox.focus();
			return;
		}
		reg = /^[A-Za-z0-9]{6,12}$/;
		
		
		var pwBox = document.getElementById("pw");
		if(!reg.test(pwBox.value)){
			alert("비밀번호는 숫자,영문자로 6-12자리여야 합니다.");
			
			pwBox.value="";
			pwBox.focus();
			return;
		}
		
		if(isConfirmId !=true){
			alert("아이디 중복 체크를 해주셔야 합니다.");
			return;
		}
		
		frm.submit();
}
	
</script>

</head>
<body>
<img id="logo" src="${pageContext.request.contextPath}/resources/img/image1.png" width="300" height="100">
<div class="container mt-5">
<form id ="frm" action="./join_emp_process.do" method="post">
	<div class="row"> <!-- row 7개 -->
		<div class="col-3"></div>
		<div class="col">
			
			<div class="row mt-5"><!-- id -->
				<div class="col-3">
					사번
				</div>
				<div class="col">
				<input id="id" placeholder="사번을 입력해주세요." name="emp_no" type="text" class="form-control">
				</div>
			</div>
			
			<div class="row mt-1"><!-- pw -->
				<div class="col-3">
					비밀번호
				</div>
				<div class="col">
					<input id="pw" placeholder="비밀번호를 입력해주세요." name="emp_password" type="password" class="form-control">
				</div>
			</div>
			
			<div class="row mt-1"><!-- Nick -->
				<div class="col-3">
					이름
				</div>
				<div class="col">
					<input placeholder="이름을 입력해주세요" name="emp_name" type="text" class="form-control">
				</div>
			</div>
			
			<div class="row mt-1"><!-- 이메일 -->
				<div class="col-3">
					이메일
				</div>
					<div class="col">
					<input placeholder="이메일을 입력해주세요" name="emp_email" type="text" class="form-control">
				</div>
			</div>
			
			<div class="row mt-1"><!-- 연락처 -->
				<div class="col-3">
					연락처
				</div>
					<div class="col">
					<input placeholder="연락처를 입력해주세요" name="emp_phone" type="text" class="form-control">
				</div>
			</div>
			<div class="row mt-1"><!-- 입사날짜 -->
				<div class="col-3">
					입사날짜
				</div>
			</div>
			<div class="row mt-1"><!-- 주소 -->
				<div class="col-3">
					주소
				</div>
					<div class="col">
					<input placeholder="주소를 입력해주세요" name="emp_address" type="text" class="form-control">
				</div>
			</div>
			<div class="row mt-1"><!-- 성별 -->
				<div class="col-3">
					성별
				</div>
					<div class="col">
					<input placeholder="성별을 입력해주세요" name="emp_sex" type="text" class="form-control">
				</div>
			</div>
			<div class="row mt-1"><!-- 부서 -->
				<div class="col-3">
					부서번호
				</div>
					<div class="col">
					<input placeholder="부서를 입력해주세요" name="dept_no" type="text" class="form-control">
				</div>
			</div>
			<div class="row mt-1"><!-- 직급 -->
				<div class="col-3">
					직급
				</div>
			</div>
			<div class="row mt-1"><!-- 생년월일 -->
				<div class="col-3">
					주민번호
				</div>
					<input placeholder="주민번호를 입력해주세요" name="emp_resident_num" type="text" class="form-control">
			</div>
			
			
			<div class="row mt-2"><!-- 가입 버튼 -->
				<div class="col">
				<input type="submit" value="회원 가입" class="btn btn-outline-primary btn-block"> 
				</div>
			</div>
		</div>
		<div class="col-3"></div>
	</div>
</form>
</div>

<div id="test_box" >
		
</div>
 
</body>
</html>