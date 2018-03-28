package com.wkmk.vwh.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.des.DesUtil;
import com.util.encrypt.DES;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.util.file.ConvertFile;
import com.wkmk.vwh.bo.VwhComputerInfo;
import com.wkmk.vwh.bo.VwhFilmInfo;
import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhComputerInfoManager;
import com.wkmk.vwh.service.VwhFilmInfoManager;
import com.wkmk.vwh.service.VwhFilmPixManager;
import com.wkmk.vwh.web.form.VwhFilmPixActionForm;

/**
 *<p>Description: 视频库视频影片</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmPixAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
		String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
		String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
		String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "filmid", "=", filmid);
		if(!"".equals(name)){
			SearchCondition.addCondition(condition, "name", "like", "%" + name + "%");	
		}
		if(!"".equals(convertstatus)){
			SearchCondition.addCondition(condition, "convertstatus", "=", convertstatus);	
		}
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "orderindex asc";
		if(!"".equals(pageUtil.getOrderindex())){
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageVwhFilmPixs(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("filmid", filmid);
		httpServletRequest.setAttribute("name", name);
		httpServletRequest.setAttribute("convertstatus", convertstatus);
		httpServletRequest.setAttribute("flag", flag);
		httpServletRequest.setAttribute("mark", mark);
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
		String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
		String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
		String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
		String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		httpServletRequest.setAttribute("mark", mark);
		httpServletRequest.setAttribute("filmid", filmid);
		httpServletRequest.setAttribute("name", name);
		httpServletRequest.setAttribute("convertstatus", convertstatus);
		httpServletRequest.setAttribute("flag", flag);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		VwhFilmPix model = new VwhFilmPix();
		httpServletRequest.setAttribute("model", model);
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
		if (isTokenValid(httpServletRequest, true)) {
			VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
			try {
				String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
				HttpSession session = httpServletRequest.getSession();
				Integer unitid = (Integer) session.getAttribute("s_unitid");
				
				String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
				String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
				httpServletRequest.setAttribute("mark", mark);
				//视频影片
				String[] filename = httpServletRequest.getParameterValues("filename");
				String[] filepath = httpServletRequest.getParameterValues("filepath");
				String[] filesize = httpServletRequest.getParameterValues("filesize");
				//视频截屏参数
				String imgwidth = Encode.nullToBlank(httpServletRequest.getParameter("imgwidth"));
				String imgheight = Encode.nullToBlank(httpServletRequest.getParameter("imgheight"));
				String second = Encode.nullToBlank(httpServletRequest.getParameter("second"));
				
				if(filename != null && filename.length > 0){
					String fext = null;
					String fname = null;
					VwhFilmPix pix = null;
					String[] paths = null;
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
			    		
			    		pix = new VwhFilmPix();
			    		pix.setName(fname);
			    		pix.setSrcpath(paths[0]);
			    		//转码后的路径统一通过接口修改
		    			//pix.setFlvpath(filepath[i].substring(0,filepath[i].lastIndexOf(".")) + ".mp4");
		    			pix.setImgpath("convert.png");
		    			pix.setConvertstatus("0");
			    		pix.setImgwidth(Integer.valueOf(imgwidth));
			    		pix.setImgheight(Integer.valueOf(imgheight));
			    		pix.setSecond(second);
			    		pix.setFilesize(Long.valueOf(filesize[i]));
			    		pix.setFileext(fext);
			    		pix.setTimelength("0");
			    		pix.setResolution("0");
			    		pix.setOrderindex(i+1);
			    		pix.setCreatedate(DateTime.getDate());
			    		pix.setFilmid(Integer.valueOf(filmid));
			    		pix.setUnitid(unitid);
			    		pix.setUpdateflag("1");
			    		pix.setUpdatetime(DateTime.getDate());
			    		pix.setMd5code(paths[1]);
			    		manager.addVwhFilmPix(pix);
			    		addLog(httpServletRequest,"增加了一个微课程视频【" + pix.getName() + "】");
			    		
			    		//开始转码
			    		ConvertFile.convertVod(pix, "add", 0);
			    		manager.updateVwhFilmPix(pix);
					}
					
					if(!"1".equals(flag)){
						//重新上传影片，视频需要重新审核
						VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
						VwhFilmInfo vfi = vfim.getVwhFilmInfo(filmid);
						vfi.setStatus("0");
						vfi.setUpdatetime(DateTime.getDate());
						vfim.updateVwhFilmInfo(vfi);
					}
				}
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
		String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
		String filmid = Encode.nullToBlank(httpServletRequest.getParameter("filmid"));
		String name = Encode.nullToBlank(httpServletRequest.getParameter("name"));
		String convertstatus = Encode.nullToBlank(httpServletRequest.getParameter("convertstatus"));
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		httpServletRequest.setAttribute("mark", mark);
		httpServletRequest.setAttribute("filmid", filmid);
		httpServletRequest.setAttribute("name", name);
		httpServletRequest.setAttribute("convertstatus", convertstatus);
		httpServletRequest.setAttribute("flag", flag);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			VwhFilmPix model = manager.getVwhFilmPix(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
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
		VwhFilmPixActionForm form = (VwhFilmPixActionForm)actionForm;
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		httpServletRequest.setAttribute("mark", mark);
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String flag = Encode.nullToBlank(httpServletRequest.getParameter("flag"));//1表示管理员身份
				VwhFilmPix model = form.getVwhFilmPix();
				VwhFilmPix vfp = manager.getVwhFilmPix(model.getPixid());
				if("0".equals(vfp.getUpdateflag())){
					vfp.setUpdateflag("2");//只修改了影片属性值
					vfp.setUpdatetime(DateTime.getDate());
				}
				vfp.setImgpath(model.getImgpath());
				vfp.setName(model.getName());
				vfp.setOrderindex(model.getOrderindex());
				manager.updateVwhFilmPix(vfp);
				addLog(httpServletRequest,"修改了一个微课程视频【" + model.getName() + "】");
				
				if(!"1".equals(flag)){
					//重新修改影片属性值，视频需要重新审核
					VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
					VwhFilmInfo vfi = vfim.getVwhFilmInfo(vfp.getFilmid());
					vfi.setStatus("0");
					vfi.setUpdatetime(DateTime.getDate());
					vfim.updateVwhFilmInfo(vfi);
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
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		httpServletRequest.setAttribute("mark", mark);

		VwhFilmPix model = null;		for (int i = 0; i < checkids.length; i++) {
			model = manager.getVwhFilmPix(checkids[i]);
			//如果视频还没有转码成功，则需要告知转码服务器已删除此视频
			if(!"1".equals(model.getConvertstatus())){
				ConvertFile.convertVod(model, "delete", 0);
			}
			manager.delVwhFilmPix(model);
			addLog(httpServletRequest,"删除了一个微课程视频【" + model.getName() + "】");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 *设置封面
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward setPhoto(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String mark = Encode.nullToBlank(httpServletRequest.getParameter("mark"));
		httpServletRequest.setAttribute("filmid", mark);

		if(checkids != null && checkids.length == 1){
			VwhFilmPix model = manager.getVwhFilmPix(checkids[0]);
			VwhFilmInfo vfi = vfim.getVwhFilmInfo(model.getFilmid());
			vfi.setSketch(model.getImgpath());
			vfi.setSketchimg(vfi.getSketch());
			vfim.updateVwhFilmInfo(vfi);
			httpServletRequest.setAttribute("msg", "微课封面设置成功!");
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 *影片预览
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward preview(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		try {
		VwhFilmPixManager manager = (VwhFilmPixManager)getBean("vwhFilmPixManager");
		VwhFilmInfoManager vfim = (VwhFilmInfoManager) getBean("vwhFilmInfoManager");
		String pixid = Encode.nullToBlank(httpServletRequest.getParameter("pixid"));
		VwhFilmPix model = manager.getVwhFilmPix(pixid);
		VwhFilmInfo vfi = vfim.getVwhFilmInfo(model.getFilmid());
		httpServletRequest.setAttribute("model", model);

		StringBuffer vurl = new StringBuffer();
		StringBuffer vurl1 = new StringBuffer();
		if("swf".equals(model.getFileext().toLowerCase())){
			VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
			VwhComputerInfo computerInfo = vcim.getVwhComputerInfo(vfi.getComputerid());
			vurl.append("http://").append(computerInfo.getIp()).append(":").append(computerInfo.getPort()).append("/upload/").append(model.getSrcpath());
			 httpServletRequest.setAttribute("playurl", DesUtil.encrypt(vurl.toString(), "cd4b635b494306cddf6a6e74a7b0b4d8"));
        }else {
        	String playurl=getUrl(model.getPixid().toString(), vfi.getComputerid().toString());
        	httpServletRequest.setAttribute("playurl", DesUtil.encrypt(playurl, "cd4b635b494306cddf6a6e74a7b0b4d8"));
        }
//		}else {
//			//拼接播放视频地址
//			vurl.append(DES.getEncryptPwd(model.getPixid().toString())).append("!|").append(DES.getEncryptPwd("1")).append("!|").append(DES.getEncryptPwd(vfi.getComputerid().toString()));
//			//html5播放视频地址
//			vurl1.append(DES.getEncryptPwd(model.getPixid().toString())).append("!").append(DES.getEncryptPwd("1")).append("!").append(DES.getEncryptPwd(vfi.getComputerid().toString()));
//		}
		httpServletRequest.setAttribute("vurl", vurl.toString());
		httpServletRequest.setAttribute("vurl1", vurl1.toString());
		
		return actionMapping.findForward("preview");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	 public String  getUrl(String pixid,String computerid){
	    	StringBuffer vurl = new StringBuffer();
				VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
				VwhComputerInfoManager vcim = (VwhComputerInfoManager) getBean("vwhComputerInfoManager");
				VwhFilmPix pix = vfpm.getVwhFilmPix(pixid);
				if(pix.getFlvpath()==null?false:pix.getFlvpath().startsWith("http://")){
					vurl.append(pix.getFlvpath());
				}else {
					VwhComputerInfo computer = vcim.getVwhComputerInfo(computerid);
					
					vurl.append("http://").append(computer.getIp());
					if(!"80".equals(computer.getPort())){
						vurl.append(":").append(computer.getPort());
					}
					vurl.append("/upload/").append(pix.getFlvpath());
				}
				if (pix.getFlvpath()==null) {
					return "";
				}
				return vurl.toString();
	    }
	    
}