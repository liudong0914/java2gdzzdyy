package com.wkmk.tk.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.UUID;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkPaperInfo;
import com.wkmk.tk.bo.TkQuestionsFilm;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsKnopoint;
import com.wkmk.tk.bo.TkQuestionsSimilar;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkPaperInfoManager;
import com.wkmk.tk.service.TkQuestionsFilmManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsKnopointManager;
import com.wkmk.tk.service.TkQuestionsSimilarManager;
import com.wkmk.tk.web.form.TkBookContentActionForm;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.TwoCodeUtil;

/**
 * <p>
 * Description: 作业本内容
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookContentAction extends BaseAction {

	/**
	 * 跳转到工作区
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
		EduSubjectInfo esi = null;
		for (int i = 0; i < subjectList.size(); i++) {
			esi = (EduSubjectInfo) subjectList.get(i);
			text = esi.getSubjectname();
			url = "javascript:;";
			tree.append("\n").append("tree").append(".nodes[\"0000_").append(esi.getSubjectid()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询相关作业本
		TkBookInfoManager bookinfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		// SearchCondition.addCondition(condition, "status", "=", "1");//管理员功能不控制
		List<TkBookInfo> bookinfoList = bookinfoManager.getTkBookInfos(condition, "bookno", 0);
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + "）";
			url = "/tkBookContentAction.do?method=list&bookid=" + bookinfo.getBookid() + "&parentno=0000";
			tree.append("\n").append("tree").append(".nodes[\"").append(bookinfo.getSubjectid() + "_").append(bookinfo.getBookid() + "0000").append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询相关章节
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		List<SearchModel> condition2 = new ArrayList<SearchModel>();
		List<TkBookContent> contentList = bookcontentManager.getTkBookContents(condition2, "contentno", 0);
		TkBookContent bookcontent = null;
		for (int i = 0; i < contentList.size(); i++) {
			bookcontent = (TkBookContent) contentList.get(i);
			text = bookcontent.getTitle();
			url = "/tkBookContentAction.do?method=list&bookid=" + bookcontent.getBookid() + "&parentno=" + bookcontent.getContentno();
			tree.append("\n").append("tree").append(".nodes[\"").append(bookcontent.getBookid() + bookcontent.getParentno() + "_" + bookcontent.getBookid() + bookcontent.getContentno()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
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
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String contentno = Encode.nullToBlank(httpServletRequest.getParameter("contentno"));
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "bookid", "=", Integer.parseInt(bookid));
		SearchCondition.addCondition(condition, "parentno", "=", parentno);
		SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		SearchCondition.addCondition(condition, "contentno", "like", "%" + contentno + "%");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "contentno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageTkBookContents(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List dataList = page.getDatalist();
		List parentnos = manager.getAllparentnos(bookid);
		TkBookContent model = null;
		for (int i = 0; i < dataList.size(); i++) {
			model = (TkBookContent) dataList.get(i);
			if (parentnos.contains(model.getContentno())) {
				model.setFlags("disabled=\"disabled\"");
			}
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("contentno", contentno);
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
	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContent model = new TkBookContent();
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String contentno = Encode.nullToBlank(httpServletRequest.getParameter("contentno"));
		model.setParentno(parentno);
		model.setBookid(Integer.parseInt(bookid));
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		httpServletRequest.setAttribute("title", "新建章节内容");
		// 分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("contentno", contentno);
		saveToken(httpServletRequest);
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
		TkBookContentActionForm form = (TkBookContentActionForm) actionForm;
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContent model = form.getTkBookContent();
				model.setPaperid(0);
				model.setContentno(model.getParentno() + model.getContentno());
				manager.addTkBookContent(model);
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkBookInfo tbi = tbim.getTkBookInfo(model.getBookid());
				int subjectid = tbi.getSubjectid().intValue();// 初步只有英语才生成听力二维码
				FormFile uploadFile = form.getThefile();
				if (uploadFile != null && uploadFile.getFileName() != null && !"".equals(uploadFile.getFileName())) {
					String mp3path = uploadMp3(httpServletRequest, model.getBookid().toString(), model.getBookcontentid().toString(), uploadFile);
					model.setMp3path(mp3path);
				}
				model.setBeforeclasstwocode(TwoCodeUtil.getbeforeClassTwoCodePath(model, httpServletRequest));
				model.setTeachingcasetwocode(TwoCodeUtil.getteachingCaseTwoCodePath(model, httpServletRequest));
				if (subjectid == 13 || (model.getMp3path() != null && !"".equals(model.getMp3path()))) {
					model.setMp3pathtwocode(TwoCodeUtil.getMp3pathTwoCodePath(model, httpServletRequest));
				}
				manager.updateTkBookContent(model);
				addLog(httpServletRequest, "增加了一个" + model.getTitle() + "作业本内容");
				httpServletRequest.setAttribute("reloadtree", 1);
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
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String contentno = Encode.nullToBlank(httpServletRequest.getParameter("contentno"));
		try {
			TkBookContent model = manager.getTkBookContent(objid);
			model.setContentno(model.getContentno().substring(model.getContentno().length() - 4));
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("title", "修改章节内容");
			httpServletRequest.setAttribute("parentno", parentno);
			httpServletRequest.setAttribute("bookid", bookid);
			// 分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
			httpServletRequest.setAttribute("title", title);
			httpServletRequest.setAttribute("contentno", contentno);
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
		TkBookContentActionForm form = (TkBookContentActionForm) actionForm;
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContent model = form.getTkBookContent();
				model.setContentno(model.getParentno() + model.getContentno());
				if (!"0000".equals(model.getParentno())) {
					FormFile theFile = form.getThefile();
					if (theFile != null && theFile.getFileName() != null && !"".equals(theFile.getFileName())) {
						String mp3path = uploadMp3(httpServletRequest, model.getBookid().toString(), model.getBookcontentid().toString(), theFile);
						model.setMp3path(mp3path);
					} else {
						model.setMp3path(httpServletRequest.getParameter("mp3path"));
					}
					if (model.getMp3path() != null && !"".equals(model.getMp3path()) && (model.getMp3pathtwocode() == null || "".equals(model.getMp3pathtwocode()))) {
						model.setMp3pathtwocode(TwoCodeUtil.getMp3pathTwoCodePath(model, httpServletRequest));
					}
				}
				manager.updateTkBookContent(model);
				addLog(httpServletRequest, "修改了一个" + model.getTitle() + "作业本内容");
				httpServletRequest.setAttribute("reloadtree", 1);
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
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		TkBookContent model = null;
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				model = manager.getTkBookContent(checkids[i]);
				manager.delTkBookContent(checkids[i]);
				addLog(httpServletRequest, "删除了一个" + model.getTitle() + "章节内容");
			}
		}
		httpServletRequest.setAttribute("reloadtree", 1);
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 判断编号是否存在
	 * */
	public ActionForward ishavecontentno(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String contentno = Encode.nullToBlank(httpServletRequest.getParameter("contentno"));
		httpServletResponse.setContentType("text/plain;charset=gbk");
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		PrintWriter out = null;
		try {
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "bookid", "=", bookid);
			SearchCondition.addCondition(condition, "contentno", "=", contentno);
			List list = manager.getTkBookContents(condition, "", 0);
			out = httpServletResponse.getWriter();
			if (list == null || list.size() == 0) {
				out.print("ok");
			} else {
				out.print("当前章节编号已被占用，请重试！");
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
	 * 选择管理试卷
	 * */
	public ActionForward pagelist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String bookcontenid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		TkPaperInfoManager paperinfoManager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		TkBookContent model = manager.getTkBookContent(bookcontenid);
		PageList page = null;
		if (model != null && model.getPaperid() != null && model.getPaperid().intValue() > 0) {
			TkPaperInfo papermodel = paperinfoManager.getTkPaperInfo(model.getPaperid());
			httpServletRequest.setAttribute("papermodel", papermodel);
			TkBookInfoManager bookinfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
			TkBookInfo bookmodel = bookinfoManager.getTkBookInfo(model.getBookid());
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "subjectid", "=", bookmodel.getSubjectid());
			SearchCondition.addCondition(condition, "gradeid", "=", bookmodel.getGradeid());
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
			SearchCondition.addCondition(condition, "status", "=", "1");
			PageUtil pageUtil = new PageUtil(httpServletRequest);
			String sorderindex = "paperid asc";
			if (!"".equals(pageUtil.getOrderindex())) {
				sorderindex = pageUtil.getOrderindex();
			}
			page = paperinfoManager.getPageTkPaperInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("bookcontenid", bookcontenid);
		return actionMapping.findForward("page_list");
	}

	/**
	 * 关联试卷
	 * */
	public ActionForward selectPage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		String bookcontenid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		String checkid = Encode.nullToBlank(httpServletRequest.getParameter("checkid"));
		if (!"".equals(checkid)) {
			TkBookContent model = manager.getTkBookContent(bookcontenid);
			model.setPaperid(Integer.parseInt(checkid));
			manager.updateTkBookContent(model);
		}
		return pagelist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 删除关联试卷
	 * */
	public ActionForward deletePage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		TkBookContent model = manager.getTkBookContent(bookcontentid);
		model.setPaperid(0);
		manager.updateTkBookContent(model);
		return pagelist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	// 上传Mp3
	public String uploadMp3(HttpServletRequest request, String bookid, String bookcontentid, FormFile uploadFile) {
		String uploadPath = "twocode/book/" + bookid + "/" + bookcontentid + "/";// 上传文件的目录
		String serverPath = request.getSession().getServletContext().getRealPath("/");
		serverPath = serverPath + "/upload/";
		try {
			// 解决上传漏洞攻击，只能上传指定扩展名(.mp3)的文件
			String fname = uploadFile.getFileName();
			String sFileExt = FileUtil.getFileExt(fname).toLowerCase();
			if (fname.indexOf(".jsp") != -1 || fname.indexOf(".jspx") != -1 || fname.indexOf(".exe") != -1 || fname.indexOf(".bat") != -1 || fname.indexOf(".js") != -1) {
				return null;
			}
			if (!"mp3".equals(sFileExt)) {
				return null;
			}
			File floderfile = new File(serverPath + uploadPath);
			if (!floderfile.isDirectory()) {
				floderfile.mkdirs();
			}
			String uuid = UUID.getNewUUID();
			File file = new File(serverPath + uploadPath + uuid + ".mp3");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = uploadFile.getFileData();
			out.write(buffer);
			out.close();
			uploadFile.destroy();
			return uploadPath + uuid + ".mp3";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 替换试题前查询书本中的试题
	 * */
	public ActionForward replaceQuestion(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		int paperid = bookcontentManager.getTkBookContent(bookcontentid).getPaperid();
		TkPaperContentManager papercontentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "questionno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList questionPageist = papercontentManager.getQuestions(paperid, questionno, title, difficult, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", questionPageist);
		httpServletRequest.setAttribute("questionno", questionno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficult);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		return actionMapping.findForward("before_replaceQuestion");
	}

	public ActionForward beforereplace(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String questionno = Encode.nullToBlank(httpServletRequest.getParameter("questionno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String difficult = Encode.nullToBlank(httpServletRequest.getParameter("difficult"));
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		TkBookInfoManager bookinfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		TkQuestionsInfoManager questionManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		TkBookContent bookcontent = bookcontentManager.getTkBookContent(bookcontentid);
		TkBookInfo bookinfo = bookinfoManager.getTkBookInfo(bookcontent.getBookid());
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "questionno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", bookinfo.getSubjectid());
		SearchCondition.addCondition(condition, "gradeid", "=", bookinfo.getGradeid());
		SearchCondition.addCondition(condition, "questionid", "!=", Integer.parseInt(questionid));
		SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		SearchCondition.addCondition(condition, "questionno", "like", "%" + questionno + "%");
		SearchCondition.addCondition(condition, "parentid", "=", 0);
		SearchCondition.addCondition(condition, "difficult", "=", difficult);
		PageList page = questionManager.getPageTkQuestionsInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("subjectid", bookinfo.getSubjectid());
		httpServletRequest.setAttribute("questionid", questionid);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("questionnno", questionno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("difficult", difficult);
		return actionMapping.findForward("before_replace");
	}

	/**
	 * 替换题目
	 * */
	public ActionForward replace(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
		String checkid = Encode.nullToBlank(httpServletRequest.getParameter("checkid"));
		if (!"".equals(checkid)) {
			TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
			TkQuestionsInfo modelA = manager.getTkQuestionsInfo(questionid);
			TkQuestionsInfo modelB = manager.getTkQuestionsInfo(checkid);
			TkQuestionsInfo modelC = manager.getTkQuestionsInfo(checkid);// 保留原始题
			// 如果modelA为复合题
			if ("F".equals(modelA.getTkQuestionsType().getType()) || "M".equals(modelA.getTkQuestionsType().getType())) {
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "parentid", "=", questionid);
				List<TkQuestionsInfo> questionlist = manager.getTkQuestionsInfos(condition, "questionno", 0);
				for (TkQuestionsInfo tk : questionlist) {
					tk.setParentid(Integer.parseInt(checkid));
					manager.updateTkQuestionsInfo(tk);
				}
			}
			modelB.setQuestionno(modelA.getQuestionno());
			modelB.setTitle(modelA.getTitle());
			modelB.setTitlecontent(modelA.getTitlecontent());
			modelB.setFilepath(modelA.getFilepath());
			modelB.setOptionnum(modelA.getOptionnum());
			modelB.setOption1(modelA.getOption1());
			modelB.setOption2(modelA.getOption2());
			modelB.setOption3(modelA.getOption3());
			modelB.setOption4(modelA.getOption4());
			modelB.setOption5(modelA.getOption5());
			modelB.setOption6(modelA.getOption6());
			modelB.setOption7(modelA.getOption7());
			modelB.setOption8(modelA.getOption8());
			modelB.setOption9(modelA.getOption9());
			modelB.setOption10(modelA.getOption10());
			modelB.setQuestiontype(modelA.getQuestiontype());
			modelB.setDoctype(modelA.getDoctype());
			modelB.setRightans(modelA.getRightans());
			modelB.setIsrightans(modelA.getIsrightans());
			modelB.setDifficult(modelA.getDifficult());
			modelB.setScore(modelA.getScore());
			modelB.setCretatdate(modelA.getCretatdate());
			// modelB.setUpdatetime(modelA.getUpdatetime());
			modelB.setUpdatetime(DateTime.getDate());
			modelB.setDescript(modelA.getDescript());
			modelB.setDescriptpath(modelA.getDescriptpath());
			modelB.setStatus(modelA.getStatus());
			modelB.setArea(modelA.getArea());
			modelB.setTheyear(modelA.getTheyear());
			modelB.setAuthorname(modelA.getAuthorname());
			modelB.setAuthorid(modelA.getAuthorid());
			modelB.setTkQuestionsType(modelA.getTkQuestionsType());
			modelB.setSubjectid(modelA.getSubjectid());
			modelB.setGradeid(modelA.getGradeid());
			modelB.setUnitid(modelA.getUnitid());
			modelB.setTag(modelA.getTag());
			manager.updateTkQuestionsInfo(modelB);
			CacheUtil.put("TkQuestionsInfo_" + checkid, modelB, 24 * 30);
			// 修改TkPaperContent中的数据
			TkPaperContentManager papercontentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "questionid", "=", modelA.getQuestionid());
			List<TkPaperContent> paperconteList = papercontentManager.getTkPaperContents(condition, "contentid", 0);
			for (TkPaperContent tpc : paperconteList) {
				tpc.setQuestionid(modelB.getQuestionid());
				papercontentManager.updateTkPaperContent(tpc);
			}
			// 修改TkQuestionsKnopoint中的数据
			TkQuestionsKnopointManager knopointManager = (TkQuestionsKnopointManager) getBean("tkQuestionsKnopointManager");
			List<TkQuestionsKnopoint> knopointList = knopointManager.getTkQuestionsKnopoints(condition, "tid", 0);
			for (TkQuestionsKnopoint tk : knopointList) {
				tk.setQuestionid(modelB.getQuestionid());
				knopointManager.updateTkQuestionsKnopoint(tk);
			}
			// 修改TkQuestionsFilm中的数据
			TkQuestionsFilmManager questionfilmManager = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
			List<TkQuestionsFilm> questionfilmList = questionfilmManager.getTkQuestionsFilms(condition, "tid", 0);
			for (TkQuestionsFilm tf : questionfilmList) {
				tf.setQuestionid(modelB.getQuestionid());
				questionfilmManager.updateTkQuestionsFilm(tf);
			}
			// 修改tk_questions_similar中的数据
			TkQuestionsSimilarManager questionssimilarManager = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
			List<TkQuestionsSimilar> similarList = questionssimilarManager.getTkQuestionsSimilars(condition, "tid", 0);
			for (TkQuestionsSimilar ts : similarList) {
				ts.setQuestionid(modelB.getQuestionid());
				questionssimilarManager.updateTkQuestionsSimilar(ts);
			}
			addLog(httpServletRequest, "替换了原始题(questionid=" + modelC.getQuestionid() + ",title=" + modelC.getTitle() + ")的内容为新题(questionid=" + modelA.getQuestionid() + ")的内容");
			// 删除modelA【原始题先不删除，避免操作错误可重新操作】
			// manager.delTkQuestionsInfo(modelA);
			modelA.setQuestionno(modelC.getQuestionno());
			modelA.setTitle(modelC.getTitle() + "【已替换】");
			modelA.setTitlecontent(modelC.getTitlecontent() + "【已替换】");
			modelA.setFilmtwocodepath(modelC.getFilmtwocodepath());
			modelA.setSimilartwocodepath(modelC.getSimilartwocodepath());
			modelA.setCretatdate("2008-08-08 20:00:00");
			modelA.setUpdatetime(modelB.getUpdatetime());
			manager.updateTkQuestionsInfo(modelA);
		}
		return actionMapping.findForward("close");
	}
}