package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserAttentionManager;
import com.wkmk.sys.service.SysUserInfoManager;
import com.wkmk.sys.web.form.SysUserAttentionActionForm;

import com.util.action.BaseAction;
import com.util.search.PageList;
import com.util.search.PageUtil;
import com.util.search.SearchModel;
import com.util.string.Encode;

/**
 *<p>Description: 微信用户关注</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserAttentionAction extends BaseAction {

	/**
	 *列表显示
	 *@param actionMapping ActionMapping
	 *@param actionForm ActionForm
	 *@param httpServletRequest HttpServletRequest
	 *@param httpServletResponse HttpServletResponse
	 *@return ActionForward
	 */
	public ActionForward list(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		SysUserAttentionManager manager = (SysUserAttentionManager)getBean("sysUserAttentionManager");
		PageUtil pageUtil = new PageUtil(httpServletRequest);
		List<SearchModel> condition = new ArrayList<SearchModel>();
		PageList page = manager.getPageSysUserAttentions(condition, "", pageUtil.getStartCount(), pageUtil.getPageSize());
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
		SysUserAttention model = new SysUserAttention();
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
		SysUserAttentionActionForm form = (SysUserAttentionActionForm)actionForm;
		SysUserAttentionManager manager = (SysUserAttentionManager)getBean("sysUserAttentionManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserAttention model = form.getSysUserAttention();
				manager.addSysUserAttention(model);
				addLog(httpServletRequest,"增加了一个微信用户关注");
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
		SysUserAttentionManager manager = (SysUserAttentionManager)getBean("sysUserAttentionManager");
		String objid = Encode.nullToBlank(httpServletRequest.getParameter("objid"));
		try {
			SysUserAttention model = manager.getSysUserAttention(objid);
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
		SysUserAttentionActionForm form = (SysUserAttentionActionForm)actionForm;
		SysUserAttentionManager manager = (SysUserAttentionManager)getBean("sysUserAttentionManager");
		if (isTokenValid(httpServletRequest, true)) {
			try {
				SysUserAttention model = form.getSysUserAttention();
				manager.updateSysUserAttention(model);
				addLog(httpServletRequest,"修改了一个微信用户关注");
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
		SysUserAttentionManager manager = (SysUserAttentionManager)getBean("sysUserAttentionManager");
		String[] checkids = httpServletRequest.getParameterValues("checkid");
		for (int i = 0; i < checkids.length; i++) {
			manager.delSysUserAttention(checkids[i]);
		}
		return list(actionMapping, actionForm, httpServletRequest, httpServletResponse);
	}
	
	/**
	 * 删除重复数据，然后设置openid字段唯一性
	 * 很多数据都有重复（openid），具体原因待查
	 */
	public ActionForward delRepeatAttentionWithOpenid(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
		try {
			SysUserAttentionManager manager = (SysUserAttentionManager)getBean("sysUserAttentionManager");
			List<SearchModel> condition = new ArrayList<SearchModel>();
			List attentionList = manager.getSysUserAttentions(condition, "userid desc, attentionid desc", 0);
			
			List<String> openidList = new ArrayList<String>();
			SysUserAttention sua = null;
			for(int i=0, size=attentionList.size(); i<size; i++){
				sua = (SysUserAttention) attentionList.get(i);
				if(openidList.contains(sua.getOpenid())){
					//删除已存在的重复openid数据
					manager.delSysUserAttention(sua);
				}else {
					openidList.add(sua.getOpenid());
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			System.out.println("数据删除失败!");
			httpServletRequest.setAttribute("promptinfo", "数据删除失败!");
			return actionMapping.findForward("failure");
		}

		System.out.println("数据删除成功!");
		httpServletRequest.setAttribute("promptinfo", "数据删除成功!");
		return actionMapping.findForward("success");
	}
	
//	/**
//	 * 设置关注公众号绑定用户
//	 * 很多用户数据，没有绑定到公众号，具体原因待查
//	 * 初步只能通过昵称和性别匹配【改头像匹配】
//	 */
//	public ActionForward updateAttentionWithUserid(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse) {
//		try {
//			SysUserAttentionManager manager = (SysUserAttentionManager)getBean("sysUserAttentionManager");
//			SysUserInfoManager suim = (SysUserInfoManager) getBean("sysUserInfoManager");
//			List<SearchModel> condition = new ArrayList<SearchModel>();
//			
//			List attentionList = manager.getSysUserAttentions(condition, "userid desc, attentionid desc", 0);
//			List userList = suim.getSysUserInfos(condition, "userid desc", 0);
//			
//			List openidList = new ArrayList();
//			List useridList = new ArrayList();
//			Map photoMap = new HashMap();
//			Map map = new HashMap();
//			SysUserAttention sua = null;
//			int temp1 = 0;
//			int temp2 = 0;
//			int temp3 = 0;
//			for(int i=0, size=attentionList.size(); i<size; i++){
//				sua = (SysUserAttention) attentionList.get(i);
//				if(openidList.contains(sua.getOpenid())){
//					//如果包含说明前期已经关注了，需要备份数据
//					sua.setOpenid(sua.getOpenid() + "-bak");
//					manager.updateSysUserAttention(sua);
//					temp1++;
//				}else {
//					openidList.add(sua.getOpenid());
//				}
//				if(sua.getUserid().intValue() == 1){
//					if(sua.getHeadimgurl() != null && !"".equals(sua.getHeadimgurl())){
//						photoMap.put(sua.getHeadimgurl(), sua);
//					}else {
//						//没有头像，可以用户昵称和性别组合关联
//						if("1".equals(sua.getSex())){
//							map.put(sua.getNickname()+"===0", sua);
//						}else {
//							map.put(sua.getNickname()+"===1", sua);
//						}
//					}
//				}else {
//					useridList.add(sua.getUserid());
//				}
//			}
//			
//			SysUserInfo sui = null;
//			for(int i=0, size=userList.size(); i<size; i++){
//				sui = (SysUserInfo) userList.get(i);
//				if(useridList.contains(sui.getUserid())){
//					//不做处理
//				}else {
//					sua = (SysUserAttention) photoMap.get(sui.getPhoto());
//					if(sua == null){
//						sua = (SysUserAttention) map.get(sui.getNickname()+"==="+sui.getSex());
//						if(sua != null && sua.getUserid().intValue() == 1){
//							if(sua.getNickname().indexOf("�") != -1){
//								System.out.println(sui.getUserid()+"》"+sui.getUsername()+"》"+sui.getLoginname()+"》"+sua.getNickname()+"》"+sua.getAttentionid());
//							}
//						}
//					}
//					if(sua != null && sua.getUserid().intValue() == 1){
//						sua.setUserid(sui.getUserid());
//						manager.updateSysUserAttention(sua);
//						temp2++;
//						photoMap.put(sua.getHeadimgurl(), sua);
//						
//						useridList.add(sui.getUserid());
//					}else{
//						temp3++;
//					}
//				}
//			}
//			System.out.println("openid重复数据处理了"+temp1+"条");
//			System.out.println("用户没有绑定微信数据处理了"+temp2+"条");
//			System.out.println("用户没有绑定微信数据没有对应用户共"+temp3+"条");
//		}catch (Exception e){
//			e.printStackTrace();
//			System.out.println("数据匹配失败!");
//			httpServletRequest.setAttribute("promptinfo", "数据匹配失败!");
//			return actionMapping.findForward("failure");
//		}
//
//		System.out.println("数据匹配成功!");
//		httpServletRequest.setAttribute("promptinfo", "数据匹配成功!");
//		return actionMapping.findForward("success");
//	}
}