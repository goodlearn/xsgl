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
import com.thinkgem.jeesite.modules.sys.entity.Studentscore;
import com.thinkgem.jeesite.modules.sys.service.StudentscoreService;

/**
 * 学生分数Controller
 * @author wzy
 * @version 2018-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/studentscore")
public class StudentscoreController extends BaseController {

	@Autowired
	private StudentscoreService studentscoreService;
	
	@ModelAttribute
	public Studentscore get(@RequestParam(required=false) String id) {
		Studentscore entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentscoreService.get(id);
		}
		if (entity == null){
			entity = new Studentscore();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:studentscore:view")
	@RequestMapping(value = {"list", ""})
	public String list(Studentscore studentscore, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Studentscore> page = studentscoreService.findPage(new Page<Studentscore>(request, response), studentscore); 
		model.addAttribute("page", page);
		return "modules/sys/studentscoreList";
	}

	@RequiresPermissions("sys:studentscore:view")
	@RequestMapping(value = "form")
	public String form(Studentscore studentscore, Model model) {
		model.addAttribute("studentscore", studentscore);
		return "modules/sys/studentscoreForm";
	}

	@RequiresPermissions("sys:studentscore:edit")
	@RequestMapping(value = "save")
	public String save(Studentscore studentscore, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, studentscore)){
			return form(studentscore, model);
		}
		studentscoreService.save(studentscore);
		addMessage(redirectAttributes, "保存学生分数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/studentscore/?repage";
	}
	
	@RequiresPermissions("sys:studentscore:edit")
	@RequestMapping(value = "delete")
	public String delete(Studentscore studentscore, RedirectAttributes redirectAttributes) {
		studentscoreService.delete(studentscore);
		addMessage(redirectAttributes, "删除学生分数成功");
		return "redirect:"+Global.getAdminPath()+"/sys/studentscore/?repage";
	}

}