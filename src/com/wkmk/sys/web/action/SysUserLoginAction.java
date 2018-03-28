package com.wkmk.sys.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysUnitInfoManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserRoleManager;
import com.wkmk.util.common.Constants;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.MD5;
import com.util.string.Encode;

/**
 *<p>Description: 后台管理员登陆</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserLoginAction extends BaseAction {

	/**
	 * 用户登录-初始模板
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward login(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String loginname = Encode.nullToBlank(request.getParameter("loginname"));
		String password = Encode.nullToBlank(request.getParameter("password"));
		HttpSession session = request.getSession();
		if (loginname == null || "".equals(loginname)) {
			request.setAttribute("promptinfo", "用户名不能为空!");
			return mapping.findForward("relogin");
		} else if (password == null || "".equals(password)) {
			request.setAttribute("promptinfo", "密码不能为空!");
			return mapping.findForward("relogin");
		} 
		
		try {
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo userInfo = manager.getUserInfoByLoginName(loginname);
			if (userInfo != null) {
				
				password = MD5.getEncryptPwd(password);
				if (!password.equals(userInfo.getPassword())) { // 密码不对
					request.setAttribute("promptinfo", "您输入的密码不正确，请重输!");
					return mapping.findForward("relogin");
				}
				if ("0".equals(userInfo.getStatus()) && !"zxd".equals(userInfo.getLoginname())) { // 未审核
					request.setAttribute("promptinfo", "您的账号还没审核，请与管理员联系!");
					return mapping.findForward("relogin");
				}else if ("2".equals(userInfo.getStatus()) && !"zxd".equals(userInfo.getLoginname())) { // 被禁用
					request.setAttribute("promptinfo", "您的账号已被禁用，请与管理员联系!");
					return mapping.findForward("relogin");
				}else if ("3".equals(userInfo.getStatus()) && !"zxd".equals(userInfo.getLoginname())) { // 被删除
					request.setAttribute("promptinfo", "您的账号已被删除，请与管理员联系!");
					return mapping.findForward("relogin");
				} else {
					Integer s_unitid = userInfo.getUnitid();
					
					//更新用户扩展信息
					Integer userid = userInfo.getUserid();
	        		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
	        		SysUserInfoDetail sysUserInfoDetail = suidm.getSysUserInfoDetail(userid);
	        		
	        		// 更新登录相关信息
	        		sysUserInfoDetail.setLastlogin(DateTime.getDate());
	        		sysUserInfoDetail.setLogintimes(new Integer(sysUserInfoDetail.getLogintimes().intValue() + 1));
	        		suidm.updateSysUserInfoDetail(sysUserInfoDetail);
					
	        		session.setAttribute("s_sysuserinfo", userInfo);
	        		session.setAttribute("s_sysuserinfodetail", sysUserInfoDetail);
	        		session.setAttribute("s_userid", userid);
	        		session.setAttribute("pagesize", "10");
	        		
	        		// 获取单位信息
	    			SysUnitInfoManager unitInfoManager = (SysUnitInfoManager) getBean("sysUnitInfoManager");
	    			SysUnitInfo sui = unitInfoManager.getSysUnitInfo(s_unitid);
	    			session.setAttribute("s_sysunitinfo", sui);
	    			session.setAttribute("s_unitid", s_unitid);
	    			
	    			//判断当前用户是否为当前系统的产品管理员，或是单位管理员【学校管理员】
	    			//SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
	    	        //List allroleids = surm.getAllRoleids(userid);
	    	        String isAdmin = "0";
	    	        //if(allroleids.contains(Constants.SYS_ROLEID_0)){
	    	        //	isAdmin = "1";//产品管理员
	    	        //}
	    	        session.setAttribute("isAdmin", isAdmin);
	    	        
					// 加入日志
					addLog(request, "用户" + userInfo.getLoginname() +"(" + userInfo.getUsername()+")登录成功!");
					return mapping.findForward("manager");
				}
			} else {
				request.setAttribute("promptinfo", "输入的用户名不存在!");
			}
		} catch (Exception e) {
			request.setAttribute("promptinfo", "登录出现错误,请检查后重试!");
			return mapping.findForward("relogin");
		}
		return mapping.findForward("relogin");
	}
	
	/**
	 * 用户登录-模板一
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward login1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String loginname = Encode.nullToBlank(request.getParameter("loginname"));
		String password = Encode.nullToBlank(request.getParameter("password"));
		String code = Encode.nullToBlank(request.getParameter("code"));//验证码
		HttpSession session = request.getSession();
		if (loginname == null || "".equals(loginname)) {
			request.setAttribute("promptinfo", "用户名不能为空!");
			return mapping.findForward("relogin1");
		} else if (password == null || "".equals(password)) {
			request.setAttribute("promptinfo", "密码不能为空!");
			return mapping.findForward("relogin1");
		} else if (code == null || "".equals(code)) {
			request.setAttribute("promptinfo", "验证码不能为空!");
			return mapping.findForward("relogin1");
		} 
		
		String randomcode = (String) session.getAttribute("randomcode");
		if(randomcode != null && !code.toLowerCase().equals(randomcode.toLowerCase())){
			request.setAttribute("promptinfo", "验证码输入错误!");
			return mapping.findForward("relogin1");
		}
		
		try {
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo userInfo = manager.getUserInfoByLoginName(loginname);
			if (userInfo != null) {
				
				password = MD5.getEncryptPwd(password);
				if (!password.equals(userInfo.getPassword())) { // 密码不对
					request.setAttribute("promptinfo", "您输入的密码不正确，请重输!");
					return mapping.findForward("relogin1");
				}
				if ("0".equals(userInfo.getStatus()) && !"zxd".equals(userInfo.getLoginname())) { // 未审核
					request.setAttribute("promptinfo", "您的账号还没审核，请与管理员联系!");
					return mapping.findForward("relogin1");
				}else if ("2".equals(userInfo.getStatus()) && !"zxd".equals(userInfo.getLoginname())) { // 被禁用
					request.setAttribute("promptinfo", "您的账号已被禁用，请与管理员联系!");
					return mapping.findForward("relogin1");
				}else if ("3".equals(userInfo.getStatus()) && !"zxd".equals(userInfo.getLoginname())) { // 被删除
					request.setAttribute("promptinfo", "您的账号已被删除，请与管理员联系!");
					return mapping.findForward("relogin1");
				} else {
					Integer s_unitid = userInfo.getUnitid();
					
					//更新用户扩展信息
					Integer userid = userInfo.getUserid();
	        		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
	        		SysUserInfoDetail sysUserInfoDetail = suidm.getSysUserInfoDetail(userid);
	        		
	        		// 更新登录相关信息
	        		sysUserInfoDetail.setLastlogin(DateTime.getDate());
	        		sysUserInfoDetail.setLogintimes(new Integer(sysUserInfoDetail.getLogintimes().intValue() + 1));
	        		suidm.updateSysUserInfoDetail(sysUserInfoDetail);
					
	        		session.setAttribute("s_sysuserinfo", userInfo);
	        		session.setAttribute("s_sysuserinfodetail", sysUserInfoDetail);
	        		session.setAttribute("s_userid", userid);
	        		session.setAttribute("pagesize", "10");
	        		
	        		// 获取单位信息
	    			SysUnitInfoManager unitInfoManager = (SysUnitInfoManager) getBean("sysUnitInfoManager");
	    			SysUnitInfo sui = unitInfoManager.getSysUnitInfo(s_unitid);
	    			session.setAttribute("s_sysunitinfo", sui);
	    			session.setAttribute("s_unitid", s_unitid);
	    			
	    			//判断当前用户是否为当前系统的产品管理员，或是单位管理员【学校管理员】
	    			//SysUserRoleManager surm = (SysUserRoleManager) getBean("sysUserRoleManager");
	    	        //List allroleids = surm.getAllRoleids(userid);
	    	        String isAdmin = "0";
	    	        //if(allroleids.contains(Constants.SYS_ROLEID_0)){
	    	        //	isAdmin = "1";//产品管理员
	    	        //}
	    	        session.setAttribute("isAdmin", isAdmin);
	    	        
					// 加入日志
					addLog(request, "用户" + userInfo.getLoginname() +"(" + userInfo.getUsername()+")登录成功!");
					if("1".equals(userInfo.getUsertype())){
						response.sendRedirect("/pcenter.do?method=index");
						return null;
					}else {
						return mapping.findForward("manager");
					}
				}
			} else {
				request.setAttribute("promptinfo", "输入的用户名不存在!");
			}
		} catch (Exception e) {
			request.setAttribute("promptinfo", "登录出现错误,请检查后重试!");
			return mapping.findForward("relogin1");
		}
		return mapping.findForward("relogin1");
	}
	
	/**
	 * 后台用户注销登录
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward logout(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute("s_userid");
		session.removeAttribute("s_sysuserinfo");
		session.removeAttribute("s_sysuserinfodetail");
		session.removeAttribute("s_unitid");
		session.removeAttribute("s_sysunitinfo");
		session.removeAttribute("isAdmin");
		
		return mapping.findForward("index");
	}
}
