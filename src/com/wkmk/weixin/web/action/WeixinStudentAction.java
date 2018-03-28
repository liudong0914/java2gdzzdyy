package com.wkmk.weixin.web.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.Base64Utils;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.image.ImageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.UUID;
import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentTask;
import com.wkmk.tk.bo.TkBookContentWatch;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkClassInfo;
import com.wkmk.tk.bo.TkClassPassword;
import com.wkmk.tk.bo.TkClassUpload;
import com.wkmk.tk.bo.TkClassUser;
import com.wkmk.tk.bo.TkPaperAnswer;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsSimilarWatch;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.bo.TkUserErrorQuestion;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookContentQuestionManager;
import com.wkmk.tk.service.TkBookContentTaskManager;
import com.wkmk.tk.service.TkBookContentWatchManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkClassInfoManager;
import com.wkmk.tk.service.TkClassPasswordManager;
import com.wkmk.tk.service.TkClassUploadManager;
import com.wkmk.tk.service.TkClassUserManager;
import com.wkmk.tk.service.TkPaperAnswerManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkQuestionsFilmManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsSimilarManager;
import com.wkmk.tk.service.TkQuestionsSimilarWatchManager;
import com.wkmk.tk.service.TkUserErrorQuestionManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;
import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.bo.VwhFilmWatch;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmPixManager;
import com.wkmk.vwh.service.VwhFilmWatchManager;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 微信服务账户绑定接口
 * 
 * @version 1.0
 */
public class WeixinStudentAction extends BaseAction {

