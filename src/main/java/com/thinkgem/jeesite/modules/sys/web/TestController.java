package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.WxJsSkdUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.JsonStuRecord;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.service.StudentService;
import com.thinkgem.jeesite.modules.sys.service.StudentrecordService;

@Controller
@RequestMapping(value = "${adminPath}/test")
public class TestController extends BaseController {
	
	protected final String successCode = "0";//成功码
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private StudentrecordService studentrecordService;
	
	@RequestMapping(value = "/getStuRewardsDetailsAll", method = RequestMethod.GET)
	public String getStuRewardsDetailsAll(HttpServletRequest request, HttpServletResponse response, Model model) {
		String stuNo = request.getParameter("stuNo");//学生学号
		Student stu = studentService.findByNo(stuNo);
	/*
		//最近的奖惩记录
		Studentrecord query = new Studentrecord();
		//query.setStudentId(stuNo);
		query.setDelFlag(Studentrecord.DEL_FLAG_NORMAL);
		Page<Studentrecord> page = studentrecordService.findPage(new Page<Studentrecord>(request, response), query); 
		model.addAttribute("page", page);
		if(null == page.getList() || page.getList().size() == 0) {
			model.addAttribute("srsNum",0);//奖惩数据
		}else {
			model.addAttribute("srsNum",page.getList().size());//奖惩数据
			model.addAttribute("srs",page.getList());//奖惩数据
			
		}*/
		Studentrecord query = new Studentrecord();
		query.setDelFlag(Studentrecord.DEL_FLAG_NORMAL);
		Page<Studentrecord> page = studentrecordService.findPage(new Page<Studentrecord>(request, response), query); 
		
		model.addAttribute("student",stu);//学生数据
		model.addAttribute("pageNo", page.getPageNo()-1);
		model.addAttribute("pageSize", String.valueOf(page.getList().size()));
		model.addAttribute("lastPage", page.getLast());
		model.addAttribute("totalCount",page.getCount());//奖惩数据
		return "modules/wxp/stuRewardsDetailsAll";
	}
	
	@RequestMapping(value = "/stuRewardsDetailsAll", method = RequestMethod.POST)
	@ResponseBody
	public String stuRewardsDetailsAll(HttpServletRequest request, HttpServletResponse response, Model model) {
		Studentrecord query = new Studentrecord();
		query.setDelFlag(Studentrecord.DEL_FLAG_NORMAL);
		Page<Studentrecord> page = studentrecordService.findPage(new Page<Studentrecord>(request, response), query); 
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("totalCount", String.valueOf(page.getCount()));
		map.put("pageNo",String.valueOf(page.getPageNo()));
		map.put("pageSize",String.valueOf(page.getList().size()));
		List<JsonStuRecord> jsonStuRecordList = new ArrayList<JsonStuRecord>();
		List<Studentrecord> rsList = page.getList();
		for(Studentrecord sr : rsList) {
			JsonStuRecord jsr = new JsonStuRecord();
			jsr.convertFromStuRecord(sr);
			jsonStuRecordList.add(jsr);
		}
		map.put("data", JSONObject.toJSONString(jsonStuRecordList));
		String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
		logger.info(jsonResult);
		return backJsonWithCode(successCode,jsonResult);	
	}
	/**
	 * 返回数据 携带检验码和参数
	 */
	protected String backJsonWithCode(String code,String message){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("code", code);
		map.put("message", message);
		String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
		return jsonResult;
	}
}
