/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 专业分类Entity
 * @author wzy
 * @version 2018-09-07
 */
public class Professioninfo extends DataEntity<Professioninfo> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String instituteId;		// 所属院系
	private Institute institute;//所属院系
	
	public Professioninfo() {
		super();
	}

	public Professioninfo(String id){
		super(id);
	}
	

	public Institute getInstitute() {
		return institute;
	}

	public void setInstitute(Institute institute) {
		this.institute = institute;
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=64, message="所属院系长度必须介于 1 和 64 之间")
	public String getInstituteId() {
		return instituteId;
	}

	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}
	
}