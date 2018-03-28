package com.wkmk.edu.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduKnopointInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduKnopointInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;

import com.util.action.BaseAction;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 教育学科信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduSelectAction extends BaseAction {

	/**
	 *获取当前学校的教学学科
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward selectSubject(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUnitInfo sysUnitInfo = (SysUnitInfo) httpServletRequest.getSession().getAttribute("s_sysunitinfo");
		
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List list = manager.getAllSubjectByUnitType(sysUnitInfo.getType());
		httpServletRequest.setAttribute("list", list);
		
		return actionMapping.findForward("selectsubject");
	}
	
	/**
	 *获取某年级的教材版本
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward selectGrade(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		
		SysUnitInfo sysUnitInfo = (SysUnitInfo) httpServletRequest.getSession().getAttribute("s_sysunitinfo");
		
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List list = manager.getAllGradeBySubjectAndUnitType(Integer.valueOf(subjectid), sysUnitInfo.getType());
		httpServletRequest.setAttribute("list", list);
		
		return actionMapping.findForward("selectgrade");
	}
	
	/**
	 * K12选择知识点
	 */
	public ActionForward selectKnopoint(ActionMapping actionMapping, ActionForm actionForm,
			HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
		try {
			String knopointid = Encode.nullToBlank(httpServletRequest.getParameter("knopointid"));
			String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
			String gradeid = Encode.nullToBlank(httpServletRequest.getParameter("gradeid"));
			EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
			EduGradeInfo egi = egim.getEduGradeInfo(gradeid);
			
			String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
			EduKnopointInfoManager manager = (EduKnopointInfoManager) getBean("eduKnopointInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "1");
			SearchCondition.addCondition(condition, "gradetype", "=", egi.getXueduanid().toString());
			SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
			if(!"".equals(title)){
				SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
			}
			List knopointList = manager.getEduKnopointInfos(condition, "knopointno asc", 0);
			httpServletRequest.setAttribute("knopointid", knopointid);
			httpServletRequest.setAttribute("subjectid", subjectid);
			httpServletRequest.setAttribute("gradeid", gradeid);
			httpServletRequest.setAttribute("title", title);
			httpServletRequest.setAttribute("knopointList", knopointList);
			
			int count = 0;
			EduKnopointInfo eki = null;
			for(int i=0, size=knopointList.size(); i<size; i++){
				eki = (EduKnopointInfo) knopointList.get(i);
				if("1".equals(eki.getType())){
					count ++;
				}
			}
			httpServletRequest.setAttribute("size", count);
	    }catch (Exception e) {
	    	e.printStackTrace();
	    }

	    return actionMapping.findForward("selectknopoint");
	}

	/**
	 *选择
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward deal(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String tag = Encode.nullToBlank(httpServletRequest.getParameter("tag"));
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		
		String objname = "";
		if("selectsubject".equals(tag)){
			EduSubjectInfoManager manager = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
			EduSubjectInfo model = manager.getEduSubjectInfo(objid);
			objname = model.getSubjectname();
		}
		if("selectgrade".equals(tag)){
			EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
			EduGradeInfo model = manager.getEduGradeInfo(objid);
			objname = model.getGradename();
		}
		if("selectknopoint".equals(tag)){
			String[] checkids = httpServletRequest.getParameterValues("checkid");
			if(checkids != null && checkids.length > 0){
				EduKnopointInfoManager manager = (EduKnopointInfoManager) getBean("eduKnopointInfoManager");
				StringBuffer ids = new StringBuffer();
				StringBuffer names = new StringBuffer();
				EduKnopointInfo model = null;
				for(int i=0, size=checkids.length; i<size; i++){
					model = manager.getEduKnopointInfo(checkids[i]);
					ids.append(";").append(model.getKnopointid());
					names.append(";").append(model.getTitle());
				}
				objid = ids.substring(1);
				objname = names.substring(1);
			}else {
				objid = "";
				objname = "";
			}
		}
		httpServletRequest.setAttribute("objid", objid);
		httpServletRequest.setAttribute("objname", objname);

		return actionMapping.findForward("deal");
	}
}