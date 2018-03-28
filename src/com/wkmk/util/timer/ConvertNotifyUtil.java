package com.wkmk.util.timer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wkmk.util.file.ConvertFile;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhFilmPixManager;
import com.wkmk.zx.bo.ZxHelpFile;
import com.wkmk.zx.service.ZxHelpFileManager;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.PublicResourceBundle;

/**
 * 定时与服务器交互
 */
public class ConvertNotifyUtil {

	private static ApplicationContext ctx = null;
	private ServletContext context = null;
	
	//当前系统是否开启转码服务
	public static String openconvertservice = null;
	static{
		if(openconvertservice == null){
			openconvertservice = PublicResourceBundle.getProperty("fileconvertserver", "open.convertservice");
		}
	}
	
	public ConvertNotifyUtil(ServletContext context){
		this.context = context;
	}
	
	private Object getBean(String name){
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		}
		return ctx.getBean(name);
	}
	
	/**
	 * 重新与转码服务器交互进行转码
	 */
	public void reConvertFile(){
		if("1".equals(openconvertservice)){
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "notifystatus", "=", "2");//交互失败
			
			//1微课视频
			VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
			List vodList = vfpm.getVwhFilmPixs(condition, "createdate asc", 0);
			VwhFilmPix vod = null;
			for(int i=0, size=vodList.size(); i<size; i++){
				vod = (VwhFilmPix) vodList.get(i);
				//重新转码
				ConvertFile.convertVod(vod, "update", 0);
				vfpm.updateVwhFilmPix(vod);
			}
			
			//2在线答疑附件
			ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
			List fileList = zhfm.getZxHelpFiles(condition, "createdate asc", 1000);
			ZxHelpFile file = null;
			for(int i=0, size=fileList.size(); i<size; i++){
				file = (ZxHelpFile) fileList.get(i);
				//重新转码
				ConvertFile.convertFile(file, "update");
				zhfm.updateZxHelpFile(file);
			}
		}
	}
}
