/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 微信信息Entity
 * @author wzy
 * @version 2018-10-01
 */
public class SysWxInfo extends DataEntity<SysWxInfo> {
	
	private static final long serialVersionUID = 1L;
	private String openId;		// 微信关联号
	private String no;		// 学号
	private Date tieEndDate;		// 绑定结束时间
	private String nickname;		// nickname
	private String sex;		// 性别
	private String headimgurl;		// 头像
	private String tieType;//绑定类型 0 学生 1 老师
	
	
	public String getTieType() {
		return tieType;
	}

	public void setTieType(String tieType) {
		this.tieType = tieType;
	}

	public SysWxInfo() {
		super();
	}

	public SysWxInfo(String id){
		super(id);
	}

	@Length(min=0, max=100, message="微信关联号长度必须介于 0 和 100 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=100, message="学号长度必须介于 0 和 100 之间")
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@NotNull(message="绑定结束时间不能为空")
	public Date getTieEndDate() {
		return tieEndDate;
	}

	public void setTieEndDate(Date tieEndDate) {
		this.tieEndDate = tieEndDate;
	}
	
	@Length(min=0, max=200, message="nickname长度必须介于 0 和 200 之间")
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Length(min=0, max=100, message="性别长度必须介于 0 和 100 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=200, message="头像长度必须介于 0 和 200 之间")
	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}
	
}