/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学生分数Entity
 * @author wzy
 * @version 2018-09-11
 */
public class Studentscore extends DataEntity<Studentscore> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// 学生学号ID
	private String score;		// 学生分数
	private Student student;
	
	public Studentscore() {
		super();
	}

	public Studentscore(String id){
		super(id);
	}
	
	

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@Length(min=1, max=100, message="学生分数长度必须介于 1 和 100 之间")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
}