<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新規投稿</title>
</head>
<body>

<c:if test="${ not empty errorMessages }">
	<div class="errorMessages">
		<ul>
			<c:forEach items="${errorMessages}" var="message">
				<li><FONT color="	#ff0000"><c:out value="${message}" /></FONT>
			</c:forEach>
		</ul>
	</div>
	<c:remove var="errorMessages" scope="session"/>
</c:if>
	<div class="form-area">
			<form action="newMessage" method="post"><br />
				<label for="title">タイトル</label><br />
				<input name="title" value="${newMessage.title}" id="title" size="50"/> <br /><br />
				<label for="message">本文</label><br />
				<textarea name="message" rows="5"  cols="100" class="text-box" ></textarea><br /><br />
				<label for="category">カテゴリー</label><br />
				<input name="category"value="${newMessage.category}" id="category" size="20"/> <br /><br />
				<c:remove var="message" scope="session"/>
				<input type="submit" value="投稿する">
			</form>
	</div>
<a href="./">戻る</a>
		<div class="copyright">Copyright(c)Ryosuke Ando</div>
</body>
</html>