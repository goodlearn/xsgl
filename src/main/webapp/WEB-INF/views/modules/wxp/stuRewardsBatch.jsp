<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>学生德育记录批量添加<</title>
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
        
        .selectAll{
            width: calc(96% - 2px);
            height: 40px;
            margin: 0 auto;
            background: #fff;
            border:1px solid #d8d8d8;
            border-radius: 8px;
            box-shadow: 0px 5px 5px #d1d1d1; 
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .selectAll .selectAllBtn{
            margin-left: 12px;
            padding-left: 25px;
            font-size: 14px;
        }
        .selectAll .selectBg{
            background: url(../static/wx/wximages/selecticon.png) no-repeat left center;
            background-size: 18px;
        }
        .selectAll .unselectBg{
            background: url(../static/wx/wximages/unselecticon.png) no-repeat left center;
            background-size: 18px;
        }
        .selectAll .selectStuNum{
            margin-right: 10px;
            font-size: 14px;
        }
        .selectAll .selectStuNum span{
            color: #2b795f;
            font-weight: bolder;
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
		 	 <c:if test = "${stuNum <= 0 }">
	    	  		无学生信息
	    	 </c:if>
	    	 <c:if test = "${stuNum > 0 }">
	    	 	 <form id="checkForm" method="post" action="${pageContext.request.contextPath}/wxsr/saveBatchSr">
	    	 	 	   <div class="checkCont">
					        <ul>
					        	<c:forEach items="${stuList}" var="stu" varStatus="status">
					        		   <li class="borderButtom">
							                <input type="checkbox"  id="check${status.count}" value="${stu.no}" name="bstuNo">
							                <label class="unselect" for="check${status.count}">${stu.name} ${stu.no}</label>
							            </li>
					        	</c:forEach>
					        </ul>
					    </div>
					    
					    <div class="selectAll">
					        <div class="selectAllBtn unselectBg">全选</div>
					        <div class="selectStuNum">已选<span>0</span>人</div>
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
					        <li><input type="radio" value="1" name="arType" data-labelauty="增加德育"></li>
					        <li><input type="radio" value="0" name="arType" data-labelauty="扣减德育"></li>
					    </ul>
					
					    <button class="submitBtn">确认提交</button>
	    	 	 </form>
	    	 </c:if>
</div>

<script type="text/javascript">
    $(function() {
        $(".topcont img").click(function(){
            history.back(-1);
        })
        
        $(':input[type=radio]').labelauty();
        
     // checkSlect
        var initCheck = function(){
            var checkArray = $(".checkCont ul li input[type=checkbox]:checked");
            //var unCheckArray = $(".checkCont ul li input[type=checkbox]:not(:checked)");
            // console.log("已选择："+checkArray.length + "未选择：" + unCheckArray.length);
            // for (var i = 0; i < checkArray.length; i++) {
            //     checkArray.eq(i).siblings('label').removeClass('unselect').addClass('select');
            // }
            // for (var i = 0; i < unCheckArray.length; i++) {
            //     unCheckArray.eq(i).siblings('label').removeClass('select').addClass('unselect');
            // }

            $(".selectStuNum span").text(checkArray.length);
        }

        $(".checkCont ul li input[type=checkbox]").change(function(){
            if ($(this).is(":checked")) {
                $(this).prop("checked",true);
                $(this).siblings('label').removeClass('unselect').addClass('select');
            } else {
                $(this).prop("checked",false);
                $(this).siblings('label').removeClass('select').addClass('unselect');
            }
            initCheck();
        });

        $(".selectAllBtn").click(function(){
            if ($(this).hasClass("unselectBg")) {
                $(this).removeClass("unselectBg");
                $(this).addClass("selectBg");
                $(".checkCont ul li input[type=checkbox]").prop("checked",true);
                $(".checkCont ul li label").removeClass('unselect').addClass('select');
            } else { 
                $(this).removeClass("selectBg");
                $(this).addClass("unselectBg");
                $(".checkCont ul li input[type=checkbox]").prop("checked",false);
                $(".checkCont ul li label").removeClass('select').addClass('unselect');
            }
            console.log("click");
            initCheck();
        })
    });
</script>
</body>
</html>