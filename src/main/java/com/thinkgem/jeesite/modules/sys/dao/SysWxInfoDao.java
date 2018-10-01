/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;

/**
 * 微信信息DAO接口
 * @author wzy
 * @version 2018-10-01
 */
@MyBatisDao
public interface SysWxInfoDao extends CrudDao<SysWxInfo> {
	
	public SysWxInfo findByNo(SysWxInfo sysWxInfo);
	
	public SysWxInfo findByOpenId(SysWxInfo sysWxInfo);
	
}