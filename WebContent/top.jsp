<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>掲示板</title>
</head>
<body>
	<div class="main-contents">
		<div class="header">

			<c:if test="${ not empty loginUser }">
				<a href="newMessage">新規投稿</a>
				<a href="administer">ユーザー管理</a>
				<a href="logout">ログアウト</a>
			</c:if>
		</div>
	</div>
	<br />
	<br />

	<c:if test="${ not empty errorMessage }">
		<div class="errorMessage">
			<ul>
				<c:forEach items="${errorMessage}" var="message">
					<li><FONT color="	#ff0000"><c:out value="${message}" /></FONT>
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessage" scope="session" />
	</c:if>


	<div class="messages">
		<c:forEach items="${messages}" var="message">
			<div class="name-date">
				<font size="-1">投稿者：<c:out value="${message.name}" /></font>&nbsp;&nbsp;&nbsp;&nbsp;<font
					size="-2"><fmt:formatDate value="${message.insertDate}"
						pattern="yyyy/MM/dd HH:mm:ss" /></font><br />
			</div>
			<div class="title">
				タイトル：<font color="blue"><c:out value="${message.title}" /></font><br />
			</div>
			<div class="text">
				<c:out value="${message.text}" />
				<br />
			</div>
			<br />
			<br />
			<c:if test="${ not empty errorMessages }">
				<div class="errorMessages">
					<ul>
						<c:forEach items="${errorMessages}" var="message">
							<li><FONT color="	#ff0000"><c:out value="${message}" /></FONT>
						</c:forEach>
					</ul>
				</div>
				<c:remove var="errorMessages" scope="session" />
			</c:if>

			<c:forEach items="${comments}" var="comment">
				<c:if test="${message.id == comment.messageId}">
					<div class="name-date">
						<font size="-1">from:<c:out value="${comment.name}" />
							&nbsp;&nbsp;&nbsp;&nbsp;
						</font><font size="-2"><fmt:formatDate
								value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></font><br />
					</div>
					<div class="text">
						<font size="-1"><c:out value="${comment.text}" /></font><br />
					</div>

				</c:if>
			</c:forEach>
			<div class="form-area">
				<form action="Comment" method="post">
					<textarea name="comment" cols="80" rows="5" class="comment-box"></textarea>
					<input type="hidden" name="messageId" value="${message.id}">
					<br /> <input type="submit" value="コメントを投稿"> <br /> <br />
				</form>
			</div>
		</c:forEach>
	</div>
	<div class="copyright">Copyright(c)Ryosuke Ando</div>
</body>
</html>