package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统模块信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysModuleInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer moduleid;
	private String moduleno;//模块编号
	private String parentno;//模块父编号
	private String modulename;//模块名称
	private String moduleicon;//模块图标
	private String linkurl;//链接地址
	private String status;//状态(0禁用，1开通)
	private String descript;//模块简单描述
	private String autoopen;//自动打开[自动右侧全部展开，左侧隐藏]
	private Integer productid;//产品id

	public SysModuleInfo(){
	}

 	public Integer getModuleid() {
		return this.moduleid;
	}

	public void setModuleid(Integer moduleid) {
		this.moduleid=moduleid;
	}

	public String getModuleno() {
		return this.moduleno;
	}

	public void setModuleno(String moduleno) {
		this.moduleno=moduleno;
	}

	public String getParentno() {
		return this.parentno;
	}

	public void setParentno(String parentno) {
		this.parentno=parentno;
	}

	public String getModulename() {
		return this.modulename;
	}

	public void setModulename(String modulename) {
		this.modulename=modulename;
	}

	public String getModuleicon() {
		return this.moduleicon;
	}

	public void setModuleicon(String moduleicon) {
		this.moduleicon=moduleicon;
	}

	public String getLinkurl() {
		return this.linkurl;
	}

	public void setLinkurl(String linkurl) {
		this.linkurl=linkurl;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getDescript() {
		return this.descript;
	}

	public void setDescript(String descript) {
		this.descript=descript;
	}

	public String getAutoopen() {
		return this.autoopen;
	}

	public void setAutoopen(String autoopen) {
		this.autoopen=autoopen;
	}

	public Integer getProductid() {
		return this.productid;
	}

	public void setProductid(Integer productid) {
		this.productid=productid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("moduleid",getModuleid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysModuleInfo))
		return false;
		SysModuleInfo castOther = (SysModuleInfo)other;
		return new EqualsBuilder().append(moduleid, castOther.moduleid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(moduleid).toHashCode();
	}

}