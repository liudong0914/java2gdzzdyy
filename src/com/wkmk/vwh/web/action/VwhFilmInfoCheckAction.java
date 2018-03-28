package com.wkmk.vwh.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduKnopointInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.edu.service.EduSubjectInfoManager;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmKnopoint;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmKnopointManager;
import com.wkmk.vwh.service.VwhFilmPixManager;
import com.wkmk.vwh.web.form.VwhFilmInfoActionForm;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 视频库我的视频信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmInfoCheckAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String actor = Encode.nullToBlank(httpServletRequest.getParameter("actor"));//视频主讲教师
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		
		Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		
		VwhFilmInfoManager manager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", unitid);
		SearchCondition.addCondition(condition, "status", "=", "0");
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
		
		//查询出所有有影片未转码完成的视频id
		VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
		List notconvertfilmids = vfpm.getAllNotConvertPixOfFilmids(unitid);
		httpServletRequest.setAttribute("notconvertfilmids", notconvertfilmids);
		
		httpServletRequest.setAttribute("pagelist", page);
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
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		String actor = Encode.nullToBlank(httpServletRequest.getParameter("actor"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
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
				String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
				EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
				String gradeid = Encode.nullToBlank(httpServletRequest.getParameter("gradeid"));
				EduGradeInfo egi = egim.getEduGradeInfo(gradeid);
				
				VwhFilmInfo model = form.getVwhFilmInfo();
				VwhFilmInfo vfi = manager.getVwhFilmInfo(model.getFilmid());
				vfi.setTitle(model.getTitle());
				vfi.setSketch(model.getSketch());
				vfi.setActor(model.getActor());
				vfi.setComputerid(model.getComputerid());
				vfi.setKeywords(model.getKeywords());
				vfi.setDescript(model.getDescript());
				vfi.setEduGradeInfo(egi);
				if("1".equals(status)){
					vfi.setStatus("1");
					if("default.gif".equals(vfi.getSketch())){//如果视频没有缩略图则默认采用第一个影片的缩略图
						VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
						List<SearchModel> condition = new ArrayList<SearchModel>();
						SearchCondition.addCondition(condition, "filmid", "=", vfi.getFilmid());
						SearchCondition.addCondition(condition, "convertstatus", "=", "1");
						SearchCondition.addCondition(condition, "fileext", "<>", "swf");
						List list = vfpm.getVwhFilmPixs(condition, "orderindex", 1);
						if(list != null && list.size() > 0){
							VwhFilmPix pix = (VwhFilmPix) list.get(0);
							vfi.setSketch(pix.getImgpath());
							vfi.setSketchimg(pix.getImgpath());
						}
					}
				}else {
					vfi.setStatus("0");
				}
				manager.updateVwhFilmInfo(vfi);
				
				if("1".equals(vfi.getStatus())){
					//需要同时审核视频关联的影片修改状态
					VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
					List<SearchModel> condition = new ArrayList<SearchModel>();
					SearchCondition.addCondition(condition, "filmid", "=", vfi.getFilmid());
					SearchCondition.addCondition(condition, "updateflag", "<>", "0");
					List list = vfpm.getVwhFilmPixs(condition, "", 0);
					VwhFilmPix pix = null;
					for(int i=0, size=list.size(); i<size; i++){
						pix = (VwhFilmPix) list.get(i);
						pix.setUpdateflag("0");
						pix.setUpdatetime(DateTime.getDate());
						vfpm.updateVwhFilmPix(pix);
					}
					
					addLog(httpServletRequest,"审核了一个微课程【" + vfi.getTitle() + "】信息");
				}else {
					addLog(httpServletRequest,"修改了一个微课程【" + vfi.getTitle() + "】信息");
				}
				
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
			model.setStatus("9");//存入回收站
			manager.updateVwhFilmInfo(model);
			addLog(httpServletRequest,"删除了一个微课程【" + model.getTitle() + "】信息");
		}
		
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 *批量审核
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward checkBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) throws Exception {
		VwhFilmInfoManager manager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");

		VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		List list = null;
		List lst = null;
		VwhFilmPix pix = null;
		
		VwhFilmInfo model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getVwhFilmInfo(checkids[i]);
			model.setStatus("1");
			if("default.gif".equals(model.getSketch())){//如果视频没有缩略图则默认采用第一个影片的缩略图
				condition.clear();
				SearchCondition.addCondition(condition, "filmid", "=", model.getFilmid());
				SearchCondition.addCondition(condition, "convertstatus", "=", "1");
				SearchCondition.addCondition(condition, "fileext", "<>", "swf");
				list = vfpm.getVwhFilmPixs(condition, "orderindex", 1);
				if(list != null && list.size() > 0){
					pix = (VwhFilmPix) list.get(0);
					model.setSketch(pix.getImgpath());
					model.setSketchimg(pix.getImgpath());
				}
			}
			manager.updateVwhFilmInfo(model);
			addLog(httpServletRequest,"审核了一个微课程【" + model.getTitle() + "】信息");
			
			//需要同时审核视频关联的影片修改状态
			condition.clear();
			SearchCondition.addCondition(condition, "filmid", "=", model.getFilmid());
			SearchCondition.addCondition(condition, "updateflag", "<>", "0");
			list = vfpm.getVwhFilmPixs(condition, "", 0);
			for(int k=0, size=list.size(); k<size; k++){
				pix = (VwhFilmPix) list.get(k);
				pix.setUpdateflag("0");
				pix.setUpdatetime(DateTime.getDate());
				vfpm.updateVwhFilmPix(pix);
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
}