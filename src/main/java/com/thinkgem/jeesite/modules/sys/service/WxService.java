package com.thinkgem.jeesite.modules.sys.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.config.WxGlobal;
import com.thinkgem.jeesite.common.service.BaseService;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.WxUrlUtils;
import com.thinkgem.jeesite.modules.sys.dao.DictDao;
import com.thinkgem.jeesite.modules.sys.dao.UserDao;
import com.thinkgem.jeesite.modules.sys.entity.wx.WechatTextMsg;
import com.thinkgem.jeesite.modules.sys.entity.wx.WxTemplate;
import com.thinkgem.jeesite.modules.sys.entity.wx.WxTemplateData;
import com.thinkgem.jeesite.modules.sys.manager.WxAccessTokenManager;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONObject;

/**
 * 微信信息处理
 * @author wzy
 *
 */
@Service
public class WxService extends BaseService implements InitializingBean {
	

	@Override
	public void afterPropertiesSet() throws Exception {
		
	}
	
	/**
	   * 获取媒体文件
	   * @param accessToken 接口访问凭证
	   * @param mediaId 媒体文件id
	   * @param savePath 文件在本地服务器上的存储路径
	   * */
	 public  String downloadMedia(String mediaId, String savePath,String openId) {
		 String filePath = null;
	     try {
	    	//获取Token
	    	 String requestUrl = String.format(WxGlobal.getUpImage(mediaId));
	    	 logger.info("格式化Url:" + requestUrl); 
	    	 URL url = new URL(requestUrl);
	    	 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    	 conn.setDoInput(true);
		     conn.setRequestMethod("GET");
		     if (!savePath.endsWith("/")) {
		        savePath += "/";
		     }
		     // 根据内容类型获取扩展名
		     String fileExt = CasUtils.getFileexpandedName(conn.getHeaderField("Content-Type"));
		     // 将mediaId作为文件名
		      filePath = savePath + openId + fileExt;
		      BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
		      FileOutputStream fos = new FileOutputStream(new File(filePath));
		      byte[] buf = new byte[8096];
		      int size = 0;
		      while ((size = bis.read(buf)) != -1)
		      fos.write(buf, 0, size);
		      fos.close();
		      bis.close();
		      conn.disconnect();
		      String info = String.format("下载媒体文件成功，filePath=" + filePath);
		      logger.info(info);
	    } catch (IOException e) {
	    	  e.printStackTrace();
	 	      String error = String.format("下载媒体文件失败：%s", e);
	 	      logger.info(error);
	    }
	    return filePath;
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
		JSONObject jsonObject = WxUrlUtils.httpRequest(url, Global.GET_METHOD, null);
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
	
	public Map<String,String> getWxUserInfo(String access_toke,String openId) {
		Map<String,String> ret = new HashMap<String,String>();
		String url = String.format(WxGlobal.getUserInfoUrl(),access_toke,openId);
		logger.info("request WxUserInfo from url: {}", url);
		JSONObject jsonObject = WxUrlUtils.httpRequest(url, Global.GET_METHOD, null);
		logger.info("返回的JSON是："+jsonObject);
		if(null != jsonObject) {
  		  	String nickname = jsonObject.getString("nickname");
  		  	String headimgurl = jsonObject.getString("headimgurl");
  		  	String sex = jsonObject.getString("sex");
  		  	logger.info(" nickname is " + nickname + " headimgurl is "+headimgurl+" sex is " + sex);
  		  	ret.put("nickname", nickname);
  		    ret.put("headimgurl", headimgurl);
  		    ret.put("sex", sex);
		}else {
			logger.info("get accessToken by code is error");
		}
		return ret;
	}
	
	
	
	/**
	 * 激活成功消息提示
	 * @param toUser
	 * @param username
	 * @return
	 */
	public String sendMessageActive(String toUser,String username) {
		logger.info("send msg start");
		/*
		 *	模板ID 为 43WT2ikE7JLLBMcBRPqZyW_HgLiDjpoX6X7h05_Mscw
		 *	内容：
		 *		{{first.DATA}}
				激活时间：{{keyword1.DATA}}
				激活账户：{{keyword2.DATA}}
				{{remark.DATA}}
		 */
		
		//first.DATA
		WxTemplateData first = new WxTemplateData();
		first.setColor(WxGlobal.getTemplateMsgColor_1());
		first.setValue("账户激活成功通知");
		WxTemplateData keyword1 = new WxTemplateData();
		keyword1.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword1.setValue(DateUtils.getDateTime());
		WxTemplateData keyword2 = new WxTemplateData();
		keyword2.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword2.setValue(username);
		WxTemplateData remark = new WxTemplateData();
		String content="感谢您的支持，详情请前往快递中心咨询";
		remark.setColor(WxGlobal.getTemplateMsgColor_1());
		remark.setValue(content);
		
		WxTemplate template = new WxTemplate();
		template.setUrl(null);
		template.setTouser(toUser);
		template.setTopcolor(WxGlobal.getTemplateMsgColor_2());
		template.setTemplate_id(WxGlobal.getTemplateMsg_2());
		Map<String,WxTemplateData> wxTemplateDatas = new HashMap<String,WxTemplateData>();
		wxTemplateDatas.put("keyword1", keyword1);
		wxTemplateDatas.put("keyword2", keyword2);
		wxTemplateDatas.put("remark", remark);
		template.setData(wxTemplateDatas);
		//获取Token
    	WxAccessTokenManager wxAccessTokenManager = WxAccessTokenManager.getInstance();
		String accessToken = wxAccessTokenManager.getAccessToken();
		String url = String.format(WxGlobal.getTemplateMsgUrl(),accessToken);
		String jsonString = JSONObject.fromObject(template).toString();
		JSONObject jsonObject = WxUrlUtils.httpRequest(url,Global.POST_METHOD,jsonString); 
		logger.info("msg is " + jsonObject);
		int result = 0;
        if (null != jsonObject) {  
             if (0 != jsonObject.getInt("errcode")) {  
                 result = jsonObject.getInt("errcode");  
                 logger.error("错误 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
             }  
         }
        logger.info("模板消息发送结果："+result);
		logger.info("send msg end");
		return null;
	}
	
	/**
	 * 
	 * @param toUser 接收人
	 * @param username 经办人
	 * @param money 金额
	 * @return
	 */
	public String sendMessageEndExpress(String toUser,String username,String money) {
		logger.info("send msg start");
		/*
		 *	模板ID 为 DQjKDzP4EQqrA6r_abDDYJjyNZ9071tuDls2DeNrJZA
		 *	内容：
		 *		{{first.DATA}}
				状态：{{keyword1.DATA}}
				时间：{{keyword2.DATA}}
				金额：{{keyword3.DATA}}
				经办人：{{keyword4.DATA}}
				{{remark.DATA}}
		 */
		
		//first.DATA
		WxTemplateData first = new WxTemplateData();
		first.setColor(WxGlobal.getTemplateMsgColor_1());
		first.setValue("您收到一个订单");
		WxTemplateData keyword1 = new WxTemplateData();
		keyword1.setColor(WxGlobal.getTemplateMsgColor_1());
		String state = DictUtils.getDictLabel("1","expressState","已完结");
		keyword1.setValue(state);
		WxTemplateData keyword2 = new WxTemplateData();
		keyword2.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword2.setValue(DateUtils.getDateTime());
		WxTemplateData keyword3 = new WxTemplateData();
		keyword3.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword3.setValue(money);
		WxTemplateData keyword4 = new WxTemplateData();
		keyword4.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword4.setValue(username);
		WxTemplateData remark = new WxTemplateData();
		String content="您的快递已取走,谢谢您的合作";
		remark.setColor(WxGlobal.getTemplateMsgColor_1());
		remark.setValue(content);
		
		WxTemplate template = new WxTemplate();
		template.setUrl(null);
		template.setTouser(toUser);
		template.setTopcolor(WxGlobal.getTemplateMsgColor_2());
		template.setTemplate_id(WxGlobal.getTemplateMsg_1());
		Map<String,WxTemplateData> wxTemplateDatas = new HashMap<String,WxTemplateData>();
		wxTemplateDatas.put("keyword1", keyword1);
		wxTemplateDatas.put("keyword2", keyword2);
		wxTemplateDatas.put("keyword3", keyword3);
		wxTemplateDatas.put("keyword4", keyword4);
		wxTemplateDatas.put("remark", remark);
		template.setData(wxTemplateDatas);
		//获取Token
    	WxAccessTokenManager wxAccessTokenManager = WxAccessTokenManager.getInstance();
		String accessToken = wxAccessTokenManager.getAccessToken();
		String url = String.format(WxGlobal.getTemplateMsgUrl(),accessToken);
		String jsonString = JSONObject.fromObject(template).toString();
		JSONObject jsonObject = WxUrlUtils.httpRequest(url,Global.POST_METHOD,jsonString); 
		logger.info("msg is " + jsonObject);
		int result = 0;
        if (null != jsonObject) {  
             if (0 != jsonObject.getInt("errcode")) {  
                 result = jsonObject.getInt("errcode");  
                 logger.error("错误 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
             }  
         }
        logger.info("模板消息发送结果："+result);
		logger.info("send msg end");
		return null;
	}
	
	public String sendMessageScore(String toUser,String username,String score,String type,String remarks) {
		logger.info("send msg start");
		/**
			用户积分：{{keyword1.DATA}}
			通知类型：{{keyword2.DATA}}
			{{remark.DATA}}
		 */
		WxTemplateData first = new WxTemplateData();
		first.setColor(WxGlobal.getTemplateMsgColor_1());
		first.setValue("分值变更通知");
		WxTemplateData keyword1 = new WxTemplateData();
		keyword1.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword1.setValue(score);
		WxTemplateData keyword2 = new WxTemplateData();
		keyword2.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword2.setValue(type);
		WxTemplateData remark = new WxTemplateData();
		remark.setColor(WxGlobal.getTemplateMsgColor_1());
		remark.setValue(remarks);
		
		WxTemplate template = new WxTemplate();
		template.setUrl(null);
		template.setTouser(toUser);
		template.setTopcolor(WxGlobal.getTemplateMsgColor_2());
		template.setTemplate_id(WxGlobal.getTemplateMsg_1());
		Map<String,WxTemplateData> wxTemplateDatas = new HashMap<String,WxTemplateData>();
		wxTemplateDatas.put("keyword1", keyword1);
		wxTemplateDatas.put("keyword2", keyword2);
		wxTemplateDatas.put("remark", remark);
		template.setData(wxTemplateDatas);
		//获取Token
    	WxAccessTokenManager wxAccessTokenManager = WxAccessTokenManager.getInstance();
		String accessToken = wxAccessTokenManager.getAccessToken();
		String url = String.format(WxGlobal.getTemplateMsgUrl(),accessToken);
		String jsonString = JSONObject.fromObject(template).toString();
		JSONObject jsonObject = WxUrlUtils.httpRequest(url,Global.POST_METHOD,jsonString); 
		logger.info("msg is " + jsonObject);
		int result = 0;
        if (null != jsonObject) {  
             if (0 != jsonObject.getInt("errcode")) {  
                 result = jsonObject.getInt("errcode");  
                 logger.error("错误 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
             }  
         }
        logger.info("模板消息发送结果："+result);
		logger.info("send msg end");
		return null;
	}
	
	/**
	 * 
	 * @param toUser 接收人
	 * @param username 经办人
	 * @param money 金额
	 * @return
	 */
	public String sendMessageExpress(String toUser,String username,String money) {
		logger.info("send msg start");
		/*
		 *	模板ID 为 DQjKDzP4EQqrA6r_abDDYJjyNZ9071tuDls2DeNrJZA
		 *	内容：
		 *		{{first.DATA}}
				状态：{{keyword1.DATA}}
				时间：{{keyword2.DATA}}
				金额：{{keyword3.DATA}}
				经办人：{{keyword4.DATA}}
				{{remark.DATA}}
		 */
		
		//first.DATA
		WxTemplateData first = new WxTemplateData();
		first.setColor(WxGlobal.getTemplateMsgColor_1());
		first.setValue("您收到一个订单");
		WxTemplateData keyword1 = new WxTemplateData();
		keyword1.setColor(WxGlobal.getTemplateMsgColor_1());
		String state = DictUtils.getDictLabel("0","expressState","已入库");
		keyword1.setValue(state);
		WxTemplateData keyword2 = new WxTemplateData();
		keyword2.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword2.setValue(DateUtils.getDateTime());
		WxTemplateData keyword3 = new WxTemplateData();
		keyword3.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword3.setValue(money);
		WxTemplateData keyword4 = new WxTemplateData();
		keyword4.setColor(WxGlobal.getTemplateMsgColor_1());
		keyword4.setValue(username);
		WxTemplateData remark = new WxTemplateData();
		String content="你的快递已到，请携带身份证前往易度空间领取";
		remark.setColor(WxGlobal.getTemplateMsgColor_1());
		remark.setValue(content);
		
		WxTemplate template = new WxTemplate();
		template.setUrl(null);
		template.setTouser(toUser);
		template.setTopcolor(WxGlobal.getTemplateMsgColor_2());
		template.setTemplate_id(WxGlobal.getTemplateMsg_1());
		Map<String,WxTemplateData> wxTemplateDatas = new HashMap<String,WxTemplateData>();
		wxTemplateDatas.put("keyword1", keyword1);
		wxTemplateDatas.put("keyword2", keyword2);
		wxTemplateDatas.put("keyword3", keyword3);
		wxTemplateDatas.put("keyword4", keyword4);
		wxTemplateDatas.put("remark", remark);
		template.setData(wxTemplateDatas);
		//获取Token
    	WxAccessTokenManager wxAccessTokenManager = WxAccessTokenManager.getInstance();
		String accessToken = wxAccessTokenManager.getAccessToken();
		String url = String.format(WxGlobal.getTemplateMsgUrl(),accessToken);
		String jsonString = JSONObject.fromObject(template).toString();
		JSONObject jsonObject = WxUrlUtils.httpRequest(url,Global.POST_METHOD,jsonString); 
		logger.info("msg is " + jsonObject);
		int result = 0;
        if (null != jsonObject) {  
             if (0 != jsonObject.getInt("errcode")) {  
                 result = jsonObject.getInt("errcode");  
                 logger.error("错误 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
             }  
         }
        logger.info("模板消息发送结果："+result);
		logger.info("send msg end");
		return null;
	}
	
	//处理微信消息
	public String WxPostMsgProcess(HttpServletRequest request) throws Exception{
		
		String respMessage = null;
		
		//xml解析
		Map<String, String> requestMap = xmlToMap(request); 
		
		//解析公共消息部分
		// 发送方帐号（open_id）
        String fromUserName = requestMap.get("FromUserName");
        // 公众帐号
        String toUserName = requestMap.get("ToUserName");
        // 消息类型
        String msgType = requestMap.get("MsgType");
        // 消息内容
        String content = requestMap.get("Content");
        
        logger.info("FromUserName is:" + fromUserName + ", ToUserName is:" + toUserName + ", MsgType is:" + msgType);
		
        //文本消息
        if (msgType.equals(Global.WX_REQ_MESSAGE_TYPE_TEXT)) {
        	WechatTextMsg wechatMsg = new WechatTextMsg();
        	wechatMsg.setContent("欢迎关注锡职快递系统，请<a href=\""+WxGlobal.getUserClick(WxGlobal.getOauthRedirectUrlIndex(),false)+"\">绑定个人信息</a>，正确绑定之后，快递到达，您将第一时间收到通知");
        	wechatMsg.setToUserName(fromUserName);
        	wechatMsg.setFromUserName(toUserName);
        	wechatMsg.setCreateTime(new Date().getTime() + "");
        	wechatMsg.setMsgType(msgType);
        	respMessage = messageToXml(wechatMsg);
        	logger.info(respMessage);
        } 
        // 事件推送
        else if (msgType.equals(Global.WX_REQ_MESSAGE_TYPE_EVENT)) {
        	String eventType = requestMap.get("Event");// 事件类型
        	// 订阅
            if (eventType.equals(Global.WX_EVENT_TYPE_SUBSCRIBE)) {
            	WechatTextMsg wechatMsg = new WechatTextMsg();
            	wechatMsg.setContent("欢迎关注学生管理系统，请点击<a href=\""+WxGlobal.getUserClick(WxGlobal.getOauthRedirectUrlIndex(),true)+"\">绑定信息</a>，正确绑定之后，积分变换，您将第一时间收到通知");
            	wechatMsg.setToUserName(fromUserName);
            	wechatMsg.setFromUserName(toUserName);
            	wechatMsg.setCreateTime(new Date().getTime() + "");
            	wechatMsg.setMsgType(Global.WX_RESP_MESSAGE_TYPE_TEXT);
                respMessage = messageToXml(wechatMsg);
            } 
            //取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
            else if (eventType.equals(Global.WX_EVENT_TYPE_UNSUBSCRIBE)) {
            	// 取消订阅
                
            } 
            // 自定义菜单点击事件
            else if (eventType.equals(Global.WX_EVENT_TYPE_CLICK)) {
                String eventKey = requestMap.get("EventKey");// 事件KEY值，与创建自定义菜单时指定的KEY值对应
                if (eventKey.equals("customer_telephone")) {
                	WechatTextMsg wechatMsg = new WechatTextMsg();
                	wechatMsg.setContent("0755-86671980");
                	wechatMsg.setToUserName(fromUserName);
                	wechatMsg.setFromUserName(toUserName);
                	wechatMsg.setCreateTime(new Date().getTime() + "");
                	wechatMsg.setMsgType(Global.WX_RESP_MESSAGE_TYPE_TEXT);
                    
                    respMessage = messageToXml(wechatMsg);
                }
            }
        }
        return respMessage;
	} 
	
	//排序
	public String sort(String timestamp, String nonce) {
		 String[] strArray = { WxGlobal.getCertificationToken(), timestamp, nonce };
		 Arrays.sort(strArray);
		 StringBuilder sbuilder = new StringBuilder();
		    for (String str : strArray) {
		        sbuilder.append(str);
		    }
		 return sbuilder.toString();
	}
	
	//sha1加密
	public String sha1(String decript) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
			messageDigest.update(decript.getBytes());
			byte messageDigests[] = messageDigest.digest();
			// Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigests.length; i++) {
                String shaHex = Integer.toHexString(messageDigests[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	
	
	/** 
     * 文本消息对象转换成xml 
     *  
     * @param textMessage 文本消息对象 
     * @return xml 
     */ 
    public static String messageToXml(WechatTextMsg wechatMsg){
        XStream xstream = new XStream();
        xstream.alias("xml", wechatMsg.getClass());
        return xstream.toXML(wechatMsg);
    }
	
	 /**
	  * 解析微信发来的请求(XML)
	  * @param request
	  * @return
	  * @throws IOException
	  * @throws DocumentException
	  */
    public Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException{
         
    	// 将解析结果存储在HashMap中  
        Map<String, String> map = new HashMap<String, String>();  
        // 从request中取得输入流    
        InputStream inputStream = request.getInputStream();  
        // 读取输入流   
        SAXReader reader = new SAXReader();   
        Document document = reader.read(inputStream);    
        String requestXml = document.asXML();  
        String subXml = requestXml.split(">")[0] + ">";  
        requestXml = requestXml.substring(subXml.length());  
        // 得到xml根元素  
        Element root = document.getRootElement();  
        // 得到根元素的全部子节点  
        List<Element> elementList = root.elements();  
        // 遍历全部子节点  
        for (Element e : elementList) {  
            map.put(e.getName(), e.getText());  
            }  
        map.put("requestXml", requestXml);    
        // 释放资源    
        inputStream.close();    
        inputStream = null;    
        return map;  
    }

    
    
    
}
