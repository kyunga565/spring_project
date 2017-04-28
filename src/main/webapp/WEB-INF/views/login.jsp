<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
	form{margin-left: 200px;}
</style>
</head>
<body>
<jsp:include page="header.jsp" />
<div id="body">
	<form action="loginpost" method="post">
		<label for="">ID</label><input type="text" name="userid" /><br>
		<label for="">PW</label><input type="text" name="userpw" /><br>
		<p id="btn">
			<button type="submit" class="btn">로그인</button>
		</p>
	</form>
</div>
<jsp:include page="footer.jsp" />
<script>
var result = '${result}'
	if (result == "success") {
		alert("회원가입성공")
	}
</script>
</body>
</html>