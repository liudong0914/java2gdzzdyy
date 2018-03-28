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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.sf.json.JSONObject;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.DESUtil;
import com.util.file.CopyFile;
import com.util.file.FileUtil;
import com.util.file.zip.AntZipFile;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUnitInfoManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.util.common.CacheUtil;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;

/**
 * APP客户端接口
 * @version 1.0
 */
public class AppServiceAction extends BaseAction {
	
	private static final String KEY = "5g23I5e3";//秘钥，正式环境再替换
	
	private static String homepage = null;
	private static String realpath = null;
	
	static{
		if(homepage == null){
			homepage = "http://www.lmzyb.com";//可从配置文件中获取
		}
	}

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
			String params = request.getParameter("params");

			String paramsJsonStr = DESUtil.decrypt(params, KEY);
			JSONObject json = JSONObject.fromObject(paramsJsonStr);

			if ("1001".equals(module)) {// Client登录
				result = userLogin(json, request);
			}else if ("1002".equals(module)) {// Client获取用户信息
				result = getUserInfo(json, request);
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
			String client = null;//android，ios
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
				CacheUtil.putObject("SESSIONID_" + sessionid, loginname, 12*60*60);
				CacheUtil.putObject("CLIENT_" + sessionid, client, 12*60*60);
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
			
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + userid + ":" + IpUtil.getIpAddr(request) + "】  调用了《9001：自动更新客户端文件》接口";
			addUseLogs(getRealpath(request), calldescript, client);

			JSONObject resultJson = new JSONObject();
			Map<String, String> map = getVersion(getRealpath(request), client);

			resultJson.put("result", "0");
			resultJson.put("sessionid", sessionid);
			resultJson.put("version", map.get("version"));
			resultJson.put("url", homepage + map.get("url"));
			
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
