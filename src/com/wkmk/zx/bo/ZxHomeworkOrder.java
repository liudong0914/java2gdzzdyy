package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线作业订单</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkOrder extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer orderid;
	private Integer questionid;//在线作业提问
	private String createdate;//抢单时间
	private String userip;//用户ip
	private Float money;//抢单时，在线作业提问定价（因为在没有被抢单之前，作业提问可能修改定价，被抢单后提问定价不可改）
	private String status;//状态，1已抢单待处理，2完成抢单回复，3过期失效
	private Integer userid;//抢单老师

	public ZxHomeworkOrder(){
	}

 	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid=orderid;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
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

	public Float getMoney() {
		return this.money;
	}

	public void setMoney(Float money) {
		this.money=money;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("orderid",getOrderid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHomeworkOrder))
		return false;
		ZxHomeworkOrder castOther = (ZxHomeworkOrder)other;
		return new EqualsBuilder().append(orderid, castOther.orderid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(orderid).toHashCode();
	}

}