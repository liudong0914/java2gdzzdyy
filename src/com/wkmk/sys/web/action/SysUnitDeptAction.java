package com.wkmk.sys.web.action;

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

import com.wkmk.sys.bo.SysUnitDept;
import com.wkmk.sys.service.SysUnitDeptManager;
import com.wkmk.sys.service.SysUnitDeptMemberManager;
import com.wkmk.sys.web.form.SysUnitDeptActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统单位机构</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitDeptAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String deptname = Encode.nullToBlank(httpServletRequest.getParameter("deptname"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		
		SysUnitDeptManager manager = (SysUnitDeptManager)getBean("sysUnitDeptManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "parentno", "=", parentno);
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		if(!"".equals(deptname)){
			SearchCondition.addCondition(condition, "deptname", "like", "%" + deptname + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "deptno asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUnitDepts(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		SysUnitDeptMemberManager sudmm = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
		List groupids = sudmm.getAllDeptidsByUnitid(unitid);
		List allparentno = manager.getAllParentno(unitid);
		List dataList = page.getDatalist();
		SysUnitDept dept = null;
		for(int i=0; i<dataList.size(); i++){
			dept = (SysUnitDept) dataList.get(i);
			if(allparentno.contains(dept.getDeptno()) || groupids.contains(dept.getDeptid())){
				dept.setFlags("disabled=\"disabled\"");
			}
		}
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("deptname", deptname);
		httpServletRequest.setAttribute("parentno", parentno);
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
		String deptname = Encode.nullToBlank(httpServletRequest.getParameter("deptname"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		httpServletRequest.setAttribute("deptname", deptname);
		httpServletRequest.setAttribute("parentno", parentno);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		SysUnitDept model = new SysUnitDept();
		model.setParentno(parentno);
		model.setStatus("1");
		model.setUnitid(unitid);
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		httpServletRequest.setAttribute("deptnum", "");
		
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
		SysUnitDeptActionForm form = (SysUnitDeptActionForm)actionForm;
		SysUnitDeptManager manager = (SysUnitDeptManager)getBean("sysUnitDeptManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUnitDept model = form.getSysUnitDept();
				manager.addSysUnitDept(model);
				addLog(httpServletRequest,"增加了一个系统单位机构【" + model.getDeptname() + "】");
			}catch (Exception e){
			}
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
		String deptname = Encode.nullToBlank(httpServletRequest.getParameter("deptname"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		httpServletRequest.setAttribute("deptname", deptname);
		httpServletRequest.setAttribute("parentno", parentno);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysUnitDeptManager manager = (SysUnitDeptManager)getBean("sysUnitDeptManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysUnitDept model = manager.getSysUnitDept(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("deptnum", model.getDeptno().substring(model.getDeptno().length()-4, model.getDeptno().length()));
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
		SysUnitDeptActionForm form = (SysUnitDeptActionForm)actionForm;
		SysUnitDeptManager manager = (SysUnitDeptManager)getBean("sysUnitDeptManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUnitDept model = form.getSysUnitDept();
				manager.updateSysUnitDept(model);
				addLog(httpServletRequest,"修改了一个系统单位机构【" + model.getDeptname() + "】");
			}catch (Exception e){
			}
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
		SysUnitDeptManager manager = (SysUnitDeptManager)getBean("sysUnitDeptManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		SysUnitDept model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysUnitDept(checkids[i]);
			manager.delSysUnitDept(model);
			addLog(httpServletRequest,"删除了一个系统单位机构【" + model.getDeptname() + "】");
		}
		
		httpServletRequest.setAttribute("reloadtree", "1");
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
		SysUnitDeptManager manager = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		List lst = manager.getSysUnitDepts(condition, "deptno", 0);
		
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target="rfrmright";
		String url = null;
		String hint = null;
		String groupno = null;
		String parentno = null;
		SysUnitDept dept = null;
		for(int k=0;k<lst.size();k++){
			dept = (SysUnitDept) lst.get(k);
			groupno = dept.getDeptno().trim();
			parentno = dept.getParentno().trim();
			text = dept.getDeptname();
			hint = "";
			//if(groupno.length() > 12){
			//	url="javascript:alert('机构部门层级不能超过三级。')";
			//}else {
				url="/sysUnitDeptAction.do?method=list&parentno=" + groupno;
			//}
			tree.append("\n").append("tree").append(".nodes[\"").append(parentno).append("_").append(groupno).append("\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl="/sysUnitDeptAction.do?method=list&parentno=0000";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
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
	public ActionForward checkDeptno(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String deptno = Encode.nullToBlank(httpServletRequest.getParameter("deptno"));
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		
		SysUnitDeptManager manager = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		SearchCondition.addCondition(condition, "deptno", "=", deptno);
		
		List lst = manager.getSysUnitDepts(condition, "", 0);

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