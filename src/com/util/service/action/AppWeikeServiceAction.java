package com.util.service.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.DESUtil;
import com.util.file.CopyFile;
import com.util.file.FileUtil;
import com.util.file.zip.AntZipFile;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.PublicResourceBundle;
import com.util.string.UUID;
import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUnitInfoManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookContentFilmManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;
import com.wkmk.util.common.TwoCodeUtil;
import com.wkmk.util.oss.OssCopyFile;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmPixManager;

/**
 * APP客户端（PC客户端和移动客户端）录课客户端上传接口
 *【龙门作业宝服务器配置】找一台服务器安装ftp服务器端，指向到临时目录uploadtemp，再通过oss转移到公共目录upload下
 *由于ftp的上传只在某一台服务器上，而上传又涉及到数据的转移，所以接口也只能走固定某一台机器。
 *【注意】客户端通过ftp上传，是否需要流量费
 * @version 1.0
 */
public class AppWeikeServiceAction extends BaseAction {
	
	//第3台服务器专门负责与录课客户端交互
	private static String homepage = null;
	private static final String KEY = "5g23I5e3";//秘钥，正式环境再替换
	
	static{
		if(homepage == null){
			homepage = "http://" + PublicResourceBundle.getProperty("ftp", "ftp.server.ip");
		}
	}
	
	private static String realpath = null;

	private String getRealpath(HttpServletRequest request) {
		if (realpath == null) {
			realpath = request.getSession().getServletContext().getRealPath("/");
		}
		return realpath;
	}

	
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			String module = request.getParameter("module");
			// 第二个参数params为json字符串{"userid":"admin","password":"123"}加密后的字符
			String params = request.getParameter("params");
			// 因为url地址通过urlencode转码后放到url地址栏中访问，浏览器会自动处理，所以无需通过程序进行urldecoder处理
			// String paramsJsonStr = DESUtil.decrypt(URLUtil.getURLDecoder(params));

			String paramsJsonStr = DESUtil.decrypt(params, KEY);
			JSONObject json = JSONObject.fromObject(paramsJsonStr);

