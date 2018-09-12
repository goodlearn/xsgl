/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Recordtype;

/**
 * 记录类型DAO接口
 * @author 王泽宇
 * @version 2018-09-11
 */
@MyBatisDao
public interface RecordtypeDao extends CrudDao<Recordtype> {
	
}