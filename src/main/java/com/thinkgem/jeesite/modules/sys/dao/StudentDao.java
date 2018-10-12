/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.dao;

import java.util.List;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.Student;

/**
 * 学生信息DAO接口
 * @author wzy
 * @version 2018-09-07
 */
@MyBatisDao
public interface StudentDao extends CrudDao<Student> {
	
	public Student findByNo(Student student);
	
	public Student findByIdCard(Student student);
	
	public List<Student> findListRank(Student student);
	
}