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
import com.thinkgem.jeesite.modules.sys.entity.Classprice;
import com.thinkgem.jeesite.modules.sys.service.ClasspriceService;

/**
 * 班费记录Controller
 * @author 王泽宇
 * @version 2018-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/classprice")
public class ClasspriceController extends BaseController {

	@Autowired
	private ClasspriceService classpriceService;
	
	@ModelAttribute
	public Classprice get(@RequestParam(required=false) String id) {
		Classprice entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = classpriceService.get(id);
		}
		if (entity == null){
			entity = new Classprice();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:classprice:view")
	@RequestMapping(value = {"list", ""})
	public String list(Classprice classprice, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Classprice> page = classpriceService.findPage(new Page<Classprice>(request, response), classprice); 
		model.addAttribute("page", page);
		model.addAttribute("clsList", classpriceService.findClassInfo());

		return "modules/sys/classpriceList";
	}

	@RequiresPermissions("sys:classprice:view")
	@RequestMapping(value = "form")
	public String form(Classprice classprice, Model model) {
		model.addAttribute("classprice", classprice);
		model.addAttribute("clsList", classpriceService.findClassInfo());

		return "modules/sys/classpriceForm";
	}

	@RequiresPermissions("sys:classprice:edit")
	@RequestMapping(value = "save")
	public String save(Classprice classprice, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, classprice)){
			return form(classprice, model);
		}
		classpriceService.save(classprice);
		addMessage(redirectAttributes, "保存班费记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/classprice/?repage";
	}
	
	@RequiresPermissions("sys:classprice:edit")
	@RequestMapping(value = "delete")
	public String delete(Classprice classprice, RedirectAttributes redirectAttributes) {
		classpriceService.delete(classprice);
		addMessage(redirectAttributes, "删除班费记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/classprice/?repage";
	}

}