package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统支付账号</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysPaymentAccount extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer paymentid;
	private Integer fromuserid;//支付账号
	private String fromuserip;//支付账号发起ip地址
	private Integer touserid;//收款账号
	private Float changemoney;//支付金额
	private Integer changetype;//交易类型，默认0付款交易待完成，-1交易未完成退款，1交易成功支付，2投诉退款【投诉老师回答】
	private String outtype;//交易关联外部事件类型，1关联答疑，3关联在线批阅，
	private Integer outobjid;//交易关联外部事件id
	private String createdate;//创建此支付时间
	private String happendate;//实际扣款时间
	private String status;//最终为0完成交易操作。默认1学生发起提问预支付，2老师回答待付款，3投诉老师待处理

	public SysPaymentAccount(){
	}

 	public Integer getPaymentid() {
		return this.paymentid;
	}

	public void setPaymentid(Integer paymentid) {
		this.paymentid=paymentid;
	}

	public Integer getFromuserid() {
		return this.fromuserid;
	}

	public void setFromuserid(Integer fromuserid) {
		this.fromuserid=fromuserid;
	}

	public String getFromuserip() {
		return this.fromuserip;
	}

	public void setFromuserip(String fromuserip) {
		this.fromuserip=fromuserip;
	}

	public Integer getTouserid() {
		return this.touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid=touserid;
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

	public String getOuttype() {
		return this.outtype;
	}

	public void setOuttype(String outtype) {
		this.outtype=outtype;
	}

	public Integer getOutobjid() {
		return this.outobjid;
	}

	public void setOutobjid(Integer outobjid) {
		this.outobjid=outobjid;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getHappendate() {
		return this.happendate;
	}

	public void setHappendate(String happendate) {
		this.happendate=happendate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("paymentid",getPaymentid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysPaymentAccount))
		return false;
		SysPaymentAccount castOther = (SysPaymentAccount)other;
		return new EqualsBuilder().append(paymentid, castOther.paymentid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(paymentid).toHashCode();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}