package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统单位信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysUnitInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer unitid;
	private String unitno;//单位编号(唯一标示)
	private String parentno;//单位父编号
	private String unitname;//单位名称
	private String shortname;//单位简称
	private String ename;//单位英文名称
	private String homepage;//网址
	private String email;//电子邮件
	private String telephone;//联系电话
	private String linkman;//联系人
	private String job;//职务
	private String fax;//传真
	private String province;//省
	private String city;//市
	private String county;//区
	private String keywords;//搜索关键字
	private String postcode;//邮编
	private String address;//联系地址
	private String descript;//描述
	private Integer orderindex;//排序
	private String sketch;//缩略图
	private String logo;
	private String banner;
	private String createdate;//创建时间
	private String updatetime;
	private String status;//状态(0申请，1正常，2禁用)
	private String icp;//单位站点ICP号
	private String type;//学校性质类型，1幼儿，2小学，3初中，4高中；如：1、1;2、1;2;3、2;3、2;3;4等。1中职，2高职，默认为1
	private String schooltype;//学校类型：1基础教育，2职业教育，3高等教育，4继续教育
	private Integer hits; // 点击
	private Integer praise;//点赞
	private String recommand;//推荐
	private Integer recommandno;//推荐编号
	private String domain;//自定义学校首页域名后缀名，如：http://www.wkmk.cn/lyxx（六一小学）

	public SysUnitInfo(){
	}

 	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String getUnitno() {
		return this.unitno;
	}

	public void setUnitno(String unitno) {
		this.unitno=unitno;
	}

	public String getParentno() {
		return this.parentno;
	}

	public void setParentno(String parentno) {
		this.parentno=parentno;
	}

	public String getUnitname() {
		return this.unitname;
	}

	public void setUnitname(String unitname) {
		this.unitname=unitname;
	}

	public String getShortname() {
		return this.shortname;
	}

	public void setShortname(String shortname) {
		this.shortname=shortname;
	}

	public String getEname() {
		return this.ename;
	}

	public void setEname(String ename) {
		this.ename=ename;
	}

	public String getHomepage() {
		return this.homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage=homepage;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	public String getTelephone() {
		return this.telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone=telephone;
	}

	public String getLinkman() {
		return this.linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman=linkman;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax=fax;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords=keywords;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode=postcode;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address=address;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public Integer getOrderindex() {
		return this.orderindex;
	}

	public void setOrderindex(Integer orderindex) {
		this.orderindex=orderindex;
	}

	public String getSketch() {
		return this.sketch;
	}

	public void setSketch(String sketch) {
		this.sketch=sketch;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getIcp() {
		return this.icp;
	}

	public void setIcp(String icp) {
		this.icp=icp;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("unitid",getUnitid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysUnitInfo))
		return false;
		SysUnitInfo castOther = (SysUnitInfo)other;
		return new EqualsBuilder().append(unitid, castOther.unitid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(unitid).toHashCode();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getSchooltype() {
		return schooltype;
	}

	public void setSchooltype(String schooltype) {
		this.schooltype = schooltype;
	}

	public Integer getHits() {
		return hits;
	}

	public void setHits(Integer hits) {
		this.hits = hits;
	}

	public Integer getPraise() {
		return praise;
	}

	public void setPraise(Integer praise) {
		this.praise = praise;
	}

	public String getRecommand() {
		return recommand;
	}

	public void setRecommand(String recommand) {
		this.recommand = recommand;
	}

	public Integer getRecommandno() {
		return recommandno;
	}

	public void setRecommandno(Integer recommandno) {
		this.recommandno = recommandno;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

}