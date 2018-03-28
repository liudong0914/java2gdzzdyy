package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 作业任务信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkBookContentTask extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer taskid;
	private Integer parentid;//任务父id，第一次做某个作业任务时，此为0，否则为第一次的taskid
	private Integer userid;//学员
	private Integer bookcontentid;//具体作业信息
	private String type;//默认1为做作业本对应试卷的题的任务，2为课前预习题任务，3为举一反三的题任务
	private String createdate;
	private Integer classid;//所在班级，如果没有关联班级则classid=0
	private String iscommit;//任务提交必须有按钮触发，如果没有提交作业，则需要清空相关数据【进入前判断】

	public TkBookContentTask(){
	}

 	public Integer getTaskid() {
		return this.taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid=taskid;
	}

	public Integer getParentid() {
		return this.parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid=parentid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getBookcontentid() {
		return this.bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid=bookcontentid;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public Integer getClassid() {
		return this.classid;
	}

	public void setClassid(Integer classid) {
		this.classid=classid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("taskid",getTaskid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContentTask))
		return false;
		TkBookContentTask castOther = (TkBookContentTask)other;
		return new EqualsBuilder().append(taskid, castOther.taskid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(taskid).toHashCode();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIscommit() {
		return iscommit;
	}

	public void setIscommit(String iscommit) {
		this.iscommit = iscommit;
	}

}