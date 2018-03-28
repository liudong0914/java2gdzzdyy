package com.wkmk.weixin.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.util.image.ZXingUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkClassErrorQuestion;
import com.wkmk.tk.bo.TkClassInfo;
import com.wkmk.tk.bo.TkClassPassword;
import com.wkmk.tk.bo.TkClassUpload;
import com.wkmk.tk.bo.TkPaperAnswer;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookContentQuestionManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkClassErrorQuestionManager;
import com.wkmk.tk.service.TkClassInfoManager;
import com.wkmk.tk.service.TkClassPasswordManager;
import com.wkmk.tk.service.TkClassUploadManager;
import com.wkmk.tk.service.TkClassUserManager;
import com.wkmk.tk.service.TkPaperAnswerManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkQuestionsFilmManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsSimilarManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.RandomCodeUtil;
import com.wkmk.util.common.TwoCodeUtil;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 微信服务账户绑定接口
 * 
 * @version 1.0
 */
public class WeixinTeacherAction extends BaseAction {

	/**
	 * 教师创建班级
	 */
	public ActionForward createClass(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
				request.setAttribute("classname", "");
				request.setAttribute("students", "50");
				request.setAttribute("pwd", "0");

				return mapping.findForward("createclass");
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
	 * 创建班级选择作业本
	 */
	public ActionForward selectBook(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			MpUtil.getUserid(request);
			// ==============返回创建班级需要用到的参数===============
			String classname = Encode.nullToBlank(request.getParameter("classname"));
			String students = Encode.nullToBlank(request.getParameter("students"));
			String pwd = Encode.nullToBlank(request.getParameter("pwd"));
			request.setAttribute("classname", classname);
			request.setAttribute("students", students);
			request.setAttribute("pwd", pwd);
			// ==============返回创建班级需要用到的参数===============

			String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
			String gradeid = Encode.nullToBlank(request.getParameter("gradeid"));
			String title = Encode.nullToBlank(request.getParameter("title"));
			TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "1");
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

			//【在线创建班级】获取有题的作业本，没有题的作业本暂不在此显示
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
			// ==============返回创建班级需要用到的参数===============
			String classname = Encode.nullToBlank(request.getParameter("classname"));
			String students = Encode.nullToBlank(request.getParameter("students"));
			String pwd = Encode.nullToBlank(request.getParameter("pwd"));
			request.setAttribute("classname", classname);
			request.setAttribute("students", students);
			request.setAttribute("pwd", pwd);
			// ==============返回创建班级需要用到的参数===============

			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
			TkBookInfo tbi = manager.getTkBookInfo(bookid);
			request.setAttribute("bookid", bookid);
			request.setAttribute("bookname", tbi.getTitle() + "（" + Constants.getCodeTypevalue("version", tbi.getVersion()) + "）");

			return mapping.findForward("createclass");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 添加班级
	 */
	public ActionForward saveClass(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			// ==============返回创建班级需要用到的参数===============
			String classname = Encode.nullToBlank(request.getParameter("classname"));
			String students = Encode.nullToBlank(request.getParameter("students"));// 0表示无限制
			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			String bookname = Encode.nullToBlank(request.getParameter("bookname"));
			String pwd = Encode.nullToBlank(request.getParameter("pwd"));
			request.setAttribute("classname", classname);
			request.setAttribute("students", students);
			request.setAttribute("bookid", bookid);
			request.setAttribute("bookname", bookname);
			request.setAttribute("pwd", pwd);
			// ==============返回创建班级需要用到的参数===============

			if ("".equals(classname)) {
				request.setAttribute("errmsg", "请填写班级名称!");
				return mapping.findForward("createclass");
			}
			if ("".equals(students)) {
				request.setAttribute("errmsg", "请设定班级人数!");
				return mapping.findForward("createclass");
			}
			if ("".equals(bookid)) {
				request.setAttribute("errmsg", "请选择作业本!");
				return mapping.findForward("createclass");
			}

			if (!"1".equals(userid)) {
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo userInfo = suim.getSysUserInfo(userid);

				TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
				//由于最近测试发现创建并保存班级时，出现多条记录，故在此控制【页面也做了控制】。同一人，班级名称和作业本都相同的情况下，一个月只能一次
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "classname", "=", classname);
				SearchCondition.addCondition(condition, "userid", "=", userInfo.getUserid());
				SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
				SearchCondition.addCondition(condition, "createdate", "like", DateTime.getDate("yyyy-MM") + "%");
				List lst = manager.getTkClassInfos(condition, "createdate desc", 1);
				TkClassInfo classInfo = null;
				if(lst != null && lst.size() > 0){
					classInfo = (TkClassInfo) lst.get(0);
				}else {
					List<String> classnoList = manager.getAllClassNos();
					classInfo = new TkClassInfo();
					classInfo.setClassname(classname);
					classInfo.setClassno(RandomCodeUtil.getClassno(classnoList));
					int studentnos = 0;
					try {
						studentnos = Integer.valueOf(students);
					} catch (Exception e) {
						studentnos = 0;
					}
					classInfo.setStudents(studentnos);
					classInfo.setPwd(pwd);
					classInfo.setCreatedate(DateTime.getDate());
					classInfo.setBookid(Integer.valueOf(bookid));
					classInfo.setUserid(userInfo.getUserid());
					classInfo.setUnitid(userInfo.getUnitid());
					classInfo.setUploadimg("0");
					manager.addTkClassInfo(classInfo);
					classInfo.setTwocodepath(TwoCodeUtil.getTwoCodePath(classInfo, request));
					manager.updateTkClassInfo(classInfo);

					if ("1".equals(pwd) && classInfo.getStudents().intValue() > 0) {
						// 为了提升性能，密码是和班级绑定，所以无需验证，不需要做到系统唯一，只要控制班级唯一即可
						List<String> passwordList = RandomCodeUtil.getClassPasswords(null, classInfo.getStudents());
						TkClassPasswordManager tcpm = (TkClassPasswordManager) getBean("tkClassPasswordManager");
						TkClassPassword tcp = new TkClassPassword();
						tcp.setUsed("0");
						tcp.setUserid(0);
						tcp.setClassid(classInfo.getClassid());
						tcp.setUnitid(classInfo.getUnitid());
						for (int i = 0, size = passwordList.size(); i < size; i++) {
							tcp.setPasswordid(null);
							tcp.setPassword(passwordList.get(i));
							tcpm.addTkClassPassword(tcp);
						}
					}
				}
				
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkBookInfo bookInfo = tbim.getTkBookInfo(classInfo.getBookid());
				request.setAttribute("bookInfo", bookInfo);
				request.setAttribute("classInfo", classInfo);
				
				//班级中没有学生加入才可以删除
				TkClassUserManager tcum = (TkClassUserManager) getBean("tkClassUserManager");
				int studentCounts = tcum.getStudentsByClassid(classInfo.getClassid().toString());
				String canDel = "0";//允许删除
				if(studentCounts > 0){
					canDel = "1";//不允许删除
				}
				request.setAttribute("canDel", canDel);
				request.setAttribute("studentCounts", studentCounts);

				// 创建成功后，直接展示班级基本信息及二维码图片
				return mapping.findForward("classinfo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 教师查看班级详情
	 */
	public ActionForward classList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				List list = manager.getTkClassInfos(condition, "createdate desc", 0);
				request.setAttribute("list", list);

				return mapping.findForward("classlist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 教师查看班级详情
	 */
	public ActionForward classInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkClassInfo classInfo = manager.getTkClassInfo(classid);
				TkBookInfo bookInfo = tbim.getTkBookInfo(classInfo.getBookid());
				request.setAttribute("classInfo", classInfo);
				request.setAttribute("bookInfo", bookInfo);
				
				//班级中没有学生加入才可以删除
				TkClassUserManager tcum = (TkClassUserManager) getBean("tkClassUserManager");
				int studentCounts = tcum.getStudentsByClassid(classid);
				String canDel = "0";//允许删除
				if(studentCounts > 0){
					canDel = "1";//不允许删除
				}
				request.setAttribute("canDel", canDel);
				request.setAttribute("studentCounts", studentCounts);

				return mapping.findForward("classinfo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 删除班级
	 */
	public ActionForward delClass(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
				TkClassInfo tci = manager.getTkClassInfo(classid);
				manager.delTkClassInfo(tci);
				if("1".equals(tci.getPwd())){
					TkClassPasswordManager tcpm = (TkClassPasswordManager) getBean("tkClassPasswordManager");
					tcpm.delTkClassPasswordByClassid(classid);
				}

				return classList(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 教师查看班级详情
	 */
	public ActionForward classEdit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkClassInfo classInfo = manager.getTkClassInfo(classid);
				TkBookInfo bookInfo = tbim.getTkBookInfo(classInfo.getBookid());
				request.setAttribute("classInfo", classInfo);
				request.setAttribute("bookInfo", bookInfo);

				return mapping.findForward("classedit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	/**
	 * 教师查看班级详情
	 */
	public ActionForward classSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkClassInfo classInfo = manager.getTkClassInfo(classid);
				TkBookInfo bookInfo = tbim.getTkBookInfo(classInfo.getBookid());
				request.setAttribute("classInfo", classInfo);
				request.setAttribute("bookInfo", bookInfo);
				
				String classname = Encode.nullToBlank(request.getParameter("classname"));
				if("".equals(classname)){
					request.setAttribute("errmsg", "班级名称不能为空!");
					return mapping.findForward("classedit");
				}else {
					classInfo.setClassname(classname);
					manager.updateTkClassInfo(classInfo);
				}

				return classInfo(mapping, form, request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 教师查看班级密码详情
	 */
	public ActionForward classPassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				TkClassPasswordManager manager = (TkClassPasswordManager) getBean("tkClassPasswordManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
				List list = manager.getTkClassPasswords(condition, "used desc", 0);
				request.setAttribute("list", list);
				request.setAttribute("classid", classid);

				return mapping.findForward("classpassword");
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

				TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
				List list = manager.getAllBookContentByBook(bookid, "contentno");
				List parentList = new ArrayList();
				Map childMap = new HashMap();
				TkBookContent tbc = null;
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
					}
				}
				request.setAttribute("parentList", parentList);
				request.setAttribute("childMap", childMap);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);

				return mapping.findForward("bookcontent");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 根据作业本内容查询试题及试题错误个数
	 * **/
	public ActionForward getQuestions(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				TkClassErrorQuestionManager errorManager = (TkClassErrorQuestionManager) getBean("tkClassErrorQuestionManager");
				TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				// 根据bookcontentid和classid查询List
				TkPaperAnswerManager answerManager = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "bookcontentid", "=", bookcontentid);
				SearchCondition.addCondition(condition, "classid", "=", classid);
				List answerlist = answerManager.getTkPaperAnswers(condition, "answerid", 0);
				Map<String, Integer> rightMap = new HashMap<String, Integer>();
				Map<String, Integer> errorMap = new HashMap<String, Integer>();
				for (int i = 0; i < answerlist.size(); i++) {
					TkPaperAnswer answermodel = (TkPaperAnswer) answerlist.get(i);
					if ("1".equals(answermodel.getIsright())) {
						if ("1".equals(answermodel.getType())) {
							TkPaperContentManager contentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
							TkPaperContent contentmodel = contentManager.getTkPaperContent(answermodel.getContentid());
							rightMap.put(contentmodel.getQuestionid().toString(),
									(rightMap.get(contentmodel.getQuestionid().toString()) == null ? 0 : rightMap.get(contentmodel.getQuestionid().toString())) + 1);
						} else if ("2".equals(answermodel.getType())) {
							rightMap.put(answermodel.getContentid().toString(),
									(rightMap.get(answermodel.getContentid().toString()) == null ? 0 : rightMap.get(answermodel.getContentid().toString())) + 1);
						}
					} else if ("0".equals(answermodel.getIsright())) {
						if ("1".equals(answermodel.getType())) {
							TkPaperContentManager contentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
							TkPaperContent contentmodel = contentManager.getTkPaperContent(answermodel.getContentid());
							errorMap.put(contentmodel.getQuestionid().toString(),
									(errorMap.get(contentmodel.getQuestionid().toString()) == null ? 0 : errorMap.get(contentmodel.getQuestionid().toString())) + 1);
						} else if ("2".equals(answermodel.getType())) {
							errorMap.put(answermodel.getContentid().toString(),
									(errorMap.get(answermodel.getContentid().toString()) == null ? 0 : errorMap.get(answermodel.getContentid().toString())) + 1);
						}
					}
				}
				List type1contentid = errorManager.getContentidByType(Integer.parseInt(bookcontentid), Integer.parseInt(classid), "1");
				List type2contentids = errorManager.getContentidByType(Integer.parseInt(bookcontentid), Integer.parseInt(classid), "2");
				// 查询课前预习题
				List<TkQuestionsInfo> beforeList = new ArrayList<TkQuestionsInfo>();
				List beforeclassQuestions = CacheUtil.getList("TkQuestionsInfoList_1_" + bookcontentid);
				if (beforeclassQuestions == null) {
					TkBookContentQuestionManager tbcqm = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
					beforeclassQuestions = tbcqm.getTkQuestionsInfosByBookcontentid(bookcontentid, "1", "");
					CacheUtil.putList("TkQuestionsInfoList_1_" + bookcontentid, beforeclassQuestions, 24 * 30);
				}
				for (int i = 0; i < beforeclassQuestions.size(); i++) {
					TkQuestionsInfo questionsInfo = (TkQuestionsInfo) beforeclassQuestions.get(i);
					TkQuestionsInfoManager questionsinfoManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
					if (type2contentids.contains(questionsInfo.getQuestionid())) {
						questionsInfo.setFlags("1");
					} else {
						questionsInfo.setFlags("0");
					}
					questionsInfo.setErrorNum(errorMap.get(questionsInfo.getQuestionid().toString()) == null ? 0 : errorMap.get(questionsInfo.getQuestionid().toString()));
					questionsInfo.setRightNum(rightMap.get(questionsInfo.getQuestionid().toString()) == null ? 0 : rightMap.get(questionsInfo.getQuestionid().toString()));
					beforeList.add(questionsInfo);
				}
				// 根据作业本查询试卷存在的试题
				LinkedList<TkQuestionsInfo> List = new LinkedList<TkQuestionsInfo>();
				TkBookContent bookcontent = bookcontentManager.getTkBookContent(bookcontentid);
				List questionList = CacheUtil.getList("TkPaperContentList_" + bookcontent.getPaperid());
				if (questionList == null) {
					TkPaperContentManager tpcm = (TkPaperContentManager) getBean("tkPaperContentManager");
					questionList = tpcm.getMobileTkPaperContents(bookcontent.getPaperid());
					CacheUtil.putList("TkPaperContentList_" + bookcontent.getPaperid(), questionList, 24 * 30);
				}
				for (int i = 0; i < questionList.size(); i++) {
					TkPaperContent papercontentmodel = (TkPaperContent) questionList.get(i);
					CacheUtil.put("TkPaperContent_" + papercontentmodel.getContentid(), papercontentmodel, 24 * 30);
					TkQuestionsInfo questionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + papercontentmodel.getQuestionid());
					TkQuestionsInfoManager questionsinfoManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
					if (questionsInfo == null) {
						questionsInfo = questionsinfoManager.getTkQuestionsInfo(papercontentmodel.getQuestionid());
						CacheUtil.put("TkQuestionsInfo_" + papercontentmodel.getQuestionid(), questionsInfo, 24 * 30);
					}
					if (type1contentid.contains(papercontentmodel.getContentid())) {
						questionsInfo.setFlags("1");
					} else {
						questionsInfo.setFlags("0");
					}
					questionsInfo.setErrorNum(errorMap.get(questionsInfo.getQuestionid().toString()) == null ? 0 : errorMap.get(questionsInfo.getQuestionid().toString()));
					questionsInfo.setRightNum(rightMap.get(questionsInfo.getQuestionid().toString()) == null ? 0 : rightMap.get(questionsInfo.getQuestionid().toString()));
					questionsInfo.setPapecontentid(papercontentmodel.getContentid());
					List.add(questionsInfo);
				}
				request.setAttribute("list", List);
				request.setAttribute("beforeList", beforeList);
				request.setAttribute("classid", classid);
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("bookid", bookcontentManager.getTkBookContent(bookcontentid).getBookid());
				
				//判断当前班级是否允许拍照上传，如果允许，则需要查看学生拍照作业
				TkClassInfoManager tcim = (TkClassInfoManager) getBean("tkClassInfoManager");
				TkClassInfo tci = tcim.getTkClassInfo(classid);
				String uploadImg = tci.getUploadimg();
				request.setAttribute("uploadImg", uploadImg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("question_list");
	}

	/**
	 * 添加到错题本
	 * */
	public ActionForward addErrorQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			TkClassErrorQuestionManager errorManager = (TkClassErrorQuestionManager) getBean("tkClassErrorQuestionManager");
			TkClassErrorQuestion model = new TkClassErrorQuestion();
			String contentid = Encode.nullToBlank(request.getParameter("papercontentid"));
			if ("".equals(contentid)) {
				contentid = Encode.nullToBlank(request.getParameter("questionid"));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String classid = Encode.nullToBlank(request.getParameter("classid"));
			String type = Encode.nullToBlank(request.getParameter("type"));
			String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "contentid", "=", Integer.parseInt(contentid));
			SearchCondition.addCondition(condition, "type", "=", type);
			SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.parseInt(bookcontentid));
			SearchCondition.addCondition(condition, "classid", "=", Integer.parseInt(classid));
			if (errorManager.getTkClassErrorQuestions(condition, "cerrorid", 0).size() == 0) {
				model.setContentid(Integer.parseInt(contentid));
				model.setType(type);
				model.setBookcontentid(Integer.parseInt(bookcontentid));
				model.setClassid(Integer.parseInt(classid));
				model.setCreatedate(sdf.format(new Date()));
				errorManager.addTkClassErrorQuestion(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 撤销错题本
	 * */
	public ActionForward delerrorQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		TkClassErrorQuestionManager errorManager = (TkClassErrorQuestionManager) getBean("tkClassErrorQuestionManager");
		String contentid = Encode.nullToBlank(request.getParameter("papercontentid"));
		if ("".equals(contentid)) {
			contentid = Encode.nullToBlank(request.getParameter("questionid"));
		}
		String classid = Encode.nullToBlank(request.getParameter("classid"));
		String type = Encode.nullToBlank(request.getParameter("type"));
		String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "contentid", "=", Integer.parseInt(contentid));
		SearchCondition.addCondition(condition, "classid", "=", Integer.parseInt(classid));
		SearchCondition.addCondition(condition, "type", "=", type);
		SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.parseInt(bookcontentid));
		errorManager.delete(errorManager.getTkClassErrorQuestions(condition, "cerrorid", 0));
		return null;
	}

	/**
	 * 查看错题本
	 * */
	public ActionForward detailErrorQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
				TkClassErrorQuestionManager errorQuestionManager = (TkClassErrorQuestionManager) getBean("tkClassErrorQuestionManager");
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				List<TkQuestionsInfo> list = new ArrayList<TkQuestionsInfo>();
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.parseInt(bookcontentid));
				SearchCondition.addCondition(condition, "classid", "=", Integer.parseInt(classid));
				List errorlist = errorQuestionManager.getTkClassErrorQuestions(condition, "type desc ", 0);
				for (int i = 0; i < errorlist.size(); i++) {
					TkClassErrorQuestion model = (TkClassErrorQuestion) errorlist.get(i);
					TkQuestionsInfo questionsInfo = null;
					if ("2".equals(model.getType())) {
						questionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + model.getContentid());
						if (questionsInfo == null) {
							TkQuestionsInfoManager questionsinfoManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
							questionsInfo = questionsinfoManager.getTkQuestionsInfo(model.getContentid());
							CacheUtil.put("TkQuestionsInfo_" + model.getContentid(), questionsInfo, 24 * 30);
						}
						if (!questionsInfo.getTitlecontent().endsWith("[课前预习题]")) {
							questionsInfo.setTitlecontent(questionsInfo.getTitlecontent() + "[课前预习题]");
						}
					} else if ("1".equals(model.getType())) {
						TkPaperContent papercontemodel = CacheUtil.getTkPaperContent("TkPaperContent_" + model.getContentid());
						if (papercontemodel == null) {
							TkPaperContentManager papercontentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
							papercontemodel = papercontentManager.getTkPaperContent(model.getContentid());
							CacheUtil.put("TkPaperContent_" + model.getContentid(), papercontemodel, 24 * 30);
						}
						questionsInfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + papercontemodel.getQuestionid());
						if (questionsInfo == null) {
							TkQuestionsInfoManager questionsinfoManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
							questionsInfo = questionsinfoManager.getTkQuestionsInfo(papercontemodel.getQuestionid());
							CacheUtil.put("TkQuestionsInfo_" + papercontemodel.getQuestionid(), questionsInfo, 24 * 30);
						}
					}
					list.add(questionsInfo);
				}
				request.setAttribute("errorlist", errorlist);
				request.setAttribute("list", list);
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("bookid", bookcontentManager.getTkBookContent(bookcontentid).getBookid());
				request.setAttribute("classid", classid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("errorlist");
	}

	/**
	 * 查看试题详情
	 * */
	public ActionForward detaiQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String result = "";
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String type = Encode.nullToBlank(request.getParameter("type"));
				TkQuestionsInfo questionsinfo = CacheUtil.getTkQuestionsInfo("TkQuestionsInfo_" + questionid);
				TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
				if (questionsinfo == null) {
					questionsinfo = tqim.getTkQuestionsInfo(questionid);
					CacheUtil.put("TkQuestionsInfo_" + questionid, questionsinfo, 24 * 30);
				}
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("model", questionsinfo);
				if ("2".equals(type)) {
					// 每道题都可能有相关微课或举一反三题
					TkQuestionsFilmManager tqfm = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
					TkQuestionsSimilarManager tqsm = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
					int filmCounts = tqfm.getCountTkQuestionsFilms(questionsinfo.getQuestionid());
					int similarCounts = tqsm.getCountTkQuestionsSimilars(questionsinfo.getQuestionid());
					request.setAttribute("filmCounts", filmCounts);
					request.setAttribute("similarCounts", similarCounts);
					// 题型分开显示，方便维护
					TkQuestionsType questionsType = questionsinfo.getTkQuestionsType();
					if ("A".equals(questionsType.getType()) || "B".equals(questionsType.getType()) || "C".equals(questionsType.getType()) || "E".equals(questionsType.getType())) {// 单选题
						return mapping.findForward("viewlianxi");
					} else if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {// 复合题
						// 查询当前试题的所有子题，如果有则是复合题【缓存查询子题】
						List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsinfo.getQuestionid());
						if (childList == null) {
							childList = tqim.getAllChildTkQuestionsInfos(questionsinfo.getQuestionid());
							CacheUtil.putList("TkQuestionsInfoChildList_" + questionsinfo.getQuestionid(), childList, 24 * 30);
						}
						request.setAttribute("childList", childList);
						result = "viewlianxiM";
					} else {// 填空题
						result = "viewlianxi";
					}
				} else if ("1".equals(type)) {
					// 每道题都可能有相关微课或举一反三题
					TkQuestionsFilmManager tqfm = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
					TkQuestionsSimilarManager tqsm = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
					int filmCounts = tqfm.getCountTkQuestionsFilms(questionsinfo.getQuestionid());
					int similarCounts = tqsm.getCountTkQuestionsSimilars(questionsinfo.getQuestionid());
					request.setAttribute("filmCounts", filmCounts);
					request.setAttribute("similarCounts", similarCounts);
					// 题型分开显示，方便维护
					TkQuestionsType questionsType = questionsinfo.getTkQuestionsType();
					if ("A".equals(questionsType.getType()) || "B".equals(questionsType.getType()) || "C".equals(questionsType.getType()) || "E".equals(questionsType.getType())) {// 单选题
						result = "viewlianxi_question";
					} else if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {// 复合题
						// 查询当前试题的所有子题，如果有则是复合题【缓存查询子题】
						List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsinfo.getQuestionid());
						if (childList == null) {
							childList = tqim.getAllChildTkQuestionsInfos(questionsinfo.getQuestionid());
							CacheUtil.putList("TkQuestionsInfoChildList_" + questionsinfo.getQuestionid(), childList, 24 * 30);
						}
						request.setAttribute("childList", childList);
						result = "viewlianxiM_question";
					} else {// 填空题
						result = "viewlianxi_question";
					}
				} else if ("3".equals(type)) {
					// 每道题都可能有相关微课或举一反三题
					TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
					request.setAttribute("bookid", bookcontentManager.getTkBookContent(bookcontentid).getBookid());
					TkQuestionsFilmManager tqfm = (TkQuestionsFilmManager) getBean("tkQuestionsFilmManager");
					TkQuestionsSimilarManager tqsm = (TkQuestionsSimilarManager) getBean("tkQuestionsSimilarManager");
					int filmCounts = tqfm.getCountTkQuestionsFilms(questionsinfo.getQuestionid());
					int similarCounts = tqsm.getCountTkQuestionsSimilars(questionsinfo.getQuestionid());
					request.setAttribute("filmCounts", filmCounts);
					request.setAttribute("similarCounts", similarCounts);
					// 题型分开显示，方便维护
					TkQuestionsType questionsType = questionsinfo.getTkQuestionsType();
					if ("A".equals(questionsType.getType()) || "B".equals(questionsType.getType()) || "C".equals(questionsType.getType()) || "E".equals(questionsType.getType())) {// 单选题
						result = "allviewlianxi_question";
					} else if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {// 复合题
						// 查询当前试题的所有子题，如果有则是复合题【缓存查询子题】
						List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsinfo.getQuestionid());
						if (childList == null) {
							childList = tqim.getAllChildTkQuestionsInfos(questionsinfo.getQuestionid());
							CacheUtil.putList("TkQuestionsInfoChildList_" + questionsinfo.getQuestionid(), childList, 24 * 30);
						}
						request.setAttribute("childList", childList);
						result = "allviewlianxiM_question";
					} else {// 填空题
						result = "allviewlianxi_question";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward(result);
	}
	
	/**
	 * 老师查看未交作业学生
	 * */
	public ActionForward getUncommitStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				request.setAttribute("classid", classid);
				request.setAttribute("bookcontentid", bookcontentid);
				
				TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
				List answerUseridList = tpam.getTkPaperAnswerStudentsByClassid(bookcontentid, classid);
				
				TkClassUserManager manager = (TkClassUserManager) getBean("tkClassUserManager");
				List userList = manager.getUncommitStudentsByClassid(classid, answerUseridList);
				request.setAttribute("userList", userList);
				
				return mapping.findForward("uncommitstudent");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 根据bookcontentid和classid查询错题
	 * */
	public ActionForward getErrQuestionByClassBook(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				List bookcontents = bookcontentManager.getAllBookContentByBook(bookid, "contentno");
				List errorQuestions = bookcontentManager.getErrorQuestions(bookid, classid);
				List<TkBookContent> list = new ArrayList<TkBookContent>();
				for (int i = 0; i < bookcontents.size(); i++) {
					TkBookContent model = (TkBookContent) bookcontents.get(i);
					List errorquestionlist = new ArrayList();
					for (int j = 0; j < errorQuestions.size(); j++) {
						TkClassErrorQuestion errormodel = (TkClassErrorQuestion) errorQuestions.get(j);
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

	/**
	 * 教师端预习题
	 * */
	public ActionForward preparationQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
				List questionList = CacheUtil.getList("TkQuestionsInfoList_1_" + bookcontentid);
				if (questionList == null) {
					TkBookContentQuestionManager tbcqm = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
					questionList = tbcqm.getTkQuestionsInfosByBookcontentid(bookcontentid, "1", null);
					CacheUtil.putList("TkQues	tionsInfoList_1_" + bookcontentid, questionList, 24 * 30);
				}
				if (questionList != null && questionList.size() > 0) {
					// 如果课前预习有文本内容并且有试题，则先显示文本内容，通过点击“查看试题”再具体显示试题
					String show = Encode.nullToBlank(request.getParameter("show"));// 通过点击触发“查看试题”按钮
					if (!"1".equals(show) && bookContent.getBeforeclass() != null && !"".equals(bookContent.getBeforeclass())) {
						request.setAttribute("show", "1");// 显示按钮“查看试题”
						return mapping.findForward("beforeclassinfo");// 课前预习有关联题，先显示课前预习文本内容
					}
					request.setAttribute("show", show);

					// 选择上一题或下一题时，可能已经是做过的
					String index = Encode.nullToBlank(request.getParameter("index"));// 当前做题数，方便获取下一题，第一次进入为1
					String next = Encode.nullToBlank(request.getParameter("next"));// 1表示获取下一题，-1表示获取上一题
					if ("".equals(index))
						index = "1";
					if ("".equals(next))
						next = "1";

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
							request.setAttribute("childList", childList);
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
	 * 教师端学案题
	 * */
	public ActionForward preparationQuestion2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
				List questionList = CacheUtil.getList("TkQuestionsInfoList_2_" + bookcontentid);
				if (questionList == null) {
					TkBookContentQuestionManager tbcqm = (TkBookContentQuestionManager) getBean("tkBookContentQuestionManager");
					questionList = tbcqm.getTkQuestionsInfosByBookcontentid(bookcontentid, "2", null);
					CacheUtil.putList("TkQuestionsInfoList_2_" + bookcontentid, questionList, 24 * 30);
				}
				if (questionList != null && questionList.size() > 0) {
					// 如果课前预习有文本内容并且有试题，则先显示文本内容，通过点击“查看试题”再具体显示试题
					String show = Encode.nullToBlank(request.getParameter("show"));// 通过点击触发“查看试题”按钮
					if (!"1".equals(show) && bookContent.getBeforeclass() != null && !"".equals(bookContent.getBeforeclass())) {
						request.setAttribute("show", "1");// 显示按钮“查看试题”
						return mapping.findForward("beforeclassinfo2");// 课前预习有关联题，先显示课前预习文本内容
					}
					request.setAttribute("show", show);

					// 选择上一题或下一题时，可能已经是做过的
					String index = Encode.nullToBlank(request.getParameter("index"));// 当前做题数，方便获取下一题，第一次进入为1
					String next = Encode.nullToBlank(request.getParameter("next"));// 1表示获取下一题，-1表示获取上一题
					if ("".equals(index))
						index = "1";
					if ("".equals(next))
						next = "1";

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

					// 题型分开显示，方便维护
					TkQuestionsType questionsType = questionsInfo.getTkQuestionsType();
					if ("A".equals(questionsType.getType())) {// 单选题
						return mapping.findForward("beforeclassA2");
					} else if ("B".equals(questionsType.getType())) {// 多选题
						return mapping.findForward("beforeclassB2");
					} else if ("C".equals(questionsType.getType())) {// 判断题
						return mapping.findForward("beforeclassC2");
					} else if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {
						// 查询当前试题的所有子题，如果有则是复合题
						TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
						List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid());
						if (childList == null) {
							childList = tqim.getAllChildTkQuestionsInfos(questionsInfo.getQuestionid());
							CacheUtil.putList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid(), childList, 24 * 30);
						}
						if (childList != null && childList.size() > 0) {// 复合题
							request.setAttribute("childList", childList);
							return mapping.findForward("beforeclassM2");
						} else {// 填空题
							return mapping.findForward("beforeclassE2");
						}
					} else {
						return mapping.findForward("beforeclassE2");
					}
				} else {
					return mapping.findForward("beforeclassinfo2");// 课前预习没有关联题，只显示课前预习文本内容
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
				String index = Encode.nullToBlank(request.getParameter("index"));
				String next = Encode.nullToBlank(request.getParameter("next"));
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);
				request.setAttribute("index", index);
				request.setAttribute("next", next);
				
				TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent bookContent = manager.getTkBookContent(bookcontentid);
				if(bookContent.getMp3path() != null && !"".equals(bookContent.getMp3path())){
					request.setAttribute("model", bookContent);

					return mapping.findForward("viewlianximain");
				}else {
					String redirecturl = "/weixinTeacher.app?method=viewLianxi&index=" + index + "&next=" + next + "&userid=" + DES.getEncryptPwd(userid) + "&bookcontentid=" + bookcontentid + "&bookid=" + bookid + "&classid=" + classid;
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
	 * 教师查看作业本所有试题
	 * */
	public ActionForward viewLianxi(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("classid", classid);
				request.setAttribute("bookid", bookid);

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
						return mapping.findForward("detail_viewlianxi");
					} else if ("F".equals(questionsType.getType()) || "M".equals(questionsType.getType())) {// 复合题
						// 查询当前试题的所有子题，如果有则是复合题【缓存查询子题】
						List childList = CacheUtil.getList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid());
						if (childList == null) {
							childList = tqim.getAllChildTkQuestionsInfos(questionsInfo.getQuestionid());
							CacheUtil.putList("TkQuestionsInfoChildList_" + questionsInfo.getQuestionid(), childList, 24 * 30);
						}
						if (childList != null && childList.size() > 0) {// 复合题
							request.setAttribute("childList", childList);
							return mapping.findForward("detail_viewlianxiM");
						} else {// 填空题
							return mapping.findForward("detail_viewlianxi");
						}
					} else {
						return mapping.findForward("detail_viewlianxi");
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
	//===================教师通过手机在线查看学生拍照作业======================
	/**
	 * 班级学生列表，查看所有学生，显示学生是否提交，按名称顺序显示
	 */
	public ActionForward classUserList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				request.setAttribute("classid", classid);
				request.setAttribute("bookcontentid", bookcontentid);
				
				TkClassUserManager manager = (TkClassUserManager) getBean("tkClassUserManager");
				TkClassUploadManager tcum = (TkClassUploadManager) getBean("tkClassUploadManager");
				List userList = manager.getSysUserInfosByClassid(classid);
				List useridList = tcum.getStudentsOfClassUpload(bookcontentid, classid);
				request.setAttribute("userList", userList);
				request.setAttribute("useridList", useridList);

				return mapping.findForward("classuserlist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
	
	public ActionForward classUserUpload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String classid = Encode.nullToBlank(request.getParameter("classid"));
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				request.setAttribute("classid", classid);
				request.setAttribute("bookcontentid", bookcontentid);
				
				String studentid = Encode.nullToBlank(request.getParameter("studentid"));
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo student = suim.getSysUserInfo(studentid);
				request.setAttribute("student", student);
				
				TkClassUploadManager manager = (TkClassUploadManager) getBean("tkClassUploadManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "classid", "=", Integer.valueOf(classid));
				SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(studentid));
				List list = manager.getTkClassUploads(condition, "createdate asc", 0);
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

				return mapping.findForward("classuserupload");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
}