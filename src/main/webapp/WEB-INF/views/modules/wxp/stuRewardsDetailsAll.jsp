<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>学生德育记录</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/mescroll.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/jquery-labelauty.js" type="text/javascript"></script>
	<link href="${ctxStatic}/wx/wxcss/jquery-labelauty.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/wx/wxcss/normalize.css" type="text/css" rel="stylesheet" />
	
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

    </style>
</head>
<body class="content">
	
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<input id="stuNo" type="hidden" value="${student.no}" />
	 <div class="topcont">
     	 ${student.name}德育记录
        <img src="../static/wx/wximages/backicon.png">
    </div>
    
    <div id="mescroll" class="mescroll">
	     <div class="stuRewardInfoCont">
	     	 <c:if test = "${srsNum > 0 }">
	     	 		<c:forEach items="${srs}" var="sr" varStatus="status">
	     	 			 <div class="stuRewardInfo">
				            <div class="rewardInfoLeft">
				                <div class="timePoint"></div>
				                <div class="timeLine"></div>
				            </div>
				            <div class="rewardInfoRight">
				                <div class="timeTxt"><fmt:formatDate value="${sr.updateDate}" type="date"/></div>
				                <div class="rewardReason">${sr.remarks}</div>
				            </div>
				        </div>
	     	 		</c:forEach>
	     	 </c:if>
	     	 <c:if test = "${srsNum <= 0 }">
	     	  	无奖惩记录
	     	 </c:if>
	     	 <div class="loading" style="display: none;">正在加载中...</div>
	     </div>
     </div>
   
	<script type="text/javascript">
	    $(function() {
	        $(".topcont img").click(function(){
	            history.back(-1);
	        })
	
	        var initFunc = function(){
	            var rightArrs = $(".rewardInfoRight");
	            for (var i = 0; i < rightArrs.length; i++) {
	                var rightH = rightArrs.eq(i).height();
	                rightArrs.eq(i).prev().children(".timeLine").css({
	                    "height" : (rightH - 20) + "px"
	                });
	            }
	        }
	        initFunc();

	        function addReward(data){
	            var $stuRewardInfo = $('<div class="stuRewardInfo"></div>');
	            var $rewardInfoLeft = $('<div class="rewardInfoLeft"></div>');
	            var $timePoint = $('<div class="timePoint"></div>');
	            var $timeLine = $('<div class="timeLine"></div>');
	            $rewardInfoLeft.append($timePoint);
	            $rewardInfoLeft.append($timeLine);

	            var $rewardInfoRight =  $('<div class="rewardInfoRight"></div>');
	            var $timeTxt = $('<div class="timeTxt">2018-10-01</div>');
	            var $rewardReason = $('<div class="rewardReason">厕所抽烟被抓</div>');
	            $rewardInfoRight.append($timeTxt);
	            $rewardInfoRight.append($rewardReason);

	            $stuRewardInfo.append($rewardInfoLeft);
	            $stuRewardInfo.append($rewardInfoRight);

	            $('.stuRewardInfoCont').append($stuRewardInfo);

	            initFunc();
	        }

	        //创建MeScroll对象
	        var mescroll = new MeScroll("mescroll", {
	            down: {
	                use: false
	            },
	            up: {
	                auto: false, //是否在初始化时以上拉加载的方式自动加载第一页数据; 默认false
	                isBounce: false, //此处禁止ios回弹,解析(务必认真阅读,特别是最后一点): http://www.mescroll.com/qa.html#q10
	                showLoading:function () { 
	                    $(".loading").show();
	                },
	                page:{
	                  pageNo : 0 , 
	                  pageSize : 1
	                },
	                callback: function(page){
	                	var pageContextVal = $("#PageContext").val();
	                    $.ajax({
	                        type: 'POST',
	                        url:pageContextVal+'/a/test/stuRewardsDetailsAll',
	                        data: page,
	                        success:function(data){
	                          
	                            addReward(data);
	                            $(".loading").hide();
	                            console.log("success");
	                            console.log(data);
	                            mescroll.endSuccess();
	                        },
	                        error:function(){
	                        	mescroll.endErr();
	                        }
	                        
	                    });

	                }
	            }
	        });
	
	
	    });
	</script>
</body>
</html>