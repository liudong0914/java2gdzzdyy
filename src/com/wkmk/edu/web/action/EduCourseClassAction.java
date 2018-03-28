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
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.web.form.EduCourseClassActionForm;

/**
 *<p>Description: 课程班</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseClassAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduCourseClassManager manager = (EduCourseClassManager)getBean("eduCourseClassManager");
		String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
		httpServletRequest.setAttribute("courseid", courseid);
		String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
		httpServletRequest.setAttribute("courseclassid", courseclassid);
		
		String classname = Encode.nullToBlank(httpServletRequest.getParameter("classname"));
        httpServletRequest.setAttribute("classname", classname);
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		 SearchCondition.addCondition(condition, "courseid", "=", courseid);
		 if(!"".equals(courseclassid)){
		     SearchCondition.addCondition(condition, "courseclassid", "=", courseclassid);
		 }
		 if(!"".equals(classname)){
             SearchCondition.addCondition(condition, "classname", "like", "%"+classname+"%");
         }
		PageList page = manager.getPageEduCourseClasss(condition, "courseclassid asc", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
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
	    String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
	    EduCourseClass model = new EduCourseClass();
	    model.setStatus("0");
	    model.setCourseid(Integer.valueOf(courseid));
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
		EduCourseClassActionForm form = (EduCourseClassActionForm)actionForm;
		EduCourseClassManager manager = (EduCourseClassManager)getBean("eduCourseClassManager");
		
		   String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
	        httpServletRequest.setAttribute("courseid", courseid);
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseClass model = form.getEduCourseClass();
				model.setCreatedate(DateTime.getDate());
				model.setStatus("0");
				model.setUsercount(0);
				manager.addEduCourseClass(model);
				addLog(httpServletRequest,"增加了一个课程班");
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
		EduCourseClassManager manager = (EduCourseClassManager)getBean("eduCourseClassManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
		    //分页与排序
	        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
	        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
	        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
	        httpServletRequest.setAttribute("pageno", pageno);
	        httpServletRequest.setAttribute("direction", direction);
	        httpServletRequest.setAttribute("sort", sort);
		    
		    String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
	        httpServletRequest.setAttribute("courseid", courseid);
			EduCourseClass model = manager.getEduCourseClass(objid);
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
		EduCourseClassActionForm form = (EduCourseClassActionForm)actionForm;
		EduCourseClassManager manager = (EduCourseClassManager)getBean("eduCourseClassManager");
		EduCourseInfoManager courseInfoManager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
			    //分页与排序
		        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		        httpServletRequest.setAttribute("pageno", pageno);
		        httpServletRequest.setAttribute("direction", direction);
		        httpServletRequest.setAttribute("sort", sort);
		        
				EduCourseClass model = form.getEduCourseClass();
				manager.updateEduCourseClass(model);
				if(model.getStatus().equals("1")){
				    EduCourseInfo eduCourseInfo = courseInfoManager.getEduCourseInfo(model.getCourseid());
				    eduCourseInfo.setStartdate(model.getStartdate());
				    eduCourseInfo.setEnddate(model.getEnddate());
				    if(!eduCourseInfo.getStatus().equals("1")){
				        eduCourseInfo.setStatus("1");
				    }
				    courseInfoManager.updateEduCourseInfo(eduCourseInfo);
				}
				addLog(httpServletRequest,"修改了一个课程班");
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
		EduCourseClassManager manager = (EduCourseClassManager)getBean("eduCourseClassManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delEduCourseClass(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
     * 跳转到主工作区
     * 
     * @param actionMapping
     *            ActionMapping
     * @param actionForm
     *            ActionForm
     * @param httpServletRequest
     *            HttpServletRequest
     * @param httpServletResponse
     *            HttpServletResponse
     * @return ActionForward
     */
    public ActionForward main(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
            return actionMapping.findForward("main");
    }
    /**
     * 树形选择器
     * 
     * @param actionMapping
     *            ActionMapping
     * @param actionForm
     *            ActionForm
     * @param httpServletRequest
     *            HttpServletRequest
     * @param httpServletResponse
     *            HttpServletResponse
     * @return ActionForward
     */
    public ActionForward tree(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
        EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        //课程信息
        EduCourseInfo model = manager.getEduCourseInfo(courseid);
        Integer userid = model.getSysUserInfo().getUserid();
        httpServletRequest.setAttribute("userid", userid.toString());
        httpServletRequest.setAttribute("model", model);
        //课程批次
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "courseid", "=", courseid);
        List lst = classManager.getEduCourseClasss(condition, "courseclassid asc", 0);
        String tree = "";
        String url = "";
        EduCourseClass cnc = null;
        String no = "";// 自身ID
        String pno = "";// 父ID
        String text = "";// 树节点显示名称
        String hint = "";
        String target = "";
        for (int i = 0; i < lst.size(); i++) {
            cnc = (EduCourseClass) lst.get(i);
            no = cnc.getCourseclassid().toString().trim();// 自身ID
            text = cnc.getClassname();// 树节点显示名称
              
             
//            url = "eduCourseClassAction.do?method=list&courseclassid=" + cnc.getCourseclassid()+"&courseid="+cnc.getCourseid();// onclick树节点后执行
            url="javascript:;";
            target = "rfrmright";// 页面在打开位置
            tree += "\n" + "tree" + ".nodes[\"" +  "0000_" + no + "\"]=\"";
            if (text != null && text.trim().trim().length() > 0) {
                tree += "text:" + text + ";";
            }
            if (hint != null && hint.trim().length() > 0) {
                tree += "hint:" + hint + ";";
            }
            tree += "url:" + url + ";";
            tree += "target:" + target + ";";
            tree += "\";";
        }
        String rooturl = "/eduCourseClassAction.do?method=list&courseid="+courseid;
        httpServletRequest.setAttribute("treenode", tree);
        httpServletRequest.setAttribute("rooturl", rooturl);
        httpServletRequest.setAttribute("modulename", "课程批次");
        return actionMapping.findForward("tree");
    }
}