<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>page</title>
<link rel="stylesheet"
   href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
   integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
   crossorigin="anonymous">

</head>
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    //본 예제에서는 도로명 주소 표기 방식에 대한 법령에 따라, 내려오는 데이터를 조합하여 올바른 주소를 구성하는 방법을 설명합니다.
     function sample4_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 도로명 주소의 노출 규칙에 따라 주소를 표시한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var roadAddr = data.roadAddress; // 도로명 주소 변수
                var extraRoadAddr = ''; // 참고 항목 변수

                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                    extraRoadAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if(data.buildingName !== '' && data.apartment === 'Y'){
                   extraRoadAddr += (extraRoadAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if(extraRoadAddr !== ''){
                    extraRoadAddr = ' (' + extraRoadAddr + ')';
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                //document.getElementById('sample4_postcode').value = data.zonecode;
                document.getElementById("emp_address").value = roadAddr;
                document.getElementById("emp_address").value = data.jibunAddress;
                /*
                // 참고항목 문자열이 있을 경우 해당 필드에 넣는다.
                if(roadAddr !== ''){
                    document.getElementById("branch_address").value = extraRoadAddr;
                } else {
                    document.getElementById("branch_address").value = '';
                }
				*/
                var guideTextBox = document.getElementById("guide");
                // 사용자가 '선택 안함'을 클릭한 경우, 예상 주소라는 표시를 해준다.
                if(data.autoRoadAddress) {
                    var expRoadAddr = data.autoRoadAddress + extraRoadAddr;
                    document.getElementById("emp_address").value = expRoadAddr;
                    

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    document.getElementById("emp_address").value = expJibunAddr;
                    
                } else {
                    
                    
                }
            }
        }).open();
    }
</script>
<!-- ------------------------------------사원번호 생성 스크립트--------------------------->
<script> //사원번호 생성 스크립트
var emp_no_text;
function create_emp_no() {
    var year = new Date().getFullYear(); 
 
    var department_no = test(); //부서 번호 선택에 따른 부서 번호 부여 
    
   
    var rank_no = ${empNoForEmployee };
    if(rank_no == null)
    {
       rank_no = 1;   //연도가 바뀌었을때 다시 사번 뒷번호를 1번으로 셋팅
    }
    
    rank_no = ${empNoForEmployee }+1; //현재 사번의 뒤 세자리 max 값 +1
    
    if(rank_no <10){
       rank_no = '00'+rank_no;
    }else if(rank_no <100){
       rank_no = '0'+rank_no;
    }
    
    emp_no_text = document.getElementById("emp_no"); //입력폼 id 가져오기


    emp_no_text.value = year + '' + department_no + '' + rank_no+'';
}

  
function test(){
   
   var s_box = document.getElementById("dept_no");
   
   var dept = s_box.options[s_box.selectedIndex].value;
   
   return dept;
}  


//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=
function test5(){
	var sel = document.getElementById("dept_no");
	var dept_no = sel.selectedOptions[0].value;
	
	var xmlhttp = new XMLHttpRequest();
	
	//호출 후 값을 받았을때... 처리 로직....
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState==4 && xmlhttp.status == 200){
			//alert(xmlhttp.responseText);
			var datas = JSON.parse(xmlhttp.responseText);
			
			var selBranch = document.getElementById("branch_no");
			
			var len = selBranch.childNodes.length;
			
			for(i = 0 ; i < len ; i++){
				selBranch.removeChild(selBranch.childNodes[0]);
				
			}
			var op = document.createElement("option");
			
			op.value = 0;
			op.innerText = "지점선택";
			selBranch.appendChild(op);
			for(data of datas){
				
				var op = document.createElement("option");
				
				op.value = data.branchVo.branch_no;

				op.innerText = data.branchVo.branch_name;
				
				selBranch.appendChild(op);
				
			}
			
		}
	};
	
	xmlhttp.open("get","./test5.do?dept_no=" + dept_no , true);
	xmlhttp.setRequestHeader("Content-type",
	"application/x-www-form-urlencoded");

	xmlhttp.send();	
	
	
	
}





$(function(){
    
    // 질문유형을 선택한다.
    chnQnaType('1' , '11');
});

function chnQnaType(type , select) {
    
    $('#dept_no').empty();
    
    if(type == '1') { 
    	$('#dept_no').append("<option>부서선택</option>'");
        $('#dept_no').append("<option value='10' >관리팀</option>'");
        $('#dept_no').append("<option value='21' >물류1팀</option>'");
        $('#dept_no').append("<option value='22' >물류2팀</option>'");
        $('#dept_no').append("<option value='23' >물류3팀</option>'");
    } else if (type == '2') {  
    	$('#dept_no').append("<option>부서선택</option>'");
        $('#dept_no').append("<option value='30' >영업부</option>'");
 
    } else if ( type == '3') { 
    	$('#dept_no').append("<option>부서선택</option>'");
        $('#dept_no').append("<option value='40' >생산부</option>'");
    }
    document.getElementById("dept_no").style.display = "";
    
    if ($.trim(select) != "") {
        $('#ttt').val(type);
        $('#dept_no').val(select);
    }
}


