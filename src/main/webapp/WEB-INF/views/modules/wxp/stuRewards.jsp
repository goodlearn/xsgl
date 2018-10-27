<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>学生德育管理</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/swiper.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/wx/wxcss/swiper.min.css" type="text/css" rel="stylesheet" />
	
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
        .topcont .backimg{
            display: block;
            position: absolute;
            top: 10px;
            left: 10px;
            width: 40px;
            height: 40px;
        }
        .topcont .scoredetail{
            display: block;
            position: absolute;
            top: 13px;
            right: 10px;
            width: 34px;
            height: 34px;
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
            width: calc(96% - 60px - 70px - 10px);
            margin-left: 15px;
            height: 70px;
        }
        .stuinfo .stuinfoTxt{
            font-size: 14px;
            line-height: 30px;
            height: 28px;
            overflow: hidden;
            word-wrap: nowrap;
            text-overflow: ellipsis;
        }
        .stuinfo .stuFunc{
            height: 30px;
            margin-top: 10px;
        }
        .stuinfo .stuFunc ul li{
            float: left;
            line-height: 30px;
            background: #04ad84;
            border: 1px solid #f1f1f1;
            border-radius: 5px;
            padding: 0px 10px;
            margin-right: 8px;
            color: #fff;
            font-size: 12px;
        }


        .userScore{
            width: 70px;
            float: right;
            font-size: 26px;
            font-weight: bold;
            line-height: 70px;
            text-align: right;
            color: #4099eb;
        }
    </style>
</head>
<body class="content">
	
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<input id="classId" type="hidden" value="${classId}" />
	<div class="topcont">
       	 学生德育管理
        <img class="backimg" src="../static/wx/wximages/backicon.png">
        <img class="scoredetail" src="../static/wx/wximages/scoredetailicon.png" >
    </div>
    <div class="rewardscont">
    	  <c:if test = "${stuNum > 0 }">
	    	  	<c:forEach items="${stuList}" var="stu" varStatus="status">
	    	  			<div class="rewardinfo">
	    	  				 <div class="headimg">
	    	  				 	<c:if test = "${stu.headImgWx}">
	    	  				 		<img src="${stu.headImgWxUrl}">	 		
	    	  				 	</c:if>
	    	  				 	<c:if test = "${not stu.headImgWx}">
	    	  				 		 <img src="../static/wx/wximages/headimg.png">
	    	  				 	</c:if>
				             </div>
				            <div class="stuinfo">
				                <div class="stuinfoTxt">${stu.name} ${stu.no}</div>
				                <div class="stuFunc">
				                    <ul id="${stu.no}">
				                        <li class="stuFunc1">德育增减</li>
				                        <li class="stuFunc2">查看明细</li>
				                    </ul>
				                </div>
				            </div>
				            <div class="userScore">${stu.score}</div>
	    	  			</div>
				</c:forEach>
    	  </c:if>
    	  <c:if test = "${stuNum <= 0 }">
    	  		无学生信息
    	  </c:if>
    </div>
	<script type="text/javascript">
	    $(function() {
	    	$(".topcont .backimg").click(function(){
	            history.back(-1);
	        })

	         $(".topcont .scoredetail").click(function(){
	        	 var classId = $("#classId").val();
	        	 var pageContextVal = $("#PageContext").val();
	             window.location.href = pageContextVal+"/wxsr/stuRewardsClassRi?classId="+classId; 
	         })
	        
	        $(".stuFunc1").click(function(){
            	var id = $(this).parent()[0].id;
             	var pageContextVal = $("#PageContext").val();
            	window.location.href= pageContextVal+"/wxsr/stuRewardsAdd?stuNo="+id; 
        	})
	        $(".stuFunc2").click(function(){
	        	var id = $(this).parent()[0].id;
             	var pageContextVal = $("#PageContext").val();
            	window.location.href= pageContextVal+"/wxsr/stuRewardsDetails?stuNo="+id; 
	        })
	        
	    });
	</script>
</body>
</html>