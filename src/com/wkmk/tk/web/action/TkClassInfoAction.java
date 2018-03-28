package com.wkmk.tk.web.action;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.tk.bo.TkClassInfo;
import com.wkmk.tk.bo.TkClassPassword;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.service.TkClassInfoManager;
import com.wkmk.tk.service.TkClassPasswordManager;
import com.wkmk.tk.web.form.TkClassInfoActionForm;

/**
 *<p>Description: 班级信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes","deprecation" })
public class TkClassInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String classno=Encode.nullToBlank(httpServletRequest.getParameter("classno"));
		String classname=Encode.nullToBlank(httpServletRequest.getParameter("classname"));
		String tag=Encode.nullToBlank(httpServletRequest.getParameter("tag"));
		Integer userid=null;
		if("".equals(tag)){
			userid=(Integer) httpServletRequest.getSession().getAttribute("s_userid");
		}
		TkClassInfoManager manager = (TkClassInfoManager)getBean("tkClassInfoManager");
		TkBookInfoManager bookManager=(TkBookInfoManager)getBean("tkBookInfoManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "classno", "like", "%"+classno+"%");
		SearchCondition.addCondition(condition, "classname", "like", "%"+classname+"%");
		SearchCondition.addCondition(condition, "userid", "=", userid);
		String sorderindex="createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex=pageUtil.getOrderindex();
		}
		PageList page = manager.getPageTkClassInfos(condition,sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List<TkClassInfo> classes=page.getDatalist();
		List list=new ArrayList();
		for (TkClassInfo cla : classes) {
			String bookname=bookManager.getTkBookInfo(cla.getBookid()).getTitle();
			Map map=new HashMap();
			map.put("classname",cla.getClassname());
			map.put("classno",cla.getClassno());
			map.put("classid", cla.getClassid());
			map.put("students",cla.getStudents());
			map.put("bookname",bookname);
			list.add(map);
		}
		httpServletRequest.setAttribute("tag", tag);
		httpServletRequest.setAttribute("classes", list);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("classno", classno);
		httpServletRequest.setAttribute("classname", classname);
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
		TkClassInfo model = new TkClassInfo();
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
		TkClassInfoActionForm form = (TkClassInfoActionForm)actionForm;
		TkClassInfoManager manager = (TkClassInfoManager)getBean("tkClassInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkClassInfo model = form.getTkClassInfo();
				manager.addTkClassInfo(model);
				addLog(httpServletRequest,"增加了一个班级信息");
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
		TkClassInfoManager manager = (TkClassInfoManager)getBean("tkClassInfoManager");
		String tag=Encode.nullToBlank(httpServletRequest.getParameter("tag"));
		tag="-1".equals(tag)?null:tag;
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkClassInfo model = manager.getTkClassInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
		}
		saveToken(httpServletRequest);
		// 分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("tag", tag);
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		httpServletRequest.setAttribute("title", "班级信息修改");
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
		TkClassInfoActionForm form = (TkClassInfoActionForm)actionForm;
		TkClassInfoManager manager = (TkClassInfoManager)getBean("tkClassInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkClassInfo model = form.getTkClassInfo();
				manager.updateTkClassInfo(model);
				addLog(httpServletRequest,"修改了一个班级信息");
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
		TkClassInfoManager manager = (TkClassInfoManager)getBean("tkClassInfoManager");
		TkClassPasswordManager pwdManager = (TkClassPasswordManager)getBean("tkClassPasswordManager");
		String classid = httpServletRequest.getParameter("classid");
		if(manager.getClassUser(classid).size()<=0){		
		    	pwdManager.delTkClassPasswordByClassid(classid);
			manager.delTkClassInfo(classid);
			return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		}else{
		    	httpServletRequest.setAttribute("promptinfo", "班级内存在学员，不允许删除。");
			return actionMapping.findForward("success");
		}
	}
	
	/**
	 *班级作业
	 *根据登陆人查询班级列表
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	
	public ActionForward getClassListByUser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
		TkBookInfoManager bookManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		String classname = Encode.nullToBlank(httpServletRequest.getParameter("classname"));
		String classno = Encode.nullToBlank(httpServletRequest.getParameter("classno"));
		String tag=Encode.nullToBlank(httpServletRequest.getParameter("tag"));
		Integer userid=null;
		if("".equals(tag)){
			userid=(Integer) httpServletRequest.getSession().getAttribute("s_userid");
		}
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "classname", "like","%"+classname+"%");
		SearchCondition.addCondition(condition, "classno", "like", "%"+classno+"%");
		SearchCondition.addCondition(condition, "userid", "=", userid);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageTkClassInfos(condition,sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List<TkClassInfo> classes=page.getDatalist();
		List list=new ArrayList();
		for (TkClassInfo cla : classes) {
			String bookname=bookManager.getTkBookInfo(cla.getBookid()).getTitle();
			Map m=new HashMap();
			m.put("bookname", bookname);
			m.put("classname",cla.getClassname());
			m.put("classno", cla.getClassno());
			m.put("classid", cla.getClassid());
			m.put("students", cla.getStudents());
			list.add(m);
		}
		httpServletRequest.setAttribute("tag", tag);
		httpServletRequest.setAttribute("classes", list);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("classname", classname);
		httpServletRequest.setAttribute("classno", classno);
		return actionMapping.findForward("myclass");
	}
	
	/*
	 * 班级详情
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward detail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		String classid = Encode.nullToBlank(httpServletRequest.getParameter("classid"));
		TkClassInfoManager manager=(TkClassInfoManager)getBean("tkClassInfoManager");
		TkClassInfo cla=manager.getTkClassInfo(classid);
		httpServletRequest.setAttribute("cla", cla);
		return actionMapping.findForward("detail");
	}
	
	/*
	 *班级作业列表
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward getBookContentByClass(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		String classid = Encode.nullToBlank(httpServletRequest.getParameter("classid"));
		String contentno = Encode.nullToBlank(httpServletRequest.getParameter("contentno"));
		String title=Encode.nullToBlank(httpServletRequest.getParameter("title"));
		TkClassInfoManager manager=(TkClassInfoManager)getBean("tkClassInfoManager");
		PageUtil util=new PageUtil(httpServletRequest);
		Map param=new HashMap();
		param.put("classid", classid);
		param.put("title", title);
		param.put("contentno",contentno);
		String sorderindex="createdate desc";
		if(!"".equals(util.getOrderindex())){
			sorderindex=util.getOrderindex();
		}
		PageList bookContents= manager.getBookContentByClass(param, sorderindex, util.getStartCount(), util.getPageSize());
		httpServletRequest.setAttribute("pagelist", bookContents);
		httpServletRequest.setAttribute("classid", classid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("contentno", contentno);
		return actionMapping.findForward("bookcontents");
	}
	
	
	/**
	 * 导出班级密码
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 */
	public void downloadClassPassword(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
		String classid=Encode.nullToBlank(httpServletRequest.getParameter("classid"));
		TkClassInfoManager manager=(TkClassInfoManager)getBean("tkClassInfoManager");
		TkClassInfo clazz=manager.getTkClassInfo(classid);
		List data=manager.getPasswordByClass(classid);
	      //第一步，创建一个webbook，对应一个Excel文件  
	      HSSFWorkbook wb = new HSSFWorkbook();  
	      // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	      HSSFSheet sheet = wb.createSheet("sheet_1");  
	      // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	      HSSFRow row = sheet.createRow((int) 0);  
	      // 第四步，创建单元格，并设置值表头 设置表头居中  
	      HSSFCellStyle style = wb.createCellStyle();  
	      style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	      HSSFCell cell = row.createCell((short) 0);  
	      cell.setCellValue("密码");  
	      cell.setCellStyle(style);  
	      HSSFCell cell1 = row.createCell((short) 1); 
	      cell1.setCellValue("使用状态");  
	      cell1.setCellStyle(style); 
	      for (int i = 0; i < data.size(); i++)  
	      {  
	          row = sheet.createRow((int) i + 1);  
	          TkClassPassword pwd= (TkClassPassword)data.get(i);  
	          // 第四步，创建单元格，并设置值  
	          row.createCell((short) 0).setCellValue(pwd.getPassword());
	          row.createCell((short) 1).setCellValue("1".equals(pwd.getUsed())?"已绑定":"未使用"); 
	      }  
	      // 第六步，将文件存到指定位置  
	      try  
	      {  
	    	   String filename=clazz.getClassname()+".xls";
	    	   httpServletResponse.setContentType("application/vnd.ms-excel");
	    	   httpServletResponse.addHeader("content_type", "application/x-msdownload");
	    	   httpServletResponse.addHeader("Content-Disposition ", "attachment;filename="+URLEncoder.encode(filename,"UTF-8"));
	    	   wb.write(httpServletResponse.getOutputStream());  
	      }  
	      catch (Exception e)  
	      {  
	          e.printStackTrace();  
	      }  
	}
	
	/**
	 * 导出班级学员
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 */
	public void downloadClassUser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
		String classid=Encode.nullToBlank(httpServletRequest.getParameter("classid"));
		TkClassInfoManager manager=(TkClassInfoManager)getBean("tkClassInfoManager");
		TkClassInfo clazz=manager.getTkClassInfo(classid);
		List data=manager.getClassUser(classid);
	      //第一步，创建一个webbook，对应一个Excel文件  
	      HSSFWorkbook wb = new HSSFWorkbook();  
	      // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
	      HSSFSheet sheet = wb.createSheet("sheet_1");  
	      // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
	      HSSFRow row = sheet.createRow((int) 0);  
	      // 第四步，创建单元格，并设置值表头 设置表头居中  
	      HSSFCellStyle style = wb.createCellStyle();  
	      style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
	      HSSFCell cell = row.createCell((short) 0);  
	      cell.setCellValue("姓名");  
	      cell.setCellStyle(style);  
	      HSSFCell cell1 = row.createCell((short) 1); 
	      cell1.setCellValue("性别");  
	      cell1.setCellStyle(style); 
	      for (int i = 0; i < data.size(); i++)  
	      {  
	          row = sheet.createRow((int) i + 1);  
	          SysUserInfo user= (SysUserInfo)data.get(i);  
	          // 第四步，创建单元格，并设置值  
	          row.createCell((short) 0).setCellValue(user.getUsername());
	          row.createCell((short) 1).setCellValue("0".equals(user.getSex())?"男":"女"); 
	      }  
	      // 第六步，将文件存到指定位置  
	      try  
	      {  
	    	   String filename=clazz.getClassname()+"——学员信息.xls";
	    	   httpServletResponse.reset();
	    	   httpServletResponse.setContentType("application/vnd.ms-excel");
	    	   httpServletResponse.addHeader("content_type", "application/x-msdownload");
	    	   httpServletResponse.addHeader("Content-Disposition ", "attachment;filename="+URLEncoder.encode(filename,"UTF-8"));
	    	   wb.write(httpServletResponse.getOutputStream());  
	      }  
	      catch (Exception e)  
	      {  
	          e.printStackTrace();  
	      }  
	}
	/*
	 *完成某项班级作业的学员列表
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward getStudents(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		String classid = Encode.nullToBlank(httpServletRequest.getParameter("classid"));
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String loginname=Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username=Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String usertype=Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno=Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		String sex=Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		
		TkClassInfoManager manager=(TkClassInfoManager)getBean("tkClassInfoManager");
		PageUtil util=new PageUtil(httpServletRequest);
		Map param=new HashMap();
		param.put("classid", classid);
		param.put("bookcontentid", bookcontentid);
		param.put("loginname",loginname);
		param.put("username",username);
		param.put("usertype",usertype);
		param.put("studentno",studentno);
		param.put("sex",sex);
		String sorderindex="createdate desc";
		if(!"".equals(util.getOrderindex())){
			sorderindex=util.getOrderindex();
		}
		PageList students= manager.getStudentByBookContent(param, sorderindex, util.getStartCount(), util.getPageSize());
		httpServletRequest.setAttribute("pagelist", students);
		httpServletRequest.setAttribute("classid", classid);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		httpServletRequest.setAttribute("sex", sex);
		return actionMapping.findForward("students");
	}
	
	/*
	 *学员作业完成情况
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward completion(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		String classid = Encode.nullToBlank(httpServletRequest.getParameter("classid"));
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String userid=Encode.nullToBlank(httpServletRequest.getParameter("userid"));		
		TkClassInfoManager manager=(TkClassInfoManager)getBean("tkClassInfoManager");	
		List detail= manager.getCompletion(classid,userid,bookcontentid);
		Map<String, Object> map=new HashMap<String, Object>();
		List<String> images=new ArrayList<String>();
		for (int i = 0; i < detail.size(); i++) {
			images.add(((Object[])detail.get(i))[3].toString());
		}
		map.put("classname", ((Object[])detail.get(0))[0].toString());
		map.put("username", ((Object[])detail.get(0))[1].toString());
		map.put("title", ((Object[])detail.get(0))[2].toString());
		map.put("images", images);
		httpServletRequest.setAttribute("detail", map);
		return actionMapping.findForward("cub_detail");
	}
	
	/**
	 *作业分析
	 *根据登陆人查询班级列表
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward getAnalysis(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
		TkBookInfoManager bookManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		String classname = Encode.nullToBlank(httpServletRequest.getParameter("classname"));
		String classno = Encode.nullToBlank(httpServletRequest.getParameter("classno"));
		String tag=Encode.nullToBlank(httpServletRequest.getParameter("tag"));
		Integer userid=null;
		if("".equals(tag)){
			userid=(Integer) httpServletRequest.getSession().getAttribute("s_userid");
		}
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "classname", "like","%"+classname+"%");
		SearchCondition.addCondition(condition, "classno", "like", "%"+classno+"%");
		SearchCondition.addCondition(condition, "userid", "=", userid);
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageTkClassInfos(condition,sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List<TkClassInfo> classes=page.getDatalist();
		List list=new ArrayList();
		for (TkClassInfo cla : classes) {
			String bookname=bookManager.getTkBookInfo(cla.getBookid()).getTitle();
			Map m=new HashMap();
			m.put("bookname", bookname);
			m.put("classname",cla.getClassname());
			m.put("classno", cla.getClassno());
			m.put("classid", cla.getClassid());
			m.put("bookid", cla.getBookid());
			m.put("students", cla.getStudents());
			list.add(m);
		}
		httpServletRequest.setAttribute("tag", tag);
		httpServletRequest.setAttribute("classes", list);
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("classname", classname);
		httpServletRequest.setAttribute("classno", classno);
		return actionMapping.findForward("analysis");
	}
	
	/*
	 *作业分析 
	 *作业列表
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward getAnalysisBookcontents(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		String classid=Encode.nullToBlank(httpServletRequest.getParameter("classid"));
		String contentno = Encode.nullToBlank(httpServletRequest.getParameter("contentno"));
		String title=Encode.nullToBlank(httpServletRequest.getParameter("title"));
		TkBookContentManager manager=(TkBookContentManager)getBean("tkBookContentManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "bookid", "=", Integer.parseInt(bookid));
		SearchCondition.addCondition(condition, "contentno", "like", "%"+contentno+"%");
		SearchCondition.addCondition(condition, "title", "like", "%"+title+"%");
		PageList page = manager.getPageTkBookContents(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookid", bookid);
		httpServletRequest.setAttribute("classid", classid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("contentno", contentno);
		return actionMapping.findForward("analysisbookcontents");
	}
	/**
	 * 
	 * 方法描述：饼状图，班级分类统计
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-29 下午1:18:31
	 */
	public ActionForward statisticalClassType(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalClassType");
	}

	/**
	 * 
	 * 方法描述：ajax,餅狀圖，班级分类统计
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-29 下午2:00:15
	 */
	public ActionForward getAjaxClassType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String cString = "";
			TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
			EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
			// 按照学段
			List list1 = manager.getClassInfoCountByType("c.cxueduanid,");
			if (list1 != null && list1.size() > 0) {
				for (int i = 0; i < list1.size(); i++) {
					Object[] lst = (Object[]) list1.get(i);
					Object a = lst[0];// 学段
					Object b = lst[1];// 学科
					Object c = lst[2];// 总数
					aString = aString + "{value:" + c + ", name:'" + a + "'},";
					cString = cString +"'"+a+"'" +",";
				}
			}
			aString = aString.substring(0, aString.length() - 1);
			aString = "[" + aString + "]";

			// 学段，学科
			List list2 = manager.getClassInfoCountByType("c.cxueduanid,c.subjectid,");
			if (list2 != null && list2.size() > 0) {
				for (int i = 0; i < list2.size(); i++) {
					Object[] lst = (Object[]) list2.get(i);
					Object a = lst[0];// 学段
					Object b = lst[1];// 学科
					Object c = lst[2];// 总数
					//根据学科id，获取学科名称
					EduSubjectInfo subjectInfo = esim.getEduSubjectInfo((Integer) b);
					String subjectname = subjectInfo.getSubjectname();
					bString = bString + "{value:" + c + ", name:'" + subjectname + "'},";
					cString = cString +"'"+subjectname+"'" +",";
				}
			}
			bString = bString.substring(0, bString.length() - 1);
			bString = "[" + bString + "]";
			
			cString = cString.substring(0, cString.length() - 1);
			cString = "[" + cString + "]";

			JSONObject jo = new JSONObject();
			JSONArray a = new JSONArray();
			JSONArray b = new JSONArray();
			JSONArray c = new JSONArray();
			a = JSONArray.fromObject(aString);
			b = JSONArray.fromObject(bString);
			c = JSONArray.fromObject(cString);
			jo.put("a", a);
			jo.put("b", b);
			jo.put("c", c);
			pw = response.getWriter();
			pw.write(jo.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}
	
	/**
	 * 
	 * 方法描述：折線图，班级注册
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-29 下午1:18:31
	 */
	public ActionForward statisticalClassAdd(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalClassAdd");
	}

	/**
	 * 
	 * 方法描述：ajax,折現圖，班级注册
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-29 下午2:00:15
	 */
	public ActionForward getAjaxClassAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String endtime ="";
			String starttime ="";
			TkClassInfoManager manager = (TkClassInfoManager) getBean("tkClassInfoManager");
			List maxNum = manager.getClassInfosOfAddMaxNum();
			List list = manager.getClassInfosOfAdd();
			Long max = (Long) Collections.max(maxNum);
			max = max + 5;
			if (list != null && list.size() > 0) {
				for (int i = 1; i <= list.size(); i++) {
					Object[] lst = (Object[]) list.get(i - 1);
					Object a = lst[0];// 日期
					Object b = lst[1];// 人数
					aString = aString + "'" + a + "'" + ",";
					bString = bString + b + ",";
				}
				//取出最后一个元素
				Object[] object = (Object[]) list.get(list.size()-1);
				endtime = (String) object[0];// 最后一个日期
				//取出倒数第30个元素
				if(list.size()>30){
					Object[] object1 = (Object[]) list.get(list.size()-30);
					starttime = (String) object1[0];// 第一个日期
				}else{
					Object[] object1 = (Object[]) list.get(0);
					starttime = (String) object1[0];// 第一个日期
				}
			}
			
			aString = aString.substring(0, aString.length() - 1);
			aString = "[" + aString + "]";
			bString = bString.substring(0, bString.length() - 1);
			bString = "[" + bString + "]";

			JSONObject jo = new JSONObject();
			JSONArray a = new JSONArray();
			JSONArray b = new JSONArray();
			a = JSONArray.fromObject(aString);
			b = JSONArray.fromObject(bString);
			jo.put("a", a);
			jo.put("b", b);
			jo.put("c", max.toString());
			jo.put("starttime", starttime);
			jo.put("endtime", endtime);
			pw = response.getWriter();
			pw.write(jo.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}
	
}