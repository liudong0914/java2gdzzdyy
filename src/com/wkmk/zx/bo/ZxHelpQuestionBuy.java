package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线答疑购买</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionBuy extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer buyid;
	private Integer questionid;//购买在线答疑提问【说明：和解题微课不同的是，免费答疑无需购买，可直接查看答案和发表评价，回答提问教师也可直接评价】
	private Integer userid;//购买学生id
	private Float price;//定价，即原价，提问时的定价
	private Float discount;//折扣，默认规则5.0折
	private Float sellprice;//售卖价格，即当前卖价
	private String createdate;//购买时间
	private String enddate;//购买后结束时间，即购买后有效查看时间，在有效期内可查看，超出有效期需要重新购买。如果不限制，则为2088-08-08 20:00:00

	public ZxHelpQuestionBuy(){
	}

 	public Integer getBuyid() {
		return this.buyid;
	}

	public void setBuyid(Integer buyid) {
		this.buyid=buyid;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
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
		if (!(other instanceof ZxHelpQuestionBuy))
		return false;
		ZxHelpQuestionBuy castOther = (ZxHelpQuestionBuy)other;
		return new EqualsBuilder().append(buyid, castOther.buyid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(buyid).toHashCode();
	}

}