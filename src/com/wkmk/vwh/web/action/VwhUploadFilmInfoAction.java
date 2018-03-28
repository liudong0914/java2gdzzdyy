package com.wkmk.vwh.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.edu.bo.EduGradeInfo;
import com.wkmk.edu.service.EduGradeInfoManager;
import com.wkmk.sys.bo.SysUserInfo;
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
import com.util.encrypt.Base64Utils;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.image.ZXingUtil;
import com.util.string.Encode;

/**
 *<p>Description: 视频库视频信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhUploadFilmInfoAction extends BaseAction {

	/**
	 *增加前
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeUpload(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String tag = Encode.nullToBlank(httpServletRequest.getParameter("tag"));//1表示从我的上传跳转过来，有返回按钮
		httpServletRequest.setAttribute("tag", tag);
		
		SysUserInfo sysUserInfo = (SysUserInfo) httpServletRequest.getSession().getAttribute("s_sysuserinfo");
		VwhFilmInfo model = new VwhFilmInfo();
		model.setSketch("default.gif");
		model.setActor(sysUserInfo.getUsername());
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "uploadSave");
		
		HttpSession session = httpServletRequest.getSession();
		String knopointid = (String) session.getAttribute("knopointid");
		if(knopointid != null && !"".equals(knopointid)){
			String subjectid = (String) session.getAttribute("subjectid");
			String subjectname = (String) session.getAttribute("subjectname");
			String gradeid = (String) session.getAttribute("gradeid");
			String gradename = (String) session.getAttribute("gradename");
			String knopointname = (String) session.getAttribute("knopointname");
			httpServletRequest.setAttribute("subjectid", subjectid);
			httpServletRequest.setAttribute("subjectname", subjectname);
			httpServletRequest.setAttribute("gradeid", gradeid);
			httpServletRequest.setAttribute("gradename", gradename);
			httpServletRequest.setAttribute("knopointid", knopointid);
			httpServletRequest.setAttribute("knopointname", knopointname);
		}else {
			httpServletRequest.setAttribute("subjectid", "");
			httpServletRequest.setAttribute("subjectname", "点击选择学科");
			httpServletRequest.setAttribute("gradeid", "");
			httpServletRequest.setAttribute("gradename", "点击选择年级");
			httpServletRequest.setAttribute("knopointid", "");
			httpServletRequest.setAttribute("gradename", "点击选择知识点");
		}
		
		return actionMapping.findForward("upload");
	}

	/**
	 *增加时保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward uploadSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		VwhFilmInfoActionForm form = (VwhFilmInfoActionForm)actionForm;
		VwhFilmInfoManager manager = (VwhFilmInfoManager)getBean("vwhFilmInfoManager");
		
		HttpSession session = httpServletRequest.getSession();
		SysUserInfo sysUserInfo = (SysUserInfo) session.getAttribute("s_sysuserinfo");
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		try {
			EduGradeInfoManager egim = (EduGradeInfoManager) getBean("eduGradeInfoManager");
			String gradeid = Encode.nullToBlank(httpServletRequest.getParameter("gradeid"));
			EduGradeInfo egi = egim.getEduGradeInfo(gradeid);
			
			VwhFilmInfo model = form.getVwhFilmInfo();
			model.setSketchimg(model.getSketch());
			model.setHits(0);
			model.setStatus("0");//待审核
			model.setOrderindex(1);
			model.setCreatedate(DateTime.getDate());
			model.setUpdatetime(model.getCreatedate());
			model.setEduGradeInfo(egi);
			model.setSysUserInfo(sysUserInfo);
			model.setComputerid(Constants.DEFAULT_COMPUTERID);//默认用户上传不能选择服务器，只有管理员管理视频时可以改
			model.setUnitid(unitid);
			manager.addVwhFilmInfo(model);
			addLog(httpServletRequest,"增加了一个微课程【" + model.getTitle() + "】信息");
			model.setTwocodepath(TwoCodeUtil.getTwoCodePath(model, httpServletRequest));
			manager.updateVwhFilmInfo(model);
			
			//视频影片
			VwhFilmPixManager vfpm = (VwhFilmPixManager) getBean("vwhFilmPixManager");
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
		    		pix.setFilmid(model.getFilmid());
		    		pix.setUnitid(unitid);
		    		pix.setUpdateflag("1");
		    		pix.setUpdatetime(DateTime.getDate());
		    		pix.setMd5code(paths[1]);
		    		vfpm.addVwhFilmPix(pix);
		    		
		    		//开始转码
		    		ConvertFile.convertVod(pix, "add", 0);
		    		vfpm.updateVwhFilmPix(pix);
				}
			}
			
			String knopointid = Encode.nullToBlank(httpServletRequest.getParameter("knopointid"));
			String knopointidupdate = Encode.nullToBlank(httpServletRequest.getParameter("knopointidupdate"));//1表示知识点有修改，需要删除先前数据然后重新保存
			//微课与知识点关联
			//if("1".equals(knopointidupdate) && !"".equals(knopointid)){//上传不需要判断
				VwhFilmKnopointManager vfkm = (VwhFilmKnopointManager) getBean("vwhFilmKnopointManager");
				String[] knopointids = knopointid.split(";");
				VwhFilmKnopoint vfk = new VwhFilmKnopoint();
				vfk.setFilmid(model.getFilmid());
				for(int i=0, size=knopointids.length; i<size; i++){
					vfk.setFid(null);
					vfk.setKnopointid(Integer.valueOf(knopointids[i]));
					vfkm.addVwhFilmKnopoint(vfk);
				}
			//}
			
			//记录学科-年级-知识点，方便下次上传自动填充
			String subjectid = Encode.nullToBlank(httpServletRequest.getParameter("subjectid"));
			String subjectname = Encode.nullToBlank(httpServletRequest.getParameter("subjectname"));
			String gradename = Encode.nullToBlank(httpServletRequest.getParameter("gradename"));
			String knopointname = Encode.nullToBlank(httpServletRequest.getParameter("knopointname"));
			session.setAttribute("subjectid", subjectid);
			session.setAttribute("subjectname", subjectname);
			session.setAttribute("gradeid", gradeid);
			session.setAttribute("gradename", gradename);
			session.setAttribute("knopointid", knopointid);
			session.setAttribute("knopointname", knopointname);
		}catch (Exception e){
			httpServletRequest.setAttribute("promptinfo", "微课上传失败,请重试!");
			return actionMapping.findForward("failure");
		}

		httpServletRequest.setAttribute("promptinfo", "微课上传成功,请等待管理员审核!");
		return actionMapping.findForward("success");
	}
}