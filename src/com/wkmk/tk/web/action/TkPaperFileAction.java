package com.wkmk.tk.web.action;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.json.JSONException;
import org.json.JSONObject;

import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkPaperFile;
import com.wkmk.tk.bo.TkPaperFileDownload;
import com.wkmk.tk.service.TkPaperFileDownloadManager;
import com.wkmk.tk.service.TkPaperFileManager;
import com.wkmk.tk.service.TkPaperTypeManager;
import com.wkmk.tk.web.form.TkPaperFileActionForm;
import com.wkmk.util.common.IpUtil;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.UUID;

/**
 * <p>
 * Description: 试卷附件
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class TkPaperFileAction extends BaseAction {

	/**
	 * 跳转到工作区
	 * */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		return actionMapping.findForward("main");
	}

	/**
	 * 树形选择器
	 */
	public ActionForward tree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		SysUnitInfo sysUnitinfo = (SysUnitInfo) session.getAttribute("s_sysunitinfo");
		// 查询学科
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<EduSubjectInfo> subjectList = manager.getAllSubjectByUnitType(sysUnitinfo.getType());
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target = "rfrmright";
		String url = null;
		String hint = null;
		EduSubjectInfo esi = null;
		for (int i = 0; i < subjectList.size(); i++) {
			esi = (EduSubjectInfo) subjectList.get(i);
			text = esi.getSubjectname();
			hint = "";
			url = "/tkPaperFileAction.do?method=list&subjectid=" + esi.getSubjectid();
			tree.append("\n").append("tree").append(".nodes[\"0000_").append(esi.getSubjectid()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			if (hint != null && !"".equals(hint.trim())) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl = "javascript:;";
		request.setAttribute("treenode", tree.toString());
		request.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}

	/**
	 * 列表显示
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
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
		TkPaperTypeManager typemanager = (TkPaperTypeManager) getBean("tkPaperTypeManager");
		String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
		String filename = Encode.nullToBlank(request.getParameter("filename"));
		String theyear = Encode.nullToBlank(request.getParameter("theyear"));
		String typeid = Encode.nullToBlank(request.getParameter("typeid"));
		String area = Encode.nullToBlank(request.getParameter("area"));
		PageUtil pageUtil = new PageUtil(request);
		String sorderindex = "createdate desc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if (!"".equals(subjectid)) {
			SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
		}
		if (!"".equals(filename)) {
			SearchCondition.addCondition(condition, "filename", "like", "%" + filename + "%");
		}
		if (!"".equals(typeid)) {
			SearchCondition.addCondition(condition, "tkPaperType.typeid", "=", Integer.parseInt(typeid));
		}
		if (!"".equals(area)) {
			SearchCondition.addCondition(condition, "area", "like", "%" + area + "%");
		}
		SearchCondition.addCondition(condition, "theyear", "=", theyear);
		PageList page = manager.getPageTkPaperFiles(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		// 試卷分類集合
		condition.clear();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List typelist = typemanager.getTkPaperTypes(condition, "typeno asc", 0);
		request.setAttribute("pagelist", page);
		request.setAttribute("subjectid", subjectid);
		request.setAttribute("filename", filename);
		request.setAttribute("typeid", typeid);
		request.setAttribute("theyear", theyear);
		request.setAttribute("area", area);
		request.setAttribute("typelist", typelist);
		return actionMapping.findForward("list");
	}

	/**
	 * 增加前
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
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFile model = new TkPaperFile();
		String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
		String filename = Encode.nullToBlank(request.getParameter("filename"));
		String theyear = Encode.nullToBlank(request.getParameter("theyear"));
		String typeid = Encode.nullToBlank(request.getParameter("typeid"));
		String area = Encode.nullToBlank(request.getParameter("area"));
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		TkPaperTypeManager typemanager = (TkPaperTypeManager) getBean("tkPaperTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List typelist = typemanager.getTkPaperTypes(condition, "typeno asc", 0);
		model.setTheyear(format.format(new Date()));
		model.setSubjectid(Integer.parseInt(subjectid));
		model.setStatus("1");
		request.setAttribute("model", model);
		request.setAttribute("typelist", typelist);
		request.setAttribute("filename", filename);
		request.setAttribute("theyear", theyear);
		request.setAttribute("typeid", typeid);
		request.setAttribute("area", area);
		saveToken(request);
		return actionMapping.findForward("upload");
	}

	/**
	 * 增加时保存
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
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFileActionForm form = (TkPaperFileActionForm) actionForm;
		TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
		TkPaperTypeManager typemanager = (TkPaperTypeManager) getBean("tkPaperTypeManager");
		if (isTokenValid(request, true)) {
			try {
				// 上传试卷附件到服务器
				FormFile paperfile = form.getThefile();
				String rootpath = request.getSession().getServletContext().getRealPath("/upload");
				String savepath = "/PageFile/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
				String fileext = FileUtil.getFileExt(paperfile.getFileName()).toLowerCase();
				String oldname = paperfile.getFileName();
				oldname = oldname.substring(0, oldname.lastIndexOf("."));
				String newname = UUID.getNewUUID() + "." + fileext;
				String filepath = rootpath + "/" + savepath;
				// 检查文件夹是否存在,如果不存在,新建一个
				if (!FileUtil.isExistDir(filepath)) {
					FileUtil.creatDir(filepath);
				}
				InputStream stream = paperfile.getInputStream(); // 把文件读入
				OutputStream bos = new FileOutputStream(filepath + "/" + newname);
				int bytesRead = 0;
				long totalRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
					bos.write(buffer, 0, bytesRead); // 将文件写入服务器
					totalRead += bytesRead;
				}
				bos.close();
				stream.close();
				// 添加附件记录信息
				SysUserInfo user = (SysUserInfo) request.getSession().getAttribute("s_sysuserinfo");
				String typeid = Encode.nullToBlank(request.getParameter("filetypeid"));
				TkPaperFile model = form.getTkPaperFile();
				model.setFilename(oldname);
				model.setFileext(fileext);
				model.setFilepath(savepath + "/" + newname);
				model.setFilesize((int) totalRead);
				model.setCreatedate(DateTime.getDate());
				model.setTkPaperType(typemanager.getTkPaperType(typeid));
				model.setDownloads(0);
				model.setUserid(user.getUserid());
				manager.addTkPaperFile(model);
				addLog(request, "增加了一个试卷附件【" + model.getFilename() + "】");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list(actionMapping, actionForm, request, response);
	}

	/**
	 * 修改前
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
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
		TkPaperTypeManager typemanager = (TkPaperTypeManager) getBean("tkPaperTypeManager");
		String objid = Encode.nullToBlank(request.getParameter("objid"));
		String filename = Encode.nullToBlank(request.getParameter("filename"));
		String theyear = Encode.nullToBlank(request.getParameter("theyear"));
		String typeid = Encode.nullToBlank(request.getParameter("typeid"));
		String area = Encode.nullToBlank(request.getParameter("area"));
		try {
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "1");
			List typelist = typemanager.getTkPaperTypes(condition, "typeno asc", 0);
			TkPaperFile model = manager.getTkPaperFile(objid);
			request.setAttribute("act", "updateSave");
			request.setAttribute("model", model);
			request.setAttribute("typelist", typelist);
			request.setAttribute("filename", filename);
			request.setAttribute("theyear", theyear);
			request.setAttribute("typeid", typeid);
			request.setAttribute("area", area);
		} catch (Exception e) {
		}
		saveToken(request);
		return actionMapping.findForward("edit");
	}

	/**
	 * 修改时保存
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
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFileActionForm form = (TkPaperFileActionForm) actionForm;
		TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
		TkPaperTypeManager typemanager = (TkPaperTypeManager) getBean("tkPaperTypeManager");
		if (isTokenValid(request, true)) {
			try {
				String typeid = Encode.nullToBlank(request.getParameter("filetypeid"));
				TkPaperFile model = form.getTkPaperFile();
				model.setTkPaperType(typemanager.getTkPaperType(typeid));
				manager.updateTkPaperFile(model);
				addLog(request, "修改了一个试卷附件【" + model.getFilename() + "】");
			} catch (Exception e) {
			}
		}
		return list(actionMapping, actionForm, request, response);
	}

	/**
	 * 批量删除
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
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
		String[] checkids = request.getParameterValues("checkid");
		TkPaperFile model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getTkPaperFile(checkids[i]);
			manager.delTkPaperFile(model);
			addLog(request, "删除了一个试卷附件【" + model.getFilename() + "】");
		}
		return list(actionMapping, actionForm, request, response);
	}

	public ActionForward disableBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
		String[] checkids = request.getParameterValues("checkid");
		String setstatus = Encode.nullToBlank(request.getParameter("setstatus"));
		TkPaperFile model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getTkPaperFile(checkids[i]);
			if ("1".equals(setstatus)) {
				model.setStatus("1");
				manager.updateTkPaperFile(model);
				addLog(request, "启用了一个试卷附件【" + model.getFilename() + "】");
			} else if ("2".equals(setstatus)) {
				model.setStatus("2");
				manager.updateTkPaperFile(model);
				addLog(request, "禁用了一个试卷附件【" + model.getFilename() + "】");
			}
		}
		return list(actionMapping, actionForm, request, response);
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
		String objid = Encode.nullToBlank(request.getParameter("objid"));
		TkPaperFile model = manager.getTkPaperFile(objid);
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
			String userid = request.getSession().getAttribute("s_userid").toString();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TkPaperFileDownloadManager downloadmanager = (TkPaperFileDownloadManager) getBean("tkPaperFileDownloadManager");
			TkPaperFileDownload download = new TkPaperFileDownload();
			download.setCreatedate(format.format(new Date()));
			download.setFileid(Integer.parseInt(objid));
			download.setUserid(Integer.parseInt(userid));
			download.setUserip(IpUtil.getIpAddr(request));
			downloadmanager.addTkPaperFileDownload(download);
			addLog(request, "下载了一个试卷附件");
		} catch (Exception e) {
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		return null;
	}

	public ActionForward papersTatistical(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
			// 按照分类统计试卷被下载总次数
			List subjectcountlist = manager.getSubjectCount();
			request.setAttribute("subjectcount", subjectcountlist);
			// 统计各学科试卷每天被下载次数
			List downloadlist = manager.getDownloadCountByDate();
			Map m = new HashMap();
			for (Object object : downloadlist) {
				Object[] o = (Object[]) object;
				m.put(o[0] + "-" + o[1], o[2]);
			}
			// 统计试卷每天下载总次数
			List downloadDateCount = manager.getDownloadCount();
			// 统计试卷所属学科分类
			List subjectlist = manager.getSubjectList();
			JSONArray datearray = new JSONArray();
			JSONArray downloadarray = new JSONArray();
			// 峰值
			int maxnum = 0;
			for (Object object : downloadDateCount) {
				Object[] o = (Object[]) object;
				datearray.add(o[0]);
				downloadarray.add(o[1]);
				int a = Integer.parseInt(o[1].toString());
				if (a > maxnum) {
					maxnum = a;
				}
			}
			List subjectdatalist = new ArrayList();
			for (Object obj : subjectlist) {
				Object[] subject = (Object[]) obj;
				JSONArray s = new JSONArray();
				Map map = new HashMap();
				for (int i = 0; i < datearray.size(); i++) {
					Object date = datearray.get(i);
					Object o2 = m.get(date + "-" + subject[0]);
					if (o2 == null) {
						s.add(0);
					} else {
						s.add(o2);
					}
				}
				map.put("subjectid", subject[0]);
				map.put("subjectname", subject[1]);
				map.put("subjectdata", s);
				subjectdatalist.add(map);
			}
			String begindate = "";
			String enddate = "";
			if (datearray.size() > 30) {
				begindate = datearray.getString(datearray.size() - 31);
			} else {
				begindate = datearray.getString(0);
			}
			enddate = datearray.getString(datearray.size() - 1);
			request.setAttribute("begindate", begindate);
			request.setAttribute("enddate", enddate);
			request.setAttribute("subjectlist", subjectlist);
			request.setAttribute("subjectdatalist", subjectdatalist);
			request.setAttribute("datearray", datearray);
			request.setAttribute("maxnum", maxnum + 10);
			request.setAttribute("downloadarray", downloadarray);
			return mapping.findForward("paperstatistical");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mapping.findForward("error");
	}
}