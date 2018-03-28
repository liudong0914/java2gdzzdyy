package com.wkmk.util.cache;       
      
import redis.clients.jedis.Jedis;

import com.util.string.PublicResourceBundle;

/**
 * Redis缓存管理
 */ 
public class RedisManager {
	
    protected static RedisManager redisManager = new RedisManager(); 
    protected static String redis_server_open = "";//1表示开启缓存
    protected static String redis_server_auth = "";//缓存服务器秘钥，默认配置文件0表示没有秘钥
    protected static Jedis jedis = null;
    
    static {   
    	try{
    		redis_server_open = PublicResourceBundle.getProperty("system","redis.server.open");
		}catch(Exception e){
			redis_server_open = "";
		}
		
		try{
			redis_server_auth = PublicResourceBundle.getProperty("system","redis.server.auth");
		}catch(Exception e){
			redis_server_auth = "";
		}
    	
    	if("1".equals(redis_server_open)){
    		jedis = RedisClientPool.jedisPool.getResource();
    		if(redis_server_auth != null && !"".equals(redis_server_auth) && !"0".equals(redis_server_auth)){
    			jedis.auth(redis_server_auth);
    		}
    	}
    }      
    
    /** 
     * 保护型构造方法，不允许实例化！ 
     */  
    protected RedisManager() {
    }  
  
    /** 
     * 获取唯一实例. 
     */  
    public static RedisManager getInstance() {  
    	if("1".equals(redis_server_open)){
    		return redisManager;
    	}else{
    		return null;
    	}
    }
    
    /** 
     * 根据指定的关键字获取对象. 
     * @param key 
     */  
    public Object get(String key) {  
    	 byte[] value = jedis.get(key.getBytes());
    	 if(value == null){
    		 return null;
    	 }
         return SerializeUtil.unserialize(value);
    }
    
    /** 
     * 添加一个指定的值到缓存中. 
     * @param key 
     * @param value 
     */  
    public String set(String key, Object value) {
    	return jedis.set(key.getBytes(), SerializeUtil.serialize(value));
    }
  
    /** 
     * 添加一个指定的值到缓存中，并设置有限时间 
     * @param key 
     * @param value 
     * @param time 有效时间，单位：秒
     */ 
    public String set(String key, Object value, int time) {
    	String result = jedis.set(key.getBytes(), SerializeUtil.serialize(value));
    	jedis.expire(key, time);
        return result;
    }
    
    /**
     * 删除缓存值
     * @param key
     */
    public boolean delete(String key){
    	return jedis.del(key.getBytes()) > 0;
    }
    
}    