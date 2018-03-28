package com.wkmk.weixin.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.date.DateTime;
import com.util.encrypt.Base64Utils;
import com.util.encrypt.DES;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.action.BaseAction;
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.service.EduCourseBuyManager;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseFilmManager;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserLog;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserLogManager;
import com.wkmk.tk.bo.TkPaperFile;
import com.wkmk.tk.bo.TkTextBookInfo;
import com.wkmk.tk.service.TkPaperFileManager;
import com.wkmk.tk.service.TkTextBookInfoManager;
import com.wkmk.util.common.IpUtil;
import com.wkmk.weixin.mp.MpUtil;

/**
 * 微信js扫码接口入口
 * @version 1.0
 */
public class WeixinScanQRCodeAction extends BaseAction {

	/**
	 * 微信菜单扫一扫
	 * 所有需要使用平台的用户必须是注册用户
	 */
	public ActionForward scan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			//扫一扫跳转通过openid查询判断，因为有可能是前期没有绑定，后期绑定了也可以点击查看以前的连接
			String scanResult0 = Encode.nullToBlank(request.getParameter("scanResult"));
			String openid = Encode.nullToBlank(request.getParameter("openid"));
			String userid = MpUtil.getUserid(request);
			if(!"".equals(openid)){
				SysUserAttentionManager suam = (SysUserAttentionManager) getBean("sysUserAttentionManager");
				SysUserAttention attention = suam.getSysUserAttentionByOpenid(openid);
				if(attention != null){
					userid = attention.getUserid().toString();
				}
			}
			if(userid != null && !"1".equals(userid)){
				//通过日志记录用户菜单行为点击事件
				SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
			    SysUserInfo sysUserInfo = suim.getSysUserInfo(userid);
			    
				MpUtil.setUserid(request, userid);
				//【取消二维码加密】
				//String scanResult = Base64Utils.base64Decoder(scanResult0);
				String scanResult = scanResult0;
				List<SearchModel> condition = new ArrayList<SearchModel>();
				if(scanResult.indexOf("qrcodeno") != -1){//课程目录二维码编号
					String qrcodeno = scanResult.split("=")[1];
					EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
					EduCourseFilm eduCourseFilm = ecfm.getEduCourseFilmByQrcodeno(qrcodeno);
					
					//记录日志内容
					String descript = "用户" + sysUserInfo.getLoginname() + "(" + sysUserInfo.getUsername() + ")调用了扫描二维码功能查看了《" + eduCourseFilm.getTitle() + "》微课视频";
				    addLog(request,  descript, sysUserInfo);
					
					if("1".equals(eduCourseFilm.getStatus())){
						//如果用户是vip用户或是课程作者，则可直接查看视频内容，否则需要判断是否购买课程，没有购买，引导用户跳转购买支付界面，同时自动弹出支付窗口
						//如果视频免费，也可直接在线查看【附件暂不提供微信端显示查看】
						if(eduCourseFilm.getSellprice().floatValue() > 0){
							condition.clear();
							EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
							SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
							SearchCondition.addCondition(condition, "courseid", "=", eduCourseFilm.getCourseid());
							List lst = ecum.getEduCourseUsers(condition, "createdate desc", 0);
							if(lst != null && lst.size() > 0){
								EduCourseUser courseUser = (EduCourseUser) lst.get(0);
								if("1".equals(courseUser.getUsertype()) || "1".equals(courseUser.getVip())){
									//直接跳转至观看界面
									String redirecturl = "/weixinCourse.app?method=play&userid=" + DES.getEncryptPwd(userid) + "&coursefilmid=" + eduCourseFilm.getCoursefilmid();
									response.sendRedirect(redirecturl);
									return null;
								}else {
									condition.clear();
									EduCourseBuyManager ecbm = (EduCourseBuyManager) getBean("eduCourseBuyManager");
									SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(userid));
									SearchCondition.addCondition(condition, "coursefilmid", "=", eduCourseFilm.getCoursefilmid());
									SearchCondition.addCondition(condition, "enddate", ">", DateTime.getDate());
									List buyList = ecbm.getEduCourseBuys(condition, "", 0);
									if(buyList != null && buyList.size() > 0){
										//已购买，直接跳转至观看界面
										String redirecturl = "/weixinCourse.app?method=play&userid=" + DES.getEncryptPwd(userid) + "&coursefilmid=" + eduCourseFilm.getCoursefilmid();
										response.sendRedirect(redirecturl);
										return null;
									}else {
										//跳转到支付购买界面
										String redirecturl = "/weixinCourse.app?method=buy&autopay=1&userid=" + DES.getEncryptPwd(userid) + "&coursefilmid=" + eduCourseFilm.getCoursefilmid();
										response.sendRedirect(redirecturl);
										return null;
									}
								}
							}else {
								//跳转到支付购买界面
								String redirecturl = "/weixinCourse.app?method=buy&autopay=1&userid=" + DES.getEncryptPwd(userid) + "&coursefilmid=" + eduCourseFilm.getCoursefilmid();
								response.sendRedirect(redirecturl);
								return null;
							}
						}else {
							//如果是第一次学习免费课程，需要加入课程班级
							EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
							condition.clear();
							SearchCondition.addCondition(condition, "courseid", "=", eduCourseFilm.getCourseid());
							SearchCondition.addCondition(condition, "userid", "=", sysUserInfo.getUserid());
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
									courseUser.setUserid(sysUserInfo.getUserid());
									courseUser.setStatus("1");
									courseUser.setCreatedate(DateTime.getDate());
									courseUser.setUsertype("3");
									courseUser.setVip("0");
									ecum.addEduCourseUser(courseUser);
									
									eduCourseClass.setUsercount(eduCourseClass.getUsercount() + 1);
									eccm.updateEduCourseClass(eduCourseClass);
								}
							}
							//直接跳转至观看界面
							String redirecturl = "/weixinCourse.app?method=play&userid=" + DES.getEncryptPwd(userid) + "&coursefilmid=" + eduCourseFilm.getCoursefilmid();
							response.sendRedirect(redirecturl);
							return null;
						}
					}else {
						request.setAttribute("promptinfo", "温馨提示：当前视频课程已下架，请查看其他课程视频！");
						return mapping.findForward("scantips");
					}
				}else if(scanResult.indexOf("TkTextBookInfo") != -1){//教材二维码
					String textbookid = scanResult.split("=")[1];
					TkTextBookInfoManager manager = (TkTextBookInfoManager)getBean("tkTextBookInfoManager");
					TkTextBookInfo textBookInfo = manager.getTkTextBookInfo(textbookid);
					
					//记录日志内容
					String descript = "用户" + sysUserInfo.getLoginname() + "(" + sysUserInfo.getUsername() + ")调用了扫描二维码功能查看了教材信息【" + textBookInfo.getTextbookname()+ "】";
				    addLog(request,  descript, sysUserInfo);
				    
				    //进入试卷详情
				    //此处需要修改，跳转到微信端教材详情页面
				    ///weixinTextBook.app?method=getTextBookDetail&userid=${userid }&textbookid=<%=bookInfo.getTextbookid() %>
					String redirecturl = "/weixinTextBook.app?method=getTextBookDetail&userid=" + DES.getEncryptPwd(userid) + "&textbookid=" +textbookid;
					response.sendRedirect(redirecturl);
					return null;
				}else if(scanResult.startsWith("http://dwz.cn")) {//短链接地址生成的二维码（pc二维码登陆）
					response.sendRedirect(scanResult);
					return null;
				}else {
					request.setAttribute("promptinfo", "温馨提示：无法识别，当前二维码信息不是系统生成的二维码！");
					return mapping.findForward("scantips");
				}
			}else {
				//跳转到登陆页面【初步控制，注册时，扫一扫作业本和班级时信息携带着，其他暂不携带，不携带如果登录成功自动跳转到个人中心，如果是注册，则需要选择注册的作业本】
				String sNikeNameEncoded = Encode.nullToBlank(request.getParameter("nickname")).trim();
//				System.out.println("传输后昵称Encoded="+sNikeNameEncoded);
//				System.out.println("传输后昵称Decoded="+Base64Utils.base64Decoder(sNikeNameEncoded));
//				System.out.println("XXX="+"54qs5bCP5ZCg".equals(sNikeNameEncoded));
//				System.out.println("传输后昵称Decoded2="+Base64Utils.base64Decoder("54qs5bCP5ZCg"));

				String nickname = Base64Utils.base64Decoder(sNikeNameEncoded);
				String headimgurl = Encode.nullToBlank(request.getParameter("headimgurl"));
				String sex = Encode.nullToBlank(request.getParameter("sex"));
				request.setAttribute("nickname", nickname);
				request.setAttribute("headimgurl", headimgurl);
				request.setAttribute("sex", sex);
				request.setAttribute("openid", openid);
				request.setAttribute("scanResult", scanResult0);
				return mapping.findForward("login");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mapping.findForward("error");
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
}