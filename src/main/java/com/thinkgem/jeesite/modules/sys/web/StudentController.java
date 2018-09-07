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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.StudentService;

/**
 * 学生信息Controller
 * @author wzy
 * @version 2018-09-07
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/student")
public class StudentController extends BaseController {

	@Autowired
	private StudentService studentService;
	
	@ModelAttribute
	public Student get(@RequestParam(required=false) String id) {
		Student entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = studentService.get(id);
		}
		if (entity == null){
			entity = new Student();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:student:view")
	@RequestMapping(value = {"list", ""})
	public String list(Student student, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Student> page = studentService.findPage(new Page<Student>(request, response), student); 
		model.addAttribute("page", page);
		return "modules/sys/studentList";
	}

	@RequiresPermissions("sys:student:view")
	@RequestMapping(value = "form")
	public String form(Student student, Model model) {
		model.addAttribute("student", student);
		return "modules/sys/studentForm";
	}

	@RequiresPermissions("sys:student:edit")
	@RequestMapping(value = "save")
	public String save(Student student, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, student)){
			return form(student, model);
		}
		studentService.save(student);
		addMessage(redirectAttributes, "保存学生信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/student/?repage";
	}
	
	@RequiresPermissions("sys:student:edit")
	@RequestMapping(value = "delete")
	public String delete(Student student, RedirectAttributes redirectAttributes) {
		studentService.delete(student);
		addMessage(redirectAttributes, "删除学生信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/student/?repage";
	}
	
	
	@RequiresPermissions("sys:student:edit")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Student student, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Student> page = studentService.findPage(new Page<Student>(request, response), student); 
    		new ExportExcel("用户数据", Student.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学生信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/student/list?repage";
    }

}