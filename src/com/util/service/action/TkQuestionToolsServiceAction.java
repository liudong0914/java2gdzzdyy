package com.util.service.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.ServletRequest;
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
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.bo.TkPaperContent;
import com.wkmk.tk.bo.TkPaperInfo;
import com.wkmk.tk.bo.TkQuestionsInfo;
import com.wkmk.tk.bo.TkQuestionsInfoFile;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkPaperContentManager;
import com.wkmk.tk.service.TkPaperInfoManager;
import com.wkmk.tk.service.TkQuestionsInfoFileManager;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsTypeManager;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.IpUtil;
import com.wkmk.util.common.TwoCodeUtil;

/**
 * 题库工具【客户端碎片入库】
 * @version 1.0
 */
public class TkQuestionToolsServiceAction extends BaseAction {
	
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
			JSONObject json = null;
			if(params != null && !"".equals(params)){
				String paramsJsonStr = DESUtil.decrypt(params, KEY);
				json = JSONObject.fromObject(paramsJsonStr);
			}

			//不需要登录，因为碎题客户端可能在服务器一直运行着监听数据上传，有效期是长久的
			if ("1003".equals(module)) {//获取FTP账号
				result=getFtpAccount(json, request);
			}else if ("3001".equals(module)) {// Client上传碎题数据
				result=uploadTkQuestions(json, request);
			}else if ("9001".equals(module)) {// Client自动更新客户端文件
				result = getVersionFile(json, request);
			}else if ("9002".equals(module)) {// Client上传错误日志
				result = uploadLogs(json, request);
			}else if ("9003".equals(module)) {// Client下载错误日志
				result = downloadLogs(json, request);
			}
			if (result != null && !"".equals(result)) {
//				response.setCharacterEncoding("UTF-8");
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
//			((HttpServletRequest) response).setCharacterEncoding("UTF-8");
			response.getWriter().write(resultStr);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/*
	 * 1003 获取ftp账号
	 */
	private String getFtpAccount(JSONObject json, HttpServletRequest request) {
		String result = null;
		try {
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + IpUtil.getIpAddr(request) + "】  调用了《1003：获取FTP账号》接口";
			addUseLogs(getRealpath(request), calldescript);

			JSONObject resultJson = new JSONObject();
			resultJson.put("result", "0");
			resultJson.put("ftphost", PublicResourceBundle.getProperty("ftp", "pctools.ftp.server.ip"));
			resultJson.put("ftpport", PublicResourceBundle.getProperty("ftp", "pctools.ftp.server.port"));
			resultJson.put("ftpusername", PublicResourceBundle.getProperty("ftp", "pctools.ftp.server.username"));
			resultJson.put("ftppassword", PublicResourceBundle.getProperty("ftp", "pctools.ftp.server.password"));
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			return resultStr;
		} catch (Exception e) {
			result = null;
			e.printStackTrace();
		}

		return result;
	}
	
	/*
	 * 3001 上传碎题数据
	 * 可能是多题一起提交，也可能是单题提交，格式一样
	 * 单题提交，就是题的json数据，如：{}，直接入题库
	 * 多题提交，就是单题json数组，用json封装，如：{"filename":"作业本名称,作业名称","filepath":"当前碎题的文件路径，服务器保留备用","zipfilepath":"整个zip文件路径","datalist":[{},{},{}]}，自动组卷关联
	 */
	private String uploadTkQuestions(JSONObject json, HttpServletRequest request) {
		TkPaperInfoManager paperInfoManager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
		TkBookInfoManager bookInfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		TkBookContentManager bookContentManager = (TkBookContentManager) getBean("tkBookContentManager");
		String result = null;
		try {
			String calldescript = DateTime.getDate() + " 【" + IpUtil.getIpAddr(request) + "】  调用了《3001：上传微课信息》接口";
			addUseLogs(getRealpath(request), calldescript);
			
			String jsonString = json.toString();
			String filepath = json.getString("filepath");//当前碎题的文件路径
			String zipfilepath = json.getString("zipfilepath");//整个zip文件路径
			String filename = json.getString("filename");
			 String newUUID = UUID.getNewUUID();
			    //解压zip文件
			  String destDir = getRealpath(request)+"/upload/unzip/"+DateTime.getDateYMD()+"/"+newUUID+"/";  
			  EctractZip.unZip(PublicResourceBundle.getProperty("ftp", "pctools.ftp.server.fileupload")+zipfilepath, destDir);  
			 System.out.println("********解压zip文件执行成功，解压路径为：**********"+destDir);

			TkPaperInfo paperInfo = new TkPaperInfo();
			if(jsonString.indexOf("datalist") != -1){
				//1.作业本名称，作业名称
				String[] strArray = null;   
				String[] strArray2 = null; 
		        strArray = filename.split(",");
		        String bookTitle = strArray[0];//作业本名称
		        strArray2 = bookTitle.split("_");
		        bookTitle = strArray2[0];//作业本名称
		        String version = strArray2[1];//版本
		        version = Constants.getCodeTypeId("version", version);
		        String bookContentTitle = strArray[1];//作业名称
				
		        //4.根据作业本名称获取学科年纪
		        List<SearchModel> condition = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition, "title", "like", "%" + bookTitle + "%");
				SearchCondition.addCondition(condition, "version", "=", version);
		        List tkBookInfos = bookInfoManager.getTkBookInfos(condition, "a.bookno", 0);
		        TkBookInfo bookInfo = new TkBookInfo();
		        Integer gradeid =0;
		        Integer subjectid =0;
		        if(tkBookInfos !=null && tkBookInfos.size()>0){
		        	bookInfo = (TkBookInfo) tkBookInfos.get(0);
		        	gradeid = bookInfo.getGradeid();//年纪
		        	subjectid = bookInfo.getSubjectid();//学科
		        }
		        
				//7.创建试卷对象
				//保存试卷信息
				paperInfo.setTitle(bookContentTitle);//用作业标题作为试卷标题
				paperInfo.setPapertype("1");
				paperInfo.setCreatedate(DateTime.getDate());
				paperInfo.setDescript("");
				paperInfo.setStatus("1");
				paperInfo.setUserid(2);
				paperInfo.setGradeid(gradeid);
				paperInfo.setSubjectid(subjectid);
				paperInfo.setUnitid(1);
				paperInfo.setFilepath(filepath);
				
				paperInfoManager.addTkPaperInfo(paperInfo);
				
				//9.根据作业名称获取作业信息，将试卷和作业关联
				List<SearchModel> condition1 = new ArrayList<SearchModel>();
				SearchCondition.addCondition(condition1, "title", "like", "%" + bookContentTitle + "%");
				SearchCondition.addCondition(condition1, "bookid", "=", bookInfo.getBookid());
			    List tkBookContents = bookContentManager.getTkBookContents(condition1, "a.contentno", 0);
			    if(tkBookContents !=null && tkBookContents.size()>0){
			    	TkBookContent  bookContent = (TkBookContent) tkBookContents.get(0);
			    	bookContent.setPaperid(paperInfo.getPaperid());
			    	bookContentManager.updateTkBookContent(bookContent);
			    }
				JSONArray jsonArray = json.getJSONArray("datalist");//整个试卷所有的试题集合[{},{},{}]
				if(jsonArray != null && jsonArray.size() > 0){
					JSONObject jsonObject = null;
					for(int i=0, size=jsonArray.size(); i<size; i++){
						jsonObject = jsonArray.getJSONObject(i);//单独的一个试题{}
						addTkQuestions(filename,filepath,zipfilepath,jsonObject, request,paperInfo,newUUID,destDir);
					}
				}
			}else {
				//单题提交
				addTkQuestions(null,filepath,zipfilepath, json, request,paperInfo,newUUID,destDir);
			}
			
			JSONObject resultJson = new JSONObject();
			resultJson.put("result", "0");
			String resultStr = DESUtil.encrypt(resultJson.toString(), KEY);
			//删除zip包
			File folder = new File(PublicResourceBundle.getProperty("ftp", "pctools.ftp.server.fileupload"));
			File[] files = folder.listFiles();
			for(File file:files){
				String name = file.getName();
				if(file.getName().equals(filename+".zip")){
					file.delete();
				}
			}
			return resultStr;
		} catch (Exception e) {
			result=null;
			e.printStackTrace();
		}
		return result;
	}
	
