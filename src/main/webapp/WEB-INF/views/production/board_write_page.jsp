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
	function sub_form() {

		var frm = document.getElementById("frm");

		var freeboard_title = document.getElementById("dept_board_title").value;
		var freeboard_content = document.getElementById("dept_board_content").value;

		if (freeboard_title == "") {
			alert("제목을 입력해주세요.");
			return;
		} else if (freeboard_content == "") {
			alert("내용을 입력해주세요.");
			return;
		} else {

			frm.submit();
		}

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
				style="padding: 0; border-right: 2px solid #e8ecf1;">
				<ul class="list-group list-group-flush">
					<!--  수정할 부분 시작 -->
					<li class="list-group-item"
						style="height: 105px; background-color: #7393a7; color: white; font-weight: bold;"><h2
							class="mt-3" align="center">부서게시판</h2></li>
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
									style="font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif; color: rgb(46, 46, 46); font-weight: bold; font-size: 36px;">부서게시글
									쓰기</span>
							</div>
							<div class="col"></div>
							<!---네비게이터-->
							<div class="col-7">
								<div class="row">
									<div class="col"></div>
								</div>
								<div class="row">
									<div class="col-2"></div>
									<div class="col mt-5" style="display: inline;">
										<ul
											style="list-style: none; margin-top: 10px; margin-bottom: 5px; margin-left: 100px; text-decoration-color: gray">
											<li></li>
										</ul>
									</div>
								</div>
							</div>
						</div>
						<!--1. 타이틀, 네비게이터 끝-->

						<form id="frm"
							action="${pageContext.request.contextPath }/production/board_write_process.do"
							method="post" enctype="multipart/form-data">

							<!-- 게시글 작성 내용 -->
							<div class="row mt-5">
								<div class="col">

									<div class="row mb-3">
										<div class="col">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">작성자</span>
												</div>
												<input class="form-control" type="text" id="text-input"
													placeholder="${sessionUser.employeeVo.emp_name }" readonly>
												<input type="hidden"
													value="${sessionUser.employeeVo.emp_code }" name="emp_code">
												<input type="hidden"
													value="${sessionUser.employeeVo.emp_name }"
													name="freeboard_writer">
											</div>
										</div>
									</div>





									<div class="row mb-3">
										<div class="col">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">제목</span>
												</div>
												<input class="form-control" placeholder="30자 이내로 작성해주세요"
													type="text" id="dept_board_title" name="dept_board_title">
											</div>
											<div class="col ml-5">
												<c:if test="${sessionUser.employeeVo.rank_no <= 5 }">
													<input type="radio" name="check_notice" value="Y"> 공지사항등록
                  				</c:if>

											</div>
										</div>
									</div>




									<div class="row mb-3">
										<!-- <div class="col-2 bg-light" style="text-align: center">내용</div> -->
										<div class="col">
											<div class="input-group">
												<div class="input-group-prepend">
													<span class="input-group-text">내용</span>
												</div>
												<textarea class="form-control" rows="10" cols="80"
													aria-label="With textarea" id="dept_board_content"
													name="dept_board_content"></textarea>

											</div>
										</div>

									</div>

									<div class="row">
										<div class="col">
											<div class="input-group mb-3">
												<div class="input-group-prepend">
													<span class="input-group-text" id="inputGroupFileAddon01">파일업로드</span>
												</div>
												<div class="custom-file">
													<input type="file" class="custom-file-input" id="inputGroupFile01" aria-describedby="inputGroupFileAddon01" name="board_upload_files" multiple accept="image/*"> 
														<label class="custom-file-label" for="inputGroupFile01">파일을선택해주세요</label>
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col"></div>
										<div class="col-3 mt-3 mb-5">
											<a
												href="${pageContext.request.contextPath }/production/main_page.do"><input
												type="button" class="btn btn-primary btn-block" value="목록으로"></a>
										</div>
										<div class="col-3 mt-3 mb-5">
											<input type="button" class="btn btn-primary btn-block"
												value="작성완료" onclick="sub_form()">
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