package com.wkmk.sys.web.action;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.wkmk.sys.web.form.SysImageUploadActionForm;
import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.FileUtil;
import com.util.image.ImageUtil;
import com.util.string.Encode;
import com.util.string.UUID;

/**
 * <p>Description: </p>
 * <p>E-mail: zhangxuedong28@gmail.com</p>
 * @version 1.0
 */
public class SysImageUploadAction extends BaseAction {

	  /**
	   * 上载图片选择文件
	   * @param actionMapping ActionMapping
	   * @param actionForm ActionForm
	   * @param httpServletRequest HttpServletRequest
	   * @param httpServletResponse HttpServletResponse
	   * @return ActionForward
	   */
	  public ActionForward uploadimage(ActionMapping actionMapping,
	                                   ActionForm actionForm,
	                                   HttpServletRequest httpServletRequest,
	                                   HttpServletResponse httpServletResponse) {
	    String savepath = Encode.nullToBlank(httpServletRequest.getParameter("savepath"));//图片保存路径
	    httpServletRequest.setAttribute("savepath", savepath);
	    String pathtype = Encode.nullToBlank(httpServletRequest.getParameter("pathtype"));//路径保存类型
	    httpServletRequest.setAttribute("pathtype", pathtype);
	    String filename = Encode.nullToBlank(httpServletRequest.getParameter("filename"));//图片保存名称
	    httpServletRequest.setAttribute("filename", filename);
	    String filesize = Encode.nullToBlank(httpServletRequest.getParameter("filesize"));//允许上传图片大小,单位KB
	    if("".equals(filesize)){
	    	filesize = "1024";//默认1M
	    }
	    if("0".equals(filesize)){
	    	filesize = "1024000";//1000M
	    }
	    httpServletRequest.setAttribute("filesize", filesize);
	    
	    String sketch = Encode.nullToBlank(httpServletRequest.getParameter("sketch"));//是否生存缩略图，true按下面给定大小压缩，false等比例压缩
	    String swidth = Encode.nullToBlank(httpServletRequest.getParameter("swidth"));//缩略图宽度
	    String sheight = Encode.nullToBlank(httpServletRequest.getParameter("sheight"));//缩略图高度
	    httpServletRequest.setAttribute("sketch", sketch);
	    httpServletRequest.setAttribute("swidth", swidth);
	    httpServletRequest.setAttribute("sheight", sheight);

	    return actionMapping.findForward("uploadimage");
	  }

