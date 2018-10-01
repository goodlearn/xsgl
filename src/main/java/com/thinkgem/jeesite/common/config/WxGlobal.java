package com.thinkgem.jeesite.common.config;

import com.thinkgem.jeesite.modules.sys.manager.WxAccessTokenManager;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
* @author wzy
* @version 创建时间：2018年1月2日 下午4:29:52
* @ClassName 类名称
* @Description 类描述
*/
public class WxGlobal {
	
	//获取上传图片
	public static String getUpImage(String mediaId) {
		WxAccessTokenManager wxAccessTokenManager = WxAccessTokenManager.getInstance();
		return String.format("http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s", wxAccessTokenManager.getAccessToken(),mediaId);
	}
		

	public static String getCertificationToken() {
		return DictUtils.getDictValue("CERTIFICATION_TOKEN", "systemControl", "");
	}
	
	public static String getAppId() {
		return DictUtils.getDictValue("APP_ID", "systemControl", "");
	}
	
	public static String getAppSecret() {
		return DictUtils.getDictValue("APP_SECRET", "systemControl", "");
	}
	
	//模板消息Url
	public static String getTemplateMsgUrl() {
		return DictUtils.getDictValue("TMPLATE_MSG_URL", "systemControl", "");
	}
	
	//JSAPITICKET
	public static String getJsApiTicketUrl() {
		return DictUtils.getDictValue("JS_API_TICKET_URL", "systemControl", "");
	}
	
	//获取接口Token
	public static String getInterfaceTokenUrl() {
		return DictUtils.getDictValue("INTERFACE_TOKEN_URL", "systemControl", "");
	}
	
	//授权回调
	public static String getOauthRedirectUrlIndex() {
		return DictUtils.getDictValue("OAUTH_REDIRECT_URL_INDEX", "systemControl", "");
	}
	
	//请求用户信息code
	public static String getReqOpenIdUrlCode() {
		return DictUtils.getDictValue("REQ_OPENID_URL_CODE", "systemControl", "");
	}
	
	//请求用户信息code
	public static String getReqUserInfoUrlCode() {
		return DictUtils.getDictValue("REQ_USER_INFO_URL", "systemControl", "");
	}
	
	

	//请求用户信息token
	public static String getUserInfoTokenUrl() {
		return DictUtils.getDictValue("REQ_USERINFO_TOKEN_URL", "systemControl", "");
	}

	//请求用户信息userinfo
	public static String getUserInfoUrl() {
		return DictUtils.getDictValue("REQ_USERINFO_URL", "systemControl", "");
	}
	
	//微信模板
	public static String getTemplateMsg_1() {
		return DictUtils.getDictValue("TEMPLATE_MSG_1", "systemControl", "");
	}
	
	public static String getTemplateMsg_2() {
		return DictUtils.getDictValue("TEMPLATE_MSG_2", "systemControl", "");
	}
	//微信模板颜色
	public static String getTemplateMsgColor_1() {
		return DictUtils.getDictValue("TEMPLATE_MSG_COLOR_1", "systemControl", "");
	}
	
	public static String getTemplateMsgColor_2() {
		return DictUtils.getDictValue("TEMPLATE_MSG_COLOR_2", "systemControl", "");
	}
	
	public static String getTestOpenId() {
		return DictUtils.getDictValue("TEST_OPEN_ID", "systemControl", "");
	}

	public static String getUserClick(String redirectUrl,boolean isBase) {
		if(isBase) {
			return String.format(WxGlobal.getReqOpenIdUrlCode(),WxGlobal.getAppId(),redirectUrl);
		}else {
			return String.format(WxGlobal.getReqUserInfoUrlCode(),WxGlobal.getAppId(),redirectUrl);
		}
	}
}
