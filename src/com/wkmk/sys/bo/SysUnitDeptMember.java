package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统单位部门成员</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitDeptMember extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer memberid;
	private SysUserInfo sysUserInfo;//部门成员
	private Integer deptid;//部门id

	public SysUnitDeptMember(){
	}

 	public Integer getMemberid() {
		return this.memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid=memberid;
	}

	public Integer getDeptid() {
		return this.deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid=deptid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("memberid",getMemberid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUnitDeptMember))
		return false;
		SysUnitDeptMember castOther = (SysUnitDeptMember)other;
		return new EqualsBuilder().append(memberid, castOther.memberid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(memberid).toHashCode();
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

}
