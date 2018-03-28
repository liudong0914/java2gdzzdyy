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
import com.wkmk.tk.bo.TkBookContentFilmWatch;
import com.wkmk.tk.service.TkBookContentFilmWatchManager;
import com.wkmk.tk.web.form.TkBookContentFilmWatchActionForm;

/**
 *<p>Description: 解题微课播放记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmWatchAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkBookContentFilmWatchManager manager = (TkBookContentFilmWatchManager)getBean("tkBookContentFilmWatchManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageTkBookContentFilmWatchs(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
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
		TkBookContentFilmWatch model = new TkBookContentFilmWatch();
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
		TkBookContentFilmWatchActionForm form = (TkBookContentFilmWatchActionForm)actionForm;
		TkBookContentFilmWatchManager manager = (TkBookContentFilmWatchManager)getBean("tkBookContentFilmWatchManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentFilmWatch model = form.getTkBookContentFilmWatch();
				manager.addTkBookContentFilmWatch(model);
				addLog(httpServletRequest,"增加了一个解题微课播放记录");
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
		TkBookContentFilmWatchManager manager = (TkBookContentFilmWatchManager)getBean("tkBookContentFilmWatchManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkBookContentFilmWatch model = manager.getTkBookContentFilmWatch(objid);
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
		TkBookContentFilmWatchActionForm form = (TkBookContentFilmWatchActionForm)actionForm;
		TkBookContentFilmWatchManager manager = (TkBookContentFilmWatchManager)getBean("tkBookContentFilmWatchManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentFilmWatch model = form.getTkBookContentFilmWatch();
				manager.updateTkBookContentFilmWatch(model);
				addLog(httpServletRequest,"修改了一个解题微课播放记录");
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
		TkBookContentFilmWatchManager manager = (TkBookContentFilmWatchManager)getBean("tkBookContentFilmWatchManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkBookContentFilmWatch(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 
	 * 方法描述：折線图，微课播放
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午1:18:31
	 */
	public ActionForward statisticalFilmWatch(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalFilmWatch");
	}

	/**
	 * 
	 * 方法描述：ajax,折現圖，微课播放
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午2:00:15
	 */
	public ActionForward getAjaxFilmWatch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String aString = "";
			String bString = "";
			String cString = "";
			String dString = "";
			String endtime ="";
			String starttime ="";
			TkBookContentFilmWatchManager manager = (TkBookContentFilmWatchManager)getBean("tkBookContentFilmWatchManager");
			// 解题微课播放记录
			List maxNum = manager.getTkBookContentFilmWatchsOfMaxNum();
			List list = manager.getTkBookContentFilmWatchsOfAll();
			Long max = (Long) Collections.max(maxNum);
			max = max + 25;
			if (list != null && list.size() > 0) {
				for (int i = 1; i <= list.size(); i++) {
					Object[] lst = (Object[]) list.get(i - 1);
					Object b = lst[0];// 日期
					Object c = lst[1];// 人数
					aString = aString + "'" + b + "'" + ",";
					bString = bString + c + ",";
				}
				//取出最后一个元素
				Object[] object = (Object[]) list.get(list.size()-1);
				endtime = (String) object[0];// 最后一个日期
				//取出倒数第30个元素
				if(list.size()>30){
					Object[] object1 = (Object[]) list.get(list.size()-30);
					starttime = (String) object1[0];// 第一个日期
				}else{
					Object[] object1 = (Object[]) list.get(0);
					starttime = (String) object1[0];// 第一个日期
				}
			}
			
			
			aString = aString.substring(0, aString.length() - 1);
			aString = "[" + aString + "]";
			bString = bString.substring(0, bString.length() - 1);
			bString = "[" + bString + "]";

			// 试听解题微课播放记录
			List maxNum2 = manager.getTkBookContentFilmAuditionWatchOfMaxNum();
			List list2 = manager.getTkBookContentFilmAuditionWatchOfAll();
			Long max2 = (Long) Collections.max(maxNum2);
			max2 = max2 + 25;
			if (list2 != null && list2.size() > 0) {
				for (int i = 1; i <= list2.size(); i++) {
					Object[] lst2 = (Object[]) list2.get(i - 1);
					Object b = lst2[0];// 日期
					Object c = lst2[1];// 人数
					cString = cString + "'" + b + "'" + ",";
					dString = dString + c + ",";
				}
			}
			cString = cString.substring(0, cString.length() - 1);
			cString = "[" + cString + "]";
			dString = dString.substring(0, dString.length() - 1);
			dString = "[" + dString + "]";

			JSONObject jo = new JSONObject();
			JSONArray a = new JSONArray();
			JSONArray b = new JSONArray();
			JSONArray c = new JSONArray();
			JSONArray d = new JSONArray();
			a = JSONArray.fromObject(aString);
			b = JSONArray.fromObject(bString);
			c = JSONArray.fromObject(cString);
			d = JSONArray.fromObject(dString);
			jo.put("a", a);
			jo.put("b", b);
			jo.put("max1", max.toString());
			jo.put("c", c);
			jo.put("d", d);
			jo.put("max2", max2.toString());
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