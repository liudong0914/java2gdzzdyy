package com.wkmk.sys.web.action;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.PageOrientation;
import jxl.format.PaperSize;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.json.JSONObject;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.PublicResourceBundle;
import com.wkmk.edu.bo.EduCourseClass;
import com.wkmk.edu.bo.EduCourseInfo;
import com.wkmk.edu.bo.EduCourseUser;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduCourseClassManager;
import com.wkmk.edu.service.EduCourseInfoManager;
import com.wkmk.edu.service.EduCourseUserManager;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.service.SysUnitInfoManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.service.SysUserLogManager;
import com.wkmk.sys.web.form.SysUserInfoActionForm;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.util.common.Constants;

/**
 *<p>Description: 系统用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUmsUserInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String xueduan = Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
		String authentication = Encode.nullToBlank(httpServletRequest.getParameter("authentication"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		String unitid = Encode.nullToBlank(httpServletRequest.getParameter("unitid"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "loginname", "<>", "~admin~");
		if(!"".equals(unitid)){
			SearchCondition.addCondition(condition, "unitid", "=", unitid);
		}
		if(!"".equals(status)){
			SearchCondition.addCondition(condition, "status", "=", status);
		}
		if(!"".equals(loginname)){
			SearchCondition.addCondition(condition, "loginname", "like", "%" + loginname + "%");
		}
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		}
		if(!"".equals(sex) && !"-1".equals(sex)){
			SearchCondition.addCondition(condition, "sex", "=", sex);
		}
		if(!"".equals(usertype) && !"-1".equals(usertype)){
			SearchCondition.addCondition(condition, "usertype", "=", usertype);
		}
		if(!"".equals(xueduan) && !"-1".equals(xueduan)){
			SearchCondition.addCondition(condition, "xueduan", "=", xueduan);
		}
		if(!"".equals(authentication) && !"-1".equals(authentication)){
			SearchCondition.addCondition(condition, "authentication", "=", authentication);
		}
		if(!"".equals(studentno)){
			SearchCondition.addCondition(condition, "studentno", "like", "%" + studentno + "%");
		}
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "loginname asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("xueduan", xueduan);
		httpServletRequest.setAttribute("authentication", authentication);
		httpServletRequest.setAttribute("studentno", studentno);
		httpServletRequest.setAttribute("unitid", unitid);
		httpServletRequest.setAttribute("status", status);
		
		//加省、市、区查询学校
		String province = Encode.nullToBlank(httpServletRequest.getParameter("province"));
		String city = Encode.nullToBlank(httpServletRequest.getParameter("city"));
		String county = Encode.nullToBlank(httpServletRequest.getParameter("county"));
		SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		List provinceList = saim.getSysAreaInfosByParentno("00");
		if(!"".equals(province)){
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(province);
			List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("cityList", cityList);
		}
		if(!"".equals(city)){
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(city);
			List countyList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("countyList", countyList);
		}
		httpServletRequest.setAttribute("provinceList", provinceList);
		httpServletRequest.setAttribute("province", province);
		httpServletRequest.setAttribute("city", city);
		httpServletRequest.setAttribute("county", county);
		if(!"".equals(province)){
			//查询学校
			SysUnitInfoManager suim = (SysUnitInfoManager)getBean("sysUnitInfoManager");
			condition.clear();
			SearchCondition.addCondition(condition, "status", "=", "1");
			if(!"".equals(province)){
				SearchCondition.addCondition(condition, "province", "=", province);
			}
			if(!"".equals(city)){
				SearchCondition.addCondition(condition, "city", "=", city);
			}
			if(!"".equals(county)){
				SearchCondition.addCondition(condition, "county", "=", county);
			}
			List schoolList = suim.getSysUnitInfos(condition, "unitname asc", 0);
			httpServletRequest.setAttribute("schoolList", schoolList);
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
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String xueduan = Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
		String authentication = Encode.nullToBlank(httpServletRequest.getParameter("authentication"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		String unitid = Encode.nullToBlank(httpServletRequest.getParameter("unitid"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("xueduan", xueduan);
		httpServletRequest.setAttribute("authentication", authentication);
		httpServletRequest.setAttribute("studentno", studentno);
		httpServletRequest.setAttribute("unitid", unitid);
		httpServletRequest.setAttribute("status", status);
		String province = Encode.nullToBlank(httpServletRequest.getParameter("province"));
		String city = Encode.nullToBlank(httpServletRequest.getParameter("city"));
		String county = Encode.nullToBlank(httpServletRequest.getParameter("county"));
		httpServletRequest.setAttribute("province", province);
		httpServletRequest.setAttribute("city", city);
		httpServletRequest.setAttribute("county", county);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysUserInfo model = new SysUserInfo();
		SysUserInfoDetail detail = new SysUserInfoDetail();
		model.setPhoto("man.jpg");
		model.setSex("0");
		model.setXueduan("2");
		model.setAuthentication("0");
		model.setUnitid(1);
		detail.setEducation("9");
		detail.setJobtitle("9");
		detail.setCanaddcourse("0");
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("detail", detail);
		httpServletRequest.setAttribute("act", "addSave");
		
		//获取所有省地区
		SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		List provinceList = saim.getSysAreaInfosByParentno("00");
		httpServletRequest.setAttribute("provinceList", provinceList);
		
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
		SysUserInfoActionForm form = (SysUserInfoActionForm)actionForm;
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserInfo model = form.getSysUserInfo();
				//将密码加密
				String password = Encode.nullToBlank(httpServletRequest.getParameter("password"));
				if (!"".equals(password)) {
					model.setPassword(MD5.getEncryptPwd(password));
				}
				model.setStatus("1");
				model.setMoney(0.00F);
				manager.addSysUserInfo(model);
				
				SysUserInfoDetail detail = form.getSysUserInfoDetail();
				if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号加密存储
					detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
				}
				detail.setUserid(model.getUserid());
				detail.setFlag("0");
				detail.setCreatedate(DateTime.getDate());
				detail.setLastlogin(detail.getCreatedate());
				detail.setLogintimes(0);
				detail.setUpdatetime(detail.getCreatedate());
				suidm.addSysUserInfoDetail(detail);
				addLog(httpServletRequest,"增加了一个系统用户【" + model.getUsername() + "】信息");
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
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String xueduan = Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
		String authentication = Encode.nullToBlank(httpServletRequest.getParameter("authentication"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		String unitid = Encode.nullToBlank(httpServletRequest.getParameter("unitid"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("xueduan", xueduan);
		httpServletRequest.setAttribute("authentication", authentication);
		httpServletRequest.setAttribute("studentno", studentno);
		httpServletRequest.setAttribute("unitid", unitid);
		httpServletRequest.setAttribute("status", status);
		String province = Encode.nullToBlank(httpServletRequest.getParameter("province"));
		String city = Encode.nullToBlank(httpServletRequest.getParameter("city"));
		String county = Encode.nullToBlank(httpServletRequest.getParameter("county"));
		httpServletRequest.setAttribute("province", province);
		httpServletRequest.setAttribute("city", city);
		httpServletRequest.setAttribute("county", county);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysUserInfo model = manager.getSysUserInfo(objid);
			SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
			if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号解密显示
				detail.setCardno(DES.getDecryptPwd(detail.getCardno()));
			}
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("detail", detail);
			
			SysUnitInfoManager suim = (SysUnitInfoManager) getBean("sysUnitInfoManager");
			SysUnitInfo sysUnitInfo = suim.getSysUnitInfo(model.getUnitid());
			httpServletRequest.setAttribute("unitInfo", sysUnitInfo);
			
			//获取所有省地区
			SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			List provinceList = saim.getSysAreaInfosByParentno("00");
			httpServletRequest.setAttribute("provinceList", provinceList);
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getProvince());
			List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("cityList", cityList);
			sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getCity());
			List countyList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("countyList", countyList);
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
		SysUserInfoActionForm form = (SysUserInfoActionForm)actionForm;
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserInfo model = form.getSysUserInfo();
				//将密码加密
				String password = Encode.nullToBlank(httpServletRequest.getParameter("password"));
				if (!"".equals(password)) {
					model.setPassword(MD5.getEncryptPwd(password));
				}
				manager.updateSysUserInfo(model);
				
				SysUserInfoDetail detail = form.getSysUserInfoDetail();
				if(detail.getCardno() != null && !"".equals(detail.getCardno())){//证件号加密存储
					detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
				}
				detail.setUserid(model.getUserid());
				suidm.updateSysUserInfoDetail(detail);
				addLog(httpServletRequest,"修改了一个系统用户【" + model.getUsername() + "】信息");
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
		SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String setstatus = Encode.nullToBlank(httpServletRequest.getParameter("setstatus"));

		SysUserInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysUserInfo(checkids[i]);
			if("1".equals(setstatus)){
				model.setStatus("1");
				manager.updateSysUserInfo(model);
				addLog(httpServletRequest,"启用了一个系统用户【" + model.getUsername() + "】信息");
			}else if("2".equals(setstatus)){
				model.setStatus("2");
				manager.updateSysUserInfo(model);
				//manager.delSysUserInfo(checkids[i]);
				addLog(httpServletRequest,"禁用了一个系统用户【" + model.getUsername() + "】信息");
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 根据省、市、区获取学校列表
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward getUnitList(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));//默认1,1表示没有“请选择”，2表示有“请选择”
		String tag = Encode.nullToBlank(httpServletRequest.getParameter("tag"));//1省，2市，3区
		String citycode = Encode.nullToBlank(httpServletRequest.getParameter("citycode"));
		
		//获取所有省地区
		SysUnitInfoManager manager = (SysUnitInfoManager) getBean("sysUnitInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		if("1".equals(tag)){
			SearchCondition.addCondition(condition, "province", "=", citycode);
		}else if("2".equals(tag)){
			SearchCondition.addCondition(condition, "city", "=", citycode);
		}else if("3".equals(tag)){
			SearchCondition.addCondition(condition, "county", "=", citycode);
		}
		List list = manager.getSysUnitInfos(condition, "unitname asc", 0);
		StringBuffer str = new StringBuffer();
		if("2".equals(type)){
			str.append("<option value=''>请选择...</option>");
		}
		if("3".equals(type)){
			str.append("<option value=''>请选择</option>");
		}
		SysUnitInfo sui = null;
		for(int i=0, size=list.size(); i<size; i++){
			sui = (SysUnitInfo) list.get(i);
			str.append("<option value='").append(sui.getUnitid()).append("'>").append(sui.getUnitname()).append("</option>");
		}

		PrintWriter pw = null;
		try {
			pw = httpServletResponse.getWriter();
			pw.write(str.toString());
		} catch (IOException ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}

		return null;
	}

/**
 * 用户信息统计报表
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-9-27 下午3:14:06 
* @lastModified  2016-9-27 下午3:14:06 
* @version  1.0 
* @param actionMapping
* @param actionForm
* @param httpServletRequest
* @param httpServletResponse
* @return
 * @throws IOException 
 * @throws ServletException 
 */
public ActionForward reportList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
	SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
	List list = manager.getUserInfoCountByUserType("usertype,");
	String pieurl= "";//生成的饼状图路径
	if(list !=null && list.size()>0){
		 pieurl= reportPie(httpServletRequest, httpServletResponse,list,"usertype,");//生成的饼状图路径
	}else{
		 pieurl= "/upload/nodate.jpg";//生成的饼状图路径
	}
	
	httpServletRequest.setAttribute("pieurl", pieurl);
	return actionMapping.findForward("reportList");
	}

