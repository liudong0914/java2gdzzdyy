package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统用户日志</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserLog extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer logid;
	private String userip;//用户ip
	private String createdate;//创建时间
	private String descript;//日志描述
	private SysUserInfo sysUserInfo;
	private Integer unitid;
	private String logtype;//0表示公共系统日志，1表示个人私有日志

	public SysUserLog(){
	}

 	public Integer getLogid() {
		return this.logid;
	}

	public void setLogid(Integer logid) {
		this.logid=logid;
	}

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("logid",getLogid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserLog))
		return false;
		SysUserLog castOther = (SysUserLog)other;
		return new EqualsBuilder().append(logid, castOther.logid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(logid).toHashCode();
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public String getLogtype() {
		return logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}

}
