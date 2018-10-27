<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>学生德育记录</title>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<link href="${ctxStatic}/wx/wxcss/swiper.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/wx/wxjs/jquery.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/wx/wxjs/swiper.min.js" type="text/javascript"></script>
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

        .rewardsInfoCont{

        }
        .rewardsInfoCont ul li{
            background-color: #fff;
            border-bottom: 1px solid #f1f1f1;
        }

        .swiper-container {
            width: 100%;
        }
        .swiper-slide {
            text-align: center;
            font-size: 18px;
            background: #fff; 
        }
        .swiper-container .menu {
            width: 100px;
            background-color: #ff3a31;
            color: #fff;
            text-align: center;
            font-size:14px;
            display: flex;
            
            justify-content: center;
            align-items: center;
            height: 100%;
        }
        .swiper-container .slidecont {
            width: 100%;
            display: flex;
            justify-content: flex-start;
            padding: 8px 0px;
        }


        .slidecont .rewDate{
            height: auto;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 0 10px 0 8px;
        }
        .slidecont .rewDate .rewDateMD{
            width: 50px;
            height: 50px;
            line-height: 50px;
            background: #e5f1fd;
            border-radius: 25px;
            text-align: center;
            font-size: 14px;
            color: #90b2fa;
        }
        .slidecont  .rewDate .rewDateY{
            margin-top: 3px;
            font-size: 14px;
            font-weight: bold;
            color: #90b2fa;
        }
		
        .slidecont .rightCont{
            width: calc(100% - 80px);
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: flex-start;
            position: relative;
        }
        .slidecont .rightCont .stuName{
            font-size: 14px;
            text-align: left;
            margin-bottom: 5px;
        }
        .slidecont .rewTxt{
            width: calc(100% - 16px);
            font-size: 12px;
            background-color: #f1f1f1;
            padding: 8px;
            border-radius: 5px;
        }
        .slidecont .rewTxt p{
            margin: 0px;
            padding: 0px;
            color: #484848;
            text-align: left;
        }
        .rightCont .rewNumTxt{
            position: absolute;
            top: 5px;
            right: 0px;
            font-size: 14px;
            color: #2b795f;
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
            z-index: 999;
            display: none;
        }
    </style>
</head>
<body  class="content">
	<input id="PageContext" type="hidden" value="${pageContext.request.contextPath}" />
	<input id="pageNo" name="pageNo" type="hidden" value="${pageNo}"/>
	<input id="pageSize" name="pageSize" type="hidden" value="${pageSize}"/>
	<input id="totalCount" name="totalCount" type="hidden" value="${totalCount}"/>
	<input id="lastPage" name="lastPage" type="hidden" value="${lastPage}"/>
	<input id="classId" type="hidden" value="${classinfo.id}" />
	<div class="topcont">
      	 ${classinfo.name}
        <img class="backimg" src="../static/wx/wximages/backicon.png">
    </div>
 	<c:if test = "${countSr <= 0 }">
 		暂无数据
 	</c:if>
 	<c:if test = "${countSr > 0 }">
		<div class="rewardsInfoCont">
			<ul>
				<c:forEach items="${rsList}" var="rs" varStatus="status">
					 <li>
		                <div class="swiper-container">
		                    <div class="swiper-wrapper">
		                        <div class="swiper-slide slidecont">
		                            <div class="rewDate">
		                                <div class="rewDateMD">${rs.createDayString}</div> 
		                                <div class="rewDateY">${rs.createYearString}</div> 
		                            </div>
		                            
		                            <div class="rightCont">
		                                <div class="stuName">${rs.name}</div>
		                                <div class="rewTxt"> <p>${rs.remarks}</p> </div>
		                                
		                                <c:if test = "${rs.scoreType == 0}">
		                                	<div class="rewNumTxt">-${rs.score}</div>
			                            </c:if>
			                            <c:if test = "${rs.scoreType == 1 }">
			                            	  <div class="rewNumTxt">+${rs.score}</div>
			                            </c:if>
		                            </div>
		                        </div>
		                        <div title="${rs.id}" class="swiper-slide menu">删除</div>
		                    </div>
		                </div>
		            </li>
		         </c:forEach>
			</ul>
		</div>
		<div class="alertFrame">
	       	内容正在加载中...
	    </div>
	</c:if>