/**
 * 用户注册统计报表
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-9-30 上午9:34:26 
* @lastModified  2016-9-30 上午9:34:26 
* @version  1.0 
* @param actionMapping
* @param actionForm
* @param httpServletRequest
* @param httpServletResponse
* @return
* @throws ServletException
* @throws IOException
 */
public ActionForward reportUserInfoList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
	SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
	String startTime = Encode.nullToBlank(httpServletRequest.getParameter("startTime"));
	String endTime = Encode.nullToBlank(httpServletRequest.getParameter("endTime"));
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	Date date=new Date();// new Date()为获取当前系统时间
	if("".equals(startTime)){
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(date);    
//		calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间    
		calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
		calendar.getTime();//获取一年前的时间，或者一个月前的时间    
		startTime=df.format(calendar.getTime());
	}
	if("".equals(endTime)){
		endTime=df.format(new Date());
	}
	List list = manager.getUserInfos(startTime,endTime);
	String lineurl= "";
	if(list !=null && list.size()>0){
		lineurl= reportLine(httpServletRequest, httpServletResponse,list);
	}else{
		lineurl= "/upload/nodate.jpg";
	}
	httpServletRequest.setAttribute("startTime", startTime);
	httpServletRequest.setAttribute("endTime", endTime);
	httpServletRequest.setAttribute("lineurl", lineurl);
	return actionMapping.findForward("reportUserInfoList");
	}

/**
 * 用户使用统计
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-10-8 下午3:29:41 
* @lastModified  2016-10-8 下午3:29:41 
* @version  1.0 
* @param actionMapping
* @param actionForm
* @param httpServletRequest
* @param httpServletResponse
* @return
* @throws ServletException
* @throws IOException
 */
public ActionForward reportUserlogList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
	SysUserLogManager manager = (SysUserLogManager)getBean("sysUserLogManager");
	String startTime = Encode.nullToBlank(httpServletRequest.getParameter("startTime"));
	String endTime = Encode.nullToBlank(httpServletRequest.getParameter("endTime"));
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	Date date=new Date();// new Date()为获取当前系统时间
	if("".equals(startTime)){
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(date);    
//		calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间    
		calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
		calendar.getTime();//获取一年前的时间，或者一个月前的时间    
		startTime=df.format(calendar.getTime());
	}
	if("".equals(endTime)){
		endTime=df.format(new Date());
	}
	
	List list = manager.getUserLogs(startTime,endTime);
	String lineurl= "";
	if(list !=null && list.size()>0){
		lineurl= reportLogLine(httpServletRequest, httpServletResponse,list);
	}else{
		lineurl= "/upload/nodate.jpg";
	}
	httpServletRequest.setAttribute("startTime", startTime);
	httpServletRequest.setAttribute("endTime", endTime);
	httpServletRequest.setAttribute("lineurl", lineurl);
	return actionMapping.findForward("reportUserLogList");
	}
/**
 * 按用户类型生成饼状图
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-9-28 上午10:49:48 
* @lastModified  2016-9-28 上午10:49:48 
* @version  1.0 
* @param actionMapping
* @param actionForm
* @param httpServletRequest
* @param httpServletResponse
* @return
* @throws ServletException
* @throws IOException
 */
public ActionForward reportListUsertype(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
	String obj = Encode.nullToBlank(httpServletRequest.getParameter("obj"));
//	String[] strings = obj.split(",");
	SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
	List list = manager.getUserInfoCountByUserType(obj);
	String pieurl= "";
	if(list !=null && list.size()>0){
		pieurl= reportPie(httpServletRequest, httpServletResponse,list,obj);
	}else{
		pieurl= "/upload/nodate.jpg";
	}
	httpServletRequest.setAttribute("pieurl", pieurl);
	return actionMapping.findForward("reportList");
}

/**
 * 
  * 方法描述：用户交易统计
  * @param: 
  * @return: 
  * @version: 1.0
  * @author: 刘冬
  * @version: 2016-11-23 下午5:52:31
 */
public ActionForward reportUserMoneyList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
	SysUserLogManager manager = (SysUserLogManager)getBean("sysUserLogManager");
	String startTime = Encode.nullToBlank(httpServletRequest.getParameter("startTime"));
	String endTime = Encode.nullToBlank(httpServletRequest.getParameter("endTime"));
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	Date date=new Date();// new Date()为获取当前系统时间
	if("".equals(startTime)){
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(date);    
//		calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间    
		calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
		calendar.getTime();//获取一年前的时间，或者一个月前的时间    
		startTime=df.format(calendar.getTime());
	}
	if("".equals(endTime)){
		endTime=df.format(new Date());
	}
	
	List list = manager.getUserMoneys(startTime,endTime);
	String lineurl= "";
	if(list !=null && list.size()>0){
		lineurl= reportMoneyLine(httpServletRequest, httpServletResponse,list);
	}else{
		lineurl= "/upload/nodate.jpg";
	}
	httpServletRequest.setAttribute("startTime", startTime);
	httpServletRequest.setAttribute("endTime", endTime);
	httpServletRequest.setAttribute("lineurl", lineurl);
	return actionMapping.findForward("reportUserMoneyList");
	}
/**
 * 
  * 方法描述：用户充值统计
  * @param: 
  * @return: 
  * @version: 1.0
  * @author: 刘冬
  * @version: 2016-11-23 下午5:52:31
 */
public ActionForward reportUserPayList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
	SysUserLogManager manager = (SysUserLogManager)getBean("sysUserLogManager");
	String startTime = Encode.nullToBlank(httpServletRequest.getParameter("startTime"));
	String endTime = Encode.nullToBlank(httpServletRequest.getParameter("endTime"));
	String option = Encode.nullToBlank(httpServletRequest.getParameter("option"));
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
	Date date=new Date();// new Date()为获取当前系统时间
	if("".equals(startTime)){
		Calendar calendar = Calendar.getInstance();    
		calendar.setTime(date);    
//		calendar.add(Calendar.YEAR, -1);//当前时间减去一年，即一年前的时间    
		calendar.add(Calendar.MONTH, -1);//当前时间前去一个月，即一个月前的时间    
		calendar.getTime();//获取一年前的时间，或者一个月前的时间    
		startTime=df.format(calendar.getTime());
	}
	if("".equals(endTime)){
		endTime=df.format(new Date());
	}
	
	List list =new ArrayList();
	if(option.equals("")){
		list = manager.getUserPaysOfchangemoney(startTime, endTime);
		option = "2";
	}else if(option.equals("1")){
		 list = manager.getUserPays(startTime,endTime);
		 option = "1";
	}else if(option.equals("2")){
		list = manager.getUserPaysOfchangemoney(startTime, endTime);
		option = "2";
	}
	String lineurl= "";
	if(list !=null && list.size()>0){
		if(option.equals("")){
			lineurl= reportPayLineOfchangemoney(httpServletRequest, httpServletResponse,list);
		}else if(option.equals("1")){
			lineurl= reportPayLine(httpServletRequest, httpServletResponse,list);
		}else if(option.equals("2")){
			lineurl= reportPayLineOfchangemoney(httpServletRequest, httpServletResponse,list);
		}
	}else{
		lineurl= "/upload/nodate.jpg";
	}
	httpServletRequest.setAttribute("startTime", startTime);
	httpServletRequest.setAttribute("endTime", endTime);
	httpServletRequest.setAttribute("option", option);
	httpServletRequest.setAttribute("lineurl", lineurl);
	return actionMapping.findForward("reportUserPayList");
	}
/**
 * ajax方式获取饼状图
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-9-29 上午10:16:45 
* @lastModified  2016-9-29 上午10:16:45 
* @version  1.0 
* @param actionMapping
* @param actionForm
* @param httpServletRequest
* @param httpServletResponse
* @return
 * @throws IOException 
 * @throws ServletException 
 */
public void getReportAjax(ActionMapping actionMapping,
		ActionForm actionForm, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) throws ServletException, IOException {
	StringBuffer str = new StringBuffer();
	String obj = Encode.nullToBlank(httpServletRequest.getParameter("obj"));
	SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
	List list = manager.getUserInfoCountByUserType(obj);
	String pieurl= "";
	if(list !=null && list.size()>0){
		pieurl= reportPie(httpServletRequest, httpServletResponse,list,obj);
	}else{
		pieurl= "/upload/nodate.jpg";
	}
	str.append(pieurl);
	PrintWriter pw = null;
	try {
		pw = httpServletResponse.getWriter();
		pw.write(str.toString());
	} catch (Exception ex) {
		ex.printStackTrace();
	} finally {
		if (pw != null) {
			pw.close();
		}
		pw = null;
	}
}
/**
 * 下载excel
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-9-28 上午11:48:48 
* @lastModified  2016-9-28 上午11:48:48 
* @version  1.0 
* @param actionMapping
* @param actionForm
* @param httpServletRequest
* @param httpServletResponse
* @return
* @throws ServletException
* @throws IOException
 */
public ActionForward uploadExcel(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
	String obj = Encode.nullToBlank(httpServletRequest.getParameter("obj"));
	SysUserInfoManager manager = (SysUserInfoManager)getBean("sysUserInfoManager");
	List list = manager.getUserInfoCountByUserType(obj);
	toExcel(httpServletRequest, httpServletResponse, list, obj);
//	return actionMapping.findForward("reportList");
	return null;
}
/**
 * 生成饼状图
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-9-27 下午6:02:41 
* @lastModified  2016-9-27 下午6:02:41 
* @version  1.0 
* @param request
* @param response
* @return
* @throws ServletException
* @throws IOException
 */
