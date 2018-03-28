package com.wkmk.tk.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.file.zip.AntZipFile;
import com.util.image.ZXingUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.UUID;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.web.form.TkBookInfoActionForm;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.TwoCodeUtil;
import com.wkmk.util.oss.OssUtil;
import com.wkmk.weixin.mp.MpUtil;

/**
 * <p>
 * Description: 作业本信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookInfoAction extends BaseAction {

	/**
	 * 跳转到工作区域
	 * */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}

	/**
	 * 树形选择器
	 * */
	public ActionForward tree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		HttpSession session = httpServletRequest.getSession();
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
			url = "/tkBookInfoAction.do?method=list&subjectid=" + esi.getSubjectid();
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
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}

	/**
	 * 列表显示
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
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String bookno = Encode.nullToBlank(httpServletRequest.getParameter("bookno"));
		String editor = Encode.nullToBlank(httpServletRequest.getParameter("editor"));
		String deputyeditor = Encode.nullToBlank(httpServletRequest.getParameter("deputyeditor"));
		TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
		SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		SearchCondition.addCondition(condition, "bookno", "like", "%" + bookno + "%");
		SearchCondition.addCondition(condition, "editor", "like", "%" + editor + "%");
		SearchCondition.addCondition(condition, "deputyeditor", "like", "%" + deputyeditor + "%");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "bookno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageTkBookInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List dataList = page.getDatalist();
		TkBookContentManager contentManager = (TkBookContentManager) getBean("tkBookContentManager");
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List bookids = contentManager.getAllbookids();
		TkBookInfo model = null;
		for (int i = 0; i < dataList.size(); i++) {
			model = (TkBookInfo) dataList.get(i);
			if (bookids.contains(model.getBookid())) {
				model.setFlags("disabled=\"disabled\"");
			}
			// 根据gradeid查询gradename
			String gradename = gradeManager.getEduGradeInfo(model.getGradeid()).getGradename();
			model.setGradename(gradename);
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("bookno", bookno);
		httpServletRequest.setAttribute("editor", editor);
		httpServletRequest.setAttribute("deputyeditor", deputyeditor);
		return actionMapping.findForward("list");
	}

	/**
	 * 增加前
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
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String bookno = Encode.nullToBlank(httpServletRequest.getParameter("bookno"));
		String editor = Encode.nullToBlank(httpServletRequest.getParameter("editor"));
		String deputyeditor = Encode.nullToBlank(httpServletRequest.getParameter("deputyeditor"));
		TkBookInfo model = new TkBookInfo();
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		model.setSubjectid(Integer.parseInt(subjectid));
		model.setUnitid(Integer.parseInt(httpServletRequest.getSession().getAttribute("s_unitid").toString()));
		model.setUserid(Integer.parseInt(httpServletRequest.getSession().getAttribute("s_userid").toString()));
		model.setSketch("default.gif");
		model.setPart("0");
		model.setTheyear(DateTime.getDate("yyyy"));
		model.setVodstate("0");
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		httpServletRequest.setAttribute("title", "新建作业本");
		// 根据学科查询年级
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
		List gradeinfoList = gradeManager.getEduGradeInfos(condition, "orderindex", 0);
		httpServletRequest.setAttribute("gradeinfoList", gradeinfoList);
		saveToken(httpServletRequest);
		// 分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("bookno", bookno);
		httpServletRequest.setAttribute("editor", editor);
		httpServletRequest.setAttribute("deputyeditor", deputyeditor);
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		return actionMapping.findForward("edit");
	}

	/**
	 * 增加时保存
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
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookInfoActionForm form = (TkBookInfoActionForm) actionForm;
		TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookInfo model = form.getTkBookInfo();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setCreatedate(sdf.format(new Date()));
				model.setUpdatetime(sdf.format(new Date()));
				manager.addTkBookInfo(model);
				model.setTwocodepath(TwoCodeUtil.getTwoCodePath(model.getBookid().toString(), httpServletRequest));
				manager.updateTkBookInfo(model);
				addLog(httpServletRequest, "增加了一个" + model.getTitle() + "作业本信息");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 修改前
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
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String bookno = Encode.nullToBlank(httpServletRequest.getParameter("bookno"));
		String editor = Encode.nullToBlank(httpServletRequest.getParameter("editor"));
		String deputyeditor = Encode.nullToBlank(httpServletRequest.getParameter("deputyeditor"));
		try {
			TkBookInfo model = manager.getTkBookInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("title", "修改作业本");
			// 根据学科查询年级
			EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "subjectid", "=", model.getSubjectid());
			List gradeinfoList = gradeManager.getEduGradeInfos(condition, "orderindex", 0);
			httpServletRequest.setAttribute("gradeinfoList", gradeinfoList);
			saveToken(httpServletRequest);
			// 分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
			httpServletRequest.setAttribute("subjectid", subjectid);
			httpServletRequest.setAttribute("title", title);
			httpServletRequest.setAttribute("bookno", bookno);
			httpServletRequest.setAttribute("editor", editor);
			httpServletRequest.setAttribute("deputyeditor", deputyeditor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 * 修改时保存
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
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookInfoActionForm form = (TkBookInfoActionForm) actionForm;
		TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookInfo model = form.getTkBookInfo();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				model.setUpdatetime(sdf.format(new Date()));
				manager.updateTkBookInfo(model);
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "作业本信息");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 批量删除
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
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				TkBookInfo model = manager.getTkBookInfo(checkids[i]);
				manager.delTkBookInfo(checkids[i]);
				addLog(httpServletRequest, "删除了一个" + model.getTitle() + "作业本");
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 详情
	 * */
	public ActionForward detail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String id = Encode.nullToBlank(httpServletRequest.getParameter("id"));
		TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
		TkBookInfo model = manager.getTkBookInfo(id);
		EduGradeInfoManager gradeManager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		httpServletRequest.setAttribute("gradeName", gradeManager.getEduGradeInfo(model.getGradeid()).getGradename());
		if ("0".equals(model.getPart())) {
			httpServletRequest.setAttribute("partName", "全册");
		} else if ("1".equals(model.getPart())) {
			httpServletRequest.setAttribute("partName", "上册");
		} else if ("2".equals(model.getPart())) {
			httpServletRequest.setAttribute("partName", "下册");
		}
		if ("1".equals(model.getStatus())) {
			httpServletRequest.setAttribute("status", "正常");
		} else if ("2".equals(model.getStatus())) {
			httpServletRequest.setAttribute("status", "禁用");
		}
		String version[] = Constants.CODETYPE_VERSION_ID;
		for (int i = 0; i < version.length; i++) {
			if (version[i].equals(model.getVersion())) {
				httpServletRequest.setAttribute("versionName", Constants.CODETYPE_VERSION_NAME[i]);
			}
		}
		SysUserInfoManager userinfoManager = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfo userinfo = userinfoManager.getSysUserInfo(model.getUserid());
		httpServletRequest.setAttribute("userName", userinfo.getUsername());
		httpServletRequest.setAttribute("title", "作业本详情");
		httpServletRequest.setAttribute("model", model);
		return actionMapping.findForward("detail");
	}

	/**
	 * 验证编号是否存在
	 * */
	public ActionForward ishavebookno(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookno = Encode.nullToBlank(httpServletRequest.getParameter("bookno"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		httpServletResponse.setContentType("text/plain;charset=gbk");
		TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
		PrintWriter out = null;
		try {
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "bookno", "=", bookno);
			SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
			List list = manager.getTkBookInfos(condition, "", 0);
			out = httpServletResponse.getWriter();
			if (list == null || list.size() == 0) {
				out.print("ok");
			} else {
				out.print("当前课本编号已被占用，请重试 ！");
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}

	/**
	 * 导出作业本下的所有二维码
	 * */
	public ActionForward exportcode(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		TkPaperContentManager papercontentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String[] versionids = Constants.CODETYPE_VERSION_ID;
		String[] versionnames = Constants.CODETYPE_VERSION_NAME;
		TkBookInfo model = manager.getTkBookInfo(bookid);
		String versionname = "";
		for (int i = 0; i < versionids.length; i++) {
			if (versionids[i].equals(model.getVersion())) {
				versionname = versionnames[i];
			}
		}
		model.setTitle(model.getTitle().replaceAll("/", "").replaceAll("\\\\", "").replaceAll(":", "").replaceAll("\\*", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "").replaceAll("|", "").trim());
		if (model.getTitle().endsWith(".")) {
			model.setTitle(model.getTitle().substring(0, model.getTitle().length() - 1) + "。");
		}
		String sourcefile = httpServletRequest.getSession().getServletContext().getRealPath("/") + "upload/" + model.getTwocodepath();
		String targetfile = httpServletRequest.getSession().getServletContext().getRealPath("/") + "uploadtemp/export/" + model.getTitle() + versionname + "/";
		// String targetfile =
		// httpServletRequest.getSession().getServletContext().getRealPath("/")
		// + "upload/export/" + model.getTitle() + versionname + "/";
		File targetFile = new File(targetfile);
		if (!targetFile.exists() && !targetFile.isDirectory()) {
			targetFile.mkdirs();
		} else {
			deleteDirectory(targetfile);
			targetFile.mkdirs();
		}
		File sourceFile = new File(sourcefile);
		try {
			copyFile(sourceFile, new File(targetfile + model.getTitle() + versionname + ".png"));
			// 查询作业本下的作业本内容
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "bookid", "=", model.getBookid());
			SearchCondition.addCondition(condition, "parentno", "!=", "0000");
			List<TkBookContent> bookcontelist = bookcontentManager.getTkBookContents(condition, "contentno", 0);
			for (int i = 0; i < bookcontelist.size(); i++) {
				TkBookContent bookcontent = bookcontelist.get(i);
				// String contentbeforequestionfile = httpServletRequest.getSession().getServletContext().getRealPath("/") + "temp/" + bookcontent.getBeforeclasstwocode();
				// String contenteachingquestionfile = httpServletRequest.getSession().getServletContext().getRealPath("/") + "temp/" + bookcontent.getTeachingcasetwocode();
				// String contentbeforequestionfile =
				// httpServletRequest.getSession().getServletContext().getRealPath("/")
				// + "upload/" + bookcontent.getBeforeclasstwocode();
				// String contenteachingquestionfile =
				// httpServletRequest.getSession().getServletContext().getRealPath("/")
				// + "upload/" + bookcontent.getTeachingcasetwocode();
				// String mp3file = "";
				// if (!"".equals(bookcontent.getMp3pathtwocode()) && null != bookcontent.getMp3pathtwocode()) {
				// mp3file = httpServletRequest.getSession().getServletContext().getRealPath("/") + "temp/" + bookcontent.getMp3pathtwocode();
				// mp3file =
				// httpServletRequest.getSession().getServletContext().getRealPath("/")
				// + "upload/" + bookcontent.getMp3pathtwocode();
				// }
				bookcontent.setTitle(bookcontent.getTitle().replaceAll("/", "").replaceAll("\\\\", "").replaceAll(":", "").replaceAll("\\*", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "").replaceAll("|", "").trim());
				if (bookcontent.getTitle().endsWith(".")) {
					bookcontent.setTitle(bookcontent.getTitle().substring(0, bookcontent.getTitle().length() - 1) + "。");
				}
				String contenttargetfile = targetfile + bookcontent.getTitle() + "/";
				File contenttargetFile = new File(contenttargetfile);
				if (!contenttargetFile.exists() && !contenttargetFile.isDirectory()) {
					contenttargetFile.mkdirs();
				}
				// File beforequestionFile = new File(contentbeforequestionfile);
				// File teachingquestionFile = new File(contenteachingquestionfile);
				OssUtil ot = new OssUtil();
				ot.ossFileDowmload(bookcontent.getBeforeclasstwocode(), contenttargetfile + "课前预习题.png");
				ot.ossFileDowmload(bookcontent.getTeachingcasetwocode(), contenttargetfile + "教学案.png");
				// copyFile(beforequestionFile, new File(contenttargetfile +
				// "课前预习.png"));
				// copyFile(teachingquestionFile, new File(contenttargetfile +
				// "教学案.png"));
				// if (!"".equals(mp3file)) {
				// File mp3File = new File(mp3file);
				// copyFile(mp3File, new File(contenttargetfile + "英语听力.png"));
				// }
				if (!"".equals(bookcontent.getMp3pathtwocode()) && null != bookcontent.getMp3pathtwocode()) {
					ot.ossFileDowmload(bookcontent.getMp3pathtwocode(), contenttargetfile + "英语听力.png");
				}
				// 查询试卷下的试题
				List<TkQuestionsInfo> questions = papercontentManager.getQuestions(bookcontent.getPaperid());
				for (int j = 0; j < questions.size(); j++) {
					TkQuestionsInfo question = questions.get(j);
					// String filmfile = httpServletRequest.getSession().getServletContext().getRealPath("/") + "temp/" + question.getFilmtwocodepath();
					// String similarfile = httpServletRequest.getSession().getServletContext().getRealPath("/") + "temp/" + question.getSimilartwocodepath();
					// String filmfile =
					// httpServletRequest.getSession().getServletContext().getRealPath("/")
					// + "upload/" + question.getFilmtwocodepath();
					// String similarfile =
					// httpServletRequest.getSession().getServletContext().getRealPath("/")
					// + "upload/" + question.getSimilartwocodepath();
					String questiontargetfile = targetfile + bookcontent.getTitle() + "/" + "试题" + "/";
					File questiontargetFile = new File(questiontargetfile);
					if (!questiontargetFile.exists() && !questiontargetFile.isDirectory()) {
						questiontargetFile.mkdirs();
					}
					// File filmFile = new File(filmfile);
					// File similarFile = new File(similarfile);
					ot.ossFileDowmload(question.getFilmtwocodepath(), "第" + (j + 1) + "题-微课.png");
					ot.ossFileDowmload(question.getSimilartwocodepath(), "第" + (j + 1) + "题-举一反三.png");
					// copyFile(filmFile, new File(questiontargetfile + "第" + (j
					// + 1) + "题-微课.png"));
					// copyFile(similarFile, new File(questiontargetfile + "第" +
					// (j + 1) + "题-举一反三.png"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// return actionMapping.findForward("two_code_success");
		// 自动下载zip文件包
		String uuid = UUID.getNewUUID();
		String realpath = httpServletRequest.getSession().getServletContext().getRealPath("/");
		String rootpath = realpath + "/uploadtemp/export";
		AntZipFile antZipFile = new AntZipFile();
		antZipFile.doZip(targetfile, rootpath, uuid + ".zip");
		// 下载包
		try {
			InputStream is = new FileInputStream(rootpath + "/" + uuid + ".zip");
			OutputStream os = httpServletResponse.getOutputStream();// 取得输出流
			httpServletResponse.reset();// 清空输出流
			httpServletResponse.setContentType("text/html; charset=utf-8");// text/html
			// application/octet-stream
			httpServletResponse.setContentType("bin");
			String name = model.getTitle() + versionname + ".zip";
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("gb2312"), "ISO8859-1"));// gb2312
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = is.read(buffer)) > 0) {
				os.write(buffer, 0, byteread);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file2 = new File(targetfile);
		deleteFile(file2);
		return null;
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	private void deleteFile(File file) {
		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
				file.delete();
			}
			file.delete();
		}
	}

	/**
	 * 复制文件
	 * 
	 * @throws IOException
	 * */
	public void copyFile(File sourcefile, File targetfile) throws IOException {
		if (sourcefile.isFile() && sourcefile.exists()) {
			FileInputStream input = new FileInputStream(sourcefile);
			BufferedInputStream inbuff = new BufferedInputStream(input);
			FileOutputStream output = new FileOutputStream(targetfile);
			BufferedOutputStream outbuff = new BufferedOutputStream(output);
			// 创建缓冲组
			byte[] b = new byte[1024 * 5];
			int len = 0;
			while ((len = inbuff.read(b)) != -1) {
				outbuff.write(b, 0, len);
			}
			outbuff.flush();
			outbuff.close();
			output.close();
			inbuff.close();
			input.close();
		}
	}

	/**
	 * 删除文件
	 * */
	public boolean deleteFile(String path) {
		boolean falg = false;
		File file = new File(path);
		if (file.isFile() && file.exists()) {
			file.delete();
			falg = true;
		}
		return falg;
	}

	/**
	 * 删除文件夹
	 * */
	public boolean deleteDirectory(String path) {
		File dirFile = new File(path);
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean falg = false;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				// 是文件
				falg = deleteFile(files[i].getAbsolutePath());
				if (!falg) {
					break;
				}
			} else {
				falg = deleteDirectory(files[i].getAbsolutePath());
				if (!falg) {
					break;
				}
			}
		}
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 导出作业本下的解题微课所有二维码
	 * */
	public ActionForward exportFilmCode(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String[] versionids = Constants.CODETYPE_VERSION_ID;
		String[] versionnames = Constants.CODETYPE_VERSION_NAME;
		TkBookInfo model = manager.getTkBookInfo(bookid);
		String versionname = "";
		for (int i = 0; i < versionids.length; i++) {
			if (versionids[i].equals(model.getVersion())) {
				versionname = versionnames[i];
			}
		}
		model.setTitle(model.getTitle().replaceAll("/", "").replaceAll("\\\\", "").replaceAll(":", "").replaceAll("\\*", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "").replaceAll("|", "").trim());
		if (model.getTitle().endsWith(".")) {
			model.setTitle(model.getTitle().substring(0, model.getTitle().length() - 1) + "。");
		}
		String targetfile = httpServletRequest.getSession().getServletContext().getRealPath("/") + "uploadtemp/export/contentfilm/" + model.getTitle() + versionname + "/";
		if (!FileUtil.isExistDir(targetfile)) {
			FileUtil.creatDir(targetfile);
		}
		try {
			// 查询作业本下的作业本内容
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "bookid", "=", model.getBookid());
			SearchCondition.addCondition(condition, "parentno", "!=", "0000");
			List<TkBookContent> bookcontelist = bookcontentManager.getTkBookContents(condition, "contentno", 0);
			TkBookContent bookcontent = null;
			for (int i = 0; i < bookcontelist.size(); i++) {
				bookcontent = bookcontelist.get(i);
				// 重新生成二维码
				String filepath = targetfile + bookcontent.getTitle().replaceAll("/", "").replaceAll("\\\\", "").replaceAll(":", "").replaceAll("\\*", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "").replaceAll("|", "").trim() + "_解题微课.png"; // 全路径
				String url = MpUtil.HOMEPAGE + "/twoCode.app?TkBookContent=" + bookcontent.getBookcontentid() + ":4";
				ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 自动下载zip文件包
		String uuid = UUID.getNewUUID();
		String realpath = httpServletRequest.getSession().getServletContext().getRealPath("/");
		String rootpath = realpath + "/uploadtemp/export/contentfilm";
		AntZipFile antZipFile = new AntZipFile();
		antZipFile.doZip(targetfile, rootpath, uuid + ".zip");
		// 下载包
		try {
			InputStream is = new FileInputStream(rootpath + "/" + uuid + ".zip");
			OutputStream os = httpServletResponse.getOutputStream();// 取得输出流
			httpServletResponse.reset();// 清空输出流
			httpServletResponse.setContentType("text/html; charset=utf-8");// text/html
			// application/octet-stream
			httpServletResponse.setContentType("bin");
			String name = model.getTitle() + versionname + "【解题微课】.zip";
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("gb2312"), "ISO8859-1"));// gb2312
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = is.read(buffer)) > 0) {
				os.write(buffer, 0, byteread);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		File file2 = new File(targetfile);
		deleteFile(file2);
		return null;
	}
}