	/*
	 * 上传碎题数据
	 * 可能是多题一起提交，也可能是单题提交，格式一样
	 * 单题提交，就是题的json数据，如：{}，直接入题库
	 * 多题提交，就是单题json数组，用json封装，如：{"filename":"作业本名称,作业名称","filepath":"当前碎题的文件路径，服务器保留备用","zipfilepath":"整个zip文件路径","datalist":[{},{},{}]}，自动组卷关联
	 */
	private String addTkQuestions(String filename, String filepath, String zipfilepath,JSONObject json, HttpServletRequest request,TkPaperInfo paperInfo,String newUUID,String destDir) {
		String result = null;
		try {
			TkQuestionsInfoManager manager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
			TkQuestionsInfoFileManager questionsInfoFilemanager = (TkQuestionsInfoFileManager) getBean("tkQuestionsInfoFileManager");
			TkPaperInfoManager paperInfoManager = (TkPaperInfoManager) getBean("tkPaperInfoManager");
			TkPaperContentManager paperContentManager = (TkPaperContentManager) getBean("tkPaperContentManager");
			TkBookInfoManager bookInfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
			TkBookContentManager bookContentManager = (TkBookContentManager) getBean("tkBookContentManager");
			TkQuestionsTypeManager typeManager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
			//1.作业本名称，作业名称
			String[] strArray = null;   
			String[] strArray2 = null; 
	        strArray = filename.split(",");
	        String bookTitle = strArray[0];//作业本名称
	        strArray2 = bookTitle.split("_");
	        bookTitle = strArray2[0];//作业本名称
	        String version = strArray2[1];//版本
	        version = Constants.getCodeTypeId("version", version);
	        String bookContentTitle = strArray[1];//作业名称
	        
	        
	        //2.解析单个试题json数据
	        String questionno = json.getString("questionno");//题号
	        String rightans = json.getString("rightans");//正确答案
	        String difficult = json.getString("difficult");//难易程度，A容易，B较易，C一般，D较难，E很难
	        String score = json.getString("score");//分值
	        String area = json.getString("area");//地区
	        String theyear = json.getString("theyear");//年份
	        String type = json.getString("type");//题型，A单选题，B多选题，C判断题，E填空题，S其他主观题
	        String tag = json.getString("tag");//考点
	        String questionsfilepath = json.getString("questionsfilepath");//试题碎片化文件路径
	        String titlefilepath = json.getString("titlefilepath");//题干碎片化文件路径
	        JSONArray optionsfilepath = json.getJSONArray("optionsfilepath");//选项数组碎片化文件路径
	        String option1filepath="";
	        String option2filepath="";
	        String option3filepath="";
	        String option4filepath="";
	        String option5filepath="";
	        String option6filepath="";
	        String option7filepath="";
	        String option8filepath="";
	        String option9filepath="";
	        String option10filepath="";
	        if(optionsfilepath != null && optionsfilepath.size() > 0){
				JSONObject jsonObject = null;
				for(int i=0, size=optionsfilepath.size(); i<size; i++){
					jsonObject = optionsfilepath.getJSONObject(i);//单独的一个选项碎片化文件
					String optionfilepath =jsonObject.getString("optionfilepath");
					if(i == 0){
						option1filepath = optionfilepath;
					}else if(i ==1){
						option2filepath = optionfilepath;
					}else if(i ==2){
						option3filepath = optionfilepath;
					}else if(i ==3){
						option4filepath = optionfilepath;
					}else if(i ==4){
						option5filepath = optionfilepath;
					}else if(i ==5){
						option6filepath = optionfilepath;
					}else if(i ==6){
						option7filepath = optionfilepath;
					}else if(i ==7){
						option8filepath = optionfilepath;
					}else if(i ==8){
						option9filepath = optionfilepath;
					}else if(i ==9){
						option10filepath = optionfilepath;
					}
				}
	        }
	        String descriptfilepath = json.getString("descriptfilepath");//答案解析碎片化文件路径
	        String rightansfilepath ="";
	        if(json.containsKey("rightansfilepath")){
	        	  rightansfilepath = json.getString("rightansfilepath");//参考答案碎片化文件路径
	        }
	        
	      //3.碎题文件的路径
	        //1）解压ZIP文件
	      
	        //2）根据碎片文件名去解压的文件夹里面找解压出来的文件
			 	File folder = new File(destDir);
			 	int countFiles = 0;// 声明统计文件个数的变量
			    int countFolders = 0;// 声明统计文件夹的变量
				//3.1题干文本内容
				String keyword = titlefilepath;
				String titletext ="";
				if(keyword != null && keyword.trim().length() > 0){
		        if (!folder.exists()) {// 如果文件夹不存在
		            System.out.println("目录不存在：" + folder.getAbsolutePath());
		        }
		        File[] result1 = FindFile.searchFile(folder, keyword);// 调用方法获得文件数组
		        System.out.println("在 " + folder + " 以及所有子文件时查找对象" + keyword);
		        System.out.println("查找了" + countFiles + " 个文件，" + countFolders + " 个文件夹，共找到 " + result1.length + " 个符合条件的文件：");
		        String htmlPath ="";
		        for (int i = 0; i < result1.length; i++) {// 循环显示文件
		            File file = result1[i];
		            titlefilepath = file.getPath().substring(file.getPath().indexOf("\\upload"), file.getAbsolutePath().length());//题干碎片化文件在本地的doc路径
		            System.out.println(file.getPath().substring(file.getPath().indexOf("\\upload"), file.getAbsolutePath().length()) + " ");// 显示文件绝对路径
		            htmlPath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."))+".htm";//查找到html文件路径
		        }
		        //根据html文件路径找到对应的html文件，然后进行解析，获取里面的内容
		        File example = new File(htmlPath);
		        titletext = ResolveHtml.getDocument(example,newUUID);
				System.out.println("题干内容为："+titletext);
				}
				//3.2选项数组
				List list = new ArrayList();
				list.add(option1filepath);
				list.add(option2filepath);
				list.add(option3filepath);
				list.add(option4filepath);
				list.add(option5filepath);
				list.add(option6filepath);
				list.add(option7filepath);
				list.add(option8filepath);
				list.add(option9filepath);
				list.add(option10filepath);
				String optionsfilepath_str = "";
				//初始化选项数据
				String option1 ="";
				String option2 ="";
				String option3 ="";
				String option4 ="";
				String option5 ="";
				String option6 ="";
				String option7 ="";
				String option8 ="";
				String option9 ="";
				String option10 ="";
				for(int j=0; j<list.size(); j++){
					String keyword2 = (String) list.get(j);
					if(keyword2 != null && keyword2.trim().length() > 0){
			        if (!folder.exists()) {// 如果文件夹不存在
			            System.out.println("目录不存在：" + folder.getAbsolutePath());
			        }
			        File[] result2 = FindFile.searchFile(folder, keyword2);// 调用方法获得文件数组
			        System.out.println("在 " + folder + " 以及所有子文件时查找对象" + keyword2);
			        System.out.println("查找了" + countFiles + " 个文件，" + countFolders + " 个文件夹，共找到 " + result2.length + " 个符合条件的文件：");
			        String htmlPath2 ="";
			        for (int i = 0; i < result2.length; i++) {// 循环显示文件
			            File file = result2[i];
			            optionsfilepath_str = file.getPath().substring(file.getPath().indexOf("\\upload"), file.getAbsolutePath().length());//选项碎片化文件在本地的doc路径
			            System.out.println(file.getPath() + " ");// 显示文件绝对路径
			            htmlPath2 = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."))+".htm";//查找到html文件路径
			        }
			        //根据html文件路径找到对应的html文件，然后进行解析，获取里面的内容
			        File example2 = new File(htmlPath2);
			        String optiontext = ResolveHtml.getDocument(example2,newUUID);
					System.out.println("选项内容为："+optiontext);
					if(j == 0){
						option1filepath = optionsfilepath_str;
						option1 = optiontext;
					}else if(j == 1){
						option2filepath = optionsfilepath_str;
						option2 = optiontext;
					}else if(j == 2){
						option3filepath = optionsfilepath_str;
						option3 = optiontext;
					}else if(j == 3){
						option4filepath = optionsfilepath_str;
						option4 = optiontext;
					}else if(j == 4){
						option5filepath = optionsfilepath_str;
						option5 = optiontext;
					}else if(j == 5){
						option6filepath = optionsfilepath_str;
						option6 = optiontext;
					}else if(j == 6){
						option7filepath = optionsfilepath_str;
						option7 = optiontext;
					}else if(j == 7){
						option8filepath = optionsfilepath_str;
						option8 = optiontext;
					}else if(j == 8){
						option9filepath = optionsfilepath_str;
						option9 = optiontext;
					}else if(j == 9){
						option10filepath = optionsfilepath_str;
						option10 = optiontext;
					}
				 }
				}	
				//3.3答案解析
				String keyword3 = descriptfilepath;
				String descripttext ="";
				if(keyword3 != null && keyword3.trim().length() > 0){
		        if (!folder.exists()) {// 如果文件夹不存在
		            System.out.println("目录不存在：" + folder.getAbsolutePath());
		        }
		        File[] result3 = FindFile.searchFile(folder, keyword3);// 调用方法获得文件数组
		        System.out.println("在 " + folder + " 以及所有子文件时查找对象" + keyword3);
		        System.out.println("查找了" + countFiles + " 个文件，" + countFolders + " 个文件夹，共找到 " + result3.length + " 个符合条件的文件：");
		        String htmldescriptfilepath ="";
		        for (int i = 0; i < result3.length; i++) {// 循环显示文件
		            File file = result3[i];
		            descriptfilepath = file.getPath().substring(file.getPath().indexOf("\\upload"), file.getAbsolutePath().length());//答案解析碎片化文件在本地的doc路径
		            System.out.println(file.getPath() + " ");// 显示文件绝对路径
		            htmldescriptfilepath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."))+".htm";//查找到html文件路径
		        }
		        //根据html文件路径找到对应的html文件，然后进行解析，获取里面的内容
		        File example3 = new File(htmldescriptfilepath);
		        descripttext = ResolveHtml.getDocument(example3,newUUID);
				System.out.println("答案解析内容为："+descripttext);
				}
		        //3.4参考答案
				String keyword4 = rightansfilepath;
				String rightanstext ="";
				if(keyword4 != null && keyword4.trim().length() > 0 && keyword4.length()>0){
		        if (!folder.exists()) {// 如果文件夹不存在
		            System.out.println("目录不存在：" + folder.getAbsolutePath());
		        }
		        File[] result4 = FindFile.searchFile(folder, keyword4);// 调用方法获得文件数组
		        System.out.println("在 " + folder + " 以及所有子文件时查找对象" + keyword4);
		        System.out.println("查找了" + countFiles + " 个文件，" + countFolders + " 个文件夹，共找到 " + result4.length + " 个符合条件的文件：");
		        String htmlrightansfilepath ="";
		        for (int i = 0; i < result4.length; i++) {// 循环显示文件
		            File file = result4[i];
		            rightansfilepath = file.getPath().substring(file.getPath().indexOf("\\upload"), file.getAbsolutePath().length());//参考答案碎片化文件在本地的doc路径
		            System.out.println(file.getPath() + " ");// 显示文件绝对路径
		            htmlrightansfilepath = file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf("."))+".htm";//查找到html文件路径
		        }
		        //根据html文件路径找到对应的html文件，然后进行解析，获取里面的内容
		        File example4 = new File(htmlrightansfilepath);
		        rightanstext = ResolveHtml.getDocument(example4,newUUID);
				System.out.println("参考答案内容为："+rightanstext);
				}
				//3.5试题文件路径
				String keyword5 = questionsfilepath;
				if(keyword5 != null && keyword5.trim().length() > 0){
		        if (!folder.exists()) {// 如果文件夹不存在
		            System.out.println("目录不存在：" + folder.getAbsolutePath());
		        }
		        File[] result5 = FindFile.searchFile(folder, keyword5);// 调用方法获得文件数组
		        System.out.println("在 " + folder + " 以及所有子文件时查找对象" + keyword5);
		        System.out.println("查找了" + countFiles + " 个文件，" + countFolders + " 个文件夹，共找到 " + result5.length + " 个符合条件的文件：");
		        for (int i = 0; i < result5.length; i++) {// 循环显示文件
		            File file = result5[i];
		            questionsfilepath = file.getPath().substring(file.getPath().indexOf("\\upload"), file.getAbsolutePath().length());//参考答案碎片化文件在本地的doc路径
		            System.out.println(file.getPath() + " ");// 显示文件绝对路径
		        }
				}
		        //3.6整个试卷文件路径
		        String keyword6 = filepath;
		        if(keyword6 != null && keyword6.trim().length() > 0){
		        if (!folder.exists()) {// 如果文件夹不存在
		            System.out.println("目录不存在：" + folder.getAbsolutePath());
		        }
		        File[] result6 = FindFile.searchFile(folder, keyword6);// 调用方法获得文件数组
		        System.out.println("在 " + folder + " 以及所有子文件时查找对象" + keyword6);
		        System.out.println("查找了" + countFiles + " 个文件，" + countFolders + " 个文件夹，共找到 " + result6.length + " 个符合条件的文件：");
		        for (int i = 0; i < result6.length; i++) {// 循环显示文件
		            File file = result6[i];
//		            filepath = file.getAbsolutePath();//参考答案碎片化文件在本地的doc路径
		            filepath = file.getPath().substring(file.getPath().indexOf("\\upload"), file.getAbsolutePath().length());
		            System.out.println(file.getPath() + " ");// 显示文件绝对路径
		        }
		        }
	        
	        //4.根据作业本名称获取学科年纪
	        List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "title", "like", "%" + bookTitle + "%");
			SearchCondition.addCondition(condition, "version", "=", version);
	        List tkBookInfos = bookInfoManager.getTkBookInfos(condition, "a.bookno", 0);
	        TkBookInfo bookInfo = new TkBookInfo();
	        Integer gradeid =0;
	        Integer subjectid =0;
	        if(tkBookInfos !=null && tkBookInfos.size()>0){
	        	bookInfo = (TkBookInfo) tkBookInfos.get(0);
	        	gradeid = bookInfo.getGradeid();//年纪
	        	subjectid = bookInfo.getSubjectid();//学科
	        }
	        
