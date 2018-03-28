package com.wkmk.util.cache;

import com.util.string.PublicResourceBundle;

import redis.clients.jedis.JedisPool;  
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Description:Redis连接池
 * @author 张学栋 <zhangxuedong28@163.com>
 * @version 1.0
 * @Date Apr 8, 201611:49:14 AM
 */
public class RedisClientPool {

	public static RedisClientPool redisClientPool = getInstance();  
    public static JedisPool jedisPool;
    
    protected static String redis_server_url = "";//缓存服务器地址
    protected static int redis_server_port = 6379;//缓存服务器端口
    protected static String redis_server_auth = "";//缓存服务器秘钥，默认配置文件0表示没有秘钥
    protected static int redis_server_timeout = 1000;
    
    private static void initRedisConfig(){    
    	try{
    		redis_server_url = PublicResourceBundle.getProperty("system","redis.server.url");
		}catch(Exception e){
			redis_server_url = "";
		}
		
		try{
			redis_server_port = Integer.valueOf(PublicResourceBundle.getProperty("system","redis.server.port")).intValue();
		}catch(Exception e){
			redis_server_port = 6379;
		}
		
		try{
			redis_server_auth = PublicResourceBundle.getProperty("system","redis.server.auth");
		}catch(Exception e){
			redis_server_auth = "";
		}
		
		try{
			redis_server_timeout = Integer.valueOf(PublicResourceBundle.getProperty("system","redis.server.timeout")).intValue();
		}catch(Exception e){
			redis_server_timeout = 1000;
		}
    }
      
    public static synchronized RedisClientPool getInstance(){  
        if (redisClientPool == null){ 
            redisClientPool = new RedisClientPool();  
        }  
        return redisClientPool;  
    }  
      
    public RedisClientPool(){  
        if (jedisPool == null){ 
            init();  
        }  
    }  
      
    /** 
     *初始化Jedis 
     */  
    private static JedisPoolConfig initPoolConfig(){  
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();  
        jedisPoolConfig.setMaxTotal(1000);
        // 控制一个pool最多有多少个状态为idle（空闲，闲置）的jedis实例  
        // 最大能够保持空闲状态的对象数  
        jedisPoolConfig.setMaxIdle(300);  
        jedisPoolConfig.setMinIdle(5);
        // 超时时间  
        jedisPoolConfig.setMaxWaitMillis(5000);  
        // 在borrow一个jedis实例时，是否提前进行alidate操作；如果为true，则得到的jedis实例均是可用的；  
        jedisPoolConfig.setTestOnBorrow(true);  
        // 在还会给pool时，是否提前进行validate操作  
        jedisPoolConfig.setTestOnReturn(true);  
          
        return jedisPoolConfig;  
    }  
      
    /** 
     * 初始化jedis连接池 
     */  
    public static void init(){  
        JedisPoolConfig jedisPoolConfig = initPoolConfig();
        initRedisConfig();
        // 构造连接池  
        jedisPool = new JedisPool(jedisPoolConfig, redis_server_url, redis_server_port, redis_server_timeout);//timeout读取超时
    }  
}
