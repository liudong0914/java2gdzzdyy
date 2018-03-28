package com.wkmk.vwh.service;

import java.util.List;

import com.wkmk.vwh.bo.VwhFilmKnopoint;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 微课知识点</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface VwhFilmKnopointManager {
	/**
	 *根据id获取微课知识点
	 *@param fid Integer
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint getVwhFilmKnopoint(String fid);

	/**
	 *根据id获取微课知识点
	 *@param fid Integer
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint getVwhFilmKnopoint(Integer fid);

	/**
	 *增加微课知识点
	 *@param vwhFilmKnopoint VwhFilmKnopoint
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint addVwhFilmKnopoint(VwhFilmKnopoint vwhFilmKnopoint);

	/**
	 *删除微课知识点
	 *@param fid Integer
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint delVwhFilmKnopoint(String fid);

	/**
	 *删除微课知识点
	 *@param vwhFilmKnopoint VwhFilmKnopoint
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint delVwhFilmKnopoint(VwhFilmKnopoint vwhFilmKnopoint);
	
	/**
	 *删除微课知识点
	 */
	public void delVwhFilmKnopoint(Integer filmid, Integer knopointid);

	/**
	 *修改微课知识点
	 *@param vwhFilmKnopoint VwhFilmKnopoint
	 *@return VwhFilmKnopoint
	 */
	public VwhFilmKnopoint updateVwhFilmKnopoint(VwhFilmKnopoint vwhFilmKnopoint);

	/**
	 *获取微课知识点集合
	 *@return List
	 */
	public List getVwhFilmKnopoints (List<SearchModel> condition, String orderby, int pagesize);

	/**
	 *获取所有知识点id
 	 *@return List
	 */
	public List getAllKnopointids();
	
	/**
	 *获取微课关联的所有知识点
 	 *@return List
	 */
	public List getEduKnopointInfoByFilmid(Integer filmid);
	
	/**
	 *获取一页微课知识点集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmKnopoints (List<SearchModel> condition, String orderby, int start, int pagesize);

}