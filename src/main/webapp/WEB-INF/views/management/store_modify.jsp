<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
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
                document.getElementById("branch_address").value = roadAddr;
                document.getElementById("branch_address").value = data.jibunAddress;
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
                    document.getElementById("branch_address").value = expRoadAddr;
                    

                } else if(data.autoJibunAddress) {
                    var expJibunAddr = data.autoJibunAddress;
                    document.getElementById("branch_address").value = expJibunAddr;
                    
                } else {
                  
                    
                }
            }
        }).open();
    }
</script>
<script>
function frm_submit(){

	var frm = document.getElementById("frm"); 

	var reg = /^[가-힣a-zA-Z0-9\s]+$/; //한글+영문(대소문자) +공백 정규표현식

	var branch_name = document.getElementById("branch_name"); 

	if(!(reg.test(branch_name.value)) || (branch_name.value.length) <= 2 || (branch_name.value.length) > 13){ 
		alert("유효한 이름값을 넣어주세요.");
		return; //값을 DB로 전달하지 않고 return
	}
    
	

	
	var reg = /^\d{2,3}-\d{3,4}-\d{4}$/; //전화번호 정규식
	
	
	var emp_phone = document.getElementById("branch_phone");
	
	if(!reg.test(branch_phone.value) || (branch_phone.value.length) <= 2 ){ 
		alert("연락처는 연락처 형식에 맞춰야 합니다. (000-000-0000)");
		return; //값을 DB로 전달하지 않고 return
	}
	//전화번호 값의 유효성 검사
	
	/*
	//var reg = /^[가-힣a-zA-Z0-9\s]+$/; //한글+영문(대소문자) +공백 정규표현식	 
	var branch_address = document.getElementById("branch_address");
	if((branch_address.value.length) <= 2 ){ 
		alert("유효한 주소값을 넣어주세요.");
		return; //값을 DB로 전달하지 않고 return
	}
	*/
	frm.submit(); //값을 DB로 전달
}




</script>
<meta charset="UTF-8">
<title>page</title>
<link rel="stylesheet"
   href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
   integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
   crossorigin="anonymous">

</head>

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
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">부서관리</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/store_view.do">매장관리</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/factory_view.do">공장관리</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/logistics_management.do">물류관리</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/logistics_add.do">물류지점등록</a></h5></li>
               
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
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">매장 정보 수정</span></div>
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
                                        <a style="color: gray; font-size: 14px" href="#">부서관리</a>
                                        >
                                        <a style="color: gray; font-size: 14px" href="${pageContext.request.contextPath }/management/store_view.do">매장관리</a>
                                         >
                                        <a style="color: gray; font-size: 14px" href="#">매장정보 수정</a>
                                        
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
            <c:if test="${!empty sessionUser && sessionUser.deptTypeVo.dept_type_no==1 }">
             <form action="./store_modify_process.do" method="post" id="frm">
             <input type="hidden" name="branch_no" value=${param.branch_no }>
            <!-- 테이블 내용 -->
               <div class="row mt-5">
                  <div class="col-3"></div>
                  <div class="col">
                  <table class="table table-hover shadow-sm p-3 mb-2 bg-white rounded">
                     <tbody>
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">매장명</th>
                        <td><input id="branch_name" name="branch_name" type="text" class="form-control" value="${branchVo.branch_name }"></td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">담당자</th>
                        <td>
                        <select id="emp_code" name="emp_code" class="form-control">
					    <option value="">담당자 선택</option>
 						<c:forEach var="zzzz" items="${emp_list }">
							<option value="${zzzz.employeeVo.emp_code }">${zzzz.employeeVo.emp_name }</option>	
						</c:forEach>

						</select>	
						</td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">연락처</th>
                        <td><input id="branch_phone" name="branch_phone" type="text" class="form-control" value="${branchVo.branch_phone }"></td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">주소</th>
                        <td><input id="branch_address" name="branch_address" type="text" class="form-control" value="${branchVo.branch_address }" onclick="sample4_execDaumPostcode()"></td>
                        
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
                        <div class="col-3">
                           <a href="./store_view.do" class="btn btn-primary btn-block">목록으로</a></div>
                        </div>
                     </div>
                     <div class="col-3"></div>
                  </div>
                  
              </form></c:if>
              
               </div>
               
            
            






            </div>        
        </div>
        <!--메인기능 내용끝-->

       </div>
         <!--  메인기능 나오는 곳 끝 -->

         <div class="col-1" style="background-color: #f9f9fa"></div>
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