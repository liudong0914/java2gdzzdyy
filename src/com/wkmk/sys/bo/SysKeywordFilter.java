package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统关键字过滤</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysKeywordFilter extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer keywordid;
	private String filtercontent;//过滤内容
	private String status;//是否启用内容过滤(0未启用，1已启用)
	private Integer unitid;

	public SysKeywordFilter(){
	}

 	public Integer getKeywordid() {
		return this.keywordid;
	}

	public void setKeywordid(Integer keywordid) {
		this.keywordid=keywordid;
	}

	public String getFiltercontent() {
		return this.filtercontent;
	}

	public void setFiltercontent(String filtercontent) {
		this.filtercontent=filtercontent;
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
		.append("keywordid",getKeywordid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysKeywordFilter))
		return false;
		SysKeywordFilter castOther = (SysKeywordFilter)other;
		return new EqualsBuilder().append(keywordid, castOther.keywordid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(keywordid).toHashCode();
	}

}
