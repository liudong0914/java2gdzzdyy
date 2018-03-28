package com.wkmk.util.service.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.util.date.DateTime;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.UUID;
import com.util.action.BaseAction;
import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkPaperInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkPaperAnswerManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkPaperInfoManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsTypeManager;
import com.wkmk.tk.service.TkUserBookContentManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.service.form.FileUploadActionForm;

/**
 * ScienceWord client接口
 * @version 1.0
 * 说明：接口文档中所有字段类型都为字符串类型
 */
public class ClientServiceAction extends BaseAction {
	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			String key = Encode.nullToBlank(request.getParameter("key"));
			System.out.println("key>>>>>>>>>>>>>"+key);
			if(!"".equals(key)){
				if("GetAPIVer".equals(key)){
					result = getAPIVer();
				}
				if("Login".equals(key)){
					result = userLogin(request);
				}
				if("Logout".equals(key)){
					result = userLogout(request);
				}
				if("GetResourceTree".equals(key)){//右侧树列表
					result = getResourceTree(request);
				}
				if("GetResourceForderAttrTree".equals(key)){//右侧树列表
					result = GetResourceForderAttrTree(request);
				}
				if("GetResourceForderTree".equals(key)){//上传文件至服务器选择树列表
					result = getResourceForderTree(request);
				}
				if("UploadResource".equals(key)){
					result = uploadResource(request, form);
				}
				if("testLibrarySubmit".equals(key)){
					result = testLibrarySubmit(request, form);
				}
				
				if(result != null && !"".equals(result)){
					response.setCharacterEncoding("UTF-8");
					response.getWriter().write(result);
					return null;
				}
			}else {
				JSONObject resultJson = new JSONObject();
				resultJson.put("status", false);
				resultJson.put("errorMsg", "请求方法错误，无权限访问！");
				resultJson.put("errorCode", "00000");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(resultJson.toString());
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			JSONObject resultJson = new JSONObject();
			resultJson.put("status", false);
			resultJson.put("errorMsg", "请求出现错误，不允许执行此操作！");
			resultJson.put("errorCode", "00000");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(resultJson.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private String getRealpath(HttpServletRequest request){
		String realpath = request.getSession().getServletContext().getRealPath("/");
		return realpath;
	}
	
	private SysUserInfo getUserInfo(String token, String userId){
		SysUserInfo userInfo = null;
		try {
			String userid = CacheUtil.get("TOKEN_" + token);
			if(userId.equals(userid)){
				userid = DES.getDecryptPwd(userid);
				SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
				userInfo = manager.getSysUserInfo(userid);
			}
		} catch (Exception e) {
			userInfo = null;
			e.printStackTrace();
		}
		return userInfo;
	}
	
	/*
	 * 返回客户端版本号，返回默认值即可
	 */
	private String getAPIVer(){
		String result = null;
		try {
			JSONObject resultJson = new JSONObject();
			resultJson.put("apiVersion", 2);
			resultJson.put("status", true);
			resultJson.put("errorMsg", "");
			resultJson.put("errorCode", "");
			result = resultJson.toString();
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 用户登录【一个账号多处登录，将以最后一次登录为准】
	 */
	private String userLogin(HttpServletRequest request){
		String result = null;
		try {
    		String username = Encode.nullToBlank(request.getParameter("username"));
    		String password = Encode.nullToBlank(request.getParameter("password"));
    		String productType = Encode.nullToBlank(request.getParameter("productType"));//scienceword/class/exercise
    		
    		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo sui = manager.getUserInfoByLoginName(username);
			if(sui != null){
				password = MD5.getEncryptPwd(password);
				if("1".equals(sui.getStatus()) && password.equals(sui.getPassword())){
					result = "0";//验证成功
				}else {
					result = "-1";//账号被禁用
				}
			}
            
			JSONObject resultJson = new JSONObject();
			if("0".equals(result)){//验证成功
				String userid = DES.getEncryptPwd(sui.getUserid().toString());//userid以加密字符串方式传递给客户端
				String token = request.getSession().getId().toString();//token以用户登录时的sessionid传递给客户端
				resultJson.put("token", token);
				//用户信息
				JSONObject userJson = new JSONObject();
				userJson.put("id", userid);
				userJson.put("name", sui.getUsername());
				userJson.put("userId", userid);
				resultJson.put("userInfo", userJson.toString());
				//tab菜单按钮
				List<JSONObject> tabList = new ArrayList<JSONObject>();
				JSONObject tabJson = new JSONObject();
				tabJson.put("Title", "作业列表");
				tabJson.put("UIType", "Tree");
				tabJson.put("ReadOnly", false);
				tabJson.put("isUploadResource", true);
				tabJson.put("isUploadTest", true);
				tabJson.put("TabID", "1");
				tabJson.put("IconURL", "");
				tabList.add(tabJson);
				resultJson.put("tabList", JSONArray.fromObject(tabList));
				resultJson.put("spaceUrl", "");
				resultJson.put("status", true);
				resultJson.put("errorMsg", "");
				resultJson.put("errorCode", "");
				
				//将键值：sessionid:userid放入缓存中
				CacheUtil.put("TOKEN_" + token, userid, 24*7);//有效期一周，关闭客户端将会自动调用退出接口方法
			}else {//验证失败
				resultJson.put("status", false);
				if("-1".equals(result)){
					resultJson.put("errorMsg", "用户名或密码错误！");
				}else {
					resultJson.put("errorMsg", "不存在该用户!");
				}
				resultJson.put("errorCode", "00001");
			}
			String resultStr = resultJson.toString();
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 返回客户端版本号，返回默认值即可
	 */
	private String userLogout(HttpServletRequest request){
		String result = null;
		try {
			String token = Encode.nullToBlank(request.getParameter("token"));
			if(!"".equals(token)){
				//清空缓存：sessionid:userid
				CacheUtil.deleteObject("TOKEN_" + token);
			}
			JSONObject resultJson = new JSONObject();
			resultJson.put("status", true);
			resultJson.put("errorMsg", "");
			resultJson.put("errorCode", "");
			result = resultJson.toString();
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
//	/*
//	 * 客户端右侧树列表
//	 * 可通过用户权限查询对应的学科-年级-作业本
//	 */
//	private String getResourceTree(HttpServletRequest request){
//		String result = null;
//		try {
//    		String userId = Encode.nullToBlank(request.getParameter("userId"));
//    		String token = Encode.nullToBlank(request.getParameter("token"));
//    		String folderId = Encode.nullToBlank(request.getParameter("folderId"));//“1-”开头表示学科，“2-”开头表示年级，“3-”开头表示教材版本，“4-”开头表示作业本，“5-”开头表示作业本内容
//    		String tabindex = Encode.nullToBlank(request.getParameter("tabindex"));
//    		
//    		//判断用户的有效性
//    		SysUserInfo userInfo = getUserInfo(token, userId);
//    		if(userInfo == null){
//    			JSONObject resultJson = new JSONObject();
//    			resultJson.put("status", false);
//    			resultJson.put("errorMsg", "用户已失效，请重新登录！");
//    			resultJson.put("errorCode", "00001");
//    			result = resultJson.toString();
//    			return result;
//    		}
//    		
//    		if("0".equals(folderId)){
//    			//默认直接加载作业本提高效率
//    			TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
//    			List list = manager.getAllTkBookInfos();
//    			if(list != null && list.size() > 0){
//    				JSONObject resultJson = new JSONObject();
//    				
//    				List<JSONObject> lst = new ArrayList<JSONObject>();
//    				JSONObject json = null;
//    				Object[] object = null;
//    				for(int i=0, size=list.size(); i<size; i++){
//    					object = (Object[]) list.get(i);
//    					json = new JSONObject();
//    					json.put("id", "4-" + object[0]);
//    					json.put("title", object[5] + "-" + object[3] + "-" + object[1]);
//    					json.put("isFolder", true);
//    					json.put("ext", "");
//    					json.put("typeId", "4");//作业本对应4
//    					json.put("typeName", "作业本");
//    					json.put("fileType", "other");
//    					json.put("dateCreated", "");
//    					json.put("userId", userId);
//    					json.put("userName", userInfo.getUsername());
//    					json.put("isWritable", false);
//    					json.put("isPrintable", false);
//    					json.put("isPrivate", false);
//    					json.put("isSubmited", false);
//    					json.put("permitDel", false);
//    					json.put("permitMove", false);
//    					json.put("permitSave", false);
//    					json.put("version", "2");
//    					json.put("streamUrl", "");
//    					json.put("downloadUrl", "");
//    					json.put("visitUrl", "");
//    					lst.add(json);
//    				}
//    				resultJson.put("list", JSONArray.fromObject(lst));
//    				resultJson.put("status", true);
//        			resultJson.put("errorMsg", "");
//        			resultJson.put("errorCode", "");
//        			result = resultJson.toString();
//        			return result;
//    			}else {
//    				JSONObject resultJson = new JSONObject();
//    				resultJson.put("list", "[]");
//    				resultJson.put("status", true);
//        			resultJson.put("errorMsg", "");
//        			resultJson.put("errorCode", "");
//        			result = resultJson.toString();
//        			return result;
//    				
//				}
//			}else if(folderId.startsWith("4-")){
//				String bookid = folderId.substring(2);
//    			TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
//    			List<SearchModel> condition = new ArrayList<SearchModel>();
//    			SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
//    			List list = manager.getTkBookContents(condition, "contentno asc", 0);
//    			if(list != null && list.size() > 0){
//    				JSONObject resultJson = new JSONObject();
//    				
//    				List<JSONObject> lst = new ArrayList<JSONObject>();
//    				JSONObject json = null;
//    				TkBookContent tbc = null;
//    				for(int i=0, size=list.size(); i<size; i++){
//    					tbc = (TkBookContent) list.get(i);
//    					json = new JSONObject();
//    					json.put("id", "5-" + tbc.getBookcontentid());
//    					json.put("title", tbc.getTitle());
//    					json.put("isFolder", false);
//    					json.put("ext", "");
//    					json.put("typeId", "5");//作业本内容对应5
//    					json.put("typeName", "作业本内容");
//    					json.put("fileType", "other");
//    					json.put("dateCreated", "");
//    					json.put("userId", userId);
//    					json.put("userName", userInfo.getUsername());
//    					json.put("isWritable", false);
//    					json.put("isPrintable", false);
//    					json.put("isPrivate", false);
//    					json.put("isSubmited", false);
//    					json.put("permitDel", false);
//    					json.put("permitMove", false);
//    					json.put("permitSave", true);
//    					json.put("version", "2");
//    					json.put("streamUrl", "");
//    					json.put("downloadUrl", "");
//    					json.put("visitUrl", "");
//    					lst.add(json);
//    				}
//    				resultJson.put("list", JSONArray.fromObject(lst));
//    				resultJson.put("status", true);
//        			resultJson.put("errorMsg", "");
//        			resultJson.put("errorCode", "");
//        			result = resultJson.toString();
//        			return result;
//    			}else {
//    				JSONObject resultJson = new JSONObject();
//    				resultJson.put("list", "[]");
//    				resultJson.put("status", true);
//        			resultJson.put("errorMsg", "");
//        			resultJson.put("errorCode", "");
//        			result = resultJson.toString();
//        			return result;
//    				
//				}
//			}else if(folderId.startsWith("5-")){
//				
//			}
//            
//		} catch (Exception e) {
//			result = null;
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
	
	/*
	 * 客户端右侧树列表
	 * 可通过用户权限查询对应的学科-年级-作业本
	 */
	private String getResourceTree(HttpServletRequest request){
		String result = null;
		try {
    		String userId = Encode.nullToBlank(request.getParameter("userId"));
    		String token = Encode.nullToBlank(request.getParameter("token"));
    		String folderId = Encode.nullToBlank(request.getParameter("folderId"));//“1-”开头表示学科，“2-”开头表示年级，“3-”开头表示教材版本，“4-”开头表示作业本，“5-”开头表示作业本内容
    		String tabindex = Encode.nullToBlank(request.getParameter("tabindex"));
    		
    		//判断用户的有效性
    		SysUserInfo userInfo = getUserInfo(token, userId);
    		if(userInfo == null){
    			JSONObject resultJson = new JSONObject();
    			resultJson.put("status", false);
    			resultJson.put("errorMsg", "用户已失效，请重新登录！");
    			resultJson.put("errorCode", "00001");
    			result = resultJson.toString();
    			return result;
    		}
    		
    		if("0".equals(folderId)){
    			TkUserBookContentManager tubcm = (TkUserBookContentManager) getBean("tkUserBookContentManager");
    			List subjectidList = tubcm.getTkUserBookContentOfIds(userInfo.getUserid(), "subjectid");
    			//默认加载学科列表
    			EduSubjectInfoManager manager = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
    			List<SearchModel> condition = new ArrayList<SearchModel>();
    			SearchCondition.addCondition(condition, "status", "=", "1");
    			List list = manager.getEduSubjectInfos(condition, "orderindex asc", 0);
    			if(list != null && list.size() > 0){
    				JSONObject resultJson = new JSONObject();
    				
    				List<JSONObject> lst = new ArrayList<JSONObject>();
    				JSONObject json = null;
    				EduSubjectInfo esi = null;
    				for(int i=0, size=list.size(); i<size; i++){
    					esi = (EduSubjectInfo) list.get(i);
    					if(subjectidList.contains(esi.getSubjectid())){
    						json = new JSONObject();
        					json.put("id", "1-" + esi.getSubjectid());
        					json.put("title", esi.getSubjectname());
        					json.put("isFolder", true);
        					json.put("ext", "");
        					json.put("typeId", "1");//学科对应1
        					json.put("typeName", "学科");
        					json.put("fileType", "other");
        					json.put("dateCreated", "");
        					json.put("userId", userId);
        					json.put("userName", userInfo.getUsername());
        					json.put("isWritable", false);
        					json.put("isPrintable", false);
        					json.put("isPrivate", false);
        					json.put("isSubmited", false);
        					json.put("permitDel", false);
        					json.put("permitMove", false);
        					json.put("permitSave", false);
        					json.put("version", "2");
        					json.put("streamUrl", "");
        					json.put("downloadUrl", "");
        					json.put("visitUrl", "");
        					lst.add(json);
    					}
    				}
    				resultJson.put("list", JSONArray.fromObject(lst));
    				resultJson.put("status", true);
        			resultJson.put("errorMsg", "");
        			resultJson.put("errorCode", "");
        			result = resultJson.toString();
        			return result;
    			}else {
    				JSONObject resultJson = new JSONObject();
    				resultJson.put("list", "[]");
    				resultJson.put("status", true);
        			resultJson.put("errorMsg", "");
        			resultJson.put("errorCode", "");
        			result = resultJson.toString();
        			return result;
    				
				}
    		}else if(folderId.startsWith("1-")){
    			TkUserBookContentManager tubcm = (TkUserBookContentManager) getBean("tkUserBookContentManager");
    			List gradeidList = tubcm.getTkUserBookContentOfIds(userInfo.getUserid(), "gradeid");
    			
    			String subjectid = folderId.substring(2);
    			EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
    			List<SearchModel> condition = new ArrayList<SearchModel>();
    			SearchCondition.addCondition(condition, "status", "=", "1");
    			SearchCondition.addCondition(condition, "subjectid", "=", Integer.valueOf(subjectid));
    			List list = manager.getEduGradeInfos(condition, "orderindex asc", 0);
    			if(list != null && list.size() > 0){
    				JSONObject resultJson = new JSONObject();
    				
    				List<JSONObject> lst = new ArrayList<JSONObject>();
    				JSONObject json = null;
    				EduGradeInfo egi = null;
    				for(int i=0, size=list.size(); i<size; i++){
    					egi = (EduGradeInfo) list.get(i);
    					if(gradeidList.contains(egi.getGradeid())){
    						json = new JSONObject();
        					json.put("id", "2-" + egi.getGradeid());
        					json.put("title", egi.getGradename());
        					json.put("isFolder", true);
        					json.put("ext", "");
        					json.put("typeId", "2");//年级对应2
        					json.put("typeName", "年级");
        					json.put("fileType", "other");
        					json.put("dateCreated", "");
        					json.put("userId", userId);
        					json.put("userName", userInfo.getUsername());
        					json.put("isWritable", false);
        					json.put("isPrintable", false);
        					json.put("isPrivate", false);
        					json.put("isSubmited", false);
        					json.put("permitDel", false);
        					json.put("permitMove", false);
        					json.put("permitSave", false);
        					json.put("version", "2");
        					json.put("streamUrl", "");
        					json.put("downloadUrl", "");
        					json.put("visitUrl", "");
        					lst.add(json);
    					}
    				}
    				resultJson.put("list", JSONArray.fromObject(lst));
    				resultJson.put("status", true);
        			resultJson.put("errorMsg", "");
        			resultJson.put("errorCode", "");
        			result = resultJson.toString();
        			return result;
    			}else {
    				JSONObject resultJson = new JSONObject();
    				resultJson.put("list", "[]");
    				resultJson.put("status", true);
        			resultJson.put("errorMsg", "");
        			resultJson.put("errorCode", "");
        			result = resultJson.toString();
        			return result;
    				
				}
    		}else if(folderId.startsWith("2-")){
    			TkUserBookContentManager tubcm = (TkUserBookContentManager) getBean("tkUserBookContentManager");
    			List bookidList = tubcm.getTkUserBookContentOfIds(userInfo.getUserid(), "bookid");
    			
    			String gradeid = folderId.substring(2);
    			TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
    			List<SearchModel> condition = new ArrayList<SearchModel>();
    			SearchCondition.addCondition(condition, "status", "=", "1");
    			SearchCondition.addCondition(condition, "gradeid", "=", Integer.valueOf(gradeid));
    			List list = manager.getTkBookInfos(condition, "version asc, part asc", 0);
    			if(list != null && list.size() > 0){
    				JSONObject resultJson = new JSONObject();
    				
    				List<JSONObject> lst = new ArrayList<JSONObject>();
    				JSONObject json = null;
    				TkBookInfo tbi = null;
    				for(int i=0, size=list.size(); i<size; i++){
    					tbi = (TkBookInfo) list.get(i);
    					if(bookidList.contains(tbi.getBookid())){
    						json = new JSONObject();
        					json.put("id", "4-" + tbi.getBookid());
        					json.put("title", tbi.getTitle());
        					json.put("isFolder", true);
        					json.put("ext", "");
        					json.put("typeId", "4");//作业本对应4
        					json.put("typeName", "作业本");
        					json.put("fileType", "other");
        					json.put("dateCreated", "");
        					json.put("userId", userId);
        					json.put("userName", userInfo.getUsername());
        					json.put("isWritable", false);
        					json.put("isPrintable", false);
        					json.put("isPrivate", false);
        					json.put("isSubmited", false);
        					json.put("permitDel", false);
        					json.put("permitMove", false);
        					json.put("permitSave", false);
        					json.put("version", "2");
        					json.put("streamUrl", "");
        					json.put("downloadUrl", "");
        					json.put("visitUrl", "");
        					lst.add(json);
    					}
    				}
    				resultJson.put("list", JSONArray.fromObject(lst));
    				resultJson.put("status", true);
        			resultJson.put("errorMsg", "");
        			resultJson.put("errorCode", "");
        			result = resultJson.toString();
        			return result;
    			}else {
    				JSONObject resultJson = new JSONObject();
    				resultJson.put("list", "[]");
    				resultJson.put("status", true);
        			resultJson.put("errorMsg", "");
        			resultJson.put("errorCode", "");
        			result = resultJson.toString();
        			return result;
    				
				}
			}else if(folderId.startsWith("3-")){
				
			}else if(folderId.startsWith("4-")){
				TkUserBookContentManager tubcm = (TkUserBookContentManager) getBean("tkUserBookContentManager");
    			List bookcontentidList = tubcm.getTkUserBookContentOfIds(userInfo.getUserid(), "bookcontentid");
    			
				String bookid = folderId.substring(2);
    			TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
    			TkPaperInfoManager tpim = (TkPaperInfoManager) getBean("tkPaperInfoManager");
    			List<SearchModel> condition = new ArrayList<SearchModel>();
    			SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
    			List list = manager.getTkBookContents(condition, "contentno asc", 0);
    			if(list != null && list.size() > 0){
    				JSONObject resultJson = new JSONObject();
    				
    				List<JSONObject> lst = new ArrayList<JSONObject>();
    				JSONObject json = null;
    				TkBookContent tbc = null;
    				TkPaperInfo tpi = null;
    				for(int i=0, size=list.size(); i<size; i++){
    					tbc = (TkBookContent) list.get(i);
    					if(bookcontentidList.contains(tbc.getBookcontentid())){
    						json = new JSONObject();
        					json.put("id", "5-" + tbc.getBookcontentid());
        					if(tbc.getPaperid().intValue() == 0){
        						json.put("title", tbc.getTitle() + "【无文件】");
        						json.put("streamUrl", "");
            					json.put("downloadUrl", "");
            					json.put("visitUrl", "");
        					}else {
        						tpi = tpim.getTkPaperInfo(tbc.getPaperid());
        						json.put("title", tbc.getTitle());
        						json.put("streamUrl", "http://localhost/upload/" + tpi.getFilepath());
            					json.put("downloadUrl", "http://localhost/upload/" + tpi.getFilepath());
            					json.put("visitUrl", "http://localhost/upload/" + tpi.getFilepath());
							}
        					json.put("isFolder", false);
        					json.put("ext", "");
        					json.put("typeId", "5");//作业本内容对应5
        					json.put("typeName", "作业本内容");
        					json.put("fileType", "other");
        					json.put("dateCreated", "");
        					json.put("userId", userId);
        					json.put("userName", userInfo.getUsername());
        					json.put("isWritable", true);
        					json.put("isPrintable", true);
        					json.put("isPrivate", true);
        					json.put("isSubmited", true);
        					json.put("permitDel", false);
        					json.put("permitMove", false);
        					json.put("permitSave", true);
        					json.put("version", "2");
        					lst.add(json);
    					}
    				}
    				resultJson.put("list", JSONArray.fromObject(lst));
    				resultJson.put("status", true);
        			resultJson.put("errorMsg", "");
        			resultJson.put("errorCode", "");
        			result = resultJson.toString();
        			return result;
    			}else {
    				JSONObject resultJson = new JSONObject();
    				resultJson.put("list", "[]");
    				resultJson.put("status", true);
        			resultJson.put("errorMsg", "");
        			resultJson.put("errorCode", "");
        			result = resultJson.toString();
        			return result;
    				
				}
			}else if(folderId.startsWith("5-")){
				
			}
            
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 客户端后加接口，因为登录后会自动调用此接口，故此实现接口，但不做任何处理
	 */
	private String GetResourceForderAttrTree(HttpServletRequest request){
		String result = null;
		try {
    		String userId = Encode.nullToBlank(request.getParameter("userId"));
    		String token = Encode.nullToBlank(request.getParameter("token"));
    		String folderId = Encode.nullToBlank(request.getParameter("folderId"));
    		String tabId = Encode.nullToBlank(request.getParameter("tabId"));
    		String productType = Encode.nullToBlank(request.getParameter("productType"));
    		
    		//判断用户的有效性
    		/*
    		SysUserInfo userInfo = getUserInfo(token, userId);
    		if(userInfo == null){
    			JSONObject resultJson = new JSONObject();
    			resultJson.put("status", false);
    			resultJson.put("errorMsg", "用户已失效，请重新登录！");
    			resultJson.put("errorCode", "00001");
    			result = resultJson.toString();
    			return result;
    		}
    		*/
    		
    		JSONObject resultJson = new JSONObject();
			resultJson.put("status", true);
			resultJson.put("errorMsg", "");
			resultJson.put("errorCode", "");
			resultJson.put("list", "[]");
			result = resultJson.toString();
			return result;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 客户端上传文件至服务器右侧树列表
	 * 可通过用户权限查询对应的学科-年级-作业本
	 */
	private String getResourceForderTree(HttpServletRequest request){
		String result = null;
		try {
    		String userId = Encode.nullToBlank(request.getParameter("userId"));
    		String token = Encode.nullToBlank(request.getParameter("token"));
    		String folderId = Encode.nullToBlank(request.getParameter("folderId"));//“1-”开头表示学科，“2-”开头表示年级，“3-”开头表示教材版本，“4-”开头表示作业本，“5-”开头表示作业本内容
    		String tabindex = Encode.nullToBlank(request.getParameter("tabindex"));
    		String productType = Encode.nullToBlank(request.getParameter("productType"));
    		
    		//判断用户的有效性
    		SysUserInfo userInfo = getUserInfo(token, userId);
    		if(userInfo == null){
    			JSONObject resultJson = new JSONObject();
    			resultJson.put("status", false);
    			resultJson.put("errorMsg", "用户已失效，请重新登录！");
    			resultJson.put("errorCode", "00001");
    			result = resultJson.toString();
    			return result;
    		}
    		
			//默认直接加载作业本提高效率
			TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
			List list = manager.getAllTkBookInfos();
			if(list != null && list.size() > 0){
				TkUserBookContentManager tubcm = (TkUserBookContentManager) getBean("tkUserBookContentManager");
    			List bookidList = tubcm.getTkUserBookContentOfIds(userInfo.getUserid(), "bookid");
    			List bookcontentidList = tubcm.getTkUserBookContentOfIds(userInfo.getUserid(), "bookcontentid");
    			
    			//作业本内容
    			TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
    			List<SearchModel> condition = new ArrayList<SearchModel>();
    			SearchCondition.addCondition(condition, "parentno", "<>", "0000");
    			List bookContentList = tbcm.getTkBookContents(condition, "contentno asc", 0);
    			
				JSONObject resultJson = new JSONObject();
				List<JSONObject> lst = new ArrayList<JSONObject>();
				JSONObject json = null;
				List<JSONObject> bookContentLst = null;
				JSONObject bookContentJson = null;
				
				Object[] object = null;
				TkBookContent tbc = null;
				for(int i=0, size=list.size(); i<size; i++){
					object = (Object[]) list.get(i);
					if(bookidList.contains(Integer.valueOf(object[0].toString()))){
						json = new JSONObject();
						json.put("ResourceFolder_Id", "4-" + object[0]);
						json.put("ResourceFolder_ParentId", "0");
						json.put("ResourceFolder_Name", object[5] + "-" + object[3] + "-" + object[1]);
						json.put("isStore_Files", "0");
						//作业本内容
						if(bookContentList != null && bookContentList.size() > 0){
							bookContentLst = new ArrayList<JSONObject>();
							for(int m=0, n=bookContentList.size(); m<n; m++){
								tbc = (TkBookContent) bookContentList.get(m);
								if(object[0].toString().equals(tbc.getBookid().toString()) && bookcontentidList.contains(tbc.getBookcontentid())){
									bookContentJson = new JSONObject();
	    							bookContentJson.put("ResourceFolder_Id", "5-" + tbc.getBookcontentid());
	    							bookContentJson.put("ResourceFolder_ParentId", "4-" + tbc.getBookid());
	    							bookContentJson.put("ResourceFolder_Name", tbc.getTitle());
	    							bookContentJson.put("isStore_Files", "1");
	    							bookContentLst.add(bookContentJson);
								}
	    					}
							json.put("list",  JSONArray.fromObject(bookContentLst));
						}
						lst.add(json);
					}
				}
				
				resultJson.put("list", JSONArray.fromObject(lst));
				resultJson.put("status", true);
    			resultJson.put("errorMsg", "");
    			resultJson.put("errorCode", "");
    			result = resultJson.toString();
    			return result;
			}else {
				JSONObject resultJson = new JSONObject();
				resultJson.put("list", "[]");
				resultJson.put("status", true);
    			resultJson.put("errorMsg", "");
    			resultJson.put("errorCode", "");
    			result = resultJson.toString();
    			return result;
				
			}
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 上传整个文档至服务器
	 */
	private String uploadResource(HttpServletRequest request, ActionForm form){
		String result = null;
		try {
			String userId = Encode.nullToBlank(request.getParameter("userId"));
    		String token = Encode.nullToBlank(request.getParameter("token"));
    		String folderId = Encode.nullToBlank(request.getParameter("folderId"));//“1-”开头表示学科，“2-”开头表示年级，“3-”开头表示教材版本，“4-”开头表示作业本，“5-”开头表示作业本内容
    		String title = Encode.nullToBlank(request.getParameter("title"));
    		String ext = Encode.nullToBlank(request.getParameter("ext"));//扩展名 (dsc/class)
    		
    		//判断用户的有效性
    		SysUserInfo userInfo = getUserInfo(token, userId);
    		if(userInfo == null){
    			JSONObject resultJson = new JSONObject();
    			resultJson.put("status", false);
    			resultJson.put("errorMsg", "用户已失效，请重新登录！");
    			resultJson.put("errorCode", "00001");
    			result = resultJson.toString();
    			return result;
    		}
    		
    		if(folderId.startsWith("5-")){
    			String bookcontentid = folderId.substring(2);
    			TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
    			TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
    			TkPaperInfoManager tpim = (TkPaperInfoManager) getBean("tkPaperInfoManager");
    			TkBookContent tkBookContent = tbcm.getTkBookContent(bookcontentid);
    			TkBookInfo tkBookInfo = tbim.getTkBookInfo(tkBookContent.getBookid());
    			TkPaperInfo tkPaperInfo = null;
    			if(tkBookContent.getPaperid() != null && tkBookContent.getPaperid().intValue() > 0){
    				tkPaperInfo = tpim.getTkPaperInfo(tkBookContent.getPaperid());
    				TkPaperContentManager tpcm = (TkPaperContentManager) getBean("tkPaperContentManager");
    				int contentCount = tpcm.getTkPaperContentCounts(tkPaperInfo.getPaperid());
    				if(contentCount > 0){//试卷有内容
    					TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
        				int answerCount = tpam.getTkPaperAnswerCountsByBookcontentid(tkBookContent.getBookcontentid());
        				if(answerCount > 0){//试卷被作答了
        					JSONObject resultJson = new JSONObject();
                			resultJson.put("status", false);
                			resultJson.put("errorMsg", "当前作业内容已被上线使用了，不能修改！");
                			resultJson.put("errorCode", "00001");
                			result = resultJson.toString();
                			return result;
        				}else {
							//试卷有内容但没有被作答，需要生成新的试卷与作业本关联，原始试卷不变
        					tkPaperInfo.setTitle(tkPaperInfo.getTitle() + "【作业本与新试卷关联了】");
        					tpim.updateTkPaperInfo(tkPaperInfo);
        					
        					//【创建新的试卷与作业本关联】
        					tkPaperInfo = new TkPaperInfo();
        					tkPaperInfo.setTitle(tkBookContent.getTitle());
        					//tkPaperInfo.setTitle(title);
        					tkPaperInfo.setPapertype("1");
        					tkPaperInfo.setCreatedate(DateTime.getDate());
        					tkPaperInfo.setDescript(tkPaperInfo.getTitle());
        					tkPaperInfo.setStatus("1");
        					tkPaperInfo.setUserid(userInfo.getUserid());
        					tkPaperInfo.setGradeid(tkBookInfo.getGradeid());
        					tkPaperInfo.setSubjectid(tkBookInfo.getSubjectid());
        					tkPaperInfo.setUnitid(tkBookInfo.getUnitid());
        					tpim.addTkPaperInfo(tkPaperInfo);
        					tkBookContent.setPaperid(tkPaperInfo.getPaperid());
        					tbcm.updateTkBookContent(tkBookContent);
						}
    				}
    			}else {
					tkPaperInfo = new TkPaperInfo();
					tkPaperInfo.setTitle(tkBookContent.getTitle());
					//tkPaperInfo.setTitle(title);
					tkPaperInfo.setPapertype("1");
					tkPaperInfo.setCreatedate(DateTime.getDate());
					tkPaperInfo.setDescript(tkPaperInfo.getTitle());
					tkPaperInfo.setStatus("1");
					tkPaperInfo.setUserid(userInfo.getUserid());
					tkPaperInfo.setGradeid(tkBookInfo.getGradeid());
					tkPaperInfo.setSubjectid(tkBookInfo.getSubjectid());
					tkPaperInfo.setUnitid(tkBookInfo.getUnitid());
					tpim.addTkPaperInfo(tkPaperInfo);
					tkBookContent.setPaperid(tkPaperInfo.getPaperid());
					tbcm.updateTkBookContent(tkBookContent);
				}
    			
    			FileUploadActionForm fileForm = (FileUploadActionForm)form;
    			FormFile contentFile = fileForm.getContent(); //文档Base64流-sw.dsc
    			FormFile htmlContentFile = fileForm.getHtmlContent(); //文档HTML流-sw.htm
    			//先将dsc和htm文件存放到服务器上
    			String dscfilepath = null;
    			String htmlfilepath = null;
    			if(contentFile != null){
					String savepath = "tk/paper/" + tkPaperInfo.getPaperid();
					String filename = MD5.getEncryptPwd(UUID.getNewUUID()+"dsc") + "." + ext;
					String realpath = getRealpath(request);
					String filepath = realpath + "/upload/" + savepath;
					// 检查文件夹是否存在,如果不存在,新建一个
					if (!FileUtil.isExistDir(filepath)) {
						FileUtil.creatDir(filepath);
					}
					
					String path = filepath + "/" + filename;
					InputStream stream = contentFile.getInputStream(); // 把文件读入
					OutputStream bos = new FileOutputStream(path);
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
						bos.write(buffer, 0, bytesRead); // 将文件写入服务器
					}
					bos.close();
					stream.close();
					dscfilepath = savepath + "/" + filename;
				}
    			//htmlContent内容为纯html文本内容，没有html文件头信息，需要引入否则直接显示乱码
    			if(htmlContentFile != null){
					String savepath = "tk/paper/" + tkPaperInfo.getPaperid();
					String filename = MD5.getEncryptPwd(UUID.getNewUUID()+"html") + ".html";
					String realpath = getRealpath(request);
					String filepath = realpath + "/upload/" + savepath;
					// 检查文件夹是否存在,如果不存在,新建一个
					if (!FileUtil.isExistDir(filepath)) {
						FileUtil.creatDir(filepath);
					}
					
					String path = filepath + "/" + filename;
					InputStream stream = htmlContentFile.getInputStream(); // 把文件读入
					OutputStream bos = new FileOutputStream(path);
					//==========先将html文件头字符串写入================
					String html1 = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title>" + title + "</title></head><body>";
					byte[] html1Buffer = html1.getBytes("UTF-8");
					bos.write(html1Buffer);
					//===========================================
					//bos.write("<".getBytes("UTF-8"));//因为获取的原始html代码少了这个字符开头
					
					int bytesRead = 0;
					byte[] buffer = new byte[8192];
					while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
						bos.write(buffer, 0, bytesRead); // 将文件写入服务器
					}
					
					//==========再将html文件尾字符串写入================
					String html2 = "</body></html>";
					byte[] html2Buffer = html2.getBytes("UTF-8");
					bos.write(html2Buffer);
					//===========================================
					
					bos.close();
					stream.close();
					
					htmlfilepath = savepath + "/" + filename;
				}
    			if(dscfilepath != null && htmlfilepath != null){
    				tkPaperInfo.setFilepath(dscfilepath);
    				tkPaperInfo.setHtmlfilepath(htmlfilepath);
    				tpim.updateTkPaperInfo(tkPaperInfo);
    			}
        		
    			JSONObject resultJson = new JSONObject();
    			resultJson.put("status", true);
    			resultJson.put("errorMsg", "");
    			resultJson.put("errorCode", "");
    			result = resultJson.toString();
    		}else {
    			JSONObject resultJson = new JSONObject();
    			resultJson.put("status", false);
    			resultJson.put("errorMsg", "请选择具体作业目录存放试卷文件！");
    			resultJson.put("errorCode", "00001");
    			result = resultJson.toString();
    			return result;
			}
    		
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*
	 * 将每道题上传至服务器
	 */
	private String testLibrarySubmit(HttpServletRequest request, ActionForm form){
		String result = null;
		try {
			String userId = Encode.nullToBlank(request.getParameter("userId"));
    		String token = Encode.nullToBlank(request.getParameter("token"));
    		String folderId = Encode.nullToBlank(request.getParameter("folderId"));//“1-”开头表示学科，“2-”开头表示年级，“3-”开头表示教材版本，“4-”开头表示作业本，“5-”开头表示作业本内容
    		String resourceId = Encode.nullToBlank(request.getParameter("resourceId"));//资源编号， 有值：覆盖现有题库 空值：创建新题库
    		String title = Encode.nullToBlank(request.getParameter("title"));
    		
    		//判断用户的有效性
    		SysUserInfo userInfo = getUserInfo(token, userId);
    		if(userInfo == null){
    			JSONObject resultJson = new JSONObject();
    			resultJson.put("status", false);
    			resultJson.put("errorMsg", "用户已失效，请重新登录！");
    			resultJson.put("errorCode", "00001");
    			result = resultJson.toString();
    			return result;
    		}
    		
    		if(folderId.startsWith("5-")){
    			String bookcontentid = folderId.substring(2);
    			TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
    			TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
    			TkPaperInfoManager tpim = (TkPaperInfoManager) getBean("tkPaperInfoManager");
    			TkBookContent tkBookContent = tbcm.getTkBookContent(bookcontentid);
    			TkBookInfo tkBookInfo = tbim.getTkBookInfo(tkBookContent.getBookid());
    			TkPaperInfo tkPaperInfo = null;
    			if(tkBookContent.getPaperid() != null && tkBookContent.getPaperid().intValue() > 0){
    				tkPaperInfo = tpim.getTkPaperInfo(tkBookContent.getPaperid());
    				/*初步控制：如果试卷有题内容，则需要判断当前试卷是否被作答过，如果作答过则不可上传修改，
    				     如果没有被作答过，则需要提示上传整个文档，然后再碎片化，原始试卷数据不变，只是作业本不关联了这个试卷；如果试卷没有内容，则直接添加*/
    				TkPaperContentManager tpcm = (TkPaperContentManager) getBean("tkPaperContentManager");
    				int contentCount = tpcm.getTkPaperContentCounts(tkPaperInfo.getPaperid());
    				if(contentCount > 0){//试卷有内容
    					TkPaperAnswerManager tpam = (TkPaperAnswerManager) getBean("tkPaperAnswerManager");
        				int answerCount = tpam.getTkPaperAnswerCountsByBookcontentid(tkBookContent.getBookcontentid());
        				if(answerCount > 0){//试卷被作答了
        					JSONObject resultJson = new JSONObject();
                			resultJson.put("status", false);
                			resultJson.put("errorMsg", "当前作业内容已被上线使用了，不能修改！");
                			resultJson.put("errorCode", "00001");
                			result = resultJson.toString();
                			return result;
        				}else {//试卷有内容但没有被作答
        					JSONObject resultJson = new JSONObject();
                			resultJson.put("status", false);
                			resultJson.put("errorMsg", "请先将当前整个文档上传至服务器，再将文档的每道题上传至服务器！");
                			resultJson.put("errorCode", "00001");
                			result = resultJson.toString();
                			return result;
						}
    				}
    			}else {
    				JSONObject resultJson = new JSONObject();
        			resultJson.put("status", false);
        			resultJson.put("errorMsg", "请先将整个文档上传至服务器，并对应好作业本内容！");
        			resultJson.put("errorCode", "00001");
        			result = resultJson.toString();
        			return result;
    			}
    			
    			//解析post传递过来的json数据
    			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(),HTTP.UTF_8));
                String line = null;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String str = new String(sb.toString().getBytes(request.getCharacterEncoding()), HTTP.UTF_8);
                JSONObject json = JSONObject.fromObject(str);
    			//JSONArray jsonArray = json.getJSONArray("answerJson");
    			JSONArray jsonArray = json.getJSONArray("list");
    			if(jsonArray != null && jsonArray.size() > 0){
    				JSONObject tkJson = null;
    				JSONArray scoreTextArray = null;//分值
    				JSONArray testCorrect = null;//选择题
    				JSONArray testCorrectBase64Array = null;//标准答案（图片Base64）；填空题、解答题专用
    				JSONArray testSelectionsArray = null;//选择项
    				JSONObject testSelectionsJson = null;
    				
    				TkQuestionsInfoManager tqim = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
    				TkPaperContentManager tpcm = (TkPaperContentManager) getBean("tkPaperContentManager");
    				TkQuestionsTypeManager tqtm = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
    				TkQuestionsInfo tqi = null;
    				TkPaperContent tpc = null;
    				TkQuestionsType tqt = null;
    				String testType = null;// 题型 (selection选择题/fill填空题/answers解答题/truefalse判断题/title标题)
    				String option = null;
    				StringBuffer questionno = null;
    				String analyzeHyperlink = null;//解析超链接文件路径地址
    				String analyzeHyperlinkData = null;
    				File analyzeHyperlinkFile = null;
    				
    				String savepath = "tk/paper/" + tkPaperInfo.getPaperid();
					String realpath = getRealpath(request);
					String filepath = realpath + "/upload/";
					String path = null;
					FileWriter fw = null;
					PrintWriter pw = null;
					// 检查文件夹是否存在,如果不存在,新建一个
					if (!FileUtil.isExistDir(filepath + savepath + "/jiexi")) {
						FileUtil.creatDir(filepath + savepath + "/jiexi");
					}
					if (!FileUtil.isExistDir(filepath + savepath + "/shiti")) {
						FileUtil.creatDir(filepath + savepath + "/shiti");
					}
					//对应关系：测量目标=年份（theyear），考查内容=标签（tag），适用地区=地区（area），typeText（题型框文本）=是否有标准答案（isrightans）
					
					StringBuffer scoreText = null;
					StringBuffer testCorrectBase64 = null;
    				for(int i=0, size=jsonArray.size(); i<size; i++){
    					tkJson = jsonArray.getJSONObject(i);
    					testType = tkJson.getString("testType");
    					if("selection".equals(testType)){
    						scoreTextArray = tkJson.getJSONArray("scoreText");
    						testSelectionsArray = tkJson.getJSONArray("testSelections");
    						testCorrect = tkJson.getJSONArray("testCorrect");
    						tqi = new TkQuestionsInfo();
        					tpc = new TkPaperContent();
        					
        					tqi.setParentid(0);
        					tqi.setTitle(tkBookInfo.getTitle() + "-" + tkBookContent.getTitle() + "-第" + (i+1) + "题");
        					tqi.setTitlecontent(tkJson.getString("docHtml"));
        					tqi.setFilepath(savepath + "/shiti/" + (i+1) + ".dsc");
    						analyzeHyperlinkData = tkJson.getString("docBase64");
    						
    						//将试题dsc文件上传至服务器
    						path = filepath + tqi.getFilepath();
    						analyzeHyperlinkFile = new File(path);
    						if(!analyzeHyperlinkFile.exists()){
    							analyzeHyperlinkFile.createNewFile();
    						}
    						fw = new FileWriter(analyzeHyperlinkFile, false);
    						pw = new PrintWriter(fw);
    						pw.println(analyzeHyperlinkData);
    						pw.flush();
    						fw.flush();
    						pw.close();
    						fw.close();
    						
        					tqi.setOptionnum(testSelectionsArray.size());
        					for(int m=0, n=testSelectionsArray.size(); m<n; m++){
        						testSelectionsJson = testSelectionsArray.getJSONObject(m);
        						option = testSelectionsJson.getString("selectionText");
        						if("A".equals(option)){
        							tqi.setOption1("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}else if("B".equals(option)){
        							tqi.setOption2("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}else if("C".equals(option)){
        							tqi.setOption3("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}else if("D".equals(option)){
        							tqi.setOption4("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}else if("E".equals(option)){
        							tqi.setOption5("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}else if("F".equals(option)){
        							tqi.setOption6("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}else if("G".equals(option)){
        							tqi.setOption7("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}else if("H".equals(option)){
        							tqi.setOption8("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}else if("I".equals(option)){
        							tqi.setOption9("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}else if("J".equals(option)){
        							tqi.setOption10("<img border=\"0px\" src=\"data:image/png;base64," + testSelectionsJson.getString("selectionBase64") + "\" />");
        						}
        					}
        					tqi.setQuestiontype("O");
        					tqi.setDoctype("1");
        					tqi.setRightans(testCorrect.getString(0));
        					tqi.setIsrightans("1");
        					if("".equals(tkJson.getString("complexityText")) || "难易度".equals(tkJson.getString("complexityText"))){
        						tqi.setDifficult("A");
        					}else {
        						tqi.setDifficult(tkJson.getString("complexityText"));
							}
        					if("".equals(scoreTextArray.getString(0)) || "分值".equals(scoreTextArray.getString(0))){
        						tqi.setScore("0");
        					}else {
        						tqi.setScore(scoreTextArray.getString(0));
							}
        					tqi.setCretatdate(DateTime.getDate());
        					tqi.setUpdatetime(tqi.getCretatdate());
        					
        					analyzeHyperlink = tkJson.getString("analyzeHyperlink");
        					if(analyzeHyperlink != null && !"".equals(analyzeHyperlink)){
        						tqi.setDescript(tkJson.getString("analyzeHyperlinkHTML"));
        						tqi.setDescriptpath(savepath + "/jiexi/" + (i+1) + ".dsc");
        						analyzeHyperlinkData = tkJson.getString("analyzeHyperlinkData");
        						
        						//将解析超链接dsc文件上传至服务器
        						path = filepath + tqi.getDescriptpath();
        						analyzeHyperlinkFile = new File(path);
        						if(!analyzeHyperlinkFile.exists()){
        							analyzeHyperlinkFile.createNewFile();
        						}
        						fw = new FileWriter(analyzeHyperlinkFile, false);
        						pw = new PrintWriter(fw);
        						pw.println(analyzeHyperlinkData);
        						pw.flush();
        						fw.flush();
        						pw.close();
        						fw.close();
        					}else {
								tqi.setDescript(tkJson.getString("analyzeText"));
							}
        					
        					tqi.setStatus("1");
        					if("适用地区".equals(tkJson.getString("areaText"))){
        						tqi.setArea("");
        					}else {
        						tqi.setArea(tkJson.getString("areaText"));
							}
        					if("测量目标".equals(tkJson.getString("targetText"))){
        						tqi.setTheyear("");
        					}else {
        						tqi.setTheyear(tkJson.getString("targetText"));
							}
        					tqi.setAuthorname(userInfo.getUsername());
        					tqi.setAuthorid(userInfo.getUserid());
        					//根据题型获取学科的题型
        					if(tqi.getRightans().length() > 1){//多选
        						tqt = tqtm.getTkQuestionsType("B", tkBookInfo.getSubjectid());
        						if(tqt == null){
        							tqt = new TkQuestionsType();
        							tqt.setTypeno("0000");
        							tqt.setTypename("多选题");
        							tqt.setType("B");
        							tqt.setSubjectid(tkBookInfo.getSubjectid());
        							tqt.setUnitid(tkBookInfo.getUnitid());
        							tqtm.addTkQuestionsType(tqt);
        						}
        					}else {//单选
        						tqt = tqtm.getTkQuestionsType("A", tkBookInfo.getSubjectid());
        						if(tqt == null){
        							tqt = new TkQuestionsType();
        							tqt.setTypeno("0000");
        							tqt.setTypename("单选题");
        							tqt.setType("A");
        							tqt.setSubjectid(tkBookInfo.getSubjectid());
        							tqt.setUnitid(tkBookInfo.getUnitid());
        							tqtm.addTkQuestionsType(tqt);
        						}
							}
        					tqi.setTkQuestionsType(tqt);
        					tqi.setSubjectid(tkBookInfo.getSubjectid());
        					tqi.setGradeid(tkBookInfo.getGradeid());
        					tqi.setUnitid(tkBookInfo.getUnitid());
        					tqi.setTag(tkBookInfo.getTitle() + ";" + tkBookContent.getTitle() + ";第" + (i+1) + "题");
        					if(tkJson.getString("contentText") != null && !"".equals(tkJson.getString("contentText")) && !"考查内容".equals(tkJson.getString("contentText"))){
        						tqi.setTag(tqi.getTag() + ";" + tkJson.getString("contentText"));
        					}
        					tqim.addTkQuestionsInfo(tqi);
        					
        					questionno = new StringBuffer();
        					for (int x=tqi.getQuestionid().toString().length(); x < 10; x++) {
        						questionno.append("0");
        					}
        					questionno.append(tqi.getQuestionid());
        					tqi.setQuestionno(questionno.toString());
        					tqim.updateTkQuestionsInfo(tqi);
        					
        					tpc.setPaperid(tkPaperInfo.getPaperid());
        					tpc.setQuestionid(tqi.getQuestionid());
        					tpc.setOrderindex(i+1);
        					tpc.setScore(Float.valueOf(tqi.getScore()));
        					tpcm.addTkPaperContent(tpc);
    					}else if("truefalse".equals(testType) && !"填空题".equals(tkJson.getString("typeText"))){//判断题
    						scoreTextArray = tkJson.getJSONArray("scoreText");
    						testCorrectBase64Array = tkJson.getJSONArray("testCorrectBase64");
    						tqi = new TkQuestionsInfo();
        					tpc = new TkPaperContent();
        					
        					tqi.setParentid(0);
        					tqi.setTitle(tkBookInfo.getTitle() + "-" + tkBookContent.getTitle() + "-第" + (i+1) + "题");
        					tqi.setTitlecontent(tkJson.getString("docHtml"));
        					tqi.setFilepath(savepath + "/shiti/" + (i+1) + ".dsc");
    						analyzeHyperlinkData = tkJson.getString("docBase64");
    						
    						//将试题dsc文件上传至服务器
    						path = filepath + tqi.getFilepath();
    						analyzeHyperlinkFile = new File(path);
    						if(!analyzeHyperlinkFile.exists()){
    							analyzeHyperlinkFile.createNewFile();
    						}
    						fw = new FileWriter(analyzeHyperlinkFile, false);
    						pw = new PrintWriter(fw);
    						pw.println(analyzeHyperlinkData);
    						pw.flush();
    						fw.flush();
    						pw.close();
    						fw.close();
        					
        					tqi.setOptionnum(1);
    						if("".equals(scoreTextArray.getString(0)) || "分值".equals(scoreTextArray.getString(0))){
        						tqi.setScore("0");
        					}else {
        						tqi.setScore(scoreTextArray.getString(0));
							}
    						if("对".equals(testCorrectBase64Array.getString(0))){
    							tqi.setRightans("1");
    						}else {
    							tqi.setRightans("0");
							}
        					tqi.setQuestiontype("O");
        					tqi.setDoctype("1");
        					tqi.setIsrightans("1");
        					if("".equals(tkJson.getString("complexityText")) || "难易度".equals(tkJson.getString("complexityText"))){
        						tqi.setDifficult("A");
        					}else {
        						tqi.setDifficult(tkJson.getString("complexityText"));
							}
        					tqi.setCretatdate(DateTime.getDate());
        					tqi.setUpdatetime(tqi.getCretatdate());
        					
        					analyzeHyperlink = tkJson.getString("analyzeHyperlink");
        					if(analyzeHyperlink != null && !"".equals(analyzeHyperlink)){
        						tqi.setDescript(tkJson.getString("analyzeHyperlinkHTML"));
        						tqi.setDescriptpath(savepath + "/jiexi/" + (i+1) + ".dsc");
        						analyzeHyperlinkData = tkJson.getString("analyzeHyperlinkData");
        						
        						//将解析超链接dsc文件上传至服务器
        						path = filepath + tqi.getDescriptpath();
        						analyzeHyperlinkFile = new File(path);
        						if(!analyzeHyperlinkFile.exists()){
        							analyzeHyperlinkFile.createNewFile();
        						}
        						fw = new FileWriter(analyzeHyperlinkFile, false);
        						pw = new PrintWriter(fw);
        						pw.println(analyzeHyperlinkData);
        						pw.flush();
        						fw.flush();
        						pw.close();
        						fw.close();
        					}else {
								tqi.setDescript(tkJson.getString("analyzeText"));
							}
        					
        					tqi.setStatus("1");
        					if("适用地区".equals(tkJson.getString("areaText"))){
        						tqi.setArea("");
        					}else {
        						tqi.setArea(tkJson.getString("areaText"));
							}
        					if("测量目标".equals(tkJson.getString("targetText"))){
        						tqi.setTheyear("");
        					}else {
        						tqi.setTheyear(tkJson.getString("targetText"));
							}
        					tqi.setAuthorname(userInfo.getUsername());
        					tqi.setAuthorid(userInfo.getUserid());
        					//根据题型获取学科的题型
    						tqt = tqtm.getTkQuestionsType("C", tkBookInfo.getSubjectid());
    						if(tqt == null){
    							tqt = new TkQuestionsType();
    							tqt.setTypeno("0000");
    							tqt.setTypename("判断题");
    							tqt.setType("C");
    							tqt.setSubjectid(tkBookInfo.getSubjectid());
    							tqt.setUnitid(tkBookInfo.getUnitid());
    							tqtm.addTkQuestionsType(tqt);
    						}
        					tqi.setTkQuestionsType(tqt);
        					tqi.setSubjectid(tkBookInfo.getSubjectid());
        					tqi.setGradeid(tkBookInfo.getGradeid());
        					tqi.setUnitid(tkBookInfo.getUnitid());
        					tqi.setTag(tkBookInfo.getTitle() + ";" + tkBookContent.getTitle() + ";第" + (i+1) + "题");
        					if(tkJson.getString("contentText") != null && !"".equals(tkJson.getString("contentText")) && !"考查内容".equals(tkJson.getString("contentText"))){
        						tqi.setTag(tqi.getTag() + ";" + tkJson.getString("contentText"));
        					}
        					tqim.addTkQuestionsInfo(tqi);
        					
        					questionno = new StringBuffer();
        					for (int x=tqi.getQuestionid().toString().length(); x < 10; x++) {
        						questionno.append("0");
        					}
        					questionno.append(tqi.getQuestionid());
        					tqi.setQuestionno(questionno.toString());
        					tqim.updateTkQuestionsInfo(tqi);
        					
        					tpc.setPaperid(tkPaperInfo.getPaperid());
        					tpc.setQuestionid(tqi.getQuestionid());
        					tpc.setOrderindex(Integer.valueOf(i+1));
        					tpc.setScore(Float.valueOf(tqi.getScore()));
        					tpcm.addTkPaperContent(tpc);
    						
    					}else if("truefalse".equals(testType) && "填空题".equals(tkJson.getString("typeText"))){//通过判断题格式实现有标准答案的填空题
    						scoreTextArray = tkJson.getJSONArray("scoreText");
    						testCorrectBase64Array = tkJson.getJSONArray("testCorrectBase64");
    						tqi = new TkQuestionsInfo();
        					tpc = new TkPaperContent();
        					
        					tqi.setParentid(0);
        					tqi.setTitle(tkBookInfo.getTitle() + "-" + tkBookContent.getTitle() + "-第" + (i+1) + "题");
        					tqi.setTitlecontent(tkJson.getString("docHtml"));
        					tqi.setFilepath(savepath + "/shiti/" + (i+1) + ".dsc");
    						analyzeHyperlinkData = tkJson.getString("docBase64");
    						
    						//将试题dsc文件上传至服务器
    						path = filepath + tqi.getFilepath();
    						analyzeHyperlinkFile = new File(path);
    						if(!analyzeHyperlinkFile.exists()){
    							analyzeHyperlinkFile.createNewFile();
    						}
    						fw = new FileWriter(analyzeHyperlinkFile, false);
    						pw = new PrintWriter(fw);
    						pw.println(analyzeHyperlinkData);
    						pw.flush();
    						fw.flush();
    						pw.close();
    						fw.close();
        					
        					tqi.setOptionnum(testCorrectBase64Array.size());
        					int totalScore = 0;
    						try {
    							totalScore = Integer.valueOf(scoreTextArray.getString(0));
							} catch (Exception e) {
								e.printStackTrace();
								totalScore = 0;
							}
        					if(tqi.getOptionnum().intValue() > 1){
								int score1 = totalScore/tqi.getOptionnum().intValue();
								int score2 = totalScore%tqi.getOptionnum().intValue();
								
        						scoreText = new StringBuffer();
        						for(int m=0, n=testCorrectBase64Array.size(); m<n; m++){
        							if("".equals(scoreTextArray.getString(0)) || "分值".equals(scoreTextArray.getString(0))){
        								scoreText.append("【】0");
                					}else {
                						if(m == n-1){
                							scoreText.append("【】").append((score1+score2));
                						}else {
                							scoreText.append("【】").append(score1);
										}
        							}
            					}
        						tqi.setScore(scoreText.substring(2));
        						testCorrectBase64 = new StringBuffer();
        						for(int m=0, n=testCorrectBase64Array.size(); m<n; m++){
        							tqi.setOptionContent(m+1, testCorrectBase64Array.getString(m));
        							testCorrectBase64.append("【】").append(testCorrectBase64Array.getString(m));
            					}
        						tqi.setRightans(testCorrectBase64.substring(2));
        					}else {
        						if("".equals(scoreTextArray.getString(0)) || "分值".equals(scoreTextArray.getString(0))){
            						tqi.setScore("0");
            					}else {
            						tqi.setScore(scoreTextArray.getString(0));
    							}
            					tqi.setRightans(testCorrectBase64Array.getString(0));
							}
        					tqi.setQuestiontype("O");
        					tqi.setDoctype("1");
        					tqi.setIsrightans("1");
        					if("".equals(tkJson.getString("complexityText")) || "难易度".equals(tkJson.getString("complexityText"))){
        						tqi.setDifficult("A");
        					}else {
        						tqi.setDifficult(tkJson.getString("complexityText"));
							}
        					tqi.setCretatdate(DateTime.getDate());
        					tqi.setUpdatetime(tqi.getCretatdate());
        					
        					analyzeHyperlink = tkJson.getString("analyzeHyperlink");
        					if(analyzeHyperlink != null && !"".equals(analyzeHyperlink)){
        						tqi.setDescript(tkJson.getString("analyzeHyperlinkHTML"));
        						tqi.setDescriptpath(savepath + "/jiexi/" + (i+1) + ".dsc");
        						analyzeHyperlinkData = tkJson.getString("analyzeHyperlinkData");
        						
        						//将解析超链接dsc文件上传至服务器
        						path = filepath + tqi.getDescriptpath();
        						analyzeHyperlinkFile = new File(path);
        						if(!analyzeHyperlinkFile.exists()){
        							analyzeHyperlinkFile.createNewFile();
        						}
        						fw = new FileWriter(analyzeHyperlinkFile, false);
        						pw = new PrintWriter(fw);
        						pw.println(analyzeHyperlinkData);
        						pw.flush();
        						fw.flush();
        						pw.close();
        						fw.close();
        					}else {
								tqi.setDescript(tkJson.getString("analyzeText"));
							}
        					
        					tqi.setStatus("1");
        					if("适用地区".equals(tkJson.getString("areaText"))){
        						tqi.setArea("");
        					}else {
        						tqi.setArea(tkJson.getString("areaText"));
							}
        					if("测量目标".equals(tkJson.getString("targetText"))){
        						tqi.setTheyear("");
        					}else {
        						tqi.setTheyear(tkJson.getString("targetText"));
							}
        					tqi.setAuthorname(userInfo.getUsername());
        					tqi.setAuthorid(userInfo.getUserid());
        					//根据题型获取学科的题型
    						tqt = tqtm.getTkQuestionsType("E", tkBookInfo.getSubjectid());
    						if(tqt == null){
    							tqt = new TkQuestionsType();
    							tqt.setTypeno("0000");
    							tqt.setTypename("填空题");
    							tqt.setType("E");
    							tqt.setSubjectid(tkBookInfo.getSubjectid());
    							tqt.setUnitid(tkBookInfo.getUnitid());
    							tqtm.addTkQuestionsType(tqt);
    						}
        					tqi.setTkQuestionsType(tqt);
        					tqi.setSubjectid(tkBookInfo.getSubjectid());
        					tqi.setGradeid(tkBookInfo.getGradeid());
        					tqi.setUnitid(tkBookInfo.getUnitid());
        					tqi.setTag(tkBookInfo.getTitle() + ";" + tkBookContent.getTitle() + ";第" + (i+1) + "题");
        					if(tkJson.getString("contentText") != null && !"".equals(tkJson.getString("contentText")) && !"考查内容".equals(tkJson.getString("contentText"))){
        						tqi.setTag(tqi.getTag() + ";" + tkJson.getString("contentText"));
        					}
        					tqim.addTkQuestionsInfo(tqi);
        					
        					questionno = new StringBuffer();
        					for (int x=tqi.getQuestionid().toString().length(); x < 10; x++) {
        						questionno.append("0");
        					}
        					questionno.append(tqi.getQuestionid());
        					tqi.setQuestionno(questionno.toString());
        					tqim.updateTkQuestionsInfo(tqi);
        					
        					tpc.setPaperid(tkPaperInfo.getPaperid());
        					tpc.setQuestionid(tqi.getQuestionid());
        					tpc.setOrderindex(Integer.valueOf(i+1));
        					tpc.setScore(Float.valueOf(totalScore));
        					tpcm.addTkPaperContent(tpc);
    						
    					}else if("fill".equals(testType) || "answers".equals(testType)){//填空题和解答题
    						scoreTextArray = tkJson.getJSONArray("scoreText");
    						testCorrectBase64Array = tkJson.getJSONArray("testCorrectBase64");
    						tqi = new TkQuestionsInfo();
        					tpc = new TkPaperContent();
        					
        					tqi.setParentid(0);
        					tqi.setTitle(tkBookInfo.getTitle() + "-" + tkBookContent.getTitle() + "-第" + (i+1) + "题");
        					tqi.setTitlecontent(tkJson.getString("docHtml"));
        					tqi.setFilepath(savepath + "/shiti/" + (i+1) + ".dsc");
    						analyzeHyperlinkData = tkJson.getString("docBase64");
    						
    						//将试题dsc文件上传至服务器
    						path = filepath + tqi.getFilepath();
    						analyzeHyperlinkFile = new File(path);
    						if(!analyzeHyperlinkFile.exists()){
    							analyzeHyperlinkFile.createNewFile();
    						}
    						fw = new FileWriter(analyzeHyperlinkFile, false);
    						pw = new PrintWriter(fw);
    						pw.println(analyzeHyperlinkData);
    						pw.flush();
    						fw.flush();
    						pw.close();
    						fw.close();
        					
        					tqi.setOptionnum(testCorrectBase64Array.size());
    						if("".equals(scoreTextArray.getString(0)) || "分值".equals(scoreTextArray.getString(0))){
        						tqi.setScore("0");
        					}else {
        						tqi.setScore(scoreTextArray.getString(0));
							}
        					tqi.setRightans("<img border=\"0px\" src=\"data:image/png;base64," + testCorrectBase64Array.getString(0) + "\" />");
        					tqi.setQuestiontype("S");
        					tqi.setDoctype("1");
        					if("是".equals(tkJson.getString("typeText"))){
        						tqi.setIsrightans("1");
        					}else {
        						tqi.setIsrightans("0");//主观题默认都是没有标准答案的
							}
        					if("".equals(tkJson.getString("complexityText")) || "难易度".equals(tkJson.getString("complexityText"))){
        						tqi.setDifficult("A");
        					}else {
        						tqi.setDifficult(tkJson.getString("complexityText"));
							}
        					tqi.setCretatdate(DateTime.getDate());
        					tqi.setUpdatetime(tqi.getCretatdate());
        					
        					analyzeHyperlink = tkJson.getString("analyzeHyperlink");
        					if(analyzeHyperlink != null && !"".equals(analyzeHyperlink)){
        						tqi.setDescript(tkJson.getString("analyzeHyperlinkHTML"));
        						tqi.setDescriptpath(savepath + "/jiexi/" + (i+1) + ".dsc");
        						analyzeHyperlinkData = tkJson.getString("analyzeHyperlinkData");
        						
        						//将解析超链接dsc文件上传至服务器
        						path = filepath + tqi.getDescriptpath();
        						analyzeHyperlinkFile = new File(path);
        						if(!analyzeHyperlinkFile.exists()){
        							analyzeHyperlinkFile.createNewFile();
        						}
        						fw = new FileWriter(analyzeHyperlinkFile, false);
        						pw = new PrintWriter(fw);
        						pw.println(analyzeHyperlinkData);
        						pw.flush();
        						fw.flush();
        						pw.close();
        						fw.close();
        					}else {
								tqi.setDescript(tkJson.getString("analyzeText"));
							}
        					
        					tqi.setStatus("1");
        					if("适用地区".equals(tkJson.getString("areaText"))){
        						tqi.setArea("");
        					}else {
        						tqi.setArea(tkJson.getString("areaText"));
							}
        					if("测量目标".equals(tkJson.getString("targetText"))){
        						tqi.setTheyear("");
        					}else {
        						tqi.setTheyear(tkJson.getString("targetText"));
							}
        					tqi.setAuthorname(userInfo.getUsername());
        					tqi.setAuthorid(userInfo.getUserid());
        					//根据题型获取学科的题型
        					if("fill".equals(testType)){//填空题
        						tqt = tqtm.getTkQuestionsType("E", tkBookInfo.getSubjectid());
        						if(tqt == null){
        							tqt = new TkQuestionsType();
        							tqt.setTypeno("0000");
        							tqt.setTypename("填空题");
        							tqt.setType("E");
        							tqt.setSubjectid(tkBookInfo.getSubjectid());
        							tqt.setUnitid(tkBookInfo.getUnitid());
        							tqtm.addTkQuestionsType(tqt);
        						}
        					}else {//解答题、主观题
        						tqt = tqtm.getTkQuestionsType("S", tkBookInfo.getSubjectid());
        						if(tqt == null){
        							tqt = new TkQuestionsType();
        							tqt.setTypeno("0000");
        							tqt.setTypename("主观题");
        							tqt.setType("S");
        							tqt.setSubjectid(tkBookInfo.getSubjectid());
        							tqt.setUnitid(tkBookInfo.getUnitid());
        							tqtm.addTkQuestionsType(tqt);
        						}
							}
        					tqi.setTkQuestionsType(tqt);
        					tqi.setSubjectid(tkBookInfo.getSubjectid());
        					tqi.setGradeid(tkBookInfo.getGradeid());
        					tqi.setUnitid(tkBookInfo.getUnitid());
        					tqi.setTag(tkBookInfo.getTitle() + ";" + tkBookContent.getTitle() + ";第" + (i+1) + "题");
        					if(tkJson.getString("contentText") != null && !"".equals(tkJson.getString("contentText")) && !"考查内容".equals(tkJson.getString("contentText"))){
        						tqi.setTag(tqi.getTag() + ";" + tkJson.getString("contentText"));
        					}
        					tqim.addTkQuestionsInfo(tqi);
        					
        					questionno = new StringBuffer();
        					for (int x=tqi.getQuestionid().toString().length(); x < 10; x++) {
        						questionno.append("0");
        					}
        					questionno.append(tqi.getQuestionid());
        					tqi.setQuestionno(questionno.toString());
        					tqim.updateTkQuestionsInfo(tqi);
        					
        					tpc.setPaperid(tkPaperInfo.getPaperid());
        					tpc.setQuestionid(tqi.getQuestionid());
        					tpc.setOrderindex(Integer.valueOf(i+1));
        					tpc.setScore(Float.valueOf(tqi.getScore()));
        					tpcm.addTkPaperContent(tpc);
    						
    					}
    				}
    			}
        		
    			JSONObject resultJson = new JSONObject();
    			resultJson.put("status", true);
    			resultJson.put("errorMsg", "");
    			resultJson.put("errorCode", "");
    			result = resultJson.toString();
    		}else {
    			JSONObject resultJson = new JSONObject();
    			resultJson.put("status", false);
    			resultJson.put("errorMsg", "请选择具体作业目录存放试卷文件！");
    			resultJson.put("errorCode", "00001");
    			result = resultJson.toString();
    			return result;
			}
    		
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}
		
		return result;
	}
	
//	/*
//	 * 客户端上传文件至服务器右侧树列表
//	 * 嵌套循环太多效率太低，导致客户端响应时间到了无法返回数据报错
//	 */
//	private String getResourceForderTree(HttpServletRequest request){
//		String result = null;
//		try {
//    		String userId = Encode.nullToBlank(request.getParameter("userId"));
//    		String token = Encode.nullToBlank(request.getParameter("token"));
//    		String folderId = Encode.nullToBlank(request.getParameter("folderId"));//“1-”开头表示学科，“2-”开头表示年级，“3-”开头表示教材版本，“4-”开头表示作业本，“5-”开头表示作业本内容
//    		String tabindex = Encode.nullToBlank(request.getParameter("tabindex"));
//    		String productType = Encode.nullToBlank(request.getParameter("productType"));
//    		
//    		//判断用户的有效性
//    		SysUserInfo userInfo = getUserInfo(token, userId);
//    		if(userInfo == null){
//    			JSONObject resultJson = new JSONObject();
//    			resultJson.put("status", false);
//    			resultJson.put("errorMsg", "用户已失效，请重新登录！");
//    			resultJson.put("errorCode", "00001");
//    			result = resultJson.toString();
//    			return result;
//    		}
//    		
//			//默认加载学科列表
//			EduSubjectInfoManager manager = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
//			List<SearchModel> condition = new ArrayList<SearchModel>();
//			SearchCondition.addCondition(condition, "status", "=", "1");
//			List list = manager.getEduSubjectInfos(condition, "orderindex asc", 0);
//			if(list != null && list.size() > 0){
//				//年级
//				EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
//    			condition.clear();
//    			SearchCondition.addCondition(condition, "status", "=", "1");
//    			List gradeList = egim.getEduGradeInfos(condition, "orderindex asc", 0);
//    			
//    			//作业本
//    			TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
//    			condition.clear();
//    			SearchCondition.addCondition(condition, "status", "=", "1");
//    			List bookList = tbim.getTkBookInfos(condition, "version asc, part asc", 0);
//    			
//    			//作业本内容
//    			TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
//    			condition.clear();
//    			List bookContentList = tbcm.getTkBookContents(condition, "contentno asc", 0);
//    			
//				JSONObject resultJson = new JSONObject();
//				List<JSONObject> lst = new ArrayList<JSONObject>();
//				JSONObject json = null;
//				List<JSONObject> gradeLst = new ArrayList<JSONObject>();
//				JSONObject gradeJson = null;
//				List<JSONObject> bookLst = new ArrayList<JSONObject>();
//				JSONObject bookJson = null;
//				List<JSONObject> bookContentLst = new ArrayList<JSONObject>();
//				JSONObject bookContentJson = null;
//				
//				EduSubjectInfo esi = null;
//				EduGradeInfo egi = null;
//				TkBookInfo tbi = null;
//				TkBookContent tbc = null;
//				for(int i=0, size=list.size(); i<size; i++){
//					esi = (EduSubjectInfo) list.get(i);
//					json = new JSONObject();
//					json.put("ResourceFolder_Id", "1-" + esi.getSubjectid());
//					json.put("ResourceFolder_ParentId", "0");
//					json.put("ResourceFolder_Name", esi.getSubjectname());
//					json.put("isStore_Files", "0");
//					//年级
//					if(gradeList != null && gradeList.size() > 0){
//						for(int m=0, n=gradeList.size(); m<n; m++){
//    						egi = (EduGradeInfo) gradeList.get(m);
//    						if(egi.getSubjectid().intValue() == esi.getSubjectid().intValue()){
//    							System.out.println(egi.getSubjectid()+">>>>>>>>>>");
//    							gradeJson = new JSONObject();
//        						gradeJson.put("ResourceFolder_Id", "2-" + egi.getGradeid());
//        						gradeJson.put("ResourceFolder_ParentId", "1-" + egi.getSubjectid());
//        						gradeJson.put("ResourceFolder_Name", egi.getGradename());
//        						gradeJson.put("isStore_Files", "0");
//        						//作业本
//        						if(bookList != null && bookList.size() > 0){
//            						for(int x=0, y=bookList.size(); x<y; x++){
//            							tbi = (TkBookInfo) bookList.get(x);
//            							if(tbi.getGradeid().intValue() == egi.getGradeid().intValue()){
//            								bookJson = new JSONObject();
//                							bookJson.put("ResourceFolder_Id", "4-" + tbi.getBookid());
//                							bookJson.put("ResourceFolder_ParentId", "2-" + tbi.getGradeid());
//                							bookJson.put("ResourceFolder_Name", tbi.getTitle());
//                							bookJson.put("isStore_Files", "0");
//                							//作业本内容
//                							if(bookContentList != null && bookContentList.size() > 0){
//                        						for(int b=0, c=bookContentList.size(); b<c; c++){
//                        							tbc = (TkBookContent) bookContentList.get(b);
//                        							if(tbc.getBookid().intValue() == tbi.getBookid().intValue()){
//                        								bookContentJson = new JSONObject();
//                            							bookContentJson.put("ResourceFolder_Id", "5-" + tbc.getBookcontentid());
//                            							bookContentJson.put("ResourceFolder_ParentId", "4-" + tbc.getBookid());
//                            							bookContentJson.put("ResourceFolder_Name", tbc.getTitle());
//                            							bookContentJson.put("isStore_Files", "1");
//                            							bookContentLst.add(bookContentJson);
//                        							}
//                            					}
//                        						bookJson.put("list",  JSONArray.fromObject(bookContentLst));
//                        					}
//                							bookLst.add(bookJson);
//            							}
//                					}
//            						gradeJson.put("list",  JSONArray.fromObject(bookLst));
//            					}
//        						gradeLst.add(gradeJson);
//    						}
//    					}
//        				json.put("list",  JSONArray.fromObject(gradeLst));
//					}
//					lst.add(json);
//				}
//				
//				resultJson.put("list", JSONArray.fromObject(lst));
//				resultJson.put("status", true);
//    			resultJson.put("errorMsg", "");
//    			resultJson.put("errorCode", "");
//    			result = resultJson.toString();
//    			System.out.println(result);
//    			return result;
//			}else {
//				JSONObject resultJson = new JSONObject();
//				resultJson.put("list", "[]");
//				resultJson.put("status", true);
//    			resultJson.put("errorMsg", "");
//    			resultJson.put("errorCode", "");
//    			result = resultJson.toString();
//    			return result;
//				
//			}
//		} catch (Exception e) {
//			result = null;
//			e.printStackTrace();
//		}
//		
//		return result;
//	}
}
