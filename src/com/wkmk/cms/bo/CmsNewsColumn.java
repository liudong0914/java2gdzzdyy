package com.wkmk.cms.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 资讯栏目</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class CmsNewsColumn extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer columnid;
	private String columnno;//栏目编号
	private String parentno;//栏目父编号
	private String columnname;//栏目名称
	private String sketch;//栏目缩略图
	private String status;//默认为1，栏目状态
	private String columntype;//默认为1，栏目类型，资讯列表
	private Integer unitid;//关联单位
	
	public String getId() {
		return String.valueOf(this.columnid);
	}

	public CmsNewsColumn(){
	}

 	public Integer getColumnid() {
		return this.columnid; 
	}

	public void setColumnid(Integer columnid) {
		this.columnid=columnid;
	}

	public String getColumnno() {
		return this.columnno;
	}

	public void setColumnno(String columnno) {
		this.columnno=columnno;
	}

	public String getParentno() {
		return this.parentno;
	}

	public void setParentno(String parentno) {
		this.parentno=parentno;
	}

	public String getColumnname() {
		return this.columnname;
	}

	public void setColumnname(String columnname) {
		this.columnname=columnname;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch=sketch;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getColumntype() {
		return this.columntype;
	}

	public void setColumntype(String columntype) {
		this.columntype=columntype;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("columnid",getColumnid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof CmsNewsColumn))
		return false;
		CmsNewsColumn castOther = (CmsNewsColumn)other;
		return new EqualsBuilder().append(columnid, castOther.columnid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(columnid).toHashCode();
	}

}