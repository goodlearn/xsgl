<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>提交成功</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	 <style type="text/css">
        *{
                margin: 0px;
                padding: 0px;
                list-style-type: none;
        }
        body{
            background-color: #f8f8f8;
        }
        .content{
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        .sucessIcon{
            width: 128px;
        }
        .sucessTxt{
            margin-top: 8px;
            font-size: 30px;
        }
    </style>
</head>
<body>
	<div class="content">
	 <div class="sucessIcon">
	 		<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	 		<input id="redirect_url" type="hidden" value="${redirect_url}" />
        	<img src="${ctxStatic}/wx/wximages/submitSucessIcon.png" alt="">
	    </div>
	   <div class="sucessTxt">提交成功</div>
</div>
<script type="text/javascript">
	$(function() {
		var pageContextVal = $("#PageContext").val();
		var redirect_url = $("#redirect_url").val();
	    setTimeout(function() {
	    	window.location.href= pageContextVal+redirect_url;
	    }, 1000);
	});
</script>
</body>
</html>