package com.wkmk.tk.web.action;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.tk.bo.TkQuestionsType;
import com.wkmk.tk.service.TkQuestionsInfoManager;
import com.wkmk.tk.service.TkQuestionsTypeManager;
import com.wkmk.tk.web.form.TkQuestionsTypeActionForm;

/**
 * <p>
 * Description: 题库试题类型
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkQuestionsTypeAction extends BaseAction {

	/**
	 * 工作区域
	 * */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}

	/**
	 * 树形选择器
	 * */
	public ActionForward tree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		HttpSession session = httpServletRequest.getSession();
		SysUnitInfo sysUnitinfo = (SysUnitInfo) session.getAttribute("s_sysunitinfo");
		// 查询学科
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<EduSubjectInfo> subjectList = manager.getAllSubjectByUnitType(sysUnitinfo.getType());
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target = "rfrmright";
		String url = null;
		String hint = null;
		EduSubjectInfo esi = null;
		for (int i = 0; i < subjectList.size(); i++) {
			esi = (EduSubjectInfo) subjectList.get(i);
			text = esi.getSubjectname();
			hint = "";
			url = "/tkQuestionsTypeAction.do?method=list&subjectid=" + esi.getSubjectid();
			tree.append("\n").append("tree").append(".nodes[\"0000_").append(esi.getSubjectid()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			if (hint != null && !"".equals(hint.trim())) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl = "javascript:;";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}

	/**
	 * 列表显示
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
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String typeno = Encode.nullToBlank(httpServletRequest.getParameter("typeno"));
		String typename = Encode.nullToBlank(httpServletRequest.getParameter("typename"));
		TkQuestionsTypeManager manager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
		SearchCondition.addCondition(condition, "typeno", "like", "%" + typeno + "%");
		SearchCondition.addCondition(condition, "typename", "like", "%" + typename + "%");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "typeno asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageTkQuestionsTypes(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List dataList = page.getDatalist();
		TkQuestionsInfoManager infoManager = (TkQuestionsInfoManager) getBean("tkQuestionsInfoManager");
		List typeids = infoManager.getAlltypeids();
		TkQuestionsType model = null;
		for (int i = 0; i < dataList.size(); i++) {
			model = (TkQuestionsType) dataList.get(i);
			if (typeids.contains(model.getTypeid())) {
				model.setFlags("disabled=\"disabled\"");
			}
		}
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("subjectid", subjectid);
		httpServletRequest.setAttribute("typeno", typeno);
		httpServletRequest.setAttribute("typename", typename);
		return actionMapping.findForward("list");
	}

	/**
	 * 增加前
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
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		try {
			// 分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			String typeno = Encode.nullToBlank(httpServletRequest.getParameter("typeno"));
			String typename = Encode.nullToBlank(httpServletRequest.getParameter("typename"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
			TkQuestionsType model = new TkQuestionsType();
			String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
			model.setSubjectid(Integer.parseInt(subjectid));
			model.setUnitid(Integer.parseInt(httpServletRequest.getSession().getAttribute("s_unitid").toString()));
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("act", "addSave");
			httpServletRequest.setAttribute("backtype", "0");
			httpServletRequest.setAttribute("subjectid", subjectid);
			httpServletRequest.setAttribute("typeno", typeno);
			httpServletRequest.setAttribute("typename", typename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 * 增加时保存
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
	public ActionForward addSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsTypeActionForm form = (TkQuestionsTypeActionForm) actionForm;
		TkQuestionsTypeManager manager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkQuestionsType model = form.getTkQuestionsType();
				manager.addTkQuestionsType(model);
				addLog(httpServletRequest, "增加了一个" + model.getTypename() + "题库试题类型");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 修改前
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
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsTypeManager manager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String typeno = Encode.nullToBlank(httpServletRequest.getParameter("typeno"));
		String typename = Encode.nullToBlank(httpServletRequest.getParameter("typename"));
		try {
			TkQuestionsType model = manager.getTkQuestionsType(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("subjectid", model.getSubjectid());
			httpServletRequest.setAttribute("backtype", "1");
			// 分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
			httpServletRequest.setAttribute("typeno", typeno);
			httpServletRequest.setAttribute("typename", typename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		saveToken(httpServletRequest);
		return actionMapping.findForward("edit");
	}

	/**
	 * 修改时保存
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
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsTypeActionForm form = (TkQuestionsTypeActionForm) actionForm;
		TkQuestionsTypeManager manager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkQuestionsType model = form.getTkQuestionsType();
				manager.updateTkQuestionsType(model);
				addLog(httpServletRequest, "修改了一个" + model.getTypename() + "题库试题类型");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 批量删除
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
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkQuestionsTypeManager manager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		if (checkids != null) {
			for (int i = 0; i < checkids.length; i++) {
				TkQuestionsType model = manager.getTkQuestionsType(checkids[i]);
				manager.delTkQuestionsType(checkids[i]);
				addLog(httpServletRequest, "删除了一个" + model.getTypename() + "题库类型");
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 判断编号是否存在
	 * */
	public ActionForward ishanveTypeno(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String typeno = Encode.nullToBlank(httpServletRequest.getParameter("typeno"));
		String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
		String id = Encode.nullToBlank(httpServletRequest.getParameter("id"));
		httpServletResponse.setContentType("text/plain;charset=gbk");
		TkQuestionsTypeManager manager = (TkQuestionsTypeManager) getBean("tkQuestionsTypeManager");
		PrintWriter out = null;
		try {
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "typeno", "=", typeno);
			SearchCondition.addCondition(condition, "subjectid", "=", subjectid);
			List list = manager.getTkQuestionsTypes(condition, "typeno", 0);
			out = httpServletResponse.getWriter();
			if (list == null || list.size() == 0) {
				out.print("ok");
			} else {
				if (id != null && !"".equals(id.trim())) {
					String ntypeno = manager.getTkQuestionsType(id).getTypeno();
					if (ntypeno.equals(typeno)) {
						out.print("ok");
					} else {
						out.print("当前题库类型编号已被占用,请重试!");
					}
				} else {
					out.print("当前题库类型编号已被占用,请重试!");
				}
			}
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
		return null;
	}
}