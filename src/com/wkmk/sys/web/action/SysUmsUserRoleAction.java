package com.wkmk.sys.web.action;

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
import com.util.string.Encode;

/**
 *<p>Description: 系统用户角色</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUmsUserRoleAction extends BaseAction {

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
		SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfo sui = suim.getSysUserInfo(userid);
		//获取该用户已有的角色
		SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
		List hasroleids = surm.getAllRoleids(Integer.valueOf(userid));
		
		Integer unitid = sui.getUnitid();
		
		//获取所有的角色列表
		SysRoleInfoManager manager = (SysRoleInfoManager) getBean("sysRoleInfoManager");
		List roleList = manager.getSysRoleInfos(unitid);
		
		StringBuffer data = new StringBuffer();
		if(roleList != null && roleList.size() > 0){
			SysRoleInfo roleInfo = null;
			for(int j=0;j<roleList.size();j++){
			  roleInfo = (SysRoleInfo)roleList.get(j);
			  if(hasroleids.contains(roleInfo.getRoleid())){
				  data.append("{ id:").append(roleInfo.getRoleid()).append(", parentId:").append(0);
				  if(roleInfo.getUnitid() == 0){
					  data.append(", name:\"").append(roleInfo.getRolename()).append("\", font:{'color':'blue'}, checked: true},\n");
				  }else {
					  data.append(", name:\"").append(roleInfo.getRolename()).append("\", checked: true},\n");
				  }
			  }else{
				  data.append("{ id:").append(roleInfo.getRoleid()).append(", parentId:").append(0);
				  if(roleInfo.getUnitid() == 0){
					  data.append(", name:\"").append(roleInfo.getRolename()).append("\", font:{'color':'blue'}},\n");
				  }else {
					  data.append(", name:\"").append(roleInfo.getRolename()).append("\"},\n");
				  }
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
		// 先删除当前用户所有已有的角色
		List hasList = manager.getSysUserRoles(Integer.valueOf(userid), 0);
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