	  /**
	   * 上载图片功能
	   * @param actionMapping ActionMapping
	   * @param actionForm ActionForm
	   * @param httpServletRequest HttpServletRequest
	   * @param httpServletResponse HttpServletResponse
	   * @return ActionForward
	   */
	  public ActionForward uploadimagedeal(ActionMapping mapping,
	                                       ActionForm actionForm,
	                                       HttpServletRequest request,
	                                       HttpServletResponse response) {
		String savepath = Encode.nullToBlank(request.getParameter("savepath"));//图片保存路径
		String pathtype = Encode.nullToBlank(request.getParameter("pathtype"));//路径保存类型
		String filename = Encode.nullToBlank(request.getParameter("filename"));//图片保存名称
		String filesize = Encode.nullToBlank(request.getParameter("filesize"));//允许上传图片大小,单位KB
		String sketch = Encode.nullToBlank(request.getParameter("sketch"));//是否生存缩略图，true按下面给定大小压缩，false等比例压缩
		String swidth = Encode.nullToBlank(request.getParameter("swidth"));//缩略图宽度
		String sheight = Encode.nullToBlank(request.getParameter("sheight"));//缩略图高度
		    
	    SysImageUploadActionForm form = (SysImageUploadActionForm) actionForm;
	    String encoding = request.getCharacterEncoding();
	    if ((encoding != null) && (encoding.equalsIgnoreCase("utf-8"))) {
	      response.setContentType("text/html; charset=utf-8");
	    }
	    String uploadimageerror = "";
	    String curdate = DateTime.getDate("yyyyMM");
	    FormFile file = form.getThefile(); //从Form类中获得上传的文件流

	    String filepath = ""; //全路径

	    Integer fsize = 0;
	    try {
	    	fsize = Integer.parseInt(filesize) * 1000;
		} catch (Exception e) {
			fsize = 1024 * 1000;//默认1M
		}
	    

	    try {
	    	//解决上传漏洞攻击，只能上传指定扩展名(.jpg|.jpeg|.gif|.png|)的图片文件
	        String fname = file.getFileName();
	        String sFileExt = FileUtil.getFileExt(fname).toLowerCase();
	        if(fname.indexOf(".jsp") != -1 || fname.indexOf(".jspx") != -1 || fname.indexOf(".exe") != -1 || fname.indexOf(".bat") != -1 || fname.indexOf(".js") != -1){
	      	  uploadimageerror = "图片上传错误，上传的图片格式不对。";
	      	  request.setAttribute("uploadimageerror", uploadimageerror);
	      	return mapping.findForward("uploadimage");
	        }
	        if(!"jpg".equals(sFileExt) && !"jpeg".equals(sFileExt) && !"gif".equals(sFileExt) && !"png".equals(sFileExt)){
	      	  uploadimageerror = "图片上传错误，上传的图片格式不对。";
	      	  request.setAttribute("uploadimageerror", uploadimageerror);
	      	return mapping.findForward("uploadimage");
	        }
	        
	        if (file.getFileSize() < fsize) { //判断文件的大小是否大于1M
		        String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
	
		        // 写文件
		        InputStream stream = file.getInputStream(); //把文件读入
		        //如果未传入文件名，则创建一个
		        if ("".equals(filename)) {
		          filename = UUID.getNewUUID();
		        }
		        filename += "." + sFileExt;
	
		        HttpSession session = request.getSession();
		        Integer unitid = (Integer) session.getAttribute("s_unitid");
		        //基础路径+站点ID+日期
		        if ("ID".equals(pathtype)) {
		          //获取站点id
		          savepath =  unitid + "/" + savepath + "/" + curdate;
		        }
		        //基础路径+站点ID
		        else if ("I".equals(pathtype)) {
		         //获取站点id
		         savepath = unitid + "/" + savepath;
		       }
		        //基础路径+日期
		        else if ("D".equals(pathtype)) {
		          savepath = savepath + "/" + curdate;
		        }
		        else {
		          savepath += "/" + curdate;
		        }
	
		        filepath = rootpath + "/" + savepath;
		        //检查文件夹是否存在,如果不存在,新建一个
		        if (!FileUtil.isExistDir(filepath)) {
		        	FileUtil.creatDir(filepath);
		        }
	
		        //如果有生成缩略图，则把缩略图返回
		        //request.setAttribute("uploadimagefile", savepath + "/" + filename);
	
		        OutputStream bos = new FileOutputStream(filepath + "/" + filename);
		        int bytesRead = 0;
		        byte[] buffer = new byte[8192];
		        while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
		          bos.write(buffer, 0, bytesRead); //将文件写入服务器
		        }
		        bos.close();
		        stream.close();
		        //生成缩略图
		        if (!"".equals(sketch)) {
		    	    Integer widht = 0;
		    	    Integer height = 0;
		    	    try {
						widht = Integer.parseInt(swidth);
						height = Integer.parseInt(sheight);
					} catch (Exception e) {
						widht = 100;
						height = 100;
					}
					if(savepath.indexOf("pwh") >= 0){//如果是图库，生成缩略图要生成2套
						if("true".equals(sketch)){
							ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/1000x500_" + filename, 1000, 500, true);
							ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/312x234_" + filename, 312, 234, true);
						}
						if("false".equals(sketch)){
							ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/1000x500_" + filename, 1000, 500, false);
							ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/312x234_" + filename, 312, 234, false);
						}
						request.setAttribute("uploadimagefile", savepath + "/" + filename);
					}else if(savepath.indexOf("vwh") >= 0){//视频缩略图
						if(savepath.indexOf("vwh/ad") >= 0){//广告图片
							if("true".equals(sketch)){
								ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/990x340_" + filename, 990, 340, true);
								ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/137x53_" + filename, 137, 53, true);
							}
							if("false".equals(sketch)){
								ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/990x340_" + filename, 990, 340, false);
								ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/137x53_" + filename, 137, 53, false);
							}
						}else {
							if("true".equals(sketch)){
								ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/160x213_" + filename, 160, 213, true);
							}
							if("false".equals(sketch)){
								ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/160x213_" + filename, 160, 213, false);
							}
							filename = "160x213_" + filename;
						}
						request.setAttribute("uploadimagefile", savepath + "/" + filename);
					}else {
						if("true".equals(sketch)){
							ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/s_" + filename, widht, height, true);
						}
						if("false".equals(sketch)){
							ImageUtil.generateThumbnailsSubImage(filepath + "/" + filename, filepath + "/s_" + filename, widht, height, false);
						}
						request.setAttribute("uploadimagefile", savepath + "/s_" + filename);
					}
		        }else {
		        	request.setAttribute("uploadimagefile", savepath + "/" + filename);
				}
	        } else {
		        //提示用户上传文件不能大于1M
		    	uploadimageerror = "图片上传失败，上传的图片不能大于" + (fsize/1000) + "KB。";
	      	}
	    } catch (Exception e) {
	    	uploadimageerror = "图片上传错误，请重试。";
	    }
	    request.setAttribute("uploadimageerror", uploadimageerror);
	    request.setAttribute("savepath", savepath);
	    request.setAttribute("pathtype", pathtype);
	    request.setAttribute("filename", filename);
	    request.setAttribute("filesize", filesize);
	    request.setAttribute("sketch", sketch);
	    request.setAttribute("swidth", swidth);
	    request.setAttribute("sheight", sheight);
	    
	    if(!"".equals(uploadimageerror)){//提示错误信息，并重新上传
	    	return mapping.findForward("uploadimage");
	    }else {
	    	return mapping.findForward("uploadimagedeal");
		}
	  }
}
