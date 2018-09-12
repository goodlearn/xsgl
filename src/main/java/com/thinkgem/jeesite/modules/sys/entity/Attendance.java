/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.excel.annotation.ExcelField;

/**
 * 考勤记录Entity
 * @author 王泽宇
 * @version 2018-09-12
 */
public class Attendance extends DataEntity<Attendance> {
	
	private static final long serialVersionUID = 1L;
	private String machineId;		// 考勤编号
	private String no;		// 人员编号
	private String name;		// 名称
	private Date recordDate;		// 打卡时间
	private Student student;
	
	//时间
	private Date beginRecordDate;
	private Date endRecordDate;
	
	public Attendance() {
		super();
	}

	public Attendance(String id){
		super(id);
	}
	
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Date getBeginRecordDate() {
		return beginRecordDate;
	}

	public void setBeginRecordDate(Date beginRecordDate) {
		this.beginRecordDate = beginRecordDate;
	}

	public Date getEndRecordDate() {
		return endRecordDate;
	}

	public void setEndRecordDate(Date endRecordDate) {
		this.endRecordDate = endRecordDate;
	}

	@Length(min=1, max=64, message="考勤编号长度必须介于 1 和 64 之间")
	@ExcelField(title="考勤编号", align=2, sort=1)
	public String getMachineId() {
		return machineId;
	}

	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	
	@Length(min=1, max=64, message="人员编号长度必须介于 1 和 64 之间")
	@ExcelField(title="人员编号", align=2, sort=2)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于 0 和 100 之间")
	@ExcelField(title="名称", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="打卡时间", align=2, sort=4)
	@NotNull(message="打卡时间不能为空")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	
	@Length(min=0, max=255)
	@ExcelField(title="备注", align=2, sort=5)
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}