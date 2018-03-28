package com.util.service.impl;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wkmk.edu.bo.EduCourseFile;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.zx.bo.ZxHelpFile;
import com.util.dao.BaseDAO;
import com.util.file.ConvertFilepathUtil;
import com.util.image.ImageUtil;
import com.util.service.IFileConvertClientService;

/**
 * 文件转换客户端接口实现类
 * @author 张学栋
 * @version 1.0
 */
public class IFileConvertClientServiceImpl implements IFileConvertClientService, ServletContextAware {

	private BaseDAO baseDAO;
	private ServletContext servletContext;
	private static ApplicationContext ctx = null;
	
	/**
	 * 加载baseDAO
	 * @param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}
	
	public void setServletContext(ServletContext context) {
		this.servletContext = context;
	}
	
	private Object getBean(String name){
		if (ctx == null) {
			ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		}
		return ctx.getBean(name);
	}
	
	/**
	 * 修改文件转换状态
	 * linkid规则：A-1000001  B-1001
	 * A表示文库文档(dwh_doc_info)，B表示视频点播(vwh_film_pix)
	 * 默认根据对象id获取对象时，如果为空默认返回成功。【web工程删除当前资源，但转码服务器上还存在此资源】
	 * @param linkid 对应传给转码服务器的连接id
	 * @param state 文件转码状态 true表示转码成功，false表示转码失败
	 * @param resultstr 如果是文档，则表示文档的总页数，如果是视频，则表示视频的总时长和视频分辨率,用分号“;”隔开
	 * @return true表示交互成功，false表示交互失败
	 */
	public boolean modifyFileConvertState(String linkid, boolean state, String resultstr){
		boolean result = false;
		try {
			if(linkid == null || "".equals(linkid)) return false;
			String[] linkids = linkid.split("-");
			if(linkids == null || linkids.length != 2) return false;
			
			String flag = linkids[0];
			Integer objid = Integer.valueOf(linkids[1]);
			if("A".equals(flag)){
			}
			if("B".equals(flag)){
				VwhFilmPix model = (VwhFilmPix) baseDAO.getObject("微课视频", VwhFilmPix.class, objid);
				if(model == null) return true;
				
				if(state){//成功
					//路径提前生成好，然后把路径传递给转码服务器，转码服务器只负责按指定路径生成转码文件【此处生成的路径和传递给转码服务器生成的路径保持一致】
					model.setImgpath(ConvertFilepathUtil.getImgpath(model.getSrcpath()));
					//此处缩略图最好通过配置文件动态配置，不需要修改代码
					/*
					//生成首页指定大小的缩略图160*213，或手机指定大小的缩略图144*72
					String realpath = servletContext.getRealPath("/");
					File file = new File(realpath + "/upload/" + model.getImgpath());
					if(file.exists() && file.isFile()){
						String imgpath1 = model.getImgpath().substring(0, model.getImgpath().lastIndexOf("/") + 1) + "160x213_" + model.getImgpath().substring(model.getImgpath().lastIndexOf("/") + 1);
						ImageUtil.generateThumbnails(realpath + "/upload/" + model.getImgpath(), realpath + "/upload/" + imgpath1, 160, 214, true);
						model.setImgpath(imgpath1);
					}
					*/
					model.setFlvpath(ConvertFilepathUtil.getFlvpath(model.getSrcpath()));
					String[] propertys = resultstr.split(";");
					model.setTimelength(propertys[0]);
					model.setResolution(propertys[1]);
					model.setConvertstatus("1");
					baseDAO.updateObject("微课视频", model);
				}else {//失败
					model.setConvertstatus("2");
					baseDAO.updateObject("微课视频", model);
				}
				
				result = true;
			}
			if("C".equals(flag)){
				ZxHelpFile model = (ZxHelpFile) baseDAO.getObject("在线答疑附件", ZxHelpFile.class, objid);
				if(model == null) return true;
				
				if(state){//成功
					//路径提前生成好，然后把路径传递给转码服务器，转码服务器只负责按指定路径生成转码文件【此处生成的路径和传递给转码服务器生成的路径保持一致】
					if("2".equals(model.getFiletype())){
						model.setMp3path(ConvertFilepathUtil.getMp3path(model.getFilepath()));
					}else if("3".equals(model.getFiletype())){
						model.setMp4path(ConvertFilepathUtil.getFlvpath(model.getFilepath()));
						model.setThumbpath(ConvertFilepathUtil.getImgpath(model.getFilepath()));
					}
					model.setConvertstatus("1");
					baseDAO.updateObject("在线答疑附件", model);
				}else {//失败
					model.setConvertstatus("2");
					baseDAO.updateObject("在线答疑附件", model);
				}
				
				result = true;
			}
			if("D".equals(flag)){
				int pagenum = Integer.valueOf(resultstr).intValue();
				if(pagenum < 0) pagenum = 0;
				
				EduCourseFile model = (EduCourseFile) baseDAO.getObject("课程附件", EduCourseFile.class, objid);
				if(model == null) return true;
				
				if(state){//成功
					model.setPdfpath(ConvertFilepathUtil.getSpdfpath(model.getFilepath()));
					//model.setSwfpath(ConvertFilepathUtil.getSswfpath(model.getFilepath(), "2"));
					model.setSketch(ConvertFilepathUtil.getSpngpath(model.getFilepath()));
					model.setPagenum(pagenum);
					model.setConvertstatus("1");
					baseDAO.updateObject("课程附件", model);
				}else {//失败
					model.setConvertstatus("2");
					baseDAO.updateObject("课程附件", model);
				}
				
				result = true;
			}
		} catch (Exception e) {
			result = false;
		}
		
		return result;
	}
}
