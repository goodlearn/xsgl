package com.thinkgem.jeesite.modules.sys.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.WxUrlUtils;
import com.thinkgem.jeesite.modules.sys.entity.wx.WxButton;
import com.thinkgem.jeesite.modules.sys.entity.wx.WxCommonButton;
import com.thinkgem.jeesite.modules.sys.entity.wx.WxComplexButton;
import com.thinkgem.jeesite.modules.sys.entity.wx.WxMenu;
import com.thinkgem.jeesite.modules.sys.entity.wx.WxViewButton;

import net.sf.json.JSONObject;

@Service
public class WxMenuManager {

    private static Logger log = LoggerFactory.getLogger(WxMenuManager.class);  

	
	//获取菜单
	private String getMenuByUrl() {
		WxAccessTokenManager wxAccessTokenManager = WxAccessTokenManager.getInstance();
		wxAccessTokenManager.getAccessToken();
		return String.format("https://api.weixin.qq.com/cgi-bin/menu/get?access_token=%s", wxAccessTokenManager.getAccessToken());
	}
	
	//创建菜单
	private String createMenuByUrl() {
		WxAccessTokenManager wxAccessTokenManager = WxAccessTokenManager.getInstance();
		return String.format("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s", wxAccessTokenManager.getAccessToken());
	}
	
	//创建菜单
	private String deleteMenuByUrl() {
		WxAccessTokenManager wxAccessTokenManager = WxAccessTokenManager.getInstance();
		wxAccessTokenManager.getAccessToken();
		return String.format("https://api.weixin.qq.com/cgi-bin/menu/create?delete=%s", wxAccessTokenManager.getAccessToken());
	}
	
	//创建菜单
	public int createMenu() {
		int ret = 0;
		WxMenu menu = createMenuStyle();//创建菜单
		WxAccessTokenManager wxAccessTokenManager = WxAccessTokenManager.getInstance();
		String createMenuUrl = createMenuByUrl();//创建菜单URL
		// 将菜单对象转换成json字符串  
	    String jsonMenu = JSONObject.fromObject(menu).toString();
	    System.out.println("菜单字符串是:"+jsonMenu);
		JSONObject jsonObject = WxUrlUtils.httpRequest(createMenuUrl, Global.POST_METHOD, jsonMenu);
		if (null != jsonObject) {  
	        if (0 != jsonObject.getInt("errcode")) {  
	            ret = jsonObject.getInt("errcode");  
	            log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));  
	        }  
	    }  
		return ret;
	}
	
	/**
	 * 创建菜单样式
	 */
	private WxMenu createMenuStyle() {
		return null;
	}
	
	//创建菜单样式(示例原始代码)
	private WxMenu createOriginMenuStyle() {
		WxCommonButton btn11 = new WxCommonButton();  
        btn11.setName("天气预报");  
        btn11.setType("click");  
        btn11.setKey("11");  
  
        WxCommonButton btn12 = new WxCommonButton();  
        btn12.setName("公交查询");  
        btn12.setType("click");  
        btn12.setKey("12");  
  
        WxCommonButton btn13 = new WxCommonButton();  
        btn13.setName("周边搜索");  
        btn13.setType("click");  
        btn13.setKey("13");  
  
        WxCommonButton btn14 = new WxCommonButton();  
        btn14.setName("历史上的今天");  
        btn14.setType("click");  
        btn14.setKey("14");  
          
        WxCommonButton btn15 = new WxCommonButton();  
        btn15.setName("电影排行榜");  
        btn15.setType("click");  
        btn15.setKey("32");  
  
        WxCommonButton btn21 = new WxCommonButton();  
        btn21.setName("歌曲点播");  
        btn21.setType("click");  
        btn21.setKey("21");  
  
        WxCommonButton btn22 = new WxCommonButton();  
        btn22.setName("经典游戏");  
        btn22.setType("click");  
        btn22.setKey("22");  
  
        WxCommonButton btn23 = new WxCommonButton();  
        btn23.setName("美女电台");  
        btn23.setType("click");  
        btn23.setKey("23");  
  
        WxCommonButton btn24 = new WxCommonButton();  
        btn24.setName("人脸识别");  
        btn24.setType("click");  
        btn24.setKey("24");  
  
        WxCommonButton btn25 = new WxCommonButton();  
        btn25.setName("聊天唠嗑");  
        btn25.setType("click");  
        btn25.setKey("25");  
  
        WxCommonButton btn31 = new WxCommonButton();  
        btn31.setName("Q友圈");  
        btn31.setType("click");  
        btn31.setKey("31");  
  
        WxCommonButton btn33 = new WxCommonButton();  
        btn33.setName("幽默笑话");  
        btn33.setType("click");  
        btn33.setKey("33");  
          
        WxCommonButton btn34 = new WxCommonButton();  
        btn34.setName("用户反馈");  
        btn34.setType("click");  
        btn34.setKey("34");  
          
        WxCommonButton btn35 = new WxCommonButton();  
        btn35.setName("关于我们");  
        btn35.setType("click");  
        btn35.setKey("35");  
          
        WxViewButton btn32 = new WxViewButton();  
        btn32.setName("使用帮助");  
        btn32.setType("view");  
        btn32.setUrl("http://www.baidu.com");  
  
        WxComplexButton mainBtn1 = new WxComplexButton();  
        mainBtn1.setName("生活助手");  
        mainBtn1.setSub_button(new WxButton[] { btn11, btn12, btn13, btn14, btn15 });  
  
        WxComplexButton mainBtn2 = new WxComplexButton();  
        mainBtn2.setName("休闲驿站");  
        mainBtn2.setSub_button(new WxButton[] { btn21, btn22, btn23, btn24, btn25 });  
  
        WxComplexButton mainBtn3 = new WxComplexButton();  
        mainBtn3.setName("更多");  
        mainBtn3.setSub_button(new WxButton[] { btn31, btn33, btn34, btn35, btn32 });  
	
        WxMenu menu = new WxMenu();
        menu.setButton(new WxButton[] { mainBtn1, mainBtn2, mainBtn3 }); 
        return menu;
	}
	
}