<script type="text/javascript">
    $(function() {
        $(".topcont .backimg").click(function(){
            history.back(-1);
        })

        var swiper="";
        var initFun = function(){
            swiper = new Swiper('.swiper-container', {
              slidesPerView: 'auto',
                spaceBetween: 0,
                freeModeSticky : true,
                resistance:true,
                resistanceRatio : 0,
                autoHeight:true
            });
        }
        
        
        initFun();

        function removeInfo(element){
               var pageContextVal = $("#PageContext").val();
               var srid = element.prop("title");
        	   $.ajax({
              		url:pageContextVal+'/wxsr/removeSr',
                    data: {"srid": srid},
					type: "POST",
					dataType: "json",
                    success: function(data){
                   	 switch(data.code) {
							case "0" :
			                    $(element).parents("li").remove();
								break;
							case "1" : alert(data.message); break;
						}
                    },
                    fail: function(){
						alert("删除失败，未知错误!");
                    }
                })
        }

        $(".menu").on("click",function(){
            var isconf = confirm("删除可真没了哦！");
            if (isconf) {
                removeInfo($(this));
            }
        })

        function addRewLi(md,y,rewtxt,rewname,rewnum,rewId){
            var $li = $("<li></li>");
            var $swiperContainer = $("<div class='swiper-container'></div>");
            var $swiperWrapper = $("<div class='swiper-wrapper'></div>");
            var $swiperSlide = $("<div class='swiper-slide slidecont'></div>");
            var $rewDate = $("<div class='rewDate'><div class='rewDateMD'>"+md+"</div><div class='rewDateY'>"+y+"</div></div>");
            var $rightCont = $("<div class='rightCont'></div>");
            var $stuname = $("<div class='stuName'>"+ rewname +"</div>");
            var $rewTxt = $("<div class='rewTxt'> <p>"+rewtxt+"</p> </div>");
            var $rewNum = $("<div class='rewNumTxt'>"+ rewnum +"</div>");
            var $menu = $("<div title="+ rewId +" class='swiper-slide menu'>删除</div>");
            $menu.on("click",function(){
            	var isconf = confirm("删除可真没了哦！");
                if (isconf) {
                    removeInfo($(this));
                }
            })


            $swiperSlide.append($rewDate);

            $rightCont.append($stuname);
            $rightCont.append($rewTxt);
            $rightCont.append($rewNum);

            $swiperSlide.append($rightCont);
            
            $swiperWrapper.append($swiperSlide);
            $swiperWrapper.append($menu);
            $swiperContainer.append($swiperWrapper);
            $li.append($swiperContainer);

            $(".rewardsInfoCont ul").append($li);

        }

    /*     setTimeout(function() {
            for (var i = 0; i < 10; i++) {
                addRewLi("26/10","2018","不听话影响课堂纪律","+8");
            }
            initFun();
        }, 3000); */
        
     
        $(window).scroll(function(){
            var scrollTop = $(this).scrollTop();  //滚动条顶端
            var scrollHeight = $(document.body).height(); //文档总高度
            var windowHeight = $(window).height();  //document.body.clientHeight;  window窗口高度
            var pageContextVal = $("#PageContext").val();
        	var pageNo = $("#pageNo").val();
        	var pageSize = $("#pageSize").val();
        	var totalCount = $("#totalCount").val();
        	var lastPage = $("#lastPage").val();
        	var classId = $("#classId").val();
          /*   console.log("pageN0 is "+pageNo);
        	console.log("lastPage is "+lastPage); */
        	//console.log("文档总高度:"+scrollHeight + "滚动条顶端" +scrollTop + "窗口高度" +windowHeight);
            if(scrollHeight - (scrollTop + windowHeight)  < 50 && (parseInt(pageNo) < parseInt(lastPage))){
            	pageNo++;
                var alertTop = (windowHeight - 100)/2 + scrollTop;
                $(".alertFrame").css({
                    "top" : alertTop+"px"
                })
            
                $(".alertFrame").show();
                  $.ajax({
               		url:pageContextVal+'/wxsr/pageMoreSrc',
                    data: {"pageNo": pageNo, "pageSize": pageSize,"classId":classId},
					type: "POST",
					async:false, 
					dataType: "json",
                     success: function(data){
                    	 switch(data.code) {
							case "0" :
	                          	var obj = JSON.parse(data.message);
	                        	var dataTwo = JSON.parse(obj.data);
	                        	$("#pageNo").val(obj.pageNo);
	                        	$("#pageSize").val(obj.pageSize);
	                        	$("#totalCount").val(obj.totalCount);
	                        	$("#lastPage").val(obj.lastPage);
	                        	for (var i = 0; i < dataTwo.length; i++) {
	                        		var score = dataTwo[i].score;
	                        		var scoreType= dataTwo[i].scoreType;
	                        		if(scoreType == 0){
	                        			score = "-" + score;
	                        		}else{
	                        			score = "+" + score;
	                        		}
	                        		addRewLi(dataTwo[i].createDayString,
											dataTwo[i].createYearString,
											dataTwo[i].remarks,
											dataTwo[i].name,
											score,
											dataTwo[i].id);
								}
	                        	initFun();
	                        	$(".alertFrame").hide();
								break;
							case "1" : alert(data.message); break;
						}
                     },
                     fail: function(){

                     }
                 })
            }
        });
    });
</script>
</body>
</html>