	        //5.创建试题对象
			TkQuestionsInfo questionsInfo = new TkQuestionsInfo();
			//保存试题信息
			questionsInfo.setParentid(0);// 复合题型中，如英语学科完型填空题，有多个小题，父id就是完型填空的主观题id，默认都为0
//			String questionno_1 = "";
//			for (int i = questionsInfo.getQuestionid().toString().length(); i < 10; i++) {
//				questionno_1 += "0";
//			}
//			questionno_1 += questionsInfo.getQuestionid();
			questionsInfo.setQuestionno(questionno);// 系统自动生成，题编号
			questionsInfo.setTitle("");//试题标题
			questionsInfo.setTitlecontent(titletext);// 试题内容，即标题题干
			questionsInfo.setFilepath(questionsfilepath);// 存放试题dsc源文件路径地址
			questionsInfo.setOptionnum(optionsfilepath.size());// 客观题选项个数，程序自动计算填充；
			questionsInfo.setOption1(option1);//选项
			questionsInfo.setOption2(option2);
			questionsInfo.setOption3(option3);
			questionsInfo.setOption4(option4);
			questionsInfo.setOption5(option5);
			questionsInfo.setOption6(option6);
			questionsInfo.setOption7(option7);
			questionsInfo.setOption8(option8);
			questionsInfo.setOption9(option9);
			questionsInfo.setOption10(option10);
			questionsInfo.setQuestiontype("O");// 题型
			questionsInfo.setDoctype("0");// 题干和答案呈现类型，默认0为普通文本，1为通过doc批量导入生成的图片
			if(rightans != null && rightans.trim().length() > 0){
			questionsInfo.setRightans(rightans);// 正确答案
			}else{
				questionsInfo.setRightans(rightanstext);// 正确答案
			}
			if(type == "A" || type == "B" || type == "C"){
				questionsInfo.setIsrightans("1");// 1是否是固定标准答案，如选择题、判断题、只有唯一答案的填空题等，0表示不是标准答案
			}else{
				questionsInfo.setIsrightans("0");// 1是否是固定标准答案，如选择题、判断题、只有唯一答案的填空题等，0表示不是标准答案
			}
			
