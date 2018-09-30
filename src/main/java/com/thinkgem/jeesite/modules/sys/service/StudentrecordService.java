/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.dao.StudentDao;
import com.thinkgem.jeesite.modules.sys.dao.StudentrecordDao;

/**
 * 奖惩记录Service
 * @author 王泽宇
 * @version 2018-09-30
 */
@Service
@Transactional(readOnly = true)
public class StudentrecordService extends CrudService<StudentrecordDao, Studentrecord> {
	
	@Autowired
	private StudentDao studentDao;
	
	public Studentrecord get(String id) {
		return super.get(id);
	}
	
	public List<Studentrecord> findList(Studentrecord studentrecord) {
		return super.findList(studentrecord);
	}
	
	public Page<Studentrecord> findPage(Page<Studentrecord> page, Studentrecord studentrecord) {
		return super.findPage(page, studentrecord);
	}
	
	@Transactional(readOnly = false)
	public void save(Studentrecord studentrecord) {
		
		//记录是扣分还是加分
		String scoreType = studentrecord.getScoreType();
		String add = DictUtils.getDictValue("加分", "scoreType", "1");
		if(add.equals(scoreType)) {
			saveAdd(studentrecord,true);
		}else {
			saveAdd(studentrecord,false);
		}
		super.save(studentrecord);
	}
	
	@Transactional(readOnly = false)
	public void saveAdd(Studentrecord studentrecord,boolean isAdd) {
		//找到学生学号
 		String student_id = studentrecord.getStudentId();
		Student queryS = new Student();
		//查询是否有学生
		queryS.setNo(student_id);//学号
		queryS.setDelFlag(Student.DEL_FLAG_NORMAL);
		Student stu = studentDao.findByNo(queryS);
		if(null == stu) {
			return;//学生不存在
		}
		Double addScore = Double.valueOf(studentrecord.getScore());
		//分数
		Double currentScore = Double.valueOf(stu.getScore());
		if(isAdd) {
			currentScore+=addScore;
		}else {
			currentScore-=addScore;
		}
		
		stu.setScore(currentScore.toString());
		studentDao.update(stu);
	}
	

	
	@Transactional(readOnly = false)
	public void delete(Studentrecord studentrecord) {
		super.delete(studentrecord);
	}
	
}