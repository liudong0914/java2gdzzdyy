package com.wkmk.skin.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.util.action.BaseAction;
import com.util.concurrent.AutomationCache;
import com.util.date.DateTime;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.UUID;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.bo.SysPaymentAccount;
import com.wkmk.sys.bo.SysTeacherQualification;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.service.SysMessageInfoManager;
import com.wkmk.sys.service.SysMessageUserManager;
import com.wkmk.sys.service.SysPaymentAccountManager;
import com.wkmk.sys.service.SysTeacherQualificationManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.action.SysMessageUserAction;
import com.wkmk.sys.web.form.SysUserInfoActionForm;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentBuy;
import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkBookContentFilmWatch;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkPaperFile;
import com.wkmk.tk.bo.TkPaperFileDownload;
import com.wkmk.tk.bo.TkPaperType;
import com.wkmk.tk.service.TkBookContentBuyManager;
import com.wkmk.tk.service.TkBookContentFilmManager;
import com.wkmk.tk.service.TkBookContentFilmWatchManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkPaperFileDownloadManager;
import com.wkmk.tk.service.TkPaperFileManager;
import com.wkmk.tk.service.TkPaperTypeManager;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;
import com.wkmk.util.file.ConvertFile;
import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmPixManager;
import com.wkmk.weixin.mp.MpUtil;
import com.wkmk.zx.bo.ZxHelpAnswer;
import com.wkmk.zx.bo.ZxHelpFile;
import com.wkmk.zx.bo.ZxHelpOrder;
import com.wkmk.zx.bo.ZxHelpQuestion;
import com.wkmk.zx.bo.ZxHelpQuestionComplaint;
import com.wkmk.zx.service.ZxHelpAnswerManager;
import com.wkmk.zx.service.ZxHelpFileManager;
import com.wkmk.zx.service.ZxHelpOrderManager;
import com.wkmk.zx.service.ZxHelpQuestionComplaintManager;
import com.wkmk.zx.service.ZxHelpQuestionManager;

/**
 * <p>
 * Description: 作业宝个人中心模板001
 * </p>
 * 
 * @version 1.0
 */
public class PersonalCenterAction extends BaseAction {

	/**
	 * 首页
	 */
	public ActionForward index(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
		String mappingurl = "/error.html";
		try {
			showUserInfo(actionMapping, actionForm, httpServletRequest, httpServletResponse);
			mappingurl = "/skin/pcenter/jsp/userinfo_list.jsp";
		} catch (Exception e) {
			mappingurl = "/error.html";
		}
		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}

