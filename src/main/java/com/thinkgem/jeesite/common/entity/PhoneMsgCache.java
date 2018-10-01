package com.thinkgem.jeesite.common.entity;

import java.io.Serializable;

import com.thinkgem.jeesite.common.config.Global;

/**
 * 手机短信缓存对象
 * @author wzy
 *
 */
public class PhoneMsgCache implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Object value;//缓存数据
	private long regTime;//注册时间
	private long timeOut;//过期时间
	private boolean prompt;//是否已经提示用户（用户可能没有收到验证码，提示一次用户，防止连续点击发送短信）
	private int sendTimes;//发送次数
	private final int defaultTimes = 1;//默认注册次数
	
	public PhoneMsgCache(String value) {
		setValue(value);
		//注册时间
		setRegTime(System.currentTimeMillis());
		//过期时间30分钟
		setTimeOut(Global.MOBILE_CODE_TOME_OUT());
		setPrompt(false);
		setSendTimes(defaultTimes);//第一次发送
	}

	//恢复注册次数
	public void resetSendTime() {
		setSendTimes(defaultTimes);//恢复默认次数
	}
	
	public long getRegTime() {
		return regTime;
	}



	public void setRegTime(long regTime) {
		this.regTime = regTime;
	}



	public int getSendTimes() {
		return sendTimes;
	}


	public void setSendTimes(int sendTimes) {
		this.sendTimes = sendTimes;
	}



	public boolean isPrompt() {
		return prompt;
	}



	public void setPrompt(boolean prompt) {
		this.prompt = prompt;
	}


	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(long timeOut) {
		this.timeOut = timeOut;
	}

}
