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
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.entity.Institute;
import com.thinkgem.jeesite.modules.sys.service.InstituteService;
import com.thinkgem.jeesite.modules.sys.utils.BaseInfoUtils;

/**
 * 学院信息Controller
 * @author wzy
 * @version 2018-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/institute")
public class InstituteController extends BaseController {

	@Autowired
	private InstituteService instituteService;
	
	@ModelAttribute
	public Institute get(@RequestParam(required=false) String id) {
		Institute entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = instituteService.get(id);
		}
		if (entity == null){
			entity = new Institute();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:institute:view")
	@RequestMapping(value = {"list", ""})
	public String list(Institute institute, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Institute> page = instituteService.findPage(new Page<Institute>(request, response), institute); 
		model.addAttribute("page", page);
		return "modules/sys/instituteList";
	}

	@RequiresPermissions("sys:institute:view")
	@RequestMapping(value = "form")
	public String form(Institute institute, Model model) {
		model.addAttribute("institute", institute);
		return "modules/sys/instituteForm";
	}

	@RequiresPermissions("sys:institute:edit")
	@RequestMapping(value = "save")
	public String save(Institute institute, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, institute)){
			return form(institute, model);
		}
		instituteService.save(institute);
		addMessage(redirectAttributes, "保存学院信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/institute/?repage";
	}
	
	@RequiresPermissions("sys:institute:edit")
	@RequestMapping(value = "delete")
	public String delete(Institute institute, RedirectAttributes redirectAttributes) {
		instituteService.delete(institute);
		addMessage(redirectAttributes, "删除学院信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/institute/?repage";
	}

}