package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysRoleInfo;
import com.wkmk.sys.bo.SysUnitDept;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserRole;
import com.wkmk.sys.service.SysRoleInfoManager;
import com.wkmk.sys.service.SysUnitDeptManager;
import com.wkmk.sys.service.SysUnitDeptMemberManager;
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
public class SysUserRoleAction extends BaseAction {

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
		// 获取所有已授权的用户列表
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		String deptid = Encode.nullToBlank(httpServletRequest.getParameter("deptid"));//从部门内选用户
		
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		if(!"".equals(deptid)){
			SysUnitDeptMemberManager manager = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
			
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "roleid", "=", Integer.valueOf(roleid));
			SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
			List roleList = surm.getSysUserRoles(condition, "", 0);

			StringBuffer strs = new StringBuffer();
			SysUserRole role = null;
			SysUserInfo info = null;
			for (int i = 0; i < roleList.size(); i++) {
				role = (SysUserRole) roleList.get(i);
				info = role.getSysUserInfo();
				int userid = info.getUserid().intValue();
				strs.append(" and ").append("a.sysUserInfo.userid<>").append(userid);
			}
			String search = "";
			if(strs.length() > 0) search = strs.substring(4);
			
			String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
			//如果组织机构包含子机构，则同时把子机构中的人员查出
			SysUnitDeptManager sudm = (SysUnitDeptManager) getBean("sysUnitDeptManager");
			SysUnitDept dept = sudm.getSysUnitDept(deptid);
			
			PageUtil pageUtil = new PageUtil(httpServletRequest);
			PageList page = manager.getPageSysUnitDeptMembers(search, username, unitid, dept.getDeptno(), "a.sysUserInfo.username", pageUtil.getStartCount(), pageUtil.getPageSize());
			
