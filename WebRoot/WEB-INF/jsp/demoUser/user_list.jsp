<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/common/include_static_res.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'list.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>

<body>
	<table border="1">
		<thead>
			<tr>
				<th>id</th>
				<th>name</th>
				<th>password</th>
				<th>nickname</th>
			</tr>
		</thead>

		<tbody>
			<c:forEach items="${userList}" var="user">
				<tr class="userdatarow" id="${user.id}">
					<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.password}</td>
					<td>${user.nickname}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<a href="<%=path%>/user/toAddUser">新增user</a>

	<script type="text/javascript">
		$(function() {
			$('.userdatarow').click(function() {
				var id = $(this).attr('id');
				location.href = '/framework/user/edit/' + id;
			});
		});
	</script>
</body>
</html>
