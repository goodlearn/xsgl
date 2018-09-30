package com.thinkgem.jeesite.modules.sys.utils;

import java.util.List;

import com.google.common.collect.Lists;
import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import com.thinkgem.jeesite.modules.sys.dao.ClassinfoDao;
import com.thinkgem.jeesite.modules.sys.dao.InstituteDao;
import com.thinkgem.jeesite.modules.sys.dao.ProfessioninfoDao;
import com.thinkgem.jeesite.modules.sys.entity.Classinfo;
import com.thinkgem.jeesite.modules.sys.entity.Institute;
import com.thinkgem.jeesite.modules.sys.entity.Professioninfo;

/**
* @author wzy
* @version 创建时间：2018年9月7日 下午7:15:54
* @ClassName 类名称
* @Description 类描述
*/
public class BaseInfoUtils {

	/**
	 * 清除缓存
	 */
	public static void clearAllCache() {
		CacheUtils.remove(Institute_LIST);
		CacheUtils.remove(Professioninfo_LIST);
		CacheUtils.remove(Classinfo_LIST);

	}
	
	//院系
	private static InstituteDao instituteDao = SpringContextHolder.getBean(InstituteDao.class);
	
	public static final String Institute_LIST= "instituteMap";
	
	//专业
	private static ProfessioninfoDao professioninfoDao = SpringContextHolder.getBean(ProfessioninfoDao.class);
	
	public static final String Professioninfo_LIST= "professioninfoMap";
	
	//班级
	private static ClassinfoDao classinfoDao = SpringContextHolder.getBean(ClassinfoDao.class);
	
	public static final String Classinfo_LIST= "classinfoMap";
	
	
	/**
	 * 获取所有院系信息
	 * @return
	 */
	public static List<Institute> getAllInstituteList(){
		@SuppressWarnings("unchecked")
		List<Institute> list = (List<Institute>)CacheUtils.get(Institute_LIST);
		if (list==null  || list.size() == 0){
			list = Lists.newArrayList();
			for (Institute cl : instituteDao.findAllList(new Institute())){
				list.add(cl);
			}
			CacheUtils.put(Institute_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取所有专业信息
	 * @return
	 */
	public static List<Professioninfo> getAllProfessioninfoList(){
		@SuppressWarnings("unchecked")
		List<Professioninfo> list = (List<Professioninfo>)CacheUtils.get(Professioninfo_LIST);
		if (list==null  || list.size() == 0){
			list = Lists.newArrayList();
			for (Professioninfo cl : professioninfoDao.findAllList(new Professioninfo())){
				list.add(cl);
			}
			CacheUtils.put(Professioninfo_LIST, list);
		}
		return list;
	}
	
	/**
	 * 获取所有班级信息
	 * @return
	 */
	public static List<Classinfo> getAllClassinfoDaoList(){
		@SuppressWarnings("unchecked")
		List<Classinfo> list = (List<Classinfo>)CacheUtils.get(Classinfo_LIST);
		if (list==null  || list.size() == 0){
			list = Lists.newArrayList();
			for (Classinfo cl : classinfoDao.findAllList(new Classinfo())){
				list.add(cl);
			}
			CacheUtils.put(Classinfo_LIST, list);
		}
		return list;
	}
	
	
}
