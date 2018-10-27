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
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.Teacher;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.utils.BaseInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import com.thinkgem.jeesite.modules.sys.dao.ClassinfoDao;
import com.thinkgem.jeesite.modules.sys.dao.StudentDao;
import com.thinkgem.jeesite.modules.sys.dao.SysWxInfoDao;
import com.thinkgem.jeesite.modules.sys.dao.TeacherDao;

/**
 * 学生信息Service
 * @author wzy
 * @version 2018-09-07
 */
@Service
@Transactional(readOnly = true)
public class StudentService extends CrudService<StudentDao, Student> {
	
	@Autowired
	private TeacherDao teacherDao;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private ClassinfoDao classinfoDao;
	@Autowired
	private SysWxInfoDao sysWxInfoDao;

	public Student get(String id) {
		return super.get(id);
	}
	
	public List<Student> findList(Student student) {
		return super.findList(student);
	}
	
	/**
	 * 获取班级学生信息
	 * @param classId
	 * @return
	 */
	public List<Student> findStudents(String classId) {
		Student student = new Student();
		student.setClassId(classId);
		student.setDelFlag(Student.DEL_FLAG_NORMAL);
		return dao.findList(student);
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
	
	//根据openId获取班级信息
	public List<Classinfo> findClassesByOpenId(String openId) {
		List<Classinfo> rets = null;
		SysWxInfo query = new SysWxInfo();
		query.setOpenId(openId);
		query.setDelFlag(SysWxInfo.DEL_FLAG_NORMAL);
		SysWxInfo swi =  sysWxInfoDao.findByOpenId(query);
		if(null == swi) {
			return null;
		}
		
		//获取学号
		String type = swi.getTieType();
		String no = swi.getNo();
		if(type.equals("0")) {
			//学生
			Student queryStu = new Student();
			queryStu.setNo(no);
			queryStu.setDelFlag(Student.DEL_FLAG_NORMAL);
			Student resultStu = studentDao.findByNo(queryStu);
			
			if(null == resultStu) {
				return null;
			}
			
			Classinfo classinfo = new Classinfo();
			classinfo.setId(resultStu.getClassId());
			rets = new ArrayList<Classinfo>();
			rets.add(classinfo);
			return rets;
			
		}else {
			//老师
			Teacher queryTea = new Teacher();
			queryTea.setNo(no);
			queryTea.setDelFlag(Teacher.DEL_FLAG_NORMAL);
			Teacher resultStu = teacherDao.findByNo(queryTea);
			
			if(null == resultStu) {
				return null;
			}
			Classinfo queryList = new Classinfo();
			queryList.setTeacherNo(resultStu.getNo());
			List<Classinfo> clsList = classinfoDao.findList(queryList);//多个班级
			rets = new ArrayList<Classinfo>();
			rets.addAll(clsList);
			return rets;
		}
	}
	
	
	/**
	 * 设置微信头像
	 */
	public void setHeadUrl(Student student) {
		//查找微信信息
		String no = student.getNo();
		SysWxInfo query = new SysWxInfo();
		query.setNo(no);
		query.setDelFlag(SysWxInfo.DEL_FLAG_NORMAL);
		SysWxInfo sysWxInfo = sysWxInfoDao.findByNo(query);
		if(null == sysWxInfo) {
			return;
		}
		String headUrl = sysWxInfo.getHeadimgurl();
		if(null!=headUrl && !headUrl.equals("")) {
			student.setHeadImgWxUrl(headUrl);
			student.setHeadImgWx(true);
		}
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