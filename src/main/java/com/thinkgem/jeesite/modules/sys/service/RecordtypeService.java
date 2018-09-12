/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Recordtype;
import com.thinkgem.jeesite.modules.sys.dao.RecordtypeDao;

/**
 * 记录类型Service
 * @author 王泽宇
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
public class RecordtypeService extends CrudService<RecordtypeDao, Recordtype> {

	public Recordtype get(String id) {
		return super.get(id);
	}
	
	public List<Recordtype> findList(Recordtype recordtype) {
		return super.findList(recordtype);
	}
	
	public Page<Recordtype> findPage(Page<Recordtype> page, Recordtype recordtype) {
		return super.findPage(page, recordtype);
	}
	
	@Transactional(readOnly = false)
	public void save(Recordtype recordtype) {
		super.save(recordtype);
	}
	
	@Transactional(readOnly = false)
	public void delete(Recordtype recordtype) {
		super.delete(recordtype);
	}
	
}