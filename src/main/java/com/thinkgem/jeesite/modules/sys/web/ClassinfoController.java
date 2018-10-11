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
import com.thinkgem.jeesite.modules.sys.entity.Classinfo;
import com.thinkgem.jeesite.modules.sys.service.ClassinfoService;

/**
 * 班级分类Controller
 * @author wzy
 * @version 2018-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/classinfo")
public class ClassinfoController extends BaseController {

	@Autowired
	private ClassinfoService classinfoService;
	
	@ModelAttribute
	public Classinfo get(@RequestParam(required=false) String id) {
		Classinfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = classinfoService.get(id);
		}
		if (entity == null){
			entity = new Classinfo();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:classinfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(Classinfo classinfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Classinfo> page = classinfoService.findPage(new Page<Classinfo>(request, response), classinfo); 
		model.addAttribute("page", page);
		return "modules/sys/classinfoList";
	}

	@RequiresPermissions("sys:classinfo:view")
	@RequestMapping(value = "form")
	public String form(Classinfo classinfo, Model model) {
		model.addAttribute("classinfo", classinfo);
		return "modules/sys/classinfoForm";
	}

	@RequiresPermissions("sys:classinfo:edit")
	@RequestMapping(value = "save")
	public String save(Classinfo classinfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, classinfo)){
			return form(classinfo, model);
		}
		classinfoService.save(classinfo);
		addMessage(redirectAttributes, "保存成功");
		return "redirect:"+Global.getAdminPath()+"/sys/classinfo/?repage";
	}
	
	@RequiresPermissions("sys:classinfo:edit")
	@RequestMapping(value = "delete")
	public String delete(Classinfo classinfo, RedirectAttributes redirectAttributes) {
		classinfoService.delete(classinfo);
		addMessage(redirectAttributes, "删除成功");
		return "redirect:"+Global.getAdminPath()+"/sys/classinfo/?repage";
	}

}