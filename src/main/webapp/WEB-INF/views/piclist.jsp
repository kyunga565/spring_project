<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
<jsp:include page="header.jsp" />
<a href="${pageContext.request.contextPath}/picview" id="addpic">사진등록하러가기</a>
<div id="body2">
<script type="text/javascript">
	$(function(){
		for (var i = 0; i < $(".img").size(); i++ ) {
			$(".img").eq(i).click(function() {
				var sImg = $(this).attr("value")
				var thumName1 = sImg.substring(0, 12);
				var thumName2 = sImg.substring(14);
				var file = thumName1 + thumName2;
				doImgPop("displayFile?filename=" + file)
			})
		}
	})
</script>
	<c:forEach var="item" items="${userVO}">
		<div id="imgFile">
			<input type="image" src="displayFile?filename=${item}" value="${item }" class="img"  style="cursor: pointer;" />
			<button class="delFile" value="${item }">X</button> 
		</div>
	</c:forEach>
</div>
<jsp:include page="footer.jsp" />
</body>
<script>
$(".delFile").each(function(i,obj){
	$(".delFile").eq(i).click(function(e){
		e.preventDefault();
		$.ajax({
			url : "removeFile",
			type : "post",
			data : { filename : $(this).val() },
			dataType : "text",
			success : function(result){
				$("#imgFile").eq(i).remove()
				$('#body2').load(document.URL + ' #imgFile');
				
			}
		})
	})
})
function doImgPop(img){ 
 	 img1= new Image(); 
	 img1.src=(img); 
	 imgControll(img); 
} 	  
function imgControll(img){ 
 if((img1.width!=0)&&(img1.height!=0)){ 
    viewImage(img); 
  } 
  else{ 
     controller="imgControll('"+img+"')"; 
     intervalID=setTimeout(controller,20); 
  } 
}
function viewImage(img) {
	W = img1.width;
	H = img1.height;
	O = "width=" + W + ",height=" + H + ",scrollbars=yes";
	imgWin = window.open("", "", O);
	imgWin.document.write("<html><head><title>이미지상세보기</title></head>");
	imgWin.document.write("<body topmargin=0 leftmargin=0>");
	imgWin.document.write("<img src=" + img + " onclick='self.close()' style='cursor:pointer;'>");
	imgWin.document.close();
}
</script>
<style>
#addpic {
	font-size: 12px;
	color: green;
	text-decoration: none;
	margin-left: 25%;
}

#imgFile {
	padding: 10px;
	margin: 10px;
	float: left;
}

.delFile {
	position:absolute;
	border: 1px solid black;
	background-color: rgba(255, 255, 255, 0.8);
	font-size: 12px;
}

</style>
</html>