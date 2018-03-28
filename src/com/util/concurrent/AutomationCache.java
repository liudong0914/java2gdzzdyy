package com.util.concurrent;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.util.socket.client.SocketClient;

/**
 * 自动化订单缓存处理，并发缓存处理【定时监听处理】
 */
public class AutomationCache {
	
	private static final String PRODUCT = "lmzyb";
	
	/*
	 * AutomationCache<K, V>
	 * 键值说明：值为1时，表示键是SysPaymentAccount主键
	 * 当服务停止或重启时，数据丢失，需要记录到数据库中，然后再销毁时再从数据库中删除。当重启服务时，一次性把数据库记录的数据全部取出，然后对比有效期和当前时间，如果已到时间，直接销毁
	 */
	protected static AutomationCache cache = new AutomationCache();
    
	/**
     * @param k 缓存键
     * @param v 缓存值
     * @param liveTime 缓存有效时间，单位：毫秒
     */
    public void put(Integer k,Integer v,long liveTime){
    	put(k.toString(), v.toString(), liveTime);
    }
    /**
     * @param k 缓存键
     * @param v 缓存值
     * @param liveTime 缓存有效时间，单位：毫秒
     */
    public void put(String k,String v,long liveTime){
    	StringBuffer str = new StringBuffer();
    	str.append(PRODUCT);
    	str.append("_");
    	str.append(k);
    	str.append("_");
    	str.append(v);
    	str.append("_");
    	str.append(liveTime);
    	str.append("_put");
    	String clientString = str.toString();
    	
    	//通过socket传递数据
    	new SocketClient(clientString);
    }
    
    /**
     * @param k 缓存键
     * @param v 缓存值
     * @param enddate 缓存有效结束时间，如：2016-12-30 23:59:59
     */
    public void put(Integer k,Integer v,String enddate){
    	put(k.toString(), v.toString(), enddate);
    }
    
    /**
     * @param k 缓存键
     * @param v 缓存值
     * @param enddate 缓存有效结束时间，如：2016-12-30 23:59:59
     */
    public void put(String k,String v,String enddate){
    	try {
    		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	Date date = format.parse(enddate);
        	long liveTime = date.getTime() - System.currentTimeMillis();
        	
        	StringBuffer str = new StringBuffer();
        	str.append(PRODUCT);
        	str.append("_");
        	str.append(k);
        	str.append("_");
        	str.append(v);
        	str.append("_");
        	str.append(liveTime);
        	str.append("_put");
        	String clientString = str.toString();
        	
        	//通过socket传递数据
        	new SocketClient(clientString);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    /**
     * @param 删除缓存值
     */
    public void remove(Integer k){
    	remove(k.toString());
    }
    
    /**
     * @param 删除缓存值
     */
    public void remove(String k){
    	StringBuffer str = new StringBuffer();
    	str.append(PRODUCT);
    	str.append("_");
    	str.append(k);
    	str.append("_0_0_remove");
    	String clientString = str.toString();
    	
    	//通过socket传递数据
    	new SocketClient(clientString);
    }
    
    protected AutomationCache(){
    }
    
    /** 
     * 获取唯一实例. 
     */  
    public static AutomationCache getInstance() {
    	if(cache == null){
    		cache = new AutomationCache();
    	}
        return cache;
    }
}
