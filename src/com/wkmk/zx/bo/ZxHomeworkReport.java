package com.wkmk.zx.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 在线作业批改报告</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkReport extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer reportid;
	private String content;//作业报告内容详情，点评、总结、建议
	private Integer answerid;//关联作业回复
	private Integer userid;//关联教师

	public ZxHomeworkReport(){
	}

 	public Integer getReportid() {
		return this.reportid;
	}

	public void setReportid(Integer reportid) {
		this.reportid=reportid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content=content;
	}

	public Integer getAnswerid() {
		return this.answerid;
	}

	public void setAnswerid(Integer answerid) {
		this.answerid=answerid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("reportid",getReportid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof ZxHomeworkReport))
		return false;
		ZxHomeworkReport castOther = (ZxHomeworkReport)other;
		return new EqualsBuilder().append(reportid, castOther.reportid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(reportid).toHashCode();
	}

}