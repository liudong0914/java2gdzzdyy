package com.wkmk.zx.service.impl;

import java.util.List;

import com.wkmk.zx.bo.ZxHelpFile;
import com.wkmk.zx.service.ZxHelpFileManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 *<p>Description: 在线答疑附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpFileManagerImpl implements ZxHelpFileManager{

	private BaseDAO baseDAO;
	private String modelname = "在线答疑附件";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取在线答疑附件
	 *@param fileid Integer
	 *@return ZxHelpFile
	 */
	public ZxHelpFile getZxHelpFile(String fileid){
		Integer iid = Integer.valueOf(fileid);
		return  (ZxHelpFile)baseDAO.getObject(modelname,ZxHelpFile.class,iid);
	}

	/**
	 *根据id获取在线答疑附件
	 *@param fileid Integer
	 *@return ZxHelpFile
	 */
	public ZxHelpFile getZxHelpFile(Integer fileid){
		return  (ZxHelpFile)baseDAO.getObject(modelname,ZxHelpFile.class,fileid);
	}

	/**
	 *增加在线答疑附件
	 *@param zxHelpFile ZxHelpFile
	 *@return ZxHelpFile
	 */
	public ZxHelpFile addZxHelpFile(ZxHelpFile zxHelpFile){
		return (ZxHelpFile)baseDAO.addObject(modelname,zxHelpFile);
	}

	/**
	 *删除在线答疑附件
	 *@param fileid Integer
	 *@return ZxHelpFile
	 */
	public ZxHelpFile delZxHelpFile(String fileid){
		ZxHelpFile model = getZxHelpFile(fileid);
		return (ZxHelpFile)baseDAO.delObject(modelname,model);
	}

	/**
	 *删除在线答疑附件
	 *@param zxHelpFile ZxHelpFile
	 *@return ZxHelpFile
	 */
	public ZxHelpFile delZxHelpFile(ZxHelpFile zxHelpFile){
		return (ZxHelpFile)baseDAO.delObject(modelname,zxHelpFile);
	}

	/**
	 *修改在线答疑附件
	 *@param zxHelpFile ZxHelpFile
	 *@return ZxHelpFile
	 */
	public ZxHelpFile updateZxHelpFile(ZxHelpFile zxHelpFile){
		return (ZxHelpFile)baseDAO.updateObject(modelname,zxHelpFile);
	}

	/**
	 *获取在线答疑附件集合
 	 *@return List
	 */
	public List getZxHelpFiles(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("ZxHelpFile",condition,orderby,pagesize);
	}
	
	/**
	 *获取在线答疑附件集合
 	 *@return List
	 */
	public List getZxHelpFilesByQuestionid(String questionid){
		String sql = "select a from ZxHelpFile as a where a.type='1' and a.questionid=" + questionid;
		return baseDAO.getObjects(sql);
	}
	
	/**
	 *获取在线答疑附件集合
 	 *@return List
	 */
	public List getZxHelpFilesByAnswerid(String answerid){
		String sql = "select a from ZxHelpFile as a where a.type='2' and a.answerid=" + answerid;
		return baseDAO.getObjects(sql);
	}

	/**
	 *获取一页在线答疑附件集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageZxHelpFiles (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("ZxHelpFile","fileid",condition, orderby, start,pagesize);
	}

}