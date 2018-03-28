package com.wkmk.sys.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

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
import com.util.string.UUID;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.form.SysUserInfoActionForm;

/**
 *<p>Description: 系统用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		
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
		if(!"".equals(studentno)){
			SearchCondition.addCondition(condition, "studentno", "like", "%" + studentno + "%");
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
		httpServletRequest.setAttribute("studentno", studentno);
		return actionMapping.findForward("list");
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
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysUserInfo model = new SysUserInfo();
		SysUserInfoDetail detail = new SysUserInfoDetail();
		model.setPhoto("man.jpg");
		model.setSex("0");
		model.setXueduan("2");
		detail.setEducation("9");
		detail.setJobtitle("9");
		detail.setCanaddcourse("0");
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("detail", detail);
		httpServletRequest.setAttribute("act", "addSave");
		
		//获取所有省地区
		SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		List provinceList = saim.getSysAreaInfosByParentno("00");
		httpServletRequest.setAttribute("provinceList", provinceList);
		
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
		SysUserInfoActionForm form = (SysUserInfoActionForm)actionForm;
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
				SysUserInfo model = form.getSysUserInfo();
				//将密码加密
				String password = Encode.nullToBlank(httpServletRequest.getParameter("password"));
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
				addLog(httpServletRequest,"增加了一个单位用户【" + model.getUsername() + "】信息");
			}catch (Exception e){
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysUserInfo model = manager.getSysUserInfo(objid);
			SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
			if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号解密显示
				detail.setCardno(DES.getDecryptPwd(detail.getCardno()));
			}
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("detail", detail);
			
			//获取所有省地区
			SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			List provinceList = saim.getSysAreaInfosByParentno("00");
			httpServletRequest.setAttribute("provinceList", provinceList);
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getProvince());
			List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("cityList", cityList);
			sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getCity());
			List countyList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("countyList", countyList);
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
		SysUserInfoActionForm form = (SysUserInfoActionForm)actionForm;
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserInfo model = form.getSysUserInfo();
				//将密码加密
				String password = Encode.nullToBlank(httpServletRequest.getParameter("password"));
				if (!"".equals(password)) {
					model.setPassword(MD5.getEncryptPwd(password));
				}
				manager.updateSysUserInfo(model);
				
				SysUserInfoDetail detail = form.getSysUserInfoDetail();
				if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号加密存储
					detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
				}
				detail.setUserid(model.getUserid());
				suidm.updateSysUserInfoDetail(detail);
				addLog(httpServletRequest,"修改了一个单位用户【" + model.getUsername() + "】信息");
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
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		SysUserInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysUserInfo(checkids[i]);
			model.setStatus("2");
			manager.updateSysUserInfo(model);
			//manager.delSysUserInfo(checkids[i]);
			addLog(httpServletRequest,"禁用了一个单位用户【" + model.getUsername() + "】信息");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 检查用户
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward checkName(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String checkname = Encode.nullToBlank(httpServletRequest.getParameter("checkname"));
		String checktype = Encode.nullToBlank(httpServletRequest.getParameter("checktype"));//1登录名，2学号，3身份证号，4手机号，5邮箱
		
		String value = "0";
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfo info = manager.getUserInfoByCheckName(checkname, checktype);
		if(info != null){
			value = "1";
		}

		PrintWriter pw = null;
		try {
			pw = httpServletResponse.getWriter();
			pw.write(value);
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
	 * 检查用户，并返回用户id
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-3-6 下午2:57:06 
	* @lastModified ; 2017-3-6 下午2:57:06 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward checkName2(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String checkname = Encode.nullToBlank(httpServletRequest.getParameter("checkname"));
        String checktype = Encode.nullToBlank(httpServletRequest.getParameter("checktype"));//1登录名，2学号，3身份证号，4手机号，5邮箱
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        
        String value = "0";
        SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
        SysUserInfo info = manager.getUserInfoByCheckName(checkname, checktype);
        if(info != null){
            String usertype = info.getUsertype();
            if(usertype.equals("1")){
                //判断是否已经加入这个批次，以助教身份
                EduCourseUserManager eduCourseUserManager = (EduCourseUserManager)getBean("eduCourseUserManager");
                List<SearchModel> condition = new ArrayList<SearchModel>();
                SearchCondition.addCondition(condition, "courseid", "=", courseid);
                SearchCondition.addCondition(condition, "courseclassid", "=", courseclassid);
                SearchCondition.addCondition(condition, "status", "=", "1");
                SearchCondition.addCondition(condition, "usertype", "!=", "3");
                SearchCondition.addCondition(condition, "userid", "=", info.getUserid());
                List list = eduCourseUserManager.getEduCourseUsers(condition, "", 0);
                if(list !=null && list.size()>0){
                    value= "-1";//已加入
                }else{
                        value = info.getUserid().toString();
                }
            }else{
                value="-2";//用户存在，但是不是教师，无法加入
            }
        }
        

        PrintWriter pw = null;
        try {
            pw = httpServletResponse.getWriter();
            pw.write(value);
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
			httpServletRequest.setAttribute("act", "updateSaveSelf");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("detail", detail);
			
			//获取所有省地区
			SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			List provinceList = saim.getSysAreaInfosByParentno("00");
			httpServletRequest.setAttribute("provinceList", provinceList);
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getProvince());
			List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("cityList", cityList);
			sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getCity());
			List countyList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("countyList", countyList);
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
		SysUserInfoActionForm form = (SysUserInfoActionForm)actionForm;
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserInfo model = form.getSysUserInfo();
				manager.updateSysUserInfo(model);
				
				SysUserInfoDetail detail = form.getSysUserInfoDetail();
				if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号加密存储
					detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
				}
				detail.setUserid(model.getUserid());
				suidm.updateSysUserInfoDetail(detail);
				addLog(httpServletRequest,"修改了个人【" + model.getUsername() + "】信息");
				
				//更新session中的信息
				HttpSession session = httpServletRequest.getSession();
				session.removeAttribute("s_sysuserinfodetail");
				session.removeAttribute("s_sysuserinfo");
				session.setAttribute("s_sysuserinfo", model);
				session.setAttribute("s_sysuserinfodetail", detail);
			}catch (Exception e){
				httpServletRequest.setAttribute("promptinfo", "修改个人信息失败!");
				return actionMapping.findForward("failure");
			}
		}

		httpServletRequest.setAttribute("promptinfo", "修改个人信息成功!");
		return actionMapping.findForward("success");
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
		SysUserInfoActionForm form = (SysUserInfoActionForm)actionForm;
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String oldpassword = Encode.nullToBlank(httpServletRequest.getParameter("oldpassword"));
				SysUserInfo model = form.getSysUserInfo();
				SysUserInfo user = manager.getSysUserInfo(model.getUserid());
				
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "password", "=", MD5.getEncryptPwd(oldpassword));
				SearchCondition.addCondition(condition, "userid", "=", model.getUserid());
				List userlist = manager.getSysUserInfos(condition, "", 0);
				if(userlist == null || userlist.size() == 0) {
					httpServletRequest.setAttribute("promptinfo", "输入的原密码不正确!");
					httpServletRequest.setAttribute("model", user);
					return actionMapping.findForward("modifypassword");
				}
				
				if (!"".equals(model.getPassword())) {
					String password = MD5.getEncryptPwd(model.getPassword());
					user.setPassword(password);
				}
				manager.updateSysUserInfo(user);
				
				addLog(httpServletRequest,"修改了个人【" + user.getUsername() + "】密码");
			}catch (Exception e){
				httpServletRequest.setAttribute("promptinfo", "修改个人密码失败!");
				return actionMapping.findForward("failure");
			}
		}

		httpServletRequest.setAttribute("promptinfo", "修改个人密码成功!");
		return actionMapping.findForward("success");
	}
	
	/**
	 * 批量生成用户，第一步
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward batchAddUserInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		
		return actionMapping.findForward("batchadduserinfo");
	}

	/**
	 * 批量生成用户，第二步
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward saveAddUserInfo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String start = Encode.nullToBlank(httpServletRequest.getParameter("start"));//用户名前缀
		String num = Encode.nullToBlank(httpServletRequest.getParameter("num"));//生成数量
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		List allLoginname = manager.getAllLoginname();//所有已存在数据库里面的登录名
		try {
			if(num !=null){
				int numInt = Integer.valueOf(num);
				//生成数据，导入到excel
				//set存放本次生成的所有用户，去重
				HashSet<String> set = new HashSet();
				//年、月
				String year = DateTime.getDateYear();
				year = year.substring(year.length()-1);//最后一位
		        String yue =DateTime.getDateMonth();//月
//		        String tian = getDateDay();//日
//		        String xiaoshi = getDateTime();//小时
		        for (int i = 0; set.size() < numInt; i++) {
		        		//4位随机码
		        		String str =String.valueOf((int)((Math.random()*9+1)*1000));
			        	String username = start+year+yue+str;
			        	if(!allLoginname.contains(username)){
							//去重
			        		set.add(username);
						}
		        }
		        //将数据写入excel
		        if(set !=null && set.size()>0){
		        	String path = toExcel(httpServletRequest, httpServletResponse, set);
		        	// <a src="/uploadtemp/${filepath }">${filename }</a>
		        	String filenameString="批量生成用户记录表.xls";
		        	httpServletRequest.setAttribute("promptinfo", "批量生成用户成功,点击下载表格：<a href=\"/upload/"+path+"\">"+filenameString+"</a>!");
		        }else{
		        	httpServletRequest.setAttribute("promptinfo", "批量生成用户失败!");
		        }
			}else{
				httpServletRequest.setAttribute("promptinfo", "生成数量为空，请重新输入数量！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			httpServletRequest.setAttribute("promptinfo", "批量生成用户失败!");
		}
		return actionMapping.findForward("batchsuccess");
	}
	
	public String toExcel(HttpServletRequest request,
			HttpServletResponse response,HashSet<String> set) {
	    String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
	  	String savepath = "export/batchuserinfo"; // 基础路径
	  	String curdate = DateTime.getDate("yyyy");
	  	String filepath = rootpath + "/" + savepath+ "/" + curdate;
			// 检查文件夹是否存在,如果不存在,新建一个
	  	  if (!FileUtil.isExistDir(filepath)) {
		        	FileUtil.creatDir(filepath);
		        }
	  	String uuid = UUID.getNewUUID();
		 String excelname =uuid+".xls";
	     String path =filepath + "/" + excelname;
	   //导入到excel
	     try {
				File file = new File(path);
				WritableWorkbook book = Workbook.createWorkbook(file);
				WritableSheet sheet = book.createSheet("sheet_name", 0);
				/**
				 * ***********Excel格式***********************************************
				 */
				// 设置页边距，注意参数0.5d是英寸，设置完后，打开Excel时，就会变成1.3厘米
				sheet.getSettings().setBottomMargin(0.5d);
				sheet.getSettings().setTopMargin(0.5d);
				sheet.getSettings().setLeftMargin(0.5d);
				sheet.getSettings().setRightMargin(0.5d);

				// 设置页面方向，参数中的两个0.5d分别是页眉和页脚的边距
				// 横向：
				sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,
						PaperSize.A3, 0.5d, 0.5d);
				// 纵向：
				sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,
						PaperSize.A3, 0.5d, 0.5d);

				// 设置页面的缩放比例，参数为百分比
				sheet.getSettings().setScaleFactor(90);
				// 设置页码
				sheet.setFooter("", "&P", "");
				/**
				 * ***********Excel格式***********************************************
				 */
		  		Label label = new Label(0, 0, "用户名"); // (列，行，数据)
				sheet.addCell(label);
				label = new Label(1, 0, "密码");
				sheet.addCell(label);
				
				Label label2 = null;
				List<String> list = new ArrayList<String>(set);
				for(int i=0;i<list.size();i++){
					//用户名
  		    		String username = (String) list.get(i);
  		    		//密码,密码规则：长度6位，字母+数字的随机组合
//  		    		String str =String.valueOf((int)((Math.random()*9+1)*100000));//6位随机数
  		    		String password = getCharAndNumr(6);
			  		label2 = new Label(0, i+1, username); 
					sheet.addCell(label2);
					label2 = new Label(1, i+1, password); 
					sheet.addCell(label2);
				}
			
				book.setProtected(true);
				book.write();
				book.close();
				
				
