package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUserPay;
import com.wkmk.sys.service.SysUserPayManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线交易明细</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserPayManagerImpl implements SysUserPayManager{

	private BaseDAO baseDAO;
	private String modelname = "在线交易明细";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线交易明细
	 *@param payid Integer
	 *@return SysUserPay
	 */
	public SysUserPay getSysUserPay(String payid){
		Integer iid = Integer.valueOf(payid);
		return  (SysUserPay)baseDAO.getObject(modelname,SysUserPay.class,iid);
	}

	/**
	 *根据id获取在线交易明细
	 *@param payid Integer
	 *@return SysUserPay
	 */
	public SysUserPay getSysUserPay(Integer payid){
		return  (SysUserPay)baseDAO.getObject(modelname,SysUserPay.class,payid);
	}

	/**
	 *增加在线交易明细
	 *@param sysUserPay SysUserPay
	 *@return SysUserPay
	 */
	public SysUserPay addSysUserPay(SysUserPay sysUserPay){
		return (SysUserPay)baseDAO.addObject(modelname,sysUserPay);
	}

	/**
	 *删除在线交易明细
	 *@param payid Integer
	 *@return SysUserPay
	 */
	public SysUserPay delSysUserPay(String payid){
		SysUserPay model = getSysUserPay(payid);
		return (SysUserPay)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线交易明细
	 *@param sysUserPay SysUserPay
	 *@return SysUserPay
	 */
	public SysUserPay delSysUserPay(SysUserPay sysUserPay){
		return (SysUserPay)baseDAO.delObject(modelname,sysUserPay);
	}

	/**
	 *修改在线交易明细
	 *@param sysUserPay SysUserPay
	 *@return SysUserPay
	 */
	public SysUserPay updateSysUserPay(SysUserPay sysUserPay){
		return (SysUserPay)baseDAO.updateObject(modelname,sysUserPay);
	}

	/**
	 *获取在线交易明细集合
 	 *@return List
	 */
	public List getSysUserPays(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserPay",condition,orderby,pagesize);
	}
	
	/**
	 *获取在线交易明细
 	 *@return List
	 */
	public SysUserPay getSysUserPayByOuttradeno(String outtradeno){
		String sql = "select a from SysUserPay as a where a.outtradeno='" + outtradeno + "'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (SysUserPay) list.get(0);
		}else {
			return null;
		}
	}

	/**
	 *获取一页在线交易明细集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserPays (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserPay","payid",condition, orderby, start,pagesize);
	}

}