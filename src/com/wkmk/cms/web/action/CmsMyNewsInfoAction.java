package com.wkmk.cms.web.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.cms.bo.CmsNewsInfo;
import com.wkmk.cms.service.CmsNewsInfoManager;
import com.wkmk.cms.web.form.CmsNewsInfoActionForm;

import com.wkmk.cms.bo.CmsNewsColumn;
import com.wkmk.cms.service.CmsNewsColumnManager;
import com.wkmk.sys.bo.SysUserInfo;

import com.util.action.BaseAction;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 资讯信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsMyNewsInfoAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
		httpServletRequest.setAttribute("columnid", columnid);
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer)session.getAttribute("s_unitid");
		SysUserInfo sysUserInfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
		String title = Encode.nullToBlank(httpServletRequest.getParameter("title"));
		httpServletRequest.setAttribute("title", title);
		String status = Encode.nullToBlank(httpServletRequest.getParameter("status"));
		httpServletRequest.setAttribute("status", status);
		CmsNewsInfoManager manager = (CmsNewsInfoManager)getBean("cmsNewsInfoManager");
		CmsNewsInfo model = new CmsNewsInfo();
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		String sorderindex = "happendate desc";
		if (!"".equals(pageUtil.getOrderindex())) {
			sorderindex = pageUtil.getOrderindex();
		}
		SearchCondition.addCondition(condition, "title", "like", "%"+title+"%");
		SearchCondition.addCondition(condition, "unitid", "=", Integer.valueOf(unitid));
		SearchCondition.addCondition(condition, "status", "=", status);
		SearchCondition.addCondition(condition, "cmsNewsColumn.columnid", "=", Integer.valueOf(columnid));
		SearchCondition.addCondition(condition, "userid", "=", Integer.valueOf(sysUserInfo.getUserid()));
		PageList page = manager.getPageCmsNewsInfos(condition, sorderindex, pageUtil.getStartCount(), pageUtil.getPageSize());
		List lst = page.getDatalist();
		for(int i = 0;i < lst.size();i++){
			CmsNewsInfo cni = (CmsNewsInfo)lst.get(i);
			if("1".equals(cni.getStatus())){
				cni.setFlago("disabled");
			}
		}
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("pagelist", page);
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
		HttpSession session = httpServletRequest.getSession();
		SysUserInfo sysUserInfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
		String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
		httpServletRequest.setAttribute("columnid", columnid);
		//分页与排序
		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
		httpServletRequest.setAttribute("pageno", pageno);
		httpServletRequest.setAttribute("direction", direction);
		httpServletRequest.setAttribute("sort", sort);
		CmsNewsInfo model = new CmsNewsInfo();
		model.setSketch("default.jpg");
		model.setHappendate(DateTime.getDate());
		model.setAuthor(sysUserInfo.getUsername());
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
		CmsNewsInfoActionForm form = (CmsNewsInfoActionForm)actionForm;
		CmsNewsInfoManager manager = (CmsNewsInfoManager)getBean("cmsNewsInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
				httpServletRequest.setAttribute("columnid", columnid);
				HttpSession session = httpServletRequest.getSession();
				Integer unitid = (Integer)session.getAttribute("s_unitid");
				SysUserInfo sysUserInfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
				CmsNewsInfo model = form.getCmsNewsInfo();
				CmsNewsColumnManager cncm = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
				CmsNewsColumn cnc = cncm.getCmsNewsColumn(columnid);
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createdate = format.format(date);
				model.setCmsNewsColumn(cnc);
				model.setUnitid(unitid);
				model.setStatus("0");
				model.setHits(new Integer(0));
				model.setCreatedate(createdate);
				model.setUserid(sysUserInfo.getUserid());
				manager.addCmsNewsInfo(model);
				addLog(httpServletRequest,"增加了一个资讯信息");
				httpServletRequest.setAttribute("reloadtree", "1");
				httpServletRequest.setAttribute("promptinfo", "资讯管理增加成功!");
			}catch (Exception e){
			}
		}else{
			saveToken(httpServletRequest);
			httpServletRequest.setAttribute("promptinfo", "请勿重复刷新页面!");
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
		CmsNewsInfoManager manager = (CmsNewsInfoManager)getBean("cmsNewsInfoManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		
			String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
            httpServletRequest.setAttribute("columnid", columnid);
            String newsid = Encode.nullToBlank(httpServletRequest.getParameter("newsid"));
            httpServletRequest.setAttribute("newsid", newsid);
            //分页与排序
    		String pageno = Encode.nullToBlank(httpServletRequest.getParameter("pager.pageNo"));
    		String direction = Encode.nullToBlank(httpServletRequest.getParameter("direction"));
    		String sort = Encode.nullToBlank(httpServletRequest.getParameter("sort"));
    		httpServletRequest.setAttribute("pageno", pageno);
    		httpServletRequest.setAttribute("direction", direction);
    		httpServletRequest.setAttribute("sort", sort);
    		
			CmsNewsInfo model = manager.getCmsNewsInfo(objid);
			httpServletRequest.setAttribute("act", "updateSave");
			httpServletRequest.setAttribute("model", model);
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
		CmsNewsInfoActionForm form = (CmsNewsInfoActionForm)actionForm;
		CmsNewsInfoManager manager = (CmsNewsInfoManager)getBean("cmsNewsInfoManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				String columnid = Encode.nullToBlank(httpServletRequest.getParameter("columnid"));
				httpServletRequest.setAttribute("columnid", columnid);
				String newsid = Encode.nullToBlank(httpServletRequest.getParameter("newsid"));
				httpServletRequest.setAttribute("newsid", newsid);
				CmsNewsInfo model = form.getCmsNewsInfo();
				HttpSession session = httpServletRequest.getSession();
				Integer unitid = (Integer) session.getAttribute("s_unitid");
				model.setUnitid(unitid);
				SysUserInfo sysUserInfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
				model.setUserid(sysUserInfo.getUserid());
				CmsNewsColumnManager cncm = (CmsNewsColumnManager)getBean("cmsNewsColumnManager");
				CmsNewsColumn cnc = cncm.getCmsNewsColumn(columnid);;
				model.setRecommand("0");
				model.setHits(new Integer("0"));
				model.setCmsNewsColumn(cnc);
				model.setHits(new Integer(0));
				manager.updateCmsNewsInfo(model);
				addLog(httpServletRequest,"修改了一个资讯信息");
				httpServletRequest.setAttribute("promptinfo", "资讯管理修改成功!");
			}catch (Exception e){
			}
		}else{
        	saveToken(httpServletRequest);
			httpServletRequest.setAttribute("promptinfo", "请勿重复刷新页面!");;
        }
		httpServletRequest.setAttribute("reloadtree", "1");
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	/**
	 * 更新新闻的点击量
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
	public ActionForward addHits(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String newsid = httpServletRequest.getParameter("newsid");
		CmsNewsInfoManager manager = (CmsNewsInfoManager) getBean("cmsNewsInfoManager");
		CmsNewsInfo model = manager.getCmsNewsInfo(newsid);
		manager.updateCmsNewsInfo(model);
		return null;
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
		CmsNewsInfoManager manager = (CmsNewsInfoManager)getBean("cmsNewsInfoManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		CmsNewsInfo model = null;
		if(checkids != null){
		for (int i = 0; i < checkids.length; i++) {
			model = manager.getCmsNewsInfo(checkids[i]);
			manager.delCmsNewsInfo(checkids[i]);
			addLog(httpServletRequest, "删除了一个" + model.getTitle() + "资讯信息");
		}
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	/**
	 * 详情页面
	 * */
	public ActionForward detail(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String newsid = Encode.nullToBlank(httpServletRequest.getParameter("id"));
		httpServletRequest.setAttribute("newsid", newsid);
		//String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		CmsNewsInfoManager manager = (CmsNewsInfoManager)getBean("cmsNewsInfoManager");
		CmsNewsInfo model = manager.getCmsNewsInfo(newsid);
		httpServletRequest.setAttribute("model", model);
		return actionMapping.findForward("detail");
	}
	/**
	 *跳转主工作区
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward main(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		return actionMapping.findForward("main");
	}
	/**
	 * 树型选择器
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
	public ActionForward tree(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		CmsNewsColumnManager manager = (CmsNewsColumnManager) getBean("cmsNewsColumnManager");
		// 取出所有记录
		List<SearchModel>condition = new ArrayList<SearchModel>();
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer)session.getAttribute("s_unitid");
		SearchCondition.addCondition(condition, "unitid", "=", Integer.valueOf(unitid));
		List lst = manager.getCmsNewsColumns(condition, "columnno", 0);
		String tree = "";
		String url = "";
		CmsNewsColumn cnc = null;
		String no = "";// 自身ID
		String pno = "";// 父ID
		String text = "";// 树节点显示名称
		String hint = "";
		String target = "";
		for (int i = 0; i < lst.size(); i++) {
			cnc = (CmsNewsColumn) lst.get(i);
			no = cnc.getColumnno().trim();// 自身ID
			pno = cnc.getParentno().trim();// 父ID
			text = cnc.getColumnname();// 树节点显示名称
			  
			 
			url = "cmsMyNewsInfoAction.do?method=list&columnid=" + cnc.getColumnid();// onclick树节点后执行
			target = "rfrmright";// 页面在打开位置
			tree += "\n" + "tree" + ".nodes[\"" + pno + "_" + no + "\"]=\"";
			if (text != null && text.trim().trim().length() > 0) {
				tree += "text:" + text + ";";
			}
			if (hint != null && hint.trim().length() > 0) {
				tree += "hint:" + hint + ";";
			}
			tree += "url:" + url + ";";
			tree += "target:" + target + ";";
			tree += "\";";
		}
		String rooturl = "/sysLayoutAction.do?method=welcome";
		httpServletRequest.setAttribute("treenode", tree);
		httpServletRequest.setAttribute("rooturl", rooturl);
		httpServletRequest.setAttribute("modulename", "资讯管理");
		return actionMapping.findForward("tree");
	}

}