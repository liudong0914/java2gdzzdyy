package com.wkmk.sys.web.action;

import java.util.ArrayList;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.string.Encode;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.sys.web.form.SysUserMoneyActionForm;
import com.wkmk.util.sms.TextBookOrderSenderMailImpl;

/**
 *<p>Description: 用户交易记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserMoneyAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserMoneyManager manager = (SysUserMoneyManager)getBean("sysUserMoneyManager");
		SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		String descript = Encode.nullToBlank(httpServletRequest.getParameter("descript"));
		
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String xueduan = Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
		String changetype = Encode.nullToBlank(httpServletRequest.getParameter("changetype"));
		
		
		HttpSession session = httpServletRequest.getSession();
//		Integer unitid = (Integer) session.getAttribute("s_unitid");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getSysUserMoneysOfPage(title, username, createdate, descript, changetype, usertype, xueduan, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		ArrayList datalist = page.getDatalist();
		if(datalist !=null && datalist.size()>0){
			for(int i= 0;i<datalist.size();i++){
				SysUserMoney model = (SysUserMoney) datalist.get(i);
				Integer userid = model.getUserid();
				SysUserInfo userInfo = userInfoManager.getSysUserInfo(userid);
				model.setFlago(userInfo.getUsername());
			}
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("createdate", createdate);
		httpServletRequest.setAttribute("descript", descript);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("xueduan", xueduan);
		httpServletRequest.setAttribute("changetype", changetype);
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
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
		SysUserMoney model = new SysUserMoney();
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
		SysUserMoneyActionForm form = (SysUserMoneyActionForm)actionForm;
		SysUserMoneyManager manager = (SysUserMoneyManager)getBean("sysUserMoneyManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserMoney model = form.getSysUserMoney();
				manager.addSysUserMoney(model);
				addLog(httpServletRequest,"增加了一个用户交易记录");
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
		SysUserMoneyManager manager = (SysUserMoneyManager)getBean("sysUserMoneyManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysUserMoney model = manager.getSysUserMoney(objid);
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
		SysUserMoneyActionForm form = (SysUserMoneyActionForm)actionForm;
		SysUserMoneyManager manager = (SysUserMoneyManager)getBean("sysUserMoneyManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserMoney model = form.getSysUserMoney();
				manager.updateSysUserMoney(model);
				addLog(httpServletRequest,"修改了一个用户交易记录");
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
		SysUserMoneyManager manager = (SysUserMoneyManager)getBean("sysUserMoneyManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delSysUserMoney(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	
	private  void sendMsg(String clientSendString){
		try {
			System.out.println("开始发送邮件");
			
			TextBookOrderSenderMailImpl mailSender = new TextBookOrderSenderMailImpl();
			JavaMailSenderImpl senderImpl = mailSender.getJavaMailSenderImpl();
			
			// 设定收件人、寄件人、主题与内文
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "gbk");
			messageHelper.setFrom(mailSender.getSender());
			messageHelper.setTo("947390587@qq.com");
			//messageHelper.setTo("liud@dukeliud.cn");
			messageHelper.setSubject("(测试数据)教材订购成功，请尽快发货处理！");	
			
			messageHelper.setText("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\"></head><body>广东省中职德育云平台教材订购成功，请尽快安排教材发货服务。</br>订单数据：</br>" + clientSendString + "</br></body></html>", true);	
			
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}
	public ActionForward testMsg(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
//		sendMsg("教材名称:把立德树人制度化;</br>订购总数:100;</br>订购总价:1000;</br>收件人:张三;</br>收件人电话:13051120665;</br>收件地址:北京市西城区");
		final String clientSendString = "教材名称:把立德树人制度化;</br>订购总数:100;</br>订购总价:1000;</br>收件人:张三;</br>收件人电话:13051120665;</br>收件地址:北京市西城区";
		Runnable runnable = new Runnable() {
			public void run() {
				sendMsg(clientSendString);
			}
		}; 
		new Thread(runnable).start();
		System.out.println("测试邮件已发送===========");
		return null;
	}
}