//				InputStream is = new FileInputStream(filepath + "/" + excelname);
//				OutputStream os = response.getOutputStream();// 取得输出流
//				response.reset();// 清空输出流
//				response.setContentType("text/html; charset=utf-8");// text/html
//				// application/octet-stream
//				response.setContentType("bin");
//				String name = "《" + DateTime.getDateYMDCN() + "》批量生成用户记录表.xls";
//				response.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("gb2312"), "ISO8859-1"));// gb2312
//				byte[] buffer = new byte[1024];
//				int byteread = 0;
//				while ((byteread = is.read(buffer)) > 0) {
//					os.write(buffer, 0, byteread);
//				}
//				os.close();
//				is.close();
			} catch (Exception e) {
				System.out.println(e);
			}
	     return savepath+ "/" + curdate+ "/" + excelname;
	}
	
	/**
	 * 获取数字+字母的组合
	 * @param length
	 * @return
	 */
	public String getCharAndNumr(int length)     
	{     
	    String val = "";     
	             
	    Random random = new Random();     
	    for(int i = 0; i < length; i++)     
	    {     
	        String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字     
	                 
	        if("char".equalsIgnoreCase(charOrNum)) // 字符串     
	        {     
//	            int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母     
	        	int choice =97;//小写字母
	            val += (char) (choice + random.nextInt(26));     
	        }     
	        else if("num".equalsIgnoreCase(charOrNum)) // 数字     
	        {     
	            val += String.valueOf(random.nextInt(10));     
	        }     
	    }     
	             
	    return val;     
	}  
	
	public static String getDateMonth() {
	    SimpleDateFormat formatter;
	    try {
	      formatter = new SimpleDateFormat("MM");
		  return formatter.format(new Date());
		} catch (Exception e) {
		}
		return "";
	  }
	
	public static String getDateDay() {
	    SimpleDateFormat formatter;
	    try {
	      formatter = new SimpleDateFormat("dd");
		  return formatter.format(new Date());
		} catch (Exception e) {
		}
		return "";
	  }
	
	public static String getDateTime() {
	    SimpleDateFormat formatter;
	    try {
	      formatter = new SimpleDateFormat("HH");
		  return formatter.format(new Date());
		} catch (Exception e) {
		}
		return "";
	  }
}