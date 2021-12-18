<%@ page language="java" contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>


<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=a5355a3a984042114aa4021986bab36e&libraries=services">

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
                    <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">매장 상세</span></div>
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
                                        <a style="color: gray; font-size: 14px" href="#">매장상세</a>
                                        
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
            <form action="./read_store_page.do" method="post">
            <!-- 테이블 내용 -->
               <div class="row mt-5">
                  <div class="col-2"></div>
                  <div class="col">
                  <table class="table table-hover shadow-sm p-3 mb-2 bg-white rounded">
                     <tbody>
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">매장명</th>
                        <td>${branchVo.branch_name }</td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">담당자</th>
                        <td>${employeeVo.emp_name }</td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">주소</th>
                        <td>${branchVo.branch_address }</td>
                        <td></td>
                     </tr>
                     
                      <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">연락처</th>
                        <td>${branchVo.branch_phone }</td>
                        <td></td>
                     </tr>
                     
                     <tr>
                        <th class="shadow-none p-3 mb-5 bg-light rounded">총 사원 수  </th>
                        <td>${employeeVoForCount }</td>
                     </tr>
                     
                     </tbody>
                  </table>
                  <!-- ----------지도------------ -->
                   <div id="map" style="width:100%;height:400px;"></div>
                        <script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=b1bb2cf59d7c0775ef2583a58ef9d4c4&libraries=services"></script>
						<script>
						var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
						    mapOption = {
						        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
						        level: 3 // 지도의 확대 레벨
						    };  
						
						// 지도를 생성합니다    
						var map = new kakao.maps.Map(mapContainer, mapOption); 
						
						// 주소-좌표 변환 객체를 생성합니다
						var geocoder = new kakao.maps.services.Geocoder();
						
						// 주소로 좌표를 검색합니다
						geocoder.addressSearch('${branchVo.branch_address }', function(result, status) {
						
						    // 정상적으로 검색이 완료됐으면 
						     if (status === kakao.maps.services.Status.OK) {
						
						        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
						
						        // 결과값으로 받은 위치를 마커로 표시합니다
						        var marker = new kakao.maps.Marker({
						            map: map,
						            position: coords
						        });
						
						        // 인포윈도우로 장소에 대한 설명을 표시합니다
						        var infowindow = new kakao.maps.InfoWindow({
						            content: '<div style="width:150px;text-align:center;padding:6px 0;">${branchVo.branch_name }</div>'
						        });
						        infowindow.open(map, marker);
						
						        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
						        map.setCenter(coords);
						    } 
						});    
						</script>
                  <!-- ------------------------------ -->
                     <!-- 버튼들 -->
                     <div class="row mt-3 mb-5">
                        <div class="col"></div>
                        <div class="col"></div>
                        <div class="col-3">
                           <a href="./store_modify.do?branch_no=${param.branch_no}" class="btn btn-primary btn-block">정보수정</a>
                        </div>
                         <div class="col-3">
                          <a href="./store_view.do" class="btn btn-primary btn-block">목록으로</a>
                        </div>
                     </div>
                     
                  </div>
                  <div class="col-2"></div>
               </div>
            </form></c:if><br><br><br><br>
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