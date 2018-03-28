package com.wkmk.edu.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 课程购买信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class EduCourseBuy extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer buyid;
	private Integer coursefilmid;//已购买课程视频
	private Integer courseid;//所属课程
	private Float price;//购买时标价，留存用
	private Float discount;//购买时折扣
	private Float sellprice;//实际购买时价格
	private String createdate;//购买时间
	private String enddate;//有效期结束时间，长期有效则为2088-08-08 20:00:00
	private Integer userid;//购买课程用户

	public EduCourseBuy(){
	}

 	public Integer getBuyid() {
		return this.buyid;
	}

	public void setBuyid(Integer buyid) {
		this.buyid=buyid;
	}

	public Integer getCoursefilmid() {
		return this.coursefilmid;
	}

	public void setCoursefilmid(Integer coursefilmid) {
		this.coursefilmid=coursefilmid;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price=price;
	}

	public Float getDiscount() {
		return this.discount;
	}

	public void setDiscount(Float discount) {
		this.discount=discount;
	}

	public Float getSellprice() {
		return this.sellprice;
	}

	public void setSellprice(Float sellprice) {
		this.sellprice=sellprice;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getEnddate() {
		return this.enddate;
	}

	public void setEnddate(String enddate) {
		this.enddate=enddate;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("buyid",getBuyid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof EduCourseBuy))
		return false;
		EduCourseBuy castOther = (EduCourseBuy)other;
		return new EqualsBuilder().append(buyid, castOther.buyid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(buyid).toHashCode();
	}

	public Integer getCourseid() {
		return courseid;
	}

	public void setCourseid(Integer courseid) {
		this.courseid = courseid;
	}

}