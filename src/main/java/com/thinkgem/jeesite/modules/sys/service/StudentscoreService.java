/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Studentscore;
import com.thinkgem.jeesite.modules.sys.dao.StudentscoreDao;

/**
 * 学生分数Service
 * @author wzy
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
public class StudentscoreService extends CrudService<StudentscoreDao, Studentscore> {

	public Studentscore get(String id) {
		return super.get(id);
	}
	
	public List<Studentscore> findList(Studentscore studentscore) {
		return super.findList(studentscore);
	}
	
	public Page<Studentscore> findPage(Page<Studentscore> page, Studentscore studentscore) {
		return super.findPage(page, studentscore);
	}
	
	@Transactional(readOnly = false)
	public void save(Studentscore studentscore) {
		super.save(studentscore);
	}
	
	@Transactional(readOnly = false)
	public void delete(Studentscore studentscore) {
		super.delete(studentscore);
	}
	
}