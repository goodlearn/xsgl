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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.Teacher;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.ClassinfoService;
import com.thinkgem.jeesite.modules.sys.service.StudentService;
import com.thinkgem.jeesite.modules.sys.service.StudentrecordService;
import com.thinkgem.jeesite.modules.sys.service.SysWxInfoService;
import com.thinkgem.jeesite.modules.sys.service.TeacherService;
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
	private TeacherService teacherService;
	
	@Autowired
	private ClassinfoService classinfoService;

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
	
	@RequiresPermissions("sys:studentrecord:batchedit")
	@RequestMapping(value = "saveBatch")
	public String saveBatch(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		String returl = "redirect:" + adminPath + "/sys/student/list?repage";

		User user = UserUtils.getUser();
		
		if(user.isAdmin()) {
			addMessage(redirectAttributes,"超管不可以批量管理全部班级");
			return returl;
		}
		

		
		
		String arType = request.getParameter("scoreType");//增减
		String reason = request.getParameter("remarks");//原因
		String dyfz = request.getParameter("score");//分值
		String[] stuNos = request.getParameterValues("id");//批量的学生学号
		
		if(StringUtils.isEmpty(arType)) {
			addMessage(redirectAttributes,"请选择增减类型");
			return returl;
		}
		
		if(StringUtils.isEmpty(reason)) {
			addMessage(redirectAttributes,"请选择原因");
			return returl;
			//return backJsonWithCode(errCode,ERR_REASON_NULL);
		}
		
		if(StringUtils.isEmpty(dyfz)) {
			addMessage(redirectAttributes,"请选择分值");
			return returl;
		//	return backJsonWithCode(errCode,ERR_DYFZ_NULL);
		}
		
		if(null == stuNos || stuNos.length == 0) {
			addMessage(redirectAttributes,"请选择学生学号");
			return returl;
		//	return backJsonWithCode(errCode,ERR_STU_NO_NULL);
		}
		
		
		String no = user.getNo();
		
		if(StringUtils.isEmpty(no)) {
			addMessage(redirectAttributes,"工号未获取");
			return returl;
		}
		
		Teacher teacher = teacherService.findByNo(no);
		if(null == teacher) {
			addMessage(redirectAttributes,"教师信息未获取");
			return returl;
		}
		
		
		//查看学号是否存在
		for(String stuNo : stuNos) {
			if(null == studentService.findByNo(stuNo)) {
				addMessage(redirectAttributes,stuNo +"学号不存在信息");
				return returl;
			}
		}
		

		for(String stuNo : stuNos) {
			Studentrecord saveEntity = new Studentrecord();
			saveEntity.setScore(dyfz);
			saveEntity.setRemarks(reason);
			saveEntity.setScoreType(arType);
			saveEntity.setStudentId(stuNo);
			Double currentScore = studentrecordService.wxSave(saveEntity);
			if(null!=currentScore) {
				//发送微信消息
				//微信绑定查询
				SysWxInfo toUserWxInfo = sysWxInfoService.findWxInfoByNo(stuNo);
				try {
					if(null != toUserWxInfo) {
						String add = DictUtils.getDictValue("加分", "scoreType", "1");
						String type = null;
						if(add.equals(arType)) {
							 type = "德育分值加分";
						}else {
							 type = "德育分值扣分";
						}
						wxService.sendMessageScore(toUserWxInfo.getOpenId(), UserUtils.get(Global.DEFAULT_ID_SYS_MANAGER).getName(), currentScore.toString(), type, reason);
					}
				}catch(Exception ex) {
					logger.info("微信未发送");
					ex.printStackTrace();
				}
			}else {
				addMessage(redirectAttributes,"学号为"+stuNo+"出现意外错误，保存中断，部分学生记录保存成功，请查询德育记录");
				return returl;
			}
		}
		
		addMessage(redirectAttributes, "保存奖惩记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/studentrecord/?repage";
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
		studentrecordService.deleteRecord(studentrecord);
		addMessage(redirectAttributes, "删除奖惩记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/studentrecord/?repage";
	}
	
	/**
	 * 导出
	 * @param student
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:studentrecord:batchedit")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Studentrecord studentrecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Studentrecord> page = studentrecordService.findPage(new Page<Studentrecord>(request, response), studentrecord); 
    		new ExportExcel("用户数据", Studentrecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学生信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/studentrecord/list?repage";
    }
	
	
	/**
	 * 导出按学号
	 * @param student
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:studentrecord:batchedit")
    @RequestMapping(value = "exportAll", method=RequestMethod.POST)
    public String exportAll(Studentrecord studentrecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            
            String no = studentrecord.getStudentId();
            if(null == no || no.equals("")) {
            	addMessage(redirectAttributes, "学号为空:");
            	return "redirect:" + adminPath + "/sys/studentrecord/list?repage";
            }
            List<Studentrecord> studentrecordList = studentrecordService.findList(studentrecord);
            Page<Studentrecord> page = studentrecordService.findPage(new Page<Studentrecord>(request, response), studentrecord); 
    		new ExportExcel("用户数据", Studentrecord.class).setDataList(studentrecordList).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学生信息失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/studentrecord/list?repage";
    }

}