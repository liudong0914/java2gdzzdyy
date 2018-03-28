package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线交易明细</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserPay extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer payid;
	private Float changemoney;//交易金额，单位元
	private Integer changetype;//交易方式，-1提现，1在线充值支付
	private Float lastmoney;//交易后最后剩余金额，便于以后查账对账追溯
	private String remark;//标记，简介
	private Integer userid;//关联用户
	private String userip;//操作ip
	private String createdate;//操作时间
	private String paytype;//交易方法，微信支付：wxpay，支付宝：alipay
	private String outtradeno;//当前系统随机生成订单号，系统唯一
	private String tradeno;//交易后的真实订单号，如支付宝订单号，微信支付订单号，记录，便于查找追溯【即SysUserPayTrade.state=2时交易支付反馈的真实订单号】
	private String openid;//记录用户微信标示
	private String feetype;//交易货币类型，默认CNY人民币
	private String banktype;//在线支付银行卡类型，如招行信用卡：CMB_CREDIT

	public SysUserPay(){
	}

 	public Integer getPayid() {
		return this.payid;
	}

	public void setPayid(Integer payid) {
		this.payid=payid;
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark=remark;
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

	public String getPaytype() {
		return this.paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype=paytype;
	}

	public String getOuttradeno() {
		return this.outtradeno;
	}

	public void setOuttradeno(String outtradeno) {
		this.outtradeno=outtradeno;
	}

	public String getTradeno() {
		return this.tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno=tradeno;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("payid",getPayid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserPay))
		return false;
		SysUserPay castOther = (SysUserPay)other;
		return new EqualsBuilder().append(payid, castOther.payid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(payid).toHashCode();
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getFeetype() {
		return feetype;
	}

	public void setFeetype(String feetype) {
		this.feetype = feetype;
	}

	public String getBanktype() {
		return banktype;
	}

	public void setBanktype(String banktype) {
		this.banktype = banktype;
	}

}