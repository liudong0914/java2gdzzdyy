package com.wkmk.cms.web.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.cms.bo.CmsImageInfo;
import com.wkmk.cms.service.CmsImageInfoManager;
import com.wkmk.cms.web.form.CmsImageInfoActionForm;
import com.wkmk.util.common.CacheUtil;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 图片广告信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsImageInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		httpServletRequest.setAttribute("title", title);
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer)session.getAttribute("s_unitid");
		CmsImageInfoManager manager = (CmsImageInfoManager)getBean("cmsImageInfoManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "title", "like", "%"+title+"%");
		SearchCondition.addCondition(condition, "unitid", "=", Integer.valueOf(unitid));
		String sorderindex = "orderindex desc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageCmsImageInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List datelist = page.getDatalist();
		if(datelist.size()>0){
			for(int i=0;i<datelist.size();i++){
				CmsImageInfo cmsImageInfo = (CmsImageInfo)datelist.get(i);
				cmsImageInfo.setCreatedate(cmsImageInfo.getCreatedate().substring(0,10));
			}
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
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
		CmsImageInfo model = new CmsImageInfo();
		model.setSketch("default.jpg");
		model.setStatus("1");
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
        
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
		CmsImageInfoActionForm form = (CmsImageInfoActionForm)actionForm;
		CmsImageInfoManager manager = (CmsImageInfoManager)getBean("cmsImageInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				CmsImageInfo model = form.getCmsImageInfo();
				HttpSession session = httpServletRequest.getSession();
				Integer unitid = (Integer)session.getAttribute("s_unitid");
				model.setUnitid(unitid);
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createdate = format.format(date); 
				model.setCreatedate(createdate);
				manager.addCmsImageInfo(model);
				addLog(httpServletRequest,"增加了一个图片广告信息");
				
				//删除微信端缓存，重新获取最新数据
				CacheUtil.deleteObject("CmsImageInfo_List");
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
		CmsImageInfoManager manager = (CmsImageInfoManager)getBean("cmsImageInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			//分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
			
			
			CmsImageInfo model = manager.getCmsImageInfo(objid);
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
		CmsImageInfoActionForm form = (CmsImageInfoActionForm)actionForm;
		CmsImageInfoManager manager = (CmsImageInfoManager)getBean("cmsImageInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				CmsImageInfo model = form.getCmsImageInfo();
				HttpSession session = httpServletRequest.getSession();
				Integer unitid = (Integer)session.getAttribute("s_unitid");
				model.setUnitid(unitid);
				manager.updateCmsImageInfo(model);
				addLog(httpServletRequest,"修改了一个图片广告信息");
				//删除微信端缓存，重新获取最新数据
				CacheUtil.deleteObject("CmsImageInfo_List");
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
		CmsImageInfoManager manager = (CmsImageInfoManager)getBean("cmsImageInfoManager");
		CmsImageInfo model = null;
		String[] checkids = httpServletRequest.getParameterValues("checkid");
        if(checkids != null){		for (int i = 0; i < checkids.length; i++) {
			model = manager.getCmsImageInfo(checkids[i]);
			manager.delCmsImageInfo(checkids[i]);
			addLog(httpServletRequest,"删除了一个"+ model.getTitle() +"图片广告信息");
		}
        }
		//删除微信端缓存，重新获取最新数据
		CacheUtil.deleteObject("CmsImageInfo_List");
		
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

}