package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 试卷附件分类</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperType extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer typeid;
	private String typename;//分类名称，如：中考、高考、中考模拟卷、高考模拟卷、期中考试、期末考试
	private String typeno;//分类编号，排序用
	private String parentno;//父编号，默认统一为0000，前期只处理一级
	private String status;//状态，1正常，0禁用

	public TkPaperType(){
	}

 	public Integer getTypeid() {
		return this.typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid=typeid;
	}

	public String getTypename() {
		return this.typename;
	}

	public void setTypename(String typename) {
		this.typename=typename;
	}

	public String getTypeno() {
		return this.typeno;
	}

	public void setTypeno(String typeno) {
		this.typeno=typeno;
	}

	public String getParentno() {
		return this.parentno;
	}

	public void setParentno(String parentno) {
		this.parentno=parentno;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("typeid",getTypeid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkPaperType))
		return false;
		TkPaperType castOther = (TkPaperType)other;
		return new EqualsBuilder().append(typeid, castOther.typeid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(typeid).toHashCode();
	}

}