			questionsInfo.setDifficult(difficult);// 难易程度，1=容易，2=较易，3=一般，4=较难，5=很难，改：用英文字母表示【Constants.difficult】
			questionsInfo.setScore(score);//参考分值
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			questionsInfo.setCretatdate(sdf.format(new Date()));// 创建时间
			questionsInfo.setUpdatetime(sdf.format(new Date()));
			questionsInfo.setDescript(descripttext);// 答案解析
			questionsInfo.setDescriptpath(descriptfilepath);// dsc格式试题解析文件存放路径
			questionsInfo.setStatus("1");// 状态，1正常 2.禁用
			questionsInfo.setArea(area);// 地区
			questionsInfo.setTheyear(theyear);//年份
			questionsInfo.setAuthorname("系统管理员");//创建者名称
			questionsInfo.setAuthorid(2);//创建者id
			TkQuestionsType tkQuestionType = typeManager.getTkQuestionsType(type, subjectid);
			if(tkQuestionType != null){
				questionsInfo.setTkQuestionsType(tkQuestionType);// 题型分类
			}else{
				tkQuestionType = new TkQuestionsType();
				tkQuestionType.setTypeno("0001");
				String typename ="";
				if("A".equals(type)){
					typename = "单选题";
				}else if("B".equals(type)){
					typename = "多选题";
				}else if("C".equals(type)){
					typename = "判断题";
				}else if("E".equals(type)){
					typename = "填空";
				}else if("S".equals(type)){
					typename = "其他主观题";
				}
				tkQuestionType.setType(type);
				tkQuestionType.setTypename(typename);
				tkQuestionType.setSubjectid(subjectid);
				tkQuestionType.setUnitid(1);
				typeManager.addTkQuestionsType(tkQuestionType);
				questionsInfo.setTkQuestionsType(tkQuestionType);// 题型分类
				
			}
			questionsInfo.setSubjectid(subjectid);//学科
			questionsInfo.setGradeid(gradeid);//年级
			questionsInfo.setUnitid(1);
			questionsInfo.setTag(tag);
			questionsInfo.setFilmtwocodepath(TwoCodeUtil.getFilmTwoCodePath(questionsInfo, request));
			questionsInfo.setSimilartwocodepath(TwoCodeUtil.getSimilarTwoCodePath(questionsInfo, request));
			
