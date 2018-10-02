<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>班级德育成绩榜<</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<style type="text/css">
		*{
			margin: 0px;
			padding: 0px;
		}
		.lazyboardCont{
			width: 100%;
			max-width: 650px;
			margin: 20px auto 0px;
			overflow: hidden;
		}
		.lazyboardCont .lazyboardHead{
			width: 100%;
			background-image: linear-gradient(to top, #424890, #f4f4f4);
			box-shadow: 0px 3px 5px #666666;
		}
		 .lazyboardHead .lazyboardHeadTitle{
		 	text-align: center;
		 	font-size: 16px;
		 	line-height: 30px;
		 	color: #fff;
		 	font-weight: bold;
		 	background: url(../static/wx/wximages/sidaibg.png) no-repeat center center;
		 	background-size: cover;
		 }
		 .lazyboardCont .lazyboardHead .lazyboardRank{
			width: 100%;
			margin-top: 10px;
			padding-bottom: 10px;
			overflow: hidden;
		 }
		 .lazyboardCont .lazyboardHead .lazyboardRank .ranknumDiv{
		 	width: 32%;
		 	margin-left: 1%;
		 	padding: 10px 0px;
		 	float: left;
		 	position: relative;
		 }
		 .lazyboardHead .lazyboardRank .ranknumDiv .ranknum2{
		 	width: 40%;
		 	margin: calc(50% - 40%) auto;
		 	border-radius: calc(50%);
		 	border: 2px solid #72d664;
		 	overflow: hidden;
		 	box-shadow: 0px 0px 3px #72d664;
		 }
		 .lazyboardHead .lazyboardRank .ranknumDiv .ranknum1{
		 	width: 50%;
		 	margin: 0 auto calc(50% - 40%);
		 	border-radius: calc(50%);
		 	border: 2px solid #f8ca03;
		 	box-shadow: 0px 0px 3px #f8ca03;
		 	overflow: hidden;
		 }
		 .lazyboardHead .lazyboardRank .ranknumDiv .ranknum3{
		 	width: 40%;
		 	margin: calc(50% - 40%) auto;
		 	border-radius: calc(50%);
		 	border: 2px solid #b66ff3;
		 	box-shadow: 0px 0px 3px #b66ff3;
		 	overflow: hidden;
		 }
		 .lazyboardHead .lazyboardRank .ranknumDiv .rankNum2Icon{
		 	position: absolute;
		 	bottom: 30px;
		 	left: calc(50% - 15px);
		 	width: 30px;
		 	height: 30px;
		 	background: url(../static/wx/wximages/ranknum2.png) no-repeat center center;
		 	background-size: 100%;
		 }
		 .lazyboardHead .lazyboardRank .ranknumDiv .rankNum1Icon{
		 	position: absolute;
		 	bottom: 30px;
		 	left: calc(50% - 17px);
		 	width: 34px;
		 	height: 34px;
		 	background: url(../static/wx/wximages/ranknum1.png) no-repeat center center;
		 	background-size: 100%;
		 }
		 .lazyboardHead .lazyboardRank .ranknumDiv .rankNum3Icon{
		 	position: absolute;
		 	bottom: 30px;
		 	left: calc(50% - 15px);
		 	width: 30px;
		 	height: 30px;
		 	background: url(../static/wx/wximages/ranknum3.png) no-repeat center center;
		 	background-size: 100%;
		 }
		 .lazyboardHead .lazyboardRank .ranknumDiv .rankNumName{
		 	font-size: 12px;
		 	width: 90%;
		 	margin: 15px auto 0px;
		 	line-height: 20px;
		 	color: #fff;
		 	text-align: center;
		 	overflow: hidden;
			white-space: nowrap;
			text-overflow: ellipsis;
		 }

		 .boardRankScroll{
		 	width: 100%;
		 	margin: 20px 0px;
		 	overflow: hidden;
		 }
		 .lazyboardCont .lazyboardRankCont{
		 	width: 100%;
		 	float: left;
		 }
		 .lazyboardCont .lazyboardRankCont .lazyboardRankDiv{
		 	width: 96%;
		 	height: 52px;
		 	padding: 10px 2%;
		 	margin-top: 5px;
		 	overflow: hidden;
		 	background: #fff;
		 	border-top: #f4f4f4;
		 	border-bottom: #f4f4f4;
		 	box-shadow: 0px 0px 2px #e1e1e1;
		 }
		 .lazyboardRankCont .lazyboardRankDiv .lazyboardRankNumTxt{
		 	width: 35px;
		 	padding-right:15px;
		 	line-height: 52px;
		 	text-align: center;
		 	font-size: 16px;
		 	font-weight: bolder;
		 	font-style: italic;
		 	float: left;
		 	color: #333333;
		 }
		 .lazyboardRankCont .lazyboardRankDiv .lazyboardRankIcon{
			width: 50px;
			height: 50px;
			float: left;
			border: 2px solid #4099eb;
			border-radius: 25px;
			overflow: hidden;
		 }
		 .lazyboardRankCont .lazyboardRankDiv .lazyboardRankNameTxt{
		 	width: calc(100% - 50px - 52px - 85px - 10px - 25px);
		 	color: #333333;
		 	padding-left: 10px;
		 	line-height: 52px;
		 	font-size: 14px;
		 	float: left;
		 	overflow: hidden;
		 	white-space: nowrap;
		 	text-overflow: ellipsis;
		 }
		 .lazyboardRankCont .lazyboardRankDiv .lazyboardRankScore{
		 	width: 95px;
		 	padding-right: 10px;
		 	line-height: 52px;
		 	font-size: 20px;
		 	font-weight: bolder;
		 	float: right;
		 	color: #4099eb;
		 	text-align: right;
		 }

		 .tab2,.tab3{
		 	
		 }
	</style>
</head>
<body>
	<div class="content">

	<div class="lazyboardCont">
	 	 <c:if test = "${stuNum <= 0 }">
    	  		无学生信息
    	 </c:if>
    	 <c:if test = "${stuNum > 0 }">
    	  		<div class="lazyboardHead">
					<div class="lazyboardHeadTitle">${classinfo.name}德育榜</div>
					
						<div class="lazyboardRank">
							<div class="ranknumDiv">
								<div class="ranknum2">
									<img src="${two.sysWxInfo.headimgurl}" width="100%">
								</div>
								<div class="rankNum2Icon"></div>
								<div class="rankNumName">${two.name}</div>
							</div>
							<div class="ranknumDiv">
								<div class="ranknum1">
									<img src="${one.sysWxInfo.headimgurl}" width="100%">
								</div>
								<div class="rankNum1Icon"></div>
								<div class="rankNumName">${one.name}</div>
							</div>
							<div class="ranknumDiv">
								<div class="ranknum3">
									<img src="${three.sysWxInfo.headimgurl}" width="100%">
								</div>
								<div class="rankNum3Icon"></div>
								<div class="rankNumName">${three.name}</div>
							</div>
						</div>
					</div>

			<div class="boardRankScroll">
				<div class="lazyboardRankCont">
					<c:forEach items="${stuList}" var="stu" varStatus="status">
							<div class="lazyboardRankDiv">
								<div class="lazyboardRankNumTxt">${i.count}</div>
								<div class="lazyboardRankIcon">
									<img src="${stu.sysWxInfo.headimgurl}" width="100%">
								</div>
								<div class="lazyboardRankNameTxt">${stu.name}</div>
								<div class="lazyboardRankScore">${stu.score}</div>
							</div>
					</c:forEach>
				</div>
			</div>
    	 </c:if>
		
	</div>
	
</div>

<script type="text/javascript">
	$(function() {
		
	});
</script>
</body>
</html>