package com.wkmk.skin.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
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
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseCollect;
import com.wkmk.edu.bo.EduCourseColumn;
import com.wkmk.edu.bo.EduCourseComment;
import com.wkmk.edu.bo.EduCourseFile;
import com.wkmk.edu.bo.EduCourseFileColumn;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseCollectManager;
import com.wkmk.edu.service.EduCourseColumnManager;
import com.wkmk.edu.service.EduCourseCommentManager;
import com.wkmk.edu.service.EduCourseFileColumnManager;
import com.wkmk.edu.service.EduCourseFileManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.edu.service.EduCourseUserModuleManager;
import com.wkmk.edu.web.form.EduCourseInfoActionForm;
import com.wkmk.sys.bo.SysMessageUser;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.bo.SysUserPayTrade;
import com.wkmk.sys.service.SysMessageUserManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.sys.service.SysUserPayTradeManager;
import com.wkmk.util.common.IpUtil;

/**
 * <p>Description: 课程个人中心模板</p>
 * @version 1.0
 */
public class CoursePersonalCenterAction extends BaseAction {

	/**
	 * 个人中心-首页
	 */
	public ActionForward index(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		String mappingurl = "/error.html";
		try {
	        HttpSession session = httpServletRequest.getSession();
	        SysUserInfo model = (SysUserInfo) session.getAttribute("s_sysuserinfo");
	        if("2".equals(model.getUsertype())){
	        	String url = "/courseCenter.do?method=courseStudy&mark=3";
	        	httpServletRequest.getRequestDispatcher(url).forward(httpServletRequest, httpServletResponse);
	    		return null;
	        }else if("1".equals(model.getUsertype())){
	        	//判断用户是否有“课程管理”模块，专家教师有，助教有，其他老师没有
	        	String s_hascoursemanager = "0";
	        	SysUserInfoDetail detail = (SysUserInfoDetail) session.getAttribute("s_sysuserinfodetail");
	        	if("1".equals(detail.getCanaddcourse())){
	        		s_hascoursemanager = "1";
	        	}else {
					EduCourseUserModuleManager ecumm = (EduCourseUserModuleManager) getBean("eduCourseUserModuleManager");
					List usermoduleList = ecumm.getEduCourseUserModules(model.getUserid().toString(), null);
					if(usermoduleList != null && usermoduleList.size() > 0){
						s_hascoursemanager = "1";
					}
				}
	        	session.setAttribute("s_hascoursemanager", s_hascoursemanager);
	        	
	        	if("1".equals(s_hascoursemanager)){
	        		String url = "/courseCenter.do?method=courseList&mark=2";
		        	httpServletRequest.getRequestDispatcher(url).forward(httpServletRequest, httpServletResponse);
		    		return null;
	        	}else {
	        		String url = "/courseCenter.do?method=courseStudy&mark=3";
		        	httpServletRequest.getRequestDispatcher(url).forward(httpServletRequest, httpServletResponse);
		    		return null;
				}
	        }else if("0".equals(model.getUsertype())){
	        	httpServletResponse.sendRedirect("/main.action");
                return null;
			}else {
				String url = "/courseCenter.do?method=courseStudy&mark=3";
	        	httpServletRequest.getRequestDispatcher(url).forward(httpServletRequest, httpServletResponse);
	    		return null;
			}
	        
			//mappingurl = "/skin/course/jsp/index.jsp";
		} catch (Exception e) {
			mappingurl = "/error.html";
		}

		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}

