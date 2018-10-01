/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.config.WxGlobal;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.WxUrlUtils;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import net.sf.json.JSONObject;

import com.thinkgem.jeesite.modules.sys.dao.SysWxInfoDao;

/**
 * 微信信息Service
 * @author wzy
 * @version 2018-10-01
 */
@Service
@Transactional(readOnly = true)
public class SysWxInfoService extends CrudService<SysWxInfoDao, SysWxInfo> {

	public SysWxInfo get(String id) {
		return super.get(id);
	}
	
	public List<SysWxInfo> findList(SysWxInfo sysWxInfo) {
		return super.findList(sysWxInfo);
	}
	
	public Page<SysWxInfo> findPage(Page<SysWxInfo> page, SysWxInfo sysWxInfo) {
		return super.findPage(page, sysWxInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(SysWxInfo sysWxInfo) {
		super.save(sysWxInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysWxInfo sysWxInfo) {
		super.delete(sysWxInfo);
	}
	
	
	
	/**
	 * 查询用户
	 * @param param
	 */
	public SysWxInfo findWxInfoByNo(String no) {
		SysWxInfo query = new SysWxInfo();
		query.setNo(no);
		query.setDelFlag(SysWxInfo.DEL_FLAG_NORMAL);
		return dao.findByNo(query);
	}
	
	/**
	 * 查询用户
	 * @param param
	 */
	public SysWxInfo findWxInfoByOpenId(String openId) {
		SysWxInfo query = new SysWxInfo();
		query.setOpenId(openId);
		query.setDelFlag(SysWxInfo.DEL_FLAG_NORMAL);
		return dao.findByOpenId(query);
	}
	
	/**
	 * 根据微信获取员工号
	 */
	public String findEmpNo(String openId) {
		SysWxInfo sysWxInfo = findWxInfoByOpenId(openId);
		return sysWxInfo.getNo();
	}
	
	//保存用户信息
	@Transactional(readOnly = false)
	public void tieInfo(String openId,String no,String tieType) {
		SysWxInfo sysWxInfo = findWxInfoByOpenId(openId);
		sysWxInfo.setNo(no);
		sysWxInfo.setTieType(tieType);
		sysWxInfo.setTieEndDate(CasUtils.plusDay2(Global.TIE_DATE_NUM));
		User user = UserUtils.get(Global.DEFAULT_ID_SYS_MANAGER);
		sysWxInfo.setUpdateBy(user);
		sysWxInfo.setUpdateDate(new Date());
		dao.update(sysWxInfo);
	}
	
	//保存用户信息(头像等)
	@Transactional(readOnly = false)
	public void saveWxInfo(Map<String,String> param) {
		if(null == param) {
			logger.info("用户信息为空，无法更新");
			return;
		}
		String openId = param.get("openId");
		if(null == openId) {
			logger.info("openId为空，无法更新");
			return;
		}
		String accessToken = param.get("access_toke");
		if(null == accessToken) {
			logger.info("accessToken为空，无法更新");
			return;
		}
		Map<String,String> map = getWxUserInfo(accessToken,openId);
		String nickname = map.get("nickname");
		String sex = map.get("sex");
		String headimgurl = map.get("headimgurl");
		SysWxInfo sysWxInfo = findWxInfoByOpenId(openId);
		if(null != sysWxInfo) {
			logger.info("nickname是:"+nickname);
			logger.info("headimgurl是:"+headimgurl);
			logger.info("sex是"+sex);
			boolean isUpdate = false;
			if(!StringUtils.isEmpty(nickname)) {
				isUpdate = true;
				sysWxInfo.setNickname(CasUtils.convertUTF8_MB4(nickname));
			}
			if(!StringUtils.isEmpty(sex)) {
				isUpdate = true;
				sysWxInfo.setSex(sex);
			}
			if(!StringUtils.isEmpty(headimgurl)) {
				isUpdate = true;
				sysWxInfo.setHeadimgurl(headimgurl);
			}
			if(isUpdate) {
				User user = UserUtils.get(Global.DEFAULT_ID_SYS_MANAGER);
				sysWxInfo.setUpdateBy(user);
				sysWxInfo.setUpdateDate(new Date());
				dao.update(sysWxInfo);
			}
		}else {
			sysWxInfo = new SysWxInfo();
			logger.info("nickname是:");
			logger.info("headimgurl是:");
			logger.info("sex是");
			User user = UserUtils.get("1");
			//第一次操作
			sysWxInfo.setId(IdGen.uuid());
			sysWxInfo.setNo(null);
			sysWxInfo.setOpenId(openId);
			sysWxInfo.setUpdateBy(user);
			sysWxInfo.setTieEndDate(new Date());
			sysWxInfo.setUpdateDate(new Date());
			sysWxInfo.setCreateBy(user);
			sysWxInfo.setCreateDate(new Date());
			if(!StringUtils.isEmpty(nickname)) {
				sysWxInfo.setNickname(CasUtils.convertUTF8_MB4(nickname));
			}
			if(!StringUtils.isEmpty(sex)) {
				sysWxInfo.setSex(sex);
			}
			if(!StringUtils.isEmpty(headimgurl)) {
				sysWxInfo.setHeadimgurl(headimgurl);
			}
			dao.insert(sysWxInfo);
		}	
	}
	
	public Map<String,String> getWxUserInfo(String access_toke,String openId) {
		Map<String,String> ret = new HashMap<String,String>();
		String url = String.format(WxGlobal.getUserInfoUrl(),access_toke,openId);
		logger.info("request WxUserInfo from url: {}", url);
		JSONObject jsonObject = WxUrlUtils.httpRequest(url, Global.GET_METHOD, null);
		logger.info("返回的JSON是："+jsonObject);
		if(null != jsonObject) {
  		  	String nickname = jsonObject.getString("nickname");
  		  	String headimgurl = jsonObject.getString("headimgurl");
  		  	String sex = jsonObject.getString("sex");
  		  	logger.info(" nickname is " + nickname + " headimgurl is "+headimgurl+" sex is " + sex);
  		  	ret.put("nickname", nickname);
  		    ret.put("headimgurl", headimgurl);
  		    ret.put("sex", sex);
		}else {
			logger.info("get accessToken by code is error");
		}
		return ret;
	}
}