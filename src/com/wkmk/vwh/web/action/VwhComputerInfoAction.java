package com.wkmk.vwh.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.web.form.VwhComputerInfoActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 视频库服务器信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhComputerInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String computername = Encode.nullToBlank(httpServletRequest.getParameter("computername"));
		
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		VwhComputerInfoManager manager = (VwhComputerInfoManager)getBean("vwhComputerInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		if(!"".equals(computername)){
			SearchCondition.addCondition(condition, "computername", "like", "%" + computername + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "computername asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageVwhComputerInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
		List allcomputerids = vfim.getAllComputerids(unitid);
		List dataList = page.getDatalist();
		VwhComputerInfo vci = null;
		for(int i=0, size=dataList.size(); i<size; i++){
			vci = (VwhComputerInfo) dataList.get(i);
			if(allcomputerids.contains(vci.getComputerid())){
				vci.setFlags("disabled=\"disabled\"");
			}
		}
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("computername", computername);
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
		String computername = Encode.nullToBlank(httpServletRequest.getParameter("computername"));
		httpServletRequest.setAttribute("computername", computername);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		VwhComputerInfo model = new VwhComputerInfo();
		model.setUnitid(unitid);
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
		VwhComputerInfoActionForm form = (VwhComputerInfoActionForm)actionForm;
		VwhComputerInfoManager manager = (VwhComputerInfoManager)getBean("vwhComputerInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				VwhComputerInfo model = form.getVwhComputerInfo();
				manager.addVwhComputerInfo(model);
				addLog(httpServletRequest,"增加了一个视频库服务器【" + model.getComputername() + "】信息");
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
		String computername = Encode.nullToBlank(httpServletRequest.getParameter("computername"));
		httpServletRequest.setAttribute("computername", computername);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		VwhComputerInfoManager manager = (VwhComputerInfoManager)getBean("vwhComputerInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			VwhComputerInfo model = manager.getVwhComputerInfo(objid);
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
		VwhComputerInfoActionForm form = (VwhComputerInfoActionForm)actionForm;
		VwhComputerInfoManager manager = (VwhComputerInfoManager)getBean("vwhComputerInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				VwhComputerInfo model = form.getVwhComputerInfo();
				manager.updateVwhComputerInfo(model);
				addLog(httpServletRequest,"修改了一个视频库服务器【" + model.getComputername() + "】信息");
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
		VwhComputerInfoManager manager = (VwhComputerInfoManager)getBean("vwhComputerInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		VwhComputerInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getVwhComputerInfo(checkids[i]);
			manager.delVwhComputerInfo(model);
			addLog(httpServletRequest,"删除了一个视频库服务器【" + model.getComputername() + "】信息");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}