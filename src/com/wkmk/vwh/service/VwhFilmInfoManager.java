package com.wkmk.vwh.service;

import java.util.List;

import com.wkmk.vwh.bo.VwhFilmInfo;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 视频库视频信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface VwhFilmInfoManager {
	/**
	 *根据id获取视频库视频信息
	 *@param filmid Integer
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo getVwhFilmInfo(String filmid);

	/**
	 *根据id获取视频库视频信息
	 *@param filmid Integer
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo getVwhFilmInfo(Integer filmid);

	/**
	 *增加视频库视频信息
	 *@param vwhFilmInfo VwhFilmInfo
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo addVwhFilmInfo(VwhFilmInfo vwhFilmInfo);

	/**
	 *删除视频库视频信息
	 *@param filmid Integer
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo delVwhFilmInfo(String filmid);

	/**
	 *删除视频库视频信息
	 *@param vwhFilmInfo VwhFilmInfo
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo delVwhFilmInfo(VwhFilmInfo vwhFilmInfo);

	/**
	 *修改视频库视频信息
	 *@param vwhFilmInfo VwhFilmInfo
	 *@return VwhFilmInfo
	 */
	public VwhFilmInfo updateVwhFilmInfo(VwhFilmInfo vwhFilmInfo);

	/**
	 *获取视频库视频信息集合
	 *@return List
	 */
	public List getVwhFilmInfos (List<SearchModel> condition, String orderby, int pagesize);
	
	/**
	 *获取视频库视频信息集合
 	 *@return List
	 */
	public List getVwhFilmInfos(List<SearchModel> condition, String typeno, String orderby, int pagesize);

	/**
	 *获取视频库服务器id集合
 	 *@return List
	 */
	public List getAllComputerids(Integer unitid);
	
	/**
	 *获取视频库分类id集合
 	 *@return List
	 */
	public List getAllTypeids(Integer unitid);
	
	/**
	 *获取一页视频库视频信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmInfos (List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 *获取一页视频库视频信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmInfos (List<SearchModel> condition, String typeno, String orderby, int start, int pagesize);
}