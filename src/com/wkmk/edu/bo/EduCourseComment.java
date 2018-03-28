package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;
import com.wkmk.sys.bo.SysUserInfo;

/**
 *<p>Description: 课程评价</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseComment extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	//课程用户才可以参与当前课程评价，助教或老师可回复
	private Integer commentid;
	private Integer courseid;//评价课程
	private String content;//评价内容
	private Integer score;//评分，1-5分，1分差评，1-3中评，3分以上好评
	private SysUserInfo sysUserInfo;//评价用户
	private String userip;//用户ip地址
	private String createdate;//评价时间
	private String anonymous;//是否匿名评论，默认0不匿名评论
	private String status;//默认为1显示，2禁用
	private Integer replyuserid;//回复用户
	private String replycontent;//回复内容
	private String replyuserip;//回复用户ip
	private String replycreatedate;//回复时间

	public EduCourseComment(){
	}

 	public Integer getCommentid() {
		return this.commentid;
	}

	public void setCommentid(Integer commentid) {
		this.commentid=commentid;
	}

	public Integer getCourseid() {
		return this.courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid=courseid;
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
		if (!(other instanceof EduCourseComment))
		return false;
		EduCourseComment castOther = (EduCourseComment)other;
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