package com.wkmk.sys.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.UUID;
import com.wkmk.sys.bo.SysTeacherQualification;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysTeacherQualificationManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.form.SysTeacherQualificationActionForm;

/**
 *<p>Description: 教师资格认证</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysTeacherQualificationAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageSysTeacherQualifications(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		return actionMapping.findForward("list");
	}

	/**
     *认证审核列表显示
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward checklist(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
        String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
        String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
        String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
        String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
        
        List<SearchModel> condition = new ArrayList<SearchModel>();
        if(!"".equals(username)){
            SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
        }
        if(!"".equals(sex) && !"-1".equals(sex)){
            SearchCondition.addCondition(condition, "sex", "=", sex);
        }
        if (!"".equals(createdate)) {
            SearchCondition.addCondition(condition, "createdate", "like", createdate + "%");
        }
        if(!"".equals(status)){
            SearchCondition.addCondition(condition, "status", "=", status);
        }
        
        PageUtil pageUtil = new PageUtil(httpServletRequest);
        String sorderindex = "username asc";
        if(!"".equals(pageUtil.getOrderindex())){
            sorderindex = pageUtil.getOrderindex();
        }
        
        PageList page = manager.getPageSysTeacherQualifications(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
        httpServletRequest.setAttribute("pagelist", page);
        httpServletRequest.setAttribute("username", username);
        httpServletRequest.setAttribute("createdate", createdate);
        httpServletRequest.setAttribute("sex", sex);
        httpServletRequest.setAttribute("status", status);
        return actionMapping.findForward("checklist");
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
	    Integer userid = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
	    SysTeacherQualification model = new SysTeacherQualification();
	    model.setSex("0");
		model.setStatus("0");//状态是待审核
		model.setPhoto("man.jpg");
		model.setIdphto("default.jpg");
		model.setImgpath("default.jpg");
		model.setUserid(userid);
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
		SysTeacherQualificationActionForm form = (SysTeacherQualificationActionForm)actionForm;
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysTeacherQualification model = form.getSysTeacherQualification();
				//上传附件
				FormFile file = form.getFile();
				String  savepath= addFile(actionMapping, actionForm, httpServletRequest, httpServletResponse,file);
				if(savepath == null){
				    String message = (String) httpServletRequest.getAttribute("message");
				    httpServletRequest.setAttribute("promptinfo", message);
	                return  beforeAdd(actionMapping, actionForm, httpServletRequest, httpServletResponse);
				}
				model.setFilepath(savepath);
				model.setCreatedate(DateTime.getDate());
				manager.addSysTeacherQualification(model);
				addLog(httpServletRequest,"增加了一个教师资格认证");
			}catch (Exception e){
			    httpServletRequest.setAttribute("promptinfo", "教师资格认证申请失败!");
                return actionMapping.findForward("failure");
			}
		}
		httpServletRequest.setAttribute("promptinfo", "教师资格认证申请成功，请等待管理员审核！");
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
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysTeacherQualification model = manager.getSysTeacherQualification(objid);
			String sex = model.getSex();
			if(sex.equals("0")){
			    model.setFlagl("男");
			}else{
			    model.setFlagl("女");
			} 
			String statusObj = model.getStatus();
			httpServletRequest.setAttribute("statusObj", statusObj);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("checkedit");
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
		SysTeacherQualificationActionForm form = (SysTeacherQualificationActionForm)actionForm;
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
		SysUserInfoManager userinfomanager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String statusObj = Encode.nullToBlank(httpServletRequest.getParameter("statusObj"));
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysTeacherQualification model = form.getSysTeacherQualification();
				model.setStatus(statusObj);
				manager.updateSysTeacherQualification(model);
				if(statusObj.equals("1")){
				    //审核通过，同步数据到ysyuserinfo
				    SysUserInfo userInfo = userinfomanager.getSysUserInfo(model.getUserid());
				    userInfo.setUsername(model.getUsername());
				    userInfo.setSex(model.getSex());
				    String mobile = userInfo.getMobile();
				    if(mobile ==null || "".equals(mobile)){
				        userInfo.setMobile(model.getMobile());
				    }
				    userInfo.setAuthentication("1");
				    userinfomanager.updateSysUserInfo(userInfo);
				}
				addLog(httpServletRequest,"修改了一个教师资格认证");
			}catch (Exception e){
			}
		}
		return  checklist(actionMapping, actionForm, httpServletRequest, httpServletResponse);
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
		SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delSysTeacherQualification(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 进入认证申请
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2016-12-19 下午2:24:39 
	* @lastModified ; 2016-12-19 下午2:24:39 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
        Integer userid_reply = (Integer) httpServletRequest.getSession().getAttribute("s_userid");
        try{
            //根据userid去查询是否为二次申请
            List list = manager.geTeacherQualificationByUserid(userid_reply);
            if(list !=null && list.size()>0){
                //已经申请过
                SysTeacherQualification model = (SysTeacherQualification) list.get(0);
                String status = model.getStatus();//状态，0待审核，1已审核，2审核未通过
                Integer teacherid = model.getTeacherid();
                if(!status.equals("1")){
                    //还未处理，或者未通过，可以编辑
                    return beforeUpdateTwo(actionMapping, actionForm, httpServletRequest, httpServletResponse, teacherid);
                }else{
                    //已经处理，不能编辑，只能预览
                    return view(actionMapping, actionForm, httpServletRequest, httpServletResponse, teacherid);
                }
            }else{
                //第一次申请,进入编辑页面
                return beforeAdd(actionMapping, actionForm, httpServletRequest, httpServletResponse);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        
        return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }
	
	/**
     *二次申请编辑前
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward beforeUpdateTwo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,Integer teacherid ) {
        SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
        try {
            SysTeacherQualification model = manager.getSysTeacherQualification(teacherid);
            String sex = model.getSex();
            if(sex.equals("0")){
                model.setFlagl("男");
            }else{
                model.setFlagl("女");
            } 
            httpServletRequest.setAttribute("act", "updateSaveTwo");
            httpServletRequest.setAttribute("model", model);
        }catch (Exception e){
        }
        saveToken(httpServletRequest);
        return actionMapping.findForward("beforeUpdateTwo");
    }
    
    /**
     *二次修改时保存
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward updateSaveTwo(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        SysTeacherQualificationActionForm form = (SysTeacherQualificationActionForm)actionForm;
        SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
        SysUserInfoManager userinfomanager = (SysUserInfoManager)getBean("sysUserInfoManager");
        String statusObj = Encode.nullToBlank(httpServletRequest.getParameter("statusObj"));
        if (isTokenValid(httpServletRequest, true)) {
            try {
                SysTeacherQualification model = form.getSysTeacherQualification();
                FormFile twofile = form.getTwofile();
                if(twofile !=null && twofile.getFileSize()>0){
                    String  savepath= addFile(actionMapping, actionForm, httpServletRequest, httpServletResponse,twofile);
                    if(savepath == null){
                        String message = (String) httpServletRequest.getAttribute("message");
                        httpServletRequest.setAttribute("promptinfo", message);
                        return  beforeAdd(actionMapping, actionForm, httpServletRequest, httpServletResponse);
                    }
                    model.setFilepath(savepath);
                }
                model.setStatus("0");//二次编辑
                manager.updateSysTeacherQualification(model);
                if(statusObj.equals("1")){
                    //审核通过，同步数据到ysyuserinfo
                    SysUserInfo userInfo = userinfomanager.getSysUserInfo(model.getUserid());
                    userInfo.setUsername(model.getUsername());
                    userInfo.setSex(model.getSex());
                    userInfo.setMobile(model.getMobile());
                    userinfomanager.updateSysUserInfo(userInfo);
                }
                addLog(httpServletRequest,"修改了一个教师资格认证");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        httpServletRequest.setAttribute("promptinfo", "教师资格认证申请成功，请等待管理员审核！");
        return actionMapping.findForward("success");
    }
    
    /**
     *预览
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward view(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse,Integer teacherid ) {
        SysTeacherQualificationManager manager = (SysTeacherQualificationManager)getBean("sysTeacherQualificationManager");
        try {
            SysTeacherQualification model = manager.getSysTeacherQualification(teacherid);
            String sex = model.getSex();
            if(sex.equals("0")){
                model.setFlagl("男");
            }else{
                model.setFlagl("女");
            } 
            httpServletRequest.setAttribute("model", model);
        }catch (Exception e){
            e.printStackTrace();
        }
        return actionMapping.findForward("view");
    }
	/**
	 * 上传附件
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
	public String addFile(ActionMapping mapping, ActionForm actionForm,  
	        HttpServletRequest request, HttpServletResponse response,FormFile file){  
	            String savepath ="";
	            //获取文件
	            String filepath = ""; //全路径
	            String filename = file.getFileName();
	            String sFileExt = FileUtil.getFileExt(filename).toLowerCase();
	            String curdate = DateTime.getDate("yyyyMM");
	            String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
                //如果未传入文件名，则创建一个
                if ("".equals(filename)) {
                  filename = UUID.getNewUUID() + "." + sFileExt;;
                }
    
                HttpSession session = request.getSession();
                Integer unitid = (Integer) session.getAttribute("s_unitid");
                //图片保存路径
                savepath =unitid + "/teacherqualific/" + curdate;
    
                filepath = rootpath + "/" + savepath;
                //检查文件夹是否存在,如果不存在,新建一个
                if (!FileUtil.isExistDir(filepath)) {
                    FileUtil.creatDir(filepath);
                }
           try{
                     // 写文件
                     InputStream stream = file.getInputStream(); //把文件读入
                    OutputStream bos = new FileOutputStream(filepath + "/" + filename);
                    int bytesRead = 0;
                    byte[] buffer = new byte[8192];
                    while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                      bos.write(buffer, 0, bytesRead); //将文件写入服务器
                    }
                    bos.close();
                    stream.close();
	       } catch (Exception e) {  
	        e.printStackTrace();  
	        request.setAttribute("message", "上传附件失败，请重新上传！");  
	        return null;
	       }  
            return savepath +"/" + filename;
	     }  
	
	/**
	 * 实现文件的下载
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
        String path = Encode.nullToBlank(request.getParameter("filepath"));//1/teacherqualific/201612/集群版统一认证接口_V2.1.1.zip
        String filename = path.substring(path.lastIndexOf("/")+1);
//        System.out.println(path+"111"); 
        BufferedInputStream bis = null; 
        BufferedOutputStream bos = null; 
        OutputStream fos = null; 
        InputStream fis = null; 
        
      //如果是从服务器上取就用这个获得系统的绝对路径方法。
        String filepath = servlet.getServletContext().getRealPath("/upload/" + path); 
//        System.out.println("文件路径"+filepath); 
        File uploadFile = new File(filepath); 
        fis = new FileInputStream(uploadFile); 
        bis = new BufferedInputStream(fis); 
        fos = response.getOutputStream(); 
        bos = new BufferedOutputStream(fos); 
        //这个就是弹出下载对话框的关键代码 
        response.setHeader("Content-disposition", 
                           "attachment;filename=" + 
                           URLEncoder.encode(filename, "utf-8")); 
        int bytesRead = 0; 
        //这个地方的同上传的一样。我就不多说了，都是用输入流进行先读，然后用输出流去写，唯一不同的是我用的是缓冲输入输出流 
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
}