	/**
	 * 创建课程
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-14 下午3:10:53 
	* @lastModified ; 2017-2-14 下午3:10:53 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward createCourse(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
	    String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        if("".equals(mark)){
            mark="1";
        }
        httpServletRequest.setAttribute("mark", mark);
        
        EduCourseInfo courseInfo = new EduCourseInfo();
        httpServletRequest.setAttribute("courseInfo", courseInfo);
        httpServletRequest.setAttribute("act", "saveCourse");
        saveToken(httpServletRequest);
        
	    return actionMapping.findForward("createcourse");
	}
	
	/**
	 * 保存课程基本信息
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-15 下午5:13:09 
	* @lastModified ; 2017-2-15 下午5:13:09 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward saveCourse(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        EduCourseInfoActionForm form = (EduCourseInfoActionForm)actionForm;
        if (isTokenValid(httpServletRequest, true)) {
            try {
            	EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
                EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
                EduCourseUserManager userManager = (EduCourseUserManager)getBean("eduCourseUserManager");
                 
                HttpSession session = httpServletRequest.getSession();
                Integer unitid = (Integer)session.getAttribute("s_unitid");
                SysUserInfo sysUserInfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
                
                EduCourseInfo model = form.getEduCourseInfo();
                //model.setCourseno(DateTime.getDate("yyyyMMddHHmmss"));//从页面获取，排序用
                model.setCreatedate(DateTime.getDate());
                model.setSysUserInfo(sysUserInfo);
                model.setUnitid(unitid);
                model.setStatus("0");//状态为私有
                model.setHits(0);
                model.setCollects(0);
                model.setStudys(0);
                model.setScore(0F);
                model.setScoreusers(0);
                manager.addEduCourseInfo(model);
                addLog(httpServletRequest,"增加了一个课程信息【" + model.getTitle() + "】");
                
                //默认创建一个批次
                EduCourseClass courseClass = new EduCourseClass();
                courseClass.setClassname("第一批次");
                courseClass.setCourseid(model.getCourseid());
                courseClass.setStatus("0");
                courseClass.setCreatedate(DateTime.getDate());
                courseClass.setStartdate(model.getStartdate());
                courseClass.setEnddate(model.getEnddate());
                courseClass.setUsercount(0);//只记录学员数量
                classManager.addEduCourseClass(courseClass);
                
                //默认将自己加入这个批次
                EduCourseUser courseUser = new EduCourseUser();
                courseUser.setCourseclassid(courseClass.getCourseclassid());
                courseUser.setCourseid(model.getCourseid());
                courseUser.setUserid(sysUserInfo.getUserid());
                courseUser.setStatus("1");
                courseUser.setCreatedate(DateTime.getDate());
                courseUser.setUsertype("1");
                courseUser.setVip("0");
                userManager.addEduCourseUser(courseUser);
                
                String url = "/courseManager.do?method=index&tag=2&courseid=" + model.getCourseid() + "&courseclassid=" + courseClass.getCourseclassid();
                httpServletResponse.sendRedirect(url);
                return null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return createCourse(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

	
	/**
	 * 检测课程编号是否存在
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-15 下午4:45:07 
	* @lastModified ; 2017-2-15 下午4:45:07 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward checkcourseno(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String courseno = Encode.nullToBlank(httpServletRequest.getParameter("courseno"));
        
        EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "courseno", "=", courseno);
        
        List lst = manager.getEduCourseInfos(condition, "", 0);

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
	
	/**
	 * 课程学习
	 */
	public ActionForward courseStudy(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
		    String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		    httpServletRequest.setAttribute("mark", mark);
		    
		    Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		    
		    //教师，助教，学生，都可以学习课程，但必须以学生身份参与课程学习【以课程班（批次）为单元进行查询展示】
		    EduCourseUserManager manager = (EduCourseUserManager) getBean("eduCourseUserManager");
		    PageUtil pageUtil = new PageUtil(httpServletRequest);
		    PageList pageList = manager.getPageEduCourses(userid, "3", pageUtil.getStartCount(), 5);
		    httpServletRequest.setAttribute("pagelist", pageList);
		    
		    //根据当前页课程班数量，获取对应助教名称
		    Map usertitleMap = new HashMap();
		    List list = pageList.getDatalist();
		    if(list != null && list.size() > 0){
		    	StringBuffer ids = new StringBuffer();
		    	Object[] obj = null;
			    EduCourseClass ecc = null;
			    for(int i=0, size=list.size(); i<size; i++){
			    	obj = (Object[]) list.get(i);
			    	ecc = (EduCourseClass) obj[1];
			    	ids.append(",").append(ecc.getCourseclassid());
			    }
			    
			    List userList = manager.getEduCourseSysUserInfos(ids.substring(1), "2");
			    if(userList != null && userList.size() > 0){
			    	Object[] objects = null;
			    	SysUserInfo sui = null;
			    	EduCourseUser ecu = null;
			    	for(int i=0, size=userList.size(); i<size; i++){
			    		objects = (Object[]) userList.get(i);
			    		sui = (SysUserInfo) objects[0];
			    		ecu = (EduCourseUser) objects[1];
			    		if(usertitleMap.get(ecu.getCourseclassid()) == null){
			    			usertitleMap.put(ecu.getCourseclassid(), "、" + sui.getUsername());
			    		}else {
							String name = (String) usertitleMap.get(ecu.getCourseclassid());
							usertitleMap.put(ecu.getCourseclassid(), name + "、" + sui.getUsername());
						}
			    	}
			    }
		    }
		    httpServletRequest.setAttribute("usertitleMap", usertitleMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return actionMapping.findForward("coursestudy");
	}
	
	/**
	 * 课程管理
	 */
	public ActionForward courseList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
	    String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
	    if("".equals(mark)){
	    	mark = "2";
	    }
        httpServletRequest.setAttribute("mark", mark);
        
		try {
			HttpSession session = httpServletRequest.getSession();
	        Integer userid = (Integer) session.getAttribute("s_userid");
	        
	        //查询教师或助教管理课程班列表
	        String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
	        String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
	        httpServletRequest.setAttribute("status", status);
	        httpServletRequest.setAttribute("title", title);
	        
	        EduCourseUserManager manager = (EduCourseUserManager) getBean("eduCourseUserManager");
	        List list = manager.getManagerEduCourseInfos(userid, status, title);
	        
	        if(list != null && list.size() > 0){
	        	List courseList = new ArrayList();
	        	Map classMap = new HashMap();
	        	List lst = null;
	        	
	        	Object[] obj = null;
	        	EduCourseInfo eci = null;
			    EduCourseClass ecc = null;
			    for(int i=0, size=list.size(); i<size; i++){
			    	obj = (Object[]) list.get(i);
			    	eci = (EduCourseInfo) obj[0];
			    	ecc = (EduCourseClass) obj[1];
			    	
			    	if(classMap.get(eci.getCourseid()) == null){
			    		lst = new ArrayList();
			    		lst.add(ecc);
			    		classMap.put(eci.getCourseid(), lst);
			    	}else {
			    		lst = (List) classMap.get(eci.getCourseid());
			    		lst.add(ecc);
			    		classMap.put(eci.getCourseid(), lst);
					}
			    	if(!courseList.contains(eci)){
			    		//课程显示状态保持和最后一个课程班状态一致
			    		eci.setFlagl(ecc.getCourseclassid().toString());
			    		eci.setFlago(ecc.getStatus());
			    		eci.setEnddate(ecc.getEnddate());
			    		if(userid.intValue() == eci.getSysUserInfo().getUserid().intValue()){
			    			eci.setFlags("add");
			    		}
			    		courseList.add(eci);
			    	}
			    }
			    httpServletRequest.setAttribute("courseList", courseList);
			    httpServletRequest.setAttribute("classMap", classMap);
	        }
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return actionMapping.findForward("courselist"); 
	}
	
	/**
	 * 提交课程班进行审核
	 */
	public ActionForward commitCourseClass(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
	        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
	        String tag = Encode.nullToBlank(httpServletRequest.getParameter("tag"));//1表示重新提交
	        EduCourseClassManager manager = (EduCourseClassManager) getBean("eduCourseClassManager");
	        EduCourseClass eduCourseClass = manager.getEduCourseClass(courseclassid);
	        eduCourseClass.setStatus("2");
	        manager.updateEduCourseClass(eduCourseClass);
	        
	        EduCourseInfoManager ecim = (EduCourseInfoManager) getBean("eduCourseInfoManager");
	        EduCourseInfo eduCourseInfo = ecim.getEduCourseInfo(eduCourseClass.getCourseid());
	        if(!"1".equals(eduCourseInfo.getStatus())){
	            eduCourseInfo.setStatus("2");
	            ecim.updateEduCourseInfo(eduCourseInfo);
	        }
	        
	        if("1".equals(tag)){
	        	addLog(httpServletRequest, "重新提交了课程批次信息【" + eduCourseInfo.getTitle() + "-" + eduCourseClass.getClassname() + "】");
	        }else {
	        	addLog(httpServletRequest, "提交了课程批次信息【" + eduCourseInfo.getTitle() + "-" + eduCourseClass.getClassname() + "】");
			}
	        
	        String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示从课程管理界面提交的课程审核
	        if("1".equals(flag)){
	        	String url = "/courseManager.do?method=index&tag=1&courseid=" + eduCourseClass.getCourseid() + "&courseclassid=" + eduCourseClass.getCourseclassid();
	        	httpServletResponse.sendRedirect(url);
	        	return null;
	        }
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return courseList(actionMapping, actionForm, httpServletRequest, httpServletResponse); 
	}
	
	/**
	 * 添加课程批次
	 */
	public ActionForward beforeAddCourseClass(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
	        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
	        httpServletRequest.setAttribute("courseid", courseid);
	        
	        String curdate = DateTime.getDate();
	        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Calendar calendar=Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, 180);//6个月
			String enddate=format.format(calendar.getTime());
			httpServletRequest.setAttribute("startdate", curdate);
			httpServletRequest.setAttribute("enddate", enddate);
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return actionMapping.findForward("addcourseclass");
	}
	
