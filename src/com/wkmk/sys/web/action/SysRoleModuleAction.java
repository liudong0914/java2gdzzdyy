package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysModuleInfo;
import com.wkmk.sys.bo.SysProductInfo;
import com.wkmk.sys.bo.SysRoleInfo;
import com.wkmk.sys.bo.SysRoleModule;
import com.wkmk.sys.service.SysModuleInfoManager;
import com.wkmk.sys.service.SysProductInfoManager;
import com.wkmk.sys.service.SysRoleInfoManager;
import com.wkmk.sys.service.SysRoleModuleManager;

import com.util.action.BaseAction;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统角色模块</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysRoleModuleAction extends BaseAction {

	public ActionForward main(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		httpServletRequest.setAttribute("roleid", roleid);
		return actionMapping.findForward("main");
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
		// 获取该角色已有的权限
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		SysRoleModuleManager manager = (SysRoleModuleManager) getBean("sysRoleModuleManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "roleid", "=", Integer.valueOf(roleid));
		SearchCondition.addCondition(condition, "sysModuleInfo.status", "=", "1");//模块正常
		List hasList = manager.getSysRoleModules(condition, "sysModuleInfo.moduleno", 0);
		
		//获取所有模块
		HttpSession session = httpServletRequest.getSession();
		SysModuleInfoManager smim = (SysModuleInfoManager) getBean("sysModuleInfoManager");
		//condition.clear();
		//SearchCondition.addCondition(condition, "status", "=", "1");//开通模块
		//List allList = smim.getSysModuleInfos(condition, "moduleno", 0);
		List allList = smim.getAllModuleList(null);
		
		//获取所有没有子模块的模块列表id
		List nosubList = smim.getNoSubModuleids(null);
		
		//查询出当前用户的所拥有的权限的一级菜单和子菜单
		//-------------------------------------------------------
		Integer userid = (Integer) session.getAttribute("s_userid");
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		// 根据用户信息获取当前用户在当前单位当前产品的角色集合
		List permissionlist = smim.getPermissionModuleList(userid, unitid, null);
		
		// 获取所有一级菜单
		condition.clear();
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "parentno", "=", "0000");
		List modulelist = smim.getSysModuleInfos(condition, "moduleno", 0);
		
		//获取有权限的一级菜单
		List dataList=getPowerModuleList(modulelist,permissionlist);
		
		permissionlist.addAll(dataList);
		
		httpServletRequest.setAttribute("permissionlist", permissionlist);
		//-------------------------------------------------------
		
		SysRoleModule roleModule = null;
		List hasmoduleids = new ArrayList();
		for(int i=0, size=hasList.size(); i<size; i++){
			roleModule = (SysRoleModule) hasList.get(i);
			hasmoduleids.add(roleModule.getSysModuleInfo().getModuleid());
		}
		
		String pids = "1,2,3,4,5,6,7,8,9,10,11,12";
		
		SysProductInfoManager spim = (SysProductInfoManager) getBean("sysProductInfoManager");
		condition.clear();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List productList = spim.getSysProductInfos(condition, "orderindex asc", 0);
		
		SysProductInfo spi = null;
		SysModuleInfo module = null;
		StringBuffer data = new StringBuffer();
		for(int m=0, n=productList.size(); m<n; m++){
			spi = (SysProductInfo) productList.get(m);
			if(pids != null && pids.indexOf(spi.getProductid().toString()) != -1){
				data.append("{ id:").append(spi.getProductid()).append(", parentId:0");
				data.append(", name:\"【").append(spi.getProductname()).append("】\", open: true, nocheck: true},\n");
				
				for(int i=0, size=allList.size(); i<size; i++){
					module = (SysModuleInfo) allList.get(i);
					if(module.getProductid().intValue() == spi.getProductid().intValue()){
						if(permissionlist.contains(module)){
							if(nosubList.contains(module.getModuleid())){
								if(hasmoduleids.contains(module.getModuleid())){
									data.append("{ id:").append(module.getModuleid()).append(", parentId:").append(module.getProductid()).append(module.getParentno());
									data.append(", name:\"").append(module.getModulename()).append("\", checked: true},\n");
								}else {
									data.append("{ id:").append(module.getModuleid()).append(", parentId:").append(module.getProductid()).append(module.getParentno());
									data.append(", name:\"").append(module.getModulename()).append("\"},\n");
								}
							}else {
								data.append("{ id:").append(module.getProductid()).append(module.getModuleno()).append(", parentId:").append(module.getProductid());
								data.append(", name:\"").append(module.getModulename()).append("\", open: true, nocheck: true},\n");
							}
						}
					}
				}
			}
		}
		
		String data00 = "";
		if(data.lastIndexOf(",\n") != -1) data00 = data.substring(0, data.length()-2);
		httpServletRequest.setAttribute("data", data00);
		httpServletRequest.setAttribute("roleid", roleid);
		
		return actionMapping.findForward("frame");
	}
	
	/**
	 * 获取当前父模块下的子模块列表
	 * 
	 * @param parentno
	 * @param permissionlist
	 * @return
	 */
	private List getSubModuleList(String parentno, List permissionlist) {
		List lst = new ArrayList();
		for (int i = 0; i < permissionlist.size(); i++) {
			SysModuleInfo moduleInfo = (SysModuleInfo) permissionlist.get(i);
			if ("1".equals(moduleInfo.getStatus())) {
				if (parentno.equals(moduleInfo.getParentno())) { // 父编号等于当前编号
					lst.add(moduleInfo);
				}
			}
		}
		return lst;
	}

	/**
	 * 获取有子模块的一级模块列表
	 * @param modulelist
	 * @param permissionlist
	 * @return
	 */
	private List getPowerModuleList(List modulelist, List permissionlist) {
		List lst = new ArrayList();
		for (int i = 0; i < modulelist.size(); i++) {
			SysModuleInfo moduleInfo = (SysModuleInfo) modulelist.get(i);
			List subList=getSubModuleList(moduleInfo.getModuleno(),permissionlist);
			if (subList.size()>0) {
				lst.add(moduleInfo);
			}
		}
		return lst;
	}

	
	/**
	 * 更新角色权限
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
	public ActionForward updateRoleModule(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String roleid = Encode.nullToBlank(httpServletRequest.getParameter("roleid"));
		SysRoleInfoManager srim = (SysRoleInfoManager) getBean("sysRoleInfoManager");
		SysRoleInfo roleInfo = srim.getSysRoleInfo(roleid);
		
		SysRoleModuleManager manager = (SysRoleModuleManager) getBean("sysRoleModuleManager");
		SysModuleInfoManager smim = (SysModuleInfoManager) getBean("sysModuleInfoManager");
		
		// 先删除所有已有的模块
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "roleid", "=", roleid);
		List hasList = manager.getSysRoleModules(condition, "sysModuleInfo.moduleno", 0);
		SysRoleModule roleModule = null;
		for (int k = 0; k < hasList.size(); k++) {
			roleModule = (SysRoleModule) hasList.get(k);
			manager.delSysRoleModule(roleModule);
		}
		// 加入选中的模块
		String checkid = Encode.nullToBlank(httpServletRequest.getParameter("checkid"));
		String[] checkids = checkid.split(",");
		if(!"".equals(checkid) && checkids != null && checkids.length > 0){
			int len = checkids.length;
			SysModuleInfo moduleInfo = null;
			for (int i = 0; i < len; i++) {
				moduleInfo = smim.getSysModuleInfo(checkids[i]);
				
				roleModule = new SysRoleModule();
				roleModule.setRoleid(Integer.valueOf(roleid));
				roleModule.setSysModuleInfo(moduleInfo);
				roleModule.setAddoperation("1");
				roleModule.setDeleteoperation("1");
				roleModule.setModifyoperation("1");
				roleModule.setViewoperation("1");
				manager.addSysRoleModule(roleModule);
				addLog(httpServletRequest, "给角色【" + roleInfo.getRolename() + "】从新分配了" + len + "个模块【" + (i+1) + "." + moduleInfo.getModulename() + "】");
			}
		}else {
			addLog(httpServletRequest, "删除了角色【" + roleInfo.getRolename() + "】所有的模块权限");
		}
		return actionMapping.findForward("close");
	}
}