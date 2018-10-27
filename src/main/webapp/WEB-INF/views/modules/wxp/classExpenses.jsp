<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>班费信息情况</title>
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
        .topcont{
            background-color: #ffffff;
            height: 60px;
            line-height: 60px;
            text-align: center;
            color: #545454;
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

        .totalExpensesCont{
        	width: 96%;
        	height: 200px;
        	margin: 20px auto 0px;
        	background-color: #8ebefe;
			background: linear-gradient(to right,#8ebefe,#87d0fd);
			border-radius: 5px;
			position: relative;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
        }
        .totalExpensesCont .className{
        	position: absolute;
        	left: 15px;
        	top: 10px;
        	font-size: 16px;
        	color: #ffffff;
        }
        .totalExpensesCont .expensesTxt{
        	font-size: 50px;
        	color: #fff;
        }
        .totalExpensesCont .expensesDesc{
        	font-size: 14px;
        	color: #fff;
        	margin-top: 5px;
        }

        .detailExpentsesCont{
			width: 96%; 
			margin: 20px auto 0px;
        }
        .detailExpentsesCont .expnone{
        	font-size: 14px;
        	color: #484848;
        	text-align: center;
        }
        .detailExpentsesCont ul li{
        	display: flex;
        	flex-direction: row;
        	justify-content: center;
        	align-items: center;
        	padding: 8px 0px;
        	border-bottom: 1px solid #f1f1f1;
        }
        .detailExpentsesCont ul li .expDate{
        	height: auto;
        	display: flex;
        	flex-direction: column;
        	justify-content: center;
        	align-items: center;
        	padding: 0 10px 0 5px;
        }
        .detailExpentsesCont ul li .expDate .expDateMD{
        	width: 50px;
        	height: 50px;
        	line-height: 50px;
        	background: #e5f1fd;
        	border-radius: 25px;
        	text-align: center;
        	font-size: 14px;
        	color: #90b2fa;
        }
        .detailExpentsesCont ul li .expDate .expDateY{
			margin-top: 3px;
			font-size: 14px;
			font-weight: bold;
			color: #90b2fa;
        }
        .detailExpentsesCont ul li .expTxt{
        	font-size: 14px;
        	display: flex;
        	flex-direction: row;
        	justify-content: left;
        	align-items: center;
        	width: calc(100% - 60px - 60px);
        }
        .detailExpentsesCont ul li .expTxt p{
        	margin: 0px;
        	padding: 0px;
        	color: #484848;
        }
        .detailExpentsesCont ul li .expNum{
        	display: flex; 
        	flex-direction: row; 
        	justify-content: center; 
        	align-items: center; 
			overflow:hidden;
        }
        .detailExpentsesCont ul li .expNum .expNumTxt{
        	width: 60px;
        	font-size: 16px;
        	text-align: right;
        	color: #2b795f;
        	overflow: hidden;
        	padding: 0px 5px;
        }

        .alertFrame{
        	width: 50%; 
        	height: 100px;
        	line-height: 100px;
        	text-align: center;
        	background-color: rgba(0,0,0,0.5);
        	position: absolute;
        	left: 25%;
        	top: 0;
        	border-radius: 10px;
        	color: #fff;
        	display: none;
        }
    </style>
</head>
<body>
	<div class="content">
			<div class="topcont">
			    班费信息情况
			    <img src="../static/wx/wximages/gobackicon.png">
			</div>
	
		<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
		<input id="pageNo" name="pageNo" type="hidden" value="${pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${pageSize}"/>
		<input id="totalCount" name="totalCount" type="hidden" value="${totalCount}"/>
		<input id="lastPage" name="lastPage" type="hidden" value="${lastPage}"/>
		<input id="classId" type="hidden" value="${clsinfo.id}" />
			 <c:if test = "${clsPriceNum > 0 }">
			 		<div class="detailExpentsesCont">
				 		<div class="totalExpensesCont">
							<p class="className">${clsinfo.name}</p>
							<p class="expensesTxt">${clsSum}</p>
							<p class="expensesDesc">班级消费总计</p>
						</div>
						
						<ul>
							<c:forEach items="${clsPrices}" var="clsp" varStatus="status">
								<li>
									<div class="expDate">
										<div class="expDateMD">${clsp.createDayString}</div> 
										<div class="expDateY">${clsp.createYearString}</div> 
									</div>
									<div class="expTxt"> <p>${clsp.remarks}</p> </div>
									<div class="expNum"> <div class="expNumTxt">${clsp.score}</div></div>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="alertFrame">
						内容正在加载中...
					</div>
			 </c:if>
			 
		 	 <c:if test = "${clsPriceNum <= 0 }">
		 			<div class="totalExpensesCont">
							<p class="className">${clsinfo.name}</p>
							<p class="expensesTxt">${clsSum}</p>
							<p class="expensesDesc">班级消费总计</p>
					</div>
		 	 		<div class="detailExpentsesCont">
						<div class="expnone">暂无消费记录</div>
					</div>
		   	 </c:if>
		
	</div>
	<script type="text/javascript">
	    $(function() {
	        $(".topcont img").click(function(){
	            history.back(-1);
	        }) 
	
	        function addExpLi(md,y,exptxt,expnum){
	        	var $li = $("<li></li>");
	        	var $expDate = $("<div class='expDate'><div class='expDateMD'>"+md+"</div><div class='expDateY'>"+y+"</div></div>");
	        	var $expTxt = $("<div class='expTxt'> <p>"+exptxt+"</p> </div>");
	        	var $expNum = $("<div class='expNum'> <div class='expNumTxt'>"+expnum+"</div></div>");
	
	        	$li.append($expDate);
	        	$li.append($expTxt);
	        	$li.append($expNum);
	
	        	$(".detailExpentsesCont ul").append($li);
	        }
	 
	        /*var tmpdata = {
	        	"classExpData" : [
	        		{"date":"2018-10-21","expTxt":"测试1测试测试测试测试测试测试测试测试测试测试测试测试测试","expNum":"110"},
	        		{"date":"2018-10-22","expTxt":"测试2测试测试测试测试测试测试测试测试测试测试测试测试测试","expNum":"110"},
	        		{"date":"2018-10-23","expTxt":"测试3测试测试测试测试测试测试测试测试测试测试测试测试测试","expNum":"110"},
	        		{"date":"2018-10-24","expTxt":"测试4测试测试测试测试测试测试测试测试测试测试测试测试测试","expNum":"110"},
	        		{"date":"2018-10-25","expTxt":"测试5测试测试测试测试测试测试测试测试测试测试测试测试测试","expNum":"110"},
	        		{"date":"2018-10-26","expTxt":"测试6测试测试测试测试测试测试测试测试测试测试测试测试测试","expNum":"110"}
	        	]
	        } */
	        var pageContextVal = $("#PageContext").val();
	    	var pageNo = $("#pageNo").val();
	    	console.log("pageN0 is "+pageNo);
        	var pageSize = $("#pageSize").val();
        	var totalCount = $("#totalCount").val();
        	var lastPage = $("#lastPage").val();
        	var classId = $("#classId").val();
	        $(window).scroll(function(){
				var scrollTop = $(this).scrollTop();
				var scrollHeight = $(document.body).height()
				var windowHeight = document.body.clientHeight;
				if(scrollHeight - (scrollTop + windowHeight)  < 50 && pageNo < lastPage){
					pageNo++;
					var alertTop = (windowHeight - 100)/2 + scrollTop;
					$(".alertFrame").css({
			        	"top" : alertTop+"px"
			        })
					$(".alertFrame").show();
			    /* 	console.log("pageN0 is "+pageNo);
	            	console.log("pageSize is "+pageSize);
	            	console.log("totalCount is "+totalCount);
	            	console.log("lastPage is "+lastPage); */
					$.ajax({
						url:pageContextVal+'/cex/pageMore',
	                    data: {"pageNo": pageNo, "pageSize": pageSize,"classId":classId},
						type: "POST",
						dataType: "json",
						async:false, 
						success: function(data){
							
							switch(data.code) {
								case "0" :
		                          	var obj = JSON.parse(data.message);
		                        
		                        	var dataTwo = JSON.parse(obj.data);
		                        
		                     /*    	console.log("-----------"+dataTwo.length);
		                        	console.log("********"+obj.totalCount);
		                        	console.log("-----------"+obj.data); */
		                        	$("#pageNo").val(obj.pageNo);
		                        	$("#pageSize").val(obj.pageSize);
		                        	//console.log("--返回后的pageSize------"+obj.pageSize)
		                        	$("#totalCount").val(obj.totalCount);
		                        	$("#lastPage").val(obj.lastPage);
		                        	for (var i = 0; i < dataTwo.length; i++) {
										addExpLi(dataTwo[i].createDayString,
												dataTwo[i].createYearString,
												dataTwo[i].remarks,
												dataTwo[i].score);
									}
		                        	$(".alertFrame").hide();
									break;
								case "1" : alert(data.message); break;
							}
						},
						 error: function(){
	
						}
					})
	
				}
			});
	    });
	</script>
</body>
</html>