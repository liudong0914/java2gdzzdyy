package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统消息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysMessageInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer messageid;
	private String title;//标题
	private String content;//内容
	private String type;//默认1为系统消息
	private Integer userid;//发送消息用户，如果是系统自动发送，则为1
	private String createdate;//发送时间

	public SysMessageInfo(){
	}

 	public Integer getMessageid() {
		return this.messageid;
	}

	public void setMessageid(Integer messageid) {
		this.messageid=messageid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content=content;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("messageid",getMessageid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysMessageInfo))
		return false;
		SysMessageInfo castOther = (SysMessageInfo)other;
		return new EqualsBuilder().append(messageid, castOther.messageid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(messageid).toHashCode();
	}

}