	/*
	 * 个人信息列表
	 */
	public ActionForward showUserInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "1";
		}
		httpServletRequest.setAttribute("mark", mark);
		try {
			Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
			SysUserInfo model = manager.getSysUserInfo(userid);
			SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
			if (detail.getCardno() != null && !"".equals(detail.getCardno())) {// 证件号解密显示
				detail.setCardno(DES.getDecryptPwd(detail.getCardno()));
			}
			if (model.getPhoto() != null && !model.getPhoto().startsWith("http://")) {
				model.setPhoto("/upload/" + model.getPhoto());
			}
			httpServletRequest.setAttribute("act", "beforeUpdateSelf");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("detail", detail);
			// 获取所有省地区
			SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			List provinceList = saim.getSysAreaInfosByParentno("00");
			httpServletRequest.setAttribute("provinceList", provinceList);
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getProvince());
			List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("cityList", cityList);
			sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getCity());
			List countyList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("countyList", countyList);
		} catch (Exception e) {
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("showUserInfo");
	}

	/**
	 * 修改个人信息前
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
	public ActionForward beforeUpdateSelf(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "1";
		}
		httpServletRequest.setAttribute("mark", mark);
		try {
			Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
			SysUserInfo model = manager.getSysUserInfo(userid);
			SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
			if (detail.getCardno() != null && !"".equals(detail.getCardno())) {// 证件号解密显示
				detail.setCardno(DES.getDecryptPwd(detail.getCardno()));
			}
			if (model.getPhoto() != null && !model.getPhoto().startsWith("http://")) {
				model.setPhoto("/upload/" + model.getPhoto());
			}
			httpServletRequest.setAttribute("act", "updateSaveSelf");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("detail", detail);
			// 获取所有省地区
			SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			List provinceList = saim.getSysAreaInfosByParentno("00");
			httpServletRequest.setAttribute("provinceList", provinceList);
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getProvince());
			List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("cityList", cityList);
			sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getCity());
			List countyList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("countyList", countyList);
		} catch (Exception e) {
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("editself");
	}

	/**
	 * 修改个人信息保存
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
	 * @throws Exception
	 */
	public ActionForward updateSaveSelf(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserInfoActionForm form = (SysUserInfoActionForm) actionForm;
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserInfo model = form.getSysUserInfo();
				manager.updateSysUserInfo(model);
				SysUserInfoDetail detail = form.getSysUserInfoDetail();
				if (detail.getCardno() != null && !"".equals(detail.getCardno())) {// 证件号加密存储
					detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
				}
				detail.setUserid(model.getUserid());
				suidm.updateSysUserInfoDetail(detail);
				addLog(httpServletRequest, "修改了个人【" + model.getUsername() + "】信息");
				// 更新session中的信息
				HttpSession session = httpServletRequest.getSession();
				session.removeAttribute("s_sysuserinfodetail");
				session.removeAttribute("s_sysuserinfo");
				session.setAttribute("s_sysuserinfo", model);
				session.setAttribute("s_sysuserinfodetail", detail);
			} catch (Exception e) {
				httpServletRequest.setAttribute("promptinfo", "修改个人信息失败!");
				return showUserInfo(actionMapping, actionForm, httpServletRequest, httpServletResponse);
			}
		}
		httpServletRequest.setAttribute("promptinfo", "修改个人信息成功!");
		return showUserInfo(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 修改前
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
	public ActionForward modifyPassword(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "2";
		}
		httpServletRequest.setAttribute("mark", mark);
		try {
			Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
			SysUserInfo model = manager.getSysUserInfo(userid);
			httpServletRequest.setAttribute("act", "modifySave");
			httpServletRequest.setAttribute("model", model);
		} catch (Exception e) {
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("modifypassword");
	}

	/**
	 * 修改时保存
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
	public ActionForward modifySave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserInfoActionForm form = (SysUserInfoActionForm) actionForm;
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String oldpassword = Encode.nullToBlank(httpServletRequest.getParameter("oldpassword"));
				SysUserInfo model = form.getSysUserInfo();
				SysUserInfo user = manager.getSysUserInfo(model.getUserid());
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "password", "=", MD5.getEncryptPwd(oldpassword));
				SearchCondition.addCondition(condition, "userid", "=", model.getUserid());
				List userlist = manager.getSysUserInfos(condition, "", 0);
				if (userlist == null || userlist.size() == 0) {
					httpServletRequest.setAttribute("promptinfo", "输入的原密码不正确!");
					httpServletRequest.setAttribute("model", user);
					return actionMapping.findForward("modifypassword");
				}
				if (!"".equals(model.getPassword())) {
					String password = MD5.getEncryptPwd(model.getPassword());
					user.setPassword(password);
				}
				manager.updateSysUserInfo(user);
				addLog(httpServletRequest, "修改了个人【" + user.getUsername() + "】密码");
			} catch (Exception e) {
				e.printStackTrace();
				httpServletRequest.setAttribute("promptinfo", "修改个人密码失败!");
				return modifyPassword(actionMapping, actionForm, httpServletRequest, httpServletResponse);
			}
		}
		httpServletRequest.setAttribute("promptinfo", "修改个人密码成功!");
		return modifyPassword(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 进入教师认证
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-28 下午1:44:00
	 * @lastModified ; 2016-12-28 下午1:44:00
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager) getBean("sysTeacherQualificationManager");
		Integer userid_reply = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "3";
		}
		httpServletRequest.setAttribute("mark", mark);
		try {
			// 根据userid去查询是否为二次申请
			List list = manager.geTeacherQualificationByUserid(userid_reply);
			if (list != null && list.size() > 0) {
				// 已经申请过
				SysTeacherQualification model = (SysTeacherQualification) list.get(0);
				String status = model.getStatus();// 状态，0待审核，1已审核，2审核未通过
				Integer teacherid = model.getTeacherid();
				return view(actionMapping, actionForm, httpServletRequest, httpServletResponse, teacherid);
				// if(!status.equals("1")){
				// //还未处理，或者未通过，可以编辑
				// return beforeUpdateTwo(actionMapping, actionForm, httpServletRequest, httpServletResponse, teacherid);
				// }else{
				// //已经处理，不能编辑，只能预览
				// return view(actionMapping, actionForm, httpServletRequest, httpServletResponse, teacherid);
				// }
			} else {
				// 第一次申请,进入编辑页面
				return beforeAdd(actionMapping, actionForm, httpServletRequest, httpServletResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 初次申请编辑
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-28 下午1:45:39
	 * @lastModified ; 2016-12-28 下午1:45:39
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		SysUserInfo userInfo = manager.getSysUserInfo(userid);
		SysTeacherQualification model = new SysTeacherQualification();
		model.setSex("0");
		model.setStatus("0");// 状态是待审核
		model.setPhoto(userInfo.getPhoto());
		model.setIdphto("default.jpg");
		model.setImgpath("default.jpg");
		model.setUserid(userid);
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 * 初次申请编辑保存
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-28 下午1:46:27
	 * @lastModified ; 2016-12-28 下午1:46:27
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserInfoActionForm form = (SysUserInfoActionForm) actionForm;
		String username = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.sex"));
		String mobile = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.mobile"));
		String identitycardno = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.identitycardno"));
		String idphto = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.idphto"));
		String imgpath = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.imgpath"));
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager) getBean("sysTeacherQualificationManager");
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysTeacherQualification model = new SysTeacherQualification();
				model.setStatus("0");// 状态是待审核
				model.setPhoto("man.jpg");
				model.setUserid(userid);
				model.setUsername(username);
				model.setSex(sex);
				model.setMobile(mobile);
				model.setIdentitycardno(identitycardno);
				model.setIdphto(idphto);
				model.setImgpath(imgpath);
				// 上传附件
				FormFile file = form.getFile();
				if (file.getFileSize() > 0) {
					String savepath = addFile(actionMapping, actionForm, httpServletRequest, httpServletResponse, file);
					if (savepath == null) {
						String message = (String) httpServletRequest.getAttribute("message");
						httpServletRequest.setAttribute("promptinfo", message);
						return beforeAdd(actionMapping, actionForm, httpServletRequest, httpServletResponse);
					}
					model.setFilepath(savepath);
				}
				model.setCreatedate(DateTime.getDate());
				manager.addSysTeacherQualification(model);
				addLog(httpServletRequest, "增加了一个教师资格认证");
			} catch (Exception e) {
				httpServletRequest.setAttribute("promptinfo", "教师资格认证申请失败!");
				return main(actionMapping, actionForm, httpServletRequest, httpServletResponse);
			}
		}
		httpServletRequest.setAttribute("promptinfo", "教师资格认证申请成功，请等待管理员审核！");
		return main(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 二次申请编辑前
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
	public ActionForward beforeUpdateTwo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String teacherid = Encode.nullToBlank(httpServletRequest.getParameter("teacherid"));
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager) getBean("sysTeacherQualificationManager");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "3";
		}
		httpServletRequest.setAttribute("mark", mark);
		try {
			SysTeacherQualification model = manager.getSysTeacherQualification(teacherid);
			String sex = model.getSex();
			if (sex.equals("0")) {
				model.setFlagl("男");
			} else {
				model.setFlagl("女");
			}
			httpServletRequest.setAttribute("act", "updateSaveTwo");
			httpServletRequest.setAttribute("model", model);
		} catch (Exception e) {
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("beforeUpdateTwo");
	}

	/**
	 * 二次修改时保存
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
	public ActionForward updateSaveTwo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserInfoActionForm form = (SysUserInfoActionForm) actionForm;
		String username = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.sex"));
		String mobile = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.mobile"));
		String identitycardno = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.identitycardno"));
		String idphto = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.idphto"));
		String imgpath = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.imgpath"));
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.userid"));
		String teacherid = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.teacherid"));
		String filepath = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.filepath"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.status"));
		String returndescript = Encode.nullToBlank(httpServletRequest.getParameter("sysTeacherQualification.returndescript"));
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager) getBean("sysTeacherQualificationManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysTeacherQualification model = manager.getSysTeacherQualification(teacherid);
				model.setUsername(username);
				model.setSex(sex);
				model.setMobile(mobile);
				model.setIdentitycardno(identitycardno);
				model.setIdphto(idphto);
				model.setImgpath(imgpath);
				model.setUserid(Integer.valueOf(userid));
				model.setReturndescript(returndescript);
				String savepath = filepath;
				FormFile twofile = form.getTwofile();
				if (twofile != null && twofile.getFileSize() > 0) {
					savepath = addFile(actionMapping, actionForm, httpServletRequest, httpServletResponse, twofile);
					if (savepath == null) {
						String message = (String) httpServletRequest.getAttribute("message");
						httpServletRequest.setAttribute("promptinfo", message);
						return beforeAdd(actionMapping, actionForm, httpServletRequest, httpServletResponse);
					}
				}
				model.setFilepath(savepath);
				model.setStatus("0");// 二次编辑
				model.setCreatedate(DateTime.getDate());
				manager.updateSysTeacherQualification(model);
				addLog(httpServletRequest, "修改了一个教师资格认证");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		httpServletRequest.setAttribute("promptinfo", "教师资格认证申请成功，请等待管理员审核！");
		return main(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 预览
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
	public ActionForward view(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Integer teacherid) {
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager) getBean("sysTeacherQualificationManager");
		try {
			SysTeacherQualification model = manager.getSysTeacherQualification(teacherid);
			String sex = model.getSex();
			if (sex.equals("0")) {
				model.setFlagl("男");
			} else {
				model.setFlagl("女");
			}
			httpServletRequest.setAttribute("model", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionMapping.findForward("view");
	}

	/**
	 * 上传附件
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-14 下午3:01:44
	 * @lastModified ; 2016-12-14 下午3:01:44
	 * @version ; 1.0
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public String addFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response, FormFile file) {
		String savepath = "";
		// 获取文件
		String filepath = ""; // 全路径
		String filename = file.getFileName();
		String sFileExt = FileUtil.getFileExt(filename).toLowerCase();
		String curdate = DateTime.getDate("yyyyMM");
		String rootpath = request.getSession().getServletContext().getRealPath("/upload");// 取当前系统路径
		// 如果未传入文件名，则创建一个
		if ("".equals(filename)) {
			filename = UUID.getNewUUID() + "." + sFileExt;
			;
		}
		HttpSession session = request.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		// 图片保存路径
		savepath = unitid + "/teacherqualific/" + curdate;
		filepath = rootpath + "/" + savepath;
		// 检查文件夹是否存在,如果不存在,新建一个
		if (!FileUtil.isExistDir(filepath)) {
			FileUtil.creatDir(filepath);
		}
		try {
			// 写文件
			InputStream stream = file.getInputStream(); // 把文件读入
			OutputStream bos = new FileOutputStream(filepath + "/" + filename);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead); // 将文件写入服务器
			}
			bos.close();
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "上传附件失败，请重新上传！");
			return null;
		}
		return savepath + "/" + filename;
	}

	/**
	 * 实现文件的下载
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-14 下午6:02:05
	 * @lastModified ; 2016-12-14 下午6:02:05
	 * @version ; 1.0
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public ActionForward downFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String path = Encode.nullToBlank(request.getParameter("filepath"));// 1/teacherqualific/201612/集群版统一认证接口_V2.1.1.zip
		String filename = path.substring(path.lastIndexOf("/") + 1);
		// System.out.println(path+"111");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;
		// 如果是从服务器上取就用这个获得系统的绝对路径方法。
		String filepath = servlet.getServletContext().getRealPath("/upload/" + path);
		// System.out.println("文件路径"+filepath);
		File uploadFile = new File(filepath);
		fis = new FileInputStream(uploadFile);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);
		// 这个就是弹出下载对话框的关键代码
		response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
		int bytesRead = 0;
		// 这个地方的同上传的一样。我就不多说了，都是用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流
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
	 * 答疑订单列表
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-28 下午5:37:40
	 * @lastModified ; 2016-12-28 下午5:37:40
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws ParseException
	 */
	public ActionForward orderList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ParseException {
		ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String point = Encode.nullToBlank(httpServletRequest.getParameter("point"));
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "4";
		}
		httpServletRequest.setAttribute("mark", mark);
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		String newDate = DateTime.getDate();// 当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if (!"".equals(status)) {
			SearchCondition.addCondition(condition, "status", "=", status);
		}
		SearchCondition.addCondition(condition, "userid", "=", userid);
		String startcount = Encode.nullToBlank(httpServletRequest.getParameter("startcount"));
		if ("".equals(startcount)) {
			startcount = "0";
		}
		// String sorderindex = "a.createdate desc";
		// if(!"".equals(pageUtil.getOrderindex())){
		// sorderindex = pageUtil.getOrderindex();
		// }
		// PageList page = manager.getZxHelpOrdersOfPage(userid.toString(), status, sorderindex, Integer.valueOf(startcount), pageUtil.getPageSize());
		PageList page = manager.getPageZxHelpOrders(condition, "createdate desc", Integer.valueOf(startcount), pageUtil.getPageSize());
		String string = getTagString6(page, "/pcenter.do?method=orderList&point=" + point + "&status=" + status);
		ArrayList datalist = page.getDatalist();
		if (datalist != null && datalist.size() > 0) {
			for (int i = 0; i < datalist.size(); i++) {
				ZxHelpOrder model = (ZxHelpOrder) datalist.get(i);
				Integer questionid = model.getQuestionid();
				ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
				String title = zxHelpQuestion.getTitle();
				model.setFlagl(title);
				String enddate = model.getEnddate();// 回复最后期限
				// 如果过期，则无法回复，只能预览
				int res = newDate.compareTo(enddate);
				if (model.getStatus().equals("1")) {
					if (res > 0) {
						// 超时
						model.setStatus("3");
					}
				}
				String createdate2 = model.getCreatedate();// 抢单时间
				Date d1 = format.parse(createdate2);
				Date d2 = format.parse(newDate);
				long diff = d2.getTime() - d1.getTime();
				// long timeDiff = diff / (60 * 1000) % 60;
				// long timeDiff = diff / (1000 * 60 * 60 * 24);
				long day = diff / (24 * 60 * 60 * 1000);
				long hour = (diff / (60 * 60 * 1000) - day * 24);
				long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
				if (day != 0) {
					model.setFlagl(day + "天" + hour + "小时" + min + "分");// 几分钟前
				} else {
					if (hour != 0) {
						model.setFlagl(hour + "小时" + min + "分");// 几分钟前
					} else {
						model.setFlagl(min + "分");// 几分钟前
					}
				}
				String gradename = zxHelpQuestion.getGradename();
				String subjectname = zxHelpQuestion.getSubjectname();
				String username = zxHelpQuestion.getSysUserInfo().getUsername();
				model.setFlago(username + " • " + subjectname + " • " + gradename);// 用户1 •学科• 七年级上
				model.setFlags(zxHelpQuestion.getTitle());
				if (zxHelpQuestion.getDescript().length() > 71) {
					model.setCreatedate(zxHelpQuestion.getDescript().substring(0, 70) + "........");
				} else {
					model.setCreatedate(zxHelpQuestion.getDescript());
				}
			}
		}
		orderList2(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		orderList3(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("startcount", startcount);
		httpServletRequest.setAttribute("string", string);
		httpServletRequest.setAttribute("createdate", createdate);
		httpServletRequest.setAttribute("status", status);
		httpServletRequest.setAttribute("point", point);
		return actionMapping.findForward("orderList");
	}

	/**
	 * 被投诉答疑订单
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2017-1-22 上午11:19:45
	 * @lastModified ; 2017-1-22 上午11:19:45
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws ParseException
	 */
	public ActionForward orderListByComplation(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ParseException {
		ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String point = Encode.nullToBlank(httpServletRequest.getParameter("point"));
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "4";
		}
		httpServletRequest.setAttribute("mark", mark);
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		String newDate = DateTime.getDate();// 当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if (!"".equals(status)) {
			SearchCondition.addCondition(condition, "status", "=", status);
		}
		SearchCondition.addCondition(condition, "userid", "=", userid);
		String startcount = Encode.nullToBlank(httpServletRequest.getParameter("startcount"));
		if ("".equals(startcount)) {
			startcount = "0";
		}
		String sorderindex = "a.createdate desc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getZxHelpOrdersOfPage(userid.toString(), status, sorderindex, Integer.valueOf(startcount), pageUtil.getPageSize());
		// PageList page = manager.getPageZxHelpOrders(condition, "createdate desc",Integer.valueOf(startcount), pageUtil.getPageSize());
		String string = getTagString6(page, "/pcenter.do?method=orderListByComplation&point=" + point + "&status=" + status);
		ArrayList datalist = page.getDatalist();
		if (datalist != null && datalist.size() > 0) {
			for (int i = 0; i < datalist.size(); i++) {
				ZxHelpOrder model = (ZxHelpOrder) datalist.get(i);
				Integer questionid = model.getQuestionid();
				ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
				String title = zxHelpQuestion.getTitle();
				model.setFlagl(title);
				String enddate = model.getEnddate();// 回复最后期限
				// 如果过期，则无法回复，只能预览
				int res = newDate.compareTo(enddate);
				if (model.getStatus().equals("1")) {
					if (res > 0) {
						// 超时
						model.setStatus("3");
					}
				}
				String createdate2 = model.getCreatedate();// 抢单时间
				Date d1 = format.parse(createdate2);
				Date d2 = format.parse(newDate);
				long diff = d2.getTime() - d1.getTime();
				// long timeDiff = diff / (60 * 1000) % 60;
				// long timeDiff = diff / (1000 * 60 * 60 * 24);
				long day = diff / (24 * 60 * 60 * 1000);
				long hour = (diff / (60 * 60 * 1000) - day * 24);
				long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
				if (day != 0) {
					model.setFlagl(day + "天" + hour + "小时" + min + "分");// 几分钟前
				} else {
					if (hour != 0) {
						model.setFlagl(hour + "小时" + min + "分");// 几分钟前
					} else {
						model.setFlagl(min + "分");// 几分钟前
					}
				}
				String gradename = zxHelpQuestion.getGradename();
				String subjectname = zxHelpQuestion.getSubjectname();
				String username = zxHelpQuestion.getSysUserInfo().getUsername();
				model.setFlago(username + " • " + subjectname + " • " + gradename);// 用户1 •学科• 七年级上
				model.setFlags(zxHelpQuestion.getTitle());
				if (zxHelpQuestion.getDescript().length() > 71) {
					model.setCreatedate(zxHelpQuestion.getDescript().substring(0, 70) + "........");
				} else {
					model.setCreatedate(zxHelpQuestion.getDescript());
				}
			}
		}
		orderList2(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		orderList3(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("startcount", startcount);
		httpServletRequest.setAttribute("string", string);
		httpServletRequest.setAttribute("createdate", createdate);
		httpServletRequest.setAttribute("status", status);
		httpServletRequest.setAttribute("point", point);
		return actionMapping.findForward("orderList");
	}

	public ActionForward orderList2(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ParseException {
		ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		String status = "2";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if (!"".equals(createdate)) {
			SearchCondition.addCondition(condition, "createdate", "like", createdate + "%");
		}
		if (!"".equals(status)) {
			SearchCondition.addCondition(condition, "status", "=", status);
		}
		SearchCondition.addCondition(condition, "userid", "=", userid);
		String startcount = Encode.nullToBlank(httpServletRequest.getParameter("startcount2"));
		if ("".equals(startcount)) {
			startcount = "0";
		}
		PageList page = manager.getPageZxHelpOrders(condition, "createdate desc", Integer.valueOf(startcount), pageUtil.getPageSize());
		String string = getTagString2(page, "/pcenter.do?method=orderList");
		ArrayList datalist = page.getDatalist();
		if (datalist != null && datalist.size() > 0) {
			for (int i = 0; i < datalist.size(); i++) {
				ZxHelpOrder model = (ZxHelpOrder) datalist.get(i);
				Integer questionid = model.getQuestionid();
				ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
				String title = zxHelpQuestion.getTitle();
				model.setFlagl(title);
				String newDate = DateTime.getDate();// 当前时间
				String enddate = model.getEnddate();// 回复最后期限
				// 如果过期，则无法回复，只能预览
				int res = newDate.compareTo(enddate);
				if (model.getStatus().equals("1")) {
					if (res > 0) {
						// 超时
						model.setStatus("3");
					}
				}
				String createdate2 = model.getCreatedate();// 抢单时间
				Date d1 = format.parse(createdate2);
				Date d2 = format.parse(newDate);
				long diff = d2.getTime() - d1.getTime();
				// long timeDiff = diff / (60 * 1000) % 60;
				// long timeDiff = diff / (1000 * 60 * 60 * 24);
				long day = diff / (24 * 60 * 60 * 1000);
				long hour = (diff / (60 * 60 * 1000) - day * 24);
				long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
				if (day != 0) {
					model.setFlagl(day + "天" + hour + "小时" + min + "分");// 几分钟前
				} else {
					if (hour != 0) {
						model.setFlagl(hour + "小时" + min + "分");// 几分钟前
					} else {
						model.setFlagl(min + "分");// 几分钟前
					}
				}
				String gradename = zxHelpQuestion.getGradename();
				String username = zxHelpQuestion.getSysUserInfo().getUsername();
				model.setFlago(username + " • " + gradename);// 用户1 • 七年级上
				model.setFlags(zxHelpQuestion.getTitle());
				if (zxHelpQuestion.getDescript().length() > 71) {
					model.setCreatedate(zxHelpQuestion.getDescript().substring(0, 70) + "........");
				} else {
					model.setCreatedate(zxHelpQuestion.getDescript());
				}
			}
		}
		httpServletRequest.setAttribute("pagelist2", page);
		httpServletRequest.setAttribute("startcount2", startcount);
		httpServletRequest.setAttribute("string2", string);
		// httpServletRequest.setAttribute("createdate", createdate);
		// httpServletRequest.setAttribute("status", status);
		// return actionMapping.findForward("orderList");
		return null;
	}

	public ActionForward orderList3(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ParseException {
		ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		String status = "1";
		String newDate = DateTime.getDate();// 当前时间
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if (!"".equals(createdate)) {
			SearchCondition.addCondition(condition, "createdate", "like", createdate + "%");
		}
		if (!"".equals(status)) {
			SearchCondition.addCondition(condition, "status", "=", status);
		}
		SearchCondition.addCondition(condition, "enddate", "<", newDate.toString());
		SearchCondition.addCondition(condition, "userid", "=", userid);
		String startcount = Encode.nullToBlank(httpServletRequest.getParameter("startcount3"));
		if ("".equals(startcount)) {
			startcount = "0";
		}
		PageList page = manager.getPageZxHelpOrders(condition, "createdate desc", Integer.valueOf(startcount), pageUtil.getPageSize());
		String string = getTagString3(page, "/pcenter.do?method=orderList");
		ArrayList datalist = page.getDatalist();
		if (datalist != null && datalist.size() > 0) {
			for (int i = 0; i < datalist.size(); i++) {
				ZxHelpOrder model = (ZxHelpOrder) datalist.get(i);
				Integer questionid = model.getQuestionid();
				ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
				String title = zxHelpQuestion.getTitle();
				model.setFlagl(title);
				String enddate = model.getEnddate();// 回复最后期限
				// 如果过期，则无法回复，只能预览
				int res = newDate.compareTo(enddate);
				if (model.getStatus().equals("1")) {
					if (res > 0) {
						// 超时
						model.setStatus("3");
					}
				}
				String createdate2 = model.getCreatedate();// 抢单时间
				Date d1 = format.parse(createdate2);
				Date d2 = format.parse(newDate);
				long diff = d2.getTime() - d1.getTime();
				// long timeDiff = diff / (60 * 1000) % 60;
				// long timeDiff = diff / (1000 * 60 * 60 * 24);
				long day = diff / (24 * 60 * 60 * 1000);
				long hour = (diff / (60 * 60 * 1000) - day * 24);
				long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
				if (day != 0) {
					model.setFlagl(day + "天" + hour + "小时" + min + "分");// 几分钟前
				} else {
					if (hour != 0) {
						model.setFlagl(hour + "小时" + min + "分");// 几分钟前
					} else {
						model.setFlagl(min + "分");// 几分钟前
					}
				}
				String gradename = zxHelpQuestion.getGradename();
				String username = zxHelpQuestion.getSysUserInfo().getUsername();
				model.setFlago(username + " • " + gradename);// 用户1 • 七年级上
				model.setFlags(zxHelpQuestion.getTitle());
				if (zxHelpQuestion.getDescript().length() > 71) {
					model.setCreatedate(zxHelpQuestion.getDescript().substring(0, 70) + "........");
				} else {
					model.setCreatedate(zxHelpQuestion.getDescript());
				}
			}
		}
		httpServletRequest.setAttribute("pagelist3", page);
		httpServletRequest.setAttribute("startcount3", startcount);
		httpServletRequest.setAttribute("string3", string);
		// httpServletRequest.setAttribute("createdate", createdate);
		// httpServletRequest.setAttribute("status", status);
		// return actionMapping.findForward("orderList");
		return null;
	}

	/**
	 * 答疑详情
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-21 上午11:01:31
	 * @lastModified ; 2016-12-21 上午11:01:31
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward viewQuestion(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ZxHelpQuestionManager questionmanager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
		ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpFileManager fileManager = (ZxHelpFileManager) getBean("zxHelpFileManager");
		SysUserInfoManager userinfomanager = (SysUserInfoManager) getBean("sysUserInfoManager");
		ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
		ZxHelpQuestionComplaintManager questionComplaintManager = (ZxHelpQuestionComplaintManager) getBean("zxHelpQuestionComplaintManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));// questionid
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "4";
		}
		httpServletRequest.setAttribute("mark", mark);
		String point = Encode.nullToBlank(httpServletRequest.getParameter("point"));
		httpServletRequest.setAttribute("point", point);
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("status", status);
		String startcount = Encode.nullToBlank(httpServletRequest.getParameter("startcount"));
		httpServletRequest.setAttribute("startcount", startcount);
		try {
			// 根据提问的id，获取提问详情
			ZxHelpQuestion model = questionmanager.getZxHelpQuestion(objid);
			httpServletRequest.setAttribute("model", model);
			ZxHelpOrder zxHelpOrder = manager.getZxHelpOrderByQuestionid(objid);
			// 如果是投诉答疑订单，需要查出关联的投诉内容
			ZxHelpQuestionComplaint zxHelpQuestionComplaint = null;
			if ("3".equals(point)) {
				List<SearchModel> condition_c = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition_c, "questionid", "=", model.getQuestionid());
				SearchCondition.addCondition(condition_c, "orderid", "=", zxHelpOrder.getOrderid());
				List list = questionComplaintManager.getZxHelpQuestionComplaints(condition_c, "complaintid asc", 0);
				if (list != null && list.size() > 0) {
					zxHelpQuestionComplaint = (ZxHelpQuestionComplaint) list.get(0);
					httpServletRequest.setAttribute("zxHelpQuestionComplaint", zxHelpQuestionComplaint);// 关联的投诉详情
				}
			}
			String gradename = model.getGradename();
			String subjectname = model.getSubjectname();
			String username = model.getSysUserInfo().getUsername();
			model.setFlago(username + " • " + subjectname + " • " + gradename);// 用户1 • 七年级上
			String newDate = DateTime.getDate();// 当前时间
			int res = newDate.compareTo(zxHelpOrder.getEnddate());
			if (zxHelpOrder.getStatus().equals("1")) {
				if (res > 0) {
					// 超时
					zxHelpOrder.setStatus("3");
				}
			}
			httpServletRequest.setAttribute("zxHelpOrder", zxHelpOrder);
			String createdate2 = zxHelpOrder.getCreatedate();// 抢单时间
			Date d1 = format.parse(createdate2);
			Date d2 = format.parse(newDate);
			long diff = d2.getTime() - d1.getTime();
			long day = diff / (24 * 60 * 60 * 1000);
			long hour = (diff / (60 * 60 * 1000) - day * 24);
			long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			if (day != 0) {
				model.setFlagl(day + "天" + hour + "小时" + min + "分");// 几分钟前
			} else {
				if (hour != 0) {
					model.setFlagl(hour + "小时" + min + "分");// 几分钟前
				} else {
					model.setFlagl(min + "分");// 几分钟前
				}
			}
			model.setFlags(zxHelpOrder.getEnddate());// 最后时限
			// 展示学生问题中的视频，音频，图片
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "type", "=", "1");
			SearchCondition.addCondition(condition, "questionid", "=", objid);
			SearchCondition.addCondition(condition, "filetype", "!=", "3");
			List files = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
			List list1 = new ArrayList();// 存放图片标题和文件路径
			List list2 = new ArrayList();// 存放音频标题和文件路径
			String openconvertservice = ConvertFile.openconvertservice;
			String flag = "0";
			if (files != null && files.size() > 0) {
				for (int i = 0; i < files.size(); i++) {
					ZxHelpFile file = (ZxHelpFile) files.get(i);
					String filetype = file.getFiletype();// 文件类型，1图片，2音频，3上传的视频
					if (filetype.equals("1")) {
						file.setFlagl("图片_" + file.getFileid());
						file.setFlago(file.getFilepath());
						list1.add(file);
					}
					if (filetype.equals("2")) {
						if ("1".equals(openconvertservice)) {
							file.setFlagl("录音_" + file.getFileid());
							file.setFlago(file.getMp3path());
							list2.add(file);
						} else {
							file.setFlagl("录音_" + file.getFileid());
							file.setFlago(file.getFilepath());
							list2.add(file);
						}
					}
					flag = "1";
				}
			}
			// 展示教师回复中的视频，音频，图片
			List files_order = new ArrayList();
			List videoFilesOrder = new ArrayList();
			List list3 = new ArrayList();// 存放图片标题和文件路径
			List list4 = new ArrayList();// 存放音频标题和文件路径
			String content = "";
			if (zxHelpOrder.getStatus().equals("2")) {
				ZxHelpAnswer zxHelpAnswer = answerManager.getZxHelpAnswerByOrderid(zxHelpOrder.getOrderid().toString());
				content = zxHelpAnswer.getContent();
				httpServletRequest.setAttribute("zxHelpAnswer", zxHelpAnswer);
				condition.clear();
				SearchCondition.addCondition(condition, "type", "=", "2");
				SearchCondition.addCondition(condition, "answerid", "=", zxHelpAnswer.getAnswerid());
				SearchCondition.addCondition(condition, "filetype", "!=", "3");
				files_order = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
				if (files_order != null && files_order.size() > 0) {
					for (int i = 0; i < files_order.size(); i++) {
						ZxHelpFile file = (ZxHelpFile) files_order.get(i);
						String filetype = file.getFiletype();// 文件类型，1图片，2音频，3上传的视频
						if (filetype.equals("1")) {
							file.setFlagl("图片_" + file.getFileid());
							file.setFlago(file.getFilepath());
							list3.add(file);
						}
						if (filetype.equals("2")) {
							if ("1".equals(openconvertservice)) {
								file.setFlagl("录音_" + file.getFileid());
								file.setFlago(file.getMp3path());
								list4.add(file);
							} else {
								file.setFlagl("录音_" + file.getFileid());
								file.setFlago(file.getFilepath());
								list4.add(file);
							}
						}
						flag = "1";
					}
				}
				// 教师回复的视频
				condition.clear();
				SearchCondition.addCondition(condition, "type", "=", "2");
				SearchCondition.addCondition(condition, "answerid", "=", zxHelpAnswer.getAnswerid());
				SearchCondition.addCondition(condition, "filetype", "=", "3");
				videoFilesOrder = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
			}
			// 学生提问的视频
			condition.clear();
			SearchCondition.addCondition(condition, "type", "=", "1");
			SearchCondition.addCondition(condition, "questionid", "=", objid);
			SearchCondition.addCondition(condition, "filetype", "=", "3");
			List videoFiles = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
			String homePage = MpUtil.HOMEPAGE;
			httpServletRequest.setAttribute("flag", flag);
			httpServletRequest.setAttribute("homePage", homePage);
			httpServletRequest.setAttribute("files", files);
			httpServletRequest.setAttribute("videoFiles", videoFiles);
			httpServletRequest.setAttribute("list1", list1);
			httpServletRequest.setAttribute("list2", list2);
			httpServletRequest.setAttribute("files_order", files_order);
			httpServletRequest.setAttribute("videoFilesOrder", videoFilesOrder);
			httpServletRequest.setAttribute("list3", list3);
			httpServletRequest.setAttribute("list4", list4);
			httpServletRequest.setAttribute("content", content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionMapping.findForward("viewQuestion");
	}

	/**
	 * 查看 回复详情
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-24 下午4:10:45
	 * @lastModified ; 2016-12-24 下午4:10:45
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward viewAnswer(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ZxHelpOrderManager orderManager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionmanager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
		ZxHelpFileManager fileManager = (ZxHelpFileManager) getBean("zxHelpFileManager");
		SysUserInfoManager userinfomanager = (SysUserInfoManager) getBean("sysUserInfoManager");
		ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));// orderid
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			// 根据提问的id，获取提问详情
			ZxHelpOrder model = orderManager.getZxHelpOrder(objid);
			httpServletRequest.setAttribute("model", model);
			ZxHelpQuestion zxHelpQuestion = questionmanager.getZxHelpQuestion(model.getQuestionid());
			httpServletRequest.setAttribute("zxHelpQuestion", zxHelpQuestion);
			String gradename = zxHelpQuestion.getGradename();
			String username = zxHelpQuestion.getSysUserInfo().getUsername();
			model.setFlago(username + " • " + gradename);// 用户1 • 七年级上
			String newDate = DateTime.getDate();// 当前时间
			String createdate2 = model.getCreatedate();// 抢单时间
			Date d1 = format.parse(createdate2);
			Date d2 = format.parse(newDate);
			long diff = d2.getTime() - d1.getTime();
			long day = diff / (24 * 60 * 60 * 1000);
			long hour = (diff / (60 * 60 * 1000) - day * 24);
			long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
			model.setFlagl(day + "天" + hour + "小时" + min + "分");// 几分钟前
			model.setFlags(model.getEnddate());// 最后时限
			ZxHelpAnswer zxHelpAnswer = answerManager.getZxHelpAnswerByOrderid(model.getOrderid().toString());
			httpServletRequest.setAttribute("zxHelpAnswer", zxHelpAnswer);
			// 展示教师回复中的视频，音频，图片
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "type", "=", "2");
			SearchCondition.addCondition(condition, "answerid", "=", zxHelpAnswer.getAnswerid());
			SearchCondition.addCondition(condition, "filetype", "!=", "3");
			List files = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
			List list1 = new ArrayList();// 存放图片标题和文件路径
			List list2 = new ArrayList();// 存放音频标题和文件路径
			String openconvertservice = ConvertFile.openconvertservice;
			String flag = "0";
			if (files != null && files.size() > 0) {
				for (int i = 0; i < files.size(); i++) {
					ZxHelpFile file = (ZxHelpFile) files.get(i);
					String filetype = file.getFiletype();// 文件类型，1图片，2音频，3上传的视频
					if (filetype.equals("1")) {
						file.setFlagl("图片_" + file.getFileid());
						file.setFlago(file.getFilepath());
						list1.add(file);
					}
					if (filetype.equals("2")) {
						if ("1".equals(openconvertservice)) {
							file.setFlagl("录音_" + file.getFileid());
							file.setFlago(file.getMp3path());
							list2.add(file);
						} else {
							file.setFlagl("录音_" + file.getFileid());
							file.setFlago(file.getFilepath());
							list2.add(file);
						}
					}
					flag = "1";
				}
			}
			String homePage = MpUtil.HOMEPAGE;
			httpServletRequest.setAttribute("flag", flag);
			httpServletRequest.setAttribute("homePage", homePage);
			httpServletRequest.setAttribute("files", files);
			httpServletRequest.setAttribute("list1", list1);
			httpServletRequest.setAttribute("list2", list2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionMapping.findForward("viewAnswer");
	}

	/**
	 * 处理订单
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-29 上午11:55:50
	 * @lastModified ; 2016-12-29 上午11:55:50
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward doOrder(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
		ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
		ZxHelpFileManager fileManager = (ZxHelpFileManager) getBean("zxHelpFileManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));// orderid
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "4";
		}
		httpServletRequest.setAttribute("mark", mark);
		String point = Encode.nullToBlank(httpServletRequest.getParameter("point"));
		httpServletRequest.setAttribute("point", point);
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("status", status);
		String startcount = Encode.nullToBlank(httpServletRequest.getParameter("startcount"));
		httpServletRequest.setAttribute("startcount", startcount);
		try {
			ZxHelpOrder model = manager.getZxHelpOrder(objid);
			Integer questionid = model.getQuestionid();
			ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
			// 草稿状态
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "0");// 草稿
			SearchCondition.addCondition(condition, "orderid", "=", model.getOrderid());
			SearchCondition.addCondition(condition, "userid", "=", model.getUserid());
			List zxHelpAnswers = answerManager.getZxHelpAnswers(condition, "answerid desc", 1);
			ZxHelpAnswer zxHelpAnswer = null;
			String content = "";
			if (zxHelpAnswers != null && zxHelpAnswers.size() > 0) {
				zxHelpAnswer = (ZxHelpAnswer) zxHelpAnswers.get(0);
				content = zxHelpAnswer.getContent();
			}
			httpServletRequest.setAttribute("content", content);
			httpServletRequest.setAttribute("zxHelpQuestion", zxHelpQuestion);
			httpServletRequest.setAttribute("act", "doOrderSave");
			httpServletRequest.setAttribute("model", model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("doOrder");
	}

	/**
	 * 处理订单保存
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-29 下午1:44:19
	 * @lastModified ; 2016-12-29 下午1:44:19
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 * @throws ParseException
	 */
	public ActionForward doOrderSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ParseException {
		ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
		ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
		ZxHelpFileManager fileManager = (ZxHelpFileManager) getBean("zxHelpFileManager");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "4";
		}
		httpServletRequest.setAttribute("mark", mark);
		String point = Encode.nullToBlank(httpServletRequest.getParameter("point"));
		httpServletRequest.setAttribute("point", point);
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("status", status);
		String startcount = Encode.nullToBlank(httpServletRequest.getParameter("startcount"));
		httpServletRequest.setAttribute("startcount", startcount);
		String questionid = Encode.nullToBlank(httpServletRequest.getParameter("zxHelpOrder.questionid"));
		String orderid = Encode.nullToBlank(httpServletRequest.getParameter("zxHelpOrder.orderid"));
		String zxHelpFileId = Encode.nullToBlank(httpServletRequest.getParameter("zxHelpFileId"));// 视频文件id字符串
		String zxHelpFileIdpicture = Encode.nullToBlank(httpServletRequest.getParameter("zxHelpFileIdpicture"));// 图片文件id字符串
		String content = Encode.nullToBlank(httpServletRequest.getParameter("content"));// 回复内容
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				ZxHelpOrder model = manager.getZxHelpOrder(orderid);
				ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
				if (zxHelpQuestion.getMoney() > 0) {
					zxHelpQuestion.setReplystatus("2");
				} else {
					zxHelpQuestion.setReplystatus("3");
					model.setPaystatus("1");
				}
				model.setStatus("2");
				model.setReplycreatedate(DateTime.getDate());
				manager.updateZxHelpOrder(model);
				// 提问
				questionManager.updateZxHelpQuestion(zxHelpQuestion);
				// 回复
				ZxHelpAnswer zxHelpAnswer = answerManager.getZxHelpAnswerByOrderid(model.getOrderid().toString());
				if (zxHelpAnswer == null) {
					zxHelpAnswer = new ZxHelpAnswer();
					zxHelpAnswer.setOrderid(model.getOrderid());
					zxHelpAnswer.setContent(content);
					zxHelpAnswer.setCreatedate(DateTime.getDate());
					zxHelpAnswer.setUserip(IpUtil.getIpAddr(httpServletRequest));
					zxHelpAnswer.setStatus("1");
					zxHelpAnswer.setUserid(userid);
					answerManager.addZxHelpAnswer(zxHelpAnswer);
				} else {
					zxHelpAnswer.setContent(content);
					zxHelpAnswer.setCreatedate(DateTime.getDate());
					zxHelpAnswer.setUserip(IpUtil.getIpAddr(httpServletRequest));
					zxHelpAnswer.setStatus("1");
					zxHelpAnswer.setUserid(userid);
					answerManager.updateZxHelpAnswer(zxHelpAnswer);
				}
				// 回复附件
				String[] videoIds = null;
				if (zxHelpFileId != null && zxHelpFileId.trim().length() > 0) {
					if (zxHelpFileId.indexOf(",") > 0) {
						videoIds = zxHelpFileId.split(","); // 视频文件id数组
						for (int i = 0; i < videoIds.length; i++) {
							String fileid = videoIds[i];
							ZxHelpFile zxHelpFile = fileManager.getZxHelpFile(fileid);
							zxHelpFile.setAnswerid(zxHelpAnswer.getAnswerid());
							// 开始转码
							ConvertFile.convertFile(zxHelpFile, "add");
							fileManager.updateZxHelpFile(zxHelpFile);
						}
					} else {
						ZxHelpFile zxHelpFile = fileManager.getZxHelpFile(zxHelpFileId);
						zxHelpFile.setAnswerid(zxHelpAnswer.getAnswerid());
						// 开始转码
						ConvertFile.convertFile(zxHelpFile, "add");
						fileManager.updateZxHelpFile(zxHelpFile);
					}
				}
				String[] pictureIds = null;
				if (zxHelpFileIdpicture != null && zxHelpFileIdpicture.trim().length() > 0) {
					if (zxHelpFileIdpicture.indexOf(",") > 0) {
						pictureIds = zxHelpFileIdpicture.split(","); // 图片文件id数组
						for (int i = 0; i < pictureIds.length; i++) {
							String fileid = pictureIds[i];
							ZxHelpFile zxHelpFile = fileManager.getZxHelpFile(fileid);
							zxHelpFile.setAnswerid(zxHelpAnswer.getAnswerid());
							fileManager.updateZxHelpFile(zxHelpFile);
						}
					} else {
						ZxHelpFile zxHelpFile = fileManager.getZxHelpFile(zxHelpFileIdpicture);
						zxHelpFile.setAnswerid(zxHelpAnswer.getAnswerid());
						fileManager.updateZxHelpFile(zxHelpFile);
					}
				}
				// 如果提问金额大于0，则需要把第三方支付收款账号改为老师
				if (zxHelpQuestion.getMoney() > 0) {
					// 设置收款账号为老师
					SysPaymentAccountManager spam = (SysPaymentAccountManager) getBean("sysPaymentAccountManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "fromuserid", "=", zxHelpQuestion.getSysUserInfo().getUserid());
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
							liveTime = 15 * 60 * 1000;// 15分钟测试时间
						}
						cache.put(account.getPaymentid(), 1, liveTime);
					}
				}
				SysUserInfoManager usermanager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo teacher = usermanager.getSysUserInfo(userid);
				// 给学生发站内短信
				String msgtitle = "您发布的在线答疑提问已被老师  [" + teacher.getUsername() + "]  回复，请注意查看。";
				String msgcontent = "您发布的在线答疑提问《" + zxHelpQuestion.getTitle() + "》已被老师  [" + teacher.getUsername() + "]  回复，请注意查看。";
				SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
				SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
				SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, zxHelpQuestion.getSysUserInfo().getUserid(), smim, smum);
				addLog(httpServletRequest, "修改了一个在线答疑订单");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return orderList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 我的微课，列表显示
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
	public ActionForward mycontentfilmlist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentFilmManager manager = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
		TkBookContentBuyManager tbcbm = (TkBookContentBuyManager) getBean("tkBookContentBuyManager");
		VwhFilmInfoManager filmInfoManager = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
		TkBookInfoManager bookmanager = (TkBookInfoManager) getBean("tkBookInfoManager");
		TkBookContentManager contentmanager = (TkBookContentManager) getBean("tkBookContentManager");
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));// 购买类型，1解题微课，2举一反三微课
		if ("".equals(type)) {
			type = "1";
		}
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		if ("".equals(mark)) {
			mark = "5";
		}
		httpServletRequest.setAttribute("mark", mark);
		httpServletRequest.setAttribute("type", type);
		String startcount = Encode.nullToBlank(httpServletRequest.getParameter("startcount"));
		if ("".equals(startcount)) {
			startcount = "0";
		}
		HttpSession session = httpServletRequest.getSession();
		Integer userid = (Integer) session.getAttribute("s_userid");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "type", "=", type);
		SearchCondition.addCondition(condition, "userid", "=", userid);
		SearchCondition.addCondition(condition, "enddate", ">=", DateTime.getDate());
		PageList page = tbcbm.getPageTkBookContentBuys(condition, "createdate desc", Integer.valueOf(startcount), 12);
		ArrayList datalist = page.getDatalist();
		if (datalist != null && datalist.size() > 0) {
			for (int i = 0; i < datalist.size(); i++) {
				TkBookContentBuy model = (TkBookContentBuy) datalist.get(i);
				TkBookInfo bookinfo = bookmanager.getTkBookInfo(model.getBookid());// 作业本
				TkBookContent bookcontent = contentmanager.getTkBookContent(model.getContentid());// 章节
				TkBookContent parentcontent = contentmanager.getTkBookContentByContentno(bookcontent.getParentno(), bookcontent.getBookid().toString());// 父章节
				List<VwhFilmInfo> filmlist = manager.getFilmByBookContent(model.getBookid(), model.getContentid());
				VwhFilmInfo film = filmlist.get(0);
				String sketch = film.getSketch();
				String title = bookinfo.getTitle() + "(" + bookinfo.getFlags() + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + ")/" + parentcontent.getTitle() + "/" + bookcontent.getTitle();
				model.setFlago(title);
				model.setFlagl(sketch);
			}
		}
		String string = getTagString6(page, "/pcenter.do?method=mycontentfilmlist&type=" + type);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("string", string);
		httpServletRequest.setAttribute("startcount", startcount);
		return actionMapping.findForward("mycontentfilmlist");
	}

	/**
	 * 微课播放
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-29 下午5:20:59
	 * @lastModified ; 2016-12-29 下午5:20:59
	 * @version ; 1.0
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward play(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String userid = Encode.nullToBlank(request.getParameter("userid"));
		try {
			// 根据用户学段查询数据
			SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
			TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
			String bookid = Encode.nullToBlank(request.getParameter("bookid"));
			TkBookInfo tkBookInfo = tbim.getTkBookInfo(bookid);// 作业本
			String bookcontentid = Encode.nullToBlank(request.getParameter("bookcontentid"));
			String type = Encode.nullToBlank(request.getParameter("type"));// 1解题微课，2举一反三微课
			request.setAttribute("bookid", bookid);
			request.setAttribute("bookcontentid", bookcontentid);
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
				String url = "/weixinVod.app?method=buy&userid=" + DES.getEncryptPwd(userid.toString()) + "&bookid=" + bookid + "&bookcontentid=" + bookcontentid + "&searchtype=0";
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
			// SearchCondition.addCondition(condition1, "type", "=", type);
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
				request.setAttribute("title", title);
				request.setAttribute("keywords", keywords);
				request.setAttribute("hits", bookContentFilm.getHits());
				// 播放第一个视频
				preview(mapping, form, request, response, filmid);
				request.setAttribute("firstFilmid", filmid);
				for (int i = 0; i < tkBookContentFilms.size(); i++) {
					bookContentFilm = (TkBookContentFilm) tkBookContentFilms.get(i);
					Integer filmid_ = bookContentFilm.getFilmid();// 关联的微课视频id
					VwhFilmInfo vwhFilmInfo_ = vfim.getVwhFilmInfo(filmid_);
					String title2 = vwhFilmInfo_.getTitle();
					bookContentFilm.setFlagl(title2);
					// 视频地址
					VwhFilmPixManager pixmanager = (VwhFilmPixManager) getBean("vwhFilmPixManager");
					List vwhFilmPixsByFilmid = pixmanager.getVwhFilmPixsByFilmid(filmid_);
					VwhFilmPix filmpix = (VwhFilmPix) vwhFilmPixsByFilmid.get(0);
					bookContentFilm.setFlago(filmpix.getImgpath());
					bookContentFilm.setFlags(filmpix.getFlvpath());
				}
			}
			// 增加播放记录
			TkBookContentFilmWatchManager tbcfw = (TkBookContentFilmWatchManager) getBean("tkBookContentFilmWatchManager");
			TkBookContentFilmWatch tkcf = new TkBookContentFilmWatch();
			tkcf.setUserid(Integer.valueOf(userid));
			tkcf.setContentfilmid(bookContentFilm.getFid());
			tkcf.setCreatedate(DateTime.getDate());
			tkcf.setUserip(IpUtil.getIpAddr(request));
			tbcfw.addTkBookContentFilmWatch(tkcf);
			return mapping.findForward("play");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
	}

	/**
	 * 单个微课视频播放
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-29 下午5:24:02
	 * @lastModified ; 2016-12-29 下午5:24:02
	 * @version ; 1.0
	 * @param actionMapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @param filmid
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
	 * 获取分页代码
	 * 
	 * @author ; liud
	 * @Description : TODO
	 * @CreateDate ; 2016-12-29 下午5:23:46
	 * @lastModified ; 2016-12-29 下午5:23:46
	 * @version ; 1.0
	 * @param pageList
	 * @param url
	 * @return
	 */
	public String getTagString6(PageList pageList, String url) {
		StringBuffer bf = new StringBuffer();
		// bf.append("<div class=\'page_01\'>");
		long totalpage = pageList.getTotalPages();
		if (totalpage <= 4) {
			if (totalpage > 0) {
				// bf.append("<a href=\"#\" id=\'page_left_01\'>< </a>");
				for (int k = 0; k < totalpage; k++) {
					long startcount = k * pageList.getPageSize();
					if ((k + 1) == pageList.getCurPage()) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append(startcount);
						bf.append("\')\"  style=\"background:#47d6aa; color:#fff;\">").append(k + 1).append("</a>");
					} else {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append(startcount);
						bf.append("\')\"  >").append(k + 1).append("</a>");
					}
				}
				// bf.append("<a href=\"#\" id=\'page_left_01\'>></a>");
				bf.append("<a class=\'page_left_02\'><input type=\'search\' name=\'gopage\' id=\'gopage\' value=\'\'/></a>");
				bf.append("<input type=\'hidden\' name=\'totalPages\' id =\'totalPages\' value=\'").append(totalpage).append("\'/>");
				bf.append("<input type=\'hidden\' name=\'page_size\' id =\'page_size\' value=\'").append(pageList.getPageSize()).append("\'/>");
				bf.append("<a href=\"#\" id=\'qd\' onclick=\"javascript:gotoPageTxt(\'" + url + "&startcount=\')\">确定</a>");
			}
		} else {
			long curpage = pageList.getCurPage();
			long pagesize = pageList.getPageSize();
			if (curpage <= 3) {
				bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>< </a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append(0 * pagesize).append("\')\" ");
				if (1 == curpage) {
					bf.append("style=\"background:#47d6aa; color:#fff;\" ");
				}
				bf.append(">1</a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append(1 * pagesize).append("\')\"  ");
				if (2 == curpage) {
					bf.append("style=\"background:#47d6aa; color:#fff;\" ");
				}
				bf.append(" >2</a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append(2 * pagesize).append("\')\"  ");
				if (3 == curpage) {
					bf.append("style=\"background:#47d6aa; color:#fff;\" ");
				}
				bf.append(" >3</a>");
				bf.append("<a href=\"javascript:void(0);\">...</a>");
				bf.append(" <a href=\"javascript:turnPage(\'" + url + "&startcount=");
				bf.append((totalpage - 1) * pagesize).append("\')\">").append(totalpage).append("</a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append(3 * pagesize);
				bf.append("\')\" id=\'page_left_01\'>></a>");
			} else {
				long k = pageList.getCurPage() % 3;
				if (k == 0) {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage - 4) * pagesize);
					bf.append("\')\" id=\'page_left_01\'>< </a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage - 3) * pagesize);
					bf.append("\')\" >").append(curpage - 2).append("</a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage - 2) * pagesize);
					bf.append("\')\" >").append(curpage - 1).append("</a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage - 1) * pagesize);
					bf.append("\')\" style=\"background:#47d6aa; color:#fff;\">").append(curpage).append("</a>");
					if (curpage < totalpage) {
						bf.append("<a href=\"javascript:void(0);\">...</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((totalpage - 1) * pagesize);
						bf.append("\')\" >").append(totalpage).append("</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append(curpage * pagesize);
						bf.append("\')\"  id=\'page_left_01\'>></a>");
					} else {
						bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>></a>");
					}
				} else if (k == 1) {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage - 2) * pagesize);
					bf.append("\')\" id=\'page_left_01\'>< </a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage - 1) * pagesize);
					bf.append("\')\" style=\"background:#47d6aa; color:#fff;\">").append(curpage).append("</a>");
					if (curpage + 1 <= totalpage) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append(curpage * pagesize);
						bf.append("\')\" >").append(curpage + 1).append("</a>");
					}
					if (curpage + 2 <= totalpage) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage + 1) * pagesize);
						bf.append("\')\" >").append(curpage + 2).append("</a>");
					}
					if ((curpage + 1 == totalpage) || (curpage + 2 == totalpage) || (curpage == totalpage)) {
						bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>></a>");
					} else {
						bf.append("<a href=\"javascript:void(0);\">...</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((totalpage - 1) * pagesize);
						bf.append("\')\" >").append(totalpage).append("</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage + 2) * pagesize);
						bf.append("\')\" id=\'page_left_01\'\" >></a>");
					}
				} else {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage - 3) * pagesize);
					bf.append("\')\" id=\'page_left_01\'>< </a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage - 2) * pagesize);
					bf.append("\')\" >").append(curpage - 1).append("</a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage - 1) * pagesize);
					bf.append("\')\" style=\"background:#47d6aa; color:#fff;\">").append(curpage).append("</a>");
					if (curpage + 1 <= totalpage) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append(curpage * pagesize);
						bf.append("\')\" >").append(curpage + 1).append("</a>");
					}
					if ((curpage + 1 == totalpage) || (curpage == totalpage)) {
						bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>></a>");
					} else {
						bf.append("<a href=\"javascript:void(0)\">...</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((totalpage - 1) * pagesize);
						bf.append("\')\" >").append(totalpage).append("</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount=").append((curpage + 1) * pagesize);
						bf.append("\')\" id=\'page_left_01\'\' >></a>");
					}
				}
			}
			bf.append("<a class=\'page_left_02\'><input type=\'search\' name=\'gopage\' id=\'gopage\' value=\'\'/></a>");
			bf.append("<input type=\'hidden\' name=\'totalPages\' id =\'totalPages\' value=\'").append(totalpage).append("\'/>");
			bf.append("<input type=\'hidden\' name=\'page_size\' id =\'page_size\' value=\'").append(pageList.getPageSize()).append("\'/>");
			bf.append("<a href=\"#\" id=\'qd\' onclick=\"javascript:gotoPageTxt(\'" + url + "&startcount=\')\">确定</a>");
		}
		// bf.append("</div><!--page E -->");
		return bf.toString();
	}

	public String getTagString2(PageList pageList, String url) {
		StringBuffer bf = new StringBuffer();
		// bf.append("<div class=\'page_01\'>");
		long totalpage = pageList.getTotalPages();
		if (totalpage <= 4) {
			bf.append("<a href=\"#\" id=\'page_left_01\'>< </a>");
			for (int k = 0; k < totalpage; k++) {
				long startcount = k * pageList.getPageSize();
				if ((k + 1) == pageList.getCurPage()) {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append(startcount);
					bf.append("\')\"  style=\"background:#47d6aa; color:#fff;\">").append(k + 1).append("</a>");
				} else {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append(startcount);
					bf.append("\')\"  >").append(k + 1).append("</a>");
				}
			}
			bf.append("<a href=\"#\" id=\'page_left_01\'>></a>");
			bf.append("<a class=\'page_left_02\'><input type=\'search\' name=\'gopage2\' id=\'gopage2\' value=\'\'/></a>");
			bf.append("<input type=\'hidden\' name=\'totalPages2\' id =\'totalPages2\' value=\'").append(totalpage).append("\'/>");
			bf.append("<input type=\'hidden\' name=\'page_size2\' id =\'page_size2\' value=\'").append(pageList.getPageSize()).append("\'/>");
			bf.append("<a href=\"#\" id=\'qd\' onclick=\"javascript:gotoPageTxt2(\'" + url + "&startcount2=\')\">确定</a>");
		} else {
			long curpage = pageList.getCurPage();
			long pagesize = pageList.getPageSize();
			if (curpage <= 3) {
				bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>< </a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append(0 * pagesize).append("\')\" ");
				if (1 == curpage) {
					bf.append("style=\"background:#47d6aa; color:#fff;\" ");
				}
				bf.append(">1</a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append(1 * pagesize).append("\')\"  ");
				if (2 == curpage) {
					bf.append("style=\"background:#47d6aa; color:#fff;\" ");
				}
				bf.append(" >2</a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append(2 * pagesize).append("\')\"  ");
				if (3 == curpage) {
					bf.append("style=\"background:#47d6aa; color:#fff;\" ");
				}
				bf.append(" >3</a>");
				bf.append("<a href=\"javascript:void(0);\">...</a>");
				bf.append(" <a href=\"javascript:turnPage(\'" + url + "&startcount2=");
				bf.append((totalpage - 1) * pagesize).append("\')\">").append(totalpage).append("</a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append(3 * pagesize);
				bf.append("\')\" id=\'page_left_01\'>></a>");
			} else {
				long k = pageList.getCurPage() % 3;
				if (k == 0) {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage - 4) * pagesize);
					bf.append("\')\" id=\'page_left_01\'>< </a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage - 3) * pagesize);
					bf.append("\')\" >").append(curpage - 2).append("</a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage - 2) * pagesize);
					bf.append("\')\" >").append(curpage - 1).append("</a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage - 1) * pagesize);
					bf.append("\')\" style=\"background:#47d6aa; color:#fff;\">").append(curpage).append("</a>");
					if (curpage < totalpage) {
						bf.append("<a href=\"javascript:void(0);\">...</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((totalpage - 1) * pagesize);
						bf.append("\')\" >").append(totalpage).append("</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append(curpage * pagesize);
						bf.append("\')\"  id=\'page_left_01\'>></a>");
					} else {
						bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>></a>");
					}
				} else if (k == 1) {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage - 2) * pagesize);
					bf.append("\')\" id=\'page_left_01\'>< </a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage - 1) * pagesize);
					bf.append("\')\" style=\"background:#47d6aa; color:#fff;\">").append(curpage).append("</a>");
					if (curpage + 1 <= totalpage) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append(curpage * pagesize);
						bf.append("\')\" >").append(curpage + 1).append("</a>");
					}
					if (curpage + 2 <= totalpage) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage + 1) * pagesize);
						bf.append("\')\" >").append(curpage + 2).append("</a>");
					}
					if ((curpage + 1 == totalpage) || (curpage + 2 == totalpage) || (curpage == totalpage)) {
						bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>></a>");
					} else {
						bf.append("<a href=\"javascript:void(0);\">...</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((totalpage - 1) * pagesize);
						bf.append("\')\" >").append(totalpage).append("</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage + 2) * pagesize);
						bf.append("\')\" id=\'page_left_01\'\" >></a>");
					}
				} else {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage - 3) * pagesize);
					bf.append("\')\" id=\'page_left_01\'>< </a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage - 2) * pagesize);
					bf.append("\')\" >").append(curpage - 1).append("</a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage - 1) * pagesize);
					bf.append("\')\" style=\"background:#47d6aa; color:#fff;\">").append(curpage).append("</a>");
					if (curpage + 1 <= totalpage) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append(curpage * pagesize);
						bf.append("\')\" >").append(curpage + 1).append("</a>");
					}
					if ((curpage + 1 == totalpage) || (curpage == totalpage)) {
						bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>></a>");
					} else {
						bf.append("<a href=\"javascript:void(0)\">...</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((totalpage - 1) * pagesize);
						bf.append("\')\" >").append(totalpage).append("</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount2=").append((curpage + 1) * pagesize);
						bf.append("\')\" id=\'page_left_01\'\' >></a>");
					}
				}
			}
			bf.append("<a class=\'page_left_02\'><input type=\'search\' name=\'gopage2\' id=\'gopage2\' value=\'\'/></a>");
			bf.append("<input type=\'hidden\' name=\'totalPages2\' id =\'totalPages2\' value=\'").append(totalpage).append("\'/>");
			bf.append("<input type=\'hidden\' name=\'page_size2\' id =\'page_size2\' value=\'").append(pageList.getPageSize()).append("\'/>");
			bf.append("<a href=\"#\" id=\'qd\' onclick=\"javascript:gotoPageTxt2(\'" + url + "&startcount2=\')\">确定</a>");
		}
		// bf.append("</div><!--page E -->");
		return bf.toString();
	}

	public String getTagString3(PageList pageList, String url) {
		StringBuffer bf = new StringBuffer();
		// bf.append("<div class=\'page_01\'>");
		long totalpage = pageList.getTotalPages();
		if (totalpage <= 4) {
			bf.append("<a href=\"#\" id=\'page_left_01\'>< </a>");
			for (int k = 0; k < totalpage; k++) {
				long startcount = k * pageList.getPageSize();
				if ((k + 1) == pageList.getCurPage()) {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append(startcount);
					bf.append("\')\"  style=\"background:#47d6aa; color:#fff;\">").append(k + 1).append("</a>");
				} else {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append(startcount);
					bf.append("\')\"  >").append(k + 1).append("</a>");
				}
			}
			bf.append("<a href=\"#\" id=\'page_left_01\'>></a>");
			bf.append("<a class=\'page_left_02\'><input type=\'search\' name=\'gopage3\' id=\'gopage3\' value=\'\'/></a>");
			bf.append("<input type=\'hidden\' name=\'totalPages3\' id =\'totalPages3\' value=\'").append(totalpage).append("\'/>");
			bf.append("<input type=\'hidden\' name=\'page_size3\' id =\'page_size3\' value=\'").append(pageList.getPageSize()).append("\'/>");
			bf.append("<a href=\"#\" id=\'qd\' onclick=\"javascript:gotoPageTxt3(\'" + url + "&startcount3=\')\">确定</a>");
		} else {
			long curpage = pageList.getCurPage();
			long pagesize = pageList.getPageSize();
			if (curpage <= 3) {
				bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>< </a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append(0 * pagesize).append("\')\" ");
				if (1 == curpage) {
					bf.append("style=\"background:#47d6aa; color:#fff;\" ");
				}
				bf.append(">1</a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append(1 * pagesize).append("\')\"  ");
				if (2 == curpage) {
					bf.append("style=\"background:#47d6aa; color:#fff;\" ");
				}
				bf.append(" >2</a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append(2 * pagesize).append("\')\"  ");
				if (3 == curpage) {
					bf.append("style=\"background:#47d6aa; color:#fff;\" ");
				}
				bf.append(" >3</a>");
				bf.append("<a href=\"javascript:void(0);\">...</a>");
				bf.append(" <a href=\"javascript:turnPage(\'" + url + "&startcount3=");
				bf.append((totalpage - 1) * pagesize).append("\')\">").append(totalpage).append("</a>");
				bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append(3 * pagesize);
				bf.append("\')\" id=\'page_left_01\'>></a>");
			} else {
				long k = pageList.getCurPage() % 3;
				if (k == 0) {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage - 4) * pagesize);
					bf.append("\')\" id=\'page_left_01\'>< </a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage - 3) * pagesize);
					bf.append("\')\" >").append(curpage - 2).append("</a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage - 2) * pagesize);
					bf.append("\')\" >").append(curpage - 1).append("</a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage - 1) * pagesize);
					bf.append("\')\" style=\"background:#47d6aa; color:#fff;\">").append(curpage).append("</a>");
					if (curpage < totalpage) {
						bf.append("<a href=\"javascript:void(0);\">...</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((totalpage - 1) * pagesize);
						bf.append("\')\" >").append(totalpage).append("</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append(curpage * pagesize);
						bf.append("\')\"  id=\'page_left_01\'>></a>");
					} else {
						bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>></a>");
					}
				} else if (k == 1) {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage - 2) * pagesize);
					bf.append("\')\" id=\'page_left_01\'>< </a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage - 1) * pagesize);
					bf.append("\')\" style=\"background:#47d6aa; color:#fff;\">").append(curpage).append("</a>");
					if (curpage + 1 <= totalpage) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append(curpage * pagesize);
						bf.append("\')\" >").append(curpage + 1).append("</a>");
					}
					if (curpage + 2 <= totalpage) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage + 1) * pagesize);
						bf.append("\')\" >").append(curpage + 2).append("</a>");
					}
					if ((curpage + 1 == totalpage) || (curpage + 2 == totalpage) || (curpage == totalpage)) {
						bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>></a>");
					} else {
						bf.append("<a href=\"javascript:void(0);\">...</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((totalpage - 1) * pagesize);
						bf.append("\')\" >").append(totalpage).append("</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage + 2) * pagesize);
						bf.append("\')\" id=\'page_left_01\'\" >></a>");
					}
				} else {
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage - 3) * pagesize);
					bf.append("\')\" id=\'page_left_01\'>< </a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage - 2) * pagesize);
					bf.append("\')\" >").append(curpage - 1).append("</a>");
					bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage - 1) * pagesize);
					bf.append("\')\" style=\"background:#47d6aa; color:#fff;\">").append(curpage).append("</a>");
					if (curpage + 1 <= totalpage) {
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append(curpage * pagesize);
						bf.append("\')\" >").append(curpage + 1).append("</a>");
					}
					if ((curpage + 1 == totalpage) || (curpage == totalpage)) {
						bf.append("<a href=\"javascript:void(0);\" id=\'page_left_01\'>></a>");
					} else {
						bf.append("<a href=\"javascript:void(0)\">...</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((totalpage - 1) * pagesize);
						bf.append("\')\" >").append(totalpage).append("</a>");
						bf.append("<a href=\"javascript:turnPage(\'" + url + "&startcount3=").append((curpage + 1) * pagesize);
						bf.append("\')\" id=\'page_left_01\'\' >></a>");
					}
				}
			}
			bf.append("<a class=\'page_left_02\'><input type=\'search\' name=\'gopage3\' id=\'gopage3\' value=\'\'/></a>");
			bf.append("<input type=\'hidden\' name=\'totalPages3\' id =\'totalPages3\' value=\'").append(totalpage).append("\'/>");
			bf.append("<input type=\'hidden\' name=\'page_size3\' id =\'page_size3\' value=\'").append(pageList.getPageSize()).append("\'/>");
			bf.append("<a href=\"#\" id=\'qd\' onclick=\"javascript:gotoPageTxt3(\'" + url + "&startcount3=\')\">确定</a>");
		}
		// bf.append("</div><!--page E -->");
		return bf.toString();
	}

	public ActionForward paperList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
		TkPaperTypeManager typemanager = (TkPaperTypeManager) getBean("tkPaperTypeManager");
		EduSubjectInfoManager subjectmanager = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
		String subjectid = Encode.nullToBlank(request.getParameter("subjectid"));
		String filename = Encode.nullToBlank(request.getParameter("filename"));
		String theyear = Encode.nullToBlank(request.getParameter("theyear"));
		String typeid = Encode.nullToBlank(request.getParameter("typeid"));
		String area = Encode.nullToBlank(request.getParameter("area"));
		PageUtil pageUtil = new PageUtil(request);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		// 试卷分类集合
		SearchCondition.addCondition(condition, "status", "=", "1");
		List typelist = typemanager.getTkPaperTypes(condition, "typeno asc", 0);
		condition.clear();
		if ("".equals(typeid)) {
			typeid = ((TkPaperType) typelist.get(0)).getTypeid() + "";
		}
		String subjectname = "";
		if (!"".equals(subjectid)) {
			SearchCondition.addCondition(condition, "subjectid", "=", Integer.parseInt(subjectid));
			subjectname = subjectmanager.getEduSubjectInfo(subjectid).getSubjectname();
		}
		if (!"".equals(filename)) {
			SearchCondition.addCondition(condition, "filename", "like", "%" + filename + "%");
		}
		if (!"".equals(typeid)) {
			SearchCondition.addCondition(condition, "tkPaperType.typeid", "=", Integer.parseInt(typeid));
		}
		if (!"".equals(area)) {
			SearchCondition.addCondition(condition, "area", "like", "%" + area + "%");
		}
		SearchCondition.addCondition(condition, "theyear", "=", theyear);
		String startcount = Encode.nullToBlank(request.getParameter("startcount"));
		if ("".equals(startcount)) {
			startcount = "0";
		}
		PageList page = manager.getPageTkPaperFiles(condition, "createdate desc", Integer.parseInt(startcount), pageUtil.getPageSize());
		TkPaperFile file = null;
		for (Object obj : page.getDatalist()) {
			file = (TkPaperFile) obj;
			String subname = subjectmanager.getEduSubjectInfo(file.getSubjectid()).getSubjectname();
			file.setFlago(subname);
		}
		String pageString = getTagString6(page, "/pcenter.do?method=paperList&subjectid=" + subjectid + "&filename=" + filename + "&theyear=" + theyear + "&typeid=" + typeid + "&area=" + area);
		// 试卷所属年份集合
		condition.clear();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List yearlist = manager.getPaperFileYears();
		// 查询学科
		HttpSession session = request.getSession();
		SysUnitInfo sysUnitinfo = (SysUnitInfo) session.getAttribute("s_sysunitinfo");
		EduGradeInfoManager grademanager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<EduSubjectInfo> subjectList = grademanager.getAllSubjectByUnitType(sysUnitinfo.getType());
		request.setAttribute("pagelist", page);
		request.setAttribute("subjectid", subjectid);
		request.setAttribute("filename", filename);
		request.setAttribute("typeid", typeid);
		request.setAttribute("theyear", theyear);
		request.setAttribute("area", area);
		request.setAttribute("typelist", typelist);
		request.setAttribute("subjectList", subjectList);
		request.setAttribute("subjectname", subjectname);
		request.setAttribute("yearlist", yearlist);
		request.setAttribute("pageString", pageString);
		return actionMapping.findForward("paperlist");
	}

	/**
	 * 下载试卷附件
	 * 
	 * @param actionMapping
	 *            ActionMapping
	 * @param actionForm
	 *            ActionForm
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward downloadPaperFile(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) {
		TkPaperFileManager manager = (TkPaperFileManager) getBean("tkPaperFileManager");
		String objid = Encode.nullToBlank(request.getParameter("objid"));
		TkPaperFile model = manager.getTkPaperFile(objid);
		// =====================开始下载===========================
		String filepath = null;
		String realpath = request.getSession().getServletContext().getRealPath("/");
		String filename = null;
		filepath = "/upload" + model.getFilepath();
		filename = model.getFilename() + "." + model.getFileext();
		filename = filename.replaceAll("\\s", "");// 把字符串中的所有空白字符替换为空字符串
		InputStream is = null;
		OutputStream os = null;
		try {
			String fullpathname = realpath + filepath;
			if (!FileUtil.isExistDir(fullpathname)) {// 不存在当前文件
				request.setAttribute("promptinfo", "下载失败，当前试卷实体文件不存在!");
				return mapping.findForward("failure");
			}
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
			model.setDownloads(model.getDownloads() + 1);
			manager.updateTkPaperFile(model);
			// 记录用户下载试卷信息
			String userid = request.getSession().getAttribute("s_userid").toString();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TkPaperFileDownloadManager downloadmanager = (TkPaperFileDownloadManager) getBean("tkPaperFileDownloadManager");
			TkPaperFileDownload download = new TkPaperFileDownload();
			download.setCreatedate(format.format(new Date()));
			download.setFileid(Integer.parseInt(objid));
			download.setUserid(Integer.parseInt(userid));
			download.setUserip(IpUtil.getIpAddr(request));
			downloadmanager.addTkPaperFileDownload(download);
			addLog(request, "下载了一个试卷附件");
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public ActionForward ajaxDelImg(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "0";
		try {
			String imgid = Encode.nullToBlank(request.getParameter("imgid"));
			ZxHelpFileManager manager = (ZxHelpFileManager) getBean("zxHelpFileManager");
			ZxHelpFile zxHelpFile = manager.getZxHelpFile(imgid);
			manager.delZxHelpFile(zxHelpFile);
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

	public ActionForward ajaxGetContent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "0";
		try {
			String orderid = Encode.nullToBlank(request.getParameter("orderid"));
			ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
			ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
			ZxHelpFileManager fileManager = (ZxHelpFileManager) getBean("zxHelpFileManager");
			ZxHelpOrder model = manager.getZxHelpOrder(orderid);
			// 草稿状态
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "0");// 草稿
			SearchCondition.addCondition(condition, "orderid", "=", model.getOrderid());
			SearchCondition.addCondition(condition, "userid", "=", model.getUserid());
			List zxHelpAnswers = answerManager.getZxHelpAnswers(condition, "answerid desc", 1);
			ZxHelpAnswer zxHelpAnswer = null;
			StringBuffer str = new StringBuffer();
			if (zxHelpAnswers != null && zxHelpAnswers.size() > 0) {
				zxHelpAnswer = (ZxHelpAnswer) zxHelpAnswers.get(0);
				// 展示教师回复中的视频，音频，图片
				List videoFilesOrder = new ArrayList();
				// 教师回复的视频
				condition.clear();
				SearchCondition.addCondition(condition, "type", "=", "2");
				SearchCondition.addCondition(condition, "answerid", "=", zxHelpAnswer.getAnswerid());
				SearchCondition.addCondition(condition, "filetype", "=", "3");
				videoFilesOrder = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
				if (videoFilesOrder != null && videoFilesOrder.size() > 0) {
					for (int i = 0; i < videoFilesOrder.size(); i++) {
						ZxHelpFile videoFile = (ZxHelpFile) videoFilesOrder.get(i);
						str.append("<div class='Published_div_yy' style='width:120px;height:45px;' id='video").append(videoFile.getFileid()).append("'>");
						str.append("<a href='javascript:deleteVideo(").append(videoFile.getFileid()).append(")'><img src='/weixin/images/d08.png' class='Published_div_yy_img' /></a>");
						str.append("<img src='/weixin/images/icon10.png' class='Published_div_yy_img02'>").append("视频_" + i).append("</img>");
						str.append("</div>");
					}
				}
			}
			pw = response.getWriter();
			pw.write(str.toString());
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

	public ActionForward ajaxGetContent2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "0";
		try {
			String orderid = Encode.nullToBlank(request.getParameter("orderid"));
			ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
			ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
			ZxHelpFileManager fileManager = (ZxHelpFileManager) getBean("zxHelpFileManager");
			ZxHelpOrder model = manager.getZxHelpOrder(orderid);
			// 草稿状态
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "0");// 草稿
			SearchCondition.addCondition(condition, "orderid", "=", model.getOrderid());
			SearchCondition.addCondition(condition, "userid", "=", model.getUserid());
			List zxHelpAnswers = answerManager.getZxHelpAnswers(condition, "answerid desc", 1);
			ZxHelpAnswer zxHelpAnswer = null;
			StringBuffer str = new StringBuffer();
			if (zxHelpAnswers != null && zxHelpAnswers.size() > 0) {
				zxHelpAnswer = (ZxHelpAnswer) zxHelpAnswers.get(0);
				// 展示教师回复中的视频，音频，图片
				List files_order = new ArrayList();
				List list3 = new ArrayList();// 存放图片标题和文件路径
				List list4 = new ArrayList();// 存放音频标题和文件路径
				String openconvertservice = ConvertFile.openconvertservice;
				if (model.getStatus().equals("1")) {
					condition.clear();
					SearchCondition.addCondition(condition, "type", "=", "2");
					SearchCondition.addCondition(condition, "answerid", "=", zxHelpAnswer.getAnswerid());
					SearchCondition.addCondition(condition, "filetype", "!=", "3");
					files_order = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
					if (files_order != null && files_order.size() > 0) {
						for (int i = 0; i < files_order.size(); i++) {
							ZxHelpFile file = (ZxHelpFile) files_order.get(i);
							String filetype = file.getFiletype();// 文件类型，1图片，2音频，3上传的视频
							if (filetype.equals("1")) {
								file.setFlagl("图片_" + file.getFileid());
								file.setFlago(file.getFilepath());
								list3.add(file);
							}
							if (filetype.equals("2")) {
								if ("1".equals(openconvertservice)) {
									file.setFlagl("录音_" + file.getFileid());
									file.setFlago(file.getMp3path());
									list4.add(file);
								} else {
									file.setFlagl("录音_" + file.getFileid());
									file.setFlago(file.getFilepath());
									list4.add(file);
								}
							}
						}
					}
				}
				if (list3 != null && list3.size() > 0) {
					for (int i = 0; i < list3.size(); i++) {
						ZxHelpFile imgFile = (ZxHelpFile) list3.get(i);
						str.append("<div class='Published_div_yy' style='width:200px;height:150px;' id='img").append(imgFile.getFileid()).append("'>");
						str.append("<a href='javascript:deleteImg(").append(imgFile.getFileid()).append(")'><img src='/weixin/images/d08.png' class='Published_div_yy_img' /></a>");
						str.append("<img src='/upload/").append(imgFile.getThumbpath()).append("' class='Published_div_yy_img02'></img>");
						str.append("</div>");
					}
				}
			}
			pw = response.getWriter();
			pw.write(str.toString());
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

	public ActionForward ajaxGetContent3(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		String result = "0";
		try {
			String orderid = Encode.nullToBlank(request.getParameter("orderid"));
			ZxHelpOrderManager manager = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
			ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager) getBean("zxHelpAnswerManager");
			ZxHelpFileManager fileManager = (ZxHelpFileManager) getBean("zxHelpFileManager");
			ZxHelpOrder model = manager.getZxHelpOrder(orderid);
			// 草稿状态
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "0");// 草稿
			SearchCondition.addCondition(condition, "orderid", "=", model.getOrderid());
			SearchCondition.addCondition(condition, "userid", "=", model.getUserid());
			List zxHelpAnswers = answerManager.getZxHelpAnswers(condition, "answerid desc", 1);
			ZxHelpAnswer zxHelpAnswer = null;
			StringBuffer str = new StringBuffer();
			if (zxHelpAnswers != null && zxHelpAnswers.size() > 0) {
				zxHelpAnswer = (ZxHelpAnswer) zxHelpAnswers.get(0);
				// 展示教师回复中的视频，音频，图片
				List files_order = new ArrayList();
				List list3 = new ArrayList();// 存放图片标题和文件路径
				List list4 = new ArrayList();// 存放音频标题和文件路径
				String openconvertservice = ConvertFile.openconvertservice;
				if (model.getStatus().equals("1")) {
					condition.clear();
					SearchCondition.addCondition(condition, "type", "=", "2");
					SearchCondition.addCondition(condition, "answerid", "=", zxHelpAnswer.getAnswerid());
					SearchCondition.addCondition(condition, "filetype", "!=", "3");
					files_order = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
					if (files_order != null && files_order.size() > 0) {
						for (int i = 0; i < files_order.size(); i++) {
							ZxHelpFile file = (ZxHelpFile) files_order.get(i);
							String filetype = file.getFiletype();// 文件类型，1图片，2音频，3上传的视频
							if (filetype.equals("1")) {
								file.setFlagl("图片_" + file.getFileid());
								file.setFlago(file.getFilepath());
								list3.add(file);
							}
							if (filetype.equals("2")) {
								if ("1".equals(openconvertservice)) {
									file.setFlagl("录音_" + file.getFileid());
									file.setFlago(file.getMp3path());
									list4.add(file);
								} else {
									file.setFlagl("录音_" + file.getFileid());
									file.setFlago(file.getFilepath());
									list4.add(file);
								}
							}
						}
					}
				}
				if (list4 != null && list4.size() > 0) {
					for (int i = 0; i < list4.size(); i++) {
						ZxHelpFile mp3File = (ZxHelpFile) list4.get(i);
						str.append("<div class='Published_div_yy' style='width:200px;height:150px;' id='img").append(mp3File.getFileid()).append("'>");
						str.append("<a href='javascript:deleteImg(").append(mp3File.getFileid()).append(")'><img src='/weixin/images/d08.png' class='Published_div_yy_img' /></a>");
						str.append("<audio id='myaudio' src='/upload/").append(mp3File.getMp3path()).append("' controls='controls' style='margin:auto'></audio>");
						str.append("</div>");
					}
				}
			}
			pw = response.getWriter();
			pw.write(str.toString());
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
}
