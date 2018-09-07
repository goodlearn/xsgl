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
import com.thinkgem.jeesite.modules.sys.entity.Professioninfo;
import com.thinkgem.jeesite.modules.sys.service.ProfessioninfoService;

/**
 * 专业分类Controller
 * @author wzy
 * @version 2018-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/professioninfo")
public class ProfessioninfoController extends BaseController {

	@Autowired
	private ProfessioninfoService professioninfoService;
	
	@ModelAttribute
	public Professioninfo get(@RequestParam(required=false) String id) {
		Professioninfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = professioninfoService.get(id);
		}
		if (entity == null){
			entity = new Professioninfo();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:professioninfo:view")
	@RequestMapping(value = {"list", ""})
	public String list(Professioninfo professioninfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Professioninfo> page = professioninfoService.findPage(new Page<Professioninfo>(request, response), professioninfo); 
		model.addAttribute("page", page);
		return "modules/sys/professioninfoList";
	}

	@RequiresPermissions("sys:professioninfo:view")
	@RequestMapping(value = "form")
	public String form(Professioninfo professioninfo, Model model) {
		model.addAttribute("professioninfo", professioninfo);
		return "modules/sys/professioninfoForm";
	}

	@RequiresPermissions("sys:professioninfo:edit")
	@RequestMapping(value = "save")
	public String save(Professioninfo professioninfo, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, professioninfo)){
			return form(professioninfo, model);
		}
		professioninfoService.save(professioninfo);
		addMessage(redirectAttributes, "保存wzy成功");
		return "redirect:"+Global.getAdminPath()+"/sys/professioninfo/?repage";
	}
	
	@RequiresPermissions("sys:professioninfo:edit")
	@RequestMapping(value = "delete")
	public String delete(Professioninfo professioninfo, RedirectAttributes redirectAttributes) {
		professioninfoService.delete(professioninfo);
		addMessage(redirectAttributes, "删除wzy成功");
		return "redirect:"+Global.getAdminPath()+"/sys/professioninfo/?repage";
	}

}