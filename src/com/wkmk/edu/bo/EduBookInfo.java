package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 教材课本</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduBookInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer bookid;
	private String title;//标题
	private String subtitle;//副标题
	private Integer orderindex;//排序，按顺序排
	private String status;//1正常，2禁用
	private Float price;//定价
	private String sketch;//缩略图
	private String coursetypeid;//教材所属大分类，如：1院校企业、2退役军人、3医护行业
	private String createdate;//创建时间

	public EduBookInfo(){
	}

 	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid=bookid;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title=title;
	}

	public String getSubtitle() {
		return this.subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle=subtitle;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price=price;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch=sketch;
	}

	public String getCoursetypeid() {
		return this.coursetypeid;
	}

	public void setCoursetypeid(String coursetypeid) {
		this.coursetypeid=coursetypeid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("bookid",getBookid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduBookInfo))
		return false;
		EduBookInfo castOther = (EduBookInfo)other;
		return new EqualsBuilder().append(bookid, castOther.bookid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(bookid).toHashCode();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

}