			if ("1001".equals(module)) {// Client登录
				result = userLogin(json, request);
			}else if ("1002".equals(module)) {// Client获取用户信息
				result = getUserInfo(json, request);
			}else if ("1003".equals(module)) {//获取FTP账号
				result=getFtpAccount(json, request);
			}else if ("2001".equals(module)) {// 获取作业本列表
				result=getBookList(json, request);
			}else if ("2002".equals(module)) {// 获取作业本内容列表
				result=getBookContentList(json, request);
			}else if ("2003".equals(module)) {// 获取微课类型
				result=getFilmType(json, request);
			}else if ("3001".equals(module)) {// Client上传微课视频
				result=uploadVideoFilm(json, request);
			}else if ("9001".equals(module)) {// Client自动更新客户端文件
				result = getVersionFile(json, request);
			}else if ("9002".equals(module)) {// Client上传错误日志
				result = uploadLogs(json, request);
			}else if ("9003".equals(module)) {// Client下载错误日志
				result = downloadLogs(json, request);
			}
			if (result != null && !"".equals(result)) {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(result);
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			//统一错误编码00001000验证失败
			JSONObject resultJson = new JSONObject();
			resultJson.put("result", "-1");
			resultJson.put("errorcode", "00000000");
			resultJson.put("error", "请求接口出错");
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(resultStr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/*
	 * 1001 用户登录
	 */
	private String userLogin(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String loginname = null;
			String password = null;
			String client = null;//android，ios，pc
			try {
				loginname = json.getString("loginname");
			} catch (Exception e) {
				loginname = null;
			}
			try {
				password = json.getString("password");
			} catch (Exception e) {
				password = null;
			}
			try {
				client = json.getString("client");
			} catch (Exception e) {
				client = null;
			}
			if(!"android".equals(client) && !"ios".equals(client)){
				client = "pc";
			}

			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + loginname + ":" + IpUtil.getIpAddr(request) + "】  调用了《1001：用户登录验证》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			SysUserInfo sui = manager.getUserInfoByLoginName(loginname);
			if (sui != null) {
				if ("1".equals(sui.getStatus())) {
					if (password != null && password.equals(sui.getPassword())) {
						result = "0";// 验证成功
					}
				} else {
					result = "-1";// 账号被禁用
				}
			}

			JSONObject resultJson = new JSONObject();
			if ("0".equals(result)) {// 验证成功
				String sessionid = request.getSession().getId().toString();
				resultJson.put("result", "0");
				resultJson.put("sessionid", sessionid);

				// 将键值：sessionid:userid放入缓存中
				CacheUtil.put("SESSIONID_" + sessionid, loginname);
				CacheUtil.put("CLIENT_" + sessionid, client);
			} else {// 验证失败
				resultJson.put("result", "-1");
				resultJson.put("sessionid", "0");
				if ("-1".equals(result)) {
					resultJson.put("errorcode", "10012000");
					resultJson.put("error", "账户被禁用");
				} else {
					resultJson.put("errorcode", "10011000");
					resultJson.put("error", "不存在该用户");
				}
			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 1002 获取用户信息
	 */
	private String getUserInfo(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String sessionid = null;
			try {
				sessionid = json.getString("sessionid");
			} catch (Exception e) {
				sessionid = null;
			}

			String userid = CacheUtil.get("SESSIONID_" + sessionid);
			String client = CacheUtil.get("CLIENT_" + sessionid);
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《1002：获取用户个人信息》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			JSONObject resultJson = new JSONObject();
			if (userid != null && !"".equals(userid)) {// 验证成功
				SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUnitInfoManager suim = (SysUnitInfoManager) getBean("sysUnitInfoManager");
				SysUserInfo sui = manager.getUserInfoByLoginName(userid);
				SysUnitInfo sysUnitInfo = suim.getSysUnitInfo(sui.getUnitid());

				resultJson.put("result", "0");
				resultJson.put("sessionid", sessionid);
				resultJson.put("userid", sui.getUserid().toString());
				resultJson.put("loginname", sui.getLoginname());
				resultJson.put("username", sui.getUsername());
				resultJson.put("photo", homepage + "/upload/" + sui.getPhoto());
				resultJson.put("sex", Constants.getCodeTypevalue("sex", sui.getSex()));
				resultJson.put("email", sui.getEmail());
				resultJson.put("mobile", sui.getMobile());
				resultJson.put("unitid", sui.getUnitid().toString());
				resultJson.put("unitname", sysUnitInfo.getUnitname());
			} else {// 验证失败
				resultJson.put("result", "-1");
				resultJson.put("sessionid", sessionid);
				resultJson.put("errorcode", "00001000");
				resultJson.put("error", "验证失败，请重新登录");
			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 1003 获取ftp账号
	 */
	private String getFtpAccount(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String sessionid = null;
			try {
				sessionid = json.getString("sessionid");
			} catch (Exception e) {
				sessionid = null;
			}

			String userid = CacheUtil.get("SESSIONID_" + sessionid);
			String client = CacheUtil.get("CLIENT_" + sessionid);
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《1003：获取FTP账号》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			JSONObject resultJson = new JSONObject();
			if (userid != null && !"".equals(userid)) {// 验证成功
				resultJson.put("result", "0");
				resultJson.put("sessionid", sessionid);
				resultJson.put("ftphost", PublicResourceBundle.getProperty("ftp", "ftp.server.ip"));
				resultJson.put("ftpport", PublicResourceBundle.getProperty("ftp", "ftp.server.port"));
				resultJson.put("ftpusername", PublicResourceBundle.getProperty("ftp", "ftp.server.username"));
				resultJson.put("ftppassword", PublicResourceBundle.getProperty("ftp", "ftp.server.password"));
			} else {// 验证失败
				resultJson.put("result", "-1");
				resultJson.put("sessionid", sessionid);
				resultJson.put("errorcode", "00001000");
				resultJson.put("error", "验证失败，请重新登录");
			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 2001 获取作业本列表
	 */
	private String getBookList(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String sessionid = null;
			String title = null;
			try {
				sessionid = json.getString("sessionid");
			} catch (Exception e) {
				sessionid = null;
			}
			try {
				title = json.getString("title");
			} catch (Exception e) {
				title = null;
			}

			String userid = CacheUtil.get("SESSIONID_" + sessionid);
			String client = CacheUtil.get("CLIENT_" + sessionid);
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《2001：获取作业本列表》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			JSONObject resultJson = new JSONObject();
			if (userid != null && !"".equals(userid)) {// 验证成功
				TkBookInfoManager manager = (TkBookInfoManager) getBean("tkBookInfoManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "status", "=", "1");
				//临时改：只要数、理、化下册书本，方便选择
				//SearchCondition.addCondition(condition, "part", "<>", "1' and (a.subjectid=12 or a.subjectid=14 or a.subjectid=15) and 1='1");//下册
				if(title != null && !"".equals(title)){
					SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
				}
				List list = manager.getTkBookInfos(condition, "title asc", 0);
				
				List<JSONObject> lst = new ArrayList<JSONObject>();
				JSONObject jb = null;
				if(list != null && list.size() > 0){
					TkBookInfo tbi = null;
					for(int i=0, size=list.size(); i<size; i++){
						tbi = (TkBookInfo) list.get(i);
						jb = new JSONObject();
						jb.put("bookid", tbi.getBookid().toString());
						jb.put("title", tbi.getTitle() + "（" + Constants.getCodeTypevalue("version", tbi.getVersion()) + "）");
						
						lst.add(jb);
					}
					resultJson.put("result", "0");
					resultJson.put("sessionid", sessionid);
					resultJson.put("datalist", JSONArray.fromObject(lst));
				}else {
					resultJson.put("result", "0");
					resultJson.put("sessionid", sessionid);
					resultJson.put("datalist", JSONArray.fromObject(lst));
				}
			} else {// 验证失败
				resultJson.put("result", "-1");
				resultJson.put("sessionid", sessionid);
				resultJson.put("errorcode", "00001000");
				resultJson.put("error", "验证失败，请重新登录");
			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 2002 获取作业本内容列表
	 */
	private String getBookContentList(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String sessionid = null;
			String bookid = null;
			String title = null;
			try {
				sessionid = json.getString("sessionid");
			} catch (Exception e) {
				sessionid = null;
			}
			try {
				bookid = json.getString("bookid");
			} catch (Exception e) {
				bookid = null;
			}
			try {
				title = json.getString("title");
			} catch (Exception e) {
				title = null;
			}

			String userid = CacheUtil.get("SESSIONID_" + sessionid);
			String client = CacheUtil.get("CLIENT_" + sessionid);
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《2002：获取作业本内容列表》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			JSONObject resultJson = new JSONObject();
			if (userid != null && !"".equals(userid)) {// 验证成功
				TkBookContentManager manager = (TkBookContentManager) getBean("tkBookContentManager");
				List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
				if(title != null && !"".equals(title)){
					SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
				}
				List list = manager.getTkBookContents(condition, "contentno asc", 0);
				
				List<JSONObject> lst = new ArrayList<JSONObject>();
				JSONObject jb = null;
				if(list != null && list.size() > 0){
					TkBookContent tbc = null;
					for(int i=0, size=list.size(); i<size; i++){
						tbc = (TkBookContent) list.get(i);
						jb = new JSONObject();
						jb.put("bookcontentid", tbc.getBookcontentid().toString());
						jb.put("title", tbc.getTitle());
						jb.put("contentno", tbc.getContentno());
						jb.put("parentno", tbc.getParentno());
						
						lst.add(jb);
					}
					resultJson.put("result", "0");
					resultJson.put("sessionid", sessionid);
					resultJson.put("datalist", JSONArray.fromObject(lst));
				}else {
					resultJson.put("result", "0");
					resultJson.put("sessionid", sessionid);
					resultJson.put("datalist", JSONArray.fromObject(lst));
				}
			} else {// 验证失败
				resultJson.put("result", "-1");
				resultJson.put("sessionid", sessionid);
				resultJson.put("errorcode", "00001000");
				resultJson.put("error", "验证失败，请重新登录");
			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 2003 获取微课类型
	 * 如：解题微课，举一反三微课
	 */
	private String getFilmType(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String sessionid = null;
			try {
				sessionid = json.getString("sessionid");
			} catch (Exception e) {
				sessionid = null;
			}

			String userid = CacheUtil.get("SESSIONID_" + sessionid);
			String client = CacheUtil.get("CLIENT_" + sessionid);
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《2003：获取微课类型》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			JSONObject resultJson = new JSONObject();
			if (userid != null && !"".equals(userid)) {// 验证成功
				List<JSONObject> lst = new ArrayList<JSONObject>();
				JSONObject jb = null;
				jb = new JSONObject();
				jb.put("typeid", "1");
				jb.put("typename", "解题微课");
				lst.add(jb);
				
				jb = new JSONObject();
				jb.put("typeid", "2");
				jb.put("typename", "举一反三微课");
				lst.add(jb);
				
				resultJson.put("result", "0");
				resultJson.put("sessionid", sessionid);
				resultJson.put("datalist", JSONArray.fromObject(lst));
			} else {// 验证失败
				resultJson.put("result", "-1");
				resultJson.put("sessionid", sessionid);
				resultJson.put("errorcode", "00001000");
				resultJson.put("error", "验证失败，请重新登录");
			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 3001 上传微课视频文件【先只考虑微课和视频一对一】
	 */
	private String uploadVideoFilm(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String sessionid = null;
			String coursename = null;//微课名称，客户端填写
			String orderindex = null;
			String coursefilepath = null;//课程文件路径，相对ftp授权根路径
			String coursesketchpath = null;//课程缩略图文件路径，相对ftp授权根路径
			String bookcontentid = null;
			String typeid = null;
			Integer sort = 1;//默认排序
			
			try {
				sessionid = json.getString("sessionid");
			} catch (Exception e) {
				sessionid = null;
			}
			try {
				coursename = json.getString("coursename");
			} catch (Exception e) {
				coursename = null;
			}
			try {
				orderindex = json.getString("orderindex");
				sort = Integer.valueOf(orderindex);
			} catch (Exception e) {
				orderindex = null;
				sort = 1;
			}
			try {
				coursefilepath = json.getString("coursefilepath");
			} catch (Exception e) {
				coursefilepath = null;
			}
			try {
				coursesketchpath = json.getString("coursesketchpath");
			} catch (Exception e) {
				coursesketchpath = null;
			}
			try {
				bookcontentid = json.getString("bookcontentid");
			} catch (Exception e) {
				bookcontentid = null;
			}
			try {
				typeid = json.getString("typeid");
			} catch (Exception e) {
				typeid = null;
			}
			if(typeid == null){
				typeid = "1";//默认解题微课
			}
			
			String userid = CacheUtil.get("SESSIONID_" + sessionid);
			String client = CacheUtil.get("CLIENT_" + sessionid);
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《3001：上传微课信息》接口";
			addUseLogs(getRealpath(request), calldescript, client);
			
			JSONObject resultJson = new JSONObject();
			if (userid != null && !"".equals(userid)) {
				String ftppath = getRealpath(request) + "/uploadtemp/";
				String rootpath = getRealpath(request) + "/upload/";
				File coursefile = new File(ftppath + coursefilepath);
				//File coursesketch = new File(ftppath + coursesketchpath);
				if(coursefile.exists()){
					SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
					SysUserInfo sysUserInfo = suim.getUserInfoByLoginName(userid);
					
					//【龙门作业宝服务器配置】找一台服务器安装ftp服务器端，指向到临时目录uploadtemp，再通过oss转移到公共目录upload下
					//把ftp上传文件，按照系统存放规则转移【龙门的oss转移需要特殊处理】
					String savepath = sysUserInfo.getUnitid() + "/" + DateTime.getDate("yyyy") + "/" + DateTime.getDate("MM") + "/" + DateTime.getDate("dd");
					String vodfileext = FileUtil.getFileExt(coursefilepath);
					String vodfilename = UUID.getNewUUID() + "." + vodfileext;
					String jpgfileext = FileUtil.getFileExt(coursesketchpath);
					String jpgfilename = UUID.getNewUUID() + "." + jpgfileext;
					// 判断是否启用oss云存储，启用则用oss对应的sdk上传文件
					String openOss = PublicResourceBundle.getProperty("system", "open.oss");
					if ("1".equals(openOss)) {
						OssCopyFile.copyDiskToOss(ftppath + coursefilepath, savepath + "/" + vodfilename);
						OssCopyFile.copyDiskToOss(ftppath + coursesketchpath, savepath + "/" + jpgfilename);
					}else {
						CopyFile.copy(ftppath + coursefilepath, rootpath + savepath + "/" + vodfilename);
						CopyFile.copy(ftppath + coursesketchpath, rootpath + savepath + "/" + jpgfilename);
					}
					
					TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
					TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
					EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
					TkBookContent tkBookContent = tbcm.getTkBookContent(bookcontentid);
					TkBookInfo tkBookInfo = tbim.getTkBookInfo(tkBookContent.getBookid());
					EduGradeInfo eduGradeInfo = egim.getEduGradeInfo(tkBookInfo.getGradeid());
					
					VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
					VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
					TkBookContentFilmManager manager = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
					
					VwhFilmInfo model = new VwhFilmInfo();
					model.setTitle(coursename);
					model.setKeywords(model.getTitle());
					model.setDescript(model.getTitle());
					model.setActor(sysUserInfo.getUsername());
					model.setSketch(savepath + "/" + jpgfilename);
					model.setSketchimg(model.getSketch());
					model.setHits(0);
					model.setStatus("1");//已审核,微课无需审核，只审核微课与作业本内容关联属性
					model.setOrderindex(1);
					model.setCreatedate(DateTime.getDate());
					model.setUpdatetime(model.getCreatedate());
					model.setEduGradeInfo(eduGradeInfo);
					model.setSysUserInfo(sysUserInfo);
					model.setComputerid(Constants.DEFAULT_COMPUTERID);//默认用户上传不能选择服务器，只有管理员管理视频时可以改
					model.setUnitid(sysUserInfo.getUnitid());
					vfim.addVwhFilmInfo(model);
					model.setTwocodepath(TwoCodeUtil.getTwoCodePath(model, request));
					vfim.updateVwhFilmInfo(model);
					
					VwhFilmPix pix = new VwhFilmPix();
		    		pix.setName(coursename);
		    		pix.setSrcpath(savepath + "/" + vodfilename);
	    			pix.setFlvpath(savepath + "/" + vodfilename);
		    		pix.setImgpath(savepath + "/" + jpgfilename);
		    		pix.setConvertstatus("1");
		    		pix.setNotifystatus("1");
		    		pix.setImgwidth(0);
		    		pix.setImgheight(0);
		    		pix.setSecond("15");
		    		pix.setFilesize(coursefile.length());
		    		pix.setFileext(vodfileext);
		    		pix.setTimelength("0");
		    		pix.setResolution("0");
		    		pix.setOrderindex(1);
		    		pix.setCreatedate(DateTime.getDate());
		    		pix.setFilmid(model.getFilmid());
		    		pix.setUnitid(sysUserInfo.getUnitid());
		    		pix.setUpdateflag("0");
		    		pix.setUpdatetime(DateTime.getDate());
		    		pix.setMd5code("0");
		    		vfpm.addVwhFilmPix(pix);
					
		    		TkBookContentFilm tkFilm = new TkBookContentFilm();
		    		tkFilm.setTitle(coursename);
		    		tkFilm.setOrderindex(sort);
		    		tkFilm.setType(typeid);
		    		tkFilm.setStatus("0");
		    		tkFilm.setBookcontentid(Integer.valueOf(bookcontentid));
		    		tkFilm.setBookid(tkBookInfo.getBookid());
		    		tkFilm.setFilmid(model.getFilmid());
		    		tkFilm.setSysUserInfo(sysUserInfo);
		    		tkFilm.setCreatedate(DateTime.getDate());
		    		tkFilm.setHits(0);
		    		manager.addTkBookContentFilm(tkFilm);
		    		tkFilm.setFilmtwocode(TwoCodeUtil.getTwoCodePath(tkFilm, request));
		    		manager.updateTkBookContentFilm(tkFilm);
					
					//删除临时文件夹转码临时文件
					File file = new File(ftppath + coursefilepath);
					if(file.exists() && file.isFile()){
						file.delete();
					}
					file = new File(ftppath + coursesketchpath);
					if(file.exists() && file.isFile()){
						file.delete();
					}
					
					resultJson.put("result", "0");
					resultJson.put("sessionid", sessionid);
				}else {
					resultJson.put("result", "-1");
					resultJson.put("sessionid", sessionid);
					resultJson.put("errorcode", "30011000");
					resultJson.put("error", "微课视频文件不存在");
				}
			}else{
				resultJson.put("result", "-1");
				resultJson.put("sessionid", sessionid);
				resultJson.put("errorcode", "00001000");
				resultJson.put("error", "验证失败，请重新登录");
			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result=null;
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * 9001 获取客户端微课工具
	 */
	private String getVersionFile(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String sessionid = null;
			try {
				sessionid = json.getString("sessionid");
			} catch (Exception e) {
				sessionid = null;
			}

			String userid = CacheUtil.get("SESSIONID_" + sessionid);
			String client = CacheUtil.get("CLIENT_" + sessionid);
			if(client == null || "".equals(client)){
				client = "pc";
			}
			
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《9001：自动更新客户端文件》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			JSONObject resultJson = new JSONObject();
			//自动更新去掉登录验证
//			if (userid != null && !"".equals(userid)) {// 验证成功
				Map<String, String> map = getVersion(getRealpath(request), client);

				resultJson.put("result", "0");
				resultJson.put("sessionid", sessionid);
				resultJson.put("version", map.get("version"));
				resultJson.put("url", homepage + map.get("url"));
//			} else {// 验证失败
//				resultJson.put("result", "-1");
//				resultJson.put("sessionid", sessionid);
//				resultJson.put("errorcode", "00001000");
//				resultJson.put("error", "验证失败，请重新登录");
//			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 9002 上传错误日志
	 */
	private String uploadLogs(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String sessionid = null;
			String exceptioninfo = null;
			try {
				sessionid = json.getString("sessionid");
			} catch (Exception e) {
				sessionid = null;
			}
			try {
				exceptioninfo = json.getString("exceptioninfo");
			} catch (Exception e) {
				exceptioninfo = null;
			}

			String userid = CacheUtil.get("SESSIONID_" + sessionid);
			String client = CacheUtil.get("CLIENT_" + sessionid);
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《9002：上传错误日志》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			JSONObject resultJson = new JSONObject();
			if (userid != null && !"".equals(userid)) {// 验证成功
				SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
				SysUserInfo sysUserInfo = manager.getUserInfoByLoginName(userid);
				String descript = DateTime.getDate() + " 【" + sysUserInfo.getUsername() + ":" + sysUserInfo.getLoginname() + "】  " + exceptioninfo;
				addLogs(getRealpath(request), descript, client);

				resultJson.put("result", "0");
				resultJson.put("sessionid", sessionid);
			} else {// 验证失败
				resultJson.put("result", "-1");
				resultJson.put("sessionid", sessionid);
				resultJson.put("errorcode", "00001000");
				resultJson.put("error", "验证失败，请重新登录");
			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 9003 下载错误日志
	 */
	private String downloadLogs(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			String sessionid = null;
			String date = null;
			try {
				sessionid = json.getString("sessionid");
			} catch (Exception e) {
				sessionid = null;
			}
			try {
				date = json.getString("date");
			} catch (Exception e) {
				date = null;
			}

			String userid = CacheUtil.get("SESSIONID_" + sessionid);
			String client = CacheUtil.get("CLIENT_" + sessionid);
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《9003：下载错误日志》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			JSONObject resultJson = new JSONObject();
			if (userid != null && !"".equals(userid)) {// 验证成功
				String zippath = "/app/" + client + "/logs/zip";
				String zipname = DateTime.getDate("yyyyMMddHHmmssS") + ".zip";
				if (date == null || "".equals(date) || "0".equals(date)) {
					String zipFilePath = "/app/" + client + "/logs/client";
					AntZipFile antZipFile = new AntZipFile();
					antZipFile.doZip(getRealpath(request) + zipFilePath, getRealpath(request) + zippath, zipname);
				} else {
					// 循环所有文件，然后拷贝需要的文件到制定目录
					String datetime = DateTime.getDate("yyyyMMddHHmmssS");
					String zipFilePath0 = "/app/" + client + "/logs/zip/" + datetime;
					String zipFilePath = "/app/" + client + "/logs/zip/" + datetime + "/client";
					String logpath = getRealpath(request) + "/app/" + client + "/logs/client";
					File[] file = (new File(logpath)).listFiles();
					String filename = null;
					for (int i = 0; i < file.length; i++) {
						if (file[i].isFile()) {
							filename = file[i].getName();
							filename = filename.substring(0, filename.length() - 4);
							if (getCompareTo(date, filename) <= 0) {
								CopyFile.copy(file[i].getPath(), getRealpath(request) + zipFilePath + "/" + file[i].getName());
							}
						}
					}

					// 检查文件夹是否存在,如果不存在,新建一个
					if (!FileUtil.isExistDir(getRealpath(request) + zipFilePath)) {
						FileUtil.creatDir(getRealpath(request) + zipFilePath);
					}

					AntZipFile antZipFile = new AntZipFile();
					antZipFile.doZip(getRealpath(request) + zipFilePath, getRealpath(request) + zippath, zipname);
					// 删除被压缩的文件
					FileUtil.deleteFile(getRealpath(request) + zipFilePath0);
				}

				resultJson.put("result", "0");
				resultJson.put("sessionid", sessionid);
				resultJson.put("logurl", homepage + zippath + "/" + zipname);
			} else {// 验证失败
				resultJson.put("result", "-1");
				resultJson.put("sessionid", sessionid);
				resultJson.put("errorcode", "00001000");
				resultJson.put("error", "验证失败，请重新登录");
			}
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 两个日期比较
	 */
	private int getCompareTo(String date1, String date2) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date _date1 = df.parse(date1);
			Date _date2 = df.parse(date2);

			if (_date1.compareTo(_date2) < 0) {
				return -1;
			} else if (_date1.compareTo(_date2) == 0) {
				return 0;
			} else {
				return 1;
			}
		} catch (Exception e) {
			return -2;
		}
	}

	/*
	 * txt文档内容如下： Client-Version:v1.0 Client-file:/app/pc/v1.0/ppwk_v1.0.zip
	 */
	private Map<String, String> getVersion(String realpath, String client) {
		String xmlpath = "/app/" + client + "/version.txt";// 解析此文件获取相关信息
		Map<String, String> map = new HashMap<String, String>();
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(realpath + xmlpath)));
			String version = input.readLine().substring(15);
			String url = input.readLine().substring(12);
			map.put("version", version);
			map.put("url", url);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
			map.put("version", "1.0.0.0");
			map.put("url", "");
		}
		return map;
	}

	/*
	 * 客户端软件在使用过程中产生的错误日志上传记录
	 */
	private void addLogs(String realpath, String descript, String client) {
		String path = "/app/" + client + "/logs/client/";
		String logpath = path + DateTime.getDateYMD() + ".log";
		String filepath = realpath + logpath;

		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			if (!FileUtil.isExistDir(realpath + path)) {
				FileUtil.creatDir(realpath + path);
			}
			
			File logFile = new File(filepath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}

			fw = new FileWriter(logFile, true);// 追加字符到文件后面
			pw = new PrintWriter(fw);
			pw.println(descript);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * 客户端软件在每次调用接口时记录日志
	 */
	private void addUseLogs(String realpath, String descript, String client) {
		String path = "/app/" + client + "/logs/used/";
		String logpath = path + DateTime.getDateYMD() + ".log";
		String filepath = realpath + logpath;
		FileWriter fw = null;
		PrintWriter pw = null;
		try {
			if (!FileUtil.isExistDir(realpath + path)) {
				FileUtil.creatDir(realpath + path);
			}
			File logFile = new File(filepath);
			if (!logFile.exists()) {
				logFile.createNewFile();
			}

			fw = new FileWriter(logFile, true);// 追加字符到文件后面
			pw = new PrintWriter(fw);
			pw.println(descript);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
