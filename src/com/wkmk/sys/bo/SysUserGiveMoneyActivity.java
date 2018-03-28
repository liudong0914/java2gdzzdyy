package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 用户赠送龙币活动</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserGiveMoneyActivity extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer activityid;
	private String name;//显示名称，比如在充值处显示活动名称内容
	private Float money;//对应赠送龙币数，当type=1时有效
	private Float maxmoney;//充值赠送最大龙币数，type=2时有效
	private Float discount;//对应充值折扣数，type=3时有效
	private String type;//分类，1注册赠送，2充值赠送，3充值打折
	private String status;//默认1正常，0禁用
	private String startdate;//活动开始时间，在活动的有效期内，赠送的龙币总和不超过最大值
	private String enddate;//活动结束时间

	public SysUserGiveMoneyActivity(){
	}

 	public Integer getActivityid() {
		return this.activityid;
	}

	public void setActivityid(Integer activityid) {
		this.activityid=activityid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name=name;
	}

	public Float getMoney() {
		return this.money;
	}

	public void setMoney(Float money) {
		this.money=money;
	}

	public Float getDiscount() {
		return this.discount;
	}

	public void setDiscount(Float discount) {
		this.discount=discount;
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
		.append("activityid",getActivityid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserGiveMoneyActivity))
		return false;
		SysUserGiveMoneyActivity castOther = (SysUserGiveMoneyActivity)other;
		return new EqualsBuilder().append(activityid, castOther.activityid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(activityid).toHashCode();
	}

	public Float getMaxmoney() {
		return maxmoney;
	}

	public void setMaxmoney(Float maxmoney) {
		this.maxmoney = maxmoney;
	}

	public String getStartdate() {
		return startdate;
	}

	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

}