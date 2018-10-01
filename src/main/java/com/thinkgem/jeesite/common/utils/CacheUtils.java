/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thinkgem.jeesite.common.entity.PhoneMsgCache;
import com.thinkgem.jeesite.common.entity.Qrecord;
import com.thinkgem.jeesite.common.entity.WxCodeCache;

/**
 * Cache工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class CacheUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	private static CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	
	private static final String SYS_CACHE = "sysCache";

	
	//手机验证码缓存Key
		private static List<String> phoneMsgCacheKeies = new ArrayList<String>();
		//code和openId的对应 如果是新code获取的openId后，将之前的code移除掉的关联
		private static Map<String,String> wxCodeOpenRelations = new HashMap<String,String>();
		//二维码Key
		private static List<String> rRecordCacheKeies = new ArrayList<String>();
		//移除微信code缓存
		public static void removeWxCodeKey(String key) {
			remove(key);
		}
		
		//添加二维码缓存Key
		public static void putQRecordCacheKey(String key) {
			rRecordCacheKeies.add(key);
		}
		
		//添加微信code缓存Key
		public static void putWxCodeKey(String code,String openId) {
			wxCodeOpenRelations.put(openId, code);
		}
		
		//添加手机验证码缓存Key
		public static void putPhoneMsgCacheKey(String key) {
			phoneMsgCacheKeies.add(key);
		}
		
		//openId的缓存
		public static String getCodeByOpenId(String openId) {
			return wxCodeOpenRelations.get(openId);
		}
		
		//openId的缓存清除掉
		public static String removeCodeByOpenId(String openId) {
			return wxCodeOpenRelations.remove(openId);
		}
		
		//清除重复二维码码
		public static void clearQRecordRepeatCacheKeies(String idCard) {
			Iterator<String> its = rRecordCacheKeies.iterator();
			List<String> emptyKeies = new ArrayList<String>();
			while(its.hasNext()) {
				String key = its.next();
				Qrecord qrecord = (Qrecord)get(key);
				if(null == qrecord) {
					//已经空了 准备移除掉
					emptyKeies.add(key);
				}else {
					long timeOut = qrecord.getTimeOut();
					if(System.currentTimeMillis() > timeOut) {
						//已经超时了
						emptyKeies.add(key);
						remove(key);//移除缓存
					}
					//用户已经存在
					String recordIdCard = qrecord.getIdCard();
					if(recordIdCard.equals(idCard)) {
						emptyKeies.add(key);
						remove(key);//移除缓存
					}
				}
			}
			//有移除的对象
			if(emptyKeies.size()>0) {
				rRecordCacheKeies.removeAll(emptyKeies);
			}
		}
		
		//清除过期二维码
		public static void clearQRecordCacheKeies() {
			Iterator<String> its = rRecordCacheKeies.iterator();
			List<String> emptyKeies = new ArrayList<String>();
			while(its.hasNext()) {
				String key = its.next();
				Qrecord qrecord = (Qrecord)get(key);
				if(null == qrecord) {
					//已经空了 准备移除掉
					emptyKeies.add(key);
				}else {
					long timeOut = qrecord.getTimeOut();
					if(System.currentTimeMillis() > timeOut) {
						//已经超时了
						emptyKeies.add(key);
						remove(key);//移除缓存
					}
				}
			}
			//有移除的对象
			if(emptyKeies.size()>0) {
				rRecordCacheKeies.removeAll(emptyKeies);
			}
		}
		
		//清除过期微信code
		public static void clearWxCodeCacheKeies() {
			Set<Entry<String, String>> entrySet = wxCodeOpenRelations.entrySet();
			Iterator<Entry<String, String>> its = entrySet.iterator();
			while(its.hasNext()) {
				Entry<String, String> key = its.next();
				String cacheKey = key.getValue();//code
				String cacheValue = key.getKey();//openId
				WxCodeCache wxCodeCache = (WxCodeCache)get(cacheKey);
				if(null == wxCodeCache) {
					//已经空了 准备移除掉
					wxCodeOpenRelations.remove(cacheValue);
				}else {
					long timeOut = wxCodeCache.getTimeOut();
					if(System.currentTimeMillis() > timeOut) {
						//已经超时了
						its.remove();
						remove(cacheKey);//移除缓存
					}
				}
			}
		}
		
		//清除过期手机验证码
		public static void clearPhoneMsgCacheKeies() {
			Iterator<String> its = phoneMsgCacheKeies.iterator();
			List<String> emptyKeies = new ArrayList<String>();
			while(its.hasNext()) {
				String key = its.next();
				PhoneMsgCache phoneMsgCache = (PhoneMsgCache)get(key);
				if(null == phoneMsgCache) {
					//已经空了 准备移除掉
					emptyKeies.add(key);
				}else {
					long timeOut = phoneMsgCache.getTimeOut();
					if(System.currentTimeMillis() > timeOut) {
						//已经超时了
						emptyKeies.add(key);
						remove(key);//移除缓存
					}
				}
			}
			//有移除的对象
			if(emptyKeies.size()>0) {
				phoneMsgCacheKeies.removeAll(emptyKeies);
			}
		}
	
	/**
	 * 获取SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}
	
	/**
	 * 获取SYS_CACHE缓存
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String key, Object defaultValue) {
		Object value = get(key);
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 写入SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}
	
	/**
	 * 从SYS_CACHE缓存中移除
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		return getCache(cacheName).get(getKey(key));
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static Object get(String cacheName, String key, Object defaultValue) {
		Object value = get(cacheName, getKey(key));
		return value != null ? value : defaultValue;
	}
	
	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		getCache(cacheName).put(getKey(key), value);
	}

	/**
	 * 从缓存中移除
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).remove(getKey(key));
	}

	/**
	 * 从缓存中移除所有
	 * @param cacheName
	 */
	public static void removeAll(String cacheName) {
		Cache<String, Object> cache = getCache(cacheName);
		Set<String> keys = cache.keys();
		for (Iterator<String> it = keys.iterator(); it.hasNext();){
			cache.remove(it.next());
		}
		logger.info("清理缓存： {} => {}", cacheName, keys);
	}
	
	/**
	 * 获取缓存键名，多数据源下增加数据源名称前缀
	 * @param key
	 * @return
	 */
	private static String getKey(String key){
//		String dsName = DataSourceHolder.getDataSourceName();
//		if (StringUtils.isNotBlank(dsName)){
//			return dsName + "_" + key;
//		}
		return key;
	}
	
	/**
	 * 获得一个Cache，没有则显示日志。
	 * @param cacheName
	 * @return
	 */
	private static Cache<String, Object> getCache(String cacheName){
		Cache<String, Object> cache = cacheManager.getCache(cacheName);
		if (cache == null){
			throw new RuntimeException("当前系统中没有定义“"+cacheName+"”这个缓存。");
		}
		return cache;
	}

}
