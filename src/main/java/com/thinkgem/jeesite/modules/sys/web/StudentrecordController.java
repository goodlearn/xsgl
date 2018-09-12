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
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.service.StudentrecordService;

/**
 * 学生奖惩记录Controller
 * @author 王泽宇
 * @version 2018-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/studentrecord")
public class StudentrecordController extends BaseController {

	@Autowired
	private StudentrecordService studentrecordService;
	
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

	@RequiresPermissions("sys:studentrecord:edit")
	@RequestMapping(value = "save")
	public String save(Studentrecord studentrecord, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentrecord)){
			return form(studentrecord, model);
		}
		studentrecordService.save(studentrecord);
		addMessage(redirectAttributes, "保存学生奖惩记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/studentrecord/?repage";
	}
	
	@RequiresPermissions("sys:studentrecord:edit")
	@RequestMapping(value = "delete")
	public String delete(Studentrecord studentrecord, RedirectAttributes redirectAttributes) {
		studentrecordService.delete(studentrecord);
		addMessage(redirectAttributes, "删除学生奖惩记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/studentrecord/?repage";
	}

}