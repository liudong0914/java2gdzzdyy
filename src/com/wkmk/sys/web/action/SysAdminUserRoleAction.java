package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysRoleInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserRole;
import com.wkmk.sys.service.SysRoleInfoManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserRoleManager;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统用户角色</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysAdminUserRoleAction extends BaseAction {

	public ActionForward main(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		SysRoleInfoManager manager = (SysRoleInfoManager) getBean("sysRoleInfoManager");
		SysRoleInfo roleInfo = manager.getSysRoleInfo(roleid);
		String rolename = roleInfo.getRolename();
		httpServletRequest.setAttribute("rolename", rolename);

		httpServletRequest.setAttribute("roleid", roleid);
		return actionMapping.findForward("main");
	}

	/**
	 * 待授权用户清单
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletRepsonse
	 * @return
	 */
	public ActionForward outroleuser(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletRepsonse) {
		//从所有单位所有用户中选择用户
		// 获取所有已授权的用户列表
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "roleid", "=", Integer.valueOf(roleid));

		SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
		List roleList = surm.getSysUserRoles(condition, "", 0);

		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		condition.clear();
		SysUserRole role = null;
		SysUserInfo info = null;
		for (int i = 0; i < roleList.size(); i++) {
			role = (SysUserRole) roleList.get(i);
			info = role.getSysUserInfo();
			int userid = info.getUserid().intValue();
			SearchCondition.addCondition(condition, "userid", "<>", userid);
		}

		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		}
		
		SearchCondition.addCondition(condition, "status", "=", "1");

		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "username asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
		httpServletRequest.setAttribute("roleid", roleid);
		
		return actionMapping.findForward("outroleuser");
	}

	/**
	 * 列出已授权用户
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletRepsonse
	 * @return
	 */
	public ActionForward inroleuser(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletRepsonse) {
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		
		SysUserRoleManager manager = (SysUserRoleManager) getBean("sysUserRoleManager");

		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "roleid", "=", Integer.valueOf(roleid));

		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "sysUserInfo.username", "like", "%" + username + "%");
		}
		SearchCondition.addCondition(condition, "sysUserInfo.status", "=", "1");

		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "sysUserInfo.username asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserRoles(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());

		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
		httpServletRequest.setAttribute("roleid", roleid);

		return actionMapping.findForward("inroleuser");
	}

	/**
	 * 给当前角色添加用户
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward addroleuser(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		SysRoleInfoManager srim = (SysRoleInfoManager) getBean("sysRoleInfoManager");
		SysRoleInfo roleInfo = srim.getSysRoleInfo(roleid);
		
		SysUserRoleManager manager = (SysUserRoleManager) getBean("sysUserRoleManager");
		SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");

		String[] checkids = httpServletRequest.getParameterValues("checkid");
		SysUserRole sysUserRole = null;
		SysUserInfo info = null;
		for (int i = 0; i < checkids.length; i++) {
			sysUserRole = new SysUserRole();
			info = suim.getSysUserInfo(checkids[i]);
			sysUserRole.setRoleid(Integer.valueOf(roleid));
			sysUserRole.setSysUserInfo(info);
			manager.addSysUserRole(sysUserRole);
			//addLog(httpServletRequest, "把角色【" + roleInfo.getRolename() + "】分配给了用户【" + info.getUsername() + "】");
		}
		//刷新已授权人员列表
		httpServletRequest.setAttribute("reload", "1");
		return outroleuser(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	
	/**
	 * 给当前角色添加用户
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward addroleuserall(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		// 获取所有已授权的用户列表
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		SysRoleInfoManager srim = (SysRoleInfoManager) getBean("sysRoleInfoManager");
		SysRoleInfo roleInfo = srim.getSysRoleInfo(roleid);
		
		SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
		
		List datalist = null;
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "roleid", "=", Integer.valueOf(roleid));

		List roleList = surm.getSysUserRoles(condition, "", 0);

		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		condition.clear();
		SysUserRole role = null;
		SysUserInfo info = null;
		for (int i = 0; i < roleList.size(); i++) {
			role = (SysUserRole) roleList.get(i);
			info = role.getSysUserInfo();
			int userid = info.getUserid().intValue();
			SearchCondition.addCondition(condition, "userid", "<>", userid);
		}

		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		}
		
		SearchCondition.addCondition(condition, "status", "=", "1");
		//获取所有未授权的用户列表
		datalist = manager.getSysUserInfos(condition, "", 0);
		
		SysUserRole sysUserRole = null;
		for (int i = 0; i < datalist.size(); i++) {
			sysUserRole = new SysUserRole();
			info = (SysUserInfo) datalist.get(i);
			sysUserRole.setRoleid(Integer.valueOf(roleid));
			sysUserRole.setSysUserInfo(info);
			surm.addSysUserRole(sysUserRole);
			//addLog(httpServletRequest, "把角色【" + roleInfo.getRolename() + "】分配给了用户【" + info.getUsername() + "】");
		}
		//刷新已授权人员列表
		httpServletRequest.setAttribute("reload", "1");
		return outroleuser(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 删除选中的人员列表
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward deleteroleuser(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		SysRoleInfoManager srim = (SysRoleInfoManager) getBean("sysRoleInfoManager");
		SysRoleInfo roleInfo = srim.getSysRoleInfo(roleid);
		
		SysUserRoleManager manager = (SysUserRoleManager) getBean("sysUserRoleManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		SysUserRole userRole = null;
		for (int i = 0; i < checkids.length; i++) {
			userRole = manager.getSysUserRole(checkids[i]);
			manager.delSysUserRole(userRole);
			//addLog(httpServletRequest, "删除了用户【" + userRole.getSysUserInfo().getUsername() + "】的角色【" + roleInfo.getRolename() + "】");
		}
		httpServletRequest.setAttribute("reload", "1");
		return inroleuser(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	
	/**
	 * 删除全部授权用户
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletRepsonse
	 * @return
	 */
	public ActionForward deleteroleuserall(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletRepsonse) {
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		SysRoleInfoManager srim = (SysRoleInfoManager) getBean("sysRoleInfoManager");
		SysRoleInfo roleInfo = srim.getSysRoleInfo(roleid);

		SysUserRoleManager manager = (SysUserRoleManager) getBean("sysUserRoleManager");

		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "roleid", "=", Integer.valueOf(roleid));
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "sysUserInfo.username", "like", "%" + username + "%");
		}
		List datalist = manager.getSysUserRoles(condition, "",0);
		SysUserRole sur = null;
		for (int i = 0; i < datalist.size(); i++) {
			sur = (SysUserRole)datalist.get(i);
			manager.delSysUserRole(sur);
		}
		//addLog(httpServletRequest, "删除了角色【" + roleInfo.getRolename() + "】对应的所有用户");
		
		httpServletRequest.setAttribute("reload", "1");
		return inroleuser(actionMapping, actionForm, httpServletRequest, httpServletRepsonse);
	}
}