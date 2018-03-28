package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysPayPassword;
import com.wkmk.sys.service.SysPayPasswordManager;

import com.sun.org.apache.regexp.internal.recompile;
import com.util.dao.BaseDAO;
import com.util.date.DateTime;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 支付密码错误记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysPayPasswordManagerImpl implements SysPayPasswordManager{

	private BaseDAO baseDAO;
	private String modelname = "支付密码错误记录";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取支付密码错误记录
	 *@param paypasswordid Integer
	 *@return SysPayPassword
	 */
	public SysPayPassword getSysPayPassword(String paypasswordid){
		Integer iid = Integer.valueOf(paypasswordid);
		return  (SysPayPassword)baseDAO.getObject(modelname,SysPayPassword.class,iid);
	}

	/**
	 *根据id获取支付密码错误记录
	 *@param paypasswordid Integer
	 *@return SysPayPassword
	 */
	public SysPayPassword getSysPayPassword(Integer paypasswordid){
		return  (SysPayPassword)baseDAO.getObject(modelname,SysPayPassword.class,paypasswordid);
	}

	/**
	 *增加支付密码错误记录
	 *@param sysPayPassword SysPayPassword
	 *@return SysPayPassword
	 */
	public SysPayPassword addSysPayPassword(SysPayPassword sysPayPassword){
		return (SysPayPassword)baseDAO.addObject(modelname,sysPayPassword);
	}

	/**
	 *删除支付密码错误记录
	 *@param paypasswordid Integer
	 *@return SysPayPassword
	 */
	public SysPayPassword delSysPayPassword(String paypasswordid){
		SysPayPassword model = getSysPayPassword(paypasswordid);
		return (SysPayPassword)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除支付密码错误记录
	 *@param sysPayPassword SysPayPassword
	 *@return SysPayPassword
	 */
	public SysPayPassword delSysPayPassword(SysPayPassword sysPayPassword){
		return (SysPayPassword)baseDAO.delObject(modelname,sysPayPassword);
	}

	/**
	 *修改支付密码错误记录
	 *@param sysPayPassword SysPayPassword
	 *@return SysPayPassword
	 */
	public SysPayPassword updateSysPayPassword(SysPayPassword sysPayPassword){
		return (SysPayPassword)baseDAO.updateObject(modelname,sysPayPassword);
	}

	/**
	 *获取支付密码错误记录集合
 	 *@return List
	 */
	public List getSysPayPasswords(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysPayPassword",condition,orderby,pagesize);
	}
	
	/**
	 *获取支付密码错误记录次数
 	 *@return List
	 */
	public int getCountSysPayPassword(String userid){
		String date = DateTime.getDateYMD();
		String sql = "select count(*) from SysPayPassword as a where a.userid=" + userid + " and a.createdate like '" + date + "%'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return ((Long)list.get(0)).intValue();
		}else {
			return 0;
		}
	}

	/**
	 *获取一页支付密码错误记录集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysPayPasswords (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysPayPassword","paypasswordid",condition, orderby, start,pagesize);
	}

}