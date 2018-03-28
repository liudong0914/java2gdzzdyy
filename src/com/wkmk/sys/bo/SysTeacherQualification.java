package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 教师资格认证</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysTeacherQualification extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer teacherid;
	private Integer userid;//关联用户id
	private String username;//认证教师名称，人工审核，必须和身份证名称一致，认证通过，把此名称设置到sys_user_info表的username中
	private String sex;//同上
	private String mobile;//默认调用userid关联的手机号，如果没有则需要重新填写，然后进行手机短信验证，验证通过后把此手机号同步到用户表中
	private String identitycardno;//身份证号码
	private String photo;//正规头像
	private String idphto;//手持身份证，拍照上传
	private String imgpath;//教师资格证扫描图片或拍照上传
	private String filepath;//其他附件，格式要求必须zip文件
	private String status;//状态，0待审核，1已审核，2审核未通过
	private String createdate;//创建时间
	private String returndescript;//审核未通过，给出的简要说明

	public SysTeacherQualification(){
	}

 	public Integer getTeacherid() {
		return this.teacherid;
	}

	public void setTeacherid(Integer teacherid) {
		this.teacherid=teacherid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username=username;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex=sex;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile=mobile;
	}

	public String getIdentitycardno() {
		return this.identitycardno;
	}

	public void setIdentitycardno(String identitycardno) {
		this.identitycardno=identitycardno;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo=photo;
	}

	public String getIdphto() {
		return this.idphto;
	}

	public void setIdphto(String idphto) {
		this.idphto=idphto;
	}

	public String getImgpath() {
		return this.imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath=imgpath;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath=filepath;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status=status;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("teacherid",getTeacherid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysTeacherQualification))
		return false;
		SysTeacherQualification castOther = (SysTeacherQualification)other;
		return new EqualsBuilder().append(teacherid, castOther.teacherid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(teacherid).toHashCode();
	}

	public String getReturndescript() {
		return returndescript;
	}

	public void setReturndescript(String returndescript) {
		this.returndescript = returndescript;
	}

}