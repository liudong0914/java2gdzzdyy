package com.wkmk.tk.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONObject;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchModel;
import com.util.string.Encode;
import com.wkmk.tk.bo.TkBookContentWatch;
import com.wkmk.tk.service.TkBookContentWatchManager;
import com.wkmk.tk.web.form.TkBookContentWatchActionForm;

/**
 *<p>Description: 音频文件观看记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentWatchAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentWatchManager manager = (TkBookContentWatchManager)getBean("tkBookContentWatchManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageTkBookContentWatchs(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
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
		TkBookContentWatch model = new TkBookContentWatch();
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
		TkBookContentWatchActionForm form = (TkBookContentWatchActionForm)actionForm;
		TkBookContentWatchManager manager = (TkBookContentWatchManager)getBean("tkBookContentWatchManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentWatch model = form.getTkBookContentWatch();
				manager.addTkBookContentWatch(model);
				addLog(httpServletRequest,"增加了一个音频文件观看记录");
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
		TkBookContentWatchManager manager = (TkBookContentWatchManager)getBean("tkBookContentWatchManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkBookContentWatch model = manager.getTkBookContentWatch(objid);
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
		TkBookContentWatchActionForm form = (TkBookContentWatchActionForm)actionForm;
		TkBookContentWatchManager manager = (TkBookContentWatchManager)getBean("tkBookContentWatchManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentWatch model = form.getTkBookContentWatch();
				manager.updateTkBookContentWatch(model);
				addLog(httpServletRequest,"修改了一个音频文件观看记录");
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
		TkBookContentWatchManager manager = (TkBookContentWatchManager)getBean("tkBookContentWatchManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkBookContentWatch(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	/**
	 * 
	 * 方法描述：折線图，听力播放
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午1:18:31
	 */
	public ActionForward statisticalContentWatch(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalContentWatch");
	}

	/**
	 * 
	 * 方法描述：ajax,折現圖，听力播放
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午2:00:15
	 */
	public ActionForward getAjaxContentWatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String endtime ="";
			String starttime ="";
			TkBookContentWatchManager manager = (TkBookContentWatchManager)getBean("tkBookContentWatchManager");
			List maxNum = manager.getTkBookContentWatchsOfDayMaxNum();
			List list = manager.getTkBookContentWatchsOfDay();
			Long max = (Long) Collections.max(maxNum);
			max = max + 150;
			if (list != null && list.size() > 0) {
				for (int i = 1; i <= list.size(); i++) {
					Object[] lst = (Object[]) list.get(i - 1);
					Object a = lst[0];// 用户id
					Object b = lst[1];// 日期
					Object c = lst[2];// 人数
					aString = aString + "'" + b + "'" + ",";
					bString = bString + c + ",";
				}
				//取出最后一个元素
				Object[] object = (Object[]) list.get(list.size()-1);
				endtime = (String) object[1];// 最后一个日期
				//取出倒数第30个元素
				if(list.size()>30){
					Object[] object1 = (Object[]) list.get(list.size()-30);
					starttime = (String) object1[1];// 第一个日期
				}else{
					Object[] object1 = (Object[]) list.get(0);
					starttime = (String) object1[1];// 第一个日期
				}
			}
			
			
			aString = aString.substring(0, aString.length() - 1);
			aString = "[" + aString + "]";
			bString = bString.substring(0, bString.length() - 1);
			bString = "[" + bString + "]";

			JSONObject jo = new JSONObject();
			JSONArray a = new JSONArray();
			JSONArray b = new JSONArray();
			a = JSONArray.fromObject(aString);
			b = JSONArray.fromObject(bString);
			jo.put("a", a);
			jo.put("b", b);
			jo.put("c", max.toString());
			jo.put("starttime", starttime);
			jo.put("endtime", endtime);
			pw = response.getWriter();
			pw.write(jo.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
			pw = null;
		}
		return null;
	}
}