	/**
	 * 添加课程批次
	 */
	public ActionForward addCourseClass(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
			HttpSession session = httpServletRequest.getSession();
	        Integer userid = (Integer) session.getAttribute("s_userid");
	        
	        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
	        String courseclassname = Encode.nullToBlank(httpServletRequest.getParameter("courseclassname"));
	        String courseclassstartdate = Encode.nullToBlank(httpServletRequest.getParameter("courseclassstartdate"));
	        String courseclassenddate = Encode.nullToBlank(httpServletRequest.getParameter("courseclassenddate"));
	        
	        EduCourseClassManager manager = (EduCourseClassManager) getBean("eduCourseClassManager");
	        EduCourseClass eduCourseClass = new EduCourseClass();
	        eduCourseClass.setClassname(courseclassname);
	        eduCourseClass.setCourseid(Integer.valueOf(courseid));
	        eduCourseClass.setStatus("0");
	        eduCourseClass.setCreatedate(DateTime.getDate());
	        eduCourseClass.setStartdate(courseclassstartdate + " 00:00:01");
	        eduCourseClass.setEnddate(courseclassenddate + " 23:59:59");
	        eduCourseClass.setUsercount(0);
	        manager.addEduCourseClass(eduCourseClass);
	        
	        EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
	        EduCourseUser eduCourseUser = new EduCourseUser();
	        eduCourseUser.setCourseclassid(eduCourseClass.getCourseclassid());
	        eduCourseUser.setCourseid(Integer.valueOf(courseid));
	        eduCourseUser.setUserid(userid);
	        eduCourseUser.setStatus("1");
	        eduCourseUser.setCreatedate(DateTime.getDate());
	        eduCourseUser.setUsertype("1");
	        eduCourseUser.setVip("0");
	        ecum.addEduCourseUser(eduCourseUser);
	        
	        EduCourseInfoManager ecim = (EduCourseInfoManager) getBean("eduCourseInfoManager");
	        EduCourseInfo eduCourseInfo = ecim.getEduCourseInfo(courseid);
	        
	        addLog(httpServletRequest, "添加了课程批次信息【" + eduCourseInfo.getTitle() + "-" + eduCourseClass.getClassname() + "】");
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return courseList(actionMapping, actionForm, httpServletRequest, httpServletResponse); 
	}
	
