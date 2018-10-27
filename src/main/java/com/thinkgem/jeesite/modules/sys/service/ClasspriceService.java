/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Classinfo;
import com.thinkgem.jeesite.modules.sys.entity.Classprice;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.BaseInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.ClassinfoDao;
import com.thinkgem.jeesite.modules.sys.dao.ClasspriceDao;

/**
 * 班费记录Service
 * @author 王泽宇
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
public class ClasspriceService extends CrudService<ClasspriceDao, Classprice> {
	
	@Autowired
	private ClassinfoDao classinfoDao;

	public Classprice get(String id) {
		return super.get(id);
	}
	
	public List<Classprice> findList(Classprice classprice) {
		return super.findList(classprice);
	}
	
	public Page<Classprice> findPageByClassId(Page<Classprice> page,Classprice query) {
		return super.findPage(page, query);
	}
	
	
	//当前账号的所有班级
	public List<Classinfo> findClassInfo(){
		User user = UserUtils.getUser();
		if(user.isAdmin()) {
			return BaseInfoUtils.getAllClassinfoDaoList();
		}
		String no = user.getNo();
		Classinfo queryci = new Classinfo();
		queryci.setTeacherNo(no);
		List<Classinfo> cls = classinfoDao.findList(queryci);
		//没有班级信息
		if(null == cls || cls.size() == 0) {
			return null;
		}
		return cls;
	}
	
	public Page<Classprice> findPage(Page<Classprice> page, Classprice classprice) {
		
		User user = UserUtils.getUser();
		if(user.isAdmin()) {
			return super.findPage(page, classprice);
		}
		//不是超管
		String no = user.getNo();
		Classinfo queryci = new Classinfo();
		queryci.setTeacherNo(no);
		queryci.setDelFlag(Classinfo.DEL_FLAG_NORMAL);
		List<Classinfo> cls = classinfoDao.findList(queryci);
		//没有班级信息
		if(null == cls || cls.size() == 0) {
			return new Page<Classprice>();
		}
		List<String> clsIds = new ArrayList<String>();
		for(Classinfo clsi : cls) {
			clsIds.add(clsi.getId());
		}
		classprice.setClass_ids(clsIds);
		classprice.setDelFlag(Classprice.DEL_FLAG_NORMAL);
		return super.findPage(page, classprice);
	}
	
	//查询班级班费
	public List<Classprice> findList(List<String> clses) {
		Classprice classprice = new Classprice();
		classprice.setClass_ids(clses);
		classprice.setDelFlag(Classprice.DEL_FLAG_NORMAL);
		return dao.findList(classprice);
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