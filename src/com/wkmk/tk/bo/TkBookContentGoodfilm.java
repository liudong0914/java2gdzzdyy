package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 精品微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentGoodfilm extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer goodfilmid;
	private Integer bookcontentid;//关联作业本内容，及具体作业
	private Integer bookid;//关联作业本
	private String type;//1解题微课，2举一反三微课
	private String status;//1正常，0禁用关闭

	public TkBookContentGoodfilm(){
	}

 	public Integer getGoodfilmid() {
		return this.goodfilmid;
	}

	public void setGoodfilmid(Integer goodfilmid) {
		this.goodfilmid=goodfilmid;
	}

	public Integer getBookcontentid() {
		return this.bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid=bookcontentid;
	}

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid=bookid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("goodfilmid",getGoodfilmid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContentGoodfilm))
		return false;
		TkBookContentGoodfilm castOther = (TkBookContentGoodfilm)other;
		return new EqualsBuilder().append(goodfilmid, castOther.goodfilmid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(goodfilmid).toHashCode();
	}

}