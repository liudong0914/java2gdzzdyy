package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUnitDept;
import com.wkmk.sys.bo.SysUnitDeptMember;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUnitDeptManager;
import com.wkmk.sys.service.SysUnitDeptMemberManager;
import com.wkmk.sys.service.SysUserInfoManager;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统单位机构成员</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitDeptMemberAction extends BaseAction {

	public ActionForward main(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String deptid = Encode.nullToBlank(httpServletRequest.getParameter("deptid"));
		SysUnitDeptManager manager = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		SysUnitDept dept = manager.getSysUnitDept(deptid);
		String deptname = dept.getDeptname();
		httpServletRequest.setAttribute("deptname", deptname);

		httpServletRequest.setAttribute("deptid", deptid);
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
	public ActionForward outmember(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletRepsonse) {
		String deptid = Encode.nullToBlank(httpServletRequest.getParameter("deptid"));
		
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "deptid", "=", Integer.valueOf(deptid));

		SysUnitDeptMemberManager sudmm = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
		List deptmemberList = sudmm.getSysUnitDeptMembers(condition, "", 0);

		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		condition.clear();
		SysUnitDeptMember deptMember = null;
		SysUserInfo info = null;
		for (int i = 0; i < deptmemberList.size(); i++) {
			deptMember = (SysUnitDeptMember) deptmemberList.get(i);
			info = deptMember.getSysUserInfo();
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
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
		httpServletRequest.setAttribute("deptid", deptid);
		
		return actionMapping.findForward("outmember");
	}

	/**
	 * 列出已授权用户
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletRepsonse
	 * @return
	 */
	public ActionForward inmember(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletRepsonse) {
		String deptid = Encode.nullToBlank(httpServletRequest.getParameter("deptid"));
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		SysUnitDeptMemberManager manager = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");

		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "deptid", "=", Integer.valueOf(deptid));

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
		PageList page = manager.getPageSysUnitDeptMembers(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());

		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
		httpServletRequest.setAttribute("deptid", deptid);

		return actionMapping.findForward("inmember");
	}

	/**
	 * 给当前角色添加用户
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward addmember(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String deptid = Encode.nullToBlank(httpServletRequest.getParameter("deptid"));
		SysUnitDeptManager sudm = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		SysUnitDept dept = sudm.getSysUnitDept(deptid);
		
		SysUnitDeptMemberManager manager = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
		SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");

		String[] checkids = httpServletRequest.getParameterValues("checkid");
		SysUnitDeptMember deptMember = null;
		SysUserInfo info = null;
		for (int i = 0; i < checkids.length; i++) {
			deptMember = new SysUnitDeptMember();
			info = suim.getSysUserInfo(checkids[i]);
			deptMember.setDeptid(Integer.valueOf(deptid));
			deptMember.setSysUserInfo(info);
			manager.addSysUnitDeptMember(deptMember);
			addLog(httpServletRequest, "把用户【" + info.getUsername() + "】分配到了机构【" + dept.getDeptname() + "】中");
		}
		//刷新已授权人员列表
		httpServletRequest.setAttribute("reload", "1");
		return outmember(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	
	/**
	 * 给当前角色添加用户
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward addmemberall(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String deptid = Encode.nullToBlank(httpServletRequest.getParameter("deptid"));
		SysUnitDeptManager sudm = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		SysUnitDept dept = sudm.getSysUnitDept(deptid);
		
		SysUnitDeptMemberManager sudmm = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		
		List datalist = null;
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "deptid", "=", Integer.valueOf(deptid));

		List deptmemberList = sudmm.getSysUnitDeptMembers(condition, "", 0);

		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		condition.clear();
		SysUnitDeptMember deptMember = null;
		SysUserInfo info = null;
		for (int i = 0; i < deptmemberList.size(); i++) {
			deptMember = (SysUnitDeptMember) deptmemberList.get(i);
			info = deptMember.getSysUserInfo();
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
		
		SysUnitDeptMember sysUnitDeptMember = null;
		for (int i = 0; i < datalist.size(); i++) {
			sysUnitDeptMember = new SysUnitDeptMember();
			info = (SysUserInfo) datalist.get(i);
			sysUnitDeptMember.setDeptid(Integer.valueOf(deptid));
			sysUnitDeptMember.setSysUserInfo(info);
			sudmm.addSysUnitDeptMember(sysUnitDeptMember);
			addLog(httpServletRequest, "把用户【" + info.getUsername() + "】分配到了机构【" + dept.getDeptname() + "】");
		}
		//刷新已授权人员列表
		httpServletRequest.setAttribute("reload", "1");
		return outmember(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 删除选中的人员列表
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward deletemember(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String deptid = Encode.nullToBlank(httpServletRequest.getParameter("deptid"));
		SysUnitDeptManager sudm = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		SysUnitDept dept = sudm.getSysUnitDept(deptid);
		
		SysUnitDeptMemberManager manager = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		SysUnitDeptMember deptMember = null;
		for (int i = 0; i < checkids.length; i++) {
			deptMember = manager.getSysUnitDeptMember(checkids[i]);
			manager.delSysUnitDeptMember(deptMember);
			addLog(httpServletRequest, "删除了机构【" + dept.getDeptname() + "】中的成员【" + deptMember.getSysUserInfo().getUsername() + "】");
		}
		httpServletRequest.setAttribute("reload", "1");
		return inmember(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	
	/**
	 * 删除全部授权用户
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletRepsonse
	 * @return
	 */
	public ActionForward deletememberall(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletRepsonse) {
		String deptid = Encode.nullToBlank(httpServletRequest.getParameter("deptid"));
		SysUnitDeptManager sudm = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		SysUnitDept dept = sudm.getSysUnitDept(deptid);

		SysUnitDeptMemberManager manager = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "deptid", "=", Integer.valueOf(deptid));
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "sysUserInfo.username", "like", "%" + username + "%");
		}
		List datalist = manager.getSysUnitDeptMembers(condition, "", 0);
		SysUnitDeptMember deptMember = null;
		for (int i = 0; i < datalist.size(); i++) {
			deptMember = (SysUnitDeptMember)datalist.get(i);
			manager.delSysUnitDeptMember(deptMember);
		}
		addLog(httpServletRequest, "删除了机构【" + dept.getDeptname() + "】中的所有成员");
		
		httpServletRequest.setAttribute("reload", "1");
		return inmember(actionMapping, actionForm, httpServletRequest, httpServletRepsonse);
	}
	
	/**
	* 列表显示
	* @param actionMapping ActionMapping
	* @param actionForm ActionForm
	* @param httpServletRequest HttpServletRequest
	* @param httpServletResponse HttpServletResponse
	* @return ActionForward
	*/
	public ActionForward deptmain(ActionMapping actionMapping, ActionForm actionForm,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		httpServletRequest.setAttribute("userid", userid);
		return actionMapping.findForward("deptmain");
	}
	
	/**
	* 列表显示
	* @param actionMapping ActionMapping
	* @param actionForm ActionForm
	* @param httpServletRequest HttpServletRequest
	* @param httpServletResponse HttpServletResponse
	* @return ActionForward
	*/
	public ActionForward frame(ActionMapping actionMapping, ActionForm actionForm,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysUnitDeptManager manager = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		SysUnitDeptMemberManager sudmm = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));

		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		List useralldeptids = sudmm.getAllDeptidsByUserid(Integer.valueOf(userid));
		
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		List list = manager.getSysUnitDepts(condition, "deptno asc", 0);
		
		//根据编号获取对应id
		SysUnitDept dept = null;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("0000", 0);
		for(int i=0, size=list.size(); i<size; i++){
			dept = (SysUnitDept) list.get(i);
			map.put(dept.getDeptno(), dept.getDeptid());
		}
		
		StringBuffer data = new StringBuffer();
		for(int i=0;i<list.size();i++){
			dept = (SysUnitDept) list.get(i);
			if(useralldeptids.contains(dept.getDeptid())){
				data.append("{ id:").append(dept.getDeptid()).append(", parentId:").append(map.get(dept.getParentno()));
				data.append(", name:\"").append(dept.getDeptname()).append("\", checked: true},\n");
			}else {
				data.append("{ id:").append(dept.getDeptid()).append(", parentId:").append(map.get(dept.getParentno()));
				data.append(", name:\"").append(dept.getDeptname()).append("\"},\n");
			}
		
		}
		
		String data00 = "";
		if(data.lastIndexOf(",\n") != -1) data00 = data.substring(0, data.length()-2);
		httpServletRequest.setAttribute("data", data00);
		httpServletRequest.setAttribute("userid", userid);
		
		return actionMapping.findForward("frame");
	}
	
	/**
	* 列表显示
	* @param actionMapping ActionMapping
	* @param actionForm ActionForm
	* @param httpServletRequest HttpServletRequest
	* @param httpServletResponse HttpServletResponse
	* @return ActionForward
	*/
	public ActionForward deal(ActionMapping actionMapping, ActionForm actionForm,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		SysUnitDeptMemberManager manager = (SysUnitDeptMemberManager) getBean("sysUnitDeptMemberManager");
		SysUnitDeptManager sudm = (SysUnitDeptManager) getBean("sysUnitDeptManager");
		SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
		
		//--------------------------------------------------------
		// 先删除当前用户在当前单位所在组
		List<SearchModel> condition = new ArrayList<SearchModel>();
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		SearchCondition.addCondition(condition, "sysUserInfo.userid", "=", userid);
		List userdeptList = manager.getSysUnitDeptMembers(condition, "", 0);
		SysUnitDeptMember sysUnitDeptMember = null;
		for(int i=0; i<userdeptList.size(); i++){
			sysUnitDeptMember = (SysUnitDeptMember) userdeptList.get(i);
			manager.delSysUnitDeptMember(sysUnitDeptMember);
		}
		
		SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
		// 加入选中的角色
		String checkid = Encode.nullToBlank(httpServletRequest.getParameter("checkid"));
		String[] checkids = checkid.split(",");
		if(!"".equals(checkid) && checkids != null && checkids.length > 0){
			int len = checkids.length;
			SysUnitDept dept = null;
			for(int i=0; i<len; i++){
				dept = sudm.getSysUnitDept(checkids[i]);
				sysUnitDeptMember = new SysUnitDeptMember();
				sysUnitDeptMember.setDeptid(Integer.valueOf(checkids[i]));
				sysUnitDeptMember.setSysUserInfo(sysUserInfo);
				manager.addSysUnitDeptMember(sysUnitDeptMember);
				addLog(httpServletRequest, "给用户【" + sysUserInfo.getUsername() + "】从新指派了" + len + "个机构【" + (i+1) + "." + dept.getDeptname() + "】");
			}
		}else {
			addLog(httpServletRequest, "删除了用户【" + sysUserInfo.getUsername() + "】所有关联的机构");
		}
		return actionMapping.findForward("close");
	}
}