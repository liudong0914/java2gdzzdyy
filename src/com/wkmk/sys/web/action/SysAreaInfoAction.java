package com.wkmk.sys.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.web.form.SysAreaInfoActionForm;

/**
 * <p>
 * Description: 地区信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class SysAreaInfoAction extends BaseAction {

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
		SysAreaInfoManager manager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageSysAreaInfos(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("pagelist", page);
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
		SysAreaInfo model = new SysAreaInfo();
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("act", "addSave");
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
		SysAreaInfoActionForm form = (SysAreaInfoActionForm) actionForm;
		SysAreaInfoManager manager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysAreaInfo model = form.getSysAreaInfo();
				manager.addSysAreaInfo(model);
				addLog(httpServletRequest, "增加了一个地区信息");
			} catch (Exception e) {
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
		SysAreaInfoManager manager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysAreaInfo model = manager.getSysAreaInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
		} catch (Exception e) {
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
		SysAreaInfoActionForm form = (SysAreaInfoActionForm) actionForm;
		SysAreaInfoManager manager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysAreaInfo model = form.getSysAreaInfo();
				manager.updateSysAreaInfo(model);
				addLog(httpServletRequest, "修改了一个地区信息");
			} catch (Exception e) {
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
		SysAreaInfoManager manager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delSysAreaInfo(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 级联查询市-区
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
	public ActionForward getAareByCitycode(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));// 默认1,1表示没有“请选择”，2表示有“请选择”
		String citycode = Encode.nullToBlank(httpServletRequest.getParameter("citycode"));
		// 获取所有省地区
		SysAreaInfoManager manager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		SysAreaInfo sysAreaInfo = manager.getSysAreaInfosByCitycode(citycode);
		List list = manager.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
		StringBuffer str = new StringBuffer();
		if ("2".equals(type)) {
			str.append("<option value=''>请选择...</option>");
		}
		if ("3".equals(type)) {
			str.append("<option value=''>请选择</option>");
		}
		SysAreaInfo sai = null;
		for (int i = 0, size = list.size(); i < size; i++) {
			sai = (SysAreaInfo) list.get(i);
			str.append("<option value='").append(sai.getCitycode()).append("'>").append(sai.getAreaname()).append("</option>");
		}
		PrintWriter pw = null;
		try {
			pw = httpServletResponse.getWriter();
			pw.write(str.toString());
		} catch (IOException ex) {
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}

	/**
	 * 导入前
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
	public ActionForward beforeImport(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("beforeimport");
	}

	/**
	 * 导入时保存
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
	public ActionForward importSave(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		InputStream stream = null;
		OutputStream bos = null;
		Workbook workBook = null;
		try {
			SysAreaInfoActionForm form = (SysAreaInfoActionForm) actionForm;
			SysAreaInfoManager manager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			FormFile file = form.getThefile();// 从Form类中获得上传的文件流
			String encoding = httpServletRequest.getCharacterEncoding();
			if ((encoding != null) && (encoding.equalsIgnoreCase("utf-8"))) {
				httpServletResponse.setContentType("text/html; charset=utf-8");
			}
			// 开始上传文件
			String rootpath = httpServletRequest.getRealPath("/upload"); // 取当前系统路径
			String sFileExt = FileUtil.getFileExt(file.getFileName());
			stream = file.getInputStream(); // 把文件读入
			String filename = DateTime.getDate("yyyyMMddHHmmssS") + "." + sFileExt;
			String filepath = rootpath + "/tempfile";
			// 检查文件夹是否存在,如果不存在,新建一个
			if (!FileUtil.isExistDir(filepath)) {
				FileUtil.creatDir(filepath);
			}
			bos = new FileOutputStream(filepath + "/" + filename);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
				bos.write(buffer, 0, bytesRead); // 将文件写入服务器
			}
			bos.close();
			stream.close();
			File importExcel = new File(filepath + "/" + filename);
			workBook = Workbook.getWorkbook(importExcel);
			Sheet[] sheet = workBook.getSheets();
			SysAreaInfo model = null;
			Map<String, String> map = new HashMap<String, String>();
			int province = 0;
			int city = 0;
			int county = 0;
			int sheet_i_num = 0;// 获取工作表格的行数
			if (sheet != null && sheet.length > 0) {
				// 这里首先要得到要读取的工作表格有多少行
				sheet_i_num = sheet[0].getRows();
				// 接下来就是对每一行进行的取数据了,此处从rowNum = 1开始,第一行一般是标题
				for (int rowNum = 1; rowNum < sheet_i_num; rowNum++) {
					// 这里就开始对每一个单元格进行操作了.
					// 显然,sheet[]第一个参数就是哪一个工作表格,然后getRow的就是哪一行.然后就赋值给Cell进行操作.
					Cell[] cells = sheet[0].getRow(rowNum);
					model = new SysAreaInfo();
					model.setAreaname(getCellsContents(cells, 1));
					if ("1".equals(getCellsContents(cells, 4))) {
						province++;
						city = 0;// 重新初始化
						county = 0;
						model.setParentno("00");
						if (province < 10) {
							model.setAreano(model.getParentno() + "0" + province);
						} else {
							model.setAreano(model.getParentno() + province);
						}
					} else if ("2".equals(getCellsContents(cells, 4))) {
						city++;
						county = 0;
						model.setParentno(map.get(getCellsContents(cells, 2)));
						if (city < 10) {
							model.setAreano(model.getParentno() + "0" + city);
						} else {
							model.setAreano(model.getParentno() + city);
						}
					} else {
						county++;
						model.setParentno(map.get(getCellsContents(cells, 2)));
						if (county < 10) {
							model.setAreano(model.getParentno() + "0" + county);
						} else {
							model.setAreano(model.getParentno() + county);
						}
					}
					model.setCitycode(getCellsContents(cells, 0));
					model.setPostcode(getCellsContents(cells, 6));
					model.setTelecode(getCellsContents(cells, 5));
					model.setPinyin(getCellsContents(cells, 7));
					model.setShortname(getCellsContents(cells, 3));
					manager.addSysAreaInfo(model);
					map.put(model.getCitycode(), model.getAreano());
				}
			}
			workBook.close();
		} catch (BiffException e) {
			e.printStackTrace();
			httpServletRequest.setAttribute("promptinfo", "地区信息导入失败!");
			return actionMapping.findForward("failure");
		} catch (IOException e) {
			e.printStackTrace();
			httpServletRequest.setAttribute("promptinfo", "地区信息导入失败!");
			return actionMapping.findForward("failure");
		} finally {
			try {
				if (stream != null) {
					stream.close();
				}
				stream = null;
				if (bos != null) {
					bos.close();
				}
				bos = null;
				if (workBook != null) {
					workBook.close();
				}
				workBook = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		httpServletRequest.setAttribute("promptinfo", "地区信息导入成功!");
		return actionMapping.findForward("success");
	}

	/*
	 * 获取excel表格中的数据，如果没有则会报数值越界异常
	 */
	private String getCellsContents(Cell[] cells, int orderindex) {
		String str = "";
		try {
			if (cells[orderindex] != null) {
				str = Encode.nullToBlank(cells[orderindex].getContents()).trim();
			}
		} catch (Exception e) {
			str = "";
		}
		return str;
	}
}