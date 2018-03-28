package com.wkmk.vwh.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 微课知识点</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class VwhFilmKnopoint extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer fid;
	private Integer filmid;
	private Integer knopointid;

	public VwhFilmKnopoint(){
	}

 	public Integer getFid() {
		return this.fid;
	}

	public void setFid(Integer fid) {
		this.fid=fid;
	}

	public Integer getFilmid() {
		return this.filmid;
	}

	public void setFilmid(Integer filmid) {
		this.filmid=filmid;
	}

	public Integer getKnopointid() {
		return this.knopointid;
	}

	public void setKnopointid(Integer knopointid) {
		this.knopointid=knopointid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("fid",getFid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof VwhFilmKnopoint))
		return false;
		VwhFilmKnopoint castOther = (VwhFilmKnopoint)other;
		return new EqualsBuilder().append(fid, castOther.fid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(fid).toHashCode();
	}

}