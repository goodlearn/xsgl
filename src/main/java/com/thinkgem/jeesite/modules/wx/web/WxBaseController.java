package com.thinkgem.jeesite.modules.wx.web;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.config.WxGlobal;
import com.thinkgem.jeesite.common.entity.WxCodeCache;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.DeviceUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.WxUrlUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.service.SysWxInfoService;

public abstract class WxBaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	
	protected final String ERR_EMP_NO_NULL = "学号（工号）为空";
	protected final String ERR_WX_TIE_NO_NULL = "微信信息未绑定";
	protected final String ERR_PWD_NO_NULL = "密码不能为空";
	protected final String ERR_ITYPE_NO_NULL = "身份类型不能为空";
	protected final String ERR_ITYPE_ERR = "身份类型错误";
	protected final String ERR_CLASS_ID_ERR = "班级信息错误";
	protected final String ERR_CLASS_NULL = "班级不存在";
	protected final String ERR_SR_NULL = "奖惩信息不存在";
	protected final String ERR_STU_NO_NULL = "学生不存在";
	protected final String ERR_STU_NO_NO_NULL = "学生不存在";
	protected final String ERR_STU_NO_WX= "学生未绑定微信号";
	protected final String ERR_TEACHER_NO_NULL = "老师不存在";
	protected final String ERR_PWD = "密码错误";
	protected final String ERR_EXIST_WX_INFO = "该工号已绑定";
	protected final String ERR_OPEN_ID_NOT_GET = "微信号未获取";
	protected final String ERR_WP_LEVEL_NULL = "身份信息无权限";
	protected final String ERR_ERROR_SCORE = "分数保存出现错误，请联系管理员";
	protected final String ERR_CALSS_ID_NO_NULL = "请选择班级";
	protected final String ERR_SR_ID_NULL = "未选择奖惩编号";
	protected final String ERR_PARAM_NULL = "参数为空";
	protected final String ERR_ADD_REWARY_TYPE_NULL = "请选择类型";
	protected final String ERR_REASON_NULL = "请填写原因";
	protected final String ERR_DYFZ_NULL = "请填写分值";
	
	//导航
	protected final String NAVIGAION_TEACHER_1 = "德育管理";
	protected final String NAVIGAION_TEACHER_2 = "批量添加";
	protected final String NAVIGAION_TEACHER_3 = "班费管理";
	protected final String NAVIGAION_TEACHER_4 = "消息公告";
	//页面
	protected final String INDEX_INFO = "modules/wxp/teacherIndex";//首页
	protected final String WX_SUCCESS = "modules/wxp/success";//首页
	protected final String REG_INFO = "modules/wxp/wxLogin";//绑定信息首页
	protected final String STU_REWARDS = "modules/wxp/stuRewards";//跳转到奖惩列表页面
	protected final String CLASS_EXPENSES = "modules/wxp/classExpenses";//跳转到班费列表页面

	
	protected final String STU_REWARDS_ADD = "modules/wxp/stuRewardsAdd";//跳转到奖惩添加页面
	
	protected final String STU_INDEX_INFO = "modules/wxp/stuindex";//学生首页
	
	protected final String STU_REWARDS_DETAILS = "modules/wxp/stuRewardsDetails";//跳转到奖惩详细页面
	protected final String STU_REWARDS_DETAILS_ALL = "modules/wxp/stuRewardsDetailsAll";//跳转到奖惩详细页面
	protected final String STU_REWARDS_DETAILS_RANK = "modules/wxp/stuRewardsRank";//跳转到奖惩详细页面
	protected final String STU_REWARDS_BATCH = "modules/wxp/stuRewardsBatch";//跳转到奖惩批量页面
	protected final String STU_CLASS_REWARDS_INFO = "modules/wxp/classRewardsInfo";//跳转到奖惩详细页面
	
	protected final String USER_TASK = "modules/wxp/userTask";///用户任务
	protected final String TASK_INFO = "modules/wxp/taskInfo";//任务信息
	protected final String CHECK_SUBMIT = "modules/wxp/checkSubmit";//任务信息
	//月度任务页面
	protected final String MONTH_MASK_INDEX_INFO = "modules/wxp/mmIndex";//首页
	protected final String MONTH_MASK_ALLOCATION_PAGE = "modules/wxp/allocationPage";//分配页面
	protected final String MONTH_MASK_ADD_CAR_PAGE = "modules/wxp/monthMaskCar";//分配页面
	protected final String MONTH_MASK_ADD_NO_CAR_PAGE = "modules/wxp/monthMaskNoCar";//分配页面
	//故障记录
	protected final String ADD_FR_PAGE = "modules/wxp/addFrPage";//添加故障页面
	protected final String SUCCESS_FR_PAGE = "modules/wxp/frSuccess";//故障成功页面
	//错误页面
	protected final String WX_ERROR = "modules/wxp/500";
	//错误信息
	protected final String ERR_MEDIA_ID_NULL = "图片编号出现错误";
	protected final String ERR_WSM_ID_NULL = "任务号为空";
	protected final String ERR_WSM_NULL = "不存在该任务";
	protected final String ERR_MONTH_MASK_NUM_LIMITED = "任务添加数量已达上限";
	protected final String ERR_WS_MASK_WC_NOT_NULL = "任务已经发布过了";
	protected final String ERR_MSP_ID_NULL = "人员未分配任务号";
	protected final String ERR_MSP_SUBMIT = "任务已经提交，不得修改";
	protected final String ERR_MSP_SUBMIT_TOTAL = "班长已经提交任务，不得修改";
	protected final String ERR_MSP_LIST_NULL = "人员未分配任务";
	protected final String ERR_WP_NULL = "不存在该员工";
	protected final String ERR_MASK_SUBMIT_NULL = "任务已经提交过了";
	protected final String ERR_MASK_NOT_EXPIRED = "任务还未结束";
	protected final String ERR_NOT_MASK_SERVICE = "没有任务处理对象";
	protected final String ERR_NOT_MASK_LIST = "没有任务信息";
	protected final String ERR_NOT_PART = "无部位信息";
	protected final String ERR_NO_WX_REQUEST = "不是微信浏览器访问";
	protected final String ERR_CLIENT_MECHINE = "请在微信客户端打开";
	protected final String ERR_USER_NO_AUTH = "用户未授权";
	protected final String ERR_NAME_NO_MATCH_NAME = "工号和姓名不匹配";
	protected final String ERR_FRR_SAVE = "故障保存失败";
	
	//信息
	protected final String MSG_ALLOCATION_SUCCESS = "任务分配成功";
	protected final String MSG_FR_SUCCESS = "故障保存成功";
	protected final String MSG_MM_SUCCESS = "任务保存成功";
	
	protected final String successCode = "0";//成功码
	protected final String errCode = "1";//错误码
	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;
	
	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	
	@Autowired
	protected SysWxInfoService sysWxInfoService;
	
	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}
	
	@ModelAttribute
	public String init(HttpServletRequest request, HttpServletResponse response,Model model) {
		
		//微信测试
		if(null != Global.TEST_WX_OPEN_ID) {
			return null;
		}
		
		try {
			if(!DeviceUtils.isWeChat(request)) {
				logger.info(ERR_NO_WX_REQUEST);
				model.addAttribute("message",WX_ERROR);
				return WX_ERROR;
			}
			//获取微信号
			String openId = getOpenId(request, response);//获取微信号
			 
			//String openId = WxGlobal.getTestOpenId();
			//微信号为空
			if(StringUtils.isEmpty(openId)) {
				model.addAttribute("message",ERR_OPEN_ID_NOT_GET);
				return WX_ERROR;
			}else {
				model.addAttribute("openId",openId);
			}
		    logger.info("openId is " + openId);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	//获取openId
	private String getOpenId(HttpServletRequest request, HttpServletResponse response) {
		 String openId = null;
		 try {
			  request.setCharacterEncoding("UTF-8");  
		      response.setCharacterEncoding("UTF-8"); 
		      
		      String code = null;
		      Cookie[] cookies = request.getCookies();
		      if(cookies!=null){
		      		for(Cookie cookie : cookies){
		      			if(cookie.getName().equals("code")){
		      				code = cookie.getValue();
		      				logger.info("初始化获取Code:"+code);
		      				//如果服务器已经移除code 那么code要重新请求
		      				 WxCodeCache wxCodeCache = (WxCodeCache)CacheUtils.get(code);
		      				  if(null == wxCodeCache) {
		    		    		  //服务器已经移除
		      					  logger.info("服务器移除了Code:"+code);
		    		    		  code = null;//置空
		    		    		  cookie.setMaxAge(0);//移除cookie
		    		    	  }
		      			}
		      		}
		      }
		      
		      
		      if(null == code) {
		    	  code = request.getParameter("code");//微信服务器返回了code
		    	  logger.info("微信服务器返回Code:"+code);
		      }
		      
		      StringBuffer sb = request.getRequestURL();
		      String redirectUrl = sb.toString();
		      if(StringUtils.isEmpty(redirectUrl)) {
		    	  logger.info("初始化跳转页面异常");
		    	  return null;//异常错误
		      }
		      logger.info("Code是:"+code);
		      //这一句纯属为了打印日志
		      if(StringUtils.isEmpty(code)) {
		    	  	logger.info("前往微信服务器获取Code");
		    	  	String redirectAddress = WxGlobal.getUserClick(redirectUrl,false);
		    	  	logger.info("前往微信服务器获取Code地址："+redirectAddress);
		      }
		      if(StringUtils.isEmpty(code)) {
		        	response.sendRedirect(WxGlobal.getUserClick(redirectUrl,false));
		        	return null;
		      }else {
		    	  /**
		    	   * 不为空的情况两种一种是微信服务器返回的新code 一种是用户强制刷新的旧code
		    	   * 旧code 获取缓存
		    	   * 新code 添加缓存 
		    	   */
		    	  
		    	  //是否是旧缓存
		    	  WxCodeCache wxCodeCache = (WxCodeCache)CacheUtils.get(code);
		    	  if(null == wxCodeCache) {
		    		  logger.info("No Code Cache,New Code Cache:"+code);
		    		  //没有缓存过 添加缓存
		    		  //获取openID
		    		  Map<String,String> map = getOpenIdInfo(code);
				      if(null != map) {
				        	openId = map.get("openId");
				        	sysWxInfoService.saveWxInfo(map);  //用户数据保存一次
				      }
				      if(null !=openId) {
				    	  logger.info("Add New Code Cache:"+openId);
				    	  //获取openID之后 缓存数据
			    		  wxCodeCache = new WxCodeCache(openId);
			    		  //记录键值 为之后删除
			    		  CacheUtils.putWxCodeKey(code, openId);  
			    		  CacheUtils.put(code, wxCodeCache);
			    		  
			    		  /**
			    		   * 存放cookie
			    		   */
			    		  Cookie userCookie=new Cookie("code",code);
			    		  userCookie.setMaxAge(Global.WX_CODE_TIME_OUT_INT());
			    		  userCookie.setPath("/");
			    		  response.addCookie(userCookie);
				      }
		    	  }else {
		    		  logger.info("Code Cache:"+code);
		    		  //缓存过
		    		  //查看过期时间
		    		  long timeOut = wxCodeCache.getTimeOut();
		    		  if(System.currentTimeMillis() > timeOut) {
		    			   //移除缓存 过时了
		    			  CacheUtils.clearWxCodeCacheKeies();//清除过期的微信code
		    			  CacheUtils.remove(code);
		    			  logger.info("缓存过时，前往微信服务器获取Code");
				          response.sendRedirect(WxGlobal.getUserClick(redirectUrl,true));
		    		  }else {
			    		  openId = wxCodeCache.getOpenId();//缓存的openId
			    		  logger.info("Cahce OpenId Is " + openId);
			    		  logger.info("没过时");
		    		  }
		    		 
		    	  }
		    	  CacheUtils.clearWxCodeCacheKeies();//清除过期的微信code
		    	  logger.info("code is " + code);
		      }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 return openId;
	}
	
	/**
	 * 获取access_token和openId
	 * @param code
	 * @return
	 */
	public Map<String,String> getOpenIdInfo(String code) {
		Map<String,String> ret = new HashMap<String,String>();
		String url = String.format(WxGlobal.getUserInfoTokenUrl(),WxGlobal.getAppId(),WxGlobal.getAppSecret(),code);
		logger.info("request accessToken from url: {}", url);
		net.sf.json.JSONObject jsonObject = WxUrlUtils.httpRequest(url, Global.GET_METHOD, null);
		logger.info("返回的JSON是："+jsonObject);
		if(null != jsonObject) {
  		  	String accessToken = jsonObject.getString("access_token");
  		  	String openId = jsonObject.getString("openid");
  		  	logger.info(" access_token is " + accessToken + " openId is "+openId);
  		  	ret.put("access_toke", accessToken);
  		    ret.put("openId", openId);
		}else {
			logger.info("get accessToken by code is error");
		}
		return ret;
	}
	
	/**
	 * 依据微信号查询是否已经注册并且激活
	 * 用户是新用户，需要跳转到注册页面
	 * 用户已经注册，需要查看是否激活，没有激活跳转到审核页面，激活后跳转到对应页面
	 * @param openId 查询微信号
	 * @return
	 * 如果微信号查询不到，返回注册页面
	 * 如果微信号查询到，但是没有激活，返回未审核页面
	 * 如果用户已注册，也已经激活，返回空值
	 */
	protected String validateRegByOpenId(String openId,Model model) {
		if(null == openId) {
			model.addAttribute("message",ERR_OPEN_ID_NOT_GET);
			return WX_ERROR;//微信号为空
		}
		
		SysWxInfo sysWxInfo = sysWxInfoService.findWxInfoByOpenId(openId);
		//判断
		if(null == sysWxInfo) {
			model.addAttribute("message",ERR_USER_NO_AUTH);
			return WX_ERROR;//微信用户未授权
		}
		
		String no = sysWxInfo.getNo();
		if(StringUtils.isEmpty(no)) {
			//没有绑定数据 跳转到绑定页面
			model.addAttribute("errUrl",REG_INFO);
			return WX_ERROR;//没有绑定数据 返回非空数据
		}
		
		Date endDate = sysWxInfo.getTieEndDate();//结束绑定日期 如果结束 需要重新绑定
		if(endDate.before(new Date())) {
			//没有绑定数据 跳转到绑定页面
			model.addAttribute("errUrl",REG_INFO);
			return WX_ERROR;//没有绑定数据 返回非空数据
		}
		return null;//用户已注册，也已经绑定，返回空值
	}
	
	protected String validateRegByOpenIdForJson(String openId,Model model) {
		if(null == openId) {
			return ERR_OPEN_ID_NOT_GET;//微信号为空
		}
		
		SysWxInfo sysWxInfo = sysWxInfoService.findWxInfoByOpenId(openId);
		//判断
		if(null == sysWxInfo) {
			return ERR_USER_NO_AUTH;//微信用户未授权
		}
		
		String no = sysWxInfo.getNo();
		if(StringUtils.isEmpty(no)) {
			//没有绑定数据 跳转到绑定页面
			return REG_INFO;//没有绑定数据 返回非空数据
		}
		
		Date endDate = sysWxInfo.getTieEndDate();//结束绑定日期 如果结束 需要重新绑定
		if(endDate.before(new Date())) {
			//没有绑定数据 跳转到绑定页面
			return REG_INFO;//没有绑定数据 返回非空数据
		}
		return null;//用户已注册，也已经绑定，返回空值
	}
	
	/**
	 * 返回数据 携带检验码和参数
	 */
	protected String backJsonWithCode(String code,String message){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("message", message);
		String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
		return jsonResult;
	}
	
}
