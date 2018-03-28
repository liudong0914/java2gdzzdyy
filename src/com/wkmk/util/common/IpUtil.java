package com.wkmk.util.common;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;


/**
 * <p>Description: IP地址工具类</p>
 * <p>E-mail: zhangxuedong28@gmail.com</p>
 * @Date Dec 12, 2012 3:08:38 PM
 */
public class IpUtil {

	/**
	 * 获取客户端真实IP地址
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {  
		String ipAddress = null;  
		ipAddress = request.getHeader("x-forwarded-for");  
	  	if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	  		ipAddress = request.getHeader("Proxy-Client-IP");  
	   	}  
	   	if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	   		ipAddress = request.getHeader("WL-Proxy-Client-IP");  
	  	}  
	 	if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {  
	 		ipAddress = request.getRemoteAddr();  
	     }
	 	if("127.0.0.1".equals(ipAddress)){
	 		//根据网卡取本机配置的IP  
    		InetAddress inet=null;  
		  	try {  
		    	inet = InetAddress.getLocalHost();  
		    } catch (Exception e) {  
		    	e.printStackTrace();  
		    }  
		    ipAddress= inet.getHostAddress();
	 	}
	  
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割  
	     if(ipAddress != null && ipAddress.length()>15){ //"***.***.***.***".length() = 15  
	    	 if(ipAddress.indexOf(",")>0){  
	         	ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));  
	         }  
	     }  
	     return ipAddress;  
	}
}