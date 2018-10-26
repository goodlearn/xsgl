<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>奖惩记录管理</title>
	<meta name="decorator" content="default"/>
		<script type="text/javascript">
		$(document).ready(function() {
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出学生数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/studentrecord/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#btnExportAll").click(function(){
				top.$.jBox.confirm("确认要导出学生数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/sys/studentrecord/exportAll");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
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
		<li class="active"><a href="${ctx}/sys/studentrecord/">奖惩记录列表</a></li>
		<shiro:hasPermission name="sys:studentrecord:edit"><li><a href="${ctx}/sys/studentrecord/form">奖惩记录单个添加</a></li></shiro:hasPermission>
		<shiro:hasPermission name="sys:studentrecord:batchedit"><li><a href="${ctx}/sys/student/listBatch">奖惩记录批量添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="studentrecord" action="${ctx}/sys/studentrecord/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>学生学号：</label>
				<form:input path="studentId" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
				<shiro:hasPermission name="sys:studentrecord:batchedit">
					<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
					<input id="btnExportAll" class="btn btn-primary" type="button" value="学号导出"/>
				</shiro:hasPermission>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>学生姓名</th>
				<th>分值</th>
				<th>增减类型</th>
				<th>更新时间</th>
				<th>原因</th>
				<shiro:hasPermission name="sys:studentrecord:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentrecord">
			<tr>
				<td><a href="${ctx}/sys/studentrecord/form?id=${studentrecord.id}">
					${studentrecord.student.name}
				</a></td>
				<td>
					${studentrecord.score}
				</td>
				<td>
					${fns:getDictLabel(studentrecord.scoreType, 'scoreType', '')}
				</td>
				<td>
					<fmt:formatDate value="${studentrecord.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${studentrecord.remarks}
				</td>
				<shiro:hasPermission name="sys:studentrecord:edit"><td>
    				<%-- <a href="${ctx}/sys/studentrecord/form?id=${studentrecord.id}">修改</a> --%>
					<a href="${ctx}/sys/studentrecord/delete?id=${studentrecord.id}" onclick="return confirmx('确认要删除该奖惩记录吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>