	        manager.addTkQuestionsInfo(questionsInfo);
	       
	        //6.创建题库碎片化附件
	        TkQuestionsInfoFile questionsInfoFile =new  TkQuestionsInfoFile();
	        //保存题库碎片化附件
	        questionsInfoFile.setFilepath(filepath);//试卷原始文件路径
	        questionsInfoFile.setQuestionsfilepath(questionsfilepath);//试题原始文件路劲
	        questionsInfoFile.setTitlefilepath(titlefilepath);
	        questionsInfoFile.setOption1filepath(option1filepath);
	        questionsInfoFile.setOption2filepath(option2filepath);
	        questionsInfoFile.setOption3filepath(option3filepath);
	        questionsInfoFile.setOption4filepath(option4filepath);
	        questionsInfoFile.setOption5filepath(option5filepath);
	        questionsInfoFile.setOption6filepath(option6filepath);
	        questionsInfoFile.setOption7filepath(option7filepath);
	        questionsInfoFile.setOption8filepath(option8filepath);
	        questionsInfoFile.setOption9filepath(option9filepath);
	        questionsInfoFile.setOption10filepath(option10filepath);
	        questionsInfoFile.setRightansfilepath(rightansfilepath);
	        questionsInfoFile.setDescriptfilepath(descriptfilepath);
	        questionsInfoFile.setQuestionid(questionsInfo.getQuestionid());
	        
