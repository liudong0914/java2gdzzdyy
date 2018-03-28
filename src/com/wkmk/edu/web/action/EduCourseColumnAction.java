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

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduCourseColumn;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.service.EduCourseColumnManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.web.form.EduCourseColumnActionForm;

/**
 *<p>Description: 课程目录信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseColumnAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String columnno = Encode.nullToBlank(httpServletRequest.getParameter("columnno"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
		String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
        httpServletRequest.setAttribute("parentno", parentno);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("columnno", columnno);
		httpServletRequest.setAttribute("status", status);
		httpServletRequest.setAttribute("courseid", courseid);
		httpServletRequest.setAttribute("courseclassid", courseclassid);
        HttpSession session = httpServletRequest.getSession();
        Integer unitid = (Integer)session.getAttribute("s_unitid");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		   if(!"".equals(title)){
	            SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
	        }  
		   if(!"".equals(columnno)){
	            SearchCondition.addCondition(condition, "columnno", "=", columnno);
	        }  
		   if(!"".equals(status)){
	            SearchCondition.addCondition(condition, "status", "=", status);
	        }
		   SearchCondition.addCondition(condition, "parentno", "=", parentno);
		   SearchCondition.addCondition(condition, "courseid", "=", courseid);
		   SearchCondition.addCondition(condition, "unitid", "=", unitid);
		PageList page = manager.getPageEduCourseColumns(condition, "columnno asc", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("tag", "2");
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
	    String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
        String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
        httpServletRequest.setAttribute("parentno", parentno);
	    EduCourseColumn model = new EduCourseColumn();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		model.setParentno(parentno);
		model.setStatus("1");
        model.setCourseid(Integer.valueOf(courseid));
        model.setUnitid(unitid);
        model.setIsopen("0");
		  //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
        
        List<SearchModel>condition = new ArrayList<SearchModel>();
        EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
        SearchCondition.addCondition(condition, "parentno", "=", parentno);
        String num = "";
        List lst2 = manager.getEduCourseColumns(condition, "columnno", 0);
        int k = lst2.size();
        if (k < 9) {
            num = "0" + String.valueOf(k + 1) + "00";
        } else {
            num = String.valueOf(k + 1) + "00";
        }
        httpServletRequest.setAttribute("num", num);
        
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
		EduCourseColumnActionForm form = (EduCourseColumnActionForm)actionForm;
		EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
		String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
		String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
		
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseColumn model = form.getEduCourseColumn();
				manager.addEduCourseColumn(model);
				addLog(httpServletRequest,"增加了一个课程目录信息");
			}catch (Exception e){
			    e.printStackTrace();
			}
		}
		httpServletRequest.setAttribute("reloadtree", "1");
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
		EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
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
	        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
	        httpServletRequest.setAttribute("courseid", courseid);
	        httpServletRequest.setAttribute("courseclassid", courseclassid);
	        
			EduCourseColumn model = manager.getEduCourseColumn(objid);
			httpServletRequest.setAttribute("num", model.getColumnno().substring(model.getColumnno().length() - 4));
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
		EduCourseColumnActionForm form = (EduCourseColumnActionForm)actionForm;
		EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseColumn model = form.getEduCourseColumn();
				if("0".equals(model.getIsopen())){
					model.setSecondTitle("");
				}
				manager.updateEduCourseColumn(model);
				addLog(httpServletRequest,"修改了一个课程目录信息");
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
		EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delEduCourseColumn(checkids[i]);
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
        EduCourseColumnManager columnManager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
        //课程信息
        EduCourseInfo model = manager.getEduCourseInfo(courseid);
        Integer userid = model.getSysUserInfo().getUserid();
        httpServletRequest.setAttribute("userid", userid.toString());
        httpServletRequest.setAttribute("model", model);
        //课程目录
        List lst = columnManager.getEduCourseColumnByCourseid(model.getCourseid(), model.getUnitid());
        String tree = "";
        String url = "";
        EduCourseColumn cnc = null;
        String no = "";// 自身ID
        String pno = "";// 父ID
        String text = "";// 树节点显示名称
        String hint = "";
        String target = "";
        for (int i = 0; i < lst.size(); i++) {
            cnc = (EduCourseColumn) lst.get(i);
            no = cnc.getColumnno().trim();// 自身ID
            pno = cnc.getParentno().trim();// 父ID
            text = cnc.getTitle();// 树节点显示名称
              
             
            url = "eduCourseColumnAction.do?method=list&columnid=" + cnc.getColumnid()+"&courseid="+cnc.getCourseid()+"&courseclassid="+courseclassid+"&parentno="
                    + cnc.getColumnno();// onclick树节点后执行
            target = "rfrmright";// 页面在打开位置
            tree += "\n" + "tree" + ".nodes[\"" + pno + "_" + no + "\"]=\"";
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
        String rooturl = "/eduCourseColumnAction.do?method=list&courseid="+courseid+"&parentno=0000";
        httpServletRequest.setAttribute("treenode", tree);
        httpServletRequest.setAttribute("rooturl", rooturl);
        httpServletRequest.setAttribute("modulename", "课程目录");
        return actionMapping.findForward("tree");
    }
    
    /**
     * 检查栏目是否存在
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
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        
        EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "columnno", "=", columnno);
        SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
        
        List lst = manager.getEduCourseColumns(condition, "", 0);

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