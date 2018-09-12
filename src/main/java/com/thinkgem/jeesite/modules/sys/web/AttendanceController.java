/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.sys.entity.Attendance;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AttendanceService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

/**
 * 考勤记录Controller
 * @author 王泽宇
 * @version 2018-09-12
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/attendance")
public class AttendanceController extends BaseController {

	@Autowired
	private AttendanceService attendanceService;
	
	@ModelAttribute
	public Attendance get(@RequestParam(required=false) String id) {
		Attendance entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = attendanceService.get(id);
		}
		if (entity == null){
			entity = new Attendance();
		}
		return entity;
	}
	
	@RequiresPermissions("sys:attendance:view")
	@RequestMapping(value = {"list", ""})
	public String list(Attendance attendance, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Attendance> page = attendanceService.findPage(new Page<Attendance>(request, response), attendance); 
		model.addAttribute("page", page);
		return "modules/sys/attendanceList";
	}
	

	@RequiresPermissions("sys:attendance:view")
	@RequestMapping(value = "form")
	public String form(Attendance attendance, Model model) {
		model.addAttribute("attendance", attendance);
		return "modules/sys/attendanceForm";
	}

	@RequiresPermissions("sys:attendance:edit")
	@RequestMapping(value = "save")
	public String save(Attendance attendance, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, attendance)){
			return form(attendance, model);
		}
		attendanceService.save(attendance);
		addMessage(redirectAttributes, "保存考勤记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/attendance/?repage";
	}
	
	@RequiresPermissions("sys:attendance:edit")
	@RequestMapping(value = "delete")
	public String delete(Attendance attendance, RedirectAttributes redirectAttributes) {
		attendanceService.delete(attendance);
		addMessage(redirectAttributes, "删除考勤记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/attendance/?repage";
	}
	
	@RequiresPermissions("sys:attendance:edit")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Attendance attendance, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Attendance> page = attendanceService.findPage(new Page<Attendance>(request, response), attendance); 
    		new ExportExcel("用户数据", Attendance.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/attendance/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:attendance:edit")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<Attendance> list = Lists.newArrayList(); 
    		list.add(new Attendance());
    		new ExportExcel("用户数据", Attendance.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:attendance:edit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/student/list?repage";
		}
		try {
			int successNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Attendance> list = ei.getDataList(Attendance.class);
			for (Attendance attendance : list){
				attendanceService.save(attendance);
				successNum++;
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/attendance/list?repage";
    }

}