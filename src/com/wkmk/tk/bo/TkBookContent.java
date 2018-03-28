package com.wkmk.tk.bo;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.util.bo.BaseObject;

/**
 * <p>
 * Description: 作业本内容
 * </p>
 * <p>
 * E-mail: zhangxuedong28@gmail.com
 * </p>
 * 
 * @version 1.0
 */
public class TkBookContent extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer bookcontentid;
	private String title;// 目录名称
	private String contentno;// 编号
	private String parentno;// 父编号
	private String beforeclass;// 作业课前预习，文本内容+题库结合呈现
	private String beforeclasstwocode;// 作业课前预习二维码，通过二维码可直接查找课前预习内容及相关练习题
	private String teachingcase;// 教学案，文本内容+题库结合呈现
	private String teachingcasetwocode;// 教学案二维码，同上
	private String mp3path;// 英语听力音频文件存放路径
	private String mp3pathtwocode;
	private Integer paperid;// 关联试卷id，如果是一级目录不关联试卷，则paperid=0【前期在初始化数据时，可以先不关联试卷，目的是为了生存对应的“课前预习”和“教学案”二维码】
	private Integer bookid;// 所属作业本
	private String pagenum;//当前作业本内容在pdf页码数，比如：作业一是1-3页，则需要填入1-2-3，“-”便于录入，中文和英文字符一样没有歧义。 便于学生提交在线作业批改时，直接选择作业即可知道作业pdf页内容
	private List errorquestionlist;

	public TkBookContent() {
	}

	public Integer getBookcontentid() {
		return bookcontentid;
	}

	public void setBookcontentid(Integer bookcontentid) {
		this.bookcontentid = bookcontentid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContentno() {
		return this.contentno;
	}

	public void setContentno(String contentno) {
		this.contentno = contentno;
	}

	public String getParentno() {
		return this.parentno;
	}

	public void setParentno(String parentno) {
		this.parentno = parentno;
	}

	public Integer getPaperid() {
		return this.paperid;
	}

	public void setPaperid(Integer paperid) {
		this.paperid = paperid;
	}

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid = bookid;
	}

	public String toString() {
		return new ToStringBuilder(this).append("bookcontentid", getBookcontentid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkBookContent))
			return false;
		TkBookContent castOther = (TkBookContent) other;
		return new EqualsBuilder().append(bookcontentid, castOther.bookcontentid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(bookcontentid).toHashCode();
	}

	public String getBeforeclass() {
		return beforeclass;
	}

	public void setBeforeclass(String beforeclass) {
		this.beforeclass = beforeclass;
	}

	public String getBeforeclasstwocode() {
		return beforeclasstwocode;
	}

	public void setBeforeclasstwocode(String beforeclasstwocode) {
		this.beforeclasstwocode = beforeclasstwocode;
	}

	public String getTeachingcase() {
		return teachingcase;
	}

	public void setTeachingcase(String teachingcase) {
		this.teachingcase = teachingcase;
	}

	public String getTeachingcasetwocode() {
		return teachingcasetwocode;
	}

	public void setTeachingcasetwocode(String teachingcasetwocode) {
		this.teachingcasetwocode = teachingcasetwocode;
	}

	public String getMp3path() {
		return mp3path;
	}

	public void setMp3path(String mp3path) {
		this.mp3path = mp3path;
	}

	public String getMp3pathtwocode() {
		return mp3pathtwocode;
	}

	public void setMp3pathtwocode(String mp3pathtwocode) {
		this.mp3pathtwocode = mp3pathtwocode;
	}

	public List getErrorquestionlist() {
		return errorquestionlist;
	}

	public void setErrorquestionlist(List errorquestionlist) {
		this.errorquestionlist = errorquestionlist;
	}

	public String getPagenum() {
		return pagenum;
	}

	public void setPagenum(String pagenum) {
		this.pagenum = pagenum;
	}

}