//---------------------------------------------------------------------------------------

function frm_submit(){

	var frm = document.getElementById("frm"); 

	var reg = /^[가-힣a-zA-Z\s]+$/; //한글+영문+공백 정규표현식

	var emp_name = document.getElementById("emp_name"); 

	if(!(reg.test(emp_name.value)) || (emp_name.value.length) <= 2 || (emp_name.value.length) > 13){ 
		alert("유효한 이름값을 넣어주세요.");
		return; //값을 DB로 전달하지 않고 return
	}
    
	var reg =/([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	//이메일 유효성 검사
	var emp_email = document.getElementById("emp_email"); 

	if(!(reg.test(emp_email.value)) || (emp_email.value.length) <= 2 ){ 
		alert("유효한 이메일값을 넣어주세요.");
		return; //값을 DB로 전달하지 않고 return
	}

	var reg = /^\d{2,3}-\d{3,4}-\d{4}$/; //전화번호 정규식
	
	
	var emp_phone = document.getElementById("emp_phone");
	
	if(!reg.test(emp_phone.value) || (emp_phone.value.length) <= 2 ){ 
		alert("연락처는 연락처 형식에 맞춰야 합니다. (000-000-0000)");
		return; //값을 DB로 전달하지 않고 return
	}
	//전화번호 값의 유효성 검사
	
	if(document.getElementById("start_date").value==""){ 
		alert("입사일을 선택해 주세요.");
		return; //값을 DB로 전달하지 않고 return
	}

	//var reg = /^[가-힣a-zA-Z0-9\s]+$/; //한글+영문(대소문자) +공백 정규표현식	 
	
	var emp_address = document.getElementById("emp_address");
	/*
	if((emp_address.value.length) <= 2){ 
		alert("유효한 주소값을 넣어주세요.");
		return; //값을 DB로 전달하지 않고 return
	}
	*/
	var chk_radio = document.getElementsByName('emp_sex');
	var sel_type_emp_sex = null;

	for(var i=0;i<chk_radio.length;i++){

		if(chk_radio[i].checked == true){ 

			sel_type_emp_sex = chk_radio[i].value;

		}

	}
	if(sel_type_emp_sex == null){

        alert("성별을 선택하세요."); 

	return;
	}//성별 체크
	
	
	if(document.getElementById("frm").branch_type_no.selectedIndex==0){ 
		alert("유형을 선택해 주세요.");
		return; //값을 DB로 전달하지 않고 return
	}
	
	if(document.getElementById("frm").dept_no.selectedIndex==0){ 
		alert("부서를 선택해 주세요.");
		return; //값을 DB로 전달하지 않고 return
	}
	
	if(document.getElementById("frm").branch_type_no.selectedIndex==2 || document.getElementById("frm").branch_type_no.selectedIndex==3 ){ 
		if(document.getElementById("frm").branch_no.selectedIndex==0){
		alert("지점을 선택해 주세요.");
		//유형이 영업부 혹은 생산부인 경우만 필터링
		return; //값을 DB로 전달하지 않고 return
		}
	}
	
	if(document.getElementById("frm").rank_no.selectedIndex==0){ 
		alert("직급을 선택해 주세요.");
		return; //값을 DB로 전달하지 않고 return
	}
	
	var reg = /^[0-9]*$/;

	//(숫자만 입력 가능)
	if((!(reg.test(emp_residentnum.value))) || (emp_residentnum.value.length) <= 2 ||( !(emp_residentnum.value.length==6))){ 
		alert("유효한 주민번호 값을 넣어주세요.");
		return; //값을 DB로 전달하지 않고 return
	}
	
	var reg = /^[0-9]*$/;
	//(숫자만 입력 가능)
	if(!reg.test(emp_no.value)||(emp_no.value.length) <= 8){ 
		alert("사원 번호 생성은 필수 입니다. 부서를 선택 후 사원번호를 생성해 주세요.");
		return; //값을 DB로 전달하지 않고 return
	}
	
	frm.submit(); //값을 DB로 전달
}




</script>
<!------------------------------------------------------------------------------------>

<body>
   <jsp:include page="../commons/global_nav.jsp"></jsp:include>
   <jsp:include page="../commons/management_nav.jsp"></jsp:include>

   <div class="container-fluid">
      <!-- 메인 기능들이 나오는 row -->
      <div class="row">
         <div class="col-1" style="background-color: #f9f9fa"></div>

         <!--  사이드바 시작  -->
         <div class="col-2"
            style="padding: 0; border-right: 2px solid #e8ecf1;">
            <ul class="list-group list-group-flush">
               <!--  수정할 부분 시작 -->
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">사원관리</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/employee_view.do">사원조회</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/employee_add.do">사원등록</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/employee_resignation.do">퇴사자관리</a></h5></li>
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
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">사원등록</span></div>
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
                                    	
                                        <a style="color: gray; font-size: 14px" href="#">사원관리</a>
                                        >
                                        <a style="color: gray; font-size: 14px" href="${pageContext.request.contextPath }/management/employee_add.do">사원등록</a>
                                    </li>
                                </ul>             
                            </div>      
                        </div>
                    </div>
                </div>
                <!--1. 타이틀, 네비게이터 끝-->

                <!--2. 메인기능 알맹이들!!!!! 여기에 넣기-->
                <!--2. 메인기능 알맹이들!!!!! 여기에 넣기-->
                <!--2. 메인기능 알맹이들!!!!! 여기에 넣기-->
                <!--2. 메인기능 알맹이들!!!!! 여기에 넣기-->
				<c:if test="${!empty sessionUser && sessionUser.deptTypeVo.dept_type_no==1}">
				
				<form action="./employee_add_process.do" method="post" id="frm">        
				    <!-- 테이블 내용 -->
               <div class="row mt-5">
                  <div class="col-2"></div>
                  <div class="col">
                  <table class="table table-hover shadow-sm p-3 mb-2 bg-white rounded">
                     <tbody>
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">이름</th>
                        <td><input id="emp_name" placeholder="이름을 입력해주세요." name="emp_name" type="text" class="form-control"></td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">이메일</th>
                        <td><input id="emp_email" placeholder="이메일을 입력해주세요." name="emp_email" type="text" class="form-control"></td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">연락처</th>
                        <td><input id="emp_phone" placeholder="연락처를 입력해주세요." name="emp_phone" type="text" class="form-control"></td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">입사날짜</th>
                        <td><input id="start_date" type="date" name="start_date" class="form-control"></td>
                    	<td></td>
					</tr>
                     
                      <tr>
                     <th class="shadow-none p-3 mb-5 bg-light rounded">주소</th>
                        <td><input id="emp_address" name="emp_address" type="text" class="form-control" onclick="sample4_execDaumPostcode()"></td>
                    	<td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">성별</th>
                        <td>
                        <label class="btn btn-outline-primary active">
           			  	<input type="radio" name="emp_sex" id="emp_sex_M" autocomplete="off" value="M" checked> 남
		         		</label>
		           		<label class="btn btn-outline-primary">
		             	<input type="radio" name="emp_sex" id="emp_sex_F" autocomplete="off" value="F" > 여
		           		</label>
           				</td>
                        <td></td>
                        
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">유형/부서</th>
                        <td>
                        <select id="branch_type_no" name="branch_type_no" class="form-control" onchange="chnQnaType(this.value)">
		                   <option value="">유형선택</option>
		                   <option value="1">본사</option>
		                   <option value="2">매장</option>
		                   <option value="3">공장</option>
						      </select>
						       
						</td>
                        <td>
                        <select id="dept_no" name="dept_no" class="form-control" onchange="test5()">
					                   <option value="">부서선택</option>
					                   
					      </select>
                         
						</td>
                        
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">지점/직급</th>
                        <td>
					      <select id="branch_no" name="branch_no" class="form-control" >
						                   <option value="">지점선택</option>
						</select> 
						       
						</td>
                        <td>
                        <select id="rank_no" name="rank_no" class="form-control">
		                   <option value="">직급선택</option>
		                   <option value="7">사원</option>
		                   <option value="6">주임</option>
		                   <option value="5">대리</option>
		                   <option value="4">과장</option>
		                   <option value="3">부장</option>
		                   <option value="2">부사장</option>
		                   <option value="1">사장</option>
                   
      					</select> 
						</td>
                        
                     </tr>
                     
                     
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">주민번호</th>
                        <td><input id="emp_residentnum" placeholder="주민번호 앞자리 6개를 입력" name="emp_residentnum" type="text" class="form-control"></td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">사원번호</th>
                        <td><input type="text" class="form-control" id="emp_no" name="emp_no" readOnly></td>
                        <td><input class="form-control" type="button" value="사원번호생성" onclick="create_emp_no()"></td>
                     </tr>
                     
                     
                     
                     
                     
                     </tbody>
                  </table>
                  
                     <!-- 버튼들 -->
                     <div class="row mt-3 mb-5">
                        <div class="col"></div>
                        <div class="col"></div>
                        <div class="col-2">
                           <input type="button" value="등록" class="btn btn-primary btn-block" onclick="frm_submit()">
                        </div>
                     </div>
                     
                  </div>
                  <div class="col-3"></div>
               </div>
            </form></c:if><br><br><br><br><br><br>
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