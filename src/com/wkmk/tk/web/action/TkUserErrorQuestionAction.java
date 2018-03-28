package com.wkmk.tk.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.wkmk.tk.bo.TkUserErrorQuestion;
import com.wkmk.tk.service.TkUserErrorQuestionManager;
import com.wkmk.tk.web.form.TkUserErrorQuestionActionForm;
import com.wkmk.util.common.Constants;

/**
 *<p>Description: 个人错题集</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkUserErrorQuestionAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		TkUserErrorQuestionManager manager = (TkUserErrorQuestionManager)getBean("tkUserErrorQuestionManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageTkUserErrorQuestions(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
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
		TkUserErrorQuestion model = new TkUserErrorQuestion();
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
		TkUserErrorQuestionActionForm form = (TkUserErrorQuestionActionForm)actionForm;
		TkUserErrorQuestionManager manager = (TkUserErrorQuestionManager)getBean("tkUserErrorQuestionManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkUserErrorQuestion model = form.getTkUserErrorQuestion();
				manager.addTkUserErrorQuestion(model);
				addLog(httpServletRequest,"增加了一个个人错题集");
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
		TkUserErrorQuestionManager manager = (TkUserErrorQuestionManager)getBean("tkUserErrorQuestionManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			TkUserErrorQuestion model = manager.getTkUserErrorQuestion(objid);
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
		TkUserErrorQuestionActionForm form = (TkUserErrorQuestionActionForm)actionForm;
		TkUserErrorQuestionManager manager = (TkUserErrorQuestionManager)getBean("tkUserErrorQuestionManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkUserErrorQuestion model = form.getTkUserErrorQuestion();
				manager.updateTkUserErrorQuestion(model);
				addLog(httpServletRequest,"修改了一个个人错题集");
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
		TkUserErrorQuestionManager manager = (TkUserErrorQuestionManager)getBean("tkUserErrorQuestionManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkUserErrorQuestion(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 
	 * 方法描述：柱状图，错题统计
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午1:18:31
	 */
	public ActionForward statisticalUserErrorQuestion(ActionMapping actionMapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) {
		return actionMapping.findForward("statisticalUserErrorQuestion");
	}

	/**
	 * 
	 * 方法描述：ajax,柱状图，错题统计
	 * 
	 * @param:
	 * @return:
	 * @version: 1.0
	 * @author: 刘冬
	 * @version: 2016-11-24 下午2:00:15
	 */
	public ActionForward getAjaxUserErrorQuestion(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		PrintWriter pw = null;
		try {
			String bookid ="";
			String paperid ="";
			String endtime ="";
			String starttime ="";
			String obj = Encode.nullToBlank(request.getParameter("obj"));
			String flag = Encode.nullToBlank(request.getParameter("flag"));
			if(obj.trim().length() > 0){
				 obj=new String(obj.getBytes("ISO8859_1"),"utf-8");//启东九年级化学(上)_2(11)
				 String title = obj.substring(0,obj.indexOf("_"));
				 String version_bookid = obj.substring(obj.indexOf("_")+1,obj.length());//2(11)
				 String version = version_bookid.substring(0,version_bookid.indexOf("("));
				 if(flag.equals("")){
					 bookid = version_bookid.substring(version_bookid.indexOf("(")+1,version_bookid.indexOf(")"));
					 flag = "1";
				 }else if(flag.equals("1")){
					 paperid = version_bookid.substring(version_bookid.indexOf("(")+1,version_bookid.indexOf(")"));
					 flag = "2";
				 }
			}
			String a1String = "";
			String b1String = "";
			String a2String = "";
			String b2String = "";
			String a3String = "";
			String b3String = "";
			String a4String = "";
			String b4String = "";
			String cString = "";
			String dString = "";
//			Map map1 = new TreeMap();
//			Map map1 = new HashMap();
			List list = new ArrayList();
			JSONObject  map2 = new JSONObject ();
			JSONObject  map3 = new JSONObject ();
			//JSONObject  map4 = new JSONObject ();
			TkUserErrorQuestionManager manager = (TkUserErrorQuestionManager)getBean("tkUserErrorQuestionManager");
			// 1.试题总数
			List list3 = new ArrayList();
			if(flag.equals("2")){
				list3 = manager.getUserErrorQuestionsOfQuestionsNum(paperid);
			if (list3 != null && list3.size() > 0) {
				for (int i = 1; i <= list3.size(); i++) {
					Object[] lst = (Object[]) list3.get(i - 1);
					Object a = lst[0];// paperid
					Object b = lst[1];// orderindex
					Object d = lst[2];// version
					Object c = lst[3];// 试题id
					if(flag.equals("2")){
						b ="第"+b+"题";
					}
					String typevalue = Constants.getCodeTypevalue("version", d.toString());
//					map1.put(b.toString()+"_"+typevalue+"("+a+")", c);
					list.add(b.toString()+"_"+typevalue+"("+a+")");
					
				}
			}
			}
			//2.作业总数
			List list4 = new ArrayList();
			if(flag.equals("1")){
				list4 = manager.getUserErrorQuestionsOfBookNum(bookid);
			if (list4 != null && list4.size() > 0) {
				for (int i = 1; i <= list4.size(); i++) {
					Object[] lst = (Object[]) list4.get(i - 1);//b.paperid,b.title,a.version,COUNT(*)
					Object a = lst[0];// paperid
					Object b = lst[1];// title
					Object d = lst[2];// version
					Object c = lst[3];// 数量
//					if(b.toString().length()>10){
//						b = b.toString().subSequence(0, 10);
//					}
					String typevalue = Constants.getCodeTypevalue("version", d.toString());
//					map1.put(b.toString()+"_"+typevalue+"("+a+")", c);
					list.add(b.toString()+"_"+typevalue+"("+a+")");
				}
			}
			}
			//3.作业或试题总使用数
			List list1 = new ArrayList();
			if(flag.equals("2")){
				list1 = manager.getUserErrorQuestionsOfQuestions(paperid);
			}else{
				list1 = manager.getUserErrorQuestionsOfBook(bookid);
			}
			if (list1 != null && list1.size() > 0) {
				for (int i = 1; i <= list1.size(); i++) {
					Object[] lst = (Object[]) list1.get(i - 1);
					Object a = lst[0];// bookid
					Object b = lst[1];// 书名
					Object d = lst[2];// 教材版本
					Object c = lst[3];// 数量
//					if(b.toString().length()>10){
//						b = b.toString().subSequence(0, 10);
//					}
					if(flag.equals("2")){
						b ="第"+b+"题";
					}
					String typevalue = Constants.getCodeTypevalue("version", d.toString());
					if(flag.equals("")){
//						map1.put(b.toString()+"_"+typevalue+"("+a+")", c);
						list.add(b.toString()+"_"+typevalue+"("+a+")");
					}
					map3.put(b.toString()+"_"+typevalue+"("+a+")", c);
				}
			}
		

			// 4.作业或试题总错题数
			List list2 = new ArrayList();
			if(flag.equals("2")){
				list2 = manager.getUserErrorQuestionsOfQuestionsError(paperid);
			}else{
				list2 = manager.getUserErrorQuestionsOfBookError(bookid);
			}
			if (list2 != null && list2.size() > 0) {
				for (int i = 1; i <= list2.size(); i++) {
					Object[] lst2 = (Object[]) list2.get(i - 1);
					Object a = lst2[0];// bookid
					Object b = lst2[1];// 书名
					Object d = lst2[2];// 教材版本
					Object c = lst2[3];// 数量
//					if(b.toString().length()>10){
//						b = b.toString().subSequence(0, 10);
//					}
					if(flag.equals("2")){
						b ="第"+b+"题";
					}
					String typevalue = Constants.getCodeTypevalue("version", d.toString());
					map2.put(b.toString()+"_"+typevalue+"("+a+")", c);
				}
			}
			
			
//			Set keySet = map1.keySet();
//			Iterator<String> it = keySet.iterator();  
			if(list !=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					 String str = (String) list.get(i); 
					  Object object1 =0;
					  Object object2 =0;
					  if(map3.has(str)){
						   object2 = map3.get(str);
					  }
					  b1String = b1String + object2 + ",";
					  if(map2.has(str)){
						   object1 = map2.get(str);
					  }
					  b2String = b2String + object1 + ",";
					  a1String = a1String + "\"" + str + "\"" + ",";
				}
				//
				starttime = (String) list.get(0);// 第一个日期
				//
				if(list.size()>5){
					 endtime = (String) list.get(4);
				}else{
					endtime = (String) list.get(list.size()-1);
				}
			}
//			while (it.hasNext()) {  
//			 
//			}  
			a1String = a1String.substring(0, a1String.length() - 1);
			a1String = "[" + a1String + "]";
			b1String = b1String.substring(0, b1String.length() - 1);
			b1String = "[" + b1String + "]";
			b2String = b2String.substring(0, b2String.length() - 1);
			b2String = "[" + b2String + "]";
			
			
			JSONObject jo = new JSONObject();
			JSONArray a1 = new JSONArray();
			JSONArray b1 = new JSONArray();
			JSONArray b2 = new JSONArray();
		
			a1 = JSONArray.fromObject(a1String);
			b1 = JSONArray.fromObject(b1String);
			b2 = JSONArray.fromObject(b2String);
			
			jo.put("a1", a1);
			jo.put("b1", b1);
			jo.put("b2", b2);
			jo.put("flag", flag);
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