package com.wkmk.weixin.web.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.concurrent.AutomationCache;
import com.util.date.DateTime;
import com.util.date.TimeUtil;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.search.PageList;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.bo.EduXueduanInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.edu.service.EduXueduanInfoManager;
import com.wkmk.sys.bo.SysPayPassword;
import com.wkmk.sys.bo.SysPaymentAccount;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserDisable;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserLog;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.bo.SysUserTeaching;
import com.wkmk.sys.service.SysMessageInfoManager;
import com.wkmk.sys.service.SysMessageUserManager;
import com.wkmk.sys.service.SysPayPasswordManager;
import com.wkmk.sys.service.SysPaymentAccountManager;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserDisableManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserLogManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.sys.service.SysUserTeachingManager;
import com.wkmk.sys.web.action.SysMessageUserAction;
import com.wkmk.util.common.ArithUtil;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;
import com.wkmk.weixin.mp.MpUtil;
import com.wkmk.weixin.util.UploadFileUtil;
import com.wkmk.zx.bo.ZxHelpAnswer;
import com.wkmk.zx.bo.ZxHelpFile;
import com.wkmk.zx.bo.ZxHelpOrder;
import com.wkmk.zx.bo.ZxHelpQuestion;
import com.wkmk.zx.bo.ZxHelpQuestionBuy;
import com.wkmk.zx.bo.ZxHelpQuestionComment;
import com.wkmk.zx.bo.ZxHelpQuestionComplaint;
import com.wkmk.zx.service.ZxHelpAnswerManager;
import com.wkmk.zx.service.ZxHelpFileManager;
import com.wkmk.zx.service.ZxHelpOrderManager;
import com.wkmk.zx.service.ZxHelpQuestionBuyManager;
import com.wkmk.zx.service.ZxHelpQuestionCommentManager;
import com.wkmk.zx.service.ZxHelpQuestionComplaintManager;
import com.wkmk.zx.service.ZxHelpQuestionManager;

/**
 * 在线答疑
 * 
 * @version 1.0
 */
public class WeixinHelpAction extends BaseAction {

	private byte[] lock = new byte[0]; // 特殊的instance变量

