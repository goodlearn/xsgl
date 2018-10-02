package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

public class JsonStuRecord {

	private String remarks;	// 备注
	
	private Date updateDate;	// 更新日期

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public void convertFromStuRecord(Studentrecord studentrecord) {
		this.remarks = studentrecord.getRemarks();
		this.updateDate = studentrecord.getUpdateDate();
	}
	
}
