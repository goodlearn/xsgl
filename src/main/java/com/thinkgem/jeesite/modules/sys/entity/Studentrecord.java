/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;


/**
 * 奖惩记录Entity
 * @author 王泽宇
 * @version 2018-09-30
 */
public class Studentrecord extends DataEntity<Studentrecord> {
	
	private static final long serialVersionUID = 1L;
	private String studentId;		// 学生记录
	private String name;		// 学生名称
	private String score;		// score
	private String scoreType;		// score_type
	private Student student;//学生
	private List<String> class_ids = null;//临时所属班级（多个）
	
	
	
	public List<String> getClass_ids() {
		return class_ids;
	}

	public void setClass_ids(List<String> class_ids) {
		this.class_ids = class_ids;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Studentrecord() {
		super();
	}

	public Studentrecord(String id){
		super(id);
	}

	@Length(min=1, max=64, message="学生记录长度必须介于 1 和 64 之间")
	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	
	@Length(min=0, max=100, message="学生名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="score长度必须介于 1 和 64 之间")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	@Length(min=1, max=1, message="score_type长度必须介于 1 和 1 之间")
	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}
	
}