/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Professioninfo;
import com.thinkgem.jeesite.modules.sys.dao.ProfessioninfoDao;

/**
 * 专业分类Service
 * @author wzy
 * @version 2018-09-07
 */
@Service
@Transactional(readOnly = true)
public class ProfessioninfoService extends CrudService<ProfessioninfoDao, Professioninfo> {

	public Professioninfo get(String id) {
		return super.get(id);
	}
	
	public List<Professioninfo> findList(Professioninfo professioninfo) {
		return super.findList(professioninfo);
	}
	
	public Page<Professioninfo> findPage(Page<Professioninfo> page, Professioninfo professioninfo) {
		return super.findPage(page, professioninfo);
	}
	
	@Transactional(readOnly = false)
	public void save(Professioninfo professioninfo) {
		super.save(professioninfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(Professioninfo professioninfo) {
		super.delete(professioninfo);
	}
	
}