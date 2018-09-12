/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 记录类型Entity
 * @author 王泽宇
 * @version 2018-09-11
 */
public class Recordtype extends DataEntity<Recordtype> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String score;		// 对应分值
	private String typekey;	//键值
	
	public Recordtype() {
		super();
	}

	public Recordtype(String id){
		super(id);
	}
	
	
	@Length(min=0, max=100, message="长度必须介于 0 和 100 之间")
	public String getTypekey() {
		return typekey;
	}

	public void setTypekey(String typekey) {
		this.typekey = typekey;
	}

	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=100, message="对应分值长度必须介于 1 和 100 之间")
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
}