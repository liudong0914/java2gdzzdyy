package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;
import com.wkmk.sys.bo.SysUserInfo;

/**
 *<p>Description: 在线作业提问</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkQuestion extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer questionid;
	private String title;//在线作业标题
	private String descript;//在线作业描述
	private String createdate;//提交时间
	private String enddate;//有效结束时间（在当前时间没有回复，当前提问失效，无法被抢单）
	private Float money;//作业提问定价
	private String userip;//用户ip地址
	private SysUserInfo sysUserInfo;//提问人
	private Integer subjectid;//答疑所属学科
	private Integer gradeid;//答疑所属年级
	private Integer bookcontentid;//关联作业本内容，即关联所属作业，这样既可根据作业本内容的pagenum自动调出作业内容的pdf图片内容
	private Integer bookid;//关联作业本，便于后期的统计
	private String status;//状态，0待发布，1已发布待抢单，2已被抢单，3老师已回复（自动支付），4投诉老师回复，9订单失效，时间已过

	public ZxHomeworkQuestion(){
	}

 	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
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

	public Float getMoney() {
		return this.money;
	}

	public void setMoney(Float money) {
		this.money=money;
	}

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public Integer getGradeid() {
		return this.gradeid;
	}

	public void setGradeid(Integer gradeid) {
		this.gradeid=gradeid;
	}

	public Integer getBookcontentid() {
		return this.bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid=bookcontentid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("questionid",getQuestionid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHomeworkQuestion))
		return false;
		ZxHomeworkQuestion castOther = (ZxHomeworkQuestion)other;
		return new EqualsBuilder().append(questionid, castOther.questionid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(questionid).toHashCode();
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

	public Integer getBookid() {
		return bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

}