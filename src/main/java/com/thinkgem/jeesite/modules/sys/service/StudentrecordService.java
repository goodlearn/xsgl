/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Recordtype;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.entity.Studentscore;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.RecordtypeDao;
import com.thinkgem.jeesite.modules.sys.dao.StudentrecordDao;
import com.thinkgem.jeesite.modules.sys.dao.StudentscoreDao;

/**
 * 学生奖惩记录Service
 * @author 王泽宇
 * @version 2018-09-11
 */
@Service
@Transactional(readOnly = true)
public class StudentrecordService extends CrudService<StudentrecordDao, Studentrecord> {
	
	@Autowired
	private StudentscoreDao studentscoreDao;
	@Autowired
	private RecordtypeDao recordtypeDao;

	public Studentrecord get(String id) {
		return super.get(id);
	}
	
	public List<Studentrecord> findList(Studentrecord studentrecord) {
		return super.findList(studentrecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(Studentrecord studentrecord) {
		super.delete(studentrecord);
	}
	
	public Page<Studentrecord> findPage(Page<Studentrecord> page, Studentrecord studentrecord) {
		return super.findPage(page, studentrecord);
	}
	
	@Transactional(readOnly = false)
	public void save(Studentrecord studentrecord) {
		/**
		 * 如果保存的话需要计算学生的分值
		 */
		if(studentrecord.getIsNewRecord()) {
			studentrecord.setRecordType(recordtypeDao.get(studentrecord.getRecordId()));
			saveStudentScore(studentrecord);
		}
		super.save(studentrecord);
	}
	
	/**
	 * 保存学生分值
	 */
	@Transactional(readOnly = false)
	private void saveStudentScore(Studentrecord studentrecord) {
		Recordtype recordtype = studentrecord.getRecordType();//获取记录类型
		int count = 0;
		/**
		 * 查询学生该记录获得次数（次数不同获得分值不同）
		 * 1、旷课次数为累加次数：旷课分数*旷课次数
		 * 2、早操旷操为累加次数    旷操分数*旷操次数
		 */
		if(recordtype.getTypekey().equals(String.valueOf(++count))) {
			kuangkeProcess_1(studentrecord);
		}else {
			kuangCaoProcess_2(studentrecord);
		}
	}
	
	//查询已有学生分数
	private Studentscore findSsByStudentId(String studentId){
		//已有分数
		Studentscore querySs = new Studentscore();
		querySs.setStudentId(studentId);
		return studentscoreDao.get(querySs);
	}
	
	//旷课处理
	@Transactional(readOnly = false)
	private void kuangkeProcess_1(Studentrecord studentrecord) {
		
		Studentscore querySs = findSsByStudentId(studentrecord.getStudentId());
		double oldScore = 0;
		if(null == querySs) {
			oldScore = Global.DEAFAULT_SCORE;//如果没有查到原先分数，那么原先分数为默认值
		}else {
			oldScore = Double.valueOf(querySs.getScore());
		}
		Recordtype recordtype = studentrecord.getRecordType();//获取记录类型
		String score = recordtype.getScore();//分数
		int kangkeCount = dao.findCountByType(studentrecord);
		kangkeCount++;
		double convertScore = Double.valueOf(score);
		Studentscore ss = new Studentscore();
		ss.preInsert();
		ss.setStudentId(studentrecord.getStudentId());
		ss.setScore(String.valueOf(oldScore - (kangkeCount * convertScore)));
		ss.setRemarks(recordtype.getRemarks());
		studentscoreDao.insert(ss);
	}
	
	//旷课处理
	@Transactional(readOnly = false)
	private void kuangCaoProcess_2(Studentrecord studentrecord) {
		Studentscore querySs = findSsByStudentId(studentrecord.getStudentId());
		double oldScore = 0;
		if(null == querySs) {
			oldScore = Global.DEAFAULT_SCORE;//如果没有查到原先分数，那么原先分数为默认值
		}else {
			oldScore = Double.valueOf(querySs.getScore());
		}
		Recordtype recordtype = studentrecord.getRecordType();//获取记录类型
		String score = recordtype.getScore();//分数
		int kangkeCount = dao.findCountByType(studentrecord);
		kangkeCount++;
		double convertScore = Double.valueOf(score);
		Studentscore ss = new Studentscore();
		ss.preInsert();
		ss.setStudentId(studentrecord.getStudentId());
		ss.setScore(String.valueOf(oldScore - (kangkeCount * convertScore)));
		ss.setRemarks(recordtype.getRemarks());
		studentscoreDao.insert(ss);
	}

	
}