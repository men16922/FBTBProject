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
function getFormatDate(date){
   var year = date.getFullYear().toString(); 
      year=year.substring(2,4);              //yyyy
   var month = (1 + date.getMonth());          //M
   month = month >= 10 ? month : '0' + month;  //month 두자리로 저장
   var day = date.getDate();                   //d
   day = day >= 10 ? day : '0' + day;          //day 두자리로 저장

   var hours = date.getHours()+9; // 시
   hours = hours >= 10 ? hours : '0' + hours;
   var minutes = date.getMinutes();  // 분
   minutes = minutes >= 10 ? minutes : '0' + minutes;
   var seconds = date.getSeconds();  // 초
   seconds = seconds >= 10 ? seconds : '0' + seconds;
return  year + '/' + month + '/' + day + '&nbsp&nbsp&nbsp&nbsp&nbsp' + hours + ':' + minutes + ':' + seconds;       //'-' 추가하여 yyyy-mm-dd 형태 생성 가능
}

<c:forEach items="${list }" var="list">
var date = new Date("${list.deliveryVo.delivery_date}");
date = getFormatDate(date);
</c:forEach>

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
               <li class="list-group-item" style="height: 105px; background-color: #7393a7; color:white; font-weight:bold;"><h2 class="mt-3" align="center">발주관리</h2></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/product_order_list_page.do">발주조회</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/delivery_request_page.do">생산예정리스트</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/delivery_ready_page.do">배송등록</a></h5></li>
               <li class="list-group-item"><h5><a style="color: #6c737e;" href="${pageContext.request.contextPath }/production/delivery_list_page.do">배송조회</a></h5></li>
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
                           style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">배송상세내역</span>
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
                                 <li><a style="color: gray; font-size: 14px" href="#">발주관리</a>
                                    > <a style="color: gray; font-size: 14px"
                                    href="${pageContext.request.contextPath }/production/delivery_list_page.do">배송조회</a>
                                    > <a style="color: gray; font-size: 14px"
                                    href="${pageContext.request.contextPath }/production/delivery_list_read_page.do">배송상세조회</a>
                                 </li>
                              </ul>
                           </div>
                        </div>
                     </div>
                  </div>
                  <!--1. 타이틀, 네비게이터 끝-->

                  <!-- 테이블  -->
                  <div class="row">
                     <div class="col"></div>
                      <div class="col">
                         <c:forEach items="${list }" var="list">
                     <table class="table table-bordered mt-5 table-sm" style="width:500px;">
                        <tbody>
                           <tr>
                              <th scope="row" class="text-center bg-light rounded">운송장번호</th>
                              <td class="text-center">${list.deliveryVo.invoice_no }</td>
                           </tr>
                           <tr> 
                              <th scope="row" class="text-center bg-light rounded">발주번호</th>
                              <td class="text-center">${list.deliveryVo.store_order_detail_no }</td>
                           </tr>
                           <tr>
                              <th scope="row" class="text-center bg-light rounded">담당자</th>
                              <td class="text-center">${list.employeeVo.emp_name }</td>
                           </tr>
                           <tr>
                              <th scope="row" class="text-center bg-light rounded">매장명</th>
                              <td class="text-center">${list.branchVo.branch_name }</td>
                           </tr>
                           <tr>
                              <th scope="row" class="text-center bg-light rounded">상품명</th>
                              <td class="text-center">${list.productVo.product_name }</td>
                           </tr>
                           <tr>
                              <th scope="row" class="text-center bg-light rounded">배송지주소</th>
                              <td class="text-center">${list.branchVo.branch_address }</td>
                           </tr>
                           <tr>
                              <th scope="row" class="text-center bg-light rounded">전화번호</th>
                              <td class="text-center">${list.branchVo.branch_phone }</td>
                           </tr>
                           <tr>
                              <th scope="row" class="text-center bg-light rounded">도착예정일</th>
                              <td class="text-center"><fmt:formatDate value="${list.deliveryVo.delivery_date }"
                              pattern="yy-MM-dd" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${list.deliveryVo.delivery_date }"
                              pattern="HH:mm:ss" /></td>
                           </tr>
                           <tr>
                              <th scope="row" class="text-center bg-light rounded">현재상태</th>
                              <td class="text-center">${list.processListVo.process_name }</td>
                           </tr>
                        </tbody>
                     </table>
   
                  </c:forEach>
                       </div>
                       <div class="col mt-5">
                          <!-- 이미지 나오는 col -->
                          <c:forEach items="${list }" var="file">
                           <img src="/upload/${file.productFileVo.product_file_link_path }" width="300" height="300">
                           <br>
                        </c:forEach>
                       </div>
                       <div class="col"></div>
                  </div>
                  
                  <div class="row">
                     <div class="col"></div>
                     <div class="col"></div>
                     <div class="col">
                        <a class="btn btn-outline-primary mr-2 mb-5"
                           href="${pageContext.request.contextPath }/production/delivery_list_page.do">목록으로</a>
                     </div>
                  </div>
                  <!-- 테이블 끝 -->

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