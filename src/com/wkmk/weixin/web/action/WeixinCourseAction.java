package com.wkmk.weixin.web.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.des.DesUtil;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.PublicResourceBundle;
import com.wkmk.edu.bo.EduCourseBuy;
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseColumn;
import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduCourseStudy;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.service.EduCourseBuyManager;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseColumnManager;
import com.wkmk.edu.service.EduCourseFilmManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduCourseStudyManager;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.sys.bo.SysPayPassword;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserLog;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.bo.SysUserSearchKeywords;
import com.wkmk.sys.service.SysPayPasswordManager;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserLogManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.sys.service.SysUserSearchKeywordsManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;
import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmPixManager;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 课程学习
 * @version 1.0
 */
public class WeixinCourseAction extends BaseAction {
	
	/**
	 *  个人中心---我的课程
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-23 上午11:20:48 
	* @lastModified ; 2017-2-23 上午11:20:48 
	* @version ; 1.0 
	* @param mapping
	* @param form
	* @param request
	* @param response
	* @return
	 */
	public ActionForward myCourse(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
        try {
            String userid=MpUtil.getUserid(request);
            if(!"1".equals(userid)){
                SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
                request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
                return mapping.findForward("mycourse");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("error");
    }
	/**
	 * 个人中心---我的课程列表刷新
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-23 上午11:13:33 
	* @lastModified ; 2017-2-23 上午11:13:33 
	* @version ; 1.0 
	* @param mapping
	* @param form
	* @param request
	* @param response
	 */
	public void getMyCourseByAjax(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
        try {
            String userid=Encode.nullToBlank(request.getParameter("userid"));
            String searchuserid=MpUtil.getUserid(request);
//          String type=Encode.nullToBlank(request.getParameter("type"));//购买类型，1解题微课，2举一反三微课
            int pagesize=10;int pagenum=Integer.parseInt(Encode.nullToBlank(request.getParameter("pagenum")));int pagestat=pagesize*pagenum;
            EduCourseUserManager courseUserManager = (EduCourseUserManager) getBean("eduCourseUserManager");
            EduCourseInfoManager infoManager = (EduCourseInfoManager) getBean("eduCourseInfoManager");
            EduCourseClassManager classManager = (EduCourseClassManager) getBean("eduCourseClassManager");
            EduCourseFilmManager filmManager = (EduCourseFilmManager) getBean("eduCourseFilmManager");
            List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(searchuserid));
            SearchCondition.addCondition(condition, "status", "=", "1");
            List<EduCourseUser> list = courseUserManager.getPageEduCourseUsers(condition, "createdate desc", pagestat, pagesize).getDatalist();
            
            StringBuffer result=new StringBuffer();
            for (EduCourseUser courseUser : list) {//循环购买记录查询相关数据
                EduCourseInfo courseInfo = infoManager.getEduCourseInfo(courseUser.getCourseid());//课程
                EduCourseClass courseClass = classManager.getEduCourseClass(courseUser.getCourseclassid());//批次
                if(courseInfo.getStatus().equals("1") && courseClass.getStatus().equals("1")){
                    condition.clear();
                    SearchCondition.addCondition(condition, "courseid", "=", courseUser.getCourseid());
                    SearchCondition.addCondition(condition, "status", "=", "1");
                    List courseFilms = filmManager.getEduCourseFilms(condition, "orderindex desc,createdate asc", 0);
                    result.append("<a href=\"/weixinCourse.app?method=courseFilmList&userid="+userid+"&courseid="+courseUser.getCourseid()+"\">"+
                                    "<div class=\"buy_main_moudle\">"+
                                        "<img src=\"/upload/"+courseInfo.getSketch()+"\"  onerror=\"javascript:this.src='/weixin/images/img07.png'\" />"+
                                        "<div class=\"buy_main_moudle_font\">"+
                                            "<p  class=\"buy_main_moudle_font_p\">【"+courseInfo.getTitle()+"】</p>"+
                                            "<p class=\"buy_main_moudle_font_p01\">"+courseClass.getClassname()+"</p>"+
                                            "<p class=\"buy_main_moudle_font_p02\">总课时：<span>"+courseInfo.getClasshour()+"</span></p>"+
                                        "</div>"+
                                    "</div>"+
                                 "</a>");
                }
            }
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 课程微课列表
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-23 上午11:48:50 
	* @lastModified ; 2017-2-23 上午11:48:50 
	* @version ; 1.0 
	* @param mapping
	* @param form
	* @param request
	* @param response
	* @return
	 */
	public ActionForward courseFilmList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String userid = MpUtil.getUserid(request);
            if(!"1".equals(userid)){
                String keywords = Encode.nullToBlank(request.getParameter("keywords"));//如果搜索，直接显示具体微课，无需显示章节
                String courseid = Encode.nullToBlank(request.getParameter("courseid"));//课程id
                EduCourseInfoManager infoManager = (EduCourseInfoManager) getBean("eduCourseInfoManager");
                EduCourseInfo eduCourseInfo = infoManager.getEduCourseInfo(courseid);//课程
                request.setAttribute("eduCourseInfo", eduCourseInfo);
                request.setAttribute("keywords", keywords);
                request.setAttribute("courseid", courseid);
                
                if(!"1".equals(eduCourseInfo.getStatus())){
                	//课程未审核通过，需要审核通过才能在前台显示
    				request.setAttribute("msg", "当前课程【" + eduCourseInfo.getTitle() + "】暂未通过审核，请等待审核通过后再访问查看。");
    				return mapping.findForward("tips");
                }
                
                //判断用户是否是专家教师、vip用户、已购买当前课程用户。用户如果存储多个课程班（即批次）中，只要有一个班级是vip，则在其他班级也能看课程学习
				EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "status", "=", "1");
				List courseUserList = ecum.getEduCourseUsers(condition, "courseuserid desc", 0);
				
				boolean canStudy = false;
				EduCourseUser ecu = null;
				String nowDate = DateTime.getDate();
				for(int i=0, size=courseUserList.size(); i<size; i++){
					ecu = (EduCourseUser) courseUserList.get(i);
					if("1".equals(ecu.getVip())){
						 String startdate = ecu.getStartdate();
						 String enddate = ecu.getEnddate();
						int res1 = nowDate.compareTo(startdate);
						int res2 = nowDate.compareTo(enddate);
						if(res1 >=0 && res2 <=0 ){
							canStudy = true;
							break;
						}
					}
				}
                
				EduCourseBuyManager ecbm = (EduCourseBuyManager) getBean("eduCourseBuyManager");
                List buyCoursefilmidList = new ArrayList();//购买的微课id的集合
                if(!canStudy){
                	buyCoursefilmidList = ecbm.getAllCoursefilmidByUserid(userid, courseid);
                }
                request.setAttribute("canStudy", canStudy);
                request.setAttribute("buyCoursefilmidList", buyCoursefilmidList);
                
                EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
                condition.clear();
                SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
                SearchCondition.addCondition(condition, "status", "=", "1");
                List coursefilmList = null;
                if(keywords != null && !"".equals(keywords)){
                	SearchCondition.addCondition(condition, "title", "like", "%" + keywords + "%");
                	coursefilmList = ecfm.getEduCourseFilms(condition, "orderindex asc", 0);
                }else {
                	//可通过缓存处理，有效期1天
                	coursefilmList = CacheUtil.getList("EduCourseFilm_List_" + courseid);
                    if(coursefilmList == null){
                    	coursefilmList = ecfm.getEduCourseFilms(condition, "orderindex asc", 0);
                    	CacheUtil.putObject("EduCourseFilm_List_" + courseid, coursefilmList, 24*60*60);
                    }
                    
                    //非搜索情况下显示课程目录
            		List columnList = (List) CacheUtil.getObject("EduCourseColumn_List_" + courseid);
            		if(columnList == null || columnList.size() == 0){
            			EduCourseColumnManager eccm = (EduCourseColumnManager) getBean("eduCourseColumnManager");
            			condition.clear();
            			SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
            			SearchCondition.addCondition(condition, "status", "=", "1");
            			List list = eccm.getEduCourseColumns(condition, "columnno asc", 0);
            			
            			columnList = new ArrayList();
            			Map map = new HashMap();
            			EduCourseColumn ecc = null;
            			for(int i=0, size=list.size(); i<size; i++){
            				ecc = (EduCourseColumn) list.get(i);
            				if("0000".equals(ecc.getParentno())){
            					map.put(ecc.getColumnno(), ecc.getTitle());
            				}else {
            					ecc.setFlags(map.get(ecc.getParentno()).toString());
            					columnList.add(ecc);
            				}
            			}
            			
            			CacheUtil.putObject("EduCourseColumn_List_" + courseid, columnList, 24*60*60);//缓存一天
            		}
            		request.setAttribute("columnList", columnList);
				}
                request.setAttribute("coursefilmList", coursefilmList);
                
    			Map courseFilmMap = new HashMap();
    			EduCourseFilm ecf = null;
    			for(int i=0, size=coursefilmList.size(); i<size; i++){
    				ecf = (EduCourseFilm) coursefilmList.get(i);
    				if(courseFilmMap.get(ecf.getCoursecolumnid()) == null){
    					List lst = new ArrayList();
    					lst.add(ecf);
    					courseFilmMap.put(ecf.getCoursecolumnid(), lst);
    				}else {
    					List lst = (List) courseFilmMap.get(ecf.getCoursecolumnid());
    					lst.add(ecf);
    					courseFilmMap.put(ecf.getCoursecolumnid(), lst);
    				}
    			}
        		request.setAttribute("courseFilmMap", courseFilmMap);
                
                return mapping.findForward("coursefilmlist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("error");
    }
	
	/**
	 * 微课播放
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-2-23 下午2:39:51 
	* @lastModified ; 2017-2-23 下午2:39:51 
	* @version ; 1.0 
	* @param mapping
	* @param form
	* @param request
	* @param response
	* @return
	 */
	public ActionForward play(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String userid = MpUtil.getUserid(request);
            if (!"1".equals(userid)) {
                String coursefilmid = Encode.nullToBlank(request.getParameter("coursefilmid"));
                request.setAttribute("coursefilmid", coursefilmid);

                EduCourseFilmManager manager = (EduCourseFilmManager) getBean("eduCourseFilmManager");
                EduCourseFilm eduCourseFilm = manager.getEduCourseFilm(coursefilmid);
                request.setAttribute("eduCourseFilm", eduCourseFilm);
                
                List<SearchModel> condition = new ArrayList<SearchModel>();
                //判断用户是否可直接观看当前课程，或已购买课程
                boolean canStudy = false;
				//如果课程是免费的，所有人都可以查看
				if(eduCourseFilm.getSellprice() > 0){
					//判断用户是否是专家教师、vip用户、已购买当前课程用户。用户如果存储多个课程班（即批次）中，只要有一个班级是vip，则在其他班级也能看课程学习
					EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
					condition.clear();
					SearchCondition.addCondition(condition, "courseid", "=", eduCourseFilm.getCourseid());
					SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
					SearchCondition.addCondition(condition, "status", "=", "1");
					List courseUserList = ecum.getEduCourseUsers(condition, "courseuserid desc", 0);
					
					EduCourseUser ecu = null;
					String nowDate = DateTime.getDate();
					for(int i=0, size=courseUserList.size(); i<size; i++){
						ecu = (EduCourseUser) courseUserList.get(i);
						if("1".equals(ecu.getUsertype())){
							canStudy = true;
							break;
						}else if("1".equals(ecu.getVip())){
							 String startdate = ecu.getStartdate();
							 String enddate = ecu.getEnddate();
							int res1 = nowDate.compareTo(startdate);
							int res2 = nowDate.compareTo(enddate);
							if(res1 >=0 && res2 <=0 ){
								canStudy = true;
								break;
							}
						}
					}
					
					if(!canStudy){
						//普通用户，需要查询是否已购买当前课程
						EduCourseBuyManager ecbm = (EduCourseBuyManager) getBean("eduCourseBuyManager");
						condition.clear();
						SearchCondition.addCondition(condition, "coursefilmid", "=", Integer.valueOf(coursefilmid));
						SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
						SearchCondition.addCondition(condition, "enddate", ">", DateTime.getDate());
						List courseBuyList = ecbm.getEduCourseBuys(condition, "", 0);
						if(courseBuyList != null && courseBuyList.size() > 0){
							canStudy = true;
						}
					}
				}else {
					canStudy = true;
					//如果是第一次学习免费课程，需要加入课程班级
					EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
					condition.clear();
					SearchCondition.addCondition(condition, "courseid", "=", eduCourseFilm.getCourseid());
					SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
					List courseUserList = ecum.getEduCourseUsers(condition, "courseuserid desc", 0);
					if(courseUserList == null || courseUserList.size() == 0){
						//获取当前课程最新课程班批次
						EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
						condition.clear();
						SearchCondition.addCondition(condition, "courseid", "=", eduCourseFilm.getCourseid());
						SearchCondition.addCondition(condition, "status", "=", "1");
						List list = eccm.getEduCourseClasss(condition, "enddate desc", 1);
						if(list != null && list.size() > 0){
							EduCourseClass eduCourseClass = (EduCourseClass) list.get(0);
							EduCourseUser courseUser = new EduCourseUser();
							courseUser.setCourseclassid(eduCourseClass.getCourseclassid());
							courseUser.setCourseid(eduCourseFilm.getCourseid());
							courseUser.setUserid(Integer.valueOf(userid));
							courseUser.setStatus("1");
							courseUser.setCreatedate(DateTime.getDate());
							courseUser.setUsertype("3");
							courseUser.setVip("0");
							ecum.addEduCourseUser(courseUser);
							
							eduCourseClass.setUsercount(eduCourseClass.getUsercount() + 1);
							eccm.updateEduCourseClass(eduCourseClass);
						}
					}
				}
                
                if(!canStudy){
                	//如果不可以查看，直接跳转到购买界面
                	String url = "/weixinCourse.app?method=buy&userid=" + DES.getEncryptPwd(userid) + "&coursefilmid=" + coursefilmid;
                	response.sendRedirect(url);
                	return null;
                }
                
                // 更新播放次数
                Integer hits = eduCourseFilm.getHits();
                eduCourseFilm.setHits(hits + 1);
                manager.updateEduCourseFilm(eduCourseFilm);
                
                EduCourseInfoManager ecim = (EduCourseInfoManager) getBean("eduCourseInfoManager");
                EduCourseInfo eduCourseInfo = ecim.getEduCourseInfo(eduCourseFilm.getCourseid());
                eduCourseInfo.setStudys(eduCourseInfo.getStudys() + 1);
                ecim.updateEduCourseInfo(eduCourseInfo);
                request.setAttribute("eduCourseInfo", eduCourseInfo);
                
                EduCourseColumnManager eccm = (EduCourseColumnManager) getBean("eduCourseColumnManager");
                EduCourseColumn column = eccm.getEduCourseColumn(eduCourseFilm.getCoursecolumnid());
                if(!"0000".equals(column.getParentno())){
                	EduCourseColumn parent = eccm.getEduCourseColumnByParentno(eduCourseFilm.getCourseid().toString(), column.getParentno());
                	request.setAttribute("parent", parent);
                }
                request.setAttribute("column", column);
                
                EduCourseStudyManager ecsm = (EduCourseStudyManager) getBean("eduCourseStudyManager");
                EduCourseStudy eduCourseStudy = new EduCourseStudy();
				eduCourseStudy.setCourseid(eduCourseInfo.getCourseid());
				eduCourseStudy.setCoursefilmid(eduCourseFilm.getCoursefilmid());
				eduCourseStudy.setUserid(Integer.valueOf(userid));
				eduCourseStudy.setUserip(IpUtil.getIpAddr(request));
				eduCourseStudy.setCreatedate(DateTime.getDate());
				ecsm.addEduCourseStudy(eduCourseStudy);
                
				VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
				VwhFilmInfo vwhFilmInfo = vfim.getVwhFilmInfo(eduCourseFilm.getFilmid());
				request.setAttribute("vwhFilmInfo", vwhFilmInfo);
				
				VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
				VwhComputerInfo vwhComputerInfo = vcim.getVwhComputerInfo(vwhFilmInfo.getComputerid());
				request.setAttribute("vwhComputerInfo", vwhComputerInfo);
				
				VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
				condition.clear();
				SearchCondition.addCondition(condition, "filmid", "=", eduCourseFilm.getFilmid());
				List pixlist = vfpm.getVwhFilmPixs(condition, "orderindex", 0);
				String flvpath = "";
				if(pixlist != null && pixlist.size() > 0){
					VwhFilmPix filmPix = (VwhFilmPix) pixlist.get(0);
					flvpath = filmPix.getFlvpath();
				}
				request.setAttribute("flvpath", flvpath);
				String playurl="http://"+vwhComputerInfo.getIp()+":"+vwhComputerInfo.getPort()+"/upload/"+flvpath;
				request.setAttribute("playurl", playurl);
                return mapping.findForward("play");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("error");
    }

    /**
     * 
      * 方法描述：播放解题微课
      * @param: 
      * @return: 
      * @version: 1.0
      * @author: 刘冬
      * @version: 2016-11-28 下午3:44:39
     */
    public void preview(ActionMapping actionMapping, ActionForm actionForm,
            HttpServletRequest request, HttpServletResponse response,
            Integer filmid) {
        try {
            // 微课视频id
            String pixid = Encode.nullToBlank(request.getParameter("pixid"));
            VwhFilmInfoManager filmmanager = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
            VwhFilmPixManager pixmanager = (VwhFilmPixManager) getBean("vwhFilmPixManager");
            VwhComputerInfoManager computermanager = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");

            VwhFilmInfo filminfo = filmmanager.getVwhFilmInfo(filmid);
            // 更新点击量
            Integer hits = filminfo.getHits();
            filminfo.setHits(hits + 1);
            filmmanager.updateVwhFilmInfo(filminfo);

            VwhComputerInfo computerinfo = computermanager.getVwhComputerInfo(filminfo.getComputerid());
            VwhFilmPix filmpix = null;
            List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "filmid", "=",filminfo.getFilmid());
            List<VwhFilmPix> pixlist = pixmanager.getVwhFilmPixs(condition,"orderindex", 0);
            if ("".equals(pixid)) {
                filmpix = pixlist.get(0);
            } else {
                filmpix = pixmanager.getVwhFilmPix(pixid);
            }

            request.setAttribute("computerinfo", computerinfo);
            request.setAttribute("filmpix", filmpix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   /**
    * 购买课程微课
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-23 下午4:48:54 
   * @lastModified ; 2017-2-23 下午4:48:54 
   * @version ; 1.0 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
    */
    public ActionForward buy(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String userid = MpUtil.getUserid(request);
            if(!"1".equals(userid)){
                //根据用户学段查询数据
                SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
                SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
                request.setAttribute("sysUserInfo", sysUserInfo);
                
                String autopay = Encode.nullToBlank(request.getParameter("autopay"));//二维码扫码自动支付
                String coursefilmid = Encode.nullToBlank(request.getParameter("coursefilmid"));
                request.setAttribute("autopay", autopay);
                request.setAttribute("coursefilmid", coursefilmid);
                
                //通过userid获取openid，支付需要，如果没有，则退出登录
                SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
                SysUserAttention sysUserAttention = suam.getSysUserAttentionByUserid(Integer.valueOf(userid));
                if(sysUserAttention == null){
                    //用户没有对应的openid，需要重新注册
                    return mapping.findForward("welcome");
                }
                request.setAttribute("openid", sysUserAttention.getOpenid());
                
                EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
                EduCourseFilm eduCourseFilm = ecfm.getEduCourseFilm(coursefilmid);
                
                List<SearchModel> condition = new ArrayList<SearchModel>();
                //判断用户是否可直接观看当前课程，或已购买课程
                boolean canStudy = false;
				//如果课程是免费的，所有人都可以查看
				if(eduCourseFilm.getSellprice() > 0){
					//判断用户是否是专家教师、vip用户、已购买当前课程用户。用户如果存储多个课程班（即批次）中，只要有一个班级是vip，则在其他班级也能看课程学习
					EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
					condition.clear();
					SearchCondition.addCondition(condition, "courseid", "=", eduCourseFilm.getCourseid());
					SearchCondition.addCondition(condition, "userid", "=", sysUserInfo.getUserid());
					SearchCondition.addCondition(condition, "status", "=", "1");
					List courseUserList = ecum.getEduCourseUsers(condition, "courseuserid desc", 0);
					
					EduCourseUser ecu = null;
					String nowDate = DateTime.getDate();
					for(int i=0, size=courseUserList.size(); i<size; i++){
						ecu = (EduCourseUser) courseUserList.get(i);
						if("1".equals(ecu.getUsertype())){
							canStudy = true;
							break;
						}else if("1".equals(ecu.getVip())){
							 String startdate = ecu.getStartdate();
							 String enddate = ecu.getEnddate();
							int res1 = nowDate.compareTo(startdate);
							int res2 = nowDate.compareTo(enddate);
							if(res1 >=0 && res2 <=0 ){
								canStudy = true;
								break;
							}
						}
					}
					
					if(!canStudy){
						//普通用户，需要查询是否已购买当前课程
						EduCourseBuyManager ecbm = (EduCourseBuyManager) getBean("eduCourseBuyManager");
						condition.clear();
						SearchCondition.addCondition(condition, "coursefilmid", "=", Integer.valueOf(coursefilmid));
						SearchCondition.addCondition(condition, "userid", "=", sysUserInfo.getUserid());
						SearchCondition.addCondition(condition, "enddate", ">", DateTime.getDate());
						List courseBuyList = ecbm.getEduCourseBuys(condition, "", 0);
						if(courseBuyList != null && courseBuyList.size() > 0){
							canStudy = true;
						}
					}
				}else {
					canStudy = true;
				}
                
                if(canStudy){
                	//如果可以查看，直接跳转到查看播放界面
                	String url = "/weixinCourse.app?method=play&userid=" + DES.getEncryptPwd(userid) + "&coursefilmid=" + coursefilmid;
                	response.sendRedirect(url);
                	return null;
                }
                
                 //获取微课定价
                 if(eduCourseFilm.getPrice() == null){
                     request.setAttribute("promptinfo", "当前课程微课未定价！");
                     return mapping.findForward("scantips");
                 }
                 
                 VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
 				 VwhFilmInfo vwhFilmInfo = vfim.getVwhFilmInfo(eduCourseFilm.getFilmid());
 				 request.setAttribute("vwhFilmInfo", vwhFilmInfo);
 				
 				
                 EduCourseInfoManager ecim = (EduCourseInfoManager) getBean("eduCourseInfoManager");
                 EduCourseInfo eduCourseInfo = ecim.getEduCourseInfo(eduCourseFilm.getCourseid());
                 request.setAttribute("eduCourseInfo", eduCourseInfo);
                 
                 EduCourseColumnManager eccm = (EduCourseColumnManager) getBean("eduCourseColumnManager");
                 EduCourseColumn column = eccm.getEduCourseColumn(eduCourseFilm.getCoursecolumnid());
                 if(!"0000".equals(column.getParentno())){
                 	EduCourseColumn parent = eccm.getEduCourseColumnByParentno(eduCourseFilm.getCourseid().toString(), column.getParentno());
                 	request.setAttribute("parent", parent);
                 }
                 request.setAttribute("column", column);
                 
                 //获取用户当天支付密码输入错误次数
                 SysPayPasswordManager sppm = (SysPayPasswordManager) getBean("sysPayPasswordManager");
                 int errorcount = sppm.getCountSysPayPassword(userid);
                 if(errorcount < Constants.PAYPASSWORD_ERROR_COUNT){
                     request.setAttribute("paypassword", "1");
                 }else {
                     request.setAttribute("paypassword", "0");
                 }
                 
                 request.setAttribute("eduCourseFilm",eduCourseFilm);
                 return mapping.findForward("buy");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("error");
    }
    
    public ActionForward pay(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, HttpServletResponse response) {
        PrintWriter pw = null;
        String result = "0";
        try {
            String userid = MpUtil.getUserid(request);
            String coursefilmid = Encode.nullToBlank(request.getParameter("coursefilmid"));
            String paypwd = Encode.nullToBlank(request.getParameter("paypwd"));
            
            EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
            EduCourseFilm eduCourseFilm = ecfm.getEduCourseFilm(coursefilmid);
            
            //验证支付密码
            SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
            SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
            if(MD5.getEncryptPwd(paypwd).equals(sysUserInfo.getPaypassword()) || eduCourseFilm.getSellprice() == 0){
            	EduCourseInfoManager ecim = (EduCourseInfoManager) getBean("eduCourseInfoManager");
            	EduCourseInfo eduCourseInfo = ecim.getEduCourseInfo(eduCourseFilm.getCourseid());
            	
                //1、修改用户余额
                sysUserInfo.setMoney(sysUserInfo.getMoney() - eduCourseFilm.getSellprice());
                suim.updateSysUserInfo(sysUserInfo);
                //2、记录交易详情
                SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
                SysUserMoney sysUserMoney = new SysUserMoney();
                String title = "购买了《" + eduCourseInfo.getTitle() + "》的课程微课【" + eduCourseFilm.getTitle() + "】";
                String descript = "购买了《" + eduCourseInfo.getTitle()  + "》的课程微课【" + eduCourseFilm.getTitle() + "】";
                sysUserMoney.setTitle(title);
                sysUserMoney.setChangemoney(eduCourseFilm.getSellprice());
                sysUserMoney.setChangetype(-1);
                sysUserMoney.setLastmoney(sysUserInfo.getMoney());
                sysUserMoney.setUserid(Integer.valueOf(userid));
                sysUserMoney.setCreatedate(DateTime.getDate());
                sysUserMoney.setUserip(IpUtil.getIpAddr(request));
                sysUserMoney.setDescript(descript);
                summ.addSysUserMoney(sysUserMoney);
                //记录购买数据
                EduCourseBuyManager ecbm = (EduCourseBuyManager) getBean("eduCourseBuyManager");
                EduCourseBuy eduCourseBuy = new EduCourseBuy();
                eduCourseBuy.setCoursefilmid(eduCourseFilm.getCoursefilmid());
                eduCourseBuy.setCourseid(eduCourseInfo.getCourseid());
                eduCourseBuy.setPrice(eduCourseFilm.getPrice());
                eduCourseBuy.setDiscount(eduCourseFilm.getDiscount());
                eduCourseBuy.setSellprice(eduCourseFilm.getSellprice());
                eduCourseBuy.setCreatedate(DateTime.getDate());
                //有效期结束时间
                eduCourseBuy.setEnddate(getEnddate());
                eduCourseBuy.setUserid(Integer.valueOf(userid));
                ecbm.addEduCourseBuy(eduCourseBuy);
                
                //记录微课销量
                eduCourseFilm.setSellcount(eduCourseFilm.getSellcount() + 1);
                ecfm.updateEduCourseFilm(eduCourseFilm);
               
               //购买微课的同时，加入这个课程，并且默认进入最新班级
               EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
               List<SearchModel> condition = new ArrayList<SearchModel>();
               SearchCondition.addCondition(condition, "courseid", "=", eduCourseInfo.getCourseid());
               SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
               List list = ecum.getEduCourseUsers(condition, "", 1);
               if(list == null || list.size() == 0){
            	   //获取当前课程最新课程班批次
					EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
					condition.clear();
					SearchCondition.addCondition(condition, "courseid", "=", eduCourseFilm.getCourseid());
					SearchCondition.addCondition(condition, "status", "=", "1");
					List lst = eccm.getEduCourseClasss(condition, "enddate desc", 1);
					if(lst != null && lst.size() > 0){
						EduCourseClass eduCourseClass = (EduCourseClass) lst.get(0);
						//说明并未加入这个课程
		                EduCourseUser model = new EduCourseUser();
		                model.setCourseclassid(eduCourseClass.getCourseclassid());
		                model.setCourseid(eduCourseInfo.getCourseid());
		                model.setUserid(Integer.valueOf(userid));
		                model.setStatus("1");
		                model.setCreatedate(DateTime.getDate());
		                model.setUsertype("3");
		                model.setVip("0");
		                ecum.addEduCourseUser(model);
					}else {
						//当前课程没有课程班
					}
               }
               
                result = "ok";
                
                addLog(request, descript, sysUserInfo);
            }else {
                //支付密码输入错误
                SysPayPasswordManager sppm = (SysPayPasswordManager) getBean("sysPayPasswordManager");
                SysPayPassword spp = new SysPayPassword();
                spp.setUserid(Integer.valueOf(userid));
                spp.setUserip(IpUtil.getIpAddr(request));
                spp.setCreatedate(DateTime.getDate());
                spp.setPassword(paypwd);
                sppm.addSysPayPassword(spp);
                
                int errorcount = sppm.getCountSysPayPassword(userid);
                result = errorcount + "";
            }
            
            pw = response.getWriter();
            pw.write(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            pw = null;
        }

        return null;
    }
    
  //获取购买的解题微课有效时间
  	private String getEnddate(){
  		int validity_time =  Integer.valueOf(PublicResourceBundle.getProperty("system","validity.time")).intValue();//默认2年
  		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  		Calendar now = Calendar.getInstance();  
          now.setTime(new Date());  
          now.set(Calendar.DATE, now.get(Calendar.DATE) + validity_time); 
          Date nowDate = now.getTime();
          return format.format(nowDate);
  	}
    
    /*
     * 系统日志记录
     */
    private void addLog(HttpServletRequest request, String descript, SysUserInfo sysUserInfo) {
        SysUserLogManager manager = (SysUserLogManager)getBean("sysUserLogManager");
        Integer unitid = sysUserInfo.getUnitid();
    
        SysUserLog model = new SysUserLog();
        model.setCreatedate(DateTime.getDate());
        model.setDescript(descript);
        model.setUserip(IpUtil.getIpAddr(request));
        model.setSysUserInfo(sysUserInfo);
        model.setUnitid(unitid);
        model.setLogtype("0");
        
        manager.addSysUserLog(model);
    }
    
  //首页课程
    public void getCourseInfoByAjax(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
        try {
            String userid=Encode.nullToBlank(request.getParameter("userid"));
            String num=Encode.nullToBlank(request.getParameter("pagenum"));
            String title=Encode.nullToBlank(request.getParameter("keywords"));
            
            int pagesize=10;
            int pagenum=Integer.parseInt(num);
            int pagecount=pagesize*pagenum;
            
            EduCourseInfoManager manager=(EduCourseInfoManager)getBean("eduCourseInfoManager");
            List courList = null;
            List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "title", "like", "%"+ title + "%");
            SearchCondition.addCondition(condition, "status", "=", "1");
            courList = manager.getPageEduCourseInfos(condition, "", pagecount, pagesize).getDatalist();
            
            StringBuffer result=new StringBuffer();
            for (int i=0;i<courList.size();i++) {
                EduCourseInfo model = (EduCourseInfo) courList.get(i);
                ///weixinCourse.app?method=courseFilmList&userid="+userid+"&courseid="+courseUser.getCourseid()
                result.append("<a href=\"/weixinCourse.app?method=courseFilmList&userid="+userid+"&courseid="+model.getCourseid()+"\"><div class=\"listen_main_01\">"+
                                    "<img src=\"/upload/"+model.getSketch()+"\" onerror=\"javascript:this.src='/weixin/images/img07.png'\"/>"+
                                    "<p>"+model.getTitle()+"</p>"+
                              "</div></a>");
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 底部，课程
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-2-24 下午1:20:12 
    * @lastModified ; 2017-2-24 下午1:20:12 
    * @version ; 1.0 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
     */
    public ActionForward getCourseList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response){
        try {
            String userid = MpUtil.getUserid(request);
            if(!"1".equals(userid)){
                SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
                request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
                request.setAttribute("keywords", Encode.nullToBlank(request.getParameter("keywords")));
                return mapping.findForward("courselist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("error");    
    }
    
   /**
    * 首页搜索
   * @author ; liud 
   * @Description : TODO 
   * @CreateDate ; 2017-2-24 下午3:27:16 
   * @lastModified ; 2017-2-24 下午3:27:16 
   * @version ; 1.0 
   * @param mapping
   * @param form
   * @param request
   * @param response
   * @return
    */
    public ActionForward toSearch(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            String userid = MpUtil.getUserid(request);
            if (!"1".equals(userid)) {
                //根据用户学段查询数据
                SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
                SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
                String usertype = "1";//老师
                if("2".equals(sysUserInfo.getUsertype())){//学生
                    usertype = "2";
                }
                request.setAttribute("usertype", usertype);
                String keywords = Encode.nullToBlank(request.getParameter("keywords"));
                String startcount = Encode.nullToBlank(request.getParameter("startcount"));
                if("".equals(startcount)){
                    startcount="0";
                }
                String searchtype = Encode.nullToBlank(request.getParameter("searchtype"));
                if ("".equals(searchtype)) {
                    searchtype = "0";
                }
                request.setAttribute("searchtype", searchtype);
                request.setAttribute("keywords", keywords);

                // 创建用户历史搜索关键词记录
                SysUserSearchKeywords userKeywords = new SysUserSearchKeywords();
                userKeywords.setKeywords(keywords);
                userKeywords.setSearchtype("1");
                userKeywords.setCreatedate(DateTime.getDate());
                userKeywords.setUserid(Integer.valueOf(userid));
                userKeywords.setUserip(IpUtil.getIpAddr(request));
                SysUserSearchKeywordsManager sskm = (SysUserSearchKeywordsManager) getBean("sysUserSearchKeywordsManager");
                sskm.addSysUserSearchKeywords(userKeywords);

                TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");

                List buycontentidList = new ArrayList();//购买的微课id的集合
                List<SearchModel> condition = new ArrayList<SearchModel>();
                //1.usertype=1,vip=1,pice=0
                EduCourseUserManager userManager = (EduCourseUserManager) getBean("eduCourseUserManager");
                List list = userManager.getEduCourseUserByCourseid(null, Integer.valueOf(userid));
                //2.已购买的
                EduCourseBuyManager buyManager = (EduCourseBuyManager) getBean("eduCourseBuyManager");
                EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
                condition.clear();
                SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
                SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
                List buys = buyManager.getEduCourseBuys(condition, "buyid asc", 0);
                
                if(list != null && list.size() > 0){
                    for(int i=0, size=list.size(); i<size; i++){
                        Integer filmid = (Integer) list.get(i);
                            buycontentidList.add(filmid);
                    }
                }
                if(buys != null && buys.size() > 0){
                    EduCourseBuy buy = null;
                    for(int i=0, size=buys.size(); i<size; i++){
                        buy = (EduCourseBuy) buys.get(i);
                        Integer coursefilmid = buy.getCoursefilmid();
                        EduCourseFilm eduCourseFilm = ecfm.getEduCourseFilm(coursefilmid);
                            buycontentidList.add(eduCourseFilm.getFilmid());
                    }
                }
                request.setAttribute("buycontentidList", buycontentidList);
                
                
                PageUtil pageUtil = new PageUtil(request);
                String sorderindex = "a.coursefilmid asc";
                if(!"".equals(pageUtil.getOrderindex())){
                    sorderindex = pageUtil.getOrderindex();
                }
                PageList pageList = ecfm.getEduCourseFilmsOfPage("", "", "1", keywords, sorderindex, Integer.valueOf(startcount),pageUtil.getPageSize());
                request.setAttribute("startcount",pageList.getStartOfNextPage());
                List filmList = pageList.getDatalist();
                request.setAttribute("filmList", filmList);

                return mapping.findForward("searchbookcontentlist");
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mapping.findForward("error");
    }
}