package com.wkmk.sys.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.util.bo.BaseObject;

/**
 *<p>Description: 手机短信</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class SysSmsInfo extends BaseObject implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Integer smsid;
	private String content;//短信内容
	private String mobile;//接收短信手机号
	private Integer userid;//关联接收短信用户
	private String state;//状态，0未发送，1发送成功，2发送失败
	private String createdate;//发送短信时间
	private String type;//1普通短信通知（后台发消息时），2修改手机号验证码，3修改支付密码验证码
	private String code;//手机短信6位数字验证码
	private String codevalidtime;//验证有效截止时间，一般为5分钟
	private Integer messageid;//type=1时，记录关联消息id

	public SysSmsInfo(){
	}

 	public Integer getSmsid() {
		return this.smsid;
	}

	public void setSmsid(Integer smsid) {
		this.smsid=smsid;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content=content;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile=mobile;
	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid=userid;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state=state;
	}

	public String getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate=createdate;
	}

	public String toString() {
		return new ToStringBuilder(this)
		.append("smsid",getSmsid()).toString();
	}

	public boolean equals(Object other) {
		if (!(other instanceof SysSmsInfo))
		return false;
		SysSmsInfo castOther = (SysSmsInfo)other;
		return new EqualsBuilder().append(smsid, castOther.smsid).isEquals();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(smsid).toHashCode();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodevalidtime() {
		return codevalidtime;
	}

	public void setCodevalidtime(String codevalidtime) {
		this.codevalidtime = codevalidtime;
	}

	public Integer getMessageid() {
		return messageid;
	}

	public void setMessageid(Integer messageid) {
		this.messageid = messageid;
	}

}