package com.wkmk.pay.tencent.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.wkmk.edu.bo.EduCourseBuy;
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseFilm;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.service.EduCourseBuyManager;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseFilmManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.pay.tencent.common.XMLParser;
import com.wkmk.sys.bo.SysUserGiveMoney;
import com.wkmk.sys.bo.SysUserGiveMoneyActivity;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.bo.SysUserPay;
import com.wkmk.sys.bo.SysUserPayTrade;
import com.wkmk.sys.service.SysMessageInfoManager;
import com.wkmk.sys.service.SysMessageUserManager;
import com.wkmk.sys.service.SysUserGiveMoneyActivityManager;
import com.wkmk.sys.service.SysUserGiveMoneyManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.sys.service.SysUserPayManager;
import com.wkmk.sys.service.SysUserPayTradeManager;
import com.wkmk.sys.web.action.SysMessageUserAction;
import com.wkmk.tk.bo.TkTextBookBuy;
import com.wkmk.tk.bo.TkTextBookInfo;
import com.wkmk.tk.service.TkTextBookBuyManager;
import com.wkmk.tk.service.TkTextBookInfoManager;
import com.wkmk.util.sms.TextBookOrderSenderMailImpl;

public class PayWxpayNotifyAction extends BaseAction {

	/**
	 * 微信服务器异步通知页面
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) {
		   /*该部分有以下3小步骤
	　　　　1）解析传过来的流信息，通过重新签名的方式验证流中包含的信息的正确性。就是判断这个信息到底是不是微信发的
	　　　　2）return_code和result_code都是SUCCESS的话，处理商户自己的业务逻辑。就是订单的支付状态啊等一些信息。
	　　　　3）告诉微信，我收到你的返回值了。不用在发了。
			//支付完成后，微信会把相关支付和用户信息发送到商户设定的通知URL，
			//验证签名，并回应微信。
			//对后台通知交互时，如果微信收到商户的应答不是成功或超时，微信认为通知失败，
			//微信会通过一定的策略（如30分钟共8次）定期重新发起通知，
			//尽可能提高通知的成功率，但微信不保证通知最终能成功。
			
			//商户自行增加处理流程,
			//例如：更新订单状态
			//例如：数据库操作
			//例如：推送支付完成信息
		    */
		//获取微信POST过来反馈信息，和支付宝不一样的是，微信是用流传递数据过来
		System.out.println("进入回调方法");
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(),HTTP.UTF_8));
            String line = null;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String returnxml = new String(sb.toString().getBytes(request.getCharacterEncoding()), HTTP.UTF_8);
            
            Map<String,Object> map = XMLParser.getMapFromXML(returnxml);
            String result_code = (String) map.get("result_code");
            String return_code = (String) map.get("return_code");
            //支付成功
            if("SUCCESS".equals(result_code) && "SUCCESS".equals(return_code)){
            	String out_trade_no = (String) map.get("out_trade_no");
            	System.out.println("支付成功，订单号out_trade_no"+out_trade_no);
            	//注意：微信支付服务器需要等到此接口的返回消息，否则会重复发送此消息8次，直到得到正确的反馈
            	//所以需要验证是否已经处理成功，但返回给微信支付接口消息失败。成功说明已经处理了数据，无需再次处理数据
            	SysUserPayManager supManager = (SysUserPayManager) getBean("sysUserPayManager");
            	SysUserPay sup = supManager.getSysUserPayByOuttradeno(out_trade_no);
            	if(sup == null){
            		//判断该笔订单是否在商户网站中已经做过处理
                	SysUserPayTradeManager manager = (SysUserPayTradeManager) getBean("sysUserPayTradeManager");
        			SysUserPayTrade sysUserPayTrade = manager.getSysUserPayTradeByOuttradeno(out_trade_no);
        			if(sysUserPayTrade != null){
        				System.out.println("sysUserPayTrade"+sysUserPayTrade.getTradeid());
        				sysUserPayTrade.setOuttradeno(out_trade_no);
        				sysUserPayTrade.setState("2");//完成支付
        				manager.updateSysUserPayTrade(sysUserPayTrade);
                    	
        				if("1".equals(sysUserPayTrade.getTradetype())){
        					SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
            				SysUserInfo sui = suim.getSysUserInfo(sysUserPayTrade.getUserid());
            				sui.setMoney(sui.getMoney() + sysUserPayTrade.getAmount());
            				suim.updateSysUserInfo(sui);
            				
            				//记录账号消费详情
            				SysUserPayManager supm = (SysUserPayManager) getBean("sysUserPayManager");
            				SysUserPay sysUserPay = new SysUserPay();
            				sysUserPay.setChangemoney(sysUserPayTrade.getAmount());
            				sysUserPay.setChangetype(1);
            				sysUserPay.setLastmoney(sui.getMoney());
            				sysUserPay.setRemark("微信在线充值");
            				sysUserPay.setUserid(sui.getUserid());
            				sysUserPay.setUserip(sysUserPayTrade.getUserip());
            				sysUserPay.setCreatedate(DateTime.getDate());
            				sysUserPay.setPaytype("wxpay");
            				sysUserPay.setOuttradeno(out_trade_no);
            				sysUserPay.setTradeno((String) map.get("transaction_id"));//微信系统订单
            				sysUserPay.setOpenid((String) map.get("openid"));
            				sysUserPay.setFeetype((String) map.get("fee_type"));
            				sysUserPay.setBanktype((String) map.get("bank_type"));
            				supm.addSysUserPay(sysUserPay);
            				
            				//记录用户可查询记录
            				SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
            				SysUserMoney sum = new SysUserMoney();
            				sum.setTitle("微信在线充值");
            				sum.setChangemoney(sysUserPayTrade.getAmount());
            				sum.setChangetype(1);
            				sum.setLastmoney(sui.getMoney());
            				sum.setUserid(sui.getUserid());
            				sum.setUserip(sysUserPayTrade.getUserip());
            				sum.setCreatedate(DateTime.getDate());
            				sum.setDescript("微信在线充值");
            				summ.addSysUserMoney(sum);
            				
            				//启动线程，计算充值活动
            				final SysUserInfo sysUserInfo = sui;
            				final SysUserInfoManager suim0 = suim;
            				final String userip = sysUserPayTrade.getUserip();
            				final SysUserPayTrade payTrade = sysUserPayTrade;
            				Runnable runnable = new Runnable() {
            					public void run() {
            						giveMoneyActivity(sysUserInfo, suim0, userip, payTrade);
            					}
            				};
            				Thread thread = new Thread(runnable);
            				thread.start();
        				}else if("5".equals(sysUserPayTrade.getTradetype())){
        					SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
            				SysUserInfo sui = suim.getSysUserInfo(sysUserPayTrade.getUserid());
            				
            				//记录账号消费详情
            				SysUserPayManager supm = (SysUserPayManager) getBean("sysUserPayManager");
            				SysUserPay sysUserPay = new SysUserPay();
            				sysUserPay.setChangemoney(sysUserPayTrade.getAmount());
            				sysUserPay.setChangetype(1);//在线微信支付消费，属于在线充值的另一种方式，对系统而言属于收入
            				sysUserPay.setLastmoney(sui.getMoney());
            				sysUserPay.setRemark(sysUserPayTrade.getBody());
            				sysUserPay.setUserid(sui.getUserid());
            				sysUserPay.setUserip(sysUserPayTrade.getUserip());
            				sysUserPay.setCreatedate(DateTime.getDate());
            				sysUserPay.setPaytype("wxpay");
            				sysUserPay.setOuttradeno(out_trade_no);
            				sysUserPay.setTradeno((String) map.get("transaction_id"));//微信系统订单
            				sysUserPay.setOpenid((String) map.get("openid"));
            				sysUserPay.setFeetype((String) map.get("fee_type"));
            				sysUserPay.setBanktype((String) map.get("bank_type"));
            				supm.addSysUserPay(sysUserPay);
            				
            				//2、记录交易详情
            				EduCourseFilmManager ecfm = (EduCourseFilmManager) getBean("eduCourseFilmManager");
            				EduCourseFilm eduCourseFilm = ecfm.getEduCourseFilm(sysUserPayTrade.getTradetypeid());
            				
            				EduCourseInfoManager ecim = (EduCourseInfoManager) getBean("eduCourseInfoManager");
                        	EduCourseInfo eduCourseInfo = ecim.getEduCourseInfo(eduCourseFilm.getCourseid());
            				
            				SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
            				SysUserMoney sysUserMoney = new SysUserMoney();
            				String title = "购买了《" + eduCourseInfo.getTitle() + "》的课程微课【" + eduCourseFilm.getTitle() + "】";
                            String descript = "购买了《" + eduCourseInfo.getTitle()  + "》的课程微课【" + eduCourseFilm.getTitle() + "】";
            				sysUserMoney.setTitle(title);
            				sysUserMoney.setChangemoney(eduCourseFilm.getSellprice());
            				sysUserMoney.setChangetype(-1);
            				sysUserMoney.setLastmoney(sui.getMoney());
            				sysUserMoney.setUserid(sui.getUserid());
            				sysUserMoney.setCreatedate(DateTime.getDate());
            				sysUserMoney.setUserip(sysUserPayTrade.getUserip());
            				sysUserMoney.setDescript(descript);
            				summ.addSysUserMoney(sysUserMoney);
            				//记录购买数据
                            EduCourseBuyManager ecbm = (EduCourseBuyManager) getBean("eduCourseBuyManager");
                            EduCourseBuy eduCourseBuy = new EduCourseBuy();
                            eduCourseBuy.setCoursefilmid(eduCourseFilm.getCoursefilmid());
                            eduCourseBuy.setCourseid(eduCourseInfo.getCourseid());
                            eduCourseBuy.setPrice(eduCourseFilm.getPrice());
                            eduCourseBuy.setDiscount(eduCourseFilm.getDiscount());
                            eduCourseBuy.setSellprice(eduCourseFilm.getSellprice());
                            eduCourseBuy.setCreatedate(DateTime.getDate());
                            eduCourseBuy.setEnddate("2088-08-08 20:00:00");
                            eduCourseBuy.setUserid(sui.getUserid());
                            ecbm.addEduCourseBuy(eduCourseBuy);
                            //记录微课销量
                            eduCourseFilm.setSellcount(eduCourseFilm.getSellcount() + 1);
                            ecfm.updateEduCourseFilm(eduCourseFilm);
                            
                            //购买微课的同时，加入这个课程，并且默认进入最新班级
                            EduCourseUserManager ecum = (EduCourseUserManager) getBean("eduCourseUserManager");
                            List<SearchModel> condition = new ArrayList<SearchModel>();
                            SearchCondition.addCondition(condition, "courseid", "=", eduCourseInfo.getCourseid());
                            SearchCondition.addCondition(condition, "userid", "=", sui.getUserid());
                            List list = ecum.getEduCourseUsers(condition, "", 1);
                            if(list == null || list.size() == 0){
                         	   //获取当前课程最新课程班批次
             					EduCourseClassManager eccm = (EduCourseClassManager) getBean("eduCourseClassManager");
             					condition.clear();
             					SearchCondition.addCondition(condition, "courseid", "=", eduCourseFilm.getCourseid());
             					SearchCondition.addCondition(condition, "status", "=", "1");
             					List lst = eccm.getEduCourseClasss(condition, "enddate desc", 1);
             					if(lst != null && lst.size() > 0){
             						EduCourseClass eduCourseClass = (EduCourseClass) lst.get(0);
             						//说明并未加入这个课程
             		                EduCourseUser model = new EduCourseUser();
             		                model.setCourseclassid(eduCourseClass.getCourseclassid());
             		                model.setCourseid(eduCourseInfo.getCourseid());
             		                model.setUserid(sui.getUserid());
             		                model.setStatus("1");
             		                model.setCreatedate(DateTime.getDate());
             		                model.setUsertype("3");
             		                model.setVip("0");
             		                ecum.addEduCourseUser(model);
             					}else {
             						//当前课程没有课程班
             					}
                            }
        				}else if("6".equals(sysUserPayTrade.getTradetype())){
        					SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
            				SysUserInfo sui = suim.getSysUserInfo(sysUserPayTrade.getUserid());
            				
            				//记录账号消费详情
            				SysUserPayManager supm = (SysUserPayManager) getBean("sysUserPayManager");
            				SysUserPay sysUserPay = new SysUserPay();
            				sysUserPay.setChangemoney(sysUserPayTrade.getAmount());
            				sysUserPay.setChangetype(1);//在线微信支付消费，属于在线充值的另一种方式，对系统而言属于收入
            				sysUserPay.setLastmoney(sui.getMoney());
            				sysUserPay.setRemark(sysUserPayTrade.getBody());
            				sysUserPay.setUserid(sui.getUserid());
            				sysUserPay.setUserip(sysUserPayTrade.getUserip());
            				sysUserPay.setCreatedate(DateTime.getDate());
            				sysUserPay.setPaytype("wxpay");
            				sysUserPay.setOuttradeno(out_trade_no);
            				sysUserPay.setTradeno((String) map.get("transaction_id"));//微信系统订单
            				sysUserPay.setOpenid((String) map.get("openid"));
            				sysUserPay.setFeetype((String) map.get("fee_type"));
            				sysUserPay.setBanktype((String) map.get("bank_type"));
            				supm.addSysUserPay(sysUserPay);
            				
            				//修改订购单状态
	        				  TkTextBookBuyManager ttbbm=(TkTextBookBuyManager)getBean("tkTextBookBuyManager");
	        				  TkTextBookBuy ttbb = ttbbm.getTkTextBookBuy(sysUserPayTrade.getTradetypeid());
	        				  ttbb.setStatus("1");
	        				  ttbbm.updateTkTextBookBuy(ttbb);
	        				
	        				  TkTextBookInfoManager ttbim=(TkTextBookInfoManager)getBean("tkTextBookInfoManager");
            				  TkTextBookInfo bookInfo = ttbim.getTkTextBookInfo(ttbb.getTextbookid());
            				  
            				//启动线程，发送通知邮件
        	                //sendMsg("教材名称:把立德树人制度化;</br>订购总数:100;</br>订购总价:1000;</br>收件人:张三;</br>收件人电话:13051120665;</br>收件地址:北京市西城区");
        		            final String clientSendString = "教材名称："+bookInfo.getTextbookname()+";</br>订购总数："+ttbb.getTotalnum()+";</br>订购总价："+ttbb.getTotalprice()+";</br>收件人："+ttbb.getRecipientname()+";</br>收件人电话："+ttbb.getRecipientphone()+";</br>收件地址："+ttbb.getRecipientaddress();
        					Runnable runnable = new Runnable() {
        						public void run() {
        							sendMsg(clientSendString);
        						}
        					}; 
        					new Thread(runnable).start();
        				}
        				
        				PrintWriter out = httpServletResponse.getWriter();
        				out.write(returnxml);
        			}else {
        				PrintWriter out = httpServletResponse.getWriter();
        				out.write("");
    				}
            	}else {
            		//如果已经等得支付回调信息，直接返回成功
            		PrintWriter out = httpServletResponse.getWriter();
    				out.write(returnxml);
				}
            }else {
            	PrintWriter out = httpServletResponse.getWriter();
				out.write("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private void giveMoneyActivity(SysUserInfo sysUserInfo, SysUserInfoManager suim, String userip, SysUserPayTrade payTrade){
		//获取当前是否有充值活动，按最新设置时间为准
		String curdate = DateTime.getDate();
		SysUserGiveMoneyActivityManager sugmam = (SysUserGiveMoneyActivityManager) getBean("sysUserGiveMoneyActivityManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1' and (a.type='2' or a.type='3') and 1='1");
		SearchCondition.addCondition(condition, "startdate", "<=", curdate);
		SearchCondition.addCondition(condition, "enddate", ">=", curdate);
		List list = sugmam.getSysUserGiveMoneyActivitys(condition, "startdate desc", 0);
		if(list != null && list.size() > 0){
			SysUserGiveMoneyActivity moneyActivity = (SysUserGiveMoneyActivity) list.get(0);
			if("2".equals(moneyActivity.getType())){
				//在充值买多少送多少的活动期间，赠送总额不能超过设置的赠送最大值
				//查询活动期间已赠送龙币数
				SysUserGiveMoneyManager sugmm = (SysUserGiveMoneyManager) getBean("sysUserGiveMoneyManager");
				Float totalGiveMoney = sugmm.getSysUserGiveMoneyByUserid(payTrade.getUserid(), moneyActivity.getStartdate(), moneyActivity.getEnddate(), "2");
				if(totalGiveMoney < moneyActivity.getMaxmoney()){
					float surplusMoney = moneyActivity.getMaxmoney() - totalGiveMoney;//剩余赠送总额
					float giveMoney = payTrade.getAmount();
					if(surplusMoney < payTrade.getAmount()){
						giveMoney = surplusMoney;
					}
					
					sysUserInfo.setMoney(sysUserInfo.getMoney() + giveMoney);
					suim.updateSysUserInfo(sysUserInfo);
					
					//记录用户可查询记录
					SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
					SysUserMoney sum = new SysUserMoney();
					sum.setTitle("微信在线充值赠送活动返现入账");
					sum.setChangemoney(giveMoney);
					sum.setChangetype(1);
					sum.setLastmoney(sysUserInfo.getMoney());
					sum.setUserid(sysUserInfo.getUserid());
					sum.setUserip(payTrade.getUserip());
					sum.setCreatedate(DateTime.getDate());
					sum.setDescript("微信在线充值赠送活动返现入账");
					summ.addSysUserMoney(sum);
					
					//记录赠送龙币数
					SysUserGiveMoney sysUserGiveMoney = new SysUserGiveMoney();
					sysUserGiveMoney.setUserid(sysUserInfo.getUserid());
					sysUserGiveMoney.setUserip(payTrade.getUserip());
					sysUserGiveMoney.setMoney(giveMoney);
					sysUserGiveMoney.setCreatedate(DateTime.getDate());
					sysUserGiveMoney.setType("2");
					sugmm.addSysUserGiveMoney(sysUserGiveMoney);
				}else {
					//发消息给充值用户，已达到赠送最大额度
    				String msgtitle = "当前充值赠送学币活动，您已获得了此活动期间总额赠送最高" + moneyActivity.getMaxmoney() + "学币额度，请期待下次的活动，谢谢！";
    				String msgcontent = "当前充值赠送学币活动，您已获得了此活动期间总额赠送最高" + moneyActivity.getMaxmoney() + "学币额度，请期待下次的活动，谢谢！";
    				SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
    				SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
    				SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, payTrade.getUserid(), smim, smum);
				}
			}else if("3".equals(moneyActivity.getType())){
				//打折赠送龙币数，先把支付的金额存入账户，再把折扣的龙币存入账户。分开存入，明细清晰。类似移动充值，9.5折，0.5折扣充入副账户
				float giveMoney = payTrade.getPrice() - payTrade.getAmount();
				sysUserInfo.setMoney(sysUserInfo.getMoney() + giveMoney);
				suim.updateSysUserInfo(sysUserInfo);
				
				//记录用户可查询记录
				SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
				SysUserMoney sum = new SysUserMoney();
				sum.setTitle("微信在线充值折扣活动返现入账");
				sum.setChangemoney(giveMoney);
				sum.setChangetype(1);
				sum.setLastmoney(sysUserInfo.getMoney());
				sum.setUserid(sysUserInfo.getUserid());
				sum.setUserip(payTrade.getUserip());
				sum.setCreatedate(DateTime.getDate());
				sum.setDescript("微信在线充值折扣活动返现入账");
				summ.addSysUserMoney(sum);
				
				//记录赠送龙币数
				SysUserGiveMoneyManager sugmm = (SysUserGiveMoneyManager) getBean("sysUserGiveMoneyManager");
				SysUserGiveMoney sysUserGiveMoney = new SysUserGiveMoney();
				sysUserGiveMoney.setUserid(sysUserInfo.getUserid());
				sysUserGiveMoney.setUserip(payTrade.getUserip());
				sysUserGiveMoney.setMoney(giveMoney);
				sysUserGiveMoney.setCreatedate(DateTime.getDate());
				sysUserGiveMoney.setType("3");
				sugmm.addSysUserGiveMoney(sysUserGiveMoney);
			}
		}
	}
	
	private void sendMsg(String clientSendString){
		try {
			TextBookOrderSenderMailImpl mailSender = new TextBookOrderSenderMailImpl();
			JavaMailSenderImpl senderImpl = mailSender.getJavaMailSenderImpl();
			
			// 设定收件人、寄件人、主题与内文
			MimeMessage mailMessage = senderImpl.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "gbk");
			messageHelper.setFrom(mailSender.getSender());
			messageHelper.setTo("5471946@qq.com");
			messageHelper.setSubject("教材订购成功，请尽快发货处理！");	
			
			messageHelper.setText("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html;charset=gbk\"></head><body>广东省中职德育云平台教材订购成功，请尽快安排教材发货服务。</br>订单数据：</br>" + clientSendString + "</br></body></html>", true);	
			
			senderImpl.send(mailMessage);
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}
}