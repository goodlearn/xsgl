package com.thinkgem.jeesite.common.entity;

import java.io.Serializable;

import com.thinkgem.jeesite.common.config.Global;

public class Qrecord implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private long timeOut;//过期时间
	private String idCard;//idCard
	
	
	
	public Qrecord(String idCard) {
		//过期时间30分钟
		setTimeOut(Global.WX_QREORD_TOME_OUT());
		setIdCard(idCard);
	}
	
	public long getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	
	
	
}

