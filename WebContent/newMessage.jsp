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
<div class="form-area">
	<form action="newMessage" method="post"><br />
		<div><font size="5">【新規投稿】</font></div>
 	<br />
 	<a href="./">TOPに戻る</a>
 	<br />
 	<br />
	<c:if test="${ not empty errorMessages }">
		<div class="errorMessages">
			<ul style ="list-style:none;">
				<c:forEach items="${errorMessages}" var="message">
					<li><font color="	#ff0000"><c:out value="${message}" /></font>
				</c:forEach>
			</ul>
			<c:remove var="errorMessages" scope="session"/>
		</div>
	</c:if>
<table class="newMessage" border="2" bgcolor="#f5f5f5">
  <tr>
    <th><label>【タイトル】</label></th>
    <td><input name="title" value="${newMessage.title}" id="title" size="50"  maxlength="50"/></td>
  </tr>
  <tr>
    <th><label>【本文】</label></th>
    <td><textarea name="text" rows="5" cols="100" id="text"  maxlength="1000"><c:out value="${newMessage.text}" /></textarea></td>
  </tr>
  <tr>
    <th><label>【カテゴリー】</label></th>
    <td><input name="category"value="${newMessage.category}" id="category" size="20"  maxlength="10"/></td>
  </tr>
 </table>
<br>
<div><input type="submit" value="投稿する"></div>
</form>
</div>
<br>

<div class="copyright">Copyright(c)Ryosuke Ando</div>
</body>
</html>