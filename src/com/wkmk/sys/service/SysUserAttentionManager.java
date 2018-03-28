package com.wkmk.sys.service;

import java.util.List;

import com.wkmk.sys.bo.SysUserAttention;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 微信用户关注</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface SysUserAttentionManager {
	/**
	 *根据id获取微信用户关注
	 *@param attentionid Integer
	 *@return SysUserAttention
	 */
	public SysUserAttention getSysUserAttention(String attentionid);

	/**
	 *根据id获取微信用户关注
	 *@param attentionid Integer
	 *@return SysUserAttention
	 */
	public SysUserAttention getSysUserAttention(Integer attentionid);

	/**
	 *增加微信用户关注
	 *@param sysUserAttention SysUserAttention
	 *@return SysUserAttention
	 */
	public SysUserAttention addSysUserAttention(SysUserAttention sysUserAttention);

	/**
	 *删除微信用户关注
	 *@param attentionid Integer
	 *@return SysUserAttention
	 */
	public SysUserAttention delSysUserAttention(String attentionid);

	/**
	 *删除微信用户关注
	 *@param sysUserAttention SysUserAttention
	 *@return SysUserAttention
	 */
	public SysUserAttention delSysUserAttention(SysUserAttention sysUserAttention);

	/**
	 *修改微信用户关注
	 *@param sysUserAttention SysUserAttention
	 *@return SysUserAttention
	 */
	public SysUserAttention updateSysUserAttention(SysUserAttention sysUserAttention);

	/**
	 *获取微信用户关注集合
	 *@return List
	 */
	public List getSysUserAttentions (List<SearchModel> condition, String orderby, int pagesize);
	
	/**
	 *获取微网站关注用户
	 */
	public SysUserAttention getSysUserAttentionByOpenid(String openid);
	
	public List getSysUserAttentionByOpenids(String openid);
	
	/**
	 *获取微网站关注用户
	 */
	public SysUserAttention getSysUserAttentionByUserid(Integer userid);
	
	public List getSysUserAttentionByUserids(Integer userid);

	/**
	 *获取一页微信用户关注集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserAttentions (List<SearchModel> condition, String orderby, int start, int pagesize);

}