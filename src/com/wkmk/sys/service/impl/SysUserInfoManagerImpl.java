package com.wkmk.sys.service.impl;

import java.util.List;

import com.util.dao.BaseDAO;
import com.util.search.PageList;
import com.util.search.SearchModel;
import com.wkmk.sys.bo.SysUserInfo;
import com.wkmk.sys.service.SysUserInfoManager;

/**
 *<p>Description: 系统用户信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserInfoManagerImpl implements SysUserInfoManager{

	private BaseDAO baseDAO;
	private String modelname = "系统用户信息";

	/**
	 *加载baseDAO 
	 *@param BaseDAO baseDAO
	 */
	public void setBaseDAO(BaseDAO baseDAO) {
		this.baseDAO = baseDAO;
	}

	/**
	 *根据id获取系统用户信息
	 *@param userid Integer
	 *@return SysUserInfo
	 */
	public SysUserInfo getSysUserInfo(String userid){
		Integer iid = Integer.valueOf(userid);
		return  (SysUserInfo)baseDAO.getObject(modelname,SysUserInfo.class,iid);
	}

	/**
	 *根据id获取系统用户信息
	 *@param userid Integer
	 *@return SysUserInfo
	 */
	public SysUserInfo getSysUserInfo(Integer userid){
		return  (SysUserInfo)baseDAO.getObject(modelname,SysUserInfo.class,userid);
	}

	/**
	 *增加系统用户信息
	 *@param sysUserInfo SysUserInfo
	 *@return SysUserInfo
	 */
	public SysUserInfo addSysUserInfo(SysUserInfo sysUserInfo){
		return (SysUserInfo)baseDAO.addObject(modelname,sysUserInfo);
	}

	/**
	 *删除系统用户信息
	 *@param userid Integer
	 *@return SysUserInfo
	 */
	public SysUserInfo delSysUserInfo(String userid){
		SysUserInfo model = getSysUserInfo(userid);
		return (SysUserInfo)baseDAO.delObject(modelname,model);
	}

	/**
	 *修改系统用户信息
	 *@param sysUserInfo SysUserInfo
	 *@return SysUserInfo
	 */
	public SysUserInfo updateSysUserInfo(SysUserInfo sysUserInfo){
		return (SysUserInfo)baseDAO.updateObject(modelname,sysUserInfo);
	}

	/**
	 *获取系统用户信息集合
 	 *@return List
	 */
	public List getSysUserInfos(List<SearchModel> condition,String orderby,int pagesize){
		return baseDAO.getObjects("SysUserInfo",condition,orderby,pagesize);
	}

	/**
	 *获取一页系统用户信息集合
	 *@param start int
	 *@param pagesize int
	 *@return PageList
	 */
	public PageList getPageSysUserInfos (List<SearchModel> condition,String orderby,int start,int pagesize){
		return baseDAO.getPageObjects("SysUserInfo","userid",condition, orderby, start,pagesize);
	}
	
	public List getSysUserInfosOfEcu(String unitid,String username,String usertype,String courseid,String courseclassid) {
        String sql = "select a from SysUserInfo as a where a.userid not in (select b.userid from EduCourseUser as b where b.courseclassid="+courseclassid+" and b.courseid="+courseid+" and b.usertype='3')   and a.loginname <> '~admin~' and a.status ='1'";
        if (unitid != null && unitid.trim().length() > 0) {
            sql += " and a.unitid = " + unitid;
        }
        if (username != null && username.trim().length() > 0) {
            sql += " and a.username like '%" + username + "%'";
        }
        if (usertype != null && usertype.trim().length() > 0) {
            sql += " and a.usertype ='" + usertype + "'";
        }
        List list = baseDAO.getObjects(sql);
        return list;
    }
	
	public PageList getSysUserInfosOfEcuOfPage(String unitid,String username,String usertype,String courseid,String courseclassid,String sorderindex,int start, int size) {
        String sql = "select a from SysUserInfo as a where a.userid not in (select b.userid from EduCourseUser as b where b.courseclassid="+courseclassid+" and b.courseid="+courseid+" and b.usertype in (1,2,3))   and a.loginname <> '~admin~' and a.status ='1'";
        String sqlcount = "select count(a.userid) from SysUserInfo as a where a.userid not in (select b.userid from EduCourseUser as b where b.courseclassid="+courseclassid+" and b.courseid="+courseid+" and b.usertype in (1,2,3))   and a.loginname <> '~admin~' and a.status ='1'";
        if (unitid != null && unitid.trim().length() > 0) {
            sql += " and a.unitid = " + unitid;
            sqlcount += " and a.unitid = " + unitid;
        }
        if (username != null && username.trim().length() > 0) {
            sql += " and a.username like '%" + username + "%'";
            sqlcount += " and a.username like '%" + username + "%'";
        }
        if (usertype != null && usertype.trim().length() > 0) {
            sql += " and a.usertype ='" + usertype + "'";
            sqlcount += " and a.usertype ='" + usertype + "'";
        }
        if (sorderindex != null && sorderindex.trim().length() > 0) {
            sql += " order by " + sorderindex;
            sqlcount += " order by " + sorderindex;
        }
        return baseDAO.getPageObjects(sqlcount, sql, start, size);
    }
	/**
	 * 根据登录名获取用户信息
	 * @param loginname
	 * @return
	 */
	public SysUserInfo getUserInfoByLoginName(String loginname) {
		String sql = "select a from SysUserInfo as a where a.loginname='" + loginname + "'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (SysUserInfo) list.get(0);
		}else {
			return null;
		}
	}

	/**
	 * 根据用户名获取用户信息
	 * @param loginname
	 * @return
	 */
	public SysUserInfo getUserInfoByUserName(String username) {
		String sql = "select a from SysUserInfo as a where a.username='" + username + "'";
		List list = baseDAO.getObjects(sql);
		if(list != null && list.size() > 0){
			return (SysUserInfo) list.get(0);
		}else {
			return null;
		}
	}
	
	
	/**
	 * 根据某字段查询用户信息
	 * @param checkname 查询字段值
	 * @param checktype 查询字段类型 1登录名，2学号，3身份证号，4手机号，5邮箱
	 * @return
	 */
	public SysUserInfo getUserInfoByCheckName(String checkname, String checktype) {
		String sql = "select a from SysUserInfo as a where 1=1";
		if(checkname != null && !"".equals(checkname)){
			if("1".equals(checktype)){
				sql = sql + " and a.loginname='" + checkname + "'";
			}else if("2".equals(checktype)){
				sql = sql + " and a.studentno='" + checkname + "'";			
			}else if("3".equals(checktype)){
				sql = "select a from SysUserInfo as a, SysUserInfoDetail as b where a.userid=b.userid and b.cardno='" + checkname + "'";
			}else if("4".equals(checktype)){
				sql = sql + " and a.mobile='" + checkname + "'";
			}else if("5".equals(checktype)){
				sql = sql + " and a.email='" + checkname + "'";
			}else {
				return null;
			}
			List list = baseDAO.getObjects(sql);
			if(list != null && list.size() > 0){
				return (SysUserInfo) list.get(0);
			}else {
				return null;
			}
		}else {
			return null;
		}
	}
	/**
	 * 根据用户类型进行分组，并获取每一类的总数
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-9-27 下午6:21:13 
	* @lastModified  2016-9-27 下午6:21:13 
	* @version  1.0 
	* @param hql
	* @return
	 */
	public List getUserInfoCountByUserType(String strings){
		String sql="";
		String strings1 = strings.substring(0,strings.length()-1);
		if(strings != null && !"".equals(strings)){
			sql="select a.usertype,a.sex,a.xueduan,COUNT(*) from SysUserInfo as a where a.usertype in (1,2) group by "+strings1;
		}
			List list = baseDAO.getObjects(sql);
			if (list != null && list.size() > 0) {
				return  list;
			} else {
				return null;
			}
	}
	
	/**
	 * 按照用户注册日期排序，获取用户注册信息
	* @author  liud 
	* @Description : TODO 
	* @CreateDate  2016-10-8 上午9:48:51 
	* @lastModified  2016-10-8 上午9:48:51 
	* @version  1.0 
	* @return
	 */
	public List getUserInfos(String startTime,String endTime){
		String sql="select a.userid,date_format(a.createdate,'%Y-%m-%d') as day,count(*) from SysUserInfoDetail as a where a.userid in (select b.userid from SysUserInfo as b where b.usertype in (1,2,3)) and date_format(a.createdate,'%Y-%m-%d') between '"+startTime+"' and '"+endTime+"' group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
	public List getUserInfosOfAdd(){
		String sql="select a.userid,date_format(a.createdate,'%Y/%m/%d') as day,count(*) from SysUserInfoDetail as a where a.userid in (select b.userid from SysUserInfo as b where b.usertype in (1,2))  group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}

	/**
	 * 
	  * 方法描述：每天注册人数最大峰值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘冬
	  * @version: 2016-11-25 上午9:38:45
	 */
	public List getUserInfosOfAddMaxNum(){
		String sql="select count(*)  from SysUserInfoDetail as a where a.userid in (select b.userid from SysUserInfo as b where b.usertype in (1,2))  group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
	
public List getUserInfosOfLog(){
		String sql="select date_format(a.createdate,'%Y/%m/%d') as day,count(*) from SysUserLog as a   group by date_format(a.createdate,'%Y-%m-%d')";
		List list = baseDAO.getObjects(sql);
		if (list != null && list.size() > 0) {
			return  list;
		} else {
			return null;
		}
	}
/* 
* 方法描述：每天使用人数最大峰值
* @param: 
* @return: 
* @version: 1.0
* @author: 刘冬
* @version: 2016-11-25 上午9:38:45
*/
public List getUserInfosOfLogMaxNum(){
	String sql="select count(*)  from SysUserLog as a   group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}

/*
 * 每天交易数
	 * @see com.wkmk.sys.service.SysUserInfoManager#getUserInfosOfMoney()
 */
public List getUserInfosOfMoney(){
	String sql="select date_format(a.createdate,'%Y/%m/%d') as day,count(*) from SysUserMoney as a   group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}

/*
 * 每天交易总人数
 */
public List getUserInfosOfMoneyCount(){
	String sql="select date_format(a.createdate,'%Y/%m/%d') as day,count(DISTINCT a.userid) from SysUserMoney as a   group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}
/* 
* 方法描述：每天交易人数最大峰值
* @param: 
* @return: 
* @version: 1.0
* @author: 刘冬
* @version: 2016-11-25 上午9:38:45
*/
public List getUserInfosOfMoneyMaxNum(){
String sql="select count(*)  from SysUserMoney as a   group by date_format(a.createdate,'%Y-%m-%d')";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
	return  list;
} else {
	return null;
}
}

/**
 * 交易总额
 */
public List getUserInfosOfMoneyAll(){
	String sql="select date_format(a.createdate,'%Y/%m/%d') as day,sum(changemoney) from SysUserMoney as a   group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}
/**
 * 支出总额
 */
public List getUserInfosOfMoneyOutAll(){
	String sql="select date_format(a.createdate,'%Y/%m/%d') as day,SUM(changemoney) from SysUserMoney as a where a.changetype=-1   group by date_format(a.createdate,'%Y-%m-%d')  ORDER  BY DATE_FORMAT(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}
/**
 * 收入总额
 */
public List getUserInfosOfMoneyInAll(){
	String sql="select date_format(a.createdate,'%Y/%m/%d') as day,SUM(changemoney) from SysUserMoney as a where a.changetype=1   group by date_format(a.createdate,'%Y-%m-%d')  ORDER  BY DATE_FORMAT(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}
/* 
* 方法描述：每天交易总额最大峰值
* @param: 
* @return: 
* @version: 1.0
* @author: 刘冬
* @version: 2016-11-25 上午9:38:45
*/
public List getUserInfosOfMoneyAllMaxNum(){
String sql="select sum(changemoney)  from SysUserMoney as a   group by date_format(a.createdate,'%Y-%m-%d')";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
	return  list;
} else {
	return null;
}
}

/**
 * 交易总额
 */
public List getUserInfosOfCountMoney(){
String sql="select sum(changemoney)  from SysUserMoney as a ";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
    Object c = list.get(0);
    Double c1= (Double) c;
    if(c1 !=null && c1 != 0.00){
        c= String.format("%.2f", c1);
    }else{
        c =0.00;
    }
    list.set(0, c);
	return  list;
} else {
	return null;
}
}

/**
 * 交易总数
 */
public List getUserInfosOfCountNum(){
String sql="select COUNT(*)  from SysUserMoney as a ";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
	return  list;
} else {
	return null;
}
}

/**
 * 交易总人数
 */
public List getUserInfosOfCountNumProper(){
String sql="select COUNT(DISTINCT a.userid)  from SysUserMoney as a ";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
	return  list;
} else {
	return null;
}
}

/**
 * 支出总额
 */
public List getUserInfosOfCountMoneyOut(){
String sql="select sum(changemoney)  from SysUserMoney as a WHERE a.changetype=-1 ";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
    Object c = list.get(0);
    Double c1= (Double) c;
    if(c1 !=null && c1 != 0.00){
        c= String.format("%.2f", c1);
    }else{
        c =0.00;
    }
    list.set(0, c);
	return  list;
} else {
	return null;
}
}

/**
 * 收入总额
 */
public List getUserInfosOfCountMoneyIn(){
String sql="select sum(changemoney) from SysUserMoney as a WHERE a.changetype=1";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
    Object c = list.get(0);
    Double c1= (Double) c;
    if(c1 !=null && c1 != 0.00){
        c= String.format("%.2f", c1);
    }else{
        c =0.00;
    }
    list.set(0, c);
	return  list;
} else {
	return null;
}
}

public List getUserInfosOfPay(){
	String sql="select date_format(a.createdate,'%Y/%m/%d ') as day,count(*) from SysUserPay as a where a.changetype=1   group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}
/* 
* 方法描述：每天充值人数最大峰值
* @param: 
* @return: 
* @version: 1.0
* @author: 刘冬
* @version: 2016-11-25 上午9:38:45
*/
public List getUserInfosOfPayMaxNum(){
String sql="select count(*)  from SysUserPay as a where a.changetype=1   group by date_format(a.createdate,'%Y-%m-%d')";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
	return  list;
} else {
	return null;
}
}

/**
 * 充值（平台收入）总额
 */
public List getUserInfosOfPayCountMoney(){
String sql="select sum(changemoney)  from SysUserPay as a  where a.changetype=1";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
    Object c = list.get(0);
    Double c1= (Double) c;
    if(c1 !=null && c1 != 0.00){
        c= String.format("%.2f", c1);
    }else{
        c =0.00;
    }
    list.set(0, c);
    return  list;
} else {
    return null;
}
}

/**
 * 交易总数
 */
public List getUserInfosOfPayCountNum(){
String sql="select COUNT(*)  from SysUserPay as a  where a.changetype=1";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
    return  list;
} else {
    return null;
}
}

