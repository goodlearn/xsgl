package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

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
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.modules.sys.entity.Classinfo;
import com.thinkgem.jeesite.modules.sys.entity.Professioninfo;
import com.thinkgem.jeesite.modules.sys.entity.Student;
import com.thinkgem.jeesite.modules.sys.entity.Studentrecord;
import com.thinkgem.jeesite.modules.sys.entity.SysWxInfo;
import com.thinkgem.jeesite.modules.sys.entity.Teacher;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.ClassinfoService;
import com.thinkgem.jeesite.modules.sys.service.StudentService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.service.TeacherService;
import com.thinkgem.jeesite.modules.sys.service.WxService;
import com.thinkgem.jeesite.modules.sys.utils.BaseInfoUtils;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;

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
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private ClassinfoService classinfoService;
	
	@Autowired
	private WxService wxService;
	
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
		model.addAttribute("clsList", studentService.findClassInfo());
		model.addAttribute("page", page);
		return "modules/sys/studentList";
	}
	
	@RequiresPermissions("sys:student:view")
	@RequestMapping(value = {"listBatch"})
	public String listBatch(Student student, HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes redirectAttributes) {
		String returl = "redirect:"+Global.getAdminPath()+"/sys/student/?repage" ;
		
		User user = UserUtils.getUser();
		
		if(user.isAdmin()) {
			addMessage(redirectAttributes,"超管不可以批量管理全部班级");
			return returl;
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
		
		Classinfo query = new Classinfo();
		query.setTeacherNo(no);
		
		List<Classinfo> classinfos =classinfoService.findList(query);
		
		if(null == classinfos || classinfos.size() == 0) {
			addMessage(redirectAttributes,"班级信息未获取");
			return returl;
		}
		
		//最近的奖惩记录
		
		List<Student> stuList = new ArrayList<Student>();
		
		for(Classinfo ci : classinfos) {
			List<Student> stus = studentService.findStudents(ci.getId());
			if(null!=stus) {
				stuList.addAll(stus);
			}
		}
		
		if(stuList.size() == 0) {
			model.addAttribute("stuNum",0);//奖惩数据
		}else {
			model.addAttribute("stuNum",stuList.size());
			model.addAttribute("stuList",stuList);//学生信息
			model.addAttribute("studentrecord",new Studentrecord());//学生信息
		}
		return "modules/sys/studentListBatch";
	}

	@RequiresPermissions("sys:student:view")
	@RequestMapping(value = "form")
	public String form(Student student, Model model) {
		model.addAttribute("student", student);
		model.addAttribute("clsList", studentService.findClassInfo());
		return "modules/sys/studentForm";
	}

	@RequiresPermissions("sys:student:edit")
	@RequestMapping(value = "save")
	public String save(Student student, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, student)){
			return form(student, model);
		}
		//给默认数据
		if(null == student.getScore()) {
			student.setScore("100");
		}
		if(null == student.getPwd()) {
			student.setPwd("1");
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
	
	
	/**
	 * 导入用户数据
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:student:supedit")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/student/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Student> list = ei.getDataList(Student.class);
			for (Student student : list){
				if(findProfessionId(student)) {
					if(studentService.findByNoAndIdCard(student.getIdcard(),student.getNo())) {
						if(null == student.getScore()) {
							student.setScore("100");
						}
						if(null == student.getPwd()) {
							student.setPwd("1");
						}
						studentService.save(student);
						successNum++;
					}else {
						failureMsg.append("<br/>登录名 "+student.getName()+" 学号或身份证有重复 ");
						failureNum++;
					}
					
				}else {
					failureMsg.append("<br/>登录名 "+student.getName()+" 没有专业信息 ");
					failureNum++;
				}
			}
			
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/student/list?repage";
    }
	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:student:view")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户数据导入模板.xlsx";
    		List<Student> list = Lists.newArrayList(); 
    		list.add(new Student());
    		new ExportExcel("用户数据", Student.class, 2).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
    }
	
	//根据专业名获取专业编号
	private boolean findProfessionId(Student student) {
		boolean isSuc = false;//是否成功
		String prosseionName = student.getProsseionName();
		List<Classinfo> clsList = BaseInfoUtils.getAllClassinfoDaoList();
		for(Classinfo cls : clsList) {
			String name = cls.getName();
			if(name.equals(prosseionName)) {
				student.setClassId(cls.getId());;
				isSuc =true;
				break;
			}
		}
		return isSuc;
	}

}