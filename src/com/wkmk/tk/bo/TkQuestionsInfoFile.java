package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 题库碎片化附件</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkQuestionsInfoFile extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer fileid;
	private String filepath;//试题碎片化原始文件路径，如果多题在一个文件中批量碎，则此可能会是重复一样的
	private String questionsfilepath;//当前完整试题源文件路径，需要把每道题单独生成碎片化文件
	private String titlefilepath;//题干碎片化文件
	private String option1filepath;//选项碎片化文件
	private String option2filepath;
	private String option3filepath;
	private String option4filepath;
	private String option5filepath;
	private String option6filepath;
	private String option7filepath;
	private String option8filepath;
	private String option9filepath;
	private String option10filepath;
	private String rightansfilepath;//参考答案碎片化文件，主观题有，客观题没有
	private String descriptfilepath;//答案解析碎片化文件
	private Integer questionid;

	public TkQuestionsInfoFile(){
	}

 	public Integer getFileid() {
		return this.fileid;
	}

	public void setFileid(Integer fileid) {
		this.fileid=fileid;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath=filepath;
	}

	public String getQuestionsfilepath() {
		return this.questionsfilepath;
	}

	public void setQuestionsfilepath(String questionsfilepath) {
		this.questionsfilepath=questionsfilepath;
	}

	public String getTitlefilepath() {
		return this.titlefilepath;
	}

	public void setTitlefilepath(String titlefilepath) {
		this.titlefilepath=titlefilepath;
	}

	public String getOption1filepath() {
		return this.option1filepath;
	}

	public void setOption1filepath(String option1filepath) {
		this.option1filepath=option1filepath;
	}

	public String getOption2filepath() {
		return this.option2filepath;
	}

	public void setOption2filepath(String option2filepath) {
		this.option2filepath=option2filepath;
	}

	public String getOption3filepath() {
		return this.option3filepath;
	}

	public void setOption3filepath(String option3filepath) {
		this.option3filepath=option3filepath;
	}

	public String getOption4filepath() {
		return this.option4filepath;
	}

	public void setOption4filepath(String option4filepath) {
		this.option4filepath=option4filepath;
	}

	public String getOption5filepath() {
		return this.option5filepath;
	}

	public void setOption5filepath(String option5filepath) {
		this.option5filepath=option5filepath;
	}

	public String getOption6filepath() {
		return this.option6filepath;
	}

	public void setOption6filepath(String option6filepath) {
		this.option6filepath=option6filepath;
	}

	public String getOption7filepath() {
		return this.option7filepath;
	}

	public void setOption7filepath(String option7filepath) {
		this.option7filepath=option7filepath;
	}

	public String getOption8filepath() {
		return this.option8filepath;
	}

	public void setOption8filepath(String option8filepath) {
		this.option8filepath=option8filepath;
	}

	public String getOption9filepath() {
		return this.option9filepath;
	}

	public void setOption9filepath(String option9filepath) {
		this.option9filepath=option9filepath;
	}

	public String getOption10filepath() {
		return this.option10filepath;
	}

	public void setOption10filepath(String option10filepath) {
		this.option10filepath=option10filepath;
	}

	public String getRightansfilepath() {
		return this.rightansfilepath;
	}

	public void setRightansfilepath(String rightansfilepath) {
		this.rightansfilepath=rightansfilepath;
	}

	public String getDescriptfilepath() {
		return this.descriptfilepath;
	}

	public void setDescriptfilepath(String descriptfilepath) {
		this.descriptfilepath=descriptfilepath;
	}

	public Integer getQuestionid() {
		return this.questionid;
	}

	public void setQuestionid(Integer questionid) {
		this.questionid=questionid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("fileid",getFileid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkQuestionsInfoFile))
		return false;
		TkQuestionsInfoFile castOther = (TkQuestionsInfoFile)other;
		return new EqualsBuilder().append(fileid, castOther.fileid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(fileid).toHashCode();
	}

}