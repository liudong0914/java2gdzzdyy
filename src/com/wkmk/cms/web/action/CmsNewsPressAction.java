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

import com.wkmk.cms.bo.CmsNewsInfo;
import com.wkmk.cms.service.CmsNewsInfoManager;
import com.wkmk.cms.web.form.CmsNewsInfoActionForm;

import com.wkmk.cms.bo.CmsNewsColumn;
import com.wkmk.cms.service.CmsNewsColumnManager;
import com.wkmk.sys.bo.SysUserInfo;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
public class CmsNewsPressAction extends BaseAction{
	/**
	 *跳转主工作区
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward edit(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		HttpSession session = httpServletRequest.getSession();
		SysUserInfo sysUserInfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
		String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
		httpServletRequest.setAttribute("columnid", columnid);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		CmsNewsColumnManager cncm = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
		CmsNewsInfoManager manager = (CmsNewsInfoManager)getBean("cmsNewsInfoManager");
		List condition = new ArrayList();
		List list = cncm.getCmsNewsColumns(condition, "", 0);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String happendate=format.format(date);
		CmsNewsInfo model = new CmsNewsInfo();
		model.setHappendate(happendate);
		model.setStatus("1");
		model.setSketch("default.jpg");
		model.setAuthor(sysUserInfo.getUsername());
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("columnlist", list);
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
		CmsNewsInfoActionForm form = (CmsNewsInfoActionForm)actionForm;
		CmsNewsInfoManager manager = (CmsNewsInfoManager)getBean("cmsNewsInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
				httpServletRequest.setAttribute("columnid", columnid);
				HttpSession session = httpServletRequest.getSession();
				Integer unitid = (Integer)session.getAttribute("s_unitid");
				SysUserInfo sysUserInfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
				CmsNewsInfo model = form.getCmsNewsInfo();
				CmsNewsColumnManager cncm = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
				CmsNewsColumn cnc = cncm.getCmsNewsColumn(columnid);
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createdate = format.format(date);
				model.setCmsNewsColumn(cnc);
				model.setUnitid(unitid);
				model.setStatus("0");
				model.setUserid(sysUserInfo.getUserid());
				model.setHits(new Integer(0));
				model.setRecommand("0");
				model.setCreatedate(createdate);
				manager.addCmsNewsInfo(model);
				addLog(httpServletRequest,"增加了一个资讯信息");
				httpServletRequest.setAttribute("reloadtree", "1");
				httpServletRequest.setAttribute("promptinfo", "资讯发布成功!");
			}catch (Exception e){
			}
		}else{
			saveToken(httpServletRequest);
			httpServletRequest.setAttribute("promptinfo", "请勿重复刷新页面!");
		}

		return actionMapping.findForward("success");
	}

}
