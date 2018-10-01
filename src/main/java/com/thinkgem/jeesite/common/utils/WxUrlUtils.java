package com.thinkgem.jeesite.common.utils;
import java.io.BufferedReader;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;  
import java.net.ConnectException;  
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;  
import javax.net.ssl.SSLContext;  
import javax.net.ssl.SSLSocketFactory;  
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

  
import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject; 
public class WxUrlUtils {

    private static Logger log = LoggerFactory.getLogger(WxUrlUtils.class);  

    //默认实现
	private static X509TrustManager x509Tm = new X509TrustManager() {
	        @Override
	        public X509Certificate[] getAcceptedIssuers() {
	            return null;
	        }
	 
	        @Override
	        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
	                throws CertificateException {
	 
	        }
	 
	        @Override
	        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
	                throws CertificateException {
	 
	        }
	    };
	    
	 /**
	  * 发起https请求
	  */
	public static HttpsURLConnection getHttpsURLConnection(String requestUrl, String requestMethod, String outputStr) {
		HttpsURLConnection httpUrlConn = null;;
		 try {  
			 // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = { x509Tm };  
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");  
            sslContext.init(null, tm, new java.security.SecureRandom());  
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();  
  
            URL url = new URL(requestUrl);  
            httpUrlConn = (HttpsURLConnection) url.openConnection();  
            httpUrlConn.setSSLSocketFactory(ssf);  
  
            httpUrlConn.setDoOutput(true);  
            httpUrlConn.setDoInput(true);  
            httpUrlConn.setUseCaches(false);  
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);  
  
            if ("GET".equalsIgnoreCase(requestMethod))  
                httpUrlConn.connect();  
		 } catch (ConnectException ce) {  
	          log.error("Weixin server connection timed out.");  
	     } catch (Exception e) {  
	          log.error("https request error:{}", e);  
	     }  
		 return httpUrlConn;
	}
    
	 /** 
     * 发起https请求并获取结果 
     *  
     * @param requestUrl 请求地址 
     * @param requestMethod 请求方式（GET、POST） 
     * @param outputStr 提交的数据 
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值) 
     */  
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {  
        JSONObject jsonObject = null;  
        StringBuffer buffer = new StringBuffer();  
        try {  
           
        	HttpsURLConnection httpUrlConn = getHttpsURLConnection(requestUrl,requestMethod,outputStr);
  
        	if(null == httpUrlConn) {
        		return null;
        	}
            // 当有数据需要提交时  
            if (null != outputStr) {  
                OutputStream outputStream = httpUrlConn.getOutputStream();  
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));  
                outputStream.close();  
            }  
  
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();  
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");  
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);  
  
            String str = null;  
            while ((str = bufferedReader.readLine()) != null) {  
                buffer.append(str);  
            }  
            bufferedReader.close();  
            inputStreamReader.close();  
            // 释放资源  
            inputStream.close();  
            inputStream = null;  
            httpUrlConn.disconnect();  
            jsonObject = JSONObject.fromObject(buffer.toString());  
        } catch (ConnectException ce) {  
            log.error("Weixin server connection timed out.");  
        } catch (Exception e) {  
            log.error("https request error:{}", e);  
        }  
        return jsonObject;  
    }  
	
}
