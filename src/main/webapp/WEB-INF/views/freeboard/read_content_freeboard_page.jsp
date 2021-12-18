<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<meta charset="UTF-8">
<title>게시글 보기</title>

<script type="text/javascript">

function refreshReplyList(){
	var boardNo = ${freeBoardData.freeBoardVo.freeboard_no };
	
	var xmlhttp = new XMLHttpRequest();
	xmlhttp.onreadystatechange = function(){
		if(xmlhttp.readyState==4 && xmlhttp.status == 200){
			//alert(xmlhttp.responseText);
			var resultData = JSON.parse(xmlhttp.responseText);
			
			//자바스크립트로 동적 UI 꾸미기... 
			var replyBox = document.getElementById("reply_box");
			
			//박스 안에 내용 지우기....
			
			var length = replyBox.childNodes.length;
			for(var i = 0 ; i < length ; i++){
				replyBox.removeChild(replyBox.childNodes[0]);
			}
			
			
			var i=0;
			for(var data of resultData){
				
				
				var temp = document.getElementById("templete");
				var newReplyBox = temp.cloneNode(true);
				newReplyBox.removeAttribute("style");
				//세팅 후...
				newReplyBox.getElementsByClassName("r_writer")[0].innerText = data.employeeVo.emp_name;
				newReplyBox.getElementsByClassName("r_content")[0].innerText = data.freeBoardReplyVo.freeboard_reply_content;
				var emp_code=${sessionUser.employeeVo.emp_code};
			
				newReplyBox.getElementsByClassName("update")[0].removeAttribute("style");
				var pop= "Popup("+data.freeBoardReplyVo.freeboard_reply_no+","+i+")";
				newReplyBox.getElementsByClassName("update")[0].setAttribute("onclick",pop);
				
				newReplyBox.getElementsByClassName("delete")[0].removeAttribute("style");
				
				var func="deleteReply("+data.freeBoardReplyVo.freeboard_reply_no+")";
				newReplyBox.getElementsByClassName("delete")[0].setAttribute("onclick",func);
				
				i=i+1;
				
				document.getElementById("reply_box").appendChild(newReplyBox);
				
			}
				
		}	
				
				
				
			};
		

	
	
	xmlhttp.open("get","./get_reply_list.do?freeboard_no=" + boardNo, true);
	xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xmlhttp.send();
	

}
	
</script>
	
<script type="text/javascript">


	function writeReply(){
		var boardNo = ${freeBoardData.freeBoardVo.freeboard_no };
	   
		var replyContent = document.getElementById("reply_content").value;
	
		if(replyContent=="")
		{
		alert("댓글을 입력하세요");
		return;
		}
		var xmlhttp= new XMLHttpRequest();
		
		xmlhttp.onreadystatechange= function(){
			if(xmlhttp.readyState==4 && xmlhttp.status == 200){
				var input = document.getElementById("reply_content");
				input.value = null;
				refreshReplyList();
			}
		};
	
		xmlhttp.open("post", "${pageContext.request.contextPath }/freeboard/write_reply_process.do", true);
		xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		xmlhttp.send("freeboard_no=" + boardNo + "&freeboard_reply_content=" +replyContent);
	}
	
	 function Popup(freeboard_reply_no,i) 
	 { 
		 var reply= "reply_content"+i;
		 document.getElementsByClassName("r_content")[i].innerHTML="<textarea id=\""+reply+"\" class=\"form-control\"></textarea>";
		 var onclick= "updateReply("+freeboard_reply_no+","+reply+")";
		 document.getElementsByClassName("r_submit")[i].innerHTML="<button class=\"btn btn-sm btn-link\" onclick=\""+onclick+"\">수정</button>";
	 }
	 
	 function updateReply(freeboard_reply_no,reply){
			
			const urlParams = new URLSearchParams(window.location.search);
			
			var freeboard_reply_no = freeboard_reply_no;
			var freeboard_no = urlParams.get("freeboard_no");
			var replyContent = reply.value;
		
			var xmlhttp= new XMLHttpRequest();
			
			xmlhttp.onreadystatechange= function(){
				if(xmlhttp.readyState==4 && xmlhttp.status == 200){
					refreshReplyList();
				}
			};
			
			xmlhttp.open("post", "${pageContext.request.contextPath }/freeboard/update_reply_process.do", true);
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlhttp.send("freeboard_reply_no=" + freeboard_reply_no + "&freeboard_reply_content=" +replyContent + "&freeboard_no=" + freeboard_no);
			
			
		}
	 
	 function deleteReply(freeboard_reply_no){
			
			const urlParams = new URLSearchParams(window.location.search);
			
			var freeboard_reply_no = freeboard_reply_no;
			var freeboard_no = urlParams.get("freeboard_no");
		
			var xmlhttp= new XMLHttpRequest();
			var emp_code = ${freeBoardData.freeBoardVo.emp_code };
			
			xmlhttp.onreadystatechange= function(){
				if(xmlhttp.readyState==4 && xmlhttp.status == 200){
					refreshReplyList();
				}
			};
			
			xmlhttp.open("post", "${pageContext.request.contextPath }/freeboard/delete_reply_process.do", true);
			xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
			xmlhttp.send("freeboard_reply_no=" + freeboard_reply_no + "&freeboard_no=" +freeboard_no);
			
			
		}
