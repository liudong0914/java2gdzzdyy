package com.wkmk.sys.service.impl;

import java.util.List;

import com.wkmk.sys.bo.SysUserAttention;
import com.wkmk.sys.service.SysUserAttentionManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 微信用户关注</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserAttentionManagerImpl implements SysUserAttentionManager{

	private BaseDAO baseDAO;
	private String modelname = "微信用户关注";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取微信用户关注
	 *@param attentionid Integer
	 *@return SysUserAttention
	 */
	public SysUserAttention getSysUserAttention(String attentionid){
		Integer iid = Integer.valueOf(attentionid);
		return  (SysUserAttention)baseDAO.getObject(modelname,SysUserAttention.class,iid);
	}

	/**
	 *根据id获取微信用户关注
	 *@param attentionid Integer
	 *@return SysUserAttention
	 */
	public SysUserAttention getSysUserAttention(Integer attentionid){
		return  (SysUserAttention)baseDAO.getObject(modelname,SysUserAttention.class,attentionid);
	}

	/**
	 *增加微信用户关注
	 *@param sysUserAttention SysUserAttention
	 *@return SysUserAttention
	 */
	public SysUserAttention addSysUserAttention(SysUserAttention sysUserAttention){
		//此表插入数据有很多重复数据，特殊处理，插入前判断
		String openid = sysUserAttention.getOpenid();
		if(openid != null && !"".equals(openid)){
			SysUserAttention sua = getSysUserAttentionByOpenid(openid);
			if(sua == null){
				return (SysUserAttention)baseDAO.addObject(modelname,sysUserAttention);
			}else {
				return sua;
			}
		}else {
			return null;
		}
	}

	/**
	 *删除微信用户关注
	 *@param attentionid Integer
	 *@return SysUserAttention
	 */
	public SysUserAttention delSysUserAttention(String attentionid){
		SysUserAttention model = getSysUserAttention(attentionid);
		return (SysUserAttention)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除微信用户关注
	 *@param sysUserAttention SysUserAttention
	 *@return SysUserAttention
	 */
	public SysUserAttention delSysUserAttention(SysUserAttention sysUserAttention){
		return (SysUserAttention)baseDAO.delObject(modelname,sysUserAttention);
	}

	/**
	 *修改微信用户关注
	 *@param sysUserAttention SysUserAttention
	 *@return SysUserAttention
	 */
	public SysUserAttention updateSysUserAttention(SysUserAttention sysUserAttention){
		return (SysUserAttention)baseDAO.updateObject(modelname,sysUserAttention);
	}

	/**
	 *获取微信用户关注集合
 	 *@return List
	 */
	public List getSysUserAttentions(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserAttention",condition,orderby,pagesize);
	}
	
	/**
	 *获取微网站关注用户
	 */
	public SysUserAttention getSysUserAttentionByOpenid(String openid){
		String sql = "select a from SysUserAttention as a where a.openid='" + openid + "' order by a.userid desc";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (SysUserAttention) list.get(0);
		}else {
			return null;
		}
	}
	
	public List getSysUserAttentionByOpenids(String openid){
		String sql = "select a from SysUserAttention as a where a.openid='" + openid + "' order by a.userid desc";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return list;
		}else {
			return null;
		}
	}
	
	/**
	 *获取微网站关注用户
	 */
	public SysUserAttention getSysUserAttentionByUserid(Integer userid){
		String sql = "select a from SysUserAttention as a where a.userid=" + userid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (SysUserAttention) list.get(0);
		}else {
			return null;
		}
	}
	
	public List getSysUserAttentionByUserids(Integer userid){
		String sql = "select a from SysUserAttention as a where a.userid=" + userid;
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return list;
		}else {
			return null;
		}
	}

	/**
	 *获取一页微信用户关注集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserAttentions (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserAttention","attentionid",condition, orderby, start,pagesize);
	}

}