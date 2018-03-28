package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;
import com.wkmk.sys.bo.SysUserInfo;

/**
 *<p>Description: 在线答疑评论</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHelpQuestionComment extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer commentid;
	private Integer questionid;//评论的答疑
	private String content;//评价内容
	private Integer score;//打分，1-5分，0-1分差评，1-3中评，3分以上好评
	private SysUserInfo sysUserInfo;//评价用户
	private String userip;//评价用户ip
	private String createdate;//评价时间
	private String anonymous;//默认0不匿名评论，1匿名评论，只是前台不显示用户信息，但还是和用户关联的【显示用户名称第一个字】
	private String status;//默认为1显示，2禁用
	private Integer replyuserid;//回复用户
	private String replycontent;//回复内容
	private String replyuserip;//回复用户ip
	private String replycreatedate;//回复时间

	public ZxHelpQuestionComment(){
	}

 	public Integer getCommentid() {
		return this.commentid;
	}

	public void setCommentid(Integer commentid) {
		this.commentid=commentid;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content=content;
	}

	public Integer getScore() {
		return this.score;
	}

	public void setScore(Integer score) {
		this.score=score;
	}

	public String getUserip() {
		return this.userip;
	}

	public void setUserip(String userip) {
		this.userip=userip;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getAnonymous() {
		return this.anonymous;
	}

	public void setAnonymous(String anonymous) {
		this.anonymous=anonymous;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
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
		.append("commentid",getCommentid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHelpQuestionComment))
		return false;
		ZxHelpQuestionComment castOther = (ZxHelpQuestionComment)other;
		return new EqualsBuilder().append(commentid, castOther.commentid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(commentid).toHashCode();
	}

	public SysUserInfo getSysUserInfo() {
		return sysUserInfo;
	}

	public void setSysUserInfo(SysUserInfo sysUserInfo) {
		this.sysUserInfo = sysUserInfo;
	}

}