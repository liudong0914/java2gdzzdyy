package com.wkmk.edu.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.util.date.DateTime;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.PublicResourceBundle;
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.bo.EduCourseUserModule;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.edu.service.EduCourseUserModuleManager;
import com.wkmk.edu.web.form.EduCourseUserActionForm;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;

/**
 *<p>Description: 课程用户</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseUserAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
		SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        httpServletRequest.setAttribute("courseclassid", courseclassid);
        String courseusertype = Encode.nullToBlank(httpServletRequest.getParameter("courseusertype"));
        httpServletRequest.setAttribute("courseusertype", courseusertype);
        String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
        httpServletRequest.setAttribute("username", username);
        
        HttpSession session = httpServletRequest.getSession();
        Integer unitid = (Integer)session.getAttribute("s_unitid");
        httpServletRequest.setAttribute("unitid", unitid);
        
        PageUtil pageUtil = new PageUtil(httpServletRequest);
        String sorderindex = "courseuserid asc";
        if (!"".equals(pageUtil.getOrderindex())) {
            sorderindex = pageUtil.getOrderindex();
        }
        PageList page = manager.getEduCoursesOfPage(courseclassid, courseid, username, "", sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		return actionMapping.findForward("list");
	}
	
	/**
	 * 学员管理
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-22 上午11:46:40 
	* @lastModified ; 2017-2-22 上午11:46:40 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	* @throws Exception
	 */
	public ActionForward studentList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
            throws Exception {
        try {
            String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
            String vip = Encode.nullToBlank(httpServletRequest.getParameter("vip"));
            httpServletRequest.setAttribute("username", username);
            httpServletRequest.setAttribute("vip", vip);
            
            HttpSession session = httpServletRequest.getSession();
            String courseid = (String) session.getAttribute("courseid");
            String courseclassid = (String) session.getAttribute("courseclassid");
            
            EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
            SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
            PageUtil pageUtil = new PageUtil(httpServletRequest);
            String sorderindex = "courseuserid asc";
            if (!"".equals(pageUtil.getOrderindex())) {
                sorderindex = pageUtil.getOrderindex();
            }
            PageList pageList = manager.getEduCoursesOfPage(courseclassid, courseid, username, vip, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
            
            httpServletRequest.setAttribute("pagelist", pageList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actionMapping.findForward("studentlist");
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
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        String courseusertype = Encode.nullToBlank(httpServletRequest.getParameter("courseusertype"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
        httpServletRequest.setAttribute("courseusertype", courseusertype);
        String usertype ="3";//学员
        httpServletRequest.setAttribute("usertype", usertype);
        
        try{
            //分页与排序
            String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
            String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
            String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
            httpServletRequest.setAttribute("pageno", pageno);
            httpServletRequest.setAttribute("direction", direction);
            httpServletRequest.setAttribute("sort", sort);
            
            EduCourseUser eduCourseUser = new EduCourseUser();
            eduCourseUser.setUsertype(usertype);
            eduCourseUser.setCourseclassid(Integer.valueOf(courseclassid));
            eduCourseUser.setCourseid(Integer.valueOf(courseid));
            eduCourseUser.setStatus("1");
            eduCourseUser.setVip("0");
            httpServletRequest.setAttribute("eduCourseUser", eduCourseUser);
            httpServletRequest.setAttribute("act", "addSave");
            
            SysUserInfo model = new SysUserInfo();
            SysUserInfoDetail detail = new SysUserInfoDetail();
            model.setPhoto("man.jpg");
            model.setSex("0");
            model.setXueduan("2");
            model.setUsertype("2");
            detail.setEducation("9");
            detail.setJobtitle("9");
            detail.setCanaddcourse("0");
            httpServletRequest.setAttribute("model", model);
            httpServletRequest.setAttribute("detail", detail);
        } catch (Exception e){
            e.printStackTrace();
        }
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
	    EduCourseUserActionForm form = (EduCourseUserActionForm)actionForm;
        SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
        SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
        if (isTokenValid(httpServletRequest, true)) {
            try {
                Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
                SysUserInfo model = form.getSysUserInfo();
                //将密码加密
                String password = Encode.nullToBlank(httpServletRequest.getParameter("newpassword"));
                if (!"".equals(password)) {
                    model.setPassword(MD5.getEncryptPwd(password));
                }
                model.setUnitid(unitid);
                model.setStatus("1");
                model.setMoney(0.00F);
                manager.addSysUserInfo(model);
                
                SysUserInfoDetail detail = form.getSysUserInfoDetail();
                if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号加密存储
                    detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
                }
                detail.setUserid(model.getUserid());
                detail.setFlag("0");
                detail.setCreatedate(DateTime.getDate());
                detail.setLastlogin(detail.getCreatedate());
                detail.setLogintimes(0);
                detail.setUpdatetime(detail.getCreatedate());
                suidm.addSysUserInfoDetail(detail);
                
                EduCourseUserManager courseUserManager = (EduCourseUserManager)getBean("eduCourseUserManager");
                EduCourseUser eduCourseUser = form.getEduCourseUser();
                eduCourseUser.setUserid(model.getUserid());
                eduCourseUser.setCreatedate(DateTime.getDate());
                courseUserManager.addEduCourseUser(eduCourseUser);
                
                EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
                EduCourseClass eduCourseClass = eccm.getEduCourseClass(eduCourseUser.getCourseclassid());
                eduCourseClass.setUsercount(eduCourseClass.getUsercount() + 1);
                eccm.updateEduCourseClass(eduCourseClass);
                
                addLog(httpServletRequest,"增加了一个课程用户【" + model.getUsername() + "】信息");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	public ActionForward view(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        String courseusertype = Encode.nullToBlank(httpServletRequest.getParameter("courseusertype"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
        httpServletRequest.setAttribute("courseusertype", courseusertype);
        String usertype ="3";//学员
        httpServletRequest.setAttribute("usertype", usertype);
        
        String courseuserid = Encode.nullToBlank(httpServletRequest.getParameter("courseuserid"));
        httpServletRequest.setAttribute("courseuserid", courseuserid);
        
        
        try{
            //分页与排序
            String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
            String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
            String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
            httpServletRequest.setAttribute("pageno", pageno);
            httpServletRequest.setAttribute("direction", direction);
            httpServletRequest.setAttribute("sort", sort);
            
            EduCourseUserManager courseUserManager = (EduCourseUserManager)getBean("eduCourseUserManager");
            SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
            SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
            
            EduCourseUser eduCourseUser = courseUserManager.getEduCourseUser(courseuserid);
            httpServletRequest.setAttribute("eduCourseUser", eduCourseUser);
            httpServletRequest.setAttribute("act", "addSave");
            
            Integer userid = eduCourseUser.getUserid();
            SysUserInfo model = manager.getSysUserInfo(userid);
            SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
            if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号解密显示
                detail.setCardno(DES.getDecryptPwd(detail.getCardno()));
            }
            httpServletRequest.setAttribute("model", model);
            httpServletRequest.setAttribute("detail", detail);
        } catch (Exception e){
            e.printStackTrace();
        }
        saveToken(httpServletRequest);
        return actionMapping.findForward("view");
    }
	
	/**
     *学员，增加前
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward beforeAddStudent(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        String courseid = (String) session.getAttribute("courseid");
        String courseclassid = (String) session.getAttribute("courseclassid");
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
        String usertype ="3";//学员
        httpServletRequest.setAttribute("usertype", usertype);
        
        try{
            //分页与排序
            String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
            String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
            String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
            httpServletRequest.setAttribute("pageno", pageno);
            httpServletRequest.setAttribute("direction", direction);
            httpServletRequest.setAttribute("sort", sort);
            
            EduCourseUser eduCourseUser = new EduCourseUser();
            eduCourseUser.setUsertype(usertype);
            eduCourseUser.setCourseclassid(Integer.valueOf(courseclassid));
            eduCourseUser.setCourseid(Integer.valueOf(courseid));
            eduCourseUser.setStatus("1");
            eduCourseUser.setVip("0");
            httpServletRequest.setAttribute("eduCourseUser", eduCourseUser);
            httpServletRequest.setAttribute("act", "addSaveStudent");
            
            SysUserInfo model = new SysUserInfo();
            SysUserInfoDetail detail = new SysUserInfoDetail();
            model.setPhoto("man.jpg");
            model.setSex("0");
            model.setXueduan("2");
            model.setUsertype("2");
            detail.setEducation("9");
            detail.setJobtitle("9");
            detail.setCanaddcourse("0");
            httpServletRequest.setAttribute("model", model);
            httpServletRequest.setAttribute("detail", detail);
        } catch (Exception e){
            e.printStackTrace();
        }
        saveToken(httpServletRequest);
        return actionMapping.findForward("editstudent");
    }
    
    public ActionForward addSaveStudent(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        EduCourseUserActionForm form = (EduCourseUserActionForm)actionForm;
        SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
        SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
        if (isTokenValid(httpServletRequest, true)) {
            try {
                Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
                SysUserInfo model = form.getSysUserInfo();
                //将密码加密
                String password = Encode.nullToBlank(httpServletRequest.getParameter("newpassword"));
                if (!"".equals(password)) {
                    model.setPassword(MD5.getEncryptPwd(password));
                }
                model.setUnitid(unitid);
                model.setStatus("1");
                model.setMoney(0.00F);
                manager.addSysUserInfo(model);
                
                SysUserInfoDetail detail = form.getSysUserInfoDetail();
                if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号加密存储
                    detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
                }
                detail.setUserid(model.getUserid());
                detail.setFlag("0");
                detail.setCreatedate(DateTime.getDate());
                detail.setLastlogin(detail.getCreatedate());
                detail.setLogintimes(0);
                detail.setUpdatetime(detail.getCreatedate());
                suidm.addSysUserInfoDetail(detail);
                
                EduCourseUserManager courseUserManager = (EduCourseUserManager)getBean("eduCourseUserManager");
                EduCourseUser eduCourseUser = form.getEduCourseUser();
                eduCourseUser.setUserid(model.getUserid());
                eduCourseUser.setCreatedate(DateTime.getDate());
                courseUserManager.addEduCourseUser(eduCourseUser);
                
                EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
                EduCourseClass eduCourseClass = eccm.getEduCourseClass(eduCourseUser.getCourseclassid());
                eduCourseClass.setUsercount(eduCourseClass.getUsercount() + 1);
                eccm.updateEduCourseClass(eduCourseClass);
                
                addLog(httpServletRequest,"增加了一个课程用户【" + model.getUsername() + "】信息");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return studentList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
		EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			EduCourseUser model = manager.getEduCourseUser(objid);
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
		EduCourseUserActionForm form = (EduCourseUserActionForm)actionForm;
		EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseUser model = form.getEduCourseUser();
				manager.updateEduCourseUser(model);
				addLog(httpServletRequest,"修改了一个课程用户");
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
		EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		  EduCourseUser eduCourseUser = null;		for (int i = 0; i < checkids.length; i++) {
		    if(i == 0){
                eduCourseUser = manager.getEduCourseUser(checkids[i]);
            }
			manager.delEduCourseUser(checkids[i]);
		}
		EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
        EduCourseClass eduCourseClass = eccm.getEduCourseClass(eduCourseUser.getCourseclassid());
        eduCourseClass.setUsercount(eduCourseClass.getUsercount() - checkids.length);
        eccm.updateEduCourseClass(eduCourseClass);
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	public ActionForward updateUserVipOne(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
		String checkid = Encode.nullToBlank(httpServletRequest.getParameter("checkid"));
		httpServletRequest.setAttribute("checkid", checkid);
		 return actionMapping.findForward("setvipone");
	}
	/**
	 * 批量修改用户身份
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2017-3-31 下午5:21:18
	 */
	public ActionForward updateUserVip(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
//		String[] checkids1 = httpServletRequest.getParameterValues("checkid");
		String checkid = Encode.nullToBlank(httpServletRequest.getParameter("checkid"));
		String startdate = Encode.nullToBlank(httpServletRequest.getParameter("startdate"));
		String enddate = Encode.nullToBlank(httpServletRequest.getParameter("enddate"));
		
		String[] checkids = checkid.split(",");
		
		  EduCourseUser eduCourseUser = null;
		for (int i = 0; i < checkids.length; i++) {
                eduCourseUser = manager.getEduCourseUser(checkids[i]);
                eduCourseUser.setVip("1");
                eduCourseUser.setStartdate(startdate);
                eduCourseUser.setEnddate(enddate);
                long dateCal = DateTime.getDateCal(enddate, startdate);
                eduCourseUser.setValiditytime(String.valueOf(dateCal));
                manager.updateEduCourseUser(eduCourseUser);
		}
		httpServletRequest.setAttribute("startdate", startdate);
		httpServletRequest.setAttribute("enddate", enddate);
		return actionMapping.findForward("setviponesuccess");
	}
	
	public ActionForward delBatchRecordStudent(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
        EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
        String[] checkids = httpServletRequest.getParameterValues("checkid");

        EduCourseUser eduCourseUser = null;
        for (int i = 0; i < checkids.length; i++) {
        	if(i == 0){
        		eduCourseUser = manager.getEduCourseUser(checkids[i]);
        	}
            manager.delEduCourseUser(checkids[i]);
        }
        
        EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
        EduCourseClass eduCourseClass = eccm.getEduCourseClass(eduCourseUser.getCourseclassid());
        eduCourseClass.setUsercount(eduCourseClass.getUsercount() - checkids.length);
        eccm.updateEduCourseClass(eduCourseClass);
        
        return studentList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
//        String courseusertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
        String courseusertype ="3";
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
        httpServletRequest.setAttribute("courseusertype", courseusertype);
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
        String courseusertype = Encode.nullToBlank(httpServletRequest.getParameter("courseusertype"));
        httpServletRequest.setAttribute("courseusertype", courseusertype);
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
              
             
            url = "eduCourseUserAction.do?method=list&courseclassid=" + cnc.getCourseclassid()+"&courseid="+cnc.getCourseid()+"&courseusertype="+courseusertype;// onclick树节点后执行
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
        String rooturl = "/sysLayoutAction.do?method=welcome";
        httpServletRequest.setAttribute("treenode", tree);
        httpServletRequest.setAttribute("rooturl", rooturl);
        httpServletRequest.setAttribute("modulename", "课程批次");
        return actionMapping.findForward("tree");
    }
    
    /**
     * 导入学员
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-2-21 下午1:55:48 
    * @lastModified ; 2017-2-21 下午1:55:48 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
     */
    public ActionForward selectUser(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        try {
            HttpSession session = httpServletRequest.getSession();
            Integer unitid = (Integer) session.getAttribute("s_unitid");
            String courseid = (String) session.getAttribute("courseid");
            String courseclassid = (String) session.getAttribute("courseclassid");
            
            String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
            SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
//            List list = manager.getSysUserInfosOfEcu(unitid.toString(), username, "2",courseid,courseclassid);
            PageUtil pageUtil = new PageUtil(httpServletRequest);
            String sorderindex = "usertype asc,a.userid asc";
            if (!"".equals(pageUtil.getOrderindex())) {
                sorderindex = pageUtil.getOrderindex();
            }
            PageList page = manager.getSysUserInfosOfEcuOfPage(unitid.toString(), username, "", courseid, courseclassid, sorderindex,  pageUtil.getStartCount(), pageUtil.getPageSize());
            
            httpServletRequest.setAttribute("username", username);
            httpServletRequest.setAttribute("pagelist", page);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return actionMapping.findForward("selectuser");
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
            String[] checkids = httpServletRequest.getParameterValues("checkid");
            try{
                if(checkids != null && checkids.length > 0){
                    SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
                    EduCourseUserManager userManager = (EduCourseUserManager) getBean("eduCourseUserManager");
                    
                    HttpSession session = httpServletRequest.getSession();
                    String courseid = (String) session.getAttribute("courseid");
                    String courseclassid = (String) session.getAttribute("courseclassid");
                    
                    SysUserInfo model = null;
                    EduCourseUser eduCourseUser = new EduCourseUser();
                    for(int i=0, size=checkids.length; i<size; i++){
                        model = manager.getSysUserInfo(checkids[i]);
                        eduCourseUser.setCourseclassid(Integer.valueOf(courseclassid));
                        eduCourseUser.setCourseid(Integer.valueOf(courseid));
                        eduCourseUser.setUserid(model.getUserid());
                        eduCourseUser.setStatus("1");
                        eduCourseUser.setCreatedate(DateTime.getDate());
                        eduCourseUser.setUsertype("3");
                        eduCourseUser.setVip("0");
                        userManager.addEduCourseUser(eduCourseUser);
                    }
                    
                    EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
                    EduCourseClass eduCourseClass = eccm.getEduCourseClass(eduCourseUser.getCourseclassid());
                    eduCourseClass.setUsercount(eduCourseClass.getUsercount() + checkids.length);
                    eccm.updateEduCourseClass(eduCourseClass);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        return actionMapping.findForward("deal");
    }
    
    /**
     * 查看用户
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-2-22 上午9:57:02 
    * @lastModified ; 2017-2-22 上午9:57:02 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
     */
    public ActionForward viewStudent(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
        EduCourseUserManager userManager = (EduCourseUserManager) getBean("eduCourseUserManager");
        SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
        String courseuserid = Encode.nullToBlank(httpServletRequest.getParameter("courseuserid"));
        
        try{
            HttpSession session = httpServletRequest.getSession();
            String courseid = (String) session.getAttribute("courseid");
            String courseclassid = (String) session.getAttribute("courseclassid");
            httpServletRequest.setAttribute("courseid", courseid);
            httpServletRequest.setAttribute("courseclassid", courseclassid);
            String usertype ="3";//学员
            httpServletRequest.setAttribute("usertype", usertype);
            
            //分页与排序
            String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
            String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
            String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
            httpServletRequest.setAttribute("pageno", pageno);
            httpServletRequest.setAttribute("direction", direction);
            httpServletRequest.setAttribute("sort", sort);
            
            EduCourseUser eduCourseUser = userManager.getEduCourseUser(courseuserid);
            httpServletRequest.setAttribute("eduCourseUser", eduCourseUser);
            
            Integer userid = eduCourseUser.getUserid();
            SysUserInfo model = manager.getSysUserInfo(userid);
            SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
            if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号解密显示
                detail.setCardno(DES.getDecryptPwd(detail.getCardno()));
            }
            httpServletRequest.setAttribute("model", model);
            httpServletRequest.setAttribute("detail", detail);
        }catch (Exception e){
            e.printStackTrace();
        }
        return actionMapping.findForward("viewstudent");
    }
    
    public ActionForward userList(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        try {
            String unitid = Encode.nullToBlank(httpServletRequest.getParameter("unitid"));
            String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
            String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
            String courseusertype = Encode.nullToBlank(httpServletRequest.getParameter("courseusertype"));
            httpServletRequest.setAttribute("unitid", unitid);
            httpServletRequest.setAttribute("courseid", courseid);
            httpServletRequest.setAttribute("courseclassid", courseclassid);
            httpServletRequest.setAttribute("courseusertype", courseusertype);
            
            String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
            SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
            PageUtil pageUtil = new PageUtil(httpServletRequest);
            String sorderindex = "usertype asc,a.userid asc";
            if (!"".equals(pageUtil.getOrderindex())) {
                sorderindex = pageUtil.getOrderindex();
            }
            PageList page = manager.getSysUserInfosOfEcuOfPage(unitid.toString(), username, "", courseid, courseclassid, sorderindex,  pageUtil.getStartCount(), pageUtil.getPageSize());
            
            httpServletRequest.setAttribute("username", username);
            httpServletRequest.setAttribute("pagelist", page);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return actionMapping.findForward("importuser");
    }
    
    public ActionForward importDeal(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String[] checkids = httpServletRequest.getParameterValues("checkid");
        try{
            if(checkids != null && checkids.length > 0){
                SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
                EduCourseUserManager userManager = (EduCourseUserManager) getBean("eduCourseUserManager");
                
                String unitid = Encode.nullToBlank(httpServletRequest.getParameter("unitid"));
                String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
                String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
                String courseusertype = Encode.nullToBlank(httpServletRequest.getParameter("courseusertype"));
                httpServletRequest.setAttribute("unitid", unitid);
                httpServletRequest.setAttribute("courseid", courseid);
                httpServletRequest.setAttribute("courseclassid", courseclassid);
                httpServletRequest.setAttribute("courseusertype", courseusertype);
                
                SysUserInfo model = null;
                EduCourseUser eduCourseUser = new EduCourseUser();
                for(int i=0, size=checkids.length; i<size; i++){
                    model = manager.getSysUserInfo(checkids[i]);
                    eduCourseUser.setCourseclassid(Integer.valueOf(courseclassid));
                    eduCourseUser.setCourseid(Integer.valueOf(courseid));
                    eduCourseUser.setUserid(model.getUserid());
                    eduCourseUser.setStatus("1");
                    eduCourseUser.setCreatedate(DateTime.getDate());
                    eduCourseUser.setUsertype("3");
                    eduCourseUser.setVip("0");
                    userManager.addEduCourseUser(eduCourseUser);
                }
                
                EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
                EduCourseClass eduCourseClass = eccm.getEduCourseClass(eduCourseUser.getCourseclassid());
                eduCourseClass.setUsercount(eduCourseClass.getUsercount() + checkids.length);
                eccm.updateEduCourseClass(eduCourseClass);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    return actionMapping.findForward("deal");
       // return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
}
    //==========================================================助教管理=================================================================================
    /**
     * 助教管理
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-2-22 上午11:46:58 
    * @lastModified ; 2017-2-22 上午11:46:58 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
    * @throws Exception
     */
    public ActionForward teacherList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
            throws Exception {
        try {
            String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
            String vip = Encode.nullToBlank(httpServletRequest.getParameter("vip"));
            httpServletRequest.setAttribute("username", username);
            httpServletRequest.setAttribute("vip", vip);
            
            HttpSession session = httpServletRequest.getSession();
            String courseid = (String) session.getAttribute("courseid");
            String courseclassid = (String) session.getAttribute("courseclassid");
            
            EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
            SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
            PageUtil pageUtil = new PageUtil(httpServletRequest);
            String sorderindex = "courseuserid asc";
            if (!"".equals(pageUtil.getOrderindex())) {
                sorderindex = pageUtil.getOrderindex();
            }
            PageList pageList = manager.getEduCoursesOfPage2(courseclassid, courseid, username, vip, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
            
            httpServletRequest.setAttribute("pagelist", pageList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actionMapping.findForward("teacherlist");
    }
    
   /**
    * 新增助教前
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-3-6 下午2:05:16 
   * @lastModified ; 2017-3-6 下午2:05:16 
   * @version ; 1.0 
   * @param actionMapping
   * @param actionForm
   * @param httpServletRequest
   * @param httpServletResponse
   * @return
    */
    public ActionForward beforeAddTeacher(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        String courseid = (String) session.getAttribute("courseid");
        String courseclassid = (String) session.getAttribute("courseclassid");
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
        String usertype ="2";//学员
        httpServletRequest.setAttribute("usertype", usertype);
        
        try{
            //分页与排序
            String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
            String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
            String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
            httpServletRequest.setAttribute("pageno", pageno);
            httpServletRequest.setAttribute("direction", direction);
            httpServletRequest.setAttribute("sort", sort);
            
            EduCourseUser eduCourseUser = new EduCourseUser();
            eduCourseUser.setUsertype(usertype);
            eduCourseUser.setCourseclassid(Integer.valueOf(courseclassid));
            eduCourseUser.setCourseid(Integer.valueOf(courseid));
            eduCourseUser.setStatus("1");
            eduCourseUser.setVip("0");
            httpServletRequest.setAttribute("eduCourseUser", eduCourseUser);
            httpServletRequest.setAttribute("act", "addSaveTeacher");
            
//            EduCourseUserModule module = new EduCourseUserModule();
//            module.setModuletype("1");//只读功能
//            module.setCourseclassid(Integer.valueOf(courseclassid));
//            module.setCourseid(Integer.valueOf(courseid));
//            httpServletRequest.setAttribute("module", module);
            
            SysUserInfo model = new SysUserInfo();
            SysUserInfoDetail detail = new SysUserInfoDetail();
            model.setPhoto("man.jpg");
            model.setSex("0");
            model.setXueduan("2");
            model.setUsertype("2");
            detail.setEducation("9");
            detail.setJobtitle("9");
            detail.setCanaddcourse("0");
            httpServletRequest.setAttribute("model", model);
            httpServletRequest.setAttribute("detail", detail);
        } catch (Exception e){
            e.printStackTrace();
        }
        saveToken(httpServletRequest);
        return actionMapping.findForward("editteacher");
    }
    
    public ActionForward addSaveTeacher(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        EduCourseUserActionForm form = (EduCourseUserActionForm)actionForm;
        SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
        SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
        
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        httpServletRequest.setAttribute("courseclassid", courseclassid);
        
        if (isTokenValid(httpServletRequest, true)) {
            try {
                Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
                String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
                SysUserInfo model =null;
                
                if(userid !=null && !"".equals(userid)){
                    //已存在用户
                     model = manager.getSysUserInfo(userid);
                }else{
                    //新增的用户
                        model = form.getSysUserInfo();
                        //将密码加密
                        String password = Encode.nullToBlank(httpServletRequest.getParameter("newpassword"));
                        if (!"".equals(password)) {
                            model.setPassword(MD5.getEncryptPwd(password));
                        }else {
                        	model.setPassword(MD5.getEncryptPwd("123456"));
						}
                        model.setNickname(model.getUsername());
                        model.setUnitid(unitid);
                        model.setStatus("1");
                        model.setMoney(0.00F);
                        model.setAuthentication("0");
                        model.setPaypassword("0");
                        model.setUsertype("1");//教师
                        model.setXueduan("2");
                        manager.addSysUserInfo(model);
                        
                        SysUserInfoDetail detail = form.getSysUserInfoDetail();
                        if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号加密存储
                            detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
                        }
                        detail.setUserid(model.getUserid());
                        detail.setFlag("0");
                        detail.setCreatedate(DateTime.getDate());
                        detail.setLastlogin(detail.getCreatedate());
                        detail.setLogintimes(0);
                        detail.setUpdatetime(detail.getCreatedate());
                        detail.setBirthday("2008-08-08");
                        detail.setCardtype("1");
                        detail.setNation("1");
                        detail.setProvince("110000");
                        detail.setCity("110100");
                        detail.setCounty("110101");
                        detail.setPostcode("100000");
                        detail.setJobtitle("9");
                        detail.setCanaddcourse("0");
                        suidm.addSysUserInfoDetail(detail);
                        
                        userid = model.getUserid().toString();
                }
                
                //助教
                EduCourseUserManager courseUserManager = (EduCourseUserManager)getBean("eduCourseUserManager");
                EduCourseUser eduCourseUser = form.getEduCourseUser();
                List list = courseUserManager.getEduCourseUserByUsertype(Integer.valueOf(courseid), Integer.valueOf(courseclassid), Integer.valueOf(userid), "3");
                //判断当前用户是否以学员身份加入了该批次，若是，则修改身份为助教
                if(list !=null && list.size()>0){
                    EduCourseUser eduCourseUser2 = (EduCourseUser) list.get(0);
                    eduCourseUser2.setUserid(model.getUserid());
                    eduCourseUser2.setUsertype("2");
                    eduCourseUser2.setVip(eduCourseUser.getVip());
                    eduCourseUser2.setCreatedate(DateTime.getDate());
                    courseUserManager.updateEduCourseUser(eduCourseUser2);
                }else{
                    eduCourseUser.setUserid(model.getUserid());
                    eduCourseUser.setCreatedate(DateTime.getDate());
                    courseUserManager.addEduCourseUser(eduCourseUser);
                }
                
                //助教授权
                EduCourseUserModuleManager courseUserModuleManager = (EduCourseUserModuleManager)getBean("eduCourseUserModuleManager");
                EduCourseUserModule module =new EduCourseUserModule();
                String [] moduleids=httpServletRequest.getParameterValues("moduleid");//模块id
                String moduletype0 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype0"));
                String moduletype1 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype1"));
                String moduletype2 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype2"));
                String moduletype3 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype3"));
                String moduletype4 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype4"));
                String moduletype5 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype5"));
                Map map = new HashMap();
                map.put(0, moduletype0);
                map.put(1, moduletype1);
                map.put(2, moduletype2);
                map.put(3, moduletype3);
                map.put(4, moduletype4);
                map.put(5, moduletype5);
                if(moduleids != null){
                    for (int i = 0; i < moduleids.length; i++) {
                        String moduleid=moduleids[i];
                        module.setCourseid(Integer.valueOf(courseid));
                        module.setCourseclassid(Integer.valueOf(courseclassid));
                        module.setUserid(model.getUserid());
                        module.setModuleid(moduleid);
                        module.setModuletype((String)map.get(i));
                        courseUserModuleManager.addEduCourseUserModule(module);
                    }
                }
                
                addLog(httpServletRequest,"增加了一个课程用户【" + model.getUsername() + "】信息");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return teacherList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }
    
    public ActionForward delBatchRecordTeacher(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
        EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
        EduCourseUserModuleManager courseUserModuleManager = (EduCourseUserModuleManager)getBean("eduCourseUserModuleManager");
        String[] checkids = httpServletRequest.getParameterValues("checkid");

        EduCourseUser eduCourseUser = null;
        for (int i = 0; i < checkids.length; i++) {
            eduCourseUser = manager.getEduCourseUser(checkids[i]);
            List list = courseUserModuleManager.getEduCourseUserModules2(eduCourseUser.getUserid().toString(), eduCourseUser.getCourseid().toString(), eduCourseUser.getCourseclassid().toString());
            if(list !=null && list.size()>0){
                for(int j=0;j<list.size();j++){
                    EduCourseUserModule module = (EduCourseUserModule) list.get(j);
                    courseUserModuleManager.delEduCourseUserModule(module);
                }
            }
            manager.delEduCourseUser(checkids[i]);
        }
        return teacherList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }
    
    /**
     *修改前
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward beforeUpdateTeacher(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
        EduCourseUserModuleManager courseUserModuleManager = (EduCourseUserModuleManager)getBean("eduCourseUserModuleManager");
        SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
        SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
        String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
        try {
            //分页与排序
            String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
            String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
            String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
            httpServletRequest.setAttribute("pageno", pageno);
            httpServletRequest.setAttribute("direction", direction);
            httpServletRequest.setAttribute("sort", sort);
            
            EduCourseUser model = manager.getEduCourseUser(objid);
            httpServletRequest.setAttribute("act", "updateSaveTeacher");
            httpServletRequest.setAttribute("model", model);
            
            SysUserInfo sysUserInfo = userInfoManager.getSysUserInfo(model.getUserid());
            httpServletRequest.setAttribute("userinfo", sysUserInfo);
            SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
            httpServletRequest.setAttribute("detail", detail);
            
            List list = courseUserModuleManager.getEduCourseUserModules2(model.getUserid().toString(), model.getCourseid().toString(), model.getCourseclassid().toString());
            Map moduleidMap = new HashMap();
            if(list !=null && list.size()>0){
                for(int i=0;i<list.size();i++){
                    EduCourseUserModule module = (EduCourseUserModule) list.get(i);
                    moduleidMap.put(module.getModuleid(), module.getModuletype());
                }
            }
            httpServletRequest.setAttribute("moduleidMap", moduleidMap);
            
        }catch (Exception e){
            e.printStackTrace();
        }

        saveToken(httpServletRequest);
        return actionMapping.findForward("updateteacher");
    }

    /**
     *修改时保存
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     * @throws Exception 
     */
    public ActionForward updateSaveTeacher(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
        EduCourseUserActionForm form = (EduCourseUserActionForm)actionForm;
        EduCourseUserManager manager = (EduCourseUserManager)getBean("eduCourseUserManager");
        EduCourseUserModuleManager courseUserModuleManager = (EduCourseUserModuleManager)getBean("eduCourseUserModuleManager");
        if (isTokenValid(httpServletRequest, true)) {
            try {
                EduCourseUser model = form.getEduCourseUser();
                manager.updateEduCourseUser(model);
                
                String [] moduleids=httpServletRequest.getParameterValues("moduleid");//模块id
                
                List list = courseUserModuleManager.getEduCourseUserModules2(model.getUserid().toString(), model.getCourseid().toString(), model.getCourseclassid().toString());
                if(list !=null && list.size()>0){
                    for(int j=0;j<list.size();j++){
                        //已经存在，删除
                       EduCourseUserModule module =  (EduCourseUserModule) list.get(j);
                       courseUserModuleManager.delEduCourseUserModule(module);
                    }
                }
                
                //助教授权
                EduCourseUserModule module =new EduCourseUserModule();
                String moduletype0 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype0"));
                String moduletype1 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype1"));
                String moduletype2 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype2"));
                String moduletype3 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype3"));
                String moduletype4 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype4"));
                String moduletype5 = Encode.nullToBlank(httpServletRequest.getParameter("moduletype5"));
                Map map = new HashMap();
                map.put(0, moduletype0);
                map.put(1, moduletype1);
                map.put(2, moduletype2);
                map.put(3, moduletype3);
                map.put(4, moduletype4);
                map.put(5, moduletype5);
                if(moduleids != null){
                    for (int i = 0; i < moduleids.length; i++) {
                        String moduleid=moduleids[i];
                        module.setCourseid(model.getCourseid());
                        module.setCourseclassid(model.getCourseclassid());
                        module.setUserid(model.getUserid());
                        module.setModuleid(moduleid);
                        module.setModuletype((String)map.get(i));
                        courseUserModuleManager.addEduCourseUserModule(module);
                    }
                }
                
                addLog(httpServletRequest,"修改了一个课程用户");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return teacherList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }
    
    /*
     * vip授权
     */
    public ActionForward setVipUserList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String userids=Encode.nullToBlank(httpServletRequest.getParameter("userids"));
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		SearchCondition.addCondition(condition, "status", "=", "1");//已审核
		SearchCondition.addCondition(condition, "loginname", "<>", "~admin~");
		if(!"".equals(loginname)){
			SearchCondition.addCondition(condition, "loginname", "like", "%" + loginname + "%");
		}
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		}
		if(!"".equals(sex) && !"-1".equals(sex)){
			SearchCondition.addCondition(condition, "sex", "=", sex);
		}
		if(!"".equals(usertype) && !"-1".equals(usertype)){
			SearchCondition.addCondition(condition, "usertype", "=", usertype);
		}
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "username asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("userids", userids);
		return actionMapping.findForward("userlist");
	}
    
    /*
     * 选择权限课程批次
     */
    public ActionForward beforeSetVip(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	String userids=Encode.nullToBlank(httpServletRequest.getParameter("userids"));
    	httpServletRequest.setAttribute("userids", userids);
    	//查询所有的课程批次
    	EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List courseList = manager.getEduCourseInfos(condition, "courseno asc", 0);
		Map courseMap = new HashMap();
		if(courseList !=null && courseList.size()>0){
			for(int k=0;k<courseList.size();k++){
				EduCourseInfo model = (EduCourseInfo) courseList.get(k);
				courseMap.put(model.getCourseid(), model.getTitle());
			}
		}
		
		EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
		condition.clear();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List classList = classManager.getEduCourseClasss(condition, "courseclassid asc", 0);
		if(classList !=null && classList.size()>0){
			for (int i = 0; i < classList.size(); i++) {
				EduCourseClass model = (EduCourseClass) classList.get(i);
				String courserTitle = (String) courseMap.get(model.getCourseid());
				model.setClassname(courserTitle+"——"+model.getClassname());
			}
		}
    	httpServletRequest.setAttribute("classList", classList);
    	return actionMapping.findForward("beforesetvip");
    }
    
    /*
     * 保存授权
     */
    public ActionForward setVip(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    	EduCourseUserManager ecum = (EduCourseUserManager)getBean("eduCourseUserManager");
    	
    	//查询所有已存在vip
    	List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "vip", "=", "1");
		SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
		List eduCourseUsers = ecum.getEduCourseUsers(condition, "courseuserid asc", 0);//所有的vip，有效期必须有效
		List checkString = new ArrayList();
		if (eduCourseUsers !=null && eduCourseUsers.size()>0) {
			for (int i = 0; i < eduCourseUsers.size(); i++) {
				EduCourseUser model = (EduCourseUser) eduCourseUsers.get(i);
				checkString.add(model.getCourseclassid()+"_"+model.getCourseid()+"_"+model.getUserid());//2_3_2
			}
		}
    	
    	String userids=Encode.nullToBlank(httpServletRequest.getParameter("userids"));//所选用户
    	String checkids = Encode.nullToBlank(httpServletRequest.getParameter("checkid"));//所选课程批次
    	 if(!"".equals(userids)&&!"".equals(checkids)){
//    		 userids = userids.substring(0, userids.length() - 1);
    		 String[] useridss = userids.split(",");//用户id数组
    		 checkids = checkids.substring(0, checkids.length() - 1);
    		 String[] classCourseids = checkids.split(",");//课程id_批次id 数组
    		 for (int i = 0; i < useridss.length; i++) {
				String userid = useridss[i];
				for (int j = 0; j < classCourseids.length; j++) {
					String a = classCourseids[j];
					String[] b = a.split("_");
					String c=b[1]+"_"+b[0]+"_"+userid;
					if(!checkString.contains(c)){
						EduCourseUser courseUser = new EduCourseUser();
	            		courseUser.setCourseclassid(Integer.valueOf(b[1]));
	            		courseUser.setCourseid(Integer.valueOf(b[0]));
	            		courseUser.setUserid(Integer.valueOf(userid));
	            		courseUser.setStatus("1");
	            		courseUser.setCreatedate(DateTime.getDate());
	            		courseUser.setUsertype("3");
	            		courseUser.setVip("1");
	            		courseUser.setStartdate(DateTime.getDate());
	            		//vip结束时间,当前时间+一年
	            		int vip_time =  Integer.valueOf(PublicResourceBundle.getProperty("system","vip.time")).intValue();//默认1年
	            		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	              		Calendar now = Calendar.getInstance();  
	                      now.setTime(new Date());  
	                      now.set(Calendar.DATE, now.get(Calendar.DATE) + vip_time); 
	                      Date nowDate = now.getTime();
	            		courseUser.setEnddate(format.format(nowDate));
	            		courseUser.setValiditytime(vip_time+"");//有效期
	            		ecum.addEduCourseUser(courseUser);
					}
				}
			}
    	 }
    	return actionMapping.findForward("setvipsuccess");
    }
    
}