public String reportPie(HttpServletRequest request,  
        HttpServletResponse response,List list,String obj) throws ServletException, IOException {  
	response.setContentType("text/html"); 
	// 默认数据类型  
     DefaultPieDataset dataType = new DefaultPieDataset();  
     // 数据参数 内容，数量  
     if(list !=null && list.size()>0){
    	 for(int i=0;i<list.size();i++){
    		Object[] lst =  (Object[]) list.get(i);
    		Object a = lst[0];//类型
    		Object b = lst[1];//性别
    		Object c = lst[2];//学段
    		Object d = lst[3];//人数
    		String e="";
    		if(obj.contains("usertype")){
    			if(a.toString().equals("1")){
    				e+="老师-";
    			}else if(a.toString().equals("2")){
    				e+="学生-";
    			}else if(a.toString().equals("3")){
    				e+="家长-";
    			}
    		}
    		if(obj.contains("sex")){
    			//性别(0男，1女)
    			if(b.toString().equals("0")){
    				e+="男-";
    			}else if(b.toString().equals("1")){
    				e+="女-";
    			}
    		}
    		if(obj.contains("xueduan")){
    			//学段：1小学，2初中，3高中
    			if(c.toString().equals("2")){
    				e+="初中-";
    			}else if(c.toString().equals("3")){
    				e+="高中-";
    			}
    		}
    		e = e.substring(0,e.length()-1);
    		 dataType.setValue(e, Integer.parseInt(String.valueOf(d)));  
    	 }
     }
     //设置路径
     String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
  	String savepath = "report"; // 基础路径
  	String curdate = DateTime.getDate("yyyy");
  	String filepath = rootpath + "/" + savepath+ "/" + curdate;
//		 检查文件夹是否存在,如果不存在,新建一个
  	  if (!FileUtil.isExistDir(filepath)) {
	        	FileUtil.creatDir(filepath);
	        }
      String filename = ""; 
  	  if(obj.equals("usertype,")){
  		filename = "1"; 
  	  }else if(obj.equals("sex,")){
  		filename = "2"; 
  	  }else if(obj.equals("xueduan,")){
  		filename = "3"; 
  	  }else if(obj.equals("usertype,sex,")){
  		filename = "4"; 
  	  }else if(obj.equals("usertype,xueduan,")){
  		filename = "5"; 
  	  }else if(obj.equals("sex,xueduan,")){
  		filename = "6"; 
  	  }else if(obj.equals("usertype,sex,xueduan,")){
  		filename = "7"; 
  	  }
      String url="";
     try {  
         DefaultPieDataset data = dataType;  
         // 生成普通饼状图 
         PiePlot plot = new PiePlot(data);  
//         plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}",new DecimalFormat("0.0"),new DecimalFormat("0.0%")));
         plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}, 人数:{1}, 百分比:{2}",new DecimalFormat("0.0"),new DecimalFormat("0.0%")));
         JFreeChart chart = new JFreeChart(  
                 "用户信息统计",            // 图形标题  
                 JFreeChart.DEFAULT_TITLE_FONT, // 标题字体  
                 plot,                          // 图标标题对象  
                 true                           // 是否显示图例  
         );  
         // 设置整个图片的背景色  
         chart.setBackgroundPaint(Color.PINK);  
         // 设置图片有边框  
         chart.setBorderVisible(true);  
         // 配置字体  
         Font kfont = new Font("宋体", Font.PLAIN, 12);    // 底部  
         Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题  
         // 图片标题  
         chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));  
         // 底部  
         chart.getLegend().setItemFont(kfont);  
         FileOutputStream fos_jpg = null; 
         //生成图片
         try { 
        	 fos_jpg=new FileOutputStream(filepath + "/" + filename+".jpg"); 
//        	 ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f,  
//                     chart, 500, 300, null);  
        	 ChartUtilities.writeChartAsJPEG(fos_jpg,1.0f,chart,980,380,null); 
        	 fos_jpg.close(); 
        	 
        	 } catch (Exception e) { 
        		 e.printStackTrace();
        	 } 

          url="/upload/" + savepath+ "/" + curdate+"/"+filename+".jpg";//生成图片的路径和名称
     
     } catch (Exception e) {  
         e.printStackTrace();  
     }
     return url; 
}  

/**
 * 导出excel
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-9-28 上午11:29:15 
* @lastModified  2016-9-28 上午11:29:15 
* @version  1.0 
* @param actionMapping
* @param actionForm
* @param request
* @param response
* @param list
* @return
 */
public void toExcel(HttpServletRequest request,
		HttpServletResponse response,List list,String obj) {
    String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
  	String savepath = "report"; // 基础路径
  	String curdate = DateTime.getDate("yyyy");
  	String filepath = rootpath + "/" + savepath+ "/" + curdate;
		// 检查文件夹是否存在,如果不存在,新建一个
  	  if (!FileUtil.isExistDir(filepath)) {
	        	FileUtil.creatDir(filepath);
	        }
	 String excelname ="用户信息统计表.xls";
     String path =filepath + "/" + excelname;
   //导入到excel
     try {
			File file = new File(path);
			WritableWorkbook book = Workbook.createWorkbook(file);
			WritableSheet sheet = book.createSheet("sheet_name", 0);
			/**
			 * ***********Excel格式***********************************************
			 */
			// 设置页边距，注意参数0.5d是英寸，设置完后，打开Excel时，就会变成1.3厘米
			sheet.getSettings().setBottomMargin(0.5d);
			sheet.getSettings().setTopMargin(0.5d);
			sheet.getSettings().setLeftMargin(0.5d);
			sheet.getSettings().setRightMargin(0.5d);

			// 设置页面方向，参数中的两个0.5d分别是页眉和页脚的边距
			// 横向：
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,
					PaperSize.A3, 0.5d, 0.5d);
			// 纵向：
			sheet.setPageSetup(PageOrientation.LANDSCAPE.LANDSCAPE,
					PaperSize.A3, 0.5d, 0.5d);

			// 设置页面的缩放比例，参数为百分比
			sheet.getSettings().setScaleFactor(90);
			// 设置页码
			sheet.setFooter("", "&P", "");
			/**
			 * ***********Excel格式***********************************************
			 */
			  if(obj.equals("usertype,")){
				  	Label label = new Label(0, 0, "用户类型"); // (列，行，数据)
					sheet.addCell(label);
					label = new Label(1, 0, "人数");
					sheet.addCell(label);
			  	  }else if(obj.equals("sex,")){
			  		Label label = new Label(0, 0, "用户性别"); // (列，行，数据)
					sheet.addCell(label);
					label = new Label(1, 0, "人数");
					sheet.addCell(label);
			  	  }else if(obj.equals("xueduan,")){
			  		Label label = new Label(0, 0, "用户学段"); // (列，行，数据)
					sheet.addCell(label);
					label = new Label(1, 0, "人数");
					sheet.addCell(label); 
			  	  }else if(obj.equals("usertype,sex,")){
			  		Label label = new Label(0, 0, "用户类型"); // (列，行，数据)
					sheet.addCell(label);
					label = new Label(1, 0, "用户性别");
					sheet.addCell(label);
					label = new Label(2, 0, "人数");
					sheet.addCell(label); 
			  	  }else if(obj.equals("usertype,xueduan,")){
			  		Label label = new Label(0, 0, "用户类型"); // (列，行，数据)
					sheet.addCell(label);
					label = new Label(1, 0, "用户学段");
					sheet.addCell(label);
					label = new Label(2, 0, "人数");
					sheet.addCell(label);  
			  	  }else if(obj.equals("sex,xueduan,")){
			  		Label label = new Label(0, 0, "用户性别"); // (列，行，数据)
					sheet.addCell(label);
					label = new Label(1, 0, "用户学段");
					sheet.addCell(label);
					label = new Label(2, 0, "人数");
					sheet.addCell(label);  
			  	  }else if(obj.equals("usertype,sex,xueduan,")){
			  		Label label = new Label(0, 0, "用户类型"); // (列，行，数据)
					sheet.addCell(label);
					label = new Label(1, 0, "用户性别");
					sheet.addCell(label);
					label = new Label(2, 0, "用户学段");
					sheet.addCell(label);
					label = new Label(3, 0, "人数");
					sheet.addCell(label);  
			  	  }
			
			
			Label label2 = null;
			for(int i=1;i<=list.size();i++){
	  		    		Object[] lst =  (Object[]) list.get(i-1);
	  		    		Object a = lst[0];//类型
	  		    		Object b = lst[1];//性别
	  		    		Object c = lst[2];//学段
	  		    		Object d = lst[3];//人数
	  		    		if(obj.contains("usertype")){
	  		    			if(a.toString().equals("1")){
	  		    				a="老师";
	  		    			}else if(a.toString().equals("2")){
	  		    				a="学生";
	  		    			}else if(a.toString().equals("3")){
	  		    				a="家长";
	  		    			}
	  		    		}
	  		    		if(obj.contains("sex")){
	  		    			//性别(0男，1女)
	  		    			if(b.toString().equals("0")){
	  		    				b="男";
	  		    			}else if(b.toString().equals("1")){
	  		    				b="女";
	  		    			}
	  		    		}
	  		    		if(obj.contains("xueduan")){
	  		    			//学段：1小学，2初中，3高中
	  		    			if(c.toString().equals("2")){
	  		    				c="初中";
	  		    			}else if(c.toString().equals("3")){
	  		    				c="高中";
	  		    			}
	  		    		}
				
				  if(obj.equals("usertype,")){
						label2 = new Label(0, i, a.toString()); 
						sheet.addCell(label2);
					  	label2 = new Label(1, i, String.valueOf(d));
						sheet.addCell(label2);
				  	  }else if(obj.equals("sex,")){
				  		label2 = new Label(0, i, b.toString()); 
						sheet.addCell(label2);
				  		label2 = new Label(1, i, String.valueOf(d));
						sheet.addCell(label2);
				  	  }else if(obj.equals("xueduan,")){
				  		label2 = new Label(0, i, c.toString()); 
						sheet.addCell(label2);
				  		label2 = new Label(1, i, String.valueOf(d));
						sheet.addCell(label2); 
				  	  }else if(obj.equals("usertype,sex,")){
				  		label2 = new Label(0, i, a.toString()); 
						sheet.addCell(label2);
						label2 = new Label(1, i, b.toString()); 
						sheet.addCell(label2);
				  		label2 = new Label(2, i, String.valueOf(d));
						sheet.addCell(label2); 
				  	  }else if(obj.equals("usertype,xueduan,")){
				  		label2 = new Label(0, i, a.toString()); 
						sheet.addCell(label2);
						label2 = new Label(1, i, c.toString()); 
						sheet.addCell(label2);
				  		label2 = new Label(2, i, String.valueOf(d));
						sheet.addCell(label2); 
				  	  }else if(obj.equals("sex,xueduan,")){
				  		label2 = new Label(0, i, b.toString()); 
						sheet.addCell(label2);
						label2 = new Label(1, i, c.toString()); 
						sheet.addCell(label2);
				  		label2 = new Label(2, i, String.valueOf(d));
						sheet.addCell(label2); 
				  	  }else if(obj.equals("usertype,sex,xueduan,")){
				  		label2 = new Label(0, i, a.toString()); 
						sheet.addCell(label2);
						label2 = new Label(1, i, b.toString()); 
						sheet.addCell(label2);
						label2 = new Label(2, i, c.toString()); 
						sheet.addCell(label2);
				  		label2 = new Label(3, i, String.valueOf(d));
						sheet.addCell(label2);  
				  	  }
				
			}
		
			book.setProtected(true);
			book.write();
			book.close();
			
			
			InputStream is = new FileInputStream(filepath + "\\" + excelname);
			OutputStream os = response.getOutputStream();// 取得输出流
			response.reset();// 清空输出流
			response.setContentType("text/html; charset=utf-8");// text/html
			// application/octet-stream
			response.setContentType("bin");
			response.setHeader("Content-disposition",
					"attachment; filename="
							+ new String(excelname.getBytes("gb2312"), "ISO8859-1"));// gb2312
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = is.read(buffer)) > 0) {
				os.write(buffer, 0, byteread);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			System.out.println(e);
		}
}

/**
 * 用户注册信息生成散点折线图
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-9-30 上午9:50:49 
* @lastModified  2016-9-30 上午9:50:49 
* @version  1.0 
* @param request
* @param response
* @throws ServletException
* @throws IOException
 */
