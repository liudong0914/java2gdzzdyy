package com.wkmk.tk.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkBookContentGoodfilm;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookContentFilmManager;
import com.wkmk.tk.service.TkBookContentGoodfilmManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.web.form.TkBookContentGoodfilmActionForm;
import com.wkmk.util.common.Constants;
import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 * <p>
 * Description: 精品微课
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookContentGoodfilmAction extends BaseAction {

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
		TkBookContent model = new TkBookContent();
		TkBookContentGoodfilm tbcg = new TkBookContentGoodfilm();
		TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
		TkBookContentGoodfilmManager tbcgmanager = (TkBookContentGoodfilmManager) getBean("tkBookContentGoodfilmManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		// String sorderindex = "contentno asc";
		// if (!"".equals(pageUtil.getOrderindex())) {
		// sorderindex = pageUtil.getOrderindex();
		// }
		// PageList page = manager.getPageTkBookContents(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		PageList page = tbcgmanager.getPageTkBookContent(bookid, parentno, title, contentno, pageUtil.getStartCount(), pageUtil.getPageSize());
		List dataList = page.getDatalist();
		// List parentnos = manager.getAllparentnos(bookid);
		Integer bookcontentid = null;
		String goodfimltype = "";
		// 精品微课个数
		int goodfimltype1number = 0;
		// 精品举一反三微课可数
		int goodfilmtype2number = 0;
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				TkBookContent bookContent = (TkBookContent) dataList.get(i);
				bookcontentid = bookContent.getBookcontentid();
				List tbcglist = new ArrayList();
				// 获取goodfilm的信息，并判断其type
				tbcglist = tbcgmanager.getTkBookContentGoodfilmByBookcontentid(Integer.valueOf(bookcontentid));
				String flag = "";
				if (tbcglist.size() == 0) {
					flag = "00";// film表中没有值
				} else if (tbcglist.size() == 1) {
					for (int a = 0; a < tbcglist.size(); a++) {
						TkBookContentGoodfilm tbcm = (TkBookContentGoodfilm) tbcglist.get(a);
						String type = tbcm.getType();
						if ("1".equals(type)) {// 有type1 的值
							flag = "11";
						}
						if ("2".equals(type)) {// 有type2的值
							flag = "12";
						}
					}
				} else if (tbcglist.size() == 2) {// 1 2 都有
					flag = "212";
				}
				// 通过bookcontentid获取tk_book_content_film的集合
				List filmlist = tbcgmanager.getTkBookContentFilmByBookcontentid(bookcontentid);
				// System.out.println("filmlist="+filmlist+"size="+filmlist.size());
				// 解题微课个数
				int type1number = 0;
				// 举一反三微课个数
				int type2number = 0;
				if (filmlist.size() > 0) {
					for (int j = 0; j < filmlist.size(); j++) {
						// 通过type类型的个数来判断含有的type类型
						TkBookContentFilm tbcm = (TkBookContentFilm) filmlist.get(j);
						String type = tbcm.getType();
						Integer bookcontentid1 = (Integer) tbcg.getBookcontentid();
						if ("1".equals(type)) {
							type1number++;
						}
						if ("2".equals(type)) {
							type2number++;
						}
					}
				}
				if (type1number != 0 && type2number == 0 && "00".equals(flag)) {
					bookContent.setFlago("10");// 设为精品解题微课
				} else if (type1number != 0 && type2number == 0 && "11".equals(flag)) {
					bookContent.setFlago("11");// 删除精品解题微课
				} else if (type1number == 0 && type2number != 0 && "00".equals(flag)) {
					bookContent.setFlago("20");// 设为精品举一反三微课
				} else if (type1number == 0 && type2number != 0 && "12".equals(flag)) {
					bookContent.setFlago("21");// 删除精品举一反三微课
				} else if (type1number != 0 && type2number != 0 && "00".equals(flag)) {
					bookContent.setFlago("300");// 设为精品微课/设为精品举一反三微课
				} else if (type1number != 0 && type2number != 0 && "11".equals(flag)) {
					bookContent.setFlago("311");// 删除精品微课/设为精品举一反三微课
				} else if (type1number != 0 && type2number != 0 && "12".equals(flag)) {
					bookContent.setFlago("312");// 设为精品微课/删除精品举一反三微课
				} else if (type1number != 0 && type2number != 0 && "212".equals(flag)) {
					bookContent.setFlago("313");// 删除精品微课/删除精品举一反三微课
				} else if (type1number == 0 && type2number == 0) {
					bookContent.setFlago("00");// 该章节下没有微课，内容不显示
				}
			}
		}
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("contentno", contentno);
		return actionMapping.findForward("list");
	}

	/**
	 * 设置为精品微课
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
	public ActionForward setGood(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentGoodfilmManager manager = (TkBookContentGoodfilmManager) getBean("tkBookContentGoodfilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		TkBookContentGoodfilm model = new TkBookContentGoodfilm();
		model.setBookcontentid(Integer.valueOf(bookcontentid));
		model.setBookid(Integer.valueOf(bookid));
		model.setType(type);
		model.setStatus("1");
		manager.addTkBookContentGoodfilm(model);
		addLog(httpServletRequest, "增加了一个精品微课");
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 删除精品微课
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
	public ActionForward delGood(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentGoodfilmManager manager = (TkBookContentGoodfilmManager) getBean("tkBookContentGoodfilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		TkBookContentGoodfilm model = new TkBookContentGoodfilm();
		model = manager.getTkBookContentGoodfilmByBookcontentidAndType(Integer.valueOf(bookcontentid), type);
		String goodfilmid = model.getGoodfilmid().toString();
		manager.delTkBookContentGoodfilm(goodfilmid);
		addLog(httpServletRequest, "删除了一个精品微课");
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 跳转至主工作区间
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
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}

	/**
	 * 树形选择器
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
		// List<SearchModel> condition = new ArrayList<SearchModel>();
		// SearchCondition.addCondition(condition, "status", "=", "1");//管理员功能不控制
		List<TkBookInfo> bookinfoList = bookinfoManager.getBookInfosByFilm();
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + "）";
			// url = "javascript:;";
			url = "/tkBookContentGoodfilmAction.do?method=list&bookid=" + bookinfo.getBookid();
			tree.append("\n").append("tree").append(".nodes[\"").append(bookinfo.getSubjectid() + "_").append(bookinfo.getBookid() + "0000").append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询相关章节
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		TkBookContentGoodfilmManager tbcgm = (TkBookContentGoodfilmManager) getBean("tkBookContentGoodfilmManager");
		List<SearchModel> condition2 = new ArrayList<SearchModel>();
		List<TkBookContent> contentList = bookcontentManager.getTkBookContents(condition2, "contentno", 0);
		TkBookContent bookcontent = null;
		for (int i = 0; i < contentList.size(); i++) {
			bookcontent = (TkBookContent) contentList.get(i);
			if (bookcontent.getContentno().length() < 12) {
				text = bookcontent.getTitle();
				Integer bookcontentid = bookcontent.getBookcontentid();
				// List filmlist = tbcgm.getTkBookContentFilmByBookcontentid(bookcontentid);
				// if(bookcontent.getParentno() != "0000" ){
				url = "/tkBookContentGoodfilmAction.do?method=list&bookid=" + bookcontent.getBookid() + "&parentno=" + bookcontent.getContentno();
				// }else{
				// url = "/tkBookContentGoodfilmAction.do?method=list&bookid=" + bookcontent.getBookid();
				// }
				tree.append("\n").append("tree").append(".nodes[\"").append(bookcontent.getBookid() + bookcontent.getParentno() + "_" + bookcontent.getBookid() + bookcontent.getContentno()).append("\"]=\"");
				if (text != null && !"".equals(text.trim())) {
					tree.append("text:").append(text).append(";");
				}
				tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
			}
		}
		String rooturl = "javascript:;";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}
}