	/**
	 *修改前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeUpdateCourse(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		HttpSession session = httpServletRequest.getSession();
    	String courseid = (String) session.getAttribute("courseid");
    	String courseclassid = (String) session.getAttribute("courseclassid");
    	
    	EduCourseInfoManager manager = (EduCourseInfoManager) getBean("eduCourseInfoManager");
    	EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
    	EduCourseInfo eduCourseInfo = manager.getEduCourseInfo(courseid);
    	EduCourseClass eduCourseClass = eccm.getEduCourseClass(courseclassid);
    	httpServletRequest.setAttribute("model", eduCourseInfo);
    	httpServletRequest.setAttribute("eduCourseClass", eduCourseClass);

		saveToken(httpServletRequest);
		return actionMapping.findForward("courseedit");
	}

	/**
	 *修改时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward updateSaveCourse(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		EduCourseInfoActionForm form = (EduCourseInfoActionForm)actionForm;
        if (isTokenValid(httpServletRequest, true)) {
            try {
            	String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
            	SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
            	SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
            	
            	EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
                EduCourseInfo model = form.getEduCourseInfo();
                model.setSysUserInfo(sysUserInfo);
                manager.updateEduCourseInfo(model);
                addLog(httpServletRequest,"修改了一个课程信息【" + model.getTitle() + "】");
                
                //可修改课程批次名称和时间
                HttpSession session = httpServletRequest.getSession();
            	String courseclassid = (String) session.getAttribute("courseclassid");
            	
            	String classname = Encode.nullToBlank(httpServletRequest.getParameter("classname"));
            	EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
            	EduCourseClass eduCourseClass = eccm.getEduCourseClass(courseclassid);
            	eduCourseClass.setClassname(classname);
            	eduCourseClass.setStartdate(model.getStartdate());
            	eduCourseClass.setEnddate(model.getEnddate());
            	eduCourseClass.setStatus("0");//修改完后自动恢复未提交，可后期再手动提交申请审核
            	eccm.updateEduCourseClass(eduCourseClass);
                
                String url = "/courseManager.do?method=courseInfo";
                httpServletRequest.getRequestDispatcher(url).forward(httpServletRequest, httpServletResponse);
                return null;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
	}
	
	/**
	 * 课程收藏
	 */
	public ActionForward courseCollect(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
		    String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		    httpServletRequest.setAttribute("mark", mark);
		    
		    Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		    
		    String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		    List<SearchModel> condition = new ArrayList<SearchModel>();
		    SearchCondition.addCondition(condition, "userid", "=", userid);
		    if(!"".equals(title)){
		    	SearchCondition.addCondition(condition, "eduCourseInfo.title", "like", "%" + title + "%");
		    }
		    
		    EduCourseCollectManager manager = (EduCourseCollectManager) getBean("eduCourseCollectManager");
		    PageUtil pageUtil = new PageUtil(httpServletRequest);
		    PageList pageList = manager.getPageEduCourseCollects(condition, "createdate desc", pageUtil.getStartCount(), 10);
		    httpServletRequest.setAttribute("pagelist", pageList);
		    httpServletRequest.setAttribute("title", title);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return actionMapping.findForward("coursecollect");
	}
	
