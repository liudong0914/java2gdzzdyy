package com.wkmk.edu.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.wkmk.edu.bo.EduCourseComment;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.service.EduCourseCommentManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.web.form.EduCourseCommentActionForm;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.util.common.IpUtil;

/**
 *<p>Description: 课程评价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseCommentAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduCourseCommentManager manager = (EduCourseCommentManager)getBean("eduCourseCommentManager");
		HttpSession session = httpServletRequest.getSession();
        String courseid = (String) session.getAttribute("courseid");
        httpServletRequest.setAttribute("courseid", courseid);
        
        String content = Encode.nullToBlank(httpServletRequest.getParameter("content"));
        httpServletRequest.setAttribute("content", content);
        
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "courseid", "=", courseid);
		SearchCondition.addCondition(condition, "status", "=", "1");
		if(!"".equals(content)){
		    SearchCondition.addCondition(condition, "content", "like", "%"+content+"%");
		}
		PageList page = manager.getPageEduCourseComments(condition, "createdate desc", pageUtil.getStartCount(), pageUtil.getPageSize());
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
		EduCourseComment model = new EduCourseComment();
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
		EduCourseCommentActionForm form = (EduCourseCommentActionForm)actionForm;
		EduCourseCommentManager manager = (EduCourseCommentManager)getBean("eduCourseCommentManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseComment model = form.getEduCourseComment();
				manager.addEduCourseComment(model);
				addLog(httpServletRequest,"增加了一个课程评价");
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
		EduCourseCommentManager manager = (EduCourseCommentManager)getBean("eduCourseCommentManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("commentid"));
		try {
			EduCourseComment model = manager.getEduCourseComment(objid);
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
		EduCourseCommentActionForm form = (EduCourseCommentActionForm)actionForm;
		EduCourseCommentManager manager = (EduCourseCommentManager)getBean("eduCourseCommentManager");
		SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		 
		  HttpSession session = httpServletRequest.getSession();
          Integer s_userid = (Integer)session.getAttribute("s_userid");
		  
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseComment model = form.getEduCourseComment();
				SysUserInfo sysUserInfo = userInfoManager.getSysUserInfo(userid);
				model.setSysUserInfo(sysUserInfo);
				model.setReplyuserid(s_userid);
				model.setReplycreatedate(DateTime.getDate());
				model.setReplyuserip(IpUtil.getIpAddr(httpServletRequest));
				
				manager.updateEduCourseComment(model);
				addLog(httpServletRequest,"修改了一个课程评价");
			}catch (Exception e){
			    e.printStackTrace();
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
		EduCourseCommentManager manager = (EduCourseCommentManager)getBean("eduCourseCommentManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delEduCourseComment(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 查看评论
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-22 下午2:33:20 
	* @lastModified ; 2017-2-22 下午2:33:20 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward view(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        EduCourseCommentManager manager = (EduCourseCommentManager)getBean("eduCourseCommentManager");
        EduCourseInfoManager infoManager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
        String commentid = Encode.nullToBlank(httpServletRequest.getParameter("commentid"));
        try {
            //分页与排序
            String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
            String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
            String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
            httpServletRequest.setAttribute("pageno", pageno);
            httpServletRequest.setAttribute("direction", direction);
            httpServletRequest.setAttribute("sort", sort);
            
            EduCourseComment model = manager.getEduCourseComment(commentid);
            Integer courseid = model.getCourseid();
            EduCourseInfo courseInfo = infoManager.getEduCourseInfo(courseid);
            httpServletRequest.setAttribute("courseInfo", courseInfo);
            httpServletRequest.setAttribute("model", model);
        }catch (Exception e){
            e.printStackTrace();
        }
        return actionMapping.findForward("view");
    }
}