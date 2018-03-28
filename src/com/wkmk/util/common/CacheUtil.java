package com.wkmk.util.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.util.search.PageList;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkPaperInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.util.cache.MemCachedManager;
import com.wkmk.util.cache.RedisManager;
import com.wkmk.vwh.bo.VwhComputerInfo;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 缓存工具【Ehcache缓存】
 */
public class CacheUtil {
	
	private static MemCachedManager memCachedManager = MemCachedManager.getInstance();
	private static RedisManager redisManager = RedisManager.getInstance();

	//***************************建议缓存数据都用如下方法************************
	/**
	 * 以分钟为单位进行数据列表缓存
	 * @param key 键
	 * @param object 值
	 * @param seconds 缓存有效期，单位：秒
	 */
	public static void putObject(String key, Object object, int seconds){
		if(redisManager != null){
			redisManager.set(key, object, seconds);	
		}else if(memCachedManager != null){
			if(seconds > 24*24*60*60){
				memCachedManager.set(key, object);
			}else{
				memCachedManager.set(key, object, new Date(seconds*1000));
			}
		}else {
			Cache cache = null;
			//判断object类型
			if((object instanceof List) || (object instanceof Map) || (object instanceof PageList)){
				cache = CacheManager.getInstance().getCache("dataListCache");
			}else {
				cache = CacheManager.getInstance().getCache("baseInfoCache");
			}
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, object);
			e.setEternal(false);
			e.setTimeToIdle(seconds);
			e.setTimeToLive(seconds);
			cache.put(e);
		}
	}
	
	/**
	 * 获取缓存对象数据
	 */
	public static Object getObject(String key){
		Object object = null;
		if(redisManager != null){
			object = redisManager.get(key);	
		}else if(memCachedManager != null){
			object = memCachedManager.get(key);
		}else{
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				object = (Object) e.getValue();
			}
			if(object == null){
				cache = CacheManager.getInstance().getCache("baseInfoCache");
				e = (Element) cache.get(key);
				if (e != null) {
					object = (Object) e.getValue();
				}
			}
		}
		return object;
	}
	
	/*
	 * 删除缓存
	 */
	public static void deleteObject(String key){
		if(redisManager != null){
			redisManager.delete(key);	
		}else if(memCachedManager != null){
			memCachedManager.delete(key);
		}else {
			//删除对象
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, null);
			e.setEternal(false);
			e.setTimeToIdle(1);//1秒
			e.setTimeToLive(1);//1秒
			cache.put(e);
			//删除集合
			cache = CacheManager.getInstance().getCache("baseInfoCache");
			e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, null);
			e.setEternal(false);
			e.setTimeToIdle(1);//1秒
			e.setTimeToLive(1);//1秒
			cache.put(e);
		}
	}
	//***************************建议获取缓存数据都用如上方法************************
	
	public static void put(String key, String value){
		if(redisManager != null){
			redisManager.set(key, value, 12*60*60);
		}else if(memCachedManager != null){
			memCachedManager.set(key, value, new Date(12*60*60*1000)); 
		}else {
			put(key, value, 12);//默认12小时
		}
	}
	
	/*
	 * 以小时为单位进行数据对象缓存
	 */
	public static void put(String key, String value, int hours){
		if(redisManager != null){
			redisManager.set(key, value, hours*60*60);	
		}else if(memCachedManager != null){
			if(hours > 24*24){
				memCachedManager.set(key, value);
			}else{
				memCachedManager.set(key, value, new Date(hours*60*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, value);
			e.setEternal(false);
			e.setTimeToIdle(3600*hours);
			e.setTimeToLive(3600*hours);
			cache.put(e);
		}
	}
	
	/*
	 * 以分钟为单位进行数据对象缓存
	 */
	public static void putOfMinutes(String key, String value, int minutes){
		if(redisManager != null){
			redisManager.set(key, value, minutes*60);	
		}else if(memCachedManager != null){
			if(minutes > 24*24*60){
				memCachedManager.set(key, value);
			}else{
				memCachedManager.set(key, value, new Date(minutes*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, value);
			e.setEternal(false);
			e.setTimeToIdle(60*minutes);
			e.setTimeToLive(60*minutes);
			cache.put(e);
		}
	}
	
	public static String get(String key){
		String value = null;
		if(redisManager != null){
			value = (String) redisManager.get(key);	
		}else if(memCachedManager != null){
			value = (String) memCachedManager.get(key);
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				value = (String) e.getValue();
			}
		}
		return value;
	}
	
	/*
	 * 以小时为单位进行数据列表缓存
	 */
	public static void putList(String key, List list, int hours){
		if(redisManager != null){
			redisManager.set(key, list, hours*60*60);	
		}else if(memCachedManager != null){
			if(hours > 24*24){
				memCachedManager.set(key, list);
			}else{
				memCachedManager.set(key, list, new Date(hours*60*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, list);
			e.setEternal(false);
			e.setTimeToIdle(3600*hours);
			e.setTimeToLive(3600*hours);
			cache.put(e);
		}
	}
	
	/*
	 * 以分钟为单位进行数据列表缓存
	 */
	public static void putListOfMinutes(String key, List list, int minutes){
		if(redisManager != null){
			redisManager.set(key, list, minutes*60);	
		}else if(memCachedManager != null){
			if(minutes > 24*24*60){
				memCachedManager.set(key, list);
			}else{
				memCachedManager.set(key, list, new Date(minutes*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, list);
			e.setEternal(false);
			e.setTimeToIdle(60*minutes);
			e.setTimeToLive(60*minutes);
			cache.put(e);
		}
	}
	
	public static List getList(String key){
		List list = null;
		if(redisManager != null){
			list = (List) redisManager.get(key);	
		}else if(memCachedManager != null){
			list = (List) memCachedManager.get(key);
		}else {
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				list = (List) e.getValue();
			}
		}
		return list;
	}
	
	/*
	 * 以分钟为单位进行数据列表缓存
	 */
	public static void putPageListOfMinutes(String key, PageList pageList, int minutes){
		if(redisManager != null){
			redisManager.set(key, pageList, minutes*60);	
		}else if(memCachedManager != null){
			if(minutes > 24*24*60){
				memCachedManager.set(key, pageList);
			}else{
				memCachedManager.set(key, pageList, new Date(minutes*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, pageList);
			e.setEternal(false);
			e.setTimeToIdle(60*minutes);
			e.setTimeToLive(60*minutes);
			cache.put(e);
		}
	}
	
	public static PageList getPageList(String key){
		PageList pageList = null;
		if(redisManager != null){
			pageList = (PageList) redisManager.get(key);	
		}else if(memCachedManager != null){
			pageList = (PageList) memCachedManager.get(key);
		}else{
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				pageList = (PageList) e.getValue();
			}
		}
		return pageList;
	}
	
	/*
	 * 以分钟为单位进行数据列表缓存
	 */
	public static void putMapOfMinutes(String key, Map map, int minutes){
		if(redisManager != null){
			redisManager.set(key, map, minutes*60);	
		}else if(memCachedManager != null){
			if(minutes > 24*24*60){
				memCachedManager.set(key, map);
			}else{
				memCachedManager.set(key, map, new Date(minutes*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, map);
			e.setEternal(false);
			e.setTimeToIdle(60*minutes);
			e.setTimeToLive(60*minutes);
			cache.put(e);
		}
	}
	
	public static Map getMap(String key){
		Map map = null;
		if(redisManager != null){
			map = (Map) redisManager.get(key);	
		}else if(memCachedManager != null){
			map = (Map) memCachedManager.get(key);
		}else{
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				map = (Map) e.getValue();
			}
		}
		return map;
	}
	
	/*
	 * 删除缓存
	 */
	public static void deleteList(String key){
		if(redisManager != null){
			redisManager.delete(key);	
		}else if(memCachedManager != null){
			memCachedManager.delete(key);
		}else {
			Cache cache = CacheManager.getInstance().getCache("dataListCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, null);
			e.setEternal(false);
			e.setTimeToIdle(1);//1秒
			e.setTimeToLive(1);//1秒
			cache.put(e);
		}
	}
	
	/*********************************************************************************************************
	 * 获取作业本内容信息
	 */
	public static TkBookContent getTkBookContent(String key){
		TkBookContent value = null;
		if(redisManager != null){
			value = (TkBookContent) redisManager.get(key);	
		}else if(memCachedManager != null){
			value = (TkBookContent) memCachedManager.get(key);
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				value = (TkBookContent) e.getValue();
			}
		}
		return value;
	}
	
	/*
	 * 以小时为单位进行数据对象缓存
	 */
	public static void put(String key, TkBookContent value, int hours){
		if(redisManager != null){
			redisManager.set(key, value, hours*60*60);	
		}else if(memCachedManager != null){
			if(hours > 24*24){
				memCachedManager.set(key, value);
			}else{
				memCachedManager.set(key, value, new Date(hours*60*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, value);
			e.setEternal(false);
			e.setTimeToIdle(3600*hours);
			e.setTimeToLive(3600*hours);
			cache.put(e);
		}
	}
	
	/*
	 * 获取试卷信息
	 */
	public static TkPaperInfo getTkPaperInfo(String key){
		TkPaperInfo value = null;
		if(redisManager != null){
			value = (TkPaperInfo) redisManager.get(key);	
		}else if(memCachedManager != null){
			value = (TkPaperInfo) memCachedManager.get(key);
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				value = (TkPaperInfo) e.getValue();
			}
		}
		return value;
	}
	
	/*
	 * 以小时为单位进行数据对象缓存
	 */
	public static void put(String key, TkPaperInfo value, int hours){
		if(redisManager != null){
			redisManager.set(key, value, hours*60*60);	
		}else if(memCachedManager != null){
			if(hours > 24*24){
				memCachedManager.set(key, value);
			}else{
				memCachedManager.set(key, value, new Date(hours*60*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, value);
			e.setEternal(false);
			e.setTimeToIdle(3600*hours);
			e.setTimeToLive(3600*hours);
			cache.put(e);
		}
	}
	
	/*
	 * 获取试题信息
	 */
	public static TkQuestionsInfo getTkQuestionsInfo(String key){
		TkQuestionsInfo value = null;
		if(redisManager != null){
			value = (TkQuestionsInfo) redisManager.get(key);	
		}else if(memCachedManager != null){
			value = (TkQuestionsInfo) memCachedManager.get(key);
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				value = (TkQuestionsInfo) e.getValue();
			}
		}
		return value;
	}
	
	/*
	 * 以小时为单位进行数据对象缓存
	 */
	public static void put(String key, TkQuestionsInfo value, int hours){
		if(redisManager != null){
			redisManager.set(key, value, hours*60*60);	
		}else if(memCachedManager != null){
			if(hours > 24*24){
				memCachedManager.set(key, value);
			}else{
				memCachedManager.set(key, value, new Date(hours*60*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, value);
			e.setEternal(false);
			e.setTimeToIdle(3600*hours);
			e.setTimeToLive(3600*hours);
			cache.put(e);
		}
	}
	
	/*
	 * 获取试卷内容信息
	 */
	public static TkPaperContent getTkPaperContent(String key){
		TkPaperContent value = null;
		if(redisManager != null){
			value = (TkPaperContent) redisManager.get(key);	
		}else if(memCachedManager != null){
			value = (TkPaperContent) memCachedManager.get(key);
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				value = (TkPaperContent) e.getValue();
			}
		}
		return value;
	}
	
	/*
	 * 以小时为单位进行数据对象缓存
	 */
	public static void put(String key, TkPaperContent value, int hours){
		if(redisManager != null){
			redisManager.set(key, value, hours*60*60);	
		}else if(memCachedManager != null){
			if(hours > 24*24){
				memCachedManager.set(key, value);
			}else{
				memCachedManager.set(key, value, new Date(hours*60*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, value);
			e.setEternal(false);
			e.setTimeToIdle(3600*hours);
			e.setTimeToLive(3600*hours);
			cache.put(e);
		}
	}
	
	/*
	 * 获取视频服务器信息
	 */
	public static VwhComputerInfo getVwhComputerInfo(String key){
		VwhComputerInfo value = null;
		if(redisManager != null){
			value = (VwhComputerInfo) redisManager.get(key);	
		}else if(memCachedManager != null){
			value = (VwhComputerInfo) memCachedManager.get(key);
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			if (e != null) {
				value = (VwhComputerInfo) e.getValue();
			}
		}
		return value;
	}
	
	/*
	 * 以小时为单位进行数据对象缓存
	 */
	public static void put(String key, VwhComputerInfo value, int hours){
		if(redisManager != null){
			redisManager.set(key, value, hours*60*60);	
		}else if(memCachedManager != null){
			if(hours > 24*24){
				memCachedManager.set(key, value);
			}else{
				memCachedManager.set(key, value, new Date(hours*60*60*1000));
			}
		}else {
			Cache cache = CacheManager.getInstance().getCache("baseInfoCache");
			Element e = (Element) cache.get(key);
			//缓存数据有效期1天
			e = new Element(key, value);
			e.setEternal(false);
			e.setTimeToIdle(3600*hours);
			e.setTimeToLive(3600*hours);
			cache.put(e);
		}
	}
	
	public static void main(String[] args){
    	CacheUtil.putObject("key1", "我是字符串", 1000);
    	System.out.println(CacheUtil.getObject("key1"));
    	
    	TkBookContent content = new TkBookContent();
    	content.setBookcontentid(1);
    	content.setTitle("测试作业本内容");
    	CacheUtil.putObject("key2", content, 10000);
    	System.out.println(((TkBookContent)CacheUtil.getObject("key2")).getTitle());
    	
    	ArrayList list = new ArrayList();
    	list.add("list测试数据1");
    	list.add("list测试数据2");
    	CacheUtil.putObject("key3", list, 1000);
    	System.out.println(CacheUtil.getObject("key3").toString());
    	
    	list.add("list测试数据3");
    	PageList pageList = new PageList(list, 0, 10, 0);
    	CacheUtil.putObject("key4", pageList, 5000);
    	System.out.println(((PageList)CacheUtil.getObject("key4")).getDatalist().toString());
    	
    	Map map = new HashMap();
    	map.put("a", "map数据1");
    	map.put("b", "map数据2");
    	CacheUtil.putObject("key5", map, 5000);
    	System.out.println(((Map)CacheUtil.getObject("key5")).get("a"));
    	
    	//CacheUtil.putObject("key6", map, 5);
    	//System.out.println(CacheUtil.getObject("key6").toString());
    }
}