	        questionsInfoFilemanager.addTkQuestionsInfoFile(questionsInfoFile);
	        
			
			
			//8.将试卷与试题关联
			TkPaperContent papercontent = new TkPaperContent();
			papercontent.setPaperid(paperInfo.getPaperid());//试卷id
			papercontent.setQuestionid(questionsInfo.getQuestionid());//题库id
			papercontent.setOrderindex(Integer.valueOf(questionno));//试题在当前试卷中的排序
			papercontent.setScore(Float.valueOf(score));//默认为题库的参考分值，也可以手动修改，当前值为当前试题所占试卷的分值大小
			
			paperContentManager.addTkPaperContent(papercontent);
			
			
			
			result = "1";
		} catch (Exception e) {
			result = "0";
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
			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + IpUtil.getIpAddr(request) + "】  调用了《9001：自动更新客户端文件》接口";
			addUseLogs(getRealpath(request), calldescript);

			JSONObject resultJson = new JSONObject();
			//自动更新
			Map<String, String> map = getVersion(getRealpath(request));

			resultJson.put("result", "0");
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
			String exceptioninfo = null;
			try {
				exceptioninfo = json.getString("exceptioninfo");
			} catch (Exception e) {
				exceptioninfo = null;
			}

			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + IpUtil.getIpAddr(request) + "】  调用了《9002：上传错误日志》接口";
			addUseLogs(getRealpath(request), calldescript);

			JSONObject resultJson = new JSONObject();
			String descript = DateTime.getDate() + " 【" + IpUtil.getIpAddr(request) + "】  " + exceptioninfo;
			addLogs(getRealpath(request), descript);

			resultJson.put("result", "0");
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
			String date = null;
			try {
				date = json.getString("date");
			} catch (Exception e) {
				date = null;
			}

			// 记录调用接口日志
			String calldescript = DateTime.getDate() + " 【" + IpUtil.getIpAddr(request) + "】  调用了《9003：下载错误日志》接口";
			addUseLogs(getRealpath(request), calldescript);

			JSONObject resultJson = new JSONObject();
			String zippath = "/app/pctools/logs/zip";
			String zipname = DateTime.getDate("yyyyMMddHHmmssS") + ".zip";
			if (date == null || "".equals(date) || "0".equals(date)) {
				String zipFilePath = "/app/pctools/logs/client";
				AntZipFile antZipFile = new AntZipFile();
				antZipFile.doZip(getRealpath(request) + zipFilePath, getRealpath(request) + zippath, zipname);
			} else {
				// 循环所有文件，然后拷贝需要的文件到制定目录
				String datetime = DateTime.getDate("yyyyMMddHHmmssS");
				String zipFilePath0 = "/app/pctools/logs/zip/" + datetime;
				String zipFilePath = "/app/pctools/logs/zip/" + datetime + "/client";
				String logpath = getRealpath(request) + "/app/pctools/logs/client";
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
			resultJson.put("logurl", homepage + zippath + "/" + zipname);
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
	private Map<String, String> getVersion(String realpath) {
		String xmlpath = "/app/pctools/version.txt";// 解析此文件获取相关信息
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
	private void addLogs(String realpath, String descript) {
		String path = "/app/pctools/logs/client/";
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
	private void addUseLogs(String realpath, String descript) {
		String path = "/app/pctools/logs/used/";//pctools区分录课客户端
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
