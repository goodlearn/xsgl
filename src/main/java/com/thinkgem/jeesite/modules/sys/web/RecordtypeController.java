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
import com.thinkgem.jeesite.modules.sys.entity.Recordtype;
import com.thinkgem.jeesite.modules.sys.service.RecordtypeService;

/**
 * 记录类型Controller
 * @author 王泽宇
 * @version 2018-09-11
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/recordtype")
public class RecordtypeController extends BaseController {

	@Autowired
	private RecordtypeService recordtypeService;
	
	@ModelAttribute
	public Recordtype get(@RequestParam(required=false) String id) {
		Recordtype entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = recordtypeService.get(id);
		}
		if (entity == null){
			entity = new Recordtype();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:recordtype:view")
	@RequestMapping(value = {"list", ""})
	public String list(Recordtype recordtype, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Recordtype> page = recordtypeService.findPage(new Page<Recordtype>(request, response), recordtype); 
		model.addAttribute("page", page);
		return "modules/sys/recordtypeList";
	}

	@RequiresPermissions("sys:recordtype:view")
	@RequestMapping(value = "form")
	public String form(Recordtype recordtype, Model model) {
		model.addAttribute("recordtype", recordtype);
		return "modules/sys/recordtypeForm";
	}

	@RequiresPermissions("sys:recordtype:edit")
	@RequestMapping(value = "save")
	public String save(Recordtype recordtype, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, recordtype)){
			return form(recordtype, model);
		}
		recordtypeService.save(recordtype);
		addMessage(redirectAttributes, "保存记录类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/recordtype/?repage";
	}
	
	@RequiresPermissions("sys:recordtype:edit")
	@RequestMapping(value = "delete")
	public String delete(Recordtype recordtype, RedirectAttributes redirectAttributes) {
		recordtypeService.delete(recordtype);
		addMessage(redirectAttributes, "删除记录类型成功");
		return "redirect:"+Global.getAdminPath()+"/sys/recordtype/?repage";
	}

}