	/**
	 * 学生直接绑定作业本
	 */
	public ActionForward addBook(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			String scanResult = Encode.nullToBlank(request.getParameter("scanResult"));
			if (!"1".equals(userid)) {
				// 如果已经是通过扫一扫作业本则自动显示作业本信息，但是还是可以选择更换其他作业本【通过新注册跳转过来】
				//【取消二维码加密】
				//scanResult = Base64Utils.base64Decoder(scanResult);
				if (scanResult != null && scanResult.indexOf("TkBookInfo") != -1) {// 作业本
					String bookid = scanResult.split("=")[1];
					TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
					TkBookInfo tkBookInfo = tbim.getTkBookInfo(bookid);
					request.setAttribute("bookid", bookid);
					request.setAttribute("bookname", tkBookInfo.getTitle());
				} else {
					request.setAttribute("bookid", "");
					request.setAttribute("bookname", "选择作业本");
				}

				return mapping.findForward("addbook");
			} else {
				// 跳转到登陆页面【初步控制，扫一扫作业本和班级时信息携带着，其他暂不携带，不携带如果登录成功自动跳转到个人中心，如果是注册，则需要选择注册的作业本】
				String nickname = Base64Utils.base64Decoder(Encode.nullToBlank(request.getParameter("nickname")));
				String headimgurl = Encode.nullToBlank(request.getParameter("headimgurl"));
				String sex = Encode.nullToBlank(request.getParameter("sex"));
				String openid = Encode.nullToBlank(request.getParameter("openid"));
				request.setAttribute("nickname", nickname);
				request.setAttribute("headimgurl", headimgurl);
				request.setAttribute("sex", sex);
				request.setAttribute("openid", openid);
				request.setAttribute("scanResult", scanResult);
				return mapping.findForward("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 选择作业本
	 */
	public ActionForward selectBook(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			// 选择作业本，不能选择自己已经关联的作业本
			TkClassUserManager tcum = (TkClassUserManager) getBean("tkClassUserManager");
			List allbookids = tcum.getAllBookidByUserid(userid);

			String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
			String gradeid = Encode.nullToBlank(request.getParameter("gradeid"));
			String title = Encode.nullToBlank(request.getParameter("title"));
			TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			if (allbookids != null && allbookids.size() > 0) {
				StringBuffer bookids = new StringBuffer();
				for (int i = 0, size = allbookids.size(); i < size; i++) {
					bookids.append(" and a.bookid<>").append(allbookids.get(i));
				}
				SearchCondition.addCondition(condition, "status", "=", "1'" + bookids.toString() + " and 1='1");
			} else {
				SearchCondition.addCondition(condition, "status", "=", "1");
			}
			if (!"".equals(subjectid)) {
				SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
			}
			if (!"".equals(gradeid)) {
				SearchCondition.addCondition(condition, "gradeid", "=", gradeid);
			}
			if (!"".equals(title)) {
				SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
			}

			List list = manager.getTkBookInfos(condition, "bookno asc", 0);
			
			//【在线做题】获取有题的作业本，没有题的作业本暂不在此显示
			TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
			List bookidList = tbcm.getAllbookidsWithPaperid();
			List bookList = new ArrayList();
			TkBookInfo tbi = null;
			for(int i=0, size=list.size(); i<size; i++){
				tbi = (TkBookInfo) list.get(i);
				if(bookidList.contains(tbi.getBookid())){
					bookList.add(tbi);
				}
			}

			request.setAttribute("list", bookList);
			request.setAttribute("subjectid", subjectid);
			request.setAttribute("gradeid", gradeid);
			request.setAttribute("title", title);

			String hiddendiv = Encode.nullToBlank(request.getParameter("hiddendiv"));
			if ("".equals(hiddendiv)) {
				hiddendiv = "0";
			}
			request.setAttribute("hiddendiv", hiddendiv);
			// 获取所有学科
			EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
			condition.clear();
			SearchCondition.addCondition(condition, "status", "=", "1");
			List subjectList = esim.getEduSubjectInfos(condition, "orderindex asc", 0);
			request.setAttribute("subjectList", subjectList);
			if (!"".equals(subjectid)) {
				EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
				condition.clear();
				SearchCondition.addCondition(condition, "status", "=", "1");
				SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
				List gradeList = egim.getEduGradeInfos(condition, "orderindex asc", 0);
				request.setAttribute("gradeList", gradeList);
			}

			return mapping.findForward("selectbook");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 创建班级选中作业本
	 */
	public ActionForward selectDeal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			MpUtil.getUserid(request);

			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
			TkBookInfo tbi = manager.getTkBookInfo(bookid);
			request.setAttribute("bookid", bookid);
			request.setAttribute("bookname", tbi.getTitle() + "（" + Constants.getCodeTypevalue("version", tbi.getVersion()) + "）");

			return mapping.findForward("addbook");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 添加绑定作业本
	 */
	public ActionForward addSaveBook(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			// ==============返回创建班级需要用到的参数===============
			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			String bookname = Encode.nullToBlank(request.getParameter("bookname"));
			request.setAttribute("bookid", bookid);
			request.setAttribute("bookname", bookname);
			// ==============返回创建班级需要用到的参数===============

			if ("".equals(bookid)) {
				request.setAttribute("errmsg", "请选择作业本!");
				return mapping.findForward("addbook");
			}

			if (!"1".equals(userid)) {
				TkClassUserManager manager = (TkClassUserManager) getBean("tkClassUserManager");
				//为了控制学生绑定一个作业本不能重复，需再此控制【页面上也了控制】
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "status", "=", "1");
				List lst = manager.getTkClassUsers(condition, "", 1);
				if(lst == null || lst.size() == 0){
					TkClassUser classUser = new TkClassUser();
					classUser.setUserid(Integer.valueOf(userid));
					classUser.setBookid(Integer.valueOf(bookid));
					classUser.setClassid(0);
					classUser.setStatus("1");
					classUser.setCreatedate(DateTime.getDate());
					manager.addTkClassUser(classUser);
				}else {
					TkClassUser classUser = (TkClassUser) lst.get(0);
					classUser.setStatus("1");
					manager.updateTkClassUser(classUser);
				}
				
				return mapping.findForward("userindex");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 学生删除绑定作业本
	 */
	public ActionForward delBook(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				
				TkClassUserManager manager = (TkClassUserManager) getBean("tkClassUserManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
				SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
				List list = manager.getTkClassUsers(condition, "", 0);
				if(list != null && list.size() > 0){
					TkClassUser tcu = null;
					for(int i=0, size=list.size(); i<size; i++){
						tcu = (TkClassUser) list.get(i);
						manager.delTkClassUser(tcu);
					}
				}

				return mapping.findForward("userindex");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/*****************************************************************************************************************
	 * 学生加入班级
	 */
	public ActionForward addClass(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			String scanResult = Encode.nullToBlank(request.getParameter("scanResult"));
			if (!"1".equals(userid)) {
				// 如果已经是通过扫一扫作业本则自动显示作业本信息，但是还是可以选择更换其他作业本【通过新注册跳转过来】
				//【取消二维码加密】
				//scanResult = Base64Utils.base64Decoder(scanResult);
				if (scanResult != null && scanResult.indexOf("TkClassInfo") != -1) {// 作业本
					String classid = scanResult.split("=")[1];
					TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
					TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
					TkClassInfo classInfo = manager.getTkClassInfo(classid);
					TkBookInfo bookInfo = tbim.getTkBookInfo(classInfo.getBookid());
					request.setAttribute("classInfo", classInfo);
					request.setAttribute("bookInfo", bookInfo);

					return mapping.findForward("addclass");// 直接显示班级相关信息，确认加入即可
				}

				return mapping.findForward("addclass1");// 需要手工输入班级编号或扫一扫班级二维码
			} else {
				// 跳转到登陆页面【初步控制，扫一扫作业本和班级时信息携带着，其他暂不携带，不携带如果登录成功自动跳转到个人中心，如果是注册，则需要选择注册的作业本】
				String nickname = Base64Utils.base64Decoder(Encode.nullToBlank(request.getParameter("nickname")));
				String headimgurl = Encode.nullToBlank(request.getParameter("headimgurl"));
				String sex = Encode.nullToBlank(request.getParameter("sex"));
				String openid = Encode.nullToBlank(request.getParameter("openid"));
				request.setAttribute("nickname", nickname);
				request.setAttribute("headimgurl", headimgurl);
				request.setAttribute("sex", sex);
				request.setAttribute("openid", openid);
				request.setAttribute("scanResult", scanResult);
				return mapping.findForward("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	public ActionForward addClass1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String classno = Encode.NoramlEncode(request.getParameter("classno"));
				if ("".equals(classno)) {
					request.setAttribute("errmsg", "班级编号不能为空!");
					return mapping.findForward("addclass1");
				}

				TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
				TkClassInfo classInfo = manager.getTkClassInfoByClassno(classno);
				if (classInfo == null) {
					request.setAttribute("errmsg", "班级编号输入错误!");
					return mapping.findForward("addclass1");
				}
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkBookInfo bookInfo = tbim.getTkBookInfo(classInfo.getBookid());
				request.setAttribute("classInfo", classInfo);
				request.setAttribute("bookInfo", bookInfo);

				return mapping.findForward("addclass");// 直接显示班级相关信息，确认加入即可
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * JS扫一扫班级二维码图片
	 */
	public ActionForward scanClass(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String scanCodeResult = Encode.NoramlEncode(request.getParameter("scanCodeResult"));
				//【取消二维码加密】
				//String scanResult = Base64Utils.base64Decoder(scanCodeResult);
				String scanResult = scanCodeResult;
				if (scanResult != null && scanResult.indexOf("TkClassInfo") != -1) {// 作业本
					String classid = scanResult.split("=")[1];
					TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
					TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
					TkClassInfo classInfo = manager.getTkClassInfo(classid);
					TkBookInfo bookInfo = tbim.getTkBookInfo(classInfo.getBookid());
					request.setAttribute("classInfo", classInfo);
					request.setAttribute("bookInfo", bookInfo);

					return mapping.findForward("addclass");// 直接显示班级相关信息，确认加入即可
				}

				request.setAttribute("errmsg", "扫描的二维码不是班级二维码!");
				return mapping.findForward("addclass1");// 需要手工输入班级编号或扫一扫班级二维码
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	public ActionForward addSaveClass(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				// 需要判断班级密码是否正确，人数是否已满
				String password = Encode.nullToBlank(request.getParameter("password"));
				String classid = Encode.NoramlEncode(request.getParameter("classid"));
				String bookid = Encode.NoramlEncode(request.getParameter("bookid"));
				TkClassInfoManager tcim = (TkClassInfoManager) getBean("tkClassInfoManager");
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkClassInfo classInfo = tcim.getTkClassInfo(classid);
				TkBookInfo bookInfo = tbim.getTkBookInfo(bookid);
				request.setAttribute("classInfo", classInfo);
				request.setAttribute("bookInfo", bookInfo);
				
				TkClassUserManager manager = (TkClassUserManager) getBean("tkClassUserManager");
				int students = manager.getStudentsByClassid(classid);
				if (classInfo.getStudents().intValue() <= students) {
					request.setAttribute("errmsg", "抱歉，当前班级学员人数已满!");
					return mapping.findForward("addclass");
				}

				TkClassPasswordManager tcpm = (TkClassPasswordManager) getBean("tkClassPasswordManager");
				TkClassPassword classPassword = null;
				if ("1".equals(classInfo.getPwd())) {
					classPassword = tcpm.getTkClassPassword(password, classid);
					if (classPassword == null) {
						request.setAttribute("errmsg", "班级密码输入错误!");
						return mapping.findForward("addclass");
					} else {
						if ("1".equals(classPassword.getUsed())) {
							request.setAttribute("errmsg", "班级密码已被绑定!");
							return mapping.findForward("addclass");
						} else {
							classPassword.setUsed("1");
							classPassword.setUserid(Integer.valueOf(userid));
							tcpm.updateTkClassPassword(classPassword);
						}
					}
				}

				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
				SearchCondition.addCondition(condition, "status", "=", "1");
				List list = manager.getTkClassUsers(condition, "", 1);
				if (list != null && list.size() > 0) {
					TkClassUser classUser = (TkClassUser) list.get(0);
					if (classUser.getClassid().intValue() == 0) {
						classUser.setClassid(Integer.valueOf(classid));
						manager.updateTkClassUser(classUser);
					}else {
						if(classPassword != null){
							classPassword.setUsed("0");
							classPassword.setUserid(0);
							tcpm.updateTkClassPassword(classPassword);
						}
						request.setAttribute("errmsg", "加入班级失败，当前班级关联的作业本你在其他班级已经关联!");
						return mapping.findForward("addclass");
					}
				} else {
					TkClassUser classUser = new TkClassUser();
					classUser.setUserid(Integer.valueOf(userid));
					classUser.setBookid(Integer.valueOf(bookid));
					classUser.setClassid(Integer.valueOf(classid));
					classUser.setStatus("1");
					classUser.setCreatedate(DateTime.getDate());
					manager.addTkClassUser(classUser);
				}

				return mapping.findForward("userindex");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/*****************************************************
	 * 开始进入作业本及作业************************************************************
	 * 进入作业本【classid尽量一直携带着】
	 */
	public ActionForward bookContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String uploadImg = "0";// 判断当前班级是否可以拍照上传
				if (!"0".equals(classid) && !"".equals(classid)) {
					TkClassInfoManager tcim = (TkClassInfoManager) getBean("tkClassInfoManager");
					TkClassInfo classInfo = tcim.getTkClassInfo(classid);
					uploadImg = classInfo.getUploadimg();
				}

				TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
				List list = manager.getAllBookContentByBook(bookid, "contentno");
				List parentList = new ArrayList();
				Map childMap = new HashMap();
				TkBookContent tbc = null;
				StringBuffer bookcontentids = new StringBuffer();
				for (int i = 0, size = list.size(); i < size; i++) {
					tbc = (TkBookContent) list.get(i);
					if ("0000".equals(tbc.getParentno())) {
						parentList.add(tbc);
					} else {
						if (childMap.get(tbc.getParentno()) == null) {
							List lst = new ArrayList();
							lst.add(tbc);
							childMap.put(tbc.getParentno(), lst);
						} else {
							List lst = (List) childMap.get(tbc.getParentno());
							lst.add(tbc);
							childMap.put(tbc.getParentno(), lst);
						}
						bookcontentids.append(",").append(tbc.getBookcontentid());
					}
				}
				request.setAttribute("parentList", parentList);
				request.setAttribute("childMap", childMap);
				request.setAttribute("uploadImg", uploadImg);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				
				List bookcontentidList = new ArrayList();
				if(bookcontentids.length() > 0){
					//查询用户当前作业本已做练习，给出提示
					TkBookContentTaskManager tbctm = (TkBookContentTaskManager) getBean("tkBookContentTaskManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
					SearchCondition.addCondition(condition, "iscommit", "=", "1' and a.bookcontentid in (" + bookcontentids.substring(1) + ") and 1='1");
					SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
					SearchCondition.addCondition(condition, "type", "=", "1");
					List lst = tbctm.getTkBookContentTasks(condition, "", 0);
					
					TkBookContentTask task = null;
					for(int i=0, size=lst.size(); i<size; i++){
						task = (TkBookContentTask) lst.get(i);
						bookcontentidList.add(task.getBookcontentid());
					}
				}
				request.setAttribute("bookcontentidList", bookcontentidList);
				
				String isAnswer = "0";
				if("0".equals(classid)){
					//查询当前作业本用户是否做过作业，如果没有数据产生，学生可以取消绑定作业本
					TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
					int answerCount = tpam.getTkPaperAnswerCountsByBookid(bookid, userid);
					if(answerCount > 0){
						isAnswer = "1";
					}
				}else{
					isAnswer = "1";
				}
				request.setAttribute("isAnswer", isAnswer);

				return mapping.findForward("bookcontent");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 学生进入作业的预习内容 如果有文本介绍先展示介绍内容，没有则直接做题，没做完一题给出答案和解析，实时提交 【共用数据尽量用缓存处理】
	 */
	public ActionForward doBeforeClass(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));

				TkBookContent bookContent = CacheUtil.getTkBookContent("TkBookContent_" + bookcontentid);
				if (bookContent == null) {
					TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
					bookContent = tbcm.getTkBookContent(bookcontentid);
					CacheUtil.put("TkBookContent_" + bookcontentid, bookContent, 24 * 30);
				}
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookContent.getBookid());
				request.setAttribute("bookContent", bookContent);

				// 获取作业对应的课前预习题，先从缓存中获取，没有再查下数据库
				List questionList = CacheUtil.getList("TkQuestionsInfoList_1_" + bookcontentid);
				if (questionList == null) {
					TkBookContentQuestionManager tbcqm = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
					questionList = tbcqm.getTkQuestionsInfosByBookcontentid(bookcontentid, "1", null);
					CacheUtil.putList("TkQuestionsInfoList_1_" + bookcontentid, questionList, 24 * 30);
				}
				if (questionList != null && questionList.size() > 0) {
					// 如果课前预习有文本内容并且有试题，则先显示文本内容，通过点击“查看试题”再具体显示试题
					String show = Encode.nullToBlank(request.getParameter("show"));// 通过点击触发“查看试题”按钮
					if (!"1".equals(show) && bookContent.getBeforeclass() != null && !"".equals(bookContent.getBeforeclass())) {
						request.setAttribute("show", "1");// 显示按钮“查看试题”
						return mapping.findForward("beforeclassinfo");// 课前预习有关联题，先显示课前预习文本内容
					}
					request.setAttribute("show", show);

					List<SearchModel> condition = new ArrayList<SearchModel>();
					String taskid = Encode.nullToBlank(request.getParameter("taskid"));
					String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
					if ("".equals(taskid) && "".equals(tasktype)) {// 点击“预习”按钮直接进入的
						TkBookContentTaskManager tbctm = (TkBookContentTaskManager) getBean("tkBookContentTaskManager");
						SearchCondition.addCondition(condition, "parentid", "=", 0);
						SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
						SearchCondition.addCondition(condition, "bookcontentid", "=", bookContent.getBookcontentid());
						SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
						SearchCondition.addCondition(condition, "type", "=", "2");
						List taskList = tbctm.getTkBookContentTasks(condition, "", 1);
						Integer parentid = 0;
						if (taskList != null && taskList.size() > 0) {
							TkBookContentTask parenTask = (TkBookContentTask) taskList.get(0);
							parentid = parenTask.getTaskid();
						}

						TkBookContentTask task = new TkBookContentTask();
						task.setParentid(parentid);
						task.setUserid(Integer.valueOf(userid));
						task.setBookcontentid(Integer.valueOf(bookcontentid));
						task.setType("2");
						task.setCreatedate(DateTime.getDate());
						task.setClassid(Integer.valueOf(classid));
						task.setIscommit("1");
						tbctm.addTkBookContentTask(task);

						taskid = task.getTaskid().toString();
						if (task.getParentid().intValue() > 0) {
							tasktype = "1";
						} else {
							tasktype = "0";
						}
					}
					request.setAttribute("taskid", taskid);
					request.setAttribute("tasktype", tasktype);

					// 选择上一题或下一题时，可能已经是做过的
					String index = Encode.nullToBlank(request.getParameter("index"));// 当前做题数，方便获取下一题，第一次进入为1
					String next = Encode.nullToBlank(request.getParameter("next"));// 1表示获取下一题，-1表示获取上一题
					if("".equals(index)) index = "1";
					if("".equals(next)) next = "1";
					
					int tindex = Integer.valueOf(index);
					String prebutton = "0";// “上一题”按钮
					String nextbutton = "0";// “下一题”按钮
					TkQuestionsInfo questionsInfo = (TkQuestionsInfo) questionList.get(tindex - 1);
					CacheUtil.put("TkQuestionsInfo_" + questionsInfo.getQuestionid(), questionsInfo, 24 * 30);
					if ("1".equals(next)) {
						if (tindex > 1)
							prebutton = "1";
						if (questionList.size() > tindex) {
							nextbutton = "1";
						}
					} else {
						nextbutton = "1";
						if (tindex > 1)
							prebutton = "1";
					}
					request.setAttribute("model", questionsInfo);
					request.setAttribute("index", tindex);
					request.setAttribute("next", next);
					request.setAttribute("prebutton", prebutton);
					request.setAttribute("nextbutton", nextbutton);

					// 每道题都可能有相关微课或举一反三题
					TkQuestionsFilmManager tqfm = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
					TkQuestionsSimilarManager tqsm = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
					int filmCounts = tqfm.getCountTkQuestionsFilms(questionsInfo.getQuestionid());
					int similarCounts = tqsm.getCountTkQuestionsSimilars(questionsInfo.getQuestionid());
					request.setAttribute("filmCounts", filmCounts);
					request.setAttribute("similarCounts", similarCounts);

					// 选择上一题或下一题时，可能已经是做过的，所以不需要重复做，直接把解析答案显示出来即可
					TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
					TkPaperAnswer answer = tpam.getTkPaperAnswer(userid, taskid, questionsInfo.getQuestionid().toString(), "2");// 获取课前预习作答情况
					request.setAttribute("answer", answer);

					// 题型分开显示，方便维护
					TkQuestionsType questionsType = questionsInfo.getTkQuestionsType();
					if ("A".equals(questionsType.getType())) {// 单选题
						return mapping.findForward("beforeclassA");
					} else if ("B".equals(questionsType.getType())) {// 多选题
						return mapping.findForward("beforeclassB");
					} else if ("C".equals(questionsType.getType())) {// 判断题
						return mapping.findForward("beforeclassC");
					} else if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {
						// 查询当前试题的所有子题，如果有则是复合题
						TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
						List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid());
						if (childList == null) {
							childList = tqim.getAllChildTkQuestionsInfos(questionsInfo.getQuestionid());
							CacheUtil.putList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid(), childList, 24 * 30);
						}
						if (childList != null && childList.size() > 0) {// 复合题
							// 如果是复合题，则每个子题的作答情况也需要查看详情
							List allChildAnswerList = tpam.getAllChildTkPaperAnswer(userid, taskid, questionsInfo.getQuestionid().toString(), "2");
							Map childAnswerMap = new HashMap();
							if (allChildAnswerList != null && allChildAnswerList.size() > 0) {
								TkPaperAnswer tkPaperAnswer = null;
								for (int i = 0, size = allChildAnswerList.size(); i < size; i++) {
									tkPaperAnswer = (TkPaperAnswer) allChildAnswerList.get(i);
									childAnswerMap.put(tkPaperAnswer.getChildid(), tkPaperAnswer);
								}
							}

							request.setAttribute("childList", childList);
							request.setAttribute("allChildAnswerList", allChildAnswerList);
							request.setAttribute("childAnswerMap", childAnswerMap);

							return mapping.findForward("beforeclassM");
						} else {// 填空题
							return mapping.findForward("beforeclassE");
						}
					} else {
						return mapping.findForward("beforeclassE");
					}
				} else {
					return mapping.findForward("beforeclassinfo");// 课前预习没有关联题，只显示课前预习文本内容
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * ajax提交课前预习作答答案
	 */
	public ActionForward answerBeforeClass(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String classid = Encode.nullToBlank(request.getParameter("classid"));
		String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
		String taskid = Encode.nullToBlank(request.getParameter("taskid"));
		String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
		String contentid = Encode.nullToBlank(request.getParameter("contentid"));
		String answer = Encode.nullToBlank(request.getParameter("answer"));

		String userid = MpUtil.getUserid(request);
		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		// 控制在同一次任务中，一道题只能做一次，避免重复提交
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "contentid", "=", Integer.valueOf(contentid));
		SearchCondition.addCondition(condition, "type", "=", "2");
		SearchCondition.addCondition(condition, "taskid", "=", Integer.valueOf(taskid));
		SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
		List lst = manager.getTkPaperAnswers(condition, "", 1);
		String result = "0";
		if (lst == null || lst.size() == 0) {
			TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
			TkQuestionsInfo tqi = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + contentid);
			if (tqi == null) {
				tqi = tqim.getTkQuestionsInfo(contentid);
				CacheUtil.put("TkQuestionsInfo_" + contentid, tqi, 24 * 30);
			}

			if (answer.equals(tqi.getRightans())) {
				result = "1";
			}

			if (userid != null && !"".equals(userid)) {
				TkPaperAnswer tpa = new TkPaperAnswer();
				tpa.setContentid(Integer.valueOf(contentid));
				tpa.setChildid(0);
				tpa.setType("2");
				tpa.setBookcontentid(Integer.valueOf(bookcontentid));
				tpa.setTaskid(Integer.valueOf(taskid));
				tpa.setTasktype(tasktype);
				tpa.setUserid(Integer.valueOf(userid));
				tpa.setClassid(Integer.valueOf(classid));
				tpa.setAnswer(answer);
				if ("0".equals(result)) {
					tpa.setIsright("0");
					tpa.setScore(0.0F);
					// 做错则记录到错题本中
					addUserErrorQuestion(tpa);
				} else {
					tpa.setIsright("1");
					tpa.setScore(Float.valueOf(tqi.getScore()));
				}
				manager.addTkPaperAnswer(tpa);
			}
		}

		response.setContentType("text/plain;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(result);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			out = null;
		}
		return null;
	}

	/**
	 * ajax提交课前预习填空题作答答案
	 */
	public ActionForward answerBeforeClassE(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String classid = Encode.nullToBlank(request.getParameter("classid"));
		String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
		String taskid = Encode.nullToBlank(request.getParameter("taskid"));
		String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
		String contentid = Encode.nullToBlank(request.getParameter("contentid"));
		String answer = Encode.nullToBlank(request.getParameter("answer"));
		// 前台进行过两次转码
		try {
			answer = java.net.URLDecoder.decode(answer, "UTF-8");// 反转码
		} catch (UnsupportedEncodingException e1) {
		}
		String userid = MpUtil.getUserid(request);

		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		// 控制在同一次任务中，一道题只能做一次，避免重复提交
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "contentid", "=", Integer.valueOf(contentid));
		SearchCondition.addCondition(condition, "type", "=", "2");
		SearchCondition.addCondition(condition, "taskid", "=", Integer.valueOf(taskid));
		SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
		List lst = manager.getTkPaperAnswers(condition, "", 1);
		String result = "0";
		if (lst == null || lst.size() == 0) {
			TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
			TkQuestionsInfo tqi = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + contentid);
			if (tqi == null) {
				tqi = tqim.getTkQuestionsInfo(contentid);
				CacheUtil.put("TkQuestionsInfo_" + contentid, tqi, 24 * 30);
			}

			Float score = 0.0F;
			if (tqi.getOptionnum().intValue() > 1) {
				String[] rights = tqi.getRightans().split("【】");
				String[] answers = answer.split("【】");
				String[] scores = tqi.getScore().split("【】");
				String tag = "1";// 有回答错误
				for (int i = 0, size = rights.length; i < size; i++) {
					if (tqi.getIsRight(rights[i], answers[i])) {
						score = score + Float.valueOf(scores[i]);
					} else {
						tag = "0";
					}
				}
				if ("1".equals(tag)) {
					result = "1";
				}
			} else {
				if (tqi.getIsRight(tqi.getRightans(), answer)) {
					result = "1";
					score = Float.valueOf(tqi.getScore());
				}
			}

			if (userid != null && !"".equals(userid)) {
				TkPaperAnswer tpa = new TkPaperAnswer();
				tpa.setContentid(Integer.valueOf(contentid));
				tpa.setChildid(0);
				tpa.setType("2");
				tpa.setBookcontentid(Integer.valueOf(bookcontentid));
				tpa.setTaskid(Integer.valueOf(taskid));
				tpa.setTasktype(tasktype);
				tpa.setUserid(Integer.valueOf(userid));
				tpa.setClassid(Integer.valueOf(classid));
				tpa.setAnswer(answer);
				if ("0".equals(result)) {
					tpa.setIsright("0");
					// 做错则记录到错题本中
					addUserErrorQuestion(tpa);
				} else {
					tpa.setIsright("1");
				}
				tpa.setScore(score);
				manager.addTkPaperAnswer(tpa);
			}
		}

		response.setContentType("text/plain;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(result);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			out = null;
		}
		return null;
	}

	/**
	 * ajax提交课前预习复合题作答答案
	 */
	public ActionForward answerBeforeClassM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String classid = Encode.nullToBlank(request.getParameter("classid"));
		String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
		String taskid = Encode.nullToBlank(request.getParameter("taskid"));
		String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
		String contentid = Encode.nullToBlank(request.getParameter("contentid"));
		String answer = Encode.nullToBlank(request.getParameter("answer"));
		// 前台进行过两次转码
		try {
			answer = java.net.URLDecoder.decode(answer, "UTF-8");// 反转码
		} catch (UnsupportedEncodingException e1) {
		}
		String userid = MpUtil.getUserid(request);

		TkPaperAnswerManager manager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
		// 控制在同一次任务中，一道题只能做一次，避免重复提交
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "contentid", "=", Integer.valueOf(contentid));
		SearchCondition.addCondition(condition, "type", "=", "2");
		SearchCondition.addCondition(condition, "taskid", "=", Integer.valueOf(taskid));
		SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
		List lst = manager.getTkPaperAnswers(condition, "", 1);
		String result = "0";
		if (lst == null || lst.size() == 0) {
			TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
			TkQuestionsInfo tqi = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + contentid);
			if (tqi == null) {
				tqi = tqim.getTkQuestionsInfo(contentid);
				CacheUtil.put("TkQuestionsInfo_" + contentid, tqi, 24 * 30);
			}

			// 复合题包含多道子题
			String[] allanswers = answer.split("【】");
			int len = allanswers.length;
			List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + tqi.getQuestionid());
			if (childList == null) {
				childList = tqim.getAllChildTkQuestionsInfos(tqi.getQuestionid());
				CacheUtil.putList("TkQuestionsInfoChildList_" + tqi.getQuestionid(), childList, 24 * 30);
			}
			TkQuestionsInfo model = null;
			TkQuestionsType type = null;
			String iswrong = "0";
			for (int i = 0, size = childList.size(); i < size; i++) {
				if (len > i && userid != null && !"".equals(userid)) {
					model = (TkQuestionsInfo) childList.get(i);
					type = model.getTkQuestionsType();
					if ("A".equals(type.getType()) || "C".equals(type.getType())) {
						if (model.getRightans().equals(allanswers[i])) {
							result = result + "," + "1";
							TkPaperAnswer tpa = new TkPaperAnswer();
							tpa.setContentid(Integer.valueOf(contentid));
							tpa.setChildid(model.getQuestionid());
							tpa.setType("2");
							tpa.setBookcontentid(Integer.valueOf(bookcontentid));
							tpa.setTaskid(Integer.valueOf(taskid));
							tpa.setTasktype(tasktype);
							tpa.setUserid(Integer.valueOf(userid));
							tpa.setClassid(Integer.valueOf(classid));
							tpa.setAnswer(allanswers[i]);
							tpa.setIsright("1");
							tpa.setScore(Float.valueOf(model.getScore()));
							manager.addTkPaperAnswer(tpa);
						} else {
							result = result + "," + "0";
							TkPaperAnswer tpa = new TkPaperAnswer();
							tpa.setContentid(Integer.valueOf(contentid));
							tpa.setChildid(model.getQuestionid());
							tpa.setType("2");
							tpa.setBookcontentid(Integer.valueOf(bookcontentid));
							tpa.setTaskid(Integer.valueOf(taskid));
							tpa.setTasktype(tasktype);
							tpa.setUserid(Integer.valueOf(userid));
							tpa.setClassid(Integer.valueOf(classid));
							tpa.setAnswer(allanswers[i]);
							tpa.setIsright("0");
							tpa.setScore(0.0F);
							manager.addTkPaperAnswer(tpa);
							if ("0".equals(iswrong)) {
								// 做错则记录到错题本中
								addUserErrorQuestion(tpa);
							}
							iswrong = "1";
						}
					} else if ("B".equals(type.getType())) {
						String answerB = Encode.nullToBlank(allanswers[i]).replaceAll(",", "");
						if (model.getRightans().equals(answerB)) {
							result = result + "," + "1";
							TkPaperAnswer tpa = new TkPaperAnswer();
							tpa.setContentid(Integer.valueOf(contentid));
							tpa.setChildid(model.getQuestionid());
							tpa.setType("2");
							tpa.setBookcontentid(Integer.valueOf(bookcontentid));
							tpa.setTaskid(Integer.valueOf(taskid));
							tpa.setTasktype(tasktype);
							tpa.setUserid(Integer.valueOf(userid));
							tpa.setClassid(Integer.valueOf(classid));
							tpa.setAnswer(answerB);
							tpa.setIsright("1");
							tpa.setScore(Float.valueOf(model.getScore()));
							manager.addTkPaperAnswer(tpa);
						} else {
							result = result + "," + "0";
							TkPaperAnswer tpa = new TkPaperAnswer();
							tpa.setContentid(Integer.valueOf(contentid));
							tpa.setChildid(model.getQuestionid());
							tpa.setType("2");
							tpa.setBookcontentid(Integer.valueOf(bookcontentid));
							tpa.setTaskid(Integer.valueOf(taskid));
							tpa.setTasktype(tasktype);
							tpa.setUserid(Integer.valueOf(userid));
							tpa.setClassid(Integer.valueOf(classid));
							tpa.setAnswer(answerB);
							tpa.setIsright("0");
							tpa.setScore(0.0F);
							manager.addTkPaperAnswer(tpa);
							if ("0".equals(iswrong)) {
								// 做错则记录到错题本中
								addUserErrorQuestion(tpa);
							}
							iswrong = "1";
						}
					} else if ("E".equals(type.getType())) {
						String result0 = "0";
						Float score = 0.0F;
						String paperAnswer = null;
						if (model.getOptionnum().intValue() > 1) {
							String[] rights = model.getRightans().split("【】");
							String[] answers = splitAnswer(allanswers[i]);// 说明：此答案可能不是按顺序排序的，比如先是第二空答案，再是第一空答案，需要排序
							String[] scores = model.getScore().split("【】");
							String tag = "1";// 有回答错误
							for (int x = 0, y = rights.length; x < y; x++) {
								if (x == 0) {
									paperAnswer = answers[x];
								} else {
									paperAnswer = paperAnswer + "【】" + answers[x];
								}
								if (model.getIsRight(rights[x], answers[x])) {
									score = score + Float.valueOf(scores[x]);
								} else {
									tag = "0";
								}
							}
							if ("1".equals(tag)) {
								result0 = "1";
								result = result + "," + "1";
							} else {
								result = result + "," + "0";
							}
						} else {
							paperAnswer = allanswers[i].substring(allanswers[i].indexOf("】") + 1);
							if (model.getIsRight(model.getRightans(), paperAnswer)) {
								result0 = "1";
								result = result + "," + "1";
								score = Float.valueOf(model.getScore());
							} else {
								result = result + "," + "0";
							}
						}
						TkPaperAnswer tpa = new TkPaperAnswer();
						tpa.setContentid(Integer.valueOf(contentid));
						tpa.setChildid(model.getQuestionid());
						tpa.setType("2");
						tpa.setBookcontentid(Integer.valueOf(bookcontentid));
						tpa.setTaskid(Integer.valueOf(taskid));
						tpa.setTasktype(tasktype);
						tpa.setUserid(Integer.valueOf(userid));
						tpa.setClassid(Integer.valueOf(classid));
						tpa.setAnswer(paperAnswer);
						if ("0".equals(result0)) {
							tpa.setIsright("0");
							if ("0".equals(iswrong)) {
								// 做错则记录到错题本中
								addUserErrorQuestion(tpa);
							}
							iswrong = "1";
						} else {
							tpa.setIsright("1");
						}
						tpa.setScore(score);
						manager.addTkPaperAnswer(tpa);
					} else {
						result = result + "," + "0";
					}
				} else {
					result = result + "," + "0";
				}
			}
			result = result.substring(2);
		}

		response.setContentType("text/plain;charset=gbk");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(result);
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			out = null;
		}
		return null;
	}

	private String[] splitAnswer(String answer) {
		String[] str = answer.split("【;】");
		String[] answers = new String[str.length];
		String k = null;
		for (int i = 0, size = str.length; i < size; i++) {
			k = str[i].substring(1, str[i].indexOf("=】"));
			answers[Integer.valueOf(k).intValue() - 1] = str[i].substring(str[i].indexOf("】") + 1);
		}

		return answers;
	}

	// 添加用户错题本
	private void addUserErrorQuestion(TkPaperAnswer tkPaperAnswer) {
		TkUserErrorQuestionManager manager = (TkUserErrorQuestionManager) getBean("tkUserErrorQuestionManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "contentid", "=", tkPaperAnswer.getContentid());
		SearchCondition.addCondition(condition, "type", "=", tkPaperAnswer.getType());
		SearchCondition.addCondition(condition, "userid", "=", tkPaperAnswer.getUserid());
		List list = manager.getTkUserErrorQuestions(condition, "", 1);
		if (list == null || list.size() == 0) {
			TkUserErrorQuestion errorQuestion = new TkUserErrorQuestion();
			errorQuestion.setContentid(tkPaperAnswer.getContentid());
			errorQuestion.setType(tkPaperAnswer.getType());
			errorQuestion.setBookcontentid(tkPaperAnswer.getBookcontentid());
			errorQuestion.setUserid(tkPaperAnswer.getUserid());
			errorQuestion.setClassid(tkPaperAnswer.getClassid());
			errorQuestion.setCreatedate(DateTime.getDate());
			errorQuestion.setTaskid(tkPaperAnswer.getTaskid());
			manager.addTkUserErrorQuestion(errorQuestion);
		}
	}

	/**
	 * 作业本错题本
	 */
	public ActionForward errorQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				// 把课前预习和作业的错题分开查询，展示时，课前预习错题后面加标示[课前预习]
				TkUserErrorQuestionManager manager = (TkUserErrorQuestionManager) getBean("tkUserErrorQuestionManager");
				List list1 = manager.getTkUserErrorQuestions(userid, bookcontentid, classid, "2");
				List list2 = manager.getTkUserErrorQuestions(userid, bookcontentid, classid, "1");
				list1.addAll(list2);
				request.setAttribute("list", list1);
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);

				return mapping.findForward("errorquestion");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 作业本错题本信息
	 */
	public ActionForward errorQuestionInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String uerrorid = Encode.nullToBlank(request.getParameter("uerrorid"));
				TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
				TkQuestionsInfo tqi = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + questionid);
				if (tqi == null) {
					tqi = manager.getTkQuestionsInfo(questionid);
					CacheUtil.put("TkQuestionsInfo_" + questionid, tqi, 24 * 30);
				}
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("model", tqi);
				request.setAttribute("uerrorid", uerrorid);

