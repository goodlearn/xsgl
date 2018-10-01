package com.thinkgem.jeesite.modules.sys.entity.wx;

import java.util.Map;

/**
* @author wzy
* @version 创建时间：2018年1月4日 上午9:51:21
* @ClassName 模板基类
* @Description 类描述
*/
public class WxTemplate {
	
		private String template_id;//模板ID
	    private String touser;//目标客户
	    private String url;//用户点击模板信息的跳转页面
	    private String topcolor;//字体颜色
	    private Map<String,WxTemplateData> data;//模板里的数据
		public String getTemplate_id() {
			return template_id;
		}
		public void setTemplate_id(String template_id) {
			this.template_id = template_id;
		}
		public String getTouser() {
			return touser;
		}
		public void setTouser(String touser) {
			this.touser = touser;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getTopcolor() {
			return topcolor;
		}
		public void setTopcolor(String topcolor) {
			this.topcolor = topcolor;
		}
		public Map<String, WxTemplateData> getData() {
			return data;
		}
		public void setData(Map<String, WxTemplateData> data) {
			this.data = data;
		}
	    
	    
}
