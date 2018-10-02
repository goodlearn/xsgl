/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;

/**
 * 奖惩记录DAO接口
 * @author 王泽宇
 * @version 2018-09-30
 */
@MyBatisDao
public interface StudentrecordDao extends CrudDao<Studentrecord> {

	public List<Studentrecord> findListLimit(Studentrecord studentrecord);
	
	public int findTotalCount(Studentrecord studentrecord);
}