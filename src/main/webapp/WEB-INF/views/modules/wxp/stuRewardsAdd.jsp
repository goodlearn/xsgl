<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>学生奖惩管理</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/jquery-labelauty.js" type="text/javascript"></script>
	<link href="${ctxStatic}/wx/wxcss/jquery-labelauty.css" type="text/css" rel="stylesheet" />
	
	  <style type="text/css">
           *{
                margin: 0px;
                padding: 0px;
                list-style-type: none;
        }
        body{
            background-color: #f1f1f1;
        }
        .content{
            max-width: 650px;
            margin: 0 auto;
            font-family: 'Microsoft YaHei';
        }
        .topcont{
            background-color: #04ad84;
            height: 60px;
            line-height: 60px;
            text-align: center;
            color: #fff;
            position: relative;
        }
        .topcont img{
            display: block;
            position: absolute;
            top: 10px;
            left: 10px;
            width: 40px;
            height: 40px;
        }

        .rewardInfo{
            width: calc(96% - 2px);
            margin: 15px 2%;
            background: #fff;
            border:1px solid #d8d8d8;
            border-radius: 8px;
            box-shadow: 0px 5px 5px #d1d1d1; 
            overflow: hidden;
        }
        .rewardInfo ul li{
            display: block;
            width: 100%;
            overflow: hidden;
        }
        .rewardInfo ul li p{
            padding-top: 15px;
            padding-bottom: 15px;
            padding-left: 35px;
            font-size: 14px;
            font-weight: bolder;
        }
        .rewardInfo ul li p.stuname{
            background: url(../static/wx/wximages/studenticon.png) no-repeat 2% center;
            background-size: 20px;
        }
        .rewardInfo ul li p.stuId{
            background: url(../static/wx/wximages/stuidicon.png) no-repeat 2% center;
            background-size: 20px;
        }
        .rewardInfo ul li .checkPerson{
            float: left;
            margin-left: 2%;
            padding-left: 25px;
        }
        .rewardInfo ul li select{
            display: block;
            float: left;
            width: calc(100% - 2% - 35px - 80px);
            padding: 15px 5px;
            border:none;
            outline: none;
            background: #fff;
        }
        .rewardInfo ul li .faultType{
            float: left;
            margin-left: 2%;
            padding-left: 5px;
            width: 80px;
        }
        .rewardInfo ul li input[type=number]{
            display: block;
            float: left;
            width: calc(100% - 2% - 15px - 80px);
            padding: 17px 5px;
            border:none;
            outline: none;
        }
        .addOtherCont{
            width: 100%;
        }
        .addOtherCont .checkOther{
            width: calc(96% - 2px);
            margin: 0 auto 15px;
            background: #fff;
            border:1px solid #d8d8d8;
            border-radius: 8px;
            box-shadow: 0px 5px 5px #d1d1d1; 
            overflow: hidden;
        }
        .addOtherCont .checkOther textarea{
            width: 96%;
            padding: 10px 2%;
            height: 150px;
            font-size: 14px;
            font-weight: 100;
            outline: none;
            border: none;
            resize: none;
        }
        .addOtherCont .checkOther .removeContBtn{
            font-size: 14px;
            font-weight: bolder;
            color: #d81e06;
            text-align: center;
            line-height: 35px;
            border-top: 1px solid #f2f2f2;
        }

        ul { list-style-type: none;}
        li { display: inline-block;}
        li { margin: 10px 0;}
        input.labelauty + label { font: 12px "Microsoft Yahei";}
        .dowebok{
            padding-left: 2%;
        }

        .submitBtn{
            display: block;
            width: calc(96% - 2px);
            margin: 10px auto 15px;
            background: #1f72ff;
            border:1px solid #d8d8d8;
            border-radius: 8px;
            box-shadow: 0px 5px 5px #d1d1d1; 
            overflow: hidden;
            line-height: 50px;
            color: #fff;
            font-size: 14px;
            font-weight: bolder;
            outline: none;
        }
    </style>
</head>
<body class="content">
	
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<input id="stuNo" type="hidden" value="${student.no}" />
	 <div class="topcont">
       	 学生奖惩记录添加
        <img src="../static/wx/wximages/backicon.png">
    </div>
    <div class="rewardInfo">
        <ul>
            <li class="borderButtom"><p class="stuname">学生姓名：${student.name}</p></li>
            <li><p class="stuId">学生学号：${student.no}</p></li>
        </ul>
    </div>

	    <div class="rewardInfo">
	        <ul>
	            <li>
	                <p class="faultType">德育分值：</p> 
	                <input id="dyfz" type="number" placeholder="请输入德育分值...">
	            </li>
	        </ul>
	    </div>
	
	    <div class="addOtherCont">
	        <div class="checkOther">
	            <textarea id="reason" placeholder="请输入理由..."></textarea>
	        </div>
	    </div>
	
	    <ul class="dowebok">
	        <li><input type="radio" name="radio" value="1" data-labelauty="增加德育"></li>
	        <li><input type="radio" name="radio" value="0" data-labelauty="扣减德育"></li>
	    </ul>
	
	    <button class="submitBtn">确认提交</button>
	<script type="text/javascript">
		 $(function() {
	        $(".topcont img").click(function(){
	            history.back(-1);
	        })

	        $(':input[type=radio]').labelauty();
	        
	        
	        $(".submitBtn").click(function(){
	        	var pageContextVal = $("#PageContext").val();
	        	var stuNo = $("#stuNo").val();
	        	var dyfz = $.trim($("#dyfz").val());
				if (dyfz.length == '' || dyfz == null) {
					alert("请输入德育分值");
					return false;
				}
				
				var reason = $.trim($("#reason").val());
				if (reason.length == '' || reason == null) {
					alert("请输入理由");
					return false;
				}
				
				var arType = $.trim($("input[type=radio][name=radio]:checked").val());
				if (arType.length == '' || arType == null) {
					alert("请选择类型");
					return false;
				}
				
	        	 $.ajax({
				     type:'POST',
				     url:pageContextVal+'/wxsr/saveStuReward',
				     data:{'arType':arType,'reason':reason,'dyfz':dyfz,'stuNo':stuNo},
				     dataType: "json",
				     async: false,    // 使用同步操作
			         timeout : 50000, //超时时间：50秒
				     success:function(data){
				    	 switch(data.code) {
							case "0" :
								alert("添加成功!");
								 var classId = data.message;
						         window.location.href= pageContextVal+"/wxsr/indexInfo?classId="+classId; 
								break;
							case "1" : alert(data.message); break;
						}
				     },
				     error:function(XMLHttpRequest, textStatus, errorThrown){
				    	 alert(XMLHttpRequest.status+"操作失败"+XMLHttpRequest.readyState+"未知错误"+textStatus);
				     }
				    
				 });
	        });
	    });
	</script>
</body>
</html>