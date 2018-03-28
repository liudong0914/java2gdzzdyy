package com.wkmk.zx.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.concurrent.AutomationCache;
import com.util.date.DateTime;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.sys.bo.SysPaymentAccount;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysMessageInfoManager;
import com.wkmk.sys.service.SysMessageUserManager;
import com.wkmk.sys.service.SysPaymentAccountManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.action.SysMessageUserAction;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;
import com.wkmk.util.file.ConvertFile;
import com.wkmk.weixin.mp.MpUtil;
import com.wkmk.zx.bo.ZxHelpAnswer;
import com.wkmk.zx.bo.ZxHelpFile;
import com.wkmk.zx.bo.ZxHelpOrder;
import com.wkmk.zx.bo.ZxHelpQuestion;
import com.wkmk.zx.service.ZxHelpAnswerManager;
import com.wkmk.zx.service.ZxHelpFileManager;
import com.wkmk.zx.service.ZxHelpOrderManager;
import com.wkmk.zx.service.ZxHelpQuestionManager;
import com.wkmk.zx.web.form.ZxHelpOrderActionForm;

/**
 *<p>Description: 在线答疑订单</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpOrderAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		ZxHelpOrderManager manager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
        String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if (!"".equals(createdate)) {
            SearchCondition.addCondition(condition, "createdate", "like", createdate + "%");
        }
        if(!"".equals(status)){
            SearchCondition.addCondition(condition, "status", "=", status);
        }
		SearchCondition.addCondition(condition, "userid", "=", userid);
		PageList page = manager.getPageZxHelpOrders(condition, "createdate desc", pageUtil.getStartCount(), pageUtil.getPageSize());
		ArrayList datalist = page.getDatalist();
		if(datalist !=null && datalist.size()>0){
		    for (int i = 0; i < datalist.size(); i++)
            {
                ZxHelpOrder model = (ZxHelpOrder) datalist.get(i);
                Integer questionid = model.getQuestionid();
                ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
                String title = zxHelpQuestion.getTitle();
                model.setFlagl(title);
                String newDate = DateTime.getDate();//当前时间
                String enddate = model.getEnddate();//回复最后期限
                //如果过期，则无法回复，只能预览
                int res = newDate.compareTo(enddate);
                if(res>0)
                {
                  //超时
                    model.setStatus("3");
                }
                {
                    
                }
            }
		}
		httpServletRequest.setAttribute("pagelist", page);
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
		ZxHelpOrder model = new ZxHelpOrder();
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
		ZxHelpOrderActionForm form = (ZxHelpOrderActionForm)actionForm;
		ZxHelpOrderManager manager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				ZxHelpOrder model = form.getZxHelpOrder();
				manager.addZxHelpOrder(model);
				addLog(httpServletRequest,"增加了一个在线答疑订单");
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
		ZxHelpOrderManager manager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));//orderid
		try {
			ZxHelpOrder model = manager.getZxHelpOrder(objid);
			Integer questionid = model.getQuestionid();
			ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
			httpServletRequest.setAttribute("zxHelpQuestion", zxHelpQuestion);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}
	
	/**
	 * 添加视频
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2016-12-22 下午3:16:30 
	* @lastModified ; 2016-12-22 下午3:16:30 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward videoAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        ZxHelpOrderManager manager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
        ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
        String num = Encode.nullToBlank(httpServletRequest.getParameter("num"));
        try {
            httpServletRequest.setAttribute("act", "videoAddSave");
            httpServletRequest.setAttribute("num", num);
        }catch (Exception e){
        }
        return actionMapping.findForward("videoAdd");
    }
	
	/**
	 * 添加图片
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2016-12-22 下午4:35:40 
	* @lastModified ; 2016-12-22 下午4:35:40 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward pictureAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        ZxHelpOrderManager manager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
        ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
        String num = Encode.nullToBlank(httpServletRequest.getParameter("num"));
        try {
            httpServletRequest.setAttribute("act", "pictureAddSave");
            httpServletRequest.setAttribute("num", num);
        }catch (Exception e){
        }
        return actionMapping.findForward("pictureAdd");
    }
	
	public ActionForward videoAddSave(ActionMapping mapping, ActionForm form,
            HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            //视频影片
            ZxHelpFileManager zxHelpFileManager = (ZxHelpFileManager) getBean("zxHelpFileManager");
            String[] filename = httpServletRequest.getParameterValues("filename");
            String[] filepath = httpServletRequest.getParameterValues("filepath");
            String[] filesize = httpServletRequest.getParameterValues("filesize");
            //视频截屏参数
            String imgwidth = Encode.nullToBlank(httpServletRequest.getParameter("imgwidth"));
            String imgheight = Encode.nullToBlank(httpServletRequest.getParameter("imgheight"));
            String second = Encode.nullToBlank(httpServletRequest.getParameter("second"));
            String fileIds ="";
            String filenames="";
            if(filename != null && filename.length > 0){
                String fext = null;
                String fname = null;
                ZxHelpFile zhf = null;
                String[] paths = null;
                for(int i=0, size=filename.length; i<size; i++){
                    if(filepath[i] == null || "".equals(filepath[i]) || filepath[i].indexOf("error_") != -1){
                        continue;
                    }
                    if(filepath[i].indexOf("exist_") != -1){
                        //可以重复上传，只给用户提示，除非用户自己删除
                        filepath[i] = filepath[i].substring(6);
                    }
                    paths = filepath[i].split(";");
                    fext = FileUtil.getFileExt(filename[i]).toLowerCase();
                    fname = filename[i].substring(0,filename[i].lastIndexOf("."));
                    
                    zhf = new ZxHelpFile();
                    zhf.setFilepath(paths[0]);
                    //转码后的路径统一通过接口修改
                    zhf.setConvertstatus("0");
                    zhf.setFilesize(Long.valueOf(filesize[i]));
                    String fextString ="*.avi;*.wmv;*.3gp;*.mov;*.asf;*.asx;*.flv;*.f4v;*.mp4;*.mpg;*.mpeg;*.vob;*.mkv;*.ts;*.wmv9;*.rm;*.rmvb";
                    if(fextString.contains(fext)){
                        zhf.setFiletype("3");//文件类型为视频
                    }
                    zhf.setCreatedate(DateTime.getDate());
                    zhf.setType("2");
                    zxHelpFileManager.addZxHelpFile(zhf);
                    
//                    //开始转码
//                    ConvertFile.convertFile(zhf, "add");
//                    zxHelpFileManager.updateZxHelpFile(zhf);
                    
                    String zxHelpFileIdId = zhf.getFileid().toString();
//                    System.out.println(zxHelpFileIdId);
                    fileIds = fileIds+ zxHelpFileIdId +",";
                    fname = zxHelpFileIdId +":"+fname;
                    filenames =filenames+ fname +",";
                }
                fileIds = fileIds.substring(0,fileIds.length()-1);
                filenames = filenames.substring(0,filenames.length()-1);
                httpServletRequest.setAttribute("fileIds", fileIds);
                httpServletRequest.setAttribute("filenames", filenames);
            }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        return mapping.findForward("videoAddSave");
    }
	
	public ActionForward pictureAddSave(ActionMapping mapping, ActionForm form,
            HttpServletRequest httpServletRequest, HttpServletResponse response) {
        try {
            //视频影片
            ZxHelpFileManager zxHelpFileManager = (ZxHelpFileManager) getBean("zxHelpFileManager");
            String[] filename = httpServletRequest.getParameterValues("filename");
            String[] filepath = httpServletRequest.getParameterValues("filepath");
            String[] filesize = httpServletRequest.getParameterValues("filesize");
            //视频截屏参数
            String imgwidth = Encode.nullToBlank(httpServletRequest.getParameter("imgwidth"));
            String imgheight = Encode.nullToBlank(httpServletRequest.getParameter("imgheight"));
            String second = Encode.nullToBlank(httpServletRequest.getParameter("second"));
            String fileIds ="";
            String filenames="";
            if(filename != null && filename.length > 0){
                String fext = null;
                String fname = null;
                ZxHelpFile zhf = null;
                String[] paths = null;
                for(int i=0, size=filename.length; i<size; i++){
                    if(filepath[i] == null || "".equals(filepath[i]) || filepath[i].indexOf("error_") != -1){
                        continue;
                    }
                    if(filepath[i].indexOf("exist_") != -1){
                        //可以重复上传，只给用户提示，除非用户自己删除
                        filepath[i] = filepath[i].substring(6);
                    }
                    paths = filepath[i].split(";");
                    fext = FileUtil.getFileExt(filename[i]).toLowerCase();
                    fname = filename[i].substring(0,filename[i].lastIndexOf("."));
                    
                    zhf = new ZxHelpFile();
                    zhf.setFilepath(paths[0]);
                    zhf.setConvertstatus("1");
                    zhf.setFilesize(Long.valueOf(filesize[i]));
                    String fextString ="*.bmp;*.jpg;*.tiff;*.gif;*.tga;*.exif;*.fpx;*.svg;*.psd;*.cdr;*.pcd;*.dxf;*.ufo;*.eps;*.ai;*.rm;*.raw;*.WMF;*.png";
                    if(fextString.contains(fext)){
                        zhf.setFiletype("1");//文件类型为图片
                    }
                    zhf.setCreatedate(DateTime.getDate());
                    zhf.setType("2");
                    zhf.setNotifystatus("1");
                    String aString = paths[0].substring(0,paths[0].lastIndexOf("/"));
                    String bString = paths[0].substring(paths[0].lastIndexOf("/")+1);
                    zhf.setThumbpath(aString+"/200x150_"+bString);
                    zxHelpFileManager.addZxHelpFile(zhf);
                    
//                    //开始转码
//                    ConvertFile.convertFile(zhf, "add");
//                    zxHelpFileManager.updateZxHelpFile(zhf);
                    
                    String zxHelpFileIdId = zhf.getFileid().toString();
                    fileIds = fileIds+ zxHelpFileIdId +",";
//                    filenames =filenames+ fname +",";
                    String thumbpath = zhf.getThumbpath();
                    thumbpath = zxHelpFileIdId +":"+ thumbpath;
                    filenames = filenames +thumbpath +",";
                }
                fileIds = fileIds.substring(0,fileIds.length()-1);
                filenames = filenames.substring(0,filenames.length()-1);
                httpServletRequest.setAttribute("fileIds", fileIds);
                httpServletRequest.setAttribute("filenames",filenames);
                
            }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
        return mapping.findForward("pictureAddSave");
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
		ZxHelpOrderActionForm form = (ZxHelpOrderActionForm)actionForm;
		ZxHelpOrderManager manager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
		ZxHelpQuestionManager questionManager = (ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
		ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager)getBean("zxHelpAnswerManager");
		ZxHelpFileManager fileManager = (ZxHelpFileManager)getBean("zxHelpFileManager");
		
		String zxHelpFileId = Encode.nullToBlank(httpServletRequest.getParameter("zxHelpFileId"));//视频文件id字符串
		String zxHelpFileIdpicture = Encode.nullToBlank(httpServletRequest.getParameter("zxHelpFileIdpicture"));//图片文件id字符串
		String content = Encode.nullToBlank(httpServletRequest.getParameter("content"));//回复内容
		Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				ZxHelpOrder model = form.getZxHelpOrder();
				Integer questionid = model.getQuestionid();
                ZxHelpQuestion zxHelpQuestion = questionManager.getZxHelpQuestion(questionid);
				if(zxHelpQuestion.getMoney()>0){
				    zxHelpQuestion.setReplystatus("2");
                }else{
                    zxHelpQuestion.setReplystatus("3");
                    model.setPaystatus("1");
                }
				model.setStatus("2");
				model.setReplycreatedate(DateTime.getDate());
				manager.updateZxHelpOrder(model);
				  //提问
				questionManager.updateZxHelpQuestion(zxHelpQuestion);
				
				//回复
				ZxHelpAnswer zxHelpAnswer = answerManager.getZxHelpAnswerByOrderid(model.getOrderid().toString());
				if(zxHelpAnswer  ==null){
    				zxHelpAnswer = new ZxHelpAnswer();
    				zxHelpAnswer.setOrderid(model.getOrderid());
    				zxHelpAnswer.setContent(content);
    				zxHelpAnswer.setCreatedate(DateTime.getDate());
    				zxHelpAnswer.setUserip(IpUtil.getIpAddr(httpServletRequest));
    				zxHelpAnswer.setStatus("1");
    				zxHelpAnswer.setUserid(userid);
    				answerManager.addZxHelpAnswer(zxHelpAnswer);
				}else{
				    zxHelpAnswer.setContent(content);
				    zxHelpAnswer.setCreatedate(DateTime.getDate());
				    zxHelpAnswer.setUserip(IpUtil.getIpAddr(httpServletRequest));
				    zxHelpAnswer.setStatus("1");
				    zxHelpAnswer.setUserid(userid);
				    answerManager.updateZxHelpAnswer(zxHelpAnswer);
				}
				
				//回复附件
				 String[] videoIds = null;
				 if(zxHelpFileId !=null && zxHelpFileId.trim().length() > 0){
    				 if(zxHelpFileId.indexOf(",")>0){
    				     videoIds = zxHelpFileId.split(","); //视频文件id数组
    				     for(int i=0;i<videoIds.length;i++){
    				         String fileid = videoIds[i];
    				         ZxHelpFile zxHelpFile = fileManager.getZxHelpFile(fileid);
    				         zxHelpFile.setAnswerid(zxHelpAnswer.getAnswerid());
    				         fileManager.updateZxHelpFile(zxHelpFile);
    				     }
    				 }else {
    				     ZxHelpFile zxHelpFile = fileManager.getZxHelpFile(zxHelpFileId);
                         zxHelpFile.setAnswerid(zxHelpAnswer.getAnswerid());
                         fileManager.updateZxHelpFile(zxHelpFile);
                    }
				 }
				 String[] pictureIds = null;
				 if(zxHelpFileIdpicture !=null && zxHelpFileIdpicture.trim().length() > 0){
                     if(zxHelpFileIdpicture.indexOf(",")>0){
                         pictureIds = zxHelpFileIdpicture.split(","); //图片文件id数组
                         for(int i=0;i<pictureIds.length;i++){
                             String fileid = pictureIds[i];
                             ZxHelpFile zxHelpFile = fileManager.getZxHelpFile(fileid);
                             zxHelpFile.setAnswerid(zxHelpAnswer.getAnswerid());
                             fileManager.updateZxHelpFile(zxHelpFile);
                         }
                     }else {
                         ZxHelpFile zxHelpFile = fileManager.getZxHelpFile(zxHelpFileIdpicture);
                         zxHelpFile.setAnswerid(zxHelpAnswer.getAnswerid());
                         fileManager.updateZxHelpFile(zxHelpFile);
                    }
                 }
				   //如果提问金额大于0，则需要把第三方支付收款账号改为老师
                 if(zxHelpQuestion.getMoney() > 0){
                     //设置收款账号为老师
                     SysPaymentAccountManager spam = (SysPaymentAccountManager) getBean("sysPaymentAccountManager");
                     List<SearchModel> condition = new ArrayList<SearchModel>();
                     SearchCondition.addCondition(condition, "fromuserid", "=", zxHelpQuestion.getSysUserInfo().getUserid());
                     SearchCondition.addCondition(condition, "changetype", "=", 0);//待完成交易
                     SearchCondition.addCondition(condition, "outtype", "=", "1");//关联答疑
                     SearchCondition.addCondition(condition, "outobjid", "=", Integer.valueOf(questionid));
                     List list = spam.getSysPaymentAccounts(condition, "", 0);
                     if(list != null && list.size() > 0){
                         SysPaymentAccount account = (SysPaymentAccount) list.get(0);
                         account.setTouserid(Integer.valueOf(userid));
                         account.setStatus("2");
                         spam.updateSysPaymentAccount(account);
                         
                         //生成定时器，如果答疑订单未被确认，钱自动打钱给老师
                         AutomationCache cache = AutomationCache.getInstance();
                         long liveTime = Constants.AUTO_PAY_DAY*24*60*60*1000;
                         if("1".equals(Constants.IS_DEBUG)){
                       	  liveTime = 15*60*1000;//15分钟测试时间
                         }
                         cache.put(account.getPaymentid(), 1, liveTime);
                     }
                 }
                 
                 
                 SysUserInfoManager usermanager=(SysUserInfoManager)getBean("sysUserInfoManager");
                 SysUserInfo teacher = usermanager.getSysUserInfo(userid);
                 //给学生发站内短信
                 String msgtitle = "您发布的在线答疑提问已被老师  ["+teacher.getUsername()+"]  回复，请注意查看。";
                 String msgcontent = "您发布的在线答疑提问《" + zxHelpQuestion.getTitle() + "》已被老师  ["+teacher.getUsername()+"]  回复，请注意查看。";
                 SysMessageInfoManager smim = (SysMessageInfoManager) getBean("sysMessageInfoManager");
                 SysMessageUserManager smum = (SysMessageUserManager) getBean("sysMessageUserManager");
                 SysMessageUserAction.sendSystemMessage(msgtitle, msgcontent, zxHelpQuestion.getSysUserInfo().getUserid(), smim, smum);
				addLog(httpServletRequest,"修改了一个在线答疑订单");
			}catch (Exception e){
			    e.printStackTrace();
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
		ZxHelpOrderManager manager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delZxHelpOrder(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 答疑详情
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
	 public ActionForward viewQuestion(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
	        ZxHelpQuestionManager questionmanager = (ZxHelpQuestionManager)getBean("zxHelpQuestionManager");
	        ZxHelpFileManager fileManager = (ZxHelpFileManager)getBean("zxHelpFileManager");
	        SysUserInfoManager userinfomanager = (SysUserInfoManager)getBean("sysUserInfoManager");
	        String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));//questionid
	        try {
	            //根据提问的id，获取提问详情
	            ZxHelpQuestion model = questionmanager.getZxHelpQuestion(objid);
	            httpServletRequest.setAttribute("model", model);
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
                String homePage= MpUtil.HOMEPAGE;
                httpServletRequest.setAttribute("flag", flag);
                httpServletRequest.setAttribute("homePage", homePage);
                httpServletRequest.setAttribute("files", files);
                httpServletRequest.setAttribute("list1", list1);
                httpServletRequest.setAttribute("list2", list2);
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
	        return actionMapping.findForward("view");
	    }
	 
	 /**
	  *查看 回复详情
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
	 public ActionForward viewAnswer(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
         ZxHelpOrderManager orderManager = (ZxHelpOrderManager)getBean("zxHelpOrderManager");
         ZxHelpFileManager fileManager = (ZxHelpFileManager)getBean("zxHelpFileManager");
         SysUserInfoManager userinfomanager = (SysUserInfoManager)getBean("sysUserInfoManager");
         ZxHelpAnswerManager answerManager = (ZxHelpAnswerManager)getBean("zxHelpAnswerManager");
         String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));//orderid
         try {
             //根据提问的id，获取提问详情
            ZxHelpOrder model = orderManager.getZxHelpOrder(objid);
             httpServletRequest.setAttribute("model", model);
             
             ZxHelpAnswer zxHelpAnswer = answerManager.getZxHelpAnswerByOrderid(model.getOrderid().toString());
             httpServletRequest.setAttribute("zxHelpAnswer", zxHelpAnswer);
           //展示j教师回复中的视频，音频，图片
             List<SearchModel> condition = new ArrayList<SearchModel>();
             SearchCondition.addCondition(condition, "type", "=",  "2");
             SearchCondition.addCondition(condition, "answerid", "=",  zxHelpAnswer.getAnswerid());
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
             String homePage= MpUtil.HOMEPAGE;
             httpServletRequest.setAttribute("flag", flag);
             httpServletRequest.setAttribute("homePage", homePage);
             httpServletRequest.setAttribute("files", files);
             httpServletRequest.setAttribute("list1", list1);
             httpServletRequest.setAttribute("list2", list2);
         }catch (Exception e) {
             e.printStackTrace();
         }
         return actionMapping.findForward("viewAnswer");
     }
	 /**
	  * 学生答疑视频列表
	 * @author ; liud 
	 * @Description : TODO 
	 * @CreateDate ; 2016-12-24 上午10:56:43 
	 * @lastModified ; 2016-12-24 上午10:56:43 
	 * @version ; 1.0 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	  */
	 public ActionForward videoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
	        String questionid = Encode.nullToBlank(httpServletRequest.getParameter("questionid"));
	        String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
	        
	        ZxHelpFileManager manager = (ZxHelpFileManager)getBean("zxHelpFileManager");
	        List<SearchModel> condition = new ArrayList<SearchModel>();
	        SearchCondition.addCondition(condition, "questionid", "=", questionid);
	        SearchCondition.addCondition(condition, "type", "=",  "1");
	        SearchCondition.addCondition(condition, "filetype", "=",  "3");
	        if(!"".equals(convertstatus)){
	            SearchCondition.addCondition(condition, "convertstatus", "=", convertstatus);   
	        }
	        
	        PageUtil pageUtil = new PageUtil(httpServletRequest);
	        String sorderindex = "fileid asc";
	        if(!"".equals(pageUtil.getOrderindex())){
	            sorderindex = pageUtil.getOrderindex();
	        }
	        PageList page = manager.getPageZxHelpFiles(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
	        
	        String openconvertservice = ConvertFile.openconvertservice;
	        
	        httpServletRequest.setAttribute("openconvertservice", openconvertservice);
	        httpServletRequest.setAttribute("pagelist", page);
	        httpServletRequest.setAttribute("questionid", questionid);
	        httpServletRequest.setAttribute("convertstatus", convertstatus);
	        return actionMapping.findForward("videoList");
	    }
	 
	 /**
	  * 回复视频列表
	 * @author ; liud 
	 * @Description : TODO 
	 * @CreateDate ; 2016-12-24 下午4:29:06 
	 * @lastModified ; 2016-12-24 下午4:29:06 
	 * @version ; 1.0 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	  */
	 public ActionForward videoListAnswer(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
         String answerid = Encode.nullToBlank(httpServletRequest.getParameter("answerid"));
         String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
         
         ZxHelpFileManager manager = (ZxHelpFileManager)getBean("zxHelpFileManager");
         List<SearchModel> condition = new ArrayList<SearchModel>();
         SearchCondition.addCondition(condition, "answerid", "=", answerid);
         SearchCondition.addCondition(condition, "type", "=",  "2");
         SearchCondition.addCondition(condition, "filetype", "=",  "3");
         if(!"".equals(convertstatus)){
             SearchCondition.addCondition(condition, "convertstatus", "=", convertstatus);   
         }
         
         PageUtil pageUtil = new PageUtil(httpServletRequest);
         String sorderindex = "fileid asc";
         if(!"".equals(pageUtil.getOrderindex())){
             sorderindex = pageUtil.getOrderindex();
         }
         PageList page = manager.getPageZxHelpFiles(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
         
         String openconvertservice = ConvertFile.openconvertservice;
         
         httpServletRequest.setAttribute("openconvertservice", openconvertservice);
         httpServletRequest.setAttribute("pagelist", page);
         httpServletRequest.setAttribute("answerid", answerid);
         httpServletRequest.setAttribute("convertstatus", convertstatus);
         return actionMapping.findForward("videoList");
     }
	 /**
	  * 视频播放
	 * @author ; liud 
	 * @Description : TODO 
	 * @CreateDate ; 2016-12-24 上午11:24:47 
	 * @lastModified ; 2016-12-24 上午11:24:47 
	 * @version ; 1.0 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	  */
	 public ActionForward preview(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
	        ZxHelpFileManager manager = (ZxHelpFileManager)getBean("zxHelpFileManager");
	        
	        String fileid = Encode.nullToBlank(httpServletRequest.getParameter("fileid"));
	        ZxHelpFile model = manager.getZxHelpFile(fileid);
	        httpServletRequest.setAttribute("model", model);
	        String openconvertservice = ConvertFile.openconvertservice;
	        if("1".equals(openconvertservice)){
                model.setFlagl(model.getMp4path());
            }else{
                model.setFlagl(model.getFilepath());
            }
	        String homePage= MpUtil.HOMEPAGE;
            httpServletRequest.setAttribute("homePage", homePage);
	        return actionMapping.findForward("preview");
	    }
}