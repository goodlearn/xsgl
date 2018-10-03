package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

public class JsonStuRecord {

	private String remarks;	// 备注
	
	private Date updateDate;	// 更新日期
	
	private String score;		// score
	private String scoreType;		// score_type
	

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getScoreType() {
		return scoreType;
	}

	public void setScoreType(String scoreType) {
		this.scoreType = scoreType;
	}

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
		this.score = studentrecord.getScore();
		this.remarks = studentrecord.getRemarks();
		this.updateDate = studentrecord.getUpdateDate();
		String state = DictUtils.getDictLabel(studentrecord.getScoreType(),"scoreType","错误信息");
		this.scoreType = state;

	}
	
}
