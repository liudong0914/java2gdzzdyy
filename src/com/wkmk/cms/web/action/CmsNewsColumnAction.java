package com.wkmk.cms.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.cms.bo.CmsNewsColumn;
import com.wkmk.cms.service.CmsNewsColumnManager;
import com.wkmk.cms.web.form.CmsNewsColumnActionForm;
import com.wkmk.cms.bo.CmsNewsInfo;
import com.wkmk.cms.service.CmsNewsInfoManager;
import com.wkmk.edu.service.EduKnopointInfoManager;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 资讯栏目</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsNewsColumnAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		httpServletRequest.setAttribute("parentno", parentno);
		String columnname = Encode.nullToBlank(httpServletRequest.getParameter("columnname"));
		httpServletRequest.setAttribute("columnname", columnname);
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer)session.getAttribute("s_unitid");
		CmsNewsColumn model = new CmsNewsColumn();
		CmsNewsColumnManager manager = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel>condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "parentno", "=", parentno);
		SearchCondition.addCondition(condition, "columnname", "like", "%"+columnname+"%");
		SearchCondition.addCondition(condition, "unitid", "=", Integer.valueOf(unitid));
		httpServletRequest.setAttribute("model", model);
		String sorderindex = "columnno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageCmsNewsColumns(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List lst = page.getDatalist();
		CmsNewsInfoManager cnim = (CmsNewsInfoManager)getBean("cmsNewsInfoManager");
		List hassublist = null;
		List hasreslist = null;
		hassublist = manager.getAllParentNo();
		hasreslist = cnim.getAllColumnIds();
		List list = new ArrayList();
		for (int i = 0; i < lst.size(); i++) {
			CmsNewsColumn ici = (CmsNewsColumn) lst.get(i);
			if (hasreslist.contains(ici.getColumnid())
					|| hassublist.contains(ici.getColumnno())) {
				ici.setFlags("disabled=true");
			}
			list.add(ici);
		}
		page.setDatalist((ArrayList) list);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
		return actionMapping.findForward("list");
		
	}

	/**
	 *增加前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		httpServletRequest.setAttribute("parentno", parentno);
		String columnname = Encode.nullToBlank(httpServletRequest.getParameter("columnname"));
		httpServletRequest.setAttribute("columnname", columnname);
		String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
		httpServletRequest.setAttribute("columnid", columnid);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		List<SearchModel>condition = new ArrayList<SearchModel>();
		CmsNewsColumnManager manager = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
		List lst = manager.getCmsNewsColumns(condition, "columnno", 0);
		httpServletRequest.setAttribute("r_columninfo_list", lst);
		SearchCondition.addCondition(condition, "parentno", "=", parentno);
		CmsNewsColumn model = new CmsNewsColumn();
		String num = "";
		List lst2 = manager.getCmsNewsColumns(condition, "columnno", 0);
		int k = lst2.size();
		if (k < 9) {
			num = "0" + String.valueOf(k + 1) + "00";
		} else {
			num = String.valueOf(k + 1) + "00";
		}
		model.setParentno(parentno);
		model.setStatus("1");
		model.setSketch("default.gif");
		model.setColumntype("1");
		httpServletRequest.setAttribute("num", num);
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 *增加时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		CmsNewsColumnActionForm form = (CmsNewsColumnActionForm)actionForm;
		CmsNewsColumnManager manager = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				HttpSession session = httpServletRequest.getSession();
				String columnno = Encode.nullToBlank(httpServletRequest.getParameter("columnno"));
				Integer unitid = (Integer)session.getAttribute("s_unitid");
				CmsNewsColumn model = form.getCmsNewsColumn();
				model.setUnitid(unitid);
				model.setColumntype("1");
				manager.addCmsNewsColumn(model);
				addLog(httpServletRequest,"增加了一个资讯栏目");
			}catch (Exception e){
				e.printStackTrace();
			}
		}else{
			saveToken(httpServletRequest);
			httpServletRequest.setAttribute("promptinfo", "请勿重复刷新页面!");
		}
		httpServletRequest.setAttribute("reloadtree", "1");
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *修改前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		CmsNewsColumnManager manager = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
		String columnname = Encode.nullToBlank(httpServletRequest.getParameter("columnname"));
		httpServletRequest.setAttribute("columnid", columnid);
		httpServletRequest.setAttribute("columnname", columnname);
		try {
			CmsNewsColumn model = manager.getCmsNewsColumn(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			//String columnno = model.getColumnno().substring(model.getParentno().length());
			//httpServletRequest.setAttribute("columnno", columnno);
			httpServletRequest.setAttribute("num", model.getColumnno().substring(model.getColumnno().length() - 4));
			List<SearchModel>condition = new ArrayList<SearchModel>();
			List lst = manager.getCmsNewsColumns(condition, "columnno", 0);
			httpServletRequest.setAttribute("r_columninfo_list", lst);
			//分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
		}catch (Exception e){
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 *修改时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		CmsNewsColumnActionForm form = (CmsNewsColumnActionForm)actionForm;
		CmsNewsColumnManager manager = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				HttpSession session = httpServletRequest.getSession();
				Integer unitid = (Integer)session.getAttribute("s_unitid");
				CmsNewsColumn model = form.getCmsNewsColumn();
				model.setColumnno(model.getParentno()+model.getColumnno());
				model.setUnitid(unitid);
				manager.updateCmsNewsColumn(model);
				addLog(httpServletRequest,"修改了一个资讯栏目");
			}catch (Exception e){
			}
		}else{
			saveToken(httpServletRequest);
			httpServletRequest.setAttribute("promptinfo", "请勿重复刷新页面!");
		}
		httpServletRequest.setAttribute("reloadtree", "1");
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *批量删除
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		CmsNewsColumnManager manager = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		CmsNewsColumn model = null;
        if(checkids != null){		for (int i = 0; i < checkids.length; i++) {
			model = manager.getCmsNewsColumn(checkids[i]);
			manager.delCmsNewsColumn(checkids[i]);
			addLog(httpServletRequest, "删除了一个" + model.getColumnname() + "章节内容");
		}
        }
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	/**
	 * 跳转到主工作区
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
	public ActionForward main(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
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
	public ActionForward tree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		CmsNewsColumnManager manager = (CmsNewsColumnManager) getBean("cmsNewsColumnManager");
		HttpSession session = httpServletRequest.getSession();
		List<SearchModel> condition = new ArrayList<SearchModel>();
		Integer unitid = (Integer)session.getAttribute("s_unitid");
		SearchCondition.addCondition(condition, "unitid", "=", Integer.valueOf(unitid));
		List lst = manager.getCmsNewsColumns(condition, "columnno", 0);
		
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target="rfrmright";
		String url = null;
		String hint = null;
		String id = null;//自身id
		String parentno = null;
		String columnno = null;
		CmsNewsColumn cnc = null;//栏目名称
		for(int k=0;k<lst.size();k++){
			cnc = (CmsNewsColumn) lst.get(k);
			id = cnc.getId().trim();
			parentno = cnc.getParentno();
			columnno = cnc.getColumnno();
			text = cnc.getColumnname();
			hint = "";
			url="cmsNewsColumnAction.do?method=list&parentno="
					 + cnc.getColumnno();
			tree.append("\n").append("tree").append(".nodes[\"").append(parentno).append("_").append(columnno).append("\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl="/cmsNewsColumnAction.do?method=list&parentno=0000";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		httpServletRequest.setAttribute("modulename", "资讯栏目");
		return actionMapping.findForward("tree");
	}
	/**
	 * 检查栏目是否存在
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward checkColumnno(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String columnno = Encode.nullToBlank(httpServletRequest.getParameter("columnno"));
		
		CmsNewsColumnManager manager = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "columnno", "=", columnno);
		
		List lst = manager.getCmsNewsColumns(condition, "", 0);

		PrintWriter pw = null;
		try {
			pw = httpServletResponse.getWriter();
			if(lst != null && lst.size() > 0){
				pw.write("1");
			}
		} catch (IOException ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}

		return null;
	}
}