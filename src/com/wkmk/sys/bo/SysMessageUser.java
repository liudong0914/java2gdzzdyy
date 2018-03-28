package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 消息用户</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysMessageUser extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer messageuserid;
	private SysMessageInfo sysMessageInfo;//对应消息
	private Integer userid;//接收消息用户
	private String isread;//默认0未阅读，1已阅
	private String readtime;//阅读时间

	public SysMessageUser(){
	}

 	public Integer getMessageuserid() {
		return this.messageuserid;
	}

	public void setMessageuserid(Integer messageuserid) {
		this.messageuserid=messageuserid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getIsread() {
		return this.isread;
	}

	public void setIsread(String isread) {
		this.isread=isread;
	}

	public String getReadtime() {
		return this.readtime;
	}

	public void setReadtime(String readtime) {
		this.readtime=readtime;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("messageuserid",getMessageuserid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysMessageUser))
		return false;
		SysMessageUser castOther = (SysMessageUser)other;
		return new EqualsBuilder().append(messageuserid, castOther.messageuserid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(messageuserid).toHashCode();
	}

	public SysMessageInfo getSysMessageInfo() {
		return sysMessageInfo;
	}

	public void setSysMessageInfo(SysMessageInfo sysMessageInfo) {
		this.sysMessageInfo = sysMessageInfo;
	}

}