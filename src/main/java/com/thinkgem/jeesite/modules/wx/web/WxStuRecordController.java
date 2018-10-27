package com.thinkgem.jeesite.modules.wx.web;

import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.CasUtils;
import com.thinkgem.jeesite.modules.sys.entity.Classinfo;
import com.thinkgem.jeesite.modules.sys.entity.Classprice;
import com.thinkgem.jeesite.modules.sys.entity.JsonClassPrice;
import com.thinkgem.jeesite.modules.sys.entity.JsonStuRecord;
import com.thinkgem.jeesite.modules.sys.entity.JsonStuReweward;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.Teacher;
import com.thinkgem.jeesite.modules.sys.service.ClassinfoService;
import com.thinkgem.jeesite.modules.sys.service.StudentService;
import com.thinkgem.jeesite.modules.sys.service.StudentrecordService;
import com.thinkgem.jeesite.modules.sys.service.TeacherService;
import com.thinkgem.jeesite.modules.sys.service.WxService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

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
	private ClassinfoService classinfoService;
	
	@Autowired
	private WxService wxService;
	
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
			model.addAttribute("message",ERR_CALSS_ID_NO_NULL);
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
				//设置头像
				for(Student forStu : students) {
					studentService.setHeadUrl(forStu);
				}
				model.addAttribute("stuNum",students.size());
				model.addAttribute("stuList",students);
				model.addAttribute("classId",classId);//加入班级ID
				
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
			model.addAttribute("message",ERR_EMP_NO_NULL);
			return WX_ERROR;
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
	 * 页面跳转 -- 获取奖惩详细页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stuRewardsDetails", method = RequestMethod.GET)
	public String stuRewardsDetails(HttpServletRequest request, HttpServletResponse response, Model model) {
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
			model.addAttribute("message",ERR_CALSS_ID_NO_NULL);
			return WX_ERROR;
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
			studentService.setHeadUrl(stu);
			//老师
			model.addAttribute("student",stu);//学生数据
			//最近的奖惩记录
			List<Studentrecord> srs = studentrecordService.findListLimit5(stuNo);
			if(null == srs || srs.size() == 0) {
				model.addAttribute("srsNum",0);//奖惩数据
			}else {
				model.addAttribute("srsNum",srs.size());//奖惩数据
				model.addAttribute("srs",srs);//奖惩数据
			}
			
			return STU_REWARDS_DETAILS;
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	
	
	
	/**
	 * 页面跳转 -- 获取奖惩班级排行页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rewardBoard", method = RequestMethod.GET)
	public String rewardBoard(HttpServletRequest request, HttpServletResponse response, Model model) {
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
			model.addAttribute("message",ERR_CLASS_ID_ERR);
			return WX_ERROR;
		}
		
		Classinfo classinfo = classinfoService.get(classId);
		if(null == classinfo) {
			model.addAttribute("message",ERR_CLASS_NULL);
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
		
		model.addAttribute("classinfo",classinfo);
		//最近的奖惩记录
		List<Student> stuList = studentService.findListRank(classId);
		if(null == stuList || stuList.size() == 0) {
			model.addAttribute("stuNum",0);//奖惩数据
		}else {
			model.addAttribute("stuNum",stuList.size());
			model.addAttribute("one",stuList.get(0));//排行数据
			model.addAttribute("two",stuList.get(1));//排行数据
			model.addAttribute("three",stuList.get(2));//排行数据
			model.addAttribute("stuList",stuList);//排行数据
		}
		
		return STU_REWARDS_DETAILS_RANK;
	}
	
	/**
	 * 页面跳转 -- 获取奖惩详细页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stuRewardsDetailsAll", method = RequestMethod.GET)
	public String stuRewardsDetailsAll(HttpServletRequest request, HttpServletResponse response, Model model) {
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
			model.addAttribute("message",ERR_CALSS_ID_NO_NULL);
			return WX_ERROR;
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

		if(tieType.equals("1") || tieType.equals("0")) {
			
			Studentrecord query = new Studentrecord();
			query.setStudentId(stuNo);
			query.setDelFlag(Studentrecord.DEL_FLAG_NORMAL);
			Page<Studentrecord> page = studentrecordService.findPage(new Page<Studentrecord>(request, response), query); 
			
			model.addAttribute("student",stu);//学生数据
			model.addAttribute("pageNo", page.getPageNo()-1);
			model.addAttribute("pageSize", String.valueOf(page.getList().size()));
			model.addAttribute("lastPage", page.getLast());
			model.addAttribute("totalCount",page.getCount());//奖惩数据
			
			return STU_REWARDS_DETAILS_ALL;
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	
	/**
	 * 刷新显示数据
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/stuRewardsDetailsAllRefresh", method = RequestMethod.POST)
	public String stuRewardsDetailsAllRefresh(HttpServletRequest request, HttpServletResponse response, Model model) {
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
					return backJsonWithCode(errCode,errUrl);
				} else {
					return backJsonWithCode(errCode,regUrl);
				}
			}
		}
		
		
		String stuNo = request.getParameter("stuNo");//学生学号
		if(StringUtils.isEmpty(stuNo)) {
			return backJsonWithCode(errCode,ERR_CALSS_ID_NO_NULL);
		}
		
		Student stu = studentService.findByNo(stuNo);
		if(null == stu) {
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

		if(tieType.equals("1") || tieType.equals("0")) {
			Studentrecord query = new Studentrecord();
			query.setDelFlag(Studentrecord.DEL_FLAG_NORMAL);
			query.setStudentId(stuNo);
			Page<Studentrecord> page = studentrecordService.findPage(new Page<Studentrecord>(request, response), query); 
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalCount", String.valueOf(page.getCount()));
			map.put("pageNo",String.valueOf(page.getPageNo()));
			map.put("pageSize",String.valueOf(page.getList().size()));
			List<JsonStuRecord> jsonStuRecordList = new ArrayList<JsonStuRecord>();
			List<Studentrecord> rsList = page.getList();
			for(Studentrecord sr : rsList) {
				JsonStuRecord jsr = new JsonStuRecord();
				jsr.convertFromStuRecord(sr);
				jsonStuRecordList.add(jsr);
			}
			map.put("data", JSONObject.toJSONString(jsonStuRecordList));
			String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
			return backJsonWithCode(successCode,jsonResult);	
		}else {
			return backJsonWithCode(errCode,ERR_WP_LEVEL_NULL);	
		}
	}
	
	/**
	 * 页面跳转 -- 保存
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
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
		
		//查看学号是否存在
		if(null == studentService.findByNo(stuNo)) {
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

		if(tieType.equals("1") || tieType.equals("0")) {
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
				//添加的学生
				Student stu = studentService.findByNo(stuNo);
				return backJsonWithCode(successCode,stu.getClassId());//将班级返回到页面以便重新访问
			}else {
				return backJsonWithCode(errCode,ERR_ERROR_SCORE);
			}
	
		}else {
			return backJsonWithCode(errCode,ERR_WP_LEVEL_NULL);
		}
	}
	
	
	
	/**
	 * 页面跳转 -- 获取批量页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/obatinBatchSr", method = RequestMethod.GET)
	public String obatinBatchSr(HttpServletRequest request, HttpServletResponse response, Model model) {
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
			model.addAttribute("message",ERR_CLASS_ID_ERR);
			return WX_ERROR;
		}
		
		Classinfo classinfo = classinfoService.get(classId);
		if(null == classinfo) {
			model.addAttribute("message",ERR_CLASS_NULL);
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
		
		model.addAttribute("classinfo",classinfo);
		//最近的奖惩记录
		List<Student> stuList = studentService.findStudents(classId);
		if(null == stuList || stuList.size() == 0) {
			model.addAttribute("stuNum",0);//奖惩数据
		}else {
			model.addAttribute("stuNum",stuList.size());
			model.addAttribute("stuList",stuList);//学生信息
		}
		
		return STU_REWARDS_BATCH;
	}
	
	/**
	 * 页面跳转 -- 批量保存
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveBatchSr", method = RequestMethod.POST)
	public String saveBatchSr(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		String[] stuNos = request.getParameterValues("bstuNo");//批量的学生学号
		
		if(StringUtils.isEmpty(arType)) {
			model.addAttribute("message",ERR_ADD_REWARY_TYPE_NULL);
			return WX_ERROR;
		//	return backJsonWithCode(errCode,ERR_ADD_REWARY_TYPE_NULL);
		}
		
		if(StringUtils.isEmpty(reason)) {
			model.addAttribute("message",ERR_REASON_NULL);
			return WX_ERROR;
			//return backJsonWithCode(errCode,ERR_REASON_NULL);
		}
		
		if(StringUtils.isEmpty(dyfz)) {
			model.addAttribute("message",ERR_DYFZ_NULL);
			return WX_ERROR;
		//	return backJsonWithCode(errCode,ERR_DYFZ_NULL);
		}
		
		if(null == stuNos || stuNos.length == 0) {
			model.addAttribute("message",ERR_STU_NO_NULL);
			return WX_ERROR;
		//	return backJsonWithCode(errCode,ERR_STU_NO_NULL);
		}
		//查询学号员工号
		String no = sysWxInfoService.findEmpNo(openId);
		if(null == no) {
			model.addAttribute("message",ERR_EMP_NO_NULL);
			return WX_ERROR;
			//return backJsonWithCode(errCode,ERR_EMP_NO_NULL);
		}
		
		Teacher teacher = teacherService.findByNo(no);
		if(null == teacher) {
			model.addAttribute("message",ERR_TEACHER_NO_NULL);
			return WX_ERROR;
		}
		
		SysWxInfo sysWxInfo = sysWxInfoService.findWxInfoByOpenId(openId);
		if(null == sysWxInfo) {
			model.addAttribute("message",ERR_WX_TIE_NO_NULL);
			return WX_ERROR;
			//return backJsonWithCode(errCode,ERR_WX_TIE_NO_NULL);
		}
		
		//查看学号是否存在
		for(String stuNo : stuNos) {
			if(null == studentService.findByNo(stuNo)) {
				model.addAttribute("message",stuNo +"学号的" + ERR_STU_NO_NO_NULL);
				return WX_ERROR;
			}
		}
		
		
		String tieType = sysWxInfo.getTieType();

		if(tieType.equals("1") || tieType.equals("0")) {
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
					try {
						SysWxInfo toUserWxInfo = sysWxInfoService.findWxInfoByNo(stuNo);
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
					}catch(Exception e) {
						logger.info("没有微信用户");
					}
				}else {
					model.addAttribute("message","学号为"+stuNo+"出现意外错误，保存中断，部分学生记录保存成功，请查询德育记录");
					return WX_ERROR;
					//return backJsonWithCode(errCode,ERR_ERROR_SCORE);
				}
			}
			//添加的学生
			return teacherProcess(model);//将班级返回到页面以便重新访问
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
			//return backJsonWithCode(errCode,ERR_WP_LEVEL_NULL);
		}
	}
	
	//老师级别信息处理
	private String teacherProcess(Model model) {
		
		model.addAttribute("redirect_url","/wi/indexInfo");//班级
		
		return WX_SUCCESS;
	}
	
	
	
	/**
	 * 页面跳转 -- 获取班级全部奖惩数据页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/stuRewardsClassRi", method = RequestMethod.GET)
	public String stuRewardsClassRi(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		
		//班级信息ID判断
		String classId = request.getParameter("classId");//学生学号
		if(StringUtils.isEmpty(classId)) {
			model.addAttribute("message",ERR_CALSS_ID_NO_NULL);
			return WX_ERROR;
		}
		
		//班级信息存在判断
		Classinfo classinfo = classinfoService.get(classId);
		if(null == classinfo) {
			model.addAttribute("message",ERR_CLASS_NULL);
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

		if(tieType.equals("1") || tieType.equals("0")) {
			
			model.addAttribute("classinfo",classinfo);//班级数据
			
			Studentrecord query = new Studentrecord();
			List<String> classIds = new ArrayList<String>();
			classIds.add(classId);
			query.setClass_ids(classIds);
			query.setDelFlag(Studentrecord.DEL_FLAG_NORMAL);
			Page<Studentrecord> page = studentrecordService.findPageByClassId(new Page<Studentrecord>(request, response), query); 
			long count = page.getCount();
			
			if(count == 0) {
				model.addAttribute("countSr",0);//数量
			}else {
				model.addAttribute("countSr",count);//数量
				model.addAttribute("pageNo", page.getPageNo());
				model.addAttribute("pageSize", String.valueOf(page.getList().size()));
				model.addAttribute("lastPage", page.getLast());
				model.addAttribute("totalCount",page.getCount());//奖惩数据
				setStringDate(page.getList());//设置时间格式
				List<Studentrecord> rsList = page.getList();
				model.addAttribute("rsList", rsList);
			}
			
			
			return STU_CLASS_REWARDS_INFO;
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	//设置创建时间的字符串形式
	private void setStringDate(List<Studentrecord> srs) {
		for(Studentrecord sr:srs) {
			//时间个是2016-12-12
			String createDate = CasUtils.convertDate2DefaultString(sr.getCreateDate());
			String[] dateArr = createDate.split("-");
			sr.setCreateYearString(dateArr[0]);
			sr.setCreateDayString(dateArr[1]+"/"+dateArr[2]);
		}
	}
	
	/**
	 * 页面跳转 -- 获取班级全部奖惩数据页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pageMoreSrc", method = RequestMethod.POST)
	@ResponseBody
	public String pageMoreSrc(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		
		//班级信息ID判断
		String classId = request.getParameter("classId");//学生学号
		if(StringUtils.isEmpty(classId)) {
			return backJsonWithCode(errCode,ERR_CALSS_ID_NO_NULL);	
		}
		
		//班级信息存在判断
		Classinfo classinfo = classinfoService.get(classId);
		if(null == classinfo) {
			return backJsonWithCode(errCode,ERR_CLASS_NULL);	

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

		if(tieType.equals("1") || tieType.equals("0")) {
			
			model.addAttribute("classinfo",classinfo);//班级数据
			
			Studentrecord query = new Studentrecord();
			List<String> classIds = new ArrayList<String>();
			classIds.add(classId);
			query.setClass_ids(classIds);
			query.setDelFlag(Studentrecord.DEL_FLAG_NORMAL);
			Page<Studentrecord> page = studentrecordService.findPageByClassId(new Page<Studentrecord>(request, response), query); 
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalCount", String.valueOf(page.getCount()));//总数量
			map.put("pageNo",String.valueOf(page.getPageNo()));//当前页
			map.put("pageSize",String.valueOf(page.getPageSize()));//共多少页
			map.put("lastPage",String.valueOf(page.getLast()));//共多少页		
			
			List<JsonStuReweward> jsonStuRecordList = new ArrayList<JsonStuReweward>();
			List<Studentrecord> rsList = page.getList();
			for(Studentrecord sr : rsList) {
				JsonStuReweward jsr = new JsonStuReweward();
				jsr.convertData(sr);
				/**
				 * 时间格式转换
				 */
				String createDate = CasUtils.convertDate2DefaultString(sr.getCreateDate());
				String[] dateArr = createDate.split("-");
				jsr.setCreateYearString(dateArr[0]);
				jsr.setCreateDayString(dateArr[1]+"/"+dateArr[2]);
				jsonStuRecordList.add(jsr);//添加到列表
			}
			map.put("data", JSONObject.toJSONString(jsonStuRecordList));
			String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
			return backJsonWithCode(successCode,jsonResult);	
		}else {
			return backJsonWithCode(errCode,ERR_WP_LEVEL_NULL);	
		}
	}
	
	
	/**
	 * 页面跳转 -- 删除奖惩数据
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/removeSr", method = RequestMethod.POST)
	@ResponseBody
	public String removeSr(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		
		//班级信息ID判断
		String srid = request.getParameter("srid");//学生学号
		if(StringUtils.isEmpty(srid)) {
			return backJsonWithCode(errCode,ERR_SR_ID_NULL);	
		}
		
		//班级信息存在判断
		Studentrecord studentrecord = studentrecordService.get(srid);
		if(null == studentrecord) {
			return backJsonWithCode(errCode,ERR_SR_NULL);	

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

		if(tieType.equals("1") || tieType.equals("0")) {
			//删除记录需要将分数返回给学生
			String scoreType = studentrecord.getScoreType();
			String add = DictUtils.getDictValue("加分", "scoreType", "1");
			if(add.equals(scoreType)) {
				studentrecordService.saveAdd(studentrecord,false);
			}else {
				studentrecordService.saveAdd(studentrecord,true);
			}
			studentrecordService.delete(studentrecord);
			Map<String, String> map = new HashMap<String, String>();
			String jsonResult = JSONObject.toJSONString(map);//将map对象转换成json类型数据
			return backJsonWithCode(successCode,jsonResult);	
		}else {
			return backJsonWithCode(errCode,ERR_WP_LEVEL_NULL);	
		}
	}

}
