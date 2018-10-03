/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.service.StudentService;
import com.thinkgem.jeesite.modules.sys.service.StudentrecordService;
import com.thinkgem.jeesite.modules.sys.service.SysWxInfoService;
import com.thinkgem.jeesite.modules.sys.service.WxService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 奖惩记录Controller
 * @author 王泽宇
 * @version 2018-09-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/studentrecord")
public class StudentrecordController extends BaseController {

	@Autowired
	private StudentrecordService studentrecordService;
	

	@Autowired
	private StudentService studentService;
	
	@Autowired
	protected SysWxInfoService sysWxInfoService;
	
	@Autowired
	private WxService wxService;
	
	@ModelAttribute
	public Studentrecord get(@RequestParam(required=false) String id) {
		Studentrecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentrecordService.get(id);
		}
		if (entity == null){
			entity = new Studentrecord();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:studentrecord:view")
	@RequestMapping(value = {"list", ""})
	public String list(Studentrecord studentrecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Studentrecord> page = studentrecordService.findPage(new Page<Studentrecord>(request, response), studentrecord); 
		model.addAttribute("page", page);
		return "modules/sys/studentrecordList";
	}
	
	@RequiresPermissions("sys:studentrecord:view")
	@RequestMapping(value = "form")
	public String form(Studentrecord studentrecord, Model model) {
		model.addAttribute("studentrecord", studentrecord);
		return "modules/sys/studentrecordForm";
	}

	@RequiresPermissions("sys:studentrecord:view")
	@RequestMapping(value = "addform")
	public String addform(Studentrecord studentrecord, Model model) {
		model.addAttribute("studentrecord", studentrecord);
		return "modules/sys/studentrecordAddForm";
	}
	
	@RequiresPermissions("sys:studentrecord:edit")
	@RequestMapping(value = "saveAdd")
	public String saveAdd(Studentrecord studentrecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentrecord)){
			return form(studentrecord, model);
		}
		studentrecordService.save(studentrecord);
		SysWxInfo toUserWxInfo = sysWxInfoService.findWxInfoByNo(studentrecord.getStudentId());
		String add = DictUtils.getDictValue("加分", "scoreType", "1");
		String type = null;
		if(add.equals(studentrecord.getScoreType())) {
			 type = "德育分值加分";
		}else {
			 type = "德育分值扣分";
		}
		wxService.sendMessageScore(toUserWxInfo.getOpenId(), UserUtils.getUser().getName(), getCurrentScore(studentrecord).toString(), type, studentrecord.getRemarks());

		addMessage(redirectAttributes, "保存奖惩记录成功");
		return "redirect:" + adminPath + "/sys/student/list?repage";
	}
	

	@RequiresPermissions("sys:studentrecord:edit")
	@RequestMapping(value = "save")
	public String save(Studentrecord studentrecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentrecord)){
			return form(studentrecord, model);
		}
		studentrecordService.save(studentrecord);
		SysWxInfo toUserWxInfo = sysWxInfoService.findWxInfoByNo(studentrecord.getStudentId());
		String add = DictUtils.getDictValue("加分", "scoreType", "1");
		String type = null;
		if(add.equals(studentrecord.getScoreType())) {
			 type = "德育分值加分";
		}else {
			 type = "德育分值扣分";
		}
		wxService.sendMessageScore(toUserWxInfo.getOpenId(), UserUtils.getUser().getName(), getCurrentScore(studentrecord).toString(), type, studentrecord.getRemarks());
		addMessage(redirectAttributes, "保存奖惩记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/studentrecord/?repage";
	}
	
	//得到当前分值
	private Double getCurrentScore(Studentrecord studentrecord) {
		//找到学生学号
 		String student_id = studentrecord.getStudentId();
		Student queryS = new Student();
		//查询是否有学生
		queryS.setNo(student_id);//学号
		queryS.setDelFlag(Student.DEL_FLAG_NORMAL);
		Student stu = studentService.findByNo(student_id);
		if(null == stu) {
			return null;//学生不存在
		}
		
		String scoreType = studentrecord.getScoreType();
		Double addScore = Double.valueOf(studentrecord.getScore());
		//分数
		Double currentScore = Double.valueOf(stu.getScore());
		String add = DictUtils.getDictValue("加分", "scoreType", "1");
		if(add.equals(scoreType)) {
			currentScore+=addScore;
		}else {
			currentScore-=addScore;
		}
		return currentScore;
	}
	
	@RequiresPermissions("sys:studentrecord:edit")
	@RequestMapping(value = "delete")
	public String delete(Studentrecord studentrecord, RedirectAttributes redirectAttributes) {
		studentrecordService.delete(studentrecord);
		addMessage(redirectAttributes, "删除奖惩记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/studentrecord/?repage";
	}

}