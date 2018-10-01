package com.thinkgem.jeesite.common.utils;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.config.WxGlobal;
import com.thinkgem.jeesite.modules.sys.manager.WxAccessTokenManager;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  


/**
* @author wzy
* @version 创建时间：2018年1月11日 下午5:22:05
* @ClassName 微信JS调用接口
* @Description 类描述
*/
public class WxJsSkdUtils {
	
	
	
	//获取签名
	public static Map<String, String> getJsApiSign(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String,String>();
		try {
			//获取AccessToken
			WxAccessTokenManager wtUtils = WxAccessTokenManager.getInstance();
			String accessToken = wtUtils.getAccessToken();
			//获取jsApiTicket
			String jsApiTicket = wtUtils.getJsApiTicket(accessToken);
			
		    String url = BasePathUtils.getFullPath(request);
		    
		    //根据服务器配置更改url中的协议是http还是https 因为nignx可能进行了转换
		    String isChangePro = DictUtils.getDictValue("httpProtocol", "systemControl", "http");
		    //String isChangePro = DictUtils.getDictValue("协议", "protocol", "http");
		    if("https".equals(isChangePro)) {
		    	url = url.replace("http", "https");
		    }
		    System.out.println("url is " + url);
		    map = WxJsSkdUtils.sign(jsApiTicket, url);
		    map.put("appId", WxGlobal.getAppId());//成功
		    map.put("code", "0");//成功
		}catch(Exception ex) {
			map.put("code", "1");//失败
			ex.printStackTrace();
		}
		return map;
	}
	

	public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        System.out.println(string1);

        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
        System.out.println("signature:"+signature);
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
}
