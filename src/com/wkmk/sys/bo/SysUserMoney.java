package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 用户交易记录</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserMoney extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer moneyid;
	private String title;//交易内容简单描述，名称
	private Float changemoney;//消费或挣取的钱数
	private Integer changetype;//-1减少，1增加
	private Float lastmoney;//当前操作后的余额
	private Integer userid;//关联用户（用户个人查询比较多，管理员查询和用户表级联查询）
	private String createdate;//发生日期
	private String userip;//ip地址
	private String descript;//交易内容详细描述

	public SysUserMoney(){
	}

 	public Integer getMoneyid() {
		return this.moneyid;
	}

	public void setMoneyid(Integer moneyid) {
		this.moneyid=moneyid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public Float getChangemoney() {
		return this.changemoney;
	}

	public void setChangemoney(Float changemoney) {
		this.changemoney=changemoney;
	}

	public Integer getChangetype() {
		return this.changetype;
	}

	public void setChangetype(Integer changetype) {
		this.changetype=changetype;
	}

	public Float getLastmoney() {
		return this.lastmoney;
	}

	public void setLastmoney(Float lastmoney) {
		this.lastmoney=lastmoney;
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

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("moneyid",getMoneyid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserMoney))
		return false;
		SysUserMoney castOther = (SysUserMoney)other;
		return new EqualsBuilder().append(moneyid, castOther.moneyid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(moneyid).toHashCode();
	}

}