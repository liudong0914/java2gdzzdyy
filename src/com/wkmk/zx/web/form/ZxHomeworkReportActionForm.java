package com.wkmk.zx.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.wkmk.zx.bo.ZxHomeworkReport;

/**
 *<p>Description: 在线作业批改报告</p>
 *<p>E-mail: zhangxuedong28@gmail.com</p>
 *@version 1.0
 */
public class ZxHomeworkReportActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String act;
	private ZxHomeworkReport zxHomeworkReport = new ZxHomeworkReport();

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;	}

	public ZxHomeworkReport getZxHomeworkReport(){
		return this.zxHomeworkReport;
	}

	public void setZxHomeworkReport(ZxHomeworkReport zxHomeworkReport){
		this.zxHomeworkReport=zxHomeworkReport;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}
}