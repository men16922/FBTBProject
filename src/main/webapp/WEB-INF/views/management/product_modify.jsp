<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
function frm_submit(){

   var frm = document.getElementById("frm"); 

   
   /*
   var reg = /^[가-힣a-zA-Z0-9\s]+$/; //한글+영문(대소문자)+숫자 +공백 정규표현식

   var product_name = document.getElementById("product_name"); 
   // HTML 내에서 id 가 branch_name인 것을 가져온다. (가져온 후 var branch_name 에 넣어준다.)

   if(!(reg.test(product_name.value)) || (product_name.value.length) <= 2){ 
      alert("유효한 이름값을 넣어주세요.");
      return; //값을 DB로 전달하지 않고 return
   }//제품명 검사
   */
   
   var reg = /^[0-9]*$/;
   //숫자만 정규식
   
   var product_price = document.getElementById("product_price");

   
   if(!reg.test(product_price.value) || (product_price.value.length) <= 2 || (product_price.value.length) > 7){ 
      alert("유효한 가격 값을 입력해 주세요.");
      return; //값을 DB로 전달하지 않고 return
   }
   //product_price 값의 유효성 검사
   
   /*   
   var reg = /^[가-힣a-zA-Z\s]+$/; //한글+영문 +공백 정규표현식   
   

   var exampleFormControlTextarea1 = document.getElementById("exampleFormControlTextarea1").value;
   // HTML 내에서 id 가 exampleFormControlTextarea1인 것을 가져온다. (가져온 후 var exampleFormControlTextarea1 에 넣어준다.)

   
   if(!reg.test(exampleFormControlTextarea1.value) || (exampleFormControlTextarea1.value.length) <= 2){ 
      alert("유효한 설명 값을 넣어주세요.");
      return; //값을 DB로 전달하지 않고 return
   }
   */
  /*
   var fileext = document.getElementById("exampleFormControlFile1").value;
   fileext = fileext.slice(fileext.indexOf(".") + 1).toLowerCase(); //파일 확장자를 잘라내고, 비교를 위해 소문자로 만듭니다.


   if(fileext != "jpg" && fileext != "png" &&  fileext != "gif" &&  fileext != "bmp"){ 
      alert("이미지 파일(jpg, png, gif, bmp)만 등록 가능합니다.");
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
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">상품관리</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/product_view.do">상품조회</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/management/product_add.do">상품등록</a></h5></li>

               
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
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">상품 정보 수정</span></div>
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
                                        <a style="color: gray; font-size: 14px" href="#">상품관리</a>
                                        >
                                        <a style="color: gray; font-size: 14px" href="${pageContext.request.contextPath }/management/product_view.do">상품조회</a>
                                       >
                                       <a style="color: gray; font-size: 14px" href="#">상품 정보 수정</a>
                                        
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
<div class="container">


<div class="mt-5"></div>
<c:if test="${!empty sessionUser && sessionUser.deptTypeVo.dept_type_no==1}">
<form action="./product_modify_process.do" method="post" enctype="multipart/form-data" id="frm">
<div class="mt-5 px-5 row">
   
   <div>
      <c:if test="${!empty read_product_page.fileVoList.product_file_link_path }">
   
      <img src="/upload/${product_modify.fileVoList.product_file_link_path }" width="300" height="300"> 
      <br>   
   </c:if>
   </div>
   
   
   
   <div class="col mx-5 px-5 bg-light py-3">
   
   <div class="row mt-1">
   <div class="col-2 mx-3">
   제품번호
   </div>
   <div class="col mx-3">
   ${product_modify.productVo.product_no }
   <input type="hidden" id="product_no" value="${product_modify.productVo.product_no }" name="product_no">
   </div>
   
   </div>
   
   <div class="row mt-1">
   <div class="col-2 mx-3">
   제품명
   </div>
   <div class="col mx-3">
   <input type="text" id="product_name" name="product_name" value="${product_modify.productVo.product_name }" class="form-control">
   </div><div class="col mx-3"></div>
   
   </div>

   <div class="row mt-1">
   <div class="col-2 mx-3">
   제품유형
   </div>
   <div class="col mx-3">
   <select id="product_type_no" name="product_type_no" class="form-control">
                   <option value="">제품유형선택</option>
                   <option value="1" <c:if test="${param.product_type_no==1}">selected="selected"</c:if>>전기밥솥</option>
                   <option value="2" <c:if test="${param.product_type_no==2}">selected="selected"</c:if>>전자레인지/오븐</option>   
                   <option value="3" <c:if test="${param.product_type_no==3}">selected="selected"</c:if>>가스레인지</option>
                   <option value="4" <c:if test="${param.product_type_no==4}">selected="selected"</c:if>>믹서기/원액기</option>   
                   <option value="5" <c:if test="${param.product_type_no==5}">selected="selected"</c:if>>커피머신</option>
                   <option value="6" <c:if test="${param.product_type_no==6}">selected="selected"</c:if>>토스트기</option>
                   <option value="7" <c:if test="${param.product_type_no==7}">selected="selected"</c:if>>전기포트</option>
                   <option value="8" <c:if test="${param.product_type_no==8}">selected="selected"</c:if>>전기그릴/팬</option>
                   <option value="9" <c:if test="${param.product_type_no==9}">selected="selected"</c:if>>식기세척기/살균건조기</option>
                   <option value="10" <c:if test="${param.product_type_no==10}">selected="selected"</c:if>>정수기</option>   
   </select>      
   </div><div class="col mx-3"></div>
   
   </div>
   
   
   <div class="row mt-1">
   <div class="col-2 mx-3">
   가격
   </div>
   <div class="col mx-3">
   <input type="text" id="product_price" name="product_price" value="${product_modify.productVo.product_price }" class="form-control">
   
      
   </div><div class="col mx-3"></div>
   
   </div> 
   
   
   <div class="row mt-1">
   <div class="col-2 mx-3">
   
   제품설명
   </div>
   <div class="col mx-3">
   <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="product_detail">${product_modify.productVo.product_detail }</textarea>
   
   </div>
   
   </div>
      <div class="row mt-1">
   <div class="col-2 mx-3">
   제품 이미지
   </div>
   <div class="col mx-3">

     <div class="form-group">
       <input type="file" multiple accept="image/*" class="form-control-file" id="exampleFormControlFile1" name="product_upload_files">
     </div>
   


   
   </div>
   
   </div>
   
   <br>

   <div class="row mt-3">
   <div class="col mx-3">
      
   </div>
   <div class="col mx-3">
      <input type="button" value="확인" class="btn btn-primary btn-block" onclick="frm_submit()">
   </div>
   <div class="col mx-3">
      <a href="./product_view.do" class="btn btn-outline-primary btn-block">목록으로</a>
   </div>
   </div>
   </div>
</div>
</form></c:if>

   <div class="row mt-5"> <!-- 테이블 -->
            <div class="col">
               
            </div>
         </div>
         
         <div class="row mt-3"> <!-- 버튼들... -->
            <div class="col-8"> <!-- 페이지 버튼 -->
               
            </div>
            <div class="col">
            </div>
         </div>   
</div>
<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>


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