/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Institute;

/**
 * 学院信息DAO接口
 * @author wzy
 * @version 2018-09-07
 */
@MyBatisDao
public interface InstituteDao extends CrudDao<Institute> {
	
}