<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>记录类型管理</title>
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
		<li class="active"><a href="${ctx}/sys/recordtype/">记录类型列表</a></li>
		<shiro:hasPermission name="sys:recordtype:edit"><li><a href="${ctx}/sys/recordtype/form">记录类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="recordtype" action="${ctx}/sys/recordtype/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>键值：</label>
				<form:input path="typekey" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>对应分值：</label>
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
				<th>键值</th>
				<th>名称</th>
				<th>对应分值</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="sys:recordtype:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="recordtype">
			<tr>
				<td>
					${recordtype.typekey}
				</td>
				<td><a href="${ctx}/sys/recordtype/form?id=${recordtype.id}">
					${recordtype.name}
				</a></td>
				<td>
					${recordtype.score}
				</td>
				<td>
					<fmt:formatDate value="${recordtype.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${recordtype.remarks}
				</td>
				<shiro:hasPermission name="sys:recordtype:edit"><td>
    				<a href="${ctx}/sys/recordtype/form?id=${recordtype.id}">修改</a>
					<a href="${ctx}/sys/recordtype/delete?id=${recordtype.id}" onclick="return confirmx('确认要删除该记录类型吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>