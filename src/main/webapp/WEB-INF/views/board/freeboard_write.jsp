<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">



</head>
<body>
<!-- navbar -->
<jsp:include page="../commons/global_nav.jsp"></jsp:include>

<!-- 글쓰기 페이지 -->
<form action="${pageContext.request.contextPath }/board/write_content_process.do" method="post" enctype="multipart/form-data">
<div class="container mt-5">
	<div class="row"> <!-- row 7개 -->
		<div class="col-2"></div>
		<div class="col">
			<div class="row mt-5"><!-- 닉네임 -->
					<div class="col-2">
						닉네임:
					</div>
					<div class="col-2">
					${sessionUser.member_nick }
					</div>
			</div>
			<div class="row mt-1"><!-- 제목 -->
					<div class="col-1">
						제목:
					</div>
					<div class="col-3">
					<input type="text" name="board_title">
					</div>
			</div>
			<div class="row mt-1"><!-- 내용 -->
					<div class="col-1">
						내용:
					</div>
			</div>
			
			<div class="row mt-1"><!-- 내용 -->
					<div class="col">
						<textarea rows="10" cols="80" name = "board_content"></textarea>
					</div>
			</div>
			
			<div class="row mt-1"><!-- 글쓰기 -->
					<div class="col-4"></div>
					<div class="col-4">
						<input type="file" name="upload_files" multiple accept="image/*">
					</div>
			</div>
			
			<div class="row mt-1"><!-- 글쓰기 -->
					<div class="col"></div>
					<div class="col-2">
						<input type="submit" value="글쓰기" class="btn btn-primary btn-block">
					</div>
					<div class="col-2">
						<input type="submit" value="수정" class="btn btn-primary btn-block">
					</div>
					<div class="col-2">
						<input type="submit" value="삭제" class="btn btn-primary btn-block">
					</div>
					<div class="col-2">
						<input type="submit" value="목록" class="btn btn-primary btn-block">
					</div>
			</div>
		</div>
	</div>
</div>		
</form>		
		
<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>

</body>
</html>