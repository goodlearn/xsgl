/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学生奖惩记录Entity
 * @author 王泽宇
 * @version 2018-09-11
 */
public class Studentrecord extends DataEntity<Studentrecord> {
	
	private static final long serialVersionUID = 1L;
	private String recordId;		// 记录
	private String studentId;		// 学生学号
	private String name;		// 学生名称
	private Recordtype recordType;//记录类型
	private Student student;//学生
	
	public Recordtype getRecordType() {
		return recordType;
	}

	public void setRecordType(Recordtype recordType) {
		this.recordType = recordType;
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

	@Length(min=1, max=64, message="记录长度必须介于 1 和 64 之间")
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
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
	
}