package com.wkmk.util.file;

import com.wkmk.edu.bo.EduCourseFile;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.zx.bo.ZxHelpFile;
import com.util.file.ConvertFilepathUtil;
import com.util.file.FileUtil;
import com.util.service.IConvertFileService;
import com.util.service.IConvertVideoService;
import com.util.service.WebService;
import com.util.string.PublicResourceBundle;

/**
 * <p>Description: 文档转码</p>
 * <p>E-mail: zhangxuedong28@gmail.com</p>
 * @Date Dec 12, 2012 3:08:38 PM
 */
public class ConvertFile {
	
	public static final String CONVERT_DOCUMENT = "(doc),(docx),(ppt),(pptx),(xls),(xlsx),(txt),(pdf),(pot),(pps),(rtf)";//避免类似于.do的文件进入转码服务器
	public static final String CONVERT_VIDEO = "(avi),(wmv),(3gp),(mov),(asf),(asx),(flv),(f4v),(mp4),(mpg),(mpeg),(vob),(mkv),(ts),(wmv9),(rm),(rmvb)";
	public static final String CONVERT_AUDIO = "(amr),(wma)";
	private static final String NOT_CONVERT_VIDEO = "(swf)";//此格式视频不能转码，也不能截图
	
	//当前系统是否开启转码服务
	public static String openconvertservice = null;
	static{
		if(openconvertservice == null){
			openconvertservice = PublicResourceBundle.getProperty("fileconvertserver", "open.convertservice");
		}
	}
	
	/**
	 * 视频转码
	 * @param model表示视频
	 * @param action update表示重新上传，需要把以前上传的先删除，否则转码文件会有已删除文件覆盖新上传文件
	 * @param level 0~9越大优先级越高
	 */
	public static void convertVod(VwhFilmPix model, String action, int level){
		try {
			String fileext = "(" + model.getFileext().toLowerCase() + ")";
			if(CONVERT_VIDEO.indexOf(fileext) >= 0){
				if("1".equals(openconvertservice)){
					model.setNotifystatus("0");
					model.setConvertstatus("0");
					IConvertVideoService videoService = WebService.getVideoService();
					if(!"add".equals(action)){
						videoService.delVideoFile("D-" + model.getPixid());
					}
					if(!"delete".equals(action)){
						String flvpath = ConvertFilepathUtil.getFlvpath(model.getSrcpath());
						String imgpath = ConvertFilepathUtil.getImgpath(model.getSrcpath());
						videoService.addVideoFile(model.getName()+"."+model.getFileext(), model.getFilesize().longValue(), model.getSrcpath(), flvpath, imgpath, model.getImgwidth(), model.getImgheight(), model.getSecond(), level, "B-" + model.getPixid(), "client");
						//model.setFlvpath(flvpath);
						//model.setImgpath(imgpath);//没有截图前，缩略图无法显示
					}
					model.setNotifystatus("1");
				}else {
					model.setNotifystatus("2");
					model.setConvertstatus("0");
				}
			}else if(NOT_CONVERT_VIDEO.indexOf(fileext) >= 0){
				//swf格式视频不能转码
				model.setConvertstatus("1");
				model.setNotifystatus("1");
			}else {
				//当文件格式不能进入转码服务器进行转码时，默认都是转换成功状态
				model.setConvertstatus("1");
				model.setNotifystatus("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//如果与转码服务器失去交互联系，则需要标示然后通过定时器重新与转码服务器交互
			model.setNotifystatus("2");
		}
	}
	
	/**
	 * 答疑提问附件实时转码
	 * @param model表示视频
	 * @param action update表示重新上传，需要把以前上传的先删除，否则转码文件会有已删除文件覆盖新上传文件
	 * @param level 0~9越大优先级越高
	 */
	public static void convertFile(ZxHelpFile model, String action){
		try {
			String fext = FileUtil.getFileExt(model.getFilepath());
			String fileext = "(" + fext.toLowerCase() + ")";
			if(CONVERT_VIDEO.indexOf(fileext) >= 0){
				if("1".equals(openconvertservice)){
					model.setNotifystatus("0");
					model.setConvertstatus("0");
					IConvertVideoService videoService = WebService.getVideoService();
					if(!"add".equals(action)){
						videoService.delVideoFile("C-" + model.getFileid());
					}
					if(!"delete".equals(action)){
						String flvpath = ConvertFilepathUtil.getFlvpath(model.getFilepath());
						String imgpath = ConvertFilepathUtil.getImgpath(model.getFilepath());
						model.setMp4path(flvpath);
						model.setThumbpath(imgpath);
						videoService.addConvertVideoFile("教师回答答疑视频."+fext, model.getFilesize().longValue(), model.getFilepath(), flvpath, imgpath, 0, 0, "1", 5, "C-" + model.getFileid(), "client", "0");
					}
					model.setNotifystatus("1");
					model.setConvertstatus("1");//先假设默认转码成功
				}else {
					model.setNotifystatus("2");
					model.setConvertstatus("0");
				}
			}else if(CONVERT_AUDIO.indexOf(fileext) >= 0){
				if("1".equals(openconvertservice)){
					model.setNotifystatus("0");
					model.setConvertstatus("0");
					IConvertVideoService videoService = WebService.getVideoService();
					if(!"add".equals(action)){
						videoService.delVideoFile("C-" + model.getFileid());
					}
					if(!"delete".equals(action)){
						String mp3path = ConvertFilepathUtil.getMp3path(model.getFilepath());
						model.setMp3path(mp3path);
						videoService.addConvertVideoFile("在线答疑音频."+fext, model.getFilesize().longValue(), model.getFilepath(), mp3path, "", 0, 0, "1", 5, "C-" + model.getFileid(), "client", "0");
					}
					model.setNotifystatus("1");
					model.setConvertstatus("1");//先假设默认转码成功
				}else {
					model.setNotifystatus("2");
					model.setConvertstatus("0");
				}
			}else {
				//当文件格式不能进入转码服务器进行转码时，默认都是转换成功状态
				model.setConvertstatus("1");
				model.setNotifystatus("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//如果与转码服务器失去交互联系，则需要标示然后通过定时器重新与转码服务器交互
			model.setNotifystatus("2");
		}
	}
	
	/**
	 * 课程附件转码
	 * @param model表示视频
	 * @param action update表示重新上传，需要把以前上传的先删除，否则转码文件会有已删除文件覆盖新上传文件
	 * @param level 0~9越大优先级越高
	 */
	public static void convertCourseFile(EduCourseFile model, String action, int level){
		try {
			String fext = FileUtil.getFileExt(model.getFilepath());
			String fileext = "(" + fext.toLowerCase() + ")";
			if(CONVERT_DOCUMENT.indexOf(fileext) >= 0){
				if("1".equals(openconvertservice)){
					model.setNotifystatus("0");
					model.setConvertstatus("0");
					IConvertFileService docService = WebService.getDocService();
					if(!"add".equals(action)){
						docService.delDocFile("D-" + model.getFileid());
					}
					if(!"delete".equals(action)){
						docService.addDocFile(model.getFilename()+"."+model.getFileext(), model.getFilesize().longValue(), model.getFilepath(), level, "1", "D-" + model.getFileid());
					}
					model.setNotifystatus("1");
				}else {
					model.setNotifystatus("2");
					model.setConvertstatus("0");
				}
			}else {
				//当文件格式不能进入转码服务器进行转码时，默认都是转换成功状态
				model.setConvertstatus("1");
				model.setNotifystatus("1");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//如果与转码服务器失去交互联系，则需要标示然后通过定时器重新与转码服务器交互
			model.setNotifystatus("2");
		}
	}
}
