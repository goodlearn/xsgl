/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 班级分类Entity
 * @author wzy
 * @version 2018-09-07
 */
public class Classinfo extends DataEntity<Classinfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String professionId;		// 所属专业
	private String teacherNo;//教师工号
	private Professioninfo professioninfo;//所属专业
	private Teacher teacher;//教师
	
	
	public String getTeacherNo() {
		return teacherNo;
	}


	public void setTeacherNo(String teacherNo) {
		this.teacherNo = teacherNo;
	}


	public Teacher getTeacher() {
		return teacher;
	}


	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}


	public Classinfo() {
		super();
	}

	
	public Professioninfo getProfessioninfo() {
		return professioninfo;
	}



	public void setProfessioninfo(Professioninfo professioninfo) {
		this.professioninfo = professioninfo;
	}



	public Classinfo(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属专业长度必须介于 1 和 64 之间")
	public String getProfessionId() {
		return professionId;
	}

	public void setProfessionId(String professionId) {
		this.professionId = professionId;
	}
	
}