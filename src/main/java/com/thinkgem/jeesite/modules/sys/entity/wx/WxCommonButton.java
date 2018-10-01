package com.thinkgem.jeesite.modules.sys.entity.wx;

/**
 * 普通按钮（子按钮） 
 * @author wzy
 *
 */
public class WxCommonButton extends WxButton{ 
	 
	 private String type;
	 private String key;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}  
	 
	 
}
