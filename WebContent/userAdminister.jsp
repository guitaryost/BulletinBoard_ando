<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ユーザー管理</title>

<script type="text/javascript">
<!--

function check(account){
	str = "停止";
	if(account == "false") {
		str = "復活";
	}

	if(window.confirm('アカウントを' + str + 'しますか？')){
		return true;
	}else{
		window.alert('キャンセルされました');
		return false;
	}
}
// -->
</script>

</head>
<body>
<div class="main-contents">
	<div class="header">
	<div><font size="5">【ユーザー管理】</font></div>
	<br />

		<a href="./">TOPに戻る</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="signup">ユーザー登録</a>

	</div>

	<br /><br />
		<div class="users">
		<table border="2" bgcolor="#dcdcdc">
				<tr>
				<th width="200">ログインID</th>
				<th width="200">氏名</th>
				<th width="200">所属</th>
				<th width="200">部署・役職</th>
				<th width="200">ユーザー編集</th>
				<th width="200">アカウント</th>
				</tr>
				</table>
			<c:forEach items="${users}" var="user"><!--ループする配列→items、取り出した値を格納する変数→var-->
			<table border="1">
				<tr>
				<td align="center" width="200"><div class="loginId"><c:out value="${user.loginId}" /><br /></div></td>
				<td align="center" width="200"><div class="name"><c:out value="${user.name}" /></div></td>
				<td align="center" width="200">
					<div class="branch">
						<c:forEach var="branch" items="${branches}">
							<c:if test="${user.branchId == branch.id}">
								<option value="${branch.id}"><c:out value="${branch.name}" /></option>
							</c:if>
						</c:forEach>
					</div>
				</td>
				<td align="center" width="200">
					<div class="department">
						<c:forEach var="department" items="${departments}">
							<c:if test="${user.departmentId == department.id}">
								<option value="${department.id}"><c:out value="${department.name}" /></option>
							</c:if>
						</c:forEach>
					</div>
				</td>
				<td align="center" width="200">
					<div class="edit">
						<a href="edit?id=${user.id}">編集する</a>
					</div>
				</td>
				<td align="center" width="200">
					<c:if test="${loginUser.id != user.id}">
						<form action="administer" method="post" onSubmit='return check("${user.account}")'>
							<input type="hidden" name="id" value="${user.id}">
								<c:if test="${user.account == true}">
									<input type="hidden" name="account" value="false">
									<input type="submit" style="color:red;" value=" 停止">
							</c:if>
								<c:if test="${user.account == false}">
									<input type="hidden" name="account" value="true">
									<input type="submit" value="復活">
								</c:if>
						</form>
					</c:if>
				</tr>
			</table>
			</c:forEach>

		</div>
		<br />
</div>
<div class="copyright">Copyright(c)Ryosuke Ando</div>
</body>
</html>