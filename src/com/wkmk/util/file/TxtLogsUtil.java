package com.wkmk.util.file;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter; 

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
  
import com.util.date.DateTime;
import com.wkmk.util.common.IpUtil;
 
public class TxtLogsUtil {

	private static TxtLogsUtil instance = null;
	private static ServletContext context = null;
 
	public static TxtLogsUtil getInstance(){
		if(instance==null){
			instance = new TxtLogsUtil();
		}
		return instance;
	}
	
	private ServletContext getServletContext(HttpServletRequest request){
		if(context == null){
			context = request.getSession().getServletContext();
		}
		return context;
	}
	
	/**
	 * 记录访问IP地址和访问时间
	 */
	public void addSQLTxt(HttpServletRequest request,String requestURI) {
		String ipAddress = IpUtil.getIpAddr(request);
		FileWriter fw = null;
		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件 
			String path = getServletContext(request).getRealPath("/") + "logs/SQLInjection/";
			String filename = "logs" + DateTime.getDate("yyyy-MM-dd") + ".txt"; 
			File f = new File(path+filename); 
			fw = new FileWriter(f, true); 
			PrintWriter pw = new PrintWriter(fw);
			pw.println("访问IP地址是：" + ipAddress + "==||==时间：" + DateTime.getDate() + "==||==路径及参数：" + requestURI);
			pw.println("--------------------------------------------------------------------------------------------------------------");
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 
}
