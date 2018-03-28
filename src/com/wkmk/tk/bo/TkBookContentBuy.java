package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 作业本内容购买</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentBuy extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer buyid;
	private Integer contentid;//关联作业本内容，如果等于0，则表示直接购买的作业本
	private Integer bookid;//关联作业本
	private Integer userid;//购买作业学生id
	private String type;//购买类型，1解题微课，2举一反三微课。对应TkBookContentPrice.type
	private Float price;//定价，即原价
	private Float discount;//折扣
	private Float sellprice;//售卖价格，即当前卖价【记录当前购买价格，如果原始价格改变，可查询当初购买价格、折扣、原价等】
	private String createdate;//购买时间
	private String enddate;//购买后结束时间，即购买后有效查看时间，在有效期内可查看，超出有效期需要重新购买。如果不限制，则为2088-08-08 20:00:00

	public TkBookContentBuy(){
	}

 	public Integer getBuyid() {
		return this.buyid;
	}

	public void setBuyid(Integer buyid) {
		this.buyid=buyid;
	}

	public Integer getContentid() {
		return this.contentid;
	}

	public void setContentid(Integer contentid) {
		this.contentid=contentid;
	}

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid=bookid;
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

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate=enddate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("buyid",getBuyid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContentBuy))
		return false;
		TkBookContentBuy castOther = (TkBookContentBuy)other;
		return new EqualsBuilder().append(buyid, castOther.buyid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(buyid).toHashCode();
	}

}