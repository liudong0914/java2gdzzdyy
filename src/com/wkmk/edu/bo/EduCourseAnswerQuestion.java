package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;
import com.wkmk.sys.bo.SysUserInfo;

/**
 *<p>Description: 课程答疑</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseAnswerQuestion extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer questionid;
	private Integer courseclassid;//课程班
	private Integer courseid;//课程
	private String content;//提问内容
	private SysUserInfo student;//提问学生
	private String userip;
	private String createdate;
	private String status;//默认0待回复，1已回复
	private SysUserInfo teacher;//回答提问教师或助教
	private String replycontent;//回复内容
	private String replyuserip;
	private String replycreatedate;

	public EduCourseAnswerQuestion(){
	}

 	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public Integer getCourseclassid() {
		return this.courseclassid;
	}

	public void setCourseclassid(Integer courseclassid) {
		this.courseclassid=courseclassid;
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

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
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
		.append("questionid",getQuestionid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseAnswerQuestion))
		return false;
		EduCourseAnswerQuestion castOther = (EduCourseAnswerQuestion)other;
		return new EqualsBuilder().append(questionid, castOther.questionid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(questionid).toHashCode();
	}

	public SysUserInfo getStudent() {
		return student;
	}

	public void setStudent(SysUserInfo student) {
		this.student = student;
	}

	public SysUserInfo getTeacher() {
		return teacher;
	}

	public void setTeacher(SysUserInfo teacher) {
		this.teacher = teacher;
	}

}