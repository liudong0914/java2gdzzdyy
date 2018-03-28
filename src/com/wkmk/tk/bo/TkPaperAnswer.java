package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 试卷作答信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperAnswer extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer answerid;
	private Integer contentid;//试卷内容题id
	private Integer childid;//如果是复合题，需要对每个子题作答情况进行统计【childid只会关联questionid】
	private String type;//默认 1为作业本对应试卷的题，2为课前预习题【直接关联TkQuestionInfo主键questionid】，3为举一反三的题【同理2】
	private Integer bookcontentid;//作业本内容id（包含作业本和试卷信息）
	private Integer taskid;//作业任务id，因为一个作业学生可做多次，方便老师统计查询
	private String tasktype;//作业任务类型，当第一次做即task对应的parentid=0时，tasktype=0，其他为1
	private Integer userid;//考试用户id
	private Integer classid;//用户所在班级id，如果没有关联班级则classid=0
	private String answer;//用户答案，需要与正确答案对比
	private Float score;//当前试题所得分值
	private String isright;// 1 表示做对，0 表示做错

	public TkPaperAnswer(){
	}

 	public Integer getAnswerid() {
		return this.answerid;
	}

	public void setAnswerid(Integer answerid) {
		this.answerid=answerid;
	}

	public Integer getContentid() {
		return this.contentid;
	}

	public void setContentid(Integer contentid) {
		this.contentid=contentid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer=answer;
	}

	public Float getScore() {
		return this.score;
	}

	public void setScore(Float score) {
		this.score=score;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("answerid",getAnswerid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkPaperAnswer))
		return false;
		TkPaperAnswer castOther = (TkPaperAnswer)other;
		return new EqualsBuilder().append(answerid, castOther.answerid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(answerid).toHashCode();
	}

	public Integer getBookcontentid() {
		return bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid = bookcontentid;
	}

	public Integer getClassid() {
		return classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

	public Integer getTaskid() {
		return taskid;
	}

	public void setTaskid(Integer taskid) {
		this.taskid = taskid;
	}

	public String getTasktype() {
		return tasktype;
	}

	public void setTasktype(String tasktype) {
		this.tasktype = tasktype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getChildid() {
		return childid;
	}

	public void setChildid(Integer childid) {
		this.childid = childid;
	}

	public String getIsright() {
		return isright;
	}

	public void setIsright(String isright) {
		this.isright = isright;
	}

}