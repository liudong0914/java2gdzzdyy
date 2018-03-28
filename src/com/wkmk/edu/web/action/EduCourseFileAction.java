package com.wkmk.edu.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.wkmk.edu.bo.EduCourseColumn;
import com.wkmk.edu.bo.EduCourseFile;
import com.wkmk.edu.bo.EduCourseFileColumn;
import com.wkmk.edu.service.EduCourseColumnManager;
import com.wkmk.edu.service.EduCourseFileColumnManager;
import com.wkmk.edu.service.EduCourseFileManager;
import com.wkmk.edu.web.form.EduCourseFileActionForm;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.util.file.ConvertFile;

/**
 *<p>Description: 课程文件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseFileAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
		SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("columnid", columnid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
           
       String filename = Encode.nullToBlank(httpServletRequest.getParameter("filename"));
       httpServletRequest.setAttribute("filename", filename);
       String filetype = Encode.nullToBlank(httpServletRequest.getParameter("filetype"));
       httpServletRequest.setAttribute("filetype", filetype);
       String coursefiletype = Encode.nullToBlank(httpServletRequest.getParameter("coursefiletype"));
       httpServletRequest.setAttribute("coursefiletype", coursefiletype);
       
       try {
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           PageUtil pageUtil = new PageUtil(httpServletRequest);
           List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "coursecolumnid", "=", columnid);
            SearchCondition.addCondition(condition, "courseid", "=", courseid);
            if(!"".equals(filename)){
                SearchCondition.addCondition(condition, "filename", "like", "%"+filename+"%");
            }
            if(!"".equals(filetype)){
                SearchCondition.addCondition(condition, "filetype", "=", filetype);
            }
            if(!"".equals(coursefiletype)){
                SearchCondition.addCondition(condition, "coursefiletype", "=", coursefiletype);
            }
           PageList page = manager.getPageEduCourseFiles(condition, "filename asc", pageUtil.getStartCount(), pageUtil.getPageSize());
           
         //资源类型
			EduCourseFileColumnManager fileColumnManager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
			
           ArrayList datalist = page.getDatalist();
           if(datalist !=null && datalist.size()>0){
               for(int i=0;i<datalist.size();i++){
                   EduCourseFile model = (EduCourseFile) datalist.get(i);
                 Integer userid = model.getUserid();
                 SysUserInfo info = userInfoManager.getSysUserInfo(userid);
                 String username = info.getUsername();
                 model.setFlagl(username);
                 model.setFlago(FileUtil.getFileSizeName(model.getFilesize().doubleValue(), 2));
                 String coursefiletype2 = model.getCoursefiletype();
                 EduCourseFileColumn eduCourseFileColumn = fileColumnManager.getEduCourseFileColumn(coursefiletype2);
                 model.setCoursefiletype(eduCourseFileColumn.getTitle());
                 //Date parse = formatter.parse(model.getCreatedate());
                 //model.setCreatedate(formatter.format(parse));
               }
           }
           httpServletRequest.setAttribute("pagelist", page);
       } catch (Exception e){
        e.printStackTrace();
       }
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
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("columnid", columnid);
        
        //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
        
	    EduCourseFile model = new EduCourseFile();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		
		//资源类型
		EduCourseFileColumnManager fileColumnManager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List filecolumnList = fileColumnManager.getEduCourseFileColumns(condition, "columnno asc", 0);
		httpServletRequest.setAttribute("filecolumnList", filecolumnList);

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
		EduCourseFileActionForm form = (EduCourseFileActionForm)actionForm;
        EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
        
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("columnid", columnid);
            
        HttpSession session = httpServletRequest.getSession();
        Integer userid = (Integer) session.getAttribute("s_userid");
        
        if (isTokenValid(httpServletRequest, true)) {
            try {
                FormFile file = form.getFile();
                Map map = addFile(actionMapping, actionForm, httpServletRequest, httpServletResponse, file);
                if(map ==null ){
                    String message = (String) httpServletRequest.getAttribute("message");
                    httpServletRequest.setAttribute("promptinfo", message);
                    return  beforeAdd(actionMapping, actionForm, httpServletRequest, httpServletResponse);
                }
                String fname = Encode.nullToBlank(httpServletRequest.getParameter("fname"));
                EduCourseFile model = form.getEduCourseFile();
                String filename = (String) map.get("filename");
                if(!"".equals(fname)){
					filename = fname;
				}
                String filepath = (String) map.get("filepath");
                Integer filesize = (Integer) map.get("filesize");
                String fileext = (String) map.get("fileext");
                model.setFilename(filename);
                model.setFilepath(filepath);
                model.setFilesize(filesize.longValue());
                model.setFileext(fileext);
                model.setCreatedate(DateTime.getDate());
                model.setFiletype("1");
                model.setCoursecolumnid(Integer.valueOf(columnid));
                model.setCourseid(Integer.valueOf(courseid));
                model.setUserid(userid);
                model.setConvertstatus("0");
                
                manager.addEduCourseFile(model);
                addLog(httpServletRequest,"增加了一个课程文件");
                
                if(!fileext.equals("pdf")){
                	//开始转码
                	ConvertFile.convertCourseFile(model, "add", 0);
                }else{
                	model.setConvertstatus("1");
                	model.setNotifystatus("1");
                	model.setPdfpath(model.getFilepath());
                }
                manager.updateEduCourseFile(model);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        
		httpServletRequest.setAttribute("reloadtree", "1");
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 重新转码
	* @author ; liud 
	* @Description : TODO 
	* @CreateDate ; 2017-3-20 下午4:02:44 
	* @lastModified ; 2017-3-20 下午4:02:44 
	* @version ; 1.0 
	* @param actionMapping
	* @param actionForm
	* @param httpServletRequest
	* @param httpServletResponse
	* @return
	 */
	public ActionForward convertFile(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
	    EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
	    String fileid = Encode.nullToBlank(httpServletRequest.getParameter("fileid"));

	    EduCourseFile model = manager.getEduCourseFile(fileid);
	    ConvertFile.convertCourseFile(model, "update", 1);
	    manager.updateEduCourseFile(model);
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
		EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
		    String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
	        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
	        httpServletRequest.setAttribute("courseid", courseid);
	        httpServletRequest.setAttribute("columnid", columnid);
	        
	        //分页与排序
	        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
	        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
	        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
	        httpServletRequest.setAttribute("pageno", pageno);
	        httpServletRequest.setAttribute("direction", direction);
	        httpServletRequest.setAttribute("sort", sort);
	        
			EduCourseFile model = manager.getEduCourseFile(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			
			//资源类型
			EduCourseFileColumnManager fileColumnManager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "1");
			List filecolumnList = fileColumnManager.getEduCourseFileColumns(condition, "columnno asc", 0);
			httpServletRequest.setAttribute("filecolumnList", filecolumnList);
			
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
		EduCourseFileActionForm form = (EduCourseFileActionForm)actionForm;
		EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
		
	   String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("columnid", columnid);
            
        HttpSession session = httpServletRequest.getSession();
        Integer userid = (Integer) session.getAttribute("s_userid");
	        
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduCourseFile model = form.getEduCourseFile();
                model.setCreatedate(DateTime.getDate());
				manager.updateEduCourseFile(model);
				addLog(httpServletRequest,"修改了一个课程文件");
				
	    		manager.updateEduCourseFile(model);
			}catch (Exception e){
			    e.printStackTrace();
			}
		}
		
		httpServletRequest.setAttribute("reloadtree", "1");
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
		EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		EduCourseFile model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getEduCourseFile(checkids[i]);
			manager.delEduCourseFile(checkids[i]);
    		ConvertFile.convertCourseFile(model, "delete", 0);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	   /**
     * 跳转到主工作区
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
    public ActionForward main(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
            return actionMapping.findForward("main");
    }
    /**
     * 树形选择器
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
    public ActionForward tree(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession();
        String courseid = (String) session.getAttribute("courseid");
        
        EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
        List list = manager.getEduCourseColumns(condition, "columnno asc", 0);
        
        StringBuffer tree = new StringBuffer();
        String text = null;
        String target="rfrmright";
        String url = null;
        String hint = null;
        String no = null;
        String pno = null;
        EduCourseColumn ecc = null;
        for (int i = 0; i < list.size(); i++) {
            ecc = (EduCourseColumn) list.get(i);
            no = ecc.getColumnno().trim();// 自身ID
            pno = ecc.getParentno().trim();// 父ID
            text = ecc.getTitle();// 树节点显示名称
             
            url = "/eduCourseFileAction.do?method=list&columnid=" + ecc.getColumnid()+"&courseid="+ecc.getCourseid();
            tree.append("\n").append("tree").append(".nodes[\"").append(pno).append("_").append(no).append("\"]=\"");
            if(text !=null && text.trim().length() > 0) {
                tree.append("text:").append(text).append(";");
            }
            if(hint!=null && hint.trim().length() > 0) {
                tree.append("hint:").append(hint).append(";");
            }
            tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
        }
        String rooturl = "javascript:;";
        httpServletRequest.setAttribute("treenode", tree.toString());
        httpServletRequest.setAttribute("rooturl", rooturl);
        httpServletRequest.setAttribute("modulename", "课程资源");
            return actionMapping.findForward("tree");
    }
    
    public Map addFile(ActionMapping mapping, ActionForm actionForm,  
            HttpServletRequest request, HttpServletResponse response,FormFile file){  
                String savepath ="";
                Map map = new HashMap();
                //获取文件
                String filepath = ""; //全路径
                String filename = file.getFileName();
                Integer filesize= file.getFileSize();
                String sFileExt = FileUtil.getFileExt(filename).toLowerCase();
                String curdate = DateTime.getDate("yyyyMM");
                String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
                
                String fname = UUID.getNewUUID() + "." + sFileExt;
                if ("".equals(filename)) {
                  filename = UUID.getNewUUID() + "." + sFileExt;
                }
    
                HttpSession session = request.getSession();
                Integer unitid = (Integer) session.getAttribute("s_unitid");
                //图片保存路径
                savepath =unitid + "/coursefile/" + curdate;
    
                filepath = rootpath + "/" + savepath;
                //检查文件夹是否存在,如果不存在,新建一个
                if (!FileUtil.isExistDir(filepath)) {
                    FileUtil.creatDir(filepath);
                }
           try{
                     // 写文件
                     InputStream stream = file.getInputStream(); //把文件读入
                    OutputStream bos = new FileOutputStream(filepath + "/" + fname);
                    int bytesRead = 0;
                    byte[] buffer = new byte[8192];
                    while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
                      bos.write(buffer, 0, bytesRead); //将文件写入服务器
                    }
                    bos.close();
                    stream.close();
           } catch (Exception e) {  
            e.printStackTrace();  
            request.setAttribute("message", "上传资源失败，请重新上传！");  
            return null;
           }  
           map.put("filename", filename);
           map.put("filepath", savepath+"/"+fname);
           map.put("filesize", filesize);
           map.put("fileext", sFileExt);
           return map;
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
        EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
        String fileid = Encode.nullToBlank(request.getParameter("fileid"));
        EduCourseFile model = manager.getEduCourseFile(fileid);
       
        String filepath = model.getFilepath();
        String filename = model.getFilename();
        String fileext = model.getFileext();
        BufferedInputStream bis = null; 
        BufferedOutputStream bos = null; 
        OutputStream fos = null; 
        InputStream fis = null; 
        
      //如果是从服务器上取就用这个获得系统的绝对路径方法。
        filepath = servlet.getServletContext().getRealPath("/upload/" + filepath); 
        if(!filename.endsWith(fileext)){
        	filename =filename+"."+fileext;
        }
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
    
  //======================================以下为管理员微课资源管理==============================================
    
    /**
     * 跳转到主工作区
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
    public ActionForward adminMain(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
            return actionMapping.findForward("adminmain");
    }
    /**
     * 树形选择器
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
    public ActionForward adminTree(ActionMapping actionMapping,
            ActionForm actionForm, HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse) {
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        httpServletRequest.setAttribute("courseid", courseid);
        
        EduCourseColumnManager manager = (EduCourseColumnManager)getBean("eduCourseColumnManager");
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "courseid", "=", Integer.valueOf(courseid));
        List list = manager.getEduCourseColumns(condition, "columnno asc", 0);
        
        StringBuffer tree = new StringBuffer();
        String text = null;
        String target="rfrmright";
        String url = null;
        String hint = null;
        String no = null;
        String pno = null;
        EduCourseColumn ecc = null;
        for (int i = 0; i < list.size(); i++) {
            ecc = (EduCourseColumn) list.get(i);
            no = ecc.getColumnno().trim();// 自身ID
            pno = ecc.getParentno().trim();// 父ID
            text = ecc.getTitle();// 树节点显示名称
             
            url = "/eduCourseFileAction.do?method=adminList&columnid=" + ecc.getColumnid()+"&courseid="+ecc.getCourseid();
            tree.append("\n").append("tree").append(".nodes[\"").append(pno).append("_").append(no).append("\"]=\"");
            if(text !=null && text.trim().length() > 0) {
                tree.append("text:").append(text).append(";");
            }
            if(hint!=null && hint.trim().length() > 0) {
                tree.append("hint:").append(hint).append(";");
            }
            tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
        }
        String rooturl = "javascript:;";
        httpServletRequest.setAttribute("treenode", tree.toString());
        httpServletRequest.setAttribute("rooturl", rooturl);
        httpServletRequest.setAttribute("modulename", "课程资源");
            return actionMapping.findForward("admintree");
    }
    
    public ActionForward adminList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
        SysUserInfoManager userInfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
        String courseclassid = Encode.nullToBlank(httpServletRequest.getParameter("courseclassid"));
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("columnid", columnid);
        httpServletRequest.setAttribute("courseclassid", courseclassid);
           
       String filename = Encode.nullToBlank(httpServletRequest.getParameter("filename"));
       httpServletRequest.setAttribute("filename", filename);
       String filetype = Encode.nullToBlank(httpServletRequest.getParameter("filetype"));
       httpServletRequest.setAttribute("filetype", filetype);
       String coursefiletype = Encode.nullToBlank(httpServletRequest.getParameter("coursefiletype"));
       httpServletRequest.setAttribute("coursefiletype", coursefiletype);
       
       try {
           SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
           PageUtil pageUtil = new PageUtil(httpServletRequest);
           List<SearchModel> condition = new ArrayList<SearchModel>();
            SearchCondition.addCondition(condition, "coursecolumnid", "=", columnid);
            SearchCondition.addCondition(condition, "courseid", "=", courseid);
            if(!"".equals(filename)){
                SearchCondition.addCondition(condition, "filename", "like", "%"+filename+"%");
            }
            if(!"".equals(filetype)){
                SearchCondition.addCondition(condition, "filetype", "=", filetype);
            }
            if(!"".equals(coursefiletype)){
                SearchCondition.addCondition(condition, "coursefiletype", "=", coursefiletype);
            }
           PageList page = manager.getPageEduCourseFiles(condition, "filename asc", pageUtil.getStartCount(), pageUtil.getPageSize());
           //资源类型
			EduCourseFileColumnManager fileColumnManager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
           ArrayList datalist = page.getDatalist();
           if(datalist !=null && datalist.size()>0){
               for(int i=0;i<datalist.size();i++){
                   EduCourseFile model = (EduCourseFile) datalist.get(i);
                 Integer userid = model.getUserid();
                 SysUserInfo info = userInfoManager.getSysUserInfo(userid);
                 String username = info.getUsername();
                 model.setFlagl(username);
                 model.setFlago(FileUtil.getFileSizeName(model.getFilesize().doubleValue(), 2));
                 String coursefiletype2 = model.getCoursefiletype();
                 EduCourseFileColumn eduCourseFileColumn = fileColumnManager.getEduCourseFileColumn(coursefiletype2);
                 model.setCoursefiletype(eduCourseFileColumn.getTitle());
                 //Date parse = formatter.parse(model.getCreatedate());
                 //model.setCreatedate(formatter.format(parse));
               }
           }
           httpServletRequest.setAttribute("pagelist", page);
       } catch (Exception e){
        e.printStackTrace();
       }
        return actionMapping.findForward("adminlist");
    }
    
    /**
     *增加前
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward adminBeforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("columnid", columnid);
        
        //分页与排序
        String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
        String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
        String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
        httpServletRequest.setAttribute("pageno", pageno);
        httpServletRequest.setAttribute("direction", direction);
        httpServletRequest.setAttribute("sort", sort);
        
        EduCourseFile model = new EduCourseFile();
        httpServletRequest.setAttribute("model", model);
        httpServletRequest.setAttribute("act", "adminAddSave");
        
      //资源类型
      		EduCourseFileColumnManager fileColumnManager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
      		List<SearchModel> condition = new ArrayList<SearchModel>();
      		SearchCondition.addCondition(condition, "status", "=", "1");
      		List filecolumnList = fileColumnManager.getEduCourseFileColumns(condition, "columnno asc", 0);
      		httpServletRequest.setAttribute("filecolumnList", filecolumnList);

        saveToken(httpServletRequest);
        return actionMapping.findForward("adminedit");
    }

    /**
     *增加时保存
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward adminAddSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        EduCourseFileActionForm form = (EduCourseFileActionForm)actionForm;
        EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
        
        String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("columnid", columnid);
            
        HttpSession session = httpServletRequest.getSession();
        Integer userid = (Integer) session.getAttribute("s_userid");
        
        if (isTokenValid(httpServletRequest, true)) {
            try {
                FormFile file = form.getFile();
                Map map = addFile(actionMapping, actionForm, httpServletRequest, httpServletResponse, file);
                if(map ==null ){
                    String message = (String) httpServletRequest.getAttribute("message");
                    httpServletRequest.setAttribute("promptinfo", message);
                    return  beforeAdd(actionMapping, actionForm, httpServletRequest, httpServletResponse);
                }
                EduCourseFile model = form.getEduCourseFile();
                String filename = (String) map.get("filename");
                String filepath = (String) map.get("filepath");
                Integer filesize = (Integer) map.get("filesize");
                String fileext = (String) map.get("fileext");
                model.setFilename(filename);
                model.setFilepath(filepath);
                model.setFilesize(filesize.longValue());
                model.setFileext(fileext);
                model.setCreatedate(DateTime.getDate());
                model.setFiletype("1");
                model.setCoursecolumnid(Integer.valueOf(columnid));
                model.setCourseid(Integer.valueOf(courseid));
                model.setUserid(userid);
                model.setConvertstatus("0");
                
                manager.addEduCourseFile(model);
                addLog(httpServletRequest,"增加了一个课程文件");
                
                if(!fileext.equals("pdf")){
                	//开始转码
                	ConvertFile.convertCourseFile(model, "add", 0);
                }else{
                	model.setConvertstatus("1");
                	model.setNotifystatus("1");
                	model.setPdfpath(model.getFilepath());
                }
                manager.updateEduCourseFile(model);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        httpServletRequest.setAttribute("reloadtree", "1");
        return adminList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    /**
     *修改前
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward adminBeforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
        String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
        try {
            String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
            String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
            httpServletRequest.setAttribute("courseid", courseid);
            httpServletRequest.setAttribute("columnid", columnid);
            
            //分页与排序
            String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
            String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
            String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
            httpServletRequest.setAttribute("pageno", pageno);
            httpServletRequest.setAttribute("direction", direction);
            httpServletRequest.setAttribute("sort", sort);
            
            EduCourseFile model = manager.getEduCourseFile(objid);
            httpServletRequest.setAttribute("act", "adminUpdateSave");
            httpServletRequest.setAttribute("model", model);
            
          //资源类型
    		EduCourseFileColumnManager fileColumnManager = (EduCourseFileColumnManager)getBean("eduCourseFileColumnManager");
    		List<SearchModel> condition = new ArrayList<SearchModel>();
    		SearchCondition.addCondition(condition, "status", "=", "1");
    		List filecolumnList = fileColumnManager.getEduCourseFileColumns(condition, "columnno asc", 0);
    		httpServletRequest.setAttribute("filecolumnList", filecolumnList);
        }catch (Exception e){
        }

        saveToken(httpServletRequest);
        return actionMapping.findForward("adminedit");
    }

    /**
     *修改时保存
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward adminUpdateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        EduCourseFileActionForm form = (EduCourseFileActionForm)actionForm;
        EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
        
       String courseid = Encode.nullToBlank(httpServletRequest.getParameter("courseid"));
        String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
        httpServletRequest.setAttribute("courseid", courseid);
        httpServletRequest.setAttribute("columnid", columnid);
            
        HttpSession session = httpServletRequest.getSession();
        Integer userid = (Integer) session.getAttribute("s_userid");
            
        if (isTokenValid(httpServletRequest, true)) {
            try {
                EduCourseFile model = form.getEduCourseFile();
                model.setCreatedate(DateTime.getDate());
                manager.updateEduCourseFile(model);
                addLog(httpServletRequest,"修改了一个课程文件");
                
                manager.updateEduCourseFile(model);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        
        httpServletRequest.setAttribute("reloadtree", "1");
        return adminList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }

    /**
     *批量删除
     *@param actionMapping ActionMapping
     *@param actionForm ActionForm
     *@param httpServletRequest HttpServletRequest
     *@param httpServletResponse HttpServletResponse
     *@return ActionForward
     */
    public ActionForward adminDelBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
        String[] checkids = httpServletRequest.getParameterValues("checkid");

        EduCourseFile model = null;
        for (int i = 0; i < checkids.length; i++) {
        	model = manager.getEduCourseFile(checkids[i]);
            manager.delEduCourseFile(checkids[i]);
            ConvertFile.convertCourseFile(model, "delete", 0);
        }
        return adminList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
    }
    
    public ActionForward adminConvertFile(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
	    EduCourseFileManager manager = (EduCourseFileManager)getBean("eduCourseFileManager");
	    String fileid = Encode.nullToBlank(httpServletRequest.getParameter("fileid"));

	    EduCourseFile model = manager.getEduCourseFile(fileid);
	    ConvertFile.convertCourseFile(model, "update", 1);
	    manager.updateEduCourseFile(model);
        return adminList(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	    }

    /**
     * 资源在线预览
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-3-20 下午2:55:22 
    * @lastModified ; 2017-3-20 下午2:55:22 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
     */
    public ActionForward preview(ActionMapping actionMapping, ActionForm actionForm,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
        String fileid = Encode.nullToBlank(httpServletRequest.getParameter("fileid"));
        EduCourseFileManager manager = (EduCourseFileManager) getBean("eduCourseFileManager");
        EduCourseFile model = manager.getEduCourseFile(fileid);
        httpServletRequest.setAttribute("model", model);

        return actionMapping.findForward("preview");
    }
}