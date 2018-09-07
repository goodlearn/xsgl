/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 学院信息Entity
 * @author wzy
 * @version 2018-09-07
 */
public class Institute extends DataEntity<Institute> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	
	public Institute() {
		super();
	}

	public Institute(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}