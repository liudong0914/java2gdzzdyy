package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysProductInfo;
import com.wkmk.sys.service.SysProductInfoManager;
import com.wkmk.sys.web.form.SysProductInfoActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统产品信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysProductInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String productname = Encode.nullToBlank(httpServletRequest.getParameter("productname"));
		
		SysProductInfoManager manager = (SysProductInfoManager)getBean("sysProductInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		
		if(!"".equals(productname)){
			SearchCondition.addCondition(condition, "productname", "like", "%" + productname + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "orderindex asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysProductInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("productname", productname);
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
		String productname = Encode.nullToBlank(httpServletRequest.getParameter("productname"));
		httpServletRequest.setAttribute("productname", productname);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysProductInfo model = new SysProductInfo();
		model.setSketch("product.gif");
		model.setStatus("1");
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
		SysProductInfoActionForm form = (SysProductInfoActionForm)actionForm;
		SysProductInfoManager manager = (SysProductInfoManager)getBean("sysProductInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysProductInfo model = form.getSysProductInfo();
				manager.addSysProductInfo(model);
				//addLog(httpServletRequest,"增加了一个系统产品【" + model.getProductname() + "】信息");
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
		String productname = Encode.nullToBlank(httpServletRequest.getParameter("productname"));
		httpServletRequest.setAttribute("productname", productname);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysProductInfoManager manager = (SysProductInfoManager)getBean("sysProductInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysProductInfo model = manager.getSysProductInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
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
		SysProductInfoActionForm form = (SysProductInfoActionForm)actionForm;
		SysProductInfoManager manager = (SysProductInfoManager)getBean("sysProductInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysProductInfo model = form.getSysProductInfo();
				manager.updateSysProductInfo(model);
				//addLog(httpServletRequest,"修改了一个系统产品【" + model.getProductname() + "】信息");
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
		SysProductInfoManager manager = (SysProductInfoManager)getBean("sysProductInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));

		SysProductInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysProductInfo(checkids[i]);
			if(!status.equals(model.getStatus())){
				model.setStatus(status);
				manager.updateSysProductInfo(model);
				//addLog(httpServletRequest,"禁用了一个系统产品【" + model.getProductname() + "】信息");
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}