/**
 * 交易总人数
 */
public List getUserInfosOfPayCountNumProper(){
String sql="select COUNT(DISTINCT a.userid)  from SysUserPay as a  where a.changetype=1";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
    return  list;
} else {
    return null;
}
}

public List getUserInfosOfPayAll(){
	String sql="select date_format(a.createdate,'%Y/%m/%d') as day,sum(changemoney) from SysUserPay as a where a.changetype=1   group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}
/* 
* 方法描述：每天充值总额最大峰值
* @param: 
* @return: 
* @version: 1.0
* @author: 刘冬
* @version: 2016-11-25 上午9:38:45
*/
public List getUserInfosOfPayAllMaxNum(){
String sql="select sum(changemoney)  from SysUserPay as a where a.changetype=1 group by date_format(a.createdate,'%Y-%m-%d')";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
	return  list;
} else {
	return null;
}
}

/**
 * 
  * 方法描述：个人支出
  * @param: 
  * @return: 
  * @version: 1.0
  * @author: 刘冬
  * @version: 2016-11-25 下午2:39:59
 */
public List getPersonalOfMoneyOut(Integer userid){
	String sql="select date_format(a.createdate,'%Y/%m/%d') as day,sum(changemoney) from SysUserMoney as a where a.changetype=-1 and a.userid ="+userid+"  group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}
/* 
* 方法描述：每天支出最大峰值
* @param: 
* @return: 
* @version: 1.0
* @author: 刘冬
* @version: 2016-11-25 上午9:38:45
*/
public List getPersonalOfMoneyOutMaxNum(Integer userid){
	String sql="select sum(changemoney)  from SysUserMoney as a where a.changetype=-1 and a.userid ="+userid+"  group by date_format(a.createdate,'%Y-%m-%d')";
List list = baseDAO.getObjects(sql);
if (list != null && list.size() > 0) {
	return  list;
} else {
	return null;
}
}

/**
 * 
  * 方法描述：个人收入
  * @param: 
  * @return: 
  * @version: 1.0
  * @author: 刘冬
  * @version: 2016-11-25 下午2:39:59
 */
public List getPersonalOfMoneyIn(Integer userid){
	String sql="select date_format(a.createdate,'%Y/%m/%d') as day,sum(changemoney) from SysUserMoney as a where a.changetype=1 and a.userid ="+userid+"  group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
}
/* 
* 方法描述：每天收入最大峰值
* @param: 
* @return: 
* @version: 1.0
* @author: 刘冬
* @version: 2016-11-25 上午9:38:45
*/
public List getPersonalOfMoneyInMaxNum(Integer userid){
	String sql="select sum(changemoney)  from SysUserMoney as a where a.changetype=1 and a.userid ="+userid+"  group by date_format(a.createdate,'%Y-%m-%d')";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
	}

/**
 * 
  * 方法描述：获取所有登录名
  * @param: 
  * @return: 
  * @version: 1.0
  * @author: 刘冬
  * @version: 2017-6-1 下午5:14:51
 */
public List getAllLoginname(){
	String sql="select a.loginname  from SysUserInfo as a ";
	List list = baseDAO.getObjects(sql);
	if (list != null && list.size() > 0) {
		return  list;
	} else {
		return null;
	}
	}
}