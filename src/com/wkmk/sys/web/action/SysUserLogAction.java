package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserLog;
import com.wkmk.sys.service.SysUserLogManager;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统用户日志信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserLogAction extends BaseAction {

	/**
	 * 列表显示
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		String descript = Encode.nullToBlank(httpServletRequest.getParameter("descript"));
		String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示系统管理查看所有日志
		
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		SysUserLogManager manager = (SysUserLogManager) getBean("sysUserLogManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"1".equals(flag)){
			SearchCondition.addCondition(condition, "unitid", "=", unitid);
		}
		SearchCondition.addCondition(condition, "logtype", "=", "0");//只能查看公共日志，不能查看个人私有日志
		if (!"".equals(username)) {
			SearchCondition.addCondition(condition, "sysUserInfo.username", "like", "%" + username + "%");
		}
		if (!"".equals(createdate)) {
			SearchCondition.addCondition(condition, "createdate", "like", createdate + "%");
		}
		if (!"".equals(descript)) {
			SearchCondition.addCondition(condition, "descript", "like", "%" + descript + "%");
		}

		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserLogs(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("createdate", createdate);
		httpServletRequest.setAttribute("descript", descript);
		httpServletRequest.setAttribute("flag", flag);
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
		return actionMapping.findForward("list");
	}

	/**
	 * 批量删除
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward delBatchRecord(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		SysUserLogManager manager = (SysUserLogManager) getBean("sysUserLogManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		for (int i = 0; i < checkids.length; i++) {
			manager.delSysUserLog(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 全部删除
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward delBatchAllRecord(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示系统管理查看所有日志
		SysUserLogManager manager = (SysUserLogManager) getBean("sysUserLogManager");
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"1".equals(flag)){
			SearchCondition.addCondition(condition, "unitid", "=", unitid);
		}
		SearchCondition.addCondition(condition, "logtype", "=", "0");
		List lst = manager.getSysUserLogs(condition, "", 0);
		if (lst != null && lst.size() > 0) {
			SysUserLog userLog = null;
			for (int i = 0; i < lst.size(); i++) {
				userLog = (SysUserLog) lst.get(i);
				manager.delSysUserLog(userLog);
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}
