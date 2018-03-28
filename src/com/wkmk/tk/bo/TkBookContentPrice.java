package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 作业本内容定价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentPrice extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer priceid;
	private Integer bookcontentid;//关联作业本内容，如果等于0，则表示直接给作业本定价
	private Integer bookid;//关联作业本
	private Float price;//定价，即原价，默认值5元
	private Float discount;//折扣，默认值2折
	private Float sellprice;//售卖价格，即当前卖价，默认值1元
	private Integer sellcount;//已被售卖数量，即购买次数
	private Integer userid;//定价用户
	private String type;//定价类型，1解题微课定价，2举一反三微课定价

	public TkBookContentPrice(){
	}

 	public Integer getPriceid() {
		return this.priceid;
	}

	public void setPriceid(Integer priceid) {
		this.priceid=priceid;
	}

	public Integer getBookcontentid() {
		return this.bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid=bookcontentid;
	}

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid=bookid;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price=price;
	}

	public Float getDiscount() {
		return this.discount;
	}

	public void setDiscount(Float discount) {
		this.discount=discount;
	}

	public Float getSellprice() {
		return this.sellprice;
	}

	public void setSellprice(Float sellprice) {
		this.sellprice=sellprice;
	}

	public Integer getSellcount() {
		return this.sellcount;
	}

	public void setSellcount(Integer sellcount) {
		this.sellcount=sellcount;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("priceid",getPriceid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContentPrice))
		return false;
		TkBookContentPrice castOther = (TkBookContentPrice)other;
		return new EqualsBuilder().append(priceid, castOther.priceid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(priceid).toHashCode();
	}

}