				TkUserErrorQuestionManager tueqm = (TkUserErrorQuestionManager) getBean("tkUserErrorQuestionManager");
				TkUserErrorQuestion userErrorQuestion = tueqm.getTkUserErrorQuestion(uerrorid);
				TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
				TkPaperAnswer answer = tpam.getTkPaperAnswer(userid, userErrorQuestion.getTaskid().toString(), userErrorQuestion.getContentid().toString(),
						userErrorQuestion.getType());
				request.setAttribute("answer", answer);

				// 每道题都可能有相关微课或举一反三题
				TkQuestionsFilmManager tqfm = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
				TkQuestionsSimilarManager tqsm = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
				int filmCounts = tqfm.getCountTkQuestionsFilms(tqi.getQuestionid());
				int similarCounts = tqsm.getCountTkQuestionsSimilars(tqi.getQuestionid());
				request.setAttribute("filmCounts", filmCounts);
				request.setAttribute("similarCounts", similarCounts);

				if ("F".equals(tqi.getTkQuestionsType().getType()) || "M".equals(tqi.getTkQuestionsType().getType())) {
					// 查询当前试题的所有子题，如果有则是复合题
					List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + tqi.getQuestionid());
					if (childList == null) {
						childList = manager.getAllChildTkQuestionsInfos(tqi.getQuestionid());
						CacheUtil.putList("TkQuestionsInfoChildList_" + tqi.getQuestionid(), childList, 24 * 30);
					}
					if (childList != null && childList.size() > 0) {// 复合题
						// 如果是复合题，则每个子题的作答情况也需要查看详情
						List allChildAnswerList = tpam.getAllChildTkPaperAnswer(userid, userErrorQuestion.getTaskid().toString(), userErrorQuestion.getContentid().toString(),
								userErrorQuestion.getType());
						Map childAnswerMap = new HashMap();
						if (allChildAnswerList != null && allChildAnswerList.size() > 0) {
							TkPaperAnswer tkPaperAnswer = null;
							for (int i = 0, size = allChildAnswerList.size(); i < size; i++) {
								tkPaperAnswer = (TkPaperAnswer) allChildAnswerList.get(i);
								childAnswerMap.put(tkPaperAnswer.getChildid(), tkPaperAnswer);
							}
						}

						request.setAttribute("childList", childList);
						request.setAttribute("allChildAnswerList", allChildAnswerList);
						request.setAttribute("childAnswerMap", childAnswerMap);

						return mapping.findForward("questioninfoM");
					} else {// 填空题
						return mapping.findForward("questioninfo");
					}
				} else {
					return mapping.findForward("questioninfo");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 作业本错题本信息
	 */
	public ActionForward errorQuestionInfo2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String uerrorid = Encode.nullToBlank(request.getParameter("uerrorid"));
				TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
				TkQuestionsInfo tqi = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + questionid);
				if (tqi == null) {
					tqi = manager.getTkQuestionsInfo(questionid);
					CacheUtil.put("TkQuestionsInfo_" + questionid, tqi, 24 * 30);
				}
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("model", tqi);
				request.setAttribute("uerrorid", uerrorid);

				TkUserErrorQuestionManager tueqm = (TkUserErrorQuestionManager) getBean("tkUserErrorQuestionManager");
				TkUserErrorQuestion userErrorQuestion = tueqm.getTkUserErrorQuestion(uerrorid);
				TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
				TkPaperAnswer answer = tpam.getTkPaperAnswer(userid, userErrorQuestion.getTaskid().toString(), userErrorQuestion.getContentid().toString(),
						userErrorQuestion.getType());
				request.setAttribute("answer", answer);

				// 每道题都可能有相关微课或举一反三题
				TkQuestionsFilmManager tqfm = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
				TkQuestionsSimilarManager tqsm = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
				int filmCounts = tqfm.getCountTkQuestionsFilms(tqi.getQuestionid());
				int similarCounts = tqsm.getCountTkQuestionsSimilars(tqi.getQuestionid());
				request.setAttribute("filmCounts", filmCounts);
				request.setAttribute("similarCounts", similarCounts);

				if ("F".equals(tqi.getTkQuestionsType().getType()) || "M".equals(tqi.getTkQuestionsType().getType())) {
					// 查询当前试题的所有子题，如果有则是复合题
					List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + tqi.getQuestionid());
					if (childList == null) {
						childList = manager.getAllChildTkQuestionsInfos(tqi.getQuestionid());
						CacheUtil.putList("TkQuestionsInfoChildList_" + tqi.getQuestionid(), childList, 24 * 30);
					}
					if (childList != null && childList.size() > 0) {// 复合题
						// 如果是复合题，则每个子题的作答情况也需要查看详情
						List allChildAnswerList = tpam.getAllChildTkPaperAnswer(userid, userErrorQuestion.getTaskid().toString(), userErrorQuestion.getContentid().toString(),
								userErrorQuestion.getType());
						Map childAnswerMap = new HashMap();
						if (allChildAnswerList != null && allChildAnswerList.size() > 0) {
							TkPaperAnswer tkPaperAnswer = null;
							for (int i = 0, size = allChildAnswerList.size(); i < size; i++) {
								tkPaperAnswer = (TkPaperAnswer) allChildAnswerList.get(i);
								childAnswerMap.put(tkPaperAnswer.getChildid(), tkPaperAnswer);
							}
						}

						request.setAttribute("childList", childList);
						request.setAttribute("allChildAnswerList", allChildAnswerList);
						request.setAttribute("childAnswerMap", childAnswerMap);

						return mapping.findForward("questioninfoM_all");
					} else {// 填空题
						return mapping.findForward("questioninfo_all");
					}
				} else {
					return mapping.findForward("questioninfo_all");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	// =============================================在线作答===============================================
	/**
	 * 学生进入在线作答作业 【共用数据尽量用缓存处理】
	 */
	public ActionForward lianxi(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String answercard = Encode.nullToBlank(request.getParameter("answercard"));// 1表示是直接通过填答题卡作答

				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 在线作答：1、没有作答开始第一次作答；2、已经作答，1）如果提交了则直接查看已作答结果，可点击重做，2）如果作答没有提交或意外退出，则需要继续接着作答
				String taskid = null;
				String tasktype = null;
				TkBookContentTaskManager tbctm = (TkBookContentTaskManager) getBean("tkBookContentTaskManager");
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
				SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
				SearchCondition.addCondition(condition, "type", "=", "1");
				List taskList = tbctm.getTkBookContentTasks(condition, "createdate desc", 0);
				String iscommit = "0";
				TkBookContentTask task = null;
				if (taskList != null && taskList.size() > 0) {
					// 已经作答过，判断最后作答是否提交
					task = (TkBookContentTask) taskList.get(0);
					taskid = task.getTaskid().toString();
					if (task.getParentid().intValue() > 0) {
						tasktype = "1";
					} else {
						tasktype = "0";
					}
					iscommit = task.getIscommit();
				} else {// 第一次作答
					task = new TkBookContentTask();
					task.setParentid(0);
					task.setUserid(Integer.valueOf(userid));
					task.setBookcontentid(Integer.valueOf(bookcontentid));
					task.setType("1");
					task.setCreatedate(DateTime.getDate());
					task.setClassid(Integer.valueOf(classid));
					task.setIscommit("0");
					tbctm.addTkBookContentTask(task);

					taskid = task.getTaskid().toString();
					tasktype = "0";
				}

				String redirecturl = null;
				if ("1".equals(answercard)) {
					//【改】控制只能在线作答练习一次
					/*
					if ("1".equals(iscommit)) {
						// 如果点击填答题卡时，作业已经提交，则需要生成新的任务供填答题卡使用
						Integer parentid = 0;
						if (task.getParentid().intValue() == 0) {
							parentid = task.getTaskid();
						} else {
							parentid = task.getParentid();
						}

						task = new TkBookContentTask();
						task.setParentid(parentid);
						task.setUserid(Integer.valueOf(userid));
						task.setBookcontentid(Integer.valueOf(bookcontentid));
						task.setType("1");
						task.setCreatedate(DateTime.getDate());
						task.setClassid(Integer.valueOf(classid));
						task.setIscommit("0");
						tbctm.addTkBookContentTask(task);

						taskid = task.getTaskid().toString();
						tasktype = "1";
					}
					*/
					redirecturl = "/weixinStudent.app?method=answerCard&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + bookcontentid + "&bookid=" + bookid
							+ "&classid=" + classid + "&taskid=" + taskid + "&tasktype=" + tasktype + "&iscommit=" + iscommit;
				} else {
					//【注意】在线作答继续练习时，如果作业本内容关联了音频文件，则需要跳转到特殊页面供边听音频边做练习
					TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
					TkBookContent bookContent = tbcm.getTkBookContent(bookcontentid);
					
					if ("1".equals(iscommit)) {// 查看作答情况
						if(bookContent.getMp3path() != null && !"".equals(bookContent.getMp3path())){
							redirecturl = "/weixinStudent.app?method=viewLianxiMain&index=1&next=1&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + bookcontentid + "&bookid="
							+ bookid + "&classid=" + classid + "&taskid=" + taskid + "&tasktype=" + tasktype;
						}else {
							redirecturl = "/weixinStudent.app?method=viewLianxi&index=1&next=1&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + bookcontentid + "&bookid="
							+ bookid + "&classid=" + classid + "&taskid=" + taskid + "&tasktype=" + tasktype;
						}
					} else {// 继续测验
						if(bookContent.getMp3path() != null && !"".equals(bookContent.getMp3path())){
							redirecturl = "/weixinStudent.app?method=lianxiMain&index=1&next=1&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + bookcontentid + "&bookid="
							+ bookid + "&classid=" + classid + "&taskid=" + taskid + "&tasktype=" + tasktype;
						}else {
							redirecturl = "/weixinStudent.app?method=lianxi1&index=1&next=1&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + bookcontentid + "&bookid="
							+ bookid + "&classid=" + classid + "&taskid=" + taskid + "&tasktype=" + tasktype;
						}
					}
				}
				// 跳转到开始自由练习作答界面
				response.sendRedirect(redirecturl);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 重做
	 */
	public ActionForward repeatLianxi(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String taskid = Encode.nullToBlank(request.getParameter("taskid"));
				TkBookContentTaskManager tbctm = (TkBookContentTaskManager) getBean("tkBookContentTaskManager");
				TkBookContentTask task0 = tbctm.getTkBookContentTask(taskid);
				Integer parentid = 0;
				if (task0.getParentid().intValue() == 0) {
					parentid = task0.getTaskid();
				} else {
					parentid = task0.getParentid();
				}
				TkBookContentTask task = new TkBookContentTask();
				task.setParentid(parentid);
				task.setUserid(Integer.valueOf(userid));
				task.setBookcontentid(task0.getBookcontentid());
				task.setType("1");
				task.setCreatedate(DateTime.getDate());
				task.setClassid(task0.getClassid());
				task.setIscommit("0");
				tbctm.addTkBookContentTask(task);

				taskid = task.getTaskid().toString();
				String tasktype = "1";

				String redirecturl = null;
				//【注意】在线作答继续练习时，如果作业本内容关联了音频文件，则需要跳转到特殊页面供边听音频边做练习
				TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent bookContent = tbcm.getTkBookContent(task0.getBookcontentid());
				if(bookContent.getMp3path() != null && !"".equals(bookContent.getMp3path())){
					redirecturl = "/weixinStudent.app?method=lianxiMain&index=1&next=1&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + task.getBookcontentid()
					+ "&bookid=" + bookid + "&classid=" + task.getClassid() + "&taskid=" + taskid + "&tasktype=" + tasktype;
				}else {
					redirecturl = "/weixinStudent.app?method=lianxi1&index=1&next=1&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + task.getBookcontentid()
					+ "&bookid=" + bookid + "&classid=" + task.getClassid() + "&taskid=" + taskid + "&tasktype=" + tasktype;
				}
				
				// 跳转到开始自由练习作答界面
				response.sendRedirect(redirecturl);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 学生进入在线作答作业 【英语听力在线听】
	 */
	public ActionForward lianxiMain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String taskid = Encode.nullToBlank(request.getParameter("taskid"));
				String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
				String index = Encode.nullToBlank(request.getParameter("index"));
				String next = Encode.nullToBlank(request.getParameter("next"));
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("taskid", taskid);
				request.setAttribute("tasktype", tasktype);
				request.setAttribute("index", index);
				request.setAttribute("next", next);
				
				TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent bookContent = manager.getTkBookContent(bookcontentid);
				if(bookContent.getMp3path() != null && !"".equals(bookContent.getMp3path())){
					request.setAttribute("model", bookContent);

					return mapping.findForward("lianximain");
				}else {
					String redirecturl = "/weixinStudent.app?method=lianxi1&index=1&next=1&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + bookcontentid + "&bookid="
					+ bookid + "&classid=" + classid + "&taskid=" + taskid + "&tasktype=" + tasktype;
					response.sendRedirect(redirecturl);
					return null;
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	public ActionForward viewLianxiMain(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String taskid = Encode.nullToBlank(request.getParameter("taskid"));
				String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
				String index = Encode.nullToBlank(request.getParameter("index"));
				String next = Encode.nullToBlank(request.getParameter("next"));
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("taskid", taskid);
				request.setAttribute("tasktype", tasktype);
				request.setAttribute("index", index);
				request.setAttribute("next", next);
				
				TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent bookContent = manager.getTkBookContent(bookcontentid);
				request.setAttribute("model", bookContent);

				if(bookContent.getMp3path() != null && !"".equals(bookContent.getMp3path())){
					return mapping.findForward("viewlianximain");
				}else {
					String redirecturl = "/weixinStudent.app?method=viewLianxi&index=" + index + "&next=" + next + "&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + bookcontentid + "&bookid=" + bookid + "&classid=" + classid + "&taskid=" + taskid + "&tasktype=" + tasktype;
					response.sendRedirect(redirecturl);
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 学生进入在线作答作业 【共用数据尽量用缓存处理】
	 */
	public ActionForward lianxi1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String taskid = Encode.nullToBlank(request.getParameter("taskid"));
				String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("taskid", taskid);
				request.setAttribute("tasktype", tasktype);

				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 获取作业对应的题，先从缓存中获取，没有再查下数据库
				TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent bookContent = tbcm.getTkBookContent(bookcontentid);
				List questionList = CacheUtil.getList("TkPaperContentList_" + bookContent.getPaperid());
				if (questionList == null) {
					TkPaperContentManager tpcm = (TkPaperContentManager) getBean("tkPaperContentManager");
					questionList = tpcm.getMobileTkPaperContents(bookContent.getPaperid());
					CacheUtil.putList("TkPaperContentList_" + bookContent.getPaperid(), questionList, 24 * 30);
				}
				if (questionList != null && questionList.size() > 0) {
					condition.clear();
					// 选择上一题或下一题时，可能已经是做过的
					String index = Encode.nullToBlank(request.getParameter("index"));// 当前做题数，方便获取下一题，第一次进入为1
					String next = Encode.nullToBlank(request.getParameter("next"));// 1表示获取下一题，-1表示获取上一题
					String useranswer = Encode.nullToBlank(request.getParameter("useranswer"));// 用户作答答案，空表示作答答案无效，只是点击上一题或下一题的触发事件，不记录数据库中

					TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
					int tindex = Integer.valueOf(index);
					String prebutton = "0";// “上一题”按钮
					String nextbutton = "0";// “下一题”按钮
					//服务器有时会报数组越界错误
					if(questionList.size() <= (tindex-1)){
						tindex = questionList.size();
						index = tindex + "";
					}
					TkPaperContent paperContent = (TkPaperContent) questionList.get(tindex - 1);
					CacheUtil.put("TkPaperContent_" + paperContent.getContentid(), paperContent, 24 * 30);
					TkQuestionsInfo questionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + paperContent.getQuestionid());
					if (questionsInfo == null) {
						questionsInfo = tqim.getTkQuestionsInfo(paperContent.getQuestionid());
						CacheUtil.put("TkQuestionsInfo_" + paperContent.getQuestionid(), questionsInfo, 24 * 30);
					}

					// 选择上一题或下一题时，可能已经是做过的
					TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
					TkPaperAnswer answer = tpam.getTkPaperAnswer(userid, taskid, paperContent.getContentid().toString(), "1");// 关联试卷试题

					if (!"".equals(useranswer)) {// 如果是作答，则自动跳转到下一个题上
						TkQuestionsType type = questionsInfo.getTkQuestionsType();
						// 如果是已经作答过，修改则只修改作答内容即可
						if (answer == null) {
							answer = new TkPaperAnswer();
							answer.setContentid(paperContent.getContentid());
							answer.setType("1");
							answer.setBookcontentid(Integer.valueOf(bookcontentid));
							answer.setTaskid(Integer.valueOf(taskid));
							answer.setTasktype(tasktype);
							answer.setUserid(Integer.valueOf(userid));
							answer.setClassid(Integer.valueOf(classid));
							if ("A".equals(type.getType()) || "B".equals(type.getType()) || "C".equals(type.getType())) {
								answer.setChildid(0);
								answer.setAnswer(useranswer);
								if (useranswer.equals(questionsInfo.getRightans())) {
									answer.setScore(paperContent.getScore());
									answer.setIsright("1");
								} else {
									answer.setScore(0.0F);
									answer.setIsright("0");
								}
								tpam.addTkPaperAnswer(answer);
							} else if ("F".equals(type.getType()) || "M".equals(type.getType())) {
								// 复合题包含多道子题
								String[] allanswers = useranswer.split("【】");
								int len = allanswers.length;
								List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid());
								if (childList == null) {
									childList = tqim.getAllChildTkQuestionsInfos(questionsInfo.getQuestionid());
									CacheUtil.putList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid(), childList, 24 * 30);
								}
								TkQuestionsInfo model = null;
								TkQuestionsType tqt = null;
								Float parentScore = Float.valueOf(questionsInfo.getScore());
								for (int i = 0, size = childList.size(); i < size; i++) {
									if (len > i) {
										model = (TkQuestionsInfo) childList.get(i);
										tqt = model.getTkQuestionsType();

										answer.setChildid(model.getQuestionid());

										if ("A".equals(tqt.getType()) || "B".equals(tqt.getType()) || "C".equals(tqt.getType())) {
											answer.setAnswer(allanswers[i]);
											if (model.getRightans().equals(allanswers[i])) {
												answer.setIsright("1");
												answer.setScore(Float.valueOf(model.getScore()) * paperContent.getScore() / parentScore);
											} else {
												answer.setIsright("0");
												answer.setScore(0.0F);
											}
											tpam.addTkPaperAnswer(answer);
										} else if ("E".equals(tqt.getType())) {
											String result0 = "0";
											Float score = 0.0F;
											String paperAnswer = null;
											if (model.getOptionnum().intValue() > 1) {
												String[] rights = model.getRightans().split("【】");
												String[] answers = allanswers[i].split("【;】");
												String[] scores = model.getScore().split("【】");
												String tag = "1";// 有回答错误
												for (int x = 0, y = rights.length; x < y; x++) {
													if (x == 0) {
														paperAnswer = answers[x];
													} else {
														paperAnswer = paperAnswer + "【】" + answers[x];
													}
													if (model.getIsRight(rights[x], answers[x])) {
														score = score + Float.valueOf(scores[x]) * paperContent.getScore() / Float.valueOf(questionsInfo.getScore());
													} else {
														tag = "0";
													}
												}
												if ("1".equals(tag)) {
													result0 = "1";
												}
											} else {
												paperAnswer = allanswers[i];
												if (model.getIsRight(model.getRightans(), paperAnswer)) {
													result0 = "1";
													score = Float.valueOf(model.getScore()) * paperContent.getScore() / parentScore;
												}
											}
											answer.setAnswer(paperAnswer);
											if ("0".equals(result0)) {
												answer.setIsright("0");
											} else {
												answer.setIsright("1");
											}
											answer.setScore(score);
											tpam.addTkPaperAnswer(answer);
										}
									}
								}
							} else {
								answer.setChildid(0);
								answer.setAnswer(useranswer);

								Float score = 0.0F;
								if (questionsInfo.getOptionnum().intValue() > 1) {
									String[] rights = questionsInfo.getRightans().split("【】");
									String[] answers = useranswer.split("【】");
									String[] scores = questionsInfo.getScore().split("【】");
									// 注意：计算分值时，需要按百分比计算得分
									Float totalScore = questionsInfo.getTotalScore();
									String tag = "1";// 有回答错误
									for (int i = 0, size = rights.length; i < size; i++) {
										if (questionsInfo.getIsRight(rights[i], answers[i])) {
											score = score + Float.valueOf(scores[i]) * paperContent.getScore() / totalScore;
										} else {
											tag = "0";
										}
									}
									if ("1".equals(tag)) {
										answer.setIsright("1");
									} else {
										answer.setIsright("0");
									}
								} else {
									if (questionsInfo.getIsRight(questionsInfo.getRightans(), useranswer)) {
										answer.setIsright("1");
										score = paperContent.getScore();
									} else {
										answer.setIsright("0");
									}
								}
								answer.setScore(score);
								tpam.addTkPaperAnswer(answer);
							}
						} else {// 未提交，重新作答
							if ("A".equals(type.getType()) || "B".equals(type.getType()) || "C".equals(type.getType())) {
								answer.setAnswer(useranswer);
								if (useranswer.equals(questionsInfo.getRightans())) {
									answer.setScore(paperContent.getScore());
									answer.setIsright("1");
								} else {
									answer.setScore(0.0F);
									answer.setIsright("0");
								}
								tpam.updateTkPaperAnswer(answer);
							} else if ("F".equals(type.getType()) || "M".equals(type.getType())) {
								// 查询当前主题的所有子题作答
								List childAnswerList = tpam.getAllChildTkPaperAnswer(userid, taskid, paperContent.getContentid().toString(), "1");
								Map childAnswerMap = new HashMap();
								TkPaperAnswer childAnswer = null;
								for (int i = 0, size = childAnswerList.size(); i < size; i++) {
									childAnswer = (TkPaperAnswer) childAnswerList.get(i);
									childAnswerMap.put(childAnswer.getChildid(), childAnswer);
								}

								// 复合题包含多道子题
								String[] allanswers = useranswer.split("【】");
								int len = allanswers.length;
								List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid());
								if (childList == null) {
									childList = tqim.getAllChildTkQuestionsInfos(questionsInfo.getQuestionid());
									CacheUtil.putList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid(), childList, 24 * 30);
								}
								TkQuestionsInfo model = null;
								TkQuestionsType tqt = null;
								Float parentScore = Float.valueOf(questionsInfo.getScore());
								for (int i = 0, size = childList.size(); i < size; i++) {
									if (len > i) {
										model = (TkQuestionsInfo) childList.get(i);
										tqt = model.getTkQuestionsType();
										childAnswer = (TkPaperAnswer) childAnswerMap.get(model.getQuestionid());
										if (childAnswer == null) {// 子题没有作答提交
											childAnswer = answer;
											childAnswer.setChildid(model.getQuestionid());
										}

										if ("A".equals(tqt.getType()) || "B".equals(tqt.getType()) || "C".equals(tqt.getType())) {
											childAnswer.setAnswer(allanswers[i]);
											if (model.getRightans().equals(allanswers[i])) {
												childAnswer.setIsright("1");
												childAnswer.setScore(Float.valueOf(model.getScore()) * paperContent.getScore() / parentScore);
											} else {
												childAnswer.setIsright("0");
												childAnswer.setScore(0.0F);
											}
											if (childAnswer.getAnswerid() == null || childAnswer.getAnswerid().intValue() == 0) {
												tpam.addTkPaperAnswer(childAnswer);
											} else {
												tpam.updateTkPaperAnswer(childAnswer);
											}
										} else if ("E".equals(tqt.getType())) {
											String result0 = "0";
											Float score = 0.0F;
											String paperAnswer = null;
											if (model.getOptionnum().intValue() > 1) {
												String[] rights = model.getRightans().split("【】");
												String[] answers = allanswers[i].split("【;】");
												String[] scores = model.getScore().split("【】");
												String tag = "1";// 有回答错误
												for (int x = 0, y = rights.length; x < y; x++) {
													if (x == 0) {
														paperAnswer = answers[x];
													} else {
														paperAnswer = paperAnswer + "【】" + answers[x];
													}
													if (model.getIsRight(rights[x], answers[x])) {
														score = score + Float.valueOf(scores[x]) * paperContent.getScore() / Float.valueOf(questionsInfo.getScore());
													} else {
														tag = "0";
													}
												}
												if ("1".equals(tag)) {
													result0 = "1";
												}
											} else {
												paperAnswer = allanswers[i];
												if (model.getIsRight(model.getRightans(), paperAnswer)) {
													result0 = "1";
													score = Float.valueOf(model.getScore()) * paperContent.getScore() / parentScore;
												}
											}
											childAnswer.setAnswer(paperAnswer);
											if ("0".equals(result0)) {
												childAnswer.setIsright("0");
											} else {
												childAnswer.setIsright("1");
											}
											childAnswer.setScore(score);
											if (childAnswer.getAnswerid() == null || childAnswer.getAnswerid().intValue() == 0) {
												tpam.addTkPaperAnswer(childAnswer);
											} else {
												tpam.updateTkPaperAnswer(childAnswer);
											}
										}
									}
								}
							} else {// 填空题
								answer.setAnswer(useranswer);

								Float score = 0.0F;
								if (questionsInfo.getOptionnum().intValue() > 1) {
									String[] rights = questionsInfo.getRightans().split("【】");
									String[] answers = useranswer.split("【】");
									String[] scores = questionsInfo.getScore().split("【】");
									// 注意：计算分值时，需要按百分比计算得分
									Float totalScore = questionsInfo.getTotalScore();
									String tag = "1";// 有回答错误
									for (int i = 0, size = rights.length; i < size; i++) {
										if (questionsInfo.getIsRight(rights[i], answers[i])) {
											score = score + Float.valueOf(scores[i]) * paperContent.getScore() / totalScore;
										} else {
											tag = "0";
										}
									}
									if ("1".equals(tag)) {
										answer.setIsright("1");
									} else {
										answer.setIsright("0");
									}
								} else {
									if (questionsInfo.getIsRight(questionsInfo.getRightans(), useranswer)) {
										answer.setIsright("1");
										score = paperContent.getScore();
									} else {
										answer.setIsright("0");
									}
								}
								answer.setScore(score);
								tpam.updateTkPaperAnswer(answer);
							}
						}

						// ===以下需要自动进入下一题，作答进入下一题不需要考虑next的值===
						if (tindex > 1)
							prebutton = "1";
						if (questionList.size() > tindex) {
							paperContent = (TkPaperContent) questionList.get(tindex);// 假如当前做的是第一题，则作答后自动跳转到第二题
							tindex = tindex + 1;
							if (tindex > 1)
								prebutton = "1";
							if (questionList.size() > tindex)
								nextbutton = "1";
						} else {
							paperContent = (TkPaperContent) questionList.get(tindex - 1);
						}
						questionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + paperContent.getQuestionid());
						if (questionsInfo == null) {
							questionsInfo = tqim.getTkQuestionsInfo(paperContent.getQuestionid());
							CacheUtil.put("TkQuestionsInfo_" + paperContent.getQuestionid(), questionsInfo, 24 * 30);
						}
					} else {
						if ("1".equals(next)) {
							if (tindex > 1)
								prebutton = "1";
							if (questionList.size() > tindex) {
								nextbutton = "1";
							}
						} else {
							nextbutton = "1";
							if (tindex > 1)
								prebutton = "1";
						}
					}
					answer = tpam.getTkPaperAnswer(userid, taskid, paperContent.getContentid().toString(), "1");// 关联试卷试题【重新查询，因为作答时自动进入下一题，paperContent改变了】
					request.setAttribute("answer", answer);

					request.setAttribute("model", questionsInfo);
					request.setAttribute("paperContent", paperContent);
					request.setAttribute("index", tindex);
					request.setAttribute("next", next);
					request.setAttribute("prebutton", prebutton);
					request.setAttribute("nextbutton", nextbutton);

					// 题型分开显示，方便维护
					TkQuestionsType questionsType = questionsInfo.getTkQuestionsType();
					if ("A".equals(questionsType.getType()) || "B".equals(questionsType.getType()) || "C".equals(questionsType.getType())) {// 单选题
						return mapping.findForward("lianxi");
					} else if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {// 复合题
						// 查询当前试题的所有子题，如果有则是复合题【缓存查询子题】
						List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid());
						if (childList == null) {
							childList = tqim.getAllChildTkQuestionsInfos(questionsInfo.getQuestionid());
							CacheUtil.putList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid(), childList, 24 * 30);
						}
						if (childList != null && childList.size() > 0) {// 复合题
							// 如果是复合题，则每个子题的作答情况也需要查看详情
							List allChildAnswerList = tpam.getAllChildTkPaperAnswer(userid, taskid, paperContent.getContentid().toString(), "1");
							Map childAnswerMap = new HashMap();
							if (allChildAnswerList != null && allChildAnswerList.size() > 0) {
								TkPaperAnswer tkPaperAnswer = null;
								for (int i = 0, size = allChildAnswerList.size(); i < size; i++) {
									tkPaperAnswer = (TkPaperAnswer) allChildAnswerList.get(i);
									childAnswerMap.put(tkPaperAnswer.getChildid(), tkPaperAnswer);
								}
							}

							request.setAttribute("childList", childList);
							request.setAttribute("allChildAnswerList", allChildAnswerList);
							request.setAttribute("childAnswerMap", childAnswerMap);

							return mapping.findForward("lianxiM");
						} else {// 填空题
							return mapping.findForward("lianxiE");
						}
					} else {
						return mapping.findForward("lianxiE");
					}
				} else {
					request.setAttribute("msg", "当前作业没有试题！");
					return mapping.findForward("lianxitips");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 提交作业确认
	 */
	public ActionForward finishConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String taskid = Encode.nullToBlank(request.getParameter("taskid"));
				String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
				String index = Encode.nullToBlank(request.getParameter("index"));
				String next = Encode.nullToBlank(request.getParameter("next"));
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("taskid", taskid);
				request.setAttribute("tasktype", tasktype);
				request.setAttribute("index", index);
				request.setAttribute("next", next);

				return mapping.findForward("finishconfirm");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 提交作业
	 */
	public ActionForward finish(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String taskid = Encode.nullToBlank(request.getParameter("taskid"));
				String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
				TkBookContentTaskManager manager = (TkBookContentTaskManager) getBean("tkBookContentTaskManager");
				TkBookContentTask task = manager.getTkBookContentTask(taskid);
				task.setIscommit("1");
				manager.updateTkBookContentTask(task);

				// 获取当前任务的所有作答情况，做错的需要添加错题本中
				TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "type", "=", "1");
				SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
				SearchCondition.addCondition(condition, "taskid", "=", Integer.valueOf(taskid));
				SearchCondition.addCondition(condition, "tasktype", "=", tasktype);
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
				List list = tpam.getTkPaperAnswers(condition, "answerid", 0);
				List lst = new ArrayList();// 复合题只把大题加入错题本
				TkPaperAnswer answer = null;
				for (int i = 0, size = list.size(); i < size; i++) {
					answer = (TkPaperAnswer) list.get(i);
					if (!"1".equals(answer.getIsright()) && !lst.contains(answer.getContentid())) {
						lst.add(answer.getContentid());
						addUserErrorQuestion(answer);
					}
				}

				return bookContent(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 学生进入在线作答作业查看已做作业 【共用数据尽量用缓存处理】
	 */
	public ActionForward viewLianxi(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String taskid = Encode.nullToBlank(request.getParameter("taskid"));
				String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("taskid", taskid);
				request.setAttribute("tasktype", tasktype);

				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 获取作业对应的题，先从缓存中获取，没有再查下数据库
				TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent bookContent = tbcm.getTkBookContent(bookcontentid);
				List questionList = CacheUtil.getList("TkPaperContentList_" + bookContent.getPaperid());
				if (questionList == null) {
					TkPaperContentManager tpcm = (TkPaperContentManager) getBean("tkPaperContentManager");
					questionList = tpcm.getMobileTkPaperContents(bookContent.getPaperid());
					CacheUtil.putList("TkPaperContentList_" + bookContent.getPaperid(), questionList, 24 * 30);
				}
				if (questionList != null && questionList.size() > 0) {
					condition.clear();
					// 选择上一题或下一题时，可能已经是做过的
					String index = Encode.nullToBlank(request.getParameter("index"));// 当前做题数，方便获取下一题，第一次进入为1
					String next = Encode.nullToBlank(request.getParameter("next"));// 1表示获取下一题，-1表示获取上一题

					TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
					int tindex = Integer.valueOf(index);
					String prebutton = "0";// “上一题”按钮
					String nextbutton = "0";// “下一题”按钮
					TkPaperContent paperContent = (TkPaperContent) questionList.get(tindex - 1);
					CacheUtil.put("TkPaperContent_" + paperContent.getContentid(), paperContent, 24 * 30);
					TkQuestionsInfo questionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + paperContent.getQuestionid());
					if (questionsInfo == null) {
						questionsInfo = tqim.getTkQuestionsInfo(paperContent.getQuestionid());
						CacheUtil.put("TkQuestionsInfo_" + paperContent.getQuestionid(), questionsInfo, 24 * 30);
					}

					// 选择上一题或下一题时，可能已经是做过的
					TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
					TkPaperAnswer answer = tpam.getTkPaperAnswer(userid, taskid, paperContent.getContentid().toString(), "1");// 关联试卷试题
					if ("1".equals(next)) {
						if (tindex > 1)
							prebutton = "1";
						if (questionList.size() > tindex) {
							nextbutton = "1";
						}
					} else {
						nextbutton = "1";
						if (tindex > 1)
							prebutton = "1";
					}
					request.setAttribute("answer", answer);

					request.setAttribute("model", questionsInfo);
					request.setAttribute("paperContent", paperContent);
					request.setAttribute("index", tindex);
					request.setAttribute("next", next);
					request.setAttribute("prebutton", prebutton);
					request.setAttribute("nextbutton", nextbutton);

					// 每道题都可能有相关微课或举一反三题
					TkQuestionsFilmManager tqfm = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
					TkQuestionsSimilarManager tqsm = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
					int filmCounts = tqfm.getCountTkQuestionsFilms(questionsInfo.getQuestionid());
					int similarCounts = tqsm.getCountTkQuestionsSimilars(questionsInfo.getQuestionid());
					request.setAttribute("filmCounts", filmCounts);
					request.setAttribute("similarCounts", similarCounts);

					// 题型分开显示，方便维护
					TkQuestionsType questionsType = questionsInfo.getTkQuestionsType();
					if ("A".equals(questionsType.getType()) || "B".equals(questionsType.getType()) || "C".equals(questionsType.getType()) || "E".equals(questionsType.getType())) {// 单选题
						return mapping.findForward("viewlianxi");
					} else if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {// 复合题
						// 查询当前试题的所有子题，如果有则是复合题【缓存查询子题】
						List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid());
						if (childList == null) {
							childList = tqim.getAllChildTkQuestionsInfos(questionsInfo.getQuestionid());
							CacheUtil.putList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid(), childList, 24 * 30);
						}
						if (childList != null && childList.size() > 0) {// 复合题
							// 如果是复合题，则每个子题的作答情况也需要查看详情
							List allChildAnswerList = tpam.getAllChildTkPaperAnswer(userid, taskid, paperContent.getContentid().toString(), "1");
							Map childAnswerMap = new HashMap();
							if (allChildAnswerList != null && allChildAnswerList.size() > 0) {
								TkPaperAnswer tkPaperAnswer = null;
								for (int i = 0, size = allChildAnswerList.size(); i < size; i++) {
									tkPaperAnswer = (TkPaperAnswer) allChildAnswerList.get(i);
									childAnswerMap.put(tkPaperAnswer.getChildid(), tkPaperAnswer);
								}
							}

							request.setAttribute("childList", childList);
							request.setAttribute("allChildAnswerList", allChildAnswerList);
							request.setAttribute("childAnswerMap", childAnswerMap);

							return mapping.findForward("viewlianxiM");
						} else {// 填空题
							return mapping.findForward("viewlianxi");
						}
					} else {
						return mapping.findForward("viewlianxi");
					}
				} else {
					request.setAttribute("msg", "当前作业没有试题！");
					return mapping.findForward("lianxitips");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 填答题卡
	 */
	public ActionForward answerCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String taskid = Encode.nullToBlank(request.getParameter("taskid"));
				String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("taskid", taskid);
				request.setAttribute("tasktype", tasktype);
				
				String iscommit = Encode.nullToBlank(request.getParameter("iscommit"));//1表示已提交，不可重复提交
				request.setAttribute("iscommit", iscommit);

				// 先查出当前作业的所有题，如果有复合题，还需要查询关联子题，再查询所有题的作答情况
				// 获取作业对应的题，先从缓存中获取，没有再查下数据库
				TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent bookContent = tbcm.getTkBookContent(bookcontentid);
				List questionList = CacheUtil.getList("TkPaperContentList_" + bookContent.getPaperid());
				if (questionList == null) {
					TkPaperContentManager tpcm = (TkPaperContentManager) getBean("tkPaperContentManager");
					questionList = tpcm.getMobileTkPaperContents(bookContent.getPaperid());
					CacheUtil.putList("TkPaperContentList_" + bookContent.getPaperid(), questionList, 24 * 30);
				}
				if (questionList != null && questionList.size() > 0) {
					TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
					Map questionMap = new HashMap();
					Map childMap = new HashMap();
					TkPaperContent paperContent = null;
					TkQuestionsInfo tqi = null;
					TkQuestionsType type = null;
					List childList = null;
					for (int i = 0, size = questionList.size(); i < size; i++) {
						paperContent = (TkPaperContent) questionList.get(i);
						tqi = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + paperContent.getQuestionid());
						if (tqi == null) {
							tqi = tqim.getTkQuestionsInfo(paperContent.getQuestionid());
							CacheUtil.put("TkQuestionsInfo_" + paperContent.getQuestionid(), tqi, 24 * 30);
						}
						questionMap.put(paperContent.getQuestionid(), tqi);
						type = tqi.getTkQuestionsType();
						if ("F".equals(type.getType()) || "M".equals(type.getType())) {// 复合题
							childList = CacheUtil.getList("TkQuestionsInfoChildList_" + tqi.getQuestionid());
							if (childList == null) {
								childList = tqim.getAllChildTkQuestionsInfos(tqi.getQuestionid());
								CacheUtil.putList("TkQuestionsInfoChildList_" + tqi.getQuestionid(), childList, 24 * 30);
							}
							if (childList != null && childList.size() > 0) {
								childMap.put(paperContent.getQuestionid(), childList);
							}
						}
					}
					request.setAttribute("questionList", questionList);
					request.setAttribute("questionMap", questionMap);
					request.setAttribute("childMap", childMap);

					// 查询作答情况
					TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "type", "=", "1");
					SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
					SearchCondition.addCondition(condition, "taskid", "=", Integer.valueOf(taskid));
					SearchCondition.addCondition(condition, "tasktype", "=", tasktype);
					SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
					SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
					List list = tpam.getTkPaperAnswers(condition, "answerid", 0);
					Map answerMap = new HashMap();
					TkPaperAnswer answer = null;
					for (int i = 0, size = list.size(); i < size; i++) {
						answer = (TkPaperAnswer) list.get(i);
						answerMap.put(answer.getContentid() + "_" + answer.getChildid(), answer);
					}
					request.setAttribute("answerMap", answerMap);

					return mapping.findForward("answercard");
				} else {
					request.setAttribute("msg", "当前作业没有试题！");
					return mapping.findForward("lianxitips");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 提交填答题卡
	 */
	public ActionForward finishAnswerCard(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String taskid = Encode.nullToBlank(request.getParameter("taskid"));
				String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));

				// 先查出当前作业的所有题，如果有复合题，还需要查询关联子题，再查询所有题的作答情况
				// 获取作业对应的题，先从缓存中获取，没有再查下数据库
				TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent bookContent = tbcm.getTkBookContent(bookcontentid);
				List questionList = CacheUtil.getList("TkPaperContentList_" + bookContent.getPaperid());
				if (questionList == null) {
					TkPaperContentManager tpcm = (TkPaperContentManager) getBean("tkPaperContentManager");
					questionList = tpcm.getMobileTkPaperContents(bookContent.getPaperid());
					CacheUtil.putList("TkPaperContentList_" + bookContent.getPaperid(), questionList, 24 * 30);
				}
				if (questionList != null && questionList.size() > 0) {
					// 先查询历史作答情况
					TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "type", "=", "1");
					SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
					SearchCondition.addCondition(condition, "taskid", "=", Integer.valueOf(taskid));
					SearchCondition.addCondition(condition, "tasktype", "=", tasktype);
					SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
					SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
					List list = tpam.getTkPaperAnswers(condition, "answerid", 0);
					Map answerMap = new HashMap();
					TkPaperAnswer answer = null;
					for (int i = 0, size = list.size(); i < size; i++) {
						answer = (TkPaperAnswer) list.get(i);
						answerMap.put(answer.getContentid() + "_" + answer.getChildid(), answer);
					}

					TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
					TkPaperContent paperContent = null;
					TkQuestionsInfo tqi = null;
					TkQuestionsType type = null;
					List childList = null;
					for (int i = 0, size = questionList.size(); i < size; i++) {
						paperContent = (TkPaperContent) questionList.get(i);
						tqi = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + paperContent.getQuestionid());
						if(tqi == null){
							tqi = tqim.getTkQuestionsInfo(paperContent.getQuestionid());
							CacheUtil.put("TkQuestionsInfo_" + paperContent.getQuestionid(), tqi, 24 * 30);
						}
						type = tqi.getTkQuestionsType();
						if ("A".equals(type.getType()) || "B".equals(type.getType()) || "C".equals(type.getType())) {
							String item = request.getParameter("item_" + i);
							if ("B".equals(type.getType())) {// 说明：多选题如果前期已经作答过但没有提交，现通过答题卡提交时，如果多选题去掉所有勾选是无效的，原始作答结果保留
								String[] items = request.getParameterValues("item_" + i);
								if (items != null && items.length > 0) {
									item = "";
									for (int m = 0; m < items.length; m++) {
										item = item + items[m];
									}
								} else {
									item = null;
								}
							}
							answer = (TkPaperAnswer) answerMap.get(paperContent.getContentid() + "_0");
							if (item != null) {
								if (answer != null) {
									answer.setAnswer(item);
									if (tqi.getRightans().equals(item)) {
										answer.setIsright("1");
										answer.setScore(paperContent.getScore());
									} else {
										answer.setIsright("0");
										answer.setScore(0.0F);
									}
									tpam.updateTkPaperAnswer(answer);
								} else {
									answer = new TkPaperAnswer();
									answer.setContentid(paperContent.getContentid());
									answer.setChildid(0);
									answer.setType("1");
									answer.setBookcontentid(Integer.valueOf(bookcontentid));
									answer.setTaskid(Integer.valueOf(taskid));
									answer.setTasktype(tasktype);
									answer.setUserid(Integer.valueOf(userid));
									answer.setClassid(Integer.valueOf(classid));
									answer.setAnswer(item);
									if (tqi.getRightans().equals(item)) {
										answer.setIsright("1");
										answer.setScore(paperContent.getScore());
									} else {
										answer.setIsright("0");
										answer.setScore(0.0F);
									}
									tpam.addTkPaperAnswer(answer);
								}
							}
							if (answer != null && !"1".equals(answer.getIsright())) {
								addUserErrorQuestion(answer);
							}
						} else if ("F".equals(type.getType()) || "M".equals(type.getType())) {// 复合题
							childList = CacheUtil.getList("TkQuestionsInfoChildList_" + tqi.getQuestionid());
							if (childList == null) {
								childList = tqim.getAllChildTkQuestionsInfos(tqi.getQuestionid());
								CacheUtil.putList("TkQuestionsInfoChildList_" + tqi.getQuestionid(), childList, 24 * 30);
							}
							TkQuestionsInfo model = null;
							TkQuestionsType tqt = null;
							Float parentScore = Float.valueOf(tqi.getScore());
							String iswrong = "0";// 复合题有一个子题错误就算做错
							for (int m = 0, n = childList.size(); m < n; m++) {
								model = (TkQuestionsInfo) childList.get(m);
								tqt = model.getTkQuestionsType();
								answer = (TkPaperAnswer) answerMap.get(paperContent.getContentid() + "_" + model.getQuestionid());
								if ("A".equals(tqt.getType()) || "B".equals(tqt.getType()) || "C".equals(tqt.getType())) {
									String item = request.getParameter("item_" + i + "_" + m);
									if ("B".equals(tqt.getType())) {// 说明：多选题如果前期已经作答过但没有提交，现通过答题卡提交时，如果多选题去掉所有勾选是无效的，原始作答结果保留
										String[] items = request.getParameterValues("item_" + i + "_" + m);
										if (items != null && items.length > 0) {
											item = "";
											for (int x = 0; x < items.length; x++) {
												item = item + items[x];
											}
										} else {
											item = null;
										}
									}
									if (item != null) {
										if (answer != null) {
											answer.setAnswer(item);
											if (model.getRightans().equals(item)) {
												answer.setIsright("1");
												answer.setScore(Float.valueOf(model.getScore()) * paperContent.getScore() / parentScore);
											} else {
												answer.setIsright("0");
												answer.setScore(0.0F);
												iswrong = "1";
											}
											tpam.updateTkPaperAnswer(answer);
										} else {
											answer = new TkPaperAnswer();
											answer.setContentid(paperContent.getContentid());
											answer.setChildid(model.getQuestionid());
											answer.setType("1");
											answer.setBookcontentid(Integer.valueOf(bookcontentid));
											answer.setTaskid(Integer.valueOf(taskid));
											answer.setTasktype(tasktype);
											answer.setUserid(Integer.valueOf(userid));
											answer.setClassid(Integer.valueOf(classid));
											answer.setAnswer(item);
											if (tqi.getRightans().equals(item)) {
												answer.setIsright("1");
												answer.setScore(Float.valueOf(model.getScore()) * paperContent.getScore() / parentScore);
											} else {
												answer.setIsright("0");
												answer.setScore(0.0F);
												iswrong = "1";
											}
											tpam.addTkPaperAnswer(answer);
										}
									} else {
										iswrong = "1";
									}
								} else if ("E".equals(tqt.getType())) {
									Float score = 0.0F;
									if (model.getOptionnum().intValue() > 1) {
										String[] rights = model.getRightans().split("【】");
										String[] scores = model.getScore().split("【】");
										String tag = "1";// 有回答错误
										StringBuffer items = new StringBuffer();
										String item = null;
										for (int x = 0, y = model.getOptionnum().intValue(); x < y; x++) {
											item = request.getParameter("item_" + i + "_" + m + "_" + (x + 1));
											if ("".equals(item))
												item = " ";
											items.append("【】").append(item);
											if (model.getIsRight(rights[x], item)) {
												score = score + Float.valueOf(scores[x]) * paperContent.getScore() / parentScore;
											} else {
												tag = "0";
											}
										}

										if (answer != null) {
											answer.setAnswer(items.substring(2));
											if ("1".equals(tag)) {
												answer.setIsright("1");
											} else {
												answer.setIsright("0");
												iswrong = "1";
											}
											answer.setScore(score);
											tpam.updateTkPaperAnswer(answer);
										} else {
											answer = new TkPaperAnswer();
											answer.setContentid(paperContent.getContentid());
											answer.setChildid(model.getQuestionid());
											answer.setType("1");
											answer.setBookcontentid(Integer.valueOf(bookcontentid));
											answer.setTaskid(Integer.valueOf(taskid));
											answer.setTasktype(tasktype);
											answer.setUserid(Integer.valueOf(userid));
											answer.setClassid(Integer.valueOf(classid));
											answer.setAnswer(items.substring(2));
											if ("1".equals(tag)) {
												answer.setIsright("1");
											} else {
												answer.setIsright("0");
												iswrong = "1";
											}
											answer.setScore(score);
											tpam.addTkPaperAnswer(answer);
										}
									} else {
										String item = request.getParameter("item_" + i + "_" + m + "_1");
										if (item != null) {
											if ("".equals(item))
												item = " ";
											if (answer != null) {
												answer.setAnswer(item);
												if (model.getIsRight(model.getRightans(), item)) {
													answer.setIsright("1");
													answer.setScore(Float.valueOf(model.getScore()) * paperContent.getScore() / parentScore);
												} else {
													answer.setIsright("0");
													answer.setScore(0.0F);
													iswrong = "1";
												}
												tpam.updateTkPaperAnswer(answer);
											} else {
												answer = new TkPaperAnswer();
												answer.setContentid(paperContent.getContentid());
												answer.setChildid(model.getQuestionid());
												answer.setType("1");
												answer.setBookcontentid(Integer.valueOf(bookcontentid));
												answer.setTaskid(Integer.valueOf(taskid));
												answer.setTasktype(tasktype);
												answer.setUserid(Integer.valueOf(userid));
												answer.setClassid(Integer.valueOf(classid));
												answer.setAnswer(item);
												if (model.getIsRight(model.getRightans(), item)) {
													answer.setIsright("1");
													answer.setScore(Float.valueOf(model.getScore()) * paperContent.getScore() / parentScore);
												} else {
													answer.setIsright("0");
													answer.setScore(0.0F);
													iswrong = "1";
												}
												tpam.addTkPaperAnswer(answer);
											}
										} else {
											iswrong = "1";
										}
									}
								}
							}

							if ("1".equals(iswrong) && answer != null) {
								addUserErrorQuestion(answer);
							}
						} else {
							answer = (TkPaperAnswer) answerMap.get(paperContent.getContentid() + "_0");
							if (tqi.getOptionnum().intValue() > 1) {
								String[] rights = tqi.getRightans().split("【】");
								String[] scores = tqi.getScore().split("【】");
								// 注意：计算分值时，需要按百分比计算得分
								Float totalScore = tqi.getTotalScore();
								String tag = "1";// 有回答错误
								StringBuffer items = new StringBuffer();
								String item = null;
								Float score = 0.0F;
								for (int m = 0, n = tqi.getOptionnum().intValue(); m < n; m++) {
									item = request.getParameter("item_" + i + "_" + (m + 1));
									if ("".equals(item))
										item = " ";
									items.append("【】").append(item);
									if (tqi.getIsRight(rights[m], item)) {
										score = score + Float.valueOf(scores[m]) * paperContent.getScore() / totalScore;
									} else {
										tag = "0";
									}
								}

								if (answer != null) {
									answer.setAnswer(items.substring(2));
									if ("1".equals(tag)) {
										answer.setIsright("1");
									} else {
										answer.setIsright("0");
									}
									answer.setScore(score);
									tpam.updateTkPaperAnswer(answer);
								} else {
									answer = new TkPaperAnswer();
									answer.setContentid(paperContent.getContentid());
									answer.setChildid(0);
									answer.setType("1");
									answer.setBookcontentid(Integer.valueOf(bookcontentid));
									answer.setTaskid(Integer.valueOf(taskid));
									answer.setTasktype(tasktype);
									answer.setUserid(Integer.valueOf(userid));
									answer.setClassid(Integer.valueOf(classid));
									answer.setAnswer(items.substring(2));
									if ("1".equals(tag)) {
										answer.setIsright("1");
									} else {
										answer.setIsright("0");
									}
									answer.setScore(score);
									tpam.addTkPaperAnswer(answer);
								}
							} else {
								String item = request.getParameter("item_" + i + "_1");
								if (item != null) {
									if ("".equals(item))
										item = " ";
									if (answer != null) {
										answer.setAnswer(item);
										if (tqi.getIsRight(tqi.getRightans(), item)) {
											answer.setIsright("1");
											answer.setScore(paperContent.getScore());
										} else {
											answer.setIsright("0");
											answer.setScore(0.0F);
										}
										tpam.updateTkPaperAnswer(answer);
									} else {
										answer = new TkPaperAnswer();
										answer.setContentid(paperContent.getContentid());
										answer.setChildid(0);
										answer.setType("1");
										answer.setBookcontentid(Integer.valueOf(bookcontentid));
										answer.setTaskid(Integer.valueOf(taskid));
										answer.setTasktype(tasktype);
										answer.setUserid(Integer.valueOf(userid));
										answer.setClassid(Integer.valueOf(classid));
										answer.setAnswer(item);
										if (tqi.getIsRight(tqi.getRightans(), item)) {
											answer.setIsright("1");
											answer.setScore(paperContent.getScore());
										} else {
											answer.setIsright("0");
											answer.setScore(0.0F);
										}
										tpam.addTkPaperAnswer(answer);
									}
								}
							}
							if (answer != null && !"1".equals(answer.getIsright())) {
								addUserErrorQuestion(answer);
							}
						}
					}
					TkBookContentTaskManager tbctm = (TkBookContentTaskManager) getBean("tkBookContentTaskManager");
					TkBookContentTask task = tbctm.getTkBookContentTask(taskid);
					task.setIscommit("1");
					tbctm.updateTkBookContentTask(task);
				}

				return bookContent(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	// ==============================================举一反三=================================================
	/*
	 * 说明：学生看举一反三题时有三个地方，1、在做预习题时，可直接看举一反三；2、在查看已经做过的练习那可以看举一反三；3、在查看错题时可以看举一反三
	 * 看完举一反三后，可以方便返回到当前看举一反三题之前的位置
	 */
	public ActionForward viewSimilar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				setAttributes(request);// 设置携带的参数
				String qid = Encode.nullToBlank(request.getParameter("qid"));
				request.setAttribute("qid", qid);

				// 获取试题对应的举一反三题，先从缓存中获取，没有再查下数据库
				List questionList = CacheUtil.getList("TkQuestionsInfoList_3_" + qid);// 1预习，2学案，3举一反三
				if (questionList == null) {
					TkQuestionsSimilarManager tqsm = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
					questionList = tqsm.getTkQuestionsSimilars(qid);
					CacheUtil.putList("TkQuestionsInfoList_3_" + qid, questionList, 24 * 30);
				}

				String index0 = Encode.nullToBlank(request.getParameter("index0"));// 当前做题数，方便获取下一题，第一次进入为1
				String next0 = Encode.nullToBlank(request.getParameter("next0"));// 1表示获取下一题，-1表示获取上一题
				if ("".equals(index0))
					index0 = "1";
				if ("".equals(next0))
					next0 = "1";

				int tindex = Integer.valueOf(index0);
				String prebutton = "0";// “上一题”按钮
				String nextbutton = "0";// “下一题”按钮
				TkQuestionsInfo questionsInfo = (TkQuestionsInfo) questionList.get(tindex - 1);
				CacheUtil.put("TkQuestionsInfo_" + questionsInfo.getQuestionid(), questionsInfo, 24 * 30);
				if ("1".equals(next0)) {
					if (tindex > 1)
						prebutton = "1";
					if (questionList.size() > tindex) {
						nextbutton = "1";
					}
				} else {
					nextbutton = "1";
					if (tindex > 1)
						prebutton = "1";
				}
				request.setAttribute("model", questionsInfo);
				request.setAttribute("index0", tindex);
				request.setAttribute("next0", next0);
				request.setAttribute("prebutton", prebutton);
				request.setAttribute("nextbutton", nextbutton);

				// 记录举一反三题被查看情况
				TkQuestionsSimilarWatchManager tqswm = (TkQuestionsSimilarWatchManager) getBean("tkQuestionsSimilarWatchManager");
				TkQuestionsSimilarWatch similarWatch = new TkQuestionsSimilarWatch();
				similarWatch.setUserid(Integer.valueOf(userid));
				similarWatch.setQuestionid(questionsInfo.getQuestionid());
				similarWatch.setCreatedate(DateTime.getDate());
				similarWatch.setUserip(IpUtil.getIpAddr(request));
				tqswm.addTkQuestionsSimilarWatch(similarWatch);

				// 题型分开显示，方便维护
				TkQuestionsType questionsType = questionsInfo.getTkQuestionsType();
				if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {
					// 查询当前试题的所有子题，如果有则是复合题
					TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
					List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid());
					if (childList == null) {
						childList = tqim.getAllChildTkQuestionsInfos(questionsInfo.getQuestionid());
						CacheUtil.putList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid(), childList, 24 * 30);
					}
					if (childList != null && childList.size() > 0) {// 复合题
						request.setAttribute("childList", childList);
						return mapping.findForward("viewsimilarM");
					} else {// 填空题
						return mapping.findForward("viewsimilar");
					}
				} else {
					return mapping.findForward("viewsimilar");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 查看微课
	 * 【题关联微课需要放缓存中获取，所以只记录具体看微课记录，不改变微课属性hits字段】
	 */
	public ActionForward videoPlay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				setAttributes(request);// 设置携带的参数
				String qid = Encode.nullToBlank(request.getParameter("qid"));
				request.setAttribute("qid", qid);

				String filmid = Encode.nullToBlank(request.getParameter("filmid"));// 直接点击微课跳转
				String pixid = Encode.nullToBlank(request.getParameter("pixid"));// 微课包含多个视频时，点击视频文件跳转

				// 获取试题对应的微课，先从缓存中获取，没有再查下数据库
				List filmList = CacheUtil.getList("VwhFilmInfoList_" + qid);
				if (filmList == null) {
					TkQuestionsFilmManager tqfm = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
					filmList = tqfm.getTkQuestionsFilms(qid);
					CacheUtil.putList("VwhFilmInfoList_" + qid, filmList, 24 * 30);
				}
				
				VwhFilmInfo model = null;
				if("".equals(filmid)){
					model = (VwhFilmInfo) filmList.get(0);
					filmid = model.getFilmid().toString();
				}else {
					VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
					model = vfim.getVwhFilmInfo(filmid);
				}
				
				VwhComputerInfo computerInfo = CacheUtil.getVwhComputerInfo("VwhComputerInfo_" + model.getComputerid());
				if(computerInfo == null){
					VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
					computerInfo = vcim.getVwhComputerInfo(model.getComputerid());
					CacheUtil.put("VwhComputerInfo_" + model.getComputerid(), computerInfo, 24 * 30);
				}
				
				List pixList = CacheUtil.getList("VwhFilmPixList_" + filmid);
				if(pixList == null){
					VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
					pixList = vfpm.getVwhFilmPixsByFilmid(Integer.valueOf(filmid));
					CacheUtil.putList("VwhFilmPixList_" + filmid, pixList, 24 * 30);
				}
				
				VwhFilmPix pix = null;
				if("".equals(pixid)){
					pix = (VwhFilmPix) pixList.get(0);
					pixid = pix.getPixid().toString();
				}else {
					VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
					pix = vfpm.getVwhFilmPix(pixid);
				}
				request.setAttribute("filmList", filmList);
				request.setAttribute("pixList", pixList);
				request.setAttribute("model", model);
				request.setAttribute("pix", pix);
				request.setAttribute("computerInfo", computerInfo);
				
				//记录观看详情
				VwhFilmWatchManager vfwm = (VwhFilmWatchManager) getBean("vwhFilmWatchManager");
				VwhFilmWatch filmWatch = new VwhFilmWatch();
				filmWatch.setUserid(Integer.valueOf(userid));
				filmWatch.setFilmid(model.getFilmid());
				filmWatch.setPixid(pix.getPixid());
				filmWatch.setCreatedate(DateTime.getDate());
				filmWatch.setUserip(IpUtil.getIpAddr(request));
				vfwm.addVwhFilmWatch(filmWatch);

				return mapping.findForward("videoplay");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/*
	 * 根据vtype类型获携带相关数据
	 */
	private void setAttributes(HttpServletRequest request) {
		String vtype = Encode.nullToBlank(request.getParameter("vtype"));// 1从预习跳转过来，2从查看练习跳转过来，3从错题本跳转过来，4从作业本错题跳转过来
		request.setAttribute("vtype", vtype);
		if ("1".equals(vtype) || "2".equals(vtype)) {
			String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
			String classid = Encode.nullToBlank(request.getParameter("classid"));
			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			String index = Encode.nullToBlank(request.getParameter("index"));
			String next = Encode.nullToBlank(request.getParameter("next"));
			String show = Encode.nullToBlank(request.getParameter("show"));
			String taskid = Encode.nullToBlank(request.getParameter("taskid"));
			String tasktype = Encode.nullToBlank(request.getParameter("tasktype"));
			request.setAttribute("bookcontentid", bookcontentid);
			request.setAttribute("classid", classid);
			request.setAttribute("bookid", bookid);
			request.setAttribute("index", index);
			request.setAttribute("next", next);
			request.setAttribute("show", show);
			request.setAttribute("taskid", taskid);
			request.setAttribute("tasktype", tasktype);
		} else if ("3".equals(vtype) || "4".equals(vtype)) {
			String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
			String classid = Encode.nullToBlank(request.getParameter("classid"));
			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			String questionid = Encode.nullToBlank(request.getParameter("questionid"));
			String uerrorid = Encode.nullToBlank(request.getParameter("uerrorid"));
			request.setAttribute("bookcontentid", bookcontentid);
			request.setAttribute("classid", classid);
			request.setAttribute("bookid", bookid);
			request.setAttribute("questionid", questionid);
			request.setAttribute("uerrorid", uerrorid);
		}else if("5".equals(vtype)){//教师点击进入查看每道题
			String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
			String classid = Encode.nullToBlank(request.getParameter("classid"));
			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			String index = Encode.nullToBlank(request.getParameter("index"));
			String next = Encode.nullToBlank(request.getParameter("next"));
			request.setAttribute("bookcontentid", bookcontentid);
			request.setAttribute("classid", classid);
			request.setAttribute("bookid", bookid);
			request.setAttribute("index", index);
			request.setAttribute("next", next);
		}else if("6".equals(vtype) || "7".equals(vtype)){//预习，学案
			String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
			String classid = Encode.nullToBlank(request.getParameter("classid"));
			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			String index = Encode.nullToBlank(request.getParameter("index"));
			String next = Encode.nullToBlank(request.getParameter("next"));
			String show = Encode.nullToBlank(request.getParameter("show"));
			request.setAttribute("bookcontentid", bookcontentid);
			request.setAttribute("classid", classid);
			request.setAttribute("bookid", bookid);
			request.setAttribute("index", index);
			request.setAttribute("next", next);
			request.setAttribute("show", show);
		}else if("8".equals(vtype) || "9".equals(vtype) || "10".equals(vtype)){//检查作业，查看错题本，老师查看作业本错题
			String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
			String classid = Encode.nullToBlank(request.getParameter("classid"));
			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			String questionid = Encode.nullToBlank(request.getParameter("questionid"));
			String type = Encode.nullToBlank(request.getParameter("type"));
			request.setAttribute("bookcontentid", bookcontentid);
			request.setAttribute("classid", classid);
			request.setAttribute("bookid", bookid);
			request.setAttribute("questionid", questionid);
			request.setAttribute("type", type);
		}
	}

	/**
	 * 根据userid,bookcontentid,classid获取个人错题集
	 * */
	public ActionForward getUserErrQuestionByClassBook(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				TkUserErrorQuestionManager errorquestionManager = (TkUserErrorQuestionManager) getBean("tkUserErrorQuestionManager");
				TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
				List bookcontents = bookcontentManager.getAllBookContentByBook(bookid, "contentno");
				List errorQUesions = errorquestionManager.getUserErrorQuestions(bookid, classid, userid);
				List<TkBookContent> list = new ArrayList<TkBookContent>();
				for (int i = 0; i < bookcontents.size(); i++) {
					TkBookContent model = (TkBookContent) bookcontents.get(i);
					List errorquestionlist = new ArrayList();
					for (int j = 0; j < errorQUesions.size(); j++) {
						TkUserErrorQuestion errormodel = (TkUserErrorQuestion) errorQUesions.get(j);
						// 判断作业本是否相同
						if (model.getBookcontentid().equals(errormodel.getBookcontentid())) {
							// 判断错题类型
							if ("1".equals(errormodel.getType())) {
								TkPaperContent papercontemodel = CacheUtil.getTkPaperContent("TkPaperContent_" + errormodel.getContentid());
								if (papercontemodel == null) {
									TkPaperContentManager papercontentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
									papercontemodel = papercontentManager.getTkPaperContent(errormodel.getContentid());
									CacheUtil.put("TkPaperContent_" + papercontemodel.getContentid(), papercontemodel, 24 * 30);
								}
								TkQuestionsInfo questionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + papercontemodel.getQuestionid());
								if (questionsInfo == null) {
									TkQuestionsInfoManager questionsinfoManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
									questionsInfo = questionsinfoManager.getTkQuestionsInfo(papercontemodel.getQuestionid());
									CacheUtil.put("TkQuestionsInfo_" + papercontemodel.getQuestionid(), questionsInfo, 24 * 30);
								}
								questionsInfo.setFlagl(errormodel.getUerrorid().toString());
								errorquestionlist.add(questionsInfo);
							} else if ("2".equals(errormodel.getType())) {
								TkQuestionsInfo questionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + errormodel.getContentid());
								if (questionsInfo == null) {
									TkQuestionsInfoManager questionsinfoManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
									questionsInfo = questionsinfoManager.getTkQuestionsInfo(errormodel.getContentid());
									CacheUtil.put("TkQuestionsInfo_" + errormodel.getContentid(), questionsInfo, 24 * 30);
								}
								if (!questionsInfo.getTitle().endsWith("[课前预习题]")) {
									questionsInfo.setTitle(questionsInfo.getTitle() + "[课前预习题]");
								}
								questionsInfo.setFlagl(errormodel.getUerrorid().toString());
								errorquestionlist.add(questionsInfo);
							}
							
						}
					}
					model.setErrorquestionlist(errorquestionlist);
					if (model.getErrorquestionlist() != null && model.getErrorquestionlist().size() != 0) {
						list.add(model);
					}
					request.setAttribute("list", list);
				}
				request.setAttribute("bookid", bookid);
				request.setAttribute("classid", classid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("book_error_question");
	}
	
	// ==============================================扫一扫进入举一反三与微课=================================================
	public ActionForward scanViewSimilar(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String qid = Encode.nullToBlank(request.getParameter("qid"));
				request.setAttribute("qid", qid);
				
				TkQuestionsInfo tkQuestionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + qid);
				if (tkQuestionsInfo == null) {
					TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
					tkQuestionsInfo = tqim.getTkQuestionsInfo(qid);
					CacheUtil.put("TkQuestionsInfo_" + qid, tkQuestionsInfo, 24 * 30);
				}

				// 获取试题对应的举一反三题，先从缓存中获取，没有再查下数据库
				List questionList = CacheUtil.getList("TkQuestionsInfoList_3_" + qid);// 1预习，2学案，3举一反三
				if (questionList == null) {
					TkQuestionsSimilarManager tqsm = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
					questionList = tqsm.getTkQuestionsSimilars(qid);
					CacheUtil.putList("TkQuestionsInfoList_3_" + qid, questionList, 24 * 30);
				}
				if(questionList != null && questionList.size() > 0){
					String index0 = Encode.nullToBlank(request.getParameter("index0"));// 当前做题数，方便获取下一题，第一次进入为1
					String next0 = Encode.nullToBlank(request.getParameter("next0"));// 1表示获取下一题，-1表示获取上一题
					if ("".equals(index0))
						index0 = "1";
					if ("".equals(next0))
						next0 = "1";

					int tindex = Integer.valueOf(index0);
					String prebutton = "0";// “上一题”按钮
					String nextbutton = "0";// “下一题”按钮
					TkQuestionsInfo questionsInfo = (TkQuestionsInfo) questionList.get(tindex - 1);
					CacheUtil.put("TkQuestionsInfo_" + questionsInfo.getQuestionid(), questionsInfo, 24 * 30);
					if ("1".equals(next0)) {
						if (tindex > 1)
							prebutton = "1";
						if (questionList.size() > tindex) {
							nextbutton = "1";
						}
					} else {
						nextbutton = "1";
						if (tindex > 1)
							prebutton = "1";
					}
					request.setAttribute("model", questionsInfo);
					request.setAttribute("index0", tindex);
					request.setAttribute("next0", next0);
					request.setAttribute("prebutton", prebutton);
					request.setAttribute("nextbutton", nextbutton);

					// 记录举一反三题被查看情况
					TkQuestionsSimilarWatchManager tqswm = (TkQuestionsSimilarWatchManager) getBean("tkQuestionsSimilarWatchManager");
					TkQuestionsSimilarWatch similarWatch = new TkQuestionsSimilarWatch();
					similarWatch.setUserid(Integer.valueOf(userid));
					similarWatch.setQuestionid(questionsInfo.getQuestionid());
					similarWatch.setCreatedate(DateTime.getDate());
					similarWatch.setUserip(IpUtil.getIpAddr(request));
					tqswm.addTkQuestionsSimilarWatch(similarWatch);

					// 题型分开显示，方便维护
					TkQuestionsType questionsType = questionsInfo.getTkQuestionsType();
					if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {
						// 查询当前试题的所有子题，如果有则是复合题
						TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
						List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid());
						if (childList == null) {
							childList = tqim.getAllChildTkQuestionsInfos(questionsInfo.getQuestionid());
							CacheUtil.putList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid(), childList, 24 * 30);
						}
						if (childList != null && childList.size() > 0) {// 复合题
							request.setAttribute("childList", childList);
							return mapping.findForward("scanviewsimilarM");
						} else {// 填空题
							return mapping.findForward("scanviewsimilar");
						}
					} else {
						return mapping.findForward("scanviewsimilar");
					}
				}else {
					request.setAttribute("promptinfo", "温馨提示：当前试题《" + tkQuestionsInfo.getTitle() + "》暂无对应的“举一反三”题！");
					return mapping.findForward("scantips");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 查看微课
	 * 【题关联微课需要放缓存中获取，所以只记录具体看微课记录，不改变微课属性hits字段】
	 */
	public ActionForward scanVideoPlay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String qid = Encode.nullToBlank(request.getParameter("qid"));
				//服务器端有时会报错，传递过来的是加密字符串
				if(qid.endsWith("==")){
					qid = DES.getDecryptPwd(qid);
				}
				request.setAttribute("qid", qid);
				
				TkQuestionsInfo tkQuestionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + qid);
				if (tkQuestionsInfo == null) {
					TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
					tkQuestionsInfo = tqim.getTkQuestionsInfo(qid);
					CacheUtil.put("TkQuestionsInfo_" + qid, tkQuestionsInfo, 24 * 30);
				}

				String filmid = Encode.nullToBlank(request.getParameter("filmid"));// 直接点击微课跳转
				String pixid = Encode.nullToBlank(request.getParameter("pixid"));// 微课包含多个视频时，点击视频文件跳转

				// 获取试题对应的微课，先从缓存中获取，没有再查下数据库
				List filmList = CacheUtil.getList("VwhFilmInfoList_" + qid);
				if (filmList == null) {
					TkQuestionsFilmManager tqfm = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
					filmList = tqfm.getTkQuestionsFilms(qid);
					CacheUtil.putList("VwhFilmInfoList_" + qid, filmList, 24 * 30);
				}
				if(filmList != null && filmList.size() > 0){
					VwhFilmInfo model = null;
					if("".equals(filmid)){
						model = (VwhFilmInfo) filmList.get(0);
						filmid = model.getFilmid().toString();
					}else {
						VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
						model = vfim.getVwhFilmInfo(filmid);
					}
					
					VwhComputerInfo computerInfo = CacheUtil.getVwhComputerInfo("VwhComputerInfo_" + model.getComputerid());
					if(computerInfo == null){
						VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
						computerInfo = vcim.getVwhComputerInfo(model.getComputerid());
						CacheUtil.put("VwhComputerInfo_" + model.getComputerid(), computerInfo, 24 * 30);
					}
					
					List pixList = CacheUtil.getList("VwhFilmPixList_" + filmid);
					if(pixList == null){
						VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
						pixList = vfpm.getVwhFilmPixsByFilmid(Integer.valueOf(filmid));
						CacheUtil.putList("VwhFilmPixList_" + filmid, pixList, 24 * 30);
					}
					
					VwhFilmPix pix = null;
					if("".equals(pixid)){
						pix = (VwhFilmPix) pixList.get(0);
						pixid = pix.getPixid().toString();
					}else {
						VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
						pix = vfpm.getVwhFilmPix(pixid);
					}
					request.setAttribute("filmList", filmList);
					request.setAttribute("pixList", pixList);
					request.setAttribute("model", model);
					request.setAttribute("pix", pix);
					request.setAttribute("computerInfo", computerInfo);
					
					//记录观看详情
					VwhFilmWatchManager vfwm = (VwhFilmWatchManager) getBean("vwhFilmWatchManager");
					VwhFilmWatch filmWatch = new VwhFilmWatch();
					filmWatch.setUserid(Integer.valueOf(userid));
					filmWatch.setFilmid(model.getFilmid());
					filmWatch.setPixid(pix.getPixid());
					filmWatch.setCreatedate(DateTime.getDate());
					filmWatch.setUserip(IpUtil.getIpAddr(request));
					vfwm.addVwhFilmWatch(filmWatch);

					return mapping.findForward("scanvideoplay");
				}else {
					request.setAttribute("promptinfo", "温馨提示：当前试题《" + tkQuestionsInfo.getTitle() + "》暂无对应的“微课”内容！");
					request.setAttribute("video", "1");
					return mapping.findForward("scantips");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	public ActionForward scanAudioPlay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String flag = Encode.nullToBlank(request.getParameter("flag"));//1表示有返回按钮
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				if(bookcontentid.endsWith("==")){
					bookcontentid = DES.getDecryptPwd(bookcontentid);
				}
				request.setAttribute("flag", flag);
				request.setAttribute("bookcontentid", bookcontentid);
				
				TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent bookContent = manager.getTkBookContent(bookcontentid);
				if(bookContent.getMp3path() != null && !"".equals(bookContent.getMp3path())){
					//记录观看详情
					TkBookContentWatchManager tbcwm = (TkBookContentWatchManager) getBean("tkBookContentWatchManager");
					TkBookContentWatch watch = new TkBookContentWatch();
					watch.setUserid(Integer.valueOf(userid));
					watch.setBookcontentid(bookContent.getBookcontentid());
					watch.setMp3path(bookContent.getMp3path());
					watch.setCreatedate(DateTime.getDate());
					watch.setUserip(IpUtil.getIpAddr(request));
					tbcwm.addTkBookContentWatch(watch);
					
					request.setAttribute("model", bookContent);
					return mapping.findForward("scanaudioplay");
				}else {
					request.setAttribute("promptinfo", "温馨提示：当前作业【" + bookContent.getTitle() + "】没有听力文件！");
					request.setAttribute("audio", "1");
					return mapping.findForward("scantips");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//==================================================通过微信上传头像和图片============================================
	/**
	 * 上传头像
	 */
	public ActionForward uploadPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo userInfo = manager.getSysUserInfo(userid);
				
				String serverId = Encode.nullToBlank(request.getParameter("serverId"));
				ServletContext context = request.getSession().getServletContext();
				String rootpath = context.getRealPath("/") + "upload/";
				String savepath = userInfo.getUnitid() + "/user/" + DateTime.getDate("yyyyMM");
				//检查文件夹是否存在,如果不存在,新建一个
		        if (!FileUtil.isExistDir(rootpath + savepath)) {
		        	FileUtil.creatDir(rootpath + savepath);
		        }
		        String filename = UUID.getNewUUID() + ".jpg";
		        String filepath = savepath + "/" + filename;
				long fsize = MpUtil.downloadMedia(serverId, rootpath, filepath);
				if(fsize > 0){
					//压缩头像
					ImageUtil.generateThumbnailsSubImage(rootpath + filepath, rootpath + savepath + "/120x120_" + filename, 120, 120, true);
					
					userInfo.setPhoto(savepath + "/120x120_" + filename);
					manager.updateSysUserInfo(userInfo);
				}
				
				String redirecturl = "/weixinAccountIndex.app?method=myAccount&userid=" + DES.getEncryptPwd(userid);
				response.sendRedirect(redirecturl);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 拍照上传前
	 */
	public ActionForward classUploadImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("classid", classid);
				
				//每个学生在一个作业中上传的图片数量不超过6张
				TkClassUploadManager manager = (TkClassUploadManager) getBean("tkClassUploadManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
				SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				List list = manager.getTkClassUploads(condition, "createdate desc", 0);
				request.setAttribute("list", list);
				
				//调用微信js接口图库预览
				StringBuffer imgUrls = new StringBuffer();
				if(list != null && list.size() > 0){
					TkClassUpload tcu = null;
					for(int i=0, size=list.size(); i<size; i++){
						tcu = (TkClassUpload) list.get(i);
						if(i == 0){
							imgUrls.append("'").append(MpUtil.HOMEPAGE + "/upload/" + tcu.getImgpath()).append("'");
						}else {
							imgUrls.append(",'").append(MpUtil.HOMEPAGE + "/upload/" + tcu.getImgpath()).append("'");
						}
					}
				}
				request.setAttribute("imgUrls", imgUrls);
				
				return mapping.findForward("classuploadimage");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 拍照上传
	 */
	public ActionForward uploadImage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo userInfo = manager.getSysUserInfo(userid);
				
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				
				ServletContext context = request.getSession().getServletContext();
				String rootpath = context.getRealPath("/") + "upload/";
				String savepath = userInfo.getUnitid() + "/class/upload/" + classid + "/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
				//检查文件夹是否存在,如果不存在,新建一个
		        if (!FileUtil.isExistDir(rootpath + savepath)) {
		        	FileUtil.creatDir(rootpath + savepath);
		        }
		        String serverIds = Encode.nullToBlank(request.getParameter("serverIds"));
				String[] serverId = serverIds.split(",");
				if(serverId != null && serverId.length > 0){
					TkClassUploadManager tcum = (TkClassUploadManager) getBean("tkClassUploadManager");
					TkClassUpload model = new TkClassUpload();
					model.setBookcontentid(Integer.valueOf(bookcontentid));
					model.setCreatedate(DateTime.getDate());
					model.setUserid(Integer.valueOf(userid));
					model.setClassid(Integer.valueOf(classid));
					
					String filename = null;
					String filepath = null;
					long fsize = 0;
					for(int i=0, size=serverId.length; i<size; i++){
						if(serverId[i] != null && !"".equals(serverId[i])){
							filename = MD5.getEncryptPwd(serverId[i]) + ".jpg";
							filepath = savepath + "/" + filename;
							fsize = MpUtil.downloadMedia(serverId[i], rootpath, filepath);
							if(fsize > 0){
								model.setUploadid(null);
								model.setImgpath(filepath);
								tcum.addTkClassUpload(model);
							}
						}
					}
				}
				
				return classUploadImage(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	//================================英语听力=================================
	/**
	 * 选择英语作业本
	 */
	public ActionForward selectEnglishBook(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			//==========通过微信自动回复消息跳转过来==========
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			//如果用户已经注册并绑定账号，再次点击注册时直接跳转进入，避免出现绑定多个账号的bug
			SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
			SysUserAttention attention = suam.getSysUserAttentionByOpenid(openid);
			if(attention != null && attention.getUserid().intValue() > 1){
				userid = attention.getUserid().toString();
			}
			if("1".equals(userid)){
				return mapping.findForward("welcome");
			}
			
			
			//根据自己所在学段，查找对应年级的作业本
			SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
			int xueduanid = 3;//默认学段初中【用户学段和学段表没有对应，需注意】
			//说明：因为目前小学和高中都没有英语听力作业内容，为了不让页面显示没有数据，故先都统一显示初中英语听力作业
			/*
			if("1".equals(sysUserInfo.getXueduan())){
				xueduanid = 2;
			}else if("2".equals(sysUserInfo.getXueduan())){
				xueduanid = 3;
			}else if("3".equals(sysUserInfo.getXueduan())){
				xueduanid = 4;
			}
			*/
			
			int subjectid = 13;//固定英语学科
			//根据学段id和学科id，查询所有关联年级id
			EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
			SearchCondition.addCondition(condition, "xueduanid", "=", xueduanid);
			List gradeList = egim.getEduGradeInfos(condition, "orderindex", 0);
			
			String title = Encode.nullToBlank(request.getParameter("title"));
			TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
			condition.clear();
			SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
			if (gradeList != null && gradeList.size() > 0) {
				StringBuffer gradeids = new StringBuffer();
				EduGradeInfo egi = null;
				for (int i = 0, size = gradeList.size(); i < size; i++) {
					egi = (EduGradeInfo) gradeList.get(i);
					gradeids.append(" or a.gradeid=").append(egi.getGradeid());
				}
				SearchCondition.addCondition(condition, "status", "=", "1' and (" + gradeids.toString().substring(3) + ") and 1='1");
			} else {
				SearchCondition.addCondition(condition, "status", "=", "1");
			}
			if (!"".equals(title)) {
				SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
			}

			List list = manager.getTkBookInfos(condition, "bookno asc", 0);
			
			//获取有听力文件的作业本id，没有听力音频文件的英语作业本暂不在此显示
			TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
			List bookidList = tbcm.getAllbookidsWithMp3path();
			List bookList = new ArrayList();
			TkBookInfo tbi = null;
			for(int i=0, size=list.size(); i<size; i++){
				tbi = (TkBookInfo) list.get(i);
				if(bookidList.contains(tbi.getBookid())){
					bookList.add(tbi);
				}
			}

			request.setAttribute("list", bookList);
			request.setAttribute("title", title);

			return mapping.findForward("selectenglishbook");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 选择英语作业本【独立以英语听力的方式呈现】
	 * 根据英语作业本查询作业，只显示有听力文件的作业，点击进入某一作业，直接在线听英语听力音频文件，不做其他处理
	 */
	public ActionForward selectEnglishBookNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if(!"1".equals(userid)){
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				
				TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
				List list = manager.getAllBookContentByBook(bookid, "contentno");
				List parentList = new ArrayList();
				List hasmp3ParentList = new ArrayList();
				Map childMap = new HashMap();
				TkBookContent tbc = null;
				for (int i = 0, size = list.size(); i < size; i++) {
					tbc = (TkBookContent) list.get(i);
					if ("0000".equals(tbc.getParentno())) {
						parentList.add(tbc);
					} else {//有音频文件
						if(tbc.getMp3path() != null && !"".equals(tbc.getMp3path())){
							if(!hasmp3ParentList.contains(tbc.getParentno())){
								hasmp3ParentList.add(tbc.getParentno());
							}
							if (childMap.get(tbc.getParentno()) == null) {
								List lst = new ArrayList();
								lst.add(tbc);
								childMap.put(tbc.getParentno(), lst);
							} else {
								List lst = (List) childMap.get(tbc.getParentno());
								lst.add(tbc);
								childMap.put(tbc.getParentno(), lst);
							}
						}
					}
				}
				
				List parentList0 = new ArrayList();
				for (int i = 0, size = parentList.size(); i < size; i++) {
					tbc = (TkBookContent) parentList.get(i);
					if(hasmp3ParentList.contains(tbc.getContentno())){
						parentList0.add(tbc);
					}
				}
				
				request.setAttribute("parentList", parentList0);
				request.setAttribute("childMap", childMap);
				request.setAttribute("bookid", bookid);
				
				return mapping.findForward("bookcontentenglish");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
//	/**
//	 * 选择英语作业本
//	 */
//	public ActionForward selectEnglishBookNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//		try {
//			String userid = MpUtil.getUserid(request);
//			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
//			//判断用户是否已绑定作业本，如果没有绑定则自动绑定，然后跳转到在线作答页面
//			TkClassUserManager manager = (TkClassUserManager) getBean("tkClassUserManager");
//			List<SearchModel> condition = new ArrayList<SearchModel>();
//			SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
//			SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
//			List list = manager.getTkClassUsers(condition, "", 0);
//			TkClassUser tkClassUser = null;
//			if(list == null || list.size() == 0){
//				tkClassUser = new TkClassUser();
//				tkClassUser.setUserid(Integer.valueOf(userid));
//				tkClassUser.setClassid(0);
//				tkClassUser.setBookid(Integer.valueOf(bookid));
//				tkClassUser.setStatus("1");
//				tkClassUser.setCreatedate(DateTime.getDate());
//				manager.addTkClassUser(tkClassUser);
//			}else {
//				tkClassUser = (TkClassUser) list.get(0);
//			}
//			
//			response.sendRedirect("/weixinStudent.app?method=bookContent&userid=" + DES.getEncryptPwd(userid) + "&bookid=" + bookid + "&classid=" + tkClassUser.getClassid());
//			return null;
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return mapping.findForward("error");
//	}
}