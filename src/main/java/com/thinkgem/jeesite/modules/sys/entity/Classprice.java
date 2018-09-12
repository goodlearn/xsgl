/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 班费记录Entity
 * @author 王泽宇
 * @version 2018-09-11
 */
public class Classprice extends DataEntity<Classprice> {
	
	private static final long serialVersionUID = 1L;
	private String classId;		// 班级编号
	private String score;		// 消费
	private Classinfo classInfo; //班级信息
	
	public Classprice() {
		super();
	}
	
	

	public Classinfo getClassInfo() {
		return classInfo;
	}



	public void setClassInfo(Classinfo classInfo) {
		this.classInfo = classInfo;
	}



	public Classprice(String id){
		super(id);
	}

	@Length(min=1, max=64, message="班级编号长度必须介于 1 和 64 之间")
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	@Length(min=1, max=100, message="消费长度必须介于 1 和 100 之间")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
}