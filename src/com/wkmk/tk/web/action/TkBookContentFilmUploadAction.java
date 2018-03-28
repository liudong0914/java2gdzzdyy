package com.wkmk.tk.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.bo.EduSubjectInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkBookInfo;
import com.wkmk.tk.service.TkBookContentFilmManager;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookContentPowerManager;
import com.wkmk.tk.service.TkBookInfoManager;
import com.wkmk.tk.web.form.TkBookContentFilmActionForm;
import com.wkmk.util.common.Constants;
import com.wkmk.util.common.TwoCodeUtil;
import com.wkmk.util.file.ConvertFile;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmKnopoint;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmKnopointManager;
import com.wkmk.vwh.service.VwhFilmPixManager;
import com.wkmk.vwh.web.form.VwhFilmInfoActionForm;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 作业本微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmUploadAction extends BaseAction {

	/**
	 *审核，列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		
		HttpSession session = httpServletRequest.getSession();
		Integer userid = (Integer) session.getAttribute("s_userid");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "bookcontentid", "=", bookcontentid);
		SearchCondition.addCondition(condition, "sysUserInfo.userid", "=", userid);
		if(!"".equals(title)){
			SearchCondition.addCondition(condition, "title", "like", "%" + title + "%");
		}
		//SearchCondition.addCondition(condition, "status", "=", "0");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		PageList page = manager.getPageTkBookContentFilms(condition, "a.orderindex", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("title", title);
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
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		httpServletRequest.setAttribute("act", "addSave");

		saveToken(httpServletRequest);
		return actionMapping.findForward("add");
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
		HttpSession session = httpServletRequest.getSession();
		SysUserInfo sysUserInfo = (SysUserInfo) session.getAttribute("s_sysuserinfo");
		try {
			String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
			String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
			
			TkBookContentManager tbcm = (TkBookContentManager) getBean("tkBookContentManager");
			TkBookInfoManager tbim = (TkBookInfoManager) getBean("tkBookInfoManager");
			EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
			TkBookContent tkBookContent = tbcm.getTkBookContent(bookcontentid);
			TkBookInfo tkBookInfo = tbim.getTkBookInfo(tkBookContent.getBookid());
			EduGradeInfo eduGradeInfo = egim.getEduGradeInfo(tkBookInfo.getGradeid());
			
			VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
			VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
			TkBookContentFilmManager manager = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
			
			//视频影片
			String[] filename = httpServletRequest.getParameterValues("filename");
			String[] filepath = httpServletRequest.getParameterValues("filepath");
			String[] filesize = httpServletRequest.getParameterValues("filesize");
			
			if(filename != null && filename.length > 0){
				String fext = null;
				String fname = null;
				TkBookContentFilm tkFilm = null;
				VwhFilmInfo model = null;
				VwhFilmPix pix = null;
				String[] paths = null;
				Integer orderindex = null;
				for(int i=0, size=filename.length; i<size; i++){
					if(filepath[i] == null || "".equals(filepath[i]) || filepath[i].indexOf("error_") != -1){
		    			continue;
		    		}
					if(filepath[i].indexOf("exist_") != -1){
						//可以重复上传，只给用户提示，除非用户自己删除
						filepath[i] = filepath[i].substring(6);
					}
					paths = filepath[i].split(";");
					fext = FileUtil.getFileExt(filename[i]).toLowerCase();
		    		fname = filename[i].substring(0,filename[i].lastIndexOf("."));
		    		
		    		model = new VwhFilmInfo();
					model.setTitle(fname);
					model.setKeywords(model.getTitle());
					model.setDescript(model.getTitle());
					model.setActor(sysUserInfo.getUsername());
					model.setSketch("jtwk.jpg");//默认图片
					model.setSketchimg(model.getSketch());
					model.setHits(0);
					model.setStatus("1");//已审核,微课无需审核，只审核微课与作业本内容关联属性
					model.setOrderindex(1);
					model.setCreatedate(DateTime.getDate());
					model.setUpdatetime(model.getCreatedate());
					model.setEduGradeInfo(eduGradeInfo);
					model.setSysUserInfo(sysUserInfo);
					model.setComputerid(Constants.DEFAULT_COMPUTERID);//默认用户上传不能选择服务器，只有管理员管理视频时可以改
					model.setUnitid(sysUserInfo.getUnitid());
					vfim.addVwhFilmInfo(model);
					model.setTwocodepath(TwoCodeUtil.getTwoCodePath(model, httpServletRequest));
					vfim.updateVwhFilmInfo(model);
					
					pix = new VwhFilmPix();
		    		pix.setName(fname);
		    		pix.setSrcpath(paths[0]);
	    			pix.setFlvpath(paths[0]);
		    		pix.setImgpath("jtwk.jpg");
		    		pix.setConvertstatus("1");
		    		pix.setNotifystatus("1");
		    		pix.setImgwidth(0);
		    		pix.setImgheight(0);
		    		pix.setSecond("15");
		    		pix.setFilesize(Long.valueOf(filesize[i]));
		    		pix.setFileext(fext);
		    		pix.setTimelength("0");
		    		pix.setResolution("0");
		    		pix.setOrderindex(1);
		    		pix.setCreatedate(DateTime.getDate());
		    		pix.setFilmid(model.getFilmid());
		    		pix.setUnitid(sysUserInfo.getUnitid());
		    		pix.setUpdateflag("0");
		    		pix.setUpdatetime(DateTime.getDate());
		    		pix.setMd5code(paths[1]);
		    		vfpm.addVwhFilmPix(pix);
					
		    		//根据文件名，自动获取微课排序
		    		int index = fname.lastIndexOf("题") + 1;
		    		if(fname.length() - index > 1){
		    			try {
		    				orderindex = Integer.valueOf(fname.substring(index, index+2));
						} catch (NumberFormatException e) {
							//此格式会报字符串转数字报错：当fname=16年数学北师七下作业13题9作者名
							orderindex = Integer.valueOf(fname.substring(index, index+1));
						}
		    		}else {
		    			orderindex = Integer.valueOf(fname.substring(index, index+1));
		    		}
		    		
		    		tkFilm = new TkBookContentFilm();
		    		tkFilm.setTitle(fname);
		    		tkFilm.setOrderindex(orderindex);
		    		tkFilm.setType(type);
		    		tkFilm.setStatus("0");
		    		tkFilm.setBookcontentid(Integer.valueOf(bookcontentid));
		    		tkFilm.setBookid(tkBookInfo.getBookid());
		    		tkFilm.setFilmid(model.getFilmid());
		    		tkFilm.setSysUserInfo(sysUserInfo);
		    		tkFilm.setCreatedate(DateTime.getDate());
		    		tkFilm.setHits(0);
		    		manager.addTkBookContentFilm(tkFilm);
		    		tkFilm.setFilmtwocode(TwoCodeUtil.getTwoCodePath(tkFilm, httpServletRequest));
		    		manager.updateTkBookContentFilm(tkFilm);
				}
			}
			
		}catch (Exception e){
			httpServletRequest.setAttribute("promptinfo", "微课上传失败,请重试!");
			return actionMapping.findForward("failure");
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
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		try {
			TkBookContentFilm model = manager.getTkBookContentFilm(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		}catch (Exception e){
			e.printStackTrace();
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
		TkBookContentFilmActionForm form = (TkBookContentFilmActionForm)actionForm;
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		SysUserInfoManager userinfoManager = (SysUserInfoManager)getBean("sysUserInfoManager");
		String userid = Encode.nullToBlank(httpServletRequest.getParameter("userid"));
		SysUserInfo userInfo = userinfoManager.getSysUserInfo(userid);
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentFilm model = form.getTkBookContentFilm();
				model.setSysUserInfo(userInfo);
				manager.updateTkBookContentFilm(model);
				addLog(httpServletRequest,"修改了一个作业本微课");
			}catch (Exception e){
				e.printStackTrace();
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
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		TkBookContentFilm model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getTkBookContentFilm(checkids[i]);
			manager.delTkBookContentFilm(checkids[i]);
			addLog(httpServletRequest,"删除了一个解题微课程【" + model.getTitle() + "】信息");
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
		TkBookContentFilmManager manager = (TkBookContentFilmManager)getBean("tkBookContentFilmManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		TkBookContentFilm model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getTkBookContentFilm(checkids[i]);
			model.setStatus("1");
			manager.updateTkBookContentFilm(model);
			addLog(httpServletRequest,"审核了一个微课程【" + model.getTitle() + "】信息");
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
		SysUnitInfo sysUnitinfo = (SysUnitInfo) session.getAttribute("s_sysunitinfo");
		// 查询学科
		EduGradeInfoManager manager = (EduGradeInfoManager) getBean("eduGradeInfoManager");
		List<EduSubjectInfo> subjectList = manager.getAllSubjectByUnitType(sysUnitinfo.getType());
		StringBuffer tree = new StringBuffer();
		String text = null;
		String target = "rfrmright";
		String url = null;
		EduSubjectInfo esi = null;
		for (int i = 0; i < subjectList.size(); i++) {
			esi = (EduSubjectInfo) subjectList.get(i);
			//临时改：只显示数、理、化
			//if(esi.getSubjectid().intValue() != 12 && esi.getSubjectid().intValue() != 14 && esi.getSubjectid().intValue() != 15){
			//	continue;
			//}
			text = esi.getSubjectname();
			url = "javascript:;";
			tree.append("\n").append("tree").append(".nodes[\"0000_").append(esi.getSubjectid()).append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询相关作业本
		TkBookInfoManager bookinfoManager = (TkBookInfoManager) getBean("tkBookInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "status", "=", "1");
		//临时改：只要数、理、化下册书本，方便选择
		//SearchCondition.addCondition(condition, "part", "<>", "1' and (a.subjectid=12 or a.subjectid=14 or a.subjectid=15) and 1='1");//下册
		List<TkBookInfo> bookinfoList = bookinfoManager.getTkBookInfos(condition, "bookno", 0);
		TkBookInfo bookinfo = null;
		for (int i = 0; i < bookinfoList.size(); i++) {
			bookinfo = (TkBookInfo) bookinfoList.get(i);
			text = bookinfo.getTitle() + "（" + Constants.getCodeTypevalue("version", bookinfo.getVersion()) + "）";
			url="javascript:;";
			tree.append("\n").append("tree").append(".nodes[\"").append(bookinfo.getSubjectid() + "_").append(bookinfo.getBookid() + "0000").append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		// 查询相关章节
		TkBookContentManager bookcontentManager = (TkBookContentManager) getBean("tkBookContentManager");
		List<SearchModel> condition2 = new ArrayList<SearchModel>();
		List<TkBookContent> contentList = bookcontentManager.getTkBookContents(condition2, "contentno", 0);
		TkBookContent bookcontent = null;
		for (int i = 0; i < contentList.size(); i++) {
			bookcontent = (TkBookContent) contentList.get(i);
			text = bookcontent.getTitle();
			url = "/tkBookContentFilmUploadAction.do?method=list&bookcontentid=" + bookcontent.getBookcontentid();
			tree.append("\n").append("tree").append(".nodes[\"").append(bookcontent.getBookid() + bookcontent.getParentno() + "_" + bookcontent.getBookid() + bookcontent.getContentno())
					.append("\"]=\"");
			if (text != null && !"".equals(text.trim())) {
				tree.append("text:").append(text).append(";");
			}
			tree.append("url:").append(url).append(";").append("target:").append(target).append(";").append("\";");
		}
		String rooturl = "javascript:;";
		httpServletRequest.setAttribute("treenode", tree.toString());
		httpServletRequest.setAttribute("rooturl", rooturl);
		return actionMapping.findForward("tree");
	}

}