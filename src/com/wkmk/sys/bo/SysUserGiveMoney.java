package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 用户赠送龙币</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserGiveMoney extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer moneyid;
	private Integer userid;//关联用户id
	private String userip;//用户ip
	private Float money;//活动赠送龙币值
	private String createdate;//发生时间
	private String type;//分类，1注册赠送龙币，2购买赠送龙币，3购买打折赠送

	public SysUserGiveMoney(){
	}

 	public Integer getMoneyid() {
		return this.moneyid;
	}

	public void setMoneyid(Integer moneyid) {
		this.moneyid=moneyid;
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

	public Float getMoney() {
		return this.money;
	}

	public void setMoney(Float money) {
		this.money=money;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("moneyid",getMoneyid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserGiveMoney))
		return false;
		SysUserGiveMoney castOther = (SysUserGiveMoney)other;
		return new EqualsBuilder().append(moneyid, castOther.moneyid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(moneyid).toHashCode();
	}

}