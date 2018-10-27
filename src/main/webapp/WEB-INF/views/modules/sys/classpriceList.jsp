<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>班费记录管理</title>
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
		<li class="active"><a href="${ctx}/sys/classprice/">班费记录列表</a></li>
		<shiro:hasPermission name="sys:classprice:edit"><li><a href="${ctx}/sys/classprice/form">班费记录添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="classprice" action="${ctx}/sys/classprice/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>查询班级：</label>
				<form:select path="classId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${clsList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<%-- <li><label>消费：</label>
				<form:input path="score" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li> --%>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>班级名称</th>
				<th>消费</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:classprice:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="classprice">
			<tr>
				<td><a href="${ctx}/sys/classprice/form?id=${classprice.id}">
					${classprice.classInfo.name}
				</a></td>
				<td>
					${classprice.score}
				</td>
				<td>
					${classprice.remarks}
				</td>
				<shiro:hasPermission name="sys:classprice:edit"><td>
    				<a href="${ctx}/sys/classprice/form?id=${classprice.id}">修改</a>
					<a href="${ctx}/sys/classprice/delete?id=${classprice.id}" onclick="return confirmx('确认要删除该班费记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>