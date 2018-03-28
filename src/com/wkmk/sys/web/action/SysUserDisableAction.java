package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserDisable;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserDisableManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.form.SysUserDisableActionForm;
import com.wkmk.zx.bo.ZxHelpQuestionComplaint;
import com.wkmk.zx.service.ZxHelpQuestionComplaintManager;
import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 * <p>
 * Description: 用户禁用记录
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class SysUserDisableAction extends BaseAction {

	/**
	 * 列表显示
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
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String complaintid = Encode.nullToBlank(httpServletRequest.getParameter("complaintid"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		SysUserDisableManager manager = (SysUserDisableManager) getBean("sysUserDisableManager");
		ZxHelpQuestionComplaintManager zhqcmanager = (ZxHelpQuestionComplaintManager) getBean("zxHelpQuestionComplaintManager");
		ZxHelpQuestionComplaint complaint = zhqcmanager.getZxHelpQuestionComplaint(complaintid);
		Integer userid = null;
		if ("1".equals(type)) {
			userid = complaint.getTeacherid();// 被投诉的教师的id
		} else if ("2".equals(type)) {
			userid = complaint.getUserid();// 投诉学生的id
		}
		if (userid == null || userid <= 0) {
			httpServletRequest.setAttribute("promptinfo", "操作失败，未获取到该禁用教师的信息，请检查投诉内容中是否包含该投诉教师");
			return actionMapping.findForward("fail");
		} else {
			SysUserInfoManager suimanager = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo userInfo = suimanager.getSysUserInfo(userid);
			String username = userInfo.getUsername();
			SysUserDisable model = new SysUserDisable();
			PageUtil pageUtil = new PageUtil(httpServletRequest);
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "sysUserInfo.userid", "=", userid);
			String sorderindex = "starttime";
			if (!"".equals(pageUtil.getOrderindex())) {
				sorderindex = pageUtil.getOrderindex();
			}
			PageList page = manager.getPageSysUserDisables(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
			httpServletRequest.setAttribute("pagelist", page);
			httpServletRequest.setAttribute("username", username);
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("type", type);
			httpServletRequest.setAttribute("userid", userid);
			httpServletRequest.setAttribute("complaintid", complaintid);
			return actionMapping.findForward("list");
		}
	}

	/**
	 * 增加前
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
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		String complaintid = Encode.nullToBlank(httpServletRequest.getParameter("complaintid"));
		// ZxHelpQuestionComplaintManager zqcmanager = (ZxHelpQuestionComplaintManager)getBean("zxHelpQuestionComplaintManager");
		SysUserInfoManager suimanager = (SysUserInfoManager) getBean("sysUserInfoManager");
		// ZxHelpQuestionComplaint comment = zqcmanager.getZxHelpQuestionComplaint(Integer.valueOf(complaintid));
		// Integer userid = null;
		// if(type.equals("1")){
		// userid = comment.getTeacherid();//禁用教师的教师id
		// }
		// if(type.equals("2")){
		// userid = comment.getUserid();//禁用学生的学生id
		// }
		if (userid != null && !"".equals(userid)) {
			SysUserInfo userInfo = suimanager.getSysUserInfo(userid);
			String username = userInfo.getUsername();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			// System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
			SysUserDisable model = new SysUserDisable();
			model.setStarttime(df.format(new Date()));
			httpServletRequest.setAttribute("username", username);
			httpServletRequest.setAttribute("userid", userid);
			httpServletRequest.setAttribute("complaintid", complaintid);
			httpServletRequest.setAttribute("type", type);
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("act", "addSave");
			saveToken(httpServletRequest);
			return actionMapping.findForward("edit");
		} else {
			httpServletRequest.setAttribute("promptinfo", "操作失败，未获取到该禁用教师的信息，请检查投诉内容中是否包含该投诉教师");
			return actionMapping.findForward("fail");
		}
	}

	/**
	 * 增加时保存
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
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		String complaintid = Encode.nullToBlank(httpServletRequest.getParameter("complaintid"));
		SysUserDisableActionForm form = (SysUserDisableActionForm) actionForm;
		SysUserDisableManager manager = (SysUserDisableManager) getBean("sysUserDisableManager");
		SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfo userInfo = suim.getSysUserInfo(Integer.valueOf(userid));
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserDisable model = form.getSysUserDisable();
				model.setSysUserInfo(userInfo);
				model.setOutlinkid(Integer.valueOf(complaintid));
				model.setOutlinktype("1");
				manager.addSysUserDisable(model);
				userInfo.setStatus("2");
				suim.updateSysUserInfo(userInfo);
				addLog(httpServletRequest, "增加了一个用户禁用记录");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
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
	public ActionForward view(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String disableid = Encode.nullToBlank(httpServletRequest.getParameter("disableid"));
		SysUserDisableManager manager = (SysUserDisableManager) getBean("sysUserDisableManager");
		SysUserDisable model = manager.getSysUserDisable(Integer.valueOf(disableid));
		SysUserInfo userInfo = model.getSysUserInfo();
		String username = userInfo.getUsername();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("username", username);
		return actionMapping.findForward("view");
	}

	/**
	 * 修改前
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
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserDisableManager manager = (SysUserDisableManager) getBean("sysUserDisableManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysUserDisable model = manager.getSysUserDisable(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		} catch (Exception e) {
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 * 修改时保存
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
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserDisableActionForm form = (SysUserDisableActionForm) actionForm;
		SysUserDisableManager manager = (SysUserDisableManager) getBean("sysUserDisableManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserDisable model = form.getSysUserDisable();
				manager.updateSysUserDisable(model);
				addLog(httpServletRequest, "修改了一个用户禁用记录");
			} catch (Exception e) {
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 批量删除
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
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserDisableManager manager = (SysUserDisableManager) getBean("sysUserDisableManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delSysUserDisable(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}