</script>	
	
</head>

<body>
<body onload="refreshReplyList()">
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

<!-- 컨테이너 시작 -->
<div class="container-fluid">
		<div class="row">
         <div class="col-1" style="background-color: #f9f9fa"></div>
         
         <!--  사이드바 시작  -->
         <div class="col-2"
            style="padding: 0; border-right: 2px solid #e8ecf1;">
            <ul class="list-group list-group-flush">
               <!--  수정할 부분 시작 -->
            
               
               <!--  수정할 부분 끝 -->
            </ul>
         </div>
         <!--  사이드바 끝   -->
         
         <!--  메인 기능 나오는 곳 -->
         <div class="col pl-5 pr-5">
            <!--  페이지 이름  -->
            <div class="row">
            <div class="col" style="background-color:white">
            <!--1. 타이틀, 네비게이터 시작-->
                <div class="row mt-3">
                
               <!--타이틀-->
               <div class="col-4 mt-3" ><span style="font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight:bold; font-size: 36px;">자유 게시글</span></div>
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
                                       
                                    </li>
                                </ul>             
                            </div>      
                        </div>
                    </div>
                </div>
            <!--  페이지 이름  -->
         
		<div class="row mt-5">
			<div class="col">
				
				<!-- 게시글 상세 -->
            <div class="row mb-3 shadow-none p-3 mb-5 bg-white" style="border: 1px solid #7393a7; border-right-color:white; border-left-color:white;">
            <div class="col">
               <div class="row mb-2">
                  <div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">글번호</div>
                  <div class="col-4 pt-2">${freeBoardData.freeBoardVo.freeboard_no }</div>
                  <div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">작성자</div>
                  <div class="col-4 pt-2">${freeBoardData.freeBoardVo.freeboard_writer }</div>
               </div>
               
               <div class="row mb-2">
                  <div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">직급</div>
                  <div class="col-4 pt-2">${freeBoardData.employeeRankVo.rank_name }</div>
                  <div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">지점명</div>
                  <div class="col-4 pt-2">${freeBoardData.branchVo.branch_name }</div>
               </div>
   
               <div class="row mb-2">
               
                  <div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">작성일</div>
                  <div class="col-4 pt-2">
                  <fmt:formatDate value="${freeBoardData.freeBoardVo.freeboard_writedate }" pattern="yy.MM.dd.HH.mm.ss"/>
                  </div>
                  <div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">조회수</div>
                  <div class="col-4 pt-2">${freeBoardData.freeBoardVo.freeboard_view }</div>
                  
               </div>
               
               <div class="row mb-2">
                  <div class="col-2 pt-2 pb-2 bg-light" style="text-align:center; height:auto">제목</div>
                  <div class="col pt-2">${freeBoardData.freeBoardVo.freeboard_title }</div>
               </div>
               
               <div class="row mb-2">
                  <div class="col pt-2 pb-2 bg-light" style="text-align:center; height:auto">내용</div>
               </div>
               <div class="row">
					<c:if test="${!empty freeBoardData.freeBoardFileVoList}">
					<c:forEach items="${freeBoardData.freeBoardFileVoList }" var="fileVo">
						<img src="/upload/${fileVo.freeboard_file_link_path }" width="300" height="300"> 
						<br>	
					</c:forEach>
				</c:if>
				</div>
               <div class="row">
                  
                     <div class="col">
                       ${freeBoardData.freeBoardVo.freeboard_content }
                     </div>
                  
               </div>
               
            </div>
            </div>
				
		<!-- - -->		
		<div class="row mt-5">
					<div class="col"></div>
					<div class="col"></div>
					<c:if
						test="${!empty sessionUser && sessionUser.employeeVo.emp_code == freeBoardData.freeBoardVo.emp_code}">
						<div class="col-1">
							<a class="btn btn-outline-secondary btn-sm btn-block"
								href="${pageContext.request.contextPath }/freeboard/update_content_freeboard_page.do?freeboard_no=${freeBoardData.freeBoardVo.freeboard_no }">수정</a>
						</div>
						<div class="col-1">
							<a class="btn btn-outline-secondary btn-sm btn-block"
								href="${pageContext.request.contextPath }/freeboard/delete_content_freeboard_page.do?freeboard_no=${freeBoardData.freeBoardVo.freeboard_no }">삭제</a>
						</div>
					</c:if>
					<div class="col-1">
						<a class="btn btn-outline-secondary btn-sm btn-block"
							href="${pageContext.request.contextPath }/freeboard/list_freeboard_page.do">목록</a>
					</div>
		</div>	
				
		<div class="row mt-2" style="font-weight:bold">
		<div class="col">
		코멘트
		</div>

		</div>
		
		<div class="row ml-2 mt-2 bg-light"><!-- 댓글 리스트 박스 -->

			<div id="reply_box" class="col" > <!-- 리스트 컨텐트 박스 -->
			</div>
		</div>
	
		<div class="row mt-2 ml-1 mb-3 bg-light" > <!--댓글 입력 창-->
			<div class="col mt-2">
				<textarea id="reply_content" class="form-control"></textarea>
			</div>
			<div class="col-1 mt-2 mr-2 border border-secondary bg-white text-center" style="vertical-align: middle;">	
				<a style="color:black; font-weight:bold; line-height:60px;" href="javascript:writeReply();">등록</a>
				<!-- <input onclick="writeReply()" type="button" class="btn btn-primary" value="작성"> -->
				
		
				<div>
			
		</div>
				<div>
			

				
			</div>
		</div>
		</div>
		<!--메인기능 내용끝-->
	
	</div>



	<div id="templete" class="row" style="display:none">
		<!-- ..... -->
		<div class="col">
			<div class="row mt-3 border-bottom border-secondary text-left">
				
				<div class="col-2 bg-secondary text-white">작성자</div>
				<img src="${pageContext.request.contextPath}/resources/img/people.png" width="30" height="30">
				<div class="col-2 r_writer">최병민</div>
				<div class="col-2 r_write_date" >2020-6-23</div>
				<div class="col-1"><button class="btn btn-sm btn-link update text-dark" style="display:none">수정</button></div>
				<div class="col-1"><button class="btn btn-sm btn-link delete text-dark" style="display:none">삭제</button></div>
				<div class="col"></div>
			</div>
			
			<div class="row bg-light ">
				<div class="col r_content">내용 어쩌고....먀며ㅗㄷ랴며ㅗㄹ며ㅑ돌</div>
				<div class="col-1 r_submit"></div>
				
				
			</div>
				
	</div>
	</div>
	
	</div>
	</div>
	
   <div class="col-1" style="background-color: #f9f9fa"></div>

      </div>

		<!--  footer  -->
      <jsp:include page="../commons/footer.jsp"></jsp:include>

</div>



<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
</body>
</html>