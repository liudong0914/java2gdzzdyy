package com.wkmk.util.cache;       
      
import java.util.Date;       

import com.danga.MemCached.MemCachedClient;       
import com.danga.MemCached.SockIOPool;       
import com.util.string.PublicResourceBundle;

/**
 * memCached缓存管理
 */ 
public class MemCachedManager {  
	
    protected static MemCachedClient memCachedClient = new MemCachedClient();          
    protected static MemCachedManager memCachedManager = new MemCachedManager(); 
    protected static String memcached_server_open = "";//1表示开启缓存
    protected static String memcached_server_url = "";//缓存服务器地址
    protected static String memcached_server_weights = "";//缓存服务器权重，和服务器地址数量对应
    
    static {   
    	try{
    		memcached_server_open = PublicResourceBundle.getProperty("system","memcached.server.open");
		}catch(Exception e){
			memcached_server_open = "";
		}
		
    	try{
    		memcached_server_url = PublicResourceBundle.getProperty("system","memcached.server.url");
		}catch(Exception e){
			memcached_server_url = "";
		}
		
		try{
			memcached_server_weights = PublicResourceBundle.getProperty("system","memcached.server.weights");
		}catch(Exception e){
			memcached_server_weights = "";
		}
    	
    	if("1".equals(memcached_server_open)){
    		//servers:127.0.0.1:11211,127.0.0.1:11212
    		//weights:3,5
	        String[] servers ={memcached_server_url};          
	        String[] weight = memcached_server_weights.split(",");
	        Integer[] weights = new Integer[weight.length];
	        for(int i=0, size=weight.length; i<size; i++){
	        	weights[i] = Integer.valueOf(weight[i]);
	        }
	          
	        //创建一个实例对象SockIOPool        
	        SockIOPool pool = SockIOPool.getInstance();        
	          
	        //设置服务器信息 
	        pool.setServers(servers);          
	        pool.setWeights(weights);          
	          
	        //设置初始连接数、最小和最大连接数以及最大处理时间         
	        pool.setInitConn(5);          
	        pool.setMinConn(5);          
	        pool.setMaxConn(2000);          
	        pool.setMaxIdle(1000 * 60 * 60 * 24 * 30);//30天，memcache最大缓存时间为30天
	          
	        //设置主线程的睡眠时间         
	        pool.setMaintSleep(30);          
	          
	        // Tcp的规则就是在发送一个包之前，本地机器会等待远程主机       
	        // 对上一次发送的包的确认信息到来；这个方法就可以关闭套接字的缓存，       
	        // 以至这个包准备好了就发；       
	        pool.setNagle(false);          
	        //连接建立后对超时的控制       
	        pool.setSocketTO(3000);       
	        //连接建立时对超时的控制       
	        pool.setSocketConnectTO(0);          
	          
	        // initialize the connection pool          
	        //初始化一些值并与MemcachedServer段建立连接       
	        pool.initialize();       
	          
	        //压缩设置，超过指定大小（单位为K）的数据都会被压缩         
	        memCachedClient.setCompressEnable(true);          
	        memCachedClient.setCompressThreshold(64 * 1024);          
    	}
    }      
    
    /** 
     * 保护型构造方法，不允许实例化！ 
     */  
    protected MemCachedManager() {
    }  
  
    /** 
     * 获取唯一实例. 
     */  
    public static MemCachedManager getInstance() {  
    	if("1".equals(memcached_server_open)){
    		return memCachedManager;
    	}else{
    		return null;
    	}
    }
    
    /** 
     * 根据指定的关键字获取对象. 
     * @param key 
     */  
    public Object get(String key) {  
        return memCachedClient.get(key);  
    }
    
    /** 
     * 添加一个指定的值到缓存中. 
     * @param key 
     * @param value 
     */  
    public boolean set(String key, Object value) {  
        return memCachedClient.set(key, value);
    }
  
    public boolean set(String key, Object value, Date expiry) {
        return memCachedClient.set(key, value, expiry);
    }
    
    /**
     * 删除缓存值
     * @param key
     */
    public boolean delete(String key){
    	return memCachedClient.delete(key);
    }
    
    public boolean delete(String key, Date expiry){
    	return memCachedClient.delete(key, expiry);
    }
    
//    /*
//     * add	仅当存储空间中不存在键相同的数据时才保存
//     * replace	仅当存储空间中存在键相同的数据时才保存
//     * set	与add和replace不同，无论何时都保存
//     */
//    /** 
//     * 添加一个指定的值到缓存中. 
//     * @param key 
//     * @param value 
//     */  
//    public boolean add(String key, Object value) {  
//        return memCachedClient.add(key, value);  
//    }
//  
//    public boolean add(String key, Object value, Date expiry) {  
//        return memCachedClient.add(key, value, expiry);
//    }
//    
//    /**
//     * 更新缓存值
//     * @param key
//     * @param value
//     * @return
//     */
//	  public boolean replace(String key, Object value) {
//        return memCachedClient.replace(key, value);  
//	  }  
//  
//    public boolean replace(String key, Object value, Date expiry) {
//    	  return memCachedClient.replace(key, value, expiry);  
//    }
    
    /*
    public static void main(String[] args){
    	MemCachedManager cache = MemCachedManager.getInstance();
    	cache.set("aaa", "我是字符串", new Date(12*60*60*1000));
    	String str = (String)cache.get("aaa");
    	System.out.println(str);
    	
    	List list = new ArrayList();
    	list.add("111111111111");
    	cache.set("list", list);
    	List lst = (List) cache.get("list");
    	System.out.println(lst.get(0));
    	
    	TkQuestionsInfo tqi = new TkQuestionsInfo();
    	tqi.setQuestionid(1);
    	tqi.setQuestionno("22222222222222");
    	cache.set("TkQuestionsInfo", tqi);
    	TkQuestionsInfo tqi1 = (TkQuestionsInfo) cache.get("TkQuestionsInfo");
    	System.out.println(tqi1.getQuestionno());
    	
    	List list2 = new ArrayList();
    	list2.add(tqi);
    	cache.set("list2", list2, new Date(24*24*60*60*1000));//注意：时间设置超过这个将无法放入到缓存中
    	List lst2 = (List) cache.get("list2");
    	System.out.println(lst2);
    }
    */      
}    