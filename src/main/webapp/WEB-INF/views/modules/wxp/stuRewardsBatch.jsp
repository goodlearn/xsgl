<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>学生德育记录添加<</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link rel="stylesheet" type="text/css" href="${ctxStatic}/wx/wxcss/jquery-labelauty.css">
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/jquery-labelauty.js"></script>
	
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

        .checkCont{
            width: calc(96% - 2px);
            margin: 15px 2%;
            background: #fff;
            border:1px solid #d8d8d8;
            border-radius: 8px;
            box-shadow: 0px 5px 5px #d1d1d1; 
            overflow-y: scroll;
            max-height: 230px;
        }
        .checkCont ul li{
            display: block; 
            width: 96%; 
            padding: 0px 2%; 
            font-size: 14px; 
            font-weight: 100; 
            overflow: hidden; 
            border-bottom: 1px solid #f1f1f1; 
        }
        .checkCont ul li input[type=checkbox]{
            display: none;
        }
        .checkCont ul li label{
            display: block;
            float: left;
            padding: 15px 0px 15px 30px;
            width:calc(100% - 30px);
            overflow: hidden;
        }
        .checkCont ul li label.unselect{
            background: url(../static/wx/wximages/unselecticon.png) no-repeat left center;
            background-size: 20px;
        }
        .checkCont ul li label.select{
            background: url(../static/wx/wximages/selecticon.png) no-repeat left center;
            background-size: 20px;
        }
        .checkCont ul li input[type=text]{
            display: none;
        }
        .checkCont ul li p{
            width: calc(100% - 25px);
            padding: 20px 0px 20px 25px;
            font-size: 14px;
            font-weight: bolder;
            color: #333333;
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
<body>
	<div class="content">
 	<div class="topcont">
     	   学生奖惩记录添加
        <img src="../static/wx/wximages/backicon.png">
    </div>
	<div class="lazyboardCont">
	 	 <c:if test = "${stuNum <= 0 }">
    	  		无学生信息
    	 </c:if>
    	 <c:if test = "${stuNum > 0 }">
    	 	 <form id="checkForm" method="post" action="${pageContext.request.contextPath}/wxsr/saveBatchSr">
    	 	 	   <div class="checkCont">
				        <ul>
				        	<c:forEach items="${stuList}" var="stu" varStatus="status">
				        		   <li class="borderButtom">
						                <input type="checkbox"  value="${i.count}" name="bstuNo">
						                <label class="unselect" for="check11">${stu.name} ${stu.no}</label>
						            </li>
				        	</c:forEach>
				        </ul>
				        
				    </div>
				
				    <div class="rewardInfo">
				        <ul>
				            <li>
				                <p class="faultType">德育分值：</p> 
				                <input type="number" name="dyfz" placeholder="请输入德育分值...">
				            </li>
				        </ul>
				    </div>
				
				    <div class="addOtherCont">
				        <div class="checkOther">
				            <textarea name="reason" placeholder="请输入理由..."></textarea>
				        </div>
				    </div>
				
				    <ul class="dowebok">
				        <li><input type="radio" name="arType" data-labelauty="增加德育"></li>
				        <li><input type="radio" name="arType" data-labelauty="扣减德育"></li>
				    </ul>
				
				    <button class="submitBtn">确认提交</button>
    	 	 </form>
    	 </c:if>
		
	</div>
	
</div>

<script type="text/javascript">
    $(function() {
        $(".topcont img").click(function(){
            history.back(-1);
        })

        $(".checkCont ul li input[type=checkbox]").change(function(){
			console.log("checkbox change log -----" + $(this).index());
            if ($(this).is(":checked")) {
                $(this).siblings('label').removeClass('unselect').addClass('select');
                //$(this).siblings('label').css({'width':"calc(100% - 30px - 60px)"});
            } else {
                $(this).siblings('label').removeClass('select').addClass('unselect');
            }

        });
        
        $(':input[type=radio]').labelauty();
    });
</script>
</body>
</html>