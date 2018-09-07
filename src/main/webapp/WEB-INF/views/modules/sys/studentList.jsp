<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生信息管理</title>
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
		<li class="active"><a href="${ctx}/sys/student/">学生信息列表</a></li>
		<shiro:hasPermission name="sys:student:edit"><li><a href="${ctx}/sys/student/form">学生信息添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="student" action="${ctx}/sys/student/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>所属班级：</label>
				<form:select path="classId" class="input-medium">
					<form:option value="" label=""/>
					<form:options items="${fns:getAllClassinfoDaoList()}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
			</li>
			<li><label>学号：</label>
				<form:input path="no" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>姓名：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li><label>宿舍号：</label>
				<form:input path="sushe" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li><label>身份证号：</label>
				<form:input path="idcard" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li>
			<li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>所属班级</th>
				<th>学号</th>
				<th>姓名</th>
				<th>宿舍号</th>
				<th>资助卡号</th>
				<th>身份证号</th>
				<th>电话</th>
				<th>更新时间</th>
				<shiro:hasPermission name="sys:student:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="student">
			<tr>
				<td><a href="${ctx}/sys/student/form?id=${student.id}">
					${student.classInfo.name}
				</a></td>
				<td>
					${student.no}
				</td>
				<td>
					${student.name}
				</td>
				<td>
					${student.sushe}
				</td>
				<td>
					${student.zizhucard}
				</td>
				<td>
					${student.idcard}
				</td>
				<td>
					${student.phone}
				</td>
				<td>
					<fmt:formatDate value="${student.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<shiro:hasPermission name="sys:student:edit"><td>
    				<a href="${ctx}/sys/student/form?id=${student.id}">修改</a>
					<a href="${ctx}/sys/student/delete?id=${student.id}" onclick="return confirmx('确认要删除该学生信息吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>