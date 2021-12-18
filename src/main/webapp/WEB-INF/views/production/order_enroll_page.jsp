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
	var loc = "${pageContext.request.contextPath }/production/ordr_enroll_page.do";
	var num = 2;

	function refreshRequestList(){
		location.reload();
	}
	
	function add_row() {
		var innerHtML = "<td>"+num+"</td>";
		var l = document.getElementsByClassName('comp_no').length;
		
		innerHtML += "<td><select name=\"comp_no\" class=\"comp_no form-control\" id=\"comp_no\" onchange=\"sendCompNo("+l+")\">";
		innerHtML += "<option value=\"\">부품을 선택해주세요</option>";
		
		//document.getElementsByClassName('comp_no')[l].setAttribute("onChange","sendCompNo("+l+")");
		
		<c:forEach var="data" items="${componentList}">
		innerHtML += "<option value=\"${data.productComponentVo.comp_no }\">${data.productComponentVo.comp_name }</option>";
		</c:forEach>
		innerHtML += "</select></td>"
		
		innerHtML += "<td><select id=\"supplier_name_select\" name=\"supplier_no\" id=\"supplier_no\" class=\"supplier_name_select form-control\">";
		innerHtML += "<option value=\"\">공급업체를 선택해주세요</option></select></td>"
	
		innerHtML += "<td><input placeholder=\"발주수량을 입력해주세요\" name=\"factory_order_qty\" type=\"text\" class=\"factory_order_qty form-control\"></td>";
		
		innerHtML += "<td><button onclick=\"javascript:add_row()\" class=\"btn btn-light btn-light btn-sm\">추가하기</button></td>"
		innerHtML += "<td><input type=\"button\" onclick=\"javascript:del_row(this);\" class=\"btn btn-light btn-light btn-sm\" value=\"삭제하기\"></td>"
		
		var tr = document.createElement("tr");
		
		tr.innerHTML = innerHtML;
		document.getElementById("tbody").appendChild(tr);
		
		num++;
	}
	
	function del_row(obj){
		if(typeof(obj) == "object") {
			$(obj).closest("tr").remove();
		} else {
			return false;
		}
		
	}
	
	function sendCompNo(index){
        var inputValue = document.getElementsByClassName("comp_no")[index].value; 
       
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange =  function(){
                             //함수 포인터 넣는 것
           if(xmlhttp.readyState==4 && xmlhttp.status == 200){
              
              //얘도 리스트
              var supplierList = JSON.parse(xmlhttp.responseText);
        
              //리스트(배열 만드는 것)
              //var supplier_name_select = document.getElementById("supplier_name_select");
              var supplier_name_select = document.getElementsByClassName("supplier_name_select")[index];
        
              
              //팀이름 바꿀 때마다 셀렉트 박스 안에 값들 리무브 차일드 해주기
               var length = supplier_name_select.childNodes.length;
               for(var i=0; i<length; i++){
                  supplier_name_select.removeChild(supplier_name_select.childNodes[0]);
               }
              
               var opt = document.createElement("option");
               opt.innerText = "공급업체를 선택해주세요";
               supplier_name_select.appendChild(opt);
         
              for(sup of supplierList){
                 var option = document.createElement("option");
                 
                 option.innerText = sup.supplier_name;
                 option.value = sup.supplier_no;
                 
                 supplier_name_select.appendChild(option);
              
              }
              
           }                                 
                             
        };
        
        xmlhttp.open("post","${pageContext.request.contextPath }/production/sendCompNo.do", true);                                  
        xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlhttp.send("comp_no="+inputValue);

     }
	
	function test(){
	      var year = new Date().getFullYear().toString(); 
	         year=year.substring(2,4);
	      var month= (new Date().getMonth() + 1).toString();
	      if(month.length==1){
	         month="0"+month;
	      }
	      var day= new Date().getDate().toString();
	      if(day.length==1){
	         day="0"+day;
	      }
	      
	      var maxSeq=${maxSeq+1};
	      maxSeq=maxSeq.toString();
	      
	      if(maxSeq.length==1){
	         maxSeq="00"+maxSeq;
	      }
	      else if(maxSeq.length==2){
	         maxSeq="0"+maxSeq;
	      }
	      if(maxSeq<1000)
	         {
	         maxSeq="B"+maxSeq;
	         }
	      else{
	         maxSeq="C"+maxSeq;
	      }
	      
	      var date=maxSeq.concat(year,month,day);
	      document.getElementById("order_no").innerHTML=date;
	      
	      document.getElementById("factory_order_res_no").value=date;
	   }
	
	function frm_submit(){
		
		var factory_order_res_no = document.getElementById("order_no").innerText;
		var comp_no = document.getElementsByClassName("comp_no");
		var supplier_no = document.getElementsByClassName("supplier_name_select");
		var factory_order_qty = document.getElementsByClassName("factory_order_qty");
		var factory_order_note = document.getElementById("factory_order_note").value;
		
		if(factory_order_res_no == 0){
   			alert("발주번호를 생성해주세요");
   			return;
   		}
		
		// 배열 반복문 돌리기
   		for(var i=0; i<comp_no.length; i++){
   			
   			if(comp_no[i].value == "" || supplier_no[i].value == "" || factory_order_qty[i].value == ""){
   				alert("부품 및 공급업체와 수량을 선택해주세요");
   				return;
   			}
   		}
        
		
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange =  function(){
                             //함수 포인터 넣는 것
           if(xmlhttp.readyState==4 && xmlhttp.status == 200){
              
        	alert("부품 발주가 정상적으로 등록되었습니다.");
        	
       		location.href = loc;
			refreshRequestList();
           }                                 
                             
        };
        
        url = "${pageContext.request.contextPath }/production/order_enroll_process.do?"
        		+"factory_order_res_no="+factory_order_res_no;
        
        for(var j=0; j<comp_no.length; j++) {
        	url += "&comp_no="+comp_no[j].value;
        	url += "&supplier_no="+supplier_no[j].value;
        	url += "&factory_order_qty="+factory_order_qty[j].value;
        }
        url += "&factory_order_note="+factory_order_note;
        
        xmlhttp.open("get", url, true);                                  
        xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        xmlhttp.send();

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
				style="padding: 0; border-right: 2px solid #e8ecf1; border-left: 2px solid #e8ecf1;">
				<ul class="list-group list-group-flush">
					<!--  수정할 부분 시작 -->
					<li class="list-group-item"
						style="height: 105px; background-color: #7393a7; color: white; font-weight: bold;">
						<h2 class="mt-3" align="center">부품공급</h2>
					</li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/order_enroll_page.do">부품
								발주등록</a>
						</h5></li>
					<li class="list-group-item"><h5>
							<a style="color: #6c737e;"
								href="${pageContext.request.contextPath }/production/order_list_page.do">발주조회</a>
						</h5></li>
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
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">부품
									발주 등록</span>
							</div>
							<div class="col"></div>
							<!---네비게이터-->
							<div class="col-6">
								<div class="row">
									<div class="col"></div>
								</div>
								<div class="row">
									<div class="col-2"></div>
									<div class="col mt-5" style="display: inline;">
										<ul
											style="list-style: none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
											<li><a style="color: gray; font-size: 14px" href="#">부품공급</a>
												> <a style="color: gray; font-size: 14px"
												href="${pageContext.request.contextPath }/production/order_enroll_page.do">부품발주등록</a>

											</li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<div class="row mt-3">
							<table class="table table-hover">
								<tbody>
									<tr>
										<th scope="row">발주번호</th>
										<td id="order_no"></td>
										<td><input type="button" value="발주번호 생성"
											class="btn btn-primary btn-block" onclick="test()"> <input
											type="hidden" name="factory_order_res_no" value=0
											id="factory_order_res_no"></td>
									</tr>
									<tr>
										<th scope="row">발주자</th>
										<td>${sessionUser.employeeVo.emp_name }</td>
										<td></td>
									</tr>
									<tr>
										<th scope="row">부품 및 공급업체</th>
										<td></td>
										<td></td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- 추가할 양식 -->
						<div class="row">
							<table class="table bg-light">
								<thead>
									<tr>
										<th>No</th>
										<th>부품</th>
										<th>공급업체</th>
										<th>수량</th>
										<th>추가</th>
										<th>삭제</th>
									</tr>
								</thead>
								<tbody id="tbody">
									<tr>
										<td>1</td>
										<td><c:if test="${!empty componentList }">
												<select name="comp_no" class="comp_no form-control" id="comp_no" onchange="sendCompNo(0)">
													<option value="">부품을 선택해주세요.</option>
													<c:forEach var="data" items="${componentList }">
														<option value="${data.productComponentVo.comp_no }">
															${data.productComponentVo.comp_name }</option>
													</c:forEach>
												</select>
											</c:if></td>
										<td><select id="supplier_name_select" name="supplier_no" id="supplier_no" class="supplier_name_select form-control">
												<option value="">공급업체를 선택해주세요</option>
										</select></td>

										<td><input placeholder="발주수량을 입력해주세요."
											name="factory_order_qty" type="text" class="factory_order_qty form-control"></td>

										<td>
											<button onclick="javascript:add_row()"
												class="btn btn-light btn-light btn-sm">추가하기</button>
										</td>

										<td></td>

									</tr>
								</tbody>
							</table>
						</div>

						<div class="row mt-3">
							<table class="table table-hover">
								<tbody>
									<tr>
										<th scope="row">비고</th>
										<td><input placeholder="비고사항을 입력해주세요."
											name="factory_order_note" id="factory_order_note" type="text" class="form-control"></td>
									</tr>
								</tbody>
							</table>
						</div>

						<div class="row">
							<div class="col"></div>
							<div class="col"></div>
							<div class="col">
								<input type="button" value="등록"
									class="btn btn-primary btn-block" onclick="frm_submit()"
									style="margin-top: 30px; margin-bottom: 30px;">
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