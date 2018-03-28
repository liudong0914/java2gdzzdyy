package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 班级分组信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassGroup extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer groupid;
	private String groupno;//分组编号，排序用
	private String groupname;//分组名称
	private Integer classid;//所属班级

	public TkClassGroup(){
	}

 	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid=groupid;
	}

	public String getGroupno() {
		return this.groupno;
	}

	public void setGroupno(String groupno) {
		this.groupno=groupno;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname=groupname;
	}

	public Integer getClassid() {
		return this.classid;
	}

	public void setClassid(Integer classid) {
		this.classid=classid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("groupid",getGroupid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkClassGroup))
		return false;
		TkClassGroup castOther = (TkClassGroup)other;
		return new EqualsBuilder().append(groupid, castOther.groupid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(groupid).toHashCode();
	}

}