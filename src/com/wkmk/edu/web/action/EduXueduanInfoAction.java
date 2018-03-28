package com.wkmk.edu.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduXueduanInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduXueduanInfoManager;
import com.wkmk.edu.web.form.EduXueduanInfoActionForm;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 教育学段信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduXueduanInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String parentid = Encode.nullToBlank(httpServletRequest.getParameter("parentid"));
		String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
		
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		EduXueduanInfoManager manager = (EduXueduanInfoManager)getBean("eduXueduanInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "parentid", "=", parentid);
		if(!"".equals(name)){
			SearchCondition.addCondition(condition, "name", "=", "%" + name + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "orderindex asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageEduXueduanInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List allxueduanids1 = egim.getAllXueduanids();
		List allparentids = manager.getAllParentids();
		List allcxueduanids1 = new ArrayList();
		if(!"0".equals(parentid)){
			allcxueduanids1 = egim.getAllCxueduanids();
		}
		List dataList = page.getDatalist();
		EduXueduanInfo model = null;
		for(int i=0, size=dataList.size(); i<size; i++){
			model = (EduXueduanInfo) dataList.get(i);
			if(allxueduanids1.contains(model.getXueduanid()) || allparentids.contains(model.getXueduanid()) || allcxueduanids1.contains(model.getXueduanid())){
				model.setFlags("disabled=\"disabled\"");
			}
		}
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("name", name);
		httpServletRequest.setAttribute("parentid", parentid);
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
		String parentid = Encode.nullToBlank(httpServletRequest.getParameter("parentid"));
		String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
		httpServletRequest.setAttribute("parentid", parentid);
		httpServletRequest.setAttribute("name", name);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		EduXueduanInfo model = new EduXueduanInfo();
		model.setParentid(Integer.valueOf(parentid));
		model.setOrderindex(1);
		model.setStatus("1");
		model.setUpdatetime(DateTime.getDate());
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
		EduXueduanInfoActionForm form = (EduXueduanInfoActionForm)actionForm;
		EduXueduanInfoManager manager = (EduXueduanInfoManager)getBean("eduXueduanInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduXueduanInfo model = form.getEduXueduanInfo();
				manager.addEduXueduanInfo(model);
				addLog(httpServletRequest,"增加了一个教育学段【" + model.getName() + "】信息");
			}catch (Exception e){
			}
		}

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
		String parentid = Encode.nullToBlank(httpServletRequest.getParameter("parentid"));
		String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
		httpServletRequest.setAttribute("parentid", parentid);
		httpServletRequest.setAttribute("name", name);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		EduXueduanInfoManager manager = (EduXueduanInfoManager)getBean("eduXueduanInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			EduXueduanInfo model = manager.getEduXueduanInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
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
		EduXueduanInfoActionForm form = (EduXueduanInfoActionForm)actionForm;
		EduXueduanInfoManager manager = (EduXueduanInfoManager)getBean("eduXueduanInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduXueduanInfo model = form.getEduXueduanInfo();
				model.setUpdatetime(DateTime.getDate());
				manager.updateEduXueduanInfo(model);
				addLog(httpServletRequest,"修改了一个教育学段【" + model.getName() + "】信息");
			}catch (Exception e){
			}
		}

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
		EduXueduanInfoManager manager = (EduXueduanInfoManager)getBean("eduXueduanInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		EduXueduanInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getEduXueduanInfo(checkids[i]);
			manager.delEduXueduanInfo(model);
			addLog(httpServletRequest,"删除了一个教育学段【" + model.getName() + "】信息");
		}
		
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 跳转到主工作区
	 */
	public ActionForward main(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}

	/**
	 * 树型选择器
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward tree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		EduXueduanInfoManager manager = (EduXueduanInfoManager) getBean("eduXueduanInfoManager");
		
		List<SearchModel> condition = new ArrayList<SearchModel>();
		List lst = manager.getEduXueduanInfos(condition, "orderindex", 0);
		
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target="rfrmright";
		String url = null;
		String hint = null;
		String xueduanid = null;
		String parentid = null;
		EduXueduanInfo xueduan = null;
		for(int k=0;k<lst.size();k++){
			xueduan = (EduXueduanInfo) lst.get(k);
			xueduanid = xueduan.getXueduanid().toString();
			parentid = xueduan.getParentid().toString();
			text = xueduan.getName().trim();
			hint = "";
			url="/eduXueduanInfoAction.do?method=list&parentid=" + xueduanid;
			tree.append("\n").append("tree").append(".nodes[\"").append(parentid).append("_").append(xueduanid).append("\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl="/eduXueduanInfoAction.do?method=list&parentid=0";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}
	
	/**
	 * 获取某父学段对应的子学段选项卡
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward getCxueduanSelect(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String xueduanid = Encode.nullToBlank(httpServletRequest.getParameter("xueduanid"));
		
		EduXueduanInfoManager manager = (EduXueduanInfoManager) getBean("eduXueduanInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "parentid", "=", Integer.valueOf(xueduanid));
		
		List cxdList = manager.getEduXueduanInfos(condition, "orderindex asc", 0);
		StringBuffer str = new StringBuffer();
		EduXueduanInfo cxueduan = null;
		for(int i=0, size=cxdList.size(); i<size; i++){
			cxueduan = (EduXueduanInfo) cxdList.get(i);
			str.append("\n").append("<option value=\"").append(cxueduan.getXueduanid()).append("\" >").append(cxueduan.getName()).append("</option>");
		}

		PrintWriter pw = null;
		try {
			pw = httpServletResponse.getWriter();
			pw.write(str.toString());
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