protected String reportLine(HttpServletRequest request,  
        HttpServletResponse response,List list) throws ServletException, IOException {  
    response.setContentType("text/html");  
    // 在Mysql中使用 select  
    // year(accessdate),month(accessdate),day(accessdate),count(*)  
    // 其中accessdate 是一个date类型的时间  
    // 时间序列对象集合  
    TimeSeriesCollection chartTime = new TimeSeriesCollection();  
    // 时间序列对象，第1个参数表示时间序列的名字，第2个参数是时间类型，这里为天  
    // 该对象用于保存前count天每天的访问次数  
    TimeSeries timeSeries = new TimeSeries("注册人数", Day.class);  
    // 为了演示，直接拼装数据  
    // Day的组装格式是day-month-year 访问次数  
    for(int i=1;i<=list.size();i++){
  		Object[] lst =  (Object[]) list.get(i-1);
  		Object a = lst[0];//用户id
  		Object b = lst[1];//日期
  		Object c = lst[2];//人数
  		Date date =null;
  		try  
  		{  
  		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
  		     date = sdf.parse(b.toString());  
  		}  
  		catch (Exception e)  
  		{  
  		    e.printStackTrace();
  		}  
  		RegularTimePeriod rp = new Day(date);
  		timeSeries.add(rp, Double.parseDouble(c.toString())); 
    }
//    timeSeries.add(new Day(1, 1, 2010), 50);  
//    timeSeries.add(new Day(2, 1, 2010), 47);  
//    timeSeries.add(new Day(3, 1, 2010), 82);  
//    timeSeries.add(new Day(4, 1, 2010), 95);  
//    timeSeries.add(new Day(5, 1, 2010), 104);  
//    timeSeries.add(new Day(6, 1, 2010), 425);  
    chartTime.addSeries(timeSeries);  
    XYDataset date = chartTime;  
    String url="";
    try {  
        // 使用ChartFactory来创建时间序列的图表对象  
        JFreeChart chart = ChartFactory.createTimeSeriesChart(  
                "用户注册统计", // 图形标题  
                "日期", // X轴说明  
                "人数", // Y轴说明  
                date, // 数据  
                true, // 是否创建图例  
                true, // 是否生成Tooltips  
                false // 是否生产URL链接  
        );  
        // 设置整个图片的背景色  
        chart.setBackgroundPaint(Color.PINK);  
        // 设置图片有边框  
        chart.setBorderVisible(true);  
        // 获得图表区域对象  
        XYPlot xyPlot = (XYPlot) chart.getPlot();  
        // 设置报表区域的背景色  
        xyPlot.setBackgroundPaint(Color.lightGray);  
        // 设置横 纵坐标网格颜色  
        xyPlot.setDomainGridlinePaint(Color.GREEN);  
        xyPlot.setRangeGridlinePaint(Color.GREEN);  
        // 设置横、纵坐标交叉线是否显示  
        xyPlot.setDomainCrosshairVisible(true);  
        xyPlot.setRangeCrosshairVisible(true);  
        // 获得数据点（X,Y）的render，负责描绘数据点  
        XYItemRenderer xyItemRenderer = xyPlot.getRenderer();  
        if (xyItemRenderer instanceof XYLineAndShapeRenderer) {  
            XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) xyItemRenderer;  
            xyLineAndShapeRenderer.setShapesVisible(true); // 数据点可见  
            xyLineAndShapeRenderer.setShapesFilled(true); // 数据点是实心点  
            xyLineAndShapeRenderer.setSeriesFillPaint(0, Color.RED); // 数据点填充为蓝色  
            xyLineAndShapeRenderer.setUseFillPaint(true);// 将设置好的属性应用到render上  
//            xyLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//            xyLineAndShapeRenderer.setBaseItemLabelsVisible(true);//  在每个散点上面显示具体的数据
        }  
        // 配置以下内容方可解决乱码问题  
        // 配置字体  
        Font xfont = new Font("宋体", Font.PLAIN, 12);    // X轴  
        Font yfont = new Font("宋体", Font.PLAIN, 12);    // Y轴  
        Font kfont = new Font("宋体", Font.PLAIN, 12);    // 底部  
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题  
        // 图片标题  
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));  
        // 底部  
        chart.getLegend().setItemFont(kfont);             
        // X 轴  
        ValueAxis domainAxis = xyPlot.getDomainAxis();  
        domainAxis.setLabelFont(xfont);// 轴标题  
        domainAxis.setTickLabelFont(xfont);// 轴数值  
        domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色  
        // Y 轴  
        ValueAxis rangeAxis = xyPlot.getRangeAxis();  
        rangeAxis.setLabelFont(yfont);  
        rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色  
        rangeAxis.setTickLabelFont(yfont);  
        // 定义坐标轴上日期显示的格式  
        DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();  
        // 设置日期格式  
        dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));  
        // 向客户端输出生成的图片  
        //设置路径
        String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
      	String savepath = "report"; // 基础路径
      	String curdate = DateTime.getDate("yyyy");
      	String filepath = rootpath + "/" + savepath+ "/" + curdate;
//    		 检查文件夹是否存在,如果不存在,新建一个
      	  if (!FileUtil.isExistDir(filepath)) {
    	        	FileUtil.creatDir(filepath);
    	        }
         String filename = "8"; 
        FileOutputStream fos_jpg = null; 
        fos_jpg=new FileOutputStream(filepath + "/" + filename+".jpg"); 
   	 ChartUtilities.writeChartAsJPEG(fos_jpg,1.0f,chart,980,380,null); 
   	 fos_jpg.close(); 
//        ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f,  
//                chart, 500, 300, null);  
   	 
   	 url="/upload/" + savepath+ "/" + curdate+"/"+filename+".jpg";//生成图片的路径和名称
    } catch (Exception e) {  
        e.printStackTrace();  
    }  
    return url;
}
/**
 * 用户使用信息生成散点折线图
* @author  liud 
* @Description : TODO 
* @CreateDate  2016-9-30 上午9:50:49 
* @lastModified  2016-9-30 上午9:50:49 
* @version  1.0 
* @param request
* @param response
* @throws ServletException
* @throws IOException
 */
protected String reportLogLine(HttpServletRequest request,  
        HttpServletResponse response,List list) throws ServletException, IOException {  
    response.setContentType("text/html");  
    // 在Mysql中使用 select  
    // year(accessdate),month(accessdate),day(accessdate),count(*)  
    // 其中accessdate 是一个date类型的时间  
    // 时间序列对象集合  
    TimeSeriesCollection chartTime = new TimeSeriesCollection();  
    // 时间序列对象，第1个参数表示时间序列的名字，第2个参数是时间类型，这里为天  
    // 该对象用于保存前count天每天的访问次数  
    TimeSeries timeSeries = new TimeSeries("使用人数", Day.class);  
    // 为了演示，直接拼装数据  
    // Day的组装格式是day-month-year 访问次数  
    for(int i=1;i<=list.size();i++){
  		Object[] lst =  (Object[]) list.get(i-1);
//  		Object a = lst[0];//用户id
  		Object b = lst[0];//日期
  		Object c = lst[1];//人数
  		Date date =null;
  		try  
  		{  
  		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
  		     date = sdf.parse(b.toString());  
  		}  
  		catch (Exception e)  
  		{  
  		    e.printStackTrace();
  		}  
  		RegularTimePeriod rp = new Day(date);
  		timeSeries.add(rp, Double.parseDouble(c.toString())); 
    } 
    chartTime.addSeries(timeSeries);  
    XYDataset date = chartTime;  
    String url="";
    try {  
        // 使用ChartFactory来创建时间序列的图表对象  
        JFreeChart chart = ChartFactory.createTimeSeriesChart(  
                "用户使用统计", // 图形标题  
                "日期", // X轴说明  
                "使用人数", // Y轴说明  
                date, // 数据  
                true, // 是否创建图例  
                true, // 是否生成Tooltips  
                false // 是否生产URL链接  
        );  
        // 设置整个图片的背景色  
        chart.setBackgroundPaint(Color.PINK);  
        // 设置图片有边框  
        chart.setBorderVisible(true);  
        // 获得图表区域对象  
        XYPlot xyPlot = (XYPlot) chart.getPlot();  
        // 设置报表区域的背景色  
        xyPlot.setBackgroundPaint(Color.lightGray);  
        // 设置横 纵坐标网格颜色  
        xyPlot.setDomainGridlinePaint(Color.GREEN);  
        xyPlot.setRangeGridlinePaint(Color.GREEN);  
        // 设置横、纵坐标交叉线是否显示  
        xyPlot.setDomainCrosshairVisible(true);  
        xyPlot.setRangeCrosshairVisible(true);  
        // 获得数据点（X,Y）的render，负责描绘数据点  
        XYItemRenderer xyItemRenderer = xyPlot.getRenderer();  
        if (xyItemRenderer instanceof XYLineAndShapeRenderer) {  
            XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) xyItemRenderer;  
            xyLineAndShapeRenderer.setShapesVisible(true); // 数据点可见  
            xyLineAndShapeRenderer.setShapesFilled(true); // 数据点是实心点  
            xyLineAndShapeRenderer.setSeriesFillPaint(0, Color.RED); // 数据点填充为蓝色  
            xyLineAndShapeRenderer.setUseFillPaint(true);// 将设置好的属性应用到render上  
//            xyLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//            xyLineAndShapeRenderer.setBaseItemLabelsVisible(true);//  在每个散点上面显示具体的数据
        }  
        // 配置以下内容方可解决乱码问题  
        // 配置字体  
        Font xfont = new Font("宋体", Font.PLAIN, 12);    // X轴  
        Font yfont = new Font("宋体", Font.PLAIN, 12);    // Y轴  
        Font kfont = new Font("宋体", Font.PLAIN, 12);    // 底部  
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题  
        // 图片标题  
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));  
        // 底部  
        chart.getLegend().setItemFont(kfont);             
        // X 轴  
        ValueAxis domainAxis = xyPlot.getDomainAxis();  
        domainAxis.setLabelFont(xfont);// 轴标题  
        domainAxis.setTickLabelFont(xfont);// 轴数值  
        domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色  
        // Y 轴  
        ValueAxis rangeAxis = xyPlot.getRangeAxis();  
        rangeAxis.setLabelFont(yfont);  
        rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色  
        rangeAxis.setTickLabelFont(yfont);  
        // 定义坐标轴上日期显示的格式  
        DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();  
        // 设置日期格式  
        dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));  
        // 向客户端输出生成的图片  
        //设置路径
        String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
      	String savepath = "report"; // 基础路径
      	String curdate = DateTime.getDate("yyyy");
      	String filepath = rootpath + "/" + savepath+ "/" + curdate;
//    		 检查文件夹是否存在,如果不存在,新建一个
      	  if (!FileUtil.isExistDir(filepath)) {
    	        	FileUtil.creatDir(filepath);
    	        }
         String filename = "9"; 
        FileOutputStream fos_jpg = null; 
        fos_jpg=new FileOutputStream(filepath + "/" + filename+".jpg"); 
   	 ChartUtilities.writeChartAsJPEG(fos_jpg,1.0f,chart,980,380,null); 
   	 fos_jpg.close(); 
//        ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f,  
//                chart, 500, 300, null);  
   	 
   	 url="/upload/" + savepath+ "/" + curdate+"/"+filename+".jpg";//生成图片的路径和名称
    } catch (Exception e) {  
        e.printStackTrace();  
    }  
    return url;
}
/**
 * 
  * 方法描述：用户交易统计散点折线图
  * @param: 
  * @return: 
  * @version: 1.0
  * @author: 刘冬
  * @version: 2016-11-23 下午6:06:27
 */
