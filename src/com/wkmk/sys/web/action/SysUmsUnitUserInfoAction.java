package com.wkmk.sys.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.encrypt.DES;
import com.util.encrypt.MD5;
import com.util.file.FileUtil;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.sys.bo.SysAreaInfo;
import com.wkmk.sys.bo.SysUnitInfo;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.bo.SysUserInfoDetail;
import com.wkmk.sys.service.SysAreaInfoManager;
import com.wkmk.sys.service.SysUnitInfoManager;
import com.wkmk.sys.service.SysUserInfoDetailManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.form.SysUserInfoActionForm;

/**
 * <p>
 * Description: 系统用户信息
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class SysUmsUnitUserInfoAction extends BaseAction {

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
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String unitid = Encode.nullToBlank(httpServletRequest.getParameter("unitid"));

		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "unitid", "=", Integer.valueOf(unitid));
		SearchCondition.addCondition(condition, "loginname", "<>", "~admin~");
		if (!"".equals(status)) {
			SearchCondition.addCondition(condition, "status", "=", status);
		}
		if (!"".equals(loginname)) {
			SearchCondition.addCondition(condition, "loginname", "like", "%" + loginname + "%");
		}
		if (!"".equals(username)) {
			SearchCondition.addCondition(condition, "username", "like", "%" + username + "%");
		}
		if (!"".equals(sex) && !"-1".equals(sex)) {
			SearchCondition.addCondition(condition, "sex", "=", sex);
		}
		if (!"".equals(usertype) && !"-1".equals(usertype)) {
			SearchCondition.addCondition(condition, "usertype", "=", usertype);
		}
		if (!"".equals(studentno)) {
			SearchCondition.addCondition(condition, "studentno", "like", "%" + studentno + "%");
		}

		PageUtil pageUtil = new PageUtil(httpServletRequest);
		String sorderindex = "loginname asc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		PageList page = manager.getPageSysUserInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());

		httpServletRequest.setAttribute("pagelist", page);
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		httpServletRequest.setAttribute("status", status);
		httpServletRequest.setAttribute("unitid", unitid);
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
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String unitid = Encode.nullToBlank(httpServletRequest.getParameter("unitid"));
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		httpServletRequest.setAttribute("status", status);
		httpServletRequest.setAttribute("unitid", unitid);
		// 分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);

		SysUserInfo model = new SysUserInfo();
		SysUserInfoDetail detail = new SysUserInfoDetail();
		model.setPhoto("man.jpg");
		model.setSex("0");
		model.setXueduan("2");
		model.setUnitid(Integer.valueOf(unitid));
		detail.setEducation("9");
		detail.setJobtitle("9");
		detail.setCanaddcourse("0");
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("detail", detail);
		httpServletRequest.setAttribute("act", "addSave");

		// 获取所有省地区
		SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		List provinceList = saim.getSysAreaInfosByParentno("00");
		httpServletRequest.setAttribute("provinceList", provinceList);

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
		SysUserInfoActionForm form = (SysUserInfoActionForm) actionForm;
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserInfo model = form.getSysUserInfo();
				// 将密码加密
				String password = Encode.nullToBlank(httpServletRequest.getParameter("password"));
				if (!"".equals(password)) {
					model.setPassword(MD5.getEncryptPwd(password));
				}
				model.setStatus("1");
				model.setMoney(0.00F);
				manager.addSysUserInfo(model);

				SysUserInfoDetail detail = form.getSysUserInfoDetail();
				if (detail.getCardno() != null && !"".equals(detail.getCardno())) {// 证件号加密存储
					detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
				}
				detail.setUserid(model.getUserid());
				detail.setFlag("0");
				detail.setCreatedate(DateTime.getDate());
				detail.setLastlogin(detail.getCreatedate());
				detail.setLogintimes(0);
				detail.setUpdatetime(detail.getCreatedate());
				suidm.addSysUserInfoDetail(detail);
				addLog(httpServletRequest, "增加了一个系统用户【" + model.getUsername() + "】信息");
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
		String loginname = Encode.nullToBlank(httpServletRequest.getParameter("loginname"));
		String username = Encode.nullToBlank(httpServletRequest.getParameter("username"));
		String sex = Encode.nullToBlank(httpServletRequest.getParameter("sex"));
		String usertype = Encode.nullToBlank(httpServletRequest.getParameter("usertype"));
		String studentno = Encode.nullToBlank(httpServletRequest.getParameter("studentno"));
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		String unitid = Encode.nullToBlank(httpServletRequest.getParameter("unitid"));
		httpServletRequest.setAttribute("loginname", loginname);
		httpServletRequest.setAttribute("username", username);
		httpServletRequest.setAttribute("sex", sex);
		httpServletRequest.setAttribute("usertype", usertype);
		httpServletRequest.setAttribute("studentno", studentno);
		httpServletRequest.setAttribute("status", status);
		httpServletRequest.setAttribute("unitid", unitid);
		// 分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);

		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysUserInfo model = manager.getSysUserInfo(objid);
			SysUserInfoDetail detail = suidm.getSysUserInfoDetail(model.getUserid());
			if (detail.getCardno() != null && !"".equals(detail.getCardno())) {// 证件号解密显示
				detail.setCardno(DES.getDecryptPwd(detail.getCardno()));
			}
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
			httpServletRequest.setAttribute("detail", detail);

			// 获取所有省地区
			SysAreaInfoManager saim = (SysAreaInfoManager) getBean("sysAreaInfoManager");
			List provinceList = saim.getSysAreaInfosByParentno("00");
			httpServletRequest.setAttribute("provinceList", provinceList);
			SysAreaInfo sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getProvince());
			List cityList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("cityList", cityList);
			sysAreaInfo = saim.getSysAreaInfosByCitycode(detail.getCity());
			List countyList = saim.getSysAreaInfosByParentno(sysAreaInfo.getAreano());
			httpServletRequest.setAttribute("countyList", countyList);
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
		SysUserInfoActionForm form = (SysUserInfoActionForm) actionForm;
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		SysUserInfoDetailManager suidm = (SysUserInfoDetailManager) getBean("sysUserInfoDetailManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserInfo model = form.getSysUserInfo();
				// 将密码加密
				String password = Encode.nullToBlank(httpServletRequest.getParameter("password"));
				if (!"".equals(password)) {
					model.setPassword(MD5.getEncryptPwd(password));
				}
				manager.updateSysUserInfo(model);

				SysUserInfoDetail detail = form.getSysUserInfoDetail();
				if (detail.getCardno() != null && !"".equals(detail.getCardno())) {// 证件号加密存储
					detail.setCardno(DES.getEncryptPwd(detail.getCardno()));
				}
				detail.setUserid(model.getUserid());
				suidm.updateSysUserInfoDetail(detail);
				addLog(httpServletRequest, "修改了一个系统用户【" + model.getUsername() + "】信息");
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
		SysUserInfoManager manager = (SysUserInfoManager) getBean("sysUserInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		String setstatus = Encode.nullToBlank(httpServletRequest.getParameter("setstatus"));

		SysUserInfo model = null;
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getSysUserInfo(checkids[i]);
			if ("1".equals(setstatus)) {
				model.setStatus("1");
				manager.updateSysUserInfo(model);
				addLog(httpServletRequest, "启用了一个系统用户【" + model.getUsername() + "】信息");
			} else if ("2".equals(setstatus)) {
				model.setStatus("2");
				manager.updateSysUserInfo(model);
				// manager.delSysUserInfo(checkids[i]);
				addLog(httpServletRequest, "禁用了一个系统用户【" + model.getUsername() + "】信息");
			}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}

	/**
	 * 导入学校页面
	 * */
	public ActionForward importSchool(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("school_import");
	}

	/**
	 * 导入学校
	 * */
	public ActionForward schoolImport(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserInfoActionForm form = (SysUserInfoActionForm) actionForm;
		SysUnitInfoManager manager = (SysUnitInfoManager) getBean("sysUnitInfoManager");
		SysAreaInfoManager areainfoManager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
		FormFile file = form.getThefile();
		String encoding = httpServletRequest.getCharacterEncoding();
		if ((encoding != null) && (encoding.equalsIgnoreCase("utf-8"))) {
			httpServletResponse.setContentType("text/html; charset=utf-8");
		}
		List<SearchModel> condiitoin = new ArrayList<SearchModel>();
		List<SysAreaInfo> areainfolist = areainfoManager.getSysAreaInfos(condiitoin, "areano", 0);
		Map<String, SysAreaInfo> map = new HashMap<String, SysAreaInfo>();
		for (int i = 0; i < areainfolist.size(); i++) {
			map.put(areainfolist.get(i).getAreaid().toString(), areainfolist.get(i));
		}
		InputStream stream = null;
		OutputStream bos = null;
		Workbook workBook = null;
		try {
			String rootpath = httpServletRequest.getRealPath("/upload_dir"); // 取当前系统路径
			String sFileExt = FileUtil.getFileExt(file.getFileName());
			stream = file.getInputStream(); // 把文件读入
			String filename = DateTime.getDate("yyyyMMddHHmmssS") + "." + sFileExt;
			String filepath = rootpath + "/tempfile";
			// 检查文件夹是否存在,如果不存在,新建一个
			if (!FileUtil.isExistDir(filepath)) {
				FileUtil.creatDir(filepath);
			}
			bos = new FileOutputStream(filepath + "/" + filename);
			int bytes = 0;
			byte[] buffer = new byte[1024];
			while ((bytes = stream.read(buffer, 0, 1024)) != -1) {
				bos.write(buffer, 0, bytes);
			}
			bos.close();
			stream.close();
			File importExcel = new File(filepath + "/" + filename);
			workBook = Workbook.getWorkbook(importExcel);
			Sheet[] sheet = workBook.getSheets();
			SysUnitInfo model = null;
			int sheet_i_num = 0;// 获取工作表格的行数
			if (sheet != null && sheet.length > 0) {
				// 这里首先要得到要读取的工作表格有多少行
				sheet_i_num = sheet[0].getRows();
				for (int i = 1; i < sheet_i_num; i++) {
					Cell[] cells = sheet[0].getRow(i);
					if (cells.length < 4)
						continue;
					model = new SysUnitInfo();
					String biaoshi = cells[0].getContents().trim();
					String areano = map.get(biaoshi).getAreano();
					if (areano.length() == 4) {
						// 如果为省
						model.setUnitname(getCellsContents(cells, 4));
						model.setProvince(map.get(biaoshi).getCitycode());
					} else if (areano.length() == 6) {
						// 如果为市
						SysAreaInfoManager areamanager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
						List<SearchModel> condition = new ArrayList<SearchModel>();
						SearchCondition.addCondition(condition, "areano", "=", map.get(biaoshi).getParentno());
						SysAreaInfo fathermodel = (SysAreaInfo) areainfoManager.getSysAreaInfos(condition, "areano", 0).get(0);
						model.setUnitname(getCellsContents(cells, 4));
						model.setProvince(fathermodel.getCitycode());
						model.setCity(map.get(biaoshi).getCitycode());
					} else if (areano.length() == 8) {
						// 如果为区
						SysAreaInfoManager areamanager = (SysAreaInfoManager) getBean("sysAreaInfoManager");
						List<SearchModel> condition = new ArrayList<SearchModel>();
						SearchCondition.addCondition(condition, "areano", "=", map.get(biaoshi).getParentno());
						SysAreaInfo fathermodel = (SysAreaInfo) areainfoManager.getSysAreaInfos(condition, "areano", 0).get(0);
						condition = new ArrayList<SearchModel>();
						SearchCondition.addCondition(condition, "areano", "=", fathermodel.getParentno());
						SysAreaInfo grandfathermodel = (SysAreaInfo) areainfoManager.getSysAreaInfos(condition, "areano", 0).get(0);
						model.setUnitname(getCellsContents(cells, 4));
						model.setCounty(map.get(biaoshi).getCitycode());
						model.setCity(fathermodel.getCitycode());
						model.setProvince(grandfathermodel.getCitycode());
					}
					manager.addSysUnitInfo(model);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return actionMapping.findForward("import_success");
	}

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