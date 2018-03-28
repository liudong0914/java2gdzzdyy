package com.wkmk.skin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseColumn;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduCourseUserModule;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseColumnManager;
import com.wkmk.edu.service.EduCourseFileManager;
import com.wkmk.edu.service.EduCourseFilmManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduCourseUserModuleManager;
import com.wkmk.edu.web.form.EduCourseColumnActionForm;

/**
 * <p>Description: 课程个人中心模板</p>
 * @version 1.0
 */
public class CourseManagerAction extends BaseAction {

	/**
	 * 课程管理-首页
	 */
	public ActionForward index(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		String mappingurl = "/error.html";
		try {
			String tag = Encode.nullToBlank(httpServletRequest.getParameter("tag"));
			
		    String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
		    String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
	        HttpSession session = httpServletRequest.getSession();
	        session.setAttribute("courseid", courseid);
	        session.setAttribute("courseclassid", courseclassid);
	        
	        //根据当前用户身份和角色模块，控制左侧菜单显示情况，及右侧模块管理权限控制
	        Integer userid = (Integer) session.getAttribute("s_userid");
	        EduCourseInfoManager manager = (EduCourseInfoManager) getBean("eduCourseInfoManager");
	        EduCourseInfo eduCourseInfo = manager.getEduCourseInfo(courseid);
	        session.setAttribute("eduCourseInfo", eduCourseInfo);
	        
	        EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
	        EduCourseClass eduCourseClass = eccm.getEduCourseClass(courseclassid);
	        httpServletRequest.setAttribute("eduCourseClass", eduCourseClass);
	        
	        String isAuhtor = "0";//课程作者
	        List moduleidList = new ArrayList();
        	Map moduleidMap = new HashMap();
	        if(eduCourseInfo.getSysUserInfo().getUserid().intValue() == userid.intValue()){
	        	isAuhtor = "1";
	        }else {
				//能进入此处即是当前课程班的助教
	        	EduCourseUserModuleManager ecumm = (EduCourseUserModuleManager) getBean("eduCourseUserModuleManager");
	        	List<SearchModel> condition = new ArrayList<SearchModel>();
	        	SearchCondition.addCondition(condition, "userid", "=", userid);
	        	SearchCondition.addCondition(condition, "courseclassid", "=", Integer.valueOf(courseclassid));
	        	List moduleList = ecumm.getEduCourseUserModules(condition, "moduleid asc", 0);
	        	
	        	//默认助教点击课程管理时，是查看课程信息，如果没有课程信息权限，则默认显示有权限的第一个模块
	        	String startmoduleid = "";
	        	boolean hasmodule = false;
	        	
	        	EduCourseUserModule userModule = null;
	        	for(int i=0, size=moduleList.size(); i<size; i++){
	        		userModule = (EduCourseUserModule) moduleList.get(i);
	        		moduleidList.add(userModule.getModuleid());
	        		moduleidMap.put(userModule.getModuleid(), userModule.getModuletype());
	        		
	        		if(i == 0){
	        			startmoduleid = userModule.getModuleid();
	        		}
	        		if(tag.equals(userModule.getModuleid())){
	        			hasmodule = true;
	        		}
	        	}
		        if(!hasmodule){
		        	tag = startmoduleid;
		        }
			}
	        session.setAttribute("isAuhtor", isAuhtor);
	        session.setAttribute("moduleidList", moduleidList);
	        session.setAttribute("moduleidMap", moduleidMap);
	        httpServletRequest.setAttribute("tag", tag);
	        
			mappingurl = "/skin/course/coursemanager/index.jsp";
		} catch (Exception e) {
			mappingurl = "/error.html";
		}

		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}
	
	/**
	 * 课程信息
	 */
	public ActionForward courseInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
        try {
        	HttpSession session = httpServletRequest.getSession();
        	String courseid = (String) session.getAttribute("courseid");
        	String courseclassid = (String) session.getAttribute("courseclassid");
        	
        	EduCourseInfoManager manager = (EduCourseInfoManager) getBean("eduCourseInfoManager");
        	EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
        	EduCourseInfo eduCourseInfo = manager.getEduCourseInfo(courseid);
        	EduCourseClass eduCourseClass = eccm.getEduCourseClass(courseclassid);
        	httpServletRequest.setAttribute("model", eduCourseInfo);
        	httpServletRequest.setAttribute("eduCourseClass", eduCourseClass);
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return actionMapping.findForward("courseinfo");
	}
	
	/**
	 * 课程目录管理
	 */
	public ActionForward columnMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
        try {
	        
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return actionMapping.findForward("columnmain");
	}
	