protected String reportMoneyLine(HttpServletRequest request,  
        HttpServletResponse response,List list) throws ServletException, IOException {  
    response.setContentType("text/html");  
    // 在Mysql中使用 select  
    // year(accessdate),month(accessdate),day(accessdate),count(*)  
    // 其中accessdate 是一个date类型的时间  
    // 时间序列对象集合  
    TimeSeriesCollection chartTime = new TimeSeriesCollection();  
    // 时间序列对象，第1个参数表示时间序列的名字，第2个参数是时间类型，这里为天  
    // 该对象用于保存前count天每天的访问次数  
    TimeSeries timeSeries = new TimeSeries("交易人数", Day.class);  
    // 为了演示，直接拼装数据  
    // Day的组装格式是day-month-year 访问次数  
    for(int i=1;i<=list.size();i++){
  		Object[] lst =  (Object[]) list.get(i-1);
//  		Object a = lst[0];//用户id
  		Object b = lst[0];//日期
  		Object c = lst[1];//人数
  		Date date =null;
  		try  
  		{  
  		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
  		     date = sdf.parse(b.toString());  
  		}  
  		catch (Exception e)  
  		{  
  		    e.printStackTrace();
  		}  
  		RegularTimePeriod rp = new Day(date);
  		timeSeries.add(rp, Double.parseDouble(c.toString())); 
    } 
    chartTime.addSeries(timeSeries);  
    XYDataset date = chartTime;  
    String url="";
    try {  
        // 使用ChartFactory来创建时间序列的图表对象  
        JFreeChart chart = ChartFactory.createTimeSeriesChart(  
                "用户交易统计", // 图形标题  
                "日期", // X轴说明  
                "交易人数", // Y轴说明  
                date, // 数据  
                true, // 是否创建图例  
                true, // 是否生成Tooltips  
                false // 是否生产URL链接  
        );  
        // 设置整个图片的背景色  
        chart.setBackgroundPaint(Color.PINK);  
        // 设置图片有边框  
        chart.setBorderVisible(true);  
        // 获得图表区域对象  
        XYPlot xyPlot = (XYPlot) chart.getPlot();  
        // 设置报表区域的背景色  
        xyPlot.setBackgroundPaint(Color.lightGray);  
        // 设置横 纵坐标网格颜色  
        xyPlot.setDomainGridlinePaint(Color.GREEN);  
        xyPlot.setRangeGridlinePaint(Color.GREEN);  
        // 设置横、纵坐标交叉线是否显示  
        xyPlot.setDomainCrosshairVisible(true);  
        xyPlot.setRangeCrosshairVisible(true);  
        // 获得数据点（X,Y）的render，负责描绘数据点  
        XYItemRenderer xyItemRenderer = xyPlot.getRenderer();  
        if (xyItemRenderer instanceof XYLineAndShapeRenderer) {  
            XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) xyItemRenderer;  
            xyLineAndShapeRenderer.setShapesVisible(true); // 数据点可见  
            xyLineAndShapeRenderer.setShapesFilled(true); // 数据点是实心点  
            xyLineAndShapeRenderer.setSeriesFillPaint(0, Color.RED); // 数据点填充为蓝色  
            xyLineAndShapeRenderer.setUseFillPaint(true);// 将设置好的属性应用到render上  
//            xyLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//            xyLineAndShapeRenderer.setBaseItemLabelsVisible(true);//  在每个散点上面显示具体的数据
        }  
        // 配置以下内容方可解决乱码问题  
        // 配置字体  
        Font xfont = new Font("宋体", Font.PLAIN, 12);    // X轴  
        Font yfont = new Font("宋体", Font.PLAIN, 12);    // Y轴  
        Font kfont = new Font("宋体", Font.PLAIN, 12);    // 底部  
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题  
        // 图片标题  
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));  
        // 底部  
        chart.getLegend().setItemFont(kfont);             
        // X 轴  
        ValueAxis domainAxis = xyPlot.getDomainAxis();  
        domainAxis.setLabelFont(xfont);// 轴标题  
        domainAxis.setTickLabelFont(xfont);// 轴数值  
        domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色  
        // Y 轴  
        ValueAxis rangeAxis = xyPlot.getRangeAxis();  
        rangeAxis.setLabelFont(yfont);  
        rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色  
        rangeAxis.setTickLabelFont(yfont);  
        // 定义坐标轴上日期显示的格式  
        DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();  
        // 设置日期格式  
        dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));  
        // 向客户端输出生成的图片  
        //设置路径
        String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
      	String savepath = "report"; // 基础路径
      	String curdate = DateTime.getDate("yyyy");
      	String filepath = rootpath + "/" + savepath+ "/" + curdate;
//    		 检查文件夹是否存在,如果不存在,新建一个
      	  if (!FileUtil.isExistDir(filepath)) {
    	        	FileUtil.creatDir(filepath);
    	        }
         String filename = "10"; 
        FileOutputStream fos_jpg = null; 
        fos_jpg=new FileOutputStream(filepath + "/" + filename+".jpg"); 
   	 ChartUtilities.writeChartAsJPEG(fos_jpg,1.0f,chart,980,380,null); 
   	 fos_jpg.close(); 
//        ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f,  
//                chart, 500, 300, null);  
   	 
   	 url="/upload/" + savepath+ "/" + curdate+"/"+filename+".jpg";//生成图片的路径和名称
    } catch (Exception e) {  
        e.printStackTrace();  
    }  
    return url;
}
/**
 * 
  * 方法描述：用户充值统计散点折线图
  * @param: 
  * @return: 
  * @version: 1.0
  * @author: 刘冬
  * @version: 2016-11-24 上午9:26:29
 */
protected String reportPayLine(HttpServletRequest request,  
        HttpServletResponse response,List list) throws ServletException, IOException {  
    response.setContentType("text/html");  
    // 在Mysql中使用 select  
    // year(accessdate),month(accessdate),day(accessdate),count(*)  
    // 其中accessdate 是一个date类型的时间  
    // 时间序列对象集合  
    TimeSeriesCollection chartTime = new TimeSeriesCollection();  
    // 时间序列对象，第1个参数表示时间序列的名字，第2个参数是时间类型，这里为天  
    // 该对象用于保存前count天每天的访问次数 
    TimeSeries timeSeries = new TimeSeries("充值人数", Day.class);  
    // 为了演示，直接拼装数据  
    // Day的组装格式是day-month-year 访问次数  
    for(int i=1;i<=list.size();i++){
  		Object[] lst =  (Object[]) list.get(i-1);
//  		Object a = lst[0];//用户id
  		Object b = lst[0];//日期
  		Object c = lst[1];//人数
  		Date date =null;
  		try  
  		{  
  		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
  		     date = sdf.parse(b.toString());  
  		}  
  		catch (Exception e)  
  		{  
  		    e.printStackTrace();
  		}  
  		RegularTimePeriod rp = new Day(date);
  		timeSeries.add(rp, Double.parseDouble(c.toString())); 
    } 
    chartTime.addSeries(timeSeries);  
    XYDataset date = chartTime;  
    String url="";
    try {  
        // 使用ChartFactory来创建时间序列的图表对象  
        JFreeChart chart = ChartFactory.createTimeSeriesChart(  
                "用户充值统计", // 图形标题  
                "日期", // X轴说明  
                "充值人数", // Y轴说明  
                date, // 数据  
                true, // 是否创建图例  
                true, // 是否生成Tooltips  
                false // 是否生产URL链接  
        );  
        // 设置整个图片的背景色  
        chart.setBackgroundPaint(Color.PINK);  
        // 设置图片有边框  
        chart.setBorderVisible(true);  
        // 获得图表区域对象  
        XYPlot xyPlot = (XYPlot) chart.getPlot();  
        // 设置报表区域的背景色  
        xyPlot.setBackgroundPaint(Color.lightGray);  
        // 设置横 纵坐标网格颜色  
        xyPlot.setDomainGridlinePaint(Color.GREEN);  
        xyPlot.setRangeGridlinePaint(Color.GREEN);  
        // 设置横、纵坐标交叉线是否显示  
        xyPlot.setDomainCrosshairVisible(true);  
        xyPlot.setRangeCrosshairVisible(true);  
        // 获得数据点（X,Y）的render，负责描绘数据点  
        XYItemRenderer xyItemRenderer = xyPlot.getRenderer();  
        if (xyItemRenderer instanceof XYLineAndShapeRenderer) {  
            XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) xyItemRenderer;  
            xyLineAndShapeRenderer.setShapesVisible(true); // 数据点可见  
            xyLineAndShapeRenderer.setShapesFilled(true); // 数据点是实心点  
            xyLineAndShapeRenderer.setSeriesFillPaint(0, Color.RED); // 数据点填充为蓝色  
            xyLineAndShapeRenderer.setUseFillPaint(true);// 将设置好的属性应用到render上  
//            xyLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//            xyLineAndShapeRenderer.setBaseItemLabelsVisible(true);//  在每个散点上面显示具体的数据
        }  
        // 配置以下内容方可解决乱码问题  
        // 配置字体  
        Font xfont = new Font("宋体", Font.PLAIN, 12);    // X轴  
        Font yfont = new Font("宋体", Font.PLAIN, 12);    // Y轴  
        Font kfont = new Font("宋体", Font.PLAIN, 12);    // 底部  
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题  
        // 图片标题  
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));  
        // 底部  
        chart.getLegend().setItemFont(kfont);             
        // X 轴  
        ValueAxis domainAxis = xyPlot.getDomainAxis();  
        domainAxis.setLabelFont(xfont);// 轴标题  
        domainAxis.setTickLabelFont(xfont);// 轴数值  
        domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色  
        // Y 轴  
        ValueAxis rangeAxis = xyPlot.getRangeAxis();  
        rangeAxis.setLabelFont(yfont);  
        rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色  
        rangeAxis.setTickLabelFont(yfont);  
        // 定义坐标轴上日期显示的格式  
        DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();  
        // 设置日期格式  
        dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));  
        // 向客户端输出生成的图片  
        //设置路径
        String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
      	String savepath = "report"; // 基础路径
      	String curdate = DateTime.getDate("yyyy");
      	String filepath = rootpath + "/" + savepath+ "/" + curdate;
//    		 检查文件夹是否存在,如果不存在,新建一个
      	  if (!FileUtil.isExistDir(filepath)) {
    	        	FileUtil.creatDir(filepath);
    	        }
         String filename = "11"; 
        FileOutputStream fos_jpg = null; 
        fos_jpg=new FileOutputStream(filepath + "/" + filename+".jpg"); 
   	 ChartUtilities.writeChartAsJPEG(fos_jpg,1.0f,chart,980,380,null); 
   	 fos_jpg.close(); 
//        ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f,  
//                chart, 500, 300, null);  
   	 
   	 url="/upload/" + savepath+ "/" + curdate+"/"+filename+".jpg";//生成图片的路径和名称
    } catch (Exception e) {  
        e.printStackTrace();  
    }  
    return url;
}
/**
 * 
  * 方法描述：用户充值统计散点折线图,交易总额/天
  * @param: 
  * @return: 
  * @version: 1.0
  * @author: 刘冬
  * @version: 2016-11-24 上午10:11:54
 */
