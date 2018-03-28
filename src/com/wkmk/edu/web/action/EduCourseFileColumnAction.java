package com.wkmk.edu.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduCourseFileColumn;
import com.wkmk.edu.service.EduCourseColumnManager;
import com.wkmk.edu.service.EduCourseFileColumnManager;
import com.wkmk.edu.web.form.EduCourseFileColumnActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 课程资源目录信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileColumnAction extends BaseAction {

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
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		
		EduCourseFileColumnManager manager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(title)){
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		}
		if(!"".equals(status) && !"-1".equals(status)){
			SearchCondition.addCondition(condition, "status", "=", status);
		}else {
			SearchCondition.addCondition(condition, "status", "<>", "0");
		}
		PageList page = manager.getPageEduCourseFileColumns(condition, "columnno asc", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("status", status);
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
		HttpSession session = httpServletRequest.getSession();
	    Integer unitid = (Integer)session.getAttribute("s_unitid");
		EduCourseFileColumn model = new EduCourseFileColumn();
		model.setStatus("1");
		model.setIsopen("0");
		 model.setUnitid(unitid);
		 
		//分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
	        
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
		EduCourseFileColumnActionForm form = (EduCourseFileColumnActionForm)actionForm;
		EduCourseFileColumnManager manager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseFileColumn model = form.getEduCourseFileColumn();
				manager.addEduCourseFileColumn(model);
				addLog(httpServletRequest,"增加了一个课程资源目录信息表");
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
		   //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
		EduCourseFileColumnManager manager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			EduCourseFileColumn model = manager.getEduCourseFileColumn(objid);
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
		EduCourseFileColumnActionForm form = (EduCourseFileColumnActionForm)actionForm;
		EduCourseFileColumnManager manager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseFileColumn model = form.getEduCourseFileColumn();
				if("0".equals(model.getIsopen())){
					model.setSecondtitle("");
				}
				manager.updateEduCourseFileColumn(model);
				addLog(httpServletRequest,"修改了一个课程资源目录信息表");
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
		EduCourseFileColumnManager manager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delEduCourseFileColumn(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	  /**
     * 检查分类是否存在
     * @param actionMapping ActionMapping
     * @param actionForm ActionForm
     * @param httpServletRequest HttpServletRequest
     * @param httpServletResponse HttpServletResponse
     * @return ActionForward
     */
    public ActionForward checkColumnno(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String columnno = Encode.nullToBlank(httpServletRequest.getParameter("columnno"));
        
        EduCourseFileColumnManager manager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "columnno", "=", columnno);
        
        List lst = manager.getEduCourseFileColumns(condition, "", 0);

        PrintWriter pw = null;
        try {
            pw = httpServletResponse.getWriter();
            if(lst != null && lst.size() > 0){
                pw.write("1");
            }
        } catch (IOException ex) {
        } finally {
            if (pw != null) {
                pw.close();
            }
            pw = null;
        }

        return null;
    }
}