	/**
	 * 课程目录管理
	 */
	public ActionForward columnTree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
        try {
        	HttpSession session = httpServletRequest.getSession();
        	String courseid = (String) session.getAttribute("courseid");
        	
            EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
            List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
            List list = manager.getEduCourseColumns(condition, "columnno asc", 0);
            
            StringBuffer tree = new StringBuffer();
			String text = null;
			String target="rfrmright";
			String url = null;
			String hint = null;
			String no = null;
			String pno = null;
			EduCourseColumn ecc = null;
            for (int i = 0; i < list.size(); i++) {
            	ecc = (EduCourseColumn) list.get(i);
                no = ecc.getColumnno().trim();// 自身ID
                pno = ecc.getParentno().trim();// 父ID
                text = ecc.getTitle();// 树节点显示名称
                 
                url = "/courseManager.do?method=columnList&parentno=" + no;
                tree.append("\n").append("tree").append(".nodes[\"").append(pno).append("_").append(no).append("\"]=\"");
				if(text !=null && text.trim().length() > 0) {
					tree.append("text:").append(text).append(";");
				}
				if(hint!=null && hint.trim().length() > 0) {
					tree.append("hint:").append(hint).append(";");
				}
				tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
            }
            String rooturl = "/courseManager.do?method=columnList&parentno=0000";
            httpServletRequest.setAttribute("treenode", tree.toString());
            httpServletRequest.setAttribute("rooturl", rooturl);
            httpServletRequest.setAttribute("modulename", "课程目录");
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return actionMapping.findForward("columntree");
	}
	
	/**
	 * 课程目录管理
	 */
	public ActionForward columnList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		try {
			EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
			String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
	        httpServletRequest.setAttribute("parentno", parentno);
	        
	        HttpSession session = httpServletRequest.getSession();
	        Integer unitid = (Integer)session.getAttribute("s_unitid");
	        String courseid = (String)session.getAttribute("courseid");
	        
			PageUtil pageUtil = new PageUtil(httpServletRequest);
			List<SearchModel> condition = new ArrayList<SearchModel>();
		    SearchCondition.addCondition(condition, "parentno", "=", parentno);
		    SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
		    SearchCondition.addCondition(condition, "unitid", "=", unitid);
		    
			PageList page = manager.getPageEduCourseColumns(condition, "columnno asc", pageUtil.getStartCount(), pageUtil.getPageSize());
			
			EduCourseColumnManager eccm = (EduCourseColumnManager) getBean("eduCourseColumnManager");
			EduCourseFileManager ecfm = (EduCourseFileManager) getBean("eduCourseFileManager");
			EduCourseFilmManager ecfm2 = (EduCourseFilmManager) getBean("eduCourseFilmManager");
			List parentnoList = eccm.getAllParentnos(courseid);
			List courseidList1 = ecfm.getAllCoursecolumnids(courseid);
			List courseidList2 = ecfm2.getAllCoursecolumnids(courseid);
			
			EduCourseColumn ecc = null;
			List list = page.getDatalist();
			for(int i=0, size=list.size(); i<size; i++){
				ecc = (EduCourseColumn) list.get(i);
				if(parentnoList.contains(ecc.getColumnno()) || courseidList1.contains(ecc.getColumnid()) || courseidList2.contains(ecc.getColumnid())){
					ecc.setFlags("disabled=\"disabled\"");
				}
			}
			httpServletRequest.setAttribute("pagelist", page);
			
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return actionMapping.findForward("columnlist"); 
	}
	
	

	/**
	 *增加前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeAddColumn(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		httpServletRequest.setAttribute("parentno", parentno);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		HttpSession session = httpServletRequest.getSession();
        Integer unitid = (Integer)session.getAttribute("s_unitid");
	    String courseid = (String)session.getAttribute("courseid");
	    
	    EduCourseColumn model = new EduCourseColumn();
		model.setParentno(parentno);
		model.setStatus("1");
        model.setCourseid(Integer.valueOf(courseid));
        model.setUnitid(unitid);
        model.setIsopen("0");
        httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSaveColumn");
        httpServletRequest.setAttribute("num", "");
        
		saveToken(httpServletRequest);
		return actionMapping.findForward("columnedit");
	}

	/**
	 *增加时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward addSaveColumn(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduCourseColumnActionForm form = (EduCourseColumnActionForm)actionForm;
		EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseColumn model = form.getEduCourseColumn();
				manager.addEduCourseColumn(model);
				addLog(httpServletRequest,"增加了一个课程目录信息【" + model.getTitle() + "】");
			}catch (Exception e){
			    e.printStackTrace();
			}
		}

		httpServletRequest.setAttribute("reloadtree", "1");
		return columnList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *修改前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeUpdateColumn(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		String parentno = Encode.nullToBlank(httpServletRequest.getParameter("parentno"));
		httpServletRequest.setAttribute("parentno", parentno);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			EduCourseColumn model = manager.getEduCourseColumn(objid);
			httpServletRequest.setAttribute("act", "updateSaveColumn");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("num", model.getColumnno().substring(model.getColumnno().length() - 4));
		}catch (Exception e){
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("columnedit");
	}

	/**
	 *修改时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward updateSaveColumn(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		EduCourseColumnActionForm form = (EduCourseColumnActionForm)actionForm;
		EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseColumn model = form.getEduCourseColumn();
				if("0".equals(model.getIsopen())){
					model.setSecondTitle("");
				}
				manager.updateEduCourseColumn(model);
				addLog(httpServletRequest,"修改了一个课程目录信息【" + model.getTitle() + "】");
			}catch (Exception e){
			}
		}

		httpServletRequest.setAttribute("reloadtree", "1");
		return columnList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *批量删除
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward delBatchColumn(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		EduCourseColumn model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getEduCourseColumn(checkids[i]);
			manager.delEduCourseColumn(model);
			addLog(httpServletRequest,"删除了一个课程目录信息【" + model.getTitle() + "】");
		}
		
		httpServletRequest.setAttribute("reloadtree", "1");
		return columnList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	/**
	 * 学员管理
	 */
	
}
