package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 题库试卷样式</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkPaperStyle extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer styleid;
	private Integer paperid;
	private Integer userid;
	private Integer subjectid;
	private Integer xueduanid;//学段id【2小学，3初中，4高中】
	private String h1state;//主标题
	private String h1value;
	private String h2state;//副标题
	private String h2value;
	private String h3state;//装订线
	private String h4state;//保密标记
	private String h4value;//保密内容
	private String h5state;//试卷信息栏
	private String h5value;
	private String h6state;//考生输入栏
	private String h6value;
	private String h7state;//誉分栏
	private String h8state;//注意事项
	private String h8value;
	private String h9state;//第I卷
	private String h9value;
	private String h9value2;
	private String h10state;//第II卷
	private String h10value;
	private String h10value2;

	public TkPaperStyle(){
	}

 	public Integer getStyleid() {
		return this.styleid;
	}

	public void setStyleid(Integer styleid) {
		this.styleid=styleid;
	}

	public Integer getPaperid() {
		return this.paperid;
	}

	public void setPaperid(Integer paperid) {
		this.paperid=paperid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getSubjectid() {
		return this.subjectid;
	}

	public void setSubjectid(Integer subjectid) {
		this.subjectid=subjectid;
	}

	public String getH1state() {
		return this.h1state;
	}

	public void setH1state(String h1state) {
		this.h1state=h1state;
	}

	public String getH1value() {
		return this.h1value;
	}

	public void setH1value(String h1value) {
		this.h1value=h1value;
	}

	public String getH2state() {
		return this.h2state;
	}

	public void setH2state(String h2state) {
		this.h2state=h2state;
	}

	public String getH2value() {
		return this.h2value;
	}

	public void setH2value(String h2value) {
		this.h2value=h2value;
	}

	public String getH3state() {
		return this.h3state;
	}

	public void setH3state(String h3state) {
		this.h3state=h3state;
	}

	public String getH4state() {
		return this.h4state;
	}

	public void setH4state(String h4state) {
		this.h4state=h4state;
	}

	public String getH4value() {
		return this.h4value;
	}

	public void setH4value(String h4value) {
		this.h4value=h4value;
	}

	public String getH5state() {
		return this.h5state;
	}

	public void setH5state(String h5state) {
		this.h5state=h5state;
	}

	public String getH5value() {
		return this.h5value;
	}

	public void setH5value(String h5value) {
		this.h5value=h5value;
	}

	public String getH6state() {
		return this.h6state;
	}

	public void setH6state(String h6state) {
		this.h6state=h6state;
	}

	public String getH6value() {
		return this.h6value;
	}

	public void setH6value(String h6value) {
		this.h6value=h6value;
	}

	public String getH7state() {
		return this.h7state;
	}

	public void setH7state(String h7state) {
		this.h7state=h7state;
	}

	public String getH8state() {
		return this.h8state;
	}

	public void setH8state(String h8state) {
		this.h8state=h8state;
	}

	public String getH8value() {
		return this.h8value;
	}

	public void setH8value(String h8value) {
		this.h8value=h8value;
	}

	public String getH9state() {
		return this.h9state;
	}

	public void setH9state(String h9state) {
		this.h9state=h9state;
	}

	public String getH9value() {
		return this.h9value;
	}

	public void setH9value(String h9value) {
		this.h9value=h9value;
	}

	public String getH9value2() {
		return this.h9value2;
	}

	public void setH9value2(String h9value2) {
		this.h9value2=h9value2;
	}

	public String getH10state() {
		return this.h10state;
	}

	public void setH10state(String h10state) {
		this.h10state=h10state;
	}

	public String getH10value() {
		return this.h10value;
	}

	public void setH10value(String h10value) {
		this.h10value=h10value;
	}

	public String getH10value2() {
		return this.h10value2;
	}

	public void setH10value2(String h10value2) {
		this.h10value2=h10value2;
	}

	public Integer getXueduanid() {
		return xueduanid;
	}

	public void setXueduanid(Integer xueduanid) {
		this.xueduanid = xueduanid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("styleid",getStyleid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkPaperStyle))
		return false;
		TkPaperStyle castOther = (TkPaperStyle)other;
		return new EqualsBuilder().append(styleid, castOther.styleid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(styleid).toHashCode();
	}

}