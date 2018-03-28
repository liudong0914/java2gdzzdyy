package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线作业批改报告错题内容</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkReportQuestion extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer rqid;
	private String content;//错题内容，一般为截图
	private String scorerate;//得分率
	private String rightanswer;//当前试题正确答案
	private String wrongreason;//错误原因，可以包含解题思路。错误原因可以是多个，从常用错误原因选择，也可手动填写
	private Integer contentfilmid;//关联解题微课，可以根据学生当前选择的作业，老师从作业解题微课列表选择具体某题的解题微课
	private Integer reportid;//关联作业报告

	public ZxHomeworkReportQuestion(){
	}

 	public Integer getRqid() {
		return this.rqid;
	}

	public void setRqid(Integer rqid) {
		this.rqid=rqid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content=content;
	}

	public String getScorerate() {
		return this.scorerate;
	}

	public void setScorerate(String scorerate) {
		this.scorerate=scorerate;
	}

	public String getRightanswer() {
		return this.rightanswer;
	}

	public void setRightanswer(String rightanswer) {
		this.rightanswer=rightanswer;
	}

	public String getWrongreason() {
		return this.wrongreason;
	}

	public void setWrongreason(String wrongreason) {
		this.wrongreason=wrongreason;
	}

	public Integer getContentfilmid() {
		return this.contentfilmid;
	}

	public void setContentfilmid(Integer contentfilmid) {
		this.contentfilmid=contentfilmid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("rqid",getRqid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHomeworkReportQuestion))
		return false;
		ZxHomeworkReportQuestion castOther = (ZxHomeworkReportQuestion)other;
		return new EqualsBuilder().append(rqid, castOther.rqid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(rqid).toHashCode();
	}

	public Integer getReportid() {
		return reportid;
	}

	public void setReportid(Integer reportid) {
		this.reportid = reportid;
	}

}