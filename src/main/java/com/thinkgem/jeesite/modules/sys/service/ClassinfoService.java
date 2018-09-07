/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Classinfo;
import com.thinkgem.jeesite.modules.sys.dao.ClassinfoDao;

/**
 * 班级分类Service
 * @author wzy
 * @version 2018-09-07
 */
@Service
@Transactional(readOnly = true)
public class ClassinfoService extends CrudService<ClassinfoDao, Classinfo> {

	public Classinfo get(String id) {
		return super.get(id);
	}
	
	public List<Classinfo> findList(Classinfo classinfo) {
		return super.findList(classinfo);
	}
	
	public Page<Classinfo> findPage(Page<Classinfo> page, Classinfo classinfo) {
		return super.findPage(page, classinfo);
	}
	
	@Transactional(readOnly = false)
	public void save(Classinfo classinfo) {
		super.save(classinfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(Classinfo classinfo) {
		super.delete(classinfo);
	}
	
}