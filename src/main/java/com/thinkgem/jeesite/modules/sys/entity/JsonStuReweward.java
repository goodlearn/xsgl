package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;

/**
* @author wzy
* @version 创建时间：2018年10月26日 下午4:54:01
* @ClassName 类名称
* @Description 类描述
*/
public class JsonStuReweward {
	private String name;		// 学生名称
	private String score;		// score
	private String scoreType;		// score_type
	protected String remarks;	// 备注
	protected Date createDate;	// 创建日期
	protected String id;
	//临时
	private String createYearString;
	private String createDayString;
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateYearString() {
		return createYearString;
	}
	public void setCreateYearString(String createYearString) {
		this.createYearString = createYearString;
	}
	public String getCreateDayString() {
		return createDayString;
	}
	public void setCreateDayString(String createDayString) {
		this.createDayString = createDayString;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
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
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public void convertData(Studentrecord studentrecord) {
		this.score = studentrecord.getScore();
		this.remarks = studentrecord.getRemarks();
		this.name = studentrecord.getName();
		this.scoreType = studentrecord.getScoreType();
		this.createDate = studentrecord.getCreateDate();
		this.id = studentrecord.getId();
	}

}
