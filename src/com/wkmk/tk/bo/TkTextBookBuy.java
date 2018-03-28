package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkTextBookBuy extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer textbookbuyid;
	private Integer textbookid;//订购教材id
	private Float price;//单价
	private Float discount;//折扣
	private Float sellprice;//售价
	private String createdate;//创建时间
	private Integer totalnum;//订购总数
	private Float totalprice;//订购总价
	private Integer buyuserid;//购买人id
	private String recipientname;//收件人姓名
	private String recipientphone;//收件人电话
	private String recipientaddress;//收件人地址
	private String isdelivery;//是否发货,0没有，1已发货
	private String status;//0未付款，不显示，1已付款成功，显示

	public TkTextBookBuy(){
	}

 	public Integer getTextbookbuyid() {
		return this.textbookbuyid;
	}

	public void setTextbookbuyid(Integer textbookbuyid) {
		this.textbookbuyid=textbookbuyid;
	}

	public Integer getTextbookid() {
		return this.textbookid;
	}

	public void setTextbookid(Integer textbookid) {
		this.textbookid=textbookid;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getIsdelivery() {
		return isdelivery;
	}

	public void setIsdelivery(String isdelivery) {
		this.isdelivery = isdelivery;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public Integer getTotalnum() {
		return this.totalnum;
	}

	public void setTotalnum(Integer totalnum) {
		this.totalnum=totalnum;
	}

	

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getSellprice() {
		return sellprice;
	}

	public void setSellprice(Float sellprice) {
		this.sellprice = sellprice;
	}

	public Float getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(Float totalprice) {
		this.totalprice = totalprice;
	}

	public Integer getBuyuserid() {
		return this.buyuserid;
	}

	public void setBuyuserid(Integer buyuserid) {
		this.buyuserid=buyuserid;
	}

	public String getRecipientname() {
		return this.recipientname;
	}

	public void setRecipientname(String recipientname) {
		this.recipientname=recipientname;
	}

	public String getRecipientphone() {
		return this.recipientphone;
	}

	public void setRecipientphone(String recipientphone) {
		this.recipientphone=recipientphone;
	}

	public String getRecipientaddress() {
		return this.recipientaddress;
	}

	public void setRecipientaddress(String recipientaddress) {
		this.recipientaddress=recipientaddress;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("textbookbuyid",getTextbookbuyid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkTextBookBuy))
		return false;
		TkTextBookBuy castOther = (TkTextBookBuy)other;
		return new EqualsBuilder().append(textbookbuyid, castOther.textbookbuyid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(textbookbuyid).toHashCode();
	}

}