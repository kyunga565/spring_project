<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
#dropBox {width: 400px; height: 300px; border: 1px dotted gray; overflow: auto;}
#dropBox img{width:100px;height:130px;margin:10px;}
#dropBox div.item{width:120px;float: left;position: relative;margin:5px; border:1px dotted gray;}
#dropBox button.del{position: absolute;right: 3px;top: 3px;font-weight: bold;}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<style type="text/css">
</style>
</head>
<body>
<jsp:include page="header.jsp" />
<div id="body">
	<form action="upload" id="form1" method="post" enctype="multipart/form-data">
		<fieldset>
			<input type="text" name="writer" id="writer" placeholder="작성자" />
			<input type="submit" value="사진업로드하기" />
		</fieldset>
	</form>
	<div id="dropBox">	
	
	</div>
</div>
<jsp:include page="footer.jsp" />
</body>	
	
	<script type="text/javascript">
		var formdata =  new FormData()
		$("#dropBox").on("dragenter dragover",function(e){
			e.preventDefault()
		})
		$("#dropBox").on("drop",function(e){
			//브라우저에서 파일으 ㄹ끌어다 놓아도 아무런 동작을 하지않게
			//이처리가 없으면 이미지가 새창에나타남
			e.preventDefault()
			
			var files = e.originalEvent.dataTransfer.files
			var file = files[0]
			console.log(file)
			
			//drag한 이미지 드롭박스에 나타나게
			var reader = new FileReader()
			reader.addEventListener("load",function(){
				var $img = $("<img>")
				$img.attr("src",reader.result)
				$("#dropBox").append($img)
			})
			
			if(file){
				reader.readAsDataURL(file)
				
				//드롭은 input type=file로 보내지지않음 그래서 formdata라는 함수를 써서보냄
				if(formdata != null){
					formdata.append("files",file)
				}
				
			}
		})
		
		$("#form1").on("submit",function(e){
			e.preventDefault();
			formdata.append("writer" , $("#writer").val())
			$.ajax({
				url : "upload",
				data : formdata,
				processData : false,
				contentType : false,
				type : "post",
				success : function(data) {
					$("#dropBox").empty()
					
					$(data).each(function(i,obj){
						var $div = $("<div>").addClass("item")
						var $img = $("<img>")
						$img.attr("src","displayFile?filename="+obj)
						$div.append($img)
						
						var $button = $("<button value='"+ obj +"'>").addClass("del").text("X")
						$div.append($button)
						$("#dropBox").append($div)
					})
					$("input[type='submit']").val("사진추가하기")
				}
			})
		})
		
$(document).on("click",".del",function(e){
	e.preventDefault();
	$.ajax({
		url : "removeFile",
		type : "post",
		data : { filename : $(".del").val() },
		dataType : "text",
		success : function(result){
		//	$("#imgFile").attr("class","fileRemoveSuccess")
			$(".item").empty()
		}
	})
})
	</script>
</html>