	/**
	 * 答疑首页
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				// 根据用户学段查询数据
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String usertype = "1";// 老师
				if ("2".equals(sysUserInfo.getUsertype())) {// 学生
					usertype = "2";
				}
				request.setAttribute("usertype", usertype);
				request.setAttribute("xueduan", sysUserInfo.getXueduan());
				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 查询条件年级（子学段）和学科从缓存中获取数据，因为不经常变动
				List cxueduanList = (List) CacheUtil.getObject("EduXueduanInfo_List_child");
				if (cxueduanList == null || cxueduanList.size() == 0) {
					EduXueduanInfoManager exim = (EduXueduanInfoManager) getBean("eduXueduanInfoManager");
					condition.clear();
					SearchCondition.addCondition(condition, "status", "=", "1");
					SearchCondition.addCondition(condition, "parentid", ">", 0);
					cxueduanList = exim.getEduXueduanInfos(condition, "parentid asc, orderindex asc", 0);
					CacheUtil.putObject("EduXueduanInfo_List_child", cxueduanList, 7 * 24 * 60 * 60);
				}
				List subjectList = (List) CacheUtil.getObject("EduSubjectInfo_List");
				if (subjectList == null || subjectList.size() == 0) {
					EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
					condition.clear();
					SearchCondition.addCondition(condition, "status", "=", "1");
					subjectList = esim.getEduSubjectInfos(condition, "orderindex asc", 0);
					CacheUtil.putObject("EduSubjectInfo_List", subjectList, 7 * 24 * 60 * 60);
				}
				request.setAttribute("cxueduanList", cxueduanList);
				request.setAttribute("subjectList", subjectList);
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));
				String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String replystatus = Encode.nullToBlank(request.getParameter("replystatus"));// 0未答，1已答
				String money = Encode.nullToBlank(request.getParameter("money"));// 0免费，1付费
				request.setAttribute("keywords", keywords);
				request.setAttribute("cxueduanid", cxueduanid);
				request.setAttribute("subjectid", subjectid);
				request.setAttribute("replystatus", replystatus);
				request.setAttribute("money", money);
				request.setAttribute("questionid", Encode.nullToBlank(request.getParameter("questionid")));
				// 如果是学生，根据学段查询数据，如果是老师，根据教学设置查询数据
				ZxHelpQuestionManager manager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				condition.clear();
				if (!"".equals(keywords)) {
					SearchCondition.addCondition(condition, "status", "=", "1' and (a.title like '%" + keywords + "%' or a.descript like '%" + keywords + "%') and 1='1");
				} else {
					SearchCondition.addCondition(condition, "status", "=", "1");
				}
				if (!"".equals(cxueduanid)) {
					SearchCondition.addCondition(condition, "cxueduanid", "=", Integer.valueOf(cxueduanid));
				}
				if (!"".equals(subjectid)) {
					SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
				}
				if ("0".equals(replystatus)) {
					SearchCondition.addCondition(condition, "replystatus", "<>", "3");
					// 查询未回答提问时，一般需求是老师，所以要控制回答时间未结束
					// SearchCondition.addCondition(condition, "enddate", ">", DateTime.getDate());
				} else if ("1".equals(replystatus)) {
					SearchCondition.addCondition(condition, "replystatus", "=", "3");
				}
				if ("0".equals(money)) {
					SearchCondition.addCondition(condition, "money", "<=", 0);
				} else if ("1".equals(money)) {
					SearchCondition.addCondition(condition, "money", ">", 0);
				}
				// 默认没有选择年级和学科时，才根据用户类型查询数据，否则根据查询条件查询，但在老师点击回答问题时，只能控制回答教学设置中的学科年级
				if ("".equals(cxueduanid) && "".equals(subjectid)) {
					String xd = "0";
					if ("1".equals(usertype)) {
						SysUserTeachingManager sutm = (SysUserTeachingManager) getBean("sysUserTeachingManager");
						List userteachingList = sutm.getSysUserTeachings(Integer.valueOf(userid));
						if (userteachingList != null && userteachingList.size() > 0) {
							StringBuffer subjectids = new StringBuffer();
							StringBuffer gradeids = new StringBuffer();
							SysUserTeaching sut = null;
							for (int i = 0, size = userteachingList.size(); i < size; i++) {
								sut = (SysUserTeaching) userteachingList.get(i);
								subjectids.append(",").append(sut.getSubjectid());
								gradeids.append(",").append(sut.getGradeid());
							}
							SearchCondition.addCondition(condition, "subjectid", "in", subjectids.substring(1) + ") and a.gradeid in (" + gradeids.substring(1));
						} else {// 没有设置教学，就通过教师学段查询
							xd = "1";
						}
					} else {
						xd = "1";
					}
					if ("1".equals(xd)) {
						String userxueduan = sysUserInfo.getXueduan();
						if ("1".equals(userxueduan)) {
							SearchCondition.addCondition(condition, "cxueduanid", "in", "9,10,11,12,13,14");
						} else if ("2".equals(userxueduan)) {
							SearchCondition.addCondition(condition, "cxueduanid", "in", "15,16,17");
						} else if ("3".equals(userxueduan)) {
							SearchCondition.addCondition(condition, "cxueduanid", "in", "19,20,21");
						}
					}
				}
				String pn = Encode.nullToBlank(request.getParameter("pagenum"));
				int pagenum = "".equals(pn) ? 1 : Integer.parseInt(pn);
				Integer pagesize = pagenum * 10;
				List list = manager.getZxHelpQuestions(condition, "createdate desc", pagesize);
				request.setAttribute("list", list);
				request.setAttribute("pagenum", pagenum);
				return mapping.findForward("index");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * ajax加载答疑
	 */
	public ActionForward getAjaxIndexQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = MpUtil.getUserid(request);
			// 根据用户学段查询数据
			SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
			String usertype = "1";// 老师
			if ("2".equals(sysUserInfo.getUsertype())) {// 学生
				usertype = "2";
			}
			String questionid = Encode.nullToBlank(request.getParameter("questionid"));
			String pagenum = Encode.nullToBlank(request.getParameter("pagenum"));
			if ("".equals(pagenum)) {
				pagenum = "1";
			}
			int startcount = Integer.valueOf(pagenum).intValue() * 10;
			String keywords = Encode.nullToBlank(request.getParameter("keywords"));
			String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
			String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
			String replystatus = Encode.nullToBlank(request.getParameter("replystatus"));// 0未答，1已答
			String money = Encode.nullToBlank(request.getParameter("money"));// 0免费，1付费
			// 如果是学生，根据学段查询数据，如果是老师，根据教学设置查询数据
			ZxHelpQuestionManager manager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			if (!"".equals(keywords)) {
				SearchCondition.addCondition(condition, "status", "=", "1' and (a.title like '%" + keywords + "%' or a.descript like '%" + keywords + "%') and 1='1");
			} else {
				SearchCondition.addCondition(condition, "status", "=", "1");
			}
			if (!"".equals(cxueduanid)) {
				SearchCondition.addCondition(condition, "cxueduanid", "=", Integer.valueOf(cxueduanid));
			}
			if (!"".equals(subjectid)) {
				SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
			}
			if ("0".equals(replystatus)) {
				SearchCondition.addCondition(condition, "replystatus", "<>", "3");
				// SearchCondition.addCondition(condition, "enddate", ">", DateTime.getDate());
			} else if ("1".equals(replystatus)) {
				SearchCondition.addCondition(condition, "replystatus", "=", "3");
			}
			if ("0".equals(money)) {
				SearchCondition.addCondition(condition, "money", "<=", 0);
			} else if ("1".equals(money)) {
				SearchCondition.addCondition(condition, "money", ">", 0);
			}
			// 默认没有选择年级和学科时，才根据用户类型查询数据，否则根据查询条件查询，但在老师点击回答问题时，只能控制回答教学设置中的学科年级
			if ("".equals(cxueduanid) && "".equals(subjectid)) {
				String xd = "0";
				if ("1".equals(usertype)) {
					SysUserTeachingManager sutm = (SysUserTeachingManager) getBean("sysUserTeachingManager");
					List userteachingList = sutm.getSysUserTeachings(Integer.valueOf(userid));
					if (userteachingList != null && userteachingList.size() > 0) {
						StringBuffer subjectids = new StringBuffer();
						StringBuffer gradeids = new StringBuffer();
						SysUserTeaching sut = null;
						for (int i = 0, size = userteachingList.size(); i < size; i++) {
							sut = (SysUserTeaching) userteachingList.get(i);
							subjectids.append(",").append(sut.getSubjectid());
							gradeids.append(",").append(sut.getGradeid());
						}
						SearchCondition.addCondition(condition, "subjectid", "in", subjectids.substring(1) + ") and a.gradeid in (" + gradeids.substring(1));
					} else {// 没有设置教学，就通过教师学段查询
						xd = "1";
					}
				} else {
					xd = "1";
				}
				if ("1".equals(xd)) {
					String userxueduan = sysUserInfo.getXueduan();
					if ("1".equals(userxueduan)) {
						SearchCondition.addCondition(condition, "cxueduanid", "in", "9,10,11,12,13,14");
					} else if ("2".equals(userxueduan)) {
						SearchCondition.addCondition(condition, "cxueduanid", "in", "15,16,17");
					} else if ("3".equals(userxueduan)) {
						SearchCondition.addCondition(condition, "cxueduanid", "in", "19,20,21");
					}
				}
			}
			PageList pageList = manager.getPageZxHelpQuestions(condition, "createdate desc", startcount, 10);
			List list = pageList.getDatalist();
			StringBuffer str = new StringBuffer();
			if (list != null && list.size() > 0) {
				ZxHelpQuestion question = null;
				SysUserInfo sui = null;
				String photo = null;
				for (int i = 0, size = list.size(); i < size; i++) {
					question = (ZxHelpQuestion) list.get(i);
					sui = question.getSysUserInfo();
					if (sui.getPhoto().startsWith("http://")) {
						photo = sui.getPhoto();
					} else {
						photo = "/upload/" + sui.getPhoto();
					}
					str.append("<a href=\"javascript:questionInfo(").append(question.getQuestionid() + ")");
					if (question.getQuestionid().toString().equals(questionid)) {
						str.append(" id=\"md\"");
					}
					str.append("\"><div class=\"answer_student\">");
					str.append("<div class=\"answer_student_top\"><img src=\"").append(photo).append("\" /><p>").append(sui.getUsername()).append(" • ").append(question.getSubjectname()).append(" - ").append(question.getGradename()).append("</p>");
					str.append("<p class=\"answer_student_top_p\">").append(TimeUtil.getTimeName(question.getCreatedate())).append("</p></div>");
					str.append("<div class=\"answer_student_font\">");
					if ("3".equals(question.getReplystatus())) {
						str.append("<p class=\"answer_student_font_p answer_student_font_p01\">已答</p>");
					} else {
						str.append("<p class=\"answer_student_font_p\">未答</p>");
					}
					str.append("<p class=\"answer_student_font_pright\">").append(question.getTitle()).append("</p></div><p class=\"answer_student_p\">").append(question.getDescript()).append("</p>");
					str.append("<p class=\"answer_student_p_teacher\">");
					if ("1".equals(usertype) && "0".equals(question.getReplystatus())) {
						str.append("回答截止时间：").append(question.getEnddate());
					}
					if (question.getMoney() > 0) {
						if ("3".equals(question.getReplystatus())) {
							str.append("<font style=\"text-align:right;float:right;\">价格：").append(question.getMoney() * Constants.BUY_QUESTION_DISCOUNT / 10).append("学币</font>");
						} else {
							str.append("<font style=\"text-align:right;float:right;\">价格：").append(question.getMoney()).append("学币</font>");
						}
					} else {
						str.append("<font style=\"text-align:right;float:right;color:#1fcc8a;\">免费</font>");
					}
					str.append("</p></div></a>");
				}
			}
			pw = response.getWriter();
			pw.write(str.toString());
		} catch (Exception ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}

	/**
	 * 查看答疑详情 1自己的，未被抢单，可修改参数，如修改时间、价格等 2未答：1、学生查看，2老师抢单回答（未被抢单，时间有效期内） 3已答：1、查看购买，2、已购买直接查看评价
	 */
	public ActionForward info(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				request.setAttribute("tag", Encode.nullToBlank(request.getParameter("tag")));
				request.setAttribute("menutype", Encode.nullToBlank(request.getParameter("menutype")));
				request.setAttribute("pagenum", Encode.nullToBlank(request.getParameter("pagenum")));
				request.setAttribute("sys_userid", userid);
				// 根据用户学段查询数据
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String usertype = "1";// 老师
				if ("2".equals(sysUserInfo.getUsertype())) {// 学生
					usertype = "2";
				}
				request.setAttribute("usertype", usertype);
				request.setAttribute("sysUserInfo", sysUserInfo);
				// 通过userid获取openid，支付需要，如果没有，则退出登录
				SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				SysUserAttention sysUserAttention = suam.getSysUserAttentionByUserid(Integer.valueOf(userid));
				if (sysUserAttention == null) {
					// 用户没有对应的openid，需要重新注册
					return mapping.findForward("welcome");
				}
				request.setAttribute("openid", sysUserAttention.getOpenid());
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				ZxHelpQuestionManager manager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				ZxHelpQuestion question = manager.getZxHelpQuestion(questionid);
				question.setHits(question.getHits() + 1);
				manager.updateZxHelpQuestion(question);
				request.setAttribute("question", question);
				// 获取提问附件
				ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
				List questionFileList = null;
				if ("3".equals(question.getReplystatus()) || "2".equals(question.getReplystatus())) {
					// 如果答疑被回复，则答疑不能修改，可以放缓存
					questionFileList = (List) CacheUtil.getObject("ZxHelpFile_List_1" + questionid);
					if (questionFileList == null || questionFileList.size() == 0) {
						questionFileList = zhfm.getZxHelpFilesByQuestionid(questionid);
						CacheUtil.putObject("ZxHelpFile_List_1" + questionid, questionFileList, 7 * 24 * 60 * 60);
					}
				} else {
					questionFileList = zhfm.getZxHelpFilesByQuestionid(questionid);
				}
				List imageQuestionFileList = new ArrayList();
				List audioQuestionFileList = new ArrayList();
				// 调用微信js接口图库预览
				StringBuffer questionImgUrls = new StringBuffer();
				ZxHelpFile zhf = null;
				for (int i = 0, size = questionFileList.size(); i < size; i++) {
					zhf = (ZxHelpFile) questionFileList.get(i);
					if ("1".equals(zhf.getFiletype())) {
						imageQuestionFileList.add(zhf);
						if (questionImgUrls.length() == 0) {
							questionImgUrls.append("'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
						} else {
							questionImgUrls.append(",'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
						}
					} else if ("2".equals(zhf.getFiletype())) {
						audioQuestionFileList.add(zhf);
					}
				}
				request.setAttribute("questionImgUrls", questionImgUrls.toString());
				request.setAttribute("questionFileList", questionFileList);
				request.setAttribute("imageQuestionFileList", imageQuestionFileList);
				request.setAttribute("audioQuestionFileList", audioQuestionFileList);
				SysUserInfo student = question.getSysUserInfo();
				if (!student.getPhoto().startsWith("http://")) {
					student.setPhoto("/upload/" + student.getPhoto());
				}
				request.setAttribute("student", student);
				if ("3".equals(question.getReplystatus()) || "2".equals(question.getReplystatus()) || question.getMoney() <= 0) {
					// 获取当前答疑评价
					ZxHelpQuestionCommentManager zhqcm = (ZxHelpQuestionCommentManager) getBean("zxHelpQuestionCommentManager");
					List scoreList = zhqcm.getZxHelpQuestionCommentScore(questionid);
					int praise = 0;
					int praise1 = 0;// 好评
					int praise2 = 0;// 中评
					int praise3 = 0;// 差评
					if (scoreList != null && scoreList.size() > 0) {
						praise = scoreList.size();
						int score = 0;
						for (int i = 0, size = scoreList.size(); i < size; i++) {
							score = ((Integer) scoreList.get(i)).intValue();
							if (score > 3) {
								praise1++;
							} else if (score > 1 && score <= 3) {
								praise2++;
							} else {
								praise3++;
							}
						}
					}
					request.setAttribute("praise", praise);
					request.setAttribute("praise1", praise1);
					request.setAttribute("praise2", praise2);
					request.setAttribute("praise3", praise3);
				}
				if (question.getMoney().floatValue() <= 0) {
					info(request, sysUserInfo, question);
					return mapping.findForward("info");
				} else if (student.getUserid().toString().equals(userid)) {
					info1(request, userid, question);
					return mapping.findForward("info1");
				} else if (!"3".equals(question.getReplystatus())) {
					info2(request, sysUserInfo, question);
					return mapping.findForward("info2");
				} else if ("3".equals(question.getReplystatus())) {
					info3(request, userid, question);
					return mapping.findForward("info3");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	private void info(HttpServletRequest request, SysUserInfo sysUserInfo, ZxHelpQuestion question) {
		String isAnswer = "0";// 是否可回答答疑
		ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpOrder order = zhom.getZxHelpOrderByQuestionid(question.getQuestionid().toString());
		request.setAttribute("order", order);
		// 如果是老师，查看老师是否已抢单，可直接回答
		if ("1".equals(sysUserInfo.getUsertype())) {
			// 查看当前订单是否已过期
			String enddate = question.getEnddate();
			String curdate = DateTime.getDate();
			if (DateTime.getCompareToDate(curdate, enddate) < 0) {
				if (order == null) {
					isAnswer = "1";// 可回复答疑
				} else {
					if ("1".equals(order.getStatus()) && sysUserInfo.getUserid().intValue() == order.getUserid().intValue()) {
						isAnswer = "2";// 可直接继续回复
					}
				}
			}
		}
		if ("3".equals(question.getReplystatus())) {
			if ("2".equals(order.getStatus())) {
				// 查询答疑教师信息
				SysUserInfo teacher = (SysUserInfo) CacheUtil.getObject("SysUserInfo_" + order.getUserid());
				if (teacher == null) {
					SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
					teacher = suim.getSysUserInfo(order.getUserid());
					if (!teacher.getPhoto().startsWith("http://")) {
						teacher.setPhoto("/upload/" + teacher.getPhoto());
					}
					CacheUtil.putObject("SysUserInfo_" + order.getUserid(), teacher, 24 * 60 * 60);
				}
				request.setAttribute("teacher", teacher);
				ZxHelpAnswer answer = (ZxHelpAnswer) CacheUtil.getObject("ZxHelpAnswer_order_" + order.getOrderid());
				if (answer == null) {
					ZxHelpAnswerManager zham = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
					answer = zham.getZxHelpAnswerByOrderid(order.getOrderid().toString());
					CacheUtil.putObject("ZxHelpAnswer_order_" + order.getOrderid(), answer, 7 * 24 * 60 * 60);
				}
				request.setAttribute("answer", answer);
				// 获取回答附件
				ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
				// 如果答疑被回复，则答疑不能修改，可以放缓存
				List answerFileList = (List) CacheUtil.getObject("ZxHelpFile_List_2" + answer.getAnswerid());
				if (answerFileList == null || answerFileList.size() == 0) {
					answerFileList = zhfm.getZxHelpFilesByAnswerid(answer.getAnswerid().toString());
					// 回答答疑附件放缓存中，需要通过时间隔开，因为多线程保存附件，还没有保存完附件，就已经走到此处放入缓存中了
					if (answerFileList != null && answerFileList.size() > 0) {
						ZxHelpFile zxHelpFile = (ZxHelpFile) answerFileList.get(0);
						// 附件保存10分钟后，才放入缓存，开始的时候实时查询数据
						String fileCreatedate = zxHelpFile.getCreatedate();
						try {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = format.parse(fileCreatedate);
							long time = date.getTime() + 10 * 60 * 1000 - System.currentTimeMillis();
							if (time < 0) {
								CacheUtil.putObject("ZxHelpFile_List_2" + answer.getAnswerid(), answerFileList, 7 * 24 * 60 * 60);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				List imageAnswerFileList = new ArrayList();
				List audioAnswerFileList = new ArrayList();
				List videoAnswerFileList = new ArrayList();
				// 调用微信js接口图库预览
				StringBuffer answerImgUrls = new StringBuffer();
				ZxHelpFile zhf = null;
				for (int i = 0, size = answerFileList.size(); i < size; i++) {
					zhf = (ZxHelpFile) answerFileList.get(i);
					if ("1".equals(zhf.getFiletype())) {
						imageAnswerFileList.add(zhf);
						if (answerImgUrls.length() == 0) {
							answerImgUrls.append("'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
						} else {
							answerImgUrls.append(",'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
						}
					} else if ("2".equals(zhf.getFiletype())) {
						audioAnswerFileList.add(zhf);
					} else if ("3".equals(zhf.getFiletype())) {
						videoAnswerFileList.add(zhf);
					}
				}
				request.setAttribute("answerImgUrls", answerImgUrls.toString());
				request.setAttribute("imageAnswerFileList", imageAnswerFileList);
				request.setAttribute("audioAnswerFileList", audioAnswerFileList);
				request.setAttribute("videoAnswerFileList", videoAnswerFileList);
				request.setAttribute("answerFileList", answerFileList);
			}
		}
		request.setAttribute("isAnswer", isAnswer);
	}

	private void info1(HttpServletRequest request, String userid, ZxHelpQuestion question) {
		// 查看自己提问的基本信息，如果未被抢单，可修改基本信息，如果已回复，可直接查看答案、在线评价、在线投诉等
		if ("3".equals(question.getReplystatus()) || "2".equals(question.getReplystatus())) {
			ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
			ZxHelpOrder order = zhom.getZxHelpOrderByQuestionid(question.getQuestionid().toString());
			request.setAttribute("order", order);
			// 查询是否被投诉过
			String iscomplaint = "0";
			ZxHelpQuestionComplaintManager manager = (ZxHelpQuestionComplaintManager) getBean("zxHelpQuestionComplaintManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "questionid", "=", question.getQuestionid());
			SearchCondition.addCondition(condition, "orderid", "=", order.getOrderid());
			List list = manager.getZxHelpQuestionComplaints(condition, "", 0);
			if (list != null && list.size() > 0) {
				iscomplaint = "1";
			}
			request.setAttribute("iscomplaint", iscomplaint);
			if ("2".equals(order.getStatus())) {
				// 查询答疑教师信息
				SysUserInfo teacher = (SysUserInfo) CacheUtil.getObject("SysUserInfo_" + order.getUserid());
				if (teacher == null) {
					SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
					teacher = suim.getSysUserInfo(order.getUserid());
					if (!teacher.getPhoto().startsWith("http://")) {
						teacher.setPhoto("/upload/" + teacher.getPhoto());
					}
					CacheUtil.putObject("SysUserInfo_" + order.getUserid(), teacher, 24 * 60 * 60);
				}
				request.setAttribute("teacher", teacher);
				ZxHelpAnswer answer = (ZxHelpAnswer) CacheUtil.getObject("ZxHelpAnswer_order_" + order.getOrderid());
				if (answer == null) {
					ZxHelpAnswerManager zham = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
					answer = zham.getZxHelpAnswerByOrderid(order.getOrderid().toString());
					CacheUtil.putObject("ZxHelpAnswer_order_" + order.getOrderid(), answer, 7 * 24 * 60 * 60);
				}
				request.setAttribute("answer", answer);
				// 获取回答附件
				ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
				// 如果答疑被回复，则答疑不能修改，可以放缓存
				List answerFileList = (List) CacheUtil.getObject("ZxHelpFile_List_2" + answer.getAnswerid());
				if (answerFileList == null || answerFileList.size() == 0) {
					answerFileList = zhfm.getZxHelpFilesByAnswerid(answer.getAnswerid().toString());
					// 回答答疑附件放缓存中，需要通过时间隔开，因为多线程保存附件，还没有保存完附件，就已经走到此处放入缓存中了
					if (answerFileList != null && answerFileList.size() > 0) {
						ZxHelpFile zxHelpFile = (ZxHelpFile) answerFileList.get(0);
						// 附件保存10分钟后，才放入缓存，开始的时候实时查询数据
						String fileCreatedate = zxHelpFile.getCreatedate();
						try {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date date = format.parse(fileCreatedate);
							long time = date.getTime() + 10 * 60 * 1000 - System.currentTimeMillis();
							if (time < 0) {
								CacheUtil.putObject("ZxHelpFile_List_2" + answer.getAnswerid(), answerFileList, 7 * 24 * 60 * 60);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				List imageAnswerFileList = new ArrayList();
				List audioAnswerFileList = new ArrayList();
				List videoAnswerFileList = new ArrayList();
				// 调用微信js接口图库预览
				StringBuffer answerImgUrls = new StringBuffer();
				ZxHelpFile zhf = null;
				for (int i = 0, size = answerFileList.size(); i < size; i++) {
					zhf = (ZxHelpFile) answerFileList.get(i);
					if ("1".equals(zhf.getFiletype())) {
						imageAnswerFileList.add(zhf);
						if (answerImgUrls.length() == 0) {
							answerImgUrls.append("'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
						} else {
							answerImgUrls.append(",'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
						}
					} else if ("2".equals(zhf.getFiletype())) {
						audioAnswerFileList.add(zhf);
					} else if ("3".equals(zhf.getFiletype())) {
						videoAnswerFileList.add(zhf);
					}
				}
				request.setAttribute("answerImgUrls", answerImgUrls.toString());
				request.setAttribute("imageAnswerFileList", imageAnswerFileList);
				request.setAttribute("audioAnswerFileList", audioAnswerFileList);
				request.setAttribute("videoAnswerFileList", videoAnswerFileList);
				request.setAttribute("answerFileList", answerFileList);
			}
		}
	}

	private void info2(HttpServletRequest request, SysUserInfo sysUserInfo, ZxHelpQuestion question) {
		// 如果是老师，查看老师是否已抢单，可直接回答
		if ("1".equals(sysUserInfo.getUsertype()) && "1".equals(sysUserInfo.getAuthentication())) {
			// 查看当前订单是否已过期
			String enddate = question.getEnddate();
			String curdate = DateTime.getDate();
			String isend = "1";
			if (DateTime.getCompareToDate(curdate, enddate) == -1) {
				isend = "0";
			}
			request.setAttribute("isend", isend);
			ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "questionid", "=", question.getQuestionid());
			SearchCondition.addCondition(condition, "userid", "=", sysUserInfo.getUserid());
			List list = manager.getZxHelpOrders(condition, "createdate desc", 0);
			if (list != null && list.size() > 0) {
				ZxHelpOrder order = (ZxHelpOrder) list.get(0);
				request.setAttribute("order", order);
				// 查询是否被投诉过
				String iscomplaint = "0";
				ZxHelpQuestionComplaintManager complaintmanager = (ZxHelpQuestionComplaintManager) getBean("zxHelpQuestionComplaintManager");
				condition.clear();
				SearchCondition.addCondition(condition, "questionid", "=", question.getQuestionid());
				SearchCondition.addCondition(condition, "orderid", "=", order.getOrderid());
				List clist = complaintmanager.getZxHelpQuestionComplaints(condition, "", 0);
				if (clist != null && clist.size() > 0) {
					iscomplaint = "1";
				}
				request.setAttribute("iscomplaint", iscomplaint);
				if ("2".equals(question.getReplystatus())) {// 老师已回复
					// 查 教师信息
					SysUserInfo teacher = (SysUserInfo) CacheUtil.getObject("SysUserInfo_" + order.getUserid());
					if (teacher == null) {
						SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
						teacher = suim.getSysUserInfo(order.getUserid());
						if (!teacher.getPhoto().startsWith("http://")) {
							teacher.setPhoto("/upload/" + teacher.getPhoto());
						}
						CacheUtil.putObject("SysUserInfo_" + order.getUserid(), teacher, 24 * 60 * 60);
					}
					request.setAttribute("teacher", teacher);
					ZxHelpAnswer answer = (ZxHelpAnswer) CacheUtil.getObject("ZxHelpAnswer_order_" + order.getOrderid());
					if (answer == null) {
						ZxHelpAnswerManager zham = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
						answer = zham.getZxHelpAnswerByOrderid(order.getOrderid().toString());
						CacheUtil.putObject("ZxHelpAnswer_order_" + order.getOrderid(), answer, 7 * 24 * 60 * 60);
					}
					request.setAttribute("answer", answer);
					// 获取回答附件
					ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
					// 如果答疑被回复，则答疑不能修改，可以放缓存
					List answerFileList = (List) CacheUtil.getObject("ZxHelpFile_List_2" + answer.getAnswerid());
					if (answerFileList == null || answerFileList.size() == 0) {
						answerFileList = zhfm.getZxHelpFilesByAnswerid(answer.getAnswerid().toString());
						// 回答答疑附件放缓存中，需要通过时间隔开，因为多线程保存附件，还没有保存完附件，就已经走到此处放入缓存中了
						if (answerFileList != null && answerFileList.size() > 0) {
							ZxHelpFile zxHelpFile = (ZxHelpFile) answerFileList.get(0);
							// 附件保存10分钟后，才放入缓存，开始的时候实时查询数据
							String fileCreatedate = zxHelpFile.getCreatedate();
							try {
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								Date date = format.parse(fileCreatedate);
								long time = date.getTime() + 10 * 60 * 1000 - System.currentTimeMillis();
								if (time < 0) {
									CacheUtil.putObject("ZxHelpFile_List_2" + answer.getAnswerid(), answerFileList, 7 * 24 * 60 * 60);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					List imageAnswerFileList = new ArrayList();
					List audioAnswerFileList = new ArrayList();
					List videoAnswerFileList = new ArrayList();
					// 调用微信js接口图库预览
					StringBuffer answerImgUrls = new StringBuffer();
					ZxHelpFile zhf = null;
					for (int i = 0, size = answerFileList.size(); i < size; i++) {
						zhf = (ZxHelpFile) answerFileList.get(i);
						if ("1".equals(zhf.getFiletype())) {
							imageAnswerFileList.add(zhf);
							if (answerImgUrls.length() == 0) {
								answerImgUrls.append("'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
							} else {
								answerImgUrls.append(",'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
							}
						} else if ("2".equals(zhf.getFiletype())) {
							audioAnswerFileList.add(zhf);
						} else if ("3".equals(zhf.getFiletype())) {
							videoAnswerFileList.add(zhf);
						}
					}
					request.setAttribute("answerImgUrls", answerImgUrls.toString());
					request.setAttribute("imageAnswerFileList", imageAnswerFileList);
					request.setAttribute("audioAnswerFileList", audioAnswerFileList);
					request.setAttribute("videoAnswerFileList", videoAnswerFileList);
					request.setAttribute("answerFileList", answerFileList);
				}
			}
		}
	}

	private void info3(HttpServletRequest request, String userid, ZxHelpQuestion question) {
		ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpOrder order = zhom.getZxHelpOrderByQuestionid(question.getQuestionid().toString());
		// 查询答疑教师信息
		SysUserInfo teacher = (SysUserInfo) CacheUtil.getObject("SysUserInfo_" + order.getUserid());
		if (teacher == null) {
			SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
			teacher = suim.getSysUserInfo(order.getUserid());
			if (!teacher.getPhoto().startsWith("http://")) {
				teacher.setPhoto("/upload/" + teacher.getPhoto());
			}
			CacheUtil.putObject("SysUserInfo_" + order.getUserid(), teacher, 24 * 60 * 60);
		}
		request.setAttribute("teacher", teacher);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		// 查询是否被投诉过
		String iscomplaint = "0";
		if (order != null) {
			ZxHelpQuestionComplaintManager complaintmanager = (ZxHelpQuestionComplaintManager) getBean("zxHelpQuestionComplaintManager");
			SearchCondition.addCondition(condition, "questionid", "=", question.getQuestionid());
			SearchCondition.addCondition(condition, "orderid", "=", order.getOrderid());
			List clist = complaintmanager.getZxHelpQuestionComplaints(condition, "", 0);
			if (clist != null && clist.size() > 0) {
				iscomplaint = "1";
			}
		}
		request.setAttribute("iscomplaint", iscomplaint);
		// 判断当前用户是否可直接查看答案和评价，已购买、免费答疑、回答教师都可以查看答案
		String viewAnswer = "0";// 1表示可查看答案
		String canConment = "0";// 1表示可发布评价
		String alreadyBuy = "0";// 1表示已购买
		String isAnswerTeacher = "0";
		if (question.getMoney() <= 0 || userid.equals(teacher.getUserid().toString())) {
			viewAnswer = "1";
			canConment = "1";
			if (userid.equals(teacher.getUserid().toString())) {
				isAnswerTeacher = "1";
			}
		} else {
			ZxHelpQuestionBuyManager zhqbm = (ZxHelpQuestionBuyManager) getBean("zxHelpQuestionBuyManager");
			condition.clear();
			SearchCondition.addCondition(condition, "questionid", "=", question.getQuestionid());
			SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
			SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
			List list = zhqbm.getZxHelpQuestionBuys(condition, "", 0);
			if (list != null && list.size() > 0) {
				viewAnswer = "1";
				canConment = "1";
				alreadyBuy = "1";
			}
		}
		request.setAttribute("viewAnswer", viewAnswer);
		request.setAttribute("canConment", canConment);
		request.setAttribute("alreadyBuy", alreadyBuy);
		request.setAttribute("isAnswerTeacher", isAnswerTeacher);
		ZxHelpAnswer answer = (ZxHelpAnswer) CacheUtil.getObject("ZxHelpAnswer_order_" + order.getOrderid());
		if (answer == null) {
			ZxHelpAnswerManager zham = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
			answer = zham.getZxHelpAnswerByOrderid(order.getOrderid().toString());
			CacheUtil.putObject("ZxHelpAnswer_order_" + order.getOrderid(), answer, 7 * 24 * 60 * 60);
		}
		request.setAttribute("answer", answer);
		// 获取回答附件
		ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
		// 如果答疑被回复，则答疑不能修改，可以放缓存
		List answerFileList = (List) CacheUtil.getObject("ZxHelpFile_List_2" + answer.getAnswerid());
		if (answerFileList == null || answerFileList.size() == 0) {
			answerFileList = zhfm.getZxHelpFilesByAnswerid(answer.getAnswerid().toString());
			// 回答答疑附件放缓存中，需要通过时间隔开，因为多线程保存附件，还没有保存完附件，就已经走到此处放入缓存中了
			if (answerFileList != null && answerFileList.size() > 0) {
				ZxHelpFile zxHelpFile = (ZxHelpFile) answerFileList.get(0);
				// 附件保存10分钟后，才放入缓存，开始的时候实时查询数据
				String fileCreatedate = zxHelpFile.getCreatedate();
				try {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = format.parse(fileCreatedate);
					long time = date.getTime() + 10 * 60 * 1000 - System.currentTimeMillis();
					if (time < 0) {
						CacheUtil.putObject("ZxHelpFile_List_2" + answer.getAnswerid(), answerFileList, 7 * 24 * 60 * 60);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		List imageAnswerFileList = new ArrayList();
		List audioAnswerFileList = new ArrayList();
		List videoAnswerFileList = new ArrayList();
		// 调用微信js接口图库预览
		StringBuffer answerImgUrls = new StringBuffer();
		ZxHelpFile zhf = null;
		for (int i = 0, size = answerFileList.size(); i < size; i++) {
			zhf = (ZxHelpFile) answerFileList.get(i);
			if ("1".equals(zhf.getFiletype())) {
				imageAnswerFileList.add(zhf);
				if (answerImgUrls.length() == 0) {
					answerImgUrls.append("'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
				} else {
					answerImgUrls.append(",'").append(MpUtil.HOMEPAGE).append("/upload/").append(zhf.getFilepath()).append("'");
				}
			} else if ("2".equals(zhf.getFiletype())) {
				audioAnswerFileList.add(zhf);
			} else if ("3".equals(zhf.getFiletype())) {
				videoAnswerFileList.add(zhf);
			}
		}
		request.setAttribute("answerImgUrls", answerImgUrls.toString());
		request.setAttribute("imageAnswerFileList", imageAnswerFileList);
		request.setAttribute("audioAnswerFileList", audioAnswerFileList);
		request.setAttribute("videoAnswerFileList", videoAnswerFileList);
		request.setAttribute("answerFileList", answerFileList);
		// 获取用户当天支付密码输入错误次数
		SysPayPasswordManager sppm = (SysPayPasswordManager) getBean("sysPayPasswordManager");
		int errorcount = sppm.getCountSysPayPassword(userid);
		if (errorcount < Constants.PAYPASSWORD_ERROR_COUNT) {
			request.setAttribute("paypassword", "1");
		} else {
			request.setAttribute("paypassword", "0");
		}
	}

	/**
	 * ajax加载微课评价
	 */
	public ActionForward getAjaxComment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			// String userid = MpUtil.getUserid(request);
			String ftag = Encode.nullToBlank(request.getParameter("ftag"));// 评论内容，不显示星星
			String questionid = Encode.nullToBlank(request.getParameter("questionid"));
			String commenttype = Encode.nullToBlank(request.getParameter("commenttype"));// 1全部，2好评，3中评，4差评
			String pagenum = Encode.nullToBlank(request.getParameter("pagenum"));
			if ("".equals(pagenum)) {
				pagenum = "0";
			}
			int startcount = Integer.valueOf(pagenum).intValue() * 10;
			ZxHelpQuestionCommentManager manager = (ZxHelpQuestionCommentManager) getBean("zxHelpQuestionCommentManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "questionid", "=", Integer.valueOf(questionid));
			SearchCondition.addCondition(condition, "status", "=", "1");
			if ("2".equals(commenttype)) {
				SearchCondition.addCondition(condition, "score", ">", 3);
			} else if ("3".equals(commenttype)) {
				SearchCondition.addCondition(condition, "score", ">", 1);
				SearchCondition.addCondition(condition, "score", "<=", 3);
			} else if ("4".equals(commenttype)) {
				SearchCondition.addCondition(condition, "score", "<=", 1);
			}
			PageList pageList = manager.getPageZxHelpQuestionComments(condition, "createdate desc", startcount, 10);
			List list = pageList.getDatalist();
			StringBuffer str = new StringBuffer();
			if (list != null && list.size() > 0) {
				ZxHelpQuestionComment comment = null;
				SysUserInfo sysUserInfo = null;
				String username = null;
				String photo = null;
				for (int i = 0, size = list.size(); i < size; i++) {
					comment = (ZxHelpQuestionComment) list.get(i);
					sysUserInfo = comment.getSysUserInfo();
					username = sysUserInfo.getUsername();
					if (username == null || "".equals(username)) {
						username = sysUserInfo.getLoginname();
					}
					photo = sysUserInfo.getPhoto();
					if (!photo.startsWith("http://")) {
						photo = "/upload/" + photo;
					}
					str.append("<div class=\"Contentbox02_main\"><div class=\"Contentbox02_main_top\">");
					if ("1".equals(comment.getAnonymous())) {// 匿名
						str.append("<img src=\"/upload/user.png\" /><p>").append(username.substring(0, 1)).append("**</p>");
					} else {
						str.append("<img src=\"").append(photo).append("\" /><p>").append(username).append("</p>");
					}
					str.append("<p class=\"Contentbox02_main_top_p\">").append(comment.getCreatedate()).append("</p></div>");
					str.append("<div  class=\"Contentbox02_main_font\">");
					if (!"1".equals(ftag)) {
						str.append("<div  class=\"Contentbox02_main_font_img\">");
						for (int m = 1; m <= 5; m++) {
							if (comment.getScore() >= m) {
								str.append("<img src=\"/weixin/images/icon16.png\" />");
							} else {
								str.append("<img src=\"/weixin/images/icon17.png\" />");
							}
						}
						str.append("</div>");
					}
					str.append("<p>").append(comment.getContent()).append("</p></div>");
					if (comment.getReplycontent() != null && !"".equals(comment.getReplycontent())) {
						str.append(" <div class=\"Contentbox02_main_font_hf\"><div class=\"Contentbox02_main_font_hf_p\"><p class=\"Contentbox02_main_font_hf_p01\">回复：</p>");
						str.append("<p class=\"Contentbox02_main_font_hf_p03\">").append(comment.getReplycreatedate()).append("</p></div>");
						str.append("<p class=\"Contentbox02_main_font_hf_p02\">").append(comment.getReplycontent()).append("</p></div>");
					}
					str.append("</div>");
				}
			}
			pw = response.getWriter();
			pw.write(str.toString());
		} catch (Exception ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}

	public ActionForward addAjaxComment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = MpUtil.getUserid(request);
			String str = "";
			if (!"1".equals(userid)) {
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String score = Encode.nullToBlank(request.getParameter("score"));
				request.setCharacterEncoding("UTF-8");
				String content = request.getParameter("content");
				if (content != null && content.trim().length() > 0) {
					String anonymouss = request.getParameter("anonymouss");
					String anonymous = "";
					if (anonymouss != null && anonymouss.trim().length() > 0) {
						anonymous = anonymouss;
					} else {
						anonymous = "0";
					}
					String questionid = Encode.nullToBlank(request.getParameter("questionid"));
					// 添加解题微课评价
					ZxHelpQuestionCommentManager manager = (ZxHelpQuestionCommentManager) getBean("zxHelpQuestionCommentManager");
					ZxHelpQuestionComment model = new ZxHelpQuestionComment();
					model.setQuestionid(Integer.valueOf(questionid));
					model.setContent(content);
					model.setScore(Integer.valueOf(score));
					model.setSysUserInfo(sysUserInfo);
					model.setUserip(IpUtil.getIpAddr(request));
					model.setCreatedate(DateTime.getDate());
					model.setAnonymous(anonymous);
					model.setStatus("1");
					manager.addZxHelpQuestionComment(model);
					str = "1";
				} else {
					str = "0";
				}
			}
			pw = response.getWriter();
			pw.write(str);
		} catch (Exception ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}

	/**
	 * ajax支付购买
	 */
	public ActionForward pay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "0";
		try {
			String userid = MpUtil.getUserid(request);
			String questionid = Encode.nullToBlank(request.getParameter("questionid"));
			String paypwd = Encode.nullToBlank(request.getParameter("paypwd"));
			// 验证支付密码
			SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
			if (MD5.getEncryptPwd(paypwd).equals(sysUserInfo.getPaypassword())) {
				ZxHelpQuestionManager zhqm = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				ZxHelpQuestion question = zhqm.getZxHelpQuestion(questionid);
				// 1、修改用户余额
				float sellprice = ArithUtil.round(question.getMoney() * Constants.BUY_QUESTION_DISCOUNT / 10);
				sysUserInfo.setMoney(sysUserInfo.getMoney() - sellprice);
				suim.updateSysUserInfo(sysUserInfo);
				// 2、记录交易详情
				SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
				SysUserMoney sysUserMoney = new SysUserMoney();
				String title = "购买了在线答疑提问《" + question.getTitle() + "》的解答答案";
				String descript = "购买了在线答疑提问《" + question.getTitle() + "》的解答答案";
				sysUserMoney.setTitle(title);
				sysUserMoney.setChangemoney(sellprice);
				sysUserMoney.setChangetype(-1);
				sysUserMoney.setLastmoney(sysUserInfo.getMoney());
				sysUserMoney.setUserid(Integer.valueOf(userid));
				sysUserMoney.setCreatedate(DateTime.getDate());
				sysUserMoney.setUserip(IpUtil.getIpAddr(request));
				sysUserMoney.setDescript(descript);
				summ.addSysUserMoney(sysUserMoney);
				// 记录购买数据
				ZxHelpQuestionBuyManager zhqbm = (ZxHelpQuestionBuyManager) getBean("zxHelpQuestionBuyManager");
				ZxHelpQuestionBuy zxHelpQuestionBuy = new ZxHelpQuestionBuy();
				zxHelpQuestionBuy.setQuestionid(question.getQuestionid());
				zxHelpQuestionBuy.setUserid(sysUserInfo.getUserid());
				zxHelpQuestionBuy.setPrice(question.getMoney());
				zxHelpQuestionBuy.setDiscount(Constants.BUY_QUESTION_DISCOUNT);
				zxHelpQuestionBuy.setSellprice(sellprice);
				zxHelpQuestionBuy.setCreatedate(DateTime.getDate());
				zxHelpQuestionBuy.setEnddate(getEnddate1());
				zhqbm.addZxHelpQuestionBuy(zxHelpQuestionBuy);
				// 记录销量
				question.setSellcount(question.getSellcount() + 1);
				zhqm.updateZxHelpQuestion(question);
				// ===============按比例分成给提问人和回答教师=====================
				// 启动线程进行分成
				ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
				ZxHelpOrder order = zhom.getZxHelpOrderByQuestionid(question.getQuestionid().toString());
				final Integer authorid = question.getSysUserInfo().getUserid();
				final Integer teacherid = order.getUserid();
				final ZxHelpQuestion zxHelpQuestion = question;
				final String userip = IpUtil.getIpAddr(request);
				Runnable runnable = new Runnable() {

					public void run() {
						shareMoney(authorid, teacherid, zxHelpQuestion, userip);
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
				result = "ok";
				addLog(request, descript, sysUserInfo);
			} else {
				// 支付密码输入错误
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

	/*
	 * 系统日志记录
	 */
	private void addLog(HttpServletRequest request, String descript, SysUserInfo sysUserInfo) {
		SysUserLogManager manager = (SysUserLogManager) getBean("sysUserLogManager");
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

	// 开始分成
	private void shareMoney(Integer authorid, Integer teacherid, ZxHelpQuestion zxHelpQuestion, String userip) {
		// 记录交易详情
		SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfo author = suim.getSysUserInfo(authorid);
		SysUserInfo teacher = suim.getSysUserInfo(teacherid);
		float sellprice = ArithUtil.round(zxHelpQuestion.getMoney() * Constants.BUY_QUESTION_DISCOUNT / 10);// 在此价格上进行分成
		// 提问作者
		author.setMoney(author.getMoney() + ArithUtil.round(sellprice * Constants.SELL_QUESTION_PROPORTION));
		suim.updateSysUserInfo(author);
		SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
		SysUserMoney sysUserMoney = new SysUserMoney();
		String title = "在线答疑提问《" + zxHelpQuestion.getTitle() + "》的解答答案卖出获得奖励";
		String descript = "在线答疑提问《" + zxHelpQuestion.getTitle() + "》的解答答案卖出获得奖励";
		sysUserMoney.setTitle(title);
		sysUserMoney.setChangemoney(ArithUtil.round(sellprice * Constants.SELL_QUESTION_PROPORTION));
		sysUserMoney.setChangetype(1);
		sysUserMoney.setLastmoney(author.getMoney());
		sysUserMoney.setUserid(author.getUserid());
		sysUserMoney.setCreatedate(DateTime.getDate());
		sysUserMoney.setUserip(userip);
		sysUserMoney.setDescript(descript);
		summ.addSysUserMoney(sysUserMoney);
		// 回答提问老师
		teacher.setMoney(teacher.getMoney() + ArithUtil.round(sellprice * Constants.SELL_QUESTION_ANSWER_PROPORTION));
		suim.updateSysUserInfo(teacher);
		sysUserMoney = new SysUserMoney();
		sysUserMoney.setTitle(title);
		sysUserMoney.setChangemoney(ArithUtil.round(sellprice * Constants.SELL_QUESTION_ANSWER_PROPORTION));
		sysUserMoney.setChangetype(1);
		sysUserMoney.setLastmoney(teacher.getMoney());
		sysUserMoney.setUserid(teacher.getUserid());
		sysUserMoney.setCreatedate(DateTime.getDate());
		sysUserMoney.setUserip(userip);
		sysUserMoney.setDescript(descript);
		summ.addSysUserMoney(sysUserMoney);
	}

	// 获取购买的在线答疑有效时间
	private String getEnddate1() {
		// SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// Calendar now = Calendar.getInstance();
		// now.setTime(new Date());
		// now.set(Calendar.DATE, now.get(Calendar.DATE) + Constants.BUY_END_DAY);
		// Date nowDate = now.getTime();
		// return format.format(nowDate);
		return "2088-08-08 20:00:00";
	}

	/**
	 * 投诉答疑
	 */
	public ActionForward complaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String orderid = Encode.nullToBlank(request.getParameter("orderid"));
				request.setAttribute("questionid", questionid);
				request.setAttribute("orderid", orderid);
				return mapping.findForward("complaint");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 投诉答疑
	 */
	public ActionForward commitComplaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String orderid = Encode.nullToBlank(request.getParameter("orderid"));
				String descript = Encode.nullToBlank(request.getParameter("descript"));
				ZxHelpQuestionManager zhqm = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
				ZxHelpQuestion question = zhqm.getZxHelpQuestion(questionid);
				question.setStatus("2");// 被投诉
				zhqm.updateZxHelpQuestion(question);
				ZxHelpQuestionComplaintManager zhqcm = (ZxHelpQuestionComplaintManager) getBean("zxHelpQuestionComplaintManager");
				ZxHelpQuestionComplaint complaint = new ZxHelpQuestionComplaint();
				ZxHelpOrder order = zhom.getZxHelpOrder(orderid);
				complaint.setQuestionid(Integer.valueOf(questionid));
				complaint.setOrderid(Integer.valueOf(orderid));
				complaint.setDescript(descript);
				complaint.setStatus("0");
				complaint.setCreatedate(DateTime.getDate());
				complaint.setUserid(Integer.valueOf(userid));
				complaint.setUserip(IpUtil.getIpAddr(request));
				complaint.setTeacherid(order.getUserid());
				zhqcm.addZxHelpQuestionComplaint(complaint);
				// 学生投诉老师，给老师发站内消息
				String msgtitle = "您回答的在线答疑提问被对方投诉了，请留意关注。";
				String msgcontent = "您回答的在线答疑提问《" + question.getTitle() + "》被对方投诉了，运维人员将根据具体实际情况进行处理，请留意关注。";
				SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
				SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
				SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, order.getUserid(), smim, smum);
				if (question.getMoney() > 0) {
					SysPaymentAccountManager spam = (SysPaymentAccountManager) getBean("sysPaymentAccountManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "fromuserid", "=", question.getSysUserInfo().getUserid());
					SearchCondition.addCondition(condition, "touserid", "=", order.getUserid());
					SearchCondition.addCondition(condition, "changetype", "=", 0);// 待完成交易
					SearchCondition.addCondition(condition, "outtype", "=", "1");// 关联答疑
					SearchCondition.addCondition(condition, "outobjid", "=", Integer.valueOf(questionid));
					List list = spam.getSysPaymentAccounts(condition, "", 0);
					if (list != null && list.size() > 0) {
						SysPaymentAccount account = (SysPaymentAccount) list.get(0);
						account.setStatus("3");// 被投诉
						spam.updateSysPaymentAccount(account);
						// 删除定时器答疑订单
						AutomationCache cache = AutomationCache.getInstance();
						cache.remove(account.getPaymentid());
					}
				}
				String url = "/weixinHelp.app?method=info&userid=" + DES.getEncryptPwd(userid) + "&questionid=" + questionid;
				response.sendRedirect(url);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 查看投诉内容
	 */
	public ActionForward viewQuestionComplaint(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String orderid = Encode.nullToBlank(request.getParameter("orderid"));
				request.setAttribute("questionid", questionid);
				request.setAttribute("orderid", orderid);
				ZxHelpQuestionComplaintManager manager = (ZxHelpQuestionComplaintManager) getBean("zxHelpQuestionComplaintManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "questionid", "=", Integer.valueOf(questionid));
				SearchCondition.addCondition(condition, "orderid", "=", Integer.valueOf(orderid));
				List list = manager.getZxHelpQuestionComplaints(condition, "", 0);
				if (list != null && list.size() > 0) {
					ZxHelpQuestionComplaint complaint = (ZxHelpQuestionComplaint) list.get(0);
					request.setAttribute("complaint", complaint);
					return mapping.findForward("viewcomplaint");
				} else {
					return info(mapping, form, request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 确认订单支付
	 */
	public ActionForward beforeConfirmOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String orderid = Encode.nullToBlank(request.getParameter("orderid"));
				request.setAttribute("questionid", questionid);
				request.setAttribute("orderid", orderid);
				ZxHelpQuestionManager zhqm = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				ZxHelpQuestion question = zhqm.getZxHelpQuestion(questionid);
				request.setAttribute("question", question);
				return mapping.findForward("confirmorder");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 确认订单支付
	 */
	public ActionForward confirmOrder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String orderid = Encode.nullToBlank(request.getParameter("orderid"));
				ZxHelpQuestionManager zhqm = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				ZxHelpQuestion question = zhqm.getZxHelpQuestion(questionid);
				if (!"3".equals(question.getReplystatus())) {
					question.setReplystatus("3");
					zhqm.updateZxHelpQuestion(question);
					ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
					ZxHelpOrder order = zhom.getZxHelpOrder(orderid);
					order.setPaystatus("1");
					zhom.updateZxHelpOrder(order);
					// 向老师付款
					SysPaymentAccountManager spam = (SysPaymentAccountManager) getBean("sysPaymentAccountManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "fromuserid", "=", Integer.valueOf(userid));
					SearchCondition.addCondition(condition, "touserid", "=", order.getUserid());
					SearchCondition.addCondition(condition, "outtype", "=", "1");// 关联答疑
					SearchCondition.addCondition(condition, "outobjid", "=", Integer.valueOf(questionid));
					List list = spam.getSysPaymentAccounts(condition, "", 0);
					if (list != null && list.size() > 0) {
						SysPaymentAccount account = (SysPaymentAccount) list.get(0);
						float addMoney = ArithUtil.round(account.getChangemoney() * Constants.ANSWER_QUESTION_PROPORTION);// 老师回答提问，平台抽2成
						SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
						SysUserInfo teacher = suim.getSysUserInfo(order.getUserid());
						teacher.setMoney(teacher.getMoney() + addMoney);
						suim.updateSysUserInfo(teacher);
						account.setChangetype(1);
						account.setHappendate(DateTime.getDate());
						account.setStatus("0");
						spam.updateSysPaymentAccount(account);
						// 记录消费详情
						SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
						SysUserMoney sysUserMoney = new SysUserMoney();
						String title = "回答在线答疑提问《" + question.getTitle() + "》获得报酬";
						String descript = "回答在线答疑提问《" + question.getTitle() + "》获得报酬";
						sysUserMoney.setTitle(title);
						sysUserMoney.setChangemoney(addMoney);
						sysUserMoney.setChangetype(1);
						sysUserMoney.setLastmoney(teacher.getMoney());
						sysUserMoney.setUserid(teacher.getUserid());
						sysUserMoney.setCreatedate(DateTime.getDate());
						sysUserMoney.setUserip(IpUtil.getIpAddr(request));
						sysUserMoney.setDescript(descript);
						summ.addSysUserMoney(sysUserMoney);
						// 给老师发站内短信
						String msgtitle = "您回答的在线答疑提问被对方确认付款了，请注意查收。";
						String msgcontent = "您回答的在线答疑提问《" + question.getTitle() + "》被对方确认付款，获得的报酬已转入到您的账户余额中，请注意查收。";
						SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
						SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
						SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, teacher.getUserid(), smim, smum);
						// 生成定时器，取消订单处理
						AutomationCache cache = AutomationCache.getInstance();
						cache.remove(account.getPaymentid());
					}
				}
				String url = "/weixinHelp.app?method=info&userid=" + DES.getEncryptPwd(userid) + "&questionid=" + questionid;
				response.sendRedirect(url);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 教师抢单回答问题 1验证教师是否已认证通过 2抢单前，需要通过线程安全处理，判断当前答疑提问确实没有被人抢单 3抢单成功，跳转到回答界面
	 */
	public ActionForward answer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				request.setAttribute("sysUserInfo", sysUserInfo);
				if (!"1".equals(sysUserInfo.getStatus())) {
					// 账号被禁用，重定向到首页
					String redirecturl = "/weixinAccountIndex.app?method=index&userid=" + DES.getEncryptPwd(userid);
					response.sendRedirect(redirecturl);
					return null;
				}
				if ("1".equals(sysUserInfo.getUsertype()) && "1".equals(sysUserInfo.getAuthentication())) {
					ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
					ZxHelpQuestionManager zhqm = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					// 线程安全同步块,此处建议需要多测试几遍【已经过高并发多线程测试没有问题，包括性能也能接受】
					synchronized (lock) {
						String questionid = Encode.nullToBlank(request.getParameter("questionid"));
						ZxHelpQuestion question = zhqm.getZxHelpQuestion(questionid);
						request.setAttribute("question", question);
						if ("0".equals(question.getReplystatus())) {
							Date date = format.parse(question.getEnddate());
							long recoverytime = date.getTime() - new Date().getTime();
							if (recoverytime > 0) {
								question.setReplystatus("1");
								zhqm.updateZxHelpQuestion(question);
								ZxHelpOrder order = new ZxHelpOrder();
								order.setQuestionid(question.getQuestionid());
								order.setCreatedate(DateTime.getDate());
								order.setEnddate(question.getEnddate());
								order.setUserip(IpUtil.getIpAddr(request));
								order.setMoney(question.getMoney());
								order.setStatus("1");
								order.setUserid(Integer.valueOf(userid));
								zhom.addZxHelpOrder(order);
								request.setAttribute("order", order);
								request.setAttribute("recoverytime", recoverytime);
								// 启动线程判断是否需要禁用当前账号
								final String teacherid = userid;
								final ZxHelpOrder zho = order;
								Runnable runnable = new Runnable() {

									public void run() {
										disableUser(teacherid, zho);
									}
								};
								Thread thread = new Thread(runnable);
								thread.start();
								// 抢单成功
								return mapping.findForward("answer");
							} else {
								// 回复失败--已超时
								return mapping.findForward("answerovertime");
							}
						} else {
							// 抢单失败，已被抢单
							return mapping.findForward("answerfailure");
						}
					}
				} else {
					String questionid = Encode.nullToBlank(request.getParameter("questionid"));
					request.setAttribute("questionid", questionid);
					// 通过分享此页面到电脑端进行认证操作
					String key = MD5.getEncryptPwd(System.currentTimeMillis() + "_" + sysUserInfo.getUserid());
					CacheUtil.putObject(key, sysUserInfo.getUserid(), 5 * 60);// 有效期5分钟
					request.setAttribute("key", key);
					return mapping.findForward("answertips");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	// 因为免费答疑没有定时器，所以老师在抢单时，需要判断是否有恶意抢单，最后已结束未回复答疑时间大于最后禁用时间，则为恶意抢单再次禁用
	// 规则：超过3次抢单成功未回复，禁用账号1周，每增加一次抢单成功但不回答问题，就多禁用1周
	private void disableUser(String teacherid, ZxHelpOrder zho) {
		ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(teacherid));
		SearchCondition.addCondition(condition, "enddate", "<", DateTime.getDate());// 已结束回答时间
		List list = zhom.getZxHelpOrders(condition, "createdate desc", 0);
		if (list != null && list.size() > 3) {
			ZxHelpOrder order = (ZxHelpOrder) list.get(0);
			String lastOrderCreatedate = order.getCreatedate();// 最后抢单时间
			// 获取用户最后禁用时间
			SysUserDisableManager sudm = (SysUserDisableManager) getBean("sysUserDisableManager");
			condition.clear();
			SearchCondition.addCondition(condition, "sysUserInfo.userid", "=", Integer.valueOf(teacherid));
			SearchCondition.addCondition(condition, "outlinktype", "=", "2");
			List lst = sudm.getSysUserDisables(condition, "endtime desc", 1);
			String disableEndtime = null;
			if (lst != null && lst.size() > 0) {
				SysUserDisable disable = (SysUserDisable) lst.get(0);
				disableEndtime = disable.getEndtime();
			}
			if (disableEndtime == null || DateTime.getCompareToDate(lastOrderCreatedate, disableEndtime) > 0) {
				int count = list.size();
				int days = (count - 3) * 7;// 禁用天数
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_MONTH, days);
				String enddate = format.format(calendar.getTime());
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo teacher = suim.getSysUserInfo(teacherid);
				SysUserDisable sysUserDisable = new SysUserDisable();
				sysUserDisable.setSysUserInfo(teacher);
				sysUserDisable.setStarttime(DateTime.getDate());
				sysUserDisable.setEndtime(enddate);
				sysUserDisable.setDescript("当前账号被禁用" + (count - 3) + "周，在线答疑抢单超过3次未回复，每增加一次抢单成功不回复问题，就多禁用1周。");
				sysUserDisable.setOutlinkid(order.getOrderid());
				sysUserDisable.setOutlinktype("2");
				sudm.addSysUserDisable(sysUserDisable);
				teacher.setStatus("2");
				suim.updateSysUserInfo(teacher);
			}
		}
	}

	/**
	 * 继续回答【已抢单成功】
	 */
	public ActionForward answerContinue(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
				ZxHelpQuestionManager zhqm = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String orderid = Encode.nullToBlank(request.getParameter("orderid"));
				ZxHelpQuestion question = zhqm.getZxHelpQuestion(questionid);
				ZxHelpOrder order = zhom.getZxHelpOrder(orderid);
				if ("2".equals(question.getReplystatus()) || "3".equals(question.getReplystatus()) || "2".equals(order.getStatus())) {
					return info(mapping, form, request, response);
				}
				Date date = format.parse(question.getEnddate());
				long recoverytime = date.getTime() - new Date().getTime();
				request.setAttribute("question", question);
				if (recoverytime > 0) {
					ZxHelpAnswerManager answermanager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
					ZxHelpAnswer answer = answermanager.getZxHelpAnswerByOrderid(orderid);
					List answerFileList = new ArrayList();
					if (answer != null) {
						ZxHelpFileManager filemanager = (ZxHelpFileManager) getBean("zxHelpFileManager");
						answerFileList = filemanager.getZxHelpFilesByAnswerid(answer.getAnswerid().toString());
					}
					request.setAttribute("answer", answer);
					request.setAttribute("answerFileList", answerFileList);
					request.setAttribute("order", order);
					request.setAttribute("recoverytime", recoverytime);
					// 回复
					return mapping.findForward("answer");
				} else {
					// 回复失败--已超时
					return mapping.findForward("answerovertime");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	public ActionForward beforeAddQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				// 通过userid获取openid，支付需要，如果没有，则退出登录
				SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				SysUserAttention sysUserAttention = suam.getSysUserAttentionByUserid(Integer.valueOf(userid));
				if (sysUserAttention == null) {
					// 用户没有对应的openid，需要重新注册
					return mapping.findForward("welcome");
				}
				request.setAttribute("openid", sysUserAttention.getOpenid());
				// 根据用户学段查询数据
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				EduGradeInfoManager grademanager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
				ZxHelpQuestionManager zhqm = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String xueduan = sysUserInfo.getXueduan();
				int xueduanid = 1;
				if ("1".equals(xueduan)) {
					xueduanid = 2;
				} else if ("2".equals(xueduan)) {
					xueduanid = 3;
				} else if ("3".equals(xueduan)) {
					xueduanid = 4;
				}
				List subjectlist = (List) CacheUtil.getObject("EduSubjectInfo_List_xueduanid_" + xueduanid);
				if (subjectlist == null || subjectlist.size() == 0) {
					subjectlist = grademanager.getAllSubjectByXueduanid(xueduanid);
					CacheUtil.putObject("EduSubjectInfo_List_xueduanid_" + xueduanid, subjectlist, 7 * 24 * 60 * 60);
				}
				List gradelist = new ArrayList();
				ZxHelpQuestion helpquestion = null;
				if (!"".equals(questionid)) {
					helpquestion = zhqm.getZxHelpQuestion(questionid);
					gradelist = grademanager.getAllGradeBySubjectAndUnitType(helpquestion.getSubjectid(), xueduanid + "");
				}
				// 获取用户当天支付密码输入错误次数
				SysPayPasswordManager sppm = (SysPayPasswordManager) getBean("sysPayPasswordManager");
				int errorcount = sppm.getCountSysPayPassword(userid);
				if (errorcount < Constants.PAYPASSWORD_ERROR_COUNT) {
					request.setAttribute("paypassword", "1");
				} else {
					request.setAttribute("paypassword", "0");
				}
				request.setAttribute("sysUserInfo", sysUserInfo);
				request.setAttribute("gradelist", gradelist);
				request.setAttribute("helpquestion", helpquestion);
				request.setAttribute("subjectlist", subjectlist);
				request.setAttribute("imgcount", Constants.UPLOAD_IMAGE_COUNT);
				request.setAttribute("audiocount", Constants.UPLOAD_AUDIO_COUNT);
				// 如果从微信在线支付扣款，在付款前没有保存答疑，所以无主键questionid，生成预付款id（用户id+时间点），后面支付回调时再改为questionid
				String createdate = DateTime.getDate();
				String payid = userid + ";" + createdate;
				request.setAttribute("createdate", createdate);
				request.setAttribute("payid", payid);
				return mapping.findForward("questionedit");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	// ajax获取年级信息
	public void getGradeByAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				EduGradeInfoManager grademanager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
				SysUserInfo user = usermanager.getSysUserInfo(userid);
				if (!"".equals(subjectid)) {
					int xueduan = Integer.parseInt(user.getXueduan()) + 1;
					List<EduGradeInfo> gradelist = grademanager.getAllGradeBySubjectAndUnitType(Integer.parseInt(subjectid), xueduan + "");
					StringBuffer result = new StringBuffer();
					for (EduGradeInfo grade : gradelist) {
						result.append("<a href=\"javascript:selectGrade(" + grade.getGradeid() + ",'" + grade.getGradename() + "'," + grade.getCxueduanid() + ")\" id=\"g" + grade.getGradeid() + "\" class=\"menu_div_a\">" + grade.getGradename() + "</a>");
					}
					response.setCharacterEncoding("UTF-8");
					response.getWriter().print(result.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveQuestionByAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String title = Encode.nullToBlank(request.getParameter("title"));
				String descript = Encode.nullToBlank(request.getParameter("descript"));
				// 前台进行过两次转码
				try {
					title = java.net.URLDecoder.decode(title, "UTF-8");// 反转码
					descript = java.net.URLDecoder.decode(descript, "UTF-8");// 反转码
				} catch (UnsupportedEncodingException e1) {
				}
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String subjectname = Encode.nullToBlank(request.getParameter("subjectname"));
				String gradeid = Encode.nullToBlank(request.getParameter("gradeid"));
				String gradename = Encode.nullToBlank(request.getParameter("gradename"));
				String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
				String times = Encode.nullToBlank(request.getParameter("times"));
				String money = Encode.nullToBlank(request.getParameter("money"));
				String status = Encode.nullToBlank(request.getParameter("status"));
				String createdate = Encode.nullToBlank(request.getParameter("createdate"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ZxHelpQuestionManager manager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo user = usermanager.getSysUserInfo(userid);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				if ("1".equals(Constants.IS_DEBUG)) {
					calendar.add(Calendar.MINUTE, Integer.parseInt(times) * 5);// 测试times*5分钟
				} else {
					calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(times));
				}
				String enddate = format.format(calendar.getTime());
				String ip = IpUtil.getIpAddr(request);
				Float changemoney = Float.parseFloat(money);
				ZxHelpQuestion question = new ZxHelpQuestion();
				question.setTitle(title);
				question.setDescript(descript);
				question.setCreatedate(createdate);// 从参数获取，微信支付通信接口需要调用
				question.setEnddate(enddate);
				question.setMoney(changemoney);
				question.setUserip(ip);
				question.setSysUserInfo(user);
				question.setSubjectid(Integer.parseInt(subjectid));
				question.setSubjectname(subjectname);
				question.setGradeid(Integer.parseInt(gradeid));
				question.setGradename(gradename);
				question.setCxueduanid(Integer.parseInt(cxueduanid));
				question.setStatus(status);
				question.setReplystatus("0");
				question.setHits(0);
				question.setSellcount(0);
				manager.addZxHelpQuestion(question);
				String imageServerIds = Encode.nullToBlank(request.getParameter("imageServerIds"));
				String audioServerIds = Encode.nullToBlank(request.getParameter("audioServerIds"));
				String audioTimes = Encode.nullToBlank(request.getParameter("audioTimes"));
				if (!"".equals(imageServerIds) || !"".equals(audioServerIds)) {
					ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
					String rootpath = request.getSession().getServletContext().getRealPath("/");
					String savepath = "ZxHelpFile/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
					// 启动线程上传附件
					final ZxHelpFileManager zhfm0 = zhfm;
					final Integer qid = question.getQuestionid();
					final String rootpath0 = rootpath;
					final String savepath0 = savepath;
					final String imageServerIds0 = imageServerIds;
					final String audioServerIds0 = audioServerIds;
					final String audioTimes0 = audioTimes;
					Runnable runnable = new Runnable() {

						public void run() {
							UploadFileUtil.saveZxHelpFile(zhfm0, "1", qid, rootpath0, savepath0, imageServerIds0, audioServerIds0, audioTimes0);
						}
					};
					Thread thread = new Thread(runnable);
					thread.start();
				}
				String result = "";
				if (question.getQuestionid() > 0) {
					result = question.getQuestionid().toString();
				}
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 微信在线支付提交答疑和附件
	 */
	public void saveQuestionByAjaxAndWxpay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String title = Encode.nullToBlank(request.getParameter("title"));
				String descript = Encode.nullToBlank(request.getParameter("descript"));
				// 前台进行过两次转码
				try {
					title = java.net.URLDecoder.decode(title, "UTF-8");// 反转码
					descript = java.net.URLDecoder.decode(descript, "UTF-8");// 反转码
				} catch (UnsupportedEncodingException e1) {
				}
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String subjectname = Encode.nullToBlank(request.getParameter("subjectname"));
				String gradeid = Encode.nullToBlank(request.getParameter("gradeid"));
				String gradename = Encode.nullToBlank(request.getParameter("gradename"));
				String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
				String times = Encode.nullToBlank(request.getParameter("times"));
				String money = Encode.nullToBlank(request.getParameter("money"));
				String status = Encode.nullToBlank(request.getParameter("status"));
				String createdate = Encode.nullToBlank(request.getParameter("createdate"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ZxHelpQuestionManager manager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo user = usermanager.getSysUserInfo(userid);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				if ("1".equals(Constants.IS_DEBUG)) {
					calendar.add(Calendar.MINUTE, Integer.parseInt(times) * 5);// 测试times*5分钟
				} else {
					calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(times));
				}
				String enddate = format.format(calendar.getTime());
				String ip = IpUtil.getIpAddr(request);
				Float changemoney = Float.parseFloat(money);
				ZxHelpQuestion question = new ZxHelpQuestion();
				question.setTitle(title);
				question.setDescript(descript);
				question.setCreatedate(createdate);// 从参数获取，微信支付通信接口需要调用
				question.setEnddate(enddate);
				question.setMoney(changemoney);
				question.setUserip(ip);
				question.setSysUserInfo(user);
				question.setSubjectid(Integer.parseInt(subjectid));
				question.setSubjectname(subjectname);
				question.setGradeid(Integer.parseInt(gradeid));
				question.setGradename(gradename);
				question.setCxueduanid(Integer.parseInt(cxueduanid));
				question.setStatus("0");// 此处默认状态为0，需要通过微信支付通信接口改变其状态
				question.setReplystatus("0");
				question.setHits(0);
				question.setSellcount(0);
				manager.addZxHelpQuestion(question);
				String imageServerIds = Encode.nullToBlank(request.getParameter("imageServerIds"));
				String audioServerIds = Encode.nullToBlank(request.getParameter("audioServerIds"));
				String audioTimes = Encode.nullToBlank(request.getParameter("audioTimes"));
				if (!"".equals(imageServerIds) || !"".equals(audioServerIds)) {
					ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
					String rootpath = request.getSession().getServletContext().getRealPath("/");
					String savepath = "ZxHelpFile/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
					// 启动线程上传附件
					final ZxHelpFileManager zhfm0 = zhfm;
					final Integer qid = question.getQuestionid();
					final String rootpath0 = rootpath;
					final String savepath0 = savepath;
					final String imageServerIds0 = imageServerIds;
					final String audioServerIds0 = audioServerIds;
					final String audioTimes0 = audioTimes;
					Runnable runnable = new Runnable() {

						public void run() {
							UploadFileUtil.saveZxHelpFile(zhfm0, "1", qid, rootpath0, savepath0, imageServerIds0, audioServerIds0, audioTimes0);
						}
					};
					Thread thread = new Thread(runnable);
					thread.start();
				}
				String result = "";
				if (question.getQuestionid() > 0) {
					result = question.getQuestionid().toString();
				}
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ajax支付答疑提问
	 */
	public ActionForward payAddQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "0";
		try {
			String userid = MpUtil.getUserid(request);
			String paypwd = Encode.nullToBlank(request.getParameter("paypwd"));
			// 验证支付密码
			SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
			if (MD5.getEncryptPwd(paypwd).equals(sysUserInfo.getPaypassword())) {
				result = "ok";
			} else {
				// 支付密码输入错误
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

	/**
	 * 余额支付提交答疑和附件
	 */
	public void saveQuestionByAjaxAndPay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String title = Encode.nullToBlank(request.getParameter("title"));
				String descript = Encode.nullToBlank(request.getParameter("descript"));
				// 前台进行过两次转码
				try {
					title = java.net.URLDecoder.decode(title, "UTF-8");// 反转码
					descript = java.net.URLDecoder.decode(descript, "UTF-8");// 反转码
				} catch (UnsupportedEncodingException e1) {
				}
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String subjectname = Encode.nullToBlank(request.getParameter("subjectname"));
				String gradeid = Encode.nullToBlank(request.getParameter("gradeid"));
				String gradename = Encode.nullToBlank(request.getParameter("gradename"));
				String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
				String times = Encode.nullToBlank(request.getParameter("times"));
				String money = Encode.nullToBlank(request.getParameter("money"));
				String status = Encode.nullToBlank(request.getParameter("status"));
				String createdate = Encode.nullToBlank(request.getParameter("createdate"));
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ZxHelpQuestionManager manager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo user = usermanager.getSysUserInfo(userid);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				if ("1".equals(Constants.IS_DEBUG)) {
					calendar.add(Calendar.MINUTE, Integer.parseInt(times) * 5);// 测试times*5分钟
				} else {
					calendar.add(Calendar.HOUR_OF_DAY, Integer.parseInt(times));
				}
				String enddate = format.format(calendar.getTime());
				String ip = IpUtil.getIpAddr(request);
				Float changemoney = Float.parseFloat(money);
				ZxHelpQuestion question = new ZxHelpQuestion();
				question.setTitle(title);
				question.setDescript(descript);
				question.setCreatedate(createdate);// 从参数获取，微信支付通信接口需要调用
				question.setEnddate(enddate);
				question.setMoney(changemoney);
				question.setUserip(ip);
				question.setSysUserInfo(user);
				question.setSubjectid(Integer.parseInt(subjectid));
				question.setSubjectname(subjectname);
				question.setGradeid(Integer.parseInt(gradeid));
				question.setGradename(gradename);
				question.setCxueduanid(Integer.parseInt(cxueduanid));
				question.setStatus("1");
				question.setReplystatus("0");
				question.setHits(0);
				question.setSellcount(0);
				manager.addZxHelpQuestion(question);
				// 1、修改用户余额
				float paymoney = Float.valueOf(money).floatValue();
				user.setMoney(user.getMoney() - paymoney);
				usermanager.updateSysUserInfo(user);
				// 2、记录交易详情
				SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
				SysUserMoney sysUserMoney = new SysUserMoney();
				String title0 = "在线答疑提问《" + question.getTitle() + "》支付";
				String descript0 = "在线答疑提问《" + question.getTitle() + "》支付";
				sysUserMoney.setTitle(title0);
				sysUserMoney.setChangemoney(paymoney);
				sysUserMoney.setChangetype(-1);
				sysUserMoney.setLastmoney(user.getMoney());
				sysUserMoney.setUserid(user.getUserid());
				sysUserMoney.setCreatedate(DateTime.getDate());
				sysUserMoney.setUserip(IpUtil.getIpAddr(request));
				sysUserMoney.setDescript(descript0);
				summ.addSysUserMoney(sysUserMoney);
				// 把钱放入第三方管理账户
				SysPaymentAccountManager spam = (SysPaymentAccountManager) getBean("sysPaymentAccountManager");
				SysPaymentAccount payment = new SysPaymentAccount();
				payment.setFromuserid(user.getUserid());
				payment.setFromuserip(IpUtil.getIpAddr(request));
				payment.setTouserid(0);
				payment.setChangemoney(paymoney);
				payment.setChangetype(0);
				payment.setOuttype("1");
				payment.setOutobjid(question.getQuestionid());
				payment.setCreatedate(DateTime.getDate());
				payment.setStatus("1");
				spam.addSysPaymentAccount(payment);
				// 生成定时器，如果答疑失效，钱自动规还账户
				AutomationCache cache = AutomationCache.getInstance();
				cache.put(payment.getPaymentid(), 1, enddate);
				String imageServerIds = Encode.nullToBlank(request.getParameter("imageServerIds"));
				String audioServerIds = Encode.nullToBlank(request.getParameter("audioServerIds"));
				String audioTimes = Encode.nullToBlank(request.getParameter("audioTimes"));
				if (!"".equals(imageServerIds) || !"".equals(audioServerIds)) {
					ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
					String rootpath = request.getSession().getServletContext().getRealPath("/");
					String savepath = "ZxHelpFile/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
					// 启动线程上传附件
					final ZxHelpFileManager zhfm0 = zhfm;
					final Integer qid = question.getQuestionid();
					final String rootpath0 = rootpath;
					final String savepath0 = savepath;
					final String imageServerIds0 = imageServerIds;
					final String audioServerIds0 = audioServerIds;
					final String audioTimes0 = audioTimes;
					Runnable runnable = new Runnable() {

						public void run() {
							UploadFileUtil.saveZxHelpFile(zhfm0, "1", qid, rootpath0, savepath0, imageServerIds0, audioServerIds0, audioTimes0);
						}
					};
					Thread thread = new Thread(runnable);
					thread.start();
				}
				String result = "";
				if (question.getQuestionid() > 0) {
					result = question.getQuestionid().toString();
				}
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAnswerByAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				String content = Encode.nullToBlank(request.getParameter("content"));
				// 前台进行过两次转码
				try {
					content = java.net.URLDecoder.decode(content, "UTF-8");// 反转码
				} catch (UnsupportedEncodingException e1) {
				}
				String answerid = Encode.nullToBlank(request.getParameter("answerid"));
				String orderid = Encode.nullToBlank(request.getParameter("orderid"));
				String questionid = Encode.nullToBlank(request.getParameter("questionid"));
				String status = Encode.nullToBlank(request.getParameter("status"));
				ZxHelpAnswerManager manager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
				ZxHelpOrderManager ordermanager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
				ZxHelpQuestionManager questionmanager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				ZxHelpAnswer answer = new ZxHelpAnswer();
				if (answerid == null || answerid.trim().length() <= 0) {
					answer.setOrderid(Integer.parseInt(orderid));
					answer.setContent(content);
					answer.setCreatedate(format.format(new Date()));
					answer.setStatus(status);
					answer.setUserip(IpUtil.getIpAddr(request));
					answer.setUserid(Integer.parseInt(userid));
					manager.addZxHelpAnswer(answer);
				} else {
					answer = manager.getZxHelpAnswer(answerid);
					answer.setContent(content);
					answer.setStatus(status);
					manager.updateZxHelpAnswer(answer);
				}
				String imageServerIds = Encode.nullToBlank(request.getParameter("imageServerIds"));
				String audioServerIds = Encode.nullToBlank(request.getParameter("audioServerIds"));
				String audioTimes = Encode.nullToBlank(request.getParameter("audioTimes"));
				if (!"".equals(imageServerIds) || !"".equals(audioServerIds)) {
					ZxHelpFileManager zhfm = (ZxHelpFileManager) getBean("zxHelpFileManager");
					String rootpath = request.getSession().getServletContext().getRealPath("/");
					String savepath = "ZxHelpFile/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
					// 启动线程上传附件
					final ZxHelpFileManager zhfm0 = zhfm;
					final Integer aid = answer.getAnswerid();
					final String rootpath0 = rootpath;
					final String savepath0 = savepath;
					final String imageServerIds0 = imageServerIds;
					final String audioServerIds0 = audioServerIds;
					final String audioTimes0 = audioTimes;
					Runnable runnable = new Runnable() {

						public void run() {
							UploadFileUtil.saveZxHelpFile(zhfm0, "2", aid, rootpath0, savepath0, imageServerIds0, audioServerIds0, audioTimes0);
						}
					};
					Thread thread = new Thread(runnable);
					thread.start();
				}
				if ("1".equals(status)) {
					ZxHelpOrder order = ordermanager.getZxHelpOrder(orderid);
					ZxHelpQuestion question = questionmanager.getZxHelpQuestion(questionid);
					if (question.getMoney() > 0) {
						question.setReplystatus("2");
					} else {
						question.setReplystatus("3");
						order.setPaystatus("1");
					}
					questionmanager.updateZxHelpQuestion(question);
					order.setStatus("2");
					order.setReplycreatedate(format.format(new Date()));
					ordermanager.updateZxHelpOrder(order);
					// 如果提问金额大于0，则需要把第三方支付收款账号改为老师
					if (question.getMoney() > 0) {
						// 设置收款账号为老师
						SysPaymentAccountManager spam = (SysPaymentAccountManager) getBean("sysPaymentAccountManager");
						List<SearchModel> condition = new ArrayList<SearchModel>();
						SearchCondition.addCondition(condition, "fromuserid", "=", question.getSysUserInfo().getUserid());
						SearchCondition.addCondition(condition, "changetype", "=", 0);// 待完成交易
						SearchCondition.addCondition(condition, "outtype", "=", "1");// 关联答疑
						SearchCondition.addCondition(condition, "outobjid", "=", Integer.valueOf(questionid));
						List list = spam.getSysPaymentAccounts(condition, "", 0);
						if (list != null && list.size() > 0) {
							SysPaymentAccount account = (SysPaymentAccount) list.get(0);
							account.setTouserid(Integer.valueOf(userid));
							account.setStatus("2");
							spam.updateSysPaymentAccount(account);
							// 生成定时器，如果答疑订单未被确认，钱自动打钱给老师
							AutomationCache cache = AutomationCache.getInstance();
							long liveTime = Constants.AUTO_PAY_DAY * 24 * 60 * 60 * 1000;
							if ("1".equals(Constants.IS_DEBUG)) {
								liveTime = 5 * 60 * 1000;// 测试时间5分钟
							}
							cache.put(account.getPaymentid(), 1, liveTime);
						}
					}
					SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
					SysUserInfo teacher = usermanager.getSysUserInfo(userid);
					// 给老师发站内短信
					String msgtitle = "您发布的在线答疑提问已被老师  [" + teacher.getUsername() + "]  回复，请注意查看。";
					String msgcontent = "您发布的在线答疑提问《" + question.getTitle() + "》已被老师  [" + teacher.getUsername() + "]  回复，请注意查看。";
					SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
					SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
					SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, question.getSysUserInfo().getUserid(), smim, smum);
				}
				response.setCharacterEncoding("utf-8");
				response.getWriter().print(answer.getAnswerid());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String fileid = Encode.nullToBlank(request.getParameter("fileid"));
			if (!"".equals(fileid)) {
				ZxHelpFileManager manager = (ZxHelpFileManager) getBean("zxHelpFileManager");
				manager.delZxHelpFile(fileid);
			}
			response.getWriter().print(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// =============================个人中心-我的答疑========================
	/**
	 * 我的答疑
	 */
	public ActionForward myQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				// 根据用户学段查询数据
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String usertype = "1";// 老师
				if ("2".equals(sysUserInfo.getUsertype())) {// 学生
					usertype = "2";
				}
				if ("1".equals(usertype)) {
					return teacherAnswer(mapping, form, request, response);
				} else {
					return studentQuestion(mapping, form, request, response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 我的答疑
	 */
	public ActionForward studentQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 查询条件年级（子学段）和学科从缓存中获取数据，因为不经常变动
				List cxueduanList = (List) CacheUtil.getObject("EduXueduanInfo_List_child");
				if (cxueduanList == null || cxueduanList.size() == 0) {
					EduXueduanInfoManager exim = (EduXueduanInfoManager) getBean("eduXueduanInfoManager");
					condition.clear();
					SearchCondition.addCondition(condition, "status", "=", "1");
					SearchCondition.addCondition(condition, "parentid", ">", 0);
					cxueduanList = exim.getEduXueduanInfos(condition, "parentid asc, orderindex asc", 0);
					CacheUtil.putObject("EduXueduanInfo_List_child", cxueduanList, 7 * 24 * 60 * 60);
				}
				List subjectList = (List) CacheUtil.getObject("EduSubjectInfo_List");
				if (subjectList == null || subjectList.size() == 0) {
					EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
					condition.clear();
					SearchCondition.addCondition(condition, "status", "=", "1");
					subjectList = esim.getEduSubjectInfos(condition, "orderindex asc", 0);
					CacheUtil.putObject("EduSubjectInfo_List", subjectList, 7 * 24 * 60 * 60);
				}
				// 根据已提问数据查询
				ZxHelpQuestionManager manager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				List allsubjectid = manager.getAllSubjectidByUserid(userid);
				List allcxueduanid = manager.getAllCxueduanidByUserid(userid);
				List userCxueduanList = new ArrayList();
				List userSubjectList = new ArrayList();
				EduXueduanInfo exi = null;
				for (int i = 0, size = cxueduanList.size(); i < size; i++) {
					exi = (EduXueduanInfo) cxueduanList.get(i);
					if (allcxueduanid.contains(exi.getXueduanid())) {
						userCxueduanList.add(exi);
					}
				}
				EduSubjectInfo esi = null;
				for (int i = 0, size = subjectList.size(); i < size; i++) {
					esi = (EduSubjectInfo) subjectList.get(i);
					if (allsubjectid.contains(esi.getSubjectid())) {
						userSubjectList.add(esi);
					}
				}
				request.setAttribute("cxueduanList", userCxueduanList);
				request.setAttribute("subjectList", userSubjectList);
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));
				String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String replystatus = Encode.nullToBlank(request.getParameter("replystatus"));// 0未答，1已答
				String money = Encode.nullToBlank(request.getParameter("money"));// 0免费，1付费
				request.setAttribute("keywords", keywords);
				request.setAttribute("cxueduanid", cxueduanid);
				request.setAttribute("subjectid", subjectid);
				request.setAttribute("replystatus", replystatus);
				request.setAttribute("money", money);
				request.setAttribute("questionid", Encode.nullToBlank(request.getParameter("questionid")));
				condition.clear();
				if (!"".equals(keywords)) {
					// 借status<>-1拼接字符串
					SearchCondition.addCondition(condition, "status", "<>", "-1' and (a.title like '%" + keywords + "%' or a.descript like '%" + keywords + "%') and 1='1");
				}
				if (!"".equals(cxueduanid)) {
					SearchCondition.addCondition(condition, "cxueduanid", "=", Integer.valueOf(cxueduanid));
				}
				if (!"".equals(subjectid)) {
					SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
				}
				if ("0".equals(replystatus)) {
					SearchCondition.addCondition(condition, "replystatus", "<>", "2");
					SearchCondition.addCondition(condition, "replystatus", "<>", "3");
				} else if ("1".equals(replystatus)) {
					SearchCondition.addCondition(condition, "status", "<>", "-1' and (a.replystatus='2' or a.replystatus='3') and 1='1");
				}
				if ("0".equals(money)) {
					SearchCondition.addCondition(condition, "money", "<=", 0);
				} else if ("1".equals(money)) {
					SearchCondition.addCondition(condition, "money", ">", 0);
				}
				String menutype = Encode.nullToBlank(request.getParameter("menutype"));
				menutype = "".equals(menutype) ? "1" : menutype;
				String pn = Encode.nullToBlank(request.getParameter("pagenum"));
				int pagenum = "".equals(pn) ? 1 : Integer.parseInt(pn);
				Integer pagesize = pagenum * 10;
				List list = null;
				if ("1".equals(menutype)) {
					SearchCondition.addCondition(condition, "sysUserInfo.userid", "=", Integer.valueOf(userid));
					list = manager.getZxHelpQuestions(condition, "createdate desc", pagesize);
				} else if ("2".equals(menutype)) {
					list = manager.alreadyBuyQuestion(condition, "", 0, pagesize, userid).getDatalist();
				} else if ("3".equals(menutype)) {
					list = manager.studentComplaintQuestion(condition, "", 0, pagesize, userid).getDatalist();
				}
				request.setAttribute("list", list);
				request.setAttribute("pagenum", pagenum);
				request.setAttribute("menutype", menutype);
				return mapping.findForward("studentquestion");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * ajax加载答疑
	 */
	public ActionForward getAjaxStudentQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = MpUtil.getUserid(request);
			String pagenum = Encode.nullToBlank(request.getParameter("pagenum"));
			String questionid = Encode.nullToBlank(request.getParameter("questionid"));
			if ("".equals(pagenum)) {
				pagenum = "1";
			}
			int startcount = Integer.valueOf(pagenum).intValue() * 10;
			String keywords = Encode.nullToBlank(request.getParameter("keywords"));
			String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
			String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
			String replystatus = Encode.nullToBlank(request.getParameter("replystatus"));// 0未答，1已答
			String money = Encode.nullToBlank(request.getParameter("money"));// 0免费，1付费
			// 如果是学生，根据学段查询数据，如果是老师，根据教学设置查询数据
			ZxHelpQuestionManager manager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			if (!"".equals(keywords)) {
				// 借status<>-1拼接字符串
				SearchCondition.addCondition(condition, "status", "<>", "-1' and (a.title like '%" + keywords + "%' or a.descript like '%" + keywords + "%') and 1='1");
			}
			if (!"".equals(cxueduanid)) {
				SearchCondition.addCondition(condition, "cxueduanid", "=", Integer.valueOf(cxueduanid));
			}
			if (!"".equals(subjectid)) {
				SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
			}
			if ("0".equals(replystatus)) {
				SearchCondition.addCondition(condition, "replystatus", "<>", "2");
				SearchCondition.addCondition(condition, "replystatus", "<>", "3");
			} else if ("1".equals(replystatus)) {
				SearchCondition.addCondition(condition, "status", "<>", "-1' and (a.replystatus='2' or a.replystatus='3') and 1='1");
			}
			if ("0".equals(money)) {
				SearchCondition.addCondition(condition, "money", "<=", 0);
			} else if ("1".equals(money)) {
				SearchCondition.addCondition(condition, "money", ">", 0);
			}
			String menutype = Encode.nullToBlank(request.getParameter("menutype"));
			menutype = "".equals(menutype) ? "1" : menutype;
			List list = null;
			if ("1".equals(menutype)) {
				SearchCondition.addCondition(condition, "sysUserInfo.userid", "=", Integer.valueOf(userid));
				list = manager.getPageZxHelpQuestions(condition, "createdate desc", startcount, 10).getDatalist();
			} else if ("2".equals(menutype)) {
				list = manager.alreadyBuyQuestion(condition, "", startcount, 10, userid).getDatalist();
			} else if ("3".equals(menutype)) {
				list = manager.studentComplaintQuestion(condition, "", startcount, 10, userid).getDatalist();
			}
			StringBuffer str = new StringBuffer();
			if (list != null && list.size() > 0) {
				ZxHelpQuestion question = null;
				SysUserInfo sui = null;
				String photo = null;
				for (int i = 0, size = list.size(); i < size; i++) {
					question = (ZxHelpQuestion) list.get(i);
					String md = "";
					if (question.getQuestionid().toString().equals(questionid)) {
						md = "id=\"md\"";
					}
					sui = question.getSysUserInfo();
					if (sui.getPhoto().startsWith("http://")) {
						photo = sui.getPhoto();
					} else {
						photo = "/upload/" + sui.getPhoto();
					}
					str.append("<a " + md + " href=\"/weixinHelp.app?method=info&questionid=").append(question.getQuestionid()).append("&userid=").append(DES.getEncryptPwd(userid)).append("\"><div class=\"answer_student\">");
					str.append("<div class=\"answer_student_top\"><img src=\"").append(photo).append("\" /><p>").append(sui.getUsername()).append(" • ").append(question.getSubjectname()).append(" - ").append(question.getGradename()).append("</p>");
					str.append("<p class=\"answer_student_top_p\">").append(TimeUtil.getTimeName(question.getCreatedate())).append("</p></div>");
					str.append("<div class=\"answer_student_font\">");
					if ("3".equals(question.getReplystatus())) {
						str.append("<p class=\"answer_student_font_p answer_student_font_p01\">已答</p>");
					} else {
						str.append("<p class=\"answer_student_font_p\">未答</p>");
					}
					str.append("<p class=\"answer_student_font_pright\">").append(question.getTitle()).append("</p></div><p class=\"answer_student_p\">").append(question.getDescript()).append("</p>");
					str.append("<p class=\"answer_student_p_teacher\">");
					if ("1".equals(question.getStatus())) {
						if ("0".equals(question.getReplystatus()) || "1".equals(question.getReplystatus())) {
							str.append("回答截止时间：");
							if (DateTime.getCompareToDate(question.getEnddate(), DateTime.getDate()) > 0) {
								str.append(question.getEnddate());
							} else {
								str.append("已结束");
							}
						} else if ("2".equals(question.getReplystatus())) {
							str.append("已回答，待确认支付。");
						}
						if (question.getMoney() > 0) {
							str.append("<font style=\"text-align:right;float:right;\">价格：").append(question.getMoney()).append("学币</font>");
						} else {
							str.append("<font style=\"text-align:right;float:right;color:#1fcc8a;\">免费</font>");
						}
					} else if ("2".equals(question.getStatus()) || "3".equals(question.getStatus())) {
						str.append("<font style=\"font-weight:bolder;\">已投诉</font>");
					}
					str.append("</p></div></a>");
				}
			}
			pw = response.getWriter();
			pw.write(str.toString());
		} catch (Exception ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}

	/**
	 * 我的回答
	 */
	public ActionForward teacherAnswer(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo teacherinfo = usermanager.getSysUserInfo(userid);
				// if("1".equals(teacherinfo.getAuthentication())){
				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 查询条件年级（子学段）和学科从缓存中获取数据，因为不经常变动
				List cxueduanList = (List) CacheUtil.getObject("EduXueduanInfo_List_child");
				if (cxueduanList == null || cxueduanList.size() == 0) {
					EduXueduanInfoManager exim = (EduXueduanInfoManager) getBean("eduXueduanInfoManager");
					condition.clear();
					SearchCondition.addCondition(condition, "status", "=", "1");
					SearchCondition.addCondition(condition, "parentid", ">", 0);
					cxueduanList = exim.getEduXueduanInfos(condition, "parentid asc, orderindex asc", 0);
					CacheUtil.putObject("EduXueduanInfo_List_child", cxueduanList, 7 * 24 * 60 * 60);
				}
				List subjectList = (List) CacheUtil.getObject("EduSubjectInfo_List");
				if (subjectList == null || subjectList.size() == 0) {
					EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
					condition.clear();
					SearchCondition.addCondition(condition, "status", "=", "1");
					subjectList = esim.getEduSubjectInfos(condition, "orderindex asc", 0);
					CacheUtil.putObject("EduSubjectInfo_List", subjectList, 7 * 24 * 60 * 60);
				}
				// 根据已提问数据查询
				ZxHelpAnswerManager manager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
				ZxHelpQuestionManager questionmanager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
				List allsubjectid = manager.getAllSubjectidByUserid(userid);
				List allcxueduanid = manager.getAllCxueduanidByUserid(userid);
				List userCxueduanList = new ArrayList();
				List userSubjectList = new ArrayList();
				EduXueduanInfo exi = null;
				for (int i = 0, size = cxueduanList.size(); i < size; i++) {
					exi = (EduXueduanInfo) cxueduanList.get(i);
					if (allcxueduanid.contains(exi.getXueduanid())) {
						userCxueduanList.add(exi);
					}
				}
				EduSubjectInfo esi = null;
				for (int i = 0, size = subjectList.size(); i < size; i++) {
					esi = (EduSubjectInfo) subjectList.get(i);
					if (allsubjectid.contains(esi.getSubjectid())) {
						userSubjectList.add(esi);
					}
				}
				request.setAttribute("cxueduanList", userCxueduanList);
				request.setAttribute("subjectList", userSubjectList);
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));
				String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String replystatus = Encode.nullToBlank(request.getParameter("replystatus"));// 0未答，1已答
				String money = Encode.nullToBlank(request.getParameter("money"));// 0免费，1付费
				request.setAttribute("keywords", keywords);
				request.setAttribute("cxueduanid", cxueduanid);
				request.setAttribute("subjectid", subjectid);
				request.setAttribute("replystatus", replystatus);
				request.setAttribute("money", money);
				request.setAttribute("questionid", Encode.nullToBlank(request.getParameter("questionid")));
				condition.clear();
				if (!"".equals(keywords)) {
					// 借status<>-1拼接字符串
					SearchCondition.addCondition(condition, "status", "<>", "-1' and (a.title like '%" + keywords + "%' or a.descript like '%" + keywords + "%') and 1='1");
				}
				if (!"".equals(cxueduanid)) {
					SearchCondition.addCondition(condition, "cxueduanid", "=", Integer.valueOf(cxueduanid));
				}
				if (!"".equals(subjectid)) {
					SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
				}
				if ("0".equals(replystatus)) {
					SearchCondition.addCondition(condition, "replystatus", "<>", "2");
					SearchCondition.addCondition(condition, "replystatus", "<>", "3");
				} else if ("1".equals(replystatus)) {
					SearchCondition.addCondition(condition, "status", "<>", "-1' and (a.replystatus='2' or a.replystatus='3') and 1='1");
				}
				if ("0".equals(money)) {
					SearchCondition.addCondition(condition, "money", "<=", 0);
				} else if ("1".equals(money)) {
					SearchCondition.addCondition(condition, "money", ">", 0);
				}
				String menutype = Encode.nullToBlank(request.getParameter("menutype"));
				menutype = "".equals(menutype) ? "1" : menutype;
				String pn = Encode.nullToBlank(request.getParameter("pagenum"));
				int pagenum = "".equals(pn) ? 1 : Integer.parseInt(pn);
				Integer pagesize = pagenum * 10;
				List list = null;
				if ("1".equals(menutype)) {
					list = questionmanager.myReply(condition, "", 0, pagesize, userid).getDatalist();
				} else if ("2".equals(menutype)) {
					list = questionmanager.alreadyBuyQuestion(condition, "", 0, pagesize, userid).getDatalist();
				} else if ("3".equals(menutype)) {
					list = questionmanager.teacherComplaintQuestion(condition, "", 0, pagesize, userid).getDatalist();
				}
				request.setAttribute("list", list);
				request.setAttribute("pagenum", pagenum);
				request.setAttribute("menutype", menutype);
				return mapping.findForward("teacherquestion");
				// }else{
				// request.setAttribute("sysUserInfo", teacherinfo);
				// return mapping.findForward("answertips");
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * ajax加载回复
	 */
	public ActionForward getAjaxTeacherQuestion(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = MpUtil.getUserid(request);
			String pagenum = Encode.nullToBlank(request.getParameter("pagenum"));
			String questionid = Encode.nullToBlank(request.getParameter("questionid"));
			if ("".equals(pagenum)) {
				pagenum = "1";
			}
			int startcount = Integer.valueOf(pagenum).intValue() * 10;
			String keywords = Encode.nullToBlank(request.getParameter("keywords"));
			String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
			String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
			String replystatus = Encode.nullToBlank(request.getParameter("replystatus"));// 0未答，1已答
			String money = Encode.nullToBlank(request.getParameter("money"));// 0免费，1付费
			// 如果是学生，根据学段查询数据，如果是老师，根据教学设置查询数据
			ZxHelpQuestionManager manager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			if (!"".equals(keywords)) {
				// 借status<>-1拼接字符串
				SearchCondition.addCondition(condition, "status", "<>", "-1' and (a.title like '%" + keywords + "%' or a.descript like '%" + keywords + "%') and 1='1");
			}
			if (!"".equals(cxueduanid)) {
				SearchCondition.addCondition(condition, "cxueduanid", "=", Integer.valueOf(cxueduanid));
			}
			if (!"".equals(subjectid)) {
				SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
			}
			if ("0".equals(replystatus)) {
				SearchCondition.addCondition(condition, "replystatus", "<>", "2");
				SearchCondition.addCondition(condition, "replystatus", "<>", "3");
			} else if ("1".equals(replystatus)) {
				SearchCondition.addCondition(condition, "status", "<>", "-1' and (a.replystatus='2' or a.replystatus='3') and 1='1");
			}
			if ("0".equals(money)) {
				SearchCondition.addCondition(condition, "money", "<=", 0);
			} else if ("1".equals(money)) {
				SearchCondition.addCondition(condition, "money", ">", 0);
			}
			String menutype = Encode.nullToBlank(request.getParameter("menutype"));
			menutype = "".equals(menutype) ? "1" : menutype;
			List list = null;
			if ("1".equals(menutype)) {
				list = manager.myReply(condition, "", startcount, 10, userid).getDatalist();
			} else if ("2".equals(menutype)) {
				list = manager.alreadyBuyQuestion(condition, "", startcount, 10, userid).getDatalist();
			} else if ("3".equals(menutype)) {
				list = manager.teacherComplaintQuestion(condition, "", startcount, 10, userid).getDatalist();
			}
			StringBuffer str = new StringBuffer();
			if (list != null && list.size() > 0) {
				ZxHelpQuestion question = null;
				SysUserInfo sui = null;
				String photo = null;
				for (int i = 0, size = list.size(); i < size; i++) {
					question = (ZxHelpQuestion) list.get(i);
					String md = "";
					if (question.getQuestionid().toString().equals(questionid)) {
						md = "id=\"md\"";
					}
					sui = question.getSysUserInfo();
					if (sui.getPhoto().startsWith("http://")) {
						photo = sui.getPhoto();
					} else {
						photo = "/upload/" + sui.getPhoto();
					}
					str.append("<a " + md + " href=\"/weixinHelp.app?method=info&questionid=").append(question.getQuestionid()).append("&userid=").append(DES.getEncryptPwd(userid)).append("\"><div class=\"answer_student\">");
					str.append("<div class=\"answer_student_top\"><img src=\"").append(photo).append("\" /><p>").append(sui.getUsername()).append(" • ").append(question.getSubjectname()).append(" - ").append(question.getGradename()).append("</p>");
					str.append("<p class=\"answer_student_top_p\">").append(TimeUtil.getTimeName(question.getCreatedate())).append("</p></div>");
					str.append("<div class=\"answer_student_font\">");
					if ("3".equals(question.getReplystatus())) {
						str.append("<p class=\"answer_student_font_p answer_student_font_p01\">已答</p>");
					} else {
						str.append("<p class=\"answer_student_font_p\">未答</p>");
					}
					str.append("<p class=\"answer_student_font_pright\">").append(question.getTitle()).append("</p></div><p class=\"answer_student_p\">").append(question.getDescript()).append("</p>");
					str.append("<p class=\"answer_student_p_teacher\">");
					if ("1".equals(question.getStatus())) {
						if ("0".equals(question.getReplystatus()) || "1".equals(question.getReplystatus())) {
							str.append("回答截止时间：");
							if (DateTime.getCompareToDate(question.getEnddate(), DateTime.getDate()) > 0) {
								str.append(question.getEnddate());
							} else {
								str.append("已结束");
							}
						} else if ("2".equals(question.getReplystatus())) {
							str.append("已回答，待确认支付。");
						}
						if (question.getMoney() > 0) {
							str.append("<font style=\"text-align:right;float:right;\">价格：").append(question.getMoney()).append("学币</font>");
						} else {
							str.append("<font style=\"text-align:right;float:right;color:#1fcc8a;\">免费</font>");
						}
					} else if ("2".equals(question.getStatus()) || "3".equals(question.getStatus())) {
						str.append("<font style=\"font-weight:bolder;\">已投诉</font>");
					}
					str.append("</p></div></a>");
				}
			}
			pw = response.getWriter();
			pw.write(str.toString());
		} catch (Exception ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}

	// 答疑规则内容说明
	public ActionForward questionDescript(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("questiondescript");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	public ActionForward answertips(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = usermanager.getSysUserInfo(userid);
				request.setAttribute("sysUserInfo", sysUserInfo);
				// 通过分享此页面到电脑端进行认证操作
				String key = MD5.getEncryptPwd(System.currentTimeMillis() + "_" + sysUserInfo.getUserid());
				CacheUtil.putObject(key, sysUserInfo.getUserid(), 5 * 60);// 有效期5分钟
				request.setAttribute("key", key);
				return mapping.findForward("answertips");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}
}