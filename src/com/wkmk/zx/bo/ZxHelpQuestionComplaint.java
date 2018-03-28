package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线答疑投诉</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionComplaint extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer complaintid;
	private Integer questionid;//投诉关联在线提问id
	private Integer orderid;//投诉关联的订单id
	private String descript;//投诉简要内容
	private String status;//0待受理，1接受受理处罚老师，2驳回受理
	private String createdate;//投诉时间
	private Integer userid;//投诉人
	private String userip;//投诉人关联ip地址
	private Integer teacherid;//被投诉老师
	private Integer replyuserid;//后台驳回投诉，受理投诉的人
	private String replycontent;//回复内容
	private String replyuserip;//关联ip
	private String replycreatedate;//回复时间

	public ZxHelpQuestionComplaint(){
	}

 	public Integer getComplaintid() {
		return this.complaintid;
	}

	public void setComplaintid(Integer complaintid) {
		this.complaintid=complaintid;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
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

	public Integer getReplyuserid() {
		return this.replyuserid;
	}

	public void setReplyuserid(Integer replyuserid) {
		this.replyuserid=replyuserid;
	}

	public String getReplycontent() {
		return this.replycontent;
	}

	public void setReplycontent(String replycontent) {
		this.replycontent=replycontent;
	}

	public String getReplyuserip() {
		return this.replyuserip;
	}

	public void setReplyuserip(String replyuserip) {
		this.replyuserip=replyuserip;
	}

	public String getReplycreatedate() {
		return this.replycreatedate;
	}

	public void setReplycreatedate(String replycreatedate) {
		this.replycreatedate=replycreatedate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("complaintid",getComplaintid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHelpQuestionComplaint))
		return false;
		ZxHelpQuestionComplaint castOther = (ZxHelpQuestionComplaint)other;
		return new EqualsBuilder().append(complaintid, castOther.complaintid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(complaintid).toHashCode();
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(Integer teacherid) {
		this.teacherid = teacherid;
	}

}