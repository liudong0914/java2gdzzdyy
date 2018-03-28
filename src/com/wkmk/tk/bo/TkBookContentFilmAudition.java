package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 试听解题微课</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmAudition extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer auditionid;
	private Integer contentfilmid;//关联TkBookContentFilm主键
	private Integer orderindex;//排序，越大越靠前
	private String status;//1显示
	private Integer hits;//试听微课用户播放记录，单独记录

	public TkBookContentFilmAudition(){
	}

 	public Integer getAuditionid() {
		return this.auditionid;
	}

	public void setAuditionid(Integer auditionid) {
		this.auditionid=auditionid;
	}

	public Integer getContentfilmid() {
		return this.contentfilmid;
	}

	public void setContentfilmid(Integer contentfilmid) {
		this.contentfilmid=contentfilmid;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("auditionid",getAuditionid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContentFilmAudition))
		return false;
		TkBookContentFilmAudition castOther = (TkBookContentFilmAudition)other;
		return new EqualsBuilder().append(auditionid, castOther.auditionid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(auditionid).toHashCode();
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

}