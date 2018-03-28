package com.wkmk.tk.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookContentBuyManager;
import com.wkmk.tk.service.TkBookContentGoodfilmManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.util.common.Constants;

public class TkBookContentBuyStatisticsAction extends BaseAction {
	/**
	 *跳转至主工作区间
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}
	/**
	 *树形选择器
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward tree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
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
		TkBookInfoManager bookinfomanager = (TkBookInfoManager) getBean("tkBookInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		List bookinfolist = bookinfomanager.getTkBookInfos(condition, "bookno", 0);
		TkBookInfo bookInfo = null;
		for (int i = 0; i < bookinfolist.size(); i++) {
			bookInfo = (TkBookInfo) bookinfolist.get(i);
			text = bookInfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookInfo.getVersion()) + "）";
			url = "/tkBookContentBuyStatisticsAction.do?method=list&bookid=" + bookInfo.getBookid();
			tree.append("\n").append("tree").append(".nodes[\"").append(bookInfo.getSubjectid() + "_")
					.append(bookInfo.getBookid() + "0000").append("\"]=\"");
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
				url = "/tkBookContentBuyStatisticsAction.do?method=list&bookid=" + bookcontent.getBookid() + "&parentno="
						+ bookcontent.getContentno();
				tree.append("\n").append("tree").append(".nodes[\"").append(bookcontent.getBookid()
						+ bookcontent.getParentno() + "_" + bookcontent.getBookid() + bookcontent.getContentno())
						.append("\"]=\"");

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
	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		TkBookContentManager bookContentManager = (TkBookContentManager)getBean("tkBookContentManager");
		List<SearchModel>condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "bookid", "=", bookid);
		SearchCondition.addCondition(condition, "parentno", "=", parentno);
		SearchCondition.addCondition(condition, "parentno", "<>", "0000");
		SearchCondition.addCondition(condition, "title", "like", "%"+title+"%");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		PageList page = bookContentManager.getPageTkBookContents(condition, "contentno", pageUtil.getStartCount(), pageUtil.getPageSize());
		condition.clear();
		List datalist = page.getDatalist();
		TkBookContent bookContent = null;
		TkBookContentBuyManager bookContentBuyManager = (TkBookContentBuyManager)getBean("tkBookContentBuyManager");
		if(datalist != null && datalist.size() > 0){
			for(int i = 0; i < datalist.size(); i++){
			    bookContent = (TkBookContent)datalist.get(i);
			    Integer contentid = bookContent.getBookcontentid();
			    //解题微课购买数量
			    SearchCondition.addCondition(condition, "contentid", "=", contentid);
			    SearchCondition.addCondition(condition, "type", "=", "1");
			    List buylist = bookContentBuyManager.getTkBookContentBuys(condition, "", 0);
			    bookContent.setFlago(String.valueOf(buylist.size()));
			    //举一反三微课购买数量
			    condition.clear();
			    SearchCondition.addCondition(condition, "contentid", "=", contentid);
			    SearchCondition.addCondition(condition, "type", "=", "2");
			    List buylist2 = bookContentBuyManager.getTkBookContentBuys(condition, "", 0);
			    bookContent.setFlags(String.valueOf(buylist2.size()));
			}
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("title", title);
		return actionMapping.findForward("list");
	}
}
