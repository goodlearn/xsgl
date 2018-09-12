<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生分数管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/sys/studentscore/">学生分数列表</a></li>
		<shiro:hasPermission name="sys:studentscore:edit"><li><a href="${ctx}/sys/studentscore/form">学生分数添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentscore" action="${ctx}/sys/studentscore/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学生学号：</label>
				<form:input path="studentId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>学生分数：</label>
				<form:input path="score" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学生姓名</th>
				<th>学生分数</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:studentscore:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentscore">
			<tr>
				<td><a href="${ctx}/sys/studentscore/form?id=${studentscore.id}">
					${studentscore.student.name}
				</a></td>
				<td>
					${studentscore.score}
				</td>
				<td>
					<fmt:formatDate value="${studentscore.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentscore.remarks}
				</td>
				<shiro:hasPermission name="sys:studentscore:edit"><td>
    				<a href="${ctx}/sys/studentscore/form?id=${studentscore.id}">修改</a>
					<a href="${ctx}/sys/studentscore/delete?id=${studentscore.id}" onclick="return confirmx('确认要删除该学生分数吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>