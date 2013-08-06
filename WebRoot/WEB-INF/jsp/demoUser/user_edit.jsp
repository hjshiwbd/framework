<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include_static_res.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>My JSP 'user_detail.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<form action="<%=path%>/user/do_edit" method="post">
		<input type="hidden" name="id" value="${user.id}">
		<p>
			name:<input type="text" name="name" id="" value="${user.name}" />
		</p>
		<p>
			password:<input type="text" name="password" id=""
				value="${user.password}" />
		</p>
		<p>
			nickname:<input type="text" name="nickname" id=""
				value="${user.nickname}" />
		</p>
		<p>
			<input type="submit" value="提交" />
		</p>
	</form>
</body>
</html>
