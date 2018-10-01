package com.thinkgem.jeesite.modules.sys.entity.wx;

/**
 * 复杂按钮
 * @author wzy
 *
 */
public class WxComplexButton extends WxButton{
	
	private WxButton[] sub_button;

	public WxButton[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(WxButton[] sub_button) {
		this.sub_button = sub_button;
	}

	

	
}
