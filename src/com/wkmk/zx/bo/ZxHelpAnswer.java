package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线答疑回复</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpAnswer extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer answerid;
	private Integer orderid;//关联订单
	private String content;//回复内容
	private String createdate;//回复时间
	private String userip;//用户ip
	private String status;//状态，0草稿，1发送
	private Integer userid;//回复教师

	public ZxHelpAnswer(){
	}

 	public Integer getAnswerid() {
		return this.answerid;
	}

	public void setAnswerid(Integer answerid) {
		this.answerid=answerid;
	}

	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid=orderid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content=content;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("answerid",getAnswerid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHelpAnswer))
		return false;
		ZxHelpAnswer castOther = (ZxHelpAnswer)other;
		return new EqualsBuilder().append(answerid, castOther.answerid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(answerid).toHashCode();
	}

}