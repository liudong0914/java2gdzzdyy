package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线交易订单明细</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUserPayTrade extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer tradeid;
	private String outtradeno;//当前系统随机生成订单号，系统唯一
	private String subject;//主题，简介
	private Float price;//交易价格
	private Integer quantity;//交易数量，默认1
	private Float amount;//交易总价
	private String paytype;//交易方法，微信支付：wxpay，支付宝：alipay
	private String body;//交易主体，即详细描述
	private String createdate;//发生时间
	private String userip;//发生ip地址
	private String state;//【0支付未完成订单，1支付异常订单，2支付完成订单】状态，0发起支付请求，1待发货支付成功【即系统给出的状态，未得到支付接口反馈信息】，2支付完成【真正完成支付，得到支付系统服务端返回消息】
	private Integer userid;//关联用户
	private String tradetype;//订单类型，默认1在线充值，2在线购买解题微课
	private String tradetypeid;//tradetype=2时，对应关联的priceid

	public SysUserPayTrade(){
	}

 	public Integer getTradeid() {
		return this.tradeid;
	}

	public void setTradeid(Integer tradeid) {
		this.tradeid=tradeid;
	}

	public String getOuttradeno() {
		return this.outtradeno;
	}

	public void setOuttradeno(String outtradeno) {
		this.outtradeno=outtradeno;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject=subject;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price=price;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity=quantity;
	}

	public Float getAmount() {
		return this.amount;
	}

	public void setAmount(Float amount) {
		this.amount=amount;
	}

	public String getPaytype() {
		return this.paytype;
	}

	public void setPaytype(String paytype) {
		this.paytype=paytype;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body=body;
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state=state;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("tradeid",getTradeid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUserPayTrade))
		return false;
		SysUserPayTrade castOther = (SysUserPayTrade)other;
		return new EqualsBuilder().append(tradeid, castOther.tradeid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(tradeid).toHashCode();
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public String getTradetypeid() {
		return tradetypeid;
	}

	public void setTradetypeid(String tradetypeid) {
		this.tradetypeid = tradetypeid;
	}

}