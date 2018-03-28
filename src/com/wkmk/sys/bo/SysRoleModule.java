package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 系统角色模块</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysRoleModule extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer rolemoduleid;
	private String addoperation;//添加模块(0禁止，1允许)
	private String modifyoperation;//删除模块(0禁止，1允许)
	private String deleteoperation;//修改模块(0禁止，1允许)
	private String viewoperation;//查询模块(0禁止，1允许)
	private Integer roleid;//角色id
	private SysModuleInfo sysModuleInfo;//模块对象

	public SysRoleModule(){
	}

 	public Integer getRolemoduleid() {
		return this.rolemoduleid;
	}

	public void setRolemoduleid(Integer rolemoduleid) {
		this.rolemoduleid=rolemoduleid;
	}

	public String getAddoperation() {
		return this.addoperation;
	}

	public void setAddoperation(String addoperation) {
		this.addoperation=addoperation;
	}

	public String getModifyoperation() {
		return this.modifyoperation;
	}

	public void setModifyoperation(String modifyoperation) {
		this.modifyoperation=modifyoperation;
	}

	public String getDeleteoperation() {
		return this.deleteoperation;
	}

	public void setDeleteoperation(String deleteoperation) {
		this.deleteoperation=deleteoperation;
	}

	public String getViewoperation() {
		return this.viewoperation;
	}

	public void setViewoperation(String viewoperation) {
		this.viewoperation=viewoperation;
	}

	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid=roleid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("rolemoduleid",getRolemoduleid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysRoleModule))
		return false;
		SysRoleModule castOther = (SysRoleModule)other;
		return new EqualsBuilder().append(rolemoduleid, castOther.rolemoduleid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(rolemoduleid).toHashCode();
	}

	public SysModuleInfo getSysModuleInfo() {
		return sysModuleInfo;
	}

	public void setSysModuleInfo(SysModuleInfo sysModuleInfo) {
		this.sysModuleInfo = sysModuleInfo;
	}

}
