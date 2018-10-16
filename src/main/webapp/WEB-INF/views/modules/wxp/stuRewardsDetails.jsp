<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>学生德育记录</title>
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
            background-color: #0fddd1;
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

        .rewardScoreCont{
            width: 100%;
            background: #0fddd1;
            position: relative;
        }
        .rewardScoreCont img{
            display: block;
            width: 80%;
            margin: 0 auto;
        }
        .rewardScoreCont p{
            text-align: center;
            font-size: 60px;
            color: #fff;
            position: absolute;
            bottom: 20px;
            left: calc((100% - 200px) / 2);
            width: 200px;
            text-align: center;
        }

        .rewardFuncCont{
            background: #0fddd1;
            padding: 20px 0px 20px;
            overflow: hidden;
        }
        .rewardFuncCont .rewardFunc{
            width: 47%;
            background: rgba(255,255,255,0.3);
            float: left;
            margin-left: 2%;
            text-align: center;
            padding: 10px 0px;
            border-radius: 5px;
            color: #fff;
        }

        .rewardinfo{
            overflow: hidden;
            background: #fff;
            margin: 10px 0px 0px 0px;
            padding: 8px 2%;
        }
        .rewardinfo .headimg{
            width: 60px;
            height: 60px;
            border-radius: 30px;
            float: left;
            overflow: hidden;
            margin: 5px 0px;
        }
        .rewardinfo .headimg img{
            width: 100%;
        }
        .stuinfo{
            float: left;
            width: calc(96% - 60px - 80px - 20px);
            margin-left: 15px;
            height: 70px;
        }
        .stuinfo .stuinfoTxt{
            line-height: 30px;
            color: #333333;
        }

        .title{
            margin-top: 20px;
            text-align: center;
            font-size: 14px;
            color: #666666;
        }

        .stuRewardInfoCont{
            background: #fff;
            padding: 8px 0px;
            margin-top: 20px;
        }
        .stuRewardInfoCont .stuRewardInfo{
            width: 96%; 
            margin: 0px auto;
            overflow: hidden;
        }
        .stuRewardInfo .rewardInfoLeft{
            width: 20px;
            float: left;
        }
        .stuRewardInfo .rewardInfoLeft .timePoint{
            width: 20px;
            height: 20px;
            font-size: 20px;
            text-align: center;
            background: url(../static/wx/wximages/circle.png) no-repeat center center;
            background-size: cover;
        }
        .stuRewardInfo .rewardInfoLeft .timeLine{
            width: 1px;
            height:20px;
            border-left: 1px solid #1296db;
            margin: 0 auto;
        }
        .stuRewardInfo .rewardInfoRight{
            width: calc(100% - 20px - 20px - 5px);
            float: left;
            margin: 0px 0px 0px 20px;
            position: relative;
        }
        .scoreTxt{
            position: absolute;
            top: 5px;
            right: 10px;
            font-size: 14px;
            color: #f49a00;
        }
        .rewardInfoRight .timeTxt{
            font-size: 12px;
            color: #666666;
            padding-top: 5px;
        }
        .rewardInfoRight .rewardReason{
            margin-top: 5px;
            background: #f1f1f1;
            border-radius: 5px;
            font-size: 14px;
            padding: 8px;
            margin-bottom: 10px;
        }

        .footer{
            text-align: center;
            font-size:14px;
            color: #666666;
            line-height: 60px;
        }

    </style>
</head>
<body class="content">
	
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<input id="stuNo" type="hidden" value="${student.no}" />
	 <div class="topcont">
     	  学生德育记录
        <img src="../static/wx/wximages/backicon.png">
    </div>
    <div class="rewardScoreCont">
        <img src="../static/wx/wximages/rewardinfobg.png" alt="">
        <p>${student.score}</p>
    </div>
     <div class="rewardFuncCont">
        <div class="rewardFunc" id="${student.no}">德育记录</div>
        <div class="rewardFunc" id="${student.classId}">班级排行</div>
    </div>
     <div class="rewardinfo">
        <div class="headimg">
       	 	<c:if test = "${student.headImgWx}">
				  <img src="${student.headImgWxUrl}">	 		
			 </c:if>
			 <c:if test = "${not student.headImgWx}">
			 		 <img src="../static/wx/wximages/headimg.png">
			 </c:if>
        </div>
        <div class="stuinfo">
            <div class="stuinfoTxt">${student.name}</div>
            <div class="stuinfoTxt">${student.no}</div>
        </div>
    </div>
     <div class="title">- 最新动态 - </div>
     <div class="stuRewardInfoCont">
     	 <c:if test = "${srsNum > 0 }">
     	 		<c:forEach items="${srs}" var="sr" varStatus="status">
     	 			 <div class="stuRewardInfo">
			            <div class="rewardInfoLeft">
			                <div class="timePoint"></div>
			                <div class="timeLine"></div>
			            </div>
			            <div class="rewardInfoRight">
			            <div class="scoreTxt">德育分${fns:getDictLabel(sr.scoreType, 'scoreType', '')}${sr.score}</div>
			                <div class="timeTxt"><fmt:formatDate value="${sr.updateDate}" type="date"/></div>
			                <div class="rewardReason">
			               		 ${sr.remarks}
			                </div>
			            </div>
			        </div>
     	 		</c:forEach>
     	 </c:if>
     	 <c:if test = "${srsNum <= 0 }">
     	  	初始德育分值100分
     	 </c:if>
     </div>
   
	<script type="text/javascript">
	    $(function() {
	        $(".topcont img").click(function(){
	            history.back(-1);
	        })

	        var rightArrs = $(".rewardInfoRight");
	        for (var i = 0; i < rightArrs.length; i++) {
	            var rightH = rightArrs.eq(i).height();
	            rightArrs.eq(i).prev().children(".timeLine").css({
	                "height" : (rightH - 20) + "px"
	            });
	        }

	        $(".rewardFunc").eq(0).click(function(){
	        	var stuNo = this.id;
	        	var pageContextVal = $("#PageContext").val();
            	window.location.href= pageContextVal+"/wxsr/stuRewardsDetailsAll?stuNo="+stuNo; 
	        })
	        $(".rewardFunc").eq(1).click(function(){
	         	var classId = this.id;
        		var pageContextVal = $("#PageContext").val();
        		window.location.href= pageContextVal+"/wxsr/rewardBoard?classId="+classId; 
	        })


	    });
	</script>
</body>
</html>