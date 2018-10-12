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
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.ClassinfoDao;
import com.thinkgem.jeesite.modules.sys.dao.StudentDao;

/**
 * 学生信息Service
 * @author wzy
 * @version 2018-09-07
 */
@Service
@Transactional(readOnly = true)
public class StudentService extends CrudService<StudentDao, Student> {
	
	@Autowired
	private ClassinfoDao classinfoDao;

	public Student get(String id) {
		return super.get(id);
	}
	
	public List<Student> findList(Student student) {
		return super.findList(student);
	}
	
	public List<Student> findListRank(String classId) {
		Student student = new Student();
		student.setClassId(classId);
		student.setDelFlag(Student.DEL_FLAG_NORMAL);
		return dao.findListRank(student);
	}
	
	//检查身份证是否存在
	private boolean findIdCard(String idCard) {
		if(null == idCard || "".equals(idCard)) {
			return false;
		}
		Student student = new Student();
		student.setIdcard(idCard);
		student.setDelFlag(Student.DEL_FLAG_NORMAL);
		if(null != dao.findByIdCard(student)) {
			return false;
		}
		return true;
	}
	
	//检查学号
	private boolean findByNoForBoolean(String no) {
		if(null == no || "".equals(no)) {
			return false;
		}
		if(null != findByNo(no)) {
			return false;
		}
		return true;
	}
	
	//检查学号和密码
	public boolean findByNoAndIdCard(String idCard,String no) {
		if(findIdCard(idCard) && findByNoForBoolean(no)) {
			return true;
		}
		return false;
	}
	
	public Page<Student> findPage(Page<Student> page, Student student) {
		
		/**
		 * 如果是超管可以查看所有 不是超管只能查看自己班级的人
		 */
		User user = UserUtils.getUser();
		if(user.isAdmin()) {
			return super.findPage(page, student);
		}
		//不是超管
		String no = user.getNo();
		Classinfo queryci = new Classinfo();
		queryci.setTeacherNo(no);
		List<Classinfo> cls = classinfoDao.findList(queryci);
		//没有班级信息
		if(null == cls || cls.size() == 0) {
			return new Page<Student>();
		}
		List<String> clsIds = new ArrayList<String>();
		for(Classinfo clsi : cls) {
			clsIds.add(clsi.getId());
		}
		student.setClass_ids(clsIds);
		return super.findPage(page, student);
	}
	
	/**
	 * 依据学号查询
	 * @param no
	 * @return
	 */
	public Student findByNo(String no) {
		Student student = new Student();
		student.setNo(no);
		student.setDelFlag(Student.DEL_FLAG_NORMAL);
		return dao.findByNo(student);
	}
	
	@Transactional(readOnly = false)
	public void save(Student student) {
		super.save(student);
	}
	
	@Transactional(readOnly = false)
	public void delete(Student student) {
		super.delete(student);
	}
	
}