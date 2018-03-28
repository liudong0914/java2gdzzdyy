package com.wkmk.tk.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.formula.IStabilityClassifier;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.tk.bo.TkBookContent;
import com.wkmk.tk.bo.TkBookContentPrice;
import com.wkmk.tk.service.TkBookContentManager;
import com.wkmk.tk.service.TkBookContentPriceManager;
import com.wkmk.tk.web.form.TkBookContentPriceActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 作业本内容定价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentPriceAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		TkBookContentPriceManager manager = (TkBookContentPriceManager)getBean("tkBookContentPriceManager");
		TkBookContentManager tbcm = (TkBookContentManager)getBean("tkBookContentManager");		
		TkBookContent tbc = tbcm.getTkBookContent(bookcontentid);
		String title = tbc.getTitle();
		TkBookContentPrice model = new TkBookContentPrice();
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "bookcontentid", "=", Integer.valueOf(bookcontentid));
		List list = manager.getTkBookContentPrices(condition, "", 0);
		if(list.size()<=0){
			httpServletRequest.setAttribute("promptinfo", "未获取到该章节价格信息，请到作业本管理栏目下对该章节所在的课本进行价格初始化，若已进行初始化，请手动添加");
			return actionMapping.findForward("failure1");
		}else{
		PageList page = manager.getPageTkBookContentPrices(condition, "a.type", pageUtil.getStartCount(), pageUtil.getPageSize());
		httpServletRequest.setAttribute("startcount", pageUtil.getStartCount());
		httpServletRequest.setAttribute("title", title);
		httpServletRequest.setAttribute("model", model);
		httpServletRequest.setAttribute("pagelist", page);
		return actionMapping.findForward("list");
		}
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
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		String sellcount = Encode.nullToBlank(httpServletRequest.getParameter("sellcount"));
		httpServletRequest.setAttribute("sellcount", sellcount);
		TkBookContentPriceManager manager = (TkBookContentPriceManager)getBean("tkBookContentPriceManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			String priceid = Encode.nullToBlank(httpServletRequest.getParameter("priceid"));
			httpServletRequest.setAttribute("priceid", priceid);
			TkBookContentPrice model = manager.getTkBookContentPrice(objid);
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
		String bookcontentid =  Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		TkBookContentPriceActionForm form = (TkBookContentPriceActionForm)actionForm;
		TkBookContentPriceManager manager = (TkBookContentPriceManager)getBean("tkBookContentPriceManager");
		TkBookContentManager tbcm = (TkBookContentManager)getBean("tkBookContentManager");
		HttpSession session = httpServletRequest.getSession();
		SysUserInfo userInfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
        TkBookContent tbc = tbcm.getTkBookContent(bookcontentid);
        Integer bookid = tbc.getBookid();
		if (isTokenValid(httpServletRequest, true)) {
			try {
				TkBookContentPrice model = form.getTkBookContentPrice();
				model.setBookcontentid(Integer.valueOf(bookcontentid));
				model.setBookid(bookid);
				model.setUserid(userInfo.getUserid());
				manager.updateTkBookContentPrice(model);
				addLog(httpServletRequest,"修改了一个作业本内容定价");
			}catch (Exception e){
			}
		}else{
        	saveToken(httpServletRequest);
			httpServletRequest.setAttribute("promptinfo", "请勿重复刷新页面!");;
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
		TkBookContentPriceManager manager = (TkBookContentPriceManager)getBean("tkBookContentPriceManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delTkBookContentPrice(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	/**
	 *未进行价格初始化时执行的操作 
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward addList(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		TkBookContentManager manager = (TkBookContentManager)getBean("tkBookContentManager");
		TkBookContent tbc = manager.getTkBookContent(bookcontentid);
		//TkBookContentPrice model = new TkBookContentPrice();
		String title = tbc.getTitle();
		httpServletRequest.setAttribute("title", title);
		String type1 = "解题微课定价";
		String type2 = "举一反三微课定价";
		String price = "";
		String discount = "";
		String sellprice = "";
		String sellcount = "0";
		httpServletRequest.setAttribute("price", price);
		httpServletRequest.setAttribute("discount", discount);
		httpServletRequest.setAttribute("sellprice", sellprice);
		httpServletRequest.setAttribute("sellcount", sellcount);
		httpServletRequest.setAttribute("type1", type1);
		httpServletRequest.setAttribute("type2", type2);
		return actionMapping.findForward("addlist");

	}
	/**
	 *未进行价格初始化时增加前 
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward beforeAdd(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontentid"));
		httpServletRequest.setAttribute("bookcontentid", bookcontentid);
		String sellcount =  Encode.nullToBlank(httpServletRequest.getParameter("sellcount"));
		httpServletRequest.setAttribute("sellcount", sellcount);
		String type = Encode.nullToBlank(httpServletRequest.getParameter("type"));
		//System.out.println(type);
		//httpServletRequest.setAttribute("type", type);
		TkBookContentPrice model = new TkBookContentPrice();
		if(type.equals("解题微课定价")){
			model.setType("1");
		}else if(type.equals("举一反三微课定价")){
			model.setType("2");
		}
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
		TkBookContentPriceActionForm form = (TkBookContentPriceActionForm)actionForm;
		TkBookContentPriceManager manager = (TkBookContentPriceManager)getBean("tkBookContentPriceManager");
		String bookcontentid = Encode.nullToBlank(httpServletRequest.getParameter("bookcontenid"));
		TkBookContentManager tbcmanager = (TkBookContentManager)getBean("tkBookContentManager");
		TkBookContent tbc = tbcmanager.getTkBookContent(bookcontentid);
		Integer bookid = tbc.getBookid();
		if(isTokenValid(httpServletRequest, true)){
			try{
				TkBookContentPrice model = form.getTkBookContentPrice();
				HttpSession session = httpServletRequest.getSession();
				Integer userid = (Integer)session.getAttribute("s_userid");
				model.setBookcontentid(Integer.valueOf(bookcontentid));
				model.setBookid(bookid);
				model.setUserid(userid);
				model.setType(model.getType());
				manager.addTkBookContentPrice(model);
				model.setBookcontentid(Integer.valueOf(bookcontentid));
				model.setBookid(bookid);
				model.setUserid(userid);
				model.setPrice(null);
				model.setSellprice(null);
				model.setDiscount(null);
				if(model.getType().equals("1")){
					model.setType("2");
				}else{
					model.setType("1");;
				}
				manager.addTkBookContentPrice(model);
			}catch(Exception e){	
			}
		}else{
        	saveToken(httpServletRequest);
			httpServletRequest.setAttribute("promptinfo", "请勿重复刷新页面!");;
        }
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}


	/**
	 *价格初始化
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward initialPrice(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		httpServletRequest.setAttribute("bookid", bookid);
		TkBookContentManager tbcm = (TkBookContentManager)getBean("tkBookContentManager");
		//根据bookid查询出所有的作业本信息
		List<SearchModel>condition = new ArrayList<SearchModel>();
		condition.clear();
		SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
		List list = tbcm.getAllBookContentByBook(bookid);
		//根据作业本id初始化作业本
		TkBookContentPriceManager tbcpm = (TkBookContentPriceManager)getBean("tkBookContentPriceManager");
		//查看作业本id，若存在，则不执行初始化
		condition.clear();
		SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
		List tbcplist = tbcpm.getTkBookContentPrices(condition, "", 0);
		if(tbcplist.size()>0){
			httpServletRequest.setAttribute("promptinfo", "价格初始化失败，该条数据已初始化!");
			return actionMapping.findForward("failure");
		}else{
//		HttpSession session = httpServletRequest.getSession();
//		SysUserInfo userInfo = (SysUserInfo)session.getAttribute("s_sysuserinfo");
//		if(list.size()>0){
//			for(int i = 0;i<list.size();i++){
//				TkBookContent tbc = (TkBookContent)list.get(i);
//				tbcp.setBookcontentid(tbc.getBookcontentid());
//				tbcp.setDiscount(Float.valueOf("2"));
//				tbcp.setSellprice(Float.valueOf("1"));
//				tbcp.setSellcount(new Integer(0));
//				tbcp.setUserid(userInfo.getUserid());
//				tbcp.setBookid(Integer.valueOf(bookid));
//				tbcp.setPrice(Float.valueOf("5"));
//				tbcp.setType("1");
//				tbcpm.addTkBookContentPrice(tbcp);
//				tbcp.setPriceid(null);
//				tbcp.setType("2");
//				tbcpm.addTkBookContentPrice(tbcp);
//			}
//		}
//		httpServletRequest.setAttribute("promptinfo", "价格初始化成功!");
	    TkBookContentPrice model = new TkBookContentPrice();
	    model.setPrice(Float.valueOf("5.00"));
        model.setDiscount(Float.valueOf("2.0"));
        model.setSellprice(Float.valueOf("1.00"));
	    httpServletRequest.setAttribute("model", model);
		return actionMapping.findForward("initedit");
		}
	}
	/**
	 *对未初始化的价格进行保存
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward initEditSave(ActionMapping actionMapping,ActionForm actionForm,HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse){
		String bookid = Encode.nullToBlank(httpServletRequest.getParameter("bookid"));
		TkBookContentPriceActionForm form = (TkBookContentPriceActionForm)actionForm;
		TkBookContentPrice model = form.getTkBookContentPrice();
		HttpSession session = httpServletRequest.getSession();
		Integer unitid = (Integer)session.getAttribute("s_unitid");
		Integer userid = (Integer)session.getAttribute("s_userid");
		TkBookContentManager tbcm = (TkBookContentManager)getBean("tkBookContentManager");
		TkBookContentPriceManager tbcpm = (TkBookContentPriceManager)getBean("tkBookContentPriceManager");
		List<SearchModel>condition = new ArrayList<SearchModel>();
		SearchCondition.addCondition(condition, "bookid", "=", Integer.valueOf(bookid));
		List lst = tbcm.getAllBookContentByBook(bookid);
		for(int i = 0;i < lst.size();i++){
			TkBookContent tbc = (TkBookContent)lst.get(i);
			model.setBookcontentid(tbc.getBookcontentid());
			model.setDiscount(model.getDiscount());
			model.setSellprice(model.getSellprice());
			model.setSellcount(new Integer(0));
			model.setUserid(userid);
			model.setBookid(Integer.valueOf(bookid));
			model.setPrice(model.getPrice());
			model.setType("1");
			tbcpm.addTkBookContentPrice(model);
			model.setPriceid(null);
			model.setType("2");
			tbcpm.addTkBookContentPrice(model);
		}
		httpServletRequest.setAttribute("promptinfo", "价格初始化成功!");
		return actionMapping.findForward("success");
	}


}