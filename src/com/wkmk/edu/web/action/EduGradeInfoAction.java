package com.wkmk.edu.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.bo.EduXueduanInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.edu.service.EduXueduanInfoManager;
import com.wkmk.edu.web.form.EduGradeInfoActionForm;
import com.wkmk.sys.service.SysUserInfoManager;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 教育年级信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduGradeInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String gradename = Encode.nullToBlank(httpServletRequest.getParameter("gradename"));
		
		EduGradeInfoManager manager = (EduGradeInfoManager)getBean("eduGradeInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
		if(!"".equals(gradename)){
			SearchCondition.addCondition(condition, "gradename", "like", "%" + gradename + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "orderindex asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageEduGradeInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		//获取所有学段
		EduXueduanInfoManager exim = (EduXueduanInfoManager) getBean("eduXueduanInfoManager");
		condition.clear();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List xdList = exim.getEduXueduanInfos(condition, "orderindex asc", 0);
		Map xdMap = new HashMap();
		EduXueduanInfo xd = null;
		for(int i=0, size=xdList.size(); i<size; i++){
			xd = (EduXueduanInfo) xdList.get(i);
			xdMap.put(xd.getXueduanid(), xd.getName());
		}
		
		List dataList = page.getDatalist();
		EduGradeInfo model = null;
		for(int i=0, size=dataList.size(); i<size; i++){
			model = (EduGradeInfo) dataList.get(i);
			model.setFlago((String)xdMap.get(model.getXueduanid()));
			model.setFlagl((String)xdMap.get(model.getCxueduanid()));
		}
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("gradename", gradename);
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
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String gradename = Encode.nullToBlank(httpServletRequest.getParameter("gradename"));
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("gradename", gradename);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		EduGradeInfo model = new EduGradeInfo();
		model.setOrderindex(1);
		model.setStatus("1");
		model.setSubjectid(Integer.valueOf(subjectid));
		model.setUpdatetime(DateTime.getDate());
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
		
		//获取所有学段，包括父学段和子学段
		EduXueduanInfoManager exim = (EduXueduanInfoManager) getBean("eduXueduanInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		SearchCondition.addCondition(condition, "parentid", "=", "0");
		List xdList = exim.getEduXueduanInfos(condition, "orderindex asc", 0);
		httpServletRequest.setAttribute("xdList", xdList);
		
		List cxdList = new ArrayList();
		if(xdList != null && xdList.size() > 0){
			EduXueduanInfo cxueduan = (EduXueduanInfo) xdList.get(0);
			condition.clear();
			SearchCondition.addCondition(condition, "status", "=", "1");
			SearchCondition.addCondition(condition, "parentid", "=", cxueduan.getXueduanid());
			cxdList = exim.getEduXueduanInfos(condition, "orderindex asc", 0);
		}
		httpServletRequest.setAttribute("cxdList", cxdList);

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
		EduGradeInfoActionForm form = (EduGradeInfoActionForm)actionForm;
		EduGradeInfoManager manager = (EduGradeInfoManager)getBean("eduGradeInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduGradeInfo model = form.getEduGradeInfo();
				manager.addEduGradeInfo(model);
				addLog(httpServletRequest,"增加了一个教育年级【" + model.getGradename() + "】信息");
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
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String gradename = Encode.nullToBlank(httpServletRequest.getParameter("gradename"));
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("gradename", gradename);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		EduGradeInfoManager manager = (EduGradeInfoManager)getBean("eduGradeInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			EduGradeInfo model = manager.getEduGradeInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			
			//获取所有学段，包括父学段和子学段
			EduXueduanInfoManager exim = (EduXueduanInfoManager) getBean("eduXueduanInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "status", "=", "1");
			SearchCondition.addCondition(condition, "parentid", "=", "0");
			List xdList = exim.getEduXueduanInfos(condition, "orderindex asc", 0);
			httpServletRequest.setAttribute("xdList", xdList);
			
			condition.clear();
			SearchCondition.addCondition(condition, "status", "=", "1");
			SearchCondition.addCondition(condition, "parentid", "=", model.getXueduanid());
			List cxdList = exim.getEduXueduanInfos(condition, "orderindex asc", 0);
			httpServletRequest.setAttribute("cxdList", cxdList);
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
		EduGradeInfoActionForm form = (EduGradeInfoActionForm)actionForm;
		EduGradeInfoManager manager = (EduGradeInfoManager)getBean("eduGradeInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduGradeInfo model = form.getEduGradeInfo();
				EduGradeInfo egi = manager.getEduGradeInfo(model.getGradeid());
				model.setUpdatetime(DateTime.getDate());
				manager.updateEduGradeInfo(model);
				addLog(httpServletRequest,"修改了一个教育年级【" + model.getGradename() + "】信息");
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
		EduGradeInfoManager manager = (EduGradeInfoManager)getBean("eduGradeInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		EduGradeInfo model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getEduGradeInfo(checkids[i]);
			manager.delEduGradeInfo(model);
			addLog(httpServletRequest,"删除了一个教育年级【" + model.getGradename() + "】信息");
		}
		
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 跳转到主工作区
	 */
	public ActionForward main(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}

	/**
	 * 树型选择器
	 * @param actionMapping ActionMapping
	 * @param actionForm ActionForm
	 * @param httpServletRequest HttpServletRequest
	 * @param httpServletResponse HttpServletResponse
	 * @return ActionForward
	 */
	public ActionForward tree(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) {
		EduSubjectInfoManager manager = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
		
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		List lst = manager.getEduSubjectInfos(condition, "orderindex", 0);
		
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target="rfrmright";
		String url = null;
		String hint = null;
		String subjectid = null;
		String parentid = "0";
		EduSubjectInfo subject = null;
		for(int k=0;k<lst.size();k++){
			subject = (EduSubjectInfo) lst.get(k);
			subjectid = subject.getSubjectid().toString();
			text = subject.getSubjectname().trim();
			hint = "";
			url="/eduGradeInfoAction.do?method=list&subjectid=" + subjectid;
			tree.append("\n").append("tree").append(".nodes[\"").append(parentid).append("_").append(subjectid).append("\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl="javascript:;";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}
}