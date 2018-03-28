package com.util.socket.http;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.Base64;
import com.wkmk.sys.bo.SysPaymentAccount;
import com.wkmk.sys.bo.SysUserDisable;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.service.SysMessageInfoManager;
import com.wkmk.sys.service.SysMessageUserManager;
import com.wkmk.sys.service.SysPaymentAccountManager;
import com.wkmk.sys.service.SysUserDisableManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.sys.web.action.SysMessageUserAction;
import com.wkmk.util.common.Constants;
import com.wkmk.zx.bo.ZxHelpOrder;
import com.wkmk.zx.bo.ZxHelpQuestion;
import com.wkmk.zx.service.ZxHelpOrderManager;
import com.wkmk.zx.service.ZxHelpQuestionManager;

/**
 * 自动化订单处理回调地址
 * @version 1.0
 */
public class AutomationOrderAction extends BaseAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			String params = request.getParameter("params");
			String paramsJsonStr = Base64.decode(params);
			JSONObject json = JSONObject.fromObject(paramsJsonStr);

			result = doPaymentAccount(json, request);
			if (result != null && !"".equals(result)) {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(result);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write("0");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/*
	 * 答疑订单自动处理
	 */
	private String doPaymentAccount(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String keyid = null;
			String keyvalue = null;
			try {
				keyid = json.getString("keyid");
			} catch (Exception e) {
				keyid = null;
			}
			try {
				keyvalue = json.getString("keyvalue");
			} catch (Exception e) {
				keyvalue = null;
			}

			if(keyid != null && keyvalue != null){
	    		if("1".equals(keyvalue)){
	        		SysPaymentAccountManager manager = (SysPaymentAccountManager) getBean("sysPaymentAccountManager");
	        		SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
	        		SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
	        		ZxHelpQuestionManager zhqm = (ZxHelpQuestionManager) getBean("zxHelpQuestionManager");
	        		SysPaymentAccount account = manager.getSysPaymentAccount(keyid);
	        		if("1".equals(account.getStatus())){
	        			//退款给学生
	        			account.setStatus("0");
	        			account.setChangetype(-1);
	        			account.setHappendate(DateTime.getDate());
	        			manager.updateSysPaymentAccount(account);
	        			
	        			SysUserInfo sui = suim.getSysUserInfo(account.getFromuserid());
	        			sui.setMoney(sui.getMoney() + account.getChangemoney());
	        			suim.updateSysUserInfo(sui);
	        			
	        			if("1".equals(account.getOuttype())){//在线答疑
	        				ZxHelpQuestion question = zhqm.getZxHelpQuestion(account.getOutobjid());
	        	        			
		        			SysUserMoney sysUserMoney = new SysUserMoney();
		    				String title = "在线答疑提问《" + question.getTitle() + "》无人回答自动退款";
		    				String descript = "在线答疑提问《" + question.getTitle() + "》无人回答自动退款";
		    				sysUserMoney.setTitle(title);
		    				sysUserMoney.setChangemoney(account.getChangemoney());
		    				sysUserMoney.setChangetype(1);
		    				sysUserMoney.setLastmoney(sui.getMoney());
		    				sysUserMoney.setUserid(sui.getUserid());
		    				sysUserMoney.setCreatedate(DateTime.getDate());
		    				sysUserMoney.setUserip(account.getFromuserip());
		    				sysUserMoney.setDescript(descript);
		    				summ.addSysUserMoney(sysUserMoney);
		    				
		    				//【说明】无人回答的在线答疑，自动给改为免费答疑，方便供大家在线评价回复
		    				question.setMoney(0F);
		    				zhqm.updateZxHelpQuestion(question);
		    				
		    				//给学生发站内消息
		    				String msgtitle = "您发布的在线答疑提问无人回答，支付的" + account.getChangemoney() + "学币已退款到您的账户余额，请注意查收。";
		    				String msgcontent = "您发布的在线答疑提问《" + question.getTitle() + "》无人回答，支付的" + account.getChangemoney() + "学币已退款到您的账户余额，请注意查收。";
		    				SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
		    				SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
		    				SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, question.getSysUserInfo().getUserid(), smim, smum);
		    				
		    				//如果是老师抢单未回答，需要对老师进行惩罚
		    				ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		    				ZxHelpOrder order = zhom.getZxHelpOrderByQuestionid(question.getQuestionid().toString());
		    				if(order != null && "1".equals(order.getStatus())){
		    					//不管付费答疑还是免费答疑，都不可恶意抢单不回答
		    					Integer teacherid = order.getUserid();
		    					//规则：超过3次抢单成功未回复，禁用账号1周，每增加一次抢单成功但不回答问题，就多禁用1周
		    					int count = zhom.getCountZxHelpOrdersByUserid(teacherid.toString());
		    					if(count > 3){
		    						int days = (count - 3)*7;//禁用天数
		    						SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    						Calendar calendar=Calendar.getInstance();
		    						calendar.setTime(new Date());
		    						calendar.add(Calendar.DAY_OF_MONTH, days);
		    						String enddate=format.format(calendar.getTime());
		    						
		    						SysUserDisableManager sudm = (SysUserDisableManager) getBean("sysUserDisableManager");
		    						SysUserInfo teacher = suim.getSysUserInfo(teacherid);
		    						
		    						SysUserDisable sysUserDisable = new SysUserDisable();
		    						sysUserDisable.setSysUserInfo(teacher);
		    						sysUserDisable.setStarttime(DateTime.getDate());
		    						sysUserDisable.setEndtime(enddate);
		    						sysUserDisable.setDescript("当前账号被禁用" + (count-3) + "周，在线答疑抢单超过3次未回复，每增加一次抢单成功不回复问题，就多禁用1周。");
		    						sysUserDisable.setOutlinkid(order.getOrderid());
		    						sysUserDisable.setOutlinktype("2");
		    						sudm.addSysUserDisable(sysUserDisable);
		    						
		    						teacher.setStatus("2");
		    						suim.updateSysUserInfo(teacher);
		    					}
		    				}
	        			}else if("3".equals(account.getOuttype())){//在线批阅
	        				
	        			}
	        		}else if("2".equals(account.getStatus())){
	        			//打款给老师
	        			account.setStatus("0");
	        			account.setChangetype(1);
	        			account.setHappendate(DateTime.getDate());
	        			manager.updateSysPaymentAccount(account);
	        			
	        			SysUserInfo sui = suim.getSysUserInfo(account.getTouserid());
	        			sui.setMoney(sui.getMoney() + account.getChangemoney());
	        			suim.updateSysUserInfo(sui);
	        			
	        			if("1".equals(account.getOuttype())){//在线答疑
	        				ZxHelpQuestion question = zhqm.getZxHelpQuestion(account.getOutobjid());
	        				question.setReplystatus("3");
	        				zhqm.updateZxHelpQuestion(question);
	        				
	        				ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
	        				ZxHelpOrder order = zhom.getZxHelpOrderByQuestionid(question.getQuestionid().toString());
	        				order.setPaystatus("1");
	        				zhom.updateZxHelpOrder(order);
	        	        			
		        			SysUserMoney sysUserMoney = new SysUserMoney();
		        			String title = "回答在线答疑提问《" + question.getTitle() + "》获得报酬";
		    				String descript = "回答在线答疑提问《" + question.getTitle() + "》获得报酬";
		    				sysUserMoney.setTitle(title);
		    				sysUserMoney.setChangemoney(account.getChangemoney());
		    				sysUserMoney.setChangetype(1);
		    				sysUserMoney.setLastmoney(sui.getMoney());
		    				sysUserMoney.setUserid(sui.getUserid());
		    				sysUserMoney.setCreatedate(DateTime.getDate());
		    				sysUserMoney.setUserip(account.getFromuserip());
		    				sysUserMoney.setDescript(descript);
		    				summ.addSysUserMoney(sysUserMoney);
		    				
		    				//给老师和学生发站内消息
		    				String msgtitle = "您回答的在线答疑提问已给您付款了，请注意查收。";
		    				String msgcontent = "您回答的在线答疑提问《" + question.getTitle() + "》已给您付款，获得的报酬已转入到您的账户余额中，请注意查收。";
		    				SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
		    				SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
		    				SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, sui.getUserid(), smim, smum);
		    				
		    				msgtitle = "您发布的在线答疑提问由于" + Constants.AUTO_PAY_DAY + "天未确认付款，系统已自动给老师付款，交易完成。";
		    				msgcontent = "您发布的在线答疑提问《" + question.getTitle() + "》由于" + Constants.AUTO_PAY_DAY + "天未确认付款，系统已自动给老师  ["+sui.getUsername()+"] 付款，交易完成。";
		    				SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, question.getSysUserInfo().getUserid(), smim, smum);
	        			}else if("3".equals(account.getOuttype())){//在线批阅
	        				
	        			}
	        		}
	        	}
	    	}
			
			result = "1";
		} catch (Exception e) {
			result = "0";
			e.printStackTrace();
		}

		return result;
	}
}
