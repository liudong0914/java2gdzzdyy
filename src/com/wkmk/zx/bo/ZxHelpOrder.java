package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线答疑订单</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpOrder extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer orderid;
	private Integer questionid;//在线答疑提问
	private String createdate;//抢单时间
	private String enddate;//提问过期时间，超过此时间无法回复，等于ZxHelpQuestion.enddate
	private String userip;//用户ip
	private Float money;//抢单时，在线答疑提问定价（因为在没有被抢单之前，答疑提问可能修改定价，被抢单后提问定价不可改）
	private String status;//状态，1已抢单待处理，2完成抢单回复
	private Integer userid;//抢单老师
	private String replycreatedate;//当状态改为status=2时，即老师完成提问回复，记录时间，便于7天后自动给老师打款
	private String paystatus;//默认付款状态为0，当学生确认支付，或到了时间自动支付，状态改为1【此时不可再投诉】
	

	public ZxHelpOrder(){
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
		if (!(other instanceof ZxHelpOrder))
		return false;
		ZxHelpOrder castOther = (ZxHelpOrder)other;
		return new EqualsBuilder().append(orderid, castOther.orderid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(orderid).toHashCode();
	}

	public String getEnddate() {
		return enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}

	public String getReplycreatedate() {
		return replycreatedate;
	}

	public void setReplycreatedate(String replycreatedate) {
		this.replycreatedate = replycreatedate;
	}

	public String getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}

}