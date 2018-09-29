<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生奖惩记录管理</title>
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
		<li class="active"><a href="${ctx}/sys/studentrecord/">学生奖惩记录列表</a></li>
		<shiro:hasPermission name="sys:studentrecord:edit"><li><a href="${ctx}/sys/studentrecord/form">学生奖惩记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentrecord" action="${ctx}/sys/studentrecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>记录：</label>
				<form:select path="recordId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllRecordtypeList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>学生名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>记录</th>
				<th>学生名称</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:studentrecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentrecord">
			<tr>
				<td><a href="${ctx}/sys/studentrecord/form?id=${studentrecord.id}">
					${studentrecord.recordType.name}
				</a></td>
				<td>
					${studentrecord.student.name}
				</td>
				<td>
					<fmt:formatDate value="${studentrecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentrecord.remarks}
				</td>
				<shiro:hasPermission name="sys:studentrecord:edit"><td>
    				<a href="${ctx}/sys/studentrecord/form?id=${studentrecord.id}">修改</a>
					<a href="${ctx}/sys/studentrecord/delete?id=${studentrecord.id}" onclick="return confirmx('确认要删除该学生奖惩记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>