			httpServletRequest.setAttribute("pagelist", page);
			httpServletRequest.setAttribute("username", username);
			httpServletRequest.setAttribute("roleid", roleid);
		}else {
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
			
			SearchCondition.addCondition(condition, "unitid", "=", unitid);
			SearchCondition.addCondition(condition, "status", "=", "1");//用户已授权

			PageUtil pageUtil = new PageUtil(httpServletRequest);
			String sorderindex = "username asc";
			if(!"".equals(pageUtil.getOrderindex())){
				sorderindex = pageUtil.getOrderindex();
			}
			PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
			
			httpServletRequest.setAttribute("pagelist", page);
			httpServletRequest.setAttribute("username", username);
			httpServletRequest.setAttribute("roleid", roleid);
		}
		
		//查询所有的机构
		SysUnitDeptManager sudm = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		List list = sudm.getSysUnitDepts(condition, "deptno", 0);
		httpServletRequest.setAttribute("list", list);
		httpServletRequest.setAttribute("deptid", deptid);
		
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
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		SysUserRoleManager manager = (SysUserRoleManager) getBean("sysUserRoleManager");

		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "roleid", "=", Integer.valueOf(roleid));

		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "sysUserInfo.username", "like", "%" + username + "%");
		}
		SearchCondition.addCondition(condition, "sysUserInfo.status", "=", "1");
		SearchCondition.addCondition(condition, "sysUserInfo.unitid", "=", unitid);

		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "sysUserInfo.username asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserRoles(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());

		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("username", username);
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
			addLog(httpServletRequest, "把角色【" + roleInfo.getRolename() + "】分配给了用户【" + info.getUsername() + "】");
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
		
		String deptid = Encode.nullToBlank(httpServletRequest.getParameter("deptid"));//从用户组内选用户
		
		SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		
		List datalist = null;
		if(!"".equals(deptid)){
			SysUnitDeptMemberManager manager = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
			
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "roleid", "=", Integer.valueOf(roleid));
			List roleList = surm.getSysUserRoles(condition, "", 0);

			StringBuffer strs = new StringBuffer();
			SysUserRole role = null;
			SysUserInfo info = null;
			for (int i = 0; i < roleList.size(); i++) {
				role = (SysUserRole) roleList.get(i);
				info = role.getSysUserInfo();
				int userid = info.getUserid().intValue();
				strs.append(" and ").append("a.sysUserInfo.userid<>").append(userid);
			}
			String search = "";
			if(strs.length() > 0) search = strs.substring(4);
			
			String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
			//如果组织机构包含子机构，则同时把子机构中的人员查出
			SysUnitDeptManager sudm = (SysUnitDeptManager) getBean("sysUnitDeptManager");
			SysUnitDept dept = sudm.getSysUnitDept(deptid);
			
			datalist = manager.getSysUnitDeptMembers(search, username, unitid, dept.getDeptno(), "", 0);
		}else {
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
			
			SearchCondition.addCondition(condition, "unitid", "=", unitid);
			SearchCondition.addCondition(condition, "status", "=", "1");//用户已授权
			//获取所有未授权的用户列表
			datalist = manager.getSysUserInfos(condition, "", 0);
		}
		
		SysUserRole sysUserRole = null;
		SysUserInfo info = null;
		for (int i = 0; i < datalist.size(); i++) {
			sysUserRole = new SysUserRole();
			info = (SysUserInfo) datalist.get(i);
			sysUserRole.setRoleid(Integer.valueOf(roleid));
			sysUserRole.setSysUserInfo(info);
			surm.addSysUserRole(sysUserRole);
			addLog(httpServletRequest, "把角色【" + roleInfo.getRolename() + "】分配给了用户【" + info.getUsername() + "】");
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
			addLog(httpServletRequest, "删除了用户【" + userRole.getSysUserInfo().getUsername() + "】的角色【" + roleInfo.getRolename() + "】");
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
		addLog(httpServletRequest, "删除了角色【" + roleInfo.getRolename() + "】对应的所有用户");
		
		httpServletRequest.setAttribute("reload", "1");
		return inroleuser(actionMapping, actionForm, httpServletRequest, httpServletRepsonse);
	}
	
	/**
	 * 角色
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward rolemain(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		httpServletRequest.setAttribute("userid", userid);
		return actionMapping.findForward("rolemain");
	}
	
	/**
	 * 角色权限,获取所有角色的列表
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward frame(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		//获取该用户已有的角色
		SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
		List hasroleids = surm.getAllRoleids(Integer.valueOf(userid));
		
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		
		//获取所有的角色列表
		SysRoleInfoManager manager = (SysRoleInfoManager) getBean("sysRoleInfoManager");
		List roleList = manager.getSysRoleInfos(unitid);
		
		StringBuffer data = new StringBuffer();
		if(roleList != null && roleList.size() > 0){
			SysRoleInfo roleInfo = null;
			for(int j=0;j<roleList.size();j++){
			  roleInfo = (SysRoleInfo)roleList.get(j);
			  if(!hasroleids.contains(roleInfo.getRoleid()) && roleInfo.getUnitid() == 0){
			  	continue;
			  }
			  if(hasroleids.contains(roleInfo.getRoleid()) && roleInfo.getUnitid() == 0){
				  data.append("{ id:").append(roleInfo.getRoleid()).append(", parentId:").append(0);
				  data.append(", name:\"").append(roleInfo.getRolename()).append("\", checked: true, chkDisabled: true},\n");
			  }else if(hasroleids.contains(roleInfo.getRoleid())){
				  data.append("{ id:").append(roleInfo.getRoleid()).append(", parentId:").append(0);
				  data.append(", name:\"").append(roleInfo.getRolename()).append("\", checked: true},\n");
			  }else{
				  data.append("{ id:").append(roleInfo.getRoleid()).append(", parentId:").append(0);
				  data.append(", name:\"").append(roleInfo.getRolename()).append("\"},\n");
			  }
			}
		}
		
		String data00 = "";
		if(data.lastIndexOf(",\n") != -1) data00 = data.substring(0, data.length()-2);
		httpServletRequest.setAttribute("data", data00);
		httpServletRequest.setAttribute("userid", userid);
		return actionMapping.findForward("frame");
	}

	
	/**
	 * 更新角色权限
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward updateUserRole(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		SysUserRoleManager manager = (SysUserRoleManager) getBean("sysUserRoleManager");
		
		//--------------------------------------------------------
		// 先删除当前用户在当前单位所有已有的角色
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		List hasList = manager.getSysUserRoles(Integer.valueOf(userid), unitid);
		SysUserRole userRole = null;
		for (int k = 0; k < hasList.size(); k++) {
			userRole = (SysUserRole) hasList.get(k);
			manager.delSysUserRole(userRole);
		}
		//--------------------------------------------------------
		
		SysRoleInfoManager srim = (SysRoleInfoManager) getBean("sysRoleInfoManager");
		SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
		// 加入选中的角色
		String checkid = Encode.nullToBlank(httpServletRequest.getParameter("checkid"));
		String[] checkids = checkid.split(",");
		if(!"".equals(checkid) && checkids != null && checkids.length > 0){
			int len = checkids.length;
			SysRoleInfo roleInfo = null;
			for(int i=0; i<len; i++){
				roleInfo = srim.getSysRoleInfo(checkids[i]);
				if(roleInfo.getUnitid() == 0) continue;//删除角色时，系统角色不删
				userRole = new SysUserRole();
				userRole.setRoleid(Integer.valueOf(checkids[i]));
				userRole.setSysUserInfo(sysUserInfo);
				manager.addSysUserRole(userRole);
				addLog(httpServletRequest, "给用户【" + sysUserInfo.getUsername() + "】从新分配了" + len + "个角色【" + (i+1) + "." + roleInfo.getRolename() + "】");
			}
		}else {
			addLog(httpServletRequest, "删除了用户【" + sysUserInfo.getUsername() + "】所有的角色");
		}
		return actionMapping.findForward("close");
	}
}