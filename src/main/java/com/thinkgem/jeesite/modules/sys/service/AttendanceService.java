/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Attendance;
import com.thinkgem.jeesite.modules.sys.dao.AttendanceDao;

/**
 * 考勤记录Service
 * @author 王泽宇
 * @version 2018-09-12
 */
@Service
@Transactional(readOnly = true)
public class AttendanceService extends CrudService<AttendanceDao, Attendance> {

	public Attendance get(String id) {
		return super.get(id);
	}
	
	public List<Attendance> findList(Attendance attendance) {
		return super.findList(attendance);
	}
	
	public Page<Attendance> findPage(Page<Attendance> page, Attendance attendance) {
		return super.findPage(page, attendance);
	}
	
	@Transactional(readOnly = false)
	public void save(Attendance attendance) {
		super.save(attendance);
	}
	
	@Transactional(readOnly = false)
	public void delete(Attendance attendance) {
		super.delete(attendance);
	}
	
}