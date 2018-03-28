package com.wkmk.vwh.service;

import java.util.List;

import com.wkmk.vwh.bo.VwhFilmPix;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 视频库视频影片</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public interface VwhFilmPixManager {
	/**
	 *根据id获取视频库视频影片
	 *@param pixid Integer
	 *@return VwhFilmPix
	 */
	public VwhFilmPix getVwhFilmPix(String pixid);

	/**
	 *根据id获取视频库视频影片
	 *@param pixid Integer
	 *@return VwhFilmPix
	 */
	public VwhFilmPix getVwhFilmPix(Integer pixid);

	/**
	 *增加视频库视频影片
	 *@param vwhFilmPix VwhFilmPix
	 *@return VwhFilmPix
	 */
	public VwhFilmPix addVwhFilmPix(VwhFilmPix vwhFilmPix);

	/**
	 *删除视频库视频影片
	 *@param pixid Integer
	 *@return VwhFilmPix
	 */
	public VwhFilmPix delVwhFilmPix(String pixid);

	/**
	 *删除视频库视频影片
	 *@param vwhFilmPix VwhFilmPix
	 *@return VwhFilmPix
	 */
	public VwhFilmPix delVwhFilmPix(VwhFilmPix vwhFilmPix);

	/**
	 *修改视频库视频影片
	 *@param vwhFilmPix VwhFilmPix
	 *@return VwhFilmPix
	 */
	public VwhFilmPix updateVwhFilmPix(VwhFilmPix vwhFilmPix);

	/**
	 *获取视频库视频影片集合
	 *@return List
	 */
	public List getVwhFilmPixs (List<SearchModel> condition, String orderby, int pagesize);
	
	/**
	 *获取视频库视频影片集合
 	 *@param Integer filmid
	 */
	public List getVwhFilmPixsByFilmid(Integer filmid);

	/**
	 *获取视频库视频id集合
 	 *@return List
	 */
	public List getAllFilmids();
	
	/**
	 *获取所有转码未完成的影片的视频id
 	 *@return List
	 */
	public List getAllNotConvertPixOfFilmids(Integer unitid);
	
	/**
	 *根据md5获取视频
 	 *@return List
	 */
	public VwhFilmPix getVwhFilmPixByMd5code(String md5code);
	
	/**
	 *获取一页视频库视频影片集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmPixs (List<SearchModel> condition, String orderby, int start, int pagesize);

}