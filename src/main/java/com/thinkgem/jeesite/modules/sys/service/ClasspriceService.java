/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Classprice;
import com.thinkgem.jeesite.modules.sys.dao.ClasspriceDao;

/**
 * 班费记录Service
 * @author 王泽宇
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
public class ClasspriceService extends CrudService<ClasspriceDao, Classprice> {

	public Classprice get(String id) {
		return super.get(id);
	}
	
	public List<Classprice> findList(Classprice classprice) {
		return super.findList(classprice);
	}
	
	public Page<Classprice> findPage(Page<Classprice> page, Classprice classprice) {
		return super.findPage(page, classprice);
	}
	
	@Transactional(readOnly = false)
	public void save(Classprice classprice) {
		super.save(classprice);
	}
	
	@Transactional(readOnly = false)
	public void delete(Classprice classprice) {
		super.delete(classprice);
	}
	
}