	/**
	 * 课程收藏
	 */
	public ActionForward delBatchCollect(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
			EduCourseCollectManager manager = (EduCourseCollectManager)getBean("eduCourseCollectManager");
			String[] checkids = httpServletRequest.getParameterValues("checkid");

			EduCourseCollect model = null;
			for (int i = 0; i < checkids.length; i++) {
				model = manager.getEduCourseCollect(checkids[i]);
				manager.delEduCourseCollect(model);
				addLog(httpServletRequest,"删除了一个收藏课程【" + model.getEduCourseInfo().getTitle() + "】");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return courseCollect(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 消息通知
	 */
	public ActionForward myMessage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
		    String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		    httpServletRequest.setAttribute("mark", mark);
		    
		    Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		    
		    String isread = Encode.nullToBlank(httpServletRequest.getParameter("isread"));
		    
		    List<SearchModel> condition = new ArrayList<SearchModel>();
		    SearchCondition.addCondition(condition, "userid", "=", userid);
		    if(!"".equals(isread)){
		    	SearchCondition.addCondition(condition, "isread", "=", isread);
		    }
		    
		    SysMessageUserManager manager = (SysMessageUserManager) getBean("sysMessageUserManager");
		    PageUtil pageUtil = new PageUtil(httpServletRequest);
		    PageList pageList = manager.getPageSysMessageUsers(condition, "sysMessageInfo.createdate desc", pageUtil.getStartCount(), 10);
		    httpServletRequest.setAttribute("pagelist", pageList);
		    httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
		    httpServletRequest.setAttribute("isread", isread);
		    
		    //查询所有消息通知，然后归类呈现
		    condition.clear();
		    SearchCondition.addCondition(condition, "userid", "=", userid);
		    List list = manager.getSysMessageUsers(condition, "", 0);
		    int allmsg = 0;
		    int unreadmsg = 0;
		    int readmsg = 0;
		    SysMessageUser smu = null;
		    for(int i=0, size=list.size(); i<size; i++){
		    	smu = (SysMessageUser) list.get(i);
		    	allmsg++;
		    	if("1".equals(smu.getIsread())){
		    		readmsg++;
		    	}else {
					unreadmsg++;
				}
		    }
		    httpServletRequest.setAttribute("allmsg", allmsg);
		    httpServletRequest.setAttribute("unreadmsg", unreadmsg);
		    httpServletRequest.setAttribute("readmsg", readmsg);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return actionMapping.findForward("mymessage");
	}
	
	/**
	 * 消息通知
	 */
	public ActionForward viewMessage(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
		    String messageuserid = Encode.nullToBlank(httpServletRequest.getParameter("messageuserid"));
		    SysMessageUserManager manager = (SysMessageUserManager) getBean("sysMessageUserManager");
		    SysMessageUser sysMessageUser = manager.getSysMessageUser(messageuserid);
		    if(!"1".equals(sysMessageUser.getIsread())){
		    	sysMessageUser.setIsread("1");
		    	sysMessageUser.setReadtime(DateTime.getDate());
		    	manager.updateSysMessageUser(sysMessageUser);
		    }
		    httpServletRequest.setAttribute("sysMessageUser", sysMessageUser);
		    
		    SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
		    SysUserInfo sysUserInfo = suim.getSysUserInfo(sysMessageUser.getSysMessageInfo().getUserid());
		    httpServletRequest.setAttribute("sysUserInfo", sysUserInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return actionMapping.findForward("viewmessage");
	}
	
	//==================================个人中心用户设置=====================================
	/**
	 *修改前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeUpdateSelf(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		try {
			Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
			SysUserInfo model = manager.getSysUserInfo(userid);
			SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
			if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号解密显示
				detail.setCardno(DES.getDecryptPwd(detail.getCardno()));
			}
			if(!model.getPhoto().startsWith("http://")){
				model.setFlags("/upload/" + model.getPhoto());
			}
			httpServletRequest.setAttribute("act", "updateSaveSelf");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("detail", detail);
		}catch (Exception e){
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("editself");
	}

	/**
	 *修改时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward updateSaveSelf(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
				String photo = Encode.nullToBlank(httpServletRequest.getParameter("photo"));
				String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
				String telephone = Encode.nullToBlank(httpServletRequest.getParameter("telephone"));
				String address = Encode.nullToBlank(httpServletRequest.getParameter("address"));
				String education = Encode.nullToBlank(httpServletRequest.getParameter("education"));
				String thetitle = Encode.nullToBlank(httpServletRequest.getParameter("thetitle"));
				String major = Encode.nullToBlank(httpServletRequest.getParameter("major"));
				String descript = Encode.nullToBlank(httpServletRequest.getParameter("descript"));
				
				HttpSession session = httpServletRequest.getSession();
				Integer userid = (Integer) session.getAttribute("s_userid");
				SysUserInfo sysUserInfo = manager.getSysUserInfo(userid);
				SysUserInfoDetail detail = suidm.getSysUserInfoDetail(userid);
				
				sysUserInfo.setUsername(username);
				sysUserInfo.setPhoto(photo);
				sysUserInfo.setSex(sex);
				if(sysUserInfo.getMobile() == null || "".equals(sysUserInfo.getMobile())){
					sysUserInfo.setMobile(telephone);
				}
				manager.updateSysUserInfo(sysUserInfo);
				
				detail.setTelephone(telephone);
				detail.setAddress(address);
				detail.setEducation(education);
				if("1".equals(sysUserInfo.getUsertype())){
					detail.setThetitle(thetitle);
					detail.setMajor(major);
					detail.setDescript(descript);
				}
				suidm.updateSysUserInfoDetail(detail);
				
				addLog(httpServletRequest,"修改了个人【" + sysUserInfo.getUsername() + "】信息");
				
				//更新session中的信息
				session.removeAttribute("s_sysuserinfodetail");
				session.removeAttribute("s_sysuserinfo");
				session.setAttribute("s_sysuserinfo", sysUserInfo);
				session.setAttribute("s_sysuserinfodetail", detail);
			}catch (Exception e){
				httpServletRequest.setAttribute("prompttag", "0");
				return beforeUpdateSelf(actionMapping, actionForm, httpServletRequest, httpServletResponse);
			}
		}

		httpServletRequest.setAttribute("prompttag", "1");
		return beforeUpdateSelf(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 *修改前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward modifyPassword(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		try {
			Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
			SysUserInfo model = manager.getSysUserInfo(userid);
			httpServletRequest.setAttribute("act", "modifySave");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("modifypassword");
	}

	/**
	 *修改时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward modifySave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String oldpassword = Encode.nullToBlank(httpServletRequest.getParameter("oldpassword"));
				String newpassword = Encode.nullToBlank(httpServletRequest.getParameter("newpassword"));
				
				Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
				SysUserInfo model = manager.getSysUserInfo(userid);
				httpServletRequest.setAttribute("model", model);
				
				String password = MD5.getEncryptPwd(oldpassword);
				if(!password.equals(model.getPassword())){
					httpServletRequest.setAttribute("prompttag", "2");
					return actionMapping.findForward("modifypassword");
				}
				
				model.setPassword(MD5.getEncryptPwd(newpassword));
				manager.updateSysUserInfo(model);
				
				addLog(httpServletRequest,"修改了个人【" + model.getUsername() + "】密码");
			}catch (Exception e){
				httpServletRequest.setAttribute("prompttag", "0");
				return actionMapping.findForward("modifypassword");
			}
		}

		httpServletRequest.setAttribute("prompttag", "1");
		return actionMapping.findForward("modifypassword");
	}
	
	/**
	 * 添加课程评价
	 */
	public ActionForward beforeAddCourseComment(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
	        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
	        httpServletRequest.setAttribute("courseid", courseid);
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return actionMapping.findForward("addcoursecomment");
	}
	
	/**
	 * 添加课程评价
	 */
	public ActionForward addCourseComment(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
			throws Exception {
		try {
			HttpSession session = httpServletRequest.getSession();
	        SysUserInfo sysUserInfo = (SysUserInfo) session.getAttribute("s_sysuserinfo");
	        
	        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
	        httpServletRequest.setAttribute("courseid", courseid);
	        
	        String score = Encode.nullToBlank(httpServletRequest.getParameter("score"));
	        String content = Encode.nullToBlank(httpServletRequest.getParameter("content"));
	        String anonymous = Encode.nullToBlank(httpServletRequest.getParameter("anonymous"));
	        if(!"1".equals(anonymous)){
	        	anonymous = "0";
	        }
	        
	        EduCourseCommentManager manager = (EduCourseCommentManager) getBean("eduCourseCommentManager");
	        EduCourseComment comment = new EduCourseComment();
	        comment.setCourseid(Integer.valueOf(courseid));
	        comment.setContent(content);
	        comment.setScore(Integer.valueOf(score));
	        comment.setSysUserInfo(sysUserInfo);
	        comment.setUserip(IpUtil.getIpAddr(httpServletRequest));
	        comment.setCreatedate(DateTime.getDate());
	        comment.setAnonymous(anonymous);
	        comment.setStatus("1");
	        manager.addEduCourseComment(comment);
	        
	        EduCourseInfoManager ecim = (EduCourseInfoManager) getBean("eduCourseInfoManager");
	        EduCourseInfo eduCourseInfo = ecim.getEduCourseInfo(courseid);
	        float courseScore = (eduCourseInfo.getScore() + comment.getScore().floatValue())/2;
	        eduCourseInfo.setScore(courseScore);
	        eduCourseInfo.setScoreusers(eduCourseInfo.getScoreusers() + 1);
	        ecim.updateEduCourseInfo(eduCourseInfo);
	        
	        addLog(httpServletRequest, "添加了课程评价【" + content + "】内容");
		} catch (Exception e) {
		    e.printStackTrace();
		}
		httpServletRequest.setAttribute("promptinfo", "感谢您对课程的真实评价!");
		return actionMapping.findForward("success");
		//return actionMapping.findForward("addcoursecommentsuccess");
	}
	
	/**
	 * 我的订单
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-22 下午3:08:56 
	* @lastModified ; 2017-2-22 下午3:08:56 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward myTrade(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SysUserPayTradeManager manager = (SysUserPayTradeManager)getBean("sysUserPayTradeManager");
        SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
        
        String subject = Encode.nullToBlank(httpServletRequest.getParameter("subject"));
        String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
        String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
        String body = Encode.nullToBlank(httpServletRequest.getParameter("body"));
        
        String paytype = Encode.nullToBlank(httpServletRequest.getParameter("paytype"));
        String state = Encode.nullToBlank(httpServletRequest.getParameter("state"));
        
        String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
        String xueduan = Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
        
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        if("".equals(mark)){
            mark = "6";
        }
        httpServletRequest.setAttribute("mark", mark);
        
        HttpSession session = httpServletRequest.getSession();
        Integer userid = (Integer) session.getAttribute("s_userid");
        PageUtil pageUtil = new PageUtil(httpServletRequest);
        String sorderindex = "createdate desc";
        if(!"".equals(pageUtil.getOrderindex())){
            sorderindex = pageUtil.getOrderindex();
        }
        PageList page = manager.getSysUserPayTradesOfPage2(userid,subject, username, createdate, body, paytype, state, usertype, xueduan, sorderindex,  pageUtil.getStartCount(), 10);
        ArrayList datalist = page.getDatalist();
        if(datalist !=null && datalist.size()>0){
            for(int i= 0;i<datalist.size();i++){
                SysUserPayTrade model = (SysUserPayTrade) datalist.get(i);
                Integer userid1 = model.getUserid();
                SysUserInfo userInfo = userInfoManager.getSysUserInfo(userid1);
                model.setFlago(userInfo.getUsername());
                String paytype1 = model.getPaytype();
                if(paytype1.equals("wxpay")){
                    model.setPaytype("微信支付");
                }else{
                    model.setPaytype("支付宝");
                }
                String state1 = model.getState();
                if(state1.equals("0")){
                    model.setState("支付未完成");
                }else if(state1.equals("1")){
                    model.setState("支付异常");
                }else if(state1.equals("2")){
                    model.setState("支付完成");
                }
            }
        }
        httpServletRequest.setAttribute("pagelist", page);
        httpServletRequest.setAttribute("subject", subject);
        httpServletRequest.setAttribute("username", username);
        httpServletRequest.setAttribute("createdate", createdate);
        httpServletRequest.setAttribute("body", body);
        httpServletRequest.setAttribute("paytype", paytype);
        httpServletRequest.setAttribute("state", state);
        httpServletRequest.setAttribute("usertype", usertype);
        httpServletRequest.setAttribute("xueduan", xueduan);
        httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
        return actionMapping.findForward("mytradelist");
    }
	
	/**
	 * 我的帐户
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-22 下午3:33:11 
	* @lastModified ; 2017-2-22 下午3:33:11 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward myMoney(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SysUserMoneyManager manager = (SysUserMoneyManager)getBean("sysUserMoneyManager");
        SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
        
        String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
        String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
        String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
        String descript = Encode.nullToBlank(httpServletRequest.getParameter("descript"));
        
        String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
        String xueduan = Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
        String changetype = Encode.nullToBlank(httpServletRequest.getParameter("changetype"));
        
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        if("".equals(mark)){
            mark = "7";
        }
        httpServletRequest.setAttribute("mark", mark);
        
        HttpSession session = httpServletRequest.getSession();
      Integer userid = (Integer) session.getAttribute("s_userid");
        PageUtil pageUtil = new PageUtil(httpServletRequest);
        String sorderindex = "createdate desc";
        if(!"".equals(pageUtil.getOrderindex())){
            sorderindex = pageUtil.getOrderindex();
        }
        PageList page = manager.getSysUserMoneysOfPage2(userid,title, username, createdate, descript, changetype, usertype, xueduan, sorderindex, pageUtil.getStartCount(), 10);
        ArrayList datalist = page.getDatalist();
        if(datalist !=null && datalist.size()>0){
            for(int i= 0;i<datalist.size();i++){
                SysUserMoney model = (SysUserMoney) datalist.get(i);
                Integer userid2 = model.getUserid();
                SysUserInfo userInfo = userInfoManager.getSysUserInfo(userid2);
                model.setFlago(userInfo.getUsername());
            }
        }
        httpServletRequest.setAttribute("pagelist", page);
        httpServletRequest.setAttribute("title", title);
        httpServletRequest.setAttribute("username", username);
        httpServletRequest.setAttribute("createdate", createdate);
        httpServletRequest.setAttribute("descript", descript);
        httpServletRequest.setAttribute("usertype", usertype);
        httpServletRequest.setAttribute("xueduan", xueduan);
        httpServletRequest.setAttribute("changetype", changetype);
        httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
        return actionMapping.findForward("mymoney");
    }
	
	/**
	 * 课程资源
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-3-29 下午2:33:20 
	* @lastModified ; 2017-3-29 下午2:33:20 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	* @throws Exception
	 */
	public ActionForward courseFile(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse)
            throws Exception {
        try {
            String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
            httpServletRequest.setAttribute("mark", mark);
            
            Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
            
            String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
            httpServletRequest.setAttribute("title", title);
            
            //教师，助教，学生，都可以进入，但必须是这个课程的vip
            EduCourseUserManager manager = (EduCourseUserManager) getBean("eduCourseUserManager");
            PageUtil pageUtil = new PageUtil(httpServletRequest);
            PageList pageList = manager.getPageEduCoursesByVip(userid, pageUtil.getStartCount(),title, 5);
            httpServletRequest.setAttribute("pagelist", pageList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return actionMapping.findForward("coursefile");
    }
	
	 /* 跳转到主工作区
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
    public ActionForward courseFileMain(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        httpServletRequest.setAttribute("mark", mark);
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
            return actionMapping.findForward("coursefilemain");
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
    public ActionForward courseFileTree(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
        httpServletRequest.setAttribute("mark", mark);
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        
        EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
        HttpSession session = httpServletRequest.getSession();
        SysUserInfo model = (SysUserInfo) session.getAttribute("s_sysuserinfo");
        if(!"1".equals(model.getUsertype())){
        	SearchCondition.addCondition(condition, "isopen", "=", "1");
        }
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
            if(!"1".equals(model.getUsertype())){
            	text = ecc.getSecondTitle();// 树节点显示名称
            }else{
            	text = ecc.getTitle();// 树节点显示名称
            }
            url = "/courseCenter.do?method=courseFileList&columnid=" + ecc.getColumnid()+"&courseid="+ecc.getCourseid();
            tree.append("\n").append("tree").append(".nodes[\"").append(pno).append("_").append(no).append("\"]=\"");
            if(text !=null && text.trim().length() > 0) {
                tree.append("text:").append(text).append(";");
            }
            if(hint!=null && hint.trim().length() > 0) {
                tree.append("hint:").append(hint).append(";");
            }
            tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
        }
        String rooturl = "javascript:;";
        httpServletRequest.setAttribute("treenode", tree.toString());
        httpServletRequest.setAttribute("rooturl", rooturl);
        httpServletRequest.setAttribute("modulename", "课程资源");
            return actionMapping.findForward("coursefiletree");
    }
    
    public ActionForward courseFileList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
        SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
        EduCourseFileColumnManager fileColumnManager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("columnid", columnid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
           
       String filename = Encode.nullToBlank(httpServletRequest.getParameter("filename"));
       httpServletRequest.setAttribute("filename", filename);
       String filetype = Encode.nullToBlank(httpServletRequest.getParameter("filetype"));
       httpServletRequest.setAttribute("filetype", filetype);
       String coursefiletype = Encode.nullToBlank(httpServletRequest.getParameter("coursefiletype"));
       httpServletRequest.setAttribute("coursefiletype", coursefiletype);
       
       try {
           PageUtil pageUtil = new PageUtil(httpServletRequest);
           HttpSession session = httpServletRequest.getSession();
           SysUserInfo sui = (SysUserInfo) session.getAttribute("s_sysuserinfo");
           String isopen="";
           if(!"1".equals(sui.getUsertype())){
        	   isopen="1";
           }
//            SearchCondition.addCondition(condition, "coursecolumnid", "=", columnid);
//            SearchCondition.addCondition(condition, "courseid", "=", courseid);
//            if(!"".equals(filename)){
//                SearchCondition.addCondition(condition, "filename", "like", "%"+filename+"%");
//            }
//            if(!"".equals(filetype)){
//                SearchCondition.addCondition(condition, "filetype", "=", filetype);
//            }
//            if(!"".equals(coursefiletype)){
//                SearchCondition.addCondition(condition, "coursefiletype", "=", coursefiletype);
//            }
//           PageList page = manager.getPageEduCourseFiles(condition, "filename asc", pageUtil.getStartCount(), pageUtil.getPageSize());
           PageList page = manager.geteEduCourseFilesOfPage(columnid, courseid, filename, filetype,isopen, "filename asc",  pageUtil.getStartCount(), pageUtil.getPageSize());
           ArrayList datalist = page.getDatalist();
           if(datalist !=null && datalist.size()>0){
               for(int i=0;i<datalist.size();i++){
                   EduCourseFile model = (EduCourseFile) datalist.get(i);
                 Integer userid = model.getUserid();
                 SysUserInfo info = userInfoManager.getSysUserInfo(userid);
                 String username = info.getUsername();
                 model.setFlagl(username);
                 model.setFlago(FileUtil.getFileSizeName(model.getFilesize().doubleValue(), 2));
                 String coursefiletype2 = model.getCoursefiletype();
                 EduCourseFileColumn eduCourseFileColumn = fileColumnManager.getEduCourseFileColumn(coursefiletype2);
                 if(!"1".equals(sui.getUsertype())){
                	 model.setCoursefiletype(eduCourseFileColumn.getSecondtitle());
                 }else{
                	 model.setCoursefiletype(eduCourseFileColumn.getTitle());
                 }
                 //Date parse = formatter.parse(model.getCreatedate());
                 //model.setCreatedate(formatter.format(parse));
               }
           }
           httpServletRequest.setAttribute("pagelist", page);
       } catch (Exception e){
        e.printStackTrace();
       }
        return actionMapping.findForward("coursefilelist");
    }
    
    public ActionForward downFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
        String fileid = Encode.nullToBlank(request.getParameter("fileid"));
        EduCourseFile model = manager.getEduCourseFile(fileid);
       
        String filepath = model.getFilepath();
        String filename = model.getFilename();
        String fileext = model.getFileext();
        BufferedInputStream bis = null; 
        BufferedOutputStream bos = null; 
        OutputStream fos = null; 
        InputStream fis = null; 
        
      //如果是从服务器上取就用这个获得系统的绝对路径方法。
        filepath = servlet.getServletContext().getRealPath("/upload/" + filepath); 
        if(!filename.endsWith(fileext)){
        	filename =filename+"."+fileext;
        }
        File uploadFile = new File(filepath); 
        fis = new FileInputStream(uploadFile); 
        bis = new BufferedInputStream(fis); 
        fos = response.getOutputStream(); 
        bos = new BufferedOutputStream(fos); 
        //这个就是弹出下载对话框的关键代码 
        response.setHeader("Content-disposition", 
                           "attachment;filename=" + 
                           URLEncoder.encode(filename, "utf-8")); 
        int bytesRead = 0; 
        //这个地方的同上传的一样。我就不多说了，都是用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流 
        byte[] buffer = new byte[8192]; 
        while ((bytesRead = bis.read(buffer, 0, 8192)) != -1) { 
            bos.write(buffer, 0, bytesRead); 
        } 
        bos.flush(); 
        fis.close(); 
        bis.close(); 
        fos.close(); 
        bos.close(); 
        return null;
    }
    
    /**
     * 资源在线预览
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-3-20 下午2:55:22 
    * @lastModified ; 2017-3-20 下午2:55:22 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
     */
    public ActionForward viewFile(ActionMapping actionMapping, ActionForm actionForm,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        String fileid = Encode.nullToBlank(httpServletRequest.getParameter("fileid"));
        EduCourseFileManager manager = (EduCourseFileManager) getBean("eduCourseFileManager");
        EduCourseFile model = manager.getEduCourseFile(fileid);
        httpServletRequest.setAttribute("model", model);

        return actionMapping.findForward("coursefileview");
    }
}
