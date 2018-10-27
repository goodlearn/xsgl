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
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.Teacher;
import com.thinkgem.jeesite.modules.sys.service.ClassinfoService;
import com.thinkgem.jeesite.modules.sys.service.ClasspriceService;
import com.thinkgem.jeesite.modules.sys.service.StudentService;
import com.thinkgem.jeesite.modules.sys.service.StudentrecordService;
import com.thinkgem.jeesite.modules.sys.service.TeacherService;
import com.thinkgem.jeesite.modules.sys.service.WxService;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

/**
 * 班费
 * 
 * @author Wzy
 *
 */
@Controller
@RequestMapping(value = "cex")
public class WxClassExpensesController extends WxBaseController {

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private ClassinfoService classinfoService;
	
	@Autowired
	private WxService wxService;
	
	@Autowired
	private ClasspriceService classpriceService;
	
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
		Classinfo classinfo = classinfoService.get(classId);
		if(null == classinfo) {
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
		
		if(tieType.equals("0")||tieType.equals("1")) {
			
			//携带数据
			setIndexInfo(request,response,classId, classinfo, model);
			return CLASS_EXPENSES;
		}else {
			model.addAttribute("message",ERR_WP_LEVEL_NULL);
			return WX_ERROR;
		}
	}
	
	/**
	 * 页面跳转 -- 加载更多
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pageMore", method = RequestMethod.POST)
	@ResponseBody
	public String pageMore(HttpServletRequest request, HttpServletResponse response, Model model) {
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
		
		//查询班级是否存在
		String classId = request.getParameter("classId");//班级编号
		if(StringUtils.isEmpty(classId)) {
			return backJsonWithCode(errCode,ERR_CALSS_ID_NO_NULL);	
		}
		Classinfo classinfo = classinfoService.get(classId);
		if(null == classinfo) {
			return backJsonWithCode(errCode,ERR_CALSS_ID_NO_NULL);	
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
		
		if(tieType.equals("0")||tieType.equals("1")) {
			
			//携带数据
			List<String> queryIds = new ArrayList<String>();
			queryIds.add(classId);
			Classprice classprice = new Classprice();
			classprice.setClass_ids(queryIds);
			classprice.setDelFlag(Classprice.DEL_FLAG_NORMAL);
			//携带数据
			Page<Classprice> page = classpriceService.findPageByClassId(new Page<Classprice>(request, response), classprice); 
			Map<String, String> map = new HashMap<String, String>();
			map.put("totalCount", String.valueOf(page.getCount()));//总数量
			map.put("pageNo",String.valueOf(page.getPageNo()));//当前页
			map.put("pageSize",String.valueOf(page.getPageSize()));//共多少页
			map.put("lastPage",String.valueOf(page.getLast()));//共多少页
			List<JsonClassPrice> jsonStuRecordList = new ArrayList<JsonClassPrice>();
			List<Classprice> rsList = page.getList();
			//转换json数据作准备
			for(Classprice cp : rsList) {
				JsonClassPrice jsr = new JsonClassPrice();
				jsr.convertFromClassPrice(cp);//从原表获取数据
				/**
				 * 时间格式转换
				 */
				String createDate = CasUtils.convertDate2DefaultString(cp.getCreateDate());
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
	
	//携带班级首页信息
	private void setIndexInfo(HttpServletRequest request, HttpServletResponse response,String classId,Classinfo classinfo,Model model) {
		
		//携带数据
		List<String> queryIds = new ArrayList<String>();
		queryIds.add(classId);
		Classprice classprice = new Classprice();
		classprice.setClass_ids(queryIds);
		classprice.setDelFlag(Classprice.DEL_FLAG_NORMAL);
		Page<Classprice> page = classpriceService.findPageByClassId(new Page<Classprice>(request, response), classprice); 
		long count = page.getCount();
		
		if(count == 0) {
			model.addAttribute("clsPriceNum",0);//数量
			model.addAttribute("clsinfo",classinfo);
			model.addAttribute("clsSum",computeSum(page.getList()));//消费额
		}else {
			model.addAttribute("clsPriceNum",count);
			setStringDate(page.getList());//设置时间格式
			model.addAttribute("clsPrices",page.getList());
			model.addAttribute("clsinfo",classinfo);
			model.addAttribute("clsSum",computeSum(page.getList()));//消费额
			//分页数据
			model.addAttribute("pageNo", page.getPageNo());
			model.addAttribute("pageSize", String.valueOf(page.getPageSize()));
			model.addAttribute("lastPage", page.getLast());
			model.addAttribute("totalCount",count);//奖惩数据

		}
	}
	
	//设置创建时间的字符串形式
	private void setStringDate(List<Classprice> clsPrices) {
		for(Classprice cp:clsPrices) {
			//时间个是2016-12-12
			String createDate = CasUtils.convertDate2DefaultString(cp.getCreateDate());
			String[] dateArr = createDate.split("-");
			cp.setCreateYearString(dateArr[0]);
			cp.setCreateDayString(dateArr[1]+"/"+dateArr[2]);
		}
	}
	
	//计算总额
	private double computeSum(List<Classprice> clsPrices) {
		double sum = 0.0d;
		try {
			if(null == clsPrices || clsPrices.size() == 0) {
				return sum;
			}else {
				for(Classprice cp:clsPrices) {
					String score = cp.getScore();
					sum+=Double.valueOf(score);
				}
				return sum;
			}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return sum;
	}

	
	
}
