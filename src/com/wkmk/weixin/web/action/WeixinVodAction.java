package com.wkmk.weixin.web.action;

import java.io.IOException;
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
import org.json.JSONException;
import org.json.JSONObject;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
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
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentBuy;
import com.wkmk.tk.bo.TkBookContentComment;
import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkBookContentFilmAudition;
import com.wkmk.tk.bo.TkBookContentFilmAuditionWatch;
import com.wkmk.tk.bo.TkBookContentFilmWatch;
import com.wkmk.tk.bo.TkBookContentPrice;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookContentBuyManager;
import com.wkmk.tk.service.TkBookContentCommentManager;
import com.wkmk.tk.service.TkBookContentFilmAuditionManager;
import com.wkmk.tk.service.TkBookContentFilmAuditionWatchManager;
import com.wkmk.tk.service.TkBookContentFilmManager;
import com.wkmk.tk.service.TkBookContentFilmWatchManager;
import com.wkmk.tk.service.TkBookContentGoodfilmManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookContentPriceManager;
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
 * 解题微课
 * @version 1.0
 */
/**
 * <p>
 * Title: WeixinVodAction
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: 北京师科阳光信息技术有限公司
 * </p>
 * 
 * @author liud
 * @date 2016-11-22 上午10:40:54
 */
public class WeixinVodAction extends BaseAction {

