package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 试听解题微课播放记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentFilmAuditionWatch extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer watchid;
	private Integer userid;//观看视频用户
	private Integer auditionid;//关联TkBookContentFilmAudition主键
	private String createdate;//观看时间
	private String userip;//当前用户ip地址

	public TkBookContentFilmAuditionWatch(){
	}

 	public Integer getWatchid() {
		return this.watchid;
	}

	public void setWatchid(Integer watchid) {
		this.watchid=watchid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getAuditionid() {
		return this.auditionid;
	}

	public void setAuditionid(Integer auditionid) {
		this.auditionid=auditionid;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("watchid",getWatchid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContentFilmAuditionWatch))
		return false;
		TkBookContentFilmAuditionWatch castOther = (TkBookContentFilmAuditionWatch)other;
		return new EqualsBuilder().append(watchid, castOther.watchid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(watchid).toHashCode();
	}

}