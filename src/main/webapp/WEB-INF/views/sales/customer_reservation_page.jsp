<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>Insert title here</title>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">

<script type="text/javascript">

	function check(){
   		var frm = document.getElementById("frm");
   		var customer_no = document.getElementById("customer_no").value;
   		var product_name=document.getElementById("product_name").value;
   		var customer_res_qty = document.getElementById("customer_res_qty").value;

		var max_cusno= ${max_cusno};
   		if(customer_no=="" || product_name=="" || customer_res_qty==""){
   			alert("고객주문정보를 전부 입력하세요");
   			return;
   		}
   		else if( Number(customer_res_qty)<=0 || Number(customer_res_qty)>99999999){
   			alert("숫자를 1~99999999 사이로 입력하세요")
   		}
   		else if( Number(customer_no)<=0 || Number(customer_no)>max_cusno){
   			alert("고객번호를 1~"+max_cusno+"사이로 입력하세요.");
   		}
   		else{

		frm.submit();
   		}

   		
   	}
</script>
</head>
<body>
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
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">고객주문관리</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/customer_view_page.do">고객조회</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/customer_reservation_page.do">고객주문등록</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/sales/customer_reservation_view_page.do">고객주문조회</a></h5></li>
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
                        <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">고객주문등록</span></div>
                        <div class="col"></div>
                        <!---네비게이터-->
                        <div class="col-7">
                            <div class="row">                                   
                                <div class="col"></div>      
                            </div>
                            <div class="row"> 
                                <div class="col-2"></div> 
                                <div class="col mt-5" style="display:inline;">     
                                    <ul style="list-style:none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
                                        <li>
                                            <a style="color: gray; font-size: 14px" href="#">고객주문관리</a>
                                            >
                                            <a style="color: gray; font-size: 14px" href="#">고객주문등록</a>
                              
                                        </li>
                                    </ul>             
                                </div>      
                            </div>
                        </div>
                    </div>
                    <!--1. 타이틀, 네비게이터 끝-->

                    <form id="frm" action="./customer_reservation_process.do" method="post">
	<div class="mt-5 px-5 mb-5 row" >
	   
	   <div class="col mx-5 px-5 py-3 bg-light" >
	   
	   <div class="row px-3">
		<div class="col">
		<ul style="font-size:large;
		background-color: #dcdcdc;
		text-align:center;
		color:#212529;
		font-size:30px;
		" >고객주문정보</ul>
		</div>
	
		</div>
	   
	   <div class="row mt-1">
	   <div class="col-2 mx-3">
		  고객번호
	   </div>
	   <div class="col mx-3">
	   <c:if test="${cusno!=0 }">
	   ${cusno }
	   <input type="hidden" id="customer_no" name="customer_no" value="${cusno }">
	   </c:if>
	   <c:if test="${cusno==0 }">
	   <input onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" placeholder="고객번호를 입력해주세요." id="customer_no" name="customer_no" type="text" class="form-control">
	  </c:if>
	   </div>
	   
	   </div>
	
	   <c:if test="${cusname!='no' }">
	   <div class="row mt-1">
	   <div class="col-2 mx-3">
			 고객이름
	   </div>
	   <div class="col mx-3">
	   ${cusname }
	   </div>
	   </div>
	   </c:if>
	   
	   <div class="row mt-1">
	   <div class="col-2 mx-3">
		  제품이름
	   </div>
	
	
				<div class="col mx-3" id="pro_form">
				   <c:if test="${!empty store_product }">
					  <select name="product_name" id="product_name" class="form-control">
						 <option value="">제품을 선택해주세요.</option>
						 <c:forEach var="data" items="${store_product }">
							<option value="${data.productVo.product_name }">
							   ${data.productVo.product_name }</option>
						 </c:forEach>
					  </select>
				   </c:if>
			  
				</div>
	  </div>
	   
	
	   
	   <div class="row mt-1">
	   <div class="col-2 mx-3">
		  수량
	   </div>
	   <div class="col mx-3">
	   <input onKeyup="this.value=this.value.replace(/[^0-9]/g,'');" placeholder="수량을 입력해주세요." id="customer_res_qty" name="customer_res_qty" type="text" class="form-control">
	   </div>
	   
	   </div>
		
		<div class="row mt-1">
	   <div class="col-2 mx-3">
		  담당자
	   </div>
	   <div class="col mx-3">
	   ${sessionUser.employeeVo.emp_name}
	   </div>
	   
	   </div>
		
	   <div class="row mt-3">
	   <div class="col"></div>
	   <div class="col-3 mx-3">
		  <input type="button" value="고객주문등록" class="btn btn-primary btn-block" onclick="check()">
	   </div>
	   </div>
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
	 <jsp:include page="../sales/sales_alert.jsp"></jsp:include> 
	 <jsp:include page="../sales/sales_alert2.jsp"></jsp:include> 


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>