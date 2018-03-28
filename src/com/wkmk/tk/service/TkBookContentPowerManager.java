package com.wkmk.tk.service;

import java.util.List;

import com.wkmk.tk.bo.TkBookContentPower;
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
public interface TkBookContentPowerManager {
	/**
	 * 根据id获取作业本内容校验授权
	 * 
	 * @param powerid
	 *            Integer
	 * @return TkBookContentPower
	 */
	public TkBookContentPower getTkBookContentPower(String powerid);

	/**
	 * 根据id获取作业本内容校验授权
	 * 
	 * @param powerid
	 *            Integer
	 * @return TkBookContentPower
	 */
	public TkBookContentPower getTkBookContentPower(Integer powerid);

	/**
	 * 增加作业本内容校验授权
	 * 
	 * @param tkBookContentPower
	 *            TkBookContentPower
	 * @return TkBookContentPower
	 */
	public TkBookContentPower addTkBookContentPower(TkBookContentPower tkBookContentPower);

	/**
	 * 删除作业本内容校验授权
	 * 
	 * @param powerid
	 *            Integer
	 * @return TkBookContentPower
	 */
	public TkBookContentPower delTkBookContentPower(String powerid);

	/**
	 * 删除作业本内容校验授权
	 * 
	 * @param tkBookContentPower
	 *            TkBookContentPower
	 * @return TkBookContentPower
	 */
	public TkBookContentPower delTkBookContentPower(TkBookContentPower tkBookContentPower);

	/**
	 * 修改作业本内容校验授权
	 * 
	 * @param tkBookContentPower
	 *            TkBookContentPower
	 * @return TkBookContentPower
	 */
	public TkBookContentPower updateTkBookContentPower(TkBookContentPower tkBookContentPower);

	/**
	 * 获取作业本内容校验授权集合
	 * 
	 * @return List
	 */
	public List getTkBookContentPowers(List<SearchModel> condition, String orderby, int pagesize);

	/**
	 * 获取一页作业本内容校验授权集合
	 * 
	 * @param start
	 *            int
	 * @param pagesize
	 *            int
	 * @return PageList
	 */
	public PageList getPageTkBookContentPowers(List<SearchModel> condition, String orderby, int start, int pagesize);

	/**
	 * 删除
	 * */
	public void delTkBookContentPower(Integer userid, Integer bookcontentid,String type);
}