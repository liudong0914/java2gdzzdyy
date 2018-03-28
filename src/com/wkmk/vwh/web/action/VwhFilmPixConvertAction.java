package com.wkmk.vwh.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.util.file.ConvertFile;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhFilmPixManager;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 视频库视频影片转码</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmPixConvertAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
		String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
		
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		SearchCondition.addCondition(condition, "convertstatus", "=", convertstatus);
		if(!"".equals(name)){
			SearchCondition.addCondition(condition, "name", "like", "%" + name + "%");	
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageVwhFilmPixs(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("name", name);
		httpServletRequest.setAttribute("convertstatus", convertstatus);
		
		return actionMapping.findForward("list");
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
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		VwhFilmPix model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getVwhFilmPix(checkids[i]);
			ConvertFile.convertVod(model, "delete", 0);
			manager.delVwhFilmPix(model);
			addLog(httpServletRequest,"删除了一个微课程视频【" + model.getName() + "】");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 *批量优先转换
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward convertBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		VwhFilmPix model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getVwhFilmPix(checkids[i]);
			ConvertFile.convertVod(model, "update", 1);
			manager.updateVwhFilmPix(model);
			addLog(httpServletRequest,"设置了一个微课程视频【" + model.getName() + "】优先转换");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
}