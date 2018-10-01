package com.thinkgem.jeesite.common.utils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
* @author wzy
* @version 创建时间：2018年1月11日 下午7:25:26
* @ClassName 类名称
* @Description 类描述
*/
public class BasePathUtils {
		protected static String contextPath = null;
		protected static String basePath = null; 
		protected static String realPath = null;
		
		public static String getBasePathNoServer(HttpServletRequest request,boolean isServer) {
			if(isServer) {
				return request.getScheme()+"://"+request.getServerName();
			}else {
				return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
			}
		}
	
		public static String getBasePath(HttpServletRequest request) {
			contextPath = request.getContextPath();
			basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
			return basePath;
		}
	
		public static String getRealPath(HttpServletRequest request, String path) {
			ServletContext context = request.getSession().getServletContext();
			realPath = context.getRealPath(path);
			realPath = context.getRealPath(path)+"\\";
			return realPath;
		}
	
		public static String getMyRealPath(HttpServletRequest request, String path) {
			ServletContext context = request.getSession().getServletContext();
			realPath = context.getRealPath(path);
			realPath = context.getRealPath(path);
			return realPath;
		}
	
		
		/**
		* 获取完整的URL地址，包括参数
		* */
		public static String getFullPath(HttpServletRequest request) {
			//getQueryString()得到的是url后面的参数串，和前者相加就是带参数的请求路径了 
			String queryString = request.getQueryString(); 
			//获取URL地址
			StringBuffer url = request.getRequestURL();
			String fullPath=null;
			//URL地址+参数串
			if(queryString!=null){
			fullPath = url +"?"+ queryString;
			}else {
			fullPath= url.toString();
			}
			return fullPath;
		}
}
