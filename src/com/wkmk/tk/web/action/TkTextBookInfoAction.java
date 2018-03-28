package com.wkmk.tk.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.tk.bo.TkPaperFile;
import com.wkmk.tk.bo.TkTextBookInfo;
import com.wkmk.tk.service.TkPaperFileManager;
import com.wkmk.tk.service.TkTextBookInfoManager;
import com.wkmk.tk.web.form.TkTextBookInfoActionForm;
import com.wkmk.util.common.Constants;
import com.wkmk.weixin.mp.MpUtil;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.FileUtil;
import com.util.file.zip.AntZipFile;
import com.util.image.ZXingUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.util.string.UUID;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkTextBookInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkTextBookInfoManager manager = (TkTextBookInfoManager)getBean("tkTextBookInfoManager");
		String textbookname = Encode.nullToBlank(httpServletRequest.getParameter("textbookname"));
		String author = Encode.nullToBlank(httpServletRequest.getParameter("author"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String createdate = Encode.nullToBlank(httpServletRequest.getParameter("createdate"));
		
		
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		if(!"".equals(textbookname)){
			SearchCondition.addCondition(condition, "textbookname", "like", "%" + textbookname + "%");
		}
		if(!"".equals(author)){
			SearchCondition.addCondition(condition, "author", "like", "%" + author + "%");
		}
		if(!"".equals(status)){
			SearchCondition.addCondition(condition, "status", "=", status);
		}
		if (createdate != null && createdate.trim().length() > 0) {
			SearchCondition.addCondition(condition, "createdate", "like", "%" + createdate + "%");;
		}
		PageList page = manager.getPageTkTextBookInfos(condition, "orderindex asc,createdate desc", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("textbookname", textbookname);
		httpServletRequest.setAttribute("author", author);
		httpServletRequest.setAttribute("status", status);
		httpServletRequest.setAttribute("createdate", createdate);
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
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		
		TkTextBookInfo model = new TkTextBookInfo();
		model.setSketch("default.gif");
		model.setUnitid(Integer.parseInt(httpServletRequest.getSession().getAttribute("s_unitid").toString()));
		model.setStatus("1");
		model.setSellcount(0);
		model.setCreatedate(DateTime.getDate());
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");

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
		TkTextBookInfoActionForm form = (TkTextBookInfoActionForm)actionForm;
		TkTextBookInfoManager manager = (TkTextBookInfoManager)getBean("tkTextBookInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkTextBookInfo model = form.getTkTextBookInfo();
				manager.addTkTextBookInfo(model);
				addLog(httpServletRequest,"增加了一个教材基本信息表");
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
		TkTextBookInfoManager manager = (TkTextBookInfoManager)getBean("tkTextBookInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			//分页与排序
			String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
			String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
			String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
			httpServletRequest.setAttribute("pageno", pageno);
			httpServletRequest.setAttribute("direction", direction);
			httpServletRequest.setAttribute("sort", sort);
			
			TkTextBookInfo model = manager.getTkTextBookInfo(objid);
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
		TkTextBookInfoActionForm form = (TkTextBookInfoActionForm)actionForm;
		TkTextBookInfoManager manager = (TkTextBookInfoManager)getBean("tkTextBookInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkTextBookInfo model = form.getTkTextBookInfo();
				manager.updateTkTextBookInfo(model);
				addLog(httpServletRequest,"修改了一个教材基本信息表");
			}catch (Exception e){
			}
		}

		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 *批量禁用
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward delBatchRecord(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		TkTextBookInfoManager manager = (TkTextBookInfoManager)getBean("tkTextBookInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String setstatus = Encode.nullToBlank(httpServletRequest.getParameter("setstatus"));
		for (int i = 0; i < checkids.length; i++) {
			TkTextBookInfo model = manager.getTkTextBookInfo(checkids[i]);
			if("1".equals(setstatus)){
				model.setStatus("1");//启用
				manager.updateTkTextBookInfo(model);
				addLog(httpServletRequest,"启用了一个教材【" + model.getTextbookname() + "】信息");
			}else if("2".equals(setstatus)){
				model.setStatus("2");//禁用
				manager.updateTkTextBookInfo(model);
				addLog(httpServletRequest,"禁用了一个教材【" + model.getTextbookname() + "】信息");
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/*
	 * 批量导出二维码
	 */
	public ActionForward exportBatchTwoCode(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkTextBookInfoManager manager = (TkTextBookInfoManager)getBean("tkTextBookInfoManager");
		String checkids = Encode.nullToBlank(httpServletRequest.getParameter("checkids"));
		checkids = checkids.substring(0,checkids.length()-1);
		String[] strArray = null;   
        strArray = checkids.split(",");
        
        String targetfile = httpServletRequest.getSession().getServletContext().getRealPath("/") + "uploadtemp/export/batchtextbookinfo/";
		if(!FileUtil.isExistDir(targetfile)){
			FileUtil.creatDir(targetfile);
		}
        
        if (strArray !=null && strArray.length>0) {
	        for (int i = 0; i < strArray.length; i++) {
	    		TkTextBookInfo model = manager.getTkTextBookInfo(strArray[i]);
	    		String twocodepath = "twocode/" + strArray[i] + ".png";
	    		//String filepath = httpServletRequest.getSession().getServletContext().getRealPath("/") + "uploadtemp/export/batchtextbookinfo/" +twocodepath;
	    		String filepath = targetfile + model.getTextbookname().replaceAll("/", "").replaceAll("\\\\", "").replaceAll(":", "").replaceAll("\\*", "").replaceAll("\\?", "").replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "").replaceAll("|", "").trim()  +  "_教材二维码.png"; // 全路径
	    		if (!FileUtil.isExistDir(filepath)) {
	    			FileUtil.creatDir(filepath);
	    		}
	    		String url = MpUtil.HOMEPAGE+"/twoCode.app?TkTextBookInfo=" + strArray[i];
	    		ZXingUtil.encodeQRCodeImage(url, null, filepath, 300, 300, null);
			}
        }

		// 自动下载zip文件包
		String uuid = UUID.getNewUUID();
		String realpath = httpServletRequest.getSession().getServletContext().getRealPath("/");
		String rootpath = realpath + "/uploadtemp/export";
		AntZipFile antZipFile = new AntZipFile();
		antZipFile.doZip(targetfile, rootpath, uuid + ".zip");

		// 下载包
		try {
			InputStream is = new FileInputStream(rootpath + "/" + uuid + ".zip");
			OutputStream os = httpServletResponse.getOutputStream();// 取得输出流
			httpServletResponse.reset();// 清空输出流
			httpServletResponse.setContentType("text/html; charset=utf-8");// text/html
			// application/octet-stream
			httpServletResponse.setContentType("bin");
//			String name = "试卷批量导出二维码"++".zip";
			String name = uuid + ".zip";
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + new String(name.getBytes("gb2312"), "ISO8859-1"));// gb2312
			byte[] buffer = new byte[1024];
			int byteread = 0;
			while ((byteread = is.read(buffer)) > 0) {
				os.write(buffer, 0, byteread);
			}
			os.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		File file2 = new File(targetfile);
		deleteFile(file2);
		return null;
	}
	
	/**
	 * 删除文件
	 * 
	 * @param file
	 */
	private void deleteFile(File file) {
		if (file.isFile()) {
			file.delete();
		} else {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
				file.delete();
			}
			file.delete();
		}
	}
}