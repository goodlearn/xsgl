<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>学生信息管理</title>
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
		<li><a href="${ctx}/sys/student/">学生信息列表</a></li>
		<li class="active"><a href="${ctx}/sys/student/form?id=${student.id}">学生信息<shiro:hasPermission name="sys:student:edit">${not empty student.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="sys:student:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="student" action="${ctx}/sys/student/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">所属班级：</label>
			<div class="controls">
				<form:select path="classId" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${clsList}" itemLabel="name" itemValue="id" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学号：</label>
			<div class="controls">
				<form:input path="no" htmlEscape="false" maxlength="64" class="input-xlarge  required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">宿舍号：</label>
			<div class="controls">
				<form:input path="sushe" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资助卡号：</label>
			<div class="controls">
				<form:input path="zizhucard" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:input path="sex" htmlEscape="false" maxlength="1" class="input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">民族：</label>
			<div class="controls">
				<form:input path="mingzhu" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">政治面貌：</label>
			<div class="controls">
				<form:input path="political" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">身份证号：</label>
			<div class="controls">
				<form:input path="idcard" htmlEscape="false" maxlength="64" class="input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出生日期：</label>
			<div class="controls">
				<form:input path="birth" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原毕业学校：</label>
			<div class="controls">
				<form:input path="originaschool" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">原毕业中学班主任：</label>
			<div class="controls">
				<form:input path="originaperson" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">籍贯：</label>
			<div class="controls">
				<form:input path="jiguan" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍所在省：</label>
			<div class="controls">
				<form:input path="jiguansheng" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍所在市：</label>
			<div class="controls">
				<form:input path="jiguanshi" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍所在区县：</label>
			<div class="controls">
				<form:input path="jiguanxian" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍详细地址：</label>
			<div class="controls">
				<form:textarea path="jianguandetail" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现家庭所在省：</label>
			<div class="controls">
				<form:input path="nowjiguansheng" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现家庭所在市：</label>
			<div class="controls">
				<form:input path="nowjiguanshi" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现家庭所在区县：</label>
			<div class="controls">
				<form:input path="nowjiguanxian" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">现家庭详细地址：</label>
			<div class="controls">
				<form:textarea path="nowjianguandetail" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">户籍性质：</label>
			<div class="controls">
				<form:input path="hujixingzhi" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否有贫困证明或低保证：</label>
			<div class="controls">
				<form:input path="pingkunzm" htmlEscape="false" maxlength="1"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否享受国家助学金：</label>
			<div class="controls">
				<form:input path="shifouxszxj" htmlEscape="false" maxlength="1"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">助学金月发放标准（元）：</label>
			<div class="controls">
				<form:input path="zxjnum" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">学生类型：</label>
			<div class="controls">
				<form:input path="stutype" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">生源类别：</label>
			<div class="controls">
				<form:input path="shengyuantype" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">父亲姓名：</label>
			<div class="controls">
				<form:input path="fathername" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">父亲身份证号：</label>
			<div class="controls">
				<form:input path="fatheridcard" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">父亲文化程度：</label>
			<div class="controls">
				<form:input path="fatherculture" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">父亲工作单位：</label>
			<div class="controls">
				<form:input path="fatherworkunit" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">父亲电话：</label>
			<div class="controls">
				<form:input path="fatherphone" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">母亲姓名：</label>
			<div class="controls">
				<form:input path="mothername" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">母亲身份证号：</label>
			<div class="controls">
				<form:input path="motheridcard" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">母亲文化程度：</label>
			<div class="controls">
				<form:input path="motherculture" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">母亲工作单位：</label>
			<div class="controls">
				<form:input path="motherworkunit" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">母亲电话：</label>
			<div class="controls">
				<form:input path="motherphone" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">家庭人口：</label>
			<div class="controls">
				<form:input path="famliynum" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否办理助学贷款：</label>
			<div class="controls">
				<form:input path="isblzxdk" htmlEscape="false" maxlength="1" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">电话：</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="100" class="input-xlarge"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否参军及参军时间：</label>
			<div class="controls">
				<form:input path="ispartjun" htmlEscape="false" maxlength="1"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="sys:student:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>