protected String reportPayLineOfchangemoney(HttpServletRequest request,  
        HttpServletResponse response,List list) throws ServletException, IOException {  
    response.setContentType("text/html");  
    // 在Mysql中使用 select  
    // year(accessdate),month(accessdate),day(accessdate),count(*)  
    // 其中accessdate 是一个date类型的时间  
    // 时间序列对象集合  
    TimeSeriesCollection chartTime = new TimeSeriesCollection();  
    // 时间序列对象，第1个参数表示时间序列的名字，第2个参数是时间类型，这里为天  
    // 该对象用于保存前count天每天的访问次数 
    TimeSeries timeSeries = new TimeSeries("充值总额", Day.class);  
    // 为了演示，直接拼装数据  
    // Day的组装格式是day-month-year 访问次数  
    for(int i=1;i<=list.size();i++){
  		Object[] lst =  (Object[]) list.get(i-1);
//  		Object a = lst[0];//用户id
  		Object b = lst[0];//日期
  		Object c = lst[1];//人数
  		Date date =null;
  		try  
  		{  
  		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
  		     date = sdf.parse(b.toString());  
  		}  
  		catch (Exception e)  
  		{  
  		    e.printStackTrace();
  		}  
  		RegularTimePeriod rp = new Day(date);
  		timeSeries.add(rp, Double.parseDouble(c.toString())); 
    } 
    chartTime.addSeries(timeSeries);  
    XYDataset date = chartTime;  
    String url="";
    try {  
        // 使用ChartFactory来创建时间序列的图表对象  
        JFreeChart chart = ChartFactory.createTimeSeriesChart(  
                "用户充值统计", // 图形标题  
                "日期", // X轴说明  
                "充值总额", // Y轴说明  
                date, // 数据  
                true, // 是否创建图例  
                true, // 是否生成Tooltips  
                false // 是否生产URL链接  
        );  
        // 设置整个图片的背景色  
        chart.setBackgroundPaint(Color.PINK);  
        // 设置图片有边框  
        chart.setBorderVisible(true);  
        // 获得图表区域对象  
        XYPlot xyPlot = (XYPlot) chart.getPlot();  
        // 设置报表区域的背景色  
        xyPlot.setBackgroundPaint(Color.lightGray);  
        // 设置横 纵坐标网格颜色  
        xyPlot.setDomainGridlinePaint(Color.GREEN);  
        xyPlot.setRangeGridlinePaint(Color.GREEN);  
        // 设置横、纵坐标交叉线是否显示  
        xyPlot.setDomainCrosshairVisible(true);  
        xyPlot.setRangeCrosshairVisible(true);  
        // 获得数据点（X,Y）的render，负责描绘数据点  
        XYItemRenderer xyItemRenderer = xyPlot.getRenderer();  
        if (xyItemRenderer instanceof XYLineAndShapeRenderer) {  
            XYLineAndShapeRenderer xyLineAndShapeRenderer = (XYLineAndShapeRenderer) xyItemRenderer;  
            xyLineAndShapeRenderer.setShapesVisible(true); // 数据点可见  
            xyLineAndShapeRenderer.setShapesFilled(true); // 数据点是实心点  
            xyLineAndShapeRenderer.setSeriesFillPaint(0, Color.RED); // 数据点填充为蓝色  
            xyLineAndShapeRenderer.setUseFillPaint(true);// 将设置好的属性应用到render上  
//            xyLineAndShapeRenderer.setBaseItemLabelGenerator(new StandardXYItemLabelGenerator());
//            xyLineAndShapeRenderer.setBaseItemLabelsVisible(true);//  在每个散点上面显示具体的数据
        }  
        // 配置以下内容方可解决乱码问题  
        // 配置字体  
        Font xfont = new Font("宋体", Font.PLAIN, 12);    // X轴  
        Font yfont = new Font("宋体", Font.PLAIN, 12);    // Y轴  
        Font kfont = new Font("宋体", Font.PLAIN, 12);    // 底部  
        Font titleFont = new Font("宋体", Font.BOLD, 25); // 图片标题  
        // 图片标题  
        chart.setTitle(new TextTitle(chart.getTitle().getText(), titleFont));  
        // 底部  
        chart.getLegend().setItemFont(kfont);             
        // X 轴  
        ValueAxis domainAxis = xyPlot.getDomainAxis();  
        domainAxis.setLabelFont(xfont);// 轴标题  
        domainAxis.setTickLabelFont(xfont);// 轴数值  
        domainAxis.setTickLabelPaint(Color.BLUE); // 字体颜色  
        // Y 轴  
        ValueAxis rangeAxis = xyPlot.getRangeAxis();  
        rangeAxis.setLabelFont(yfont);  
        rangeAxis.setLabelPaint(Color.BLUE); // 字体颜色  
        rangeAxis.setTickLabelFont(yfont);  
        // 定义坐标轴上日期显示的格式  
        DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();  
        // 设置日期格式  
        dateAxis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));  
        // 向客户端输出生成的图片  
        //设置路径
        String rootpath = request.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
      	String savepath = "report"; // 基础路径
      	String curdate = DateTime.getDate("yyyy");
      	String filepath = rootpath + "/" + savepath+ "/" + curdate;
//    		 检查文件夹是否存在,如果不存在,新建一个
      	  if (!FileUtil.isExistDir(filepath)) {
    	        	FileUtil.creatDir(filepath);
    	        }
         String filename = "12"; 
        FileOutputStream fos_jpg = null; 
        fos_jpg=new FileOutputStream(filepath + "/" + filename+".jpg"); 
   	 ChartUtilities.writeChartAsJPEG(fos_jpg,1.0f,chart,980,380,null); 
   	 fos_jpg.close(); 
//        ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 1.0f,  
//                chart, 500, 300, null);  
   	 
   	 url="/upload/" + savepath+ "/" + curdate+"/"+filename+".jpg";//生成图片的路径和名称
    } catch (Exception e) {  
        e.printStackTrace();  
    }  
    return url;
}
/**
 * 下载图片
 * @param mapping
 * @param actionForm
 * @param request
 * @param response
 * @return
 */
