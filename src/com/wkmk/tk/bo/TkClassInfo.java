package com.wkmk.tk.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 班级信息</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class TkClassInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer classid;
	private String classname;//班级名称
	private String classno;//班级唯一编码，RandomCodeUtil.getClassno获取唯一编码
	private Integer students;//学生数量
	private String pwd;//学生进入班级是否需要密码，1需要，默认0不需要
	private String createdate;
	private String twocodepath;//班级二维码图片路径
	private Integer bookid;//对应启东作业本课本
	private Integer userid;
	private Integer unitid;
	private String uploadimg;//1表示可以拍照上传，管理员后台控制，默认0

	public TkClassInfo(){
	}

 	public Integer getClassid() {
		return this.classid;
	}

	public void setClassid(Integer classid) {
		this.classid=classid;
	}

	public String getClassno() {
		return this.classno;
	}

	public void setClassno(String classno) {
		this.classno=classno;
	}

	public Integer getStudents() {
		return this.students;
	}

	public void setStudents(Integer students) {
		this.students=students;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd=pwd;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String getTwocodepath() {
		return this.twocodepath;
	}

	public void setTwocodepath(String twocodepath) {
		this.twocodepath=twocodepath;
	}

	public Integer getBookid() {
		return this.bookid;
	}

	public void setBookid(Integer bookid) {
		this.bookid=bookid;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public Integer getUnitid() {
		return this.unitid;
	}

	public void setUnitid(Integer unitid) {
		this.unitid=unitid;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("classid",getClassid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof TkClassInfo))
		return false;
		TkClassInfo castOther = (TkClassInfo)other;
		return new EqualsBuilder().append(classid, castOther.classid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(classid).toHashCode();
	}

	public String getUploadimg() {
		return uploadimg;
	}

	public void setUploadimg(String uploadimg) {
		this.uploadimg = uploadimg;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

}