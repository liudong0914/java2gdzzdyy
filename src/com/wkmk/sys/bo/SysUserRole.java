package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统用户角色</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserRole extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer userroleid;
	private SysUserInfo sysUserInfo;//用户对象
	private Integer roleid;//角色id

	public SysUserRole(){
	}

 	public Integer getUserroleid() {
		return this.userroleid;
	}

	public void setUserroleid(Integer userroleid) {
		this.userroleid=userroleid;
	}

	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid=roleid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("userroleid",getUserroleid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserRole))
		return false;
		SysUserRole castOther = (SysUserRole)other;
		return new EqualsBuilder().append(userroleid, castOther.userroleid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(userroleid).toHashCode();
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

}
