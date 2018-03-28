package com.wkmk.sys.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysMessageInfo;
import com.wkmk.sys.bo.SysMessageUser;
import com.wkmk.sys.bo.SysSmsInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysMessageInfoManager;
import com.wkmk.sys.service.SysMessageUserManager;
import com.wkmk.sys.service.SysSmsInfoManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.form.SysMessageInfoActionForm;
import com.wkmk.util.sms.SmsSend;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统消息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysMessageInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String username=Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String title=Encode.nullToBlank(httpServletRequest.getParameter("title"));
		SysMessageInfoManager manager = (SysMessageInfoManager)getBean("sysMessageInfoManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysMessageInfos (title,username, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("title", title);
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
		SysMessageInfo model = new SysMessageInfo();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");

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
		SysMessageInfoActionForm form = (SysMessageInfoActionForm)actionForm;
		SysMessageInfoManager manager = (SysMessageInfoManager)getBean("sysMessageInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysMessageInfo model = form.getSysMessageInfo();
				manager.addSysMessageInfo(model);
				addLog(httpServletRequest,"增加了一个系统消息");
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
		SysMessageInfoManager manager = (SysMessageInfoManager)getBean("sysMessageInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysMessageInfo model = manager.getSysMessageInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
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
		SysMessageInfoActionForm form = (SysMessageInfoActionForm)actionForm;
		SysMessageInfoManager manager = (SysMessageInfoManager)getBean("sysMessageInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysMessageInfo model = form.getSysMessageInfo();
				manager.updateSysMessageInfo(model);
				addLog(httpServletRequest,"修改了一个系统消息");
			}catch (Exception e){
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *详情跳转
	 */
	public ActionForward beforeDetail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysMessageInfoManager manager = (SysMessageInfoManager)getBean("sysMessageInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysMessageInfo model = manager.getSysMessageInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("detail");
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
		SysMessageInfoManager manager = (SysMessageInfoManager)getBean("sysMessageInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delSysMessageInfo(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 跳转到主工作区
	 */
	public ActionForward main(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}

	/**
	 * 树型选择器
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward tree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		StringBuffer tree = new StringBuffer();
		tree.append("\ntree.nodes[\"0_1\"]=\"text:联系人;hint:;url:/sysMessageInfoAction.do?method=userList;target:rfrmright;\";");
		tree.append("\ntree.nodes[\"0_2\"]=\"text:群组;hint:;url:/sysMessageInfoAction.do?method=beforeSendGroup;target:rfrmright;\";");
		String rooturl="javascript:";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}
	
	/**
	 *联系人列表
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward userList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		String userids=Encode.nullToBlank(httpServletRequest.getParameter("userids"));
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
		httpServletRequest.setAttribute("userids", userids);
		return actionMapping.findForward("userlist");
	}
	
	/**
	 * 跳转向联系人发送消息页面
	 */
	public ActionForward beforeSendContacts(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		httpServletRequest.setAttribute("userids", Encode.nullToBlank(httpServletRequest.getParameter("userids")));
		saveToken(httpServletRequest);
		return actionMapping.findForward("sendcontacts");
	}
	
	/**
	 * 向联系人发送消息
	 */
	public ActionForward saveSendContacts(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (isTokenValid(httpServletRequest, true)) {
			String issms=Encode.nullToBlank(httpServletRequest.getParameter("issms"));
			String title=Encode.nullToBlank(httpServletRequest.getParameter("title"));
			final String content=Encode.nullToBlank(httpServletRequest.getParameter("content"));
			final String smscontent=Encode.nullToBlank(httpServletRequest.getParameter("smscontent"));
			String userids=Encode.nullToBlank(httpServletRequest.getParameter("userids"));
			String senduserid=httpServletRequest.getSession().getAttribute("s_userid").toString();
			
			SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
			SysMessageInfoManager messagemanager=(SysMessageInfoManager)getBean("sysMessageInfoManager");
			final SysSmsInfoManager smsmanager=(SysSmsInfoManager)getBean("sysSmsInfoManager");
			final SysMessageUserManager mumanager=(SysMessageUserManager)getBean("sysMessageUserManager");
			final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd　HH:mm:ss");
			final String[] userid=userids.split(",");
			SysMessageInfo message=new SysMessageInfo();
			message.setContent(content);
			message.setCreatedate(format.format(new Date()));
			message.setTitle(title);
			message.setType("1");
			message.setUserid(Integer.parseInt(senduserid));
			final SysMessageInfo msg=messagemanager.addSysMessageInfo(message);
			final Integer messageid=message.getMessageid();
			for (int i = 0; i < userid.length; i++) {
				final int j=i;
				Runnable runnable = new Runnable(){
					public void run() {
						SysMessageUser mu=new SysMessageUser();
						mu.setIsread("0");
						mu.setUserid(Integer.parseInt(userid[j]));
						mu.setSysMessageInfo(msg);
						mu.setReadtime("");
						mumanager.addSysMessageUser(mu);			
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();	
				if("1".equals(issms)){//短信通知用户
					final SysUserInfo user=usermanager.getSysUserInfo(Integer.parseInt(userid[i]));//接收消息用户信息
					final String mobile=user.getMobile();//接收消息用户手机号码
					if(mobile!=null&&!"".equals(mobile)){//号码不为空  给用户发送短信通知
						Runnable runnable1 = new Runnable(){
							public void run() {
								//直接发送邮件，无需保存
								String smsstate=SmsSend.send(mobile, content)?"1":"2";//短信发送状态 1：成功  2：失败
								SysSmsInfo sms=new SysSmsInfo();
								sms.setContent(smscontent);
								sms.setMobile(mobile);
								sms.setUserid(user.getUserid());
								sms.setState(smsstate);
								sms.setCreatedate(format.format(new Date()));
								sms.setType("1");
								sms.setMessageid(messageid);
								smsmanager.addSysSmsInfo(sms);
							}
						};
						Thread thread1 = new Thread(runnable1);
						thread1.start();					
					}
				}
			}
			
			addLog(httpServletRequest, "向"+userids.length()+"位用户发送消息【"+content+"】");
			if("1".equals(issms)){
				addLog(httpServletRequest, "向"+userids.length()+"位用户发送短信【"+smscontent+"】");
			}
		}
		httpServletRequest.setAttribute("promptinfo", "消息正通过后台发送~~");
		return actionMapping.findForward("success");
	} 
	
	/**
	 * 跳转向群组发送消息页面
	 */
	public ActionForward beforeSendGroup(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		httpServletRequest.setAttribute("usertype", "");
		httpServletRequest.setAttribute("xueduan", "");
		saveToken(httpServletRequest);
		return actionMapping.findForward("sendgroup");
	}
	
	/**
	 *保存群组消息
	 */
	@SuppressWarnings("unchecked")
	public ActionForward saveSendGroup(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		if (isTokenValid(httpServletRequest, true)) {
			String issms=Encode.nullToBlank(httpServletRequest.getParameter("issms"));
			String title=Encode.nullToBlank(httpServletRequest.getParameter("title"));
			String usertype=Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
			String xueduan=Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
			final String content=Encode.nullToBlank(httpServletRequest.getParameter("content"));
			final String smscontent=Encode.nullToBlank(httpServletRequest.getParameter("smscontent"));
			String senduserid=httpServletRequest.getSession().getAttribute("s_userid").toString();
			
			SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
			SysMessageInfoManager messagemanager=(SysMessageInfoManager)getBean("sysMessageInfoManager");
			final SysSmsInfoManager smsmanager=(SysSmsInfoManager)getBean("sysSmsInfoManager");
			final SysMessageUserManager mumanager=(SysMessageUserManager)getBean("sysMessageUserManager");
			final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd　HH:mm:ss");
			
			List<SearchModel> condition=new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "usertype", "=", usertype);
			SearchCondition.addCondition(condition, "xueduan", "=", xueduan);
			List<SysUserInfo> userlist=usermanager.getSysUserInfos(condition, "", 0);
			if(userlist.size()>0){
				SysMessageInfo message=new SysMessageInfo();
				message.setContent(content);
				message.setCreatedate(format.format(new Date()));
				message.setTitle(title);
				message.setType("1");
				message.setUserid(Integer.parseInt(senduserid));
				final SysMessageInfo msg=messagemanager.addSysMessageInfo(message);
				final Integer messageid=message.getMessageid();
				for (final SysUserInfo user : userlist) {
					Runnable runnable = new Runnable(){
						public void run() {
							SysMessageUser mu=new SysMessageUser();
							mu.setIsread("0");
							mu.setUserid(user.getUserid());
							mu.setSysMessageInfo(msg);
							mu.setReadtime("");
							mumanager.addSysMessageUser(mu);
						}
					};
					Thread thread = new Thread(runnable);
					thread.start();		
					if("1".equals(issms)){//短信通知用户
						final String mobile=user.getMobile();//接收消息用户手机号码
						if(mobile!=null&&!"".equals(mobile)){//号码不为空  给用户发送短信通知
							Runnable runnable1 = new Runnable(){
								public void run() {
									//直接发送邮件，无需保存
									String smsstate=SmsSend.send(mobile, content)?"1":"2";//短信发送状态 1：成功  0：失败
									SysSmsInfo sms=new SysSmsInfo();
									sms.setContent(smscontent);
									sms.setMobile(mobile);
									sms.setUserid(user.getUserid());
									sms.setState(smsstate);
									sms.setCreatedate(format.format(new Date()));
									sms.setType("1");
									sms.setMessageid(messageid);
									smsmanager.addSysSmsInfo(sms);
								}
							};
							Thread thread1 = new Thread(runnable1);
							thread1.start();					
						}
					}
				}
				httpServletRequest.setAttribute("promptinfo", "消息正通过后台发送~~");
			}else{
				httpServletRequest.setAttribute("promptinfo", "群组下没有联系人~");	
			}
			String usertypename="0".equals(usertype)?"系统用户":"1".equals(usertype)?"老师":"2".equals(usertype)?"学生":"3".equals(usertype)?"家长":"所有用户";
			String xueduanname="1".equals(xueduan)?"小学":"2".equals(xueduan)?"初中":"3".equals(xueduan)?"高中":"";
			addLog(httpServletRequest, "向（"+xueduanname+usertypename+"）发送消息【"+content+"】");
			if("1".equals(issms)){
				addLog(httpServletRequest, "向（"+xueduanname+usertypename+"）发送短信【"+smscontent+"】");
			}
		}
		return actionMapping.findForward("success");
	} 
	
	/**
	 * 跳转向群组发送消息页面
	 */
	public ActionForward messageUserList(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String messageid=Encode.nullToBlank(httpServletRequest.getParameter("messageid"));
		String username=Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String usertype=Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String xueduan=Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
		String isread=Encode.nullToBlank(httpServletRequest.getParameter("isread"));
		PageUtil pageUtil=new PageUtil(httpServletRequest);
		SysMessageUserManager smum=(SysMessageUserManager)getBean("sysMessageUserManager");
		PageList page=smum.getPageSysMessageUsers(messageid, username, usertype, xueduan, isread, pageUtil.getOrderindex(), pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("messageid", messageid);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("xueduan", xueduan);
		httpServletRequest.setAttribute("isread", isread);
		httpServletRequest.setAttribute("pagelist", page);
		return actionMapping.findForward("messageuserlist");
	}
	
}