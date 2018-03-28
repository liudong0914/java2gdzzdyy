package com.util.action;

import com.wkmk.util.oss.OssUtil;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhFilmPixManager;
import com.util.date.DateTime;
import com.util.file.FileMd5;
import com.util.file.FileUtil;
import com.util.string.PublicResourceBundle;
import com.util.string.UUID;
import com.util.action.BaseAction;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 单上传文件
 */

public class FileUploadActionBak extends BaseAction {

	/**
	 * 上传文件
	 * 
	 * @param actionMapping
	 *            ActionMapping
	 * @param actionForm
	 *            ActionForm
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @param httpServletResponse
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		// ftag:上传文件类型，‘file’普通上传，‘course’网络课程，‘batchfile’批量上传。默认存放文件夹为‘files’
		// ftag:上传文件类型，‘networkfile’网络文件夹，‘vod’视频点播
		String ftag = httpServletRequest.getParameter("ftag");
		String unitid = httpServletRequest.getParameter("unitid");
		String realpath = httpServletRequest.getSession().getServletContext().getRealPath("/");
		String folder = "files";// 默认存放路径
		String savepath = "/upload/" + unitid + "/" + folder + "/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
		if ("vwhfilm".equals(ftag)) {// 视频点播
			savepath = "/upload/" + unitid + "/vwh/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
		}
		if ("vwhfilmannex".equals(ftag)) {// 视频点播附件
			savepath = "/upload/" + unitid + "/vwh/annex/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
		}

		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 设置内存缓冲区，超过后写入临时文件
			factory.setSizeThreshold(1024 * 1024 * 1024);

			String filepath = realpath + savepath;
			File file = new File(filepath);
			if (!file.exists())
				file.mkdirs();
			factory.setSizeThreshold(4096);
			factory.setRepository(file);
			ServletFileUpload upload = new ServletFileUpload(factory);

			StringBuffer json_str = new StringBuffer();
			// String name = null;
			String value = null;
			String fileext = null;
			String filename = null;
			List items = upload.parseRequest(httpServletRequest);
			String canUploadFileext = "*.avi;*.wmv;*.asf;*.asx;*.mpg;*.mpeg;*.3gp;*.mov;*.vob;*.mkv;*.ts;*.mp4;*.flv;*.f4v;*.rmvb;*.rm;*.wmv9;*.doc;*.docx;*.ppt;*.pptx;*.xls;*.xlsx;*.pdf;*.txt;*.pot;*.pps;*.rtf;*.mp3;*.wma;*.jpg;*.gif;*.png;*.swf;*.rar;*.zip";
			String md5code = null;
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				if (item.isFormField()) {// 一般参数
					// name = item.getFieldName();
					// value = item.getString("UTF-8");
				} else {// 文件流
					value = item.getName();
					fileext = FileUtil.getFileExt(value);
					// ======================================================================================
					// 解决上传漏洞攻击，只能上传指定扩展名(.jpg|.jpeg|.gif|.png|)的图片文件
					if (value.indexOf(".jsp") != -1 || value.indexOf(".jspx") != -1 || value.indexOf(".exe") != -1 || value.indexOf(".bat") != -1 || value.indexOf(".js") != -1) {
						json_str.append("error_");
						PrintWriter out = httpServletResponse.getWriter();
						out.print(json_str.toString());
						out.flush();
						out.close();
						return null;
					}
					/*
					 * if(canUploadFileext.indexOf("*."+fileext+";") == -1){
					 * json_str.append("error_"); PrintWriter
					 * out=httpServletResponse.getWriter();
					 * out.print(json_str.toString()); out.flush(); out.close();
					 * return null; }
					 */
					// ======================================================================================
					filename = UUID.getNewUUID() + "." + fileext;

					// 判断是否启用oss云存储，启用则用oss对应的sdk上传文件
					String openOss = PublicResourceBundle.getProperty("system", "open.oss");
					if ("1".equals(openOss)) {
						String key_filepathname = savepath.substring(8) + "/" + filename;
						OssUtil ot = new OssUtil();
						ot.ossStreamUpload(item.getInputStream(), key_filepathname);
						//md5code = FileMd5.getFileMD5String(item.getInputStream());
					} else {
						item.write(new File(filepath, filename));// 文件存放的地址
						//md5code = FileMd5.getFileMD5String(new File(filepath, filename));
					}
				}
			}
			// 数据库中保存不包含upload
			savepath = savepath.substring(8);
			if ("vwhfilm".equals(ftag)) {
				//VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
				//VwhFilmPix vfp = vfpm.getVwhFilmPixByMd5code(md5code);
				//if (vfp != null) {
				//	json_str.append("exist_").append(savepath).append("/").append(filename).append(";").append(md5code);
				//} else {
					md5code = "0";
					json_str.append(savepath).append("/").append(filename).append(";").append(md5code);
				//}
			} else {
				json_str.append(savepath).append("/").append(filename);
			}

			PrintWriter out = httpServletResponse.getWriter();
			out.print(json_str.toString());
			out.flush();
			out.close();

		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}