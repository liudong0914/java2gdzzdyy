package com.wkmk.sys.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.wkmk.sys.bo.SysSmsInfo;
import com.wkmk.sys.service.SysSmsInfoManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 手机短信</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysSmsInfoManagerImpl implements SysSmsInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "手机短信";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取手机短信
	 *@param smsid Integer
	 *@return SysSmsInfo
	 */
	public SysSmsInfo getSysSmsInfo(String smsid){
		Integer iid = Integer.valueOf(smsid);
		return  (SysSmsInfo)baseDAO.getObject(modelname,SysSmsInfo.class,iid);
	}

	/**
	 *根据id获取手机短信
	 *@param smsid Integer
	 *@return SysSmsInfo
	 */
	public SysSmsInfo getSysSmsInfo(Integer smsid){
		return  (SysSmsInfo)baseDAO.getObject(modelname,SysSmsInfo.class,smsid);
	}

	/**
	 *增加手机短信
	 *@param sysSmsInfo SysSmsInfo
	 *@return SysSmsInfo
	 */
	public SysSmsInfo addSysSmsInfo(SysSmsInfo sysSmsInfo){
		return (SysSmsInfo)baseDAO.addObject(modelname,sysSmsInfo);
	}

	/**
	 *删除手机短信
	 *@param smsid Integer
	 *@return SysSmsInfo
	 */
	public SysSmsInfo delSysSmsInfo(String smsid){
		SysSmsInfo model = getSysSmsInfo(smsid);
		return (SysSmsInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除手机短信
	 *@param sysSmsInfo SysSmsInfo
	 *@return SysSmsInfo
	 */
	public SysSmsInfo delSysSmsInfo(SysSmsInfo sysSmsInfo){
		return (SysSmsInfo)baseDAO.delObject(modelname,sysSmsInfo);
	}

	/**
	 *修改手机短信
	 *@param sysSmsInfo SysSmsInfo
	 *@return SysSmsInfo
	 */
	public SysSmsInfo updateSysSmsInfo(SysSmsInfo sysSmsInfo){
		return (SysSmsInfo)baseDAO.updateObject(modelname,sysSmsInfo);
	}

	/**
	 *获取手机短信集合
 	 *@return List
	 */
	public List getSysSmsInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysSmsInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页手机短信集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysSmsInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysSmsInfo","smsid",condition, orderby, start,pagesize);
	}
	
	/**
	 *获取一页手机短信集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysSmsInfos (String mobile,String username,String state,String type,String orderby,int start,int pagesize){
		String sql="select a,b.username from SysSmsInfo as a,SysUserInfo as b where a.userid=b.userid";
		String sqlcount="select count(*) from SysSmsInfo as a,SysUserInfo as b where a.userid=b.userid";
		if(!"".equals(mobile)){
			sql+=" and a.mobile like '%"+mobile+"%'";
			sqlcount+=" and a.mobile like '%"+mobile+"%'";
		}
		if(!"".equals(username)){
			sql+=" and b.username like '%"+username+"%'";
			sqlcount+=" and b.username like '%"+username+"%'";
		}
		if(!"".equals(state)){
			sql+=" and a.state = '"+state+"'";
			sqlcount+=" and a.state = '"+state+"'";
		}
		if(!"".equals(type)){
			sql+=" and a.type = '"+type+"'";
			sqlcount+=" and a.type = '"+type+"'";
		}
		if(!"".equals(orderby)){
			sql+=" order by "+orderby;
		}else{
			sql+=" order by a.createdate desc";
		}
		return baseDAO.getPageObjects(sqlcount, sql, start, pagesize);
	}
	
	/* 
	 * 变更手机号码根据手机号码查询当天发送给该手机号码的短信数
	 */
	public int getMobileCountMessage(String userid){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String minDate=format.format(new Date())+" 00:00:00";
		String maxDate=format.format(new Date())+" 23:59:59";
		String sql="select a from SysSmsInfo as a where a.state='1' and a.type='2' and a.userid="+userid+" and a.createdate>='"+minDate+"' and a.createdate<='"+maxDate+"'";
		return baseDAO.getObjects(sql).size();
	}
	
	/* 
	 * 修改支付密码根据手机号码查询当天发送给该手机号码的短信数
	 */
	public int getPasswordCountMessage(String userid){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		String minDate=format.format(new Date())+" 00:00:00";
		String maxDate=format.format(new Date())+" 23:59:59";
		String sql="select a from SysSmsInfo as a where a.state='1' and a.type='3' and a.userid="+userid+" and a.createdate>='"+minDate+"' and a.createdate<='"+maxDate+"'";
		return baseDAO.getObjects(sql).size();
	}
	
	

}