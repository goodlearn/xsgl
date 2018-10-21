<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>学生信息管理系统</title>
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
            background-color: #fafafa；
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
        }

        .classinfo{
            margin: 10px 0px 0px 0px;
        }
        .swiper-container{
            width: 96%;
            margin: 0 auto 10px auto;
            height: 160px;
            border-radius: 8px;
            box-shadow: 3px 3px 3px #dddddd;
        }
        .classcont{
            width: 100%;
            background: linear-gradient(to right,#699c99,#7bc6c2);
            height: 160px;
            line-height: 160px;
            text-align: center;
            color: #fff;
            font-weight: bold;
        }
        .userFunc{
            width: 100%;
            height: 160px;
            overflow: hidden;
            background: linear-gradient(to left,#699c99,#7bc6c2);
            color: #fff;
        }
        .userFunc ul{
            overflow: hidden;
            border-bottom: 1px solid #f1f1f1;
        }
        .userFunc ul li{
            width: calc((100% - 3px) / 4); 
            float: left;
            padding: 5px 0px;
        }
        .userFunc ul li.leftborder{
            border-right: 1px solid #f1f1f1;
        }
        .userFunc ul li .funcIcon{
            width: 100%;
            height: 40px;
        }
        .userFunc ul li:nth-child(1) .funcIcon{
            background: url(../static/wx/wximages/checkicon.png) no-repeat center center;
            background-size: 24px;
        }
        .userFunc ul li:nth-child(2) .funcIcon{
            background: url(../static/wx/wximages/stuicon.png) no-repeat center center;
            background-size: 24px;
        }
        .userFunc ul li:nth-child(3) .funcIcon{
            background: url(../static/wx/wximages/qrcodeicon.png) no-repeat center center;
            background-size: 24px;
        }
        .userFunc ul li:nth-child(4) .funcIcon{
            background: url(../static/wx/wximages/noticeicon.png) no-repeat center center;
            background-size: 24px;
        }
        .userFunc ul li .funcTxt{
            text-align: center;
            font-size: 12px;
            padding: 5px 0px;
        }
    </style>
</head>
<body class="content">
	
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<div class="topcont">
                     学生信息管理系统
    </div>
    
    <div class="classinfo">
    	  <c:if test = "${clsNum > 0 }">
	    	  	<c:forEach items="${clsList}" var="clsinfo" varStatus="status">
					     <div class="swiper-container">
				    	  	  <div class="swiper-wrapper">
				    	  	  	 <div class="swiper-slide">
				                    <div class="classcont">
				                    	    ${clsinfo.name}
				                    </div>
				                 </div>
			                     <div class="swiper-slide">
				                    <div class="userFunc">
				                        <ul>  
				                        	<c:forEach items="${navigaionList}" var="navigation" varStatus="status">
				                        			<c:if test = "${status.count < 4 }">
			                        				    <li class="leftborder rewardid" id="${clsinfo.id}">
					                                		<p class="funcIcon"></p>
					                               		 	<p class="funcTxt">${navigation}</p>
					                            		</li>
				                        			</c:if>
				                        			<c:if test = "${status.count >= 4 }">
				                        				 <li class="rewardid">
					                                		<p class="funcIcon"></p>
					                               		 	<p class="funcTxt">${navigation}</p>
					                            		</li>
				                        			</c:if>
				                        	</c:forEach>
				                        </ul>
				                    </div>
			                	</div>
				    	  	  </div>
				    	  </div>
				</c:forEach>
    	  </c:if>
    	  <c:if test = "${clsNum <= 0 }">
    	  	无班级信息
    	  </c:if>
    </div>

<script type="text/javascript">
    $(function() {
        var mySwiper = new Swiper ('.swiper-container', {
            direction: 'horizontal', // 垂直切换选项
            loop: true, // 循环模式选项
            
            // 如果需要滚动条
            scrollbar: {
              el: '.swiper-scrollbar',
            },
          })

        $(".rewardid").click(function(){
	       	 var pageContextVal = $("#PageContext").val();
	       	 var classId = this.id;
	       	 var curnum = $(this).index();
	       	 switch(curnum) {
		       	 case 0: window.location.href= pageContextVal+"/wxsr/indexInfo?classId="+classId; break;
		       	 case 1: window.location.href= pageContextVal+"/wxsr/obatinBatchSr?classId="+classId; break;
		       	 case 2: window.location.href= pageContextVal+"/cex/indexInfo?classId="+classId; break;
		       	 case 3: alert("敬请期待！");break;
	       	 }
	         
        })
    });
</script>
</body>
</html>