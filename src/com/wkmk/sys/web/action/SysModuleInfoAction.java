package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysModuleInfo;
import com.wkmk.sys.service.SysModuleInfoManager;
import com.wkmk.sys.service.SysProductInfoManager;
import com.wkmk.sys.service.SysRoleModuleManager;
import com.wkmk.sys.web.form.SysModuleInfoActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统模块信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysModuleInfoAction extends BaseAction {
	
	/**
	 *产品列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward productList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String productname = Encode.nullToBlank(httpServletRequest.getParameter("productname"));
		
		SysProductInfoManager manager = (SysProductInfoManager)getBean("sysProductInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		
		if(!"".equals(productname)){
			SearchCondition.addCondition(condition, "productname", "like", "%" + productname + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "orderindex asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysProductInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("productname", productname);
		return actionMapping.findForward("productlist");
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
		String productid = Encode.nullToBlank(httpServletRequest.getParameter("productid"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		String modulename = Encode.nullToBlank(httpServletRequest.getParameter("modulename"));
		
		SysModuleInfoManager manager = (SysModuleInfoManager)getBean("sysModuleInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "productid", "=", productid);
		SearchCondition.addCondition(condition, "parentno", "=", parentno);
		if(!"".equals(modulename)){
			SearchCondition.addCondition(condition, "modulename", "like", "%" + modulename + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "moduleno asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysModuleInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("modulename", modulename);
		
		//如果当前模块有子模块或在模块权限中出现过则不可删除
		SysRoleModuleManager srmm = (SysRoleModuleManager) getBean("sysRoleModuleManager");
		Integer pid = Integer.valueOf(productid);
		List allparentno = manager.getAllParentnoByProduct(pid);
		List hasusemoduleids = srmm.getAllModuleidsByProduct(pid);
		List dataList = page.getDatalist();
		SysModuleInfo moduleInfo = null;
		for(int i=0; i<dataList.size(); i++){
			moduleInfo = (SysModuleInfo) dataList.get(i);
			if(allparentno.contains(moduleInfo.getModuleno()) || hasusemoduleids.contains(moduleInfo.getModuleid())){
				moduleInfo.setFlags("disabled=\"disabled\"");
			}
		}
		
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("productid", productid);
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
		String productid = Encode.nullToBlank(httpServletRequest.getParameter("productid"));
		String modulename = Encode.nullToBlank(httpServletRequest.getParameter("modulename"));
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("productid", productid);
		httpServletRequest.setAttribute("modulename", modulename);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysModuleInfo model = new SysModuleInfo();
		model.setParentno(parentno);
		model.setModuleicon("/libs/images/module/arrow.gif");
		model.setLinkurl("");
		model.setStatus("1");
		model.setAutoopen("0");
		model.setProductid(Integer.valueOf(productid));
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		httpServletRequest.setAttribute("curno", "");
		
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
		SysModuleInfoActionForm form = (SysModuleInfoActionForm)actionForm;
		SysModuleInfoManager manager = (SysModuleInfoManager)getBean("sysModuleInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysModuleInfo model = form.getSysModuleInfo();
				manager.addSysModuleInfo(model);
				//addLog(httpServletRequest,"增加了一个系统模块【" + model.getModulename() + "】信息");
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
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		String productid = Encode.nullToBlank(httpServletRequest.getParameter("productid"));
		String modulename = Encode.nullToBlank(httpServletRequest.getParameter("modulename"));
		httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("productid", productid);
		httpServletRequest.setAttribute("modulename", modulename);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysModuleInfoManager manager = (SysModuleInfoManager)getBean("sysModuleInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysModuleInfo model = manager.getSysModuleInfo(objid);
			String curno = model.getModuleno().substring(model.getModuleno().length() - 4, model.getModuleno().length());
			httpServletRequest.setAttribute("curno", curno);
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
		SysModuleInfoActionForm form = (SysModuleInfoActionForm)actionForm;
		SysModuleInfoManager manager = (SysModuleInfoManager)getBean("sysModuleInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysModuleInfo model = form.getSysModuleInfo();
				manager.updateSysModuleInfo(model);
				//addLog(httpServletRequest,"修改了一个系统模块【" + model.getModulename() + "】信息");
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
		SysModuleInfoManager manager = (SysModuleInfoManager)getBean("sysModuleInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		SysModuleInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysModuleInfo(checkids[i]);
			manager.delSysModuleInfo(model);
			//addLog(httpServletRequest,"修改了一个系统模块【" + model.getModulename() + "】信息");
		}
		
		httpServletRequest.setAttribute("reloadtree", "1");
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 跳转到主工作区
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward main(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String productid = Encode.nullToBlank(httpServletRequest.getParameter("productid"));
		httpServletRequest.setAttribute("productid", productid);
		return actionMapping.findForward("main");
	}
	
	
	/**
	 * 树型选择器
	 */
	public ActionForward tree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		SysModuleInfoManager manager = (SysModuleInfoManager) getBean("sysModuleInfoManager");
		String productid = Encode.nullToBlank(httpServletRequest.getParameter("productid"));
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "productid", "=", Integer.valueOf(productid));
		List lst = manager.getSysModuleInfos(condition, "moduleno", 0);

		StringBuffer tree = new StringBuffer();
		String target = "rfrmright";
		String url = null;
		String rooturl = null;
		SysModuleInfo model = null;
		String moduleno = null;
		String parentno = null;
		String text = null;
		for(int i=0;i<lst.size();i++){
			model = (SysModuleInfo) lst.get(i);
			moduleno = model.getModuleno().trim();
			parentno = model.getParentno().trim();
			text = model.getModulename();
			
			url="/sysModuleInfoAction.do?method=list&parentno=" + moduleno + "&productid=" + productid;
			tree.append("\n").append("tree.nodes[\"").append(parentno).append("_").append(moduleno).append("\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("hint:;");
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		
		rooturl="/sysModuleInfoAction.do?method=list&parentno=0000&productid=" + productid;
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}
}