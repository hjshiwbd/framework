<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	        + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'adduser.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

</head>

<body>
	<form action="<%=path%>/user/doAddUser" method="post">
		<table border="1">
			<tr>
				<td>用户名</td>
				<td><input type="text" name="name" />
				</td>
			</tr>
			<tr>
				<td>密码</td>
				<td><input type="text" name="password" />
				</td>
			</tr>
			<tr>
				<td>昵称</td>
				<td><input type="text" name="nickname" />
				</td>
			</tr>
			<tr>
				<td>性别</td>
				<td><input type="radio" name="gender" id="gender1" value="1"
					checked="checked" /> <label for="gender1">男</label> <input
					type="radio" id="gender2" name="gender" value="2" /><label
					for="gender2">女</label></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="提交" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
