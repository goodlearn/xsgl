package com.thinkgem.jeesite.modules.sys.entity.wx;

/**
 * 普通消息类
 * @author wzy
 *
 */
public class WechatTextMsg extends WechatMsg{

	// 文本内容
	private String Content = "";

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
	
}
