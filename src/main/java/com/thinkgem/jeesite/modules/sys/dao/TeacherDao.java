/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Teacher;

/**
 * 老师信息DAO接口
 * @author wzy
 * @version 2018-10-01
 */
@MyBatisDao
public interface TeacherDao extends CrudDao<Teacher> {
	
	//依据工号查询
	public Teacher findByNo(Teacher teacher);
	
}