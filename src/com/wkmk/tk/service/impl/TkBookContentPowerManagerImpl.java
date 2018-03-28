package com.wkmk.tk.service.impl;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentPower;
import com.wkmk.tk.service.TkBookContentPowerManager;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;

/**
 * <p>
 * Description: 作业本内容校验授权
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookContentPowerManagerImpl implements TkBookContentPowerManager {

	private BaseDAO baseDAO;
	private String modelname = "作业本内容校验授权";

	/**
	 * 加载baseDAO
	 * 
	 * @param BaseDAO
	 *            baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 * 根据id获取作业本内容校验授权
	 * 
	 * @param powerid
	 *            Integer
	 * @return TkBookContentPower
	 */
	public TkBookContentPower getTkBookContentPower(String powerid) {
		Integer iid = Integer.valueOf(powerid);
		return (TkBookContentPower) baseDAO.getObject(modelname, TkBookContentPower.class, iid);
	}

	/**
	 * 根据id获取作业本内容校验授权
	 * 
	 * @param powerid
	 *            Integer
	 * @return TkBookContentPower
	 */
	public TkBookContentPower getTkBookContentPower(Integer powerid) {
		return (TkBookContentPower) baseDAO.getObject(modelname, TkBookContentPower.class, powerid);
	}

	/**
	 * 增加作业本内容校验授权
	 * 
	 * @param tkBookContentPower
	 *            TkBookContentPower
	 * @return TkBookContentPower
	 */
	public TkBookContentPower addTkBookContentPower(TkBookContentPower tkBookContentPower) {
		return (TkBookContentPower) baseDAO.addObject(modelname, tkBookContentPower);
	}

	/**
	 * 删除作业本内容校验授权
	 * 
	 * @param powerid
	 *            Integer
	 * @return TkBookContentPower
	 */
	public TkBookContentPower delTkBookContentPower(String powerid) {
		TkBookContentPower model = getTkBookContentPower(powerid);
		return (TkBookContentPower) baseDAO.delObject(modelname, model);
	}

	/**
	 * 删除作业本内容校验授权
	 * 
	 * @param tkBookContentPower
	 *            TkBookContentPower
	 * @return TkBookContentPower
	 */
	public TkBookContentPower delTkBookContentPower(TkBookContentPower tkBookContentPower) {
		return (TkBookContentPower) baseDAO.delObject(modelname, tkBookContentPower);
	}

	/**
	 * 修改作业本内容校验授权
	 * 
	 * @param tkBookContentPower
	 *            TkBookContentPower
	 * @return TkBookContentPower
	 */
	public TkBookContentPower updateTkBookContentPower(TkBookContentPower tkBookContentPower) {
		return (TkBookContentPower) baseDAO.updateObject(modelname, tkBookContentPower);
	}

	/**
	 * 获取作业本内容校验授权集合
	 * 
	 * @return List
	 */
	public List getTkBookContentPowers(List<SearchModel> condition, String orderby, int pagesize) {
		return baseDAO.getObjects("TkBookContentPower", condition, orderby, pagesize);
	}

	/**
	 * 获取一页作业本内容校验授权集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkBookContentPowers(List<SearchModel> condition, String orderby, int start, int pagesize) {
		return baseDAO.getPageObjects("TkBookContentPower", "powerid", condition, orderby, start, pagesize);
	}

	public void delTkBookContentPower(Integer userid, Integer bookcontentid, String type) {
		baseDAO.delObjects(baseDAO.getObjects("select a from TkBookContentPower as a where userid=" + userid + " and bookcontentid=" + bookcontentid + " and type='" + type + "'"));
	}

}