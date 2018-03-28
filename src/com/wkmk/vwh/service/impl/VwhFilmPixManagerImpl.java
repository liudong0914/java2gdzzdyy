package com.wkmk.vwh.service.impl;

import java.util.List;

import com.wkmk.vwh.bo.VwhFilmPix;
import com.wkmk.vwh.service.VwhFilmPixManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 视频库视频影片</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmPixManagerImpl implements VwhFilmPixManager{

	private BaseDAO baseDAO;
	private String modelname = "视频库视频影片";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取视频库视频影片
	 *@param pixid Integer
	 *@return VwhFilmPix
	 */
	public VwhFilmPix getVwhFilmPix(String pixid){
		Integer iid = Integer.valueOf(pixid);
		return  (VwhFilmPix)baseDAO.getObject(modelname,VwhFilmPix.class,iid);
	}

	/**
	 *根据id获取视频库视频影片
	 *@param pixid Integer
	 *@return VwhFilmPix
	 */
	public VwhFilmPix getVwhFilmPix(Integer pixid){
		return  (VwhFilmPix)baseDAO.getObject(modelname,VwhFilmPix.class,pixid);
	}

	/**
	 *增加视频库视频影片
	 *@param vwhFilmPix VwhFilmPix
	 *@return VwhFilmPix
	 */
	public VwhFilmPix addVwhFilmPix(VwhFilmPix vwhFilmPix){
		return (VwhFilmPix)baseDAO.addObject(modelname,vwhFilmPix);
	}

	/**
	 *删除视频库视频影片
	 *@param pixid Integer
	 *@return VwhFilmPix
	 */
	public VwhFilmPix delVwhFilmPix(String pixid){
		VwhFilmPix model = getVwhFilmPix(pixid);
		return (VwhFilmPix)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除视频库视频影片
	 *@param vwhFilmPix VwhFilmPix
	 *@return VwhFilmPix
	 */
	public VwhFilmPix delVwhFilmPix(VwhFilmPix vwhFilmPix){
		return (VwhFilmPix)baseDAO.delObject(modelname,vwhFilmPix);
	}

	/**
	 *修改视频库视频影片
	 *@param vwhFilmPix VwhFilmPix
	 *@return VwhFilmPix
	 */
	public VwhFilmPix updateVwhFilmPix(VwhFilmPix vwhFilmPix){
		return (VwhFilmPix)baseDAO.updateObject(modelname,vwhFilmPix);
	}

	/**
	 *获取视频库视频影片集合
 	 *@return List
	 */
	public List getVwhFilmPixs(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("VwhFilmPix",condition,orderby,pagesize);
	}
	
	/**
	 *获取视频库视频影片集合
 	 *@param Integer filmid
	 */
	public List getVwhFilmPixsByFilmid(Integer filmid){
		String sql = "select a from VwhFilmPix as a where a.filmid=" + filmid;
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取视频库视频id集合
 	 *@return List
	 */
	public List getAllFilmids(){
		String sql = "select distinct a.filmid from VwhFilmPix as a";
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取所有转码未完成的影片的视频id
 	 *@return List
	 */
	public List getAllNotConvertPixOfFilmids(Integer unitid){
		String sql = "select distinct a.filmid from VwhFilmPix as a where a.convertstatus<>'1' and a.unitid=" + unitid;
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *根据md5获取视频
 	 *@return List
	 */
	public VwhFilmPix getVwhFilmPixByMd5code(String md5code){
		String sql = "select a from VwhFilmPix as a where a.md5code='" + md5code + "'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (VwhFilmPix) list.get(0);
		}else {
			return null;
		}
	}

	/**
	 *获取一页视频库视频影片集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageVwhFilmPixs (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("VwhFilmPix","pixid",condition, orderby, start,pagesize);
	}

}