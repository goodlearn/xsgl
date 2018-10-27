/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.IdGen;
import com.thinkgem.jeesite.modules.sys.entity.Classinfo;
import com.thinkgem.jeesite.modules.sys.entity.Classprice;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.ClassinfoDao;
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
	@Autowired
	private ClassinfoDao classinfoDao;

	
	public Studentrecord get(String id) {
		return super.get(id);
	}
	
	public List<Studentrecord> findList(Studentrecord studentrecord) {
		return super.findList(studentrecord);
	}
	
	/**
	 * 总数据量
	 */
	public int findCount() {
		Studentrecord studentrecord = new Studentrecord();
		studentrecord.setDelFlag(Studentrecord.DEL_FLAG_NORMAL);
		return dao.findTotalCount(studentrecord);
	}
	
	public List<Studentrecord> findListLimit5(String stuNo){
		Studentrecord studentrecord = new Studentrecord();
		studentrecord.setStudentId(stuNo);
		studentrecord.setDelFlag(Studentrecord.DEL_FLAG_NORMAL);
		return dao.findListLimit(studentrecord);
	}
	
	public Page<Studentrecord> findPageByClassId(Page<Studentrecord> page,Studentrecord studentrecord) {
		return super.findPage(page, studentrecord);
	}
	
	public Page<Studentrecord> findPage(Page<Studentrecord> page, Studentrecord studentrecord) {
		/**
		 * 如果是超管可以查看所有 不是超管只能查看自己班级的人
		 */
		User user = UserUtils.getUser();
		if(user.isAdmin()) {
			return super.findPage(page, studentrecord);
		}
		//不是超管
		String no = user.getNo();
		Classinfo queryci = new Classinfo();
		queryci.setTeacherNo(no);
		List<Classinfo> cls = classinfoDao.findList(queryci);
		//没有班级信息
		if(null == cls || cls.size() == 0) {
			return new Page<Studentrecord>();
		}
		List<String> clsIds = new ArrayList<String>();
		for(Classinfo clsi : cls) {
			clsIds.add(clsi.getId());
		}
		studentrecord.setClass_ids(clsIds);
		return super.findPage(page, studentrecord);
	}
	
	@Transactional(readOnly = false)
	public Double wxSave(Studentrecord studentrecord) {
		Double ret = null;
		//记录是扣分还是加分
		String scoreType = studentrecord.getScoreType();
		String add = DictUtils.getDictValue("加分", "scoreType", "1");
		if(add.equals(scoreType)) {
			ret = saveAdd(studentrecord,true);
		}else {
			ret = saveAdd(studentrecord,false);
		}
		studentrecord.setId(IdGen.uuid());
		User user = UserUtils.get(Global.DEFAULT_ID_SYS_MANAGER);
		if (StringUtils.isNotBlank(user.getId())){
			studentrecord.setUpdateBy(user);
			studentrecord.setCreateBy(user);
		}
		studentrecord.setUpdateDate(new Date());
		studentrecord.setCreateDate(studentrecord.getUpdateDate());
		dao.insert(studentrecord);
		return ret;
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
	public Double saveAdd(Studentrecord studentrecord,boolean isAdd) {
		//找到学生学号
 		String student_id = studentrecord.getStudentId();
		Student queryS = new Student();
		//查询是否有学生
		queryS.setNo(student_id);//学号
		queryS.setDelFlag(Student.DEL_FLAG_NORMAL);
		Student stu = studentDao.findByNo(queryS);
		if(null == stu) {
			return null;//学生不存在
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
		return currentScore;
	}
	
	/**
	 * 删除记录需要将分数返回给学生
	 * @param studentrecord
	 */
	@Transactional(readOnly = false)
	public void deleteRecord(Studentrecord studentrecord) {
		//删除记录需要将分数返回给学生
		String scoreType = studentrecord.getScoreType();
		String add = DictUtils.getDictValue("加分", "scoreType", "1");
		if(add.equals(scoreType)) {
			saveAdd(studentrecord,false);
		}else {
			 saveAdd(studentrecord,true);
		}
		
		super.delete(studentrecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(Studentrecord studentrecord) {
		super.delete(studentrecord);
	}
	
}