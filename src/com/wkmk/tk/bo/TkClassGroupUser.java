package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 班级组成员</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassGroupUser extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer groupuserid;
	private Integer userid;//组成员，学生
	private Integer groupid;//所属分组

	public TkClassGroupUser(){
	}

 	public Integer getGroupuserid() {
		return this.groupuserid;
	}

	public void setGroupuserid(Integer groupuserid) {
		this.groupuserid=groupuserid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid=groupid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("groupuserid",getGroupuserid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkClassGroupUser))
		return false;
		TkClassGroupUser castOther = (TkClassGroupUser)other;
		return new EqualsBuilder().append(groupuserid, castOther.groupuserid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(groupuserid).toHashCode();
	}

}