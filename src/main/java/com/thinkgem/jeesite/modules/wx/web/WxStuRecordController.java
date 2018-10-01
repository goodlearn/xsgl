package com.thinkgem.jeesite.modules.wx.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.service.StudentService;
import com.thinkgem.jeesite.modules.sys.service.StudentrecordService;
import com.thinkgem.jeesite.modules.sys.service.TeacherService;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

/**
 * 首页
 * 
 * @author Wzy
 *
 */
@Controller
@RequestMapping(value = "wxsr")
public class WxStuRecordController extends WxBaseController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private StudentrecordService studentrecordService;
	/**
	 * 页面跳转 -- 获取首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/indexInfo", method = RequestMethod.GET)
	public String indexInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		
		String classId = request.getParameter("classId");//班级编号
		if(StringUtils.isEmpty(classId)) {
			return backJsonWithCode(errCode,ERR_CALSS_ID_NO_NULL);
		}
		
		//查询学号员工号
		String no = sysWxInfoService.findEmpNo(openId);
		if(null == no) {
			model.addAttribute("message",ERR_EMP_NO_NULL);
			return WX_ERROR;
		}
		
		SysWxInfo sysWxInfo = sysWxInfoService.findWxInfoByOpenId(openId);
		if(null == sysWxInfo) {
			model.addAttribute("message",ERR_WX_TIE_NO_NULL);
			return WX_ERROR;
		}
		
		String tieType = sysWxInfo.getTieType();

		if(tieType.equals("0")) {
			//学生
			return STU_REWARDS;
		}else if(tieType.equals("1")) {
			//老师
			Student query = new Student();
			query.setClassId(classId);
			query.setDelFlag(Student.DEL_FLAG_NORMAL);
			List<Student> students = studentService.findList(query);
			if(null == students || students.size() == 0) {
				model.addAttribute("stuNum",0);
			}else {
				model.addAttribute("stuNum",students.size());
				model.addAttribute("stuList",students);
			}
			
			return STU_REWARDS;
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	
	/**
	 * 页面跳转 -- 获取奖惩添加页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stuRewardsAdd", method = RequestMethod.GET)
	public String stuRewardsAdd(HttpServletRequest request, HttpServletResponse response, Model model) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		
		String stuNo = request.getParameter("stuNo");//学生学号
		if(StringUtils.isEmpty(stuNo)) {
			return backJsonWithCode(errCode,ERR_CALSS_ID_NO_NULL);
		}
		
		Student stu = studentService.findByNo(stuNo);
		if(null == stu) {
			model.addAttribute("message",ERR_STU_NO_NULL);
			return WX_ERROR;
		}
		
		
		//查询学号员工号
		String no = sysWxInfoService.findEmpNo(openId);
		if(null == no) {
			model.addAttribute("message",ERR_EMP_NO_NULL);
			return WX_ERROR;
		}
		
		SysWxInfo sysWxInfo = sysWxInfoService.findWxInfoByOpenId(openId);
		if(null == sysWxInfo) {
			model.addAttribute("message",ERR_WX_TIE_NO_NULL);
			return WX_ERROR;
		}
		
		String tieType = sysWxInfo.getTieType();

		if(tieType.equals("1")) {
			//老师
			model.addAttribute("student",stu);
			return STU_REWARDS_ADD;
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	
	
	/**
	 * 页面跳转 -- 获取首页
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveStuReward", method = RequestMethod.POST)
	public String saveStuReward(HttpServletRequest request, HttpServletResponse response, Model model) {
		String openId = null;
		if (null != Global.TEST_WX_OPEN_ID) {
			// 微信测试
			openId = Global.TEST_WX_OPEN_ID;
		} else {
			// 是否已经注册并且激活
			openId = (String) model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId, model);
			if (null != regUrl) {
				// 有错误信息
				String errUrl = (String) model.asMap().get("errUrl");
				if (null != errUrl) {
					// 看是否有错误
					return errUrl;
				} else {
					return regUrl;
				}
			}
		}
		
		String arType = request.getParameter("arType");//增减
		String reason = request.getParameter("reason");//原因
		String dyfz = request.getParameter("dyfz");//分值
		String stuNo = request.getParameter("stuNo");//学号
		
		if(StringUtils.isEmpty(arType)) {
			return backJsonWithCode(errCode,ERR_ADD_REWARY_TYPE_NULL);
		}
		
		if(StringUtils.isEmpty(reason)) {
			return backJsonWithCode(errCode,ERR_REASON_NULL);
		}
		
		if(StringUtils.isEmpty(dyfz)) {
			return backJsonWithCode(errCode,ERR_DYFZ_NULL);
		}
		
		if(StringUtils.isEmpty(stuNo)) {
			return backJsonWithCode(errCode,ERR_STU_NO_NULL);
		}
		
		//查询学号员工号
		String no = sysWxInfoService.findEmpNo(openId);
		if(null == no) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		SysWxInfo sysWxInfo = sysWxInfoService.findWxInfoByOpenId(openId);
		if(null == sysWxInfo) {
			return backJsonWithCode(errCode,ERR_WX_TIE_NO_NULL);
		}
		
		String tieType = sysWxInfo.getTieType();

		if(tieType.equals("0")) {
			//学生
			return backJsonWithCode(errCode,ERR_WP_LEVEL_NULL);

		}else if(tieType.equals("1")) {
			Studentrecord saveEntity = new Studentrecord();
			saveEntity.setScore(dyfz);
			saveEntity.setRemarks(reason);
			saveEntity.setScoreType(arType);
			saveEntity.setStudentId(stuNo);
			studentrecordService.wxSave(saveEntity);
			return backJsonWithCode(successCode,null);
		}else {
			return backJsonWithCode(errCode,ERR_WP_LEVEL_NULL);
		}
	}

}
