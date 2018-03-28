package com.wkmk.skin.action;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.util.HSSFColor.PALE_BLUE;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.des.DesUtil;
import com.util.encrypt.DES;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseCollect;
import com.wkmk.edu.bo.EduCourseColumn;
import com.wkmk.edu.bo.EduCourseFile;
import com.wkmk.edu.bo.EduCourseFileDownload;
import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduCourseStudy;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.service.EduBookInfoManager;
import com.wkmk.edu.service.EduCourseBuyManager;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseCollectManager;
import com.wkmk.edu.service.EduCourseColumnManager;
import com.wkmk.edu.service.EduCourseCommentManager;
import com.wkmk.edu.service.EduCourseFileDownloadManager;
import com.wkmk.edu.service.EduCourseFileManager;
import com.wkmk.edu.service.EduCourseFilmManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduCourseStudyManager;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.IpUtil;
import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmPixManager;

/**
 * <p>
 * Description: 作业宝模板001
 * </p>
 * 
 * @version 1.0
 */
public class Course01Action extends BaseAction {

	/**
	 * 首页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mappingurl = "/error.html";
		try {
			String coursetypeid = Encode.nullToBlank(request.getParameter("typeid"));
			EduCourseInfoManager manager = (EduCourseInfoManager) getBean("eduCourseInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "coursetypeid", "=", coursetypeid);
			SearchCondition.addCondition(condition, "status", "=", "1");
			List list = manager.getEduCourseInfos(condition, "courseno asc", 0);
			request.setAttribute("list", list);
			request.setAttribute("coursetypeid", coursetypeid);
			// 获取当前分类的教材课本
			EduBookInfoManager ebim = (EduBookInfoManager) getBean("eduBookInfoManager");
			condition.clear();
			SearchCondition.addCondition(condition, "coursetypeid", "=", coursetypeid);
			SearchCondition.addCondition(condition, "status", "=", "1");
			List bookList = ebim.getEduBookInfos(condition, "orderindex asc", 0);
			request.setAttribute("bookList", bookList);
			// mappingurl = "/skin/course01/jsp/index" + coursetypeid + ".jsp";
			mappingurl = "/skin/course01/jsp/index-" + coursetypeid + ".jsp";
		} catch (Exception e) {
			mappingurl = "/error.html";
		}
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}

	/**
	 * 课程展示页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mappingurl = "/error.html";
		try {
			String courseid = Encode.nullToBlank(request.getParameter("courseid"));
			EduCourseInfoManager manager = (EduCourseInfoManager) getBean("eduCourseInfoManager");
			EduCourseInfo model = manager.getEduCourseInfo(courseid);
			if (!"1".equals(model.getStatus())) {
				// 课程未审核通过，需要审核通过才能在前台显示
				request.setAttribute("promptinfo", "当前课程【" + model.getTitle() + "】暂未通过审核，请等待审核通过后再访问查看。");
				return mapping.findForward("failure");
			}
			model.setHits(model.getHits() + 1);
			manager.updateEduCourseInfo(model);
			request.setAttribute("model", model);
			SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
			SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getSysUserInfo().getUserid());
			request.setAttribute("detail", detail);
			EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
			int students = ecum.getStudentCounts(courseid);
			request.setAttribute("students", students);
			mappingurl = "/skin/course01/jsp/course_index.jsp";
		} catch (Exception e) {
			mappingurl = "/error.html";
		}
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}

	/**
	 * 课程详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward coursedescript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mappingurl = "/error.html";
		try {
			String courseid = Encode.nullToBlank(request.getParameter("courseid"));
			EduCourseInfoManager manager = (EduCourseInfoManager) getBean("eduCourseInfoManager");
			EduCourseInfo model = manager.getEduCourseInfo(courseid);
			request.setAttribute("model", model);
			mappingurl = "/skin/course01/jsp/course_descript.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			mappingurl = "/error.html";
		}
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}

	/**
	 * 课程详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward coursecolumn(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mappingurl = "/error.html";
		try {
			String courseid = Encode.nullToBlank(request.getParameter("courseid"));
			// 获取课程目录和课程视频
			getCourseColumnAndFilm(courseid, request);
			mappingurl = "/skin/course01/jsp/course_column.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			mappingurl = "/error.html";
		}
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}

	private void getCourseColumnAndFilm(String courseid, HttpServletRequest request) {
		// 课程发布后，课程目录不能修改，只能是超管协助帮忙修订，超管后台修订可清空缓存
		// List columnList = (List) CacheUtil.getObject("EduCourseColumn_List_" + courseid);
		// if(columnList == null || columnList.size() == 0){
		EduCourseColumnManager eccm = (EduCourseColumnManager) getBean("eduCourseColumnManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
		SearchCondition.addCondition(condition, "status", "=", "1");
		List list_all = eccm.getEduCourseColumns(condition, "columnno asc", 0);// 所有栏目
		// columnList = new ArrayList();
		// Map map = new HashMap();
		EduCourseColumn ecc = null;
		// for(int i=0, size=list.size(); i<size; i++){
		// ecc = (EduCourseColumn) list.get(i);
		// if("0000".equals(ecc.getParentno())){
		// map.put(ecc.getColumnno(), ecc.getTitle());
		// }else {
		// ecc.setFlags(map.get(ecc.getParentno()).toString());
		// columnList.add(ecc);
		// }
		// }
		List parentList = new ArrayList();// 所有单元级别栏目信息
		Map childMap = new HashMap();// 所有节级别栏目信息
		for (int i = 0, size = list_all.size(); i < size; i++) {
			ecc = (EduCourseColumn) list_all.get(i);
			if ("0000".equals(ecc.getParentno())) {
				parentList.add(ecc);
			} else {
				if (childMap.get(ecc.getParentno()) == null) {
					List lst = new ArrayList();
					lst.add(ecc);
					childMap.put(ecc.getParentno(), lst);
				} else {
					List lst = (List) childMap.get(ecc.getParentno());
					lst.add(ecc);
					childMap.put(ecc.getParentno(), lst);
				}
			}
		}
		// CacheUtil.putObject("EduCourseColumn_List_" + courseid, columnList, 24*60*60);//缓存一天
		// }
		request.setAttribute("parentList", parentList);
		request.setAttribute("childMap", childMap);
		Map courseFilmMap = (Map) CacheUtil.getObject("EduCourseFilm_Map_" + courseid);
		if (courseFilmMap == null || courseFilmMap.isEmpty() || courseFilmMap.size() == 0) {
			EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
			condition.clear();
			SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
			SearchCondition.addCondition(condition, "status", "=", "1");
			List list = ecfm.getEduCourseFilms(condition, "qrcodeno asc", 0);
			courseFilmMap = new HashMap();
			EduCourseFilm ecf = null;
			for (int i = 0, size = list.size(); i < size; i++) {
				ecf = (EduCourseFilm) list.get(i);
				if (courseFilmMap.get(ecf.getCoursecolumnid()) == null) {
					List lst = new ArrayList();
					lst.add(ecf);
					courseFilmMap.put(ecf.getCoursecolumnid(), lst);
				} else {
					List lst = (List) courseFilmMap.get(ecf.getCoursecolumnid());
					lst.add(ecf);
					courseFilmMap.put(ecf.getCoursecolumnid(), lst);
				}
			}
			CacheUtil.putObject("EduCourseFilm_Map_" + courseid, courseFilmMap, 24 * 60 * 60);// 缓存一天
			CacheUtil.putObject("EduCourseFilm_List_" + courseid, list, 24 * 60 * 60);
		}
		request.setAttribute("courseFilmMap", courseFilmMap);
	}

	private void getCourseColumnAndFilmPlay(String courseid, HttpServletRequest request) {
		// 课程发布后，课程目录不能修改，只能是超管协助帮忙修订，超管后台修订可清空缓存
		List columnList = (List) CacheUtil.getObject("EduCourseColumn_List_" + courseid);
		if (columnList == null || columnList.size() == 0) {
			EduCourseColumnManager eccm = (EduCourseColumnManager) getBean("eduCourseColumnManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
			SearchCondition.addCondition(condition, "status", "=", "1");
			List list = eccm.getEduCourseColumns(condition, "columnno asc", 0);// 所有栏目
			columnList = new ArrayList();
			Map map = new HashMap();
			EduCourseColumn ecc = null;
			for (int i = 0, size = list.size(); i < size; i++) {
				ecc = (EduCourseColumn) list.get(i);
				if ("0000".equals(ecc.getParentno())) {
					map.put(ecc.getColumnno(), ecc.getTitle());
				} else {
					ecc.setFlags(map.get(ecc.getParentno()).toString());
					columnList.add(ecc);
				}
			}
			if (columnList != null && columnList.size() > 0) {
				CacheUtil.putObject("EduCourseColumn_List_" + courseid, columnList, 24 * 60 * 60);// 缓存一天
			} else {
				for (int i = 0, size = list.size(); i < size; i++) {
					ecc = (EduCourseColumn) list.get(i);
					ecc.setFlags("");
					columnList.add(ecc);
				}
				CacheUtil.putObject("EduCourseColumn_List_" + courseid, columnList, 24 * 60 * 60);// 缓存一天
			}
		}
		request.setAttribute("columnList", columnList);
		Map courseFilmMap = (Map) CacheUtil.getObject("EduCourseFilm_Map_" + courseid);
		if (courseFilmMap == null || courseFilmMap.isEmpty() || courseFilmMap.size() == 0) {
			EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
			SearchCondition.addCondition(condition, "status", "=", "1");
			List list = ecfm.getEduCourseFilms(condition, "qrcodeno asc", 0);
			courseFilmMap = new HashMap();
			EduCourseFilm ecf = null;
			for (int i = 0, size = list.size(); i < size; i++) {
				ecf = (EduCourseFilm) list.get(i);
				if (courseFilmMap.get(ecf.getCoursecolumnid()) == null) {
					List lst = new ArrayList();
					lst.add(ecf);
					courseFilmMap.put(ecf.getCoursecolumnid(), lst);
				} else {
					List lst = (List) courseFilmMap.get(ecf.getCoursecolumnid());
					lst.add(ecf);
					courseFilmMap.put(ecf.getCoursecolumnid(), lst);
				}
			}
			CacheUtil.putObject("EduCourseFilm_Map_" + courseid, courseFilmMap, 24 * 60 * 60);// 缓存一天
			CacheUtil.putObject("EduCourseFilm_List_" + courseid, list, 24 * 60 * 60);
		}
		request.setAttribute("courseFilmMap", courseFilmMap);
	}

	/**
	 * 课程详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward coursecomment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String mappingurl = "/error.html";
		try {
			String courseid = Encode.nullToBlank(request.getParameter("courseid"));
			String pingjia = Encode.nullToBlank(request.getParameter("pingjia"));// 0全部，1好评，2中评，3差评
			if ("".equals(pingjia)) {
				pingjia = "0";
			}
			EduCourseCommentManager manager = (EduCourseCommentManager) getBean("eduCourseCommentManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
			SearchCondition.addCondition(condition, "status", "=", "1");
			if ("1".equals(pingjia)) {
				SearchCondition.addCondition(condition, "score", ">", 3);
			} else if ("2".equals(pingjia)) {
				SearchCondition.addCondition(condition, "score", ">", 1);
				SearchCondition.addCondition(condition, "score", "<=", 3);
			} else if ("3".equals(pingjia)) {
				SearchCondition.addCondition(condition, "score", "<=", 1);
			}
			PageUtil pageUtil = new PageUtil(request);
			PageList pagelist = manager.getPageEduCourseComments(condition, "createdate desc", pageUtil.getStartCount(), 10);
			request.setAttribute("pagelist", pagelist);
			request.setAttribute("courseid", courseid);
			request.setAttribute("pingjia", pingjia);
			// 获取当前课程所有评价分数
			List scoreList = manager.getEduCourseCommentScores(courseid);
			int scorecount = 0;// 总评论数
			int score1 = 0;// 好评
			int score2 = 0;// 中评
			int score3 = 0;// 差评
			Integer score0 = null;
			for (int i = 0, size = scoreList.size(); i < size; i++) {
				score0 = (Integer) scoreList.get(i);
				if (score0 > 3) {
					score1++;
				} else if (score0 > 1 && score0 <= 3) {
					score2++;
				} else if (score0 <= 1) {
					score3++;
				}
				scorecount++;
			}
			String haoping = "暂无评价";
			if (scorecount > 0) {
				int hp = score1 * 100 / scorecount;
				haoping = "<a>" + hp + "%</a>好评率";
			}
			request.setAttribute("scorecount", scorecount);
			request.setAttribute("score1", score1);
			request.setAttribute("score2", score2);
			request.setAttribute("score3", score3);
			request.setAttribute("haoping", haoping);
			mappingurl = "/skin/course01/jsp/course_comment.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			mappingurl = "/error.html";
		}
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}

	/**
	 * 播放
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward play(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String mappingurl = "/error.html";
		try {
			String courseid = Encode.nullToBlank(request.getParameter("siteid"));
			String coursefilmid = Encode.nullToBlank(request.getParameter("objid"));
			EduCourseInfoManager ecim = (EduCourseInfoManager) getBean("eduCourseInfoManager");
			EduCourseInfo model = ecim.getEduCourseInfo(courseid);
			if (!"1".equals(model.getStatus())) {
				// 课程未审核通过，需要审核通过才能在前台显示
				request.setAttribute("promptinfo", "当前课程【" + model.getTitle() + "】暂未通过审核，请等待审核通过后再访问查看。");
				return mapping.findForward("failure");
			}
			// 必须先登录才能观看视频
			HttpSession session = request.getSession();
			SysUserInfo sysUserInfo = (SysUserInfo) session.getAttribute("s_sysuserinfo");
			if (sysUserInfo != null && sysUserInfo.getUserid() != null) {
				EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
				EduCourseStudyManager ecsm = (EduCourseStudyManager) getBean("eduCourseStudyManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 判断用户是否已购买当前课程，如果没有，给出二维码提示在线支付购买
				EduCourseFilm eduCourseFilm = null;
				if ("0".equals(coursefilmid)) {
					// 通过点击课程的开始学习按钮跳转过来，默认进入上次最后学习课程，没有记录则默认学习第一个课程视频
					// condition.clear();
					// SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
					// SearchCondition.addCondition(condition, "userid", "=", sysUserInfo.getUserid());
					// List studyList = ecsm.getEduCourseStudys(condition, "createdate desc", 1);
					// if(studyList != null && studyList.size() > 0){
					// EduCourseStudy eduCourseStudy = (EduCourseStudy) studyList.get(0);
					// coursefilmid = eduCourseStudy.getCoursefilmid().toString();
					// eduCourseFilm = ecfm.getEduCourseFilm(coursefilmid);
					// }else {
					// 获取课程第一个视频课程
					List courseFilmList = (List) CacheUtil.getObject("EduCourseFilm_List_" + courseid);
					if (courseFilmList == null || courseFilmList.size() == 0) {
						condition.clear();
						SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
						SearchCondition.addCondition(condition, "status", "=", "1");
						courseFilmList = ecfm.getEduCourseFilms(condition, "qrcodeno asc", 0);
						CacheUtil.putObject("EduCourseFilm_List_" + courseid, courseFilmList, 24 * 60 * 60);
					}
					if (courseFilmList == null || courseFilmList.size() == 0) {
						// 当前课程下面没有任何视频课程
						request.setAttribute("promptinfo", "当前课程【" + model.getTitle() + "】没有视频课程供学习！");
						return mapping.findForward("failure");
					}
					eduCourseFilm = (EduCourseFilm) courseFilmList.get(0);
					coursefilmid = eduCourseFilm.getCoursefilmid().toString();
					// }
				} else {
					// 点击课程视频学习课程视频内容
					eduCourseFilm = ecfm.getEduCourseFilm(coursefilmid);
				}
				boolean canStudy = false;
				String isOkDown = "0";// 是否可以下载资料
				// 如果课程是免费的，所有人都可以查看
				if (eduCourseFilm.getSellprice() > 0) {
					// 判断用户是否是专家教师、vip用户、已购买当前课程用户。用户如果存储多个课程班（即批次）中，只要有一个班级是vip，则在其他班级也能看课程学习
					EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
					condition.clear();
					SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
					SearchCondition.addCondition(condition, "userid", "=", sysUserInfo.getUserid());
					SearchCondition.addCondition(condition, "status", "=", "1");
					List courseUserList = ecum.getEduCourseUsers(condition, "courseuserid desc", 0);
					EduCourseUser ecu = null;
					String nowDate = DateTime.getDate();
					for (int i = 0, size = courseUserList.size(); i < size; i++) {
						ecu = (EduCourseUser) courseUserList.get(i);
						if ("1".equals(ecu.getVip())) {
							String startdate = ecu.getStartdate();
							String enddate = ecu.getEnddate();
							int res1 = nowDate.compareTo(startdate);
							int res2 = nowDate.compareTo(enddate);
							if (res1 >= 0 && res2 <= 0) {
								canStudy = true;
								break;
							}
						}
					}
					// 判断用户是否为助教或者专家
					for (int i = 0, size = courseUserList.size(); i < size; i++) {
						ecu = (EduCourseUser) courseUserList.get(i);
						if ("1".equals(ecu.getUsertype()) || "2".equals(ecu.getUsertype())) {
							isOkDown = "1";
							break;
						}
					}
					if (!canStudy) {
						// 普通用户，需要查询是否已购买当前课程
						EduCourseBuyManager ecbm = (EduCourseBuyManager) getBean("eduCourseBuyManager");
						condition.clear();
						SearchCondition.addCondition(condition, "coursefilmid", "=", Integer.valueOf(coursefilmid));
						SearchCondition.addCondition(condition, "userid", "=", sysUserInfo.getUserid());
						SearchCondition.addCondition(condition, "enddate", ">", DateTime.getDate());
						List courseBuyList = ecbm.getEduCourseBuys(condition, "", 0);
						if (courseBuyList != null && courseBuyList.size() > 0) {
							canStudy = true;
						}
					}
				} else {
					isOkDown = "1";
					canStudy = true;
					// 如果是第一次学习免费课程，需要加入课程班级
					EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
					condition.clear();
					SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
					SearchCondition.addCondition(condition, "userid", "=", sysUserInfo.getUserid());
					List courseUserList = ecum.getEduCourseUsers(condition, "courseuserid desc", 0);
					if (courseUserList == null || courseUserList.size() == 0) {
						// 获取当前课程最新课程班批次
						EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
						condition.clear();
						SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
						SearchCondition.addCondition(condition, "status", "=", "1");
						List list = eccm.getEduCourseClasss(condition, "enddate desc", 1);
						if (list != null && list.size() > 0) {
							EduCourseClass eduCourseClass = (EduCourseClass) list.get(0);
							EduCourseUser courseUser = new EduCourseUser();
							courseUser.setCourseclassid(eduCourseClass.getCourseclassid());
							courseUser.setCourseid(Integer.valueOf(courseid));
							courseUser.setUserid(sysUserInfo.getUserid());
							courseUser.setStatus("1");
							courseUser.setCreatedate(DateTime.getDate());
							courseUser.setUsertype("3");
							courseUser.setVip("0");
							ecum.addEduCourseUser(courseUser);
							eduCourseClass.setUsercount(eduCourseClass.getUsercount() + 1);
							eccm.updateEduCourseClass(eduCourseClass);
							isOkDown = "1";
						}
					}
				}
				request.setAttribute("isOkDown", isOkDown);
				if (canStudy) {
					// 可以学习，查询微课视频路径
					model.setStudys(model.getStudys() + 1);
					ecim.updateEduCourseInfo(model);
					eduCourseFilm.setHits(eduCourseFilm.getHits() + 1);
					ecfm.updateEduCourseFilm(eduCourseFilm);
					EduCourseStudy eduCourseStudy = new EduCourseStudy();
					eduCourseStudy.setCourseid(model.getCourseid());
					eduCourseStudy.setCoursefilmid(eduCourseFilm.getCoursefilmid());
					eduCourseStudy.setUserid(sysUserInfo.getUserid());
					eduCourseStudy.setUserip(IpUtil.getIpAddr(request));
					eduCourseStudy.setCreatedate(DateTime.getDate());
					ecsm.addEduCourseStudy(eduCourseStudy);
					VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
					VwhFilmInfo vwhFilmInfo = vfim.getVwhFilmInfo(eduCourseFilm.getFilmid());
					request.setAttribute("vwhFilmInfo", vwhFilmInfo);
					VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
					condition.clear();
					SearchCondition.addCondition(condition, "filmid", "=", eduCourseFilm.getFilmid());
					List pixlist = vfpm.getVwhFilmPixs(condition, "orderindex", 0);
					String pixid = "";
					String mp4urlString="";
					if (pixlist != null && pixlist.size() > 0) {
						VwhFilmPix filmPix = (VwhFilmPix) pixlist.get(0);
						pixid = filmPix.getPixid().toString();
						mp4urlString=filmPix.getFlvpath();
					}
					request.setAttribute("playid", DES.getEncryptPwd(pixid));
					VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
					VwhComputerInfo vci = vcim.getVwhComputerInfo(vwhFilmInfo.getComputerid());
					
					String playurl="http://"+vci.getIp()+":"+vci.getPort()+"/upload/"+mp4urlString;
					request.setAttribute("playurl", DesUtil.encrypt(playurl, "cd4b635b494306cddf6a6e74a7b0b4d8"));
					request.setAttribute("fdm", vci.getIp() + ":" + vci.getPort());
				}
				request.setAttribute("canStudy", canStudy);
				request.setAttribute("eduCourseFilm", eduCourseFilm);
				request.setAttribute("model", model);
				// 获取课程目录和课程视频
				getCourseColumnAndFilmPlay(courseid, request);
				// 获取当前课程所有资源，呈现方式和课程视频一致
				Map courseFileMap = (Map) CacheUtil.getObject("EduCourseFile_Map_" + courseid);
				if (courseFileMap == null || courseFileMap.isEmpty() || courseFileMap.size() == 0) {
					EduCourseFileManager manager = (EduCourseFileManager) getBean("eduCourseFileManager");
					condition.clear();
					SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
					List courseFileList = manager.getEduCourseFiles(condition, "filename asc", 0);
					courseFileMap = new HashMap();
					EduCourseFile ecf = null;
					for (int i = 0, size = courseFileList.size(); i < size; i++) {
						ecf = (EduCourseFile) courseFileList.get(i);
						if (courseFileMap.get(ecf.getCoursecolumnid()) == null) {
							List lst = new ArrayList();
							lst.add(ecf);
							courseFileMap.put(ecf.getCoursecolumnid(), lst);
						} else {
							List lst = (List) courseFileMap.get(ecf.getCoursecolumnid());
							lst.add(ecf);
							courseFileMap.put(ecf.getCoursecolumnid(), lst);
						}
					}
					CacheUtil.putObject("EduCourseFile_Map_" + courseid, courseFileMap, 60 * 60);// 缓存1小时
				}
				request.setAttribute("courseFileMap", courseFileMap);
			} else {
				String url = "/plogin.do?method=slogin&redirecturl=/course-play-" + courseid + "-" + coursefilmid + ".htm";
				response.sendRedirect(url);
				return null;
			}
			mappingurl = "/skin/course01/jsp/play_index.jsp";
		} catch (Exception e) {
			mappingurl = "/error.html";
		}
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}

	/**
	 * 视频播放地址转换
	 */
	public ActionForward url(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		StringBuffer vurl = new StringBuffer();
		try {
			String vid = Encode.nullToBlank(request.getParameter("vid"));
			if (vid != null && !"".equals(vid)) {
				String pixid = DES.getDecryptPwd(vid);
				if (pixid != null && !"".equals(pixid)) {
					VwhFilmPixManager manager = (VwhFilmPixManager) getBean("vwhFilmPixManager");
					VwhFilmPix model = manager.getVwhFilmPix(pixid);
					if (model.getFlvpath() != null && model.getFlvpath().startsWith("http://")) {
						vurl.append(model.getFlvpath());
					} else {
						VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
						VwhFilmInfo vfi = vfim.getVwhFilmInfo(model.getFilmid());
						VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
						VwhComputerInfo vci = vcim.getVwhComputerInfo(vfi.getComputerid());
						vurl.append("http://").append(vci.getIp());
						if (!"80".equals(vci.getPort())) {
							vurl.append(":").append(vci.getPort());
						}
						vurl.append("/upload/" + model.getFlvpath());
					}
				}
			}
			out = response.getWriter();
			out.print(vurl.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 收藏课程
	 */
	public ActionForward collects(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String courseid = Encode.nullToBlank(request.getParameter("courseid"));
		EduCourseCollectManager manager = (EduCourseCollectManager) getBean("eduCourseCollectManager");
		String responseStr = "";
		HttpSession session = request.getSession();
		Integer userid = (Integer) session.getAttribute("s_userid");
		if (userid == null) {
			responseStr = "-1";
		} else {
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "userid", "=", userid);
			SearchCondition.addCondition(condition, "eduCourseInfo.courseid", "=", Integer.valueOf(courseid));
			List list = manager.getEduCourseCollects(condition, "", 1);
			if (list != null && list.size() > 0) {
				responseStr = "-1";
			} else {
				EduCourseInfoManager ecim = (EduCourseInfoManager) getBean("eduCourseInfoManager");
				EduCourseInfo eduCourseInfo = ecim.getEduCourseInfo(courseid);
				eduCourseInfo.setCollects(eduCourseInfo.getCollects() + 1);
				ecim.updateEduCourseInfo(eduCourseInfo);
				EduCourseCollect collect = new EduCourseCollect();
				collect.setEduCourseInfo(eduCourseInfo);
				collect.setUserid(userid);
				collect.setCreatedate(DateTime.getDate());
				manager.addEduCourseCollect(collect);
				responseStr = eduCourseInfo.getCollects().toString();
			}
		}
		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(responseStr);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 资源下载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String fileid = Encode.nullToBlank(request.getParameter("objid"));
		EduCourseFileManager manager = (EduCourseFileManager) getBean("eduCourseFileManager");
		EduCourseFile file = manager.getEduCourseFile(fileid);
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String filepath = file.getFilepath();
		String filename = file.getFilename();
		String fileext = file.getFileext();
		filename = filename.replaceAll("\\s", "");// 把字符串中的所有空白字符替换为空字符串
		filename = filename + "." + fileext;
		String fullpathname = null;
		fullpathname = realpath + "/upload/" + filepath;
		if (!FileUtil.isExistDir(fullpathname)) {// 不存在当前文件
			request.setAttribute("promptinfo", "下载失败，当前文档实体文件不存在!");
			return mapping.findForward("failure");
		} else {
			file.setDownloads(file.getDownloads() + 1);
			manager.updateEduCourseFile(file);
			Integer userid = (Integer) request.getSession().getAttribute("s_userid");
			if (userid != null) {
				EduCourseFileDownloadManager ecfdm = (EduCourseFileDownloadManager) getBean("eduCourseFileDownloadManager");
				EduCourseFileDownload download = new EduCourseFileDownload();
				download.setFileid(Integer.valueOf(fileid));
				download.setUserid(userid);
				download.setCreatedate(DateTime.getDate());
				ecfdm.addEduCourseFileDownload(download);
			}
			InputStream is = null;
			OutputStream os = null;
			try {
				is = new FileInputStream(fullpathname);
				os = response.getOutputStream();// 取得输出流
				response.reset();// 清空输出流
				response.setContentType("application/x-download; charset=gb2312");
				response.setContentType("bin");
				response.setHeader("Content-disposition", "attachment; filename=" + new String(filename.getBytes("gb2312"), "iso8859-1"));// 设定输出文件头
				byte[] buffer = new byte[1024];
				int byteread = 0;
				while ((byteread = is.read(buffer)) > 0) {
					os.write(buffer, 0, byteread);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (os != null) {
						os.close();
						os = null;
					}
					if (is != null) {
						is.close();
						is = null;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return mapping.findForward(null);
		}
	}

	/**
	 * 获取客户端扫码支付购买状态
	 */
	public ActionForward getScanBuyCourseFilmStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String status = "";
		try {
			String coursefilmid = Encode.nullToBlank(request.getParameter("fid"));
			Integer userid = (Integer) request.getSession().getAttribute("s_userid");
			if (userid != null) {
				EduCourseBuyManager manager = (EduCourseBuyManager) getBean("eduCourseBuyManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "coursefilmid", "=", Integer.valueOf(coursefilmid));
				SearchCondition.addCondition(condition, "userid", "=", userid);
				SearchCondition.addCondition(condition, "enddate", ">", DateTime.getDate());
				List courseBuyList = manager.getEduCourseBuys(condition, "", 0);
				if (courseBuyList != null && courseBuyList.size() > 0) {
					status = "1";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		try {
			out = response.getWriter();
			if (status != null && !"".equals(status)) {
				out.print(status);
			} else {
				out.print("");
			}
		} catch (Exception e) {
		}
		return null;
	}
}
