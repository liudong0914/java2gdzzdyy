package com.wkmk.sys.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.service.SysUnitInfoManager;
import com.wkmk.sys.web.form.SysUnitInfoActionForm;

import com.util.action.BaseAction;

/**
 *<p>Description: 系统单位信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitInfoAction extends BaseAction {

	/**
	 * 修改前
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward beforeUpdateSelf(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		SysUnitInfoManager manager = (SysUnitInfoManager) getBean("sysUnitInfoManager");
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		try {
			SysUnitInfo model = manager.getSysUnitInfo(unitid);
			httpServletRequest.setAttribute("act", "updateSaveSelf");
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
		} catch (Exception e1) {
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("editself");
	}
	
	/**
	 * 修改时保存
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward updateSaveSelf(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		SysUnitInfoActionForm form = (SysUnitInfoActionForm) actionForm;
		SysUnitInfoManager manager = (SysUnitInfoManager) getBean("sysUnitInfoManager");

		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUnitInfo model = form.getSysUnitInfo();
				manager.updateSysUnitInfo(model);
				
				HttpSession session = httpServletRequest.getSession();
				// 重新初始化sysUnitInfo
				session.removeAttribute("s_sysunitinfo");
				session.setAttribute("s_sysunitinfo", model);

				addLog(httpServletRequest, "修改了当前的站点【" + model.getUnitname() + "】信息");
			} catch (Exception e1) {
			}
			httpServletRequest.setAttribute("promptinfo", "站点版权信息修改成功!");
		}
		return actionMapping.findForward("success");
	}

	/**
	 * 网站优化参数修改前
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward beforeUpdateSeo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		SysUnitInfoManager manager = (SysUnitInfoManager) getBean("sysUnitInfoManager");
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		try {
			SysUnitInfo model = manager.getSysUnitInfo(unitid);
			httpServletRequest.setAttribute("act", "updateSaveSeo");
			httpServletRequest.setAttribute("model", model);
		} catch (Exception e1) {
		}

		saveToken(httpServletRequest);
		return actionMapping.findForward("seoedit");
	}

	/**
	 * 更新网站优化信息
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward updateSaveSeo(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		SysUnitInfoActionForm form = (SysUnitInfoActionForm) actionForm;
		SysUnitInfoManager manager = (SysUnitInfoManager) getBean("sysUnitInfoManager");

		if (isTokenValid(httpServletRequest, true)) {
			try {
				HttpSession session = httpServletRequest.getSession();
				SysUnitInfo model = form.getSysUnitInfo();
				SysUnitInfo model1 = manager.getSysUnitInfo(model.getUnitid());
				model1.setKeywords(SysKeywordFilterAction.getFilterString(httpServletRequest, model.getKeywords())); // 网站关键词
				model1.setDescript(SysKeywordFilterAction.getFilterString(httpServletRequest, model.getDescript())); // 网站优化
				manager.updateSysUnitInfo(model1);
				// 重新初始化sysUnitInfo
				session.removeAttribute("s_sysunitinfo");
				session.setAttribute("s_sysunitinfo", model1);
				addLog(httpServletRequest, "更新了当前网站【" + model1.getUnitname() + "】的优化(SEO)参数");
			} catch (Exception e1) {
			}
			httpServletRequest.setAttribute("promptinfo", "更新网站优化成功!");
		}

		return actionMapping.findForward("success");
	}
}