	/**
	 * 精品微课-作业本列表
	 */
	public ActionForward goodFilmBookList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
				// 无需做级联查询修改，只需要把所有查询条件整合查询数据即可
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
				String version = Encode.nullToBlank(request.getParameter("version"));
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));
				TkBookContentGoodfilmManager tbcgm = (TkBookContentGoodfilmManager) getBean("tkBookContentGoodfilmManager");
				List bookidList = tbcgm.getAllBookid("1");
				if (bookidList != null && bookidList.size() > 0) {
					TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "vodstate", "<>", "0");
					if (!"".equals(subjectid)) {
						SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
					}
					String gradeid = null;
					if (!"".equals(cxueduanid)) {
						EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
						List gradeidList = (List) CacheUtil.getObject("EduGradeInfo_gradeid_List_cxueduan" + cxueduanid);
						if (gradeidList == null || gradeidList.size() == 0) {
							gradeidList = egim.getAllGradeidByCxueduanid(cxueduanid);
							CacheUtil.putObject("EduGradeInfo_gradeid_List_cxueduan" + cxueduanid, gradeidList, 24 * 60 * 60);
						}
						if (gradeidList != null && gradeidList.size() > 0) {
							StringBuffer str = new StringBuffer();
							for (int i = 0, size = gradeidList.size(); i < size; i++) {
								str.append(",").append(gradeidList.get(i));
							}
							gradeid = str.substring(1);
						}
					} else {
						// 根据当前用户学段查询
						String xueduan = sysUserInfo.getXueduan();
						String xueduanid = "2";
						if ("2".equals(xueduan)) {
							xueduanid = "3";
						} else if ("3".equals(xueduan)) {
							xueduanid = "4";
						}
						EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
						List gradeidList = (List) CacheUtil.getObject("EduGradeInfo_gradeid_List_xueduan" + xueduanid);
						if (gradeidList == null || gradeidList.size() == 0) {
							gradeidList = egim.getAllGradeidByXueduanid(xueduanid);
							CacheUtil.putObject("EduGradeInfo_gradeid_List_xueduan" + xueduanid, gradeidList, 24 * 60 * 60);
						}
						if (gradeidList != null && gradeidList.size() > 0) {
							StringBuffer str = new StringBuffer();
							for (int i = 0, size = gradeidList.size(); i < size; i++) {
								str.append(",").append(gradeidList.get(i));
							}
							gradeid = str.substring(1);
						}
					}
					if (!"".equals(version)) {
						SearchCondition.addCondition(condition, "version", "=", version);
					}
					if (!"".equals(keywords)) {
						SearchCondition.addCondition(condition, "title", "like", "%" + keywords + "%");
					}
					StringBuffer str = new StringBuffer();
					for (int i = 0, size = bookidList.size(); i < size; i++) {
						str.append(",").append(bookidList.get(i));
					}
					if (gradeid != null && !"".equals(gradeid)) {
						SearchCondition.addCondition(condition, "status", "=", "1' and a.gradeid in (" + gradeid + ") and a.bookid in (" + str.substring(1) + ") and 1='1");
					} else {
						SearchCondition.addCondition(condition, "status", "=", "1' and a.bookid in (" + str.substring(1) + ") and 1='1");
					}
					List bookList = manager.getTkBookInfos(condition, "bookno asc", 0);
					request.setAttribute("bookList", bookList);
				}
				request.setAttribute("xueduan", sysUserInfo.getXueduan());
				request.setAttribute("subjectid", subjectid);
				request.setAttribute("cxueduanid", cxueduanid);
				request.setAttribute("version", version);
				request.setAttribute("keywords", keywords);
				// 获取学科列表
				List subjectList = (List) CacheUtil.getObject("EduSubjectInfo_List");
				if (subjectList == null || subjectList.size() == 0) {
					EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "status", "=", "1");
					subjectList = esim.getEduSubjectInfos(condition, "orderindex asc", 0);
					CacheUtil.putObject("EduSubjectInfo_List", subjectList, 7 * 24 * 60 * 60);
				}
				request.setAttribute("subjectList", subjectList);
				return mapping.findForward("gookfilmbooklist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("welcome");
	}

	/**
	 * 作业本列表
	 */
	public ActionForward booklist(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			// ==========通过微信自动回复消息跳转过来==========
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			// 如果用户已经注册并绑定账号，再次点击注册时直接跳转进入，避免出现绑定多个账号的bug
			SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
			SysUserAttention attention = suam.getSysUserAttentionByOpenid(openid);
			if (attention != null && attention.getUserid().intValue() > 1) {
				userid = attention.getUserid().toString();
			}
			if (!"1".equals(userid)) {
				// 根据用户学段查询数据
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String usertype = "1";// 老师
				if ("2".equals(sysUserInfo.getUsertype())) {// 学生
					usertype = "2";
				}
				request.setAttribute("usertype", usertype);
				// 无需做级联查询修改，只需要把所有查询条件整合查询数据即可
				String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
				String cxueduanid = Encode.nullToBlank(request.getParameter("cxueduanid"));
				String version = Encode.nullToBlank(request.getParameter("version"));
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));
				// 解题微课作业本，必须是已经有发布的解题微课视频才显示，否则点击进入全是没视频文件的，体验不好
				TkBookContentFilmManager tbcfm = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
				List bookidList = tbcfm.getAllBookid("1");// 已审核解题微课
				if (bookidList != null && bookidList.size() > 0) {
					TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "vodstate", "<>", "0");
					if (!"".equals(subjectid)) {
						SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
					}
					String gradeid = null;
					if (!"".equals(cxueduanid)) {
						EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
						List gradeidList = (List) CacheUtil.getObject("EduGradeInfo_gradeid_List_cxueduan" + cxueduanid);
						if (gradeidList == null || gradeidList.size() == 0) {
							gradeidList = egim.getAllGradeidByCxueduanid(cxueduanid);
							CacheUtil.putObject("EduGradeInfo_gradeid_List_cxueduan" + cxueduanid, gradeidList, 24 * 60 * 60);
						}
						if (gradeidList != null && gradeidList.size() > 0) {
							StringBuffer str = new StringBuffer();
							for (int i = 0, size = gradeidList.size(); i < size; i++) {
								str.append(",").append(gradeidList.get(i));
							}
							gradeid = str.substring(1);
						}
					} else {
						// 根据当前用户学段查询
						String xueduan = sysUserInfo.getXueduan();
						String xueduanid = "2";
						if ("2".equals(xueduan)) {
							xueduanid = "3";
						} else if ("3".equals(xueduan)) {
							xueduanid = "4";
						}
						EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
						List gradeidList = (List) CacheUtil.getObject("EduGradeInfo_gradeid_List_xueduan" + xueduanid);
						if (gradeidList == null || gradeidList.size() == 0) {
							gradeidList = egim.getAllGradeidByXueduanid(xueduanid);
							CacheUtil.putObject("EduGradeInfo_gradeid_List_xueduan" + xueduanid, gradeidList, 24 * 60 * 60);
						}
						if (gradeidList != null && gradeidList.size() > 0) {
							StringBuffer str = new StringBuffer();
							for (int i = 0, size = gradeidList.size(); i < size; i++) {
								str.append(",").append(gradeidList.get(i));
							}
							gradeid = str.substring(1);
						}
					}
					if (!"".equals(version)) {
						SearchCondition.addCondition(condition, "version", "=", version);
					}
					if (!"".equals(keywords)) {
						SearchCondition.addCondition(condition, "title", "like", "%" + keywords + "%");
					}
					StringBuffer str = new StringBuffer();
					for (int i = 0, size = bookidList.size(); i < size; i++) {
						str.append(",").append(bookidList.get(i));
					}
					if (gradeid != null && !"".equals(gradeid)) {
						SearchCondition.addCondition(condition, "status", "=", "1' and a.gradeid in (" + gradeid + ") and a.bookid in (" + str.substring(1) + ") and 1='1");
					} else {
						SearchCondition.addCondition(condition, "status", "=", "1' and a.bookid in (" + str.substring(1) + ") and 1='1");
					}
					List bookList = manager.getTkBookInfos(condition, "bookno asc", 0);
					request.setAttribute("bookList", bookList);
				}
				request.setAttribute("xueduan", sysUserInfo.getXueduan());
				request.setAttribute("subjectid", subjectid);
				request.setAttribute("cxueduanid", cxueduanid);
				request.setAttribute("version", version);
				request.setAttribute("keywords", keywords);
				// 获取学科列表
				List subjectList = (List) CacheUtil.getObject("EduSubjectInfo_List");
				if (subjectList == null || subjectList.size() == 0) {
					EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "status", "=", "1");
					subjectList = esim.getEduSubjectInfos(condition, "orderindex asc", 0);
					CacheUtil.putObject("EduSubjectInfo_List", subjectList, 7 * 24 * 60 * 60);
				}
				request.setAttribute("subjectList", subjectList);
				return mapping.findForward("booklist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("welcome");
	}

	/**
	 * 作业本解题微课列表
	 */
	public ActionForward bookContentFilmList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
				String searchtype = Encode.nullToBlank(request.getParameter("searchtype"));// 默认0全部，1已购买，2未购买
				if ("".equals(searchtype)) {
					searchtype = "0";
				}
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));// 如果搜索，直接显示具体作业，无需显示章
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkBookInfo tkBookInfo = tbim.getTkBookInfo(bookid);
				tkBookInfo.setFlags(Constants.getCodeTypevalue("version", tkBookInfo.getVersion()));
				request.setAttribute("tkBookInfo", tkBookInfo);
				request.setAttribute("searchtype", searchtype);
				request.setAttribute("keywords", keywords);
				request.setAttribute("bookid", bookid);
				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 获取已购买的所有作业id
				TkBookContentBuyManager tbcbm = (TkBookContentBuyManager) getBean("tkBookContentBuyManager");
				condition.clear();
				SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
				List contentbuyList = tbcbm.getTkBookContentBuys(condition, "", 0);
				List buycontentidList1 = new ArrayList();// 购买解题微课
				List buycontentidList2 = new ArrayList();// 购买举一反三微课
				if (contentbuyList != null && contentbuyList.size() > 0) {
					TkBookContentBuy buy = null;
					for (int i = 0, size = contentbuyList.size(); i < size; i++) {
						buy = (TkBookContentBuy) contentbuyList.get(i);
						if ("1".equals(buy.getType())) {
							buycontentidList1.add(buy.getContentid());
						} else if ("2".equals(buy.getType())) {
							buycontentidList2.add(buy.getContentid());
						}
					}
				}
				request.setAttribute("buycontentidList1", buycontentidList1);
				request.setAttribute("buycontentidList2", buycontentidList2);
				TkBookContentFilmManager tbcfm = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
				if (keywords != null && !"".equals(keywords)) {
					List bookcontentList = tbcfm.getTkBookContentsByBookContentFilm(bookid, tkBookInfo.getVodstate(), keywords);
					request.setAttribute("bookcontentList", bookcontentList);
				} else {
					// 可通过缓存处理，有效期1天
					List bookcontentList = CacheUtil.getList("TkBookContentFilm_TkBookContentList_" + bookid);
					if (bookcontentList == null) {
						bookcontentList = tbcfm.getTkBookContentsByBookContentFilm(bookid, tkBookInfo.getVodstate(), null);
						CacheUtil.putList("TkBookContentFilm_TkBookContentList_" + bookid, bookcontentList, 1);// 1小时
					}
					// 获取当前作业本所有父栏目
					List parentList = CacheUtil.getList("TkBookContent_parentlist_" + bookid);
					if (parentList == null) {
						TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
						condition.clear();
						SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
						SearchCondition.addCondition(condition, "parentno", "=", "0000");
						parentList = tbcm.getTkBookContents(condition, "contentno asc", 0);
						CacheUtil.putList("TkBookContent_parentlist_" + bookid, parentList, 24);
					}
					List parentnoList = new ArrayList();
					Map childMap = new HashMap();
					TkBookContent tbc = null;
					for (int i = 0, size = bookcontentList.size(); i < size; i++) {
						tbc = (TkBookContent) bookcontentList.get(i);
						if (childMap.get(tbc.getParentno()) == null) {
							List lst = new ArrayList();
							lst.add(tbc);
							childMap.put(tbc.getParentno(), lst);
						} else {
							List lst = (List) childMap.get(tbc.getParentno());
							lst.add(tbc);
							childMap.put(tbc.getParentno(), lst);
						}
						if ("0".equals(searchtype)) {
							if (!parentnoList.contains(tbc.getParentno())) {
								parentnoList.add(tbc.getParentno());
							}
						} else if ("1".equals(searchtype)) {// 已购买
							if (!parentnoList.contains(tbc.getParentno())) {
								if (buycontentidList1.contains(tbc.getBookcontentid()) || buycontentidList2.contains(tbc.getBookcontentid())) {
									parentnoList.add(tbc.getParentno());
								}
							}
						} else if ("2".equals(searchtype)) {// 未购买
							if (!parentnoList.contains(tbc.getParentno())) {
								if (!buycontentidList1.contains(tbc.getBookcontentid()) && !buycontentidList2.contains(tbc.getBookcontentid())) {
									parentnoList.add(tbc.getParentno());
								}
							}
						}
					}
					request.setAttribute("bookcontentList", bookcontentList);
					request.setAttribute("parentList", parentList);
					request.setAttribute("parentnoList", parentnoList);
					request.setAttribute("childMap", childMap);
				}
				return mapping.findForward("bookcontentfilmlist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 精品微课-作业本解题微课列表
	 */
	public ActionForward bookContentGoodFilmList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
				String searchtype = Encode.nullToBlank(request.getParameter("searchtype"));// 默认0全部，1已购买，2未购买
				if ("".equals(searchtype)) {
					searchtype = "0";
				}
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));// 如果搜索，直接显示具体作业，无需显示章
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkBookInfo tkBookInfo = tbim.getTkBookInfo(bookid);
				tkBookInfo.setFlags(Constants.getCodeTypevalue("version", tkBookInfo.getVersion()));
				request.setAttribute("tkBookInfo", tkBookInfo);
				request.setAttribute("searchtype", searchtype);
				request.setAttribute("keywords", keywords);
				request.setAttribute("bookid", bookid);
				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 获取已购买的所有作业id
				TkBookContentBuyManager tbcbm = (TkBookContentBuyManager) getBean("tkBookContentBuyManager");
				condition.clear();
				SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
				List contentbuyList = tbcbm.getTkBookContentBuys(condition, "", 0);
				List buycontentidList1 = new ArrayList();// 购买解题微课
				List buycontentidList2 = new ArrayList();// 购买举一反三微课
				if (contentbuyList != null && contentbuyList.size() > 0) {
					TkBookContentBuy buy = null;
					for (int i = 0, size = contentbuyList.size(); i < size; i++) {
						buy = (TkBookContentBuy) contentbuyList.get(i);
						if ("1".equals(buy.getType())) {
							buycontentidList1.add(buy.getContentid());
						} else if ("2".equals(buy.getType())) {
							buycontentidList2.add(buy.getContentid());
						}
					}
				}
				request.setAttribute("buycontentidList1", buycontentidList1);
				request.setAttribute("buycontentidList2", buycontentidList2);
				TkBookContentGoodfilmManager tbcgm = (TkBookContentGoodfilmManager) getBean("tkBookContentGoodfilmManager");
				if (keywords != null && !"".equals(keywords)) {
					List bookcontentList = tbcgm.getTkBookContentsByBookContentGoodFilm(bookid, tkBookInfo.getVodstate(), keywords);
					request.setAttribute("bookcontentList", bookcontentList);
				} else {
					// 可通过缓存处理，有效期1小时
					List bookcontentList = CacheUtil.getList("TkBookContentGoodfilm_TkBookContentList_" + bookid);
					if (bookcontentList == null) {
						bookcontentList = tbcgm.getTkBookContentsByBookContentGoodFilm(bookid, tkBookInfo.getVodstate(), null);
						CacheUtil.putList("TkBookContentGoodfilm_TkBookContentList_" + bookid, bookcontentList, 1);
					}
					// 获取当前作业本所有父栏目
					List parentList = CacheUtil.getList("TkBookContent_parentlist_" + bookid);
					if (parentList == null) {
						TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
						condition.clear();
						SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
						SearchCondition.addCondition(condition, "parentno", "=", "0000");
						parentList = tbcm.getTkBookContents(condition, "contentno asc", 0);
						CacheUtil.putList("TkBookContent_parentlist_" + bookid, parentList, 24);
					}
					List parentnoList = new ArrayList();
					Map childMap = new HashMap();
					TkBookContent tbc = null;
					for (int i = 0, size = bookcontentList.size(); i < size; i++) {
						tbc = (TkBookContent) bookcontentList.get(i);
						if (childMap.get(tbc.getParentno()) == null) {
							List lst = new ArrayList();
							lst.add(tbc);
							childMap.put(tbc.getParentno(), lst);
						} else {
							List lst = (List) childMap.get(tbc.getParentno());
							lst.add(tbc);
							childMap.put(tbc.getParentno(), lst);
						}
						if ("0".equals(searchtype)) {
							if (!parentnoList.contains(tbc.getParentno())) {
								parentnoList.add(tbc.getParentno());
							}
						} else if ("1".equals(searchtype)) {// 已购买
							if (!parentnoList.contains(tbc.getParentno())) {
								if (buycontentidList1.contains(tbc.getBookcontentid()) || buycontentidList2.contains(tbc.getBookcontentid())) {
									parentnoList.add(tbc.getParentno());
								}
							}
						} else if ("2".equals(searchtype)) {// 未购买
							if (!parentnoList.contains(tbc.getParentno())) {
								if (!buycontentidList1.contains(tbc.getBookcontentid()) && !buycontentidList2.contains(tbc.getBookcontentid())) {
									parentnoList.add(tbc.getParentno());
								}
							}
						}
					}
					request.setAttribute("bookcontentList", bookcontentList);
					request.setAttribute("parentList", parentList);
					request.setAttribute("parentnoList", parentnoList);
					request.setAttribute("childMap", childMap);
				}
				return mapping.findForward("bookcontentgoodfilmlist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 购买解题微课
	 */
	public ActionForward buy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
				request.setAttribute("sysUserInfo", sysUserInfo);
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String searchtype = Encode.nullToBlank(request.getParameter("searchtype"));
				if ("".equals(searchtype)) {
					searchtype = "0";
				}
				request.setAttribute("bookid", bookid);
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("searchtype", searchtype);
				// 通过userid获取openid，支付需要，如果没有，则退出登录
				SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				SysUserAttention sysUserAttention = suam.getSysUserAttentionByUserid(Integer.valueOf(userid));
				if (sysUserAttention == null) {
					// 用户没有对应的openid，需要重新注册
					return mapping.findForward("welcome");
				}
				request.setAttribute("openid", sysUserAttention.getOpenid());
				String buytype = Encode.nullToBlank(request.getParameter("buytype"));// 1购买解题微课，2购买举一反三微课，如果为空，则需要根据用户查询
				// 判断当前用户所能购买的微课，如果没有，则直接跳转到微课列表页
				TkBookContentBuyManager tbcbm = (TkBookContentBuyManager) getBean("tkBookContentBuyManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "contentid", "=", Integer.valueOf(bookcontentid));
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
				List list = tbcbm.getTkBookContentBuys(condition, "", 0);
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkBookInfo tkBookInfo = tbim.getTkBookInfo(bookid);
				if ("".equals(buytype)) {
					if ("1".equals(tkBookInfo.getVodstate())) {
						buytype = "1";
						if (list != null && list.size() > 0) {
							// 已购买，直接跳转到播放界面
							String url = "/weixinVod.app?method=play&userid=" + DES.getEncryptPwd(userid) + "&bookid=" + bookid + "&bookcontentid=" + bookcontentid + "&type=1&searchtype=" + searchtype;
							response.sendRedirect(url);
							return null;
						}
					} else if ("2".equals(tkBookInfo.getVodstate())) {
						buytype = "2";
						if (list != null && list.size() > 0) {
							// 已购买，直接跳转到播放界面
							String url = "/weixinVod.app?method=play&userid=" + DES.getEncryptPwd(userid) + "&bookid=" + bookid + "&bookcontentid=" + bookcontentid + "&type=2&searchtype=" + searchtype;
							response.sendRedirect(url);
							return null;
						}
					} else if ("3".equals(tkBookInfo.getVodstate())) {
						if (list != null && list.size() > 1) {
							// 已购买，直接跳转到播放界面
							String url = "/weixinVod.app?method=play&userid=" + DES.getEncryptPwd(userid) + "&bookid=" + bookid + "&bookcontentid=" + bookcontentid + "&type=1&searchtype=" + searchtype;
							response.sendRedirect(url);
							return null;
						}
						// 如果buytype=1，并且vodstate=3，才有“解题微课”和“举一反三”微课切换购买
						buytype = "1";// 默认购买解题微课
						if (list != null && list.size() > 0) {
							TkBookContentBuy contentBuy = (TkBookContentBuy) list.get(0);
							if ("1".equals(contentBuy.getType())) {
								buytype = "2";
							}
						}
					}
				}
				// 获取作业本、作业、定价基本信息
				tkBookInfo.setFlags(Constants.getCodeTypevalue("version", tkBookInfo.getVersion()));
				TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent tkBookContent = tbcm.getTkBookContent(bookcontentid);
				TkBookContent parent = tbcm.getTkBookContentByContentno(tkBookContent.getParentno(), bookid);
				request.setAttribute("tkBookInfo", tkBookInfo);
				request.setAttribute("tkBookContent", tkBookContent);
				request.setAttribute("parent", parent);
				request.setAttribute("buytype", buytype);
				TkBookContentPriceManager tbcpm = (TkBookContentPriceManager) getBean("tkBookContentPriceManager");
				condition.clear();
				SearchCondition.addCondition(condition, "type", "=", buytype);
				SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
				List priceList = tbcpm.getTkBookContentPrices(condition, "", 1);
				if (priceList == null || priceList.size() == 0) {
					request.setAttribute("promptinfo", "当前作业解题微课未定价！");
					return mapping.findForward("scantips");
				}
				TkBookContentPrice contentPrice = (TkBookContentPrice) priceList.get(0);
				request.setAttribute("contentPrice", contentPrice);
				// 获取当前作业微课列表
				List filmList = CacheUtil.getList("TkBookContentFilm_List_" + bookcontentid + "_" + buytype);
				if (filmList == null) {
					TkBookContentFilmManager manager = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
					condition.clear();
					SearchCondition.addCondition(condition, "type", "=", buytype);
					SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
					SearchCondition.addCondition(condition, "status", "=", "1");
					filmList = manager.getTkBookContentFilms(condition, "orderindex asc, createdate asc", 0);
					CacheUtil.putList("TkBookContentFilm_List_" + bookcontentid + "_" + buytype, filmList, 24 * 7);// 缓存一周
				}
				request.setAttribute("filmList", filmList);
				// 获取当前作业评价
				TkBookContentCommentManager tbccm = (TkBookContentCommentManager) getBean("tkBookContentCommentManager");
				List scoreList = CacheUtil.getList("TkBookContentComment_List_" + bookcontentid + "_" + buytype);
				if (scoreList == null) {
					scoreList = tbccm.getTkBookContentCommentScore(bookcontentid, buytype);
					CacheUtil.putList("TkBookContentComment_List_" + bookcontentid + "_" + buytype, scoreList, 1);
				}
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
				// 获取用户当天支付密码输入错误次数
				SysPayPasswordManager sppm = (SysPayPasswordManager) getBean("sysPayPasswordManager");
				int errorcount = sppm.getCountSysPayPassword(userid);
				if (errorcount < Constants.PAYPASSWORD_ERROR_COUNT) {
					request.setAttribute("paypassword", "1");
				} else {
					request.setAttribute("paypassword", "0");
				}
				return mapping.findForward("buy");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 
	 * 方法描述：微课播放
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-28 下午3:38:44
	 */
	public ActionForward play(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				TkBookInfo tkBookInfo = tbim.getTkBookInfo(bookid);// 作业本
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String searchtype = Encode.nullToBlank(request.getParameter("searchtype"));
				String type = Encode.nullToBlank(request.getParameter("type"));// 1解题微课，2举一反三微课
				request.setAttribute("bookid", bookid);
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("searchtype", searchtype);
				request.setAttribute("type", type);
				// 播放前再做一次判断
				TkBookContentBuyManager tbcbm = (TkBookContentBuyManager) getBean("tkBookContentBuyManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "contentid", "=", Integer.valueOf(bookcontentid));
				SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
				SearchCondition.addCondition(condition, "type", "=", type);
				SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
				List list = tbcbm.getTkBookContentBuys(condition, "", 1);
				if (list == null || list.size() == 0) {
					// 重新跳转到购买界面
					String url = "/weixinVod.app?method=buy&userid=" + DES.getEncryptPwd(userid) + "&bookid=" + bookid + "&bookcontentid=" + bookcontentid + "&searchtype=0";
					response.sendRedirect(url);
					return null;
				} else {
					TkBookContentBuy contentBuy = (TkBookContentBuy) list.get(0);
					request.setAttribute("contentBuy", contentBuy);
				}
				// 查询播放界面需要的数据
				TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
				VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
				TkBookContent tkBookContent = tbcm.getTkBookContent(bookcontentid);// 作业内容
				request.setAttribute("tkBookContent", tkBookContent);
				// 查询当前作业内容的父作业
				String parentTitle = "";
				if (!tkBookContent.getParentno().equals("0000")) {
					TkBookContent tkBookContent2 = tbcm.getTkBookContentByContentno(tkBookContent.getParentno(), bookid);
					parentTitle = tkBookContent2.getTitle();
				}
				if (parentTitle != null && parentTitle.trim().length() > 0) {
					parentTitle = parentTitle + "_";
				}
				// 查询当前作业和作业本关联的解题微课
				TkBookContentFilmManager manager = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
				List<SearchModel> condition1 = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition1, "bookid", "=", Integer.valueOf(bookid));
				SearchCondition.addCondition(condition1, "bookcontentid", "=", Integer.valueOf(bookcontentid));
				SearchCondition.addCondition(condition1, "type", "=", type);
				List tkBookContentFilms = manager.getTkBookContentFilms(condition1, "a.orderindex", 0);// 作业本微课
				request.setAttribute("num", tkBookContentFilms.size());
				request.setAttribute("tkBookContentFilms", tkBookContentFilms);
				// 微课关联的视频
				TkBookContentFilm bookContentFilm = new TkBookContentFilm();
				if (tkBookContentFilms != null && tkBookContentFilms.size() > 0) {
					bookContentFilm = (TkBookContentFilm) tkBookContentFilms.get(0);
					// 更新播放次数
					Integer hits = bookContentFilm.getHits();
					bookContentFilm.setHits(hits + 1);
					manager.updateTkBookContentFilm(bookContentFilm);
					// 关联的微课第一个视频视频id
					Integer filmid = bookContentFilm.getFilmid();
					// 根据id去查询微课视频
					VwhFilmInfo vwhFilmInfo = vfim.getVwhFilmInfo(filmid);
					String title = vwhFilmInfo.getTitle();// 视频标题
					String keywords = "本视频属于作业本：" + tkBookInfo.getTitle() + "，作业：" + parentTitle + tkBookContent.getTitle();// 视频简介
					request.setAttribute("title", keywords);
					request.setAttribute("keywords", keywords);
					request.setAttribute("hits", bookContentFilm.getHits());
					// 播放第一个视频
					preview(mapping, form, request, response, filmid);
					request.setAttribute("firstFilmid", filmid);
					for (int i = 0; i < tkBookContentFilms.size(); i++) {
						bookContentFilm = (TkBookContentFilm) tkBookContentFilms.get(i);
						Integer filmid_ = bookContentFilm.getFilmid();// 关联的微课视频id
						// 视频地址
						VwhFilmPixManager pixmanager = (VwhFilmPixManager) getBean("vwhFilmPixManager");
						List vwhFilmPixsByFilmid = pixmanager.getVwhFilmPixsByFilmid(filmid_);
						VwhFilmPix filmpix = (VwhFilmPix) vwhFilmPixsByFilmid.get(0);
						bookContentFilm.setFlago(filmpix.getImgpath());
						bookContentFilm.setFlags(filmpix.getFlvpath());
					}
				}
				// 微课评价
				TkBookContentCommentManager tbccm = (TkBookContentCommentManager) getBean("tkBookContentCommentManager");
				SysUserInfoManager sysm = (SysUserInfoManager) getBean("sysUserInfoManager");
				List<SearchModel> condition2 = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition2, "status", "=", "1");
				SearchCondition.addCondition(condition2, "type", "=", type);
				SearchCondition.addCondition(condition2, "bookcontentid", "=", Integer.valueOf(bookcontentid));
				List tkBookContentComments = tbccm.getTkBookContentComments(condition2, "a.createdate desc", 0);
				Integer goodNum = 0;// 好评数
				Integer mediumNum = 0;// 中评数
				Integer poorNum = 0;// 差评数
				Integer evaluationNum = 0;// 评价总数
				evaluationNum = tkBookContentComments.size();
				if (tkBookContentComments != null && tkBookContentComments.size() > 0) {
					for (int i = 0; i < tkBookContentComments.size(); i++) {
						TkBookContentComment tbcc = (TkBookContentComment) tkBookContentComments.get(i);
						SysUserInfo userInfo = sysm.getSysUserInfo(tbcc.getSysUserInfo().getUserid());
						Integer score = tbcc.getScore();
						if (score == 0 || score == 1) {
							poorNum = poorNum + 1;
						} else if (score == 2 || score == 3) {
							mediumNum = mediumNum + 1;
						} else if (score == 4 || score == 5) {
							goodNum = goodNum + 1;
						}
						String anonymous = tbcc.getAnonymous();
						if (anonymous.equals("0")) {
							tbcc.setFlago(userInfo.getUsername());
						} else {
							tbcc.setFlago(userInfo.getUsername().substring(0, 1) + "***");
						}
					}
				}
				request.setAttribute("goodNum", goodNum);
				request.setAttribute("mediumNum", mediumNum);
				request.setAttribute("poorNum", poorNum);
				request.setAttribute("evaluationNum", evaluationNum);
				request.setAttribute("tkBookContentCommentScore", tkBookContentComments);
				// 增加播放记录
				TkBookContentFilmWatchManager tbcfw = (TkBookContentFilmWatchManager) getBean("tkBookContentFilmWatchManager");
				TkBookContentFilmWatch tkcf = new TkBookContentFilmWatch();
				tkcf.setUserid(Integer.valueOf(userid));
				tkcf.setContentfilmid(bookContentFilm.getFid());
				tkcf.setCreatedate(DateTime.getDate());
				tkcf.setUserip(IpUtil.getIpAddr(request));
				tbcfw.addTkBookContentFilmWatch(tkcf);
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
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-28 下午3:44:39
	 */
	public void preview(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, Integer filmid) {
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
			SearchCondition.addCondition(condition, "filmid", "=", filminfo.getFilmid());
			List<VwhFilmPix> pixlist = pixmanager.getVwhFilmPixs(condition, "orderindex", 0);
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
	 * 
	 * 方法描述：ajax,解题微课评价
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-28 下午3:46:03
	 */
	public ActionForward addAjaxComment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = MpUtil.getUserid(request);
			String str = "";
			if (!"1".equals(userid)) {
				// 根据用户学段查询数据
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String usertype = "1";// 老师
				if ("2".equals(sysUserInfo.getUsertype())) {// 学生
					usertype = "2";
				}
				request.setAttribute("usertype", usertype);
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
					String bookid = Encode.nullToBlank(request.getParameter("bookid"));
					String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
					String searchtype = Encode.nullToBlank(request.getParameter("searchtype"));
					String type = Encode.nullToBlank(request.getParameter("type"));// 1解题微课，2举一反三微课
					request.setAttribute("bookid", bookid);
					request.setAttribute("bookcontentid", bookcontentid);
					request.setAttribute("searchtype", searchtype);
					request.setAttribute("type", type);
					TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
					TkBookContent tkBookContent = tbcm.getTkBookContent(bookcontentid);
					request.setAttribute("tkBookContent", tkBookContent);
					// 添加解题微课评价
					TkBookContentCommentManager tbccm = (TkBookContentCommentManager) getBean("tkBookContentCommentManager");
					TkBookContentComment model = new TkBookContentComment();
					model.setBookcontentid(Integer.valueOf(bookcontentid));
					model.setContent(content);
					model.setScore(Integer.valueOf(score));
					model.setSysUserInfo(sysUserInfo);
					model.setUserip(IpUtil.getIpAddr(request));
					model.setCreatedate(DateTime.getDate());
					model.setAnonymous(anonymous);
					model.setType(type);
					model.setStatus("1");
					tbccm.addTkBookContentComment(model);
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
	 * 
	 * 方法描述：ajax 刷新微课播放次数
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-28 下午3:47:42
	 */
	public ActionForward getAjaxCommentHits(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = MpUtil.getUserid(request);
			String str = "";
			if (!"1".equals(userid)) {
				// 根据用户学段查询数据
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
				String usertype = "1";// 老师
				if ("2".equals(sysUserInfo.getUsertype())) {// 学生
					usertype = "2";
				}
				request.setAttribute("usertype", usertype);
				String bookid = Encode.nullToBlank(request.getParameter("bookid"));
				String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
				String searchtype = Encode.nullToBlank(request.getParameter("searchtype"));
				String type = Encode.nullToBlank(request.getParameter("type"));// 1解题微课，2举一反三微课
				request.setAttribute("bookid", bookid);
				request.setAttribute("bookcontentid", bookcontentid);
				request.setAttribute("searchtype", searchtype);
				request.setAttribute("type", type);
				String filmid = Encode.nullToBlank(request.getParameter("filmid"));
				VwhFilmInfoManager filmmanager = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
				VwhFilmInfo vwhFilmInfo = filmmanager.getVwhFilmInfo(filmid);
				Integer hits = vwhFilmInfo.getHits();
				vwhFilmInfo.setHits(hits + 1);
				filmmanager.updateVwhFilmInfo(vwhFilmInfo);
				TkBookContentFilmManager tbcfm = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
				SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
				SearchCondition.addCondition(condition, "filmid", "=", Integer.valueOf(filmid));
				SearchCondition.addCondition(condition, "type", "=", type);
				List list = tbcfm.getTkBookContentFilms(condition, "orderindex", 1);
				if (list != null && list.size() > 0) {
					TkBookContentFilm model = (TkBookContentFilm) list.get(0);
					Integer hits2 = model.getHits();
					model.setHits(hits2 + 1);
					tbcfm.updateTkBookContentFilm(model);
					Integer hits3 = model.getHits();
					str = hits3 + "";
					TkBookContentFilmWatchManager tbcfw = (TkBookContentFilmWatchManager) getBean("tkBookContentFilmWatchManager");
					TkBookContentFilmWatch tkcf = new TkBookContentFilmWatch();
					tkcf.setUserid(Integer.valueOf(userid));
					tkcf.setContentfilmid(model.getFid());
					tkcf.setCreatedate(DateTime.getDate());
					tkcf.setUserip(IpUtil.getIpAddr(request));
					tbcfw.addTkBookContentFilmWatch(tkcf);
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
	 * ajax加载微课评价
	 */
	public ActionForward getAjaxComment(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			// String userid = MpUtil.getUserid(request);
			String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
			String type = Encode.nullToBlank(request.getParameter("type"));// 1解题微课评论，2举一反三微课评论
			String commenttype = Encode.nullToBlank(request.getParameter("commenttype"));// 1全部，2好评，3中评，4差评
			String pagenum = Encode.nullToBlank(request.getParameter("pagenum"));
			if ("".equals(pagenum)) {
				pagenum = "0";
			}
			int startcount = Integer.valueOf(pagenum).intValue() * 10;
			TkBookContentCommentManager manager = (TkBookContentCommentManager) getBean("tkBookContentCommentManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
			SearchCondition.addCondition(condition, "type", "=", type);
			SearchCondition.addCondition(condition, "status", "=", "1");
			if ("2".equals(commenttype)) {
				SearchCondition.addCondition(condition, "score", ">", 3);
			} else if ("3".equals(commenttype)) {
				SearchCondition.addCondition(condition, "score", ">", 1);
				SearchCondition.addCondition(condition, "score", "<=", 3);
			} else if ("4".equals(commenttype)) {
				SearchCondition.addCondition(condition, "score", "<=", 1);
			}
			PageList pageList = manager.getPageTkBookContentComments(condition, "createdate desc", startcount, 10);
			List list = pageList.getDatalist();
			StringBuffer str = new StringBuffer();
			if (list != null && list.size() > 0) {
				TkBookContentComment comment = null;
				SysUserInfo sysUserInfo = null;
				String username = null;
				String photo = null;
				for (int i = 0, size = list.size(); i < size; i++) {
					comment = (TkBookContentComment) list.get(i);
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
					str.append("<div  class=\"Contentbox02_main_font\"><div  class=\"Contentbox02_main_font_img\">");
					for (int m = 1; m <= 5; m++) {
						if (comment.getScore() >= m) {
							str.append("<img src=\"/weixin/images/icon16.png\" />");
						} else {
							str.append("<img src=\"/weixin/images/icon17.png\" />");
						}
					}
					str.append("</div><p>").append(comment.getContent()).append("</p></div>");
					if (comment.getReplycontent() != null && !"".equals(comment.getReplycontent())) {
						str.append(" <div class=\"Contentbox02_main_font_hf\"><div class=\"Contentbox02_main_font_hf_p\"><p class=\"Contentbox02_main_font_hf_p01\">龙门回复：</p>");
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

	/**
	 * 
	 * 方法描述：ajax,刷新评论总数、好评数
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-25 上午9:15:50
	 */
	public ActionForward getAjaxCommentNum(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = MpUtil.getUserid(request);
			String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
			String type = Encode.nullToBlank(request.getParameter("type"));// 1解题微课评论，2举一反三微课评论
			// 微课评价
			TkBookContentCommentManager tbccm = (TkBookContentCommentManager) getBean("tkBookContentCommentManager");
			SysUserInfoManager sysm = (SysUserInfoManager) getBean("sysUserInfoManager");
			List<SearchModel> condition2 = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition2, "status", "=", "1");
			SearchCondition.addCondition(condition2, "type", "=", type);
			SearchCondition.addCondition(condition2, "bookcontentid", "=", Integer.valueOf(bookcontentid));
			List tkBookContentComments = tbccm.getTkBookContentComments(condition2, "a.createdate desc", 0);
			Integer goodNum = 0;// 好评数
			Integer mediumNum = 0;// 中评数
			Integer poorNum = 0;// 差评数
			Integer evaluationNum = 0;// 评价总数
			evaluationNum = tkBookContentComments.size();
			if (tkBookContentComments != null && tkBookContentComments.size() > 0) {
				for (int i = 0; i < tkBookContentComments.size(); i++) {
					TkBookContentComment tbcc = (TkBookContentComment) tkBookContentComments.get(i);
					SysUserInfo userInfo = sysm.getSysUserInfo(tbcc.getSysUserInfo().getUserid());
					Integer score = tbcc.getScore();
					if (score == 0 || score == 1) {
						poorNum = poorNum + 1;
					} else if (score == 2 || score == 3) {
						mediumNum = mediumNum + 1;
					} else if (score == 4 || score == 5) {
						goodNum = goodNum + 1;
					}
					String anonymous = tbcc.getAnonymous();
					if (anonymous.equals("0")) {
						tbcc.setFlago(userInfo.getUsername());
					} else {
						tbcc.setFlago(userInfo.getUsername().substring(0, 1) + "***");
					}
				}
			}
			JSONObject jo = new JSONObject();
			jo.put("goodNum", goodNum);
			jo.put("mediumNum", mediumNum);
			jo.put("poorNum", poorNum);
			jo.put("evaluationNum", evaluationNum);
			pw = response.getWriter();
			pw.write(jo.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
	 * ajax支付购买
	 */
	public ActionForward pay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "0";
		try {
			String userid = MpUtil.getUserid(request);
			String priceid = Encode.nullToBlank(request.getParameter("priceid"));
			String paypwd = Encode.nullToBlank(request.getParameter("paypwd"));
			TkBookContentPriceManager tbcpm = (TkBookContentPriceManager) getBean("tkBookContentPriceManager");
			TkBookContentPrice contentPrice = tbcpm.getTkBookContentPrice(priceid);
			// 验证支付密码
			SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
			if (MD5.getEncryptPwd(paypwd).equals(sysUserInfo.getPaypassword()) || contentPrice.getSellprice() == 0) {
				TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContent tkBookContent = tbcm.getTkBookContent(contentPrice.getBookcontentid());
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkBookInfo tkBookInfo = tbim.getTkBookInfo(tkBookContent.getBookid());
				// 1、修改用户余额
				sysUserInfo.setMoney(sysUserInfo.getMoney() - contentPrice.getSellprice());
				suim.updateSysUserInfo(sysUserInfo);
				// 2、记录交易详情
				SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
				SysUserMoney sysUserMoney = new SysUserMoney();
				String title = "购买了《" + tkBookContent.getTitle() + "》的";
				String descript = "购买了《" + tkBookInfo.getTitle() + "(" + Constants.getCodeTypevalue("version", tkBookInfo.getVersion()) + ")-" + tkBookContent.getTitle() + "》的";
				if ("1".equals(contentPrice.getType())) {
					title = title + "【解题微课】";
					descript = descript + "【解题微课】";
				} else if ("2".equals(contentPrice.getType())) {
					title = title + "【举一反三微课】";
					descript = descript + "【举一反三微课】";
				}
				sysUserMoney.setTitle(title);
				sysUserMoney.setChangemoney(contentPrice.getSellprice());
				sysUserMoney.setChangetype(-1);
				sysUserMoney.setLastmoney(sysUserInfo.getMoney());
				sysUserMoney.setUserid(Integer.valueOf(userid));
				sysUserMoney.setCreatedate(DateTime.getDate());
				sysUserMoney.setUserip(IpUtil.getIpAddr(request));
				sysUserMoney.setDescript(descript);
				summ.addSysUserMoney(sysUserMoney);
				// 记录购买数据
				TkBookContentBuyManager tbcbm = (TkBookContentBuyManager) getBean("tkBookContentBuyManager");
				TkBookContentBuy tkBookContentBuy = new TkBookContentBuy();
				tkBookContentBuy.setContentid(tkBookContent.getBookcontentid());
				tkBookContentBuy.setBookid(tkBookContent.getBookid());
				tkBookContentBuy.setUserid(Integer.valueOf(userid));
				tkBookContentBuy.setType(contentPrice.getType());
				tkBookContentBuy.setPrice(contentPrice.getPrice());
				tkBookContentBuy.setDiscount(contentPrice.getDiscount());
				tkBookContentBuy.setSellprice(contentPrice.getSellprice());
				tkBookContentBuy.setCreatedate(DateTime.getDate());
				tkBookContentBuy.setEnddate(getEnddate());
				tbcbm.addTkBookContentBuy(tkBookContentBuy);
				// 记录微课销量
				contentPrice.setSellcount(contentPrice.getSellcount() + 1);
				tbcpm.updateTkBookContentPrice(contentPrice);
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

	// 获取购买的解题微课有效时间
	private String getEnddate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		now.set(Calendar.DATE, now.get(Calendar.DATE) + Constants.BUY_END_DAY);
		Date nowDate = now.getTime();
		return format.format(nowDate);
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

	// 首页试听微课
	public void getFilmAuditionByAjax(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = Encode.nullToBlank(request.getParameter("userid"));
			String num = Encode.nullToBlank(request.getParameter("pagenum"));
			String title = Encode.nullToBlank(request.getParameter("keywords"));
			int pagesize = 10;
			int pagenum = Integer.parseInt(num);
			int pagecount = pagesize * pagenum;
			TkBookContentFilmAuditionManager manager = (TkBookContentFilmAuditionManager) getBean("tkBookContentFilmAuditionManager");
			List filmlist = null;
			if ("0".equals(num) && "".equals(title)) {// 缓存第一页
				filmlist = (List) CacheUtil.getObject("TkBookContentFilmAudition_List_page_0");
				if (filmlist == null || filmlist.size() == 0) {
					filmlist = manager.getPageFilms(title, "", pagecount, pagesize).getDatalist();
					CacheUtil.putObject("TkBookContentFilmAudition_List_page_0", filmlist, 7 * 24 * 3600);// 默认缓存7天，直到后台数据改变
				}
			} else {
				filmlist = manager.getPageFilms(title, "", pagecount, pagesize).getDatalist();
			}
			StringBuffer result = new StringBuffer();
			for (Object o : filmlist) {
				Object[] obj = (Object[]) o;
				VwhFilmInfo film = (VwhFilmInfo) obj[0];
				result.append("<a href=\"/weixinVod.app?method=bookContentFilmPlay&userid=" + userid + "&filmid=" + obj[1] + "&auditionid=" + obj[2] + "\"><div class=\"listen_main_01\">" + "<img src=\"/upload/" + film.getSketch() + "\" onerror=\"javascript:this.src='/weixin/images/img07.png'\"/>" + "<p>" + film.getTitle() + "</p>" + "</div></a>");
			}
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 试听专享微课列表
	public ActionForward getFilmAuditionList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				request.setAttribute("keywords", Encode.nullToBlank(request.getParameter("keywords")));
				return mapping.findForward("auditionlist");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	// 试听微课播放
	public ActionForward bookContentFilmPlay(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				// 作业本微课id
				String auditionid = Encode.nullToBlank(request.getParameter("auditionid"));
				TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
				TkBookContentFilmManager fmanager = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
				VwhFilmInfoManager filmmanager = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
				VwhFilmPixManager pixmanager = (VwhFilmPixManager) getBean("vwhFilmPixManager");
				VwhComputerInfoManager computermanager = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
				TkBookContentManager bookcontentmanager = (TkBookContentManager) getBean("tkBookContentManager");
				TkBookContentFilmAuditionManager manager = (TkBookContentFilmAuditionManager) getBean("tkBookContentFilmAuditionManager");
				TkBookContentFilmAudition audition = manager.getTkBookContentFilmAudition(auditionid);
				audition.setHits(audition.getHits() + 1);
				manager.updateTkBookContentFilmAudition(audition);
				TkBookContentFilm bookcontentfilm = fmanager.getTkBookContentFilm(audition.getContentfilmid());
				VwhFilmInfo filminfo = filmmanager.getVwhFilmInfo(bookcontentfilm.getFilmid());
				VwhComputerInfo computerinfo = computermanager.getVwhComputerInfo(filminfo.getComputerid());
				TkBookContent bookContent = bookcontentmanager.getTkBookContent(bookcontentfilm.getBookcontentid());
				TkBookInfo bookInfo = tbim.getTkBookInfo(bookContent.getBookid());
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "filmid", "=", filminfo.getFilmid());
				List<VwhFilmPix> pixlist = pixmanager.getVwhFilmPixs(condition, "orderindex", 0);
				VwhFilmPix filmpix = pixlist.get(0);
				request.setAttribute("audition", audition);
				request.setAttribute("bookcontentfilm", bookcontentfilm);
				request.setAttribute("book", bookInfo);
				request.setAttribute("bookcontent", bookContent);
				request.setAttribute("filminfo", filminfo);
				request.setAttribute("computerinfo", computerinfo);
				request.setAttribute("filmpix", filmpix);
				TkBookContentFilmAuditionWatchManager tbcfawm = (TkBookContentFilmAuditionWatchManager) getBean("tkBookContentFilmAuditionWatchManager");
				TkBookContentFilmAuditionWatch auditionWatch = new TkBookContentFilmAuditionWatch();
				auditionWatch.setUserid(Integer.valueOf(userid));
				auditionWatch.setAuditionid(Integer.valueOf(auditionid));
				auditionWatch.setCreatedate(DateTime.getDate());
				auditionWatch.setUserip(IpUtil.getIpAddr(request));
				tbcfawm.addTkBookContentFilmAuditionWatch(auditionWatch);
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				request.setAttribute("usertype", usermanager.getSysUserInfo(userid).getUsertype());
				return mapping.findForward("bookcontentfilmplay");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 方法描述：跳转搜索页面
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-22 上午10:51:30
	 */
	public ActionForward inSearchIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		try {
			String userid = MpUtil.getUserid(request);
			if (!"1".equals(userid)) {
				// 根据userid获取用户历史搜索记录
				SysUserSearchKeywordsManager sskm = (SysUserSearchKeywordsManager) getBean("sysUserSearchKeywordsManager");
				List list = sskm.getSysUserSearchKeywordssByHistory(userid, "1", "createdate desc", 20);
				request.setAttribute("list", list);
				return mapping.findForward("insearchindex");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 方法描述：跳转搜索结果列表页
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-22 上午11:30:06
	 */
	public ActionForward toSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
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
				String keywords = Encode.nullToBlank(request.getParameter("keywords"));
				String startcount = Encode.nullToBlank(request.getParameter("startcount"));
				if ("".equals(startcount)) {
					startcount = "0";
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
				List<SearchModel> condition = new ArrayList<SearchModel>();
				// 获取已购买的所有作业id
				TkBookContentBuyManager tbcbm = (TkBookContentBuyManager) getBean("tkBookContentBuyManager");
				List buycontentidList1 = new ArrayList();// 购买解题微课
				List buycontentidList2 = new ArrayList();// 购买举一反三微课
				TkBookContent tbc = null;
				TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
				if (keywords != null && !"".equals(keywords)) {
					PageUtil pageUtil = new PageUtil(request);
					String sorderindex = "a.bookcontentid desc";
					if (!"".equals(pageUtil.getOrderindex())) {
						sorderindex = pageUtil.getOrderindex();
					}
					PageList pageList = tbcm.getTkBookContentsOfPage(keywords, keywords, sorderindex, Integer.valueOf(startcount), pageUtil.getPageSize());
					request.setAttribute("startcount", pageList.getStartOfNextPage());
					List bookcontentList = pageList.getDatalist();
					for (int i = 0; i < bookcontentList.size(); i++) {
						TkBookContent tkBookContent = (TkBookContent) bookcontentList.get(i);
						Integer bookid2 = tkBookContent.getBookid();
						TkBookInfo tkBookInfo = tbim.getTkBookInfo(bookid2);
						String vodstate = tkBookInfo.getVodstate();
						if (vodstate == null || vodstate.trim().length() <= 0) {
							vodstate = "0";
						}
						tkBookContent.setFlago(vodstate);
						condition.clear();
						SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid2));
						SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
						SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
						List contentbuyList = tbcbm.getTkBookContentBuys(condition, "", 0);
						if (contentbuyList != null && contentbuyList.size() > 0) {
							TkBookContentBuy buy = null;
							for (int j = 0, size = contentbuyList.size(); j < size; j++) {
								buy = (TkBookContentBuy) contentbuyList.get(j);
								if ("1".equals(buy.getType())) {
									buycontentidList1.add(buy.getContentid());
								} else if ("2".equals(buy.getType())) {
									buycontentidList2.add(buy.getContentid());
								}
							}
						}
					}
					request.setAttribute("buycontentidList1", buycontentidList1);
					request.setAttribute("buycontentidList2", buycontentidList2);
					request.setAttribute("bookcontentList", bookcontentList);
					return mapping.findForward("searchbookcontentlist");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 
	 * 方法描述：搜索
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-22 下午2:03:51
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String userid = Encode.nullToBlank(request.getParameter("userid"));
			String userid_ = MpUtil.getUserid(request);
			String keywords = Encode.nullToBlank(request.getParameter("keywords"));
			TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			// 获取已购买的所有作业id
			TkBookContentBuyManager tbcbm = (TkBookContentBuyManager) getBean("tkBookContentBuyManager");
			List buycontentidList1 = new ArrayList();// 购买解题微课
			List buycontentidList2 = new ArrayList();// 购买举一反三微课
			TkBookContent tbc = null;
			TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
			if (keywords != null && !"".equals(keywords)) {
				PageUtil pageUtil = new PageUtil(request);
				String sorderindex = "a.bookcontentid desc";
				if (!"".equals(pageUtil.getOrderindex())) {
					sorderindex = pageUtil.getOrderindex();
				}
				PageList pagelist = tbcm.getTkBookContentsOfPage(keywords, keywords, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
				request.setAttribute("startcount", pageUtil.getStartCount());
				List bookcontentList = pagelist.getDatalist();
				for (int i = 0; i < bookcontentList.size(); i++) {
					TkBookContent tkBookContent = (TkBookContent) bookcontentList.get(i);
					Integer bookid2 = tkBookContent.getBookid();
					TkBookInfo tkBookInfo = tbim.getTkBookInfo(bookid2);
					String vodstate = tkBookInfo.getVodstate();
					if (vodstate == null || vodstate.trim().length() <= 0) {
						vodstate = "0";
					}
					tkBookContent.setFlago(vodstate);
					condition.clear();
					SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid2));
					SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid_));
					SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
					List contentbuyList = tbcbm.getTkBookContentBuys(condition, "", 0);
					if (contentbuyList != null && contentbuyList.size() > 0) {
						TkBookContentBuy buy = null;
						for (int j = 0, size = contentbuyList.size(); j < size; j++) {
							buy = (TkBookContentBuy) contentbuyList.get(j);
							if ("1".equals(buy.getType())) {
								buycontentidList1.add(buy.getContentid());
							} else if ("2".equals(buy.getType())) {
								buycontentidList2.add(buy.getContentid());
							}
						}
					}
				}
				StringBuffer str = new StringBuffer();
				String temp = "0";
				if (bookcontentList != null && bookcontentList.size() > 0) {
					for (int i = 0, size = bookcontentList.size(); i < size; i++) {
						tbc = (TkBookContent) bookcontentList.get(i);
						if ("1".equals(tbc.getFlago())) {
							temp = "1";
							str.append("<div  class=\"class_buy_moudle_main");
							if (i == size - 1) {
								str.append("  class_buy_moudle_main02 ");
							}
							str.append("\">");
							str.append("<div class=\"class_buy_moudle_main_p\">");
							str.append("<p class=\"class_buy_moudle_main_p01\">");
							str.append(tbc.getTitle());
							str.append("</p>");
							if (!buycontentidList1.contains(tbc.getBookcontentid())) {
								str.append("<a href=\"/weixinVod.app?method=buy&userid=").append(userid).append("&bookid=").append(tbc.getBookid()).append("&bookcontentid=").append(tbc.getBookcontentid());
								str.append("\">购买</a>");
							}
							str.append("</div>");
							if (!buycontentidList1.contains(tbc.getBookcontentid())) {
								str.append("<a class=\"class_buy_moudle_main_a03\">解题微课</a>");
							} else {
								str.append("<a href=\"/weixinVod.app?method=play&userid=").append(userid).append("&bookid=").append(tbc.getBookid()).append("&bookcontentid=").append(tbc.getBookcontentid()).append("&type=1\" class=\"class_buy_moudle_main_a01\">解题微课</a>");
							}
							str.append("</div>");
						} else if ("2".equals(tbc.getFlago())) {
							temp = "1";
							str.append("<div  class=\"class_buy_moudle_main ");
							if (i == size - 1) {
								str.append("  class_buy_moudle_main02");
							}
							str.append("\">");
							str.append("<div class=\"class_buy_moudle_main_p\">");
							str.append("<p class=\"class_buy_moudle_main_p01\">").append(tbc.getTitle()).append("</p>");
							if (!buycontentidList2.contains(tbc.getBookcontentid())) {
								str.append("<a href=\"/weixinVod.app?method=buy&userid=").append(userid).append("&bookid=").append(tbc.getBookid()).append("&bookcontentid=").append(tbc.getBookcontentid()).append("\">购买</a>");
							}
							str.append("</div>");
							if (!buycontentidList2.contains(tbc.getBookcontentid())) {
								str.append("<a class=\"class_buy_moudle_main_a03\">举一反三</a>");
							} else {
								str.append("<a href=\"/weixinVod.app?method=play&userid=").append(userid).append("&bookid=").append(tbc.getBookid()).append("&bookcontentid=").append(tbc.getBookcontentid()).append("&type=2\" class=\"class_buy_moudle_main_a01\">举一反三</a>");
							}
							str.append("</div>");
						} else if ("3".equals(tbc.getFlago())) {
							temp = "1";
							str.append("<div  class=\"class_buy_moudle_main");
							if (i == size - 1) {
								str.append("  class_buy_moudle_main02");
							}
							str.append("\">");
							;
							str.append("<div class=\"class_buy_moudle_main_p\">");
							str.append("<p class=\"class_buy_moudle_main_p01\"> ").append(tbc.getTitle()).append("</p>");
							if (!buycontentidList1.contains(tbc.getBookcontentid()) || !buycontentidList2.contains(tbc.getBookcontentid())) {
								str.append("<a href=\"/weixinVod.app?method=buy&userid=").append(userid).append("&bookid=").append(tbc.getBookid()).append("&bookcontentid=").append(tbc.getBookcontentid()).append("\">购买</a>");
							}
							str.append("</div>");
							if (!buycontentidList1.contains(tbc.getBookcontentid())) {
								str.append("<a class=\"class_buy_moudle_main_a03\">解题微课</a>");
							} else {
								str.append("<a href=\"/weixinVod.app?method=play&userid=").append(userid).append("&bookid=").append(tbc.getBookid()).append("&bookcontentid=").append(tbc.getBookcontentid()).append("&type=1\" class=\"class_buy_moudle_main_a01\">解题微课</a>");
							}
							if (!buycontentidList2.contains(tbc.getBookcontentid())) {
								str.append("<a class=\"class_buy_moudle_main_a02\">举一反三</a>");
							} else {
								str.append("<a href=\"/weixinVod.app?method=play&userid=").append(userid).append("&bookid=").append(tbc.getBookid()).append("&bookcontentid=").append(tbc.getBookcontentid()).append("&type=2\" class=\"class_buy_moudle_main_a04\">举一反三</a>");
							}
							str.append("</div>");
						}
					}
					if ("0".equals(temp)) {
						str.append("暂无数据！");
					}
				} else {
					str.append("暂无数据！");
				}
				JSONObject jo = new JSONObject();
				jo.put("startcount", pagelist.getStartOfNextPage());
				String hasNextPage = "0";
				if (pagelist.getCurPage() == pagelist.getTotalPages()) {
					hasNextPage = "1";
				}
				jo.put("hasNextPage", hasNextPage);
				jo.put("datalist", str.toString());
				pw = response.getWriter();
				pw.write(jo.toString());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}
}