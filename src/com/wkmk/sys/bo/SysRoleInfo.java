package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统角色信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysRoleInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer roleid;
	private String roleno;//角色编号
	private String rolename;//角色名称
	private String status;//状态(0禁用，1开通)
	private Integer unitid;

	public SysRoleInfo(){
	}

 	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid=roleid;
	}

	public String getRoleno() {
		return this.roleno;
	}

	public void setRoleno(String roleno) {
		this.roleno=roleno;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename=rolename;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("roleid",getRoleid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysRoleInfo))
		return false;
		SysRoleInfo castOther = (SysRoleInfo)other;
		return new EqualsBuilder().append(roleid, castOther.roleid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(roleid).toHashCode();
	}

}