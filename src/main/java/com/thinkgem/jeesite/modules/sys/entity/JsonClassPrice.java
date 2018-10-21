package com.thinkgem.jeesite.modules.sys.entity;

import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.modules.sys.utils.DictUtils;

/**
* @author wzy
* @version 创建时间：2018年10月21日 下午3:52:49
* @ClassName 类名称
* @Description 类描述
*/
public class JsonClassPrice {
	private String score;		// 消费
	private String remarks;	// 备注
	//临时
	private String createYearString;
	private String createDayString;
	
	
	
	public String getScore() {
		return score;
	}



	public void setScore(String score) {
		this.score = score;
	}



	public String getRemarks() {
		return remarks;
	}



	public void setRemarks(String remarks) {
		this.remarks = remarks;
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



	public void convertFromClassPrice(Classprice classPrice) {
		this.score = classPrice.getScore();
		this.remarks = classPrice.getRemarks();
	}
}
