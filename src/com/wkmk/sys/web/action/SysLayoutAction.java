package com.wkmk.sys.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wkmk.sys.bo.SysModuleInfo;
import com.wkmk.sys.bo.SysProductInfo;
import com.wkmk.sys.service.SysModuleInfoManager;
import com.wkmk.sys.service.SysProductInfoManager;
import com.util.action.BaseAction;
import com.util.search.SearchCondition;
import com.util.search.SearchModel;

public class SysLayoutAction extends BaseAction  {

	public ActionForward main(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		//先获取用户关联产品【用户-角色-模块-产品】
		List<SearchModel> condition = new ArrayList<SearchModel>();
		SysProductInfoManager spim = (SysProductInfoManager) getBean("sysProductInfoManager");
		SearchCondition.addCondition(condition, "status", "=", "1");
		List prodcutList = spim.getSysProductInfos(condition, "orderindex asc", 0);
		HashMap productMap = new HashMap();
		SysProductInfo spi = null;
		for(int i=0,size=prodcutList.size(); i<size; i++){
			spi = (SysProductInfo) prodcutList.get(i);
			if(productMap.get(spi.getProductid()) == null){
				productMap.put(spi.getProductid(), spi);
			}
		}
		
		HttpSession session = request.getSession();
		Integer userid = (Integer) session.getAttribute("s_userid");
		Integer unitid = (Integer) session.getAttribute("s_unitid");
		
		SysModuleInfoManager smim = (SysModuleInfoManager) getBean("sysModuleInfoManager");
		List permissionlist = smim.getPermissionModuleList(userid, unitid, null);
		HashMap modulehashmap = getTreeMap(permissionlist);
		
		//获取所有一级菜单
		List allparentmodulelist = smim.getAllParentModuleList(null);
		//用户所能看到的一级菜单
		List datalist = new ArrayList();
		List produclist = new ArrayList();//用户关联产品
		SysModuleInfo smi = null;
		for (int i=0, size=allparentmodulelist.size(); i < size; i++) {
			smi = (SysModuleInfo) allparentmodulelist.get(i);
			List sublist = (List) modulehashmap.get(smi.getModuleno() + "_" + smi.getProductid());
			if (sublist != null && sublist.size() > 0) {
				datalist.add(smi);
				if(!produclist.contains(smi.getProductid())){
					produclist.add(smi.getProductid());
				}
			}
		}
		permissionlist.addAll(0, datalist);
		
		Integer productid = null;
		StringBuffer data = new StringBuffer();//显示产品列表
		StringBuffer listdata = new StringBuffer();//显示模块列表
		StringBuffer listdata0 = new StringBuffer();
		for(int i=0, size=produclist.size(); i<size; i++){
			productid = (Integer) produclist.get(i);
			spi = (SysProductInfo) productMap.get(productid);
			if(spi == null) continue;
			data.append("{id:\"").append(productid).append("\",name:\"").append(spi.getProductname()).append("\"},\n");
			if(i==0){
				listdata.append("if(idx==\"").append(productid).append("\"){\n").append("listData={\"treeNodes\":[\n");
			}else {
				listdata.append("else if(idx==\"").append(productid).append("\"){\n").append("listData={\"treeNodes\":[\n");
			}
			listdata0 = new StringBuffer();
			for(int m=0, n=permissionlist.size(); m<n; m++){
				smi = (SysModuleInfo) permissionlist.get(m);
				if(smi.getProductid().equals(productid)){
					listdata0.append("{ id:").append(productid).append(smi.getModuleno());
					listdata0.append(", parentId:").append(productid).append(smi.getParentno()).append(", name:\"").append(smi.getModulename()).append("\",");
					if(smi.getLinkurl() != null && !"".equals(smi.getLinkurl())){
						if("0".equals(smi.getAutoopen())){
							listdata0.append("url:\"").append(smi.getLinkurl()).append("\", target:\"frmright\",");
						}else {
							listdata0.append("url:\"").append(smi.getLinkurl()).append("\", target:\"_blank\",");
						}
					}
					listdata0.append("icon:\"").append(smi.getModuleicon()).append("\"},\n");
				}
			}
			if(listdata0.lastIndexOf(",\n") != -1) listdata.append(listdata0.substring(0, listdata0.length()-2)).append("\n");
			listdata.append("]};\n}");
		}
		String data00 = "";
		if(data.lastIndexOf(",\n") != -1) data00 = data.substring(0, data.length()-2);
		String listdata00 = listdata.toString();
		request.setAttribute("data", data00);
		request.setAttribute("listdata", listdata00);
		
		return mapping.findForward("main");
	}

	public ActionForward left(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("left");
	}
	
	public ActionForward right(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("right");
	}
	
	public ActionForward welcome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("welcome");
	}
	
	/**
	 * 框架用，大页面
	 */
	public ActionForward welcome0(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("welcome0");
	}
	
	/*
	 * 遍历结果
	 */
	private HashMap getTreeMap(List objlist) {
		HashMap treemap = new HashMap();
		Iterator it = objlist.iterator();
		SysModuleInfo obj = null;

		while (it.hasNext()) {
			obj = (SysModuleInfo) it.next();

			String parentno = obj.getParentno() + "_" + obj.getProductid();
			if (null == treemap.get(parentno)) {
				List list = new ArrayList();
				list.add(obj);
				treemap.put(parentno, list);
			} else {
				((List) treemap.get(parentno)).add(obj);
			}
		}
		return treemap;
	}
}
