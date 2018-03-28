package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysRoleInfo;
import com.wkmk.sys.service.SysRoleInfoManager;
import com.wkmk.sys.service.SysRoleModuleManager;
import com.wkmk.sys.service.SysUserRoleManager;
import com.wkmk.sys.web.form.SysRoleInfoActionForm;
import com.wkmk.util.common.Constants;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统角色信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysAdminRoleInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String rolename = Encode.nullToBlank(httpServletRequest.getParameter("rolename"));
		
		SysRoleInfoManager manager = (SysRoleInfoManager)getBean("sysRoleInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", 0);
		if(!"".equals(rolename)){
			SearchCondition.addCondition(condition, "rolename", "like", "%"+rolename+"%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "roleno asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysRoleInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		//如果当前角色与模块或用户关联则不可删除
		SysRoleModuleManager srmm = (SysRoleModuleManager) getBean("sysRoleModuleManager");
		List hasuseroleids = srmm.getAllRoleidsByProduct(null);
		//当角色与模块不关联时检查角色与用户的关联
		SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
		List userroleids = surm.getAllRoleids(null);
		
		List<Integer> sysroleids = Constants.getSysRoleids();
		List dataList = page.getDatalist();
		SysRoleInfo roleInfo = null;
		Integer roleid = null;
		for(int i=0; i<dataList.size(); i++){
			roleInfo = (SysRoleInfo) dataList.get(i);
			roleid = roleInfo.getRoleid();
			if(hasuseroleids.contains(roleid) || userroleids.contains(roleid) || sysroleids.contains(roleid)){
				roleInfo.setFlags("disabled=\"disabled\"");
			}
		}
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("rolename", rolename);
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
		String rolename = Encode.nullToBlank(httpServletRequest.getParameter("rolename"));
		httpServletRequest.setAttribute("rolename", rolename);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysRoleInfo model = new SysRoleInfo();
		model.setStatus("1");
		model.setUnitid(0);
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
		SysRoleInfoActionForm form = (SysRoleInfoActionForm)actionForm;
		SysRoleInfoManager manager = (SysRoleInfoManager)getBean("sysRoleInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysRoleInfo model = form.getSysRoleInfo();
				manager.addSysRoleInfo(model);
				//addLog(httpServletRequest,"增加了一个系统角色【" + model.getRolename() + "】信息");
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
		String rolename = Encode.nullToBlank(httpServletRequest.getParameter("rolename"));
		httpServletRequest.setAttribute("rolename", rolename);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysRoleInfoManager manager = (SysRoleInfoManager)getBean("sysRoleInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysRoleInfo model = manager.getSysRoleInfo(objid);
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
		SysRoleInfoActionForm form = (SysRoleInfoActionForm)actionForm;
		SysRoleInfoManager manager = (SysRoleInfoManager)getBean("sysRoleInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysRoleInfo model = form.getSysRoleInfo();
				manager.updateSysRoleInfo(model);
				//addLog(httpServletRequest,"修改了一个系统角色【" + model.getRolename() + "】信息");
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
		SysRoleInfoManager manager = (SysRoleInfoManager)getBean("sysRoleInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		SysRoleInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysRoleInfo(checkids[i]);
			manager.delSysRoleInfo(model);
			//addLog(httpServletRequest,"删除了一个系统角色【" + model.getRolename() + "】信息");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}