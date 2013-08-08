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
	<input type="hidden" name="curtPage" id="curtPage" value="${curtPage}">
	<div id="search">
		用户名：<input type="text" name="name" id="name" value="${user.name}">
		<input type="button" id="doSearch" value="查询">
	</div>
	<br />
	<div style="height: 95px;">
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
	</div>
	<div>
		<a class="pager" page="1" href="javascript:void(0)">首页</a>
		<c:choose>
			<c:when test="${pager.curtPage != 1}">
				<a class="pager" page="${pager.curtPage - 1}"
					href="javascript:void(0)">上一页</a>
			</c:when>
			<c:otherwise>
				上一页
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${pager.curtPage < pager.totalPage}">
				<a class="pager" page="${pager.curtPage + 1}"
					href="javascript:void(0)">下一页</a>
			</c:when>
			<c:otherwise>
				下一页
			</c:otherwise>
		</c:choose>
		<a class="pager" page="${pager.totalPage}" href="javascript:void(0)">末页</a>
		${pager.curtPage }/${pager.totalPage }
	</div>

	<a href="<%=path%>/user/toAddUser">新增user</a>

	<script type="text/javascript" src="<%=script%>/demo_user/user_list.js"></script>
</body>
</html>