public ActionForward downpicture(ActionMapping mapping, ActionForm actionForm,
		HttpServletRequest request, HttpServletResponse response) {
	String lineurl = Encode.nullToBlank(request.getParameter("lineurl"));
	String name = Encode.nullToBlank(request.getParameter("name"));
	if (!"".equals(lineurl)) {
		String realPath = request.getSession().getServletContext().getRealPath("/");
		String sourcefile=realPath+lineurl;
		if(name.equals("1")){
			name="用户注册统计散点折线图";
		}else if(name.equals("2")){
			name="用户使用统计散点折线图";
		}else if(name.equals("3")){
			name="用户充值统计散点折线图";
		}else if(name.equals("4")){
			name="用户交易统计散点折线图";
		}else{
			name="统计图";
		}
		String filename = name+".jpg";
		filedown(sourcefile, filename, response);
	}
	return null;
}
/**
 * 下载文件
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
public void filedown(String sourcefile, String filename,
		HttpServletResponse response) {
	try {
		InputStream is = new FileInputStream(sourcefile);
		OutputStream os = response.getOutputStream();// 取得输出流
		response.reset();// 清空输出流
		response.setContentType("text/html; charset=utf-8");

		response.setContentType("bin");
		response.setHeader("Content-disposition", "attachment; filename="
				+ java.net.URLEncoder.encode(filename, "utf-8"));// 设定输出文件头
		byte[] buffer = new byte[1024];
		int byteread = 0;
		while ((byteread = is.read(buffer)) > 0) {
			os.write(buffer, 0, byteread);
		}
		os.close();
		is.close();
	} catch (Exception e) {
		System.out.println(e);
	}
}

	/**
	 * 
	 * 方法描述：饼状图，用户信息
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午1:18:31
	 */
	public ActionForward statisticalUserInfo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalUserInfo");
	}

	/**
	 * 
	 * 方法描述：ajax,餅狀圖，用戶信息
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午2:00:15
	 */
	public ActionForward getAjaxUserInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String cString = "";
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			// 老師，學生，家長
			List list1 = manager.getUserInfoCountByUserType("usertype,");
			if (list1 != null && list1.size() > 0) {
				for (int i = 0; i < list1.size(); i++) {
					Object[] lst = (Object[]) list1.get(i);
					Object a = lst[0];// 类型
					Object b = lst[1];// 性别
					Object c = lst[2];// 学段
					Object d = lst[3];// 人数
					if (a.equals("1")) {
						a = "老师";
					} else if (a.equals("2")) {
						a = "学生";
					} else if (a.equals("3")) {
						a = "家长";
					}
					aString = aString + "{value:" + d + ", name:'" + a + "'},";
				}
			}
			aString = aString.substring(0, aString.length() - 1);
			aString = "[" + aString + "]";

			// 類別，性別
			List list2 = manager.getUserInfoCountByUserType("usertype,sex,");
			if (list2 != null && list2.size() > 0) {
				for (int i = 0; i < list2.size(); i++) {
					Object[] lst = (Object[]) list2.get(i);
					Object a = lst[0];// 类型
					Object b = lst[1];// 性别
					Object c = lst[2];// 学段
					Object d = lst[3];// 人数
					if (b.equals("0")) {
						b = "男";
					} else if (b.equals("1")) {
						b = "女";
					}
					bString = bString + "{value:" + d + ", name:'" + b + "'},";
				}
			}
			bString = bString.substring(0, bString.length() - 1);
			bString = "[" + bString + "]";

			// 類型，性別，學段
			List list3 = manager.getUserInfoCountByUserType("usertype,sex,xueduan,");
			if (list3 != null && list3.size() > 0) {
				for (int i = 0; i < list3.size(); i++) {
					Object[] lst = (Object[]) list3.get(i);
					Object a = lst[0];// 类型
					Object b = lst[1];// 性别
					Object c = lst[2];// 学段
					Object d = lst[3];// 人数
					if (c.equals("1")) {
						c = "小学";
					} else if (c.equals("2")) {
						c = "初中";
					} else if (c.equals("3")) {
						c = "高中";
					}
					cString = cString + "{value:" + d + ", name:'" + c + "'},";
				}
			}
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
	 * 方法描述：折線图，用户註冊
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午1:18:31
	 */
	public ActionForward statisticalUserAdd(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalUserAdd");
	}

	/**
	 * 
	 * 方法描述：ajax,折現圖，用戶註冊
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午2:00:15
	 */
	public ActionForward getAjaxUserAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String endtime ="";
			String starttime ="";
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			List maxNum = manager.getUserInfosOfAddMaxNum();
			List list = manager.getUserInfosOfAdd();
			Long max = (Long) Collections.max(maxNum);
			max = max + 150;
			if (list != null && list.size() > 0) {
				for (int i = 1; i <= list.size(); i++) {
					Object[] lst = (Object[]) list.get(i - 1);
					Object a = lst[0];// 用户id
					Object b = lst[1];// 日期
					Object c = lst[2];// 人数
					aString = aString + "'" + b + "'" + ",";
					bString = bString + c + ",";
				}
				//取出最后一个元素
				Object[] object = (Object[]) list.get(list.size()-1);
				endtime = (String) object[1];// 最后一个日期
				//取出倒数第30个元素
				if(list.size()>30){
					Object[] object1 = (Object[]) list.get(list.size()-30);
					starttime = (String) object1[1];// 第一个日期
				}else{
					Object[] object1 = (Object[]) list.get(0);
					starttime = (String) object1[1];// 第一个日期
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

	/**
	 * 
	 * 方法描述：折線图，用户使用
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午1:18:31
	 */
	public ActionForward statisticalUserLog(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalUserLog");
	}

	/**
	 * 
	 * 方法描述：ajax,折現圖，用戶使用
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午2:00:15
	 */
	public ActionForward getAjaxUserLog(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String endtime ="";
			String starttime ="";
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			List maxNum = manager.getUserInfosOfLogMaxNum();
			List list = manager.getUserInfosOfLog();
			Long max = (Long) Collections.max(maxNum);
			max = max + 25;
			if (list != null && list.size() > 0) {
				for (int i = 1; i <= list.size(); i++) {
					Object[] lst = (Object[]) list.get(i - 1);
					Object b = lst[0];// 日期
					Object c = lst[1];// 人数
					aString = aString + "'" + b + "'" + ",";
					bString = bString + c + ",";
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

	/**
	 * 
	 * 方法描述：折線图，用户交易
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午1:18:31
	 */
	public ActionForward statisticalUserMoney(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalUserMoney");
	}

	/**
	 * 
	 * 方法描述：ajax,折現圖，用戶交易
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午2:00:15
	 */
	public ActionForward getAjaxUserMoney(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String cString = "";
			String a2String = "";
			String b2String = "";
			String c2String = "";
			String b3String = "";
			String c3String = "";
			String b4String = "";
			String c4String = "";
			String a5String = "";
			String b5String = "";
			String endtime ="";
			String starttime ="";
			Map map1 = new TreeMap();
			JSONObject  map2 = new JSONObject ();
			JSONObject  map3 = new JSONObject ();
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			//交易总数
			List maxNum = manager.getUserInfosOfMoneyMaxNum();
			List list = manager.getUserInfosOfMoney();
			Long max = (Long) Collections.max(maxNum);
			max = max + 25;
			if (list != null && list.size() > 0) {
				for (int i = 1; i <= list.size(); i++) {
					Object[] lst = (Object[]) list.get(i - 1);
					Object b = lst[0];// 日期
					Object c = lst[1];// 人数
					aString = aString + "'" + b + "'" + ",";
					bString = bString + c + ",";
				}
			}
			aString = aString.substring(0, aString.length() - 1);
			aString = "[" + aString + "]";
			bString = bString.substring(0, bString.length() - 1);
			bString = "[" + bString + "]";
			
			//交易人数
			List list5 = manager.getUserInfosOfMoneyCount();
			if (list5 != null && list5.size() > 0) {
				for (int i = 1; i <= list5.size(); i++) {
					Object[] lst = (Object[]) list5.get(i - 1);
					Object b = lst[0];// 日期
					Object c = lst[1];// 人数
					a5String = a5String + "'" + b + "'" + ",";
					b5String = b5String + c + ",";
				}
			}
			a5String = a5String.substring(0, a5String.length() - 1);
			a5String = "[" + a5String + "]";
			b5String = b5String.substring(0, b5String.length() - 1);
			b5String = "[" + b5String + "]";

			//交易总额，支出总额，收入总额
			List list2 = manager.getUserInfosOfMoneyAll();
			List list3 = manager.getUserInfosOfMoneyOutAll();
			List list4 = manager.getUserInfosOfMoneyInAll();
			List maxNum2 = manager.getUserInfosOfMoneyAllMaxNum();
			Double max2 = (Double) Collections.max(maxNum2);
			max2 = max2 + 20;
			
			if (list2 != null && list2.size() > 0) {
				for (int i = 1; i <= list2.size(); i++) {
					Object[] lst = (Object[]) list2.get(i - 1);
					Object b = lst[0];// 日期
					Object c = lst[1];// 总额
					Double c1= (Double) c;
					c= String.format("%.2f", c1);
					a2String = a2String + "'" + b + "'" + ",";
					b2String = b2String + c + ",";//总额的集合
					map1.put(b, c);
				}
				//取出最后一个元素
				Object[] object = (Object[])  list2.get( list2.size()-1);
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
			
			if (list3 != null && list3.size() > 0) {
				for (int i = 1; i <= list3.size(); i++) {
					Object[] lst = (Object[]) list3.get(i - 1);
					Object b = lst[0];// 日期
					Object c = lst[1];// 支出总额
					Double c1= (Double) c;
                    c= String.format("%.2f", c1);
					map2.put(b.toString(), c);
				}
			}
			if (list4 != null && list4.size() > 0) {
				for (int i = 1; i <= list4.size(); i++) {
					Object[] lst = (Object[]) list4.get(i - 1);
					Object b = lst[0];// 日期
					Object c = lst[1];// 收入总额
					Double c1= (Double) c;
                    c= String.format("%.2f", c1);
					map3.put(b.toString(), c);
				}
			}
				Set keySet = map1.keySet();
				Iterator<String> it = keySet.iterator();  
				while (it.hasNext()) {  
				  String str = it.next();  
				  Object object2 =0;
				  Object object3 =0;
				  if(map2.has(str)){
					   object2 = map2.get(str);
				  }
				  b3String = b3String + object2 + ",";//支出总额的集合
				  if(map3.has(str)){
					   object3 = map3.get(str);
				  }
				  b4String = b4String + object3 + ",";//收入总额的集合
				}  
			a2String = a2String.substring(0, a2String.length() - 1);
			a2String = "[" + a2String + "]";
			b2String = b2String.substring(0, b2String.length() - 1);
			b2String = "[" + b2String + "]";
			b3String = b3String.substring(0, b3String.length() - 1);
			b3String = "[" + b3String + "]";
			b4String = b4String.substring(0, b4String.length() - 1);
			b4String = "[" + b4String + "]";
			
			//所有交易总额
			List countMoney = manager.getUserInfosOfCountMoney();
			//所有交易总数
			List countNum = manager.getUserInfosOfCountNum();
			//所有交易总人数
			List countNumProper = manager.getUserInfosOfCountNumProper();
			//所有支出总额
			List countMoneyOut = manager.getUserInfosOfCountMoneyOut();
			//所有收入总额
			List countMoneyIn = manager.getUserInfosOfCountMoneyIn();
			
			
			JSONObject jo = new JSONObject();
			JSONArray a = new JSONArray();
			JSONArray b = new JSONArray();
			JSONArray a2 = new JSONArray();
			JSONArray b2 = new JSONArray();
			JSONArray b3 = new JSONArray();
			JSONArray b4 = new JSONArray();
			JSONArray a5 = new JSONArray();
			JSONArray b5 = new JSONArray();
			// JSONArray c = new JSONArray();
			a = JSONArray.fromObject(aString);
			b = JSONArray.fromObject(bString);//每天交易总数
			a5 = JSONArray.fromObject(a5String);//
			b5 = JSONArray.fromObject(b5String);//每天交易总人数
			a2 = JSONArray.fromObject(a2String);//时间
			b2 = JSONArray.fromObject(b2String);//总额
			b3 = JSONArray.fromObject(b3String);//支出
			b4= JSONArray.fromObject(b4String);//收入
			// c = JSONArray.fromObject(cString);
			jo.put("a", a);
			jo.put("b", b);
			jo.put("a5", a5);
			jo.put("b5", b5);
			jo.put("c", max.toString());
			jo.put("a2", a2);
			jo.put("b2", b2);
			jo.put("b3", b3);
			jo.put("b4", b4);
			jo.put("c2",  String.format("%.2f", max2));
			
			jo.put("countMoney", countMoney);
			jo.put("countNum", countNum);
			jo.put("countNumProper", countNumProper);
			jo.put("countMoneyOut", countMoneyOut);
			jo.put("countMoneyIn", countMoneyIn);
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

	/**
	 * 
	 * 方法描述：折線图，用户充值
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午1:18:31
	 */
	public ActionForward statisticalUserPay(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalUserPay");
	}

	/**
	 * 
	 * 方法描述：ajax,折現圖，用戶充值
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午2:00:15
	 */
	public ActionForward getAjaxUserPay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String cString = "";
			String dString = "";
			String endtime ="";
			String starttime ="";
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			// 充值总额
			List maxNum = manager.getUserInfosOfPayAllMaxNum();
			List list = manager.getUserInfosOfPayAll();
			Double max = (Double) Collections.max(maxNum);
			max = max + 25;
			if (list != null && list.size() > 0) {
				for (int i = 1; i <= list.size(); i++) {
					Object[] lst = (Object[]) list.get(i - 1);
					Object b = lst[0];// 日期
					Object c = lst[1];// 人数
					    Double c1= (Double) c;
					    c= String.format("%.2f", c1);
					aString = aString + "'" + b + "'" + ",";
					bString = bString + c + ",";
				}
				//取出最后一个元素
				Object[] object = (Object[])  list.get( list.size()-1);
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

			// 充值人数
			List maxNum2 = manager.getUserInfosOfPayMaxNum();
			List list2 = manager.getUserInfosOfPay();
			Long max2 = (Long) Collections.max(maxNum2);
			max2 = max2 + 25;
			if (list2 != null && list2.size() > 0) {
				for (int i = 1; i <= list2.size(); i++) {
					Object[] lst2 = (Object[]) list2.get(i - 1);
					Object b = lst2[0];// 日期
					Object c = lst2[1];// 人数
					cString = cString + "'" + b + "'" + ",";
					dString = dString + c + ",";
				}
			}
			cString = cString.substring(0, cString.length() - 1);
			cString = "[" + cString + "]";
			dString = dString.substring(0, dString.length() - 1);
			dString = "[" + dString + "]";
			
			 //所有充值（平台收入）总额
            List countMoney = manager.getUserInfosOfPayCountMoney();
            //所有交易总数
            List countNum = manager.getUserInfosOfPayCountNum();
            //所有交易总人数
            List countNumProper = manager.getUserInfosOfPayCountNumProper();

			JSONObject jo = new JSONObject();
			JSONArray a = new JSONArray();
			JSONArray b = new JSONArray();
			JSONArray c = new JSONArray();
			JSONArray d = new JSONArray();
			a = JSONArray.fromObject(aString);
			b = JSONArray.fromObject(bString);
			c = JSONArray.fromObject(cString);
			d = JSONArray.fromObject(dString);
			jo.put("a", a);
			jo.put("b", b);
			jo.put("max1", String.format("%.2f", max));
			jo.put("c", c);
			jo.put("d", d);
			jo.put("max2", max2.toString());
			jo.put("starttime", starttime);
			jo.put("endtime", endtime);
            jo.put("countMoney", countMoney);
            jo.put("countNum", countNum);
            jo.put("countNumProper", countNumProper);
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
	 * 方法描述：折線图，个人交易
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午1:18:31
	 */
	public ActionForward statisticalPersonalMoney(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		Integer userid = (Integer) request.getSession().getAttribute("s_userid");
		request.setAttribute("userid", userid);
		return actionMapping.findForward("statisticalPersonalMoney");
	}

	/**
	 * 
	 * 方法描述：ajax,折現圖，个人交易
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午2:00:15
	 */
	public ActionForward getAjaxPersonalMoney(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String cString = "";
			String dString = "";
			String userid = Encode.nullToBlank(request.getParameter("userid"));
			SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
			// 个人支出
			List maxNum = manager.getPersonalOfMoneyOutMaxNum(Integer
					.valueOf(userid));
			List list = manager.getPersonalOfMoneyOut(Integer.valueOf(userid));
			Double max = 0D;
			if(maxNum != null && maxNum.size() > 0){
				max = (Double) Collections.max(maxNum);
			}
			max = max + 20;
			if (list != null && list.size() > 0) {
				for (int i = 1; i <= list.size(); i++) {
					Object[] lst = (Object[]) list.get(i - 1);
					Object b = lst[0];// 日期
					Object c = lst[1];// 人数
					aString = aString + "'" + b + "'" + ",";
					bString = bString + c + ",";
				}
			}
			if(aString.length() > 0){
				aString = aString.substring(0, aString.length() - 1);
			}
			aString = "[" + aString + "]";
			if(bString.length() > 0){
				bString = bString.substring(0, bString.length() - 1);
			}
			bString = "[" + bString + "]";

			// 个人收入
			List maxNum2 = manager.getPersonalOfMoneyInMaxNum(Integer.valueOf(userid));
			List list2 = manager.getPersonalOfMoneyIn(Integer.valueOf(userid));
			Double max2 = 0D;
			if(maxNum2 != null){
				max2 = (Double) Collections.max(maxNum2);
			}
			max2 = max2 + 20;
			if (list2 != null && list2.size() > 0) {
				for (int i = 1; i <= list2.size(); i++) {
					Object[] lst2 = (Object[]) list2.get(i - 1);
					Object b = lst2[0];// 日期
					Object c = lst2[1];// 人数
					cString = cString + "'" + b + "'" + ",";
					dString = dString + c + ",";
				}
			}
			if(cString.length() > 0){
				cString = cString.substring(0, cString.length() - 1);
			}
			cString = "[" + cString + "]";
			if(dString.length() > 0){
				dString = dString.substring(0, dString.length() - 1);
			}
			dString = "[" + dString + "]";

			JSONObject jo = new JSONObject();
			JSONArray a = new JSONArray();
			JSONArray b = new JSONArray();
			JSONArray c = new JSONArray();
			JSONArray d = new JSONArray();
			a = JSONArray.fromObject(aString);
			b = JSONArray.fromObject(bString);
			c = JSONArray.fromObject(cString);
			d = JSONArray.fromObject(dString);
			jo.put("a", a);
			jo.put("b", b);
			jo.put("max1", String.format("%.2f", max));
			jo.put("c", c);
			jo.put("d", d);
			jo.put("max2", String.format("%.2f", max2));
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
     * 导入用户页面
     * */
    public ActionForward importUser(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        //查询所有的课程批次
    	EduCourseInfoManager manager = (EduCourseInfoManager)getBean("eduCourseInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List courseList = manager.getEduCourseInfos(condition, "courseno asc", 0);
		Map courseMap = new HashMap();
		if(courseList !=null && courseList.size()>0){
			for(int k=0;k<courseList.size();k++){
				EduCourseInfo model = (EduCourseInfo) courseList.get(k);
				courseMap.put(model.getCourseid(), model.getTitle());
			}
		}
		
		EduCourseClassManager classManager = (EduCourseClassManager)getBean("eduCourseClassManager");
		condition.clear();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List classList = classManager.getEduCourseClasss(condition, "courseclassid asc", 0);
		if(classList !=null && classList.size()>0){
			for (int i = 0; i < classList.size(); i++) {
				EduCourseClass model = (EduCourseClass) classList.get(i);
				String courserTitle = (String) courseMap.get(model.getCourseid());
				model.setClassname(courserTitle+"——"+model.getClassname());
			}
		}
    	httpServletRequest.setAttribute("classList", classList);
    	return actionMapping.findForward("user_import");
    }

    /**
     * 导入用户
     * */
    public ActionForward userImport(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SysUserInfoActionForm form = (SysUserInfoActionForm) actionForm;
        SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
        SysAreaInfoManager areainfoManager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
        SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
        EduCourseUserManager ecum = (EduCourseUserManager)getBean("eduCourseUserManager");
        //vip有效期
        String viptime = Encode.nullToBlank(httpServletRequest.getParameter("viptime"));
        
        //获取授权课程批次
        String checkids = Encode.nullToBlank(httpServletRequest.getParameter("checkid"));
        if (!"".equals(checkids)) {
        	checkids = checkids.substring(0, checkids.length() - 1);
        }
        //获取所有登录名
        List allLoginname = manager.getAllLoginname();
        
        int totalcount = 0;
        try {
                Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
                FormFile file = form.getFile();
                Workbook workbook = Workbook.getWorkbook(file.getInputStream());
                Sheet[] sheets = workbook.getSheets();// excel 的每一个sheet
                Cell cell = null;
                String str = "";
                
                Sheet sheect = workbook.getSheet(0);
                int row = sheect.getRows();
                int col = sheect.getColumns();
                String message="";
                String failname="";
                for (int i = 1; i < row; i++) {
                    String loginname = Encode.nullToBlank(sheect.getCell(0, i).getContents()).trim();//第一列 ，登录名
                    String password = Encode.nullToBlank(sheect.getCell(1, i).getContents()).trim();//第二列，密码
                    
                    if("".equals(loginname) || "".equals(password)){
                    	message="导入失败，请检查后重试。";
                        continue;
                    }
                    
                    loginname = loginname.replaceAll(" ", "");
                    password = password.replaceAll(" ", "");
                    
                    if(!allLoginname.contains(loginname)){
                    	SysUserInfo model = new SysUserInfo();
                        model.setLoginname(loginname);
                        model.setUsername(loginname);
                        model.setNickname(loginname);
                        model.setUsertype("2");
                        if (!"".equals(password)) {
                            model.setPassword(MD5.getEncryptPwd(password));
                        }else{
                            model.setPassword(MD5.getEncryptPwd("123456"));
                        }
                        model.setSex("0");
                        model.setPhoto("man.jpg");
                        model.setStatus("1");
                        model.setUnitid(unitid);
                        model.setXueduan("2");
                        model.setMoney(0.00F);
                        model.setAuthentication("0");
                        model.setVipmark("1");//标识，1是导入的未激活vip用户
                        manager.addSysUserInfo(model);
                        
                        SysUserInfoDetail detail = new SysUserInfoDetail();
                        detail.setEducation("9");
                        detail.setJobtitle("9");
                        detail.setCanaddcourse("0");
                        detail.setUserid(model.getUserid());
                        detail.setFlag("0");
                        detail.setCreatedate(DateTime.getDate());
                        detail.setLastlogin(detail.getCreatedate());
                        detail.setLogintimes(0);
                        detail.setUpdatetime(detail.getCreatedate());
                        suidm.addSysUserInfoDetail(detail);
                        
                        if (!"".equals(checkids)) {
                        	String[] classCourseids = checkids.split(",");//[1_1,2_3,3_4]
                        	for (int j = 0; j < classCourseids.length; j++) {
                        		String a = classCourseids[j];
                        		String[] b = a.split("_");
                        		EduCourseUser courseUser = new EduCourseUser();
                        		courseUser.setCourseclassid(Integer.valueOf(b[1]));
                        		courseUser.setCourseid(Integer.valueOf(b[0]));
                        		courseUser.setUserid(model.getUserid());
                        		courseUser.setStatus("1");
                        		courseUser.setCreatedate(DateTime.getDate());
                        		courseUser.setUsertype("3");
                        		courseUser.setVip("1");
                        		courseUser.setValiditytime(viptime);//有效期为输入的时间
                        		//开始时间和结束时间为第一次登录的时候设置
//                        		//开始时间为第一次登录的时间
//                        		courseUser.setStartdate(DateTime.getDate());
//                        		//vip结束时间,当前时间+一年
//                        		int vip_time =  Integer.valueOf(PublicResourceBundle.getProperty("system","vip.time")).intValue();//默认1年
//                        		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                          		Calendar now = Calendar.getInstance();  
//                                  now.setTime(new Date());  
//                                  now.set(Calendar.DATE, now.get(Calendar.DATE) + vip_time); 
//                                  Date nowDate = now.getTime();
//                        		courseUser.setEnddate(format.format(nowDate));
                        		ecum.addEduCourseUser(courseUser);
							}
                    	}
                        totalcount++;
                    }else{
                    	failname =  failname + loginname+",";
                    }
                    
                }
                
                workbook.close();
                if("".equals(message)){
                	  addLog(httpServletRequest, "批量导入了用户信息");
                	  if("".equals(failname)){
                		  httpServletRequest.setAttribute("promptinfo", "成功导入"+totalcount+"个用户!");
                	  }else{
                		  httpServletRequest.setAttribute("promptinfo", "成功导入"+totalcount+"个用户，导入失败用户为："+failname);
                	  }
                }else{
                      httpServletRequest.setAttribute("promptinfo", message);
                }
              
        } catch (Exception e1) {
        	httpServletRequest.setAttribute("promptinfo", "上传文件失败，发生异常!");
            e1.printStackTrace();
        }
        
        return actionMapping.findForward("import_success");
    }
    
    /**
     * 导出用户
    * @author ; liud 
    * @Description : TODO 
    * @CreateDate ; 2017-3-1 下午4:15:31 
    * @lastModified ; 2017-3-1 下午4:15:31 
    * @version ; 1.0 
    * @param actionMapping
    * @param actionForm
    * @param httpServletRequest
    * @param httpServletResponse
    * @return
     */
    public ActionForward userExport(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        SysUserInfoActionForm form = (SysUserInfoActionForm) actionForm;
        SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
        SysAreaInfoManager areainfoManager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
        SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
        
        String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
        String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
        String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
        String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
        String xueduan = Encode.nullToBlank(httpServletRequest.getParameter("xueduan"));
        String authentication = Encode.nullToBlank(httpServletRequest.getParameter("authentication"));
        String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
        String unitid = Encode.nullToBlank(httpServletRequest.getParameter("unitid"));
        String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
        
        List<SearchModel> condition = new ArrayList<SearchModel>();
        SearchCondition.addCondition(condition, "loginname", "<>", "~admin~");
        if(!"".equals(unitid)){
            SearchCondition.addCondition(condition, "unitid", "=", unitid);
        }
        if(!"".equals(status)){
            SearchCondition.addCondition(condition, "status", "=", status);
        }
        if(!"".equals(loginname)){
            SearchCondition.addCondition(condition, "loginname", "like", "%" + loginname + "%");
        }
        if(!"".equals(username)){
            SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
        }
        if(!"".equals(sex) && !"-1".equals(sex)){
            SearchCondition.addCondition(condition, "sex", "=", sex);
        }
        if(!"".equals(usertype) && !"-1".equals(usertype)){
            SearchCondition.addCondition(condition, "usertype", "=", usertype);
        }
        if(!"".equals(xueduan) && !"-1".equals(xueduan)){
            SearchCondition.addCondition(condition, "xueduan", "=", xueduan);
        }
        if(!"".equals(authentication) && !"-1".equals(authentication)){
            SearchCondition.addCondition(condition, "authentication", "=", authentication);
        }
        if(!"".equals(studentno)){
            SearchCondition.addCondition(condition, "studentno", "like", "%" + studentno + "%");
        }
        List list = manager.getSysUserInfos(condition, "loginname asc", 0);
        
        String rootpath = httpServletRequest.getSession().getServletContext().getRealPath("/upload");//取当前系统路径
        String targetfile = "userList.xls";//输出的excel文件名   
        String worksheet = "useslist";//输出的excel文件工作表名   
        String[] title = {"登录名","用户姓名"};//excel工作表的标题   
        WritableWorkbook workbook;   
        try {   
            //创建可写入的Excel工作薄,运行生成的文件在tomcat/bin下   
            OutputStream os=new FileOutputStream(rootpath+"/"+targetfile);    
            workbook=Workbook.createWorkbook(os);    
            WritableSheet sheet = workbook.createSheet(worksheet, 0); //添加第一个工作表   
            jxl.write.Label label;   
            label = new jxl.write.Label(0, 0, "登录名");
            sheet.addCell(label); 
            label = new jxl.write.Label(1, 0, "用户名");
            sheet.addCell(label); 
            if(list !=null && list.size()>0)
            for (int i=0; i<list.size(); i++)   
            {   
                SysUserInfo model = (SysUserInfo) list.get(i);
            //Label(列号,行号 ,内容 )   
            label = new jxl.write.Label(0, i+1, model.getLoginname()); //第一列，登录名
            sheet.addCell(label);   
            label = new jxl.write.Label(1, i+1, model.getUsername()); //第二列，用户名
            sheet.addCell(label);    
            }   
            workbook.write();    
            workbook.close();   
        }catch(Exception e)  {    
            e.printStackTrace();    
        }    
        
        try
        {
            BufferedInputStream bis = null; 
            BufferedOutputStream bos = null; 
            OutputStream fos = null; 
            InputStream fis = null; 
            File uploadFile = new File(rootpath+"/"+targetfile); 
            fis = new FileInputStream(uploadFile); 
            bis = new BufferedInputStream(fis); 
            fos = httpServletResponse.getOutputStream(); 
            bos = new BufferedOutputStream(fos); 
            //这个就是弹出下载对话框的关键代码 
            httpServletResponse.setHeader("Content-disposition", 
                               "attachment;filename=" + 
                               URLEncoder.encode(targetfile, "utf-8")); 
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
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        
        return null;
    }
}