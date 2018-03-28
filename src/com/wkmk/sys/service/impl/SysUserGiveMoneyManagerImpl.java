package com.wkmk.sys.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserGiveMoney;
import com.wkmk.sys.service.SysUserGiveMoneyManager;

/**
 *<p>Description: 用户赠送学币</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserGiveMoneyManagerImpl implements SysUserGiveMoneyManager{

	private BaseDAO baseDAO;
	private String modelname = "用户赠送学币";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取用户赠送龙币
	 *@param moneyid Integer
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney getSysUserGiveMoney(String moneyid){
		Integer iid = Integer.valueOf(moneyid);
		return  (SysUserGiveMoney)baseDAO.getObject(modelname,SysUserGiveMoney.class,iid);
	}

	/**
	 *根据id获取用户赠送龙币
	 *@param moneyid Integer
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney getSysUserGiveMoney(Integer moneyid){
		return  (SysUserGiveMoney)baseDAO.getObject(modelname,SysUserGiveMoney.class,moneyid);
	}

	/**
	 *增加用户赠送龙币
	 *@param sysUserGiveMoney SysUserGiveMoney
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney addSysUserGiveMoney(SysUserGiveMoney sysUserGiveMoney){
		return (SysUserGiveMoney)baseDAO.addObject(modelname,sysUserGiveMoney);
	}

	/**
	 *删除用户赠送龙币
	 *@param moneyid Integer
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney delSysUserGiveMoney(String moneyid){
		SysUserGiveMoney model = getSysUserGiveMoney(moneyid);
		return (SysUserGiveMoney)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除用户赠送龙币
	 *@param sysUserGiveMoney SysUserGiveMoney
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney delSysUserGiveMoney(SysUserGiveMoney sysUserGiveMoney){
		return (SysUserGiveMoney)baseDAO.delObject(modelname,sysUserGiveMoney);
	}

	/**
	 *修改用户赠送龙币
	 *@param sysUserGiveMoney SysUserGiveMoney
	 *@return SysUserGiveMoney
	 */
	public SysUserGiveMoney updateSysUserGiveMoney(SysUserGiveMoney sysUserGiveMoney){
		return (SysUserGiveMoney)baseDAO.updateObject(modelname,sysUserGiveMoney);
	}

	/**
	 *获取用户赠送龙币集合
 	 *@return List
	 */
	public List getSysUserGiveMoneys(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserGiveMoney",condition,orderby,pagesize);
	}
	
	/**
	 *获取用户赠送龙币总值
 	 *@return List
	 */
	public Float getSysUserGiveMoneyByUserid(Integer userid, String startdate, String enddate, String type){
		String sql = "select sum(a.money) from SysUserGiveMoney as a where a.userid=" + userid + " and a.createdate>='" + startdate + "' and a.createdate<='" + enddate + "'";
		if(type != null && !"".equals(type)){
			sql = sql + " and a.type='" + type + "'";
		}
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return Float.valueOf(list.get(0).toString());
		}else {
			return 0F;
		}
	}

	/**
	 *获取一页用户赠送龙币集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserGiveMoneys (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserGiveMoney","moneyid",condition, orderby, start,pagesize);
	}

}