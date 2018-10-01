package com.thinkgem.jeesite.common.entity;

import java.io.Serializable;

import com.thinkgem.jeesite.common.config.Global;

public class WxCodeCache implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long timeOut;//过期时间
	private String openId;//openID
	
	
	
	public WxCodeCache(String openId) {
		//过期时间30分钟
		setTimeOut(Global.WX_CODE_TOME_OUT());
		setOpenId(openId);
	}
	
	public long getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	
}

