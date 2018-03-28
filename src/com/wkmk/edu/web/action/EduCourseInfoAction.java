package com.wkmk.edu.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.wkmk.edu.bo.EduCourseFile;
import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseColumnManager;
import com.wkmk.edu.service.EduCourseFileManager;
import com.wkmk.edu.service.EduCourseFilmManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.edu.web.form.EduCourseInfoActionForm;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.service.VwhFilmInfoManager;

/**
 *<p>Description: 课程信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageEduCourseInfos(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		return actionMapping.findForward("list");
	}

	/**
	 * 进入课程管理页面，展示当前课程基本信息
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-16 下午4:33:47 
	* @lastModified ; 2017-2-16 下午4:33:47 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward index(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        try {
            EduCourseInfo model = manager.getEduCourseInfo(courseid);
            httpServletRequest.setAttribute("tag", "1");
            httpServletRequest.setAttribute("courseid", model.getCourseid());
            httpServletRequest.setAttribute("courseclassid", courseclassid);
            httpServletRequest.setAttribute("model", model);
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        return actionMapping.findForward("index");
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
		EduCourseInfo model = new EduCourseInfo();
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
		EduCourseInfoActionForm form = (EduCourseInfoActionForm)actionForm;
		EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseInfo model = form.getEduCourseInfo();
				manager.addEduCourseInfo(model);
				addLog(httpServletRequest,"增加了一个课程信息");
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
		EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
		String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
		String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
		try {
		    EduCourseInfo model = manager.getEduCourseInfo(courseid);
            httpServletRequest.setAttribute("tag", "1");
            httpServletRequest.setAttribute("act", "updateSave");
            httpServletRequest.setAttribute("courseid", model.getCourseid());
            httpServletRequest.setAttribute("courseclassid", courseclassid);
            httpServletRequest.setAttribute("coursetypeid", model.getCoursetypeid());
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
		EduCourseInfoActionForm form = (EduCourseInfoActionForm)actionForm;
		EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
		SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseInfo model = form.getEduCourseInfo();
				SysUserInfo sysUserInfo = userInfoManager.getSysUserInfo(userid);
	               model.setSysUserInfo(sysUserInfo);
				manager.updateEduCourseInfo(model);
				addLog(httpServletRequest,"修改了一个课程信息");
			}catch (Exception e){
			}
		}

		return index(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
		EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delEduCourseInfo(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 课程审核，列表
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-13 上午11:39:57 
	* @lastModified ; 2017-2-13 上午11:39:57 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
   public ActionForward checkCourseList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
        String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
        String coursetypeid = Encode.nullToBlank(httpServletRequest.getParameter("coursetypeid"));
        String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
        
        PageUtil pageUtil = new PageUtil(httpServletRequest);
       
        String sorderindex = "createdate desc";
        if(!"".equals(pageUtil.getOrderindex())){
            sorderindex = pageUtil.getOrderindex();
        }
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "title", "like", "%"+ title +"%");
        SearchCondition.addCondition(condition, "coursetypeid", "=", coursetypeid);
        SearchCondition.addCondition(condition, "createdate", "like", "%"+ createdate +"%");
        //PageList page = manager.getEduCourseInfosOfPage(title, coursetypeid, createdate, sorderindex,  pageUtil.getStartCount(), pageUtil.getPageSize());
        PageList page = manager.getPageEduCourseInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
        httpServletRequest.setAttribute("pagelist", page);
        httpServletRequest.setAttribute("title", title);
        httpServletRequest.setAttribute("coursetypeid", coursetypeid);
        httpServletRequest.setAttribute("createdate", createdate);
        return actionMapping.findForward("checklist");
    }
   
   /**
    * 审核前
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-14 上午9:41:05 
   * @lastModified ; 2017-2-14 上午9:41:05 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward beforeEditCheck(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       String objid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       try {
           String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
           String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
           String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
           httpServletRequest.setAttribute("pageno", pageno);
           httpServletRequest.setAttribute("direction", direction);
           httpServletRequest.setAttribute("sort", sort);
           
           EduCourseInfo model = manager.getEduCourseInfo(objid);
           Integer userid = model.getSysUserInfo().getUserid();
           httpServletRequest.setAttribute("userid", userid.toString());
           httpServletRequest.setAttribute("act", "saveEditCheck");
           httpServletRequest.setAttribute("model", model);
       }catch (Exception e){
       }

       saveToken(httpServletRequest);
       return actionMapping.findForward("beforeEditCheck");
   }
   
   /**
    * 审核保存
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-14 上午10:53:31 
   * @lastModified ; 2017-2-14 上午10:53:31 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward saveEditCheck(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseInfoActionForm form = (EduCourseInfoActionForm)actionForm;
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
       EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
       
       String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
       if (isTokenValid(httpServletRequest, true)) {
           try {
               EduCourseInfo model = form.getEduCourseInfo();
               SysUserInfo sysUserInfo = userInfoManager.getSysUserInfo(userid);
               model.setSysUserInfo(sysUserInfo);
              manager.updateEduCourseInfo(model);
               addLog(httpServletRequest,"审核了一个课程信息");
           }catch (Exception e){
           }
       }

       return checkCourseList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
   }
   
   /**
    * 预览课程
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-14 下午1:33:18 
   * @lastModified ; 2017-2-14 下午1:33:18 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward view(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       String objid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       try {
           EduCourseInfo model = manager.getEduCourseInfo(objid);
           Integer userid = model.getSysUserInfo().getUserid();
           httpServletRequest.setAttribute("userid", userid.toString());
           httpServletRequest.setAttribute("model", model);
       }catch (Exception e){
           e.printStackTrace();
       }
       return actionMapping.findForward("view");
   }
   
   /**
    * 课程文件查看
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-14 下午4:51:27 
   * @lastModified ; 2017-2-14 下午4:51:27 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward viewFileMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       httpServletRequest.setAttribute("courseid", courseid);
       return actionMapping.findForward("viewfilemain");
   }
   /**
    * 树型选择器
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
   public ActionForward viewFileTree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseColumnManager columnManager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
       String objid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       //课程信息
       EduCourseInfo model = manager.getEduCourseInfo(objid);
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
             
            
           url = "eduCourseInfoAction.do?method=viewFileList&columnid=" + cnc.getColumnid()+"&courseid="+cnc.getCourseid();// onclick树节点后执行
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
       String rooturl = "/sysLayoutAction.do?method=welcome";
       httpServletRequest.setAttribute("treenode", tree);
       httpServletRequest.setAttribute("rooturl", rooturl);
       httpServletRequest.setAttribute("modulename", "课程文件");
       return actionMapping.findForward("viewfiletree");
   }
   /**
    * 课程文档查看
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-14 下午4:36:08 
   * @lastModified ; 2017-2-14 下午4:36:08 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward viewFileList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseFileManager fileManager = (EduCourseFileManager)getBean("eduCourseFileManager");
       SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
       String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
       httpServletRequest.setAttribute("courseid", courseid);
       httpServletRequest.setAttribute("columnid", columnid);
       
       String filename = Encode.nullToBlank(httpServletRequest.getParameter("filename"));
       httpServletRequest.setAttribute("filename", filename);
       String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
       httpServletRequest.setAttribute("createdate", createdate);
       String filetype = Encode.nullToBlank(httpServletRequest.getParameter("filetype"));
       httpServletRequest.setAttribute("filetype", filetype);
       
       try {
           //课程文件
           PageUtil pageUtil = new PageUtil(httpServletRequest);
           List<SearchModel> condition = new ArrayList<SearchModel>();
           SearchCondition.addCondition(condition, "coursecolumnid", "=", columnid);
           SearchCondition.addCondition(condition, "courseid", "=", courseid);
//           SearchCondition.addCondition(condition, "convertstatus", "=", "1");
           if(!"".equals(filename)){
               SearchCondition.addCondition(condition, "filename", "like", "%"+filename+"%");
           }
           if(!"".equals(createdate)){
               SearchCondition.addCondition(condition, "createdate", "like", "%"+createdate+"%");
           }
           if(!"".equals(filetype)){
               SearchCondition.addCondition(condition, "filetype", "=", filetype);
           }
           
           PageList pageList = fileManager.getPageEduCourseFiles(condition, "fileid asc", pageUtil.getStartCount(), pageUtil.getPageSize());
           ArrayList datalist = pageList.getDatalist();
           if(datalist !=null && datalist.size()>0){
               for(int i=0;i<datalist.size();i++){
                   EduCourseFile model = (EduCourseFile) datalist.get(i);
                 Integer userid = model.getUserid();
                 SysUserInfo info = userInfoManager.getSysUserInfo(userid);
                 String username = info.getUsername();
                 model.setFlagl(username);
               }
           }
           httpServletRequest.setAttribute("pagelist", pageList);
       }catch (Exception e){
       }
       return actionMapping.findForward("viewfilelist");
   }
   
   /**
    * 课程目录
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-14 下午5:35:07 
   * @lastModified ; 2017-2-14 下午5:35:07 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward viewColumn(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseColumnManager columnManager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
       String objid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       try {
           EduCourseInfo model = manager.getEduCourseInfo(objid);
           httpServletRequest.setAttribute("courseid", model.getCourseid());
        
           String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
           httpServletRequest.setAttribute("title", title);
           PageUtil pageUtil = new PageUtil(httpServletRequest);
           List<SearchModel>condition = new ArrayList<SearchModel>();
           if(!"".equals(title)){
               SearchCondition.addCondition(condition, "title", "like", "%"+title+"%");
           }
           SearchCondition.addCondition(condition, "unitid", "=", model.getUnitid());
           SearchCondition.addCondition(condition, "courseid", "=", model.getCourseid());
           String sorderindex = "a.columnno asc";
           if (!"".equals(pageUtil.getOrderindex())) {
               sorderindex = pageUtil.getOrderindex();
           }
           PageList page = columnManager.getPageEduCourseColumns(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
         ArrayList datalist = page.getDatalist();
         if(datalist !=null && datalist.size()>0){
             for(int i=0;i<datalist.size();i++){
                 EduCourseColumn column = (EduCourseColumn) datalist.get(i);
                 String columnno = column.getColumnno();
                 if(columnno.length() == 8){
                     String title2 = column.getTitle();
                     title2 = "—— "+title2;
                     column.setTitle(title2);
                 }
                 if(columnno.length() == 12){
                     String title2 = column.getTitle();
                     title2 = "—— —— "+title2;
                     column.setTitle(title2);
                 }
             }
         }
           
           httpServletRequest.setAttribute("pagelist", page);
           httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
           
       }catch (Exception e){
           e.printStackTrace();
       }
       return actionMapping.findForward("viewcolumn");
   }
   
   /**
    * 课程批次
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-15 上午9:23:16 
   * @lastModified ; 2017-2-15 上午9:23:16 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward viewClass(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
       String objid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       try {
           EduCourseInfo model = manager.getEduCourseInfo(objid);
           httpServletRequest.setAttribute("courseid", model.getCourseid());
        
           String classname = Encode.nullToBlank(httpServletRequest.getParameter("classname"));
           httpServletRequest.setAttribute("classname", classname);
           PageUtil pageUtil = new PageUtil(httpServletRequest);
           List<SearchModel>condition = new ArrayList<SearchModel>();
           if(!"".equals(classname)){
               SearchCondition.addCondition(condition, "classname", "like", "%"+classname+"%");
           }
           SearchCondition.addCondition(condition, "courseid", "=", model.getCourseid());
           String sorderindex = "a.courseclassid asc";
           if (!"".equals(pageUtil.getOrderindex())) {
               sorderindex = pageUtil.getOrderindex();
           }
           PageList page = classManager.getPageEduCourseClasss(condition, sorderindex, pageUtil.getStartCount(), 5);
           ArrayList datalist = page.getDatalist();
           if(datalist !=null && datalist.size()>0){
               for(int i=0;i<datalist.size();i++){
                   EduCourseClass courseClass = (EduCourseClass) datalist.get(i);
                   String status = courseClass.getStatus();
                   if(status.equals("0") || status.equals("2")){
                       courseClass.setFlagl("1");
                   }
               }
           }
        
           
           httpServletRequest.setAttribute("pagelist", page);
           httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
           
       }catch (Exception e){
           e.printStackTrace();
       }
       return actionMapping.findForward("viewclass");
   }
   
   /**
    * 批次审核通过
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-15 上午9:46:16 
   * @lastModified ; 2017-2-15 上午9:46:16 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward passClass(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
       String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
       try {
           EduCourseClass courseClass = classManager.getEduCourseClass(courseclassid);
           courseClass.setStatus("1");
           EduCourseInfo courseInfo = manager.getEduCourseInfo(courseClass.getCourseid());
           courseInfo.setStatus("1");
           courseInfo.setStartdate(courseClass.getStartdate());
           courseInfo.setEnddate(courseClass.getEnddate());
           
           classManager.updateEduCourseClass(courseClass);
           manager.updateEduCourseInfo(courseInfo);
       }catch (Exception e){
           e.printStackTrace();
       }
       return viewClass(actionMapping, actionForm, httpServletRequest, httpServletResponse);
   }
   
   /**
    * 批次审核不通过
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-15 上午9:46:16 
   * @lastModified ; 2017-2-15 上午9:46:16 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward noClass(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
       String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
       try {
           EduCourseClass courseClass = classManager.getEduCourseClass(courseclassid);
           courseClass.setStatus("3");
           EduCourseInfo courseInfo = manager.getEduCourseInfo(courseClass.getCourseid());
           courseInfo.setStatus("3");
           courseInfo.setStartdate(courseClass.getStartdate());
           courseInfo.setEnddate(courseClass.getEnddate());
           
           classManager.updateEduCourseClass(courseClass);
           manager.updateEduCourseInfo(courseInfo);
       }catch (Exception e){
           e.printStackTrace();
       }
       return viewClass(actionMapping, actionForm, httpServletRequest, httpServletResponse);
   }
   
   /**
    * 批次禁用
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-15 上午9:46:16 
   * @lastModified ; 2017-2-15 上午9:46:16 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward stopClass(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
       String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
       try {
           EduCourseClass courseClass = classManager.getEduCourseClass(courseclassid);
           courseClass.setStatus("4");
           EduCourseInfo courseInfo = manager.getEduCourseInfo(courseClass.getCourseid());
           courseInfo.setStatus("4");
           courseInfo.setStartdate(courseClass.getStartdate());
           courseInfo.setEnddate(courseClass.getEnddate());
           
           classManager.updateEduCourseClass(courseClass);
           manager.updateEduCourseInfo(courseInfo);
       }catch (Exception e){
           e.printStackTrace();
       }
       return viewClass(actionMapping, actionForm, httpServletRequest, httpServletResponse);
   }
   
   /**
    * 课程用户
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-15 上午10:56:46 
   * @lastModified ; 2017-2-15 上午10:56:46 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward viewUserMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       httpServletRequest.setAttribute("courseid", courseid);
       return actionMapping.findForward("viewusermain");
   }
   /**
    * 树型选择器
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
   public ActionForward viewUserTree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
       String objid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       //课程信息
       EduCourseInfo model = manager.getEduCourseInfo(objid);
       Integer userid = model.getSysUserInfo().getUserid();
       httpServletRequest.setAttribute("userid", userid.toString());
       httpServletRequest.setAttribute("model", model);
       //课程批次
       List<SearchModel>condition = new ArrayList<SearchModel>();
       SearchCondition.addCondition(condition, "courseid", "=", objid);
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
           pno = "0000";// 父ID
           text = cnc.getClassname();// 树节点显示名称
             
            
           url = "eduCourseInfoAction.do?method=viewUserList&courseclassid=" + cnc.getCourseclassid()+"&courseid="+cnc.getCourseid();// onclick树节点后执行
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
       String rooturl = "/sysLayoutAction.do?method=welcome";
       httpServletRequest.setAttribute("treenode", tree);
       httpServletRequest.setAttribute("rooturl", rooturl);
       httpServletRequest.setAttribute("modulename", "课程用户");
       return actionMapping.findForward("viewusertree");
   }
   /**
    * 课程文档查看
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-14 下午4:36:08 
   * @lastModified ; 2017-2-14 下午4:36:08 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward viewUserList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseUserManager userManager = (EduCourseUserManager)getBean("eduCourseUserManager");
       SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
       
       String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
       httpServletRequest.setAttribute("courseid", courseid);
       httpServletRequest.setAttribute("courseclassid", courseclassid);
       
       EduCourseInfo eduCourseInfo = manager.getEduCourseInfo(courseid);
       EduCourseClass eduCourseClass = classManager.getEduCourseClass(courseclassid);
       
       String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
       httpServletRequest.setAttribute("createdate", createdate);
       String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
       httpServletRequest.setAttribute("usertype", usertype);
       
       try {
           //课程用户
           PageUtil pageUtil = new PageUtil(httpServletRequest);
           List<SearchModel> condition = new ArrayList<SearchModel>();
           SearchCondition.addCondition(condition, "courseclassid", "=", courseclassid);
           SearchCondition.addCondition(condition, "courseid", "=", courseid);
           SearchCondition.addCondition(condition, "status", "=", "1");
           if(!"".equals(createdate)){
               SearchCondition.addCondition(condition, "createdate", "like", "%"+createdate+"%");
           }
           if(!"".equals(usertype)){
               SearchCondition.addCondition(condition, "usertype", "=", usertype);
           }
           
           PageList pageList = userManager.getPageEduCourseUsers(condition, "courseuserid asc", pageUtil.getStartCount(), pageUtil.getPageSize());
           ArrayList datalist = pageList.getDatalist();
           if(datalist !=null && datalist.size()>0){
               for(int i=0;i<datalist.size();i++){
                   EduCourseUser model = (EduCourseUser) datalist.get(i);
                   Integer userid = model.getUserid();
                   SysUserInfo sysUserInfo = userInfoManager.getSysUserInfo(userid);
                   model.setFlagl(eduCourseInfo.getTitle());//所属课程
                   model.setFlago(eduCourseClass.getClassname());//所属批次
                   model.setFlags(sysUserInfo.getUsername());//用户姓名
                   
               }
           }
           httpServletRequest.setAttribute("pagelist", pageList);
       }catch (Exception e){
       }
       return actionMapping.findForward("viewuserlist");
   }
   
   /**
    * 课程视频
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-15 下午1:32:48 
   * @lastModified ; 2017-2-15 下午1:32:48 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward viewFilmMain(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       httpServletRequest.setAttribute("courseid", courseid);
       return actionMapping.findForward("viewfilmmain");
   }
   /**
    * 树型选择器
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
   public ActionForward viewFilmTree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseColumnManager columnManager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
       String objid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       //课程信息
       EduCourseInfo model = manager.getEduCourseInfo(objid);
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
             
            
           url = "eduCourseInfoAction.do?method=viewFilmList&columnid=" + cnc.getColumnid()+"&courseid="+cnc.getCourseid();// onclick树节点后执行
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
       String rooturl = "/sysLayoutAction.do?method=welcome";
       httpServletRequest.setAttribute("treenode", tree);
       httpServletRequest.setAttribute("rooturl", rooturl);
       httpServletRequest.setAttribute("modulename", "课程视频");
       return actionMapping.findForward("viewfilmtree");
   }
   /**
    * 课程视频查看
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-14 下午4:36:08 
   * @lastModified ; 2017-2-14 下午4:36:08 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
   public ActionForward viewFilmList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
       EduCourseFilmManager filmManager = (EduCourseFilmManager)getBean("eduCourseFilmManager");
       VwhFilmInfoManager filmInfoManager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
       EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
       EduCourseColumnManager columnManager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
       
       String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
       String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
       httpServletRequest.setAttribute("courseid", courseid);
       httpServletRequest.setAttribute("columnid", columnid);
       
       String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
       httpServletRequest.setAttribute("createdate", createdate);
       
       try {
           //课程视频
           PageUtil pageUtil = new PageUtil(httpServletRequest);
           List<SearchModel> condition = new ArrayList<SearchModel>();
           SearchCondition.addCondition(condition, "coursecolumnid", "=", columnid);
           SearchCondition.addCondition(condition, "courseid", "=", courseid);
           SearchCondition.addCondition(condition, "status", "=", "1");
           if(!"".equals(createdate)){
               SearchCondition.addCondition(condition, "createdate", "like", "%"+createdate+"%");
           }
           
           PageList pageList = filmManager.getPageEduCourseFilms(condition, "orderindex desc", pageUtil.getStartCount(), pageUtil.getPageSize());
           ArrayList datalist = pageList.getDatalist();
           if(datalist !=null && datalist.size()>0){
               for(int i=0;i<datalist.size();i++){
                   EduCourseFilm model = (EduCourseFilm) datalist.get(i);
                   Integer filmid = model.getFilmid();
                   VwhFilmInfo vwhFilmInfo = filmInfoManager.getVwhFilmInfo(filmid);
                   model.setFlagl(vwhFilmInfo.getTitle());//视频名称
                   model.setFlago(vwhFilmInfo.getActor());//主讲人
               }
           }
           httpServletRequest.setAttribute("pagelist", pageList);
       }catch (Exception e){
       }
       return actionMapping.findForward("viewfilmlist");
   }
}