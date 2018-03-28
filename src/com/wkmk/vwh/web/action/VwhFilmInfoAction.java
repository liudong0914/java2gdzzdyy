package com.wkmk.vwh.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduKnopointInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmKnopoint;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmKnopointManager;
import com.wkmk.vwh.web.form.VwhFilmInfoActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 视频库视频信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String searchSubjectid = Encode.nullToBlank(httpServletRequest.getParameter("searchSubjectid"));
		String searchGradeid = Encode.nullToBlank(httpServletRequest.getParameter("searchGradeid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String actor = Encode.nullToBlank(httpServletRequest.getParameter("actor"));//视频主讲教师
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));//上传用户
		
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		VwhFilmInfoManager manager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		SearchCondition.addCondition(condition, "status", "=", "1");
		if(!"".equals(searchSubjectid)){
			SearchCondition.addCondition(condition, "eduGradeInfo.subjectid", "=", searchSubjectid);
		}
		if(!"".equals(searchGradeid)){
			SearchCondition.addCondition(condition, "eduGradeInfo.gradeid", "=", searchGradeid);
		}
		if(!"".equals(title)){
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		}
		if(!"".equals(actor)){
			SearchCondition.addCondition(condition, "actor", "like", "%" + actor + "%");
		}
		if(!"".equals(username)){
			SearchCondition.addCondition(condition, "sysUserInfo.username", "like", "%" + username + "%");
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "createdate desc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageVwhFilmInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("searchSubjectid", searchSubjectid);
		httpServletRequest.setAttribute("searchGradeid", searchGradeid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("actor", actor);
		httpServletRequest.setAttribute("username", username);
		
		return actionMapping.findForward("list");
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
		String searchSubjectid = Encode.nullToBlank(httpServletRequest.getParameter("searchSubjectid"));
		String searchGradeid = Encode.nullToBlank(httpServletRequest.getParameter("searchGradeid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String actor = Encode.nullToBlank(httpServletRequest.getParameter("actor"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		httpServletRequest.setAttribute("searchSubjectid", searchSubjectid);
		httpServletRequest.setAttribute("searchGradeid", searchGradeid);
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("actor", actor);
		httpServletRequest.setAttribute("username", username);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		VwhFilmInfoManager manager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			VwhFilmInfo model = manager.getVwhFilmInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			
			//视频服务器
			VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "unitid", "=", model.getUnitid());
			List computerList = vcim.getVwhComputerInfos(condition, "computername", 0);
			httpServletRequest.setAttribute("computerList", computerList);
			
			//获取微课关联学科-年级-知识点
			EduSubjectInfoManager esim = (EduSubjectInfoManager) getBean("eduSubjectInfoManager");
			EduSubjectInfo subjectInfo = esim.getEduSubjectInfo(model.getEduGradeInfo().getSubjectid());
			httpServletRequest.setAttribute("subjectInfo", subjectInfo);
			//微课与知识点的关联
			VwhFilmKnopointManager vfkm = (VwhFilmKnopointManager) getBean("vwhFilmKnopointManager");
			List list = vfkm.getEduKnopointInfoByFilmid(model.getFilmid());
			StringBuffer knopointids = new StringBuffer();
			StringBuffer knopointnames = new StringBuffer();
			if(list != null && list.size() > 0){
				EduKnopointInfo eki = null;
				for(int i=0, size=list.size(); i<size; i++){
					eki = (EduKnopointInfo) list.get(i);
					knopointids.append(";").append(eki.getKnopointid());
					knopointnames.append(";").append(eki.getTitle());
				}
				httpServletRequest.setAttribute("knopointids", knopointids.substring(1));
				httpServletRequest.setAttribute("knopointnames", knopointnames.substring(1));
			}else {
				knopointnames.append("点击选择知识点");
				httpServletRequest.setAttribute("knopointids", knopointids.toString());
				httpServletRequest.setAttribute("knopointnames", knopointnames.toString());
			}
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
		VwhFilmInfoActionForm form = (VwhFilmInfoActionForm)actionForm;
		VwhFilmInfoManager manager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
				String gradeid = Encode.nullToBlank(httpServletRequest.getParameter("gradeid"));
				EduGradeInfo egi = egim.getEduGradeInfo(gradeid);
				
				VwhFilmInfo model = form.getVwhFilmInfo();
				VwhFilmInfo vfi = manager.getVwhFilmInfo(model.getFilmid());
				vfi.setTitle(model.getTitle());
				vfi.setSketch(model.getSketch());
				vfi.setActor(model.getActor());
				vfi.setComputerid(model.getComputerid());
				vfi.setOrderindex(model.getOrderindex());
				vfi.setKeywords(model.getKeywords());
				vfi.setDescript(model.getDescript());
				vfi.setEduGradeInfo(egi);
				manager.updateVwhFilmInfo(vfi);
				addLog(httpServletRequest,"修改了一个微课程【" + vfi.getTitle() + "】信息");
				
				String knopointid = Encode.nullToBlank(httpServletRequest.getParameter("knopointid"));
				String knopointidupdate = Encode.nullToBlank(httpServletRequest.getParameter("knopointidupdate"));//1表示知识点有修改，需要删除先前数据然后重新保存
				//微课与知识点关联
				if("1".equals(knopointidupdate)){
					VwhFilmKnopointManager vfkm = (VwhFilmKnopointManager) getBean("vwhFilmKnopointManager");
					//删除当前微课关联的所有知识点
					vfkm.delVwhFilmKnopoint(vfi.getFilmid(), null);
					if(!"".equals(knopointid)){
						String[] knopointids = knopointid.split(";");
						VwhFilmKnopoint vfk = new VwhFilmKnopoint();
						vfk.setFilmid(vfi.getFilmid());
						for(int i=0, size=knopointids.length; i<size; i++){
							vfk.setFid(null);
							vfk.setKnopointid(Integer.valueOf(knopointids[i]));
							vfkm.addVwhFilmKnopoint(vfk);
						}
					}
				}
			}catch (Exception e){
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
		//微课所有的修改都是弹出窗口，因为宽度不够
		//return actionMapping.findForward("close");
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
		VwhFilmInfoManager manager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		VwhFilmInfo model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getVwhFilmInfo(checkids[i]);
			model.setStatus("9");
			manager.updateVwhFilmInfo(model);
			addLog(httpServletRequest,"删除了一个微课程【" + model.getTitle() + "】信息");
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
		HttpSession session = httpServletRequest.getSession();
		SysUnitInfo sysUnitInfo = (SysUnitInfo) session.getAttribute("s_sysunitinfo");
		
		//只查询学校对应的学科和年级
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List subjectList = manager.getAllSubjectByUnitType(sysUnitInfo.getType());
		List gradeList = manager.getAllGradeBySubjectAndUnitType(null, sysUnitInfo.getType());
		
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target="rfrmright";
		String url = null;
		String hint = null;
		EduSubjectInfo esi = null;
		for(int k=0;k<subjectList.size();k++){
			esi = (EduSubjectInfo) subjectList.get(k);
			text = esi.getSubjectname();
			hint = "";
			url="/vwhFilmInfoAction.do?method=list&searchSubjectid=" + esi.getSubjectid();
			tree.append("\n").append("tree").append(".nodes[\"0000_").append(esi.getSubjectid()).append("\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		EduGradeInfo egi = null;
		for(int k=0;k<gradeList.size();k++){
			egi = (EduGradeInfo) gradeList.get(k);
			text = egi.getGradename();
			hint = "";
			url="/vwhFilmInfoAction.do?method=list&searchGradeid=" + egi.getGradeid();
			tree.append("\n").append("tree").append(".nodes[\"").append(egi.getSubjectid()).append("_0").append(egi.getGradeid()).append("\"]=\"");
			if(text !=null && text.trim().length() > 0) {
				tree.append("text:").append(text).append(";");
			}
			if(hint!=null && hint.trim().length() > 0) {
				tree.append("hint:").append(hint).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl="/vwhFilmInfoAction.do?method=list";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}
	
}