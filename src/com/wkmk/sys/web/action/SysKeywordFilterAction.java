package com.wkmk.sys.web.action;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wkmk.sys.bo.SysKeywordFilter;
import com.wkmk.sys.service.SysKeywordFilterManager;
import com.wkmk.sys.web.form.SysKeywordFilterActionForm;
import com.wkmk.util.common.Constants;

import com.util.action.BaseAction;
import com.util.string.Encode;

/**
 *<p>Description: 系统关键字过滤</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysKeywordFilterAction extends BaseAction {

	/**
	* 修改前
	* @param actionMapping ActionMapping
	* @param actionForm ActionForm
	* @param httpServletRequest HttpServletRequest
	* @param httpServletResponse HttpServletResponse
	* @return ActionForward
	*/
	public ActionForward beforeUpdate(ActionMapping actionMapping, ActionForm actionForm,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysKeywordFilterManager manager = (SysKeywordFilterManager)getBean("sysKeywordFilterManager");
		//Integer unitid = (Integer) httpServletRequest.getSession().getAttribute("s_unitid");
		Integer unitid = Constants.DEFAULT_UNITID;
		SysKeywordFilter model = manager.getSysKeywordFilterByUnitid(unitid);
		if(model == null){
			model = new SysKeywordFilter();
			model.setUnitid(unitid);
			model.setStatus("1");
		}
		httpServletRequest.setAttribute("model", model);
		return actionMapping.findForward("edit");
	}

	/**
	* 修改时保存
	* @param actionMapping ActionMapping
	* @param actionForm ActionForm
	* @param httpServletRequest HttpServletRequest
	* @param httpServletResponse HttpServletResponse
	* @return ActionForward
	*/
	public ActionForward updateSave(ActionMapping actionMapping, ActionForm actionForm,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		SysKeywordFilterActionForm form=(SysKeywordFilterActionForm)actionForm;
		SysKeywordFilterManager manager=(SysKeywordFilterManager)getBean("sysKeywordFilterManager");
		try {
			SysKeywordFilter model=form.getSysKeywordFilter();
			String keywordid = Encode.nullToBlank(httpServletRequest.getParameter("keywordid"));
			if(!"".equals(keywordid) && keywordid != null){
				manager.updateSysKeywordFilter(model);
			}else {
				manager.addSysKeywordFilter(model);
			}
			
			addLog(httpServletRequest,"修改了系统关键字过滤系统");}
		catch (Exception e1){
			e1.printStackTrace();
		}
		httpServletRequest.setAttribute("promptinfo", "关键字过滤信息修改成功!");
		return actionMapping.findForward("success");
	}
	
	/**
	 * 过滤关键字
	 * @param content
	 * @param keyword
	 * @return
	 */
	public static String getFilterString(HttpServletRequest request, String content){
		ServletContext servletContext = request.getSession().getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		SysKeywordFilterManager manager = (SysKeywordFilterManager) ctx.getBean("sysKeywordFilterManager");
		//Integer unitid = (Integer) request.getSession().getAttribute("s_unitid");
		Integer unitid = Constants.DEFAULT_UNITID;
		SysKeywordFilter keyword = manager.getSysKeywordFilterByUnitid(unitid);
		
	    if(keyword == null || keyword.getFiltercontent() == null || "".equals(keyword.getFiltercontent())){
	      return content;
	    }
	    if(keyword != null && "0".equals(keyword.getStatus())){//未启用过滤器
	    	return content;
	    }

	    Pattern p = Pattern.compile(keyword.getFiltercontent());
	    Matcher m = p.matcher(content);
	    String value = m.replaceAll("*").trim();

	    return value;
	}
	
	/**
	 * 过滤关键字
	 * @param content
	 * @param keyword
	 * @return
	 */
	public static String getFilterString(HttpServletRequest request, String content, Integer unitid){
		unitid = Constants.DEFAULT_UNITID;
		ServletContext servletContext = request.getSession().getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		SysKeywordFilterManager manager = (SysKeywordFilterManager) ctx.getBean("sysKeywordFilterManager");
		SysKeywordFilter keyword = manager.getSysKeywordFilterByUnitid(unitid);
		
	    if(keyword == null || keyword.getFiltercontent() == null || "".equals(keyword.getFiltercontent())){
	      return content;
	    }
	    if(keyword != null && "0".equals(keyword.getStatus())){//未启用过滤器
	    	return content;
	    }

	    Pattern p = Pattern.compile(keyword.getFiltercontent());
	    Matcher m = p.matcher(content);
	    String value = m.replaceAll("*").trim();

	    return value;
	}
}
