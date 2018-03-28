package com.wkmk.skin.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.DES;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.tk.bo.TkBookContentFilm;
import com.wkmk.tk.bo.TkBookContentFilmAudition;
import com.wkmk.tk.bo.TkBookContentFilmAuditionWatch;
import com.wkmk.tk.service.TkBookContentFilmAuditionManager;
import com.wkmk.tk.service.TkBookContentFilmAuditionWatchManager;
import com.wkmk.tk.service.TkBookContentFilmManager;
import com.wkmk.util.common.IpUtil;
import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmPixManager;

/**
 * <p>Description: 作业宝模板001</p>
 * @version 1.0
 */
public class Zyb01Action extends BaseAction {

	/**
	 * 首页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward index(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mappingurl = "/error.html";
		try {
			//1、查询广告图片
			
			//2、试听微课
			TkBookContentFilmAuditionManager manager=(TkBookContentFilmAuditionManager)getBean("tkBookContentFilmAuditionManager");
			List filmlist = manager.getPageFilms("", "", 0, 8).getDatalist();
			request.setAttribute("filmlist", filmlist);
			
			mappingurl = "/skin/zyb01/jsp/index.jsp";
		} catch (Exception e) {
			mappingurl = "/error.html";
		}

		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}
	
	public void getFilmAuditionByAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
			String num = Encode.nullToBlank(request.getParameter("pagenum"));
			
			int pagesize = 8;
			int pagenum = Integer.parseInt(num);
			int pagecount = pagesize * pagenum;
			
			TkBookContentFilmAuditionManager manager=(TkBookContentFilmAuditionManager)getBean("tkBookContentFilmAuditionManager");
			List filmlist=manager.getPageFilms("", "", pagecount, pagesize).getDatalist();
			
			StringBuffer result = new StringBuffer();
			Object[] obj = null;
			VwhFilmInfo film = null;
			for (int i=0, size=filmlist.size(); i<size; i++) {
				obj = (Object[]) filmlist.get(i);
				film = (VwhFilmInfo)obj[0];
				result.append("<a href=\"/zyb-play-1-").append(obj[2]).append(".htm\" target=\"_blank\" title=\"").append(film.getTitle()).append("\"><div class=\"listen_main_module ");
				if(i%4 != 0){
					result.append("listen_main_module01");
				}
				result.append("\">");
				result.append("<img src=\"/upload/").append(film.getSketch()).append("\" /><p>").append(film.getTitle()).append("</p></div></a>");
			}
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 播放
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward play(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String mappingurl = "/error.html";
		try {
			String auditionid = Encode.nullToBlank(request.getParameter("objid"));
			TkBookContentFilmAuditionManager manager = (TkBookContentFilmAuditionManager) getBean("tkBookContentFilmAuditionManager");
			TkBookContentFilmAudition audition = manager.getTkBookContentFilmAudition(auditionid);
			audition.setHits(audition.getHits() + 1);
			manager.updateTkBookContentFilmAudition(audition);
			
			TkBookContentFilmManager tbcfm = (TkBookContentFilmManager) getBean("tkBookContentFilmManager");
			VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
			VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
			TkBookContentFilm contentFilm = tbcfm.getTkBookContentFilm(audition.getContentfilmid());
			VwhFilmInfo filmInfo = vfim.getVwhFilmInfo(contentFilm.getFilmid());
			
			List<SearchModel> condition=new ArrayList<SearchModel>();
			SearchCondition.addCondition(condition, "filmid", "=", filmInfo.getFilmid());
			List pixlist = vfpm.getVwhFilmPixs(condition, "orderindex", 0);
			VwhFilmPix filmPix = (VwhFilmPix) pixlist.get(0);
			request.setAttribute("audition", audition);
			request.setAttribute("contentFilm", contentFilm);
			request.setAttribute("filmInfo", filmInfo);
			request.setAttribute("filmPix", filmPix);
			request.setAttribute("playid", DES.getEncryptPwd(filmPix.getPixid().toString()));
			
			TkBookContentFilmAuditionWatchManager tbcfawm = (TkBookContentFilmAuditionWatchManager) getBean("tkBookContentFilmAuditionWatchManager");
			TkBookContentFilmAuditionWatch auditionWatch = new TkBookContentFilmAuditionWatch();
			auditionWatch.setUserid(1);//前台用户播放，以游客身份记录
			auditionWatch.setAuditionid(Integer.valueOf(auditionid));
			auditionWatch.setCreatedate(DateTime.getDate());
			auditionWatch.setUserip(IpUtil.getIpAddr(request));
			tbcfawm.addTkBookContentFilmAuditionWatch(auditionWatch);
			
			//2、试听微课
			TkBookContentFilmAuditionManager tbcfam=(TkBookContentFilmAuditionManager)getBean("tkBookContentFilmAuditionManager");
			List filmlist = tbcfam.getPageFilms("", "", 0, 8).getDatalist();
			request.setAttribute("filmlist", filmlist);
			
			mappingurl = "/skin/zyb01/jsp/play.jsp";
		} catch (Exception e) {
			mappingurl = "/error.html";
		}

		ActionForward gotourl = new ActionForward(mappingurl);
		gotourl.setPath(mappingurl);
		gotourl.setRedirect(false);
		return gotourl;
	}
	
	/**
	 * 视频播放地址转换
	 */
	public ActionForward url(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/plain;charset=gbk");
		PrintWriter out;
		StringBuffer vurl = new StringBuffer();
		try {
			String vid = Encode.nullToBlank(request.getParameter("vid"));
			if (vid != null && !"".equals(vid)) {
				String pixid = DES.getDecryptPwd(vid);
				if(pixid != null && !"".equals(pixid)){
					VwhFilmPixManager manager = (VwhFilmPixManager) getBean("vwhFilmPixManager");
					VwhFilmPix model = manager.getVwhFilmPix(pixid);
					if (model.getFlvpath() != null && model.getFlvpath().startsWith("http://")) {
						vurl.append(model.getFlvpath());
					} else {
						VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
						VwhFilmInfo vfi = vfim.getVwhFilmInfo(model.getFilmid());
						VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
						VwhComputerInfo vci = vcim.getVwhComputerInfo(vfi.getComputerid());
						vurl.append("http://").append(vci.getIp());
						if (!"80".equals(vci.getPort())) {
							vurl.append(":").append(vci.getPort());
						}
						vurl.append("/upload/" + model.getFlvpath());
					}
				}
			}

			out = response.getWriter();
			out.print(vurl.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
