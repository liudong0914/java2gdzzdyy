package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.service.SysUnitInfoManager;
import com.wkmk.sys.web.form.SysUnitInfoActionForm;
import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 系统单位信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUmsUnitInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String province = Encode.nullToBlank(httpServletRequest.getParameter("province"));
		String city = Encode.nullToBlank(httpServletRequest.getParameter("city"));
		String county = Encode.nullToBlank(httpServletRequest.getParameter("county"));
		String schooltype = Encode.nullToBlank(httpServletRequest.getParameter("schooltype"));
		String unitname = Encode.nullToBlank(httpServletRequest.getParameter("unitname"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		
		SysUnitInfoManager manager = (SysUnitInfoManager)getBean("sysUnitInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(province)){
			SearchCondition.addCondition(condition, "province", "=", province);
		}
		if(!"".equals(city)){
			SearchCondition.addCondition(condition, "city", "=", city);
		}
		if(!"".equals(county)){
			SearchCondition.addCondition(condition, "county", "=", county);
		}
		if(!"".equals(schooltype)){
			SearchCondition.addCondition(condition, "schooltype", "=", schooltype);
		}
		if(!"".equals(unitname)){
			SearchCondition.addCondition(condition, "unitname", "like", "%" + unitname + "%");
		}
		if(!"".equals(status) && !"-1".equals(status)){
			SearchCondition.addCondition(condition, "status", "=", status);
		}else {
			SearchCondition.addCondition(condition, "status", "<>", "0");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "status asc, createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUnitInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("province", province);
		httpServletRequest.setAttribute("city", city);
		httpServletRequest.setAttribute("county", county);
		httpServletRequest.setAttribute("schooltype", schooltype);
		httpServletRequest.setAttribute("unitname", unitname);
		httpServletRequest.setAttribute("status", status);
		
		//获取所有省地区
		SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		List provinceList = saim.getSysAreaInfosByParentno("00");
		httpServletRequest.setAttribute("provinceList", provinceList);
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
		String province = Encode.nullToBlank(httpServletRequest.getParameter("province"));
		String city = Encode.nullToBlank(httpServletRequest.getParameter("city"));
		String county = Encode.nullToBlank(httpServletRequest.getParameter("county"));
		String schooltype = Encode.nullToBlank(httpServletRequest.getParameter("schooltype"));
		String unitname = Encode.nullToBlank(httpServletRequest.getParameter("unitname"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("province", province);
		httpServletRequest.setAttribute("city", city);
		httpServletRequest.setAttribute("county", county);
		httpServletRequest.setAttribute("schooltype", schooltype);
		httpServletRequest.setAttribute("unitname", unitname);
		httpServletRequest.setAttribute("status", status);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysUnitInfo model = new SysUnitInfo();
		model.setParentno("0000");
		model.setOrderindex(1);
		model.setSketch("default.jpg");
		model.setLogo("logo.jpg");
		model.setBanner("banner.jpg");
		model.setCreatedate(DateTime.getDate());
		model.setUpdatetime(model.getCreatedate());
		model.setStatus("1");
		model.setHits(0);
		model.setPraise(0);
		model.setRecommand("0");
		model.setRecommandno(1);
		model.setSchooltype("1");
		model.setType("1");
		httpServletRequest.setAttribute("model", model);
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
		SysUnitInfoActionForm form = (SysUnitInfoActionForm)actionForm;
		SysUnitInfoManager manager = (SysUnitInfoManager)getBean("sysUnitInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUnitInfo model = form.getSysUnitInfo();
				if("1".equals(model.getSchooltype()) || "2".equals(model.getSchooltype())){
					String[] schooltype = httpServletRequest.getParameterValues("schooltype"+model.getSchooltype());
					String type = "";
					for(int i=0, size=schooltype.length; i<size; i++){
						type = type + "," + schooltype[i];
					}
					model.setType(type.substring(1));
				}else {
					model.setType("1");
				}
				manager.addSysUnitInfo(model);
				addLog(httpServletRequest,"增加了一个系统单位【" + model.getUnitname() + "】信息");
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
		String province = Encode.nullToBlank(httpServletRequest.getParameter("province"));
		String city = Encode.nullToBlank(httpServletRequest.getParameter("city"));
		String county = Encode.nullToBlank(httpServletRequest.getParameter("county"));
		String schooltype = Encode.nullToBlank(httpServletRequest.getParameter("schooltype"));
		String unitname = Encode.nullToBlank(httpServletRequest.getParameter("unitname"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("province", province);
		httpServletRequest.setAttribute("city", city);
		httpServletRequest.setAttribute("county", county);
		httpServletRequest.setAttribute("schooltype", schooltype);
		httpServletRequest.setAttribute("unitname", unitname);
		httpServletRequest.setAttribute("status", status);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		SysUnitInfoManager manager = (SysUnitInfoManager)getBean("sysUnitInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysUnitInfo model = manager.getSysUnitInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			
			//获取所有省地区
			SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			List provinceList = saim.getSysAreaInfosByParentno("00");
			httpServletRequest.setAttribute("provinceList", provinceList);
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(model.getProvince());
			List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("cityList", cityList);
			sysAreaInfo = saim.getSysAreaInfosByCitycode(model.getCity());
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
		SysUnitInfoActionForm form = (SysUnitInfoActionForm)actionForm;
		SysUnitInfoManager manager = (SysUnitInfoManager)getBean("sysUnitInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUnitInfo model = form.getSysUnitInfo();
				if("1".equals(model.getSchooltype()) || "2".equals(model.getSchooltype())){
					String[] schooltype = httpServletRequest.getParameterValues("schooltype"+model.getSchooltype());
					String stype = "";
					for(int i=0, size=schooltype.length; i<size; i++){
						stype = stype + "," + schooltype[i];
					}
					model.setType(stype.substring(1));
				}else {
					model.setType("1");
				}
				manager.updateSysUnitInfo(model);
				addLog(httpServletRequest,"修改了一个系统单位【" + model.getUnitname() + "】信息");
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
		SysUnitInfoManager manager = (SysUnitInfoManager)getBean("sysUnitInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String status = Encode.nullToBlank(httpServletRequest.getParameter("setstatus"));

		SysUnitInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysUnitInfo(checkids[i]);
			model.setStatus(status);
			manager.updateSysUnitInfo(model);
			//manager.delSysUnitInfo(checkids[i]);
			addLog(httpServletRequest,"禁用了一个系统单位【" + model.getUnitname() + "】信息");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}