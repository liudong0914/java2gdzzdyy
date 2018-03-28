package com.wkmk.edu.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduBookInfo;
import com.wkmk.edu.service.EduBookInfoManager;
import com.wkmk.edu.web.form.EduBookInfoActionForm;

/**
 *<p>Description: 教材课本</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduBookInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduBookInfoManager manager = (EduBookInfoManager)getBean("eduBookInfoManager");
		
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String subtitle = Encode.nullToBlank(httpServletRequest.getParameter("subtitle"));
		String coursetypeid = Encode.nullToBlank(httpServletRequest.getParameter("coursetypeid"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(title)){
		    SearchCondition.addCondition(condition, "title", "like", "%"+title+"%");
		}
		if(!"".equals(subtitle)){
		    SearchCondition.addCondition(condition, "subtitle", "like", "%"+subtitle+"%");
		}
		if(!"".equals(coursetypeid)){
		    SearchCondition.addCondition(condition, "coursetypeid", "=", coursetypeid);
		}
		if(!"".equals(status)){
		    SearchCondition.addCondition(condition, "status", "=", status);
		}
		if(!"".equals(createdate)){
		    SearchCondition.addCondition(condition, "createdate", "like", "%"+createdate+"%");
		}
        
        String sorderindex = "orderindex asc";
        if(!"".equals(pageUtil.getOrderindex())){
            sorderindex = pageUtil.getOrderindex();
        }
		PageList page = manager.getPageEduBookInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("subtitle", subtitle);
		httpServletRequest.setAttribute("coursetypeid", coursetypeid);
		httpServletRequest.setAttribute("status", status);
		httpServletRequest.setAttribute("createdate", createdate);
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
	    String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
        String subtitle = Encode.nullToBlank(httpServletRequest.getParameter("subtitle"));
        String coursetypeid = Encode.nullToBlank(httpServletRequest.getParameter("coursetypeid"));
        String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
        String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
        
        httpServletRequest.setAttribute("title", title);
        httpServletRequest.setAttribute("subtitle", subtitle);
        httpServletRequest.setAttribute("coursetypeid", coursetypeid);
        httpServletRequest.setAttribute("status", status);
        httpServletRequest.setAttribute("createdate", createdate);
	    
	    //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
	    
	    EduBookInfo model = new EduBookInfo();
	    model.setCreatedate(DateTime.getDate());
	    model.setSketch("default.jpg");
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
		EduBookInfoActionForm form = (EduBookInfoActionForm)actionForm;
		EduBookInfoManager manager = (EduBookInfoManager)getBean("eduBookInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduBookInfo model = form.getEduBookInfo();
				manager.addEduBookInfo(model);
				addLog(httpServletRequest,"增加了一个教材课本");
			}catch (Exception e){
			    e.printStackTrace();
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
		EduBookInfoManager manager = (EduBookInfoManager)getBean("eduBookInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
        String subtitle = Encode.nullToBlank(httpServletRequest.getParameter("subtitle"));
        String coursetypeid = Encode.nullToBlank(httpServletRequest.getParameter("coursetypeid"));
        String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
        String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
        
        httpServletRequest.setAttribute("title", title);
        httpServletRequest.setAttribute("subtitle", subtitle);
        httpServletRequest.setAttribute("coursetypeid", coursetypeid);
        httpServletRequest.setAttribute("status", status);
        httpServletRequest.setAttribute("createdate", createdate);
        
        //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
		try {
			EduBookInfo model = manager.getEduBookInfo(objid);
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
		EduBookInfoActionForm form = (EduBookInfoActionForm)actionForm;
		EduBookInfoManager manager = (EduBookInfoManager)getBean("eduBookInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduBookInfo model = form.getEduBookInfo();
				manager.updateEduBookInfo(model);
				addLog(httpServletRequest,"修改了一个教材课本");
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
		EduBookInfoManager manager = (EduBookInfoManager)getBean("eduBookInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		//EduBookInfo model =null;		for (int i = 0; i < checkids.length; i++) {
		  //  model = manager.getEduBookInfo(checkids[i]);
		   // model.setStatus("2");
		 //   manager.updateEduBookInfo(model);
			manager.delEduBookInfo(checkids[i]);
		    //addLog(httpServletRequest,"删除了一个教材课本【" + model.getTitle()+ "】信息");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}