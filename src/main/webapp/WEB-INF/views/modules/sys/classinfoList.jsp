<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>wzy管理</title>
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
		<li class="active"><a href="${ctx}/sys/classinfo/">班级列表</a></li>
		<shiro:hasPermission name="sys:classinfo:edit"><li><a href="${ctx}/sys/classinfo/form">班级添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="classinfo" action="${ctx}/sys/classinfo/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>所属专业：</label>
				<form:select path="professionId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllProfessioninfoList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>所属专业</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:classinfo:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="classinfo">
			<tr>
				<td><a href="${ctx}/sys/classinfo/form?id=${classinfo.id}">
					${classinfo.name}
				</a></td>
				<td>
					${classinfo.professioninfo.name}
				</td>
				<td>
					<fmt:formatDate value="${classinfo.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${classinfo.remarks}
				</td>
				<shiro:hasPermission name="sys:classinfo:edit"><td>
    				<a href="${ctx}/sys/classinfo/form?id=${classinfo.id}">修改</a>
					<a href="${ctx}/sys/classinfo/delete?id=${classinfo.id}" onclick="return confirmx('确认要删除该wzy吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>