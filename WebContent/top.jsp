<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
				<div>＜${loginUser.name}＞でログインしています</div>
				<br />
				<a href="newMessage">新規投稿</a>
				<a href="administer">ユーザー管理</a>
				<a href="logout">ログアウト</a>

			</c:if>
		</div>
	</div>
	<br />
	<br />

	<c:if test="${ not empty errorMessage }"><!--フィルター用のエラーメッセージ -->
		<div class="errorMessage">
			<ul>
				<c:forEach items="${errorMessage}" var="message">
					<li><FONT color="	#ff0000"><c:out value="${errorMessage}" /></FONT>
				</c:forEach>
			</ul>
			<c:remove var="errorMessage" scope="session" />
		</div>
	</c:if>


	<c:if test="${ not empty errorMessages }"><!--コメント用のエラーメッセージ -->
		<div class="errorMessages">
			<ul>
				<c:forEach items="${errorMessages}" var="message">
					<li><FONT color="	#ff0000"><c:out value="${errorMessages}" /></FONT>
				</c:forEach>
			</ul>
		</div>
		<c:remove var="errorMessages" scope="session" />
	</c:if>




	<form action="./" method="get">
		<div><label for="search">＜絞込み検索＞</label></div>
		【カテゴリー選択】<select name="category">
			<option value=""></option>
			<c:forEach var="c" items="${categories}">
				<c:if test="${c.category == selectedCategory}">
				<option value="${c.category}" selected><c:out value="${c.category}" /></option>
				</c:if>
				<c:if test="${c.category != selectedCategory}">
				<option value="${c.category}"><c:out value="${c.category}" /></option>
				</c:if>
			</c:forEach>
		</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<label>【日付選択】<input type="date" name="fromDate" value="${fromDate}">～<input type="date" name="toDate" value="${toDate}"></label>
		<input type="submit" value="送信">
	</form>
	<br />
	<br />



	<div class="messages">
	＜投稿一覧＞
	<hr noshade>
		<c:forEach items="${messages}" var="message">

			<div class="title_category">
			<font size="-1">カテゴリー：<c:out value="${message.category}" /></font><br /><br />
				<font color="blue" size="4"><b><c:out value="${message.title}" /></b></font>
			</div>

			<div class="name-date">
				<font size="-1">投稿者：<b><c:out value="${message.name}" /></b></font>&nbsp;&nbsp;&nbsp;&nbsp;<font
					size="-2"><fmt:formatDate value="${message.insertDate}"
						pattern="yyyy/MM/dd HH:mm:ss" /></font><br />
			</div>
			<br />
			<div class="text"><!--改行含めて表示fn:split -->
				<c:forEach var="s" items="${fn:split(message.text, '
')}"><div>${s}</div></c:forEach>
				<br />
			</div>
			<br />
			<br />

			<br />
			<br />
			<hr size="-2">
			<b>コメント</b>
			<hr size="-2">
			<c:forEach items="${comments}" var="comment">
				<c:if test="${message.id == comment.messageId}">
					<div class="name-date">
						<font size="-1"><b><c:out value="${comment.name}" /></b>
							&nbsp;&nbsp;&nbsp;&nbsp;
						</font><font size="-2"><fmt:formatDate
								value="${comment.insertDate}" pattern="yyyy/MM/dd HH:mm:ss" /></font><br />
					</div>
					<div class="text"><!--改行含めて表示fn:split -->
						<font size="-1"><c:forEach var="s" items="${fn:split(comment.text, '
')}"><div>${s}</div></c:forEach></font><br /><br />

					</div>

				</c:if>
			</c:forEach>
			<div class="form-area">
				<form action="Comment" method="post">
					<textarea name="comment" cols="80" rows="5" id="text"><c:out value="${comment.text}"/></textarea>
					<input type="hidden" name="messageId" value="${message.id}">
					<br /> <input type="submit" value="コメントする"> <br /> <br />
				</form>
			</div>
			<hr noshade>
		</c:forEach>
	</div>
	<div class="copyright">Copyright(c)Ryosuke Ando</div>
</body>
</html>