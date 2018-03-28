package com.wkmk.sys.web.action;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.string.Encode;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserPayTrade;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserPayTradeManager;

/**
 *<p>Description: 在线交易订单明细</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserPayTradeAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserPayTradeManager manager = (SysUserPayTradeManager)getBean("sysUserPayTradeManager");
		SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		
		String subject = Encode.nullToBlank(httpServletRequest.getParameter("subject"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		String body = Encode.nullToBlank(httpServletRequest.getParameter("body"));
		
		String paytype = Encode.nullToBlank(httpServletRequest.getParameter("paytype"));
		String state = Encode.nullToBlank(httpServletRequest.getParameter("state"));
		
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String xueduan = Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
		
		HttpSession session = httpServletRequest.getSession();
//		Integer unitid = (Integer) session.getAttribute("s_unitid");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getSysUserPayTradesOfPage(subject, username, createdate, body, paytype, state, usertype, xueduan, sorderindex,  pageUtil.getStartCount(), pageUtil.getPageSize());
		ArrayList datalist = page.getDatalist();
		if(datalist !=null && datalist.size()>0){
			for(int i= 0;i<datalist.size();i++){
				SysUserPayTrade model = (SysUserPayTrade) datalist.get(i);
				Integer userid = model.getUserid();
				SysUserInfo userInfo = userInfoManager.getSysUserInfo(userid);
				model.setFlago(userInfo.getUsername());
				String paytype1 = model.getPaytype();
				if(paytype1.equals("wxpay")){
					model.setPaytype("微信支付");
				}else{
					model.setPaytype("支付宝");
				}
				String state1 = model.getState();
				if(state1.equals("0")){
					model.setState("支付未完成");
				}else if(state1.equals("1")){
					model.setState("支付异常");
				}else if(state1.equals("2")){
					model.setState("支付完成");
				}
			}
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("subject", subject);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("createdate", createdate);
		httpServletRequest.setAttribute("body", body);
		httpServletRequest.setAttribute("paytype", paytype);
		httpServletRequest.setAttribute("state", state);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("xueduan", xueduan);
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
		return actionMapping.findForward("list");
	}
}