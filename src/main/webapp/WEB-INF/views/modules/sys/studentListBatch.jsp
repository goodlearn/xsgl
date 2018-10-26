<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>奖惩记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/sys/studentrecord/">奖惩记录列表</a></li>
	</ul><br/>
	<sys:message content="${message}"/>	
	<c:if test = "${stuNum <= 0 }">
	    	无学生信息
    </c:if>	
    
  
    
    <c:if test = "${stuNum > 0 }">
	    	<form:form id="inputForm" modelAttribute="studentrecord" action="${ctx}/sys/studentrecord/saveBatch" method="post" class="form-horizontal">
			
			<div class="control-group">
				<label class="control-label">学生学号</label>
				<div class="controls">
					<form:checkboxes path="id" items="${stuList}" itemLabel="name" itemValue="no" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label">增减类型</label>
				<div class="controls">
					<form:radiobuttons path="scoreType" items="${fns:getDictList('scoreType')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">分值</label>
				<div class="controls">
					<form:input path="score" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">原因</label>
				<div class="controls">
					<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
			<div class="form-actions">
				<shiro:hasPermission name="sys:studentrecord:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
				<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
			</div>
		</form:form>
    </c:if>
</body>
</html>