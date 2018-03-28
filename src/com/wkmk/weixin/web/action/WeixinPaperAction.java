package com.wkmk.weixin.web.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.encrypt.DESUtil;
import com.util.file.FileUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkPaperFile;
import com.wkmk.tk.bo.TkPaperFileDownload;
import com.wkmk.tk.service.TkPaperFileDownloadManager;
import com.wkmk.tk.service.TkPaperFileManager;
import com.wkmk.tk.service.TkPaperTypeManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.IpUtil;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 试卷下载
 * 
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class WeixinPaperAction extends BaseAction {

	/**
	 * 试卷列表首页
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			// ==========通过微信自动回复消息跳转过来==========
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			// 如果用户已经注册并绑定账号，再次点击注册时直接跳转进入，避免出现绑定多个账号的bug
			SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
			SysUserAttention attention = suam.getSysUserAttentionByOpenid(openid);
			if (attention != null && attention.getUserid().intValue() > 1) {
				userid = attention.getUserid().toString();
			}
			if (!"1".equals(userid)) {
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 学科
				List subjectList = (List) CacheUtil.getObject("EduSubjectInfo_List");
				if (subjectList == null || subjectList.size() == 0) {
					EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
					SearchCondition.addCondition(condition, "status", "=", "1");
					subjectList = esim.getEduSubjectInfos(condition, "orderindex asc", 0);
					CacheUtil.putObject("EduSubjectInfo_List", subjectList, 7 * 24 * 60 * 60);
				}
				request.setAttribute("subjectList", subjectList);
				// 试卷分类
				condition.clear();
				List typelist = (List) CacheUtil.getObject("TkPaperType_List");
				if (typelist == null || typelist.size() == 0) {
					TkPaperTypeManager typemanager = (TkPaperTypeManager) getBean("tkPaperTypeManager");
					SearchCondition.addCondition(condition, "status", "=", "1");
					typelist = typemanager.getTkPaperTypes(condition, "typeno asc", 0);
					CacheUtil.putObject("TkPaperType_List", typelist, 7 * 24 * 60 * 60);
				}
				request.setAttribute("typelist", typelist);
				// 年份
				condition.clear();
				List yearlist = (List) CacheUtil.getObject("TkPaperFileYear_List");
				if (yearlist == null || yearlist.size() == 0) {
					TkPaperFileManager filemanager = (TkPaperFileManager) getBean("tkPaperFileManager");
					yearlist = filemanager.getPaperFileYears();
					CacheUtil.putObject("TkPaperFileYear_List", yearlist, 7 * 24 * 60 * 60);
				}
				request.setAttribute("yearlist", yearlist);
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String typeid = Encode.nullToBlank(request.getParameter("typeid"));
				String theyear = Encode.nullToBlank(request.getParameter("theyear"));
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));
				TkPaperFileManager filemanager = (TkPaperFileManager) getBean("tkPaperFileManager");
				condition.clear();
				SearchCondition.addCondition(condition, "status", "=", "1");
				if (!"".equals(subjectid)) {
					SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
				}
				if (!"".equals(typeid)) {
					SearchCondition.addCondition(condition, "tkPaperType.typeid", "=", Integer.parseInt(typeid));
				}
				if (!"".equals(theyear)) {
					SearchCondition.addCondition(condition, "theyear", "=", theyear);
				}
				if (!"".equals(keywords)) {
					SearchCondition.addCondition(condition, "filesize", ">", 0 + "' and (a.filename like '%" + keywords + "%' or a.area like '%" + keywords + "%') and 1='1");
				}
				List filelist = filemanager.getPageTkPaperFilesAndSubjectName(condition, "a.theyear desc, a.subjectid asc, a.area asc", 0, 10).getDatalist();
				request.setAttribute("filelist", filelist);
				request.setAttribute("subjectid", subjectid);
				request.setAttribute("typeid", typeid);
				request.setAttribute("theyear", theyear);
				request.setAttribute("keywords", keywords);
				return mapping.findForward("index");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	public void getPaperFileByAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			String userid = MpUtil.getUserid(request);
			String desUserid = Encode.nullToBlank(request.getParameter("userid"));
			if (!"1".equals(userid)) {
				TkPaperFileManager filemanager = (TkPaperFileManager) getBean("tkPaperFileManager");
				EduSubjectInfoManager subjectmanager = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String typeid = Encode.nullToBlank(request.getParameter("typeid"));
				String theyear = Encode.nullToBlank(request.getParameter("theyear"));
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));
				String pn = Encode.nullToBlank(request.getParameter("pagenum"));
				int pagesize = 10;
				int pagenum = "".equals(pn) ? 0 : Integer.parseInt(pn);
				int start = pagesize * pagenum;
				//
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "status", "=", "1");
				if (!"".equals(subjectid)) {
					SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
				}
				if (!"".equals(typeid)) {
					SearchCondition.addCondition(condition, "tkPaperType.typeid", "=", Integer.parseInt(typeid));
				}
				if (!"".equals(theyear)) {
					SearchCondition.addCondition(condition, "theyear", "=", theyear);
				}
				if (!"".equals(keywords)) {
					SearchCondition.addCondition(condition, "filesize", ">", 0 + "' and (a.filename like '%" + keywords + "%' or a.area like '%" + keywords + "%') and 1='1");
				}
				List filelist = filemanager.getPageTkPaperFilesAndSubjectName(condition, "a.theyear desc, a.subjectid asc, a.area asc", start, pagesize).getDatalist();
				StringBuffer result = new StringBuffer();
				String subjectname = "";
				TkPaperFile pf = null;
				for (int i = 0; i < filelist.size(); i++) {
					Object[] array = (Object[]) filelist.get(i);
					pf = (TkPaperFile) array[0];
					subjectname = array[1].toString();
					result.append("<a href=\"/weixinPaper.app?method=getPaperFileDetail&fileid=" + pf.getFileid() + "&userid=" + desUserid + "\">" + "<div class=\"testpaper_main\">" + "<p class=\"testpaper_main_p\">" + pf.getFilename() + "</p>" + "<div class=\"testpaper_main_right\">" + "<p>" + pf.getTheyear() + "</p>" + "<p>" + subjectname + "</p>" + "<p>" + pf.getArea() + "</p>" + "</div>" + "</div>" + "</a>");
				}
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(result.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ActionForward getPaperFileDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				String fileid = Encode.nullToBlank(request.getParameter("fileid"));
				TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
				EduSubjectInfoManager subjectmanager = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
				TkPaperFile fileinfo = manager.getTkPaperFile(fileid);
				String fileext_img = "pdf.png";
				if (fileinfo.getFileext().toLowerCase().indexOf("doc") != -1) {
					fileext_img = "doc.png";
				} else if (fileinfo.getFileext().toLowerCase().indexOf("ppt") != -1) {
					fileext_img = "ppt.png";
				} else if (fileinfo.getFileext().toLowerCase().indexOf("xls") != -1) {
					fileext_img = "xls.png";
				} else if (fileinfo.getFileext().toLowerCase().indexOf("txt") != -1) {
					fileext_img = "txt.png";
				}
				String subjectname = subjectmanager.getEduSubjectInfo(fileinfo.getSubjectid()).getSubjectname();
				String filesize = getFileSize(fileinfo.getFilesize());
				request.setAttribute("fileinfo", fileinfo);
				request.setAttribute("subjectname", subjectname);
				request.setAttribute("filesize", filesize);
				request.setAttribute("fileext_img", fileext_img);
			}
			return mapping.findForward("detail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	private String getFileSize(Integer size) {
		BigDecimal filesize = BigDecimal.valueOf(size);
		String ext = "B";
		if (filesize.compareTo(BigDecimal.valueOf(999)) > 0) {
			filesize = filesize.divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP);
			ext = "KB";
		}
		if (filesize.compareTo(BigDecimal.valueOf(999)) > 0) {
			filesize = filesize.divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP);
			ext = "MB";
		}
		if (filesize.compareTo(BigDecimal.valueOf(999)) > 0) {
			filesize = filesize.divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP);
			ext = "GB";
		}
		return filesize.setScale(2) + "&nbsp;&nbsp;" + ext;
	}

	/**
	 * 下载试卷附件
	 * 
	 * @param actionMapping
	 *            ActionMapping
	 * @param actionForm
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward downloadPaperFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
		String fileid = Encode.nullToBlank(request.getParameter("fileid"));
		TkPaperFile model = manager.getTkPaperFile(fileid);
		// =====================开始下载===========================
		String filepath = null;
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String filename = null;
		filepath = "/upload" + model.getFilepath();
		filename = model.getFilename() + "." + model.getFileext();
		filename = filename.replaceAll("\\s", "");// 把字符串中的所有空白字符替换为空字符串
		InputStream is = null;
		OutputStream os = null;
		try {
			String fullpathname = realpath + filepath;
			if (!FileUtil.isExistDir(fullpathname)) {// 不存在当前文件
				request.setAttribute("promptinfo", "下载失败，当前试卷实体文件不存在!");
				return mapping.findForward("failure");
			}
			is = new FileInputStream(fullpathname);
			os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setContentType("application/x-download; charset=gb2312");
			response.setContentType("bin");
			response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));// 设定输出文件头
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = is.read(buffer)) > 0) {
				os.write(buffer, 0, byteread);
			}
			model.setDownloads(model.getDownloads() + 1);
			manager.updateTkPaperFile(model);
			// 记录用户下载试卷信息
			String userid = MpUtil.getUserid(request);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TkPaperFileDownloadManager downloadmanager = (TkPaperFileDownloadManager) getBean("tkPaperFileDownloadManager");
			TkPaperFileDownload download = new TkPaperFileDownload();
			download.setCreatedate(format.format(new Date()));
			download.setFileid(Integer.parseInt(fileid));
			download.setUserid(Integer.parseInt(userid));
			download.setUserip(IpUtil.getIpAddr(request));
			downloadmanager.addTkPaperFileDownload(download);
		} catch (Exception e) {
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (is != null) {
					is.close();
					is = null;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
}