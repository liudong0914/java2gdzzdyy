package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.form.SysUserInfoActionForm;

import com.util.action.BaseAction;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserInfoCheckAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		SearchCondition.addCondition(condition, "status", "=", "0");//待审核
		if(!"".equals(loginname)){
			SearchCondition.addCondition(condition, "loginname", "like", "%" + loginname + "%");
		}
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		}
		if(!"".equals(sex) && !"-1".equals(sex)){
			SearchCondition.addCondition(condition, "sex", "=", sex);
		}
		if(!"".equals(usertype) && !"-1".equals(usertype)){
			SearchCondition.addCondition(condition, "usertype", "=", usertype);
		}
		if(!"".equals(studentno)){
			SearchCondition.addCondition(condition, "studentno", "like", "%" + studentno + "%");
		}
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "username asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		return actionMapping.findForward("list");
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
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysUserInfo model = manager.getSysUserInfo(objid);
			SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
			if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号解密显示
				detail.setCardno(DES.getDecryptPwd(detail.getCardno()));
			}
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("detail", detail);
			
			//获取所有省地区
			SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			List provinceList = saim.getSysAreaInfosByParentno("00");
			httpServletRequest.setAttribute("provinceList", provinceList);
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getProvince());
			List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("cityList", cityList);
			sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getCity());
			List countyList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("countyList", countyList);
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
		SysUserInfoActionForm form = (SysUserInfoActionForm)actionForm;
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserInfo model = form.getSysUserInfo();
				//将密码加密
				String password = Encode.nullToBlank(httpServletRequest.getParameter("password"));
				if (!"".equals(password)) {
					model.setPassword(MD5.getEncryptPwd(password));
				}
				manager.updateSysUserInfo(model);
				
				SysUserInfoDetail detail = form.getSysUserInfoDetail();
				if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号加密存储
					detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
				}
				detail.setUserid(model.getUserid());
				suidm.updateSysUserInfoDetail(detail);
				addLog(httpServletRequest,"修改了一个单位用户【" + model.getUsername() + "】信息");
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
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		SysUserInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysUserInfo(checkids[i]);
			model.setStatus("2");
			manager.updateSysUserInfo(model);
			//manager.delSysUserInfo(checkids[i]);
			addLog(httpServletRequest,"禁用了一个系统用户【" + model.getUsername() + "】信息");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 *批量审核
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward checkBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		SysUserInfo model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysUserInfo(checkids[i]);
			model.setStatus("1");
			manager.updateSysUserInfo(model);
			addLog(httpServletRequest,"审核了一个系统用户【" + model.getUsername() + "】信息");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward delList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		SearchCondition.addCondition(condition, "status", "=", "2");//禁用
		if(!"".equals(loginname)){
			SearchCondition.addCondition(condition, "loginname", "like", "%" + loginname + "%");
		}
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		}
		if(!"".equals(sex) && !"-1".equals(sex)){
			SearchCondition.addCondition(condition, "sex", "=", sex);
		}
		if(!"".equals(usertype) && !"-1".equals(usertype)){
			SearchCondition.addCondition(condition, "usertype", "=", usertype);
		}
		if(!"".equals(studentno)){
			SearchCondition.addCondition(condition, "studentno", "like", "%" + studentno + "%");
		}
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "username asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		return actionMapping.findForward("dellist");
	}
	
	/**
	 *批量审核
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward enableBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		SysUserInfo model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysUserInfo(checkids[i]);
			model.setStatus("1");
			manager.updateSysUserInfo(model);
			addLog(httpServletRequest,"启用了一个单位用户【" + model.getUsername() + "】信息");
		}
		return delList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
}