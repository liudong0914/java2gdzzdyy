package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 教材基本信息表</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkTextBookInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer textbookid;
	private Integer orderindex;//排序
	private String textbookname;//教材名称
	private String textbookno;//教材书号
	private String author;//作者
	private String sketch;//缩略图
	private String press;//出版社
	private Integer unitid;//单位
	private String status;//默认1正常，2禁用
	private Float price;//单价
	private Float discount;//折扣
	private Float sellprice;//售价
	private Integer sellcount;//售卖数量
	private String createdate;//创建时间
	private String phone;//订购联系电话
	private String type;//教材范围和种类

	public TkTextBookInfo(){
	}

 	public Integer getTextbookid() {
		return this.textbookid;
	}

	public void setTextbookid(Integer textbookid) {
		this.textbookid=textbookid;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public String getTextbookname() {
		return this.textbookname;
	}

	public void setTextbookname(String textbookname) {
		this.textbookname=textbookname;
	}

	public String getTextbookno() {
		return textbookno;
	}

	public void setTextbookno(String textbookno) {
		this.textbookno = textbookno;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author=author;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch=sketch;
	}

	public String getPress() {
		return this.press;
	}

	public void setPress(String press) {
		this.press=press;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getDiscount() {
		return discount;
	}

	public void setDiscount(Float discount) {
		this.discount = discount;
	}

	public Float getSellprice() {
		return sellprice;
	}

	public void setSellprice(Float sellprice) {
		this.sellprice = sellprice;
	}

	public Integer getSellcount() {
		return this.sellcount;
	}

	public void setSellcount(Integer sellcount) {
		this.sellcount=sellcount;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone=phone;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type=type;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("textbookid",getTextbookid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkTextBookInfo))
		return false;
		TkTextBookInfo castOther = (TkTextBookInfo)other;
		return new EqualsBuilder().append(textbookid, castOther.textbookid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(textbookid).toHashCode();
	}

}