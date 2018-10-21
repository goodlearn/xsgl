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
import com.thinkgem.jeesite.modules.sys.entity.Classinfo;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.Teacher;
import com.thinkgem.jeesite.modules.sys.service.ClassinfoService;
import com.thinkgem.jeesite.modules.sys.service.StudentService;
import com.thinkgem.jeesite.modules.sys.service.StudentrecordService;
import com.thinkgem.jeesite.modules.sys.service.SysWxInfoService;
import com.thinkgem.jeesite.modules.sys.service.TeacherService;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

/**
 * 首页
 * @author Wzy
 *
 */
@Controller
@RequestMapping(value = "wi")
public class WxIndexController extends WxBaseController{

	
	
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private ClassinfoService classinfoService;
	
	
	@Autowired
	private SysWxInfoService sysWxInfoService;
	
	@Autowired
	private StudentrecordService studentrecordService;
	
	
	
	/**
	 * 用户绑定
	 */
	@RequestMapping(value="/tieInfo",method=RequestMethod.POST)
	@ResponseBody
	public String tieInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
		String no = request.getParameter("no");//学号（工号）
		String pwd = request.getParameter("pwd");//密码
		String iType = request.getParameter("iType");//身份类型
		
		if(StringUtils.isEmpty(no)) {
			return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		if(StringUtils.isEmpty(pwd)) {
			return backJsonWithCode(errCode,ERR_PWD_NO_NULL);
		}
		
		if(StringUtils.isEmpty(iType)) {
			return backJsonWithCode(errCode,ERR_ITYPE_NO_NULL);
		}
		
		
	
		if(iType.equals("0")) {
			//学生身份
			Student stu = studentService.findByNo(no);
			//不存在该学生
			if(null == stu) {
				return backJsonWithCode(errCode,ERR_STU_NO_NULL);
			}
			//密码
			String dbPwd = stu.getPwd();
			if(!pwd.equals(dbPwd)) {
				return backJsonWithCode(errCode,ERR_PWD);
			}
		}else if(iType.equals("1")){
			//老师身份
			Teacher teacher = teacherService.findByNo(no);
			//不存在该老师
			if(null == teacher) {
				return backJsonWithCode(errCode,ERR_TEACHER_NO_NULL);
			}
			//密码
			String dbPwd = teacher.getPwd();
			if(!pwd.equals(dbPwd)) {
				return backJsonWithCode(errCode,ERR_PWD);
			}
			
		}else {
			return backJsonWithCode(errCode,ERR_ITYPE_ERR);
		}
		
		//查询微信信息
		SysWxInfo sysWxInfo = sysWxInfoService.findWxInfoByNo(no);
		if(null != sysWxInfo) {
			//已经绑定过了 看是否需要重新登录
			Date endDate = sysWxInfo.getTieEndDate();//结束绑定日期 如果结束 需要重新绑定
			
			if(endDate.after(new Date())) {
				//已经绑定过了 
				return backJsonWithCode(errCode,ERR_EXIST_WX_INFO);
			}
		}
		
		String openId = (String)model.asMap().get("openId");
		if(null == openId) {
			return backJsonWithCode(errCode,ERR_OPEN_ID_NOT_GET);
		}
		
		//保存用户
		sysWxInfoService.tieInfo(openId, no,iType);
		return backJsonWithCode(successCode,null);
	}
	
	/**
	 * 页面跳转 -- 获取首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/indexInfo",method=RequestMethod.GET)
	public String indexInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId,model);
			if(null!=regUrl) {
				//有错误信息
				String errUrl = (String)model.asMap().get("errUrl");
				if(null != errUrl) {
					//看是否有错误
					return errUrl;
				}else {
					return regUrl;
				}
			}	
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
			Student stu = studentService.findByNo(no);
			if(null == stu) {
				model.addAttribute("message",ERR_STU_NO_NULL);
				return WX_ERROR;
			}
			model.addAttribute("student",stu);//学生数据
			model.addAttribute("clssinfoId",stu.getClassId());//学生数据
			return STU_INDEX_INFO;
			//return stuProcess(model,stu);
		}else if(tieType.equals("1")) {
			//老师
			Teacher teacher = teacherService.findByNo(no);
			if(null == teacher) {
				model.addAttribute("message",ERR_TEACHER_NO_NULL);
				return WX_ERROR;
			}
			
			return teacherProcess(model,teacher);
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
		
	}
	
	/**
	 * 页面跳转 -- 获取学生首页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/stuIndexInfo",method=RequestMethod.GET)
	public String stuIndexInfo(HttpServletRequest request, HttpServletResponse response,Model model) {
		String openId = null;
		if(null != Global.TEST_WX_OPEN_ID) {
			//微信测试
			openId = Global.TEST_WX_OPEN_ID;
		}else {
			//是否已经注册并且激活
		    openId = (String)model.asMap().get("openId");
			String regUrl = validateRegByOpenId(openId,model);
			if(null!=regUrl) {
				//有错误信息
				String errUrl = (String)model.asMap().get("errUrl");
				if(null != errUrl) {
					//看是否有错误
					return errUrl;
				}else {
					return regUrl;
				}
			}	
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
			Student stu = studentService.findByNo(no);
			if(null == stu) {
				model.addAttribute("message",ERR_STU_NO_NULL);
				return WX_ERROR;
			}
			return stuProcess(model,stu);
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
		
	}
	
	
	//老师级别信息处理
	private String teacherProcess(Model model,Teacher teacher) {
		
		//查询班级名称
		Classinfo queryList = new Classinfo();
		queryList.setTeacherNo(teacher.getNo());
		List<Classinfo> clsList = classinfoService.findList(queryList);
		
		model.addAttribute("clsNum",clsList.size());//班级数量
		model.addAttribute("clsList",clsList);//班级
		model.addAttribute("userName",teacher.getName());//教师名称
		model.addAttribute("isTeacher","yes");//是老师
		/**
		 * 导航
		 */
		List<String> navigaionList = new ArrayList<String>();
		navigaionList.add(NAVIGAION_TEACHER_1);
		navigaionList.add(NAVIGAION_TEACHER_2);
		navigaionList.add(NAVIGAION_TEACHER_3);
		navigaionList.add(NAVIGAION_TEACHER_4);
		model.addAttribute("navigaionList",navigaionList);
		
		return INDEX_INFO;
	}
		
	//学生级别信息处理
	private String stuProcess(Model model,Student stu) {
		//老师
		model.addAttribute("student",stu);//学生数据
		//最近的奖惩记录
		List<Studentrecord> srs = studentrecordService.findListLimit5(stu.getNo());
		if(null == srs || srs.size() == 0) {
			model.addAttribute("srsNum",0);//奖惩数据
		}else {
			model.addAttribute("srsNum",srs.size());//奖惩数据
			model.addAttribute("srs",srs);//奖惩数据
		}
		
		return STU_REWARDS_DETAILS;
	}
}
