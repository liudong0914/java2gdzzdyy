package com.wkmk.tk.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkTextBookBuy;
import com.wkmk.tk.bo.TkTextBookInfo;
import com.wkmk.tk.service.TkTextBookBuyManager;
import com.wkmk.tk.service.TkTextBookInfoManager;
import com.wkmk.tk.web.form.TkTextBookBuyActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkTextBookBuyAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		
		String textbookname = Encode.nullToBlank(httpServletRequest.getParameter("textbookname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		String recipientname = Encode.nullToBlank(httpServletRequest.getParameter("recipientname"));
		String isdelivery = Encode.nullToBlank(httpServletRequest.getParameter("isdelivery"));
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "a.createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getTkTextBookBuysOfPage(textbookname, username, createdate, recipientname, isdelivery,sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("textbookname", textbookname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("createdate", createdate);
		httpServletRequest.setAttribute("recipientname", recipientname);
		httpServletRequest.setAttribute("isdelivery", isdelivery);
		return actionMapping.findForward("list");
	}

	/**
	 *增加前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkTextBookBuy model = new TkTextBookBuy();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 *增加时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkTextBookBuyActionForm form = (TkTextBookBuyActionForm)actionForm;
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkTextBookBuy model = form.getTkTextBookBuy();
				manager.addTkTextBookBuy(model);
				addLog(httpServletRequest,"增加了一个教材基本信息表");
			}catch (Exception e){
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		TkTextBookInfoManager ttbim = (TkTextBookInfoManager)getBean("tkTextBookInfoManager");
		SysUserInfoManager suim = (SysUserInfoManager)getBean("sysUserInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			//分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
			
			//订购记录
			TkTextBookBuy model = manager.getTkTextBookBuy(objid);
			String isdelivery = model.getIsdelivery();
			if("".equals(isdelivery)){
				isdelivery ="0";
			}
			httpServletRequest.setAttribute("isdelivery", isdelivery);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			//教材详情
			Integer textbookid = model.getTextbookid();
			TkTextBookInfo textBookInfo = ttbim.getTkTextBookInfo(textbookid);
			String bookStatus ="";
			String status = textBookInfo.getStatus();
			if("1".equals(status)){
				bookStatus="正常";
			}else{
				bookStatus="禁用";
			}
			httpServletRequest.setAttribute("textBookInfo", textBookInfo);
			httpServletRequest.setAttribute("bookStatus", bookStatus);
			//购买人
			Integer buyuserid = model.getBuyuserid();
			SysUserInfo sysUserInfo = suim.getSysUserInfo(buyuserid);
			httpServletRequest.setAttribute("sysUserInfo", sysUserInfo);
			
		}catch (Exception e){
			e.printStackTrace();
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
		TkTextBookBuyActionForm form = (TkTextBookBuyActionForm)actionForm;
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String textbookbuyid = Encode.nullToBlank(httpServletRequest.getParameter("textbookbuyid"));
				String isdelivery = Encode.nullToBlank(httpServletRequest.getParameter("isdelivery"));
//				TkTextBookBuy model = form.getTkTextBookBuy();
				TkTextBookBuy model = manager.getTkTextBookBuy(textbookbuyid);
				model.setIsdelivery(isdelivery);
				manager.updateTkTextBookBuy(model);
				addLog(httpServletRequest,"修改了一个教材基本信息表");
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
		TkTextBookBuyManager manager = (TkTextBookBuyManager)getBean("tkTextBookBuyManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkTextBookBuy(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}