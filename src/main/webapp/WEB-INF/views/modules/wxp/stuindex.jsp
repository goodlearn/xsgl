<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>学生信息管理系统</title>
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
            background-color: #ffffff;
        }
        .content{
            max-width: 650px;
            margin: 0 auto;
            font-family: 'Microsoft YaHei';
            position: relative;
        }
        .SloganTxt{
            text-align: center;
            font-size: 16px;
            margin-top: 100px;
            color: #c6d4dd;
        }

        .navCont{
            width: 100%;
            position: fixed;
            left: 0;
            bottom: 100px;
        }
        .navCont ul{
            width: 100%;
            overflow: hidden;
        }
        .navCont ul li{
            width: 25%;
            float: left;
            padding: 10px 0px;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
        }
        .navCont ul li .navIcon{
            width: 60%;
            max-width: 100px;
            border-radius: 60%;
        }
        .navCont ul li:nth-child(1) .navIcon{
            background: url(../static/wx/wximages/rankicon.png) #609ffe no-repeat center center;
            background-size: 60% 60%;
        }
        .navCont ul li:nth-child(2) .navIcon{
            background: url(../static/wx/wximages/rewards.png) #1bd5d2 no-repeat center center;
            background-size: 60% 60%;
        }
        .navCont ul li:nth-child(3) .navIcon{
            background: url(../static/wx/wximages/scoredetailicon.png) #9b84f8 no-repeat center center;
            background-size: 60% 60%;
        }
        .navCont ul li:nth-child(4) .navIcon{
            background: url(../static/wx/wximages/classexpicon.png) #ff974e no-repeat center center;
            background-size: 60% 60%;
        }
        .navCont ul li:nth-child(5) .navIcon{
            background: url(../static/wx/wximages/stucheckicon.png) #fd695d no-repeat center center;
            background-size: 60% 60%;
        }
        .navCont ul li .navTxt{
            font-size: 14px;
            color: #585858;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<div class="content">
    <div class="SloganTxt">- 为了让你成为更好的自己 -</div>
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<input id="stuNo" type="hidden" value="${student.no}" />
   <input id="clssinfoId" type="hidden" value="${clssinfoId}" />
    <div class="navCont">
        <!-- 德育排行，德育总分，德育明细，班费明细，早操考勤 -->
        <ul>
            <li>
                <div class="navIcon"><img src="../static/wx/wximages/null.png" width="100%" alt=""></div>
                <div class="navTxt">德育排行</div>
            </li>
            <li>
                <div class="navIcon"><img src="../static/wx/wximages/null.png" width="100%" alt=""></div>
                <div class="navTxt">德育总分</div>
            </li>
            <li>
                <div class="navIcon"><img src="../static/wx/wximages/null.png" width="100%" alt=""></div>
                <div class="navTxt">德育明细</div>
            </li>
            <li>
                <div class="navIcon"><img src="../static/wx/wximages/null.png" width="100%" alt=""></div>
                <div class="navTxt">班费明细</div>
            </li>
            <li>
                <div class="navIcon"><img src="../static/wx/wximages/null.png" width="100%" alt=""></div>
                <div class="navTxt">早操考勤</div>
            </li>
        </ul>
    </div>
</div>


<script type="text/javascript">
    $(function() {
        $(".navCont ul li").click(function(){
        	var pageContextVal = $("#PageContext").val();
        	var clssinfoId = $("#clssinfoId").val();
        	var stuNo = $("#stuNo").val();
            var index = $(this).index();
            switch(index){
           
                case 0 : window.location.href= pageContextVal+"/wxsr/rewardBoard?classId="+clssinfoId; break;
                case 1 : window.location.href= pageContextVal+"/wi/stuIndexInfo"; break;
                case 2 : window.location.href= pageContextVal+"/wxsr/stuRewardsDetailsAll?stuNo="+stuNo; break;
                case 3 : window.location.href= pageContextVal+"/cex/indexInfo?classId="+clssinfoId; break;
                case 4 : alert("敬请期待！"); break;
                case 5 : 
            }
        })
    });
</script>

</body>
</html>