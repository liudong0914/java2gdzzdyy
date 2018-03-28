package com.wkmk.zx.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.sys.bo.SysPaymentAccount;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserMoney;
import com.wkmk.sys.service.SysMessageInfoManager;
import com.wkmk.sys.service.SysMessageUserManager;
import com.wkmk.sys.service.SysPaymentAccountManager;
import com.wkmk.sys.service.SysSmsInfoManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserMoneyManager;
import com.wkmk.sys.web.action.SysMessageUserAction;
import com.wkmk.util.common.ArithUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;
import com.wkmk.util.file.ConvertFile;
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
import com.wkmk.zx.web.form.ZxHelpQuestionComplaintActionForm;

/**
 *<p>Description: 在线答疑投诉</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionComplaintAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ZxHelpQuestionComplaintManager manager = (ZxHelpQuestionComplaintManager)getBean("zxHelpQuestionComplaintManager");
		SysUserInfoManager userinfomanager = (SysUserInfoManager)getBean("sysUserInfoManager");
		Integer userid_reply = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
	    String descript = Encode.nullToBlank(httpServletRequest.getParameter("descript"));
        String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
        String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
        
        List<SearchModel> condition = new ArrayList<SearchModel>();
        if(!"".equals(descript)){
            SearchCondition.addCondition(condition, "descript", "like", "%" + descript + "%");
        }
        if (!"".equals(createdate)) {
            SearchCondition.addCondition(condition, "createdate", "like", createdate + "%");
        }
        if(!"".equals(status)){
            SearchCondition.addCondition(condition, "status", "=", status);
        }
        
        PageUtil pageUtil = new PageUtil(httpServletRequest);
        String sorderindex = "complaintid asc";
        if(!"".equals(pageUtil.getOrderindex())){
            sorderindex = pageUtil.getOrderindex();
        }
		PageList page = manager.getPageZxHelpQuestionComplaints(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		ArrayList datalist = page.getDatalist();
		if(datalist !=null && datalist.size()>0){
		    for(int i=0; i<datalist.size(); i++){
		        ZxHelpQuestionComplaint model = (ZxHelpQuestionComplaint) datalist.get(i);
		        Integer userid = model.getUserid();
		        SysUserInfo userInfo = userinfomanager.getSysUserInfo(userid);
		        model.setFlagl(userInfo.getUsername());//投诉人姓名
		        Integer replyuserid = model.getReplyuserid();
		        if(replyuserid !=null && replyuserid.intValue()>0){
		            SysUserInfo userInfo_reply = userinfomanager.getSysUserInfo(replyuserid);
		            model.setFlago(userInfo_reply.getUsername());//处理人姓名
		        }else{
		            model.setFlago("");//处理人姓名
		        }
		    }
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("descript", descript);
        httpServletRequest.setAttribute("createdate", createdate);
        httpServletRequest.setAttribute("status", status);
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
		ZxHelpQuestionComplaint model = new ZxHelpQuestionComplaint();
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
		ZxHelpQuestionComplaintActionForm form = (ZxHelpQuestionComplaintActionForm)actionForm;
		ZxHelpQuestionComplaintManager manager = (ZxHelpQuestionComplaintManager)getBean("zxHelpQuestionComplaintManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				ZxHelpQuestionComplaint model = form.getZxHelpQuestionComplaint();
				manager.addZxHelpQuestionComplaint(model);
				addLog(httpServletRequest,"增加了一个在线答疑投诉");
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
		ZxHelpQuestionComplaintManager manager = (ZxHelpQuestionComplaintManager)getBean("zxHelpQuestionComplaintManager");
		  ZxHelpQuestionManager questionmanager = (ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
	        SysUserInfoManager userinfomanager = (SysUserInfoManager)getBean("sysUserInfoManager");
	        ZxHelpFileManager fileManager = (ZxHelpFileManager)getBean("zxHelpFileManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		ZxHelpOrderManager orderManager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
        ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager)getBean("zxHelpAnswerManager");
		try {
		    ZxHelpQuestionComplaint model = manager.getZxHelpQuestionComplaint(objid);
            Integer questionid = model.getQuestionid();
            //根据提问的id，获取提问详情
            ZxHelpQuestion zxHelpQuestion = questionmanager.getZxHelpQuestion(questionid);
            Integer userid = model.getUserid();
            SysUserInfo userInfo = userinfomanager.getSysUserInfo(userid);
            model.setFlagl(userInfo.getUsername());//投诉人姓名
            Integer replyuserid = model.getReplyuserid();
            if(replyuserid !=null && replyuserid.intValue()>0){
                SysUserInfo userInfo_reply = userinfomanager.getSysUserInfo(replyuserid);
                model.setFlago(userInfo_reply.getUsername());//处理人姓名
            }else{
                model.setFlago("");//处理人姓名
            }
            String status = model.getStatus();
            httpServletRequest.setAttribute("status_obj", status);
            httpServletRequest.setAttribute("model", model);
            httpServletRequest.setAttribute("zxHelpQuestion", zxHelpQuestion);
			httpServletRequest.setAttribute("act", "updateSave");
			
			  //展示学生问题中的视频，音频，图片
            List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "type", "=",  "1");
            SearchCondition.addCondition(condition, "questionid", "=",  objid);
            SearchCondition.addCondition(condition, "filetype", "!=",  "3");
            List files = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
            List list1 = new ArrayList();//存放图片标题和文件路径
            List list2 = new ArrayList();//存放音频标题和文件路径
            String openconvertservice = ConvertFile.openconvertservice;
            String flag ="0";
            if(files !=null && files.size()>0){
                for (int i = 0; i < files.size(); i++)
                {
                    ZxHelpFile file = (ZxHelpFile) files.get(i);
                    String filetype = file.getFiletype();//文件类型，1图片，2音频，3上传的视频
                    if(filetype.equals("1")){
                        file.setFlagl("图片_"+file.getFileid());
                        file.setFlago(file.getFilepath());
                        list1.add(file);
                    }
                    if(filetype.equals("2")){
                        if("1".equals(openconvertservice)){
                            file.setFlagl("录音_"+file.getFileid());
                            file.setFlago(file.getMp3path());
                            list2.add(file);
                        }else{
                            file.setFlagl("录音_"+file.getFileid());
                            file.setFlago(file.getFilepath());
                            list2.add(file);
                        }
                    }
                    flag ="1";
                }
            }
            
            //学生提问的视频
            condition.clear();
            SearchCondition.addCondition(condition, "type", "=",  "1");
            SearchCondition.addCondition(condition, "questionid", "=",  objid);
            SearchCondition.addCondition(condition, "filetype", "=",  "3");
            List videoFiles = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
            
            httpServletRequest.setAttribute("files", files);
            httpServletRequest.setAttribute("videoFiles", videoFiles);
            httpServletRequest.setAttribute("list1", list1);
            httpServletRequest.setAttribute("list2", list2);
            
            ZxHelpOrder zxHelpOrder = orderManager.getZxHelpOrderByQuestionid(questionid.toString());
            //展示教师回复中的视频，音频，图片
            List files_order = new ArrayList();
            List videoFilesOrder = new ArrayList();
            List list3 = new ArrayList();//存放图片标题和文件路径
            List list4 = new ArrayList();//存放音频标题和文件路径
            String content = "";
            if(zxHelpOrder.getStatus().equals("2")){
                ZxHelpAnswer zxHelpAnswer = answerManager.getZxHelpAnswerByOrderid(zxHelpOrder.getOrderid().toString());
//                content = zxHelpAnswer.getContent();
                Integer userid2 = zxHelpAnswer.getUserid();
                SysUserInfo replayUserInfo = userinfomanager.getSysUserInfo(userid2);
                String replayUsername = replayUserInfo.getUsername();
                zxHelpAnswer.setFlagl(replayUsername);
                httpServletRequest.setAttribute("zxHelpAnswer", zxHelpAnswer);
                condition.clear();
                SearchCondition.addCondition(condition, "type", "=",  "2");
                SearchCondition.addCondition(condition, "answerid", "=",  zxHelpAnswer.getAnswerid());
                SearchCondition.addCondition(condition, "filetype", "!=",  "3");
                files_order = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
                if(files_order !=null && files_order.size()>0){
                    for (int i = 0; i < files_order.size(); i++)
                    {
                        ZxHelpFile file = (ZxHelpFile) files_order.get(i);
                        String filetype = file.getFiletype();//文件类型，1图片，2音频，3上传的视频
                        if(filetype.equals("1")){
                            file.setFlagl("图片_"+file.getFileid());
                            file.setFlago(file.getFilepath());
                            list3.add(file);
                        }
                        if(filetype.equals("2")){
                            if("1".equals(openconvertservice)){
                                file.setFlagl("录音_"+file.getFileid());
                                file.setFlago(file.getMp3path());
                                list4.add(file);
                            }else{
                                file.setFlagl("录音_"+file.getFileid());
                                file.setFlago(file.getFilepath());
                                list4.add(file);
                            }
                        }
                        flag ="1";
                    }
                }
                
                //教师回复的视频
                condition.clear();
                SearchCondition.addCondition(condition, "type", "=",  "2");
                SearchCondition.addCondition(condition, "answerid", "=",  zxHelpAnswer.getAnswerid());
                SearchCondition.addCondition(condition, "filetype", "=",  "3");
                videoFilesOrder = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
            }
            
            httpServletRequest.setAttribute("files_order", files_order);
            httpServletRequest.setAttribute("videoFilesOrder", videoFilesOrder);
            httpServletRequest.setAttribute("list3", list3);
            httpServletRequest.setAttribute("list4", list4);
            
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
		ZxHelpQuestionComplaintActionForm form = (ZxHelpQuestionComplaintActionForm)actionForm;
		ZxHelpQuestionComplaintManager manager = (ZxHelpQuestionComplaintManager)getBean("zxHelpQuestionComplaintManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
		SysMessageInfoManager messageInfoManager = (SysMessageInfoManager)getBean("sysMessageInfoManager");
		final SysMessageUserManager mumanager=(SysMessageUserManager)getBean("sysMessageUserManager");
		 SysPaymentAccountManager spam = (SysPaymentAccountManager) getBean("sysPaymentAccountManager");
		 ZxHelpOrderManager zhom = (ZxHelpOrderManager) getBean("zxHelpOrderManager");
		//final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd　HH:mm:ss");
		//final SysSmsInfoManager smsmanager=(SysSmsInfoManager)getBean("sysSmsInfoManager");
		//SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
		Integer userid_reply = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
//		String status_obj = Encode.nullToBlank(httpServletRequest.getParameter("status_obj"));
		if (isTokenValid(httpServletRequest, true)) {
			try {
				ZxHelpQuestionComplaint model = form.getZxHelpQuestionComplaint();
				model.setReplyuserid(userid_reply);//处理人
				model.setReplyuserip(IpUtil.getIpAddr(httpServletRequest));//处理人ip
				model.setReplycreatedate(DateTime.getDate());//处理时间
//				model.setStatus(status_obj);//处理状态
				Integer questionid = model.getQuestionid();
				ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
				if(model.getStatus().equals("1")){
				    //投诉成功，需要要教师获得的奖金原路返回学生
				    //1.提问
				    zxHelpQuestion.setStatus("3");//投诉成功
				    
				    //需要将钱返回学生
				    List<SearchModel> condition = new ArrayList<SearchModel>();
                    SearchCondition.addCondition(condition, "fromuserid", "=", Integer.valueOf(model.getUserid()));
                    SearchCondition.addCondition(condition, "touserid", "=", model.getTeacherid());
                    SearchCondition.addCondition(condition, "status", "=", "3");
                    SearchCondition.addCondition(condition, "outtype", "=", "1");//关联答疑
                    SearchCondition.addCondition(condition, "outobjid", "=", Integer.valueOf(questionid));
                    List list = spam.getSysPaymentAccounts(condition, "", 0);
                    if(list != null && list.size() > 0){
                        SysPaymentAccount account = (SysPaymentAccount) list.get(0);
                        
                        SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
                        SysUserInfo userInfo = suim.getSysUserInfo(model.getUserid());
                        userInfo.setMoney(userInfo.getMoney() + account.getChangemoney());
                        suim.updateSysUserInfo(userInfo);
                        
                        account.setChangetype(2);
                        account.setHappendate(DateTime.getDate());
                        account.setStatus("0");
                        spam.updateSysPaymentAccount(account);
                        
                        //记录消费详情
                        SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
                        SysUserMoney sysUserMoney = new SysUserMoney();
                        String title = "在线答疑提问《" + zxHelpQuestion.getTitle() + "》的回复被投诉成功，报酬返还账户";
                        String descript = "在线答疑提问《" + zxHelpQuestion.getTitle() + "》的回复被投诉成功，报酬返还账户";
                        sysUserMoney.setTitle(title);
                        sysUserMoney.setChangemoney(account.getChangemoney());
                        sysUserMoney.setChangetype(1);
                        sysUserMoney.setLastmoney(userInfo.getMoney());
                        sysUserMoney.setUserid(userInfo.getUserid());
                        sysUserMoney.setCreatedate(DateTime.getDate());
                        sysUserMoney.setUserip(IpUtil.getIpAddr(httpServletRequest));
                        sysUserMoney.setDescript(descript);
                        summ.addSysUserMoney(sysUserMoney);
                    }  
				    //给学生发消息
				    String msgtitle = "您投诉的在线答疑提问回复已经被处理了，请注意看看。";
                    String msgcontent = "您投诉的在线答疑提问《" + zxHelpQuestion.getTitle() + "》回复已被处理，扣除的报酬将返还到您的账户余额中，请注意查收。";
                    SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
                    SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
                    SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, model.getUserid(), smim, smum);  
				    //给老师发消息
                    String msgtitle2 = "您回答的在线答疑提问被对方确认投诉了，请注意看看。";
                    String msgcontent2 = "您回答的在线答疑提问《" + zxHelpQuestion.getTitle() + "》被对方投诉，投诉已被成功处理，获得的报酬将无法转入到您的账户余额中，请注意查收。";
                    SysMessageUserAction.sendSystemMessage(msgtitle2, msgcontent2, model.getTeacherid(), smim, smum);     
				}else if(model.getStatus().equals("2")){
				    //投诉被驳回
				    //1.提问状态变为1
				    zxHelpQuestion.setStatus("1");
				    zxHelpQuestion.setReplystatus("3");//付款状态变为3
				    
				    //2.订单状态
	                ZxHelpOrder order = zhom.getZxHelpOrder(model.getOrderid());
	                order.setPaystatus("1");
	                zhom.updateZxHelpOrder(order);
	                
	                //向老师付款
	                List<SearchModel> condition2 = new ArrayList<SearchModel>();
	                SearchCondition.addCondition(condition2, "fromuserid", "=", Integer.valueOf(model.getUserid()));
	                SearchCondition.addCondition(condition2, "touserid", "=", order.getUserid());
	                SearchCondition.addCondition(condition2, "outtype", "=", "1");//关联答疑
	                SearchCondition.addCondition(condition2, "status", "=", "3");
	                SearchCondition.addCondition(condition2, "outobjid", "=", Integer.valueOf(questionid));
	                List list2 = spam.getSysPaymentAccounts(condition2, "", 0);
	                if(list2 != null && list2.size() > 0){
	                    SysPaymentAccount account = (SysPaymentAccount) list2.get(0);
	                    
	                    float addMoney = ArithUtil.round(account.getChangemoney()*Constants.ANSWER_QUESTION_PROPORTION);//老师回答提问，平台抽2成
	                    
	                    SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
	                    SysUserInfo teacher = suim.getSysUserInfo(order.getUserid());
	                    teacher.setMoney(teacher.getMoney() + addMoney);
	                    suim.updateSysUserInfo(teacher);
	                    
	                    account.setChangetype(1);
	                    account.setHappendate(DateTime.getDate());
	                    account.setStatus("0");
	                    spam.updateSysPaymentAccount(account);
	                    
	                    //记录消费详情
	                    SysUserMoneyManager summ = (SysUserMoneyManager) getBean("sysUserMoneyManager");
	                    SysUserMoney sysUserMoney = new SysUserMoney();
	                    String title = "回答在线答疑提问《" + zxHelpQuestion.getTitle() + "》获得报酬";
	                    String descript = "回答在线答疑提问《" + zxHelpQuestion.getTitle() + "》获得报酬";
	                    sysUserMoney.setTitle(title);
	                    sysUserMoney.setChangemoney(addMoney);
	                    sysUserMoney.setChangetype(1);
	                    sysUserMoney.setLastmoney(teacher.getMoney());
	                    sysUserMoney.setUserid(teacher.getUserid());
	                    sysUserMoney.setCreatedate(DateTime.getDate());
	                    sysUserMoney.setUserip(IpUtil.getIpAddr(httpServletRequest));
	                    sysUserMoney.setDescript(descript);
	                    summ.addSysUserMoney(sysUserMoney);
	                    
	                    //给老师发站内短信
	                    String msgtitle = "您回答的在线答疑提问被对方确认付款了，请注意查收。";
	                    String msgcontent = "您回答的在线答疑提问《" + zxHelpQuestion.getTitle() + "》被对方确认付款，获得的报酬已转入到您的账户余额中，请注意查收。";
	                    SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
	                    SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
	                    SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, teacher.getUserid(), smim, smum);
	                    //给学生发消息
	                    String msgtitle2 = "您投诉的在线答疑提问回复已经被驳回了，请注意看看。";
	                    String msgcontent2 = "您投诉的在线答疑提问《" + zxHelpQuestion.getTitle() + "》回复已被驳回，请注意看看。";
	                    SysMessageUserAction.sendSystemMessage(msgtitle2, msgcontent2, model.getUserid(), smim, smum);  
	                }
				}
				questionManager.updateZxHelpQuestion(zxHelpQuestion);
				manager.updateZxHelpQuestionComplaint(model);
				addLog(httpServletRequest,"修改了一个在线答疑投诉");
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
		ZxHelpQuestionComplaintManager manager = (ZxHelpQuestionComplaintManager)getBean("zxHelpQuestionComplaintManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delZxHelpQuestionComplaint(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 查看投诉详情
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2016-12-17 下午4:45:37 
	* @lastModified ; 2016-12-17 下午4:45:37 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
    public ActionForward view(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        ZxHelpQuestionComplaintManager manager = (ZxHelpQuestionComplaintManager)getBean("zxHelpQuestionComplaintManager");
        ZxHelpQuestionManager questionmanager = (ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
        SysUserInfoManager userinfomanager = (SysUserInfoManager)getBean("sysUserInfoManager");
        ZxHelpFileManager fileManager = (ZxHelpFileManager)getBean("zxHelpFileManager");
        ZxHelpOrderManager orderManager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
        ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager)getBean("zxHelpAnswerManager");
        String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
        try {
            ZxHelpQuestionComplaint model = manager.getZxHelpQuestionComplaint(objid);
            Integer questionid = model.getQuestionid();
            //根据提问的id，获取提问详情
            ZxHelpQuestion zxHelpQuestion = questionmanager.getZxHelpQuestion(questionid);
            Integer userid = model.getUserid();
            SysUserInfo userInfo = userinfomanager.getSysUserInfo(userid);
            model.setFlagl(userInfo.getUsername());//投诉人姓名
            Integer replyuserid = model.getReplyuserid();
            if(replyuserid !=null && replyuserid.intValue()>0){
                SysUserInfo userInfo_reply = userinfomanager.getSysUserInfo(replyuserid);
                model.setFlago(userInfo_reply.getUsername());//处理人姓名
            }else{
                model.setFlago("");//处理人姓名
            }
            httpServletRequest.setAttribute("model", model);
            httpServletRequest.setAttribute("zxHelpQuestion", zxHelpQuestion);
            
            //展示学生问题中的视频，音频，图片
            List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "type", "=",  "1");
            SearchCondition.addCondition(condition, "questionid", "=",  objid);
            SearchCondition.addCondition(condition, "filetype", "!=",  "3");
            List files = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
            List list1 = new ArrayList();//存放图片标题和文件路径
            List list2 = new ArrayList();//存放音频标题和文件路径
            String openconvertservice = ConvertFile.openconvertservice;
            String flag ="0";
            if(files !=null && files.size()>0){
                for (int i = 0; i < files.size(); i++)
                {
                    ZxHelpFile file = (ZxHelpFile) files.get(i);
                    String filetype = file.getFiletype();//文件类型，1图片，2音频，3上传的视频
                    if(filetype.equals("1")){
                        file.setFlagl("图片_"+file.getFileid());
                        file.setFlago(file.getFilepath());
                        list1.add(file);
                    }
                    if(filetype.equals("2")){
                        if("1".equals(openconvertservice)){
                            file.setFlagl("录音_"+file.getFileid());
                            file.setFlago(file.getMp3path());
                            list2.add(file);
                        }else{
                            file.setFlagl("录音_"+file.getFileid());
                            file.setFlago(file.getFilepath());
                            list2.add(file);
                        }
                    }
                    flag ="1";
                }
            }
            
            //学生提问的视频
            condition.clear();
            SearchCondition.addCondition(condition, "type", "=",  "1");
            SearchCondition.addCondition(condition, "questionid", "=",  objid);
            SearchCondition.addCondition(condition, "filetype", "=",  "3");
            List videoFiles = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
            
            httpServletRequest.setAttribute("files", files);
            httpServletRequest.setAttribute("videoFiles", videoFiles);
            httpServletRequest.setAttribute("list1", list1);
            httpServletRequest.setAttribute("list2", list2);
            
            
            ZxHelpOrder zxHelpOrder = orderManager.getZxHelpOrderByQuestionid(questionid.toString());
            //展示教师回复中的视频，音频，图片
            List files_order = new ArrayList();
            List videoFilesOrder = new ArrayList();
            List list3 = new ArrayList();//存放图片标题和文件路径
            List list4 = new ArrayList();//存放音频标题和文件路径
            String content = "";
            if(zxHelpOrder.getStatus().equals("2")){
                ZxHelpAnswer zxHelpAnswer = answerManager.getZxHelpAnswerByOrderid(zxHelpOrder.getOrderid().toString());
//                content = zxHelpAnswer.getContent();
                Integer userid2 = zxHelpAnswer.getUserid();
                SysUserInfo replayUserInfo = userinfomanager.getSysUserInfo(userid2);
                String replayUsername = replayUserInfo.getUsername();
                zxHelpAnswer.setFlagl(replayUsername);
                httpServletRequest.setAttribute("zxHelpAnswer", zxHelpAnswer);
                condition.clear();
                SearchCondition.addCondition(condition, "type", "=",  "2");
                SearchCondition.addCondition(condition, "answerid", "=",  zxHelpAnswer.getAnswerid());
                SearchCondition.addCondition(condition, "filetype", "!=",  "3");
                files_order = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
                if(files_order !=null && files_order.size()>0){
                    for (int i = 0; i < files_order.size(); i++)
                    {
                        ZxHelpFile file = (ZxHelpFile) files_order.get(i);
                        String filetype = file.getFiletype();//文件类型，1图片，2音频，3上传的视频
                        if(filetype.equals("1")){
                            file.setFlagl("图片_"+file.getFileid());
                            file.setFlago(file.getFilepath());
                            list3.add(file);
                        }
                        if(filetype.equals("2")){
                            if("1".equals(openconvertservice)){
                                file.setFlagl("录音_"+file.getFileid());
                                file.setFlago(file.getMp3path());
                                list4.add(file);
                            }else{
                                file.setFlagl("录音_"+file.getFileid());
                                file.setFlago(file.getFilepath());
                                list4.add(file);
                            }
                        }
                        flag ="1";
                    }
                }
                
                //教师回复的视频
                condition.clear();
                SearchCondition.addCondition(condition, "type", "=",  "2");
                SearchCondition.addCondition(condition, "answerid", "=",  zxHelpAnswer.getAnswerid());
                SearchCondition.addCondition(condition, "filetype", "=",  "3");
                videoFilesOrder = fileManager.getZxHelpFiles(condition, "fileid asc", 0);
            }
            
            httpServletRequest.setAttribute("files_order", files_order);
            httpServletRequest.setAttribute("videoFilesOrder", videoFilesOrder);
            httpServletRequest.setAttribute("list3", list3);
            httpServletRequest.setAttribute("list4", list4);
            
        }catch (Exception e) {
            e.printStackTrace();
        }
        return actionMapping.findForward("view");
    }
}