package com.thinkgem.jeesite.modules.sys.manager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.config.WxGlobal;
import com.thinkgem.jeesite.common.utils.WxUrlUtils;

import net.sf.json.JSONObject;

/**
 * 微信调用接口凭证保存
 * @author wzy
 *
 */
public class WxAccessTokenManager {
	
	 /**
	  * 日志对象
	  */
	 protected Logger logger = LoggerFactory.getLogger(getClass());
	
	 private Map<String, String> map = new HashMap<String, String>();
	 
	 //JS SDK
	 private Map<String,String> jsApiTicket = new HashMap<String, String>();
	 
	 private static WxAccessTokenManager single = null;
	 
	 public WxAccessTokenManager() {}
	 
	 //单例
	 public static synchronized WxAccessTokenManager getInstance() {
		 if(null == single) {
			 single = new WxAccessTokenManager();
		 }
		 return single;
	 }
	 
	 //获取JSPAPI_TOKEN
	 public String getJsApiTicket(String accessToken) {
		 if(null == accessToken) {
			 logger.info("getJsApiTicket accessToken is null");
		 }
		 String retJsapiTicket = null;
		 String time = jsApiTicket.get("time");
         String jsapi_ticket = jsApiTicket.get("jsapi_ticket");
         Long nowDate = new Date().getTime();
         if (jsapi_ticket != null && time != null && (nowDate - Long.parseLong(time)) < 7200 * 1000) { 
        	 logger.info("当前时间是：" + nowDate);
        	 logger.info("缓存时间是：" + time);
        	 logger.info("jsapi_ticket 存在，且没有超时 ， 返回单例：" + jsapi_ticket);
        	 retJsapiTicket = jsapi_ticket;
         }else {
        	 logger.info("start jsapi_ticket");
        	 logger.info("jsapi_ticket 超时 ， 或者不存在 ， 重新获取");
	       	 String url = String.format(WxGlobal.getJsApiTicketUrl(),accessToken);
	       	 logger.info("格式化Url:" + url); 
	       	 JSONObject jsonObject = WxUrlUtils.httpRequest(url,Global.GET_METHOD,null); 
	       	 if(null != jsonObject) {
	       		 String errcode = jsonObject.getString("errcode");
	       		 if("0".equals(errcode)) {
		       		 retJsapiTicket = jsonObject.getString("ticket");
		       		jsApiTicket.put("time", nowDate.toString());
		       		jsApiTicket.put("jsapi_ticket", retJsapiTicket);
		           	logger.info("jsapi_ticket is " + retJsapiTicket);  
	       		 }else {
	       			logger.info("errorCode jsApiTicket" + errcode);  
	       		 }
	           	 
	       	 }else {
	       		logger.info("jsonObject is null");  
	       	 }
	       	logger.info("end access_token");  
        }
		 return retJsapiTicket;
	 }
	 
	
	 //获取access_token
	 public String getAccessToken() {
		 String retAccessToken = null;
		 String time = map.get("time");
         String accessToken = map.get("access_token");
         Long nowDate = new Date().getTime();
         if (accessToken != null && time != null && (nowDate - Long.parseLong(time)) < 7200 * 1000) {  
        	 logger.info("当前时间是：" + nowDate);
        	 logger.info("缓存时间是：" + time);
        	 logger.info("accessToken 存在，且没有超时 ， 返回单例：" + accessToken);  
              retAccessToken = accessToken;
         }else {
        	 logger.info("start access_token");
        	 logger.info("accessToken 超时 ， 或者不存在 ， 重新获取");
        	  String url = String.format(WxGlobal.getInterfaceTokenUrl(),WxGlobal.getAppId(),WxGlobal.getAppSecret());
        	  logger.info("格式化Url:" + url); 
        	  JSONObject jsonObject = WxUrlUtils.httpRequest(url,Global.GET_METHOD,null); 
        	  if(null != jsonObject) {
        		  retAccessToken = jsonObject.getString("access_token");
            	  map.put("time", nowDate.toString());
            	  map.put("access_token", retAccessToken);
            	  logger.info("access_token" + retAccessToken);  
        	  }else {
        		  logger.info("jsonObject is null");  
        	  }
        	  logger.info("end access_token");  
         }
		 return retAccessToken;
	 }
	 
}
