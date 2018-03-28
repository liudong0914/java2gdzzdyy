package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 支付密码错误记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysPayPassword extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer paypasswordid;
	private Integer userid;//关联用户
	private String userip;//用户ip地址
	private String createdate;//发生时间
	private String password;//输入错误的支付密码

	public SysPayPassword(){
	}

 	public Integer getPaypasswordid() {
		return this.paypasswordid;
	}

	public void setPaypasswordid(Integer paypasswordid) {
		this.paypasswordid=paypasswordid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password=password;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("paypasswordid",getPaypasswordid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysPayPassword))
		return false;
		SysPayPassword castOther = (SysPayPassword)other;
		return new EqualsBuilder().append(paypasswordid, castOther.paypasswordid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(paypasswordid).toHashCode();
	}

}