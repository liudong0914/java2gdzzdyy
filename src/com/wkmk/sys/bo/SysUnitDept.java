package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统单位部门</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitDept extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer deptid;
	private String deptname;//部门名称
	private String deptno;//部门编号
	private String parentno;//部门父编号
	private String telephone;//部门电话
	private String fax;//部门传真
	private String leader;//部门领导
	private String status;//状态(0关闭，1正常)
	private Integer unitid;

	public SysUnitDept(){
	}

 	public Integer getDeptid() {
		return this.deptid;
	}

	public void setDeptid(Integer deptid) {
		this.deptid=deptid;
	}

	public String getDeptname() {
		return this.deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname=deptname;
	}

	public String getDeptno() {
		return this.deptno;
	}

	public void setDeptno(String deptno) {
		this.deptno=deptno;
	}

	public String getParentno() {
		return this.parentno;
	}

	public void setParentno(String parentno) {
		this.parentno=parentno;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone=telephone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax=fax;
	}

	public String getLeader() {
		return this.leader;
	}

	public void setLeader(String leader) {
		this.leader=leader;
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
		.append("deptid",getDeptid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUnitDept))
		return false;
		SysUnitDept castOther = (SysUnitDept)other;
		return new EqualsBuilder().append(deptid, castOther.deptid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(deptid).toHashCode();
	}

}